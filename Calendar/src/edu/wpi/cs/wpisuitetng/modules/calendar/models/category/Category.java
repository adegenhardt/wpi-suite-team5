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

/**
 * Category class that contains all the fields required to create a category
 * 
 * @author Joe Hill
 * 
 * @version $Revision: 1.0 $
 */
public class Category {
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
}