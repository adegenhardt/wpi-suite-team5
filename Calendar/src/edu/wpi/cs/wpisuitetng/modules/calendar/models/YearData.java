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

import edu.wpi.cs.wpisuitetng.modules.calendar.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

/**
 * @author Inferno505
 *
 */
public class YearData {
MonthData[] months;
DateInfo dateInfo;

	public YearData(int year){
		//build months based on gregorean for given year
		this.dateInfo = new DateInfo (year, -1, -1, -1);
		months = new MonthData[ 12 ];
		createMonths( year );
	}
	
	/**
	 * Obtains this YearData's MonthData at a given index
	 * @param index The index from 0-11 for the month
	 * @return The month at a given index
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public MonthData getMonth( int index ) throws ArrayIndexOutOfBoundsException {
		
		if ( index < 0 || index >= 12 ) {
			throw new ArrayIndexOutOfBoundsException();
		}
		
		return months[ index ];
		
	}
	
	/**
	 * Create the months of a given year
	 * @param year the Year having its months created
	 */
	private void createMonths( int year ) {
		
		for ( int i = 0; i < 12; i++ ) {
			months[ i ] = new MonthData( year, i );
		}
		
	}

	/**
	 * Add an event to the YearData
	 * @param event the Event to be added
	 */
	public void addEvent(Event event) {
		int eventMonth = event.getStartTime().getMonth();
		
		months[ eventMonth ].addEvent( event );
		
	}
}
