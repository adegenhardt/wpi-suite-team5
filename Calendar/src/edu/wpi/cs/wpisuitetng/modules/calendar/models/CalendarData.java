package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

public class CalendarData extends AbstractModel {

	// class map of YearData objects
	// Storage Structure for calendars and their events/commitments
	private String name;
	private String type;
	private HashMap<Integer, YearData> dataMap = new HashMap<Integer, YearData>();

	// class constructor
	public CalendarData(String name, String type) {
		this.name = name;
		this.type = type;

	}

	// Required Functions Database Interaction
	// --------------------------------------------------------------------------------------------------------------------------

	/**
	 * Method save.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	public void save() {

	}

	/**
	 * Method delete.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	public void delete() {

	}

	/**
	 * Method toJSON.
	 * 
	 * 
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	/**This returns a Json encoded String representation of this requirement object.
	 * 
	 * @return a Json encoded String representation of this requirement
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, CalendarData.class);
	}

	/**
	 * Method toString.
	 * 
	 * 
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	/**
	 * Method identify.
	 * 
	 * @param o
	 *            Object
	 * 
	 * 
	 * @return Boolean * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns an instance of CalendarData constructed using the given
	 * CalendarData encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded CalendarData to deserialize
	 * 
	 * @return the CalendarData contained in the given JSON
	 */
	public static CalendarData fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarData.class);
	}

	/**
	 * Returns an array of CalendarData parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of CalendarData
	 * 
	 * @return an array of CalendarData deserialized from the given JSON string
	 */
	public static CalendarData[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarData[].class);
	}

	/**
	 * Copies all of the values from the given CalendarData to this
	 * CalendarData.
	 * 
	 * @param toCopyFrom
	 *            the CalendarData to copy from.
	 */
	public void copyFrom(CalendarData toCopyFrom) {
		this.name = toCopyFrom.name;
		this.dataMap = toCopyFrom.dataMap;
		this.type = toCopyFrom.type;
	}

	// End Required Functions Database Interaction
	// --------------------------------------------------------------------------------------------------------------------------

	// Get Functions Database Interaction
	// --------------------------------------------------------------------------------------------------------------------------
	/**
	 * NOte: May not be needed or desired for outside classes to manipulate
	 * dataMap
	 * 
	 * @return dataMap parameter of this CalendarData
	 */
	private HashMap<Integer, YearData> getDataMap() {
		return this.dataMap;
	}

	/**
	 * 
	 * @return name parameter of this CalendarData
	 */
	private String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return type parameter of this CalendarData
	 */
	private String getType() {
		return this.type;
	}

	// End Get Functions Database Interaction
	// --------------------------------------------------------------------------------------------------------------------------
	// mapData YearData manipulation Functions
	// --------------------------------------------------------------------------------------------------------------------------

	// checks dataMap for YearData for given year
	private boolean containsYearData(int year) {
		return ((this.dataMap.containsKey(year) && (this.dataMap.get(year) != null)));
	}

	// returns YearData for given year returns exception if YearData for given
	// year does not exist
	private YearData getYearData(int year) {
		YearData yearData = null;

		if (containsYearData(year)) {
			yearData = (this.dataMap.get(year));
		} else {
			// TODO exception YearData object is not existent.
		}
		return yearData;
	}

	// makes and returns YearData for given year
	private YearData getbuildYearData(int year) {
		YearData yearData = null;
		if (containsYearData(year)) {
			yearData = this.getYearData(year);
		} else {
			// build year with gregorian for given int year
			yearData = new YearData(year);
		}
		return yearData;
	}

	private void removeYearData(int year) {
		dataMap.remove(year);
	}

	//
	private void saveYearData(int year, YearData yearData) {
		// add to map (year,yearData);
		this.dataMap.put(year, yearData);
	}

	// add YearData object to dataMap
	private void addYearData(int year) {
		YearData yearData = getbuildYearData(year);
		this.removeYearData(year);
		this.saveYearData(year, yearData);
	}

	private void addYearData(int year, YearData yearData) {
		this.removeYearData(year);
		this.saveYearData(year, yearData);
	}

	// mapData YearData manipulation Functions(INPUT AS AN EVENT VERSION)
	// --------------------------------------------------------------------------------------------------------------------------

	private boolean containsYearDataEvent(Event event) {
		return this.containsYearData(event.getStartYear());
	}

	// returns YearData for given year returns exception if YearData for given
	// year does not exist
	private YearData getYearDataEvent(Event event) {
		return this.getYearData(event.getStartYear());
	}

	// makes and returns YearData for given year
	private YearData buildYearDataEvent(Event event) {
		return this.getbuildYearData(event.getStartYear());
	}

	// add YearData object to dataMap
	private void addYearDataEvent(Event event) {
		this.addYearData(event.getStartYear());
	}

	// End mapData YearData manipulation Functions(INPUT AS AN EVENT VERSION)
	// --------------------------------------------------------------------------------------------------------------------------

	// End mapData YearData manipulation Functions
	// ------------------------------------------------
	// Calendar Event manipulation Functions
	// --------------------------------------------------------------------------------------------------------------------------
	
	// add event accordingly to calendar dataMap
	public void addEvent(Event event) {
		int year = event.getStartYear();
		YearData yearData = this.getbuildYearData(year);
		yearData.addEvent(event);
		this.addYearData(year, yearData);
	}
public void removeEvent(Event event){
	int year = event.getStartYear();
	YearData yearData = this.getbuildYearData(year);
	yearData.removeEvent(event);
	this.addYearData(year, yearData);
	
}
	// get events spanning view region specified, related to the date of the
	// given DateInfo
	public List<Event> getEventsPerView(String view, DateInfo dateRegion) {

		List<Event> eventList = new ArrayList<Event>();

		// get events spanning year
		if (view.toLowerCase().equals("year")) {
			eventList = (this.getYearData(dateRegion.getYear()))
					.getYearEvents(dateRegion);
		}
		// get events spanning month
		else if (view.toLowerCase().equals("month")) {
			MonthData[] months = (this.getYearData(dateRegion.getYear()))
					.getMonths();
			eventList = months[dateRegion.getMonth()]
					.getMonthEvents(dateRegion);
		}
		// get events spanning week
		else if (view.toLowerCase().equals("week")) {
			MonthData[] months = (this.getYearData(dateRegion.getYear()))
					.getMonths();
			DayData[] days = months[dateRegion.getMonth()].getDays();
			Calendar refCal = new GregorianCalendar(dateRegion.getYear(),
					dateRegion.getMonth(), dateRegion.getDay());
			int weekNum = refCal.getWeekYear();
			int offset = 0;
			refCal = new GregorianCalendar(dateRegion.getYear(),
					dateRegion.getMonth(), dateRegion.getDay() - 1);

			while (refCal.getWeekYear() == weekNum) {
				refCal = new GregorianCalendar(dateRegion.getYear(),
						dateRegion.getMonth(), dateRegion.getDay() - 1);
				offset++;
			}
			refCal = new GregorianCalendar(dateRegion.getYear(),
					dateRegion.getMonth(), dateRegion.getDay() - 1);
			for (int i = 0; i <= 6; i++) {
				eventList.addAll(days[dateRegion.getDay() - offset + i]
						.getDayEvents(dateRegion));
			}

		}
		// get events spanning day
		else if (view.toLowerCase().equals("day")) {
			MonthData[] months = (this.getYearData(dateRegion.getYear()))
					.getMonths();
			DayData[] days = months[dateRegion.getMonth()].getDays();
			eventList = days[dateRegion.getDay()].getDayEvents(dateRegion);

		}
		// return events list
		return eventList;
	}

	// to add
	// Populate list of events per a view no criteria
	// one for criteria
	// add event to calendar (in according place)
	//
	// can: make cal, build year, (get, check for, build, add)
	// need save year con y without data (lu map functions for save)

	// End Calendar Event manipulation Functions
	// --------------------------------------------------------------------------------------------------------------------------

	/*
	 * 
	 * 
	 * 
	 * //adds event to according year private void addEventToYearData(Event
	 * event) { if (!(containsYearData(Event.year))) { YearData yearOfEvent =
	 * buldYearData(Event.year); } else{ YearData yearOfEvent = getYearData
	 * (Event.year); } //functions add event to yearOfEvent YearData //Save
	 * yearOfEvent to dataMap //or save over if alternate function is needed for
	 * saving over existing }
	 */

	// TODO make versions that expect events so other programs do not need to
	// have a line of get functions inside function parameters
	// contained bellow. requires event class implementation
	/*
	 * private boolean containsYearData(Event event) { return false; }
	 * 
	 * private YearData getYearData(Event event) { return null; }
	 * 
	 * private void buildYearData(Event event) { // build year with gregorian
	 * for given int year
	 * 
	 * // add year to dataMap }
	 * 
	 * private void addYearData(Event event){ //YearData yearData =
	 * buildYearData(Event.DateInfo.year); //alter for Event Getters //add
	 * YearData object to dataMap }
	 */

	/*
	 * private void addEvent(Event event){
	 * 
	 * //get event date //check if YearData exists for event.year //build if
	 * needed //thread to year's day as designated by event.date //add event to
	 * day's list of events }
	 */

	// End Additional Functions Database Interaction
	// --------------------------------------------------------------------------------------------------------------------------

	// Additional Functions Database Interaction
	// --------------------------------------------------------------------------------------------------------------------------

	// End Additional Functions Database Interaction
	// --------------------------------------------------------------------------------------------------------------------------

}
