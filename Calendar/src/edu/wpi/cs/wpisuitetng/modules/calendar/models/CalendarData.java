/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 * Matt Rafferty
 * Samson
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.Event;
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
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;

/**
 * @author Inferno505
 * @version $Revision: 1.0 $ Main Class for Holding Data for Team and Personal
 *          Calendars Holds identity info, HashNap of Events and Commitments
 *          Organized by day of event Holds master list of categories
 */
public class CalendarData extends AbstractModel implements InterfaceCalendarData{
	private String name;
	private String type;// "personal" or "project"
	private int id;
	// class map of YearData objects
	// Storage Structure for calendars and their events/commitments
	private HashMap<Integer, YearData> dataMap = new HashMap<Integer, YearData>();


	/**
	 * Constructor for CalendarData. Basic constructor for generating a test
	 * CalendarData
	 */
	public CalendarData() {
		this.name = "";
		this.type = "";
		this.id = 0;
		// dataMap = new HashMap<Integer, YearData>();
	}

	/**
	 * Constructor for CalendarData.
	 * 
	 * @param name
	 *            String
	 * @param type
	 *            String
	 * @param id
	 *            int
	 */
	public CalendarData(String name, String type, int id) {
		this.name = name;
		this.type = type;
		this.id = id;
		// dataMap = new HashMap<Integer, YearData>();
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
	public HashMap<Integer, YearData> getDataMap() {
		return this.dataMap;
	}

	/**
	 * 
	 * @return name parameter of this CalendarData
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return type parameter of this CalendarData
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 
	 * @return the id of the CalendarData
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            the CalendarData's new ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	// End Get Functions Database Interaction
	// --------------------------------------------------------------------------------------------------------------------------
	// mapData YearData manipulation Functions
	// --------------------------------------------------------------------------------------------------------------------------
	// <<<<<<< HEAD

	// checks dataMap for YearData for given year
	/**
	 * Method containsYearData. checks for a YearData in given CalendarData's
	 * dataMap for given year integer
	 * 
	 * @param year
	 *            int
	 * @return boolean state of CalendarData dataMap containing YearData for
	 *         year
	 */
	public boolean containsYearData(int year) {
		return ((this.dataMap.containsKey(year) && (this.dataMap.get(year) != null)));
	}

	// re
	//
	// year does not exist
	/**
	 * Method getYearData.
	 * 
	 * @param year
	 *            int
	 * @return YearData returns YearData for given year //TODO and returns
	 *         exception if YearData for given
	 */
	public YearData getYearData(int year) {
		YearData yearData = null;

		if (containsYearData(year)) {
			yearData = (this.dataMap.get(year));
		} else {
			// TODO exception YearData object is not existent.
		}
		return yearData;
	}

	/**
	 * Method getbuildYearData. makes and or gets then returns YearData for
	 * given year
	 * 
	 * @param year
	 *            int
	 * 
	 * @return YearData
	 */
	public YearData getbuildYearData(int year) {
		YearData yearData = null;
		if (containsYearData(year)) {
			yearData = this.getYearData(year);
		} else {
			// build year with gregorian for given int year
			yearData = new YearData(year);
		}
		return yearData;
	}

	/**
	 * Method removeYearData. removes YearData for given year from dataMap
	 * 
	 * @param year
	 *            int
	 */
	public void removeYearData(int year) {
		dataMap.remove(year);
	}

	/**
	 * Method saveYearData. adds given YearData to madData with key being
	 * integer of its year
	 * 
	 * @param year
	 *            int
	 * @param yearData
	 *            YearData
	 */
	public void saveYearData(int year, YearData yearData) {
		this.dataMap.put(year, yearData);
	}

	/**
	 * Method addYearData. adds YearData object to dataMap for given year
	 * integer
	 * 
	 * @param year
	 *            int
	 */
	public void addYearData(int year) {
		YearData yearData = getbuildYearData(year);
		this.removeYearData(year);
		this.saveYearData(year, yearData);
	}

	/**
	 * Method addYearData. adds given YearData to CalendarData map data
	 * 
	 * @param year
	 *            int
	 * @param yearData
	 *            YearData
	 */
	public void addYearData(YearData yearData) {
		int year = yearData.getYear();
		this.removeYearData(year);
		this.saveYearData(year, yearData);
	}

	// mapData YearData manipulation Functions(INPUT AS AN EVENT VERSION)
	// --------------------------------------------------------------------------------------------------------------------------

	/**
	 * Method containsYearDataEvent. checks dataMap for Year data using event's
	 * DateInfo
	 * 
	 * @param event
	 *            Event
	 * 
	 * @return boolean
	 */
	public boolean containsYearDataEvent(Event event) {
		return this.containsYearData(event.getStartYear());
	}

	/**
	 * Method getYearDataEvent. returns YearData for given year returns //TODO
	 * exception if YearData for given year does not exist
	 * 
	 * @param event
	 *            Event
	 * 
	 * @return YearData
	 */
	public YearData getYearDataEvent(Event event) {
		return this.getYearData(event.getStartYear());
	}

	// makes and returns YearData for given year
	/**
	 * Method buildYearDataEvent. makes and or gets YearData for based on
	 * event's DateInfo
	 * 
	 * @param event
	 *            Event
	 * 
	 * @return YearData
	 */
	public YearData getbuildYearDataEvent(Event event) {
		return this.getbuildYearData(event.getStartYear());
	}

	// add YearData object to dataMap
	/**
	 * Method addYearDataEvent. adds YearDate to dataMap based on event's
	 * DateInfo
	 * 
	 * @param event
	 *            Event
	 */
	public void addYearDataEvent(Event event) {
		this.addYearData(event.getStartYear());
	}

	// End mapData YearData manipulation Functions(INPUT AS AN EVENT VERSION)
	// --------------------------------------------------------------------------------------------------------------------------

	// End mapData YearData manipulation Functions
	// ------------------------------------------------------------------------------------------------------------------------------------------------

	// Calendar Event manipulation Functions
	// --------------------------------------------------------------------------------------------------------------------------

	/**
	 * Add an event to the CalendarData in its according day
	 * 
	 * @param event
	 *            the Event to be added
	 */
	// add event accordingly to calendar dataMap
	public void addEvent(Event event) {
		int year = event.getStartYear();
		YearData yearData = this.getbuildYearData(year);
		yearData.addEvent(event);
		this.addYearData(yearData);
	}

	/**
	 * Method removeEvent. removes event from according place in YearData,
	 * updates YearData in dataMap
	 * 
	 * @param event
	 *            Event
	 */
	public void removeEvent(Event event) {
		int year = event.getStartYear();
		YearData yearData = this.getbuildYearData(year);
		yearData.removeEvent(event);
		this.addYearData(yearData);

	}

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
			// determines offset between given day and start of week
			while (refCal.getWeekYear() == weekNum) {
				refCal = new GregorianCalendar(dateRegion.getYear(),
						dateRegion.getMonth(), dateRegion.getDay() - 1);
				offset++;
			}

			refCal = new GregorianCalendar(dateRegion.getYear(),
					dateRegion.getMonth(), dateRegion.getDay() - 1);
			// gets events for all days in week region, starting with earliest
			// day
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

	// =======
	// Pre merge functions
	/**
	 * Checks if the CalendarData contains an instance of YearData for a given
	 * year
	 * 
	 * @param year
	 *            the index of the YearData being checked
	 * @return true if the YearData exists, false if not
	 */
	/*
	 * public boolean containsYearData(int year) { return dataMap.containsKey(
	 * year ); }
	 */
	/**
	 * Gets a YearData object using a year as index if it exists Otherwise,
	 * returns null
	 * 
	 * @param year
	 *            The index of the year being checked
	 * @return A YearData object at the given index, or null if it doesn't exist
	 */
	/*
	 * public YearData getYearData( int year ) { if ( !(containsYearData(year))
	 * ) { //TODO: throw exception return null; }
	 * 
	 * return dataMap.get( year ); }
	 */

	/**
	 * Creates a new YearData object given an integer value for a year
	 * 
	 * @param year
	 *            The integer value of the year
	 * @return the new YearData object
	 */
	/*
	 * private YearData getbuildYearData( int year ) { return new YearData( year
	 * ); }
	 */

	/**
	 * Add a YearData object to the CalendarData object, unless one already
	 * exists
	 * 
	 * @param year
	 *            the index of the YearData object to be added
	 */
	/*
	 * public void addYearData( int year ) { if ( !(containsYearData(year)) ) {
	 * dataMap.put( year, getbuildYearData( year ) ); } }
	 */

	/**
	 * Removes a given year from the Calendar using its year index
	 * 
	 * @param year
	 *            the Year to remove
	 */
	/*
	 * public void removeYearData( int year ) { if ( containsYearData( year ) )
	 * { dataMap.remove( year ); } }
	 */

	// >>>>>>> c4cef2d60ddbacdb606b3899e169561ed6112a5d
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
