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


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.Component;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * YearViewCalendarPanel: A JPanel to display an entire year in
 * a traditional calendar format
 * @author Team_
 * @version 1.0
 *
 */
public class YearViewCalendarPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;

	private final YearViewCalendar calendarView;
	
	private final JButton nextYear;
	private final JButton prevYear;
	private final JButton year;
	
	private JTabbedPane parentTab;
	
	//Constructor for YearViewCalendarPanel
	public YearViewCalendarPanel(JTabbedPane _parentTab)
	{
		parentTab = _parentTab;
		// Create the main panel, will hold button and calendar panels
		final JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[924.00px]", "[30.00][500px]"));
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonPanel.setLayout(new MigLayout("", "[100px][][100px][][100px]", "[25px]"));
		
		// Define navigation buttons
		nextYear = new JButton("Next Year");
		year = new JButton("This Year");
		prevYear = new JButton("Previous Year");
		
		// Set the navigation buttons to the same size
		nextYear.setPreferredSize(prevYear.getPreferredSize());
		
		// Add the buttons to the panel
		buttonPanel.add(prevYear, "cell 0 0,grow");
		buttonPanel.add(year, "cell 2 0,grow");
		buttonPanel.add(nextYear, "cell 4 0,grow");
		
		// Add the button panel to the main panel
		contentPanel.add(buttonPanel, "cell 0 0,alignx center,aligny center");
		
		// Define the calendar panel
		final JPanel calendarPanel = new JPanel(new BorderLayout());
		calendarView = new YearViewCalendar(parentTab);
		calendarPanel.add(calendarView, BorderLayout.CENTER);
		contentPanel.add(calendarPanel, "cell 0 1,alignx left,aligny top");
		
		// Create the listeners for all buttons
		setupButtonListeners();
		// Set the view for the GUI
		this.setViewportView(contentPanel);
	}
	
	// Sets up the Button Listeners for each button when called
	// (called on creation)
	private void setupButtonListeners() {
		// Listener for Next button; calls a method to move
		// the calendar ahead one year
		nextYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextYear();
			}
		});
		// Listener for Previous button; calls a method to move
		// the calendar back one year
		prevYear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				previousYear();
			}
		});
		// Listener for This Year button; calls a method to move
		// the calendar back one year
		year.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				today();
			}
		});
	}
	
	// Moves the calendar in the view back by one year
	private void previousYear() {
		final Calendar cal = calendarView.getCalendar();
		// Subtract one year from the current year to move back one
		// The two sister methods use similar functionality
		cal.add(Calendar.YEAR, -1);
		calendarView.setFirstDisplayedDay(cal.getTime());
		calendarView.refreshYear();
	}
	
	// Moves the calendar in the view to the current year
	private void today() {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		calendarView.setFirstDisplayedDay(cal.getTime());
		calendarView.refreshYear();
	}
	
	// Moves the calendar in the view forward by one year
	private void nextYear() {
		final Calendar cal = calendarView.getCalendar();
		cal.add(Calendar.YEAR, +1);
		calendarView.setFirstDisplayedDay(cal.getTime());
		calendarView.refreshYear();
	}
	
	/**
	 * Returns the year view calendar
	
	 * @return the year view calendar */
	public YearViewCalendar getYearViewCalendar() {
		return calendarView;
	}
}
