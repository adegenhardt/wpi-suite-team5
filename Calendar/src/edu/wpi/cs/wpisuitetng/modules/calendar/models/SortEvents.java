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
	public ArrayList<Event> sortEventsByName(ArrayList<Event> events){
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

	public ArrayList<Event> sortEventsByStartDate(ArrayList<Event> events){
		//Using our beautiful bubble sort, we now sort the events by
		//start date!
		for(int i = 1; i < events.size(); i++){
			for(int j = 0; j < events.size() - i; j++){
				if(((events.get(j).getStartDate().getYear()) > 
				(events.get(j+1).getStartDate().getYear()))){
					Event temp = events.get(j);
					events.set(j, events.get(j+1));
					events.set(j+1, temp);
				} else {
					if(((events.get(j).getStartDate().getMonth()) > 
					(events.get(j+1).getStartDate().getMonth()))){
						Event temp = events.get(j);
						events.set(j, events.get(j+1));
						events.set(j+1, temp);
					} else {
						if(((events.get(j).getStartDate().getDay()) > 
						(events.get(j+1).getStartDate().getDay()))){
							Event temp = events.get(j);
							events.set(j, events.get(j+1));
							events.set(j+1, temp);
						} else {
							if(((events.get(j).getStartDate().getHalfHour()) > 
							(events.get(j+1).getStartDate().getHalfHour()))){
								Event temp = events.get(j);
								events.set(j, events.get(j+1));
								events.set(j+1, temp);
								//If the two start times are the same, sort by the end times
							} else {
								if(((events.get(j).getEndDate().getYear()) > 
								(events.get(j+1).getEndDate().getYear()))){
									Event temp = events.get(j);
									events.set(j, events.get(j+1));
									events.set(j+1, temp);
								} else {
									if(((events.get(j).getEndDate().getMonth()) > 
									(events.get(j+1).getEndDate().getMonth()))){
										Event temp = events.get(j);
										events.set(j, events.get(j+1));
										events.set(j+1, temp);
									} else {
										if(((events.get(j).getEndDate().getDay()) > 
										(events.get(j+1).getEndDate().getDay()))){
											Event temp = events.get(j);
											events.set(j, events.get(j+1));
											events.set(j+1, temp);
										} else {
											if(((events.get(j).getEndDate().getHalfHour()) > 
											(events.get(j+1).getEndDate().getHalfHour()))){
												Event temp = events.get(j);
												events.set(j, events.get(j+1));
												events.set(j+1, temp);
											} else {
												events = sortEventsByName(events);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return events;
	}
}
