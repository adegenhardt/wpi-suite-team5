package edu.wpi.cs.wpisuitetng.modules.calendar.refresh;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarSidebar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars.DayView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars.WeekView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars.YearViewCalendar;


 public class RefreshListenerUponEventCreation implements ListDataListener {
	@Override
	public void intervalAdded(ListDataEvent e) {
		CalendarSidebar.getInstance().populateTable();
		DayView.getInstance().refreshEvents();
		YearViewCalendar.getInstance(null).refreshYear();
		WeekView.getInstance().refreshEvents();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		CalendarSidebar.getInstance().populateTable();
		DayView.getInstance().refreshEvents();
		YearViewCalendar.getInstance(null).refreshYear();
		WeekView.getInstance().refreshEvents();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		CalendarSidebar.getInstance().populateTable();
		DayView.getInstance().refreshEvents();
		YearViewCalendar.getInstance(null).refreshYear();
		WeekView.getInstance().refreshEvents();
	}
	
}
