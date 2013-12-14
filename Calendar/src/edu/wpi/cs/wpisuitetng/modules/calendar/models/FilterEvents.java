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


public class FilterEvents {
	/**
	 * This class is solely made for the purpose of filtering events by
	 * a given list of categories. 
	 * @param events A list of events to be filtered
	 * @param cats A list of categories by which the events are filtered
	 * @return The filtered list of events
	 */
	public static List<Event> filterEventsByCategory(List<Event> events,
			List<Category> cats){
		ArrayList<Event> filteredEvents = new ArrayList<Event>();
		for(int i = 0; i < events.size(); i++){
			for(int j = 0; j < cats.size(); j++){
				if (events.get(i).getCategory() == cats.get(j)){
					filteredEvents.add(events.get(i));
				}
			}
		}
		return filteredEvents;
	}	 
}
