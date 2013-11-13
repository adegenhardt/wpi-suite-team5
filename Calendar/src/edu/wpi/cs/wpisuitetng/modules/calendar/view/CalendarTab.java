/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Underscore 
 *    
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view;


import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.*;

public class CalendarTab extends JTabbedPane {
	private final MonthView monthPanel;
	private final YearViewCalendarPanel yearPanel;
	
	
	
	public CalendarTab() {
		monthPanel = new MonthView();
		yearPanel= new YearViewCalendarPanel();
		
		this.addTab("Year", yearPanel);
		this.addTab("Month", monthPanel);
		
		
		
	}
}
