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

import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.jdesktop.swingx.JXDatePicker;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EventEditor extends JPanel {
	private JTextField textField_1;

	/**
	 * Create the panel. Created using WindowBuilder
	 */
	public EventEditor() {
		// Set the layout
		setLayout(new MigLayout("", "[114px][85px:125.00:85px][85px:60.00:85px][60px:75.00px:60px][150.00]", "[50.00px][125px:125:150px][25.00][][][][][][][100px:100:100px][]"));
		
		// Set the Event label and text editor (single line)
		JLabel lblEventName = new JLabel("Event Name:");
		add(lblEventName, "cell 0 0,alignx trailing");
		
		textField_1 = new JTextField();
		add(textField_1, "cell 1 0 4 1,growx,aligny center");
		textField_1.setColumns(10);
		
		// Set the description label and text editor
		JLabel lblDescription = new JLabel("Description:");
		add(lblDescription, "cell 0 1,alignx trailing");
		
		// The text editor should scroll vertically but not horizontally; it should not resize
		JScrollPane scrollPaneDesc = new JScrollPane();
		scrollPaneDesc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPaneDesc, "cell 1 1 4 1,grow");
		// Put the text editor into the scroll pane
		JEditorPane editorPane = new JEditorPane();
		scrollPaneDesc.setViewportView(editorPane);
		
		// Create the date picker: three combo boxes, Month/Day/Year
		JLabel lblDate = new JLabel("Date:");
		add(lblDate, "cell 0 3,alignx trailing");
		
		// JTextField comboBoxMonth = new JTextField();
		// I think this is better for date picking
		// Easier for user, please don't kill me
		JXDatePicker comboBoxMonth = new JXDatePicker();
		add(comboBoxMonth, "cell 1 3,growx, span 2");
		
		// Set the Start and End time fields
		JLabel lblTime = new JLabel("Start Time:");
		add(lblTime, "cell 0 4,alignx trailing");
		
		JComboBox comboBoxStartHour = new JComboBox();
		add(comboBoxStartHour, "cell 1 4,growx");
		
		JComboBox comboBoxStartMinutes = new JComboBox();
		add(comboBoxStartMinutes, "cell 2 4,growx");
		
		JComboBox comboBoxStartAMPM = new JComboBox();
		add(comboBoxStartAMPM, "cell 3 4,growx");
		
		JLabel lblEndTime = new JLabel("End Time:");
		add(lblEndTime, "cell 0 5,alignx trailing");
		
		JComboBox comboBoxEndHour = new JComboBox();
		add(comboBoxEndHour, "cell 1 5,growx");
		
		JComboBox comboBoxEndMinutes = new JComboBox();
		add(comboBoxEndMinutes, "cell 2 5,growx");
		
		JComboBox comboBoxEndAMPM = new JComboBox();
		add(comboBoxEndAMPM, "cell 3 5,growx");
		
		// Set the Category picker; will be populated by current categories
		JLabel lblCategory = new JLabel("Category:");
		add(lblCategory, "cell 0 7,alignx trailing");
		
		JComboBox comboBoxCategory = new JComboBox();
		add(comboBoxCategory, "cell 1 7 2 1,growx");
		
		// Label and create the Participants text editor
		// TODO: This is a bit unintuitive; we should come up with a
		// better way to do this
		JLabel lblParticipants = new JLabel("Participants:");
		add(lblParticipants, "cell 0 9,alignx trailing");
		
		JScrollPane scrollPaneParticipants = new JScrollPane();
		scrollPaneParticipants.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPaneParticipants, "cell 1 9 2 1,grow");
		
		JEditorPane editorPane_1 = new JEditorPane();
		scrollPaneParticipants.setViewportView(editorPane_1);
		
		JButton btnSubmit = new JButton("Submit");
		add(btnSubmit, "cell 1 10,growx");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		add(btnCancel, "cell 2 10");

	}
	// Set listeners
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
