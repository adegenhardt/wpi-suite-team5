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
package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;

/**
 * @author Inferno505
 * @version $Revision: 1.0 $
 */

public class Event extends AbstractModel implements ICalendarEntry {

	// ID Parameters
	private int absoluteId; // object unique ID integer
	private String projectId; // name of Event's project
	private String userId; // name of creating User
	private String typeId; // "project"" or "personal" calendar type ID
	private boolean isDeleted; // object is deleted from user view

	// Date Region information
	// can be regulated to only startDate and endDate
	// Date ID fields for Event defining date (start Date)
	private int year; // year event occurs on (absolute base 1900 = 1900)
	private int month; // month event occurs on (0 based (0-11 = Jan - Dec)
	private int day; // day event occurs on (0 Based (0 to DaysInMonth-1)
	private int halfHour; // half hour event occurs on (0 based 0-47 = 12am to
							// 11:30 pm or 0:00 to 23:30)

	// Descriptive Parameters
	private String name; // Name of Event
	private String description; // Description of Event

	// These two could be regulated to sets of start/end year, month, day,
	// halfHour
	private DateInfo startDate; // StartDate of event
	private DateInfo endDate; // End Date of event
	private List<Category> category; // Categories Event belongs to for
										// filtering
	private List<String> participants;// userIds of users to participate in
										// event

	// Potential fields for later
	// Repeat repeat;

	// secondary constructor, for samson's tests
	// not using start and end HalfHour
	/**
	 * Constructor for Event.
	 * 
	 * @param startDate
	 *            Date
	 * @param endDate
	 *            Date
	 * @param participants
	 * @param absoluteId
	 *            int
	 * @param projectId
	 *            String
	 * @param userId
	 *            String
	 * @param typeId
	 *            String
	 * @param name
	 *            String
	 * @param description
	 *            String
	 * @param category
	 *            List<Category>
	
	 */
	// @Deprecated
	/*
	 * public Event(int id, String eventName, String eventDescr, Date startDate,
	 * Date endDate) { this.id = id; this.name = eventName; this.description =
	 * eventDescr;
	 * 
	 * this.startDate = new DateInfo( startDate ); this.endDate = new DateInfo(
	 * endDate ); }
	 */

	/**
	 * Full Specification Constructor for Event. (Test Only)
	 * 
	 * @param projectId
	 *            String name of Project Event is Linked to.
	 * @param userId
	 *            String name of User Event is Linked to (either by creation or
	 *            by personal calendar)
	 * @param typeId
	 *            String "personal" or "project" string to ID which form of
	 *            calendar it is related to
	 * @param name
	 *            String name of Event
	 * @param description
	 *            String description of Event
	 * @param startDate
	 *            DateInfo dateInfo parameter for holding date Event starts
	 * @param endDate
	 *            DateInfo dateInfo parameter for holding date Event ends
	 * @param category
	 *            List<Category> list of categories commitment is part of
	 */
	public Event(int absoluteId, String projectId, String userId,
			String typeId, String name, String description, DateInfo startDate,
			DateInfo endDate, List<Category> category, List<String> participants) {

		this.absoluteId = absoluteId;
		this.projectId = projectId;
		this.userId = userId;
		this.setTypeId(typeId);
		this.isDeleted = false;

		// Date Region information
		this.setYear(startDate.getYear());
		this.setMonth(startDate.getMonth());
		this.setDay(startDate.getDay());
		this.setHalfHour(startDate.getHalfHour());

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setCategory(category);
		this.participants = participants;
	}

	/**
	 * Automatic Example Constructor for Event. (Test Only)
	 * 
	 * @param ex int
	 */
	public Event(int ex) {
if (ex == 1){
		this.absoluteId = 1;
		this.projectId = "project";
		this.userId = "user";
		this.typeId = "project";
		this.isDeleted = false;

		// Descriptive Parameters
		DateInfo startDate = new DateInfo(2000,0,0,0);
		DateInfo endDate = new DateInfo(2000,0,0,1);
		this.name = "event";
		this.description = "an event";
		this.startDate = startDate;
		this.endDate = endDate;
		 List<Category> category = new ArrayList<Category>();
		 category.add(new Category ("cName", "cDescription"));
		this.setCategory(category);
		List<String> participants = new ArrayList<String>();
		participants.add("user");
		this.participants = participants;
		// Date Region information

		this.setYear(startDate.getYear());
		this.setMonth(startDate.getMonth());
		this.setDay(startDate.getDay());
		this.setHalfHour(startDate.getHalfHour());
}
else if(ex == 2){
	this.absoluteId = 2;
	this.projectId = "project2";
	this.userId = "user2";
	this.typeId = "personal";
	this.isDeleted = true;

	// Descriptive Parameters
	DateInfo startDate = new DateInfo(2000,1,1,1);
	DateInfo endDate = new DateInfo(2000,1,1,2);
	this.name = "event2";
	this.description = "an event2";
	this.startDate = startDate;
	this.endDate = endDate;
	 List<Category> category = new ArrayList<Category>();
	 category.add(new Category ("cName2", "cDescription2"));
	this.setCategory(category);
	List<String> participants = new ArrayList<String>();
	participants.add("user2");
	this.participants = participants;
	// Date Region information

	this.setYear(startDate.getYear());
	this.setMonth(startDate.getMonth());
	this.setDay(startDate.getDay());
	this.setHalfHour(startDate.getHalfHour());
	
}
	}

	/**
	 * Basic Constructor for Event with inputs of Type Date. (No participants,
	 * categories)
	 * 
	 * 
	 * @param name
	 *            String name of Event
	 * @param description
	 *            String description of Event
	 * @param startDate
	 *            Date date parameter for holding date Event starts
	 * @param endDate
	 *            Date date parameter for holding date Event ends
	
	 * 
	 * @param typeId
	 *            String "personal" or "project" to identify calendar type
	 *            object belongs to
	
	
	 * @param absoluteId int
	 */
	public Event(int absoluteId, String name, String description,
			Date startDate, Date endDate, String typeId) {

		this.absoluteId = absoluteId;// generate new id;
		// this.projectId = TODO getProjectName();
		// this.userId = TODO getUserName();
		this.typeId = typeId;
		this.isDeleted = false;

		// Date Region information
		this.setYear(startDate.getYear());
		this.setMonth(startDate.getMonth());
		this.setDay(startDate.getDay());
		this.halfHour = 0;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = new DateInfo(startDate.getYear(),
				startDate.getMonth(), startDate.getDay(), 0);
		this.endDate = new DateInfo(endDate.getYear(), endDate.getMonth(),
				endDate.getDay(), 0);
		this.setCategory(category);
		this.participants = participants;
	}

	/**
	 * System Implemented Constructor for Event. Gets and sets the project and
	 * user Id fields based on system's current project and user. absoluteId is
	 * generated as unique int at creation To be used in User Event creation
	 * 
	
	
	 * @param typeId
	 *            String "personal" or "project" string to ID which form of
	 *            calendar it is related to
	 * @param name
	 *            String name of Event
	 * @param description
	 *            String description of Event
	 * @param startDate
	 *            DateInfo dateInfo parameter for holding date Event starts
	 * @param endDate
	 *            DateInfo dateInfo parameter for holding date Event ends
	 * @param participants
	 * @param category
	 *            List<Category> list of categories commitment is part of
	 */
	public Event(String name, String description, DateInfo startDate,
			String typeId, DateInfo endDate, List<Category> category,
			List<String> participants) {

		// this.absoluteId = getNextAbsoluteId();// generate new id;
		// this.projectId = TODO getProjectName();
		// this.userId = TODO getUserName();
		this.setTypeId(typeId);
		this.isDeleted = false;

		// Date Region information
		this.setYear(startDate.getYear());
		this.setMonth(startDate.getMonth());
		this.setDay(startDate.getDay());
		this.setHalfHour(startDate.getHalfHour());

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;
		this.participants = participants;
	}

	// ---------------------------------------------------------
	// Get/Set for the fields of Event

	/**
	 * 
	 * @return The description of the event
	 */
	public String getDescription() {
		return description;
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
	 * @return The starting date and time of event as a DateInfo object
	 */
	public DateInfo getStartDate() {
		return this.startDate;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * Method getisDeleted.
	 * 
	 * @return boolean
	 */
	public boolean getIsDeleted() {
		return isDeleted;
	}

	public int getAbsoluteId() {
		return absoluteId;
	}

	/**
	 * 
	 * @param absoluteId
	 *            the event's new ID
	 */
	public void setAbsoluteId(int absoluteId) {
		this.absoluteId = absoluteId;
	}

	/**
	 * 
	 * @param idProject
	 */
	public void setProjectId(String idProject) {
		this.projectId = idProject;
	}

	/**
	 * 
	 * @param idUser
	 */
	public void setUserIdr(String idUser) {
		this.userId = idUser;
	}

	/**
	 * 
	 * @param isDeleted
	 */
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * 
	 * @param eventName
	 *            The new name of the event
	 */
	public void setName(String Name) {
		this.name = Name;
	}

	/**
	 * 
	 * @param eventDescr
	 *            The new event description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @param startDate
	 *            The new starting date for the event
	 */
	public void setStartDate(DateInfo startDate) {
		this.startDate = startDate;
		this.year = endDate.getYear();
		this.month = endDate.getMonth();
		this.day = endDate.getDay();
		this.halfHour = endDate.getHalfHour();

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
	 * @param endDate
	 *            the new end date of the event
	 */
	public void setEndDate(DateInfo endDate) {
		this.endDate = endDate;

	}

	/**
	 * 
	 * @return
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * 
	 * @param typeId
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * 
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
		this.startDate = new DateInfo(year, this.month, this.day, this.halfHour);
	}

	/**
	 * 
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * 
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month;
		this.startDate = new DateInfo(this.year, month, this.day, this.halfHour);
	}

	/**
	 * 
	 * @return
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 
	 * @param day
	 */
	public void setDay(int day) {
		this.day = day;
		this.startDate = new DateInfo(this.year, this.month, day, this.halfHour);
	}

	/**
	 * 
	 * @return
	 */
	public List<Category> getCategory() {
		return category;
	}

	/**
	 * 
	 * @param category
	 */
	public void setCategory(List<Category> category) {
		this.category = category;
	}

	/**
	 * 
	 * @return
	 */
	public int getHalfHour() {
		return halfHour;
	}

	/**
	 * 
	 * @param halfHour
	 */
	public void setHalfHour(int halfHour) {
		this.halfHour = halfHour;
		this.startDate = new DateInfo(this.year, this.month, this.day, halfHour);
	}

	// Required Functions Database Interaction
	// -----------------------------------------
	/**
	 * Method save.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	public void save() {

	}

	/**
	 * Method delete.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	public void delete() {

	}

	/**
	 * Method toJSON.
	 * 
	 * 
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	/**This returns a Json encoded String representation of this requirement object.
	 * 
	 * @return a Json encoded String representation of this requirement
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, Event.class);
	}

	/**
	 * Method toString.
	 * 
	 * 
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	/**
	 * This returns a Json encoded String representation of this requirement
	 * object.
	 * 
	 * @return a Json encoded String representation of this Event
	 * 
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	public List<String> getParticipants() {
		return participants;
	}

	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

	public String getName() {
		return name;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Method identify.
	 * 
	 * @param o
	 *            Object
	 * 
	 * 
	 * @return Boolean * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns an instance of Event constructed using the given Event encoded as
	 * a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Event to deserialize
	 * 
	 * 
	 * 
	
	 * @return the Event contained in the given JSON */
	public static Event fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Event.class);
	}

	/**
	 * Returns an array of Event parsed from the given JSON-encoded string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Event
	 * 
	 * 
	 * 
	
	 * @return an array of Event deserialized from the given JSON string */
	public static Event[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Event[].class);
	}

	/**
	 * Copies all of the values from the given Event to this Event.
	 * 
	 * @param toCopyFrom
	 *            the Event to copy from.
	 */

	public void copyFrom(Event toCopyFrom) {
		this.absoluteId = toCopyFrom.absoluteId;
		this.projectId = toCopyFrom.projectId;
		this.userId = toCopyFrom.userId;
		this.typeId = toCopyFrom.typeId;
		this.isDeleted = toCopyFrom.isDeleted;

		// Date Region information
		this.setYear(toCopyFrom.getYear());
		this.setMonth(toCopyFrom.getMonth());
		this.setDay(toCopyFrom.getDay());
		this.setHalfHour(toCopyFrom.getHalfHour());

		// Descriptive Parameters
		this.name = toCopyFrom.name;
		this.description = toCopyFrom.description;
		this.startDate = toCopyFrom.startDate;
		this.endDate = toCopyFrom.endDate;
		this.category =(toCopyFrom.category);
		this.participants = toCopyFrom.participants;
	}
//TODO Can we use this for AbsoluteId?
	//@Override
	/*public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + day;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + halfHour;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result + month;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((participants == null) ? 0 : participants.hashCode());
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + year;
		return result;
	}*/

	@Override
	public boolean equals(Object obj) {
		return
				
		this.absoluteId == (((Event)obj).absoluteId) &&
		this.projectId.equals(((Event)obj).projectId) &&
		this.userId.equals(((Event)obj).userId) &&
		this.typeId.equals(((Event)obj).typeId) &&
		this.isDeleted  == ((Event)obj).isDeleted &&

		
		this.year==(startDate.getYear())&&
		this.month==(startDate.getMonth())&&
		this.day==(startDate.getDay())&&
		this.halfHour==(startDate.getHalfHour())&&

		this.name.equals(((Event)obj).name)&&
		this.description.equals(((Event)obj).description)&&
		this.startDate.equals(((Event)obj).startDate)&&
		this.endDate.equals(((Event)obj).endDate)&&
		this.category.equals(((Event)obj).category) &&
		this.participants.equals(((Event)obj).participants);
	}


	
	// End Required Functions Database Interaction
	// -------------------------------------------

}
