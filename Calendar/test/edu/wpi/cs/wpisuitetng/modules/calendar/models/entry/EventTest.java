package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DayData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.YearData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import junit.framework.TestCase;

/**
 * The class <code>EventTest</code> contains tests for the class {@link <code>Event</code>}
 * 
 * @pattern JUnit Test Case
 * 
 * @generatedBy CodePro at 11/24/13 1:25 AM
 * 
 * @author Inferno505
 * 
 * @version $Revision$
 */
public class EventTest extends TestCase {

	/**
	 * Method setUp.
	 * 
	 * @throws Exception
	 */
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
		Event event = new Event(1);

		assertEquals(1, event.getAbsoluteId());

		assertEquals("project", event.getProjectId());
		assertEquals("user", event.getUserId());
		assertEquals("project", event.getTypeId());
		assertEquals(false, event.getIsDeleted());

		assertEquals("event", event.getName());
		assertEquals("an event", event.getDescription());
		assertEquals(true,
				(new DateInfo(2000, 0, 0, 0)).equals(event.getStartDate()));
		assertEquals(new DateInfo(2000, 0, 0, 1), event.getEndDate());
		List<Category> category = new ArrayList<Category>();
		category.add(new Category("cName", "cDescription"));

		assertEquals(category, event.getCategory());
		List<String> participants = new ArrayList<String>();
		participants.add("user");

		assertEquals(participants, event.getParticipants());

		assertEquals(2000, event.getYear());
		assertEquals(0, event.getMonth());
		assertEquals(0, event.getDay());
		assertEquals(0, event.getHalfHour());

	}

	/**
	 * Test adding, getting, and removing years years
	 */
	// @Test
	/*
	 * public void testYears() { CalendarData cd = new CalendarData("a_name",
	 * "a_type", 13);
	 * 
	 * assertNull(cd.getYearData(1984));
	 * 
	 * cd.addYearData(1984); assertNotNull(cd.getYearData(1984));
	 * 
	 * int numYears = cd.getDataMap().size();
	 * 
	 * // Attempt to add same year again cd.addYearData(1984);
	 * assertEquals(numYears, cd.getDataMap().size());
	 * 
	 * // Make sure no unnecessary intermediate years added
	 * cd.addYearData(2101); assertNotNull(cd.getYearData(2101));
	 * assertFalse(cd.hasYearData(2100));
	 * 
	 * cd.removeYearData(1984); assertFalse(cd.hasYearData(1984));
	 * 
	 * // Attempt to remove the year again and ensure that no errors are thrown
	 * cd.removeYearData(1984); }
	 */

	/**
	 * Test whether or not a calendar can be copied from another one
	 */
	@Test
	public void testCopyFrom() {
		Event event1 = new Event(1);

		Event event2 = new Event(2);

		// First confirm that they are not equal
		assertNotSame(event1, event2);

		// Now copy and test that the results are equal
		event1.copyFrom(event2);

		assertEquals(event1, event2);
	}

	/**
	 * Test the conversion to and from JSON
	 */
	@Test
	public void testjSONConversion() {

		Event event = new Event(1);

		String jsonMessage = event.toJSON();
		Event eventAfter = Event.fromJson(jsonMessage);

		assertEquals(event.getName(), eventAfter.getName());
		assertEquals(event, event);
		// Test Why event vs evetAfter failed
		assertEquals(event.getAbsoluteId(), eventAfter.getAbsoluteId());
		assertEquals(event.getUserId(), eventAfter.getUserId());
		assertEquals(event.getProjectId(), eventAfter.getProjectId());
		assertEquals(event.getDay(), eventAfter.getDay());
		assertEquals(event.getYear(), eventAfter.getYear());
		assertEquals(event.getMonth(), eventAfter.getMonth());
		assertEquals(event.getHalfHour(), eventAfter.getHalfHour());
		assertEquals(event.getDescription(), eventAfter.getDescription());
		assertEquals(event.getIsDeleted(), eventAfter.getIsDeleted());
		assertEquals(event.getTypeId(), eventAfter.getTypeId());
		assertEquals(event.getCategory(), eventAfter.getCategory()); // category
																		// Equals
																		// was
																		// not
																		// set
																		// to
																		// string.equals(string),
																		// worked
																		// on
																		// other
																		// test
																		// due
																		// to
																		// same
																		// memory
																		// address
																		// location
		assertEquals(event.getEndDate(), eventAfter.getEndDate());
		assertEquals(event.getStartDate(), eventAfter.getStartDate());

		assertEquals(true, event.equals(eventAfter));

	}

	/**
	 * Test that CalendarData can be converted to a string
	 */
	@Test
	public void testToString() {
		Event event = new Event(1);
		assertEquals("event", event.toString());
	}
}
/*
 * $CPS$ This comment was generated by CodePro. Do not edit it. patternId =
 * com.instantiations.assist.eclipse.pattern.testCasePattern strategyId =
 * com.instantiations.assist.eclipse.pattern.testCasePattern.junitTestCase
 * additionalTestNames = assertTrue = false callTestMethod = true createMain =
 * true createSetUp = true createTearDown = true createTestFixture = false
 * createTestStubs = false methods = package =
 * edu.wpi.cs.wpisuitetng.modules.calendar.models.entry package.sourceFolder =
 * Calendar/src superclassType = junit.framework.TestCase testCase = EventTest
 * testClassType = edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event
 */