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
 * @author Matt Rafferty
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

		// build days based on GregorianCalendar system for given month: 0-11 = January-December
		dateInfo = new DateInfo(year, month, -1, -1);
		createDays(year, month);
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
	 private void createDays( int year, int month ) { 
		 int numDays;
		 //number of days in the month
	 
		 // Months with 31 days
	     if ( month == 0 || month == 2 || month == 4 ||
	          month == 6 || month == 7 || month == 9 || month == 11 )
	     {
	    	 numDays = 31;
	     }
	     else if ( month == 1 ) { // February
	    	      if ( year % 4 == 0 && 
	    	    	   ( !( year % 100 == 0 ) ||
	    	    	   ( year % 400 == 0 ) ) ) { //leap year  
	    	    	  //TODO Does this cover leap year rule to the same resolution 
	    	    	  //as the GregorianCalendar class? If so then this is swell
	    	    	         numDays = 29; 
	    	      } 
	    	      else {
	    	    	  numDays = 28;  
	    	      }
	      }
	      else { //Otherwise, month has 30 days
	    	  numDays = 30;
	      }
	  //sets days to be of needed length  // I could have just done List of<DayData>
	  days = new DayData[ numDays ];
	  for ( int i = 0; i < numDays; i++ ) {
	      days[ i ] = new DayData( year, month, i );
	  }
	  
	  }
	 

	/**
	 * 
	 * @return dateInfo of given MonthData
	 */
	public DateInfo getDateInfo() {
		return dateInfo;
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
		return days;
	}
	
	/**
	 * Add an event 
	 * @param event the event to add
	 */
	/*date number indicator is to be absolute 
	 * (1 to last day of month, not 0 to last day of month -1)
	 */
	public void addEvent( Event event ) {
		final int eventDay = event.getStartDate().getDate() - 1;
		
		if ( eventDay >= 0 && eventDay < days.length ) {
			days[ eventDay ].addEvent( event );
		}
		else {
			// TODO exception the event has an invalid day
			//this.days[ eventDay ].addEvent( event ); 
			//TODO why was it set to still add the event if the day is invalid?
		}
		// TODO Indication of event added to month
	}

	/**
	 * Remove an event 
	 * @param event the event to remove
	 */
	public void removeEvent( Event event ) {
		final int eventDay = event.getStartDate().getDate() - 1;
		
		if ( eventDay >= 0 && eventDay < days.length ) {
			days[ eventDay ].removeEvent( event );
		}
		else {
			// TODO exception the event has an invalid day
		}
		// TODO Indication of event added to month
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
		final List<Event> monthEvents = new ArrayList<Event>();

		///calendar with day in region for total month days reference
		final Calendar refCal = new GregorianCalendar(dateRegion.getYear(),
				this.getMonth(), 1);
		final int totalDays = refCal.getActualMaximum(1);
		for (int i = 1; i <= totalDays; i++) {
			dayEvents = days[i].getDayEvents(dateRegion);
			monthEvents.addAll(dayEvents);
		}

		return monthEvents;
	}

}
