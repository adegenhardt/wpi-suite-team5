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

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.GregorianCalendar;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MonthView extends JPanel {
	private static final long serialVersionUID = 1L;
	JTable tblCalendar;
	JButton btnPrev, btnNext;
	JLabel lblMonth, lblYear;
	JScrollPane scrlCalendar; 
	DefaultTableModel mtblCalendar;
	JComboBox<String> cmbYear;
	JPanel topPanel;
	int realYear, realMonth, realDay, currentYear, currentMonth;

	/**
	 * Create the panel.
	 */
	public MonthView() {
		setLayout(new BorderLayout());
		createControls();
		registerActionListeners();
		addControls();
		createDate();
		addHeaders();
		createBackground();
		createTableProperties();
		populateTable();
		refreshCalendar(realMonth, realYear);
		this.addComponentListener(new ResizeListener());

	}
	
	private void createControls(){
		topPanel = new JPanel();
		btnNext = new JButton("Next");
		btnPrev = new JButton("Previous");
		lblMonth = new JLabel("January", JLabel.CENTER);
		cmbYear = new JComboBox<String>();
		lblYear = new JLabel("Change year:");
		mtblCalendar = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		tblCalendar = new JTable(mtblCalendar);
		scrlCalendar = new JScrollPane(tblCalendar);
	}
	
	private void registerActionListeners() {
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());
	}
	
	private void addControls() {
		add(topPanel, BorderLayout.NORTH);
		topPanel.add(btnPrev);
		topPanel.add(lblMonth);
		topPanel.add(btnNext);
		btnNext.setPreferredSize(btnPrev.getPreferredSize());
		add(cmbYear, BorderLayout.SOUTH);
		tblCalendar.setBackground(Color.WHITE);
		tblCalendar.setCellSelectionEnabled(true);
		add(scrlCalendar, BorderLayout.CENTER);
	}
	
	private void createDate() {
		final GregorianCalendar cal = new GregorianCalendar(); // Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
		realMonth = cal.get(GregorianCalendar.MONTH); // Get month
		realYear = cal.get(GregorianCalendar.YEAR); // Get year
		currentMonth = realMonth; // Match month and year
		currentYear = realYear;
	}

	private void addHeaders() {
		final String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		for (int i = 0; i < 7; i++) {
			mtblCalendar.addColumn(headers[i]);
		}
	}
	private void createBackground() {
		tblCalendar.getParent().setBackground(tblCalendar.getBackground());
	}

	private void createTableProperties() {
		// No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(true);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		// Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Set row/column count
		tblCalendar.setRowHeight(62);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);
	}

	private void populateTable() {
		for (int i = realYear - 100; i <= realYear + 100; i++) {
			cmbYear.addItem(String.valueOf(i));
		}
	}

	private void refreshCalendar(int month, int year) {
		// Variables
		final String[] months = { "January", "February", "March", "April", "May", "June", "July",
				"August", "September", "October", "November", "December" };
		final int nod, som; // Number Of Days, Start Of Month

		// Allow (or disallow) buttons
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= realYear - 10) {
			btnPrev.setEnabled(false);
		} // Too early
		if (month == 11 && year >= realYear + 100) {
			btnNext.setEnabled(false);
		} // Too late
		lblMonth.setText(months[month]); // Refresh the month label (at the top)

		// Re-align label with calendar
		lblMonth.setBounds(160 - lblMonth.getPreferredSize().width / 2, 25, 180, 25); 
		// Select the correct year in the combo box
		cmbYear.setSelectedItem(String.valueOf(year)); 

		// Clear table
		// THIS NEEDS MODIFICATION TO SUPPORT BUTTON CELLS IN THE CALENDAR 
		// MAKE CHANGES HERE TO REMOVE BUTTONS 
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				tblCalendar.setValueAt(null, i, j);
			}
		}

		// Get first day of month and number of days
		final GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		// Draw calendar
		// THIS NEEDS MODIFICATION TO ADD BUTTONS TO THE CALENDAR 
		for (int i = 1; i <= nod; i++) {
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			tblCalendar.setValueAt(i, row, column);
		}

		// Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

	class tblCalendarRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
				boolean focused, int row, int column) {
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);

			// Adding this code allows for selection to be used on this component
			// For now I think this should work in place of event listeners
			// an if statement checking if selected should work in theory
			if (column == 0 || column == 6) { // Highlight the week-end
				setBackground(Color.white);
			}
			else { // Week
				setBackground(Color.white);
			}
			if (value != null) {
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth 
						&& currentYear == realYear) { // Today
					setBackground(new Color(138,173,209));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}
	
	// This can help resize the calendar component
	// I'm more or less sleepily trying out random
	// Numbers until it works I hope someone has
	// A better idea than me
	class ResizeListener implements ComponentListener {

	    public void componentHidden(ComponentEvent e) {}
	    public void componentMoved(ComponentEvent e) {}
	    public void componentShown(ComponentEvent e) {}

	    public void componentResized(ComponentEvent e) {
	    	// The split pane isn't resizing, but this should work when it does
	        Dimension newSize = e.getComponent().getBounds().getSize();
	        // THIS NEEDS A LITTLE WORK BUT ITS SO CLOSE
	        //Have we tried +1?
	        tblCalendar.setRowHeight((newSize.height - 84)/6);
	         tblCalendar.setRowHeight(6, (((newSize.height - 84)/6) + ((newSize.height - 84)%6)));
	    }
	}

	class btnPrev_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currentMonth == 0) { // Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else { // Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}

	class btnNext_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currentMonth == 11) { // Forward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else { // Forward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}

	class cmbYear_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (cmbYear.getSelectedItem() != null) {
				final String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
}
