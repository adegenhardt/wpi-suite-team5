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

import java.util.Date;

//this class is a suggested data structure that contains a date (in numerical information).  It would be used in areas to signify the date
//such as the start and end times of events, the date information of a year, month, or day object.
//I have made if in case it is useful, and am using it at least to the extent of yYearData, MonthData, and DayData

//a -1 for a parameter signifies that the date is only defined to the most specific point that is not a -1
////there is never a time when a point more specific, than a -1 containing info point, is not also -1
//ie year 20082 date info is (20082,-1,-1,-1)

//Format
//year is absolute value
//moth is 0 = January 11 = December
//Day is absolute (starts at 1 to max number of given month)
//HalfHour is 0 based (0 to 47)

//Critique this concept here:
//

/**
 * @authors Inferno505
 * @version $Revision: 1.0 $
 */
public class DateInfo {
	int year;
	int month;
	int day;
	int halfHour;

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
	 * 
	 * @return year parameter
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * 
	 * @return month parameter
	 */
	public int getMonth() {
		return this.month;
	}

	/**
	 * 
	 * @return day parameter
	 */
	public int getDay() {
		return this.day;
	}

	/**
	 * 
	 * @return halfHour parameter
	 */
	public int getHalfHour() {
		return this.halfHour;
	}

	/**
	 * Method convertDateInfoToDate. converts a DateInfo object to a Date object
	 * of the same parameters
	 * 
	 * @return Date
	 */
	public Date convertDateInfoToDate() {
		Date date = new Date(this.getYear(), this.getMonth(), this.getDay());
		return date;
	}
}
