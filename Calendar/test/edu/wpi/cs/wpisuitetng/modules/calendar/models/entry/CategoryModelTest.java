/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Test cases for the toString() method under DateInfo
 * 
 * Contributors: Team _
 ******************************************************************************/

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
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * @author Team_
 * @version 1.0
 */

public class CategoryModelTest {

	Category testCategory = new Category( "name", 10 );
	Category testCategory1 = new Category( "A", 11 );
	Category testCategory2 = new Category( "B", 12 );
	Category testCategory3 = new Category( "C", 13 );
	
	Category testCategory4 = new Category( "Alpha", 17, "Beta", false, true );
	Category testCategory5 = new Category( "Alpha1", 18, "Beta1", false, true );
	Category testCategory6 = new Category( "Alpha2", 19, "Beta2", false, true );
	Category testCategory7 = new Category( "Alpha3", 20, "Beta3", false, true );
	
	/**
	 * Set up the network and categories
	 */
	@Before
	public void setUp() {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		
		CategoryModel.getInstance().emptyModel();
		
		testCategory.setId( 0 );
		testCategory.setDeleted( false );
	}
	
	/**
	 * Test getting size
	 */
	@Test
	public void testGetSize() {
		assertEquals( 0, CategoryModel.getInstance().getSize() );
	}
	
	/**
	 * Test adding a category and removing it
	 */
	@Test
	public void testAddRemoveCategory() {
		CategoryModel.getInstance().addCategory( testCategory );
		assertEquals( 1, CategoryModel.getInstance().getSize() );
		
		CategoryModel.getInstance().removeCategory( testCategory.getId() );
		assertEquals( 0, CategoryModel.getInstance().getSize() );
	}

	/**
	 * Test adding a category and removing it
	 */
	@Test
	public void testGetCategory() {
		CategoryModel.getInstance().addCategory( testCategory );
		assertEquals( testCategory, CategoryModel.getInstance().getCategory( 0 ) );
	}
	
	/**
	 * Test emptying the model once something has been added
	 */
	@Test
	public void testEmptyModelWithActualData() {
		CategoryModel.getInstance().addCategory( testCategory );
		CategoryModel.getInstance().addCategory( testCategory1 );
		assertEquals( 2, CategoryModel.getInstance().getSize() );
		
		CategoryModel.getInstance().emptyModel();
		assertEquals( 0, CategoryModel.getInstance().getSize() );
	}
	
	/**
	 * Test getNextId
	 */
	@Test
	public void testGetNextID(){
		assertFalse( CategoryModel.getInstance().getNextID() == CategoryModel.getInstance().getNextID() );
		assertTrue( CategoryModel.getInstance().getNextID() + 1 == CategoryModel.getInstance().getNextID() );
	}
	
	/**
	 * Test getting elements of the model
	 * Turns out that the newest categories are in the front
	 */
	@Test
	public void testGetElementAt() {
		CategoryModel.getInstance().emptyModel();
		CategoryModel.getInstance().addCategory( testCategory );
		CategoryModel.getInstance().addCategory( testCategory1 );
		CategoryModel.getInstance().addCategory( testCategory2 );
		CategoryModel.getInstance().addCategory( testCategory3 );
		assertEquals( testCategory2, CategoryModel.getInstance().getElementAt( 1 ) );
		assertEquals( testCategory3, CategoryModel.getInstance().getElementAt( 0 ) );
	}
	
	/**
	 * Test getting all the categories
	 */
	@Test
	public void testGetAllCategories() {
		CategoryModel.getInstance().emptyModel();
		CategoryModel.getInstance().addCategory( testCategory1 );
		CategoryModel.getInstance().addCategory( testCategory );
		
		final List<Category> desiredCategories = new ArrayList<Category>();
		desiredCategories.add( testCategory1 );
		desiredCategories.add( testCategory );
		
		assertEquals( desiredCategories, CategoryModel.getInstance().getAllcategories() );
	}
	
	/**
	 * Test getting team categories
	 */
	@Test
	public void testGetTeamCategories() {
		CategoryModel.getInstance().emptyModel();
		CategoryModel.getInstance().addCategory( testCategory );
		CategoryModel.getInstance().addCategory( testCategory1 );
		CategoryModel.getInstance().addCategory( testCategory2 );
		CategoryModel.getInstance().addCategory( testCategory3 );
		CategoryModel.getInstance().addCategory( testCategory4 );
		CategoryModel.getInstance().addCategory( testCategory5 );
		CategoryModel.getInstance().addCategory( testCategory6 );
		CategoryModel.getInstance().addCategory( testCategory7 );
		
		String userId = ConfigManager.getConfig().getUserName();
		
		assertEquals( CategoryModel.getInstance().getTeamCategories( userId ).size(), 4 );
		assertTrue( CategoryModel.getInstance().getTeamCategories( userId ).contains(testCategory4) && 
				CategoryModel.getInstance().getTeamCategories( userId ).contains( testCategory5 ) &&
				CategoryModel.getInstance().getTeamCategories( userId ).contains( testCategory6 ) &&
				CategoryModel.getInstance().getTeamCategories( userId ).contains( testCategory7 ) &&
				!CategoryModel.getInstance().getTeamCategories( userId ).contains( testCategory ) &&
				!CategoryModel.getInstance().getTeamCategories( userId ).contains( testCategory1 ) &&
				!CategoryModel.getInstance().getTeamCategories( userId ).contains( testCategory2 ) &&
				!CategoryModel.getInstance().getTeamCategories( userId ).contains( testCategory3 ) );
	}
	
	/**
	 * Test getting user categories
	 */
	@Test
	public void testGetUserCategories() {
		CategoryModel.getInstance().emptyModel();
		CategoryModel.getInstance().addCategory( testCategory );
		CategoryModel.getInstance().addCategory( testCategory1 );
		CategoryModel.getInstance().addCategory( testCategory2 );
		CategoryModel.getInstance().addCategory( testCategory3 );
		CategoryModel.getInstance().addCategory( testCategory4 );
		CategoryModel.getInstance().addCategory( testCategory5 );
		CategoryModel.getInstance().addCategory( testCategory6 );
		CategoryModel.getInstance().addCategory( testCategory7 );
		
		String userId = ConfigManager.getConfig().getUserName();
		
		assertEquals( CategoryModel.getInstance().getuserCategories( userId ).size(), 8 );
		assertTrue( CategoryModel.getInstance().getuserCategories( userId ).contains(testCategory4) && 
				CategoryModel.getInstance().getuserCategories( userId ).contains( testCategory5 ) &&
				CategoryModel.getInstance().getuserCategories( userId ).contains( testCategory6 ) &&
				CategoryModel.getInstance().getuserCategories( userId ).contains( testCategory7 ) &&
				CategoryModel.getInstance().getuserCategories( userId ).contains( testCategory ) &&
				CategoryModel.getInstance().getuserCategories( userId ).contains( testCategory1 ) &&
				CategoryModel.getInstance().getuserCategories( userId ).contains( testCategory2 ) &&
				CategoryModel.getInstance().getuserCategories( userId ).contains( testCategory3 ) );
	}
	
	/**
	 * Test getting personal categories
	 */
	@Test
	public void testGetPersonalCategories() {
		CategoryModel.getInstance().emptyModel();
		CategoryModel.getInstance().addCategory( testCategory );
		CategoryModel.getInstance().addCategory( testCategory1 );
		CategoryModel.getInstance().addCategory( testCategory2 );
		CategoryModel.getInstance().addCategory( testCategory3 );
		CategoryModel.getInstance().addCategory( testCategory4 );
		CategoryModel.getInstance().addCategory( testCategory5 );
		CategoryModel.getInstance().addCategory( testCategory6 );
		CategoryModel.getInstance().addCategory( testCategory7 );
		
		String userId = ConfigManager.getConfig().getUserName();
		
		assertEquals( CategoryModel.getInstance().getPersonalCategories( userId ).size(), 4 );
		assertTrue( !CategoryModel.getInstance().getPersonalCategories( userId ).contains(testCategory4) && 
				!CategoryModel.getInstance().getPersonalCategories( userId ).contains( testCategory5 ) &&
				!CategoryModel.getInstance().getPersonalCategories( userId ).contains( testCategory6 ) &&
				!CategoryModel.getInstance().getPersonalCategories( userId ).contains( testCategory7 ) &&
				CategoryModel.getInstance().getPersonalCategories( userId ).contains( testCategory ) &&
				CategoryModel.getInstance().getPersonalCategories( userId ).contains( testCategory1 ) &&
				CategoryModel.getInstance().getPersonalCategories( userId ).contains( testCategory2 ) &&
				CategoryModel.getInstance().getPersonalCategories( userId ).contains( testCategory3 ) );
	}
	
	/**
	 * Test addcategories( Category[] ) method
	 */
	@Test
	public void testAddCategoriesWithArrayInputMethod() {
		
		CategoryModel.getInstance().emptyModel();
		
		Category[] categoryArray = { testCategory, testCategory1,
									 testCategory2, testCategory3,
									 testCategory4, testCategory5,
									 testCategory6, testCategory7 };
		
		CategoryModel.getInstance().addcategories( categoryArray );
	
		assertEquals( CategoryModel.getInstance().getAllcategories().size(), 8 );
		assertTrue( CategoryModel.getInstance().getAllcategories().contains(testCategory4) && 
				CategoryModel.getInstance().getAllcategories().contains( testCategory5 ) &&
				CategoryModel.getInstance().getAllcategories().contains( testCategory6 ) &&
				CategoryModel.getInstance().getAllcategories().contains( testCategory7 ) &&
				CategoryModel.getInstance().getAllcategories().contains( testCategory ) &&
				CategoryModel.getInstance().getAllcategories().contains( testCategory1 ) &&
				CategoryModel.getInstance().getAllcategories().contains( testCategory2 ) &&
				CategoryModel.getInstance().getAllcategories().contains( testCategory3 ) );
	}
}
