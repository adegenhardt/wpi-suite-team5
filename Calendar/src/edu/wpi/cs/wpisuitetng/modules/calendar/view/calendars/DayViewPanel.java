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

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.awt.Rectangle;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Creates the Day View tab
 */
@SuppressWarnings("serial")
public class DayViewPanel extends JPanel {
	
	// Milliseconds for day in Calendar class
	private static final long ONE_DAY = 86400000; 
	
	private final DayView dayView;
	
	private final JPanel buttonsPanel;
	private final JButton prevDay;
	
	private final JButton currentDate;
	
	private final Calendar currentDateCal; 
	private final JButton nextDay;

	/**
	 * Create the panel.
	 */
	public DayViewPanel() {
		
		// This makes the blue highlighting work for some reason
		// Thanks Calendar class, you're the best
		currentDateCal = Calendar.getInstance(); 
		
		buttonsPanel = new JPanel();
		// Set the view constraints
		dayView = DayView.getInstance();
		dayView.refreshDay(currentDateCal);
		dayView.setMaximumSize(new Dimension(1000, 1000));
		dayView.setRequestFocusEnabled(false);
		dayView.setMinimumSize(new Dimension(5, 5));
		dayView.setBounds(new Rectangle(0, 0, 1000, 1000));
		
		// Create a button that displays the current date.
		// Clicking it will reset the view to the current date
		currentDate = new JButton("Today");
		currentDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final Calendar currentDisplay = Calendar.getInstance();
				dayView.refreshDay(currentDisplay);
			}
		});
		// Create a button to move the view back one day
		prevDay = new JButton("Previous Day");
		prevDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final Calendar currentDisplay = dayView.getRealDay();
				currentDisplay.setTimeInMillis(currentDisplay.getTimeInMillis() - ONE_DAY);
				dayView.refreshDay(currentDisplay);
			}
		});
		// Create a button to move the view forward one day
		nextDay = new JButton("Next Day");
		nextDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final Calendar currentDisplay = dayView.getRealDay();
				currentDisplay.setTimeInMillis(currentDisplay.getTimeInMillis() + ONE_DAY);
				dayView.refreshDay(currentDisplay);
			}
		});
		// Create a panel for the buttons
		setLayout(new MigLayout("", "[600px,grow 1000,center]", "[pref!][600px,grow 1000,fill]"));
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonsPanel.add(prevDay);
		buttonsPanel.add(currentDate);
		buttonsPanel.add(nextDay);
		
		// Add the button panel and the view
		this.add(buttonsPanel, "cell 0 0,alignx center,aligny top");
		this.add(dayView, "cell 0 1,grow");
		dayView.setLayout(new GridLayout(1, 0, 0, 0));
	}
}
