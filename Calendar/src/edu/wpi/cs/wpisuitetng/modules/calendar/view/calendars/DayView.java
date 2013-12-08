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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Color;
import java.awt.Component;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;

/**
 * Panel for the Day View tab of the calendar
 * 
 * @author Team Underscore
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class DayView extends JPanel {
//HEAD
	private JScrollPane dayScroll;
	private JTable dayTable;
	private Calendar currentDay;
	private Calendar realDay;

	// Variables used to disallow time column selection
	private final int disabledColumn = 0;
	private int currentColumn = 1;

	// String date format that the Day View will give
	private final DateFormat dayFormat = new SimpleDateFormat("MMM/dd/yy");

	// List of events that take place on the current realDay, sorted according to TODO Connor's sorting method specifications
	private ArrayList<Event> realDayEvents = new ArrayList<Event>();

	/**
	 * Create the panel.
	 * 
	 * @param isWeek
	 *            boolean
	 */
	public DayView() {
		// Run these methods to create this view
		initDay();
		createControls();
		addElements();
		createBounds();
		createBackground();
		createTableProperties();
		createUnselectableCol();
		colorCurrentDate();

		// additions
		// call refresh function for currentDay to build list
	}

	// Create the table of DayView
	private void createControls() {
		dayTable = new JTable(new DefaultTableModel(new Object[][] {
				{ "Midnight", null }, { "", null }, { "1:00", null },
				{ "", null }, { "2:00", null }, { "", null }, { "3:00", null },
				{ "", null }, { "4:00", null }, { "", null }, { "5:00", null },
				{ "", null }, { "6:00", null }, { "", null }, { "7:00", null },
				{ "", null }, { "8:00", null }, { "", null }, { "9:00", null },
				{ "", null }, { "10:00", null }, { "", null },
				{ "11:00", null }, { "", null }, { "12:00", null },
				{ "", null }, { "1:00", null }, { "", null }, { "2:00", null },
				{ "", null }, { "3:00", null }, { "", null }, { "4:00", null },
				{ "", null }, { "5:00", null }, { "", null }, { "6:00", null },
				{ "", null }, { "7:00", null }, { "", null }, { "8:00", null },
				{ "", null }, { "9:00", null }, { "", null },
				{ "10:00", null }, { "", null }, { "11:00", null },
				{ "", null }, }, new String[] { "", this.getStringDay() }) {
			// Do not allow the cells to be editable
			private final boolean[] columnEditables = new boolean[] { false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		// Set the view constraints and appearance
		dayTable.setAutoCreateColumnsFromModel(false);
		dayTable.getColumnModel().getColumn(0).setResizable(false);
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(43);
		dayTable.getColumnModel().getColumn(0).setMinWidth(30);
		dayTable.getColumnModel().getColumn(0).setMaxWidth(43);
		dayTable.getColumnModel().getColumn(1).setResizable(false);
		dayTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		dayTable.setSelectionBackground(Color.GREEN);
		dayTable.setDefaultRenderer(Object.class,
				new DefaultTableCellRenderer() {

					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						final DefaultTableCellRenderer rendererComponent = (DefaultTableCellRenderer) super
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);

						if ((row % 2) == 0 && column != 0) {
							rendererComponent.setBackground(new Color(185, 209,
									234));
						} else {
							rendererComponent.setBackground(Color.white);
						}
						return rendererComponent;
					}
				});

		final JTableHeader header = dayTable.getTableHeader();

		header.setDefaultRenderer(new DefaultTableCellRenderer() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {

				final DefaultTableCellRenderer rendererComponent = (DefaultTableCellRenderer) super
						.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
				rendererComponent.setBackground(UIManager
						.getColor(JTableHeader.class));
				return rendererComponent;
			}
		});
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		dayTable.getColumnModel().getColumn(0).setMinWidth(55);
		dayTable.getColumnModel().getColumn(0).setMaxWidth(55);
		dayScroll = new JScrollPane(dayTable);

	}

	// Add a scroll bar
	private void addElements() {
		setLayout(new BorderLayout(0, 0));
		this.add(dayScroll);
	}

	// Set the bounds
	private void createBounds() {
		this.setBounds(0, 0, 626, 600);
	}

	// Set a background
	private void createBackground() {
		dayTable.getParent().setBackground(dayTable.getBackground());
	}

	private void createTableProperties() {
		// No resize or reorder
		dayTable.getTableHeader().setResizingAllowed(true);
		dayTable.getTableHeader().setReorderingAllowed(false);

		// Multiple cell selection
		dayTable.setColumnSelectionAllowed(true);
		dayTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Set no row and single column count
		// Should I be setting the row height here?
		dayTable.setRowHeight(15);
	}

	private void createUnselectableCol() {
		final ListSelectionModel sel = dayTable.getColumnModel()
				.getSelectionModel();
		sel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// If the column is disabled, reselect previous column
				if (sel.isSelectedIndex(disabledColumn)) {
					sel.setSelectionInterval(currentColumn, currentColumn);
				}
				// Set current selection
				else
					currentColumn = sel.getMaxSelectionIndex();
			}
		});
	}

	// Method to color in today's date if the view
	// Is currently on today's date
	// For some reason this doesn't work with the
	// Day view panel, I'll look into this when it
	// Isn't 2:00AM
	private void colorCurrentDate() {
		final JTableHeader header = dayTable.getTableHeader();
		// thisDay and displayDay get the respective integer day values
		// So they can be compared because Calendar.equals is garbage
		// Have to compare every individual value because
		// CALENDAR IS COMPLETE GARBAGE
		final int thisYear = currentDay.get(Calendar.YEAR);
		final int displayYear = realDay.get(Calendar.YEAR);
		final int thisDay = currentDay.get(Calendar.DAY_OF_YEAR);
		final int displayDay = realDay.get(Calendar.DAY_OF_YEAR);
		if ((thisDay == displayDay) && (thisYear == displayYear)) {
			header.setBackground(new Color(138, 173, 209));
		} else {
			header.setBackground(UIManager.getColor(JTableHeader.class));
		}
	}

	private void initDay() {
		currentDay = Calendar.getInstance();
		realDay = currentDay;
		// function call of filter by Current day
		this.setRealDayEventsByRealDay();
	}

	/**
	 * Method refreshDay.
	 * 
	 * @param newDay
	 *            Calendar Changes the day on the column and the stored date
	 *            Changes the list of current view events
	 */
	public void refreshDay(Calendar newDay) {
		realDay = newDay;
		dayTable.getTableHeader().getColumnModel().getColumn(1)
				.setHeaderValue(this.getStringDay());
		repaint();
		dayTable.getSelectionModel().clearSelection();
		colorCurrentDate();
		// function call of filter by Current day
		this.setRealDayEventsByRealDay();
	}

	// Get the day in a nice string format declared
	// At the top of this file
	public String getStringDay() {
		return dayFormat.format(realDay.getTime());
	}
/**
 * 
 * @return the current day in string format
 */
	public String getToday() {
		return dayFormat.format(currentDay.getTime());
	}
/**
 * 
 * @return the real date in string format
 */
	public String getRealDayString(){
		return dayFormat.format(realDay.getTime());
	}
	public Calendar getRealDay() {
		return realDay;
	}

	/**
	 * Method resetCurrent.
	 */
	public void resetCurrent() {
		refreshDay(currentDay);
	}

	/**
	 * Builds realDayEvents list of events filtered based on the DayView's
	 * current real day
	 */
	private void setRealDayEventsByRealDay() {
		ArrayList<Event> holdEvents = new ArrayList<Event>();

		// fun filter
		// if current state is team calendar
		System.out.println("personal: "+GlobalButtonVars.isPersonalView);
		System.out.println("team: "+GlobalButtonVars.isTeamView);
		
		// if current state is Personal View
		if (!GlobalButtonVars.isTeamView && GlobalButtonVars.isPersonalView) {
			holdEvents.addAll(EventModel.getInstance().getUserEvents(
					ConfigManager.getConfig().getUserName(),
					this.getRealDay().get(Calendar.YEAR),
					this.getRealDay().get(Calendar.MONTH),
					this.getRealDay().get(Calendar.DATE)));
			System.out
					.println("Built realDayEvents from Personal Calendar for day: "
							+ this.getRealDayString());
			System.out.println("size: "+holdEvents.size());
		}

		// if current state is Team calendar
		else if (GlobalButtonVars.isTeamView
				&& !GlobalButtonVars.isPersonalView) {
			holdEvents.addAll(EventModel.getInstance().getTeamEvents(
					ConfigManager.getConfig().getUserName(),
					this.getRealDay().get(Calendar.YEAR),
					this.getRealDay().get(Calendar.MONTH),
					this.getRealDay().get(Calendar.DATE)));
			System.out
					.println("Built realDayEvents from Team Calendar for day: "
							+ this.getRealDayString());
			System.out.println("size: "+holdEvents.size());
		}
		
		// if current state is both calendar
		else if (GlobalButtonVars.isTeamView && GlobalButtonVars.isPersonalView) {
			holdEvents.addAll(EventModel.getInstance().getUserEvents(
					ConfigManager.getConfig().getUserName(),
					this.getRealDay().get(Calendar.YEAR),
					this.getRealDay().get(Calendar.MONTH),
					this.getRealDay().get(Calendar.DATE)));
			holdEvents.addAll(EventModel.getInstance().getTeamEvents(
					ConfigManager.getConfig().getUserName(),
					this.getRealDay().get(Calendar.YEAR),
					this.getRealDay().get(Calendar.MONTH),
					this.getRealDay().get(Calendar.DATE)));
			System.out
					.println("Built realDayEvents from Both Calendars for day: "
							+ this.getRealDayString());
			System.out.println("size: "+holdEvents.size());

		}
		//System.out.println(EventModel.getInstance().getAllEvents().get(0).getName());
		// set returns to realDayEvents
		//TODO CONNER add functionality that will sort holdEvents to the desired order HERE, using the sort methods you have created
		this.realDayEvents = holdEvents;

	}

	/**
	 * @return the realDayEvents
	 */
	public ArrayList<Event> getRealDayEvents() {
		return realDayEvents;
	}

}
