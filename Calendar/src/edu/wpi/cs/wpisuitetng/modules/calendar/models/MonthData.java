package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import edu.wpi.cs.wpisuitetng.modules.calendar.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MonthData {
	// parameter for moth's date date ie this object is March of year 2082
	DateInfo dateInfo;
	DayData[] days;

	public MonthData(int year, int month) {
		// build array of months
		Calendar refCal = new GregorianCalendar(this.getDateInfo().getYear(),
				this.getMonth(), 1);
		int totalDays = refCal.getActualMaximum(1);
		for (int i = 0; i < totalDays; i++) {
			days[i] = new DayData(year, month, i);
		}

		// build days based on gregorean for given month 0-11 = January-December
		this.dateInfo = new DateInfo(year, month, -1, -1);
	}

	public DateInfo getDateInfo() {
		return this.dateInfo;
	}

	public int getMonth() {
		return dateInfo.getMonth();

	}

	public DayData[] getDays() {
		return this.days;
	}

	public void addEvent(Event event, DateInfo eventStoreDateInfo) {

		Calendar refCal = new GregorianCalendar(this.getDateInfo().getYear(),
				this.getMonth(), 1);
		int totalDays = refCal.getActualMaximum(1);
		boolean dayFound = false;
		for (int i = 0; i <= totalDays; i++) {
			if (this.days[i].getDay() == eventStoreDateInfo.getDay()) {
				dayFound = true;
				this.days[i].addEvent(event);
				break;
			}

		}
		if (dayFound == false) {
			// TODO exception the event has an invalid day
		}
		// TODO Indication of event added to year

	}
	public void removeEvent(Event event, DateInfo eventStoreDateInfo) {
		Calendar refCal = new GregorianCalendar(this.getDateInfo().getYear(),
				this.getMonth(), 1);
		int totalDays = refCal.getActualMaximum(1);
		boolean dayFound = false;
		for (int i = 0; i <= totalDays; i++) {
			if (this.days[i].getDay() == eventStoreDateInfo.getDay()) {
				dayFound = true;
				this.days[i].removeEvent(event);
				break;
			}

		}
		if (dayFound == false) {
			// TODO exception the event has an invalid day
		}
		// TODO Indication of event added to year

		
	}
	
	public List<Event> getMonthEvents(DateInfo dateRegion) {

		List<Event> dayEvents = new ArrayList<Event>();
		List<Event> monthEvents = new ArrayList<Event>();

		// Set totalDays based on gregorian for month
		Calendar refCal = new GregorianCalendar(dateRegion.getYear(),
				this.getMonth(), 1);
		int totalDays = refCal.getActualMaximum(1);
		for (int i = 1; i <= totalDays; i++) {
			dayEvents = days[i].getDayEvents(dateRegion);
			monthEvents.addAll(dayEvents);
		}

		return monthEvents;
	}

	

}
