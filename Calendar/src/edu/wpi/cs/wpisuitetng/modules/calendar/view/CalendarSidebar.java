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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.DefaultListModel;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.swing.JComboBox;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller.AddCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller.GetCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller.UpdateCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.controllers.GetEventController;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$ CalendarSidebar creates the list of events as well
 *          as the Filter and Category managers next to the calendar view
 */
@SuppressWarnings("serial")
public class CalendarSidebar extends JPanel {

	private JTable eventTable;
	private JTable commitmentTable;
	private final JTextField filterTextField;
	private JComboBox<Category> comboBoxCats;
	private final DefaultListModel<String> appliedFiltersListModel;
	private boolean initialized = false;

	/**
	 * The static singleton instance of the CalendarSidebar.
	 */
	private static CalendarSidebar instance = null;

	/**
	 * @return the instance of the CalendarSidebar singleton.
	 */
	public static CalendarSidebar getInstance() {
		if (instance == null) {
			instance = new CalendarSidebar();
		}
		return instance;
	}

	/**
	 * Constructor method for the CalendarSidebar class. It prepares a split
	 * pane in the Calendar module that displays the table of events and the
	 * host of graphical components that enable the creation of categories and
	 * the filtering of events via category selection.
	 */
	private CalendarSidebar() {
		setLayout(new MigLayout("", "[grow][grow]",
				"[][100.00,grow,fill][grow]"));

		/*********************************************************************************/
		/**************************** Upcoming Events Table ******************************/
		/*********************************************************************************/

		// Create a scroll pane for the table of upcoming events.
		final JScrollPane eventScroll = new JScrollPane();
		add(eventScroll, "cell 0 1 2 1,grow");

		// Create a table of upcoming events that is initially empty.
		eventTable = new JTable();
		eventScroll.setViewportView(eventTable);
		eventTable.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, },

		// Provide titles for the columns in the table of upcoming events.
				new String[] { "Events", "Start Date", "End Date",
						"Description" }) {

			// Disable manual editing on the table of upcoming events.
			private final boolean[] columnEditables = new boolean[] { false,
					false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		});
		eventTable.getTableHeader().setReorderingAllowed(false);

		/*********************************************************************************/
		/***************** Filtering and Category Selection Components *******************/
		/*********************************************************************************/
		/**************************** STATUS: IN PROGRESS *******************************/
		/*********************************************************************************/

		/*
		 * A button for removing only the selected categories from the text
		 * window for applied filters in the CalendarSidebar. TODO Add a button
		 * for removing only the selected categories from the Applied Filters
		 * text window TODO Have Current categories drop down build from only
		 * not already a filter categories
		 */

		// Creation of a scroll pane to hold the Filter/Category manager.
		final JScrollPane scrollPaneFilters = new JScrollPane();
		add(scrollPaneFilters, "cell 0 2 2 1,grow");

		// Creation of a panel within the scroll pane that holds the
		// Filter/Category manager.
		final JPanel filtersCatsPanel = new JPanel();
		filtersCatsPanel.setBorder(new TitledBorder(null, "Filtering Tools",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPaneFilters.setViewportView(filtersCatsPanel);
		filtersCatsPanel
				.setLayout(new MigLayout("",
						"[][160px:160.00px:160px,grow][70px:70px:70px,grow]",
						"[35px:35px:35px][35px:35px:35px][][][][][][][][20px:20px:20px][]"));

		JLabel lblList = new JLabel("Applied Filters:");
		filtersCatsPanel.add(lblList, "cell 0 0,alignx right");

		JScrollPane scrollPaneList = new JScrollPane();
		filtersCatsPanel.add(scrollPaneList, "flowx,cell 1 0 2 2,grow");

		JButton btnUnapplyAll = new JButton("Unapply All");
		filtersCatsPanel.add(btnUnapplyAll, "flowx,cell 1 3,growx");

		// CFFLAG
		// Category Filter Elements-----------------------------------------
		// Button to unapply a Filter
		// TODO Add listener add functionality
		final JButton btnUnapply = new JButton("Unapply Selected");
		filtersCatsPanel.add(btnUnapply, "cell 1 2 1 2,growx,aligny top");

		JLabel lblCurrentCategories = new JLabel("Current Categories:");
		filtersCatsPanel.add(lblCurrentCategories, "cell 0 4,alignx trailing");

		// Category Add Message text
		// TODO: Change and display if user tries to add a duplicate category
		final JLabel lblNewcatmsg = new JLabel("");
		filtersCatsPanel.add(lblNewcatmsg, "cell 1 10");

		// Creation of a list of current filters.
		appliedFiltersListModel = new DefaultListModel<String>();
		final JList<String> listFilters = new JList<String>();
		scrollPaneList.setViewportView(listFilters);

		comboBoxCats = new JComboBox<Category>();
		filtersCatsPanel.add(comboBoxCats, "cell 1 4 2 1,growx");
		// adds categories to drop down.

		// Create a button to apply a filter.
		final JButton btnApply = new JButton("Apply as Filter");
		filtersCatsPanel.add(btnApply, "cell 1 5,growx");
		// Apply listeners

		// Button to delete a category
		JButton btnDelete = new JButton("Delete Category");
		filtersCatsPanel.add(btnDelete, "cell 1 6,growx");

		// Label for new category
		final JLabel lblCategory = new JLabel("New Category:");
		filtersCatsPanel.add(lblCategory, "cell 0 8,alignx right");

		// Creation of a text field for adding a filter.
		filterTextField = new JTextField();
		filtersCatsPanel.add(filterTextField, "cell 1 8 2 1,growx");
		filterTextField.setColumns(10);

		// Button for submitting changes to a Category.
		final JButton btnCatCreate = new JButton("Add Category");
		filtersCatsPanel.add(btnCatCreate, "cell 1 9,growx,aligny center");
		btnCatCreate.setEnabled(false);

		// -------------------------------------------------------

		// If the category text field is not empty, allow the add button to be
		// pressed
		// Uses a DocumentListener to check for changes, specifically to check
		// if empty
		filterTextField.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						enableAdd();
					}

					public void removeUpdate(DocumentEvent e) {
						enableAdd();
					}

					public void insertUpdate(DocumentEvent e) {
						enableAdd();
					}

					private void enableAdd() {
						if ((filterTextField.getText().equals(""))) {
							btnCatCreate.setEnabled(false);
						} else
							btnCatCreate.setEnabled(true);
					}
				});

		// CSFLAG
		// Category filter action listeners
		// ---------------------------------------------------
		// Initialize
		// Clicking Apply will add a category to the applied filters
		// TODO: DATABASE FUNCTIONALITY
		@SuppressWarnings("unused")
		class applyButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				appliedFiltersListModel.addElement((String) comboBoxCats
						.getSelectedItem());
			}
		}

		// Clicking the unapply button will unapply the filter
		// TODO: DATABASE FUNCTIONALITY
		@SuppressWarnings("unused")
		class unapplyButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				appliedFiltersListModel.removeElement(listFilters
						.getSelectedValue());
			}
		}

		// Create a listener to add a new category
		btnCatCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// TODO: Update this call, as these radio buttons no longer
				// exist
				/*
				 * final Category newCat = new
				 * Category(filterTextField.getText(), rdbtnTeam.isSelected());
				 * newCat.setId(CategoryModel.getInstance().getNextID());
				 * AddCategoryController.getInstance().addCategory(newCat);
				 */

				// Build Category
				Category newCategory = new Category(filterTextField.getText());

				// Set ID field
				newCategory.setId(CategoryModel.getInstance().getNextID());

				// does exist, not send y tell
				/*
				 * if (newCategory.equals((CategoryModel.getInstance()
				 * .getCategory(newCategory.getName() )))) {
				 */
				Category existingCategory = CategoryModel.getInstance()
						.getCategory(newCategory.getName());

				if ((existingCategory != null && !existingCategory.isDeleted())) {

					lblNewcatmsg.setText("Category Already Exists in System");
				}
				/*
				 * else if (existingCategory != null &&
				 * existingCategory.isDeleted()) {
				 * existingCategory.setDeleted(false);
				 * UpdateCategoryController.getInstance().updateCategory(
				 * existingCategory); // inform use of event creation
				 * lblNewcatmsg.setText("Category " + newCategory.getName() +
				 * "Was Created");
				 * 
				 * <<<<<<< HEAD // clear category name text box
				 * filterTextField.setText("");
				 * 
				 * }
				 */
				// category does not exist. build, add to model, and inform user
				/*
				 * else if ((CategoryModel.getInstance().getCategory(newCategory
				 * .getName())) == null) {
				 */

				else {
					// Update the category model with the new category
					// TODO Verify this is correct model functions
					AddCategoryController.getInstance()
							.addCategory(newCategory);

					// inform use of event creation
					lblNewcatmsg.setText("Category " + newCategory.getName()
							+ "Was Created");

					// Clear the text box for category name.
					filterTextField.setText("");
				}
			}
		});

		// Create a listener to delete selected category
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Set Selected Category to isDeleted = true
				Category toDeleteFrom = (Category) comboBoxCats
						.getSelectedItem();
				Category toDelete = CategoryModel.getInstance().getCategory(
						toDeleteFrom.getId());
				toDelete.setDeleted(true);

				// Update model
				UpdateCategoryController.getInstance().updateCategory(toDelete);
				// TODO check if removed category is active in filters
				// if so, remove it, and re-trigger paint

			}

		});

		// Create a listener to remove all categories
		btnUnapply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// TODO Set all of the categories in Applied Filters window
				// hasFilter = false
				// TODO remove all categories from Applied FIlters text window

				// TODO rerun all view paints with new list of categories in
				// Applied Filters Text Window

			}
		});

		// Create a listener to apply a category filter to the system's views
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// TODO add selected category to Applied FIlters text window
				// TODO set selected category hasFilter to true
				// TODO update model
				// TODO re-run all view paints with new list of categories in
				// Applied Filters Text Window

			}
		});
		// btnApply.addActionListener(new applyButtonListener());
		// btnUnapply.addActionListener(new unapplyButtonListener());

		/*********************************************************************************/
		/************************** Upcoming Commitments Table ***************************/
		/*********************************************************************************/

		// Create a table for commitments, nearly identical to the Events table
		// TODO: Un-comment this once Commitments are implemented
		/*
		 * final JScrollPane commitScroll = new JScrollPane(); add(commitScroll,
		 * "cell 0 2 2 1,grow");
		 * 
		 * commitmentTable = new JTable(); commitmentTable.setModel(new
		 * DefaultTableModel( new Object[][] { {null, null, null, null}, {null,
		 * null, null, null}, {null, null, null, null}, {null, null, null,
		 * null}, {null, null, null, null}, {null, null, null, null}, {null,
		 * null, null, null}, }, new String[] { "Commitments", "Due Date",
		 * "Category", "Description" } ) { boolean[] columnEditables = new
		 * boolean[] { false, false, false, false }; public boolean
		 * isCellEditable(int row, int column) { return columnEditables[column];
		 * } });
		 * commitmentTable.getColumnModel().getColumn(0).setResizable(false);
		 * commitmentTable.getColumnModel().getColumn(1).setResizable(false);
		 * commitmentTable.getColumnModel().getColumn(2).setResizable(false);
		 * commitmentTable.getColumnModel().getColumn(3).setResizable(false);
		 * commitScroll.setViewportView(commitmentTable);
		 * 
		 * commitmentTable.getTableHeader().setReorderingAllowed(false);
		 */

	}

	// ----------------------------------------------------------------------------------------

	// Populates the table of Events in the side bar

	// TODO: Expand to work with Commitments once required
	/*********************************************************************************/
	/**************************** Methods for Repainting *****************************/
	/*********************************************************************************/

	// TODO: Expand to work with Commitments once required
	/**
	 * Populate the category drop down menu that is contained within the
	 * CalendarSidebar split pane.
	 */
	public void populateCategoryDropDown() {
		comboBoxCats.removeAllItems();

		// Updates drop down list
		for (Category categoryIn : CategoryModel.getInstance()
				.getAllNondeletedCategories()) {
			comboBoxCats.addItem(categoryIn);
		}
	}

	/**
	 * Populates the table of Events in the side bar
	 */
	public void populateTable() {
		this.repaint();
		int numRows = eventTable.getModel().getRowCount();

		// Iterate through the table, emptying it
		for (int i = 0; i < numRows; i++) {
			for (int k = 0; k < 4; k++) {
				eventTable.setValueAt(null, i, k);
			}
		}
		// If the events list has changed, get the events.
		// Create a list of Events
		List<Event> events = null;

		// Get personal, team, or both views according to the state that was
		// selected by the user upon using the Calendar module.
		String userId = ConfigManager.getConfig().getUserName();
		// View only those events that are personal.
		if (GlobalButtonVars.getInstance().isStatePersonalView()) {
			events = EventModel.getInstance().getPersonalEvents(userId);
		}

		// View only those events that are team.
		else if (GlobalButtonVars.getInstance().isStateTeamView()) {
			events = EventModel.getInstance().getTeamEvents(userId);
		}

		// View all events that are classified as being both team and personal.
		else if (GlobalButtonVars.getInstance().isStateBothView()) {
			events = EventModel.getInstance().getUserEvents(userId);
		}

		// Inform the user via the console in the case that no state was
		// selected. This should never happen, but if it does, this is the
		// warning.
		if (!GlobalButtonVars.getInstance().isStateTeamView()
				&& !GlobalButtonVars.getInstance().isStatePersonalView()
				&& !GlobalButtonVars.getInstance().isStateBothView()) {
			System.out
					.println("No state (team or personal) has been selected!");
		}

		// checks to see if there are more events than rows, if so adds a new
		// row all in one go
		while (events.size() > eventTable.getModel().getRowCount()) {
			((DefaultTableModel) eventTable.getModel()).insertRow(numRows,
					new Object[] { null, null, null, null });
			numRows++;
		}

		// Populate the table with the appropriate list of events according to
		// the state that the user has selected (team, personal, or both).
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < 4; j++) {

				// Populate the first column of the table of upcoming events in
				// the
				// CalendarSidebar with the names of the upcoming events.
				if (j == 0) {
					try {
						eventTable.setValueAt(events.get(i).getName(), i, j);
					} 
					catch (IndexOutOfBoundsException e) {
					}
				}

				// Populate the second column of the table of upcoming events in
				// the
				// CalendarSidebar with the starting dates for each of the
				// upcoming
				// events.
				if (j == 1) {
					try {
						eventTable.setValueAt(events.get(i).getStartDate(), i,
								j);
					} catch (IndexOutOfBoundsException e) {
					}
				}

				// Populate the third column of the table of upcoming events in
				// the
				// CalendarSidebar with the ending dates for each of the
				// upcoming
				// events.
				if (j == 2) {
					try {
						eventTable.setValueAt(events.get(i).getEndDate(), i, j);
					} catch (IndexOutOfBoundsException e) {
						}
				}

				// Populate the fourth column of the table of upcoming events in
				// the
				// CalendarSidebar with the descriptions for each of the
				// upcoming
				// events.
				if (j == 3) {
					try {
						eventTable.setValueAt(events.get(i).getDescription(),
								i, j);
					} catch (IndexOutOfBoundsException e) {
					}
				}
			}
		}
	}

	/**
	 * Overrides the paintComponent method to retrieve the events and categories
	 * on the first painting.
	 * 
	 * @param g
	 *            The component object to paint
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (!initialized) {
			try {
				Timer timeEvent = new Timer(10000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							GetEventController.getInstance().retrieveEvents();
							System.out.println("Update Successful");
						}
						catch (Exception x) {
							System.out.println("Connection Error");
						}
					}
				});
				timeEvent.setRepeats(true);
				timeEvent.start();
				GetEventController.getInstance().retrieveEvents();
				GetCategoryController.getInstance().retrieveCategory();
				initialized = true;
			} catch (Exception e) {
				System.out.println("Caught Exception");
			}
		}
		super.paintComponent(g);
	}

	/*********************************************************************************/
	/***************************** Getters and Setters *******************************/
	/*********************************************************************************/

	/**
	 * @return The table of upcoming events within the CalendarSidebar.
	 */
	public JTable getEventTable() {
		return eventTable;
	}

	/**
	 * Method for setting the contents of the table of upcoming events in the
	 * CalendarSidebar.
	 * 
	 * @param eventTable
	 *            A table of events that is received as input.
	 */
	public void setEventTable(JTable eventTable) {
		this.eventTable = eventTable;
	}

	/**
	 * @return The table of upcoming commitments within the CalendarSidebar;
	 */
	public JTable getCommitmentTable() {
		return commitmentTable;
	}

	/**
	 * Method for setting the contents of the table of upcoming commitments.
	 * 
	 * @param commitmentTable
	 *            A table of commitments that is received as input.
	 */
	public void setCommitmentTable(JTable commitmentTable) {
		this.commitmentTable = commitmentTable;
	}
	
	

}
