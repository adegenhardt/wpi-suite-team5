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
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Color;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;

/**
 * @author Johnny
 * @version $Revision: 1.0 $
 */
public class DayView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final long serialVersionUID1 = 1L;
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
	private Calendar realDay;

	// Variables used to disallow time column selection
	private final int disabledColumn = 0;
	private int currentColumn = 1;
	
	// String date format that the Day View will give
	private final DateFormat dayFormat = new SimpleDateFormat("MMM/dd/yy");

	/**
	 * Create the panel.
	 */
	public DayView() {
		
		initDay();
		createControls();
		addElements();
		createBounds(); 
		createBackground();
		createTableProperties();
		createUnselectableCol();
		colorCurrentDate();
		
	}
	
	private void createControls() {
		dayTable = new JTable(new DefaultTableModel(
				new Object[][] {
						{"Midnight", null},
						{"12:30", null},
						{"1:00", null},
						{"1:30", null},
						{"2:00", null},
						{"2:30", null},
						{"3:00", null},
						{"3:30", null},
						{"4:00", null},
						{"4:30", null},
						{"5:00", null},
						{"5:30", null},
						{"6:00", null},
						{"6:30", null},
						{"7:00", null},
						{"7:30", null},
						{"8:00", null},
						{"8:30", null},
						{"9:00", null},
						{"9:30", null},
						{"10:00", null},
						{"10:30", null},
						{"11:00", null},
						{"11:30", null},
						{"12:00", null},
						{"12:30", null},
						{"1:00", null},
						{"1:30", null},
						{"2:00", null},
						{"2:30", null},
						{"3:00", null},
						{"3:30", null},
						{"4:00", null},
						{"4:30", null},
						{"5:00", null},
						{"5:30", null},
						{"6:00", null},
						{"6:30", null},
						{"7:00", null},
						{"7:30", null},
						{"8:00", null},
						{"8:30", null},
						{"9:00", null},
						{"9:30", null},
						{"10:00", null},
						{"10:30", null},
						{"11:00", null},
						{"11:30", null},
					},
					new String[] {
						"", this.getStringDay()
					}
				) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					boolean[] columnEditables = new boolean[] {
						false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
		dayTable.setAutoCreateColumnsFromModel(false);
		dayTable.getColumnModel().getColumn(0).setResizable(false);
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(43);
		dayTable.getColumnModel().getColumn(0).setMinWidth(30);
		dayTable.getColumnModel().getColumn(0).setMaxWidth(43);
		dayTable.getColumnModel().getColumn(1).setResizable(false);
		dayTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		dayTable.setSelectionBackground(Color.GREEN);
		dayScroll = new JScrollPane(dayTable);
		// dayScroll.setBounds(0, 0, 554, 569);
		
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
        final ListSelectionModel sel = dayTable.getColumnModel().getSelectionModel();
        sel.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // If the column is disabled, reselect previous column
                if (sel.isSelectedIndex(disabledColumn)) {
                    sel.setSelectionInterval(currentColumn, currentColumn);
                }
                // Set current selection
                else currentColumn = sel.getMaxSelectionIndex();
            }
        });
    }
	
	// Method to color in today's date if the view
	// Is currently on today's date
	// For some reason this doesn't work with the
	// Day view panel, I'll look into this when it
	// Isn't 2:00AM
	private void colorCurrentDate() {
		JTableHeader header = dayTable.getTableHeader();
		// thisDay and displayDay get the respective integer day values
		// So they can be compared because Calendar.equals is garbage
		// Have to compare every individual value because 
		// CALENDAR IS COMPLETE GARBAGE
		int thisYear = currentDay.get(Calendar.YEAR);
		int displayYear = realDay.get(Calendar.YEAR);
		int thisDay = currentDay.get(Calendar.DAY_OF_YEAR);
		int displayDay = realDay.get(Calendar.DAY_OF_YEAR);
		if ((thisDay == displayDay) && (thisYear == displayYear)) {
			header.setBackground(new Color(138,173,209));
		}
		else {
			header.setBackground(UIManager.getColor(JTableHeader.class));
		}
	}
	
	private void initDay() {
		currentDay = Calendar.getInstance();
		realDay = currentDay;
	}
	
	/**
	 * Method refreshDay.
	 * @param newDay Calendar
	 * Changes the day on the column and the stored date
	 */
	public void refreshDay(Calendar newDay) {
		realDay = newDay;
		dayTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(this.getStringDay());
		repaint();
		colorCurrentDate();
	}
	
	// Get the day in a nice string format declared
	// At the top of this file
	public String getStringDay() {
		return dayFormat.format(realDay.getTime());
	}
	
	public String getToday() {
		return dayFormat.format(currentDay.getTime());
	}
	
	public Calendar getRealDay() {
		return realDay;
	}
	
	public void setCurrent() {
		refreshDay(currentDay); 
	}
}
