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

import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer handles responses to requests for all Event instance
 *
 * @version $Revision: 1.0 $
 * @author srkodzis
 */
public class GetEventRequestObserver implements RequestObserver {
	
	private final GetEventController controller;
	
	/**
	 * Constructs the observer given a GetEventController
	 * @param controller the controller used to retrieve Events
	 */
	public GetEventRequestObserver(GetEventController controller) {
		this.controller = controller;
	}

	/**
	 * Parse the Events out of the response body and pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Convert the JSON array of Events to a Event object array
		final Event[] calendarData = Event.fromJsonArray(iReq.getResponse().getBody());
		
		// Pass these Events to the controller
		controller.receivedEvent( calendarData );
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}

	/**
	 * Put an error Event in the PostBoardPanel if the request fails.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		//TODO: generate an error form of Event
		final Event[] errorEvent = { new Event() };
		controller.receivedEvent(errorEvent);
	}

}
