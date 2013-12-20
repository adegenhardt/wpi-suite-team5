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
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
import edu.wpi.cs.wpisuitetng.modules.calendar.models.EventFilter;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.EventSorter;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * YearhViewTable handles creating and displaying the YearCalendar and all events on it
 *
 */
@SuppressWarnings("serial")
public class YearViewCalendar extends JXMonthView {
	
	
	
	// Milliseconds for day in Calendar class
	private static final long ONE_DAY = 86400000;
	// Day tab index in the tabbed pane
	private static final int DAY_TAB = 3; 
	private ActionListener calendarListener;
	private List<Event> events;
	private final JTabbedPane parentTab;
	
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
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				try {
					final Date startDate = thisInstance.getDayAtLocation(e.getX(), e.getY());
					thisInstance.setSelectionInterval(startDate, startDate);
				}
				catch (Exception x) {}
			}
		});
		
		this.addActionListener(calendarListener);
	}
	
	private void selected() {
		final SortedSet<Date> ds = this.getSelection();
		final Calendar selectDay = Calendar.getInstance();
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
		final DateInfo eventDay = new DateInfo(this.getCalendar());
		if (GlobalButtonVars.getInstance().isStateBothView()) {
			events = EventModel.getInstance().getUserEvents(ConfigManager.getConfig()
					.getUserName(), eventDay.getYear());
		}
		else if (GlobalButtonVars.getInstance().isStatePersonalView()) {
			events = EventModel.getInstance().getPersonalEvents(ConfigManager.getConfig()
					.getUserName(), eventDay.getYear());

		}
		else if (GlobalButtonVars.getInstance().isStateTeamView()) {
			events = EventModel.getInstance().getTeamEvents(ConfigManager.getConfig()
					.getUserName(), eventDay.getYear());
		}
		//Filter View's events by applied filters

		events = EventFilter.filterEventsByCategory(events, CategoryModel
				.getInstance().getAllNondeletedCategoriesAsFilters());

		events = EventSorter.sortEventsByDate(events);
	}
	
	/**
	 * refresh year
	 */
	public void refreshYear() {
		this.setFlaggedDates((Date[]) null);
		updateEvents();
		final List<Date> eventLongs = new ArrayList<Date>();
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
				eventLongs.add(new Date(startLong));
				startLong += ONE_DAY;
			}
			// Discount the last day as it ends at 11:59PM, technically.
			if (events.get(i).getEndDate().getHalfHour() == 0) {
				eventLongs.remove(eventLongs.size() - 1);
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
