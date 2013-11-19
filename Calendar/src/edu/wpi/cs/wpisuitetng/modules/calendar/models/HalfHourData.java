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

/**
 * @authors Inferno505
 * @version $Revision: 1.0 $
 */
public class HalfHourData {
	DateInfo dateInfo; //date to full resolution (to HalfHour)

	/**
	 * Constructor for HalfHourData.
	 * @param year int
	 * @param month int
	 * @param day int
	 * @param halfHour int
	 */
	public HalfHourData(int year, int month, int day, int halfHour) {
		dateInfo = new DateInfo(year, month, day, halfHour);
	}
/**
 * 
 * @return halfHour integer of given HalfHourData
 */
	public int getHalfHour() {
		return this.dateInfo.getHalfHour();
	}
}