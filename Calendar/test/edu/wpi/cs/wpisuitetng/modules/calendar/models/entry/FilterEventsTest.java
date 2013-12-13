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
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;

public class FilterEventsTest {
	ArrayList<Event> testList1 = new ArrayList<Event>();
	
	Category important = new Category ("Important", 0, "Joe Schmoe", false, false);
	Category party = new Category ("Party", 1, "Matt", false, false);
	
	DateInfo time2 = new DateInfo(2014, 0, 0, 0);
	DateInfo time1 = new DateInfo(2013, 0, 18, 42);
	DateInfo time3 = new DateInfo(2000, 0, 0, 23);
	DateInfo time4 = new DateInfo(2001, 0 ,0, 23);
	
	Event event1 = new Event("Rescue Zelda", null, time1, time2, important,
			true, 0, "Link");
	Event event2 = new Event("Conquer the world", null, time3, time4, important,
			true, 1, "Genghis Khan");
	Event event3 = new Event("Unimportant", null, time1, time2, party, false, 2,
			"guy");
	
	@Before
	public void createTestList1(){
		testList1.add(event1);
		testList1.add(event2);
		testList1.add(event3);
	}
	
	@Test
	public boolean testFilterOutAnEvent(){
		return true;
	}
}
