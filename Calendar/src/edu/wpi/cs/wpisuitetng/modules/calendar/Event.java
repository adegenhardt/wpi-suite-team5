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
	private Date date;

	/*
	 * private Date startTime; private Date endTime;
	 */

	// String category;
	// Repeat repeat;
	// List<String> participants;
	// boolean committed;
	
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

	/**
	 * Method convertParametersToDateInfo.
	
	 * @return DateInfo */
	@Deprecated
	public DateInfo getStartDateAsDateInfo() {
		Date eventStoreDate = this.getStartDate();
		int eventStoreYear = eventStoreDate.getYear();
		int eventStoreMonth = eventStoreDate.getMonth();
		int eventStoreDay = eventStoreDate.getDay();
		int eventStoreHalfHour = ( eventStoreDate.getHours() * 2 ) +
				                 ( eventStoreDate.getMinutes() / 30 );
		DateInfo eventStoreDateInfo = new DateInfo(eventStoreYear,
				eventStoreMonth, eventStoreDay, eventStoreHalfHour );
		return eventStoreDateInfo;
	}

	public Date getStartDate() {
		return this.startDate;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * 
	 * @return the calculated starting half hour from 0-47
	 */
	public int getStartHalfHour() {
		return 	( startDate.getHours() * 2 ) +
                ( startDate.getMinutes() / 30 );
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
