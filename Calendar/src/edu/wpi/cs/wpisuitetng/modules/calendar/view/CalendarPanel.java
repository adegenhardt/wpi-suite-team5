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

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;

/**
 * This class is a JPanel. 
 * 
 * @author Team 5 (B13)
 * @version 1.0
 *
 */
@SuppressWarnings({"serial"})
public class CalendarPanel extends JPanel {
	private final CalendarTab tabs;
	private final CalendarSidebar sidebar; 
	private final JSplitPane splitPane;
	private final JPanel panel1, panel2;
	private final JPanel mainPanel;
	

	public CalendarPanel() {
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		
		
		// Adding the in progress sidebar
		// Should look into the JSplitPane for this 
		sidebar = new CalendarSidebar();
		panel1.add(sidebar);
				
		tabs = new CalendarTab();
		panel2.add(tabs);
		
		//Provide minimum sizes for the two components in the split pane
		final Dimension minimumSize = new Dimension(10, 5);
		sidebar.setMinimumSize(minimumSize);
		tabs.setMinimumSize(minimumSize);
		setLayout(new BorderLayout(0, 0));
				
		
		//Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		                           panel1, panel2);
		splitPane.setResizeWeight(.5);
		splitPane.setBorder(null);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(.25);

		
		mainPanel.add(splitPane);
		mainPanel.setBorder(null);
		this.add(mainPanel);
		
		
		
		
		}
}
