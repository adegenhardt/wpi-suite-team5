/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Test cases for the toString() method under DateInfo
 * 
 * @author Connor Porell
 * 
 * Contributors: Team _
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import static org.junit.Assert.*;


import org.junit.Before;

import org.junit.Test;

/**
 * 
 * @author Team_
 * @version 1.0
 *
 */
public class DateInfoTest {
	DateInfo dateTest1 = new DateInfo(2013, 2, 8, 22);
	DateInfo dateTest2 = new DateInfo(1977, 5, 6, 35);
	DateInfo dateTest3 = new DateInfo(2014, 6, 7, 0);
	String dateTest1S = "11:00 AM, 3/9/2013";
	String dateTest2S = "5:30 PM, 6/7/1977";
	String dateTest3S = "12:00 AM, 7/8/2014";
	String dateTest1SOld = "Mar/9/2013 at 10:00 AM";
	String dateTest2SOld = "Jun/7/1977 at 5:30 PM";
	String dateTest3SOld = "Jul/8/2014 at 12:00 AM";
	
	/**
	 * Test the toString() method under DateInfo for the
	 * first set of example of test data
	 */
	@Test
	public void testDateInfo1ToString(){
		assertEquals(dateTest1S, dateTest1.toString());
		assertNotSame(dateTest1SOld, dateTest1.toString());
	}
	
	/**
	 * Test the toString() method under DateInfo for the
	 * second set of example of test data
	 */
	@Test
	public void testDateInfo2ToString(){
		assertEquals(dateTest2S, dateTest2.toString());
		assertNotSame(dateTest2SOld, dateTest2.toString());
	}
	
	/**
	 * Test the toString() method under DateInfo for the
	 * third set of example of test data
	 */
	@Test
	public void testDateInfo3ToString(){
		assertEquals(dateTest3S, dateTest3.toString());
		assertNotSame(dateTest3SOld, dateTest3.toString());
	}
	
	/**
	 * Test the toString() method under DateInfo for the
	 * third set of example of test data
	 */
	@Test
	public void testDateToDateInfo(){
		Date date1 = new Date(100, 5, 8);
		date1.setHours(5);
		date1.setMinutes(45);
		DateInfo dateInfoCompare = new DateInfo(2000, 5, 7, 11);
		DateInfo dateInfoTest = new DateInfo(-1, -1, -1, -1);
		dateInfoTest.convertToDateInfo(date1);
		System.out.println(dateInfoTest.toString());
		assertEquals(dateInfoCompare, dateInfoTest);
		
	}
}
