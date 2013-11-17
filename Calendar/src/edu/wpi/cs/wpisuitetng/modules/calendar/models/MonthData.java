package edu.wpi.cs.wpisuitetng.modules.calendar.models;

public class MonthData {
	// parameter for moth's date date ie this object is March of year 2082
	DateInfo dateInfo;
	DayData[] days;

	public MonthData(int year, int month) {
		// build days based on gregorean for given month 0-11 = January-December
		this.dateInfo = new DateInfo(year, month, -1, -1);
	}
}
