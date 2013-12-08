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
// Global variables to check whether the calendar should display
// Team or Personal events (or both)

/**
 * 
 * @author Team_
 * @version 1.0
 *
 */
public class GlobalButtonVars {
	
	public static boolean isTeamView = false;
	public static boolean isPersonalView = true;
	
	
	
	public static boolean isStateTeamView(){
		return isTeamView && !isPersonalView;
		
	}
	
	public static boolean isStatePersonalView(){
		return !isTeamView && isPersonalView;
		
	}
	
	public static boolean isStateBothView(){
		return isTeamView && isPersonalView;
	}
	
	//TODO add function that asks for state and returns an enumeration of the state
}
