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
public class DayView extends JPanel {
	
	/**
	 * 
	 */
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
	private Calendar realDay;

	// Variables used to disallow time column selection
	private final int disabledColumn = 0;
	private int currentColumn = 1;
	
	// String date format that the Day View will give
	private final DateFormat dayFormat = new SimpleDateFormat("MMM/dd/yy");
	
	// Will this component be used in the week view? 
	private final boolean isWeekView; 

	/**
	 * Create the panel. 
	
	 * @param isWeek boolean
	 */
	public DayView(boolean isWeek) {
		
		isWeekView = isWeek;
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
		if(isWeekView) {
			dayTable = new JTable(new DefaultTableModel(
					new Object[][] {
							{"Midnight", null},
							{"", null},
							{"1:00", null},
							{"", null},
							{"2:00", null},
							{"", null},
							{"3:00", null},
							{"", null},
							{"4:00", null},
							{"", null},
							{"5:00", null},
							{"", null},
							{"6:00", null},
							{"", null},
							{"7:00", null},
							{"", null},
							{"8:00", null},
							{"", null},
							{"9:00", null},
							{"", null},
							{"10:00", null},
							{"", null},
							{"11:00", null},
							{"", null},
							{"12:00", null},
							{"", null},
							{"1:00", null},
							{"", null},
							{"2:00", null},
							{"", null},
							{"3:00", null},
							{"", null},
							{"4:00", null},
							{"", null},
							{"5:00", null},
							{"", null},
							{"6:00", null},
							{"", null},
							{"7:00", null},
							{"", null},
							{"8:00", null},
							{"", null},
							{"9:00", null},
							{"", null},
							{"10:00", null},
							{"", null},
							{"11:00", null},
							{"", null},
						},
						new String[] {
							"", this.getStringDay()
						}
					) {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						private final boolean[] columnEditables = new boolean[] {
								false, false
						};
						public boolean isCellEditable(int row, int column) {
							return columnEditables[column];
						}
					});
			}
		else {
			dayTable = new JTable(new DefaultTableModel(
					new Object[][] {
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
						},
						new String[] {
							this.getStringDay()
						}
					) {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						private final boolean[] columnEditables = new boolean[] {
								false
						};
						public boolean isCellEditable(int row, int column) {
							return columnEditables[column];
						}
					});
			}
		dayTable.setAutoCreateColumnsFromModel(false);
		dayTable.getColumnModel().getColumn(0).setResizable(false);
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(43);
		dayTable.getColumnModel().getColumn(0).setMinWidth(30);
		dayTable.getColumnModel().getColumn(0).setMaxWidth(43);
		if (isWeekView) {
			dayTable.getColumnModel().getColumn(1).setResizable(false);
			dayTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		}
		else {
			dayTable.getColumnModel().getColumn(0).setPreferredWidth(100);
			dayTable.getColumnModel().getColumn(0).setMinWidth(10);
			dayTable.getColumnModel().getColumn(0).setMaxWidth(238473);
		}
		dayTable.setSelectionBackground(Color.GREEN);
		
		dayTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = -6561816808930289515L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				 final DefaultTableCellRenderer rendererComponent = (DefaultTableCellRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				 
				
				if ((row % 2) == 0 && column != 0) {
					rendererComponent.setBackground(new Color(185, 209, 234));
				}
				else {
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
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            	
                final DefaultTableCellRenderer rendererComponent = (DefaultTableCellRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                rendererComponent.setBackground(UIManager.getColor(JTableHeader.class));
            	return rendererComponent;
            }
        });
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		dayTable.getColumnModel().getColumn(0).setMinWidth(55);
		dayTable.getColumnModel().getColumn(0).setMaxWidth(55);
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
        final ListSelectionModel sel = dayTable.getColumnModel().getSelectionModel();
        sel.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // If the column is disabled, reselect previous column
                if (sel.isSelectedIndex(disabledColumn) && isWeekView) {
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
		if(isWeekView) {
			dayTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(
					this.getStringDay());
		}
		else {
			dayTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(
					this.getStringDay());
		}
		repaint();
		dayTable.getSelectionModel().clearSelection();
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
	
	/**
	 * Method resetCurrent.
	 */
	public void resetCurrent() {
		refreshDay(currentDay); 
	}
}
