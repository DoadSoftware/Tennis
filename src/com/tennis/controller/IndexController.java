package com.tennis.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tennis.containers.Scene;
import com.tennis.containers.ScoreBug;
import com.tennis.broadcaster.ATP_2022;
import com.tennis.model.Clock;
import com.tennis.model.Configurations;
import com.tennis.model.Event;
import com.tennis.model.EventFile;
import com.tennis.model.Game;
import com.tennis.model.Match;
import com.tennis.model.Set;
import com.tennis.service.TennisService;
import com.tennis.util.TennisFunctions;
import com.tennis.util.TennisUtil;
import net.sf.json.JSONObject;

@Controller
public class IndexController 
{
	@Autowired
	TennisService tennisService;
	
	public static String expiry_date = "2022-12-31";
	public static String current_date = "";
	public static String error_message = "";
	public static ATP_2022 this_ATP_2022;
	public static Match session_match = new Match();
	public static EventFile session_event = new EventFile();
	public static Clock session_clock = new Clock();
	public static Configurations session_Configurations = new Configurations();
	
	List<Scene> session_selected_scenes = new ArrayList<Scene>();
	public static String session_selected_broadcaster;
	public static Socket session_socket;
	public static PrintWriter print_writer;
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model) throws JAXBException, IOException, ParseException 
	{
		
		if(current_date == null || current_date.isEmpty()) {
			current_date = TennisFunctions.getOnlineCurrentDate();
		}
		model.addAttribute("session_viz_scenes", new File(TennisUtil.TENNIS_DIRECTORY + 
				TennisUtil.SCENES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".via") && pathname.isFile();
		    }
		}));

		model.addAttribute("match_files", new File(TennisUtil.TENNIS_DIRECTORY 
				+ TennisUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".xml") && pathname.isFile();
		    }
		}));
		
		if(new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CONFIGURATIONS_DIRECTORY + TennisUtil.OUTPUT_XML).exists()) {
			session_Configurations = (Configurations)JAXBContext.newInstance(
					Configurations.class).createUnmarshaller().unmarshal(
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CONFIGURATIONS_DIRECTORY 
					+ TennisUtil.OUTPUT_XML));
		} else {
			session_Configurations = new Configurations();
			JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CONFIGURATIONS_DIRECTORY + 
					TennisUtil.OUTPUT_XML));
		}
		
		model.addAttribute("session_Configurations",session_Configurations);
	
		return "initialise";
	}
	
	@RequestMapping(value = {"/setup"}, method = RequestMethod.POST)
	public String setupPage(ModelMap model) throws JAXBException, IllegalAccessException, 
		InvocationTargetException, IOException, ParseException  
	{
		model.addAttribute("match_files", new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".xml") && pathname.isFile();
		    }
		}));
		model.addAttribute("players", tennisService.getAllPlayer());
		model.addAttribute("licence_expiry_message",
				"Software licence expires on " + new SimpleDateFormat("E, dd MMM yyyy").format(
				new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date)));

		return "setup";
	}

	@RequestMapping(value = {"/match"}, method = {RequestMethod.POST,RequestMethod.GET})
	public String footballMatchPage(ModelMap model, 
		@RequestParam(value = "selectedBroadcaster", required = false, defaultValue = "") String selectedBroadcaster,
		@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddresss,
		@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") String vizPortNumber,
		@RequestParam(value = "vizScene", required = false, defaultValue = "") String vizScene)
			throws IOException, ParseException, JAXBException, InterruptedException  
	{
		if(current_date == null || current_date.isEmpty()) {
		
			model.addAttribute("error_message","You must be connected to the internet online");
			return "error";
		
		} else if(new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date).before(new SimpleDateFormat("yyyy-MM-dd").parse(current_date))) {
			
			model.addAttribute("error_message","This software has expired");
			return "error";
			
		}else {
			
			model.addAttribute("match_files", new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
				@Override
			    public boolean accept(File pathname) {
			        String name = pathname.getName().toLowerCase();
			        return name.endsWith(".xml") && pathname.isFile();
			    }
			}));

			model.addAttribute("licence_expiry_message",
				"Software licence expires on " + new SimpleDateFormat("E, dd MMM yyyy").format(
				new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date)));
			
			session_match = new Match();
			session_event = new EventFile();
			if(session_event.getEvents() == null || session_event.getEvents().size() <= 0)
				session_event.setEvents(new ArrayList<Event>());
			if(session_match.getSets() == null || session_match.getSets().size() <= 0)
				session_match.setSets(new ArrayList<Set>());
			
			session_Configurations.setBroadcaster(selectedBroadcaster);
			session_Configurations.setVizscene(vizScene);
			session_Configurations.setIpAddress(vizIPAddresss);
			if(!vizPortNumber.trim().isEmpty()) {
				session_Configurations.setPortNumber(Integer.valueOf(vizPortNumber));
				session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
				print_writer = new PrintWriter(session_socket.getOutputStream(), true);
			}
			session_selected_broadcaster = selectedBroadcaster;
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case TennisUtil.ATP_2022:
				session_selected_scenes.add(new Scene("/Default/ScoreBug-Single","FRONT_LAYER")); // Front layer
				session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				session_selected_scenes.get(0).scene_load(print_writer, session_selected_broadcaster);
				this_ATP_2022 = new ATP_2022();
				this_ATP_2022.scorebug = new ScoreBug();
				break;
			}
			
			JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CONFIGURATIONS_DIRECTORY + 
					TennisUtil.OUTPUT_XML));
			
			model.addAttribute("session_selected_broadcaster", session_selected_broadcaster);
			
			return "match";
		}
	}
	
	@RequestMapping(value = {"/back_to_match"}, method = RequestMethod.POST)
	public String backToMatchPage(ModelMap model) throws ParseException
	{
		if(current_date == null || current_date.isEmpty()) {
		
			model.addAttribute("error_message","You must be connected to the internet online");
			return "error";
		
		} else if(new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date).before(new SimpleDateFormat("yyyy-MM-dd").parse(current_date))) {
			
			model.addAttribute("error_message","This software has expired");
			return "error";
			
		}else {
		
			model.addAttribute("match_files", new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
				@Override
			    public boolean accept(File pathname) {
			        String name = pathname.getName().toLowerCase();
			        return name.endsWith(".xml") && pathname.isFile();
			    }
			}));
			model.addAttribute("licence_expiry_message",
				"Software licence expires on " + new SimpleDateFormat("E, dd MMM yyyy").format(
				new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date)));
			
			model.addAttribute("session_selected_broadcaster", session_selected_broadcaster);

			return "match";
		
		}
	}	
	
	@RequestMapping(value = {"/upload_match_setup_data", "/reset_and_upload_match_setup_data"}
		,method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String uploadFormDataToSessionObjects(MultipartHttpServletRequest request) 
			throws IllegalAccessException, InvocationTargetException, JAXBException, IOException
	{
		if (request.getRequestURI().contains("upload_match_setup_data") 
				|| request.getRequestURI().contains("reset_and_upload_match_setup_data")) {
			
	   		boolean reset_all_variables = false;
			if(request.getRequestURI().contains("reset_and_upload_match_setup_data")) {
				reset_all_variables = true;
			} else if(request.getRequestURI().contains("upload_match_setup_data")) {
				for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
					if(entry.getKey().equalsIgnoreCase("select_existing_tennis_matches") && entry.getValue()[0].equalsIgnoreCase("new_match")) {
						reset_all_variables = true;
						break;
					}
				}
			}
			if(reset_all_variables == true) {
				session_match = new Match(); 
				session_event = new EventFile();
				session_event.setEvents(new ArrayList<Event>());
			}
			
			for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
   				BeanUtils.setProperty(session_match, entry.getKey(), entry.getValue()[0]);
	   		}
			
			new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY + session_match.getMatchFileName()).createNewFile();
			new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.EVENT_DIRECTORY + session_match.getMatchFileName()).createNewFile();
			
			JAXBContext.newInstance(Match.class).createMarshaller().marshal(session_match, 
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY + session_match.getMatchFileName()));

			JAXBContext.newInstance(EventFile.class).createMarshaller().marshal(session_event, 
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.EVENT_DIRECTORY + session_match.getMatchFileName()));

		}
		session_match.setEvents(session_event.getEvents());
		return JSONObject.fromObject(session_match).toString();
	}
	
	@RequestMapping(value = {"/processTennisProcedures"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processTennisProcedures(
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess)
					throws JAXBException, IllegalAccessException, InvocationTargetException, IOException, NumberFormatException, InterruptedException
	{	
		switch (whatToProcess.toUpperCase()) {
		case "READ_CLOCK":
			if(new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CLOCK_XML).exists()) {
				session_match.setClock((Clock) JAXBContext.newInstance(Clock.class).createUnmarshaller().unmarshal(
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CLOCK_XML)));
			}
			
			switch (session_selected_broadcaster) {
			case TennisUtil.ATP_2022:
				this_ATP_2022.updateScoreBug(session_selected_scenes, session_match, print_writer);
				break;
			}
			return JSONObject.fromObject(session_match).toString();
			
		case TennisUtil.LOAD_MATCH: case TennisUtil.LOAD_SETUP:

			session_match = TennisFunctions.populateMatchVariables(tennisService, 
					(Match) JAXBContext.newInstance(Match.class).createUnmarshaller().unmarshal(
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY + valueToProcess)));
			
			return JSONObject.fromObject(session_match).toString();
			
		case TennisUtil.LOG_SET:
			
			if(session_match.getSets() == null) {
				session_match.setSets(new ArrayList<Set>());
			}
			if(valueToProcess.toUpperCase().contains(TennisUtil.START)) {
				session_match.getSets().add(new Set(session_match.getSets().size() + 1, TennisUtil.START, new ArrayList<Game>()));
			} else if(valueToProcess.toUpperCase().contains(TennisUtil.RESET)) {
				session_match.getSets().remove(session_match.getSets().size()-1);
			} else if(valueToProcess.toUpperCase().contains(TennisUtil.END)) {
				session_match.getSets().get(session_match.getSets().size()-1).setSet_status(TennisUtil.END);
				session_match.getSets().get(session_match.getSets().size()-1).setSet_winner(valueToProcess.split(",")[1]);
			}
			
			JAXBContext.newInstance(Match.class).createMarshaller().marshal(session_match, 
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY + session_match.getMatchFileName()));
			
			return JSONObject.fromObject(session_match).toString();
			
		case TennisUtil.LOG_GAME: case TennisUtil.LOG_SERVE:
			
			if(session_match.getSets() == null) {
				session_match.setSets(new ArrayList<Set>());
			}
			if(session_match.getSets().get(session_match.getSets().size()-1).getGames() == null) {
				session_match.getSets().get(session_match.getSets().size()-1).setGames(new ArrayList<Game>());
			}
			switch (whatToProcess.toUpperCase()) {
			case TennisUtil.LOG_SERVE:
				
				session_match.getSets().get(session_match.getSets().size()-1).getGames().get(
					session_match.getSets().get(session_match.getSets().size()-1).getGames().size()-1)
					.setServing_player(valueToProcess);
				
				break;
				
			case TennisUtil.LOG_GAME: 
				
				if(valueToProcess.toUpperCase().contains(TennisUtil.START)) {
					session_match.getSets().get(session_match.getSets().size()-1).getGames().add(
						new Game(session_match.getSets().get(session_match.getSets().size()-1).getGames().size() + 1, 
						TennisUtil.LOVE, TennisUtil.LOVE, TennisUtil.START, valueToProcess.split(",")[1]));
				} else if(valueToProcess.toUpperCase().contains(TennisUtil.RESET)) {
					session_match.getSets().get(session_match.getSets().size()-1).getGames().remove(
							session_match.getSets().get(session_match.getSets().size()-1).getGames().size()-1);
				} else if(valueToProcess.toUpperCase().contains(TennisUtil.END)) {
					session_match.getSets().get(session_match.getSets().size()-1).getGames().get(
						session_match.getSets().get(session_match.getSets().size()-1)
						.getGames().size()-1).setGame_status(TennisUtil.END);
					session_match.getSets().get(session_match.getSets().size()-1).getGames().get(
							session_match.getSets().get(session_match.getSets().size()-1)
							.getGames().size()-1).setGame_winner(valueToProcess.split(",")[1]);
				}
				break;
			}

			JAXBContext.newInstance(Match.class).createMarshaller().marshal(session_match, 
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY + session_match.getMatchFileName()));
			
			return JSONObject.fromObject(session_match).toString();

		case TennisUtil.LOG_SCORE:
			
			if (valueToProcess.toUpperCase().contains(TennisUtil.SCORE)) {
				if (valueToProcess.toUpperCase().contains(TennisUtil.HOME)) {
					if(valueToProcess.toUpperCase().contains(TennisUtil.INCREMENT)) {
						
						session_match.getSets().get(session_match.getSets().size()-1)
							.getGames().get(session_match.getSets().get(
							session_match.getSets().size()-1).getGames().size()-1).setHome_score(
							TennisFunctions.processScore(session_match, session_match.getSets().size() - 1, 
							session_match.getSets().get(session_match.getSets().size()-1).getGames().size()-1, 
							TennisUtil.HOME, TennisUtil.INCREMENT));
						
					} else if(valueToProcess.toUpperCase().contains(TennisUtil.DECREMENT)) {

						session_match.getSets().get(session_match.getSets().size()-1)
							.getGames().get(session_match.getSets().get(
							session_match.getSets().size()-1).getGames().size()-1).setHome_score(
							TennisFunctions.processScore(session_match, session_match.getSets().size() - 1, 
							session_match.getSets().get(session_match.getSets().size()-1).getGames().size()-1, 
							TennisUtil.HOME, TennisUtil.DECREMENT));

					}
					
				} else if (valueToProcess.toUpperCase().contains(TennisUtil.AWAY)) {
					if(valueToProcess.toUpperCase().contains(TennisUtil.INCREMENT)) {

						session_match.getSets().get(session_match.getSets().size()-1)
							.getGames().get(session_match.getSets().get(
							session_match.getSets().size()-1).getGames().size()-1).setAway_score(
							TennisFunctions.processScore(session_match, session_match.getSets().size() - 1, 
							session_match.getSets().get(session_match.getSets().size()-1).getGames().size()-1, 
							TennisUtil.AWAY, TennisUtil.INCREMENT));
						
					} else if(valueToProcess.toUpperCase().contains(TennisUtil.DECREMENT)) {

						session_match.getSets().get(session_match.getSets().size()-1)
							.getGames().get(session_match.getSets().get(
							session_match.getSets().size()-1).getGames().size()-1).setAway_score(
							TennisFunctions.processScore(session_match, session_match.getSets().size() - 1, 
							session_match.getSets().get(session_match.getSets().size()-1).getGames().size()-1, 
							TennisUtil.AWAY, TennisUtil.DECREMENT));

					}
				}
			}
			JAXBContext.newInstance(Match.class).createMarshaller().marshal(session_match, 
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY + session_match.getMatchFileName()));
			
			return JSONObject.fromObject(session_match).toString();
			
		default:
			
			switch (session_selected_broadcaster) {
			case TennisUtil.ATP_2022:
				this_ATP_2022.ProcessGraphicOption(whatToProcess, session_match, tennisService, print_writer, session_selected_scenes, valueToProcess);
				break;
			}
			return JSONObject.fromObject(session_match).toString();
		}
	}
}