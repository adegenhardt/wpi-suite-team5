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
import edu.wpi.cs.wpisuitetng.modules.calendar.models.SortEvents;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;

public class WeekViewTable extends JTable {
	
	private static final long serialVersionUID = -4325747905323115241L;
	

	boolean isUpdated;					/* whether or not the event list is updated */
	WeekView dayView;					/* instance of dayview for updating current events */
	
	
	private List[] eventsArray; 		/* This is a little dangerous, but I program in C with emacs I'll be fine */
	private List[] rectanglesArray;
	
	public WeekViewTable(DefaultTableModel defaultTableModel) {
		super( defaultTableModel );
		
		isUpdated = false;
		
		// rectangles = new ArrayList< EventRectangle >();
		
		eventsArray = new ArrayList[7];
		rectanglesArray = new ArrayList[7];
		
		// Init the arrays, remind me to slap some casts everywhere
		// Oh! Eclipse already alerts me to cast, noice.
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
					rectanglesArray[k].add(new EventRectangle((Event) eventsArray[k].get(i)));
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
				paintEventRectangle(g, (EventRectangle) rectanglesArray[k].get(i), k);
			}
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
			return ( s.substring( 0, Math.max( 1, s.length() - 3 ) ) + "..." );
		} else {
			return ( s.substring( 0, Math.max( 1, i - 3 ) ) + "..." );
		}
	}
	
	/**
	 * @param i The day of the week (0/Sunday - 6/Saturday)
	 * @return the day's events
	 */
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
			if (GlobalButtonVars.isPersonalView && GlobalButtonVars.isTeamView) {
				eventsArray[i] = EventModel.getInstance().getUserEvents(
						ConfigManager.getConfig().getUserName(),
						eventDay.getYear(), eventDay.getMonth(),
						eventDay.getDay());
			} else if (GlobalButtonVars.isPersonalView) {
				eventsArray[i] = EventModel.getInstance().getPersonalEvents(
						ConfigManager.getConfig().getUserName(),
						eventDay.getYear(), eventDay.getMonth(),
						eventDay.getDay());

			} else if (GlobalButtonVars.isTeamView) {
				eventsArray[i] = EventModel.getInstance().getTeamEvents(
						ConfigManager.getConfig().getUserName(),
						eventDay.getYear(), eventDay.getMonth(),
						eventDay.getDay());
			}
			eventsArray[i] = SortEvents.sortEventsByDate(eventsArray[i]);
		}
	}
	
	/**
	 * Paint a single EventRectangle
	 * @param g The Graphics object to handle the painting
	 * @param rect An EventRectangle to draw
	 */
	void paintEventRectangle( Graphics g, EventRectangle rect, int iOffset ) {
		
		// Convert Calendar to DateInfo
		DateInfo displayedDay = new DateInfo( dayView.getCalendarDay(iOffset) );
		
		final int CURVE_SIZE = 16;			/* Size of curves for rounded rectangles */
		
		int stringHeight;		/* height of string being printed */
		String printString;		/* string to print for Event */
		Color textColor;		/* color to print text for an event */
		
		Event e = rect.getEvent();
		int x = rect.getX();
		int y = rect.getY();
		int width = rect.getWidth();
		int height = rect.getHeight();
		
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
		
		// TODO: Have some way of displaying when an event continues
		// into the next day
		
		g.drawString( printString, x + 2, y + stringHeight );
		
	}
	
	/**
	 * Update the list of rectangles according to updated events
	 * Important: rectangles and events should have a 1:1 correspondance
	 * before entering this function
	 */
	public void updateRectangles(int iOffset) {
		
		// Convert Calendar to DateInfo
		DateInfo displayedDay = new DateInfo( dayView.getCalendarDay(iOffset) );
		
		// Set the half-hour field to 0 to signify the beginning of the day
		displayedDay.setHalfHour( 0 );
		
		// Offset for column to place the events on the appropriate day column
		final int X_COLUMN_OFFSET = getColumnModel().getColumn(1).getWidth();
		
		final int X_OFFSET = getColumnModel().getColumn( 0 ).getWidth() + (X_COLUMN_OFFSET*iOffset);
		final int Y_OFFSET = 0;
		
		
		// Maximum width an event can be
		final int MAX_WIDTH = (getWidth() - X_COLUMN_OFFSET) / 7;
		final int ROW_HEIGHT = getRowHeight();
		int x;
		int y;
		
		// Actual height and width values for the current event
		int width;
		int height;
		
		/* number of events already occuring in a given slot */
		int numPriorEvents[] = new int[ 48 ];
		final int PRIOR_EVENT_WIDTH = 8;	/* Number of pixels to reserve for each prior event */
		
		
		// Set number of prior events to 0 for all slots
		for ( int i = 0; i < numPriorEvents.length; i++ ) {
			numPriorEvents[ i ] = 0;
		}
		
		Event e;			/* the current event */
		DateInfo startDate;
		DateInfo endDate;
		int numEventsInRow; 	/* Number of events in current row */
		EventRectangle r;
		for ( int i = 0; i < eventsArray[iOffset].size(); i++ ) {
			e = (Event) eventsArray[iOffset].get( i );
			
			startDate = e.getStartDate();
			endDate = e.getEndDate();
			
			// Set the half-hour field to 0 to signify the beginning of the day
			displayedDay.setHalfHour( 0 );
			
			// if event started before today, begin drawing at the top
			if ( e.getStartDate().compareTo( displayedDay ) < 0 ) {
				y = Y_OFFSET;
			} else {
				y = Y_OFFSET + ( startDate.getHalfHour() * ROW_HEIGHT );
			}
			
			numEventsInRow = 1;
			for ( int j = i + 1; j < eventsArray[iOffset].size(); j++ ) {
				// check if date has same start time, or begins before the current day
				if (((Event) eventsArray[iOffset].get( j )).getStartDate().equals( startDate ) ||
						((Event) eventsArray[iOffset].get( j )).getStartDate().compareTo( displayedDay ) < 0 ) {
					numEventsInRow++;
				} else {		// since events are sorted, break
								// at the first different start time
					break;
				}
			}
			
			// calculate the width of all events in the row
			width = ( MAX_WIDTH - ( numPriorEvents[ i ] * PRIOR_EVENT_WIDTH ) ) /
					numEventsInRow;
			
			// Add 1 to each row that this event occupies for future events
			// If this runs into the next day, it's the same as occupying all remaining rows
			if ( e.getEndDate().compareTo( displayedDay ) >= 0 ) {
				for ( int j = i + 1; j < 48; j++ ) {
					numPriorEvents[ j ]++;
				}
			} else {
				for ( int j = i + 1; j < endDate.getHalfHour(); j++ ) {
					numPriorEvents[ j ]++;
				}
			}
			
			// draw all events in row
			for ( int j = 0; j < numEventsInRow; j++ ) {
				e = (Event) eventsArray[iOffset].get( i + j );
				r = (EventRectangle) rectanglesArray[iOffset].get( i + j );
				
				endDate = e.getEndDate();
				
				// Calculate offset of x depending on number of prior events
				// and also which event in the row this is
				x = X_OFFSET +
					( numPriorEvents[ startDate.getHalfHour() ] * PRIOR_EVENT_WIDTH ) +
					( width * j );
				
				// if event ends after current day, draw to the bottom
				displayedDay.setHalfHour( 48 );
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
		e1.setDescription("This part isn't too long though");
		e1.setStartDate( time0 );
		e1.setEndDate( time12 );
		
		Event e2 = new Event();
		e2.setName( "event 2" );
		e2.setDescription("This is event 2, things happen at this time");
		e2.setStartDate( time2 );
		e2.setEndDate( time7 );
		
		Event e3 = new Event();
		e3.setName( "event 3" );
		e3.setDescription("I don't want to make this meeting, I have made this event to make sure I miss it.");
		e3.setStartDate( time2 );
		e3.setEndDate( time7 );
		
		Event e4 = new Event();
		e4.setName( "event 4" );
		e4.setDescription("The fourth thing I need to attend today, I enjoy this one");
		e4.setStartDate( time4 );
		e4.setEndDate( time6 );
		
		Event e5 = new Event();
		e5.setName( "event 5" );
		e5.setDescription("Let's try a really long description this time. Never know when I need something to wrap around something, and it would be a shame when that moment happens if I didn't take the time to think this would happen.");
		e5.setStartDate( time7 );
		e5.setEndDate( time18 );
		
		Event e6 = new Event();
		e6.setName( "event 6" );
		e6.setDescription("The sixth event, how descriptive.");
		e6.setStartDate( time7 );
		e6.setEndDate( time12 );
		
		Event e7 = new Event();
		e7.setName( "event 7" );
		e7.setDescription("Lucky number 7th event.");
		e7.setStartDate( time7 );
		e7.setEndDate( time8 );
		
		Event e8 = new Event();
		e8.setName( "event 8" );
		e8.setDescription("Eight is gr8");;
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
	 * @return true if the cell is visible, false otherwise
	 */
	public boolean isCellVisible(int rowIndex) {
		if (!(getParent() instanceof JViewport)) {
			return false;
		}
		JViewport viewport = (JViewport) getParent();
		Rectangle rect = getCellRect(rowIndex, 1, true);
		Point pt = viewport.getViewPosition();
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

	public EventRectangle getRectangle(int _x, int _y, int _day) {
		for (int i=rectanglesArray[_day].size()-1; i >= 0; i--) {
			if (((EventRectangle) rectanglesArray[_day].get(i)).isAtPoint(_x, _y)) {
				return (EventRectangle) rectanglesArray[_day].get(i);
			}
		}
		return null;
	}
	
}
