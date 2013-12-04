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
 * a day, and a half hour from 0-47
 * 
 * @author Inferno505
 * @version $Revision: 1.0 $
 */
public class DateInfo {

	//Format
	//year is absolute value
	//moth is 0 = January 11 = December
	//Day is absolute (starts at 1 to max number of given month)
	//HalfHour is 0 based (0 to 47)
	private final int year;
	// month is 0-based
	private final int month;
	// day is 1-based
	private final int day;
	// half-hour is 0-based
	private final int halfHour;

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
	 * Construct a DateInfo from the Java Date class
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
	 * Converts a Java calendar Date to a DateInfo Used to maintain
	 * compatability with an old version of the program
	 * 
	 * @param date
	 *            The date in its original format
	 * @return a DateInfo object with the updated information
	 */
	@SuppressWarnings("deprecation")
	public static DateInfo convertToDateInfo(Date date) {
		final int year = date.getYear();
		final int month = date.getMonth();
		final int day = date.getDate() - 1;
		final int halfHour = (date.getHours() * 2) + (date.getMinutes() / 30);

		return new DateInfo(year, month, day, halfHour);

	}

	/**
	 * Method convertDateInfoToDate. converts a DateInfo object to a Date object
	 * of the same parameters
	 * 
	 * @return Date
	 */
	@Deprecated
	public Date convertDateInfoToDate() {
		final Date date = new Date(year, month, day);
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
	 * Converts the given DateInfo to a Calendar Object of the same parameters
	 * @return
	 */
	public Calendar dateInfoToCalendar(){
		Calendar cal = new GregorianCalendar();
		int hourOfDay = ((this.halfHour/2));
		int minute = 0;
		if(this.halfHour%2 !=0){
			minute = 30;
		}
		
		cal.set(this.year, this.month, this.day, hourOfDay, minute);
		return cal;
	}

	
	/**
	 * Returns a Json-encoded String of this DateInfo
	 */
	@Override
	public String toString() {
		String theYear = "" + this.year;
		int theMonthI = this.getMonth();
		// Initialize a blank Month string
		String theMonthS = "";
		// Assign a 3-letter Month String depending on the value
		// of the month int.
		if (theMonthI == 0){
			theMonthS = "Jan";
		}
		if (theMonthI == 1){
			theMonthS = "Feb";
		}
		if (theMonthI == 2){
			theMonthS = "Mar";
		}
		if (theMonthI == 3){
			theMonthS = "Apr";
		}
		if (theMonthI == 4){
			theMonthS = "May";
		}
		if (theMonthI == 5){
			theMonthS = "Jun";
		}
		if (theMonthI == 6){
			theMonthS = "Jul";
		}
		if (theMonthI == 7){
			theMonthS = "Aug";
		}
		if (theMonthI == 8){
			theMonthS = "Sep";
		}
		if (theMonthI == 9){
			theMonthS = "Oct";
		}
		if (theMonthI == 10){
			theMonthS = "Nov";
		}
		else {
			theMonthS = "Dec";
		}
		String theDay = "" + this.day;
		// Deliver a time depending on the halfHour int
		int iTime = 0;
		String sTime = "";
		int theTime = this.getHalfHour();
		while (theTime > 1){
			theTime = theTime - 2;
			iTime++;
		}
		sTime = "" + iTime;
		if (theTime == 0){
			sTime = sTime + ":00";
		}
		else{
			sTime = sTime + ":30";
		}
		if (iTime > 12){
			iTime = iTime/2;
			sTime = sTime + " PM";
		}
		else{
			sTime = sTime + " AM";
		}
		// Collect all the info gathered above into a single string
		// and return it
		String theDateInfo = "" + theMonthS + "/" + theDay + "/" + theYear +
				" at " + sTime;
		return theDateInfo;
	} 

}
