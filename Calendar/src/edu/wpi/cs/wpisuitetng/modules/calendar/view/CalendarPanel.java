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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

/**
 * This class is a JPanel. 
 * 
 * @author Team 5 (B13)
 * @version 1.0
 *
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked" })
public class CalendarPanel extends JPanel {
	private CalendarTab tabs;

	public CalendarPanel() {
		setLayout(new BorderLayout(0, 0));
		
		tabs = new CalendarTab();
		this.add(tabs, BorderLayout.CENTER);
	}
}
