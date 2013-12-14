/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team _
 *    
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Test nonvisual functionality of MonthViewTable
 */
public class MonthViewTableTest {

	MonthViewTable table;
	
	@Before
	public void setup() {
		table = new MonthViewTable( null );
	}
	
	/**
	 * Test basic earliestOpenLine functionality
	 */
	@Test
	public void testFindEarliestOpenLineSuccess() {
		int earliestOpenSpaces[] = { 5, 3, 1, -1 };
		
		// test succeeding at the first spot
		assertEquals( 0, table.findEarliestOpenLine( 6, 9, earliestOpenSpaces ) );
		
		// test success finding the second spot
		assertEquals( 1, table.findEarliestOpenLine( 4, 5, earliestOpenSpaces ) );
		
		// and the third spot...
		assertEquals( 2, table.findEarliestOpenLine( 2, 5, earliestOpenSpaces ) );
		
		// finally the fourth spot
		assertEquals( 3, table.findEarliestOpenLine( 0, 5, earliestOpenSpaces ) );
	}

	/**
	 * Test basic earliestOpenLine functionality that doesn't get a complete success
	 */
	@Test
	public void testFindEarliestOpenLinePartialSuccess() {
		int earliestOpenSpaces[] = { 5, 9 };
		
		// test succeeding at the first spot
		assertEquals( 0, table.findEarliestOpenLine( 5, 8, earliestOpenSpaces ) );
		
	}
	
	/**
	 * Test basic earliestOpenLine functionality that doesn't success at all
	 */
	@Test
	public void testFindEarliestOpenLineFail() {
		int earliestOpenSpaces[] = { 5, 9 };
		
		// test succeeding at the first spot
		assertEquals( -1, table.findEarliestOpenLine( 1, 4, earliestOpenSpaces ) );
		
	}
	
}
