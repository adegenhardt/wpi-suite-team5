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
 * This controller responds when the user clicks the Update button by
 * adding the contents of the CalendarData text fields to the model as a new
 * CalendarData.
 * @version $Revision: 1.0 $
 * @author srkodzis
 */
public class UpdateCalendarDataController{
	
	private static UpdateCalendarDataController instance;
	private UpdateCalendarDataRequestObserver observer;
	
	/**
	 * Construct an UpdateCalendarDataController for the given model, view pair
	
	
	 */
	private UpdateCalendarDataController() {
		observer = new UpdateCalendarDataRequestObserver( this );
	}
	
	/**
	
	 * @return the instance of the UpdateCalendarDataController or creates one if it does not
	 * exist. */
	public static UpdateCalendarDataController getInstance()
	{
		if( instance == null )
		{
			instance = new UpdateCalendarDataController();
		}
		
		return instance;
	}

	/**
	 * This method updates a CalendarData to the server.
	 * @param newCalendarData is the CalendarData to be updated to the server.
	 */
	public void updateCalendarData(CalendarData newCalendarData) 
	{
		Request request = Network.getInstance().makeRequest( "calendar/calendardata",
				              HttpMethod.POST); // POST == update
		request.setBody( newCalendarData.toJSON() ); // put the new CalendarData in the body of the request
		request.addObserver( observer ); // add an observer to process the response
		request.send(); 
	}
}
