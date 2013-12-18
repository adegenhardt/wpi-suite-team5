/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Filters events by a list of Categories
 * @author Connor Porell
 * 
 * Contributors: Team Underscore 
 *    
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;


/**
 * 
 * @author Team _
 * 
 * @version $Revision: 1.0 $
 */
public class EventFilter {
	/**
	 * When given a list of events and a list of categories, filters the events
	 * so that only events whose categories matches the input categories are
	 * returned.
	 * @param events A list of events to be filtered
	 * @param cats A list of categories by which the events are filtered
	
	 * @return The filtered list of events */
	public static List<Event> filterEventsByCategory(List<Event> events,
			List<Category> cats){
		//If there is an empty event list, return the list right away
		if (events.size() == 0){
			return events;
		}
		//If there is an empty category list, return the list posthaste
		if (cats.size() == 0){
			return events;
		} else {
			//If both the category list and the event list have contents,
			//we apply the filters as necessary
			final List<Event> filteredEvents = new ArrayList<Event>();
			for(int i = 0; i < events.size(); i++){
				for(int j = 0; j < cats.size(); j++){
					//Check if the current event matches the current category
					//If so, adds the event to the filteredEvents list
					//Otherwise, ignores the event and continues iterating
					if (events.get(i).getCategory() == cats.get(j).getId()){
						filteredEvents.add(events.get(i));
					}
				}
			}
			//Return the list of events that match the filters
			return filteredEvents;
		}
	}
}
