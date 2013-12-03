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
 *********************************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.calendar.models.category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller.GetCategoryController;

import javax.swing.AbstractListModel;


/**List of Categories pulled from the server
 * 
 * @author Joe Hill, adapted from EventModel.java
 *
 * @version $Revision: 1.0 $
 */
public class CategoryModel extends AbstractListModel<Category>{
	
	private static final long serialVersionUID = 8555534911453497404L;

	// ********************************************************************
	// Construct the Calendar Model
	
	/**
	 * The list in which all the calendar data for a single project
	 * are contained
	 */
	private final List<Category> categories;
	
	// TODO: Research if and how this is maintained between different instances of the program
	private int nextID; // the next available ID number for the calendar data
						// that are added
	// the static object that allows the calendar data model to be
	private static CategoryModel instance = null;
	
	/**
	 * Constructs an empty list of calendar data
	 * Sets a default ID of 0 to the calendar data
	 */
	private CategoryModel(){
		categories = new ArrayList<Category>();
		nextID = 0;
	}
	
	// **********************************************************************
	// Manipulate calendar data
	
	/**
	 * @return the instance of the calendar data model singleton
	 */
	public static CategoryModel getInstance(){
		if(instance == null){
			instance = new CategoryModel();
		}
		return instance;
	}
	
	/**
	 * Adds a single calendar datum to the data of the project
	 * 
	 * @param newCategory The calendar datum to be added to the list
	 * 						of calendar data in the project
	 */
	public void addCategory(Category newCategory){
		categories.add(newCategory);
	}
	
	/**
	 * Returns the Calendar Data with the given ID
	 * 
	 * @param id The ID number of the calendar data to be returned
	 * 
	
	 * @return the calendar data for the ID, or null if the data is not
	 * 			found. */
	public Category getCategory(int id){
		Category temp = null;
		// iterate through the calendar data in order to find the matching ID
		// break the loop once the ID is found
		for(int i = 0; i < categories.size(); i++){
			temp = categories.get(i);
			if(temp.getId() == id){
				break;
			}
		}
		return temp;
	}
	
	/**
	 * Removes the calendar data with the given ID
	 * 
	 * @param removeID The ID number of the cldr data to be removed
	 */
	public void removeCategory(int removeID){
		// iterate through the calendar data to find the given ID
		// break the loop once that element has been found and removed
		for(int i = 0; i < categories.size(); i++){
			if(categories.get(i).getId() == removeID){
				categories.remove(i);
				break;
			}
		}
	}
		
	/**
	 * Removes all the Category from a model
	 * Each Category is removed individually
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
	 * @param categories the array of data to add
	 */
	public void addcategories(Category[] categories){
		// iterate through the added array, adding each element to 
		// the model and assigning each element a unique ID as it is added.
		for (int i = 0; i < categories.length; i++) {
			this.categories.add(categories[i]);
			if(categories[i].getId() >= nextID){ 
				nextID = categories[i].getId() + 1;
			}
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	// ******************************************************************
	// Getters for the calendar data
	
	/**
	 * Provides the number of elements in the list of calendar data for 
	 * this project. Elements are returned from the newest to the oldest.
	 * 
	 * @return the number of calendar data in the project
	 */
	public int getSize() {
		return categories.size();
	}
	
	/**
	 * Provides the next ID number the should be used for the next 
	 * calendar data generated.
	 * 
	 * @return the next avail. ID number
	 */
	public int getNextID(){
		return nextID++;
	}

	/**
	 * Takes in an index and find the calendar data in the list for the
	 * project. 
	 * 
	 * @param index The index of the calendar data to be returned
	 * @return the calendar data associated with the given index
	 */
	public Category getElementAt(int index) {
		return categories.get(categories.size() - 1 - index);
	}
		
	/**
	 * Returns the list of calendar data
	 * @return the requirements held within the calendar data model. */
	public List<Category> getAllcategories() {
		return categories;
	}
	
	/**
	 * Get all the categories for the team that the user can access
	 * @param userId The id of the user attempting to access the categories
	 * @return A list of all categories the user has access to
	 */
	public List<Category> getTeamCategories( String userId) {
		final List< Category > teamCategories = new ArrayList< Category >();
		Category currentCategory;
		
		for ( int i = 0; i < categories.size(); i++ ) {
			
			currentCategory = categories.get( i );
			if  ( !currentCategory.isDeleted() && 
					currentCategory.isTeamCat()) {
				teamCategories.add( currentCategory );
			}
		}
		
		return teamCategories;
		
	}
	
	/**
	 * Get all the categories for the team that the user can access
	 * @param userId The id of the user attempting to access the categories
	 * @param year the year to check
	 * @param month the month to check (0-11)
	 * @return A list of all categories the user has access to
	 */
	public List<Category> getTeamCategories( String userId, int year, int month) {
		final List< Category > teamCategories = new ArrayList< Category >();
		Category currentCategory;
		
		for ( int i = 0; i < categories.size(); i++ ) {
			
			currentCategory = categories.get( i );
			if  ( !currentCategory.isDeleted() && 
					currentCategory.isTeamCat()) {
				teamCategories.add( currentCategory );
			}
		}
		
		return teamCategories;
		
	}
	
	/**
	 * Get all the categories for the team that the user can access,
	 * and add those categories to a now empty CategoryModel.
	 * @param userId the id of the user attempting to access the categories
	 */
	public void toTeamCategoryModel( String userId ) {
		List< Category > teamCategories = new ArrayList< Category >();
		
		// Gather all of the team categories from the current list of categories
		// contained in the local CategoryModel.
		teamCategories = getTeamCategories( userId );
		
		// Empty the contents of the current version of the local CategoryModel.
		// *This may cause a problem in which the view is confused because there
		// *are now no category objects present in the model while the view is running.
		emptyModel();
		
		// Proceed to add only those categories to the local CategoryModel that
		// are classified as "team categories".
		for ( int i = 0; i < teamCategories.size(); i++ ) {
			categories.add(teamCategories.get ( i ));
		}
		
		// The local CategoryModel is now populated with only team categories.
	}
	
	/**
	 * Get all the categories that the user can access,
	 * and add those categories to a now empty CategoryModel.
	 * Uses the GetCategoryController class and GetCategoryRequestObserver
	 * classes in order to populate the local CategoryModel.
	 */
	public void toPersonalCategoryModel() {
		// Empty the local CategoryModel so that it does not contain
		// any duplicate categories.
		emptyModel();
		
		// Send HTTP request to obtain all categories from server
		// and place them in the local CategoryModel.
		GetCategoryController.getInstance().retrieveCategory();
		
		// The local CategoryModel now possess a collection of all categories.
	}
	
	/**
	 * Get all the categories for the team that the user can access
	 * @param userId The id of the user attempting to access the categories
	 * @param year the year to check
	 * @param month the month to check (0-11)
	 * @param day the day to check (0-30, depending on month)
	 * @return A list of all categories the user has access to
	 */
	public List<Category> getTeamCategories( String userId, int year, int month, int day) {
		final List< Category > teamCategories = new ArrayList< Category >();
		Category currentCategory;
		
		for ( int i = 0; i < categories.size(); i++ ) {
			
			currentCategory = categories.get( i );
			if  ( !currentCategory.isDeleted() && 
					currentCategory.isTeamCat()) {
				teamCategories.add( currentCategory );
			}
		}
		
		return teamCategories;
		
	}
	
	/**
	 * Get all the categories for the user that the user can access
	 * This includes both team and individual categories
	 * @param userId The id of the user attempting to access the categories
	 * @param year the year to check
	 * @return A list of all categories the user has access to
	 */
	public List<Category> getuserCategories( String userId, int year) {
		final List< Category > userCategories = new ArrayList< Category >();
		Category currentCategory;
		
		for ( int i = 0; i < categories.size(); i++ ) {
			
			currentCategory = categories.get( i );
			if  ( !currentCategory.isDeleted()) {
				userCategories.add( currentCategory );
			}
		}
		
		return userCategories;
		
	}
	
	/**
	 * Get all the categories for the user that the user can access
	 * This includes both team and individual categories
	 * @param userId The id of the user attempting to access the categories
	 * @param year the year to check
	 * @param month the month to check (0-11)
	 * @return A list of all categories the user has access to
	 */
	public List<Category> getuserCategories( String userId, int year, int month) {
		final List< Category > userCategories = new ArrayList< Category >();
		Category currentCategory;
		
		for ( int i = 0; i < categories.size(); i++ ) {
			
			currentCategory = categories.get( i );
			if  ( !currentCategory.isDeleted()) {
				userCategories.add( currentCategory );
			}
		}
		
		return userCategories;
		
	}
	
	/**
	 * Get all the categories for the user that the user can access
	 * This includes both team and individual categories
	 * @param userId The id of the user attempting to access the categories
	 * @return A list of all categories the user has access to
	 */
	public List<Category> getuserCategories( String userId) {
		final List< Category > userCategories = new ArrayList< Category >();
		Category currentCategory;
		
		for ( int i = 0; i < categories.size(); i++ ) {
			
			currentCategory = categories.get( i );
			if  ( !currentCategory.isDeleted()) {
				userCategories.add( currentCategory );
			}
		}
		
		return userCategories;
		
	}
	
}
