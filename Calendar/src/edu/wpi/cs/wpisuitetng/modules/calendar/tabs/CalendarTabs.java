package edu.wpi.cs.wpisuitetng.modules.calendar.tabs;

import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.*;

public class CalendarTabs extends JTabbedPane {
	private final MonthView monthPanel;

	public CalendarTabs() {
		// TODO Auto-generated constructor stub
		monthPanel = new MonthView();
		
		this.addTab("Month", monthPanel);
		
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
