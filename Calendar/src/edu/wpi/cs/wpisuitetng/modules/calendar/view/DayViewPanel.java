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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

public class DayViewPanel extends JPanel {
	
	/**
	 * 
	 */
	// Millis for day in Calendar class
	// Going to use this to calculate first day of week
	private final static long ONE_DAY = 86400000; 
	
	private static final long serialVersionUID = 1L;

	private DayView dayView;
	
	private final JPanel buttonsPanel;
	
	private final JButton nextDay;
	private final JButton prevDay;
	
	private final JLabel currentDate;
	
	private Calendar currentDateCal; 

	/**
	 * Create the panel.
	 */
	public DayViewPanel() {
		
		// This makes the blue highlighting work for some reason
		// Thanks Calendar class, your the best
		currentDateCal = Calendar.getInstance(); 
		
		buttonsPanel = new JPanel(); 
		buttonsPanel.setLayout(new MigLayout());
		
		dayView = new DayView();
		dayView.refreshDay(currentDateCal);
		dayView.setMaximumSize(new Dimension(1000, 1000));
		dayView.setRequestFocusEnabled(false);
		dayView.setMinimumSize(new Dimension(5, 5));
		dayView.setBounds(new Rectangle(0, 0, 1000, 1000));
		currentDate = new JLabel(dayView.getStringDay());
		prevDay = new JButton("<");
		prevDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final Calendar currentDisplay = dayView.getRealDay();
				currentDisplay.setTimeInMillis(currentDisplay.getTimeInMillis() - ONE_DAY);
				dayView.refreshDay(currentDisplay);
				currentDate.setText(dayView.getStringDay());
			}
		});
		nextDay = new JButton(">");
		nextDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final Calendar currentDisplay = dayView.getRealDay();
				currentDisplay.setTimeInMillis(currentDisplay.getTimeInMillis() + ONE_DAY);
				dayView.refreshDay(currentDisplay);
				currentDate.setText(dayView.getStringDay());
			}
		});
		setLayout(new MigLayout("", "[600px,grow 1000,center]", "[100px][600px,grow 1000,fill]"));

		
		buttonsPanel.add(prevDay, "span 2");
		buttonsPanel.add(currentDate, "span 2");
		buttonsPanel.add(nextDay, "span 2");
		this.add(buttonsPanel, "cell 0 0,alignx center,aligny bottom");
		this.add(dayView, "cell 0 1,grow");
		dayView.setLayout(new GridLayout(1, 0, 0, 0));
	}
}
