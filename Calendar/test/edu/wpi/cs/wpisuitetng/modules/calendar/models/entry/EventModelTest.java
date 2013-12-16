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
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * 
 * @author Team_
 * @version 1.0
 *
 */
public class EventModelTest {

	DateInfo startDate1 = new DateInfo( 1984, 1, 11, 1 );
	DateInfo endDate1 = new DateInfo( 1994, 4, 11, 1 );
	
	DateInfo startDate2 = new DateInfo( 2013, 5, 12, 2 );
	DateInfo endDate2 = new DateInfo( 2013, 5, 12, 40 );
	
	int testCategory = 10;
	
	Event indEvent1 = new Event( "name1", "description1", startDate1,
			endDate1, testCategory, false, 1, "111" );
	
	Event indEvent2 = new Event( "name2", "description2", startDate2,
			endDate2, false, 2, "222" );
	
	Event teamEventOdds = new Event( "tname1", "description1", startDate1,
			endDate1, testCategory, true, 3, "333" );
	
	Event teamEventEvens = new Event( "tname2", "description2", startDate2,
			endDate2, true, 4, "444" );
	
	/**
	 * Set up the network and events
	 */
	@Before
	public void setUp() {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		
		EventModel.getInstance().emptyModel();
		
		// Set all events to current project
		String projName = ConfigManager.getConfig().getProjectName();
		
		indEvent1.setProjectId( projName );
		indEvent2.setProjectId( projName );
		teamEventOdds.setProjectId( projName );
		teamEventEvens.setProjectId( projName );
		
		
		indEvent1.setDeleted( false );
		indEvent2.setDeleted( false );
		teamEventOdds.setDeleted( false );
		teamEventEvens.setDeleted( false );
	}
	
	/**
	 * Test getting size
	 */
	@Test
	public void testGetSize() {
		assertEquals( 0, EventModel.getInstance().getSize() );
	}
	
	/**
	 * Test adding an event and removing it
	 */
	@Test
	public void testAddRemoveEvent() {
		EventModel.getInstance().addEvent( indEvent1 );
		assertEquals( 1, EventModel.getInstance().getSize() );
		
		EventModel.getInstance().removeEvent( indEvent1.getId() );
		assertEquals( 0, EventModel.getInstance().getSize() );
	}
	
	/**
	 * Test adding an event and removing it
	 */
	@Test
	public void testGetEvent() {
		EventModel.getInstance().addEvent( indEvent1 );
		assertEquals( indEvent1, EventModel.getInstance().getEvent( 1 ) );
	}
	
	/**
	 * Test emptying the model once something has been added
	 * (after this test was working, I started using emptyModel as part of before method)
	 */
	@Test
	public void testEmptyModelWithActualData() {
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		assertEquals( 2, EventModel.getInstance().getSize() );
		
		EventModel.getInstance().emptyModel();
		assertEquals( 0, EventModel.getInstance().getSize() );
	}
	
	/**
	 * Test getNextId
	 */
	@Test
	public void testGetNextID(){
		assertEquals( 0, EventModel.getInstance().getNextID() );
		assertEquals( 1, EventModel.getInstance().getNextID() );
		assertEquals( 2, EventModel.getInstance().getNextID() );
	}
	
	/**
	 * Test getting elements of the model
	 * Turns out that the newest events are in the front
	 */
	@Test
	public void testGetElementAt() {
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		assertEquals( indEvent1, EventModel.getInstance().getElementAt( 1 ) );
		assertEquals( indEvent2, EventModel.getInstance().getElementAt( 0 ) );
	}
	
	/**
	 * Test getting all the events
	 */
	@Test
	public void testGetAllEvents() {
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		
		final List<Event> desiredEvents = new ArrayList<Event>();
		desiredEvents.add( indEvent1 );
		desiredEvents.add( indEvent2 );
		
		assertEquals( desiredEvents, EventModel.getInstance().getAllEvents() );
	}
	
	/**
	 * Test getting team events by year
	 */
	@Test
	public void testGetTeamEventsByYear() {
		
		// Add users to team events
		teamEventOdds.addUserId( "111" );
		teamEventEvens.addUserId( "222" );
		
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		EventModel.getInstance().addEvent( teamEventOdds );
		EventModel.getInstance().addEvent( teamEventEvens );
		
		final List< Event > events = EventModel.getInstance().getTeamEvents( "111", 1985 );
		
		// test an event that exists and the user has access to
		assertTrue( events.contains( teamEventOdds ) );
		
		// test an event that exists, but the user doesn't have access to
		assertFalse( events.contains( teamEventEvens ) );
		
		// test an event that exists for the user, but isn't a team event
		assertFalse( events.contains( indEvent1 ) );
		
		// test calling a year that has no events
		assertEquals( 0, EventModel.getInstance().getTeamEvents( "111", 1911 ).size() );
		
		// test a valid event that is deleted (and thus shouldn't be obtained)
		teamEventOdds.setDeleted( true );
		assertEquals( 0, EventModel.getInstance().getTeamEvents( "111", 1985 ).size() );
	}
	
	/**
	 * Test getting team events by month
	 */
	@Test
	public void testGetTeamEventsByMonth() {
		
		
		// Add users to team events
		teamEventOdds.addUserId( "111" );
		teamEventEvens.addUserId( "222" );
		
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		EventModel.getInstance().addEvent( teamEventOdds );
		EventModel.getInstance().addEvent( teamEventEvens );
		
		final List< Event > events = EventModel.getInstance().getTeamEvents( "111", 1984, 1 );
		
		// test an event that exists and the user has access to
		assertTrue( events.contains( teamEventOdds ) );
		
		// test an event that exists, but the user doesn't have access to
		assertFalse( events.contains( teamEventEvens ) );
		
		// test an event that exists for the user, but isn't a team event
		assertFalse( events.contains( indEvent1 ) );
		
		// test calling a year that has no events
		assertEquals( 0, EventModel.getInstance().getTeamEvents( "111", 1984, 0 ).size() );
		
		// test a valid event that is deleted (and thus shouldn't be obtained)
		teamEventOdds.setDeleted( true );
		assertEquals( 0, EventModel.getInstance().getTeamEvents( "111", 1984, 1 ).size() );
	}
	
	/**
	 * Test getting team events by date
	 */
	@Test
	public void testGetTeamEventsByDate() {
		
		// Add users to team events
		teamEventOdds.addUserId( "111" );
		teamEventEvens.addUserId( "222" );
		
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		EventModel.getInstance().addEvent( teamEventOdds );
		EventModel.getInstance().addEvent( teamEventEvens );
		
		final List< Event > events = EventModel.getInstance().getTeamEvents( "111", 1984, 1, 11 );
		
		// test an event that exists and the user has access to
		assertTrue( events.contains( teamEventOdds ) );
		
		// test an event that exists, but the user doesn't have access to
		assertFalse( events.contains( teamEventEvens ) );
		
		// test an event that exists for the user, but isn't a team event
		assertFalse( events.contains( indEvent1 ) );
		
		// test calling a year that has no events
		assertEquals( 0, EventModel.getInstance().getTeamEvents( "111", 1984, 1, 10 ).size() );
		
		// test a valid event that is deleted (and thus shouldn't be obtained)
		teamEventOdds.setDeleted( true );
		assertEquals( 0, EventModel.getInstance().getTeamEvents( "111", 1984, 1, 11 ).size() );
	}
	
	/**
	 * Test getting team events by year
	 */
	@Test
	public void testGetUserEventsByYear() {
		
		// Add users to team events
		teamEventOdds.addUserId( "111" );
		teamEventEvens.addUserId( "222" );
		
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		EventModel.getInstance().addEvent( teamEventOdds );
		EventModel.getInstance().addEvent( teamEventEvens );

		final List< Event > events = EventModel.getInstance().getAllEvents( "111", 1985 );
		
		// test an event that exists and the user has access to
		assertTrue( events.contains( teamEventOdds ) );
		assertTrue( events.contains( indEvent1 ) );
		
		// test an event that exists, but the user doesn't have access to
		assertFalse( events.contains( teamEventEvens ) );
		assertFalse( events.contains( indEvent2 ) );
		
		// test calling a year that has no events
		assertEquals( 0, EventModel.getInstance().getAllEvents( "111", 1983 ).size() );
		
		// test a valid event that is deleted (and thus shouldn't be obtained)
		teamEventOdds.setDeleted( true );
		assertEquals( 1, EventModel.getInstance().getAllEvents( "111", 1984 ).size() );
	}
	
	/**
	 * Test getting team events by month
	 */
	@Test
	public void testGetUserEventsByMonth() {
		
		
		// Add users to team events
		teamEventOdds.addUserId( "111" );
		teamEventEvens.addUserId( "222" );
		
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		EventModel.getInstance().addEvent( teamEventOdds );
		EventModel.getInstance().addEvent( teamEventEvens );
		
		final List< Event > events = EventModel.getInstance().getUserEvents( "111", 1984, 1 );
		
		// test an event that exists and the user has access to
		assertTrue( events.contains( teamEventOdds ) );
		assertTrue( events.contains( indEvent1 ) );
		
		// test an event that exists, but the user doesn't have access to
		assertFalse( events.contains( teamEventEvens ) );
		assertFalse( events.contains( indEvent2 ) );
		
		// test calling a year that has no events
		assertEquals( 0, EventModel.getInstance().getUserEvents( "111", 1984, 1, 10 ).size() );
		
		// test a valid event that is deleted (and thus shouldn't be obtained)
		teamEventOdds.setDeleted( true );
		assertEquals( 1, EventModel.getInstance().getUserEvents( "111", 1984, 1, 11 ).size() );
	}
	
	/**
	 * Test getting team events by date
	 */
	@Test
	public void testGetUserEventsByDate() {
		
		// Add users to team events
		teamEventOdds.addUserId( "111" );
		teamEventEvens.addUserId( "222" );
		
		EventModel.getInstance().addEvent( indEvent1 );
		EventModel.getInstance().addEvent( indEvent2 );
		EventModel.getInstance().addEvent( teamEventOdds );
		EventModel.getInstance().addEvent( teamEventEvens );
		
		final List< Event > events = EventModel.getInstance().getUserEvents( "111", 1984, 1, 11 );
		
		// test an event that exists and the user has access to
		assertTrue( events.contains( teamEventOdds ) );
		assertTrue( events.contains( indEvent1 ) );
		
		// test an event that exists, but the user doesn't have access to
		assertFalse( events.contains( teamEventEvens ) );
		assertFalse( events.contains( indEvent2 ) );
		
		// test calling a year that has no events
		assertEquals( 0, EventModel.getInstance().getUserEvents( "111", 1984, 1, 10 ).size() );
		
		// test a valid event that is deleted (and thus shouldn't be obtained)
		teamEventOdds.setDeleted( true );
		assertEquals( 1, EventModel.getInstance().getUserEvents( "111", 1984, 1, 11 ).size() );
	}
}
