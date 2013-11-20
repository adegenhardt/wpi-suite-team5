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

import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

/**
 * @authors Inferno505
 * @version $Revision: 1.0 $
 */
public class DayData {
	DateInfo dateInfo; // DateInfo indicating date down to day resolution
	HalfHourData[] halfHours;// array of contained halfHour Objects
	ArrayList<Event> events; // List of events taking place on this day

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

		// build halfHour array
		int totalHalfHours = 48;
		for (int i = 0; i < totalHalfHours; i++) {
			halfHours[i] = new HalfHourData(year, month, day, i);
		}
	}

	/**
	 * Add a new Event to the current DayData
	 * 
	 * @param event
	 *            the Event being added
	 */
	public void addEvent(Event event) {
		// adds event to list in slot to establish order of event
		// occurrence(index 0 is earliest)
		// does not correspond to actual halfHour it takes place at because
		// indexes can not be shared
		// newest event will be lowest index in a set of events at the same
		// halfHour
		// order != event.getStartHalfHour order is relative occurrence.
		this.events.add(event.getStartHalfHour(), event);
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
		boolean output = false;
		// TODO: catch error if someone tries to remove from an empty list

		if (events.size() == 0) {
			output = false;
		} else if (this.events.contains(event)) {
			output = this.events.remove(event);
		}
		return output;
	}

	/**
	 * 
	 * @return integer of day for given DayData object
	 */
	public int getDay() {
		return this.dateInfo.getDay();
	}

	/**
	 * Method getDayEvents.
	 * gets day events, retains dateRegion for access without re build if is needed later
	 * @param dateRegion
	 *            DateInfo
	 * 
	 * @return List<Event> List of events of given DayData
	 */
	public ArrayList<Event> getDayEvents(DateInfo dateRegion) {

		return this.events;
	}
	

	/**
	 * Method getDayEvents.
	 * base implementation, returns events in list
	 * @param dateRegion
	 *            DateInfo
	 * 
	 * @return List<Event> List of events of given DayData
	 */
	public ArrayList<Event> getDayEvents() {

		return this.events;
	}


}
