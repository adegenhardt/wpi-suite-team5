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
	DateInfo time5 = new DateInfo(2014, 1, 3, 00);
	DateInfo time6 = new DateInfo(2014, 1, 4, 00);
	DateInfo time7 = new DateInfo(2014, 1, 5, 00);
	DateInfo time8 = new DateInfo(2013, 12, 7, 38);
	DateInfo time9 = new DateInfo(2013, 12, 7, 40);

	Event event1 = new Event("Team Meeting", null, time1, time2, cat, true,
			0, "SamuelOak");
	Event event2 = new Event("Become Champion", null, time3, time4, cat2, true, 
			1, "Red");
	Event event3 = new Event("Do a Thing", null, time1, time2, cat, true, 2,
			"Guy Person");
	Event event4 = new Event("Do this Thing", null, time3, time4, cat, true,
			3, "Dude Guy");
	Event event5 = new Event("Rescue Zelda", null, time5, time7, cat2, true,
			4, "Link");
	Event event6 = new Event("Slay the World-Eater", null, time3, time4, cat,
			true, 5, "Dovahkiin");
	Event event7 = new Event("Keep Exploring the Map", null, time5, time6, cat2,
			true, 6, "Scout");
	Event event8 = new Event("Troll everyone!", null, time8, time9, cat, true,
			7, "Reggie");
	Event event9 = new Event("Hunt Vaults", null, time8, time1, cat2, true, 8,
			"Maya");
	Event event10 = new Event("Geronimo!", null, time8, time1, cat2, true, 9,
			"The Doctor");
	
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
		assertEquals(l, s.sortEventsByName(l));
	}
	
	@Test
	public void testMakeSureSortDetectionWorks(){
		ArrayList<Event> sorted = new ArrayList<Event>();
		sorted.add(event1);
		sorted.add(event2);
		assertFalse(sorted.equals(s.sortEventsByName(testList1)));
	}
	
	@Test
	public void testSimpleAlphabeticalSort(){
		ArrayList<Event> toSort = new ArrayList<Event>();
		toSort.add(event4);
		toSort.add(event3);
		ArrayList<Event> sorted = new ArrayList<Event>();
		sorted.add(event3);
		sorted.add(event4);
		assertEquals(sorted, s.sortEventsByName(toSort));
	}
	
	@Test
	public void testLargerAlphabeticalSort(){
		ArrayList<Event> toSort = new ArrayList<Event>();
		toSort.add(event6);
		toSort.add(event1);
		toSort.add(event3);
		toSort.add(event4);
		toSort.add(event5);
		ArrayList<Event> sorted = new ArrayList<Event>();
		sorted.add(event3);
		sorted.add(event4);
		sorted.add(event5);
		sorted.add(event6);
		sorted.add(event1);
		assertEquals(sorted, s.sortEventsByName(toSort));
	}
	
	@Test
	public void testSortTwoEqualStartDates(){
		ArrayList<Event> toSort = new ArrayList<Event>();
		toSort.add(event5);
		toSort.add(event7);
		ArrayList<Event> sorted = new ArrayList<Event>();
		sorted.add(event7);
		sorted.add(event5);
		assertEquals(sorted, s.sortEventsByDate(toSort));
	}
	
	@Test
	public void testSortTwoEventsWithSameTimesButDiffNames(){
		ArrayList<Event> toSort = new ArrayList<Event>();
		toSort.add(event9);
		toSort.add(event10);
		ArrayList<Event> sorted = new ArrayList<Event>();
		sorted.add(event10);
		sorted.add(event9);
		assertEquals(sorted, s.sortEventsByDate(toSort));
	}
}
