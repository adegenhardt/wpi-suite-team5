package edu.wpi.cs.wpisuitetng.modules.calendar.refresh;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarSidebar;


 public class RefreshListenerUponEventCreation implements ListDataListener {
	@Override
	public void intervalAdded(ListDataEvent e) {
		CalendarSidebar.getInstance().populateTable();
		
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		CalendarSidebar.getInstance().populateTable();
		
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		CalendarSidebar.getInstance().populateTable();
		
	}
	
}
