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
 *    
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JComboBox;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller.AddCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.controllers.GetEventController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * CalendarSidebar creates the list of events as well as
 * the Filter and Category managers next to the calendar view
 */
@SuppressWarnings("serial")
public class CalendarSidebar extends JPanel {

	private JTable eventTable;
	private JTable commitmentTable;
	private final JTextField textField;
	private final JTextField filterTextField;
	private boolean isUpdated = false;
	private final ButtonGroup radioGroup;

	// Create the sidebar panel
	@SuppressWarnings("unchecked")
	public CalendarSidebar() {
		setLayout(new MigLayout("", "[grow][grow]", "[][100.00,grow,center][100.00,grow][grow]"));

		// Create a button to refresh the list of events
		// TODO: Incorporate this functionality into Event/Commitment Submit buttons
		// and the Team/Personal View buttons
		final JButton btnRefreshEvents = new JButton("Refresh Events");
		btnRefreshEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				populateTable();
			}
		});
		add(btnRefreshEvents, "cell 0 0 2097051 1,growx");

		// Create a scroll pane for the Events table
		final JScrollPane eventScroll = new JScrollPane();
		add(eventScroll, "cell 0 1 2 1,grow");
		// Create a table, initially empty, of upcoming Events
		eventTable = new JTable();
		eventScroll.setViewportView(eventTable);
		eventTable.setModel(new DefaultTableModel(
				new Object[][] {
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
				},
				// Column titles
				new String[] {
						"Events", "Start Date", "End Date", "Description"
				}
				){
			// Do not allow the table to be manually editable
			private final boolean[] columnEditables = new boolean[] {
					false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// Create a table for commitments, nearly identical to the Events table
		final JScrollPane commitScroll = new JScrollPane();
		add(commitScroll, "cell 0 2 2 1,grow");

		commitmentTable = new JTable();
		commitmentTable.setModel(new DefaultTableModel(
				new Object[][] {
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
				},
				new String[] {
						"Commitment", "Date", "Category ", "Description"
				}
				) {
			private final boolean[] columnEditables = new boolean[] {
					false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		commitScroll.setViewportView(commitmentTable);

		// Create a scroll pane to hold the Filter and Category managers
		final JScrollPane scrollPaneManagers = new JScrollPane();
		add(scrollPaneManagers, "cell 0 3 2 1,grow");
		// Create a panel within this scroll pane
		final JPanel filtersCatsPanel = new JPanel();
		scrollPaneManagers.setViewportView(filtersCatsPanel);
		filtersCatsPanel.setLayout(new MigLayout("", "[grow]", "[][]"));

		// Create a panel within filterCatsPanel to hold the Filter manager
		final JPanel panelFilter = new JPanel();
		filtersCatsPanel.add(panelFilter, "cell 0 0,grow");
		panelFilter.setBorder(new TitledBorder(
				null, 
				"Filters", 
				TitledBorder.LEADING,
				TitledBorder.TOP, 
				null, 
				null));
		panelFilter.setLayout(new MigLayout("", "[grow][grow]", "[85.00px][][]"));

		// Create a list of current filters
		// TODO: This is a predefined list until we implement this feature
		final JList<Object> list = new JList<Object>();
		panelFilter.add(list, "cell 0 0,grow");
		list.setModel(new AbstractListModel<Object>() {
			private final String[] values = new String[] {"Team", "Personal", "Things"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});

		// Create a button to Apply a Filter
		final JButton btnApply = new JButton("Apply");
		panelFilter.add(btnApply, "flowx,cell 0 1,alignx left");
		// Create a text field to Add a filter
		filterTextField = new JTextField();
		panelFilter.add(filterTextField, "cell 0 2,growx");
		filterTextField.setColumns(10);
		// Button to Add a new Filter
		final JButton btnNewFilter = new JButton("New Filter");
		panelFilter.add(btnNewFilter, "cell 1 2,alignx left");
		// Button to Delete a Filter
		final JButton btnDelete = new JButton("Delete");
		panelFilter.add(btnDelete, "cell 0 1,alignx left");

		// Create a panel for the Category manager
		final JPanel panelCatCreate = new JPanel();
		filtersCatsPanel.add(panelCatCreate, "cell 0 1,grow");
		panelCatCreate.setBorder(new TitledBorder(
				null,
				"Categories",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panelCatCreate.setLayout(new MigLayout("", "[80.00,grow][100px,grow][]", "[][][][]"));
		// Label for the category list
		final JLabel lblCurrentCategories = new JLabel("Categories:");
		panelCatCreate.add(lblCurrentCategories, "cell 0 0,alignx right");

		// ComboBox to select existing Categories
		final JComboBox<Object> comboBox = new JComboBox<Object>();
		panelCatCreate.add(comboBox, "cell 1 0,growx");
		// Button to Delete a category
		final JButton btnDeleteCat = new JButton("Delete");
		panelCatCreate.add(btnDeleteCat, "cell 1 1,alignx left");
		// Label for New Category
		final JLabel lblCategory = new JLabel("New Category:");
		panelCatCreate.add(lblCategory, "cell 0 2,alignx trailing");
		// Text Field to create a new category or edit an existing one
		textField = new JTextField();
		panelCatCreate.add(textField, "cell 1 2,growx");
		textField.setColumns(10);
		// Radio Buttons for Team or Personal calendar choice
		final JRadioButton rdbtnTeam = new JRadioButton("Team");
		panelCatCreate.add(rdbtnTeam, "flowx,cell 1 3");
		final JRadioButton rdbtnPersonal = new JRadioButton("Personal", true);
		panelCatCreate.add(rdbtnPersonal, "cell 1 3");
		// Add these buttons to a radio group so only one can be selected
		radioGroup = new ButtonGroup();
		radioGroup.add(rdbtnTeam);
		radioGroup.add(rdbtnPersonal);
		// Button to Submit changes to a Category
		final JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final Category newCat = new Category(textField.getText(), rdbtnTeam.isSelected());
				newCat.setId(CategoryModel.getInstance().getNextID());
				AddCategoryController.getInstance().addCategory(newCat);
			}
		});
		panelCatCreate.add(btnSubmit, "cell 1 3,growx");

	}
	// Populates the table of Events in the side bar
	// TODO: Expand to work with Commitments once required
	private void populateTable() {
		this.repaint();
		int numRows = eventTable.getModel().getRowCount();

		// Iterate through the table, emptying it
		for(int i=0; i < numRows; i++) {
			for(int k=0; k < 4; k++) {
				eventTable.setValueAt(null, i, k);
			}
		}
		// If the events list has changed, get the events
		if(!isUpdated){
			isUpdated=true;
			GetEventController.getInstance().retrieveEvents();
		}
		// Create a list of Events
		List<Event> events = null;

		// Get personal, team, or both views according to the state that was selected
		// by the user upon using the Calendar module.

		// View only those events that are personal.
		if (GlobalButtonVars.isStatePersonalView()) {
			String userId = ConfigManager.getConfig().getUserName();
			events = EventModel.getInstance().getPersonalEvents(userId);
		}

		// View only those events that are team.
		if (GlobalButtonVars.isStateTeamView()) {
			String userId = ConfigManager.getConfig().getUserName();
			events = EventModel.getInstance().getTeamEvents(userId);
		}

		// View all events that are classified as being both team and personal.
		if (GlobalButtonVars.isStateBothView()) {
			String userId = ConfigManager.getConfig().getUserName();
			events = EventModel.getInstance().getAllEvents();
		}

		// Inform the user via the console in the case that no state was selected.
		// This should never happen, but if it does, this is the warning.
		if (!GlobalButtonVars.isStateTeamView() 
				&& !GlobalButtonVars.isStatePersonalView()
				&& !GlobalButtonVars.isStateBothView())
		{
			System.out.println("No state (team or personal) has been selected!");
		}

		//checks to see if there are more events than rows, if so adds a new row
		if(events.size()>numRows)
		{
			((DefaultTableModel) eventTable.getModel()).insertRow(numRows, new Object[] {null, null, null, null});
		}

		// Populate the table with the appropriate list of events according to the state
		// that the user has selected (team, personal, or both).
		for(int i=0;i < numRows;i++){
			for(int j=0;j < 4;j++){
				if(j == 0){
					try{
						eventTable.setValueAt(events.get(i).getName(), i, j);
					}
					catch (IndexOutOfBoundsException e) {}
					// eventTable.setValueAt(events.get(i).getName(), i, j);	

				}
				if (j == 1) {
					try {
						eventTable.setValueAt(events.get(i).getStartDate(), i, j);
					}
					catch (IndexOutOfBoundsException e) {}
					//eventTable.setValueAt(events.get(i).getStartDate(), i, j);	
				}
				// NEW JUST ADDED
				if(j == 2){
					try{
						eventTable.setValueAt(events.get(i).getEndDate(), i, j);
					}
					catch(IndexOutOfBoundsException e){
						//eventTable.setValueAt(events.get(i).getEndDate(), i, j);
					}
				}

				/*
				final List<Event> events = EventModel.getInstance().getAllEvents();
				for(int i = 0; i < events.size(); i++) {
					for(int j = 0; j < 4; j++) {
						if(j == 0) {
							try {
								eventTable.setValueAt(events.get(i).getName(), i, j);
							}
							catch(IndexOutOfBoundsException e) {}
							eventTable.setValueAt(events.get(i).getName(), i, j);

						}
						if(j == 1) {
							try {
								eventTable.setValueAt(events.get(i).getStartDate(), i, j);
							}
							catch(IndexOutOfBoundsException e) {}
							eventTable.setValueAt(events.get(i).getStartDate(), i, j);
						}
						if(j == 2) {
							try {
								eventTable.setValueAt(events.get(i).getEndDate(), i, j);
							}
							catch(IndexOutOfBoundsException e) {}
							eventTable.setValueAt(events.get(i).getEndDate(), i, j);
						}
						if(j == 3) {
							try {
								eventTable.setValueAt(events.get(i).getDescription(), i, j);
							}
							catch(IndexOutOfBoundsException e) {}
							eventTable.setValueAt(events.get(i).getDescription(), i, j);
						}
					}
					//eventTable.setValueAt(events.get(i).getEndDate(), i, j);	
				}
				 */
				if (j == 3) {
					try {
						eventTable.setValueAt(events.get(i).getDescription(), i, j);
					}
					catch(IndexOutOfBoundsException e) {}
					//eventTable.setValueAt(events.get(i).getDescription(), i, j);	
				}
			}
		}
	}
	// Getters and setters
	public JTable getEventTable() {
		return eventTable;
	}

	public void setEventTable(JTable eventTable) {
		this.eventTable = eventTable;
	}

	public JTable getCommitmentTable() {
		return commitmentTable;
	}

	public void setCommitmentTable(JTable commitmentTable) {
		this.commitmentTable = commitmentTable;
	}

}
