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

package edu.wpi.cs.wpisuitetng.modules.calendar.refresh;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarSidebar;

/**
 * This ListDataListener is used to monitor whether or not a change has
 * occurred in the list to which it has been applied. If a change occurs in
 * the list that this ListDataListener is monitoring, then the view components
 * of the GUI are updated to reflect that change.
 * 
 * @author Team Underscore
 * @version 1.0
 *
 */
 public class RefreshListenerCategories implements ListDataListener {
	@Override
	public void intervalAdded(ListDataEvent e) {
		CalendarSidebar.getInstance().populateCategoryDropDown();
		CalendarSidebar.getInstance().populateFiltersWindow();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		CalendarSidebar.getInstance().populateCategoryDropDown();
		CalendarSidebar.getInstance().populateFiltersWindow();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		CalendarSidebar.getInstance().populateFiltersWindow();
		CalendarSidebar.getInstance().populateFiltersWindow();
	}
	
}
