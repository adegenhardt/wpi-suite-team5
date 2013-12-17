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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.calendar.MockData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryEntityManager;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;

/**
 * Test for the CategoryEntityManager class structured according to the
 * tests produced for the EventEntityManager class.
 * @author awitt
 *
 * @version $Revision: 1.0 $
 */
public class CategoryEntityManagerTest {

	MockData db;
	User existingUser;
	Session defaultSession;
	String mockSsid;
	User alfred;
	Session adminSession;
	Project testProject;
	Project otherProject;
	CategoryEntityManager manager;
	Category category1;
	Category category2;
	Category category3;
	
	Category testCategory = new Category( "name", 10 );
	
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

		category1 = new Category( "name1", 10, "12345", false, false );
		category2 = new Category( "name2", 11, "123456", false, false );
		category3 = new Category( "name3", 12, "1234567", false, false );
		
		defaultSession = new Session(existingUser, testProject, mockSsid);
		
		db = new MockData(new HashSet<Object>());
		db.save( category1, testProject);
		db.save(existingUser);
		db.save( category2, otherProject );
		
		
		db.save(admin);
		manager = new CategoryEntityManager( db );
	}

	/**
	 * Stores a new category and ensures the correct data was stored
	 * @throws WPISuiteException 
	 */
	@Test
	public void testMakeEntity() throws WPISuiteException {
		final Category created = manager.makeEntity(defaultSession, category1.toJSON());
		assertEquals( 10, created.getId() ); // IDs are unique across projects
		assertEquals( created, category1 );
	}
	
	/**
	 * Ensures a category can be retrieved from the database
	 * @throws NotFoundException 
	 */
	@Test
	public void testGetEntity() throws NotFoundException {
		final Category[] gotten = manager.getEntity( defaultSession, "10" );
		assertSame( category1, gotten[0] );
	}

	/**
	 * Ensures a NotFoundException is thrown when trying to
	 * retrieve an invalid category
	 * @throws NotFoundException
	 */
	@Test(expected=NotFoundException.class)
	public void testGetBadId() throws NotFoundException {
		manager.getEntity(defaultSession, "-1");
	}

	/**
	 * Ensures that Category can be deleted
	 * @throws WPISuiteException 
	 */
	@Test
	public void testDelete() throws WPISuiteException {
		assertSame( category1, db.retrieve( Category.class, "id", 10).get(0));
		assertTrue(manager.deleteEntity(adminSession, "10"));
		assertEquals(0, db.retrieve( Category.class, "id", 10).size());
	}
	
	/**
	 * Ensures a NotFoundException is thrown when trying to delete
	 * an invalid Category instance
	 * @throws WPISuiteException
	 */
	@Test(expected=NotFoundException.class)
	public void testDeleteMissing() throws WPISuiteException {
		manager.deleteEntity( adminSession, "4534" );
	}
	
	/**
	 * Ensures an UnauthorizedException is thrown when trying
	 * to delete an entity while not authorized
	 * @throws WPISuiteException 
	 */
	@Test(expected=UnauthorizedException.class)
	public void testDeleteNotAllowed() throws WPISuiteException {
		manager.deleteEntity( defaultSession, Integer.toString( category1.getId() ) );
	}
	
	/**
	 * Ensures the deletion of all calendar data functions properly
	 * @throws WPISuiteException 
	 */
	@Test
	public void testDeleteAll() throws WPISuiteException {
		final Category anotherCategory = new Category();
		manager.makeEntity(defaultSession, anotherCategory.toJSON());
		assertEquals(2, db.retrieveAll(new Category(), testProject).size());
		manager.deleteAll(adminSession);
		assertEquals(0, db.retrieveAll(new Category(), testProject).size());
		// category2 should still be around on the other project
		assertEquals(1, db.retrieveAll(new Category(), otherProject).size());
	}

	/**
	 * Method testDeleteAllWhenEmpty.
	 * @throws WPISuiteException 
	 */
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
	 * Method updateCategoryTest.
	 * @throws WPISuiteException 
	 */
	@Test
	public void updateCategoryTest() throws WPISuiteException {
		final Category updatedCategory = manager.update(defaultSession, category1.toJSON());
		assertEquals(category1.getName(), updatedCategory.getName());
		assertEquals(category1.getId(), updatedCategory.getId());
	}
	
	/**
	 * Method updateCategoryTestBadRequestException
	 * @throws WPISuiteException
	 * @throws BadRequestException 
	 */
	@Test
	public void updateCategoryTestBadRequestException() throws WPISuiteException, BadRequestException {
		try {
			manager.update(defaultSession, category3.toJSON());
		}
		catch (BadRequestException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionDetails = sw.toString();
			System.out.println(exceptionDetails);
			assertTrue(true);
		}
	}
	
	/**
	 * Test that all Category entities are returned
	 */
	@Test
	public void getAllTest() {
		final Category reqList[] = new Category[2];
		reqList[0] = category1;
		reqList[1] = category2;
		manager.save(defaultSession, category2);
		final Category returnedCategoryList[] = manager.getAll( defaultSession );
		assertEquals(2, returnedCategoryList.length);
	}
	
	/**
	 * Test that advancedGet is not implemented
	 * @throws NotImplementedException
	 */
	@Test
	public void advancedGetTest() throws NotImplementedException {
		try {
		String[] testString = null;
		manager.advancedGet(adminSession, testString);
		}
		catch (NotImplementedException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionDetails = sw.toString();
			System.out.println(exceptionDetails);
			assertTrue(true);
		}
	}
	
	/**
	 * Test that advancedPut is not implemented
	 * @throws NotImplementedException
	 */
	@Test
	public void advancedPutTest() throws NotImplementedException {
		try {
		String[] testString1 = null;
		String testString2 = null;
		manager.advancedPut(adminSession, testString1, testString2);
		}
		catch (NotImplementedException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionDetails = sw.toString();
			System.out.println(exceptionDetails);
			assertTrue(true);
		}
	}
	
	/**
	 * Test that advancedPost is not implemented
	 * @throws NotImplementedException
	 */
	@Test
	public void advancedPostTest() throws NotImplementedException {
		try {
		String testString = null;
		manager.advancedPost(adminSession, testString, testString);
		}
		catch (NotImplementedException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionDetails = sw.toString();
			System.out.println(exceptionDetails);
			assertTrue(true);
		}
	}
}