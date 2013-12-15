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

package edu.wpi.cs.wpisuitetng.modules.calendar.models.category;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Category class that contains all the fields required to create a category
 * 
 * @author Team Underscore
 * 
 * @version $Revision: 1.0 $
 */
public class Category extends AbstractModel {
	/** Name of the category **/
	private String name;
	/** The unique identification number **/
	private int id;

	private String creatorId;
	private boolean isDeleted;
	private boolean isTeamCat;

	private boolean hasFilter;

	/**
	 * Constructor for a category object.
	 * 
	 * @param name
	 *            a string that represent the category type.
	 * @param id
	 *            an integer that represents the object id.
	 */
	public Category(String name, int id) {
		this.name = name;
		this.id = id;
	}

	/**
	 * New, more full constructor more the category object.(for testing) Adds
	 * creatorID, team status, and deleted values to the category.
	 * 
	 * @param name
	 *            a string that represents the category type.
	 * @param id
	 *            an integer that represents the object id.
	 * @param creatorId
	 *            a String that represents the creator of the category
	 * @param isDeleted
	 *            stores whether the category has been removed from the display
	 *            of categories or not
	 * @param isTeamCat
	 *            states whether this category will show up on a team calendar
	 *            or not
	 */
	public Category(String name, int id, String creatorId, boolean isDeleted,
			boolean isTeamCat) {
		this.name = name;
		this.id = id;
		this.setCreatorID(creatorId);
		this.setDeleted(isDeleted);
		this.setTeamCat(isTeamCat);
	}

	/**
	 * Constructor for a category object.
	 * 
	 * @param name
	 *            a string that represent the category type.
	 * @param isTeamCat
	 *            states whether this category will show up on a team calendar
	 *            or not
	 */
	public Category(String name, boolean isTeamCat) {
		this.name = name;
		id = 0;
		creatorId = ConfigManager.getConfig().getUserName(); // gets user id
																// from system
																// configuration
		this.isTeamCat = isTeamCat;
		isDeleted = false;
	}

	/**
	 * (FOR ACTUAL USE) Constructor for a category object.
	 * 
	 * @param name
	 *            a string that represent the category type.
	 */
	public Category(String name) {
		this.name = name;
		id = 0;
		creatorId = ConfigManager.getConfig().getUserName(); // gets user id
																// from system
																// configuration
		this.isTeamCat = true;// all categories are avaliable to all users
		isDeleted = false;
		this.hasFilter = false;
	}

	// ------------------------------------------------------------------------
	// Getters/Setters for Category

	/**
	 * Empty Category constructor used in the Entity Manager
	 */
	public Category() {

	}

	/**
	 * Method getName.
	 * 
	 * @return name the name of the Category
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method setName.
	 * 
	 * @param n
	 *            the new name of the Category
	 */
	public void setName(String n) {
		if (!n.equals(name)) {
			final String originalName = name;
			String newName = n;
			if (newName.length() > 100) {
				newName = newName.substring(0, 100);
			}
			final String message = ("Name changed from " + originalName
					+ " to " + newName);
			System.out.println(message);
			// Possibly implemented later
			// Add the message to the history (There was code here, this is what
			// it did)
		}
		name = n;
		if (name.length() > 100) {
			name = n.substring(0, 100);
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the creator's ID
	 * 
	 * @return creatorID
	 */
	public String getCreatorID() {
		return creatorId;
	}

	/**
	 * Set the creator ID
	 * 
	 * @param creatorID
	 */
	public void setCreatorID(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * Get whether the category is deleted or not
	 * 
	 * @return isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * Set the "deleted" status of a category
	 * 
	 * @param isDeleted
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * Get the status of whether the category is team or personal
	 * 
	 * @return isTeamCat
	 */
	public boolean isTeamCat() {
		return isTeamCat;
	}

	/**
	 * Set the privacy status of the category
	 * 
	 * @param isTeamCat
	 */
	public void setTeamCat(boolean isTeamCat) {
		this.isTeamCat = isTeamCat;
	}

	// ------------------------------------------------------------------------
	// Functionality for the Category class

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean out = false;
		
		if(obj == null){
		}
		else if( this.getClass() == obj.getClass()){
			if(this.getName().equals(((Category) obj).getName()) ){
				out = true;
			}
		}
		return out;
				
		/*if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Category other = (Category) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;*/
	}

	/**
	 * Method toJSON.
	 * 
	 * @return String *
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() *
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */

	/**
	 * This returns a JSON encoded String representation of this requirement
	 * object.
	 * 
	 * @return a JSON encoded String representation of this requirement
	 */
	public String toJSON() {
		return new Gson().toJson(this, Category.class);
	}

	/**
	 * Method toString.
	 * 
	 * @return String
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	/**
	 * This returns a Json encoded String representation of this requirement
	 * object.
	 * 
	 * @return a Json encoded String representation of this Event
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Returns an instance of Category constructed using the given Category
	 * encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Category to deserialize
	 * @return the Event contained in the given JSON
	 */
	public static Category fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Category.class);
	}

	/**
	 * Returns an array of Category parsed from the given JSON-encoded string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Category
	 * @return an array of Event deserialized from the given JSON string
	 */
	public static Category[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Category[].class);
	}

	/**
	 * Method createNewCategory.
	 * 
	 * @param name
	 *            the name to give the new category
	 * @param id
	 *            the id for the new category
	 * @param creatorID
	 *            the ID of the user that created this Category
	 * @param isTeamCat
	 *            a boolean that is true if the category is available to the
	 *            whole team
	 * @return cat the new category
	 */
	public Category createNewCategory(String name, int id, String creatorID,
			boolean isTeamCat) {
		final Category cat = new Category(name, id, creatorID, false, isTeamCat);
		System.out.println("New Category " + name + " created!");
		return cat;
	}

	/**
	 * Copies all of the values from the given Category to this Category.
	 * 
	 * @param toCopyFrom
	 *            the Category to copy from.
	 */
	public void copyFrom(Category toCopyFrom) {
		id = toCopyFrom.id;

		// Descriptive Parameters

		name = toCopyFrom.name;
	}

	// ------------------------------------------------------------------------
	// Required Methods for Database Interaction //

	/**
	 * Method save.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	/**
	 * Method delete.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}

	/**
	 * Method identify.
	 * 
	 * @param o
	 *            Object
	 * @return Boolean
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the hasFilter
	 */

	public boolean getHasFilter() {
		return hasFilter;
	}

	/**
	 * @param hasFilter
	 *            the hasFilter to set
	 */

	public void setHasFilter(boolean hasFilter) {
		this.hasFilter = hasFilter;
	}

}