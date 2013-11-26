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

public class Commitment extends AbstractModel implements ICalendarEntry {

	// ID Parameters
	private int id; // object unique ID integer
	private String creatorId; // id of creating user
	private boolean isDeleted; // object is deleted from user view
	boolean isTeamCommitment; /*
								 * Whether or not the Commitment is a team
								 * Commitment
								 */

	// Descriptive Parameters
	private String name; // Name of Commitment
	private String description; // Description of Commitment

	// These two could be regulated to sets of start/end year, month, day,
	// halfHour
	private DateInfo dueDate; // StartDate of Commitment
	// private DateInfo endDate; // End Date of Commitment
	private Category category; // Category describing the Commitment
	private ArrayList<String> userIds; // userIds of users to participate in
										// Commitment

	// Potential fields for later
	// Repeat repeat;

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
	 * @param projectId
	 *            String id of Project that the Commitment is Linked to.
	 * @param creatorId
	 *            String id of User that the Commitment is Linked to (either
	 *            by creation or by personal calendar)
	 */
	public Commitment(String name, String description, DateInfo startDate,
			DateInfo endDate, Category category, boolean isTeamCommitment,
			int id, String creatorId) {

		this.isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.dueDate = startDate;

		this.category = category;

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
	 * @param projectId
	 *            String id of Project that the Commitment is Linked to.
	 * @param creatorId
	 *            String id of User that the Commitment is Linked to (either
	 *            by creation or by personal calendar)
	 */
	public Commitment(String name, String description, DateInfo startDate,
			DateInfo endDate, boolean isTeamCommitment, int id,
			String creatorId) {

		this.isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.dueDate = startDate;

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
	 * @param id
	 *            The id of the Commitment
	 * @param projectId
	 *            String id of Project that the Commitment is Linked to.
	 * @param creatorId
	 *            String id of User that the Commitment is Linked to (either
	 *            by creation or by personal calendar)
	 */
	public Commitment(String name, String description, DateInfo startDate,
			DateInfo endDate, boolean isTeamCommitment, Category category) {

		this.isDeleted = false;

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.dueDate = startDate;

		this.id = 0;// TODO auto generate unique
		this.creatorId = "bundle of fish";// TODO get from session

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
		return this.dueDate;
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
		this.id = absoluteId;
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
	 * Returns an instance of Commitment constructed using the given
	 * Commitment encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Commitment to deserialize
	 * 
	 * 
	 * 
	 * 
	 * @return the Commitment contained in the given JSON
	 */
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
	 * @return an array of Commitment deserialized from the given JSON string
	 */
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
		this.id = toCopyFrom.id;
		this.creatorId = toCopyFrom.creatorId;
		this.isDeleted = toCopyFrom.isDeleted;

		// Descriptive Parameters
		this.name = toCopyFrom.name;
		this.description = toCopyFrom.description;
		this.dueDate = toCopyFrom.dueDate;

		this.category = (toCopyFrom.category);
		this.userIds = toCopyFrom.userIds;

		this.isTeamCommitment = toCopyFrom.isTeamCommitment;
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
	public boolean equals(Object obj) {
		boolean out = false;

		if (obj instanceof Commitment) {
			out = (this.id == (((Commitment) obj).id))
					&& this.creatorId.equals(((Commitment) obj).creatorId)
					&& this.isDeleted == ((Commitment) obj).isDeleted &&

					this.name.equals(((Commitment) obj).name)
					&& this.description.equals(((Commitment) obj).description)
					&& this.dueDate.equals(((Commitment) obj).dueDate)

					&& this.category.equals(((Commitment) obj).category)
					&& this.userIds.equals(((Commitment) obj).userIds);
		}
		return out;
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
	public ArrayList<String> getUserIds() {
		return userIds;
	}

	/**
	 * Determines whether or not an Commitment occurs on a given year
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.models.entry#occursOnYear(int
	 *      year)
	 */
	@Override
	public boolean occursOnYear(int year) {
		if (dueDate.getYear() == year) {
			return true;
		}

		else {
			return false;
		}
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

			if (dueDate.getMonth() == month) {
				return true;
			} else {
				return false;
			}
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

				if (dueDate.getDay() == day) {
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}
		}

		else {
			return false;
		}
	}

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
