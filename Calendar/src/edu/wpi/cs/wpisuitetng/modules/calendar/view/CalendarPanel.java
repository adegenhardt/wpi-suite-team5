package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.tabs.*;

/**
 * This class is a JPanel. 
 * 
 * @author Team 5 (B13)
 *
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked" })
public class CalendarPanel extends JPanel {
	//private MonthView aCal; 
	private CalendarTabs tabs;

	public CalendarPanel() {
		setLayout(new BorderLayout(0, 0));
		
		/*aCal = new MonthView(); 
		this.add(aCal, BorderLayout.CENTER); 
		*/
		tabs = new CalendarTabs();
		this.add(tabs, BorderLayout.CENTER);
	}
}
