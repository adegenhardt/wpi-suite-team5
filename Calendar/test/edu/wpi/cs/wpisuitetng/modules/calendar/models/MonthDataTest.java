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

import org.junit.Test;

/**
 * Test functionality of the MonthData class
 *
 * @version $Revision: 1.0 $
 * @author srkodzis
 */
public class MonthDataTest {

	/**
	 * Test that that MonthData can be created
	 */
	@Test
	public void testCreation() {
		MonthData month = new MonthData( 1984, 1 );
		
		assertNotNull( month );
		
		DayData[] days = month.getDays();
		assertNotNull ( days[ 0 ] );
	}

	/**
	 * Test creation of months with 31 days
	 */
	@Test
	public void test31DayMonths() {
		MonthData month = new MonthData( 1984, 0 );
		assertEquals( 31, month.getDays().length );
		
		month = new MonthData( 1984, 2 );
		assertEquals( 31, month.getDays().length );
		
		month = new MonthData( 1984, 4 );
		assertEquals( 31, month.getDays().length );
		
		month = new MonthData( 1984, 6 );
		assertEquals( 31, month.getDays().length );
		
		month = new MonthData( 1984, 7 );
		assertEquals( 31, month.getDays().length );
		
		month = new MonthData( 1984, 9 );
		assertEquals( 31, month.getDays().length );
		
		month = new MonthData( 1984, 11 );
		assertEquals( 31, month.getDays().length );
	}
	
	/**
	 * Test creation of months with 31 days
	 */
	@Test
	public void test30DayMonths() {
		MonthData month = new MonthData( 1984, 3 );
		assertEquals( 30, month.getDays().length );
		
		month = new MonthData( 1984, 5 );
		assertEquals( 30, month.getDays().length );
		
		month = new MonthData( 1984, 8 );
		assertEquals( 30, month.getDays().length );
		
		month = new MonthData( 1984, 10 );
		assertEquals( 30, month.getDays().length );
		
	}
	
	/**
	 * Test creation of variants of February
	 */
	@Test
	public void testFebruary() {
		// Non-leap
		MonthData month = new MonthData( 1985, 1 );
		assertEquals( 28, month.getDays().length );
		
		// Standard leap
		month = new MonthData( 1904, 1 );
		assertEquals( 29, month.getDays().length );
		
		// Non-leap (divisible by 100)
		month = new MonthData( 1900, 1 );
		assertEquals( 28, month.getDays().length );
		
		// Leap (divisible by 400)
		month = new MonthData( 2000, 1 );
		assertEquals( 29, month.getDays().length );
		
	}
	
}
