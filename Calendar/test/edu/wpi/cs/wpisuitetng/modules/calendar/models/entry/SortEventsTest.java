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

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;

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
	ArrayList<Event> testList1;
	
	//Note to self: ArrayLists can only be initialized within a method.
	@Before
	public ArrayList<Event> createTestList1(Event event1, Event event2,
			ArrayList<Event> testList1){
		this.testList1.add(event2);
		this.testList1.add(event1);
		return testList1;
	}
	
	@Test
	public boolean testSimpleSort(ArrayList<Event> testList){
		ArrayList<Event> sorted = this.testList1;
		return true;
	}
	
	
}
