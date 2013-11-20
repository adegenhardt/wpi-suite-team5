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

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;


// I'm not sure if we need this, but
// import edu.wpi.cs.wpisuitetng.modules.postboard.model.PostBoardMessage; 
// I'll just comment it out to be safe.
// import edu.wpi.cs.wpisuitetng.database.data;
import java.util.List;
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

	private Date startDate;
	private Date endDate;
	private int startHalfHour;
	private int endHalfHour;
	private Date date;

	/*
	 * private Date startTime; private Date endTime;
	 */

	// String category;
	// Repeat repeat;
	// List<String> participants;
	// boolean committed;

	/**
	 * Constructs an event with the following fields. An event has a name,
	 * description, date on which it occurs, a series of categories under which
	 * the event falls, and an ID by which the program recognizes a
	 * particular event.
	 *  
	 * @param id A unique ID for this event.
	 * @param eventName The name of this event.
	 * @param eventDescr A description of this event.
	 * @param date The date of the event.
	 * @param category A series of categories that filters can use to recognize
	 * 				   this event.
	 *
	 * @param startHalfHour int
	 * @param endHalfHour int
	 */
	public Event(int id, String eventName, String eventDescr, Date startDate,
			Date endDate, /*
						 * boolean committed, List<String> participants, String
						 * category, Repeat repeat,
						 */
			int startHalfHour, int endHalfHour, Date date) {
		this.id = id;
		this.eventName = eventName;
		this.eventDescr = eventDescr;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startHalfHour = startHalfHour;
		this.endHalfHour = endHalfHour;
		/*
		 * this.startTime = startTime; this.endTime = endTime;
		 */
		// this.category = category;

	
	}
	
//secondary constructor, for samson's tests
	//not using start and end HalfHour
	/**
	 * Constructor for Event.
	 * @param id int
	 * @param eventName String
	 * @param eventDescr String
	 * @param startDate Date
	 * @param endDate Date
	 */
	public Event(int id, String eventName, String eventDescr, Date startDate,
			Date endDate) {
		this.id = id;
		this.eventName = eventName;
		this.eventDescr = eventDescr;
		this.startDate = startDate;
		this.endDate = endDate;
		// TODO Auto-generated constructor stub
	}



	


	// ----------------------------------------------------------------------------
	// Getters for the fields of Event
	
	public int getEventID(){
		return id;
	}
	

	public String getEventName() {
		return eventName;
	}
	
	public String getEventDescr() {
		return eventDescr;
	}

	public Date getDate() {
		return date;
	}

	//public List<Category>(){
	//	return categories;
	//}
	
	// -----------------------------------------------------------------------------
	// Setters for the fields of Event
	
	public void setEventID(int eventID){
		this.id = eventID;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setEventDescr(String eventDescr) {
		this.eventDescr = eventDescr;
	}


	/*
	 * public Date getStartTime() { return this.startTime; }
	 * 
	 * public void setStartTime(Date startTime) { this.startTime = startTime; }
	 * 
	 * public Date getEndTime() { return endTime; }
	 * 
	 * public void setEndTime(Date endTime) { this.endTime = endTime; }
	 */

	@SuppressWarnings("deprecation")
	public int getStartYear() {
		return this.startDate.getYear();
	}

	@SuppressWarnings("deprecation")
	public int getEndYear() {
		return this.endDate.getYear();
	}

	public int getStartHalfHour() {
		return this.startHalfHour;
	}

	public int getEndHalfHour() {
		return this.endHalfHour;
	}

	/**
	 * Method convertParametersToDateInfo.
	
	 * @return DateInfo */
	public DateInfo convertParametersToDateInfo() {
		Date eventStoreDate = this.getStartDate();
		int eventStoreYear = eventStoreDate.getYear();
		int eventStoreMonth = eventStoreDate.getMonth();
		int eventStoreDay = eventStoreDate.getDay();
		int eventStoreHalfHour = this.getStartHalfHour();
		DateInfo eventStoreDateInfo = new DateInfo(eventStoreYear,
				eventStoreMonth, eventStoreDay, eventStoreHalfHour);
		return eventStoreDateInfo;
	}

	public Date getStartDate() {
		return this.startDate;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	// It seems that these are all kept in EventCldr
	// But I will keep these here for safekeeping

	// Stores a new event in the database
	// public void storeEvent(){
	// save(this);
	// System.out.println("Stored "+this.eventName);
	// }

	// Uses input from the GUI to update the event
	// Find the event, delete it, then create the updated ver. of it
	// public void updateEvent(){
	// save(this);
	// }

	// Deletes an event from the database
	// public void deleteEvent(){
	// delete(this);
	// }


}
