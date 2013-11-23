/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user creates a new project,
 * creating an empty Calendar with it.
 * @version $Revision: 1.0 $
 * @author srkodzis
 */
public class AddCalendarDataController  {
	
	private static AddCalendarDataController instance;
	private AddCalendarDataRequestObserver observer;
	
	/**
	 * Construct an AddCalendarDataController for the given model, view pair
	 */
	private AddCalendarDataController() {
		observer = new AddCalendarDataRequestObserver( this );
	}
	
	/**
	 * @return the instance of the AddCalendarDataController or
	 * creates one if it does notexist.
	 */
	public static AddCalendarDataController getInstance()
	{
		if( instance == null )
		{
			instance = new AddCalendarDataController();
		}
		
		return instance;
	}

	/**
	 * This method adds a CalendarData to the server.
	 * @param newCalendarData is the CalendarData to be added to the server.
	 */
	public void addCalendarData( CalendarData newCalendarData ) 
	{
		// PUT == create
		final Request request = Network.getInstance().makeRequest( "calendar/calendardata", HttpMethod.PUT );
		request.setBody( newCalendarData.toJSON() ); // put the new CalendarData in the body of the request		
		request.addObserver( observer ); // add an observer to process the response
		request.send(); 
	}
}
