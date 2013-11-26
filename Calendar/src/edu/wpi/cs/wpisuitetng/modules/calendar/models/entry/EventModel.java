package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

/*********************************************************************************************
 * Copyright (c) 2013 WPI Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * List of Event pulled from the server
 * 
 * Adapted from RequirementModel.java by Team Underscore Database Crew
 *
 *********************************************************************************************/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.controllers.AddEventController;


/**List of Calendars pulled from the server
 * 
 * @author cporell, adapted from RequirementModel.java
 *
 * @version $Revision: 1.0 $
 */


public class EventModel extends AbstractListModel{

	// ********************************************************************
	// Construct the Calendar Model
	
	/**
	 * The list in which all the calendar data for a single project
	 * are contained
	 */
	private List<Event> events;
	
	// TODO: Research if and how this is maintained between different instances of the program
	private int nextID; // the next available ID number for the calendar data
						// that are added
	// the static object that allows the calendar data model to be
	private static EventModel instance;
	
	/**
	 * Constructs an empty list of calendar data
	 * Sets a default ID of 0 to the calendar data
	 */
	private EventModel(){
		events = new ArrayList<Event>();
		nextID = 0;
	}
	
	// **********************************************************************
	// Manipulate calendar data
	
	/**
	 * @return the instance of the calendar data model singleton
	 */
	public static EventModel getInstance(){
		if(instance == null){
			instance = new EventModel();
		}
		return instance;
	}
	
	/**
	 * Adds a single calendar datum to the data of the project
	 * 
	 * @param newEvent The calendar datum to be added to the list
	 * 						of calendar data in the project
	 */
	public void addEvent(Event newEvent){
		events.add(newEvent);
		
		this.fireIntervalAdded(this, 0, 0);
	}
	
	/**
	 * Returns the Calendar Data with the given ID
	 * 
	 * @param id The ID number of the calendar data to be returned
	 * 
	
	 * @return the calendar data for the ID, or null if the data is not
	 * 			found. */
	public Event getEvent(int id){
		Event temp = null;
		// iterate through the calendar data in order to find the matching ID
		// break the loop once the ID is found
		for(int i = 0; i < this.events.size(); i++){
			temp = events.get(i);
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
	public void removeEvent(int removeID){
		// iterate through the calendar data to find the given ID
		// break the loop once that element has been found and removed
		for(int i = 0; i < this.events.size(); i++){
			if(events.get(i).getId() == removeID){
				events.remove(i);
				break;
			}
		}
		try{
//			ViewEventController.getInstance().refreshTable();
//			ViewEventController.getInstance().refreshTree();
		}
		catch(Exception e){}
	}
		
	/**
	 * Removes all the event from a model
	 * Each event is removed individually
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Event> iterator = events.iterator();
		// in case the iterator has data, remove each element individually
		// in order to make sure the model is empty
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		try{
//			ViewEventController.getInstance().refreshTable();
//			ViewEventController.getInstance().refreshTree();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Adds the given array of calendar data to the list
	 * @param events the array of data to add
	 */
	public void addEvents(Event[] events){
		// iterate through the added array, adding each element to 
		// the model and assigning each element a unique ID as it is added.
		for (int i = 0; i < events.length; i++) {
			this.events.add(events[i]);
			if(events[i].getId() >= nextID){ 
				nextID = events[i].getId() + 1;
			}
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
//		ViewEventController.getInstance().refreshTable();
//		ViewEventController.getInstance().refreshTree();
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
		return events.size();
	}
	
	/**
	 * Provides the next ID number the should be used for the next 
	 * calendar data generated.
	 * 
	 * @return the next avail. ID number
	 */
	public int getNextID(){
		return this.nextID++;
	}

	/**
	 * Takes in an index and find the calendar data in the list for the
	 * project. 
	 * 
	 * @param index The index of the calendar data to be returned
	 * @return the calendar data associated with the given index
	 */
	public Object getElementAt(int index) {
		return events.get(events.size() - 1 - index);
	}
		
	/**
	 * Returns the list of calendar data
	 * @return the requirements held within the calendar data model. */
	public List<Event> getAllEvents() {
		return events;
	}
	
	/**
	 * Get all the events for the team that the user can access
	 * @param userId The id of the user attempting to access the events
	 * @param year the year to check
	 * @return A list of all events the user has access to
	 */
	public List<Event> getTeamEvents( String userId, int year) {
		ArrayList< Event > teamEvents = new ArrayList< Event >();
		Event currentEvent;
		
		for ( int i = 0; i < events.size(); i++ ) {
			
			currentEvent = events.get( i );
			if  ( !currentEvent.isDeleted() && 
					currentEvent.isTeamEvent() &&
					currentEvent.hasAccess( userId ) &&
					currentEvent.occursOnYear( year ) ) {
				teamEvents.add( currentEvent );
			}
		}
		
		return teamEvents;
		
	}
	
	/**
	 * Get all the events for the team that the user can access
	 * @param userId The id of the user attempting to access the events
	 * @param year the year to check
	 * @param month the month to check (0-11)
	 * @return A list of all events the user has access to
	 */
	public List<Event> getTeamEvents( String userId, int year, int month) {
		ArrayList< Event > teamEvents = new ArrayList< Event >();
		Event currentEvent;
		
		for ( int i = 0; i < events.size(); i++ ) {
			
			currentEvent = events.get( i );
			if  ( !currentEvent.isDeleted() && 
					currentEvent.isTeamEvent() &&
					currentEvent.hasAccess( userId ) &&
					currentEvent.occursOnMonth( year, month ) ) {
				teamEvents.add( currentEvent );
			}
		}
		
		return teamEvents;
		
	}
	
	/**
	 * Get all the events for the team that the user can access
	 * @param userId The id of the user attempting to access the events
	 * @param year the year to check
	 * @param month the month to check (0-11)
	 * @param
	 * @return A list of all events the user has access to
	 */
	public List<Event> getTeamEvents( String userId, int year, int month, int day) {
		ArrayList< Event > teamEvents = new ArrayList< Event >();
		Event currentEvent;
		
		for ( int i = 0; i < events.size(); i++ ) {
			
			currentEvent = events.get( i );
			if  ( !currentEvent.isDeleted() && 
					currentEvent.isTeamEvent() &&
					currentEvent.hasAccess( userId ) &&
					currentEvent.occursOnDate( year, month, day ) ) {
				teamEvents.add( currentEvent );
			}
		}
		
		return teamEvents;
		
	}
	
	/**
	 * Get all the events for the user that the user can access
	 * This includes both team and individual events
	 * @param userId The id of the user attempting to access the events
	 * @param year the year to check
	 * @return A list of all events the user has access to
	 */
	public List<Event> getUserEvents( String userId, int year) {
		ArrayList< Event > userEvents = new ArrayList< Event >();
		Event currentEvent;
		
		for ( int i = 0; i < events.size(); i++ ) {
			
			currentEvent = events.get( i );
			if  ( !currentEvent.isDeleted() &&
					currentEvent.hasAccess( userId ) &&
					currentEvent.occursOnYear( year ) ) {
				userEvents.add( currentEvent );
			}
		}
		
		return userEvents;
		
	}
	
	/**
	 * Get all the events for the user that the user can access
	 * This includes both team and individual events
	 * @param userId The id of the user attempting to access the events
	 * @param year the year to check
	 * @param month the month to check (0-11)
	 * @return A list of all events the user has access to
	 */
	public List<Event> getUserEvents( String userId, int year, int month) {
		ArrayList< Event > userEvents = new ArrayList< Event >();
		Event currentEvent;
		
		for ( int i = 0; i < events.size(); i++ ) {
			
			currentEvent = events.get( i );
			if  ( !currentEvent.isDeleted() &&
					currentEvent.hasAccess( userId ) &&
					currentEvent.occursOnMonth( year, month ) ) {
				userEvents.add( currentEvent );
			}
		}
		
		return userEvents;
		
	}
	
	/**
	 * Get all the events for the user that the user can access
	 * This includes both team and individual events
	 * @param userId The id of the user attempting to access the events
	 * @param year the year to check
	 * @param month the month to check (0-11)
	 * @param
	 * @return A list of all events the user has access to
	 */
	public List<Event> getUserEvents( String userId, int year, int month, int day) {
		ArrayList< Event > userEvents = new ArrayList< Event >();
		Event currentEvent;
		
		for ( int i = 0; i < events.size(); i++ ) {
			
			currentEvent = events.get( i );
			if  ( !currentEvent.isDeleted() &&
					currentEvent.hasAccess( userId ) &&
					currentEvent.occursOnDate( year, month, day ) ) {
				userEvents.add( currentEvent );
			}
		}
		
		return userEvents;
		
	}
	
}
