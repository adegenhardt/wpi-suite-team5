package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JPanel;

/**
 * This panel fills the main content area of the tab for this module.
 * 
 * @author Team 5 (B13)
 *
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {

	/** The panel containing the post board */
	private final CalendarPanel calendarPanel;
	
	/**
	 * Construct the panel.
	 */
	public MainView() {
		// Add the calendar panel to this view
		calendarPanel = new CalendarPanel();
		add(calendarPanel);
	}
}
