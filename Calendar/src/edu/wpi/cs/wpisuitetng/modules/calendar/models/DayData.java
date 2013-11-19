package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.List;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

public class DayData {
	DateInfo dateInfo;
	HalfHourData[] halfHours;
	List<Event> events;

	public DayData(int year, int month, int day) {
		dateInfo = new DateInfo(year, month, day, -1);
		events = new ArrayList<Event>();
		int totalHalfHours = 48;
		// build halfHour array
		for (int i = 0; i < totalHalfHours; i++) {
			halfHours[i] = new HalfHourData(year, month, day, i);
		}
	}

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

	public void removeEvent(Event event) {
		if (this.events.contains(event)) {
			this.events.remove(event);
		}

	}

	public int getDay() {
		return this.dateInfo.getDay();
	}

	public List<Event> getDayEvents(DateInfo dateRegion) {

		return this.events;
	}

}
