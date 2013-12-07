/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Tests the SortEvents function
 * @author Connor Porell
 * 
 * Contributors: Team Underscore 
 *    
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.SortEvents;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;

/**
 * 
 * @author Team_
 * @version 1.0
 *
 */
public class SortEventsTest {
	/**
	 * Sample objects for test cases to draw from
	 */
	Category cat = new Category("test", 1, "JoeSchmoe", false, true);
	Category cat2 = new Category("test2", 2, "BillChill", false, true);
	DateInfo time1 = new DateInfo(2013, 12, 10, 24);
	DateInfo time2 = new DateInfo(2013, 12, 10, 28);
	DateInfo time3 = new DateInfo(2013, 12, 14, 30);
	DateInfo time4 = new DateInfo(2013, 12, 14, 31);

	Event event1 = new Event("Team Meeting", "Meet the co-workers", time1,
			time2, cat, true, 0, "SamuelOak");
	Event event2 = new Event("Become Champion", "Beat the Elite Four", time3,
			time4, cat2, true, 1, "Red");
	
	ArrayList<Event> testList1 = new ArrayList<Event>();
	SortEvents s = new SortEvents();
	
	/**
	 * Note to self: ArrayLists can only be initialized within a method.
	 * @param event1 one event to add to list
	 * @param event2 another event to add to list
	 * @param testList1 list to add events to
	 * @return list
	 */
	@Before
	public void createTestList1(){
		testList1.add(event2);
		testList1.add(event1);
	}

	/**
	 * 
	 * @param testList list to sort
	 * @return sorted list
	 */
	@Test
	public void testSameEvent(){
		ArrayList<Event> l = new ArrayList<Event>();
		l.add(event1);
		assertEquals(l, s.sortEvents(l));
	}
	
	@Test
	public void testMakeSureSortDetectionWorks(){
		ArrayList<Event> sorted = new ArrayList<Event>();
		sorted.add(event1);
		sorted.add(event2);
		assertFalse(sorted.equals(s.sortEvents(testList1)));
	}
	
	@Test
	public void testSimpleAlphabeticalSort(){
		
	}
}
