package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.MockNetwork;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests the jSON conversion functions and all the getters and setters
 * for the CalendarData class
 * 
 * @author srkodzis
 * @version $Revision: 1.0 $
 */
public class CalendarDataTest {

	/**
	 * Method setUp.
	
	 * @throws Exception */
	@Before
	public void setUp() throws Exception {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
	}	
	
	
	/**
	 * Test the simple getter fields for CalendarData
	 */
	@Test
	public void testGetters() {
		CalendarData cd = new CalendarData( "a_name", "a_type", 13 );
		
		assertEquals( "a_name", cd.getName() );
		assertEquals( "a_type", cd.getType() );
		assertEquals( 13, cd.getId() );
		assertEquals( 0, cd.getDataMap().size() );
	}
	
	/**
	 * Test adding, getting, and removing years years
	 */
	@Test
	public void testYears() {
		CalendarData cd = new CalendarData( "a_name", "a_type", 13 );
		
		assertNull( cd.getYearData( 1984 ) );
		
		cd.addYearData( 1984 );
		assertNotNull( cd.getYearData( 1984 ) );
		
		int numYears = cd.getDataMap().size();
		
		// Attempt to add same year again
		cd.addYearData( 1984 );
		assertEquals( numYears, cd.getDataMap().size() );
		
		// Make sure no unnecessary intermediate years added
		cd.addYearData( 2101 );
		assertNotNull( cd.getYearData( 2101 ) );
		assertFalse( cd.hasYearData( 2100 ) );
		
		cd.removeYearData( 1984 );
		assertFalse( cd.hasYearData( 1984 ) );
		
		// Attempt to remove the year again and ensure that no errors are thrown
		cd.removeYearData( 1984 );
	}
	
	/**
	 * Test whether or not a calendar can be copied from another one
	 */
	@Test
	public void testCopyFrom() {
		CalendarData cd1 = new CalendarData( "a_name", "a_type", 13 );
		cd1.addYearData( 1984 );
		CalendarData cd2 = new CalendarData();
		
		
		// First confirm that they are not equal
		assertNotSame( cd1.getName(), cd2.getName() );
		assertNotSame( cd1.getType(), cd2.getType() );
		assertNotSame( cd1.getId(), cd2.getId() );
		assertNotSame( cd1.getDataMap().size(), cd2.getDataMap().size() );
		
		// Test that the map contents are not the same
		cd2.addYearData( 2100 );
		assertNotSame( cd1.getDataMap(), cd2.getDataMap() );
		
		// Add an event for testing
		Date startTime = new Date();
		startTime.setDate( 1 );
		startTime.setYear( 1984 );
		startTime.setMonth( 2 );
		
		Date endTime = new Date();
		endTime.setDate( 1 );
		endTime.setYear( 1984 );
		endTime.setMonth( 2 );
		
		Event event = new Event( 1, "name1", "desc1", startTime, endTime );
		
		YearData year1 = cd1.getYearData( 1984 );
		year1.addEvent( event );
		
		// Now copy and test that the results are equal
		cd2.copyFrom( cd1 );
		
		assertEquals( cd1.getName(), cd2.getName() );
		assertEquals( cd1.getType(), cd2.getType() );
		assertEquals( cd1.getDataMap(), cd2.getDataMap() );
		
		// Test that the event is also transferred
		DayData[] days1 = year1.getMonth( 2 ).getDays();
		List< Event > retrievedEvents1 = days1[ 1 ].getDayEvents();
		
		YearData year2 = cd2.getYearData( 1984 );
		DayData[] days2 = year2.getMonth( 2 ).getDays();
		List< Event > retrievedEvents2 = days2[ 1 ].getDayEvents();
		
		assertEquals( retrievedEvents1, retrievedEvents2 );
	}
	
	/**
	 * Test the conversion to and from JSON
	 */
	@Test
	public void testjSONConversion() {
		
		CalendarData cdOriginal = new CalendarData( "a_name", "a_type", 13 );
		cdOriginal.addYearData( 1984 );
		cdOriginal.addYearData( 2100 );

		// Add an event for testing
		Date startTime = new Date();
		startTime.setDate( 1 );
		startTime.setYear( 1984 );
		startTime.setMonth( 2 );
		
		Date endTime = new Date();
		endTime.setDate( 1 );
		endTime.setYear( 1984 );
		endTime.setMonth( 2 );
		
		Event event = new Event( 1, "name1", "desc1", startTime, endTime );
		
		YearData year1 = cdOriginal.getYearData( 1984 );
		year1.addEvent( event );
		
		String jsonMessage = cdOriginal.toJSON();
		CalendarData cdNew = CalendarData.fromJson(jsonMessage);
		
		assertEquals( cdOriginal.getName(), cdNew.getName() );
		assertEquals( cdOriginal.getType(), cdNew.getType() );
		assertEquals( cdOriginal.getId(), cdNew.getId() );
		assertEquals( cdOriginal.getDataMap().size(),
				cdNew.getDataMap().size() );
		assertNotNull( cdNew.getYearData( 1984 ) );
		assertNotNull( cdNew.getYearData( 2100 ) );
		
		// Test that the event is also transferred
		DayData[] days1 = year1.getMonth( 2 ).getDays();
		List< Event > retrievedEvents1 = days1[ 0 ].getDayEvents();
		
		YearData year2 = cdNew.getYearData( 1984 );
		DayData[] days2 = year2.getMonth( 2 ).getDays();
		List< Event > retrievedEvents2 = days2[ 0 ].getDayEvents();
		//these were changed to date functions
		assertEquals( retrievedEvents1.size(), retrievedEvents2.size() );
		assertEquals( retrievedEvents1.get( 0 ).getStartDate().getHalfHour(),
				      retrievedEvents2.get( 0 ).getStartDate().getHalfHour() );
		
		
	}
	
	/**
	 * Test that CalendarData can be converted to a string
	 */
	@Test
	public void testToString() {
		CalendarData cd = new CalendarData( "a_name", "a_type", 13 );
		
		assertEquals( "a_name", cd.toString() );
	}
}
