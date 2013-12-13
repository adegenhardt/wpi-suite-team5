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
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
 * MonthViewTable handles displaying the Calendar and all evnets on it
 */
public class MonthViewTable extends JTable {

	private MonthView monthView;
	private List< Event > events;		/* current day's events to display */
	private boolean isUpdated;					/* whether or not the event list is updated */
	
	/**
	 * Create a month view table using a defaultTableModel as a parameter
	 * @param mtblCalendar The default tablemodel whose data will be used
	 */
	public MonthViewTable(DefaultTableModel mtblCalendar) {
		super( mtblCalendar );
	}
	
	/**
	 * 
	 * @return the corresponding month view for this table
	 */
	public MonthView getMonthView() {
		return monthView;
	}

	/**
	 * 
	 * @param monthView the new month view for this table to use
	 */
	public void setMonthView(MonthView monthView) {
		this.monthView = monthView;
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
			isUpdated = true;
		}

		paintEvents( g );
	}

	/**
	 * Update the list of events stored with this table
	 */
	private void updateEvents() {
		// TODO Replace sample tests with the real ones
		
		events = new ArrayList< Event >();
		
		Event e1 = new Event();
		
		e1.setName( "First event with the fairly incredibly long title!" );
		e1.setStartDate( new DateInfo( 2013, 11, 13, 0 ) );
		e1.setEndDate( new DateInfo( 2013, 11, 25, 1 ) );
		
		events.add( e1 );
		
		Event e2 = new Event();
		
		e2.setName( "Second!" );
		e2.setStartDate( new DateInfo( 2013, 11, 13, 0 ) );
		e2.setEndDate( new DateInfo( 2013, 11, 26, 1 ) );
		
		events.add( e2 );
		
		Event e3 = new Event();
		
		e3.setName( "Third even with yet another moderately long title!" );
		e3.setStartDate( new DateInfo( 2013, 11, 15, 0 ) );
		e3.setEndDate( new DateInfo( 2014, 1, 30, 1 ) );
		
		events.add( e3 );
		
		Event e4 = new Event();
		
		e4.setName( "Fourth!" );
		e4.setStartDate( new DateInfo( 2013, 11, 15, 0 ) );
		e4.setEndDate( new DateInfo( 2013, 11, 15, 1 ) );
		
		events.add( e4 );
		
		Event e5 = new Event();
		
		e5.setName( "Fifth!" );
		e5.setStartDate( new DateInfo( 2013, 11, 15, 0 ) );
		e5.setEndDate( new DateInfo( 2013, 11, 29, 1 ) );
		
		events.add( e5 );
		
//		int year = monthView.getCurrentYear();
//		int month = monthView.getCurrentMonth();
//		if ( GlobalButtonVars.isPersonalView && GlobalButtonVars.isTeamView ) {
//			events = EventModel.getInstance().getUserEvents(
//					ConfigManager.getConfig().getUserName(), year, month );
//		} else if ( GlobalButtonVars.isPersonalView ) {
//			events = EventModel.getInstance().getPersonalEvents(
//					ConfigManager.getConfig().getUserName(), year, month );
//		} else if ( GlobalButtonVars.isTeamView ) {
//			events = EventModel.getInstance().getTeamEvents(
//					ConfigManager.getConfig().getUserName(), year, month );
//		}
//		events = SortEvents.sortEventsByDate( events );
		
	}
	
	/**
	 * Paint the stored events on the table
	 * @param g The graphics component to handle the drawings
	 */
	private void paintEvents(Graphics g) {
		
		// Don't go any further if there are no events, as the rest of the
		// function assumes at least 1 event
		if ( events.size() == 0 ) {
			return;
		}
		
		// Get first day of month and number of days
		final GregorianCalendar cal = new GregorianCalendar( 
				monthView.getCurrentYear(), monthView.getCurrentMonth(), 1 );
		
		// Number of days in the month
		final int NUM_DAYS = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		
		// First day of the week in the month (0-6)
		final int FIRST_DAY = cal.get(java.util.Calendar.DAY_OF_WEEK);
		
		/* start of the month */
		final DateInfo START_OF_MONTH = new DateInfo( 
				monthView.getCurrentYear(),
				monthView.getCurrentMonth(),
				1,
				0 );
		
		/* Very final hour in month */
		final DateInfo END_OF_MONTH = new DateInfo( 
				monthView.getCurrentYear(),
				monthView.getCurrentMonth(),
				NUM_DAYS - 1,
				48 );
		
		final int COLUMN_WIDTH = getColumnModel().
				getColumn( 0 ).getWidth();		/* current width of the column */
		final int ROW_HEIGHT = getRowHeight();	/* current height of the rows */
		
		/* height of each line within a row */
		final int LINE_HEIGHT = g.getFontMetrics().getMaxAscent();
		
		/* Size of the arrows to indicate continuous events */
		final int ARROW_SIZE = g.getFontMetrics().stringWidth( "-> " );
		
		/* Max string width in pixels, after allocating space for arrows */
		final int MAX_STRING_WIDTH = COLUMN_WIDTH - ( 2 * ARROW_SIZE ) - 1;
		
		// Number of lines to display in each cell
		// 2 slots are reserved for the date and the number of events not displayed
		final int NUM_LINES = ( ROW_HEIGHT / LINE_HEIGHT )
				- 2;
		
		// The last occupied date in each row
		int[] lastOccupiedDate = new int[ NUM_LINES ];
		
		// Set all rows to -1, ie: not yet occupied
		for ( int i = 0; i < NUM_LINES; i++ ) {
			lastOccupiedDate[ i ] = -1;
		}
		
		// Number of events remaining (ie: not displayed) on each day
		int[] numEventsRemaining = new int[ NUM_DAYS ];
		
		// Clear number of non-displayed events
		for ( int i = 0; i < NUM_DAYS; i++ ) {
			numEventsRemaining[ i ] = 0;
		}
		
		int occupiedRow;	/* Row occupied by current event */
		Event e;				/* Current event */
		int startDay;			/* Day within month to start drawing */
		int endDay;				/* Day within month to stop drawing */
		for ( int i = 0; i < events.size(); i++ ) {
			e = events.get( i );
			occupiedRow = -1;
			
			// Determine if event starts before the beginning of the month or not
			if ( e.getStartDate().compareTo( START_OF_MONTH ) < 0 ) {
				startDay = 0;
			} else {
				startDay = e.getStartDate().getDay();
			}
			
			// Determine if event ends after the month or not
			if ( e.getEndDate().compareTo( END_OF_MONTH ) > 0 ) {
				endDay = NUM_DAYS - 1;
			} else {
				endDay = e.getEndDate().getDay();
			}
			
			// Find earliest open row
			occupiedRow = findEarliestOpenLine( startDay, endDay, lastOccupiedDate );
			
			// If no row was open at all
			if ( occupiedRow == -1 ) {
				// Mark all numEventRemaining slots
				for ( int j = startDay; j < endDay; j++ ) {
					numEventsRemaining[ j ]++;
				}
			} else {
				// Mark numEventRemaining slots prior to the start of the event
				for ( int j = startDay; j <= lastOccupiedDate[ occupiedRow ]; j++ ) {
					numEventsRemaining[ j ]++;
				}
				
				// draw and write the event where visible
				int row;
				int column;
				int x;
				int y;
				String printString = trimString( e.getName(), g.getFontMetrics(), MAX_STRING_WIDTH );
				
				// Determine and set text color
				Color textColor = new Color( Math.max( e.getColor().getRed() - 120, 0 ),
						Math.max( e.getColor().getGreen() - 120, 0 ),
						Math.max( e.getColor().getBlue() - 120, 0 ) );
				
				// draw rectangles for each event
				for ( int j = Math.max( lastOccupiedDate[ occupiedRow ] + 1, startDay ); 
						j <= endDay; j++ ) {
					row = ( j + FIRST_DAY - 1 ) / 7;
					column = ( j + FIRST_DAY - 1 ) % 7;
					
					x = ( column * COLUMN_WIDTH );
					y = ( ROW_HEIGHT * row ) + ( ( 1 + occupiedRow ) * LINE_HEIGHT );
					
					g.setColor( e.getColor() );
					
					g.fillRect( x, y, COLUMN_WIDTH, LINE_HEIGHT );
					
					g.setColor( textColor );
					g.drawString( 	printString,
									x + 1 + ARROW_SIZE,
									y + LINE_HEIGHT );
					
					// If not first day, draw the left arrow
					if ( ( j != lastOccupiedDate[ occupiedRow ] && j != startDay )
							|| e.getStartDate().compareTo( START_OF_MONTH ) < 0 ) {
						g.drawString( 	"<-",
										x + 1,
										y + LINE_HEIGHT );
					}
					
					// If not last day, draw the right arrow
					if ( j != endDay || e.getEndDate().compareTo( END_OF_MONTH ) > 0 ) {
						g.drawString( 	"->",
										x + COLUMN_WIDTH - ARROW_SIZE,
										y + LINE_HEIGHT );
					}
				}
				
				
				// Update last occupied date of row occupied
				lastOccupiedDate[ occupiedRow ] = endDay;
			}
			
		}
		
		g.setColor( Color.BLACK );
		
		int row;
		int column;
		int x;
		int y;
		// Draw the "(# more)" text at the bottom of each day
		for ( int i = 0; i < NUM_DAYS; i++ ) {
			if ( numEventsRemaining[ i ] > 0 ) {
				row = ( i + FIRST_DAY - 1 ) / 7;
				column = ( i + FIRST_DAY - 1 ) % 7;
				
				x = ( column * COLUMN_WIDTH ) + 1;
				y = ( ROW_HEIGHT * row ) + ( ( NUM_LINES + 2 ) * LINE_HEIGHT );
				
				g.drawString( "( " + numEventsRemaining[ i ] + " more)", x, y );
				
			}
		}
	}
	
	/**
	 * Find the earliest open slot available between two days
	 * @param startDay the earliest day to check
	 * @param endDay the latest dday to check
	 * @param lastOccupiedDate a list of integers corresponding to latest occupied days in a line
	 * @return Either the row with the earliest open slot, or -1 if not found
	 */
	public int findEarliestOpenLine( int startDay, int endDay, int[] lastOccupiedDate ) {
		
		int earliestOpenLine = -1;		// default to -1
		for ( int i = 0; i < lastOccupiedDate.length; i++ ) {
			
			// if any slot is free before the start day, return it immediately
			if ( lastOccupiedDate[ i ] < startDay ) {
				return i;
			}
			
			// if a slot is freed by the end day, and it's freed
			// the earlier than the last stored one, store it
			if ( lastOccupiedDate[ i ] < endDay &&
					( ( earliestOpenLine == -1 ) ||
					( lastOccupiedDate[ i ] < lastOccupiedDate[ earliestOpenLine ] ) ) ) {
				earliestOpenLine = i;
			}
		}
		
		return earliestOpenLine;
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
	
}
