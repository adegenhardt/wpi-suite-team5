/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 * 
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarSidebar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars.DayView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars.WeekView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars.YearViewCalendar;


/**
 * This class is used primarily to monitor the current view state of the GUI
 * and update the view within GUI components when the view state is changed
 * to different classifications of view.
 * 
 * @author Team Underscore
 * @version 1.0
 *
 */
public class GlobalButtonVars  {
	
	public static boolean isTeamView = false;
	public static boolean isPersonalView = true;
	public static boolean triedOnce = false;
	private static GlobalButtonVars instance = null;
	
	/**
	 * A private constructor for the GlobalButtonVars class that is used
	 * primarily for constructing the singleton instance of the GlobalButtonVars class.
	 */
	private GlobalButtonVars(){
	}
	
	/**
	 * @return the instance of the GlobalButtonVars singleton
	 */
	public static GlobalButtonVars getInstance(){
		if(instance == null){
			instance = new GlobalButtonVars();
		}
		return instance;
	}	
	
	/**
	 * Method that indicates whether or not the current view state
	 * is classified as a "team" view.
	 * 
	 * @return whether or not the current view state is team.
	 */
	public boolean isStateTeamView(){
		return isTeamView() && !isPersonalView();
		
	}
	
	/**
	 * Method that indicates whether or not the current view state
	 * is classified as a "personal" view.
	 * 
	 * @return whether or not the current view state is personal.
	 */
	public boolean isStatePersonalView(){
		return !isTeamView() && isPersonalView();
		
	}
	
	/**
	 * Method that indicates whether or not the current view state
	 * is classified as both personal and team.
	 * 
	 * @return whether or not the current view state is both personal
	 * and team.
	 */
	public boolean isStateBothView(){
		return isTeamView() && isPersonalView();
	}

	/**
	 * @return the value of the isPersonalView boolean for this class.
	 */
	public boolean isPersonalView() {
		return isPersonalView;
	}

	/**
	 * Method that sets the current view state to personal and populates
	 * all of the the current views within the GUI according to what contents
	 * should appear in them when the view state is changed to personal.
	 */
	public void setPersonalView() {
		isPersonalView = true;
		isTeamView = false;
		CalendarSidebar.getInstance().populateTable();
		DayView.getInstance().refreshEvents();
		YearViewCalendar.getInstance(null).refreshYear();
		WeekView.getInstance().refreshEvents();
		// Still missing the month view, waiting to jump into 
		// Sam's branch/or when he finishes to add it
	}

	/**
	 * @return the value of the isTeamView boolean for this class.
	 */
	public boolean isTeamView() {
		return isTeamView;
	}

	/**
	 * Method that sets the current view state to team and populates
	 * all of the the current views within the GUI according to what contents
	 * should appear in them when the view state is changed to team.
	 */
	public void setTeamView() {
		isPersonalView = false;
		isTeamView = true;
		CalendarSidebar.getInstance().populateTable();
		DayView.getInstance().refreshEvents();
		YearViewCalendar.getInstance(null).refreshYear();
		WeekView.getInstance().refreshEvents();
		// Still missing the month view, waiting to jump into 
		// Sam's branch/or when he finishes to add it
	}
	
	/**
	 * Method that sets the current view state to both team and personal and populates
	 * all of the the current views within the GUI according to what contents
	 * should appear in them when the view state is changed to being both team and personal.
	 */
	public void setBothView() {
		isPersonalView = true;
		isTeamView = true;
		CalendarSidebar.getInstance().populateTable();
		DayView.getInstance().refreshEvents();
		YearViewCalendar.getInstance(null).refreshYear();
		WeekView.getInstance().refreshEvents();
		// Still missing the month view, waiting to jump into 
		// Sam's branch/or when he finishes to add it
	}

	/**
	 * @param once sets the value of the boolean triedOnce which monitors whether or not
	 * the initial population of the local models from the server has occurred.
	 */
	public void setTriedOnce(boolean once) {
		triedOnce = once;
	}

	/**
	 * Method that determines whether or not the first server draw has been triggered.
	 * 
	 * @return whether or not the first draw from the server has been triggered.
	 */
	public boolean isTriedOnce() {
		return triedOnce;
	}
	
}
