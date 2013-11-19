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

/** Category class that contains all the fields required to create a category
 * @author Joe Hill
 *
 */

public class Category {
	/** Name of the category **/
	private String name;
	/** Description of the category **/
	private String description;
	
	/** Constructs a Category with default characteristics **/
	public Category() {
		super();
		name = description = "";
	}
	
	/** Constructs a Category with given values **/
	public Category(String name, String description) {
		this();
		this.name = name;
		this.description = description;
	}
	
	/** Method getName.
	 * 
	 * @return name
	 * 				the name of the Category
	 */
	public String getName() {
		return this.name;
	}
	
	/** Method setName.
	 * 
	 * @param n
	 * 			the new name of the Category
	 */
	public void setName(String n) {
		if (!n.equals(this.name)) {
			String originalName = this.name;
			String newName = n;
			if (newName.length() > 100)
				newName = newName.substring(0, 100);
			String message = ("Name changed from " + originalName + " to " + newName);
			// this.history.add(message);            Possibly implemented later
		}
		this.name = n;
		if (name.length() > 100)
			this.name = n.substring(0, 100);
	}
	
	/** Method getDescription.
	 * 
	 * 
	 * @return description
	 * 				the description of the Category
	 **/
	public String getDescription() {
		return description;
	}

	/** Method setDescription.
	 * 
	 *  @param description
	 *            the description to set
	 */
	public void setDescription(String desc) {
		if (!desc.equals(this.description)) {
			// this.history.add("Description changed");			Will be implemented later
		}
		this.description = desc;
	}
	
	/** Method createNewCategory.
	 * 
	 * @param name
	 * 			the name to give the new category
	 * @param description
	 * 			the description to give the new category
	 * @return cat
	 * 			the new category
	 */
	public Category createNewCategory(String name, String description) {
		Category cat = new Category(name, description);
		return cat;
	}
}