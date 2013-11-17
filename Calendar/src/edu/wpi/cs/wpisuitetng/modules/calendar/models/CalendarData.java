package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

//import HashMap
import java.util.HashMap;

//import gson
import com.google.gson.Gson;

public class CalendarData extends AbstractModel {

	// class map of YearData objects
	// Storage Structure for calendars and their events/commitments
	private HashMap<Integer, YearData> dataMap = new HashMap<Integer, YearData>();
	private String name;
	private String type;

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

	
	
	// Additional Functions Database Interaction
	// --------------------------------------------------------------------------------------------------------------------------
	// checks dataMap for YearData for given year
	private boolean containsYearData(int year) {
		return false;
	}

	// returns YearData for given year returns exception if YearData for given
	// year does not exist
	private YearData getYearData(int year) {
		return null;
	}

	// makes and returns YearData for given year
	private YearData buildYearData(int year) {
		// build year with gregorian for given int year
		return null;

	}

	// add YearData object to dataMap
	private void addYearData(int year) {
		if (!(containsYearData(year))) {
			YearData yearData = buildYearData(year);
		} else {
			YearData yearData = getYearData(year);
		}

	}

	/*
	 * 
	 * TODO requires event implementation
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

}
