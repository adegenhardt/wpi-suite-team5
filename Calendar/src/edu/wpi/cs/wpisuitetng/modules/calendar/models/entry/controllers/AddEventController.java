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
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user creates a new project,
 * creating an empty Calendar with it.
 * @version $Revision: 1.0 $
 * @author srkodzis
 */
public class AddEventController  {
	
	private static AddEventController instance = null;
	private final AddEventRequestObserver observer;
	
	/**
	 * Construct an AddEventController for the given model, view pair
	 */
	private AddEventController() {
		observer = new AddEventRequestObserver( this );
	}
	
	/**
	 * @return the instance of the AddEventController or
	 * creates one if it does notexist.
	 */
	public static AddEventController getInstance()
	{
		if( instance == null )
		{
			instance = new AddEventController();
		}
		
		return instance;
	}

	/**
	 * This method adds a Event to the server.
	 * @param newEvent is the Event to be added to the server.
	 */
	public void addEvent( Event newEvent ) 
	{
		// PUT == create
		final Request request = Network.getInstance().makeRequest( "calendar/event", HttpMethod.PUT );
		request.setBody( newEvent.toJSON() ); // put the new Event in the body of request		
		request.addObserver( observer ); // add an observer to process the response
		request.send(); 
	}
	
	/**
	 * When the new event is received back from the server, add it to the local model.
	 * @param event the event successfully added on the server
	 */
	public void addMessageToModel( Event event ) {
		EventModel.getInstance().addEvent( event );
	}
}
