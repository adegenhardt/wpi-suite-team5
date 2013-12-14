/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team _
 *    
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarSidebar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.tabs.CalendarTab;

import java.awt.BorderLayout;

/**
 * JPanel for the main Calendar view
 * 
 * @author Team Underscore
 * @version 1.0
 *
 */
@SuppressWarnings({"serial"})
public class CalendarPanel extends JPanel {
	// Variables for CalenderPanel components
	private final CalendarTab tabs;
	private final CalendarSidebar sidebar; 
	private final JSplitPane splitPane;
	private final JPanel panelSide, panelCalendar;
	private final JPanel mainPanel;
	
	// Default constructor for a CalenderPanel
	public CalendarPanel() {
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		panelSide = new JPanel();
		panelSide.setLayout(new BorderLayout());
		panelCalendar = new JPanel();
		panelCalendar.setLayout(new BorderLayout());
		
		
		// Adding the in progress sidebar
		// Should look into the JSplitPane for this 
		sidebar = CalendarSidebar.getInstance();
		panelSide.add(sidebar);
				
		tabs = new CalendarTab();
		panelCalendar.add(tabs);
		
		//Provide minimum sizes for the two components in the split pane
		final Dimension minimumSize = new Dimension(10, 5);
		sidebar.setMinimumSize(minimumSize);
		tabs.setMinimumSize(minimumSize);
		setLayout(new BorderLayout(0, 0));
				
		
		//Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		                           panelSide, panelCalendar);
		splitPane.setResizeWeight(.5);
		splitPane.setBorder(null);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);

		
		mainPanel.add(splitPane);
		mainPanel.setBorder(null);
		this.add(mainPanel);
		
		
		
		
		}
	// Getter for CalenderTab
	public CalendarTab getCalendarTab () {
		return tabs;
	}
}
