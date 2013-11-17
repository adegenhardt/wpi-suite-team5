/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.models;

/**
 * @author Inferno505
 *
 */
public class YearData {
MonthData[] months;
DateInfo dateInfo;

public YearData(int year){
	//build months based on gregorean for given year
	this.dateInfo = new DateInfo (year, -1, -1, -1);
}
}
