/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Sorts the events of a given Day by start time.
 * When it is fully implemented, feel free to move it wherever you like.
 * 
 * @author Connor Porell
 * 
 * Contributors: Team _
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.List;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;

/**
 * @param events A list of Events for the given block of time
 * @return The sorted list of events
 */
public class SortEvents {
	// This method is solely for sorting events.
	// When it is fully implemented, you can move it wherever you like.
	/**
	 * Sorts an ArrayList of Events by name.
	 * This is meant as a sort of last resort, as it is only called
	 * when the start and end times for two events are the same.
	 * @param events Events that have been checked for sorting by date
	 * @return An array list of events that have been sorted alphabetically
	 */
	public static List<Event> sortEventsByName(List<Event> events){
		for(int i = 1; i < events.size(); i++){
			for(int j = 0; j < events.size() - i; j++){
				if(((events.get(j).getName())
						.compareTo(events.get(j+1).getName()) > 0) ){
					Event temp = events.get(j);
					events.set(j, events.get(j+1));
					events.set(j+1, temp);
				}
			}
		}
		return events;
	}

	/**
	 * @param events list of events to sort
	 * @return sorted list
	 */
	public static List<Event> sortEventsByDate(List<Event> events){
		//Using our beautiful bubble sort, we now sort the events by
		//start date!
		
		boolean stillActive = true;
		while(stillActive) {
			stillActive = false;
			for(int i=0; i <= events.size()-2; i++) {
				Event temp = events.get(i);
				if (temp.getStartDate().compareTo(events.get(i+1).getStartDate()) > 0) {
					events.set(i, events.get(i+1));
					events.set(i+1, temp);
					stillActive = true;
				}
				else if (temp.getStartDate().compareTo(events.get(i+1).getStartDate()) == 0) {
					if (temp.getEndDate().compareTo(events.get(i+1).getEndDate()) > 0) {
						events.set(i, events.get(i+1));
						events.set(i+1, temp);
						stillActive = true;
					}
				}
			}
		}
		return events;
	}
}
