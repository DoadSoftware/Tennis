package com.tennis.broadcaster;

import com.tennis.containers.ScoreBug;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;


import com.tennis.model.Match;


import com.tennis.service.TennisService;
import com.tennis.containers.Scene;

public class ATP_2022 extends Scene {
	
	public String session_selected_broadcaster = "ATP_2022";
	
	public ScoreBug scorebug = new ScoreBug(); 
	public String which_graphics_onscreen = "";
	public boolean is_infobar = false;
	private String status;
	/*
	 * private String logo_path =
	 * "D:\\DOAD_In_House_Everest\\Everest_Sports\\Everest_I-League_2022\\Logos\\";
	 * private String colors_path =
	 * "D:\\DOAD_In_House_Everest\\Everest_Sports\\Everest_I-League_2022\\Colours\\";
	 *  private String slashOrDash = "-";
	 */
	
	public ATP_2022() {
		super();
	}
	
	public ScoreBug updateScoreBug(List<Scene> scenes, Match match, PrintWriter print_writer) throws InterruptedException, MalformedURLException, IOException
	{
		if(scorebug.isScorebug_on_screen() == true) {
			scorebug = populateScoreBug(true,scorebug, print_writer, scenes.get(0).getScene_path(), match, session_selected_broadcaster);
			//scorebug = populateExtraTime(true,scorebug,print_writer,null,match,session_selected_broadcaster);
		}
		return scorebug;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, Match match, TennisService tennisService,PrintWriter print_writer, 
			List<Scene> scenes, String valueToProcess) throws InterruptedException, NumberFormatException, MalformedURLException, IOException, JAXBException{
		switch (whatToProcess.toUpperCase()) {
		case "POPULATE-SCOREBUG":
			switch(whatToProcess.toUpperCase()) {
			/*
			 * case "POPULATE-SCOREBUG_STATS": case "POPULATE-RED_CARD": case
			 * "POPULATE-EXTRA_TIME": case "POPULATE-SPONSOR": case
			 * "POPULATE-SCOREBUG_STATS_TWO": case "POPULATE-SCOREBUG-CARD": case
			 * "POPULATE-SCOREBUG-SUBS": break;
			 */
			case "POPULATE-SCOREBUG":
				//System.out.println("I_League :"+ valueToProcess.split(",")[0]);
				scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
				scenes.get(0).scene_load(print_writer, session_selected_broadcaster);
				break;
			default:
				scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
				scenes.get(1).scene_load(print_writer,session_selected_broadcaster);
				break;
			}
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-SCOREBUG":
				populateScoreBug(false,scorebug,print_writer, valueToProcess.split(",")[0], match, session_selected_broadcaster);
				break;
			}
			
		
			
		case "ANIMATE-IN-SCOREBUG": case "CLEAR-ALL": case "ANIMATE-OUT-SCOREBUG":		
			switch (whatToProcess.toUpperCase()) {
			
			case "ANIMATE-IN-SCOREBUG":
				AnimateInGraphics(print_writer, "SCOREBUG");
				which_graphics_onscreen = "SCOREBUG";
				is_infobar = true;
				scorebug.setScorebug_on_screen(true);
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
	               
					break;
			case "ANIMATE-OUT-SCOREBUG":
				if(is_infobar == true) {
					AnimateOutGraphics(print_writer, "SCOREBUG");
					is_infobar = false;
					scorebug.setScorebug_on_screen(false);
				}
				break;
			}
			break;
			}
		return null;
	}
	
	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException
	{
		
		switch(whichGraphic) {
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		}	
	}	
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic)
	{
		switch(whichGraphic.toUpperCase()) {
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		}	
	}
	
	public ScoreBug populateScoreBug(boolean is_this_updating,ScoreBug scorebug, PrintWriter print_writer,String viz_sence_path, Match match, String selectedbroadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			List<String> home_data = new ArrayList<String>();
			List<String> away_data = new ArrayList<String>();
			
			for(int i= 0; i <= match.getSets().size() - 1; i++) {
				int home = 0, away = 0;
				for(int j= 0; j <= match.getSets().get(i).getGames().size() - 1; j++) {
					if(match.getSets().get(i).getGames().get(j).getGame_winner() != null) {
						if(match.getSets().get(i).getGames().get(j).getGame_winner().toUpperCase().equalsIgnoreCase("HOME")) {
							home = home + 1 ;
						}else if(match.getSets().get(i).getGames().get(j).getGame_winner().toUpperCase().equalsIgnoreCase("AWAY")) {
							away = away + 1 ;
						}
					}
				}
				home_data.add(i,String.valueOf(home));
				away_data.add(i,String.valueOf(away));
			}
			
			if(match.getSets() != null) {
				for(int i= 0; i <= match.getSets().size() - 1; i++) {
					if(match.getSets().get(i).getSet_status().toUpperCase().equalsIgnoreCase("START")) {
						
						if(i == 0) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScoreColour1" + " SET " + "247 244 246" + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScoreColour1" + " SET " + "247 244 246" + "\0");
							
							if(home_data.get(0).equalsIgnoreCase("0") && away_data.get(0).equalsIgnoreCase("0")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$SetOmo*FUNCTION*Omo*vis_con SET 0 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$SetOmo*FUNCTION*Omo*vis_con SET 0 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + "0" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + "0" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$SetOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$SetOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
							}
						}else if(i == 1) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScoreColour2" + " SET " + "247 244 246" + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScoreColour2" + " SET " + "247 244 246" + "\0");
							if(home_data.get(1).equalsIgnoreCase("0") && away_data.get(1).equalsIgnoreCase("0")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$SetOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$SetOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + "0" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + "0" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$SetOmo*FUNCTION*Omo*vis_con SET 2 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$SetOmo*FUNCTION*Omo*vis_con SET 2 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1) + "\0");
							}
						}
						for(int j= 0; j <= match.getSets().get(i).getGames().size() - 1; j++) {
							if(match.getSets().get(i).getGames().get(j).getGame_status().toUpperCase().equalsIgnoreCase("START")) {
								if(match.getSets().get(i).getGames().get(j).getServing_player().toUpperCase().equalsIgnoreCase("HOME")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$ServiceDot*FUNCTION*Omo*vis_con SET 1 \0");
								}else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$ServiceDot*FUNCTION*Omo*vis_con SET 2 \0");
								}
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$GameScore*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$GameScore*ACTIVE SET 1 \0");
								if(match.getSets().get(i).getGames().get(j).getHome_score().toUpperCase().equalsIgnoreCase("ADVANTAGE")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopGameScore" + " SET " + "AD" + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomGameScore" + " SET " + match.getSets().get(i).getGames().get(j).getAway_score() + "\0");
								}else if(match.getSets().get(i).getGames().get(j).getAway_score().toUpperCase().equalsIgnoreCase("ADVANTAGE")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopGameScore" + " SET " + match.getSets().get(i).getGames().get(j).getHome_score() + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomGameScore" + " SET " + "AD" + "\0");
								}else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopGameScore" + " SET " + match.getSets().get(i).getGames().get(j).getHome_score() + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomGameScore" + " SET " + match.getSets().get(i).getGames().get(j).getAway_score() + "\0");
								}
								
								if(match.getSets().get(i).getGames().get(j).getHome_score().equalsIgnoreCase("0") && match.getSets().get(i).getGames().get(j).getAway_score().equalsIgnoreCase("0")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$GameScore*ACTIVE SET 0 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$GameScore*ACTIVE SET 0 \0");
								}else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$GameScore*ACTIVE SET 1 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$GameScore*ACTIVE SET 1 \0");
								}
								
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$GameScore*ACTIVE SET 0 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$GameScore*ACTIVE SET 0 \0");
							}
						}
					}else {
						if(i == 0) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$SetOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$SetOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$GameScore*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$GameScore*ACTIVE SET 0 \0");
							if(match.getSets().get(0).getSet_winner().toUpperCase().equalsIgnoreCase("HOME")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScoreColour1" + " SET " + "208 221 83" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScoreColour1" + " SET " + "247 244 246" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScoreColour1" + " SET " + "247 244 246" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScoreColour1" + " SET " + "208 221 83" + "\0");
							}
						}else if(i == 1) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$SetOmo*FUNCTION*Omo*vis_con SET 2 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$SetOmo*FUNCTION*Omo*vis_con SET 2 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore1" + " SET " + home_data.get(0) + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore1" + " SET " + away_data.get(0) + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScore2" + " SET " + home_data.get(1) + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScore2" + " SET " + away_data.get(1) + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$TopGrp$ScoreGrp$TopScore$GameScore*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BottomGrp$ScoreGrp$BottomScore$GameScore*ACTIVE SET 0 \0");
							if(match.getSets().get(0).getSet_winner().toUpperCase().equalsIgnoreCase("HOME")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScoreColour1" + " SET " + "208 221 83" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScoreColour1" + " SET " + "247 244 246" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScoreColour1" + " SET " + "247 244 246" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScoreColour1" + " SET " + "208 221 83" + "\0");
							}
							if(match.getSets().get(1).getSet_winner().toUpperCase().equalsIgnoreCase("HOME")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScoreColour2" + " SET " + "208 221 83" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScoreColour2" + " SET " + "247 244 246" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTopSetScoreColour2" + " SET " + "247 244 246" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBottomSetScoreColour2" + " SET " + "208 221 83" + "\0");
							}
						}
					}
				}
			}
			if(is_this_updating == false) {
				if(match.getMatchType().toUpperCase().equalsIgnoreCase("SINGLES")) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName1" + " SET " + match.getHomeFirstPlayer().getTicker_name().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName2" + " SET " + match.getAwayFirstPlayer().getTicker_name().toUpperCase() + "\0");
				}else if(match.getMatchType().toUpperCase().equalsIgnoreCase("DOUBLES")) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName1" + " SET " + match.getHomeFirstPlayer().getTicker_name().toUpperCase() 
										+ " / " + match.getHomeSecondPlayer().getTicker_name().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName2" + " SET " + match.getAwayFirstPlayer().getTicker_name().toUpperCase() 
										+ " / " + match.getAwaySecondPlayer().getTicker_name().toUpperCase() + "\0");
				}
								
			}
		}
		return scorebug;
	}
}
