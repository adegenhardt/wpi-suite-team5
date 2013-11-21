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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;

 import org.jdesktop.swingx.JXMonthView;
 import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Creates the calendar for the Year View calendar tab
 */
public class YearViewCalendar extends JXMonthView {
	private static final long serialVersionUID = 1L;
	
	/* Don't know how, but possibly distinguish Events and Commitments marked as different colors
	 * Read up on this, but it seems like its a hidden incomplete feature as of now
	 * I found the renderer to do so, not entirely sure how to use it may add later */
	private static final Color EVENT_DAY  = Color.green;
	private static final Color COM_DAY = Color.cyan;
	private ActionListener doCalStuff;
	
	/**
	 * Constructor for YearViewCalendar.
	 */
	public YearViewCalendar() {
		buildYearView();
		buildActionListeners(); 
	}
	
	private void buildYearView() {
		// Set up for a 12month Calendar view 
		this.setPreferredColumnCount(4);
		this.setPreferredRowCount(3);
		this.setShowingLeadingDays(true);
		this.setFirstDayOfWeek(Calendar.SUNDAY);
		// Change this if you want to be able to select more than one day
		this.setSelectionMode(SelectionMode.SINGLE_SELECTION);
		this.setFirstDisplayedDay(firstDay());
	}
	
	private void buildActionListeners() {
		doCalStuff = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				selected();
			}
		};
		
		this.addActionListener(doCalStuff);
	}
	
	// Here we can handle the selection of a day in year view
	// Change this function to do what is needed (right now it prints the
	// Date selected to console
	private void selected() {
		final SortedSet<Date> ds = this.getSelection();
		System.out.println(ds.first().toString());
	}
	
	// Simple calculation to set the calendar view like a normal calendar
	private Date firstDay() {
		final Calendar tempCal = Calendar.getInstance();
		tempCal.set(Calendar.DAY_OF_YEAR, 1);
		return tempCal.getTime();
	}

}
