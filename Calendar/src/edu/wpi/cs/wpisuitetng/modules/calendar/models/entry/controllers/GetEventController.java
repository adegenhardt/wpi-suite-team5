/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the Events from the server.
 * 
 * @version $Revision: 1.0 $
 * @author Team Underscore
 */
public class GetEventController implements ActionListener {

	private final GetEventRequestObserver observer;
	private static GetEventController instance = null;

	/**
	 * Constructs the controller given a EventModel
	 */
	private GetEventController() {

		observer = new GetEventRequestObserver(this);
	}

	/**
	 * 
	 * @return the instance of the GetEventController or creates one if it does
	 *         not exist.
	 */
	public static GetEventController getInstance() {
		if (instance == null) {
			instance = new GetEventController();
		}

		return instance;
	}

	/**
	 * Sends an HTTP request to store a Event when the update button is pressed
	 * 
	 * @param e
	 *            ActionEvent
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to save this Event
		final Request request = Network.getInstance().makeRequest(
				"calendar/event", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the
		// response
		request.send(); // send the request
	}

	/**
	 * Sends an HTTP request to retrieve all Event instances
	 */
	public void retrieveEvents() {
		final Request request = Network.getInstance().makeRequest(
				"calendar/event", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the
		// response
		request.send(); // send the request
	}

	/**
	 * Add the given Events to the local model (they were received from the
	 * core). This method is called by the GetEventRequestObserver
	 * 
	 * @param events
	 *            array of Event instances received from the server
	 */
	public void receivedEvent(Event[] events) {
		// Make sure the response was not null 
		if (events != null) {
			// add the Event instances to the local model
			EventModel.getInstance().addAllEvents(events);
		}
	}
}
