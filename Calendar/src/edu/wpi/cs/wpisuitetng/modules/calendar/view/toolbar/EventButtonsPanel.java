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
package edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar;

import java.awt.Dimension;
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

import javax.swing.JRadioButton;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Create the event buttons
 */
public class EventButtonsPanel extends ToolbarGroupView{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	final JButton createCancelButton = new JButton("<html>Cancel<br />Changes</html>");
	JButton createEventButton;
	JButton createCommitButton;

	/**
	 * Method disableCreateEventButton.
	 */
	public void disableCreateEventButton() {
		createEventButton.setEnabled(false);
	}
	
	/**
	 * Method enableCreateEventButton.
	 */
	public void enableCreateEventButton() {
		createEventButton.setEnabled(true);
	}
	
	/**
	 * Method disableCreateCommitButton.
	 */
	public void disableCreateCommitButton() {
		createEventButton.setEnabled(false);
	}
	
	/**
	 * Method enableCreateCommitButton.
	 */
	public void enableCreateCommitButton() {
		createEventButton.setEnabled(true);
	}

	/**
	 * Constructor for EventButtonsPanel.
	 */
	public EventButtonsPanel(){
		super("");
		
		createEventButton = new JButton("<html>Create <br/>Event</html>");
		createCommitButton = new JButton("<html>Create <br/>Commitment</html>");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(350);
		
		createCommitButton.setPreferredSize(new Dimension(175, 40));
		createEventButton.setPreferredSize(new Dimension(175, 40));
		
		createEventButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		try {
			final Image imgE = ImageIO.read(getClass().getResource("new_event.png"));
		    createEventButton.setIcon(new ImageIcon(imgE));
		    
		    final Image imgC = ImageIO.read(getClass().getResource("new_commit.png"));
		    createCommitButton.setIcon(new ImageIcon(imgC));
		    
		} catch (IOException ex) {}
		
		createEventButton.setVisible(true);
		createCommitButton.setVisible(true);
		
		// the action listener for the Create Event Button
		createEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
				
		// action listener for the Create Commit Button
		createCommitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
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
