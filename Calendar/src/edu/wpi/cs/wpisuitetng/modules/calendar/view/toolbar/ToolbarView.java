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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;

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

	private final ClosableTabCreator tabCreator; 

	/**
	 * Construct this view and all components in it.
	 * @param _tabCreator
	 */
	public ToolbarView(ClosableTabCreator _tabCreator) {
		tabCreator = _tabCreator;

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

		// Create a ButtonGroup so only one of the three Calendar
		// buttons can be selected at once
		final ButtonGroup toggleGroup = new ButtonGroup();
		toggleGroup.add(teamPanel.getDisplayPersonalButton());
		toggleGroup.add(teamPanel.getDisplayTeamButton());
		toggleGroup.add(teamPanel.getDisplayBothButton());

		// Create item listeners for each Calendar view button
		teamPanel.getDisplayPersonalButton().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				final int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					GlobalButtonVars.isPersonalView = true;
					GlobalButtonVars.isTeamView = false;
				}
			}});
		
		teamPanel.getDisplayTeamButton().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				final int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					GlobalButtonVars.isPersonalView = false;
					GlobalButtonVars.isTeamView = true;
				}
			}});
		
		teamPanel.getDisplayBothButton().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				final int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					GlobalButtonVars.isPersonalView = true;
					GlobalButtonVars.isTeamView = true;
				}
			}});
		
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
	public TeamPersButtonsPanel getBothButton(){
		return teamPanel;
	}

}
