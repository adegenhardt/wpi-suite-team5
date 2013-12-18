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
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.EventUpdater;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.tabs.ClosableTabCreator;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

/**
 * Panel for the Day View tab of the calendar
 * 
 * @author Team Underscore
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class DayView extends JLayeredPane {

	// Strings defining what to be displayed in the tool-tip
	// Useful for getting some nice spacing as well
	private static final String PROJECT = "Project: ";
	private static final String USERNAME = "Username: ";
	private static final String NAME = "Name: ";
	private static final String DESC = "Description: ";
	private static final String CATEGORY = "Category: ";
	private static final String STIME = "Start Time: ";
	private static final String ETIME = "End Time: ";

	private JScrollPane dayScroll;

	private DayViewTable dayTable;
	// Current day signifies the day of today
	private Calendar currentDay;
	// Real day is for the day being displayed
	private Calendar realDay;

	// String date format that the Day View will give
	private final DateFormat dayFormat = new SimpleDateFormat("MMM/dd/yy");

	// Last listened mouse coordinates
	@SuppressWarnings("unused")
	private int lastX = 0;
	@SuppressWarnings("unused")
	private int lastY = 0;

	private static DayView thisInstance = null;

	/**
	 * Create the panel
	 * 
	 * @return instance
	 */
	public static DayView getInstance() {
		if (thisInstance == null) {
			thisInstance = new DayView();
		}
		return thisInstance;
	}

	private DayView() {

		// Run these methods to create this view
		initDay();
		createControls();
		addElements();
		createBounds();
		createBackground();
		createTableProperties();
		colorCurrentDate();

		// Setting the dismiss delay to max seems to be as close to
		// Keeping the tool tip on for as long as the user desires
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		// Initial delay for tool tip
		// Set to 0 for testing
		ToolTipManager.sharedInstance().setInitialDelay(0);
		// ReShowDelay is also something we can use if need be

		dayTable.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				EventRectangle thisTangle = dayTable.getRectangle(e.getX(),
						e.getY());
				if (thisTangle == null) {
					dayTable.setToolTipText(null);
				} else {
					dayTable.setToolTipText("<html>"
							+ PROJECT
							+ formatString(ConfigManager.getConfig().getProjectName(), 30)
							+ "<br>"
							+ USERNAME
							+ formatString(ConfigManager.getConfig().getUserName(), 30)
							+ "<br>"
							+ NAME
							+ formatString(thisTangle.getEvent().getName(), 30)
							+ "<br>"
							+ DESC
							+ formatString(thisTangle.getEvent()
									.getDescription(), 30) + "<br>"
							+ CATEGORY + formatString(CategoryModel.getInstance().getNameOfCatId(thisTangle.getEvent().getCategory()), 30)
							+ "<br>" + STIME
							+ (thisTangle.getEvent().getStartDate())
							+ "<br><br>" + ETIME
							+ thisTangle.getEvent().getEndDate());
				}
			}
		});

		dayTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent m) {
				EventRectangle thisRectangle = dayTable.getRectangle(m.getX(),
						m.getY());
				if (thisRectangle == null) {
					System.out.println("no rectangle");
				} else {
					System.out.println("a rectangle");
					final EventUpdater eventTab = new EventUpdater(
							ClosableTabCreator.getInstance(null)
									.getTabbedPane(), thisRectangle.getEvent());
					ClosableTabCreator.getInstance(null).addClosableTab(
							eventTab, "Update Event");
				}
			}
		});
	}

	// Create the table of DayView
	private void createControls() {
		dayTable = new DayViewTable(new DefaultTableModel(new Object[][] {
				{ "12:00 AM", null }, { "", null }, { "01:00 AM", null },
				{ "", null }, { "02:00 AM", null }, { "", null },
				{ "03:00 AM", null }, { "", null }, { "04:00 AM", null },
				{ "", null }, { "05:00 AM", null }, { "", null },
				{ "06:00 AM", null }, { "", null }, { "07:00 AM", null },
				{ "", null }, { "08:00 AM", null }, { "", null },
				{ "09:00 AM", null }, { "", null }, { "010:00 AM", null },
				{ "", null }, { "11:00 AM", null }, { "", null },
				{ "12:00 PM", null }, { "", null }, { "01:00 PM", null },
				{ "", null }, { "02:00 PM", null }, { "", null },
				{ "03:00 PM", null }, { "", null }, { "04:00 PM", null },
				{ "", null }, { "05:00 PM", null }, { "", null },
				{ "06:00 PM", null }, { "", null }, { "07:00 PM", null },
				{ "", null }, { "08:00 PM", null }, { "", null },
				{ "09:00 PM", null }, { "", null }, { "10:00 PM", null },
				{ "", null }, { "11:00 PM", null }, { "", null }, },
				new String[] { "", this.getStringDay() }) {
			// Do not allow the cells to be editable
			private final boolean[] columnEditables = new boolean[] { false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		dayTable.setDayView(this);

		// Set the view constraints and appearance
		dayTable.setAutoCreateColumnsFromModel(false);
		dayTable.getColumnModel().getColumn(0).setResizable(false);
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		dayTable.getColumnModel().getColumn(0).setMinWidth(70);
		dayTable.getColumnModel().getColumn(0).setMaxWidth(70);
		dayTable.getColumnModel().getColumn(1).setResizable(false);
		dayTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		dayTable.setFocusable(false);
		dayTable.setRowSelectionAllowed(false);
		dayTable.setDefaultRenderer(Object.class,
				new DefaultTableCellRenderer() {

					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {

						final DefaultTableCellRenderer rendererComponent = (DefaultTableCellRenderer) super
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
						// Set the colors. Every two rows will be light blue,
						// rows in between are white
						if ((row % 2) == 0 && column != 0) {
							rendererComponent.setBackground(new Color(227, 235,
									243));
						} else {
							rendererComponent.setBackground(Color.white);
						}
						this.repaint();
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
		dayScroll = new JScrollPane(dayTable);
		
		dayTable.scrollRectToVisible(dayTable.getCellRect(16, 1, true));

	}

	// Add a scroll bar
	private void addElements() {
		setLayout(new OverlayLayout(this));
		this.add(dayScroll, 0);
	}

	// Set the bounds
	private void createBounds() {
		this.setBounds(0, 0, 626, 600);
	}

	// Set a background
	private void createBackground() {
		dayTable.getParent().setBackground(dayTable.getBackground());
	}

	// Set the table properties
	private void createTableProperties() {
		// Resizing is allowed, no reorder
		dayTable.getTableHeader().setResizingAllowed(true);
		dayTable.getTableHeader().setReorderingAllowed(false);

		// Multiple cell selection
		dayTable.setColumnSelectionAllowed(true);
		dayTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Set no row and single column count
		// Should I be setting the row height here?
		dayTable.setRowHeight(15);
	}

	// Method to color in today's date if the view
	// Is currently on today's date
	// For some reason this doesn't work with the
	// Day view panel, I'll look into this when it
	// Isn't 2:00AM
	// It is no longer 2:00AM
	private void colorCurrentDate() {
		final JTableHeader header = dayTable.getTableHeader();
		// thisDay and displayDay get the respective integer day values
		// So they can be compared.
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
		// Set all hours/minutes/seconds/milliseconds to zero (for comparisons)
		currentDay.set(Calendar.HOUR_OF_DAY, 0);
		currentDay.set(Calendar.MINUTE, 0);
		currentDay.set(Calendar.SECOND, 0);
		currentDay.set(Calendar.MILLISECOND, 0);
		realDay = currentDay;
	}

	/**
	 * Method refreshDay.
	 * 
	 * @param newDay
	 *            Calendar Changes the day on the column and the stored date
	 */
	public void refreshDay(Calendar newDay) {
		realDay = newDay;
		dayTable.getTableHeader().getColumnModel().getColumn(1)
				.setHeaderValue(this.getStringDay());
		dayTable.setUpdated(false);
		repaint();
		colorCurrentDate();
	}

	/**
	 * Repaint the view to refresh events being shown
	 * 
	 */
	public void refreshEvents() {
		dayTable.setUpdated(false);
		repaint();
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
	public String getRealDayString() {
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

	// Helper method to format the strings within the tool-tips
	private String formatString(String str, int len) {
		String[] arrayStr = formatIntoArrays(str, len);
		StringBuilder finalStr = new StringBuilder();
		for (int i = 0; i < arrayStr.length; i++) {
			finalStr.append(arrayStr[i] + "<br>");
		}
		return finalStr.toString();
	}
	
	// Thanks programmers cook book
	// WordUtils may be a better idea 
	private String[] formatIntoArrays(String text, int len) {
		// return empty array for null text
		if (text == null)
			return new String[] {};

		// return text if length is zero or less
		if (len <= 0)
			return new String[] { text };

		// return text if less than length
		if (text.length() <= len)
			return new String[] { text };

		char[] chars = text.toCharArray();
		Vector<String> lines = new Vector<String>();
		StringBuffer line = new StringBuffer();
		StringBuffer word = new StringBuffer();

		for (int i = 0; i < chars.length; i++) {
			word.append(chars[i]);

			if (chars[i] == ' ') {
				if ((line.length() + word.length()) > len) {
					lines.add(line.toString());
					line.delete(0, line.length());
				}

				line.append(word);
				word.delete(0, word.length());
			}
		}

		// handle any extra chars in current word
		if (word.length() > 0) {
			if ((line.length() + word.length()) > len) {
				lines.add(line.toString());
				line.delete(0, line.length());
			}
			line.append(word);
		}

		// handle extra line
		if (line.length() > 0) {
			lines.add(line.toString());
		}

		String[] ret = new String[lines.size()];
		int c = 0; // counter
		for (Enumeration<String> e = lines.elements(); e.hasMoreElements(); c++) {
			ret[c] = e.nextElement();
		}

		return ret;
	}

	/**
	 * 
	 * @return the current scroll pane for DayView
	 */
	public JScrollPane getScrollPane() {
		return dayScroll;
	}

}
