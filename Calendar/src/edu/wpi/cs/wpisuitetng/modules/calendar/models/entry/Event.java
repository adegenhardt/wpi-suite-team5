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
	private int id; // object unique ID integer
	private String projectId; // id of Event's project
	private String creatorId; // id of creating user
	private boolean isDeleted; // object is deleted from user view

	// Descriptive Parameters
	private String name; // Name of Event
	private String description; // Description of Event

	// These two could be regulated to sets of start/end year, month, day,
	// halfHour
	private DateInfo startDate; // StartDate of event
	private DateInfo endDate; // End Date of event
	private Category category; // Category describing the event
	private ArrayList<String> userIds; // userIds of users to participate in
										// event

	// Potential fields for later
	// Repeat repeat;

	/**
	 * Full Specification Constructor for Event. (Test Only)
	 * 
	 * 
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
	 * @param id
	 * 			  The id of the Event
	 * @param projectId
	 *            String id of Project that the Event is Linked to.
	 * @param creatorId
	 *            String id of User that the Event is Linked to (either by creation or
	 *            by personal calendar)
	 */
	public Event(String name, String description, DateInfo startDate,
			DateInfo endDate, Category category,
			int id, String projectId, String creatorId) {

		this.isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;
		
		this.id = id;
		this.projectId = projectId;
		this.creatorId = creatorId;
		
		// create empty list of userIds and add the creator
		userIds = new ArrayList< String >();
		userIds.add( creatorId );
	}

	/**
	 * System Implemented Constructor for Event. Gets and sets the project and
	 * user Id fields based on system's current project and user. absoluteId is
	 * generated as unique int at creation To be used in User Event creation
	 * 
	
	
	 * @param name
	 *            String name of Event
	 * @param description
	 *            String description of Event
	 * @param startDate
	 *            DateInfo dateInfo parameter for holding date Event starts
	 * @param endDate
	 *            DateInfo dateInfo parameter for holding date Event ends
	 * @param id
	 * 			  The id of the Event
	 * @param projectId
	 *            String id of Project that the Event is Linked to.
	 * @param creatorId
	 *            String id of User that the Event is Linked to (either by creation or
	 *            by personal calendar)
	 */
	public Event(String name, String description, DateInfo startDate,
			DateInfo endDate, int id, String projectId, String creatorId) {

		this.isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		
		this.id = id;
		this.projectId = projectId;
		this.creatorId = creatorId;
		
		category = new Category();
		
		// create empty list of userIds and add the creator
		userIds = new ArrayList< String >();
		userIds.add( creatorId );
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

	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * 
	 * @param absoluteId
	 *            the event's new ID
	 */
	public void setAbsoluteId(int absoluteId) {
		this.id = absoluteId;
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
	public void setCreatorId(String idUser) {
		this.creatorId = idUser;
	}

	/**
	 * 
	 * @param eventName
	 *            The new name of the event
	 */
	public void setName(String name) {
		this.name = name;
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
	public Category getCategory() {
		return category;
	}

	/**
	 * 
	 * @param category
	 */
	public void setCategory(Category category) {
		this.category = category;
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

	public void setUserIds(ArrayList<String> userIds) {
		this.userIds = userIds;
	}
	
	
	public String getName() {
		return name;
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
		this.id = toCopyFrom.id;
		this.projectId = toCopyFrom.projectId;
		this.creatorId = toCopyFrom.creatorId;
		this.isDeleted = toCopyFrom.isDeleted;

		// Descriptive Parameters
		this.name = toCopyFrom.name;
		this.description = toCopyFrom.description;
		this.startDate = toCopyFrom.startDate;
		this.endDate = toCopyFrom.endDate;
		this.category =(toCopyFrom.category);
		this.userIds = toCopyFrom.userIds;
	}

	/**
	 * 
	 * @return the unique ID of the event
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id the new ID for the object
	 */
	public void setId(int id) {
		this.id = id;
	}


	@Override
	public boolean equals(Object obj) {
		return
				
		this.id == (((Event)obj).id) &&
		this.projectId.equals(((Event)obj).projectId) &&
		this.creatorId.equals(((Event)obj).creatorId) &&
		this.isDeleted  == ((Event)obj).isDeleted &&

		this.name.equals(((Event)obj).name)&&
		this.description.equals(((Event)obj).description)&&
		this.startDate.equals(((Event)obj).startDate)&&
		this.endDate.equals(((Event)obj).endDate)&&
		this.category.equals(((Event)obj).category) &&
		this.userIds.equals(((Event)obj).userIds);
	}

	/**
	 * Add a user to the collection of users that are involved in an event
	 * @param newId the userID to be added
	 */
	public void addUserId( String newId ) {
		userIds.add( newId );
	}
	
	/**
	 * Remove a user from the collection of users involved with the event
	 * @param removedId the user ID to be removed
	 */
	public void removeUserId( String removedId ) {
		userIds.remove( removedId );
	}
	
	
	// Interface methods
	/////////////////////////////////////////////
	

	/**
	 * Method isDeleted.
	 * 
	 * @return true if an object is deleted, false otherwise
	 */
	public boolean isDeleted() {
		return isDeleted;
	}
	
	/**
	 * 
	 * @param isDeleted true if the object is now "deleted", false otherwise
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	/**
	 * 
	 * @return the user ids of all people involved in the event
	 */
	public ArrayList<String> getUserIds() {
		return userIds;
	}

	/**
	 * Determines whether or not an event occurs on a given year
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnYear(int year)
	 */
	@Override
	public boolean occursOnYear(int year) {
		if ( startDate.getYear() == year ||
				endDate.getYear() == year ) {
			return true;
			
		// check for event that starts before a given year and ends after it
		// (a very long event)
		} else if ( startDate.getYear() < year &&
					endDate.getYear() > year ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines whether or not an event occurs on a given month
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnMonth(int year, int month)
	 */
	@Override
	public boolean occursOnMonth(int year, int month) {
		
		// TODO: Error handling for invalid months (< 0, > 11 )
		
		if ( startDate.getYear() == year ||
				endDate.getYear() == year ) {
			
			if ( startDate.getMonth() == month ||
					endDate.getMonth() == month ) {
				return true;
				
			// check for an event spanning multiple months
			// from start to finish
			} else if ( startDate.getMonth() < month &&
						endDate.getMonth() > month ) {
				return true;
			} else {
				return false;
			}
			
		// check for event that starts before a given year and ends after it
		// (a very long event)
		} else if ( startDate.getYear() < year &&
					endDate.getYear() > year ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines whether or not an event occurs on a given month
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnDate(int year, int month, int day)
	 */
	@Override
	public boolean occursOnDate(int year, int month, int day) {
		
		// TODO: Error handling for invalid days (< 0, >= number of days in month )
		
		if ( startDate.getYear() == year ||
				endDate.getYear() == year ) {
			
			if ( startDate.getMonth() == month ||
					endDate.getMonth() == month ) {

				if ( startDate.getDay() == day ||
					 endDate.getDay() == day ) {
					return true;
					
				// check for an event spanning multiple days
				// from start to finish
				} else if ( startDate.getDay() < day &&
							endDate.getDay() > day ) {
					return true;
				} else {
					return false;
				}
				
			// check for an event spanning multiple months
			// from start to finish	
			} else if ( startDate.getMonth() < month &&
						endDate.getMonth() > month ) {
				return true;
			} else {
				return false;
			}
			
		// check for event that starts before a given year and ends after it
		// (a very long event)
		} else if ( startDate.getYear() < year &&
					endDate.getYear() > year ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines whether or not a user has access to this event
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#hasAccess( String userId )
	 */
	@Override
	public boolean hasAccess(String userId) {
		return userIds.contains( userId );
	}
	
	

}
