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
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JComboBox;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.controllers.GetEventController;

import java.awt.event.*;

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
	private final JTextField filterTextField;
	private final DefaultListModel<String> appliedFiltersListModel;
	private boolean isUpdated = false;
	private boolean initialized = false;
	
	// The list model that is being listened to it is used for comparison
	// with the changing list.
	public static DefaultListModel model = new DefaultListModel();
	
	// Create the sidebar panel
	public CalendarSidebar() {
		setLayout(new MigLayout("", "[grow][grow]", "[][100.00,grow,fill][grow]"));
		
		// List data listener on the contents of the local EventModel.
		// It listens for changes in the contents of the list and if they occur,
		// it proceeds to update the contents of the CalendarSidebar table of events.
		// ListDataListener listDataListener = new ListDataListener() {
		//	public void contentsChanged( ListDataEvent listDataEvent ) {
		//		populateTable( listDataEvent );
		//	}
		//	
		//	public void intervalAdded( ListDataEvent listDataEvent ) {
		//		populateTable( listDataEvent );
		//	}
		//	
		//	public void intervalRemoved( ListDataEvent listDataEvent ) {
		//		populateTable( listDataEvent );
		//	}
		//};
		
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
		eventTable.getTableHeader().setReorderingAllowed(false);

		// Create a table for commitments, nearly identical to the Events table
		// TODO: Uncomment this once Commitments are implemented
		/*
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
				"Commitments", "Due Date", "Category", "Description"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		commitmentTable.getColumnModel().getColumn(0).setResizable(false);
		commitmentTable.getColumnModel().getColumn(1).setResizable(false);
		commitmentTable.getColumnModel().getColumn(2).setResizable(false);
		commitmentTable.getColumnModel().getColumn(3).setResizable(false);
		commitScroll.setViewportView(commitmentTable);
		
		commitmentTable.getTableHeader().setReorderingAllowed(false);
		*/

		// Create a scroll pane to hold the Filter/Category manager
		final JScrollPane scrollPaneFilters = new JScrollPane();
		add(scrollPaneFilters, "cell 0 2 2 1,grow");
		
		// Create a panel within this scroll pane
		final JPanel filtersCatsPanel = new JPanel();
		filtersCatsPanel.setBorder(new TitledBorder(null, "Filtering Tools", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPaneFilters.setViewportView(filtersCatsPanel);
		filtersCatsPanel.setLayout(new MigLayout("", "[][150px:150px:150px,grow][70px:70px:70px,grow]", "[35px:35px:35px][35px:35px:35px][][][][][][][][20px:20px:20px][]"));
		
		JLabel lblList = new JLabel("Applied Filters:");
		filtersCatsPanel.add(lblList, "cell 0 0,alignx right");

		JScrollPane scrollPaneList = new JScrollPane();
		filtersCatsPanel.add(scrollPaneList, "flowx,cell 1 0 2 2,grow");

		// Button to unapply a Filter
		final JButton btnUnapply = new JButton("Unapply Filter");
		filtersCatsPanel.add(btnUnapply, "cell 1 2,growx,aligny top");
		
		JLabel lblCurrentCategories = new JLabel("Current Categories:");
		filtersCatsPanel.add(lblCurrentCategories, "cell 0 4,alignx trailing");

		// Create a list of current filters
		appliedFiltersListModel = new DefaultListModel<String>();
		final JList<String> listFilters = new JList<String>();
		scrollPaneList.setViewportView(listFilters);

		// Create a combo box to hold the current list of categories
		// TODO: Populate with info from database
		final JComboBox comboBoxCats = new JComboBox();
		filtersCatsPanel.add(comboBoxCats, "cell 1 4 2 1,growx");

		// Create a button to Apply a Filter
		final JButton btnApply = new JButton("Apply as Filter");
		filtersCatsPanel.add(btnApply, "cell 1 5,growx");
		// Apply listeners

		// Button to delete a category
		JButton btnDelete = new JButton("Delete Category");
		filtersCatsPanel.add(btnDelete, "cell 1 6,growx");

		// Label for New Category
		final JLabel lblCategory = new JLabel("New Category:");
		filtersCatsPanel.add(lblCategory, "cell 0 8,alignx right");

		// Create a text field to Add a filter
		filterTextField = new JTextField();
		filtersCatsPanel.add(filterTextField, "cell 1 8 2 1,growx");
		filterTextField.setColumns(10);

		// Button to Submit changes to a Category
		final JButton btnCatCreate = new JButton("Add Category");
		filtersCatsPanel.add(btnCatCreate, "cell 1 9,growx,aligny center");
		btnCatCreate.setEnabled(false);

		// If the category text field is not empty, allow the add button to be pressed
		// Uses a DocumentListener to check for changes, specifically to check if empty
		filterTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				enableAdd();
			}
			public void removeUpdate(DocumentEvent e) {
				enableAdd();
			}
			public void insertUpdate(DocumentEvent e) {
				enableAdd();
			}

			private void enableAdd(){
				if ((filterTextField.getText().equals(""))){
					btnCatCreate.setEnabled(false);
				}
				else btnCatCreate.setEnabled(true);
			}
		});


		// Create a listener to add a new category
		btnCatCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO: Update this call, as these radio buttons no longer exist
				/*
				final Category newCat = new Category(filterTextField.getText(), rdbtnTeam.isSelected());
				newCat.setId(CategoryModel.getInstance().getNextID());
				AddCategoryController.getInstance().addCategory(newCat);
				 */
			}
		});

		// Error text
		// TODO: Change and display if user tries
		// to add a duplicate category
		JLabel lblNewcaterror = new JLabel("");
		filtersCatsPanel.add(lblNewcaterror, "cell 1 10");

		// Clicking Apply will add a category to the applied filters
		// TODO: DATABASE FUNCTIONALITY
		/**
		 * 
		 * @author Team_
		 * @version 1.0
		 *
		 */
		class applyButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				appliedFiltersListModel.addElement((String) comboBoxCats.getSelectedItem());
			}
		}

		// Clicking Unapply will unapply the filter
		// TODO: DATABASE FUNCTIONALITY
		/**
		 * 
		 * @author Team_
		 * @version 1.0
		 *
		 */
		class unapplyButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				appliedFiltersListModel.removeElement(listFilters.getSelectedValue());
			}
		}
		btnApply.addActionListener(new applyButtonListener());
		btnUnapply.addActionListener(new unapplyButtonListener());
		populateTable();
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
		while(events.size() > eventTable.getModel().getRowCount())
		{
			((DefaultTableModel) eventTable.getModel()).insertRow(numRows, new Object[] {null, null, null, null});
			numRows++;
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
	
	/**
	 * Overrides the paintComponent method to retrieve the events on the first painting.
	 * 
	 * @param g	The component object to paint
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (!initialized) {
			try {
				GetEventController.getInstance().retrieveEvents();
				initialized = true;
			} catch (Exception e) {
			}
		}
		super.paintComponent(g);
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
