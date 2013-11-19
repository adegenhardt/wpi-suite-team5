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
import java.util.List;
import java.util.Date;

public class Event {
	private int id;
	private String eventName;
	private String eventDescr;
	private Date date;
	//List<Category> category;
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
	 */
	public Event(int id, String eventName, String eventDescr, Date date){
			//List<Category> category){
		this.id = id;
		this.eventName = eventName;
		this.eventDescr = eventDescr;
		this.date = date;
		//this.category = category;
		// this.repeat = repeat;
		// this.committed = committed;
		// this.participants = participants;
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

	public void setDate(Date date) {
		this.date = date;
	}

}
