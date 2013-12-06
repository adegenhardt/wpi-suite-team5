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
package edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;

import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.tabs.ClosableTabCreator;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CommitEditor;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.EventEditor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Creates the toolbar view
 */
@SuppressWarnings("serial")
public class ToolbarView extends DefaultToolbarView {
	
	/** The panel containing toolbar buttons */
	private final ToolbarPanel toolbarPanel;
	
	private final EventButtonsPanel eventPanel = new EventButtonsPanel();
	private final TeamPersButtonsPanel teamPanel = new TeamPersButtonsPanel();
	
	private ClosableTabCreator tabCreator; 

	/**
	 * Construct this view and all components in it.
	 */
	public ToolbarView(ClosableTabCreator _tabCreator) {
		this.tabCreator = _tabCreator;
		
		eventPanel.getCreateEventButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final EventEditor eventTab = new EventEditor(tabCreator.getTabbedPane());
				tabCreator.addClosableTab(eventTab, "Create Event");
			}
		});
		
		eventPanel.getCreateCommitButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final CommitEditor commitTab = new CommitEditor();
				tabCreator.addClosableTab(commitTab, "Create Commitment");
			}
		});
		
		this.addGroup(eventPanel);
		
		
		// the mouse listener for the Team Calendar Button
		teamPanel.getCreateTeamButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final String userId;
				// Acquire the username from the configuration class within
				// the Janeway module and store it in a variable.
				// ConfigManager.getInstance();
				// userId = ConfigManager.getConfig().getUserName();
						
				// Changing the contents of local data models for
				// event and category objects.
				// EventModel.getInstance().toTeamEventModel( userId );
				// CategoryModel.getInstance().toTeamCategoryModel( userId );
				GlobalButtonVars.isPersonalView = false;
				GlobalButtonVars.isTeamView = true;
			}
		});

		// mouse listener for the Personal Calendar Button
		teamPanel.getCreatePersonalButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				// Changing the contents of local data models for
				// event and category objects.
				// EventModel.getInstance().toPersonalEventModel();
				// CategoryModel.getInstance().toPersonalCategoryModel();
				GlobalButtonVars.isPersonalView = true;
				GlobalButtonVars.isTeamView = false;
				
			}
		});
		
		this.addGroup(teamPanel);
				
		// Prevent this toolbar from being moved
		setFloatable(false);
		
		// Add the panel containing the toolbar buttons
		toolbarPanel = new ToolbarPanel();
		add(toolbarPanel);
	
	}
	
	// Getters
	public EventButtonsPanel getEventButton(){
		return eventPanel;
	}
	public EventButtonsPanel getCommitButton(){
		return eventPanel;
	}
	public TeamPersButtonsPanel getTeamButton(){
		return teamPanel;
	}
	public TeamPersButtonsPanel getPersonalButton(){
		return teamPanel;
	}
	
}
