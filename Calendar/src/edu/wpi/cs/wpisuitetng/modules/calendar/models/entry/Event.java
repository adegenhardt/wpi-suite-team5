/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
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


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.List;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;


import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

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
	private Calendar absoluteId; // calendar identifying the time of original
									// creation of the event, for unique id
									// parameter
	private String creatorId; // id of creating user
	private boolean isDeleted; // object is deleted from user view
	boolean isTeamEvent; /* Whether or not the event is a team event */

	// Descriptive Parameters
	private String name; // Name of Event
	private String description; // Description of Event

	// These two could be regulated to sets of start/end year, month, day,
	// halfHour
	private DateInfo startDate; // StartDate of event
	private DateInfo endDate; // End Date of event
	private Category category; // Category describing the event
	private List<String> userIds; // userIds of users to participate in
										// event

	// TODO Either a projectId field is needed or we need to be certain that we
	// are only retrieving from the current project's section of the database


	/**
	 * Default event constructor that sets invalid values to all fields
	 */
	public Event() {
		isDeleted = false;

		// Descriptive Parameters
		name = "";
		description = "";
		startDate = new DateInfo(-1, -1, -1, -1);
		endDate = new DateInfo(-1, -1, -1, -1);
		category = new Category("-1", -1);

		final Calendar currentDateTime = Calendar.getInstance();
		absoluteId = currentDateTime;
		id = -1;
		creatorId = "-1";

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
	}

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
	 *            The id of the Event
	 * @param creatorId
	 *            String id of User that the Event is Linked to (either by
	 *            creation or by personal calendar)
	 * @param isTeamEvent
	 * 			  Whether or not the event is a team event
	 */
	public Event(String name, String description, DateInfo startDate,
			DateInfo endDate, Category category, boolean isTeamEvent, int id,
			String creatorId) {

		isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;

		final Calendar currentDateTime = Calendar.getInstance();
		absoluteId = currentDateTime;
		this.id = id;
		this.creatorId = creatorId;

		this.isTeamEvent = isTeamEvent;

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		userIds.add(creatorId);
	}

	/**
	 * System Implemented Constructor for Event.
	 * Gets and sets the project and
	 * user Id fields based on system's current project and user. absoluteId is
	 * generated as unique int at creation To be used in User Event creation
	 *
	 * (GIVES DEFAULT CATEGORY)System Implemented Constructor for Event. Gets
	 * and sets the project and user Id fields based on system's current project
	 * and user. absoluteId is generated as unique int at creation To be used in
	 * User Event creation
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
	 * @param id
	 *            The id of the Event
	 * @param creatorId
	 *            String id of User that the Event is Linked to (either by
	 *            creation or by personal calendar)
	 * @param isTeamEvent
	 * 			  Whether or not this event is a team event
	 */
	public Event(String name, String description, DateInfo startDate,
			DateInfo endDate, boolean isTeamEvent, int id, String creatorId) {

		isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;


		final Calendar currentDateTime = Calendar.getInstance();
		absoluteId = currentDateTime;

		this.id = id;
		this.creatorId = creatorId;
		
		category = new Category( name, id);
		

		this.id = id;// TODO auto generate unique
		this.creatorId = creatorId;// TODO get from session

		category = new Category(name, id);

		this.isTeamEvent = isTeamEvent;

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		userIds.add(creatorId);
	}

	/**
	 * FOR REAL USE CONSTRUCTOR (REQUIRES CATEGORY)System Implemented
	 * Constructor for Event. Gets and sets the project and user Id fields based
	 * on system's current project and user. absoluteId is generated as unique
	 * int at creation To be used in User Event creation
	 * 
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
	 * @param isTeamEvent
	 * 			  Whether or not this is a team event
	 * @param category
	 * 			  The category this event is in
	 */
	public Event(String name, String description, DateInfo startDate,
			DateInfo endDate, boolean isTeamEvent, Category category) {

		isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;


		final Calendar currentDateTime = Calendar.getInstance();
		absoluteId = currentDateTime;

		id = 0;// TODO auto generate unique
		creatorId = ConfigManager.getConfig().getUserName();// gets user id
																	// from
																	// system
																	// configuration


		this.category = category;

		this.isTeamEvent = isTeamEvent;

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		userIds.add(creatorId);
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
		return startDate;
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
		id = absoluteId;
	}

	/**
	 * 
	 * @param idUser
	 */
	public void setCreatorId(String idUser) {
		creatorId = idUser;
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

	/**
	 * 
	 * @return True if the event is a team event, false if it's an individual
	 *         event
	 */
	public boolean isTeamEvent() {
		return isTeamEvent;
	}

	/**
	 * 
	 * @param isTeamEvent
	 *            true if the event is a team event, false if it's an individual
	 *            event
	 */
	public void setTeamEvent(boolean isTeamEvent) {
		this.isTeamEvent = isTeamEvent;
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
	 * @return String 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
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
	 * @return a Json encoded String representation of this Event 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 * This returns a Json encoded String representation of this requirement
	 * object.
	 * 
	 */
	@Override
	public String toString() {
		return name;
	}

	public void setUserIds(List<String> userIds) {
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
		id = toCopyFrom.id;
		creatorId = toCopyFrom.creatorId;
		isDeleted = toCopyFrom.isDeleted;

		// Descriptive Parameters
		name = toCopyFrom.name;
		description = toCopyFrom.description;
		startDate = toCopyFrom.startDate;
		endDate = toCopyFrom.endDate;
		category =(toCopyFrom.category);
		userIds = toCopyFrom.userIds;
		
		isTeamEvent = toCopyFrom.isTeamEvent;
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
	 * @param id
	 *            the new ID for the object
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((creatorId == null) ? 0 : creatorId.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + id;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result + (isTeamEvent ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((userIds == null) ? 0 : userIds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if ( !( obj instanceof Event ) ||
				!( obj instanceof ICalendarEntry ) ){
			return false;
		}
		
		if (this == obj) {
			return true;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		final Event other = (Event) obj;
		
		if (category == null) {
			if (other.category != null) {
				return false;
			}
		} else if (!category.equals(other.category)) {
			return false;
		}
		
		if (creatorId == null) {
			if (other.creatorId != null) {
				return false;
			}
		} else if (!creatorId.equals(other.creatorId)) {
			return false;
		}
		
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		
		if (id != other.id) {
			return false;
		}
		
		if (isDeleted != other.isDeleted) {
			return false;
		}
		
		if (isTeamEvent != other.isTeamEvent) {
			return false;
		}
		
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		
		if (userIds == null) {
			if (other.userIds != null) {
				return false;
			}
		} else if (!userIds.equals(other.userIds)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Add a user to the collection of users that are involved in an event
	 * 
	 * @param newId
	 *            the userID to be added
	 */
	public void addUserId(String newId) {
		// TODO how to enforce that these are actual user IDS for sending out
		// invites
		if (isTeamEvent()) {
			userIds.add(newId);
		}
	}

	/**
	 * Remove a user from the collection of users involved with the event
	 * 
	 * @param removedId
	 *            the user ID to be removed
	 */
	public void removeUserId(String removedId) {
		userIds.remove(removedId);
	}

	// Interface methods
	// ///////////////////////////////////////////

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
	 * @param isDeleted
	 *            true if the object is now "deleted", false otherwise
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * 
	 * @return the user ids of all people involved in the event
	 */
	public List<String> getUserIds() {
		return userIds;
	}

	public Calendar getAbsoluteId() {
		return absoluteId;
	}
	
	/**
	 * @return the Event's absoluteId in a string of format:yyyy/MM/dd HH:mm:ss
	 */
	public String getAbsoluteIdStringFormat(){
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(absoluteId.getTime());
	}

	/**
	 * Determines whether or not an event occurs on a given year
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnYear(int
	 *      year)
	 */
	@Override
	public boolean occursOnYear(int year) {
		if (startDate.getYear() == year || endDate.getYear() == year) {
			return true;

			// check for event that starts before a given year and ends after it
			// (a very long event)
		} else if (startDate.getYear() < year && endDate.getYear() > year) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines whether or not an event occurs on a given month
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnMonth(int
	 *      year, int month)
	 */
	@Override
	public boolean occursOnMonth(int year, int month) throws ArrayIndexOutOfBoundsException {

		if ( month < 0 || month > 11 ) {
			throw new ArrayIndexOutOfBoundsException( "Valid months are in the range 0 - 11" );
		}
		if (startDate.getYear() == year || endDate.getYear() == year) {

			if (startDate.getMonth() == month || endDate.getMonth() == month) {
				return true;

				// check for an event spanning multiple months
				// from start to finish
			} else if (startDate.getMonth() < month
					&& endDate.getMonth() > month) {
				return true;
			} else {
				return false;
			}

			// check for event that starts before a given year and ends after it
			// (a very long event)
		} else if (startDate.getYear() < year && endDate.getYear() > year) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines whether or not an event occurs on a given month
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnDate(int
	 *      year, int month, int day)
	 */
	@Override
	public boolean occursOnDate(int year, int month, int day) throws ArrayIndexOutOfBoundsException {

		if ( month < 0 || month > 11 ) {
			throw new ArrayIndexOutOfBoundsException( "Valid months are in the range 0 - 11" );
		}
		
		if ( day < 0 || day >= calculateNumDays( year, month ) ) {
			throw new ArrayIndexOutOfBoundsException( "Day is out of range" );
		}

		if (startDate.getYear() == year || endDate.getYear() == year) {

			if (startDate.getMonth() == month || endDate.getMonth() == month) {

				if (startDate.getDay() == day || endDate.getDay() == day) {
					return true;

					// check for an event spanning multiple days
					// from start to finish
				} else if (startDate.getDay() < day && endDate.getDay() > day) {
					return true;
				} else {
					return false;
				}

				// check for an event spanning multiple months
				// from start to finish
			} else if (startDate.getMonth() < month
					&& endDate.getMonth() > month) {
				return true;
			} else {
				return false;
			}

			// check for event that starts before a given year and ends after it
			// (a very long event)
		} else if (startDate.getYear() < year && endDate.getYear() > year) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * states if an event has the same absoluteId as the given event
	 * 
	 * @param eventCompare
	 *            event to compare absoluteId of with
	
	 * @return boolean
	 */
	public boolean isSameAbsoluteId(Event eventCompare) {
		boolean out = false;
		if (absoluteId == eventCompare.getAbsoluteId()) {
			out = true;
		}
		return out;
	}

	/**
	 * states if an event has the same absoluteId as the given absoluteId
	 * 
	
	
	 * @param absoluteIdCompare Calendar
	 * @return boolean
	 */
	public boolean isSameAbsoluteId(Calendar absoluteIdCompare) {
		boolean out = false;
		if (absoluteId == absoluteIdCompare) {
			out = true;
		}
		return out;
	}
/**
 * determines if given event belongs to the current janeway session's user
 * @return boolean
 */
	public boolean isActiveUserEvent(){
		boolean out = false;
		if (creatorId.equals(ConfigManager.getConfig().getUserName()) ) {
			out = true;
		}
		
		return out;
	}
	
	/**
	 * determines if given event belongs to the given user's userId
	
	 * @param userCheck String
	 * @return boolean */
		public boolean isUserEvent(String userCheck){
			boolean out = false;
			if (creatorId.equals(userCheck) ) {
				out = true;
			}
			
			return out;
		}
	
	/**
	 * Determines whether or not a user has access to this event
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#hasAccess(String
	 *      userId )
	 */
	@Override
	public boolean hasAccess(String userId) {
		return userIds.contains(userId);
	}

	
	/**
	 * Calculate the number of days in a given month
	 * @param year the year to check
	 * @param month the month to check (from 0-11)
	 * @return a number from 28-31 representing the number of days in the month
	 */
	private int calculateNumDays( int year, int month ) {
		
		// Check for months with 31 days
		if ( month == 0 || month == 2 || month == 5 ||
				month == 7 || month == 8 || month == 10 ||
				month == 12 ) {
			return 31;
			
		// Otherwise, check if the month isn't February (ie: 30 days)
		} else if ( month != 1 ) {
			return 30;
		}
		
		// At this point the month is February, so check if leap year or not
		if ( year % 4 == 0 &&
			( !( year % 100 == 0 ) || (year % 400 == 0 ) ) ) {
			return 29;
		} else {
			return 28;
		}
	}
	
}
