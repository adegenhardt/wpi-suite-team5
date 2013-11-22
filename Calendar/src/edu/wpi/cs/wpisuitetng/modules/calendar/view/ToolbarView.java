/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Underscore 
 *    
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view;



import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;






import edu.wpi.cs.wpisuitetng.modules.calendar.tabs.ClosableTabCreator;

import javax.swing.JDialog;





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
				/*
				final JDialog eventWindow  = new JDialog();
				final EventEditor eventTab = new EventEditor(); 
				eventWindow.setContentPane(eventTab);
				eventWindow.setBounds(0, 0, 680, 480);
				eventWindow.setLocationRelativeTo(null);
				eventWindow.setTitle("Create Event");
				eventWindow.setVisible(true);
				/*pane.setTabComponentAt(i, new ButtonTabComponent(pane)); */
				final EventEditor eventTab = new EventEditor();
				tabCreator.addClosableTab(eventTab, "Create Event");
			}
		});
		
		eventPanel.getCreateCommitButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/*
				final JDialog commitWindow  = new JDialog();
				final CommitEditor commitTab = new CommitEditor(); 
				commitWindow.setContentPane(commitTab);
				commitWindow.setBounds(0, 0, 680, 480);
				commitWindow.setLocationRelativeTo(null);
				commitWindow.setTitle("Create Commitment");
				commitWindow.setVisible(true);
				/*pane.setTabComponentAt(i, new ButtonTabComponent(pane)); */
				final CommitEditor commitTab = new CommitEditor();
				tabCreator.addClosableTab(commitTab, "Create Commitment");
			}
		});
		
		this.addGroup(eventPanel);
		
		teamPanel.getCreateTeamButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				/*pane.setTabComponentAt(i,
                 new ButtonTabComponent(pane)); */
			}
		});
		
		this.addGroup(teamPanel);
				
		// Prevent this toolbar from being moved
		setFloatable(false);
		
		// Add the panel containing the toolbar buttons
		toolbarPanel = new ToolbarPanel();
		add(toolbarPanel);
	
	}
	
	/**
	 * Method getEditButton.
	
	 * @return EditButtonsPanel */
	public EventButtonsPanel getEventButton(){
		return eventPanel;
	}
	
	public EventButtonsPanel getCommitButton(){
		return eventPanel;
	}
	
	
	
}
