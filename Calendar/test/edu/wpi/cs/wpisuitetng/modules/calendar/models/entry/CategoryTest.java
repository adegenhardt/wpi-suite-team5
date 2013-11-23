package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class CategoryTest {

	Category category1;
	Category category2;
	
	Category testCategory = new Category( "name", 10 );
	
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
	
	/*
	 * Test get and set for the object id field
	 */
	@Test
	public void testGetSetId() {
		assertEquals( 11, category1.getId() );
		assertNotSame( 44, category1.getId() );
		category1.setId( 44 );
		assertEquals( 44, category1.getId() );
	}
	
	/*
	 * Test get and set for the name field of category
	 */
	@Test
	public void testGetSetName() {
		assertEquals( "Category Name 1", category1.getName() );
		assertNotSame( "Category Name 2", category1.getName() );
		category1.setName( "Category Name 2" );
		assertEquals( "Category Name 2", category1.getName() );
	}

}
