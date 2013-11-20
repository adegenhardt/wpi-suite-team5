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
import java.util.Date;
import java.util.List;
import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

/**
 * @author Inferno505
 * 
 * @version $Revision: 1.0 $
 */
public class YearData {
	MonthData[] months; // array of contained MonthDatas
	DateInfo dateInfo; // Date info with resolution to year

	/**
	 * Constructor for YearData.
	 * 
	 * @param year
	 *            int
	 */
	public YearData(int year) {
		// build array of months
		for (int i = 0; i <= 11; i++) {
			months[i] = new MonthData(year, i);
		}

		this.dateInfo = new DateInfo(year, -1, -1, -1);
	}

	/**
	 * 
	 * @return array of contained MonthDatas
	 */
	public MonthData[] getMonths() {
		return this.months;
	}

	//
	/**
	 * Add an event to the YearData in according DayData
	 * 
	 * @param event
	 *            the Event to be added
	 */
	public void addEvent(Event event) {

		DateInfo eventStoreDateInfo = event.convertParametersToDateInfo();
		// this.months[ eventStoreDateInfo.getMonth()].addEvent( event,
		// eventStoreDateInfo );

		boolean monthFound = false;
		for (int i = 0; i <= 11; i++) {
			if (this.months[i].getMonth() == eventStoreDateInfo.getMonth()) {
				monthFound = true;
				this.months[i].addEvent(event, eventStoreDateInfo);
				break;
			}

		}
		if (monthFound == false) {
			// TODO exception the event has an invalid month
		}
		// TODO Indication of event added to year
	}

	/**
	 * Method removeEvent. Removes event from according DayData
	 * 
	 * @param event
	 *            Event
	 */
	public void removeEvent(Event event) {
		DateInfo eventStoreDateInfo = event.convertParametersToDateInfo();
		boolean monthFound = false;
		for (int i = 0; i <= 11; i++) {
			if (this.months[i].getMonth() == eventStoreDateInfo.getMonth()) {
				monthFound = true;
				this.months[i].removeEvent(event, eventStoreDateInfo);
				break;
			}

		}
		if (monthFound == false) {
			// TODO exception the event has an invalid month
		}
		// TODO Indication of event added to year

	}

	/**
	 * Method getYearEvents. gets list of events contained in given MonthData
	 * 
	 * @param dateRegion
	 *            DateInfo
	 * 
	 * @return List<Event>
	 */
	public List<Event> getYearEvents(DateInfo dateRegion) {

		List<Event> monthEvents = new ArrayList<Event>();
		List<Event> yearEvents = new ArrayList<Event>();
		for (int i = 0; i <= 11; i++) {
			monthEvents = months[i].getMonthEvents(dateRegion);
			yearEvents.addAll(monthEvents);
			/*
			 * monthEventsAray = monthEvents.toArray( monthEventsAray); for(int
			 * j = 0; j< monthEvents.size(); j++){
			 * yearEvents.add(monthEventsAray[j]); }
			 */

		}

		return yearEvents;
	}

	/**
	 * Obtains this YearData's MonthData at a given index
	 * 
	 * @param index
	 *            The index from 0-11 for the month
	 * 
	 * 
	 * @return The month at a given index * @throws
	 *         ArrayIndexOutOfBoundsException * @throws
	 *         ArrayIndexOutOfBoundsException
	 */
	public MonthData getMonth(int index) throws ArrayIndexOutOfBoundsException {

		if (index < 0 || index >= 12) {
			throw new ArrayIndexOutOfBoundsException();
		}

		return months[index];

	}

	public int getYear() {

		return this.dateInfo.getYear();
	}

	// pre merge functions

	/**
	 * Create the months of a given year
	 * 
	 * @param year
	 *            the Year having its months created
	 */
	/*
	 * private void createMonths( int year ) {
	 * 
	 * for ( int i = 0; i < 12; i++ ) { months[ i ] = new MonthData( year, i );
	 * }
	 * 
	 * }
	 */

}
