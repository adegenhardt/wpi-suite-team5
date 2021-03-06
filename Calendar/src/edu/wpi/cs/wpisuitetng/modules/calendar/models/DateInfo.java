/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * DateInfo is a form of storing time information. It contains a year, a month,
 * a day, and a half hour.
 * 
 * @author Team Underscore
 * @version $Revision: 1.0 $
 */
public class DateInfo {
	// Format:
	// Year - absolute value
	private int year;
	// Month - 0 = January to 11 = December
	private int month;
	// Day - O based 0 = day 1 to last day -1 = last day of month
	private int day;
	// HalfHour - 0 based (0 to 47)
	private int halfHour;

	/**
	 * Constructor for DateInfo.
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @param day
	 *            int
	 * @param halfHour
	 *            int
	 */
	public DateInfo(int year, int month, int day, int halfHour) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.halfHour = halfHour;
	}

	/**
	 * Construct a DateInfo object from a Calendar Object
	 * @param cal
	 */
	public DateInfo( Calendar cal ) {
		year = cal.get( Calendar.YEAR );
		month = cal.get( Calendar.MONTH );
		day = cal.get( Calendar.DATE ) - 1;
		halfHour = ( 2 * cal.get( Calendar.HOUR_OF_DAY ) ) +
				( cal.get( Calendar.MINUTE ) / 30 );
	}
	
	/**
	 * Construct a DateInfo from a Java Date class Object
	 * 
	 * @param date
	 *            An instance of Date with required information
	 */
	@SuppressWarnings("deprecation")
	public DateInfo(Date date) {
		year = date.getYear();
		month = date.getMonth();
		day = date.getDate() - 1;
		halfHour = (date.getHours() * 2) + (date.getMinutes() / 30);
	}

	/**
	 * 
	 * @return year parameter
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 
	 * @return month parameter
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * 
	 * @return day parameter
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 
	 * @return halfHour parameter
	 */
	public int getHalfHour() {
		return halfHour;
	}

	
	/**
	 * 
	 * @param year the DateInfo's year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * 
	 * @param month the DateInfo's month from 0-11
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * 
	 * @param day the DateInfo's day from 0-28
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * 
	 * @param halfHour the DateInfo's half-hour from 0-47
	 */
	public void setHalfHour(int halfHour) {
		this.halfHour = halfHour;
	}

	/**
	 * Converts a Given DateInfo into a DateInfo of the parameters of the given
	 * Java calendar Date Used to maintain compatibility with an old version of
	 * the program
	 * 
	 * @param date
	 *            The date in its original format
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void convertToDateInfo(Date date) {
		final int yearN = date.getYear() + 1900;
		final int monthN = date.getMonth();
		final int dayN = date.getDate() - 1;
		final int halfHourN = (date.getHours() * 2) + (date.getMinutes() / 30);
		year = yearN;
		month = monthN;
		day = dayN;
		halfHour = halfHourN;

	}

	/**
	 * Converts the given DateInfo to a Calendar Object of the same parameters
	 * 
	 * @return Calendar object
	 */
	public Calendar dateInfoToCalendar() {
		final Calendar cal = new GregorianCalendar();
		final int hourOfDay = ((halfHour / 2));
		int minute = 0;
		if (halfHour % 2 != 0) {
			minute = 30;
		}
		// added plus 1 to day for if now use 0 base
		cal.set(year, month, day + 1, hourOfDay, minute);
		return cal;
	}

	
	/**
	 * Method convertDateInfoToDate. converts a DateInfo object to a Date object
	 * of the same parameters
	 * 
	 * @return Date
	 */
	@Deprecated
	public Date convertDateInfoToDate() {
		final Date date = new Date(year - 1900, month, day + 1);
		return date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + halfHour;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DateInfo other = (DateInfo) obj;
		if (day != other.day) {
			return false;
		}
		if (halfHour != other.halfHour) {
			return false;
		}
		if (month != other.month) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns a JSON-encoded String of this DateInfo
	 */
	@Override
	public String toString() {
		final String theYear = "" + year;
		final int theMonthI = month + 1;
		// Initialize a blank Month string
		String theMonthS = "";
		// Assign an MM Month String depending on the value
		// of the month int.
		theMonthS += theMonthI;
		final String theDay = "" + (day + 1);
		// Deliver a time depending on the halfHour int
		int iTime = 0;
		String sTime = "";

		int theTime = halfHour;
		while (theTime > 1){
			theTime -= 2;
			iTime++;
		}
		
		if (iTime > 12){
			iTime -= 12;
			
			sTime = "" + iTime;
			
			if (theTime == 0){
				sTime += ":00";
			}
			else{
				sTime += ":30";
			}
			
			sTime += " PM";
		}
		else{
			
			final int tempTime = iTime;
			
			// If midnight...
			if ( iTime == 0 ) {
				iTime = 12;
			}
			
			sTime = "" + iTime;
			
			if (theTime == 0){
				sTime += ":00";
			}
			else{
				sTime += ":30";
			}
			
			if (tempTime == 12) {
				sTime += " PM";
			}
			else {
				sTime += " AM";
			}
		}
		// Collect all the info gathered above into a single string
		// and return it
		final String theDateInfo = "" + sTime + ", " + theMonthS + "/" + theDay + "/" + theYear;

		return theDateInfo;
	}

	/**
	 * Compare two DateInfo objects
	 * @param other the other DateInfo object
	 * @return -1 if this DateInfo is earlier than other,
	 * 1 if this DateInfo is after the other,
	 * or 0 if both are the same
	 */
	public int compareTo( DateInfo other ) {
		
		// Start by comparing years
		if ( year < other.year ) {
			return -1;
		} else if ( year > other.year ) {
			return 1;
		}
		
		// If years are equal, look at months
		if ( month < other.month ) {
			return -1;
		} else if ( month > other.month ) {
			return 1;
		}
		
		// The move on to days...
		if ( day < other.day ) {
			return -1;
		} else if ( day > other.day ) {
			return 1;
		}
		
		// And finally half-hours
		if ( halfHour < other.halfHour ) {
			return -1;
		} else if ( halfHour > other.halfHour ) {
			return 1;
		}
		
		// If nothing has returned by now, then both are the same
		return 0;
	}
	
	/**
	 * 
	 * @return An array of times where the 0th position is the hour and the 1st position is the 
	 * half hour
	 */
	public int[] getTime(){
		int timeRemaining = halfHour;
		int hours = 0;
		int halfHours = 0;
		while (timeRemaining > 1) {
			timeRemaining -= 2;
			hours++;
		}
		if (hours > 12) {
			hours -= 12;
		}
		if (hours == 0) {
			hours = 12;
		}
		if (timeRemaining == 1) {
			halfHours = 30;
		}
		final int[] returnArr = {hours, halfHours};
		return returnArr;
	}
	
	/**
	 * 
	 * @return An integer representing whether it is in the AM or PM (0, 1)
	 */
	public int getAMPM() {
		if (halfHour >= 24) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
}
