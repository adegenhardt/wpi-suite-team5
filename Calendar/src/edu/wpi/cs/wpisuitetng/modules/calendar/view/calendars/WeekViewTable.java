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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.EventFilter;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.EventSorter;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;

/**
 * 
 * @author Team _
 *
 * @version $Revision: 1.0 $
 * WeekViewTable manages the application of building events for display in the WeekView window
 */
public class WeekViewTable extends JTable {
	
	private static final long serialVersionUID = -4325747905323115241L;
	

	boolean isUpdated; /* whether or not the event list is updated */
	WeekView dayView; /* instance of day view for updating current events */
	
	
	private final List<Event>[] eventsArray;
	private final List<EventRectangle>[] rectanglesArray;
	
	/**
	 * 
	 * @param defaultTableModel
	 */
	@SuppressWarnings("unchecked")
	public WeekViewTable(DefaultTableModel defaultTableModel) {
		super( defaultTableModel );
		
		isUpdated = false;
		
		eventsArray = new ArrayList[7];
		rectanglesArray = new ArrayList[7];
		
		// Initialize the arrays, remind me to slap some casts everywhere
		for (int i=0; i < eventsArray.length; i++) {
			eventsArray[i] = new ArrayList<Event>();
			rectanglesArray[i] = new ArrayList<EventRectangle>();
		}
	}

	/**
	 * Paint the DayViewTable with events on top
	 * @see javax.swing.JTable#paintComponents(Graphics)
	 */
	@Override
	public void paintComponent( Graphics g ) {
		
		super.paintComponent( g );
		
		if ( !isUpdated ) {
			updateEvents();
			
			// Take updated events and put them in rectangles
			for (int k = 0; k < rectanglesArray.length; k++) {
				if (rectanglesArray[k].size() > 0) {
					rectanglesArray[k].clear();
				}

				for (int i = 0; i < eventsArray[k].size(); i++) {
					rectanglesArray[k].add(new EventRectangle(eventsArray[k].get(i)));
				}
			}
			
			isUpdated = true;
		}
		for (int i=0; i < 7; i++) {
			updateRectangles(i);
			paintRectangles( g );
		}
	}
	
	/**
	 * Iterate through the rectangles and paint them
	 * @param g the graphics class to use in rendering
	 */
	private void paintRectangles(Graphics g) {
		for (int k = 0; k < rectanglesArray.length; k++) {
			for (int i = 0; i < rectanglesArray[k].size(); i++) {
				paintEventRectangle(g, rectanglesArray[k].get(i), k);
			}
		}
	}

	/**
	 * Trim a string into a fitting length and append '...' to the end
	 * @param s the Original unedited string
	 * @param metric the statistics of the font being used
	 * @param maxWidth the maximum width allowed in pixels
	
	 * @return the trimmed string */
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
			return ( s.substring( 0, Math.max( 1, s.length() - 3 ) ) + "..." );
		} else {
			return ( s.substring( 0, Math.max( 1, i - 3 ) ) + "..." );
		}
	}
	
	/**
	 * @param i The day of the week (0/Sunday - 6/Saturday)
	
	 * @return the day's events */
	public List<Event> getEvents(int i) {
		return eventsArray[i];
	}
	
	/**
	 * 
	 * @param isUpdated true if the DayView is updated, false otherwise
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	
	/**
	 * Update the stored events by retrieving and sorting them
	 */
	public void updateEvents() {
		for (int i = 0; i < 7; i++) {
			DateInfo eventDay = new DateInfo(dayView.getCalendarDay(i));
			if (GlobalButtonVars.getInstance().isPersonalView() && 
					GlobalButtonVars.getInstance().isTeamView()) {
				eventsArray[i] = EventModel.getInstance().getUserEvents(
						ConfigManager.getConfig().getUserName(),
						eventDay.getYear(), eventDay.getMonth(),
						eventDay.getDay());
			} else if (GlobalButtonVars.getInstance().isPersonalView()) {
				eventsArray[i] = EventModel.getInstance().getPersonalEvents(
						ConfigManager.getConfig().getUserName(),
						eventDay.getYear(), eventDay.getMonth(),
						eventDay.getDay());

			} else if (GlobalButtonVars.getInstance().isTeamView()) {
				eventsArray[i] = EventModel.getInstance().getTeamEvents(
						ConfigManager.getConfig().getUserName(),
						eventDay.getYear(), eventDay.getMonth(),
						eventDay.getDay());
			}
			//Filter View's events by applied filters
			eventsArray[i] = EventFilter.filterEventsByCategory(
					eventsArray[i], CategoryModel.getInstance()
					.getAllNondeletedCategoriesAsFilters());
			eventsArray[i] = EventSorter.sortEventsByDate(eventsArray[i]);
		}
	}
	
	/**
	 * Paint a single EventRectangle
	 * @param g The Graphics object to handle the painting
	 * @param rect An EventRectangle to draw
	 * @param iOffset offset
	 */
	void paintEventRectangle( Graphics g, EventRectangle rect, int iOffset ) {
		
		// Convert Calendar to DateInfo
		final DateInfo displayedDay = new DateInfo( dayView.getCalendarDay(iOffset) );
		
		final int CURVE_SIZE = 16; /* Size of curves for rounded rectangles */
		
		final int stringHeight; /* height of string being printed */
		String printString; /* string to print for Event */
		final Color textColor; /* color to print text for an event */
		
		final Event e = rect.getEvent();
		final int x = rect.getX();
		final int y = rect.getY();
		final int width = rect.getWidth();
		final int height = rect.getHeight();
		
		// draw the event rectangle
		g.setColor( e.getColor() );
		g.fillRoundRect( x, y,
				width, height, CURVE_SIZE, CURVE_SIZE );
		
		// Determine and set text/border color
		textColor = new Color( Math.max( e.getColor().getRed() - 120, 0 ),
				Math.max( e.getColor().getGreen() - 120, 0 ),
				Math.max( e.getColor().getBlue() - 120, 0 ) );
		
		g.setColor( textColor );
		g.drawRoundRect( x, y,
				width, height, CURVE_SIZE, CURVE_SIZE );
		
		stringHeight = g.getFontMetrics().getMaxAscent();
		
		printString = e.getName();
		
		// if event started before today, preface the string with (cont'd)
		displayedDay.setHalfHour( 0 );
		if (e.getStartDate().compareTo( displayedDay ) < 0) {
			printString = "(cont'd) " + printString;
		}
		
		// trim event name to fit as necessary
		printString = trimString( printString,
				g.getFontMetrics(),
				width );
		
		
		
		g.drawString( printString, x + 2, y + stringHeight );
		
	}
	
	/**
	 * Update the list of rectangles according to updated events
	 * Important: rectangles and events should have a 1:1 correspondence
	 * before entering this function
	 * @param iOffset offset
	 */
	public void updateRectangles(int iOffset) {
		
		// Convert Calendar to DateInfo
		final DateInfo displayedDay = new DateInfo( dayView.getCalendarDay(iOffset) );
		
		// Set the half-hour field to 0 to signify the beginning of the day
		displayedDay.setHalfHour( 0 );
		
		// Offset for column to place the events on the appropriate day column
		@SuppressWarnings("unused")
		final int X_COLUMN_OFFSET = getColumnModel().getColumn(1).getWidth();
		
		final int X_OFFSET = getCellRect( 1, iOffset + 1, true ).x;
		final int Y_OFFSET = 0;
		
		
		// Maximum width an event can be
		final int MAX_WIDTH = getCellRect( 1, iOffset + 1, true ).width - 1;
		final int ROW_HEIGHT = getRowHeight();
		int x;
		int y;
		
		// Actual height and width values for the current event
		int width;
		int height;
		
		/* number of events already occurring in a given slot */
		final int[] numPriorEvents = new int[ 48 ];
		final int PRIOR_EVENT_WIDTH = 4; /* Number of pixels to reserve for each prior event */
		
		
		// Set number of prior events to 0 for all slots
		for ( int i = 0; i < numPriorEvents.length; i++ ) {
			numPriorEvents[ i ] = 0;
		}
		
		Event e; /* the current event */
		DateInfo startDate;
		DateInfo endDate;
		int numEventsInRow; /* Number of events in current row */
		EventRectangle r;
		int startHour;
		for ( int i = 0; i < eventsArray[iOffset].size(); i++ ) {
			e = eventsArray[iOffset].get( i );
			
			startDate = e.getStartDate();
			endDate = e.getEndDate();
			
			// Set the half-hour field to 0 to signify the beginning of the day
			displayedDay.setHalfHour( 0 );
			
			// if event started before today, begin drawing at the top
			if ( e.getStartDate().compareTo( displayedDay ) < 0 ) {
				y = Y_OFFSET;
				startHour = 0;
			} else {
				y = Y_OFFSET + ( startDate.getHalfHour() * ROW_HEIGHT );
				startHour = startDate.getHalfHour();
			}
			
			numEventsInRow = 1;
			for ( int j = i + 1; j < eventsArray[iOffset].size(); j++ ) {
				// check if date has same start time, or begins before the current day
				if ((eventsArray[iOffset].get( j )).getStartDate().equals( startDate ) ||
						(eventsArray[iOffset].get(j)).getStartDate().compareTo(displayedDay) < 0){
					numEventsInRow++;
				} else {		// since events are sorted, break
								// at the first different start time
					break;
				}
			}
			
			// calculate the width of all events in the row
			width = ( MAX_WIDTH - ( numPriorEvents[ startHour ] * PRIOR_EVENT_WIDTH ) ) /
					numEventsInRow;
			
			displayedDay.setHalfHour( 48 );
			
			// Add 1 to each row that this event occupies for future events
			// If this runs into the next day, it's the same as occupying all remaining rows
			if ( e.getEndDate().compareTo( displayedDay ) >= 0 ) {
				for ( int j = startHour + 1; j < 48; j++ ) {
					numPriorEvents[ j ]++;
				}
			} else {
				for ( int j = startHour + 1; j < endDate.getHalfHour(); j++ ) {
					numPriorEvents[ j ]++;
				}
			}
			
			// draw all events in row
			for ( int j = 0; j < numEventsInRow; j++ ) {
				e = eventsArray[iOffset].get( i + j );
				r = rectanglesArray[iOffset].get( i + j );
				
				endDate = e.getEndDate();
				
				// Calculate offset of x depending on number of prior events
				// and also which event in the row this is
				x = X_OFFSET +
					( numPriorEvents[ startHour ] * PRIOR_EVENT_WIDTH ) +
					( width * j );
				
				// if event ends after current day, draw to the bottom
				if ( endDate.compareTo( displayedDay ) >= 0 ) {
					height = Y_OFFSET + ( 48 * ROW_HEIGHT ) - y;
				}
				else {
					height = ( Y_OFFSET + ( endDate.getHalfHour() * ROW_HEIGHT ) ) -
							y;
				}
				
				// Apply the calculated bounds to the selected rectangle
				r.setX( x );
				r.setY( y );
				r.setWidth( width );
				r.setHeight( height );
				rectanglesArray[iOffset].set( i + j, r );
			}
			
			// increment i to skip over the drawn events
			i += numEventsInRow - 1;
			
		}
	}
	
	/**
	 * Generate sample events for testing
	
	 * @return list of sample events */
	public List<Event> generateSampleEvents() {
		final List<Event> sampleEvents = new ArrayList<Event>();
		
		// For testing, create start times based on the current date
		final Calendar cal = Calendar.getInstance();
		final DateInfo time0 = new DateInfo( cal.get( Calendar.YEAR ),
											cal.get( Calendar.MONTH ),
											cal.get( Calendar.DATE ) - 1,
											0 );
		
		final DateInfo time2 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				2 );
		
		final DateInfo time4 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				4 );
		
		final DateInfo time6 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				6 );
		
		final DateInfo time7 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				7 );
		
		final DateInfo time8 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				8 );
		
		final DateInfo time10 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				10 );
		
		final DateInfo time12 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				12 );
		
		final DateInfo time13 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				13 );
		
		final DateInfo time18 = new DateInfo( cal.get( Calendar.YEAR ),
				cal.get( Calendar.MONTH ),
				cal.get( Calendar.DATE ) - 1,
				18 );
		
		final Event e1 = new Event();
		e1.setName( "event 1 - aka the incredibly long name to test my trimmming capability;" +
				"It keeps going on and on without any rhyme or reason. Oh why won't it stop?" +
				"Who knows? Probably the elders, but they're so old. I guess we'll never know." );
		e1.setDescription("This part isn't too long though");
		e1.setStartDate( time0 );
		e1.setEndDate( time12 );
		
		final Event e2 = new Event();
		e2.setName( "event 2" );
		e2.setDescription("This is event 2, things happen at this time");
		e2.setStartDate( time2 );
		e2.setEndDate( time7 );
		
		final Event e3 = new Event();
		e3.setName( "event 3" );
		e3.setDescription("I don't want to make this meeting, "
				+ "I have made this event to make sure I miss it.");
		e3.setStartDate( time2 );
		e3.setEndDate( time7 );
		
		final Event e4 = new Event();
		e4.setName( "event 4" );
		e4.setDescription("The fourth thing I need to attend today, I enjoy this one");
		e4.setStartDate( time4 );
		e4.setEndDate( time6 );
		
		final Event e5 = new Event();
		e5.setName( "event 5" );
		e5.setDescription("Let's try a really long description this time. "
				+ "Never know when I need something to wrap around something, "
				+ "and it would be a shame when that moment happens if I didn't take the time to "
				+ "think this would happen.");
		e5.setStartDate( time7 );
		e5.setEndDate( time18 );
		
		final Event e6 = new Event();
		e6.setName( "event 6" );
		e6.setDescription("The sixth event, how descriptive.");
		e6.setStartDate( time7 );
		e6.setEndDate( time12 );
		
		final Event e7 = new Event();
		e7.setName( "event 7" );
		e7.setDescription("Lucky number 7th event.");
		e7.setStartDate( time7 );
		e7.setEndDate( time8 );
		
		final Event e8 = new Event();
		e8.setName( "event 8" );
		e8.setDescription("Eight is gr8");
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
	
	/**
	 * Determines whether or not the index of a cell is visible
	 * @param rowIndex the index of the cell in question
	
	 * @return true if the cell is visible, false otherwise */
	public boolean isCellVisible(int rowIndex) {
		if (!(getParent() instanceof JViewport)) {
			return false;
		}
		final JViewport viewport = (JViewport) getParent();
		final Rectangle rect = getCellRect(rowIndex, 1, true);
		final Point pt = viewport.getViewPosition();
		rect.setLocation(rect.x - pt.x, rect.y - pt.y);
		return new Rectangle(viewport.getExtentSize()).contains(rect);
	}


	/**
	 * 
	 * @param dayView the instance of the day view holding this table
	 */
	public void setWeekView(WeekView dayView) {
		this.dayView = dayView;
	}

	/**
	 * 
	 * @param _x
	 * @param _y
	 * @param _day
	
	 * @return rectangle */
	public EventRectangle getRectangle(int _x, int _y, int _day) {
		for (int i=rectanglesArray[_day].size() - 1; i >= 0; i--) {
			if ((rectanglesArray[_day].get(i)).isAtPoint(_x, _y)) {
				return rectanglesArray[_day].get(i);
			}
		}
		return null;
	}
	
}
