package edu.wpi.cs.wpisuitetng.modules.calendar;


//this class is a suggested data structure that contains a date (in numerical information).  It would be used in areas to signify the date
//such as the start and end times of events, the date information of a year, month, or day object.
//I have made if in case it is useful, and am using it at least to the extent of yYearData, MonthData, and DayData

//a -1 for a parameter signifies that the date is only defined to the most specific point that is not a -1
////there is never a time when a point more specific, than a -1 containing info point, is not also -1
//ie year 20082 date info is (20082,-1,-1,-1)
//Critique this concept here:
//

public class DateInfo {
int year;
int month;
int day;
int halfHour;


public DateInfo (int year, int month, int day, int halfHour){
	this.year = year;
	this.month = month;
	this.day = day;
	this.halfHour = halfHour;
}
}
