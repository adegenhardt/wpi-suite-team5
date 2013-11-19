/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

//Creates a panel in the toolbar with buttons 
//to switch between Team and Personal calendars
public class TeamPersButtonsPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	final JButton createCancelButton = new JButton("<html>Cancel<br />Changes</html>");
	// Create the buttons
	JButton createTeamButton;
	JButton createPersonalButton;
	final private ImageIcon editImg = null;
	final private ImageIcon saveImg = null;

	// Methods to enable and disable these buttons
	public void disableCreateTeamButton() {
		createTeamButton.setEnabled(false);
	}
	
	public void enableCreateTeamButton() {
		createTeamButton.setEnabled(true);
	}
	
	public void disableCreatePersonalButton() {
		createTeamButton.setEnabled(false);
	}
	
	public void enableCreatePersonalButton() {
		createTeamButton.setEnabled(true);
	}

	// Create the panel
	public TeamPersButtonsPanel(){
		super("");
		
		// Set the text
		createTeamButton = new JButton("<html>Team <br/>Calendar</html>");
		createPersonalButton = new JButton("<html>Personal <br/>Calendar</html>");
		// Set the size and layout
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(350);
		
		this.createTeamButton.setHorizontalAlignment(SwingConstants.CENTER);
		// Set the icons
		// TODO: New icons
		try {
			final Image imgE = ImageIO.read(getClass().getResource("new_event.png"));
		    this.createTeamButton.setIcon(new ImageIcon(imgE));
		    
		    final Image imgC = ImageIO.read(getClass().getResource("new_commit.png"));
		    this.createPersonalButton.setIcon(new ImageIcon(imgC));
		    
		} catch (IOException ex) {}
		
		createTeamButton.setVisible(true);
		createPersonalButton.setVisible(true);
		
		// the action listener for the Team Calendar Button
		createTeamButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});	
				
		// action listener for the Personal Calendar Button
		createPersonalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					// ViewEventController.getInstance().createIteration();
				}
			//}
		});
		// Add the buttons to the panel
		contentPanel.add(createPersonalButton);
		contentPanel.add(createTeamButton);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
	}
	
	/**
	 * Method getCreateButton.
	
	 * @return JButton */
	public JButton getCreateTeamButton() {
		return createTeamButton;
	}

	/**
	 * Method getCreateIterationButton.
	
	 * @return JButton */
	public JButton getCreatePersonalButton() {
		return createPersonalButton;
	}
}
