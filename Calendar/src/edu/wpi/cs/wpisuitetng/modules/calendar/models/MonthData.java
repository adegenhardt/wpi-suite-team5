package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import edu.wpi.cs.wpisuitetng.modules.calendar.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

public class MonthData {
	// parameter for moth's date date ie this object is March of year 2082
	DateInfo dateInfo;

	DayData[] days;

	public MonthData(int year, int month) {
		// TODO: Add error handling if the month is out of range [1, 12]
		
		this.dateInfo = new DateInfo(year, month, -1, -1);
		createDays( year, month );
	}
	
	/**
	 * Create the days of the month
	 * @param year The year containing the month
	 * @param month The number value of the month itself
	 */
	private void createDays( int year, int month ) {
		int numDays;                      /* number of days in the month */
		
		// Months with 31 days
		if ( month == 0 || month == 2 || month == 4 || month == 6 ||
				month == 7 || month == 9 || month == 11 ) {
			numDays = 31;
		} else if ( month == 1 ) { // February
			if ( year % 4 == 0 &&
				( !( year % 100 == 0 ) ||
					( year % 400 == 0 ) ) ) { //leap year
				numDays = 29;
			} else {
				numDays = 28;
			}
		} else { //Otherwise, month has 30 days
			numDays = 30;
		}
		
		days = new DayData[ numDays ];
		for ( int i = 0; i < numDays; i++ ) {
			days[ i ] = new DayData( year, month, i );
		}
				
	}
	
	/**
	 * 
	 * @return the days of the month
	 */
	public DayData[] getDays() {
		return days;
	}

	/**
	 * Add an event to the MonthData
	 * @param event the Event to be added
	 */
	public void addEvent( Event event ) {
		int eventDay = event.getStartTime().getDate();
		
		days[ eventDay ].addEvent( event );
		
	}

}
