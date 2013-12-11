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

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Create the New Event and New Commitment buttons
 * in the toolbar
 */
@SuppressWarnings("serial")
public class EventButtonsPanel extends ToolbarGroupView{
	
	// Create a panel for these buttons
	private final JPanel contentPanel = new JPanel();
	JButton createEventButton;
	JButton createCommitButton;

	/**
	 * Disable the Event button if need be
	 */
	public void disableCreateEventButton() {
		createEventButton.setEnabled(false);
	}
	
	/**
	 * Enable the Event button
	 */
	public void enableCreateEventButton() {
		createEventButton.setEnabled(true);
	}
	
	/**
	 * Disable the Commitment button if need be
	 */
	public void disableCreateCommitButton() {
		createCommitButton.setEnabled(false);
	}
	
	/**
	 * Enable the Commitment button
	 */
	public void enableCreateCommitButton() {
		createCommitButton.setEnabled(true);
	}

	/**
	 * Constructor for EventButtonsPanel.
	 */
	public EventButtonsPanel(){
		super("");
		// Create two buttons
		createEventButton = new JButton("<html>Create <br/>Event</html>");
		createCommitButton = new JButton("<html>Create <br/>Commitment</html>");
		
		// Set panel and button constraints and alignment
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(350);
		createCommitButton.setPreferredSize(new Dimension(175, 40));
		createEventButton.setPreferredSize(new Dimension(175, 40));
		createEventButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set the icons for the buttons
		try {
			final Image imgE = ImageIO.read(getClass().getResource("new_event.png"));
		    createEventButton.setIcon(new ImageIcon(imgE));
		    
		    final Image imgC = ImageIO.read(getClass().getResource("new_commit.png"));
		    createCommitButton.setIcon(new ImageIcon(imgC));
		    
		} catch (IOException ex) {}
		
		// Make sure these buttons are visible by default
		createEventButton.setVisible(true);
		createCommitButton.setVisible(false);
		
		// Add the buttons, set the panel
		contentPanel.add(createCommitButton);
		contentPanel.add(createEventButton);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
	}
	
	// Getters
	public JButton getCreateEventButton() {
		return createEventButton;
	}
	public JButton getCreateCommitButton() {
		return createCommitButton;
	}
}
