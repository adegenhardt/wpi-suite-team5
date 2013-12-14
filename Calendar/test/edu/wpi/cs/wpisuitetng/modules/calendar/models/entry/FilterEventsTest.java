/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Tests the FilterEvents function
 * @author Connor Porell
 * 
 * Contributors: Team Underscore 
 *    
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.FilterEvents;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;

public class FilterEventsTest {
	//Instantiate the sample lists of events and categories
	//for later use in the test cases
	ArrayList<Event> testList1 = new ArrayList<Event>();
	ArrayList<Event> testList2 = new ArrayList<Event>();
	ArrayList<Category> catList1 = new ArrayList<Category>();
	ArrayList<Category> catList2 = new ArrayList<Category>();
	
	//Create sample categories for use in the test cases
	Category important = new Category ("Important", 0, "Joe Schmoe", false, false);
	Category party = new Category ("Party", 1, "Matt", false, false);
	Category blah = new Category ("Blah", 2, "Connor", false, false);
	Category games = new Category ("Games", 3, "Connor", false, false);
	
	//Create sample date info to be put into the sample events
	DateInfo time2 = new DateInfo(2014, 0, 0, 0);
	DateInfo time1 = new DateInfo(2013, 0, 18, 42);
	DateInfo time3 = new DateInfo(2000, 0, 0, 23);
	DateInfo time4 = new DateInfo(2001, 0 ,0, 23);
	
	//Create sample events for use throughout the test cases
	Event event1 = new Event("Rescue Zelda", null, time1, time2, important,
			true, 0, "Link");
	Event event2 = new Event("Conquer the world", null, time3, time4, important,
			true, 1, "Genghis Khan");
	Event event3 = new Event("Unimportant", null, time1, time2, party, false, 2,
			"guy");
	Event event4 = new Event("Mincraft LAN Party", null, time1, time2, games,
			false, 3, "Nathan");
	Event event5 = new Event("Meh", null, time3, time4, blah, false, 4,
			"Marvin");
	
	@Before
	//Use the instantiated TestList1 and a few sample events to fill the list
	public void createTestList1(){
		testList1.add(event1);
		testList1.add(event2);
		testList1.add(event3);
	}
	
	@Before
	//Use the instantiated TestList2 and a few sample events to fill the list
	public void createTestList2(){
		testList2.add(event1);
		testList2.add(event3);
		testList2.add(event4);
		testList2.add(event5);
	}
	
	@Before
	//Use the instantiated CatList1 and a sample category to fill the list
	public void createCatList1(){
		catList1.add(important);
	}
	
	@Before
	//Use the instantiated CatList2 and a sample category to fill the list
	public void createCatList2(){
		catList2.add(blah);
	}
	
	@Test
	//Test simple functionality on the filtering function
	public void testFilterOutAnEvent(){
		ArrayList<Event> filtered = new ArrayList<Event>();
		filtered.add(event1);
		filtered.add(event2);
		assertEquals(filtered, FilterEvents.filterEventsByCategory
				(testList1, catList1));
	}
	
	@Test
	//Tests whether the function can filter out all events
	public void testFilterOutAllEvents(){
		ArrayList<Event> filtered = new ArrayList<Event>();
		assertEquals(filtered, FilterEvents.filterEventsByCategory(testList1,
				catList2));
	}
	
	@Test
	//Tests to make sure the function can return the complete events
	//list when all the events in the list match the category(ies) being
	//passed
	public void testAllEventsIncludedInFilter(){
		ArrayList<Event> filtered = new ArrayList<Event>();
		filtered.add(event1);
		filtered.add(event2);
		filtered.add(event3);
		ArrayList<Category> cats = new ArrayList<Category>();
		cats.add(party);
		cats.add(important);
		assertEquals(filtered, FilterEvents.filterEventsByCategory
				(testList1, cats));
	}
	
	@Test
	//Makes sure the function returns the unaltered events list
	//when an empty list of categories is passed
	public void testFilterEventsWhenNoCategories(){
		ArrayList<Event> filtered = new ArrayList<Event>();
		filtered.add(event1);
		filtered.add(event3);
		filtered.add(event4);
		filtered.add(event5);
		ArrayList<Category> cats = new ArrayList<Category>();
		assertEquals(filtered, FilterEvents.filterEventsByCategory
				(testList2, cats));
	}
	
	@Test
	//Tests the function to make sure filtering works on an empty list,
	//even though categories are being passed
	public void testFilterEventsWhenNoEventsButCatsAreThere(){
		ArrayList<Event> filtered = new ArrayList<Event>();
		ArrayList<Event> events = new ArrayList<Event>();
		assertEquals(filtered, FilterEvents.filterEventsByCategory
				(events, catList2));
	}
	
	@Test
	//Tests the function to make sure filtering works (i.e, doesn't break,
	//when the input is both an empty list of events and an empty list of
	//categories
	public void testFilterWhenThereAreNoEventsOrCats(){
		ArrayList<Event> filtered = new ArrayList<Event>();
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Category> cats = new ArrayList<Category>();
		assertEquals(filtered, FilterEvents.filterEventsByCategory
				(events, cats));
	}
	
	
}
