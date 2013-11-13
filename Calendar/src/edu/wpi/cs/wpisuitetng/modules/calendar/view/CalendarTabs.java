package edu.wpi.cs.wpisuitetng.modules.calendar.tabs;

import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.*;

public class CalendarTabs extends JTabbedPane {
	private final MonthView monthPanel;
	private final MonthView monthPanel2; // these next three should become the other views
	private final MonthView monthPanel3;
	private final MonthView monthPanel4;
	
	
	public CalendarTabs() {
		// TODO Auto-generated constructor stub
		monthPanel = new MonthView();
		monthPanel2 = new MonthView(); // these next three should be the other views when implemented
		monthPanel3 = new MonthView();
		monthPanel4 = new MonthView();
		
		this.addTab("Year", monthPanel2);
		this.addTab("Month", monthPanel);
		this.addTab("Week", monthPanel3);
		this.addTab("Day", monthPanel4);
		
		
	}
/*
	public CalendarTabs(int tabPlacement) {
		super(tabPlacement);
		// TODO Auto-generated constructor stub
	}

	public CalendarTabs(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
		// TODO Auto-generated constructor stub
	}
*/
}
