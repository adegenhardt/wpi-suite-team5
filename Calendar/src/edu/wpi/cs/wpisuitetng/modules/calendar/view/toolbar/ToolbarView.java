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

import java.awt.Desktop;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ButtonGroup;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 *          Creates the toolbar view
 */
@SuppressWarnings("serial")
public class ToolbarView extends DefaultToolbarView {

	/** The panel containing toolbar buttons */
	private final ToolbarPanel toolbarPanel;

	private final EventButtonsPanel eventPanel = new EventButtonsPanel();
	private final TeamPersButtonsPanel teamPanel = new TeamPersButtonsPanel();
	private final HelpButtonsPanel helpPanel = new HelpButtonsPanel();

	/**
	 * Construct this view and all components in it.
	 * 
	 * @param _tabCreator
	 */
	public ToolbarView() {

		// Create listeners for the Event and Commitment buttons
		eventPanel.getCreateEventButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final EventEditor eventTab = new EventEditor(ClosableTabCreator
						.getInstance(null).getTabbedPane());
				ClosableTabCreator.getInstance(null).addClosableTab(eventTab,
						"Create Event");
			}
		});

		eventPanel.getCreateCommitButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final CommitEditor commitTab = new CommitEditor();
				ClosableTabCreator.getInstance(null).addClosableTab(commitTab,
						"Create Commitment");
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
		teamPanel.getDisplayPersonalButton().addItemListener(
				new ItemListener() {
					public void itemStateChanged(ItemEvent itemEvent) {
						final int state = itemEvent.getStateChange();
						if (state == ItemEvent.SELECTED
								&& GlobalButtonVars.getInstance().isTriedOnce()) {
							GlobalButtonVars.getInstance().setPersonalView();
						}
						if (state == ItemEvent.SELECTED
								&& !GlobalButtonVars.getInstance()
										.isTriedOnce()) {
							GlobalButtonVars.getInstance().setTriedOnce(true);
						}
					}
				});

		teamPanel.getDisplayTeamButton().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				final int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					GlobalButtonVars.getInstance().setTeamView();
				}
			}
		});

		teamPanel.getDisplayBothButton().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				final int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					GlobalButtonVars.getInstance().setBothView();
				}
			}
		});

		this.addGroup(teamPanel);
		teamPanel.getDisplayPersonalButton().setSelected(true);

		helpPanel.getCreateEventButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String url = "https://github.com/adegenhardt/wpi-suite-team5/wiki/Calendar-Module";

				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI(url));
					} catch (IOException | URISyntaxException e) {}
				} else {
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec("xdg-open " + url);
					} catch (IOException e) {}
				}
			}
		});

		this.addGroup(helpPanel);

		// Prevent this toolbar from being moved
		setFloatable(false);

		// Add the panel containing the toolbar buttons
		toolbarPanel = new ToolbarPanel();
		add(toolbarPanel);

	}

	// Getters
	public EventButtonsPanel getEventButton() {
		return eventPanel;
	}

	public EventButtonsPanel getCommitButton() {
		return eventPanel;
	}

	public TeamPersButtonsPanel getTeamButton() {
		return teamPanel;
	}

	public TeamPersButtonsPanel getPersonalButton() {
		return teamPanel;
	}

	public TeamPersButtonsPanel getBothButton() {
		return teamPanel;
	}

}
