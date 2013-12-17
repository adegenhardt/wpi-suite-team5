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

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * 
 * @author Team_
 * @version 1.0
 *
 */
public class CategoryTest {

	Category category1;
	Category category2;
	
	Category testCategory = new Category ("name", 10);
	
	/**
	 * Set up category1 and 2 as well as the network.
	 */
	@Before
	public void setUp() {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		
		category1 = new Category( "Category Name 1", 11 );
		
		category2 = new Category( "Category Name 2", 13 );
	}
	
	/**
	 * Make sure that both constructors work
	 */
	@Test
	public void testCreation() {
		assertNotNull( category1 );
		assertNotNull( category2 );
	}
	
	/**
	 * Test get and set for the object id field
	 */
	@Test
	public void testGetSetId() {
		assertEquals( 11, category1.getId() );
		assertNotSame( 44, category1.getId() );
		category1.setId( 44 );
		assertEquals( 44, category1.getId() );
	}
	
	/**
	 * Test get and set for the name field of category
	 */
	@Test
	public void testGetSetName() {
		assertEquals( "Category Name 1", category1.getName() );
		assertNotSame( "Category Name 2", category1.getName() );
		category1.setName( "Category Name 2" );
		assertEquals( "Category Name 2", category1.getName() );
	}
	
	/**
	 * Test toJson and fromJson methods
	 */
	@Test
	public void testJsonConversion() {
		final String convertedCategory = category1.toJSON();
		final Category categoryFromJson = Category.fromJson( convertedCategory );
		assertEquals( category1, categoryFromJson );
	}
	
	/**
	 * Test toString method
	 */
	@Test
	public void testToString() {
		assertEquals( "Category Name 1", category1.toString() );
	}
	
	/**
	 * Test the copyFrom method
	 */
	@Test
	public void testCopyFrom() {
		assertFalse( category1.equals( category2 ) );
		category1.copyFrom( category2 );
		assertTrue( category1.equals( category2 ) );
	}

	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals() {
		assertTrue( category1.equals( category1 ) );
	}
	
	/**
	 * Test the createNewCategory constructor method
	 */
	@Test
	public void testCreateNewCategory() {
		Category category = new Category( "Norman", 17 );
		category = category.createNewCategory( "name", 5, "Norman", false );
		assertTrue( category.getCreatorID().equals( "Norman" ));
		assertTrue( category.getName().equals( "name" ) );
		assertEquals( category.isTeamCat(), false );
		assertEquals( category.getId(), 5 );
	}
	
	/**
	 * Test the Category ( String, boolean ) constructor 
	 */
	@Test
	public void testCategoryConstructorStringAndBoolean() {
		Category category = new Category( "Norman", true );
		assertTrue( category.getCreatorID().
				equals( ConfigManager.getConfig().getUserName() ));
		assertTrue( category.getName().equals( "Norman" ) );
		assertEquals( category.isTeamCat(), true );
		assertEquals( category.getId(), 0 );
	}
	
	/**
	 * Test the SetName method if the input name does equal the value of
	 * the name field of Category.
	 */
	@Test
	public void testSetNameNotEqualToValueOfNameField() {
		Category category = new Category( "Norman", 17 );
		category = category.createNewCategory( "name", 5, "Norman", false );
		assertTrue( category.getName().equals( "name" ) );
		category.setName( "name" );
		assertTrue( category.getName().equals( "name" ) );
	}
	
	/**
	 * Test the SetName method if the input name is greater than 100
	 * characters long.
	 */
	@Test
	public void testSetNameGreaterThan100Characters() {
		Category category = new Category( "name", 17 );
		category.setName( "ANANANANANANANANANANANANANANAN"
				+ "ANANANANANANANANANANANANANANANANANANANANANANANAN"
				+ "ANANANANANANANANANANANAN" );
		assertFalse( category.getName().equals( "ANANANANANANANANANANANANANANAN"
				+ "ANANANANANANANANANANANANANANANANANANANANANANANAN"
				+ "ANANANANANANANANANANANAN" ) );
		assertTrue( category.getName().equals( "ANANANANANANANANANANANANANANAN"
				+ "ANANANANANANANANANANANANANANANANANANANANANANANAN"
				+ "ANANANANANANANANANANAN" ) );
	}
	
	/**
	 * Test the isDeleted method
	 */
	@Test
	public void testIsDeleted() {
		Category category = new Category( "name", 17 );
		category.setDeleted( true );
		assertTrue( category.isDeleted() );
		category.setDeleted( false );
		assertFalse( category.isDeleted() );
	}
	
	/**
	 * Test the hashCode method
	 */
	@Test
	public void testHashCode() {
		Category category = new Category( "Norman", 17 );
		Category category1 = new Category( "Norman", 17 );
		category = category.createNewCategory( "name", 5, "Norman", false );
		category = category1.createNewCategory( null, 5, "Norman", false );
		assertEquals( category.hashCode(), 1116 );
		assertEquals( category1.hashCode(), -1955877159 );
	}

	/**
	 * Test the equals method when the object provided is null
	 */
	@Test
	public void testEqualsWithNull() {
		Category category = new Category( "Norman", 17 );
		assertFalse( category.equals( null ) );
	}
	
	/**
	 * Test the equals method when the class is not the desired class
	 */
	@Test
	public void testEqualsWithWrongClass() {
		Category category = new Category( "Norman", 17 );
		Event event = new Event();
		assertFalse( category.equals( event ) );
	}
	
	/** 
	 * Test the equals method when the name field is null
	 */
	@Test
	public void testEqualsWithNullNameField() {
		Category category = new Category( null, 17 );
		Category category1 = new Category( "Nelson", 17 );
		assertFalse( category.equals( category1 ) );
	}
	
	/** 
	 * Test the equals method when both name fields are null
	 */
	@Test
	public void testEqualsWithBothNullNameFields() {
		Category category = new Category( null, 17 );
		Category category1 = new Category( null, 17 );
		assertTrue( category.equals( category1 ) );
	}
	
	/**
	 * Test the equals method when the name fields are not equal
	 */
	@Test
	public void testEqualsNamesNotSame() {
		Category category = new Category( "Dunlop", 17 );
		Category category1 = new Category( "Wilson", 17 );
		assertFalse( category.equals( category1 ) );
	}
	
	/**
	 * Test the identify method
	 */
	@Test
	public void testIdentify() {
		Category category = new Category( "Dunlop", 17 );
		assertEquals( category.identify( category ), null );

	}
}
