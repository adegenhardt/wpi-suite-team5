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
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;


/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Create the Team and Personal calendar toggles
 * in the toolbar
 */
@SuppressWarnings("serial")
public class TeamPersButtonsPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	// Create the buttons
	JToggleButton displayTeamButton;
	JToggleButton displayPersonalButton;
	JToggleButton displayBothButton;
	
	/**
	 * Disable display team button
	 */
	public void disableDisplayTeamButton() {
		displayTeamButton.setEnabled(false);
	}
	/**
	 * Enable display team button
	 */
	public void enableDisplayTeamButton() {
		displayTeamButton.setEnabled(true);
	}
	/**
	 * Disable display personal button
	 */
	public void disableDisplayPersonalButton() {
		displayPersonalButton.setEnabled(false);
	}
	/**
	 * Enable display personal button
	 */
	public void enableDisplayPersonalButton() {
		displayPersonalButton.setEnabled(true);
	}
	/**
	 * Disable display both button
	 */
	public void disableDisplayBothButton() {
		displayBothButton.setEnabled(false);
	}
	/**
	 * Enable display both button
	 */
	public void enableDisplayBothButton() {
		displayBothButton.setEnabled(true);
	}

	/**
	 * Create the panel 
	 */
	public TeamPersButtonsPanel() {
		super("");
		
		// Set the text
		displayTeamButton = new JToggleButton("<html>Team <br/>Calendar</html>");
		displayPersonalButton = new JToggleButton("<html>Personal <br/>Calendar</html>");
		displayBothButton = new JToggleButton("<html>View Both<br/>Calendars</html>");
		
		// Set the size and layout
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(525);
		
		displayTeamButton.setPreferredSize(new Dimension(175, 40));
		displayPersonalButton.setPreferredSize(new Dimension(175, 40));
		displayBothButton.setPreferredSize(new Dimension(175, 40));
		
		displayTeamButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set the icons
		try {
			final Image imgT = ImageIO.read(getClass().getResource("team_calendar.png"));
		    displayTeamButton.setIcon(new ImageIcon(imgT));
		    
		    final Image imgP = ImageIO.read(getClass().getResource("pers_calendar.png"));
		    displayPersonalButton.setIcon(new ImageIcon(imgP));
		    
		    final Image imgB = ImageIO.read(getClass().getResource("both_calendars.png"));
		    displayBothButton.setIcon(new ImageIcon(imgB));
		    
		} catch (IOException ex) {
			System.out.println("Caught IOException");
			ex.printStackTrace();
		}
		
		// Set these buttons visible by default
		displayTeamButton.setVisible(true);
		displayPersonalButton.setVisible(true);
		displayBothButton.setVisible(true);
		
		// Add the buttons to the panel
		contentPanel.add(displayPersonalButton);
		contentPanel.add(displayTeamButton);
		contentPanel.add(displayBothButton);
		contentPanel.setOpaque(false);

		this.add(contentPanel);
	}
	
	// Getters
	public JToggleButton getDisplayTeamButton() {
		return displayTeamButton;
	}
	public JToggleButton getDisplayPersonalButton() {
		return displayPersonalButton;
	}
	public JToggleButton getDisplayBothButton(){
		return displayBothButton;
	}
}
