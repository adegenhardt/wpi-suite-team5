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
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class WeekViewPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WeekView weekView;
	
	private final JPanel buttonsPanel;
	
	private final JButton nextDay;
	private final JButton prevDay;
	
	private final JButton currentDate;

	/**
	 * Create the panel.
	 */
	public WeekViewPanel() {
		
		buttonsPanel = new JPanel();
		
		weekView = new WeekView();
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
		setLayout(new MigLayout("", "[638px,grow]", "[40:n:40,grow][247px,grow]"));
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		
		buttonsPanel.add(prevDay);
		buttonsPanel.add(currentDate);
		buttonsPanel.add(nextDay);
		this.add(buttonsPanel, "cell 0 0,alignx center,growy");
		this.add(weekView, "cell 0 1,grow");
	}

}
