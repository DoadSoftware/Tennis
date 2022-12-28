package com.tennis.broadcaster;

import com.tennis.containers.ScoreBug;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBException;

import com.tennis.model.Match;
import com.tennis.model.NameSuper;
import com.tennis.model.Stat;
import com.tennis.service.TennisService;
import com.tennis.util.TennisUtil;

import net.sf.json.JSONArray;

import com.tennis.containers.Scene;

public class ATP_2022 extends Scene {

	public String session_selected_broadcaster = "ATP_2022";

	public ScoreBug scorebug = new ScoreBug();
	public String which_graphics_onscreen = "";
	public boolean is_infobar = false;
	public long last_date = 0;
	
	 private String flag_path = "C:\\\\Images\\\\ATP\\\\Flag\\\\";
	 //private String colors_path ="D:\\DOAD_In_House_Everest\\Everest_Sports\\Everest_I-League_2022\\Colours\\";
	 

	public ATP_2022() {
		super();
	}

	public ScoreBug updateScoreBug(List<Scene> scenes, Match match, PrintWriter print_writer)
			throws InterruptedException, MalformedURLException, IOException {
		if (scorebug.isScorebug_on_screen() == true) {
			scorebug = populateScoreBug(true, scorebug, print_writer, scenes.get(0).getScene_path(), match,session_selected_broadcaster);
			
			if(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getHome_score().equalsIgnoreCase(TennisUtil.GAME)||
					match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getAway_score().equalsIgnoreCase(TennisUtil.GAME)||
					match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getHome_score().equalsIgnoreCase(TennisUtil.ADVANTAGE)||
					match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getAway_score().equalsIgnoreCase(TennisUtil.ADVANTAGE)){
					
					scorebug = populateGameScore(true, scorebug, print_writer, match, session_selected_broadcaster);
					
			}else if(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.NORMAL)
					&& (Integer.valueOf(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getHome_score()) > 0
					&& Integer.valueOf(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getHome_score()) < 30
					|| Integer.valueOf(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getAway_score()) > 0
					&& Integer.valueOf(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getAway_score()) < 30)) {
				scorebug = populateGameScore(false, scorebug, print_writer, match, session_selected_broadcaster);
			}else if(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)
					&& (Integer.valueOf(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getHome_score()) == 0
					&& Integer.valueOf(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getAway_score()) == 1
					|| Integer.valueOf(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getHome_score()) == 1
					&& Integer.valueOf(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getAway_score()) == 0)) {
				scorebug = populateGameScore(false, scorebug, print_writer, match, session_selected_broadcaster);
			}else {
				scorebug = populateGameScore(true, scorebug, print_writer, match, session_selected_broadcaster);
			}
			//scorebug = populateGameScore(true, scorebug, print_writer, match, session_selected_broadcaster);
		}
		return scorebug;
	}

	public Object ProcessGraphicOption(String whatToProcess, Match match, TennisService tennisService,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess)
			throws InterruptedException, NumberFormatException, MalformedURLException, IOException, JAXBException {
		switch (whatToProcess.toUpperCase()) {
		case "POPULATE-SCOREBUG": case "POPULATE-SCOREBUG_STATS": case "POPULATE-SCOREBUG_SET_STATS":
		case "POPULATE-LT-MATCH_RESULTSINGLES": case "POPULATE-LT-MATCH_RESULTDOUBLES": case "POPULATE-LT-MATCHID": case "POPULATE-LT-MATCHID_DOUBLE": case "POPULATE-NAMESUPERDB":
		case "POPULATE-MATCHID_DOUBLE": case "POPULATE-MATCHID": case "POPULATE-FF-MATCH_RESULTSINGLES": case "POPULATE-FF-MATCH_RESULTDOUBLES":
			switch (whatToProcess.toUpperCase()) {
			
			case "POPULATE-SCOREBUG_STATS": case "POPULATE-SCOREBUG_SET_STATS":
				 break;
			 
			case "POPULATE-SCOREBUG":
				// System.out.println("I_League :"+ valueToProcess.split(",")[0]);
				//scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
				scenes.get(0).scene_load(print_writer, session_selected_broadcaster);
				break;
			default:
				scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
				scenes.get(1).scene_load(print_writer, session_selected_broadcaster);
				break;
			}
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-SCOREBUG":
				populateScoreBug(false, scorebug, print_writer, valueToProcess.split(",")[0], match,session_selected_broadcaster);
				break;
			case "POPULATE-SCOREBUG_STATS":
				if(scorebug.getLast_scorebug_stat() != null && !scorebug.getLast_scorebug_stat().trim().isEmpty()) {
					switch(scorebug.getLast_scorebug_stat()) {
					case "doubleFault": case "ace": case "winner": case "firstServeWon": case "secondServeWon": case "error": case "breakPointWon":
					case "setfirstServeWon": case "setsecondServeWon": case "setwinner": case "setace": case "setdoubleFault": case "seterror": case "setbreakPointWon":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*StatType1Out START \0");
						break;
					}
					TimeUnit.MILLISECONDS.sleep(200);
					scorebug.setScorebug_stat(valueToProcess);
					populateScoreBugStats(false,scorebug,print_writer,match,session_selected_broadcaster);
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SoreAllOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					scorebug.setScorebug_stat(valueToProcess);
					populateScoreBugStats(false,scorebug,print_writer,match,session_selected_broadcaster);
				}
				break;
			case "POPULATE-SCOREBUG_SET_STATS":
				if(scorebug.getLast_scorebug_stat() != null && !scorebug.getLast_scorebug_stat().trim().isEmpty()) {
					switch(scorebug.getLast_scorebug_stat()) {
					case "doubleFault": case "ace": case "winner": case "firstServeWon": case "secondServeWon": case "error": case "breakPointWon":
					case "setfirstServeWon": case "setsecondServeWon": case "setwinner": case "setace": case "setdoubleFault": case "seterror": case "setbreakPointWon":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*StatType1Out START \0");
						break;
					}
					TimeUnit.MILLISECONDS.sleep(200);
					scorebug.setScorebug_stat(valueToProcess);
					populateScoreBugStatsSet(false,scorebug,print_writer,match,session_selected_broadcaster);
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SoreAllOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					scorebug.setScorebug_stat(valueToProcess);
					populateScoreBugStatsSet(false,scorebug,print_writer,match,session_selected_broadcaster);
				}
				break;
			case "POPULATE-LT-MATCH_RESULTSINGLES":
				populateLtMatchResultSingles(print_writer, valueToProcess.split(",")[0], match,session_selected_broadcaster);
				break;
			case "POPULATE-FF-MATCH_RESULTSINGLES":
				populateFFMatchResultSingles(print_writer, valueToProcess.split(",")[0], match,session_selected_broadcaster);
				break;
			case "POPULATE-LT-MATCH_RESULTDOUBLES":
				populateLtMatchResultDoubles(print_writer, valueToProcess.split(",")[0], match,session_selected_broadcaster);
				break;
			case "POPULATE-FF-MATCH_RESULTDOUBLES":
				populateFFMatchResultDoubles(print_writer, valueToProcess.split(",")[0], match,session_selected_broadcaster);
				break;
			case "POPULATE-MATCHID_DOUBLE":
				populateMatchIdDouble(print_writer, valueToProcess.split(",")[0],match, session_selected_broadcaster);
				break;
			case "POPULATE-MATCHID":
				populateMatchId(print_writer, valueToProcess.split(",")[0],match, session_selected_broadcaster);
				break;
			case "POPULATE-LT-MATCHID":
				populateltMatchId(print_writer, valueToProcess.split(",")[0],match, session_selected_broadcaster);
				break;
			case "POPULATE-LT-MATCHID_DOUBLE":
				populateltMatchIdDouble(print_writer, valueToProcess.split(",")[0],match, session_selected_broadcaster);
				break;
			case "POPULATE-NAMESUPERDB":
				populateNameSuperDB(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),tennisService.getNameSupers(),
						match, session_selected_broadcaster);
				break;
			}
			
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			return JSONArray.fromObject(tennisService.getNameSupers()).toString();

		case "ANIMATE-IN-SCOREBUG":
		case "ANIMATE-LT-MATCH_RESULTSINGLES": case "ANIMATE-LT-MATCH_RESULTDOUBLES": case "ANIMATE-IN-LT_MATCHID": case "ANIMATE-IN-LT-MATCHID_DOUBLE": case "ANIMATE-LT-NAMESUPERDB":
		case "ANIMATE-IN-MATCHID_DOUBLE": case "ANIMATE-IN-MATCHID": case "ANIMATE-FF-MATCH_RESULTSINGLES": case "ANIMATE-FF-MATCH_RESULTDOUBLES":
			
		case "CLEAR-ALL":
		case "ANIMATE-OUT-SCOREBUG": case "ANIMATE-OUT-SCOREBUG_STAT":
		
		case "ANIMATE-OUT":
			switch (whatToProcess.toUpperCase()) {

			case "ANIMATE-IN-SCOREBUG":
				AnimateInGraphics(print_writer, "SCOREBUG");
				TimeUnit.SECONDS.sleep(1);
				if(match.getSets().get(0).getGames().get(0).getGame_status().equalsIgnoreCase(TennisUtil.END)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SoreAllIn START \0");
					TimeUnit.SECONDS.sleep(1);
				}
				which_graphics_onscreen = "SCOREBUG";
				is_infobar = true;
				scorebug.setScorebug_on_screen(true);
				break;
			case "ANIMATE-LT-MATCH_RESULTSINGLES":
				AnimateInGraphics(print_writer, "LT-MATCH_RESULTSINGLES");
				which_graphics_onscreen = "LT-MATCH_RESULTSINGLES";
				break;
			case "ANIMATE-FF-MATCH_RESULTSINGLES":
				AnimateInGraphics(print_writer, "FF-MATCH_RESULTSINGLES");
				which_graphics_onscreen = "FF-MATCH_RESULTSINGLES";
				break;
			case "ANIMATE-LT-MATCH_RESULTDOUBLES":
				AnimateInGraphics(print_writer, "LT-MATCH_RESULTDOUBLES");
				which_graphics_onscreen = "LT-MATCH_RESULTDOUBLES";
				break;
			case "ANIMATE-FF-MATCH_RESULTDOUBLES":
				AnimateInGraphics(print_writer, "FF-MATCH_RESULTDOUBLES");
				which_graphics_onscreen = "FF-MATCH_RESULTDOUBLES";
				break;
			case "ANIMATE-IN-MATCHID_DOUBLE":
				AnimateInGraphics(print_writer, "MATCHID_DOUBLE");
				which_graphics_onscreen = "MATCHID_DOUBLE";
				break;
			case "ANIMATE-IN-MATCHID":
				AnimateInGraphics(print_writer, "MATCHID");
				which_graphics_onscreen = "MATCHID";
				break;
			case "ANIMATE-IN-LT_MATCHID":
				AnimateInGraphics(print_writer, "LT_MATCHID");
				which_graphics_onscreen = "LT_MATCHID";
				break;
			case "ANIMATE-IN-LT-MATCHID_DOUBLE":
				AnimateInGraphics(print_writer, "LT-MATCHID_DOUBLE");
				which_graphics_onscreen = "LT-MATCHID_DOUBLE";
				break;
			case "ANIMATE-LT-NAMESUPERDB":
				AnimateInGraphics(print_writer, "NAMESUPERDB");
				which_graphics_onscreen = "NAMESUPERDB";
				break;
			case "CLEAR-ALL":
				print_writer.println("-1 SCENE CLEANUP\0");
				print_writer.println("-1 IMAGE CLEANUP\0");
				print_writer.println("-1 GEOM CLEANUP\0");
				print_writer.println("-1 FONT CLEANUP\0");

				print_writer.println("-1 IMAGE INFO\0");
				print_writer.println("-1 RENDERER SET_OBJECT SCENE*" + valueToProcess.split(",")[0] + "\0");

				print_writer.println("-1 RENDERER INITIALIZE\0");
				print_writer.println("-1 RENDERER*SCENE_DATA INITIALIZE\0");
				print_writer.println("-1 RENDERER*UPDATE SET 0\0");
				print_writer.println("-1 RENDERER*STAGE SHOW 0.0\0");

				print_writer.println("-1 RENDERER*UPDATE SET 1\0");

				print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/ScoreBug-Single\0");

				print_writer.println("-1 RENDERER*FRONT_LAYER INITIALIZE\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 0\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0\0");

				print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 1\0");

				print_writer.println("-1 SCENE CLEANUP\0");
				print_writer.println("-1 IMAGE CLEANUP\0");
				print_writer.println("-1 GEOM CLEANUP\0");
				print_writer.println("-1 FONT CLEANUP\0");

				which_graphics_onscreen = "";
				is_infobar = false;
				scorebug.setScorebug_on_screen(false);
				scorebug.setGame_in(false);

				break;
			case "ANIMATE-OUT-SCOREBUG_STAT":
				if(scorebug.getLast_scorebug_stat() != null && !scorebug.getLast_scorebug_stat().trim().isEmpty()) {
					//System.out.println(scorebug.getLast_scorebug_stat());
					switch(scorebug.getLast_scorebug_stat()) {
					case "doubleFault": case "ace": case "winner": case "firstServeWon": case "secondServeWon": case "error": case "breakPointWon":
					case "setfirstServeWon": case "setsecondServeWon": case "setwinner": case "setace": case "setdoubleFault": case "seterror": case "setbreakPointWon":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*StatType1HeadOut START \0");
						TimeUnit.MICROSECONDS.sleep(800);
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*StatType1Out START \0");
						TimeUnit.SECONDS.sleep(1);
						break;
					}
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SoreAllIn START \0");
				}
				scorebug.setLast_scorebug_stat("");
				scorebug.setScorebug_stat("");
				break;
			case "ANIMATE-OUT-SCOREBUG":
				if (is_infobar == true) {
					AnimateOutGraphics(print_writer, "SCOREBUG");
					is_infobar = false;
					scorebug.setScorebug_on_screen(false);
					scorebug.setGame_in(false);
				}
				break;
			
			case "ANIMATE-OUT":
				switch (which_graphics_onscreen) {
				case "LT-MATCH_RESULTSINGLES": case "LT-MATCH_RESULTDOUBLES": case "LT_MATCHID": case "LT-MATCHID_DOUBLE": case "NAMESUPERDB":
				case "MATCHID_DOUBLE": case "MATCHID": case "FF-MATCH_RESULTSINGLES": case "FF-MATCH_RESULTDOUBLES":
					AnimateOutGraphics(print_writer, which_graphics_onscreen);
					which_graphics_onscreen = "";
					break;
				}
				break;
			}
			break;
		}
		return null;
	}

	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException {

		switch (whichGraphic) {
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "LT-MATCH_RESULTSINGLES": case "LT-MATCH_RESULTDOUBLES": case "LT_MATCHID": case "LT-MATCHID_DOUBLE": case "NAMESUPERDB":
		case "MATCHID_DOUBLE": case "MATCHID": case "FF-MATCH_RESULTSINGLES": case "FF-MATCH_RESULTDOUBLES":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;
		}
	}

	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic) {
		switch (whichGraphic.toUpperCase()) {
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "LT-MATCH_RESULTSINGLES": case "LT-MATCH_RESULTDOUBLES": case "LT_MATCHID": case "LT-MATCHID_DOUBLE": case "NAMESUPERDB":
		case "MATCHID_DOUBLE": case "MATCHID": case "FF-MATCH_RESULTSINGLES": case "FF-MATCH_RESULTDOUBLES":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		}
	}
	
	public static List<String> getSpeed(String FileName) throws IOException {
		long last_date = 0;
		String filePath = TennisUtil.TENNIS_DIRECTORY + "/Speeds/" + FileName + ".txt";
        File file = new File(filePath);
        long lastModified = file.lastModified();
		
		List<String> allLines = new ArrayList<String>();
		if(new File(TennisUtil.TENNIS_DIRECTORY + "/Speeds/" + FileName + ".txt").exists()) {
			if(last_date == 0) {
				last_date = lastModified;
				allLines = Files.readAllLines(Paths.get(TennisUtil.TENNIS_DIRECTORY + "/Speeds/" + FileName + ".txt"));
				System.out.println("1 = " + allLines.get(1));
			}else if(last_date != 0 && last_date != lastModified) {
				last_date = lastModified;
				allLines = Files.readAllLines(Paths.get(TennisUtil.TENNIS_DIRECTORY + "/Speeds/" + FileName + ".txt"));
				System.out.println("2 = " + allLines.get(1));
			}
		}
		return allLines;
	}
	
	public ScoreBug populateScoreBug(boolean is_this_updating, ScoreBug scorebug, PrintWriter print_writer,String viz_sence_path, Match match, String selectedbroadcaster) throws IOException {
		if (match == null) {
			System.out.println("ERROR: ScoreBug -> Match is null");
		} else {
			getSpeed("SPEED");
			
			if (is_this_updating == false) {
				if (match.getMatchType().toUpperCase().equalsIgnoreCase(TennisUtil.SINGLES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName1"+ " SET " + 
							match.getHomeFirstPlayer().getTicker_name().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName2"+ " SET " + 
							match.getAwayFirstPlayer().getTicker_name().toUpperCase() + "\0");
				} else if (match.getMatchType().toUpperCase().equalsIgnoreCase(TennisUtil.DOUBLES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName1" + " SET " + 
							match.getHomeFirstPlayer().getTicker_name().toUpperCase()+ " / " + match.getHomeSecondPlayer().getTicker_name().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName2" + " SET " + 
							match.getAwayFirstPlayer().getTicker_name().toUpperCase() + " / " + match.getAwaySecondPlayer().getTicker_name().toUpperCase() + "\0");
				}
			}
			
			List<String> home_data = new ArrayList<String>();
			List<String> away_data = new ArrayList<String>();

			if (match.getSets() != null) {
				for (int i = 0; i <= match.getSets().size() - 1; i++) {
					int home = 0, away = 0;
					for (int j = 0; j <= match.getSets().get(i).getGames().size() - 1; j++) {
						if (match.getSets().get(i).getGames().get(j).getGame_winner() != null) {
							if (match.getSets().get(i).getGames().get(j).getGame_winner().toUpperCase()
									.equalsIgnoreCase("HOME")) {
								home = home + 1;
							} else if (match.getSets().get(i).getGames().get(j).getGame_winner().toUpperCase()
									.equalsIgnoreCase("AWAY")) {
								away = away + 1;
							}
						}
					}
					home_data.add(i, String.valueOf(home));
					away_data.add(i, String.valueOf(away));
				}
			}

			if (match.getSets() != null) {
				if(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getServing_player() == match.getHomeFirstPlayer().getPlayerId()) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$MainScoreBug$ServiceDot*FUNCTION*Omo*vis_con SET 1 \0");
				}else if(match.getSets().get(match.getSets().size()-1).getGames().get(match.getSets().get(match.getSets().size()-1).getGames().size()-1).getServing_player() == match.getAwayFirstPlayer().getPlayerId()) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$MainScoreBug$ServiceDot*FUNCTION*Omo*vis_con SET 2 \0");
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$MainScoreBug$ServiceDot*FUNCTION*Omo*vis_con SET 0 \0");
				}
				for (int i = 0; i <= match.getSets().size() - 1; i++) {
					if (match.getSets().get(i).getSet_status().toUpperCase().equalsIgnoreCase("START")||match.getSets().get(i).getSet_status().toUpperCase().equalsIgnoreCase("END")) {
						if(i==0) {
							if(is_this_updating == false) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$SetOmo*FUNCTION*Omo*vis_con SET 0 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$SetOmo*FUNCTION*Omo*vis_con SET 0 \0");
							}
							if (home_data.get(0).equalsIgnoreCase("0") && away_data.get(0).equalsIgnoreCase("0")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "	+ "tTopRunningSet" + " SET " + "0" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomRunningSet" + " SET " + "0" + "\0");
							} else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopRunningSet" + " SET " + home_data.get(0) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomRunningSet" + " SET " + away_data.get(0) + "\0");
							}
						}else if(i==1) {
							if(is_this_updating == false) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$SetOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$SetOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
								
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
								
								if(Integer.valueOf(home_data.get(0)) > Integer.valueOf(away_data.get(0))) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopSetStatus1" + " SET " + "1" + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomSetStatus1" + " SET " + "0" + "\0");
								}else if(Integer.valueOf(home_data.get(0))  < Integer.valueOf(away_data.get(0))) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopSetStatus1" + " SET " + "0" + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomSetStatus1" + " SET " + "1" + "\0");
								}
								
								if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
									if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
												match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
									}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
												match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
									}
								}
								
							}
							
							if (home_data.get(0).equalsIgnoreCase("0") && away_data.get(0).equalsIgnoreCase("0")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "	+ "tTopRunningSet" + " SET " + "0" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomRunningSet" + " SET " + "0" + "\0");
							} else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopRunningSet" + " SET " + home_data.get(1) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomRunningSet" + " SET " + away_data.get(1) + "\0");
							}
						}else if(i==2) {
							if(is_this_updating == false) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$SetOmo*FUNCTION*Omo*vis_con SET 2 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$SetOmo*FUNCTION*Omo*vis_con SET 2 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomSetScore2" + " SET " + away_data.get(1) + "\0");
								
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + "" + "\0");
								
								if(Integer.valueOf(home_data.get(0)) > Integer.valueOf(away_data.get(0))) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopSetStatus1" + " SET " + "1" + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomSetStatus1" + " SET " + "0" + "\0");
								}else if(Integer.valueOf(home_data.get(0))  < Integer.valueOf(away_data.get(0))) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopSetStatus1" + " SET " + "0" + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomSetStatus1" + " SET " + "1" + "\0");
								}
								
								if(Integer.valueOf(home_data.get(1))  > Integer.valueOf(away_data.get(1))) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopSetStatus2" + " SET " + "1" + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomSetStatus2" + " SET " + "0" + "\0");
								}else if(Integer.valueOf(home_data.get(1))  < Integer.valueOf(away_data.get(1))) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopSetStatus2" + " SET " + "0" + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomSetStatus2" + " SET " + "1" + "\0");
								}
								
								if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
									if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
												match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
									}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
												match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
									}
								}
								
								if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
									if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + 
												match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getAway_score() + "\0");
									}else if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + 
												match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getHome_score() + "\0");
									}
								}
							}
							
							
							if (home_data.get(0).equalsIgnoreCase("0") && away_data.get(0).equalsIgnoreCase("0")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "	+ "tTopRunningSet" + " SET " + "0" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomRunningSet" + " SET " + "0" + "\0");
							} else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopRunningSet" + " SET " + home_data.get(2) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomRunningSet" + " SET " + away_data.get(2) + "\0");
							}
						}
					}
				}
			}
		}
		return scorebug;
	}
	public ScoreBug populateGameScore(boolean is_this_updating,ScoreBug scorebug, PrintWriter print_writer, Match match, String selectedbroadcaster) {
		
		if(is_this_updating == false && scorebug.isGame_in() == false) {
			if(match.getSets().get(0).getGames().get(0).getGame_status().equalsIgnoreCase(TennisUtil.START)) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SoreAllIn START \0");
			}
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*GameIn START \0");
			scorebug.setGame_in(true);
		}
		
		if (match.getSets().get(match.getSets().size() - 1).getGames().get(match.getSets().get(match.getSets().size() - 1).getGames().size()-1).getGame_status().
				equalsIgnoreCase(TennisUtil.START)) {
			
			if (match.getSets().get(match.getSets().size() - 1).getGames().get(match.getSets().get(match.getSets().size() - 1).getGames().size()-1).getHome_score().
					toUpperCase().equalsIgnoreCase(TennisUtil.ADVANTAGE)) {
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopGameScore" + " SET " + "AD" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomGameScore" + " SET " + "" + "\0");
				
			}else if (match.getSets().get(match.getSets().size() - 1).getGames().get(match.getSets().get(match.getSets().size() - 1).getGames().size()-1).
					getAway_score().toUpperCase().equalsIgnoreCase(TennisUtil.ADVANTAGE)) {
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopGameScore" + " SET "+ "" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomGameScore" + " SET " + "AD" + "\0");
				
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tTopGameScore" + " SET " + 
						match.getSets().get(match.getSets().size() - 1).getGames().get(match.getSets().get(match.getSets().size() - 1).getGames().size()-1).getHome_score() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tBottomGameScore" + " SET "+ 
						match.getSets().get(match.getSets().size() - 1).getGames().get(match.getSets().get(match.getSets().size() - 1).getGames().size()-1).getAway_score() + "\0");
			}

		}else if(match.getSets().get(match.getSets().size() - 1).getGames().get(match.getSets().get(match.getSets().size() - 1).getGames().size()-1).getGame_status().
				equalsIgnoreCase(TennisUtil.END)) {
			if(scorebug.isGame_in() == true) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*GameOut START \0");
				scorebug.setGame_in(false);
			}
			
		}
		
		return scorebug;
	}
	public ScoreBug populateScoreBugStats(boolean is_this_updating,ScoreBug scorebug, PrintWriter print_writer, Match match, String selectedbroadcaster) 
			throws MalformedURLException, IOException, InterruptedException {
		
		int Home_df=0,Away_df=0,Home_1won=0,Away_1won=0,Home_2won=0,Away_2won=0,Home_win=0,Away_win=0,Home_ace=0,Away_ace=0,Home_error=0,Away_error=0,Home_bpw=0,Away_bpw=0;
	
		if (match.getSets() != null) {
			//System.out.println("SETS SIZE : " + match.getSets().size());
			//System.out.println("STATS : " + match.getSets().get(0).getGames().get(0).getStats().size());
			for (int i = 0; i <= match.getSets().size() - 1; i++) {
				for (int j = 0; j <= match.getSets().get(i).getGames().size() - 1; j++) {
					for(Stat st : match.getSets().get(i).getGames().get(j).getStats()) {
						if(st.getStatType().equalsIgnoreCase("firstServeWon")) {
							if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
								Home_1won = Home_1won + 1;
							}else {
								Away_1won = Away_1won + 1;
							}
						}else if(st.getStatType().equalsIgnoreCase("secondServeWon")) {
							if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
								Home_2won = Home_2won + 1;
							}else {
								Away_2won = Away_2won + 1;
							}
						}else if(st.getStatType().equalsIgnoreCase(TennisUtil.FOREHAND_WINNER) || st.getStatType().equalsIgnoreCase(TennisUtil.BACKHAND_WINNER) ||
								st.getStatType().equalsIgnoreCase(TennisUtil.SERVE_WINNER)) {
							if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
								Home_win = Home_win + 1;
							}else {
								Away_win = Away_win + 1;
							}
						}else if (st.getStatType().equalsIgnoreCase(TennisUtil.ACE)) {
							if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
								Home_ace = Home_ace + 1;
							}else {
								Away_ace = Away_ace + 1;
							}
						}else if(st.getStatType().equalsIgnoreCase(TennisUtil.DOUBLE_FAULT)) {
							if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
								Home_df = Home_df + 1;
							}else {
								Away_df = Away_df + 1;
							}
						}else if(st.getStatType().equalsIgnoreCase("frontHandError") || st.getStatType().equalsIgnoreCase("backHandError") ||
								st.getStatType().equalsIgnoreCase("serveError")) {
							if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
								Home_error = Home_error + 1;
							}else {
								Away_error = Away_error + 1;
							}
						}else if(st.getStatType().equalsIgnoreCase("breakPointWon")) {
							if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
								Home_bpw = Home_bpw + 1;
							}else {
								Away_bpw = Away_bpw + 1;
							}
						}
					}
				}
			}
		}
		
		//System.out.println("IN ScoreBugStats : " + scorebug.getScorebug_stat());
		//System.out.println("DOUBLE FAULT : " + " Home : " + home_data.get(0) + " Away : " + away_data.get(0));
		
		//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tx" + " SET " + "1" + "\0");
		//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "bx" + " SET " + "1" + "\0");
		if(scorebug.getScorebug_stat().equalsIgnoreCase("firstServeWon")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "1st SERVE WON" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_1won + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_1won + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("secondServeWon")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "2nd SERVE WON" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " +Home_2won + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_2won + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("winner")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "WINNER" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_win + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_win + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase(TennisUtil.ACE)) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "ACES" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_ace + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_ace + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase(TennisUtil.DOUBLE_FAULT)) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "DOUBLE FAULT" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_df + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_df + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("error")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "ERROR" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_error + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_error + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("breakPointWon")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "BREAK POINT WON" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_bpw + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_bpw+ "\0");
		}
		
		if(is_this_updating == false) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$StatGrpAll$StatType1$StatValueGrp$Top$ScoreBg$LoseBg*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$StatGrpAll$StatType1$StatValueGrp$Bottom$ScoreBg$LoseBg*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$StatGrpAll$StatType1$StatValueGrp$Top$ScoreBg$WinBg*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$StatGrpAll$StatType1$StatValueGrp$Bottom$ScoreBg$WinBg*ACTIVE SET 1 \0");
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*StatType1HeadIn START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*StatType1In START \0");
			TimeUnit.MILLISECONDS.sleep(4);
		}
		scorebug.setLast_scorebug_stat(scorebug.getScorebug_stat());
		return scorebug;
	}
	public ScoreBug populateScoreBugStatsSet(boolean is_this_updating,ScoreBug scorebug, PrintWriter print_writer, Match match, String selectedbroadcaster) 
			throws MalformedURLException, IOException, InterruptedException {
		
		int Home_df=0,Away_df=0,Home_1won=0,Away_1won=0,Home_2won=0,Away_2won=0,Home_win=0,Away_win=0,Home_ace=0,Away_ace=0,Home_error=0,Away_error=0,Home_bpw=0,Away_bpw=0;
	
		if (match.getSets() != null) {
			for (int j = 0; j <= match.getSets().get(match.getSets().size()-1).getGames().size() - 1; j++) {
				for(Stat st : match.getSets().get(match.getSets().size()-1).getGames().get(j).getStats()) {
					if(st.getStatType().equalsIgnoreCase("firstServeWon")) {
						if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
							Home_1won = Home_1won + 1;
						}else {
							Away_1won = Away_1won + 1;
						}
					}else if(st.getStatType().equalsIgnoreCase("secondServeWon")) {
						if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
							Home_2won = Home_2won + 1;
						}else {
							Away_2won = Away_2won + 1;
						}
					}else if(st.getStatType().equalsIgnoreCase(TennisUtil.FOREHAND_WINNER) || st.getStatType().equalsIgnoreCase(TennisUtil.BACKHAND_WINNER) ||
							st.getStatType().equalsIgnoreCase(TennisUtil.SERVE_WINNER)) {
						if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
							Home_win = Home_win + 1;
						}else {
							Away_win = Away_win + 1;
						}
					}else if (st.getStatType().equalsIgnoreCase(TennisUtil.ACE)) {
						if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
							Home_ace = Home_ace + 1;
						}else {
							Away_ace = Away_ace + 1;
						}
					}else if(st.getStatType().equalsIgnoreCase(TennisUtil.DOUBLE_FAULT)) {
						if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
							Home_df = Home_df + 1;
						}else {
							Away_df = Away_df + 1;
						}
					}else if(st.getStatType().equalsIgnoreCase("frontHandError") || st.getStatType().equalsIgnoreCase("backHandError") ||
							st.getStatType().equalsIgnoreCase("serveError")) {
						if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
							Home_error = Home_error + 1;
						}else {
							Away_error = Away_error + 1;
						}
					}else if(st.getStatType().equalsIgnoreCase("breakPointWon")) {
						if(match.getHomeFirstPlayerId() == st.getPlayerId() || match.getHomeSecondPlayerId() == st.getPlayerId()) {
							Home_bpw = Home_bpw + 1;
						}else {
							Away_bpw = Away_bpw + 1;
						}
					}
				}
			}
		}
		
		//System.out.println("IN ScoreBugStats : " + scorebug.getScorebug_stat());
		//System.out.println("DOUBLE FAULT : " + " Home : " + home_data.get(0) + " Away : " + away_data.get(0));
		
		//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tx" + " SET " + "1" + "\0");
		//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "bx" + " SET " + "1" + "\0");
		if(scorebug.getScorebug_stat().equalsIgnoreCase("setfirstServeWon")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "1st SERVE WON" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_1won + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_1won + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("setsecondServeWon")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "2nd SERVE WON" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " +Home_2won + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_2won + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("setwinner")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "WINNER" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_win + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_win + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("setace")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "ACES" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_ace + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_ace + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("setdoubleFault")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "DOUBLE FAULT" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_df + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_df + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("seterror")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "DOUBLE FAULT" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_error + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_error + "\0");
		}else if(scorebug.getScorebug_stat().equalsIgnoreCase("breakPointWon")) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeadType1"+ " SET " + "BREAK POINT WON" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopStatValueType1"+ " SET " + Home_bpw + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomStatValueType1"+ " SET " + Away_bpw+ "\0");
		}
		
		if(is_this_updating == false) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$StatGrpAll$StatType1$StatValueGrp$Top$ScoreBg$LoseBg*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$StatGrpAll$StatType1$StatValueGrp$Bottom$ScoreBg$LoseBg*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$StatGrpAll$StatType1$StatValueGrp$Top$ScoreBg$WinBg*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$StatGrpAll$StatType1$StatValueGrp$Bottom$ScoreBg$WinBg*ACTIVE SET 1 \0");
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*StatType1HeadIn START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*StatType1In START \0");
			TimeUnit.MILLISECONDS.sleep(4);
		}
		scorebug.setLast_scorebug_stat(scorebug.getScorebug_stat());
		return scorebug;
	}
	
	public void populateLtMatchResultSingles(PrintWriter print_writer, String viz_sence_path, Match match,String selectedbroadcaster) {
		if (match == null) {
			System.out.println("ERROR: MATCH RESULT -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + match.getMatchIdent() + "\0");
			
			if(match.getHomeFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName1" + " SET " + match.getHomeFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountry1" + " SET " + match.getHomeFirstPlayer().getNationality() + "\0");
			if(match.getHomeFirstPlayer().getRankingSingle() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank1" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank1" + " SET " + match.getHomeFirstPlayer().getRankingSingle() + "\0");
			}
			
			if(match.getAwayFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName2" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName2" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName2" + " SET " + match.getAwayFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountry2" + " SET " + match.getAwayFirstPlayer().getNationality() + "\0");
			if(match.getAwayFirstPlayer().getRankingSingle() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank2" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank2" + " SET " + match.getAwayFirstPlayer().getRankingSingle() + "\0");
			}
			
			List<String> home_data = new ArrayList<String>();
			List<String> away_data = new ArrayList<String>();
			
			if(match.getSets() != null) {
				
				for (int i = 0; i <= match.getSets().size() - 1; i++) {
					int home = 0, away = 0;
					for (int j = 0; j <= match.getSets().get(i).getGames().size() - 1; j++) {
						if (match.getSets().get(i).getGames().get(j).getGame_winner() != null) {
							if (match.getSets().get(i).getGames().get(j).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
								home = home + 1;
							} else if (match.getSets().get(i).getGames().get(j).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
								away = away + 1;
							}
						}
					}
					home_data.add(i, String.valueOf(home));
					away_data.add(i, String.valueOf(away));
				}
				
				if(match.getSets().size() == 2) {
					if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "0" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "5" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + "" + "\0");
					
					
					if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getHome_score() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1)+ "\0");
					
				}else if(match.getSets().size() == 3) {
					if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "1" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "2" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "3" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "4" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore3" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore3" + " SET " + "" + "\0");
					
					if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getHome_score() + "\0");
						}
					}
					
					if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore3" + " SET " + 
									match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore3" + " SET " + 
									match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore3" + " SET " + home_data.get(2) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1)+ "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore3" + " SET " + away_data.get(2)+ "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 1.220 \0");
		}
	}
	public void populateLtMatchResultDoubles(PrintWriter print_writer, String viz_sence_path, Match match,String selectedbroadcaster) {
		if (match == null) {
			System.out.println("ERROR: MATCH RESULT -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + match.getMatchIdent() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNameA1" + " SET " + match.getHomeFirstPlayer().getFull_name() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountryA1" + " SET " + match.getHomeFirstPlayer().getNationality() + "\0");
			if(match.getHomeFirstPlayer().getRankingDouble() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankA1" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankA1" + " SET " + match.getHomeFirstPlayer().getRankingDouble() + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNameA2" + " SET " + match.getHomeSecondPlayer().getFull_name() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountryA2" + " SET " + match.getHomeSecondPlayer().getNationality() + "\0");
			if(match.getHomeSecondPlayer().getRankingDouble() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankA2" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankA2" + " SET " + match.getHomeSecondPlayer().getRankingDouble() + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNameB1" + " SET " + match.getAwayFirstPlayer().getFull_name() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountryB1" + " SET " + match.getAwayFirstPlayer().getNationality() + "\0");
			if(match.getAwayFirstPlayer().getRankingDouble() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankB1" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankB1" + " SET " + match.getAwayFirstPlayer().getRankingDouble() + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNameB2" + " SET " + match.getAwaySecondPlayer().getFull_name() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountryB2" + " SET " + match.getAwaySecondPlayer().getNationality() + "\0");
			if(match.getAwaySecondPlayer().getRankingDouble() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankB2" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankB2" + " SET " + match.getAwaySecondPlayer().getRankingDouble() + "\0");
			}
			
			
			List<String> home_data = new ArrayList<String>();
			List<String> away_data = new ArrayList<String>();
			
			if(match.getSets() != null) {
				
				for (int i = 0; i <= match.getSets().size() - 1; i++) {
					int home = 0, away = 0;
					for (int j = 0; j <= match.getSets().get(i).getGames().size() - 1; j++) {
						if (match.getSets().get(i).getGames().get(j).getGame_winner() != null) {
							if (match.getSets().get(i).getGames().get(j).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
								home = home + 1;
							} else if (match.getSets().get(i).getGames().get(j).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
								away = away + 1;
							}
						}
					}
					home_data.add(i, String.valueOf(home));
					away_data.add(i, String.valueOf(away));
				}
				
				if(match.getSets().size() == 2) {
					if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "0" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "5" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + "" + "\0");
					
					
					if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getHome_score() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1)+ "\0");
					
				}else if(match.getSets().size() == 3) {
					if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "1" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "2" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "3" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "4" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore3" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore3" + " SET " + "" + "\0");
					
					if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getHome_score() + "\0");
						}
					}
					
					if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore3" + " SET " + 
									match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore3" + " SET " + 
									match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore3" + " SET " + home_data.get(2) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1)+ "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore3" + " SET " + away_data.get(2)+ "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 1.220 \0");
		}
	}
	
	public void populateFFMatchResultSingles(PrintWriter print_writer, String viz_sence_path, Match match,String selectedbroadcaster) {
		if (match == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vResults" + " SET " + "1" + "\0");
			
			if(match.getHomeFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName1" + " SET " + match.getHomeFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountry1" + " SET " + match.getHomeFirstPlayer().getNationality() + "\0");
			if(match.getHomeFirstPlayer().getRankingSingle() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank1" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank1" + " SET " + match.getHomeFirstPlayer().getRankingSingle() + "\0");
			}
			
			if(match.getAwayFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName2" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName2" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName2" + " SET " + match.getAwayFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountry2" + " SET " + match.getAwayFirstPlayer().getNationality() + "\0");
			if(match.getAwayFirstPlayer().getRankingSingle() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank2" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank2" + " SET " + match.getAwayFirstPlayer().getRankingSingle() + "\0");
			}
			
			List<String> home_data = new ArrayList<String>();
			List<String> away_data = new ArrayList<String>();
			
			if(match.getSets() != null) {
				
				for (int i = 0; i <= match.getSets().size() - 1; i++) {
					int home = 0, away = 0;
					for (int j = 0; j <= match.getSets().get(i).getGames().size() - 1; j++) {
						if (match.getSets().get(i).getGames().get(j).getGame_winner() != null) {
							if (match.getSets().get(i).getGames().get(j).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
								home = home + 1;
							} else if (match.getSets().get(i).getGames().get(j).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
								away = away + 1;
							}
						}
					}
					home_data.add(i, String.valueOf(home));
					away_data.add(i, String.valueOf(away));
				}
				
				if(match.getSets().size() == 2) {
					if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "0" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "5" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + "" + "\0");
					
					
					if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getHome_score() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1)+ "\0");
					
				}else if(match.getSets().size() == 3) {
					if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "1" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "2" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "3" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "4" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore3" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore3" + " SET " + "" + "\0");
					
					if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getHome_score() + "\0");
						}
					}
					
					if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore3" + " SET " + 
									match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore3" + " SET " + 
									match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore3" + " SET " + home_data.get(2) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1)+ "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore3" + " SET " + away_data.get(2)+ "\0");
				}
			}
			
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 2.520 \0");
		}
	}
	public void populateFFMatchResultDoubles(PrintWriter print_writer, String viz_sence_path, Match match,String selectedbroadcaster) {
		if (match == null) {
			System.out.println("ERROR: H2H -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getCategoryType().toUpperCase() + " " + match.getMatchType().toUpperCase()
					+ " - " + match.getMatchIdent() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vResults" + " SET " + "1" + "\0");
			//print_writer.println("-1 RENDERER*TREE*$Main$All$BottomOut$BottomGrp$BottttomGrp$NameGrpAll$PlayerAll1$TopPlayerGrp1$SlashIn*ACTIVE SET 0 \0");
			//print_writer.println("-1 RENDERER*TREE*$Main$All$BottomOut$BottomGrp$BottttomGrp$NameGrpAll$PlayerAll1$TopPlayerGrp1$SlashIn*ACTIVE SET 0 \0");
			
			if(match.getHomeFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftLastName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftFirstName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftLastName1" + " SET " + match.getHomeFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftCountry1" + " SET " + match.getHomeFirstPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLeftFlag1" + " SET " + flag_path + 
					match.getHomeFirstPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			if(match.getHomeSecondPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftFirstName2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftLastName2" + " SET " + match.getHomeSecondPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftFirstName2" + " SET " + match.getHomeSecondPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftLastName2" + " SET " + match.getHomeSecondPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftCountry2" + " SET " + match.getHomeSecondPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLeftFlag2" + " SET " + flag_path + 
					match.getHomeSecondPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			if(match.getAwayFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName1" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName1" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName1" + " SET " + match.getAwayFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightCountry1" + " SET " + match.getAwayFirstPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRightFlag1" + " SET " + flag_path + 
					match.getAwayFirstPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			if(match.getAwaySecondPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName2" + " SET " + match.getAwaySecondPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName2" + " SET " + match.getAwaySecondPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName2" + " SET " + match.getAwaySecondPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightCountry2" + " SET " + match.getAwaySecondPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRightFlag2" + " SET " + flag_path + 
					match.getAwaySecondPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			List<String> home_data = new ArrayList<String>();
			List<String> away_data = new ArrayList<String>();
			
			if(match.getSets() != null) {
				
				for (int i = 0; i <= match.getSets().size() - 1; i++) {
					int home = 0, away = 0;
					for (int j = 0; j <= match.getSets().get(i).getGames().size() - 1; j++) {
						if (match.getSets().get(i).getGames().get(j).getGame_winner() != null) {
							if (match.getSets().get(i).getGames().get(j).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
								home = home + 1;
							} else if (match.getSets().get(i).getGames().get(j).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
								away = away + 1;
							}
						}
					}
					home_data.add(i, String.valueOf(home));
					away_data.add(i, String.valueOf(away));
				}
				
				if(match.getSets().size() == 2) {
					if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "0" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "5" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + "" + "\0");
					
					
					if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getHome_score() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1)+ "\0");
					
				}else if(match.getSets().size() == 3) {
					if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "1" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "2" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.HOME) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "3" + "\0");
					}else if(match.getSets().get(0).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY) && match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.HOME)
							&& match.getSets().get(1).getSet_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSetStatus" + " SET " + "4" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore3" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore3" + " SET " + "" + "\0");
					
					if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore1" + " SET " + 
									match.getSets().get(0).getGames().get(match.getSets().get(0).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore2" + " SET " + 
									match.getSets().get(1).getGames().get(match.getSets().get(1).getGames().size()-1).getHome_score() + "\0");
						}
					}
					
					if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_type().equalsIgnoreCase(TennisUtil.TIE_BREAK)) {
						if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.HOME)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomTieBreakScore3" + " SET " + 
									match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getAway_score() + "\0");
						}else if(match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getGame_winner().equalsIgnoreCase(TennisUtil.AWAY)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopTieBreakScore3" + " SET " + 
									match.getSets().get(2).getGames().get(match.getSets().get(2).getGames().size()-1).getAway_score() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore3" + " SET " + home_data.get(2) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1)+ "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore3" + " SET " + away_data.get(2)+ "\0");
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 2.000 \0");
		}
	}
	
	public void populateNameSuperDB(PrintWriter print_writer, String viz_sence_path,int namesuperId,List<NameSuper> NameSuper,Match match,String selectedbroadcaster) {
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName1" + " SET " + NameSuper.get(namesuperId-1).getFirstname() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName1" + " SET " + NameSuper.get(namesuperId-1).getSurname() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + NameSuper.get(namesuperId-1).getSubLine() + "\0");
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 0.980 \0");
	}
	
	public void populateMatchIdDouble(PrintWriter print_writer, String viz_sence_path, Match match,String selectedbroadcaster) {
		if (match == null) {
			System.out.println("ERROR: H2H -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getCategoryType().toUpperCase() + " " + match.getMatchType().toUpperCase()
					+ " - " + match.getMatchIdent() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vResults" + " SET " + "0" + "\0");
			//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vDiscipline" + " SET " + "2" + "\0");
			
			if(match.getHomeFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftLastName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftFirstName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftLastName1" + " SET " + match.getHomeFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftCountry1" + " SET " + match.getHomeFirstPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLeftFlag1" + " SET " + flag_path + 
					match.getHomeFirstPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			if(match.getHomeSecondPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftFirstName2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftLastName2" + " SET " + match.getHomeSecondPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftFirstName2" + " SET " + match.getHomeSecondPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftLastName2" + " SET " + match.getHomeSecondPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftCountry2" + " SET " + match.getHomeSecondPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLeftFlag2" + " SET " + flag_path + 
					match.getHomeSecondPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			if(match.getAwayFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName1" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName1" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName1" + " SET " + match.getAwayFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightCountry1" + " SET " + match.getAwayFirstPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRightFlag1" + " SET " + flag_path + 
					match.getAwayFirstPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			if(match.getAwaySecondPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName2" + " SET " + match.getAwaySecondPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightFirstName2" + " SET " + match.getAwaySecondPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightLastName2" + " SET " + match.getAwaySecondPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightCountry2" + " SET " + match.getAwaySecondPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRightFlag2" + " SET " + flag_path + 
					match.getAwaySecondPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 2.000 \0");
		}
	}
	public void populateMatchId(PrintWriter print_writer, String viz_sence_path, Match match,String selectedbroadcaster) {
		if (match == null) {
			System.out.println("ERROR: H2H -> Match is null");
		} else {
			
			//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + match.getTournament() + "\0");
			//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getCategoryType().toUpperCase() + " " + match.getMatchType().toUpperCase()
					//+ " - " + match.getMatchIdent() + "\0");
			//print_writer.println("-1 RENDERER*TREE*$Main$All$BottomOut$BottomGrp$BottttomGrp*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$TopGrp$HeaderGrp$Header*GEOM*TEXT SET " + match.getTournament() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vResults" + " SET " + "0" + "\0");
			
			if(match.getHomeFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName1" + " SET " + match.getHomeFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountry1" + " SET " + match.getHomeFirstPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag1" + " SET " + flag_path + 
					match.getHomeFirstPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			if(match.getAwayFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName2" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName2" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName2" + " SET " + match.getAwayFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountry2" + " SET " + match.getAwayFirstPlayer().getNationality() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag2" + " SET " + flag_path + 
					match.getAwayFirstPlayer().getNationality() + TennisUtil.PNG_EXTENSION + "\0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 2.520 \0");
		}
	}
	
	public void populateltMatchId(PrintWriter print_writer, String viz_sence_path, Match match,String selectedbroadcaster) {
		if (match == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vResults" + " SET " + "0" + "\0");
			
			if(match.getHomeFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName1" + " SET " + match.getHomeFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountry1" + " SET " + match.getHomeFirstPlayer().getNationality() + "\0");
			if(match.getHomeFirstPlayer().getRankingSingle() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank1" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank1" + " SET " + match.getHomeFirstPlayer().getRankingSingle() + "\0");
			}
			
			if(match.getAwayFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName2" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName2" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName2" + " SET " + match.getAwayFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountry2" + " SET " + match.getAwayFirstPlayer().getNationality() + "\0");
			if(match.getAwayFirstPlayer().getRankingSingle() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank2" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRank2" + " SET " + match.getAwayFirstPlayer().getRankingSingle() + "\0");
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 1.000 \0");
		}
	}
	public void populateltMatchIdDouble(PrintWriter print_writer, String viz_sence_path, Match match,String selectedbroadcaster) {
		if (match == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + match.getTournament() + "\0");
			
			if(match.getHomeFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstNameA1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastNameA1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstNameA1" + " SET " + match.getHomeFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastNameA1" + " SET " + match.getHomeFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountryA1" + " SET " + match.getHomeFirstPlayer().getNationality() + "\0");
			if(match.getHomeFirstPlayer().getRankingDouble() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankA1" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankA1" + " SET " + match.getHomeFirstPlayer().getRankingSingle() + "\0");
			}
			
			if(match.getHomeSecondPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstNameA2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastNameA2" + " SET " + match.getHomeSecondPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstNameA2" + " SET " + match.getHomeSecondPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastNameA2" + " SET " + match.getHomeSecondPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountryA2" + " SET " + match.getHomeSecondPlayer().getNationality() + "\0");
			if(match.getHomeSecondPlayer().getRankingDouble() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankA2" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankA2" + " SET " + match.getHomeSecondPlayer().getRankingSingle() + "\0");
			}
			
			if(match.getAwayFirstPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstNameB1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastNameB1" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstNameB1" + " SET " + match.getAwayFirstPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastNameB1" + " SET " + match.getAwayFirstPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountryB1" + " SET " + match.getAwayFirstPlayer().getNationality() + "\0");
			if(match.getAwayFirstPlayer().getRankingDouble() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankB1" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankB1" + " SET " + match.getAwayFirstPlayer().getRankingSingle() + "\0");
			}
			
			if(match.getAwaySecondPlayer().getSurname() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstNameB2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastNameAB2" + " SET " + match.getAwaySecondPlayer().getFirstname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstNameB2" + " SET " + match.getAwaySecondPlayer().getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastNameB2" + " SET " + match.getAwaySecondPlayer().getSurname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCountryB2" + " SET " + match.getAwaySecondPlayer().getNationality() + "\0");
			if(match.getAwaySecondPlayer().getRankingDouble() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankB2" + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRankB2" + " SET " + match.getAwaySecondPlayer().getRankingSingle() + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 1.000 \0");
		}
	}
}
