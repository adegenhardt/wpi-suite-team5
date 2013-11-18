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

import java.util.Date;

import org.junit.Test;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.DayData;
import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

/**
 * Test functionality of the DayData class
 *
 * @version $Revision: 1.0 $
 * @author srkodzis
 */
public class DayDataTest {

	/**
	 * Test creation of DayData
	 */
	@Test
	public void testCreation() {
		DayData day = new DayData( 1984, 1, 12 );
		
		assertNotNull( day );
	}
	
	/**
	 * Test the addition, retrieval, and removal of an events
	 */
	@Test
	public void testEvents() {
		DayData day = new DayData( 1984, 1, 12 );
		
		Date startTime = new Date();
		Date endTime = new Date();
		
		Event event1 = new Event( 1, "name1", "desc1", startTime, endTime );
		
		// test that the first item can be added and retrieved
		day.addEvent( event1 );
		ArrayList<Event> retrievedEvents = day.getEvents();
		assertTrue( retrievedEvents.contains( event1 ) );
		
		Event event2 = new Event( 2, "name2", "desc2", startTime, endTime );
		day.addEvent( event2 );
		
		retrievedEvents = day.getEvents();
		assertTrue( retrievedEvents.contains( event1 ) );
		assertTrue( retrievedEvents.contains( event2 ) );
		
		// Try to remove the same event twice
		assertTrue( day.removeEvent( event1 ) );
		assertFalse( day.removeEvent( event1 ) );
		
		retrievedEvents = day.getEvents();
		assertFalse( retrievedEvents.contains( event1 ) );
		assertTrue( retrievedEvents.contains( event2 ) );
		
		// Attempt to remove more items than exist
		assertTrue( day.removeEvent( event2 ) );
		assertFalse( day.removeEvent( event2 ) );
		
	}

}
