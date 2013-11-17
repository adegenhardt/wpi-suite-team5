/**
 * This controller responds when the user updates calendar data.
 * Adapted from AddRequirementController.java
 */
package edu.wpi.cs.wpisuitetng.modules.calendar;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddCalendarDataController {
	private static AddCalendarDataController instance;
	private AddCalendarDataRequestObserver observer;
	
	private AddCalendarDataController(){
		observer = new AddCalendarDataRequestObserver(this);
	}

	/**
	 * @return the instance of the AddCalendarDataController or creates one
	 *         if it does not exist.
	 */
	public static AddCalendarDataController getInstance(){
		if(instance == null){
			instance = new AddCalendarDataController();
		}
		return instance;
	}
	
	/**
	 * This method adds a piece of calendar data to the server
	 * @return newCalendarData will be added to the server
	 */
	public void addCalendarData(CalendarData newCalendarData){
		final Request request = Network.getInstance()
				.makeRequest("calendar/calendardata", HttpMethod.PUT); 
		// PUT == create
		request.setBody(newCalendarData.toJSON()); 
		// put the new data in the body of the request
		request.addObserver(observer); 
		// add an observer to process the response
		request.send(); 
	}
}
