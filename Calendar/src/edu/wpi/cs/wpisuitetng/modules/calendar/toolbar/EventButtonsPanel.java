package edu.wpi.cs.wpisuitetng.modules.calendar.toolbar;
/**
 *  * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 */

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.calendar.*;


public class EventButtonsPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	final JButton createCancelButton = new JButton("<html>Cancel<br />Changes</html>");
	JButton createEventButton;
	JButton createCommitButton;
	private ImageIcon editImg = null;
	private ImageIcon saveImg = null;

	public void disableCreateEventButton() {
		createEventButton.setEnabled(false);
	}
	
	public void enableCreateEventButton() {
		createEventButton.setEnabled(true);
	}
	
	public void disableCreateCommitButton() {
		createEventButton.setEnabled(false);
	}
	
	public void enableCreateCommitButton() {
		createEventButton.setEnabled(true);
	}

	public EventButtonsPanel(){
		super("");
		
		createEventButton = new JButton("<html>Create <br/>Event</html>");
		createCommitButton = new JButton("<html>Create <br/>Commitment</html>");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(350);
		
		this.createEventButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		try {
			Image imgE = ImageIO.read(getClass().getResource("new_event.png"));
		    this.createEventButton.setIcon(new ImageIcon(imgE));
		    
		    Image imgC = ImageIO.read(getClass().getResource("new_commit.png"));
		    this.createCommitButton.setIcon(new ImageIcon(imgC));
		    
		} catch (IOException ex) {}
		
		createEventButton.setVisible(true);
		createCommitButton.setVisible(true);
		
		// the action listener for the Create Event Button
		createEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Event Created!");
			}
		});		
				
		//action listener for the Create Commit Button
		createCommitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					//ViewEventController.getInstance().createIteration();
				}
			//}
		});
		
		contentPanel.add(createCommitButton);
		contentPanel.add(createEventButton);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
	}
	
	/**
	 * Method getCreateButton.
	
	 * @return JButton */
	public JButton getCreateEventButton() {
		return createEventButton;
	}

	/**
	 * Method getCreateIterationButton.
	
	 * @return JButton */
	public JButton getCreateCommitButton() {
		return createCommitButton;
	}
}
