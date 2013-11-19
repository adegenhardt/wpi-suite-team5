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

/**
 * Testing for various aspects of CalendarDataEntityManager
 * Thanks to Team Rolling Thunder for the helpful starting point
 * @author srkodzis
 *
 * @version $Revision: 1.0 $
 */
public class CalendarDataEntityManagerTest {

	MockData db;
	User existingUser;
	Session defaultSession;
	String mockSsid;
	User alfred;
	Session adminSession;
	Project testProject;
	Project otherProject;
	CalendarDataEntityManager manager;
	CalendarData cd1;
	CalendarData cd2;
	
	/**
	 * Set up objects and create a mock session for testing
	
	 * @throws Exception */
	@Before
	public void setUp() throws Exception {
		User admin = new User("admin", "admin", "4321", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		existingUser = new User("alfred", "alfred", "1234", 2);
		cd1 = new CalendarData( "name", "type", 1 );
		
		cd2 = new CalendarData( "other_name", "other_type", 2 );
		
		defaultSession = new Session(existingUser, testProject, mockSsid);
		
		db = new MockData(new HashSet<Object>());
		db.save( cd1, testProject);
		db.save(existingUser);
		
		// Saving something to another project seems to overwrite the old one
		// Hence why this is commented out
		// db.save( cd1, otherProject);
		db.save( cd2, otherProject );
		
		
		db.save(admin);
		manager = new CalendarDataEntityManager( db );
	}

	/**
	 * Stores a new requirement and ensures the correct data was stored
	
	 * @throws WPISuiteException */
	@Test
	public void testMakeEntity() throws WPISuiteException {
		CalendarData created = manager.makeEntity(defaultSession, cd1.toJSON());
		assertEquals( 1, created.getId() ); // IDs are unique across projects
		assertEquals( "name", created.getName() );
		assertEquals( "type", created.getType() );
		//TODO: Test Years stored in Calendar using a getEquals method
		//TODO: Write said getEquals method
	}
	
	/**
	 * Ensures a requirement can be retrieved from the database
	
	 * @throws NotFoundException */
	@Test
	public void testGetEntity() throws NotFoundException {
		CalendarData[] gotten = manager.getEntity( defaultSession, "1" );
		assertSame( cd1, gotten[0] );
	}

	/**
	 * Ensures a NotFoundException is thrown when trying to
	 * retrieve an invalid requirement
	
	 * @throws NotFoundException */
	@Test(expected=NotFoundException.class)
	public void testGetBadId() throws NotFoundException {
		manager.getEntity(defaultSession, "-1");
	}

	/**
	 * Ensures that CalendarData can be deleted
	
	 * @throws WPISuiteException */
	@Test
	public void testDelete() throws WPISuiteException {
		assertSame( cd1, db.retrieve( CalendarData.class, "id", 1).get(0));
		assertTrue(manager.deleteEntity(adminSession, "1"));
		assertEquals(0, db.retrieve( CalendarData.class, "id", 1).size());
	}
	
	/**
	 * Ensures a NotFoundException is thrown when trying to delete
	 * an invalid CalendarData instance
	
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
		manager.deleteEntity( defaultSession, Integer.toString( cd1.getId() ) );
	}
	
	/**
	 * Ensures the deletion of all calendar data funtions properly
	
	 * @throws WPISuiteException */
	@Test
	public void testDeleteAll() throws WPISuiteException {
		CalendarData anotherRequirement = new CalendarData();
		manager.makeEntity(defaultSession, anotherRequirement.toJSON());
		assertEquals(2, db.retrieveAll(new CalendarData(), testProject).size());
		manager.deleteAll(adminSession);
		assertEquals(0, db.retrieveAll(new CalendarData(), testProject).size());
		// cd2 should still be around on the other project
		assertEquals(1, db.retrieveAll(new CalendarData(), otherProject).size());
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
	
	 * @throws WPISuiteException */
	@Test
	public void testCount() throws WPISuiteException {
		assertEquals(2, manager.Count());
	}


	/**
	 * Method updateCalendarDataTest.
	
	 * @throws WPISuiteException */
	@Test
	public void updateRequirementTest() throws WPISuiteException {
		CalendarData updatedCD = manager.update(defaultSession,cd1.toJSON());
		assertEquals(cd1.getName(), updatedCD.getName());
		assertEquals(cd1.getId(), updatedCD.getId());
	}
	
	/**
	 * Test that all CalendarData entities are returned
	 */
	@Test
	public void getAllTest() {
		CalendarData reqList[] = new CalendarData[2];
		reqList[0] = cd1;
		reqList[1] = cd2;
		manager.save(defaultSession, cd2);
		CalendarData returnedCDList[] = manager.getAll( defaultSession );
		assertEquals(2, returnedCDList.length);
	}
}
