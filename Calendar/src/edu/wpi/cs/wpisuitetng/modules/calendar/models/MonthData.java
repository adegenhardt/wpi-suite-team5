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

import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @authors Inferno505
 * @version $Revision: 1.0 $
 */
public class MonthData {
	// parameter for moth's date date ie this object is March of year 2082
	DateInfo dateInfo; // date down to resolution of month
	DayData[] days; // array of contained day objects

	/**
	 * Constructor for MonthData.
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 */
	public MonthData(int year, int month) {

		// build array of months
		// make calendar in given month to get number of days in month
		Calendar refCal = new GregorianCalendar(this.getDateInfo().getYear(),
				this.getMonth(), 1);
		int totalDays = refCal.getActualMaximum(1);
		for (int i = 0; i < totalDays; i++) {
			days[i] = new DayData(year, month, i);
		}

		// build days based on gregorean for given month 0-11 = January-December

		// TODO: Add error handling if the month is out of range [1, 12]

		this.dateInfo = new DateInfo(year, month, -1, -1);
		//createDays(year, month);
	}

	/**
	 * Create the days of the month
	 * 
	 * @param year
	 *            The year containing the month
	 * @param month
	 *            The number value of the month itself
	 */

	/**
	 * Manual Method of Days in Month Determination
	 * 
	 * @param year
	 * @param month
	 */
	/*
	 * private void createDays( int year, int month ) { int numDays; //number of
	 * days in the month
	 * 
	 * // Months with 31 days if ( month == 0 || month == 2 || month == 4 ||
	 * month == 6 || month == 7 || month == 9 || month == 11 ) { numDays = 31; }
	 * else if ( month == 1 ) { // February if ( year % 4 == 0 && ( !( year %
	 * 100 == 0 ) || ( year % 400 == 0 ) ) ) { //leap year numDays = 29; } else
	 * { numDays = 28; } } else { //Otherwise, month has 30 days numDays = 30; }
	 * 
	 * days = new DayData[ numDays ]; for ( int i = 0; i < numDays; i++ ) {
	 * days[ i ] = new DayData( year, month, i ); }
	 * 
	 * }
	 */

	/**
	 * 
	 * @return dateInfo of given MonthData
	 */
	public DateInfo getDateInfo() {
		return this.dateInfo;
	}

	/**
	 * 
	 * @return integer month of given MonthData
	 */
	public int getMonth() {
		return dateInfo.getMonth();

	}

	/**
	 * 
	 * @return the DayDatas of the month
	 */
	public DayData[] getDays() {
		return this.days;
	}

	//

	/**
	 * Add an event to the MonthData in according day
	 * 
	 * @param event
	 *            the Event to be added
	 * @param eventStoreDateInfo
	 *            DateInfo
	 */
	public void addEvent(Event event, DateInfo eventStoreDateInfo) {
//calendar with day in region for total month days reference
		Calendar refCal = new GregorianCalendar(this.getDateInfo().getYear(),
				this.getMonth(), 1);
		int totalDays = refCal.getActualMaximum(1);
		boolean dayFound = false;
		for (int i = 0; i <= totalDays; i++) {
			if (this.days[i].getDay() == eventStoreDateInfo.getDay()) {
				dayFound = true;
				this.days[i].addEvent(event);
				break;
			}

		}
		if (dayFound == false) {
			// TODO exception the event has an invalid day
		}
		// TODO Indication of event added to year

	}

	/**
	 * Method removeEvent.
	 * removes and event from its according DayData
	 * @param event
	 *            Event
	 * @param eventStoreDateInfo
	 *            DateInfo
	 */
	public void removeEvent(Event event, DateInfo eventStoreDateInfo) {
		//calendar with day in region for total month days reference
		Calendar refCal = new GregorianCalendar(this.getDateInfo().getYear(),
				this.getMonth(), 1);
		int totalDays = refCal.getActualMaximum(1);
		boolean dayFound = false;
		for (int i = 0; i <= totalDays; i++) {
			if (this.days[i].getDay() == eventStoreDateInfo.getDay()) {
				dayFound = true;
				this.days[i].removeEvent(event);
				break;
			}

		}
		if (dayFound == false) {
			// TODO exception the event has an invalid day
		}
		// TODO Indication of event added to year

	}

	/**
	 * Method getMonthEvents.
	 * get all the events in a list of events for the given month
	 * @param dateRegion
	 *            DateInfo
	 * 
	 * @return List<Event>
	 */
	public List<Event> getMonthEvents(DateInfo dateRegion) {

		List<Event> dayEvents = new ArrayList<Event>();
		List<Event> monthEvents = new ArrayList<Event>();

		///calendar with day in region for total month days reference
		Calendar refCal = new GregorianCalendar(dateRegion.getYear(),
				this.getMonth(), 1);
		int totalDays = refCal.getActualMaximum(1);
		for (int i = 1; i <= totalDays; i++) {
			dayEvents = days[i].getDayEvents(dateRegion);
			monthEvents.addAll(dayEvents);
		}

		return monthEvents;
	}

}
