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
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Creates the event viewer
 */
public class EventViewer extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel. Created using WindowBuilder
	 */
	public EventViewer() {
		// Set the layout
		setLayout(new MigLayout("", "[114px][275.00]", "[50.00px][125px:125:150px][25.00][][][][]"
				+ "[][][100px:100:100px]"));
		
		// Set the Event label and text editor (single line)
		final JLabel lblEventName = new JLabel("Event Name:");
		add(lblEventName, "cell 0 0,alignx trailing");
		
		final JLabel lblInputName = new JLabel("Input Name");
		add(lblInputName, "cell 1 0");
		
		// Set the description label and text editor
		final JLabel lblDescription = new JLabel("Description:");
		add(lblDescription, "cell 0 1,alignx trailing");
		
		final JLabel lblInputDesc = new JLabel("Input Desc");
		add(lblInputDesc, "cell 1 1");
		
		// Create the date picker: three combo boxes, Month/Day/Year
		final JLabel lblDate = new JLabel("Date:");
		add(lblDate, "cell 0 3,alignx trailing");
		
		final JLabel lblInputDate = new JLabel("Input Date");
		add(lblInputDate, "cell 1 3");
		
		// Set the Start and End time fields
		final JLabel lblTime = new JLabel("Start Time:");
		add(lblTime, "cell 0 4,alignx trailing");
		
		final JLabel lblInputStart = new JLabel("Input Start");
		add(lblInputStart, "cell 1 4");
		
		final JLabel lblEndTime = new JLabel("End Time:");
		add(lblEndTime, "cell 0 5,alignx trailing");
		
		final JLabel lblInputEnd = new JLabel("Input End");
		add(lblInputEnd, "cell 1 5");
		
		// Set the Category picker; will be populated by current categories
		final JLabel lblCategory = new JLabel("Category:");
		add(lblCategory, "cell 0 7,alignx trailing");
		
		final JLabel lblInputCategory = new JLabel("Input Category");
		add(lblInputCategory, "cell 1 7");
		
		// Label and create the Participants text editor
		// TODO: This is a bit un-intuitive; we should come up with a
		// better way to do this
		final JLabel lblParticipants = new JLabel("Participants:");
		add(lblParticipants, "cell 0 9,alignx trailing");
		
		final JLabel lblInputParticipants = new JLabel("Input Participants");
		add(lblInputParticipants, "cell 1 9");

	}
	// Set listeners
	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
