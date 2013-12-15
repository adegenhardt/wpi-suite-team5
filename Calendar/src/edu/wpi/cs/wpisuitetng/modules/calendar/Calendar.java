/*********************************************************************************************
 * Copyright (c) 2013 WPI Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 *
 *********************************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.tabs.ClosableTabCreator;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.ToolbarView;

/**
 * This observer is called when a response is received from a request to
 * the server to add a piece of calendar data.
 * Adapted from AddRequirementRequestObserver from WPISuite 
 * Requirements Manager Module
 * 
 * @author Team Underscore
 * @version 1.0
 *
 */
public class Calendar implements IJanewayModule {

	/**
	 * A list of tabs owned by this module
	 */
	List<JanewayTabModel> tabs;
	
	@SuppressWarnings("unused")
	private final ClosableTabCreator tabCreator;
	
	/**
	 * Constructs the main views for this module.
	 */
	public Calendar() {
		// Initialize the list of tabs (however, this module has only one tab)
		tabs = new ArrayList<JanewayTabModel>();
		
		// Constructs and adds the MainPanel	
		final MainView mainPanel = new MainView();
		
		tabCreator = ClosableTabCreator.getInstance(mainPanel.getCalendarPanel().getCalendarTab());
		
		// Create a JPanel to hold the tool-bar for the tab
		final ToolbarView toolbarView = new ToolbarView();

		// Create a tab model that contains the tool-bar panel and the main content panel
		final JanewayTabModel tab1 = 
				new JanewayTabModel(getName(), new ImageIcon(), toolbarView, mainPanel);

		// Add the tab to the list of tabs owned by this module
		tabs.add(tab1);
		
	}
	
	/*
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return "Calendar";
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
	
}
