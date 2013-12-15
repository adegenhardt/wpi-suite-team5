/*********************************************************************************************
 * Copyright (c) 2013 WPI Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * List of Category pulled from the server
 * 
 * Adapted from RequirementModel.java by Team Underscore Database Crew
 * 
 * Contributors: Team _
 *
 *********************************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models.category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller.GetCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.refresh.RefreshListenerCategories;
import edu.wpi.cs.wpisuitetng.modules.calendar.refresh.RefreshListenerEvents;

import javax.swing.AbstractListModel;

/**
 * List of Categories pulled from the server
 * 
 * @author Team Underscore
 * 
 * @version $Revision: 1.0 $
 */
public class CategoryModel extends AbstractListModel<Category> {

	private static final long serialVersionUID = 8555534911453497404L;

	// ********************************************************************
	// Construct the Calendar Model

	/**
	 * The list in which all the calendar data for a single project are
	 * contained
	 */
	private final List<Category> categories;

	// TODO: Research if and how this is maintained between different instances
	// of the program
	private int nextID; // the next available ID number for the calendar data
						// that are added
	// the static object that allows the calendar data model to be
	private static CategoryModel instance = null;

	/**
	 * Constructs an empty list of calendar data Sets a default ID of 0 to the
	 * calendar data
	 */
	private CategoryModel() {
		categories = new ArrayList<Category>();
		nextID = 0;
	}

	// **********************************************************************
	// Manipulate calendar data

	/**
	 * @return the instance of the calendar data model singleton
	 */
	public static CategoryModel getInstance() {
		if (instance == null) {
			instance = new CategoryModel();
			instance.addListDataListener(new RefreshListenerCategories());
		}
		return instance;
	}

	/**
	 * Adds a single calendar datum to the data of the project
	 * 
	 * @param newCategory
	 *            The calendar datum to be added to the list of calendar data in
	 *            the project
	 */
	public void addCategory(Category newCategory) {
		categories.add(newCategory);

		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Returns the Calendar Data with the given ID
	 * 
	 * @param id
	 *            The ID number of the calendar data to be returned
	 * 
	 * 
	 * @return the calendar data for the ID, or null if the data is not found.
	 */
	public Category getCategory(int id) {
		Category temp = null;
		Category out = null;
		// iterate through the calendar data in order to find the matching ID
		// break the loop once the ID is found
		for (int i = 0; i < categories.size(); i++) {
			temp = categories.get(i);
			if (temp.getId() == id) {
				out = temp;
				break;
			}
		}
		return out;
	}

	/**
	 * Returns the Calendar Data with the given Name
	 * 
	 * @param name
	 *            The name string of the category to be returned
	 * 
	 * 
	 * @return the category for the ID, or null if the data is not found.
	 */
	public Category getCategory(String name) {
		Category temp = null;
		Category out = null;
		// iterate through the category in order to find the matching Name
		// break the loop once the Name is found
		for (int i = 0; i < categories.size(); i++) {
			temp = categories.get(i);
			System.out.println(temp.getName());
			System.out.println(name);
			System.out.println(categories.size());
			if (temp != null && !(temp.getName() == null)) {
				if (temp.getName().equals(name)) {
					out = temp;
					break;
				}
			}
		}

		return out;
	}

	/**
	 * Removes the calendar data with the given ID
	 * 
	 * @param removeID
	 *            The ID number of the cldr data to be removed
	 */
	public void removeCategory(int removeID) {
		// iterate through the calendar data to find the given ID
		// break the loop once that element has been found and removed
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getId() == removeID) {
				categories.remove(i);
				break;
			}
		}
	}

	/**
	 * Removes all the Category from a model Each Category is removed
	 * individually
	 */
	public void emptyModel() {
		final int oldSize = getSize();
		final Iterator<Category> iterator = categories.iterator();
		// in case the iterator has data, remove each element individually
		// in order to make sure the model is empty
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	/**
	 * Adds the given array of calendar data to the list
	 * 
	 * @param categories
	 *            the array of data to add
	 */
	public void addcategories(Category[] categories) {
		// iterate through the added array, adding each element to
		// the model and assigning each element a unique ID as it is added.
		for (int i = 0; i < categories.length; i++) {
			this.categories.add(categories[i]);
			if (categories[i].getId() >= nextID) {
				nextID = categories[i].getId() + 1;
			}
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	// ******************************************************************
	// Getters for the calendar data

	/**
	 * Provides the number of elements in the list of calendar data for this
	 * project. Elements are returned from the newest to the oldest.
	 * 
	 * @return the number of calendar data in the project
	 */
	public int getSize() {
		return categories.size();
	}

	/**
	 * Provides the next ID number the should be used for the next calendar data
	 * generated.
	 * 
	 * @return the next avail. ID number
	 */
	public int getNextID() {
		return nextID++;
	}

	/**
	 * Takes in an index and find the calendar data in the list for the project.
	 * 
	 * @param index
	 *            The index of the calendar data to be returned
	 * @return the calendar data associated with the given index
	 */
	public Category getElementAt(int index) {
		return categories.get(categories.size() - 1 - index);
	}

	/**
	 * Returns the list of calendar data
	 * 
	 * @return the requirements held within the calendar data model.
	 */
	public List<Category> getAllcategories() {
		return categories;
	}

	/**
	 * Returns the list of calendar data
	 * 
	 * @return the requirements held within the calendar data model.
	 */
	public List<Category> getAllNondeletedCategories() {
		List<Category> categories = new ArrayList<Category>();

		for (Category cat : this.getAllcategories()) {

			if (!cat.isDeleted()) {
				categories.add(cat);
			}
		}
		return categories;
	}

	/**
	 * Returns the list of calendar data
	 * 
	 * @return the requirements held within the calendar data model.
	 */
	public List<Category> getAllNonfilterCategories() {
		List<Category> categories = new ArrayList<Category>();

		for (Category cat : this.getAllcategories()) {

			if (!cat.getHasFilter()) {
				categories.add(cat);
			}
		}
		return categories;
	}

	/**
	 * Get all the categories for the team that the user can access
	 * 
	 * @param userId
	 *            The id of the user attempting to access the categories
	 * @return A list of all team categories the user has access to
	 */
	public List<Category> getTeamCategories(String userId) {
		final List<Category> teamCategories = new ArrayList<Category>();
		Category currentCategory;

		for (int i = 0; i < categories.size(); i++) {

			currentCategory = categories.get(i);
			if (!currentCategory.isDeleted() && currentCategory.isTeamCat()) {
				teamCategories.add(currentCategory);
			}
		}

		return teamCategories;

	}

	/**
	 * Get all the categories that are classified as personal and accessible to
	 * the user.
	 * 
	 * @param userId
	 *            The id of the user attempting to access the categories
	 * @return A list of all personal categories the user has access to
	 */
	public List<Category> getPersonalCategories(String userId) {
		final List<Category> personalCategories = new ArrayList<Category>();
		Category currentCategory;

		for (int i = 0; i < categories.size(); i++) {

			currentCategory = categories.get(i);
			if (!currentCategory.isDeleted() && !currentCategory.isTeamCat()) {
				personalCategories.add(currentCategory);
			}
		}

		return personalCategories;

	}

	// /**
	// * Get all the categories for the team that the user can access
	// * @param userId The id of the user attempting to access the categories
	// * @param year the year to check
	// * @param month the month to check (0-11)
	// * @return A list of all categories the user has access to
	// */
	// public List<Category> getTeamCategories( String userId, int year, int
	// month ) {
	// final List< Category > teamCategories = new ArrayList< Category >();
	// Category currentCategory;

	// for ( int i = 0; i < categories.size(); i++ ) {

	// currentCategory = categories.get( i );
	// if ( !currentCategory.isDeleted() &&
	// currentCategory.isTeamCat()) {
	// teamCategories.add( currentCategory );
	// }
	// }

	// return teamCategories;

	// }

	// /**
	// * Get all the categories for the team that the user can access
	// * @param userId The id of the user attempting to access the categories
	// * @param year the year to check
	// * @param month the month to check (0-11)
	// * @param day the day to check (0-30, depending on month)
	// * @return A list of all categories the user has access to
	// */
	// public List<Category> getTeamCategories( String userId, int year, int
	// month, int day ) {
	// final List< Category > teamCategories = new ArrayList< Category >();
	// Category currentCategory;

	// for ( int i = 0; i < categories.size(); i++ ) {

	// currentCategory = categories.get( i );
	// if ( !currentCategory.isDeleted() &&
	// currentCategory.isTeamCat()) {
	// teamCategories.add( currentCategory );
	// }
	// }

	// return teamCategories;

	// }

	// /**
	// * Get all the categories for the user that the user can access
	// * This includes both team and individual categories
	// * @param userId The id of the user attempting to access the categories
	// * @param year the year to check
	// * @return A list of all categories the user has access to
	// */
	// public List<Category> getuserCategories( String userId, int year ) {
	// final List< Category > userCategories = new ArrayList< Category >();
	// Category currentCategory;

	// for ( int i = 0; i < categories.size(); i++ ) {

	// currentCategory = categories.get( i );
	// if ( !currentCategory.isDeleted()) {
	// userCategories.add( currentCategory );
	// }
	// }

	// return userCategories;

	// }

	// /**
	// * Get all the categories for the user that the user can access
	// * This includes both team and individual categories
	// * @param userId The id of the user attempting to access the categories
	// * @param year the year to check
	// * @param month the month to check (0-11)
	// * @return A list of all categories the user has access to
	// */
	// public List<Category> getuserCategories( String userId, int year, int
	// month ) {
	// final List< Category > userCategories = new ArrayList< Category >();
	// Category currentCategory;

	// for ( int i = 0; i < categories.size(); i++ ) {

	// currentCategory = categories.get( i );
	// if ( !currentCategory.isDeleted()) {
	// userCategories.add( currentCategory );
	// }
	// }

	// return userCategories;

	// }

	/**
	 * Get all the categories for the user that the user can access This
	 * includes both team and individual categories
	 * 
	 * @param userId
	 *            The id of the user attempting to access the categories
	 * @return A list of all categories the user has access to
	 */
	public List<Category> getuserCategories(String userId) {
		final List<Category> userCategories = new ArrayList<Category>();
		Category currentCategory;

		for (int i = 0; i < categories.size(); i++) {

			currentCategory = categories.get(i);
			if (!currentCategory.isDeleted()) {
				userCategories.add(currentCategory);
			}
		}

		return userCategories;

	}

	/**
	 * builds a list of strings of the model's categories' names
	 * 
	 * @return
	 */
	public List<String> getAllCategoryNames() {
		List<String> categoryNames = new ArrayList<String>();

		for (Category cat : this.getAllcategories()) {
			categoryNames.add(cat.getName());
		}
		return categoryNames;
	}

	/**
	 * builds a list of strings of the model's categories' names for nondeleteed
	 * categories
	 * 
	 * @return
	 */
	public List<String> getAllNondeletedCategoryNames() {
		List<String> categoryNames = new ArrayList<String>();

		for (Category cat : this.getAllcategories()) {
			if (!cat.isDeleted()) {
				categoryNames.add(cat.getName());
			}
		}
		return categoryNames;
	}

	/**
	 * Builds A list of categories based on the list of category names given
	 * 
	 * @param categoryNames
	 * @return
	 */
	public List<Category> getCategoriesFromListOfNames(
			List<String> categoryNames) {
		List<Category> categories = new ArrayList<Category>();

		for (String categoryName : categoryNames) {
			categories.add(this.getCategory(categoryName));
		}
		return categories;
	}
}
