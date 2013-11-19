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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the CalendarDatas
 * from the server.
 *
 * @version $Revision: 1.0 $
 * @author srkodzis
 */
public class GetCalendarDataController implements ActionListener {

	private GetCalendarDataRequestObserver observer;
	private static GetCalendarDataController instance;

	/**
	 * Constructs the controller given a CalendarDataModel
	 */
	private GetCalendarDataController() {
		
		observer = new GetCalendarDataRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the GetCalendarDataController or creates one if it does not
	 * exist. */
	public static GetCalendarDataController getInstance()
	{
		if(instance == null)
		{
			instance = new GetCalendarDataController();
		}
		
		return instance;
	}

	/**
	 * Sends an HTTP request to store a CalendarData when the
	 * update button is pressed
	 * @param e ActionEvent
	
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent) */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to save this CalendarData
		final Request request = Network.getInstance().makeRequest("calendar/calendardata", HttpMethod.GET); // GET == read
		request.addObserver( observer ); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Sends an HTTP request to retrieve all CalendarData instances
	 */
	public void retrieveCalendarData() {
		final Request request = Network.getInstance().makeRequest("calendar/calendardata", HttpMethod.GET); // GET == read
		request.addObserver( observer ); // add an observer to process the response
		request.send(); // send the request
	}

	/**
	 * Add the given CalendarDatas to the local model (they were received from the core).
	 * This method is called by the GetCalendarDatasRequestObserver
	 * 
	 * @param CalendarDatas array of CalendarData instances received from the server
	 */
	public void receivedCalendarData(CalendarData[] calendarData) {
		// Make sure the response was not null
		if (calendarData != null) 
		{	
			// add the CalendarData instances to the local model
			CalendarDataModel.getInstance().addCalendarData( calendarData );
		}
	}
}
