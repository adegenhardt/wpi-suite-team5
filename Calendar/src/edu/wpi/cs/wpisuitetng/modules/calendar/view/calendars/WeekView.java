/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Underscore 
 *    
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Color;
import java.awt.Component;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @author Team Underscore
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class WeekView extends JPanel {

	// Milliseconds for day in Calendar class
	// Used to calculate first day of week
	private static final long ONE_DAY = 86400000;
	
	private static final String NAME = "Name: ";
	private static final String DESC = "Description: ";
	private static final String STIME = "Start Time: ";
	private static final String ETIME = "End Time: ";
	
	private JScrollPane dayScroll;
	private WeekViewTable dayTable;

	private Calendar currentDay;
	private Calendar firstDayOfWeek;
	
	private Calendar[] weekCalendar;
	
	// Days of the week
	private final String[] weekDays = new String[7];

	// Variables used to disallow time column selection
	private final int disabledColumn = 0;
	private int currentColumn = 1;

	// String date format that the Day View will give
	private final DateFormat dayFormat = new SimpleDateFormat("MMM/dd/yy");
	
	private int lastX = 0;
	private int lastY = 0;

	// Create the WeekView panel
	public WeekView() {

		initWeek();
		createControls();
		addElements();
		createBounds();
		createBackground();
		createTableProperties();
		createUnselectableCol();
		
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		// Initial delay for tool tip
		// Set to 0 for testing
		ToolTipManager.sharedInstance().setInitialDelay(0);
		
		dayTable.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				for (int i=0; i < 7; i++) {
					EventRectangle thisTangle = dayTable.getRectangle(e.getX(),
							e.getY(), i);
					if (thisTangle == null) {
						dayTable.setToolTipText(null);
					} else {
						dayTable.setToolTipText("<html>"
								+ NAME
								+ formatString(thisTangle.getEvent().getName(),
										30)
								+ "<br><br>"
								+ DESC
								+ formatString(thisTangle.getEvent()
										.getDescription(), 30) + "<br><br>"
								+ STIME
								+ (thisTangle.getEvent().getStartDate())
								+ "<br><br>" + ETIME
								+ thisTangle.getEvent().getEndDate());
						break;
					}
				}
			}
		});

	}

	// Set the times
	private void createControls() {
		dayTable = new WeekViewTable(new DefaultTableModel(new Object[][] {
				{ "12:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "01:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "02:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "03:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "04:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "05:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "06:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "07:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "08:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "09:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "10:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "11:00 AM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "12:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "01:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "02:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "03:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "04:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "05:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "06:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "07:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "08:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "09:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "10:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "11:00 PM", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null }, },
				new String[] { "", weekDays[0], weekDays[1], weekDays[2], weekDays[3], 
								   weekDays[4], weekDays[5], weekDays[6] }) {

			// Do not allow editing of this table
			private final boolean[] columnEditables = new boolean[] { false,
					false, false, false, false, false, false, false };
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		dayTable.setWeekView(this);
		
		final JTableHeader header = dayTable.getTableHeader();
		
		// Set up the custom table renderers
		dayTable.setSelectionBackground(Color.GREEN);
		dayTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, 
							boolean isSelected, boolean hasFocus, int row, int column) {
				 final DefaultTableCellRenderer rendererComponent = 
						 (DefaultTableCellRenderer)super.getTableCellRendererComponent(
								 				table, value, isSelected, hasFocus, row, column);
				 
				// Make every second row blue
				if ((row % 2) == 0 && column != 0) {
					rendererComponent.setBackground(new Color(227, 235,
							243));
				}
				else {
					rendererComponent.setBackground(Color.white);
				}
				return rendererComponent;
			}
		});
		
        header.setDefaultRenderer(new DefaultTableCellRenderer() {

			@Override
            public Component getTableCellRendererComponent(
                    JTable table, 
                    Object value, 
                    boolean isSelected,
                    boolean hasFocus, 
                    int row, 
                    int column) {
            	
                final DefaultTableCellRenderer rendererComponent = 
                		(DefaultTableCellRenderer)super.getTableCellRendererComponent(
                				table, 
                				value, 
                				isSelected, 
                				hasFocus, 
                				row, 
                				column);
            	final String thisDay = getStringDay(currentDay);
            	
            	if (column != 0 && weekDays[column - 1].equals(thisDay)) {
            		rendererComponent.setBorder(
            				BorderFactory.createCompoundBorder(rendererComponent.getBorder(), 
            				BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            		rendererComponent.setBackground(new Color(138, 173, 209));
            	}
            	else {
            		rendererComponent.setBorder(
            				BorderFactory.createCompoundBorder(rendererComponent.getBorder(), 
            				BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            		rendererComponent.setBackground(UIManager.getColor(JTableHeader.class));
            	}
            	
            	return rendererComponent;
            }
        });
		
        // Set constraints
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		dayTable.getColumnModel().getColumn(0).setMinWidth(70);
		dayTable.getColumnModel().getColumn(0).setMaxWidth(70);
		dayTable.setAutoCreateColumnsFromModel(false);
		// Make this panel scrollable
		dayScroll = new JScrollPane(dayTable);

	}

	private void addElements() {
		setLayout(new BorderLayout(0, 0));
		this.add(dayScroll);
	}

	private void createBounds() {
		this.setBounds(0, 0, 626, 600);
	}

	private void createBackground() {
		dayTable.getParent().setBackground(dayTable.getBackground());
	}

	private void createTableProperties() {
		// Resizing allowed, no reorder
		dayTable.getTableHeader().setResizingAllowed(true);
		dayTable.getTableHeader().setReorderingAllowed(false);

		// Multiple cell selection
		dayTable.setColumnSelectionAllowed(true);
		dayTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Sets row height at 15
		dayTable.setRowHeight(15);
	}
	
	// Creates an unselectable column!
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
				else {
					currentColumn = sel.getMaxSelectionIndex();
				}
			}
		});
	}

	// Initialize the week with the current week
	private void initWeek() {
		currentDay = Calendar.getInstance();
		currentDay.set(Calendar.HOUR_OF_DAY, 0);
		currentDay.set(Calendar.MINUTE, 0);
		currentDay.set(Calendar.SECOND, 0);
		currentDay.set(Calendar.MILLISECOND, 0);
		
		final Calendar shiftWeek = Calendar.getInstance();
		
	    while(shiftWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	         shiftWeek.setTimeInMillis(shiftWeek.getTimeInMillis() - ONE_DAY);
	    }
	    
	    weekCalendar = new Calendar[7];
	    
	    firstDayOfWeek = (Calendar) shiftWeek.clone();
	    
	    changeWeekArray(shiftWeek);
	}

	/**
	 * Method refreshDay.
	 * 
	 * @param newDay
	 *            Calendar Changes the day on the column and the stored date
	 */
	private void refreshWeek(Calendar newDay) {
		firstDayOfWeek = newDay;
		changeWeekArray(newDay);
		updateHeaders();
		repaint();
		dayTable.getSelectionModel().clearSelection();
	}

	/**
	 * Get the day in a nice string format declared
	 * At the top of this file
	 * @param theDay to convert to a string
	 * @return the string
	 */
	public String getStringDay(Calendar theDay) {
		return dayFormat.format(theDay.getTime());
	}
	
	private void changeWeekArray(Calendar firstDay) {
		final Calendar tempDay = (Calendar) firstDay.clone();
		weekCalendar[0] = (Calendar) tempDay.clone();
		weekDays[0] = this.getStringDay(firstDay);
		for(int i=1; i < weekDays.length; i++) {
			tempDay.add(Calendar.DATE, 1);
			weekDays[i] = this.getStringDay(tempDay);
			weekCalendar[i] = (Calendar) tempDay.clone();
		}
	}
	// Update the headers of the table
	private void updateHeaders() {
		for(int i=0; i < weekDays.length; i++) {
			dayTable.getTableHeader().getColumnModel().getColumn(i + 1).setHeaderValue(weekDays[i]);
		}
	}
	
	/**
	 * Move the week forward
	 */
	public void forwardWeek() {
		firstDayOfWeek.add(Calendar.DATE, 7);
		refreshWeek(firstDayOfWeek);
		dayTable.setUpdated(false);
	}
	/**
	 * Move the week back
	 */
	public void backWeek() {
		firstDayOfWeek.add(Calendar.DATE, -7);
		refreshWeek(firstDayOfWeek);
		dayTable.setUpdated(false);
	}
	/**
	 * Move the week to the current week
	 */
	public void currentWeek() {
		final Calendar resetWeek = Calendar.getInstance();
		
	    while(resetWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	         resetWeek.setTimeInMillis(resetWeek.getTimeInMillis() - ONE_DAY);
	    }
	    
	    refreshWeek(resetWeek);
	    dayTable.setUpdated(false);
	}
	/**
	 * 
	 * @param i Index into day of the week (0/Sunday - 6/Saturday)
	 * @return The Calendar object for that day, null if out of bounds
	 */
	public Calendar getCalendarDay(int i) {
		try {
			return weekCalendar[i];
		}
		catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private String formatString(String str, int len) {
		str = str.trim();
		if (str.length() < len)
			return str;
		if (str.substring(0, len).contains("<br>"))
			return str.substring(0, str.indexOf("<br>")).trim() + "<br><br>"
					+ formatString(str.substring(str.indexOf("<br>") + 1), len);
		int place = Math
				.max(Math.max(str.lastIndexOf(" ", len),
						str.lastIndexOf("\t", len)), str.lastIndexOf("-", len));
		return str.substring(0, place).trim() + "<br>"
				+ formatString(str.substring(place), len);
	}
}
