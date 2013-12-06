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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;

/**
 * @author Team Underscore
 * @version $Revision: 1.0 $
 */

public class Commitment extends AbstractModel implements ICalendarEntry {

	// ID Parameters
	private int id; // object unique ID integer
	private String creatorId; // id of creating user
	private boolean isDeleted; // object is deleted from user view
	boolean isTeamCommitment; /*
								 * Whether or not the Commitment is a team
								 * Commitment
								 */
	private final Calendar absoluteId;
	
	// Descriptive Parameters
	private String name; // Name of Commitment
	private String description; // Description of Commitment

	// These two could be regulated to sets of start/end year, month, day,
	// halfHour
	private DateInfo dueDate; // StartDate of Commitment
	private Category category; // Category describing the Commitment
	private List<String> userIds; // userIds of users to participate in
										// Commitment

	/**
	 * Default Commitment constructor that sets invalid values to all fields
	 */
	public Commitment() {
		isDeleted = false;

		// Descriptive Parameters
		name = "";
		description = "";
		dueDate = new DateInfo(-1, -1, -1, -1);

		category = new Category("-1", -1);

		final Calendar currentDateTime = Calendar.getInstance();
		absoluteId = currentDateTime;
		id = -1;
		creatorId = "-1";

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
	}

	/**
	 * Full Specification Constructor for Commitment. (Test Only)
	 * 
	 * 
	 * @param name
	 *            String name of Commitment
	 * @param description
	 *            String description of Commitment
	 * @param startDate
	 *            DateInfo dateInfo parameter for holding date Commitment
	 *            starts
	 * @param endDate
	 *            DateInfo dateInfo parameter for holding date Commitment ends
	 * @param category
	 *            List<Category> list of categories commitment is part of
	 * @param id
	 *            The id of the Commitment
	
	 * @param creatorId
	 *            String id of User that the Commitment is Linked to (either
	 *            by creation or by personal calendar)
	 * @param isTeamCommitment boolean
	 */
	public Commitment(String name, String description, DateInfo startDate,
			DateInfo endDate, Category category, boolean isTeamCommitment,
			int id, String creatorId) {

		isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		dueDate = startDate;

		this.category = category;

		final Calendar currentDateTime = Calendar.getInstance();
		absoluteId = currentDateTime;
		this.id = id;
		this.creatorId = creatorId;

		this.isTeamCommitment = isTeamCommitment;

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		userIds.add(creatorId);
	}

	/**
	 * (GIVES DEFAULT CATEGORY)System Implemented Constructor for Commitment.
	 * Gets and sets the project and user Id fields based on system's current
	 * project and user. absoluteId is generated as unique int at creation To be
	 * used in User Commitment creation
	 * 
	 * 
	 * 
	 * @param name
	 *            String name of Commitment
	 * @param description
	 *            String description of Commitment
	 * @param startDate
	 *            DateInfo dateInfo parameter for holding date Commitment
	 *            starts
	 * @param endDate
	 *            DateInfo dateInfo parameter for holding date Commitment ends
	 * @param id
	 *            The id of the Commitment
	
	 * @param creatorId
	 *            String id of User that the Commitment is Linked to (either
	 *            by creation or by personal calendar)
	 * @param isTeamCommitment boolean
	 */
	public Commitment(String name, String description, DateInfo startDate,
			DateInfo endDate, boolean isTeamCommitment, int id,
			String creatorId) {

		isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		dueDate = startDate;

		final Calendar currentDateTime = Calendar.getInstance();
		absoluteId = currentDateTime;
		
		this.id = id;// TODO auto generate unique
		this.creatorId = creatorId;// TODO get from session

		category = new Category(name, id);

		this.isTeamCommitment = isTeamCommitment;

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		userIds.add(creatorId);
	}

	/**
	 * FOR REAL USE CONSTRUCTOR (REQUIRES CATEGORY)System Implemented
	 * Constructor for Commitment. Gets and sets the project and user Id
	 * fields based on system's current project and user. absoluteId is
	 * generated as unique int at creation To be used in User Commitment
	 * creation
	 * 
	 * 
	 * 
	 * @param name
	 *            String name of Commitment
	 * @param description
	 *            String description of Commitment
	 * @param startDate
	 *            DateInfo dateInfo parameter for holding date Commitment
	 *            starts
	 * @param endDate
	 *            DateInfo dateInfo parameter for holding date Commitment ends
	
	
	
	 * @param isTeamCommitment boolean
	 * @param category Category
	 */
	public Commitment(String name, String description, DateInfo startDate,
			DateInfo endDate, boolean isTeamCommitment, Category category) {

		isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		dueDate = startDate;

		id = 0;// TODO auto generate unique
		creatorId = ConfigManager.getConfig().getUserName();

		final Calendar currentDateTime = Calendar.getInstance();
		absoluteId = currentDateTime;
		this.category = category;

		this.isTeamCommitment = isTeamCommitment;

		// create empty list of userIds and add the creator
		userIds = new ArrayList<String>();
		userIds.add(creatorId);
	}

	// ---------------------------------------------------------
	// Get/Set for the fields of Commitment

	/**
	 * 
	 * @return The description of the Commitment
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @return the year that the Commitment starts
	 */
	public int getStartYear() {
		return dueDate.getYear();
	}

	/**
	 * 
	 * @return The starting date and time of Commitment as a DateInfo object
	 */
	public DateInfo getStartDate() {
		return dueDate;
	}

	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * 
	 * @param absoluteId
	 *            the Commitment's new ID
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
	 * @param CommitmentName
	 *            The new name of the Commitment
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param CommitmentDescr
	 *            The new Commitment description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @param startDate
	 *            The new starting date for the Commitment
	 */
	public void setDueDate(DateInfo dueDate) {
		this.dueDate = dueDate;
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
	 * @return True if the Commitment is a team Commitment, false if it's an
	 *         individual Commitment
	 */
	public boolean isTeamCommitment() {
		return isTeamCommitment;
	}

	/**
	 * 
	 * @param isTeamCommitment
	 *            true if the Commitment is a team Commitment, false if it's
	 *            an individual Commitment
	 */
	public void setTeamCommitment(boolean isTeamCommitment) {
		this.isTeamCommitment = isTeamCommitment;
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
		return new Gson().toJson(this, Commitment.class);
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
	 * @return a Json encoded String representation of this Commitment
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
	 * Returns an instance of Commitment constructed using the given
	 * Commitment encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Commitment to deserialize
	 * 
	 * 
	 * 
	 * 
	
	 * @return the Commitment contained in the given JSON */
	public static Commitment fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Commitment.class);
	}

	/**
	 * Returns an array of Commitment parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Commitment
	 * 
	 * 
	 * 
	 * 
	
	 * @return an array of Commitment deserialized from the given JSON string */
	public static Commitment[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Commitment[].class);
	}

	/**
	 * Copies all of the values from the given Commitment to this
	 * Commitment.
	 * 
	 * @param toCopyFrom
	 *            the Commitment to copy from.
	 */

	public void copyFrom(Commitment toCopyFrom) {
		id = toCopyFrom.id;
		creatorId = toCopyFrom.creatorId;
		isDeleted = toCopyFrom.isDeleted;

		// Descriptive Parameters
		name = toCopyFrom.name;
		description = toCopyFrom.description;
		dueDate = toCopyFrom.dueDate;

		category = (toCopyFrom.category);
		userIds = toCopyFrom.userIds;

		isTeamCommitment = toCopyFrom.isTeamCommitment;
	}

	/**
	 * 
	 * @return the unique ID of the Commitment
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
				+ ((absoluteId == null) ? 0 : absoluteId.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((creatorId == null) ? 0 : creatorId.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + id;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result + (isTeamCommitment ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((userIds == null) ? 0 : userIds.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Commitment other = (Commitment) obj;
		if (absoluteId == null) {
			if (other.absoluteId != null) {
				return false;
			}
		} else if (!absoluteId.equals(other.absoluteId)) {
			return false;
		}
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
		if (dueDate == null) {
			if (other.dueDate != null) {
				return false;
			}
		} else if (!dueDate.equals(other.dueDate)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (isDeleted != other.isDeleted) {
			return false;
		}
		if (isTeamCommitment != other.isTeamCommitment) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
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
	 * Add a user to the collection of users that are involved in an
	 * Commitment
	 * 
	 * @param newId
	 *            the userID to be added
	 */
	public void addUserId(String newId) {
		// TODO how to enforce that these are actual user IDS for sending out
		// invites
		if (isTeamCommitment()) {
			userIds.add(newId);
		}
	}

	/**
	 * Remove a user from the collection of users involved with the Commitment
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
	 * @return the user ids of all people involved in the Commitment
	 */
	public List<String> getUserIds() {
		return userIds;
	}

	private Calendar getAbsoluteId() {
		return absoluteId;
	}
	
	/**
	 * @return the Commitment's absoluteId in a string of format:yyyy/MM/dd HH:mm:ss
	 */
	public String getAbsoluteIdStringFormat(){
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(absoluteId.getTime());
	}

	/**
	 * Determines whether or not an Commitment occurs on a given year
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnYear(int
	 *      year)
	 */
	@Override
	public boolean occursOnYear(int year) {
		return (dueDate.getYear() == year);
	}

	/**
	 * Determines whether or not an Commitment occurs on a given month
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnMonth(int
	 *      year, int month)
	 */
	@Override
	public boolean occursOnMonth(int year, int month) {

		// TODO: Error handling for invalid months (< 0, > 11 )

		if (dueDate.getYear() == year) {
			return (dueDate.getMonth() == month);
		} else {
			return false;
		}
	}

	/**
	 * Determines whether or not an Commitment occurs on a given month
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnDate(int
	 *      year, int month, int day)
	 */
	@Override
	public boolean occursOnDate(int year, int month, int day) {

		// TODO: Error handling for invalid days (< 0, >= number of days in
		// month )

		if (dueDate.getYear() == year) {

			if (dueDate.getMonth() == month) {

				return (dueDate.getDay() == day);

			} else {
				return false;
			}
		}

		else {
			return false;
		}
	}
	/**
	 * states if an Commitment has the same absoluteId as the given Commitment
	 * 
	 * @param CommitmentCompare
	 *            Commitment to compare absoluteId of with
	
	 * @return boolean
	 */
	public boolean isSameAbsoluteId(Commitment CommitmentCompare) {
		boolean out = false;
		if (absoluteId == CommitmentCompare.getAbsoluteId()) {
			out = true;
		}
		return out;
	}

	
	/**
	 * states if an Commitment has the same absoluteId as the given absoluteId
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
	 * determines if given Commitment belongs to the current janeway session's user
	 * @return boolean
	 */
		public boolean isActiveUserCommitment(){
			boolean out = false;
			if(creatorId.equals(ConfigManager.getConfig().getUserName()) ) {
				out = true;
			}
			
			return out;
		}
	
		/**
		 * determines if given Commitment belongs to the given user's userId
		
		 * @param userCheck String
		 * @return boolean */
			public boolean isUserCommitment(String userCheck){
				boolean out = false;
				if(creatorId.equals(userCheck) ) {
					out = true;
				}
				
				return out;
			}
		
		/**
		
	/**
	 * Determines whether or not a user has access to this Commitment
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#hasAccess(String
	 *      userId )
	 */
	@Override
	public boolean hasAccess(String userId) {
		return userIds.contains(userId);
	}

}
