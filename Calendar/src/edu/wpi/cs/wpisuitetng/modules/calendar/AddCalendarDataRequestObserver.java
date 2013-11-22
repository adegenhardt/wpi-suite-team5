/*********************************************************************************************
 * Copyright (c) 2013 WPI Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * This observer is called when a response is received from a request to
 * the server to add a piece of calendar data.
 * Adapted from AddRequirementRequestObserver.java by Team Underscore Database Crew
 *
 *********************************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * 
 * @author Team_
 * @version 1.0
 *
 */
public class AddCalendarDataRequestObserver implements RequestObserver {

	private final AddCalendarDataController controller;

	/**
	 * Adds an observer for a CalendarData request
	 * @param controller Controller to add observer to
	 *
	 */
	public AddCalendarDataRequestObserver(AddCalendarDataController controller){
		this.controller = controller;
	}
	
	/**
	 * Parse the calendar data that was received from the server then pass it
	 * to the controller.
	 */
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		final CalendarData datum = CalendarData.fromJson(response.getBody());
	}
	
	/**
	 * Takes action if the response results in error 
	 * and outputs that the request failed.
	 * @param iReq The request
	 */
	public void responseError(IRequest iReq) {
		System.err.println("The request to add calendar data failed.");
	}

	/**
	 * Takes action if the request fails, and outputs that it failed.
	 * @param iReq The given request
	 * @param exception The Exception
	 */
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add calendar data failed.");
	}

}
