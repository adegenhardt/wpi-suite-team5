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

/**
 * DateInfo is a form of storing time information. It contains a year, a month,
 * a day, and a half hour from 0-47
 * 
 * @author Inferno505
 * @version $Revision: 1.0 $
 */
public class DateInfo {
	/*
	 */
	private int year;
	private int month;
	private int day;
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
	public DateInfo convertToDateInfo(Date date) {
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
	public boolean equals(Object obj) {
		// You need to do checking for instance of object first
		return (this.year == ((DateInfo) obj).getYear()
				&& this.month == ((DateInfo) obj).getMonth()
				&& this.day == ((DateInfo) obj).getDay() && this.halfHour == ((DateInfo) obj)
					.getHalfHour());
	}
}
