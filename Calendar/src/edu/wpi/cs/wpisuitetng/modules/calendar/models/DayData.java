/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 * Matt Rafferty
 * Samson 
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.List;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;

/**
 * @author Inferno505
 * @version $Revision: 1.0 $
 */
public class DayData {
	DateInfo dateInfo; // DateInfo indicating date down to day resolution
	HalfHourData[] halfHours;// array of contained halfHour Objects
	List<Event> events; // List of events taking place on this day

	/**
	 * Constructor for DayData.
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @param day
	 *            int
	 */
	public DayData(int year, int month, int day) {
		dateInfo = new DateInfo(year, month, day, -1);
		events = new ArrayList<Event>();
		
	}

	/**
	 * Add a new Event to the current DayData
	 * 
	 * @param event
	 *            the Event being added
	 */
	public void addEvent(Event event) {
		// adds event to list
		// currently this does not sort the objects for the array
		// that can come later, as the old attempted method was breaking things
		events.add( event );
	}

	/**
	 * Method removeEvent. removes event from DayData's list of events
	 * 
	 * @param event
	 *            Event
	 * 
	 * @return boolean returns T/F to signify success
	 */
	public boolean removeEvent(Event event) {
		// TODO: catch error if someone tries to remove from an empty list

		if (events.size() == 0) {
			return false;
		} else {
			// Remove will catch if the event was stored or not
			return events.remove(event);
		}
	}

	/**
	 * 
	 * @return integer of day for given DayData object
	 */
	public int getDay() {
		return dateInfo.getDay();
	}

	/**
	 * Method getDayEvents.
	 * gets day events, retains dateRegion for access without re build if is needed later
	 * @param dateRegion
	 *            DateInfo
	 * 
	 * @return List<Event> List of events of given DayData
	 */
	public List<Event> getDayEvents(DateInfo dateRegion) {

		return events;
	}
	

	/**
	 * Method getDayEvents.
	 * base implementation, returns events in list
	 * @param dateRegion
	 *            DateInfo
	 * 
	 * @return List<Event> List of events of given DayData
	 */
	public List<Event> getDayEvents() {

		return events;
	}


}
