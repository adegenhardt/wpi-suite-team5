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
	/** Description of the category **/
	private String description;

	/** Constructs a Category with default characteristics **/
	public Category() {
		// Default value for name and description is blank
		name = description = "";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Category) {
			return (this.name.equals(((Category) obj).getName()) &&
					this.description.equals(((Category) obj)
							.getDescription()));
		}
		else {
			return false;
		}

	}

	/**
	 * Constructs a Category with given values
	 * 
	 * @param name
	 *            String
	 * @param description
	 *            String
	 **/
	public Category(String name, String description) {
		this();
		this.name = name;
		this.description = description;
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
			// Add the message to the history (There was code here, this is what it did)
		}
		name = n;
		if (name.length() > 100) {
			name = n.substring(0, 100);
		}
	}

	/**
	 * Method getDescription.
	 * 
	 * @return description the description of the Category
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method setDescription.
	 * 
	 * @param desc
	 *            String the description to set
	 */
	public void setDescription(String desc) {
		if (!desc.equals(description)) {
			System.out.println("Description changed!");
			// Possibly implemented later
			// Add a message in history to say description changed (Code here again)
		}
		description = desc;
	}

	/**
	 * Method createNewCategory.
	 * 
	 * @param name
	 *            the name to give the new category
	 * @param description
	 *            the description to give the new category
	 * @return cat the new category
	 */
	public Category createNewCategory(String name, String description) {
		final Category cat = new Category(name, description);
		System.out.println("New Category " + name + " created!");
		return cat;
	}
}