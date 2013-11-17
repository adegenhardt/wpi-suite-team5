package edu.wpi.cs.wpisuitetng.modules.calendar.models;

//import java.util.LinkedLists ??

public class DayData {
	DateInfo dateInfo;

	// List<Event> events;

	public DayData(int year, int month, int day) {
		dateInfo = new DateInfo(year, month, day, -1);
		// events = new List<Events>
	}
}
