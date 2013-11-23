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

package edu.wpi.cs.wpisuitetng.modules.calendar.models.category;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;

/**
 * Category class that contains all the fields required to create a category
 * 
 * @author Joe Hill
 * 
 * @version $Revision: 1.0 $
 */
public class Category extends AbstractModel {
	/** Name of the category **/
	private String name;
	/** The unique identification number **/
	private int id;
	
	/**
	 * Constructor for a category object.
	 * @param name a string that represent the category type.
	 * @param id an integer that represents the object id.
	 */
	public Category(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Method toJSON.
	 * @return String * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	
	/**
	 * This returns a JSON encoded String 
	 * representation of this requirement object.
	 * @return a JSON encoded String representation
	 * of this requirement
	 */
	public String toJSON() {
		return new Gson().toJson(this, Category.class);
	}
	
	/**
	 * Method toString.
	 * @return String 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	/**
	 * This returns a Json encoded String representation
	 * of this requirement object.
	 * @return a Json encoded String representation of
	 * this Event
	 */
	@Override
	public String toString() {
		return this.getName();
	}
	
	/**
	 * Returns an instance of Category constructed 
	 * using the given Category encoded as a JSON string.
	 * @param json JSON-encoded Category to deserialize
	 * @return the Event contained in the given JSON
	 */
	public static Category fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Category.class);
	}
	
	/**
	 * Returns an array of Category parsed from 
	 * the given JSON-encoded string.
	 * @param json string containing a JSON-encoded
	 * array of Category
	 * @return an array of Event deserialized
	 * from the given JSON string 
	 */
	public static Category[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Category[].class);
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
	 * @param n the new name of the Category
	 */
	public void setName(String n) {
		if (!n.equals(name)) {
			final String originalName = name;
			String newName = n;
			if (newName.length() > 100)
				newName = newName.substring(0, 100);
			final String message = ("Name changed from " + originalName
					+ " to " + newName);
			System.out.println(message);
			// Possibly implemented later
			// this.history.add(message);
		}
		name = n;
		if (name.length() > 100)
			name = n.substring(0, 100);
	}

	/**
	 * Method createNewCategory.
	 * 
	 * @param name the name to give the new category
	 * @return cat the new category
	 */
	public Category createNewCategory(String name, int id) {
		final Category cat = new Category(name, id);
		System.out.println("New Category " + name + " created!");
		return cat;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Copies all of the values from the given Category
	 * to this Category.
	 * @param toCopyFrom the Category to copy from.
	 */
	public void copyFrom(Category toCopyFrom) {
		this.id = toCopyFrom.id;
		
		// Descriptive Parameters
		this.name = toCopyFrom.name;
	}
	
	
	// Required Methods for Database Interaction //

	
	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub	
	}

	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}

	/**
	 * Method identify.
	 * @param o Object
	 * @return Boolean
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
}