package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.calendar.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

public class DayData {
	DateInfo dateInfo;

	ArrayList<Event> events;

	public DayData(int year, int month, int day) {
		dateInfo = new DateInfo(year, month, day, -1);
		events = new ArrayList<Event>();
		
	}
	
	/**
	 * 
	 * @return The list of events included in DayData
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}
	
	/**
	 * Add a new Event to the current DayData
	 * @param event the Event being added
	 */
	public void addEvent( Event event ) {
		events.add( event );
	}

	/**
	 * Remove an Event from the current DayData
	 * @param event the event to be removed
	 * @return true if the event was removed, false otherwise
	 */
	public boolean removeEvent( Event event ) {
		//TODO: catch error if someone tries to remove from an empty list
		if ( events.size() == 0 ) {
			return false;
		}
		return events.remove( event );
	}
	
}
