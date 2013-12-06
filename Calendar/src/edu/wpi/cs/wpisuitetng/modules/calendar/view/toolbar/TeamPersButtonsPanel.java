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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;

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
	final JButton createCancelButton = new JButton("<html>Cancel<br />Changes</html>");
	// Create the buttons
	JButton createTeamButton;
	JButton createPersonalButton;
	
	// Methods to enable and disable these buttons
	public void disableCreateTeamButton() {
		createTeamButton.setEnabled(false);
		GlobalButtonVars.isTeamView = false;
		GlobalButtonVars.isPersonalView = true;
	}
	public void enableCreateTeamButton() {
		createTeamButton.setEnabled(true);
		GlobalButtonVars.isTeamView = true;
		GlobalButtonVars.isPersonalView = false;
	}
	public void disableCreatePersonalButton() {
		createTeamButton.setEnabled(false);
		GlobalButtonVars.isTeamView = true;
		GlobalButtonVars.isPersonalView = false;
	}
	public void enableCreatePersonalButton() {
		createTeamButton.setEnabled(true);
		GlobalButtonVars.isTeamView = false;
		GlobalButtonVars.isPersonalView = true;
	}

	// Create the panel
	public TeamPersButtonsPanel(){
		super("");
		
		// Set the text
		createTeamButton = new JButton("<html>Team <br/>Calendar</html>");
		createPersonalButton = new JButton("<html>Personal <br/>Calendar</html>");
		
		// Set the size and layout
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(350);
		createTeamButton.setPreferredSize(new Dimension(175, 40));
		createPersonalButton.setPreferredSize(new Dimension(175, 40));
		createTeamButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set the icons
		try {
			final Image imgT = ImageIO.read(getClass().getResource("team_calendar.png"));
		    createTeamButton.setIcon(new ImageIcon(imgT));
		    
		    final Image imgP = ImageIO.read(getClass().getResource("pers_calendar.png"));
		    createPersonalButton.setIcon(new ImageIcon(imgP));
		    
		} catch (IOException ex) {}
		
		// Set these buttons visible by default
		createTeamButton.setVisible(true);
		createPersonalButton.setVisible(true);
		
		
		// Add the buttons to the panel
		contentPanel.add(createPersonalButton);
		contentPanel.add(createTeamButton);
		contentPanel.setOpaque(false);

		this.add(contentPanel);
	}
	
	// Getters
	public JButton getCreateTeamButton() {
		return createTeamButton;
	}
	public JButton getCreatePersonalButton() {
		return createPersonalButton;
	}
}
