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

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.tabs.ClosableTabCreator;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * MonthView creates the Month tab in the Calendar module
 */
@SuppressWarnings("serial")
public class MonthView extends JPanel {

	protected static final int DAY_TAB = 3;
	MonthViewTable tblCalendar;
	JButton btnPrev, btnNext;
	JLabel lblMonth, lblYear;
	DefaultTableModel mtblCalendar;
	JComboBox<String> cmbYear;
	JPanel buttonPanel;
	int realYear, realMonth, realDay, currentYear, currentMonth;
	private JButton btnThisMonth;
	private JPanel navPanel;
	private JPanel labelPanel;
	private JPanel calAndHeader;
	
	private int rowFocus = -1;
	private int colFocus = -1;
	
	private static MonthView thisInstance;

	/**
	 * Create the Month mainPanel
	 * @return instance
	 */
	public static MonthView getInstance() {
		if (thisInstance == null) {
			thisInstance = new MonthView();
		}
		return thisInstance;
	}
	
	
	private MonthView() {
		setLayout(new BorderLayout());
		createControls();
		addControls();
		registerActionListeners();
		createDate();
		addHeaders();
		createBackground();
		createTableProperties();
		populateTable();
		refreshCalendar(realMonth, realYear);
		this.addComponentListener(new ResizeListener());
		tblCalendar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				colorChange(e.getX(), e.getY());
				tblCalendar.repaint();
			}
		});
		tblCalendar.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Integer day = new Integer(dayClicked(arg0.getX(), arg0.getY()));
				if (day != null) {
					Calendar selectDay = Calendar.getInstance();
					selectDay.set(Calendar.YEAR, currentYear);
					selectDay.set(Calendar.MONTH, currentMonth);
					selectDay.set(Calendar.DATE, day);
			        selectDay.set(Calendar.HOUR_OF_DAY, 0);
			        selectDay.set(Calendar.MINUTE, 0);
			        selectDay.set(Calendar.SECOND, 0);
			        selectDay.set(Calendar.MILLISECOND, 0);
					DayView.getInstance().refreshDay(selectDay);
					ClosableTabCreator.getInstance(null).getTabbedPane().setSelectedIndex(DAY_TAB);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	// Build the various GUI aspects of the Month view
	private void createControls(){
		buttonPanel = new JPanel();
		calAndHeader = new JPanel();
		
		// Set size constraints for month label
		cmbYear = new JComboBox<String>();
		lblYear = new JLabel("Change year:");
		// The Month view is essentially a table
		mtblCalendar = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		tblCalendar = new MonthViewTable(mtblCalendar);
		tblCalendar.setMonthView( this );
	}
	
	/**
	 * 
	 * @return the current selected year
	 */
	public int getCurrentYear() {
		return currentYear;
	}
	
	/**
	 * 
	 * @return the current selected month
	 */
	public int getCurrentMonth() {
		return currentMonth;
	}
	
	// Create the action listeners, to be defined
	private void registerActionListeners() {
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());
		btnThisMonth.addActionListener(new thisMonth_Action());
	}
	// Add the various controls and constraints to the GUI
	private void addControls() {
		final Dimension mlabelDim = new Dimension(115, 15);
		
		add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		
		calAndHeader.setLayout(new BorderLayout());
		
		navPanel = new JPanel();
		
		// Create the buttons for Previous, This, and Next month navigation
		btnPrev = new JButton("Previous Month");
		navPanel.add(btnPrev);
		btnThisMonth = new JButton("This Month");
		navPanel.add(btnThisMonth);
		btnNext = new JButton("Next Month");
		btnNext.setPreferredSize(btnPrev.getPreferredSize());
		navPanel.add(btnNext);
		// Set the various panels and labels
		labelPanel = new JPanel();
		buttonPanel.add(labelPanel);
		buttonPanel.add(navPanel);
		lblMonth = new JLabel("January", javax.swing.SwingConstants.CENTER);
		labelPanel.add(lblMonth);
		lblMonth.setMinimumSize(mlabelDim);
		lblMonth.setPreferredSize(mlabelDim);
		lblMonth.setMaximumSize(mlabelDim);
		// Set layout constraints
		add(cmbYear, BorderLayout.SOUTH);
		tblCalendar.setBackground(Color.WHITE);
		tblCalendar.setCellSelectionEnabled(true);
		calAndHeader.add(tblCalendar, BorderLayout.CENTER);
		calAndHeader.add(tblCalendar.getTableHeader(), BorderLayout.NORTH);
		add(calAndHeader, BorderLayout.CENTER);
	}
	// Find the date
	private void createDate() {
		final GregorianCalendar cal = new GregorianCalendar(); // Create calendar
		realDay = cal.get(java.util.Calendar.DAY_OF_MONTH); // Get day
		realMonth = cal.get(java.util.Calendar.MONTH); // Get month
		realYear = cal.get(java.util.Calendar.YEAR); // Get year
		currentMonth = realMonth; // Match month and year
		currentYear = realYear;
	}
	// Add labels for the days of the week
	private void addHeaders() {
		final String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		for (int i = 0; i < 7; i++) {
			mtblCalendar.addColumn(headers[i]);
		}
	}
	// Set a background
	private void createBackground() {
		tblCalendar.getParent().setBackground(tblCalendar.getBackground());
	}
	
	// Set constraints for the table
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
	// Fill the combo box with the years
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
		if (month == 0 && year <= realYear - 100) {
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
		// TODO: THIS NEEDS MODIFICATION TO SUPPORT BUTTON CELLS IN THE CALENDAR 
		// MAKE CHANGES HERE TO REMOVE BUTTONS 
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				tblCalendar.setValueAt(null, i, j);
			}
		}

		// Get first day of month and number of days
		final GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		som = cal.get(java.util.Calendar.DAY_OF_WEEK);

		// Draw calendar
		// TODO: THIS NEEDS MODIFICATION TO ADD BUTTONS TO THE CALENDAR 
		for (int i = 1; i <= nod; i++) {
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			tblCalendar.setValueAt(i, row, column);
		}

		// Apply renderer
		tblCalendarRenderer renderer = new tblCalendarRenderer();
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
		renderer.setVerticalAlignment( SwingConstants.TOP );
		
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), renderer );
		tblCalendar.setUpdated( false );	/* notify the table that it needs to update */
	}

	/**
	 * @author Team Underscore
	 * @version $Revision: 1.0$
	 * 
	 * Cell Renderer for the calendar
	 */
	class tblCalendarRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
				boolean focused, int row, int column) {
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);

			// Adding this code allows for selection to be used on this component
			// For now I think this should work in place of event listeners
			// an if statement checking if selected should work in theory
			setBackground(Color.white);
			if (rowFocus == row && colFocus == column) {
				setBackground(Color.orange);
			}
			if (value != null) {
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth 
						&& currentYear == realYear) { // Today
					setBackground(new Color(138, 173, 209));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}
	
	/**
	 * @author Team Underscore
	 * @version $Revision: 1.0$
	 * 
	 * Component Listener for resizing the calendar
	 */
	class ResizeListener implements ComponentListener {

	    public void componentHidden(ComponentEvent e) {}
	    public void componentMoved(ComponentEvent e) {}
	    public void componentShown(ComponentEvent e) {}

	    public void componentResized(ComponentEvent e) {
	        final Dimension newSize = e.getComponent().getBounds().getSize();
	        tblCalendar.setRowHeight((newSize.height - 105) / 6);
	        tblCalendar.setRowHeight(6, (((newSize.height - 105) / 6) + 
	        		 ((newSize.height - 105) % 6)));
	    }
	}

	/**
	 * @author Team Underscore
	 * @version $Revision: 1.0$
	 * 
	 * Action Listener for the Previous Button
	 */
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

	/**
	 * @author Team Underscore
	 * @version $Revision: 1.0$
	 * 
	 * Action Listener for the Next button
	 */
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

	/**
	 * @author Team Underscore
	 * @version $Revision: 1.0$
	 * 
	 * Action Listener for the Year dropdown menu
	 */
	class cmbYear_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (cmbYear.getSelectedItem() != null) {
				final String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
			}
			
			refreshCalendar(currentMonth, currentYear);
		}
	}
	
	/**
	 * 
	 * @author Team_
	 * @version 1.0
	 *
	 */
	class thisMonth_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if ((currentMonth != realMonth) || (currentYear != realYear)) {
				currentMonth = realMonth;
				currentYear = realYear;
				refreshCalendar(realMonth, realYear);
			}
		}
	}
	
	/**
	 * refresh events
	 */
	public void refreshEvents() {
		refreshCalendar(currentMonth, currentYear);
	}
	
	private int dayClicked(int _x, int _y) {
		for (int i = 0; i < tblCalendar.getRowCount(); i++) {
			for (int k = 0; k < tblCalendar.getColumnCount(); k++) {
				Rectangle cellRect = tblCalendar.getCellRect(i, k, false);
				int x = cellRect.x;
				int y = cellRect.y;
				int width = cellRect.width;
				int height = cellRect.height;
				if (((_x >= x) && (_x <= x + width)) && ((_y >= y) && (_y <= y + height))) {
					if (tblCalendar.getValueAt(i, k) == null) {
						return (Integer) null;
					}
					else {
						return (int) tblCalendar.getValueAt(i, k);
					}
				}
			}
		}
		return (Integer) null;
	}
	
	private void colorChange(int _x, int _y) {
		for (int i = 0; i < tblCalendar.getRowCount(); i++) {
			for (int k = 0; k < tblCalendar.getColumnCount(); k++) {
				Rectangle cellRect = tblCalendar.getCellRect(i, k, false);
				int x = cellRect.x;
				int y = cellRect.y;
				int width = cellRect.width;
				int height = cellRect.height;
				if (((_x >= x) && (_x <= x + width)) && ((_y >= y) && (_y <= y + height))) {
					if (tblCalendar.getValueAt(i, k) == null) {
					}
					else {
						rowFocus = i;
						colFocus = k;
						return;
					}
				}
			}
		}
		return;
	}
}
