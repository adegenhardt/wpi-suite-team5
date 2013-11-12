package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MonthView extends JPanel {

    private static final long serialVersionUID = 1L;
    static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext;
    static JTable tblCalendar;
    static DefaultTableModel mtblCalendar;
    static JScrollPane stblCalendar;
    static int realYear, realMonth, realDay, currentYear, currentMonth;
	static JComboBox cmbYear;

    public MonthView() {

	setLayout(null);
	// Set look and feel will make the look of the Janeway different on all fronts
	// Leave the look and feel default for now, make it prettier later
	// setLookAndFeel();
	createControls();
	setBorder();
	registerActionListeners();
	addControls();
	setBounds();
	setDate();
	addHeaders();
	setBackground();
	setTableProperties();
	populateTable();
	refreshCalendar(realMonth, realYear);

    }

    /*
    // Sets the look and feel based on operating system, if supported by Java, if not just get the defaults
    private void setLookAndFeel() {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException e) {
	} catch (InstantiationException e) {
	} catch (IllegalAccessException e) {
	} catch (UnsupportedLookAndFeelException e) {
	}
    }
	*/
    // Create the UI controls for the Calendar and surrounding components
    // Buttons to change month
    // Dropdown to change year 
    // Label for current selected month
    private void createControls() {
	lblMonth = new JLabel("January");
	lblYear = new JLabel("Change year:");
	cmbYear = new JComboBox();
	btnPrev = new JButton("<");
	btnNext = new JButton(">");
	mtblCalendar = new DefaultTableModel() {

	    private static final long serialVersionUID = 1L;

	    public boolean isCellEditable(int rowIndex, int mColIndex) {
		return false;
	    }
	};
	tblCalendar = new JTable(mtblCalendar);
	//I can not for the life of me make the cells selectable, this doesn't work may have something to do with the renderer
	//tblCalendar.setCellSelectionEnabled(true);
	stblCalendar = new JScrollPane(tblCalendar);
    }

    private void setBorder() {
	this.setBorder(BorderFactory.createTitledBorder("Calendar"));
    }

    // Set the action listenrer for the button components 
    private void registerActionListeners() {
	btnPrev.addActionListener(new btnPrev_Action());
	btnNext.addActionListener(new btnNext_Action());
	cmbYear.addActionListener(new cmbYear_Action());
    }

    private void addControls() {
	this.add(lblMonth);
	this.add(lblYear);
	this.add(cmbYear);
	this.add(btnPrev);
	this.add(btnNext);
	this.add(stblCalendar);
    }

    private void setBounds() {
	this.setBounds(0, 0, 626, 600);
	lblMonth.setBounds(88, 25, 100, 25);
	lblYear.setBounds(520, 230, 80, 20);
	cmbYear.setBounds(520, 261, 80, 20);
	btnPrev.setBounds(10, 25, 50, 25);
	btnNext.setBounds(260, 25, 50, 25);
	stblCalendar.setBounds(10, 50, 500, 420);
    }

    private void setDate() {
	GregorianCalendar cal = new GregorianCalendar(); // Create calendar
	realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
	realMonth = cal.get(GregorianCalendar.MONTH); // Get month
	realYear = cal.get(GregorianCalendar.YEAR); // Get year
	currentMonth = realMonth; // Match month and year
	currentYear = realYear;
    }

    private void addHeaders() {
	String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	for (int i = 0; i < 7; i++) {
	    mtblCalendar.addColumn(headers[i]);
	}
    }

    private void setBackground() {
	tblCalendar.getParent().setBackground(tblCalendar.getBackground());
    }

    private void setTableProperties() {
	// No resize/reorder
	tblCalendar.getTableHeader().setResizingAllowed(false);
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

    private static void refreshCalendar(int month, int year) {
	// Variables
	String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
	int nod, som; // Number Of Days, Start Of Month

	// Allow/disallow buttons
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
	// THIS NEEDS MODIFICATION TO SUPPORT BUTTON CELLS IN THE CALENDAR MAKE CHANGES HERE TO REMOVE BUTTONS 
	for (int i = 0; i < 6; i++) {
	    for (int j = 0; j < 7; j++) {
		mtblCalendar.setValueAt(null, i, j);
	    }
	}

	// Get first day of month and number of days
	GregorianCalendar cal = new GregorianCalendar(year, month, 1);
	nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	som = cal.get(GregorianCalendar.DAY_OF_WEEK);

	// Draw calendar
	// THIS NEEDS MODIFICATION TO ADD BUTTONS TO THE CALENDAR 
	for (int i = 1; i <= nod; i++) {
	    int row = new Integer((i + som - 2) / 7);
	    int column = (i + som - 2) % 7;
	    mtblCalendar.setValueAt(i, row, column);
	}

	// Apply renderers
	tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
    }

    static class tblCalendarRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
	    super.getTableCellRendererComponent(table, value, selected, focused, row, column);
	    
	    // Adding this code allows for selection to be used on this component
	    // For now I think this should work in place of event listeners
	    // Apparently doesn't work 
	    // SHOULD LOOK AT THIS LATER
	    /*
	    if (selected == true) {
	    	setBackground(new Color(220,220,0));
	    }
	    */
	    if (column == 0 || column == 6) { // Week-end
		setBackground(new Color(255, 220, 220));
	    }
	    else { // Week
		setBackground(new Color(255, 255, 255));
	    }
	    if (value != null) {
		if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear) { // Today
		    setBackground(new Color(220, 220, 255));
		}
	    }
	    setBorder(null);
	    setForeground(Color.black);
	    return this;
	}
    }

    static class btnPrev_Action implements ActionListener {
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

    static class btnNext_Action implements ActionListener {
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

    static class cmbYear_Action implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if (cmbYear.getSelectedItem() != null) {
		String b = cmbYear.getSelectedItem().toString();
		currentYear = Integer.parseInt(b);
		refreshCalendar(currentMonth, currentYear);
	    }
	}
    }
}
