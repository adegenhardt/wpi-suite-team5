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
package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
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

/**
 * @author Team Underscore
 * @version $Revision: 1.0 $
 */
public class WeekView extends JPanel {

	/**
	 * 
	 */
	// Millis for day in Calendar class
	// Going to use this to calculate first day of week
	private static final long ONE_DAY = 86400000;
	
	private static final long serialVersionUID = 1L;
	private JScrollPane dayScroll;
	private JTable dayTable;
	// This may very well be useless
	// I don't want to break anything now so just leave it

	// Not sure what I want to do with the date values right now
	// Pretty sure they will be handy later (highlighting events maybe)

	// Perhaps the use of the Date class would be better
	// Nevermind, Date is deprecated Calendar is better
	// WHY DID I EVER THINK CALENDAR WAS BETTER JODA-TIME PLEASE
	private Calendar currentDay;
	private Calendar firstDayOfWeek;
	
	// Days of the week
	private String[] weekDays = new String[7];

	// Variables used to disallow time column selection
	private final int disabledColumn = 0;
	private int currentColumn = 1;

	// String date format that the Day View will give
	private final DateFormat dayFormat = new SimpleDateFormat("MMM/dd/yy");

	// Will this component be used in the week view?

	/**
	 * Create the panel.
	 * 
	 * @param isWeek
	 *            boolean
	 */
	public WeekView() {

		initWeek();
		createControls();
		addElements();
		createBounds();
		createBackground();
		createTableProperties();
		createUnselectableCol();

	}

	private void createControls() {
		dayTable = new JTable(new DefaultTableModel(new Object[][] {
				{ "Midnight", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "1:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "2:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "3:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "4:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "5:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "6:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "7:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "8:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "9:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "10:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "11:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "12:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "1:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "2:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "3:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "4:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "5:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "6:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "7:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "8:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "9:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "10:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null },
				{ "11:00", null, null, null, null, null, null, null },
				{ "", null, null, null, null, null, null, null }, },
				new String[] { "", weekDays[0], weekDays[1], weekDays[2], weekDays[3], weekDays[4], weekDays[5], weekDays[6] }) {

			private static final long serialVersionUID = 1L;
			private final boolean[] columnEditables = new boolean[] { false,
					false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JTableHeader header = dayTable.getTableHeader();
		
		// Set up the custom table renderers here
		
		dayTable.setSelectionBackground(Color.GREEN);
		
		dayTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				 DefaultTableCellRenderer rendererComponent = (DefaultTableCellRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				 
				
				if ((row % 2) == 0 && column != 0) {
					rendererComponent.setBackground(new Color(185, 209, 234));
				}
				else {
					rendererComponent.setBackground(Color.white);
				}
				return rendererComponent;
			}
		});
		
        header.setDefaultRenderer(new DefaultTableCellRenderer() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            	
                DefaultTableCellRenderer rendererComponent = (DefaultTableCellRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            	String thisDay = getStringDay(currentDay);
            	
            	if (column != 0 && weekDays[column - 1].equals(thisDay)) {
            		rendererComponent.setBorder(BorderFactory.createCompoundBorder(rendererComponent.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            		rendererComponent.setBackground(new Color(138, 173, 209));
            	}
            	else {
            		rendererComponent.setBorder(BorderFactory.createCompoundBorder(rendererComponent.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            		rendererComponent.setBackground(UIManager.getColor(JTableHeader.class));
            	}
            	
            	return rendererComponent;
            }
        });
		
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		dayTable.getColumnModel().getColumn(0).setMinWidth(55);
		dayTable.getColumnModel().getColumn(0).setMaxWidth(55);
		dayTable.setAutoCreateColumnsFromModel(false);
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

	private void initWeek() {
		currentDay = Calendar.getInstance();
		
		Calendar shiftWeek = Calendar.getInstance();
		
	    while(shiftWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	         shiftWeek.setTimeInMillis(shiftWeek.getTimeInMillis() - ONE_DAY);
	    }
	    
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

	// Get the day in a nice string format declared
	// At the top of this file
	public String getStringDay(Calendar theDay) {
		return dayFormat.format(theDay.getTime());
	}
	
	private void changeWeekArray(Calendar firstDay) {
		Calendar tempDay = (Calendar) firstDay.clone();
		weekDays[0] = this.getStringDay(firstDay);
		for(int i=1; i < weekDays.length; i++) {
			tempDay.add(Calendar.DATE, 1);
			weekDays[i] = this.getStringDay(tempDay);
		}
	}
	
	private void updateHeaders() {
		for(int i=0; i < weekDays.length; i++) {
			dayTable.getTableHeader().getColumnModel().getColumn(i + 1).setHeaderValue(weekDays[i]);
		}
	}
	
	public void forwardWeek() {
		firstDayOfWeek.add(Calendar.DATE, 7);
		refreshWeek((Calendar) firstDayOfWeek);
	}
	
	public void backWeek() {
		firstDayOfWeek.add(Calendar.DATE, -7);
		refreshWeek((Calendar) firstDayOfWeek);
	}
	
	public void currentWeek() {
		Calendar resetWeek = Calendar.getInstance();
		
	    while(resetWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	         resetWeek.setTimeInMillis(resetWeek.getTimeInMillis() - ONE_DAY);
	    }
	    
	    refreshWeek((Calendar) resetWeek);
	}

	/**
	 * Method resetCurrent.
	 */
}
