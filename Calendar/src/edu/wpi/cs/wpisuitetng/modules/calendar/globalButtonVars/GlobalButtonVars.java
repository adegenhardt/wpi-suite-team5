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

// Global variables to check whether the calendar should display
// Team or Personal events (or both)

/**
 * 
 * @author Team_
 * @version 1.0
 *
 */
public class GlobalButtonVars  {
	
	public static boolean isTeamView = false;
	public static boolean isPersonalView = true;
	public static boolean triedOnce = false;
	private static GlobalButtonVars instance = null;
	
	private GlobalButtonVars(){
		
	}
	
	public static GlobalButtonVars getInstance(){
		if(instance == null){
			instance = new GlobalButtonVars();
		}
		return instance;
	}	
	
	public boolean isStateTeamView(){
		return isTeamView() && !isPersonalView();
		
	}
	
	public boolean isStatePersonalView(){
		return !isTeamView() && isPersonalView();
		
	}
	
	public boolean isStateBothView(){
		return isTeamView() && isPersonalView();
	}

	/**
	 * @return the isPersonalView
	 */
	public boolean isPersonalView() {
		return isPersonalView;
	}

	/**
	 * @param isPersonalView the isPersonalView to set
	 */
	public void setPersonalView() {
		isPersonalView = true;
		isTeamView = false;
		CalendarSidebar.getInstance().populateTable();
	}

	/**
	 * @return the isTeamView
	 */
	public boolean isTeamView() {
		return isTeamView;
	}

	/**
	 * @param isTeamView the isTeamView to set
	 */
	public void setTeamView() {
		isPersonalView = false;
		isTeamView = true;
		CalendarSidebar.getInstance().populateTable();
	}
	
	public void setBothView() {
		isPersonalView = true;
		isTeamView = true;
		CalendarSidebar.getInstance().populateTable();
	}

	/**
	 * @param triedOnce the triedOnce to set
	 */
	public void setTriedOnce(boolean once) {
		triedOnce = once;
	}

	public boolean isTriedOnce() {
		return triedOnce;
	}
	
	//TODO add function that asks for state and returns an enumeration of the state
}
