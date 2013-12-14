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

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import javax.swing.JTabbedPane;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.SortEvents;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Creates the calendar for the Year View calendar tab
 */
@SuppressWarnings("serial")
public class YearViewCalendar extends JXMonthView {
	
	/* TODO: Don't know how, but possibly distinguish Events 
	 * and Commitments marked as different colors
	 * Read up on this, but it seems like its a hidden incomplete feature as of now
	 * I found the renderer to do so, not entirely sure how to use it may add later */
	
	// Milliseconds for day in Calendar class
	private static final long ONE_DAY = 86400000;
	// Day tab index in the tabbed pane
	private static final int DAY_TAB = 3; 
	private ActionListener calendarListener;
	private List<Event> events;
	private JTabbedPane parentTab;
	
	private static YearViewCalendar thisInstance = null;
	
	
	/**
	 * Constructor for YearViewCalendar.
	 * @param _parentTab
	 * @return instance
	 */
	public static YearViewCalendar getInstance(JTabbedPane _parentTab) {
		if (thisInstance == null) {
			thisInstance = new YearViewCalendar(_parentTab);
		}
		return thisInstance;
	}
	
	
	private YearViewCalendar(JTabbedPane _parentTab) {
		parentTab = _parentTab;
		
		buildYearView();
		buildActionListeners(); 
	}
	
	// Create a year view
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
	// Create an action listener for the year view
	private void buildActionListeners() {
		calendarListener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				selected();
			}
		};
		
		this.addActionListener(calendarListener);
	}
	
	private void selected() {
		final SortedSet<Date> ds = this.getSelection();
		Calendar selectDay = Calendar.getInstance();
		selectDay.setTime(ds.first());
        selectDay.set(Calendar.HOUR_OF_DAY, 0);
        selectDay.set(Calendar.MINUTE, 0);
        selectDay.set(Calendar.SECOND, 0);
        selectDay.set(Calendar.MILLISECOND, 0);
		DayView.getInstance().refreshDay(selectDay);
		parentTab.setSelectedIndex(DAY_TAB);
	}
	
	// Simple calculation to set the calendar view like a normal calendar
	private Date firstDay() {
		final Calendar tempCal = Calendar.getInstance();
		tempCal.set(Calendar.DAY_OF_YEAR, 1);
		return tempCal.getTime();
	}
	
	private void updateEvents() {
		DateInfo eventDay = new DateInfo(this.getCalendar());
		if (GlobalButtonVars.getInstance().isPersonalView && GlobalButtonVars.getInstance().isTeamView) {
			events = EventModel.getInstance().getUserEvents(ConfigManager.getConfig().getUserName(), eventDay.getYear());
		}
		else if (GlobalButtonVars.getInstance().isPersonalView) {
			events = EventModel.getInstance().getPersonalEvents(ConfigManager.getConfig().getUserName(), eventDay.getYear());

		}
		else if (GlobalButtonVars.getInstance().isTeamView) {
			events = EventModel.getInstance().getTeamEvents(ConfigManager.getConfig().getUserName(), eventDay.getYear());
		}
		events = SortEvents.sortEventsByDate(events);
	}
	
	/**
	 * refresh year
	 */
	public void refreshYear() {
		this.setFlaggedDates((Date[]) null);
		updateEvents();
		List<Date> eventLongs = new ArrayList<Date>();
		for (int i = 0; i < events.size(); i++) {
			Calendar startDate = events.get(i).getStartDate().dateInfoToCalendar();
			Calendar endDate = events.get(i).getEndDate().dateInfoToCalendar();
	        startDate.set(Calendar.HOUR_OF_DAY, 0);
	        startDate.set(Calendar.MINUTE, 0);
	        startDate.set(Calendar.SECOND, 0);
	        startDate.set(Calendar.MILLISECOND, 0);
	        endDate.set(Calendar.HOUR_OF_DAY, 0);
	        endDate.set(Calendar.MINUTE, 0);
	        endDate.set(Calendar.SECOND, 0);
	        endDate.set(Calendar.MILLISECOND, 0);
			Long startLong = startDate.getTimeInMillis();
			Long endLong = endDate.getTimeInMillis();
			while (startLong <= endLong) {
				// TODO: Figure out a way to discount marking days
				// That end at 12:00AM
				eventLongs.add(new Date(startLong));
				startLong += ONE_DAY;
			}
		}
		for (int k = 0; k < eventLongs.size(); k++) {
			this.addFlaggedDates(eventLongs.get(k));
		}
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		refreshYear();
	}

}
