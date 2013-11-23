package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;

public interface InterfaceCalendarData {

	/**
	 * 
	 * @return name parameter of this CalendarData
	 */
	public String getName();

	/**
	 * 
	 * @return type parameter of this CalendarData
	 */
	public String getType();

	/**
	 * 
	 * @return the id of the CalendarData
	 */
	public int getId();

	/**
	 * 
	 * @param id
	 *            the CalendarData's new ID
	 */
	public void setId(int id);

	/**
	 * Add an event to the CalendarData in its according day
	 * 
	 * @param event
	 *            the Event to be added
	 */
	// add event accordingly to calendar dataMap
	public void addEvent(Event event);

	/**
	 * Method removeEvent. removes event from according place in YearData,
	 * updates YearData in dataMap
	 * 
	 * @param event
	 *            Event
	 */
	public void removeEvent(Event event);

	/**
	 * Method getEventsPerView. get events spanning view region specified,
	 * related to the view string and date of the given DateInfo
	 * 
	 * @param view
	 *            String Accepts "year" "month" "week" "day"
	 * @param dateRegion
	 *            DateInfo DateInfo for a day inside the region desired to get
	 *            events from
	 * 
	 * @return List<Event>
	 */
	public List<Event> getEventsPerView(String view, DateInfo dateRegion);

}
