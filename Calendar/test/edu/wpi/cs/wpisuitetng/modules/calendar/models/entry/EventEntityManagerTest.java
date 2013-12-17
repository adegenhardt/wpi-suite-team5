/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

import edu.wpi.cs.wpisuitetng.modules.calendar.MockData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;

/**
 * Testing for various aspects of EventEntityManager
 * Thanks to Team Rolling Thunder for the helpful starting point
 * @author srkodzis
 *
 * @version $Revision: 1.0 $
 */
public class EventEntityManagerTest {

	MockData db;
	User existingUser;
	Session defaultSession;
	String mockSsid;
	User alfred;
	Session adminSession;
	Project testProject;
	Project otherProject;
	EventEntityManager manager;
	Event event1;
	Event event2;
	
	DateInfo startDate1 = new DateInfo( 1984, 1, 11, 1 );
	DateInfo endDate1 = new DateInfo( 1994, 4, 11, 1 );
	
	DateInfo startDate2 = new DateInfo( 2013, 5, 12, 2 );
	DateInfo endDate2 = new DateInfo( 2013, 5, 12, 40 );
	
	int testCategory = 10;
	
	/**
	 * Set up objects and create a mock session for testing
	 */
	@Before
	public void setUp() {
		final User admin = new User("admin", "admin", "4321", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		existingUser = new User("alfred", "alfred", "1234", 2);

		event1 = new Event( "name1", "description1", startDate1,
				endDate1, testCategory, false, 1, "111" );
		
		event2 = new Event( "name2", "description2", startDate2,
				endDate2, true, 2, "222" );
		
		defaultSession = new Session(existingUser, testProject, mockSsid);
		
		db = new MockData(new HashSet<Object>());
		db.save( event1, testProject);
		db.save(existingUser);
		db.save( event2, otherProject );
		
		
		db.save(admin);
		manager = new EventEntityManager( db );
	}

	/**
	 * Stores a new event and ensures the correct data was stored
	
	 * @throws WPISuiteException */
	@Test
	public void testMakeEntity() throws WPISuiteException {
		final Event created = manager.makeEntity(defaultSession, event1.toJSON());
		assertEquals( 1, created.getId() ); // IDs are unique across projects
		assertEquals( created, event1 );
	}
	
	/**
	 * Ensures a event can be retrieved from the database
	
	 * @throws NotFoundException */
	@Test
	public void testGetEntity() throws NotFoundException {
		final Event[] gotten = manager.getEntity( defaultSession, "1" );
		assertSame( event1, gotten[0] );
	}

	/**
	 * Ensures a NotFoundException is thrown when trying to
	 * retrieve an invalid event
	
	 * @throws NotFoundException */
	@Test(expected=NotFoundException.class)
	public void testGetBadId() throws NotFoundException {
		manager.getEntity(defaultSession, "-1");
	}

	/**
	 * Ensures that Event can be deleted
	
	 * @throws WPISuiteException */
	@Test
	public void testDelete() throws WPISuiteException {
		assertSame( event1, db.retrieve( Event.class, "id", 1).get(0));
		assertTrue(manager.deleteEntity(adminSession, "1"));
		assertEquals(0, db.retrieve( Event.class, "id", 1).size());
	}
	
	/**
	 * Ensures a NotFoundException is thrown when trying to delete
	 * an invalid Event instance
	
	 * @throws WPISuiteException */
	@Test(expected=NotFoundException.class)
	public void testDeleteMissing() throws WPISuiteException {
		manager.deleteEntity( adminSession, "4534" );
	}
	
	/**
	 * Ensures an UnauthorizedException is thrown when trying
	 * to delete an entity while not authorized
	
	 * @throws WPISuiteException */
	@Test(expected=UnauthorizedException.class)
	public void testDeleteNotAllowed() throws WPISuiteException {
		manager.deleteEntity( defaultSession, Integer.toString( event1.getId() ) );
	}
	
	/**
	 * Ensures the deletion of all calendar data funtions properly
	
	 * @throws WPISuiteException */
	@Test
	public void testDeleteAll() throws WPISuiteException {
		final Event anotherEvent = new Event();
		manager.makeEntity(defaultSession, anotherEvent.toJSON());
		assertEquals(2, db.retrieveAll(new Event(), testProject).size());
		manager.deleteAll(adminSession);
		assertEquals(0, db.retrieveAll(new Event(), testProject).size());
		// event2 should still be around on the other project
		assertEquals(1, db.retrieveAll(new Event(), otherProject).size());
	}

	/**
	 * Method testDeleteAllWhenEmpty.
	
	 * @throws WPISuiteException */
	@Test
	public void testDeleteAllWhenEmpty() throws WPISuiteException {
		manager.deleteAll(adminSession);
		manager.deleteAll(adminSession);
		// no exceptions
	}
	
	/**
	 * Method testCount.
	 */
	@Test
	public void testCount() {
		assertEquals(2, manager.Count());
	}


	/**
	 * Method updateEventTest.
	
	 * @throws WPISuiteException */
	@Test
	public void updateEventTest() throws WPISuiteException {
		final Event updatedEvent = manager.update(defaultSession, event1.toJSON());
		assertEquals(event1.getName(), updatedEvent.getName());
		assertEquals(event1.getId(), updatedEvent.getId());
	}
	
	/**
	 * Test that all Event entities are returned
	 */
	@Test
	public void getAllTest() {
		final Event reqList[] = new Event[2];
		reqList[0] = event1;
		reqList[1] = event2;
		manager.save(defaultSession, event2);
		final Event returnedEventList[] = manager.getAll( defaultSession );
		assertEquals(2, returnedEventList.length);
	}
}
