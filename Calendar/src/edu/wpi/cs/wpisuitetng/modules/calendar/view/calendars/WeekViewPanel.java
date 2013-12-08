/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.FlowLayout;

// Panel to hold a WeekView
@SuppressWarnings("serial")
public class WeekViewPanel extends JPanel {
	
	private WeekView weekView;
	
	private final JPanel buttonsPanel;
	
	private final JButton nextDay;
	private final JButton prevDay;
	
	private final JButton currentDate;

	/**
	 * Create the panel.
	 */
	public WeekViewPanel() {
		
		// Create a panel for the buttons
		buttonsPanel = new JPanel();
		// Create the WeekView
		weekView = new WeekView();
		
		// Create navigation buttons for the Current, Previous, and Next weeks
		currentDate = new JButton("Current Week");
		currentDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				weekView.currentWeek();
			}
		});
		prevDay = new JButton("Previous Week");
		prevDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				weekView.backWeek();
			}
		});
		nextDay = new JButton("Next Week");
		nextDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				weekView.forwardWeek();
			}
		});
		
		// Set the layout
		setLayout(new MigLayout("", "[638px,grow]", "[40:n:40,grow][247px,grow]"));
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		// Add buttons to the panel
		buttonsPanel.add(prevDay);
		buttonsPanel.add(currentDate);
		buttonsPanel.add(nextDay);
		this.add(buttonsPanel, "cell 0 0,alignx center,growy");
		// Add the view
		this.add(weekView, "cell 0 1,grow");
	}
}
