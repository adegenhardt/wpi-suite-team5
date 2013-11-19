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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

/**
 * Test functionality of the YearData class
 *
 * @version $Revision: 1.0 $
 * @author srkodzis
 */
public class YearDataTest {

	/**
	 * Test that years can be created
	 */
	@Test
	public void testCreation() {
		YearData year = new YearData( 1984 );
		
		assertNotNull( year );
	}
	
	/**
	 * Test that months can be retrieved
	 */
	@Test
	public void testMonthRetrieval() {
		YearData year = new YearData( 1984 );
		
		assertNotNull( year.getMonth( 0 ) );
		assertNotNull( year.getMonth( 11 ) );
		
		// Check that errors are being handled
		try {
			assertNull( year.getMonth( -1 ) );
		}
		catch ( ArrayIndexOutOfBoundsException a ) {
			assertTrue( true );
		}

		try {
			assertNull( year.getMonth( 12 ) );
		}
		catch ( ArrayIndexOutOfBoundsException a ) {
			assertTrue( true );
		}
	}
	
	/**
	 * Test that events can be added to the month
	 */
	@Test
	public void testAddEvent() {
		
		Date startTime = new Date();
		startTime.setDate( 1 );
		startTime.setYear( 1984 );
		startTime.setMonth( 2 );
		
		Date endTime = new Date();
		endTime.setDate( 1 );
		endTime.setYear( 1984 );
		endTime.setMonth( 2 );
		
		Event event = new Event( 1, "name1", "desc1", startTime, endTime );
		
		YearData year = new YearData( 1984 );
		
		year.addEvent( event );
		
		// Now retrieve the event all the way back out
		DayData[] days = year.getMonth( 2 ).getDays();
		ArrayList< Event > retrievedEvents = days[ 0 ].getDayEvents();
		
		assertEquals( 1, retrievedEvents.size() );
		assertEquals( event, retrievedEvents.get( 0 ) );
	}

}
