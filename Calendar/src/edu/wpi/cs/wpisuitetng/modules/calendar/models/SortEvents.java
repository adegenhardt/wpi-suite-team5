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

import java.util.ArrayList;
import java.util.List;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;

/**
 * @param events A list of Events for the given block of time
 * @return The sorted list of events
 */
public class SortEvents {
	// This method is solely for sorting events.
	// When it is fully implemented
	public ArrayList<Event> sortEvents(ArrayList<Event> events){
		//First sort the events in alphabetical order,
		//using a wonderfully efficient bubble sort! :)		
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
}
