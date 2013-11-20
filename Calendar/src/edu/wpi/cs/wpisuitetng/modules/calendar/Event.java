/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Underscore 
 *    
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar;


import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;

/**
 * @author Inferno505
 * @version $Revision: 1.0 $
 */

public class Event {
	private int id;
	private String eventName;
	private String eventDescr;

	private DateInfo startDate;
	private DateInfo endDate;
	//TODO private List<Category> categories

	
	// Potential fields for later
	// String category;
	// Repeat repeat;
	// List<String> participants;
	// boolean committed;

	// secondary constructor, for samson's tests
	// not using start and end HalfHour
	/**
	 * Constructor for Event.
	 * 
	 * @param id
	 *            int
	 * @param eventName
	 *            String
	 * @param eventDescr
	 *            String
	 * @param startDate
	 *            Date
	 * @param endDate
	 *            Date
	 */
	@Deprecated
	public Event(int id, String eventName, String eventDescr, Date startDate,
			Date endDate) {
		this.id = id;
		this.eventName = eventName;
		this.eventDescr = eventDescr;
		this.startDate = new DateInfo( startDate );
		this.endDate = new DateInfo( endDate );
	}

	/**
	 * Construct an event object
	 * @param id The ID of the event
	 * @param eventName The event's name
	 * @param eventDescr The description of the event
	 * @param startDate Start date and time of event
	 * @param endDate End date and time of event
	 */
	public Event( int id, String eventName, String eventDescr,
			      DateInfo startDate, DateInfo endDate ) {
		this.id = id;
		this.eventName = eventName;
		this.eventDescr = eventDescr;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	// ----------------------------------------------------------------------------
	// Getters for the fields of Event
	/**
	 * 
	 * @return the ID of the event
	 */
	public int getEventId() {
		return id;
	}

	/**
	 * 
	 * @return the name of the event
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * 
	 * @return The description of the event
	 */
	public String getEventDescription() {
		return eventDescr;
	}


	// ---------------------------------------------
	// Setters for the fields of Event

	/**
	 * 
	 * @param eventID the event's new ID
	 */
	public void setId(int eventID) {
		id = eventID;
	}

	/**
	 * 
	 * @param eventName The new name of the event
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * 
	 * @param eventDescr The new event description
	 */
	public void setEventDescription(String eventDescr) {
		this.eventDescr = eventDescr;
	}

	/**
	 * 
	 * @return the year that the event starts
	 */
	public int getStartYear() {
		return startDate.getYear();
	}

	/**
	 * 
	 * @return the year that the event ends
	 */
	public int getEndYear() {
		return endDate.getYear();
	}

	/**
	 * 
	 * @return The starting date and time of event as a DateInfo object
	 */
	public DateInfo getStartDate() {
		return startDate;
	}
	
	/**
	 * 
	 * @param startDate The new starting date for the event
	 */
	public void setStartDate(DateInfo startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * 
	 * @return The ending date and time of event as a DateInfo object
	 */
	public DateInfo getEndDate() {
		return endDate;
	}

	/**
	 * 
	 * @param endDate the new end date of the event
	 */
	public void setEndDate(DateInfo endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 
	 * @return the starting half hour of the event from 0-47
	 */
	public int getStartHalfHour() {
		return startDate.getHalfHour();
	}
	
	/**
	 * 
	 * @return the ending half hour of the event from 0-47
	 */
	public int getEndHalfHour() {
		return endDate.getHalfHour();
	}

}
