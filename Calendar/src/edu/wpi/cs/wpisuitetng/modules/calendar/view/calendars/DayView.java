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
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;

/**
 * Panel for the Day View tab of the calendar
 * 
 * @author Team Underscore
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class DayView extends JLayeredPane {
	
	// One day in milliseconds
	private static final long ONE_DAY = 86400000;

	private JScrollPane dayScroll;
	private JTable dayTable;
	// Current day signifies the day of today
	private Calendar currentDay;
	// Real day is for the day being displayed
	private Calendar realDay;

	// String date format that the Day View will give
	private final DateFormat dayFormat = new SimpleDateFormat("MMM/dd/yy");

	private List< Event > events;		/* current day's events to display */
	boolean isUpdated;					/* whether or not the event list is updated */
	
	private JLayeredPane thisInstance; 
	
	/**
	 * Create the panel.
	 * 
	 * @param isWeek
	 *            boolean
	 */
	public DayView() {
		
		thisInstance = this;
		
		// Run these methods to create this view
		initDay();
		createControls();
		addElements();
		createBounds();
		createBackground();
		createTableProperties();
		colorCurrentDate();
		updateEventRects();
		
		isUpdated = false;
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
		dayTable.setFocusable(false);
		dayTable.setRowSelectionAllowed(false);
		dayTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				final DefaultTableCellRenderer rendererComponent = (DefaultTableCellRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						final DefaultTableCellRenderer rendererComponent = 
								(DefaultTableCellRenderer) super
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
				if ((row % 2) == 0 && column != 0) {
					rendererComponent.setBackground(new Color(185, 209, 234));
				}
				else {
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
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		dayTable.getColumnModel().getColumn(0).setMinWidth(55);
		dayTable.getColumnModel().getColumn(0).setMaxWidth(55);
		dayScroll = new JScrollPane(dayTable);

	}

	// Add a scroll bar
	private void addElements() {
		setLayout(new OverlayLayout( this ) );
		this.add(dayScroll, 0 );
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
		// Set all hours/minutes/seconds/ms to zero (for comparisons)
        currentDay.set(Calendar.HOUR_OF_DAY, 0);
        currentDay.set(Calendar.MINUTE, 0);
        currentDay.set(Calendar.SECOND, 0);
        currentDay.set(Calendar.MILLISECOND, 0);
		realDay = currentDay;
		// function call of filter by Current day
		this.setRealDayEventsByRealDay();
	}
	
	private void updateEventRects() {
		dayScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				thisInstance.repaint();
			}
		});
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
		final List<Event> holdEvents = new ArrayList<Event>();

		// fun filter
		// if current state is team calendar
		System.out.println("personal: " + GlobalButtonVars.isPersonalView);
		System.out.println("team: " + GlobalButtonVars.isTeamView);
		
		// if current state is Personal View
		if (!GlobalButtonVars.isTeamView && GlobalButtonVars.isPersonalView) {
			holdEvents.addAll(EventModel.getInstance().getUserEvents(
					ConfigManager.getConfig().getUserName(),
					realDay.get(Calendar.YEAR),
					realDay.get(Calendar.MONTH),
					realDay.get(Calendar.DATE)));
			System.out
					.println("Built realDayEvents from Personal Calendar for day: "
							+ this.getRealDayString());
			System.out.println("size: " + holdEvents.size());
		}

		// if current state is Team calendar
		else if (GlobalButtonVars.isTeamView
				&& !GlobalButtonVars.isPersonalView) {
			holdEvents.addAll(EventModel.getInstance().getTeamEvents(
					ConfigManager.getConfig().getUserName(),
					realDay.get(Calendar.YEAR),
					realDay.get(Calendar.MONTH),
					realDay.get(Calendar.DATE)));
			System.out
					.println("Built realDayEvents from Team Calendar for day: "
							+ this.getRealDayString());
			System.out.println("size: " + holdEvents.size());
		}
		
		// if current state is both calendar
		else if (GlobalButtonVars.isTeamView && GlobalButtonVars.isPersonalView) {
			holdEvents.addAll(EventModel.getInstance().getUserEvents(
					ConfigManager.getConfig().getUserName(),
					realDay.get(Calendar.YEAR),
					realDay.get(Calendar.MONTH),
					realDay.get(Calendar.DATE)));
			holdEvents.addAll(EventModel.getInstance().getTeamEvents(
					ConfigManager.getConfig().getUserName(),
					realDay.get(Calendar.YEAR),
					realDay.get(Calendar.MONTH),
					realDay.get(Calendar.DATE)));
			System.out
					.println("Built realDayEvents from Both Calendars for day: "
							+ this.getRealDayString());
			System.out.println("size: " + holdEvents.size());

		}
		// set returns to realDayEvents
		//TODO CONNER add functionality that will sort holdEvents to the 
		// desired order HERE, using the sort methods you have created
		realDayEvents = holdEvents;

	}

	/**
	 * @return the realDayEvents
	 */
	public List<Event> getRealDayEvents() {
		return realDayEvents;
	}
	
	/**
	 * Paint function. Draws components and events on top
	 * @see javax.swing.JLayeredPane#paint(Graphics)
	 */
	@Override
	public void paint( Graphics g ) {
		super.paint( g );			// draw table
		
		if ( !isUpdated ) {
			updateEvents();
			isUpdated = true;
		}
		
		paintEvents( g );
	}
	
	/**
	 * Draw the events onto the table
	 * Note: Events should be sorted with ascending start time,
	 * and descending end time (for events with the same start time)
	 * for this to work
	 * @param g A graphics object to render the events
	 */
	public void paintEvents( Graphics g ) {
		
		// TODO: Make sure the events don't disappear when scrolling
		
		final java.awt.Insets insets = getInsets();
		final int X_OFFSET = insets.left + dayTable.getColumnModel().getColumn(0 ).getWidth();
		final int Y_OFFSET = insets.top + dayTable.getRowHeight() + 4;
		
		// Maximum width an event can be
		final int MAX_WIDTH = getWidth() - X_OFFSET -
				insets.right - dayScroll.getVerticalScrollBar().getWidth() - 2;
		final int ROW_HEIGHT = dayTable.getRowHeight();
		int x;
		int y;
		
		// Actual height and width values for the current event
		int width;
		int height;
		
		/* number of events already occuring in a given slot */
		int numPriorEvents[] = new int[ 48 ];
		final int PRIOR_EVENT_WIDTH = 4;	/* Number of pixels to reserve for each prior event */
		
		
		// Set number of prior events to 0 for all slots
		for ( int i = 0; i < numPriorEvents.length; i++ ) {
			numPriorEvents[ i ] = 0;
		}
		
		Event e;			/* the current event */
		DateInfo startDate;
		DateInfo endDate;
		int numEventsInRow; 	/* Number of events in current row */
		int stringWidth;		/* width of string being printed */
		int stringHeight;		/* height of string being printed */
		String printString;		/* string to print for Event */
		for ( int i = 0; i < events.size(); i++ ) {
			e = events.get( i );
			
			startDate = e.getStartDate();
			endDate = e.getEndDate();
			
			//TODO: check if date starts before current day
			// Also I'm assuming checking if the event date is after the current day
			// Would be silly since it shouldn't be in the event list
			// Sorry I couldn't do more, I'm not too sure what to do with these cases
			if (e.getStartDate().dateInfoToCalendar().getTimeInMillis() < realDay.getTimeInMillis()) {
				System.out.println("The Event begins before this day");
			}
			else {
				System.out.println("The Event begins on this day");
			}
			y = Y_OFFSET + ( startDate.getHalfHour() * ROW_HEIGHT );
			
			// TODO: handle counting events that start prior to the day
			// Determine the number of events with the same starting time
			numEventsInRow = 1;
			for ( int j = i + 1; j < events.size(); j++ ) {
				if ( events.get( j ).getStartDate().equals( startDate ) ) {
					numEventsInRow++;
				} else {		// since events are sorted, break
								// at the first different start time
					break;
				}
			}
			
			width = ( MAX_WIDTH - ( numPriorEvents[ i ] * PRIOR_EVENT_WIDTH ) ) /
					numEventsInRow;
			
			// Add 1 to each row that this event occupies for future events
			for ( int j = i + 1; j < endDate.getHalfHour(); j++ ) {
				numPriorEvents[ j ]++;
			}
			
			// draw all events in row
			for ( int j = 0; j < numEventsInRow; j++ ) {
				e = events.get( i + j );
				
				endDate = e.getEndDate();
				
				// Calculate offset of x depending on number of prior events
				// and also which event in the row this is
				x = X_OFFSET +
					( numPriorEvents[ startDate.getHalfHour() ] * PRIOR_EVENT_WIDTH ) +
					( width * j );
				
				//TODO: check if date ends after current day
				// Again I'm assuming checking for if the date ends
				// Before today would be silly
				if (e.getEndDate().dateInfoToCalendar().getTimeInMillis() > (realDay.getTimeInMillis() + ONE_DAY)) {
					System.out.println("The Event ends after this day");
				}
				else {
					System.out.println("The Event ends on this day");
				}
				
				height = ( Y_OFFSET + ( endDate.getHalfHour() * ROW_HEIGHT ) ) -
						y;

				// draw the event rectangle
				g.setColor( e.getColor() );
				g.fillRect( x, y, width, height );
				g.setColor( Color.BLACK );
				g.drawRect( x, y, width, height );
				
				// calculate and height of Event name
				stringWidth = g.getFontMetrics().stringWidth( e.getName() );
				stringHeight = g.getFontMetrics().getMaxAscent();
				
				// trim event name to fit as necessary
				printString = trimString( e.getName(),
						g.getFontMetrics(),
						width );
				
				g.drawString( printString, x + 2, y + stringHeight );
			}
			
			// increment i to skip over the drawn events
			i += numEventsInRow - 1;
			
		}
		
	}
	
	/**
	 * Trim a string into a fitting length and append '...' to the end
	 * @param s the Original unedited string
	 * @param metric the statistics of the font being used
	 * @param maxWidth the maximum width allowed in pixels
	 * @return the trimmed string
	 */
	public String trimString( String s, FontMetrics metric, int maxWidth ) {
		
		// If string already fits, return it
		if ( metric.stringWidth( s ) < maxWidth ) {
			return s;
		}
		
		int i;
		// one by one, trim the last characters off until the string fits
		for ( i = s.length() - 1; i > 0; i-- ) {
			
			if ( metric.stringWidth( s.substring( 0, i ) ) < maxWidth ) {
				break;
			}
			
		}
		
		// if < 3 characters trimmed, replace last 3 characters with "..."
		if ( i > s.length() - 3 ) {
			return ( s.substring( 0, s.length() - 3 ) + "..." );
		} else {
			return ( s.substring( 0, i - 3 ) + "..." );
		}
	}
	
	/**
	 * Update the stored events by retrieving and sorting them
	 */
	public void updateEvents() {
		// TODO: Replace this with actual functionality after testing
		
		events = generateSampleEvents();
	}
	
	/**
	 * Generate sample events for testing
	 * @return
	 */
	public List<Event> generateSampleEvents() {
		ArrayList<Event> sampleEvents = new ArrayList<Event>();
		
		// For testing, create start times based on the current date
		Calendar cal = Calendar.getInstance();
		DateInfo time0 = new DateInfo( cal.get( Calendar.YEAR ),
											cal.get( Calendar.MONTH ),
											cal.get( Calendar.DATE ) - 1,
											0 );
		
		DateInfo time2 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				2 );
		
		DateInfo time4 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				4 );
		
		DateInfo time6 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				6 );
		
		DateInfo time7 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				7 );
		
		DateInfo time8 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				8 );
		
		DateInfo time10 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				10 );
		
		DateInfo time12 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				12 );
		
		DateInfo time13 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				13 );
		
		DateInfo time18 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				18 );
		
		Event e1 = new Event();
		e1.setName( "event 1 - aka the incredibly long name to test my trimmming capability;" +
				"It keeps going on and on without any rhyme or reason. Oh why won't it stop?" +
				"Who knows? Probably the elders, but they're so old. I guess we'll never know." );
		e1.setStartDate( time0 );
		e1.setEndDate( time12 );
		
		Event e2 = new Event();
		e2.setName( "event 2" );
		e2.setStartDate( time2 );
		e2.setEndDate( time7 );
		
		Event e3 = new Event();
		e3.setName( "event 3" );
		e3.setStartDate( time2 );
		e3.setEndDate( time7 );
		
		Event e4 = new Event();
		e4.setName( "event 4" );
		e4.setStartDate( time4 );
		e4.setEndDate( time6 );
		
		Event e5 = new Event();
		e5.setName( "event 5" );
		e5.setStartDate( time7 );
		e5.setEndDate( time18 );
		
		Event e6 = new Event();
		e6.setName( "event 6" );
		e6.setStartDate( time7 );
		e6.setEndDate( time12 );
		
		Event e7 = new Event();
		e7.setName( "event 7" );
		e7.setStartDate( time7 );
		e7.setEndDate( time8 );
		
		Event e8 = new Event();
		e8.setName( "event 8" );
		e8.setStartDate( time10 );
		e8.setEndDate( time13 );
		
		sampleEvents.add( e1 );
		sampleEvents.add( e2 );
		sampleEvents.add( e3 );
		sampleEvents.add( e4 );
		sampleEvents.add( e5 );
		sampleEvents.add( e6 );
		sampleEvents.add( e7 );
		sampleEvents.add( e8 );
		
		return sampleEvents;
	}
}
