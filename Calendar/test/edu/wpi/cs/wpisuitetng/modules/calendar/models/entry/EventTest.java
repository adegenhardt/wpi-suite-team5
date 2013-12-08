/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Underscore 
 *    
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import java.util.*;

/**
 * Testing for various aspects of Event
 * @author srkodzis
 *
 * @version $Revision: 1.0 $
 */
public class EventTest {

	Event event1;
	Event event2;
	
	DateInfo startDate1 = new DateInfo( 1984, 1, 11, 1 );
	DateInfo endDate1 = new DateInfo( 1994, 4, 11, 1 );
	
	DateInfo startDate2 = new DateInfo( 2013, 5, 12, 2 );
	DateInfo endDate2 = new DateInfo( 2013, 5, 12, 40 );
	
	Category testCategory = new Category( "name", 10 );
	
	/**
	 * Set up event1 and 2 as well as the network
	 */
	@Before
	public void setUp() {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		
		event1 = new Event( "name1", "description1", startDate1,
				endDate1, testCategory, true, 1, "111" );
		
		event2 = new Event( "name2", "description2", startDate2,
				endDate2, false, 2, "222" );
	}
	
	/**
	 * Make sure that both constructors work
	 */
	@Test
	public void testCreation() {
		assertNotNull( event1 );
		assertNotNull( event2 );
	}
	
	/**
	 * Test get and set for id field
	 */
	@Test
	public void testGetSetId() {
		assertEquals( 1, event1.getId() );
		assertNotSame( 4, event1.getId() );
		event1.setId( 4 );
		assertEquals( 4, event1.getId() );
	}
	
	/**
	 * Test get and set for project id field
	 */
	@Test
	public void testGetSetTeamEvent() {
		assertFalse( event2.isTeamEvent() );
		event2.setTeamEvent( true );
		assertTrue( event2.isTeamEvent() );
	}
	
	/**
	 * Test get and set for creator id field
	 */
	@Test
	public void testGetSetCreatorId() {
		assertEquals( "111", event1.getCreatorId() );
		assertNotSame( "444", event1.getCreatorId() );
		event1.setCreatorId( "444" );
		assertEquals( "444", event1.getCreatorId() );
	}
	
	/**
	 * Test capability to set and obtain delete status of Event
	 */
	@Test
	public void testDeleteStatus() {
		assertFalse( event1.isDeleted() );
		event1.setDeleted( true );
		assertTrue( event1.isDeleted() );
	}

	/**
	 * Test get and set for name field
	 */
	@Test
	public void testGetSetName() {
		assertEquals( "name1", event1.getName() );
		assertNotSame( "testName", event1.getName() );
		event1.setName( "testName" );
		assertEquals( "testName", event1.getName() );
	}
	
	/**
	 * Test get and set for description field
	 */
	@Test
	public void testGetSetDescription() {
		assertEquals( "description1", event1.getDescription() );
		assertNotSame( "testDescription", event1.getDescription() );
		event1.setDescription( "testDescription" );
		assertEquals( "testDescription", event1.getDescription() );
	}
	
	/**
	 * Test get and set for start date
	 */
	@Test
	public void testGetSetStartDate() {
		assertEquals( startDate1, event1.getStartDate() );
		assertNotSame( startDate2, event1.getStartDate() );
		event1.setStartDate( startDate2 );
		assertEquals( startDate2, event1.getStartDate() );
	}
	
	/**
	 * Test get and set for end date
	 */
	@Test
	public void testGetSetEndDate() {
		assertEquals( endDate1, event1.getEndDate() );
		assertNotSame( endDate2, event1.getEndDate() );
		event1.setEndDate( endDate2 );
		assertEquals( endDate2, event1.getEndDate() );
	}
	
	/**
	 * Test get and set for category
	 */
	@Test
	public void testGetSetCategory() {
		final Category newCategory = new Category( "newName", 3 );
		assertEquals( testCategory, event1.getCategory() );
		assertNotSame( newCategory, event1.getCategory() );
		event1.setCategory( newCategory );
		assertEquals( newCategory, event1.getCategory() );
	}
	
	/**
	 * Test various functionality regarding userIds and access
	 */
	@Test
	public void testUserIdFunctions() {
		
		// Test user involved in creation
		assertTrue( event1.hasAccess( "111" ) );
		
		// Test setting a list of users
		final List<String> testUsers = new ArrayList< String >();
		testUsers.add( "10" );
		testUsers.add( "20" );
		testUsers.add( "30" );
		
		assertNotSame( testUsers, event1.getUserIds() );
		
		event1.setUserIds( testUsers );
		assertEquals( testUsers, event1.getUserIds() );
		
		assertTrue( event1.hasAccess( "10" ) );
		assertTrue( event1.hasAccess( "20" ) );
		assertTrue( event1.hasAccess( "30" ) );
	
		
		// Test adding a user
		assertFalse( event1.hasAccess( "40" ) );
		event1.addUserId( "40" );
		assertTrue( event1.hasAccess( "40" ) );
	
		// Test removing a user
		assertTrue( event1.hasAccess( "20" ) );
		event1.removeUserId( "20" );
		assertFalse( event1.hasAccess( "20" ) );
		
		
		// Attempt to addd user to individual event
		event2.addUserId( "111" );
		assertFalse( event2.hasAccess( "111" ) );
	}
	
	/**
	 * Test occurs on year function for Event
	 */
	@Test
	public void testOccursOnYear() {
		
		// Test start date is equal
		assertTrue( event1.occursOnYear( 1984 ) );
		
		// Test end date is equal
		assertTrue( event1.occursOnYear( 1994 ) );
		
		// Test date is between start and end date
		assertTrue( event1.occursOnYear( 1988 ) );
		
		// test before start date (false)
		assertFalse( event1.occursOnYear( 1983 ) );
		
		// test after end date (false)
		assertFalse( event1.occursOnYear( 1995 ) );
		
	}
	
	/**
	 * Test occurs on month function for Event
	 */
	@Test
	public void testOccursOnMonth() {
		
		// Test start date is equal to month
		assertTrue( event1.occursOnMonth( 1984, 1 ) );
		
		// Test end date is equal
		assertTrue( event1.occursOnMonth( 1994, 4 ) );
		
		// Test date is between start and end year
		assertTrue( event1.occursOnMonth( 1990, 4 ) );
		
		// Test date is between start and end month
		assertTrue( event1.occursOnMonth( 1994, 3 ) );
		
		// test before start date (false)
		assertFalse( event1.occursOnMonth( 1983, 3 ) );
		assertFalse( event1.occursOnMonth( 1984, 0 ) );
		
		// test after end date (false)
		assertFalse( event1.occursOnMonth( 1995, 3 ) );
		assertFalse( event1.occursOnMonth( 1994, 5 ) );
		
	}

	/**
	 * Test occurs on date function for Event
	 */
	@Test
	public void testOccursOnDate() {
		
		// Test start date is equal to month
		assertTrue( event1.occursOnDate( 1984, 1, 11 ) );
		
		// Test end date is equal
		assertTrue( event1.occursOnDate( 1994, 4, 11 ) );
		
		// Test date is between start and end year
		assertTrue( event1.occursOnDate( 1990, 2, 11 ) );
		
		// Test date is between start and end month
		assertTrue( event1.occursOnDate( 1994, 3, 11 ) );
		
		// test before start date (false)
		assertFalse( event1.occursOnDate( 1983, 1, 11 ) );
		assertFalse( event1.occursOnDate( 1984, 0, 11 ) );
		assertFalse( event1.occursOnDate( 1984, 0, 10 ) );
		
		// test after end date (false)
		assertFalse( event1.occursOnDate( 1995, 4, 11 ) );
		assertFalse( event1.occursOnDate( 1994, 5, 11 ) );
		assertFalse( event1.occursOnDate( 1994, 4, 12 ) );
		
	}
	
	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals() {
		assertTrue( event1.equals( event1 ) );
	}
	
	/**
	 * Test the copyFrom method
	 */
	@Test
	public void testCopyFrom() {
		assertFalse( event1.equals( event2 ) );
		event1.copyFrom( event2 );
		assertTrue( event1.equals( event2 ) );
	}
	
	/**
	 * Test toJson and fromJson methods
	 */
	@Test
	public void testJsonConversion() {
		final String convertedEvent = event1.toJSON();
		final Event eventFromJson = Event.fromJson( convertedEvent );
		assertEquals( event1, eventFromJson );
	}
	
	/**
	 * Test toString method
	 */
	@Test
	public void testToString() {
		assertEquals( "name1", event1.toString() );
	}
	
	// TODO: Write tests to check the out-of-range exceptions
	// for occursOnMonth and Day
}
