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






import javax.swing.JDialog;




import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ToolbarView extends DefaultToolbarView {
	
	/** The panel containing toolbar buttons */
	private final ToolbarPanel toolbarPanel;
	
	private final EventButtonsPanel eventButton = new EventButtonsPanel();


	/**
	 * Construct this view and all components in it.
	 */
	public ToolbarView() {
		eventButton.getCreateEventButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final JDialog eventWindow  = new JDialog();
				final EventEditor eventTab = new EventEditor(); 
				eventWindow.setContentPane(eventTab);
				eventWindow.setBounds(0, 0, 680, 480);
				eventWindow.setLocationRelativeTo(null);
				eventWindow.setTitle("Create Event");
				eventWindow.setVisible(true);
			}
		});
		
		this.addGroup(eventButton);
		
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
		return eventButton;
	}
	
}
