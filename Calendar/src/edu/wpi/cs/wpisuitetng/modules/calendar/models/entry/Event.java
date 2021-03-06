/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team _ 
 *    
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;


import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;

/**
 * Event Data Structure class: Users can add, modify, and remove events
 * from their calendar(s)
 * @author Team Underscore
 * @version $Revision: 1.0 $
 */
public class Event extends AbstractModel implements ICalendarEntry {

	// ID Parameters
	private int id; // object unique ID integer

	private String creatorId; // id of creating user
	private boolean isDeleted; // object is deleted from user view
	boolean isTeamEvent; /* Whether or not the event is a team event */

	// Descriptive Parameters
	private String name; // Name of Event
	private String projectId; // Name of Project for this Event
	private String description; // Description of Event
	private List<String> participants; // Participants of the event

	// These two could be regulated to sets of start/end year, month, day,
	// halfHour
	private DateInfo startDate; // StartDate of event
	private DateInfo endDate; // End Date of event
	private int categoryId; // id of category describing the event
	private List<String> userIds; // userIds of users to participate in
										// event

	private Color color;		// The color to draw the event on the calendar views
	
	

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
		categoryId = -1;

		id = -1;
		creatorId = "-1";

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		
		color = generateRandomColor();
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
	 * @param categoryId
	 *            id of corresponding category for the event
	 * @param id
	 *            The id of the Event
	 * @param creatorId
	 *            String id of User that the Event is Linked to (either by
	 *            creation or by personal calendar)
	 * @param isTeamEvent
	 * 			  Whether or not the event is a team event
	 */
	public Event(String name, String description, DateInfo startDate,
			DateInfo endDate, int categoryId, boolean isTeamEvent, int id,
			String creatorId) {

		isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.categoryId = categoryId;

		this.id = id;
		this.creatorId = creatorId;
		
		projectId = ConfigManager.getConfig().getProjectName();

		this.isTeamEvent = isTeamEvent;

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		userIds.add(creatorId);
		
		color = generateRandomColor();
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

		this.id = id;
		this.creatorId = creatorId;
		
		categoryId = -1;

		this.isTeamEvent = isTeamEvent;

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		userIds.add(creatorId);
		
		color = generateRandomColor();
	}

	/**
	 * FOR FUNCTIONALITY USE CONSTRUCTOR (REQUIRES CATEGORY)System Implemented
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
	 * @param categoryId
	 * 			  The category this event is in
	 */
	public Event(String name, String description, DateInfo startDate,
			DateInfo endDate, boolean isTeamEvent, int categoryId) {

		isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;

		id = 0;
		creatorId = ConfigManager.getConfig().getUserName();// gets user id
												// from system configuration
		
		projectId = ConfigManager.getConfig().getProjectName();

		this.categoryId = categoryId;

		this.isTeamEvent = isTeamEvent;

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		userIds.add(creatorId);
		
		color = generateRandomColor();
	}
	
	/**
	 * PARTICIPANT-SUPPORTED EVENT CONSTRUCTOR
	 * This is the same as the previous constructor, just with the added
	 * participants field.
	 * @param name The name of the event
	 * @param description A description of the event
	 * @param startDate The start time of the event
	 * @param endDate The end time of the event
	 * @param categoryId A category used to filter this event
	 * @param isTeamEvent Says whether the event is team or personal
	 * @param participants A list of people involved with this event
	 */
	
	public Event(String name, String description, DateInfo startDate,
			DateInfo endDate, int categoryId, boolean isTeamEvent,
			List<String> participants){
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.categoryId = categoryId;
		this.isTeamEvent = isTeamEvent;
		this.setParticipants(participants);
		
		color = generateRandomColor();
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
	 * @return The project name with which this event was created
	 */
	public String getProjectId() {
		return projectId;
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
	
	public void setProjectId(String idProject) {
		projectId = idProject;
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
	 * @return the id of the category for this event
	 */
	public int getCategory() {
		return categoryId;
	}

	/**
	 * 
	 * @param category the id of the new category of this event
	 */
	public void setCategory(int category) {
		categoryId = category;
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

	/**
	 * @return participants The entire list of participants
	 */
	public List<String> getParticipants() {
		return participants;
	}

	/**
	 * @param participants A List of participants involved with the event
	 */
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	
	/**
	 * @param index The index of a given participant in the list
	 * @return theParticipant The participant whose index in the list
	 * 						  matches the input.
	 */
	public String getAParticipant(int index){
		return participants.get(index);
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
		categoryId =(toCopyFrom.categoryId);
		userIds = toCopyFrom.userIds;
		color = toCopyFrom.color;
		
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
	
	/**
	 * 
	 * @return the color that displays the event on the calendar view
	 */
	public Color getColor() {
		
		if ( color == null ) {
			color = generateRandomColor();
		}
		
		return color;
	}

	/**
	 * 
	 * @param color the color to display the event
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Generates a random light color for the event to use
	 * @return the generated color
	 */
	public Color generateRandomColor() {
		
		final Random rand = new Random();
		
		final int minimumRGB = 400;			// combined RGB values must equal at least this much
		final int minimumGreen = 100;		// The green value must be at least this much
		
		// generated RBG values
		int r = rand.nextInt( 250 );
		int g = rand.nextInt( 250 );
		int b = rand.nextInt( 250 );
		
		while ( g < minimumGreen ) {
			g = rand.nextInt( 250 );
		}
		
		while ( r + g + b < minimumRGB ) {
			r = rand.nextInt( 250 );
			b = rand.nextInt( 250 );
		}
		
		return new Color( r, g, b );
		
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ categoryId;
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

	// Error checking
	/**
	 * checks for equality between objects
	 */
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
		
		if (categoryId != other.categoryId) {
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
	 * Determines whether or not two events are equivalent with the
	 * exception of their unique IDs.
	 * 
	 * @param obj any object that is passed for input to compare against.
	 * 
	 * @return true or false depending upon whether or not the object passed in is
	 * equivalent with the exception of its unique ID.
	 */
	public boolean everythingEquivalentButUniqueID(Object obj) {
		
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
		
		if (categoryId != other.categoryId) {
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
	public boolean occursOnDate(int year, int month, int day) 
			throws ArrayIndexOutOfBoundsException {

		if ( month < 0 || month > 11 ) {
			throw new ArrayIndexOutOfBoundsException( "Valid months are in the range 0 - 11" );
		}
		
		if ( day < 0 || day >= calculateNumDays( year, month ) ) {
			throw new ArrayIndexOutOfBoundsException( "Day is out of range" );
		}

		if (startDate.getYear() == year || endDate.getYear() == year) {

			if (startDate.getMonth() == month || endDate.getMonth() == month) {

				if (startDate.getDay() == day ) {
					return true;
					
				// check that the day isn't ending at midnight
				} else if ( endDate.getDay() == day && endDate.getHalfHour() != 0 ) {
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
	 * 
	 * @param _projectId The given project name to be checked against
	 * @return True if the project names are equal, false if otherwise
	 */
	public boolean hasTeamAccess(String _projectId) {
		return projectId.equals(_projectId);
	}

	
	/**
	 * Calculate the number of days in a given month
	 * @param year the year to check
	 * @param month the month to check (from 0-11)
	 * @return a number from 28-31 representing the number of days in the month
	 */
	public int calculateNumDays( int year, int month ) {
		
		// Check for months with 31 days
		if ( month == 0 || month == 2 || month == 4 ||
				month == 6 || month == 7 || month == 9 ||
				month == 11 ) {
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
