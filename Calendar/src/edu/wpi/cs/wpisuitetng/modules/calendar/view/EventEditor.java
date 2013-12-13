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
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.controllers.AddEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;

import java.util.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JRadioButton;
import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Component;
import java.awt.BorderLayout;


/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Creates the event editor tab
 */
@SuppressWarnings("serial")
public class EventEditor extends JPanel {

	private final JTextField eventName;
	private final JEditorPane descriptionPane;
	private final JXDatePicker datePickerStartMonth;
	private final JXDatePicker datePickerEndMonth;
	private final JComboBox<String> comboBoxStartHour;
	private final JComboBox<String> comboBoxStartMinutes;
	private final JComboBox<String> comboBoxStartAMPM;
	private final JComboBox<String> comboBoxEndHour;
	private final JComboBox<String> comboBoxEndMinutes;
	private final JComboBox<String> comboBoxEndAMPM;

	private final JLabel lblDatemsg;
	private final JLabel lblDescmsg;
	private final JLabel lblEventnamemsg;
	private JLabel lblDuplicateEventmsg;
	private final JLabel labelEDate;
	private final JLabel lblDateEndMsg;
	private final JLabel lblTimemsg;
	private final JRadioButton rdbtnPersonal;
	private final JRadioButton rdbtnTeam;
	
	private final JTabbedPane parent;
	private final JPanel thisInstance;
	private final JTextField textFieldPartic;
	private final JButton btnAddPartic;
	private final JScrollPane scrollPanePartics;
	private final JList<String> listPartics;
	private final JButton btnRemovePartic;
	private final JLabel lblParterror;
				
	private final DefaultListModel<String> particsListModel;
	
	/**
	 * Create the panel. Created using WindowBuilder
	 * @param _parent the parent pane
	 */
	public EventEditor(JTabbedPane _parent) {
		
		// Set the parent tabbed pane for this closable tab
		// Set itself to be called later in the tabbed pane
		parent = _parent; 
		thisInstance = this;
		setLayout(new BorderLayout(0, 0));
		
		// Define a panel within a scrollpane
		// Hacky fix to add a scrollbar to this tab
		final JPanel eventPanel = new JPanel();
		JScrollPane mainScroll = new JScrollPane(); 
		mainScroll.add(eventPanel);
		mainScroll.setViewportView(eventPanel);
		thisInstance.add(mainScroll);
		
		// Set the layout
		eventPanel.setLayout(new MigLayout(
				"", 
				"[114px][50px:125.00:50px,grow][50px:60.00:50px][60px:75.00px:60px][][150px:150.00:150px,grow][]",
				"[50.00px][125px:125:150px][][][][][][][][40.00][][125px:125px:125px,grow][]"));

		// Set the Event label and text editor (single line)
		final JLabel lblEventName = new JLabel("Event Name:");
		eventPanel.add(lblEventName, "cell 0 0,alignx trailing");
		
		eventName = new JTextField();
		eventPanel.add(eventName, "cell 1 0 5 1,growx,aligny center");
		eventName.setColumns(10);

		lblEventnamemsg = new JLabel("");
		lblEventnamemsg.setForeground(Color.red);
		eventPanel.add(lblEventnamemsg, "cell 6 0");

		// Set the description label and text editor
		final JLabel lblDescription = new JLabel("Description:");
		eventPanel.add(lblDescription, "cell 0 1,alignx trailing");

		// The text editor should scroll vertically but not horizontally; it should not resize
		final JScrollPane scrollPaneDesc = new JScrollPane();
		scrollPaneDesc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		eventPanel.add(scrollPaneDesc, "cell 1 1 5 1,grow");
		// Put the text editor into the scroll pane
		descriptionPane = new JEditorPane();
		scrollPaneDesc.setViewportView(descriptionPane);

		lblDescmsg = new JLabel("");
		lblDescmsg.setForeground(Color.red);
		eventPanel.add(lblDescmsg, "cell 6 1");
		
		// Set the Start and End time fields
		final JLabel lblTime = new JLabel("Start Time:");
		eventPanel.add(lblTime, "cell 0 2,alignx trailing");
		
		// Create the ComboBoxes for time selection
		comboBoxStartHour = new JComboBox<String>();
		comboBoxStartHour.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3",
				"4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		eventPanel.add(comboBoxStartHour, "cell 1 2,growx");
		
		comboBoxStartMinutes = new JComboBox<String>();
		comboBoxStartMinutes.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "30"}));
		eventPanel.add(comboBoxStartMinutes, "cell 2 2,growx");
		
		comboBoxStartAMPM = new JComboBox<String>();
		comboBoxStartAMPM.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		eventPanel.add(comboBoxStartAMPM, "cell 3 2,growx");
		
		lblTimemsg = new JLabel("");

		eventPanel.add(lblTimemsg, "cell 4 2, growx, span 3");


		// Create the date picker
		final JLabel lblSDate = new JLabel("Start Date:");
		eventPanel.add(lblSDate, "cell 0 3,alignx trailing");

		// Date pickers
		datePickerStartMonth = new JXDatePicker();
		datePickerEndMonth = new JXDatePicker();
		// Setting the date format to something more intuitive
		datePickerStartMonth.setFormats(new SimpleDateFormat("MMM/dd/yyyy"));
		datePickerEndMonth.setFormats(new SimpleDateFormat("MMM/dd/yyyy"));
		eventPanel.add(datePickerStartMonth, "cell 1 3 3 1,growx");
		eventPanel.add(datePickerEndMonth, "cell 1 6 3 1,growx");
		
		// Set the default dates to Today
		datePickerStartMonth.setDate(new Date());
		datePickerEndMonth.setDate(new Date());

		datePickerStartMonth.getEditor().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblDatemsg.setText("Ex. Oct/02/1993");
				lblDatemsg.setForeground(Color.black);
			}
		});
		
		datePickerEndMonth.getEditor().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblDatemsg.setText("Ex. Oct/02/1993");
				lblDatemsg.setForeground(Color.black);
			}
		});
				
		// Set the example label, will change to show errors
		lblDatemsg = new JLabel("Ex. Oct/02/1993");
		lblDatemsg.setForeground(Color.black);
		eventPanel.add(lblDatemsg, "cell 4 3,alignx center");
		
		final JLabel lblEndTime = new JLabel("End Time:");
		eventPanel.add(lblEndTime, "cell 0 5,alignx trailing");
		
		lblDateEndMsg = new JLabel("");
		lblDateEndMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDateEndMsg.setForeground(Color.BLACK);
		eventPanel.add(lblDateEndMsg, "cell 4 6,alignx center");
		
		// Set the duplicate label, will appear if a duplicate has been created.
		lblDuplicateEventmsg = new JLabel("");
		lblDuplicateEventmsg.setForeground(Color.black);
		eventPanel.add(lblDuplicateEventmsg, "cell 3 12,alignx center");
		
		// Create the combo boxes and labels for time selection
		comboBoxEndHour = new JComboBox<String>();
		comboBoxEndHour.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3",
				"4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		eventPanel.add(comboBoxEndHour, "cell 1 5,growx");
		
		comboBoxEndMinutes = new JComboBox<String>();
		comboBoxEndMinutes.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "30"}));
		eventPanel.add(comboBoxEndMinutes, "cell 2 5,growx");
				
		comboBoxEndAMPM = new JComboBox<String>();
		comboBoxEndAMPM.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		eventPanel.add(comboBoxEndAMPM, "cell 3 5,growx");
				
		labelEDate = new JLabel("End Date:");
		eventPanel.add(labelEDate, "cell 0 6,alignx trailing");
		
		
		// Set the Category picker; will be populated by current categories
		final JLabel lblCategory = new JLabel("Category:");
		eventPanel.add(lblCategory, "cell 0 8,alignx trailing");
		
		// TODO: Populate this with actual categories instead of this predefined list
		final JComboBox<String> comboBoxCategory = new JComboBox<String>();
		comboBoxCategory.setModel(new DefaultComboBoxModel<String>(new String[] {"Important",
				"Not Important", "Even Less Important", "Party!"}));
		eventPanel.add(comboBoxCategory, "cell 1 8,growx");
		
				
		// Create Team/Personal calendar options and group them
		rdbtnPersonal = new JRadioButton("Personal");
		rdbtnPersonal.setSelected(!GlobalButtonVars.isStateTeamView());
		eventPanel.add(rdbtnPersonal, "cell 1 9");
		
		rdbtnTeam = new JRadioButton("Team");
		rdbtnTeam.setSelected(GlobalButtonVars.isStateTeamView());
		eventPanel.add(rdbtnTeam, "cell 3 9");
		
		//Group the radio buttons.
        final ButtonGroup calGroup = new ButtonGroup();
        calGroup.add(rdbtnPersonal);
        calGroup.add(rdbtnTeam);

		// Label the Participants text editor
		final JLabel lblParticipants = new JLabel("Participants:");
		eventPanel.add(lblParticipants, "cell 0 10,alignx trailing");

		final JButton btnSubmit = new JButton("Submit");
		
		// Create a listener for the Submit button
		/**
		 * 
		 * @author Team_
		 * @version 1.0
		 *
		 */
		class SubmitButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				// Check for validity of input
				if (!checkValid()) {
					return;
				}
				// Parse the start time
				final int startHalfHours = parseTime((String) comboBoxStartHour.getSelectedItem(),
						(String) comboBoxStartMinutes.getSelectedItem(),
						(String) comboBoxStartAMPM.getSelectedItem());

				// TODO: Replace code with something using new data model
				final Date start = (Date) datePickerStartMonth.getDate().clone();
				final DateInfo startDate = new DateInfo(start.getYear() + 1900, start.getMonth(),
						start.getDate() - 1, startHalfHours);

				final int endHalfHours = parseTime((String) comboBoxEndHour.getSelectedItem(),
						(String) comboBoxEndMinutes.getSelectedItem(),
						(String) comboBoxEndAMPM.getSelectedItem());

				// TODO: Replace code with something using new data model
				final Date end = (Date) datePickerEndMonth.getDate().clone();
				final DateInfo endDate = new DateInfo(end.getYear() + 1900, end.getMonth(),
						end.getDate() - 1, endHalfHours);
				
				// Check whether this is a team or personal event
				boolean isTeamEvent;
				if (rdbtnPersonal.isSelected()) {
					System.out.println("Personal event");
					isTeamEvent = false;
				}
				else {
					System.out.println("Team Event");
					isTeamEvent = true;
				}
				
				// Retrieve the user name from Janeway's configuration storage
				// and place it in the userId variable.
				final String userId = ConfigManager.getConfig().getUserName();

				// Create an event
				final Event makeEvent = new Event(eventName.getText(),
						descriptionPane.getText(), startDate, endDate, isTeamEvent,
						new Category("Place", 5));
				makeEvent.setId(EventModel.getInstance().getNextID());
				List<String> participants = new ArrayList<String>();
				for(int i=0; i < listPartics.getModel().getSize(); i++) {
					participants.add(listPartics.getModel().getElementAt(i));
				}
				if (participants.size() > 0) {
					makeEvent.setUserIds(participants);
				}
				
				// If the user creates an event similar in all fields but unique ID,
				// then do not add it to the local model or the server.
				if (EventModel.getInstance().similarEventFound(userId, makeEvent)) {
					lblDuplicateEventmsg = new JLabel("Duplicate event present: will not create.");
					lblDuplicateEventmsg.setForeground(Color.red);
					eventPanel.add(lblDuplicateEventmsg, "cell 3 12,alignx center");
					System.out.println("Duplicate event present: will not create event.");
					return;
				}

				else {
					AddEventController.getInstance().addEvent(makeEvent);
				}

				parent.remove(thisInstance);

				/*
				final List<Event> events = EventModel.getInstance().getAllEvents();
				editorPane_1.setText(Integer.toString(events.size()));


				for(int i=0; i < events.size(); i++) {
					String deprecatedDefecation = editorPane_1.getText();
					editorPane_1.setText(deprecatedDefecation + events.get(i).getName());
				}
				*/
				
				/*matt's test for system user name and entity absoluteId
				 * ignore/remove it if there is a merge conflict battle for other changes
				System.out.println("vv");
				editorPane_1.setText(ConfigManager.getConfig().getUserName());
				
		
				 //Calendar cal = Calendar.getInstance();
				// editorPane_1.setText(dateFormat.format(cal.getTime()));
				 String test1 ="no1";
				 String test2 ="no2";
				 String test3 ="no3";
				 
				 editorPane_1.setText("point1");
				// editorPane_1.setText(makeEvent.getCreatorId() ); works
				// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				// editorPane_1.setText(dateFormat.format(makeEvent.getAbsoluteId().getTime())); works
				 if(makeEvent.isActiveUserEvent()){
					 test2="yes2"; 
				 }
				 if(makeEvent.isSameAbsoluteId(makeEvent) ) {
					 test3 = "yes3";
				 }
				 if(makeEvent.isUserEvent("bundleOfFish")){
					 test1 ="yes1";
				 }
				 
				 editorPane_1.setText(test1+"-"+test2+"-"+test3+"-"+makeEvent.getAbsoluteIdStringFormat());
				 */
				

				//Database Interaction Attempt
				//Attempts to replace DB CalendarData with Updated Calendar Data evntCal
				// TODO: Replace code with something using new data model
				//				UpdateCalendarDataController.getInstance().updateCalendarData(eventCal);
				//				GetCalendarDataController.getInstance().retrieveCalendarData();
				//				
				//editorPane_1.setText("PointA");
				// TODO: Replace code with something using new data model
				//				CalendarData retrievedEventData = CalendarDataModel.getInstance().getCalendarData().get(0);
				//editorPane_1.setText("PointB");
				// TODO: Replace code with something using new data model
				//				List<Event> eventList = retrievedEventData.getEventsPerView("year", new DateInfo(makeEvent.getStartYear(), -1, -1, -1));
				//editorPane_1.setText("PointC");
				// TODO: Replace code with something using new data model
				//				Event retrievedEventDB = eventList.get(0);
				//				editorPane_1.setText(retrievedEventDB.getName()+" "+retrievedEventDB.getDescription()+" "+retrievedEventDB.getStartDate().toString());

				//Demo Methods (Lacks Database Functionality)
				//editorPane_1.setText(makeEvent.getEventName()+" "+makeEvent.getEventDescr()+" "+makeEvent.getStartDate().toString());			
			}
		}
		
		// Clicking Add will add a participant to the list below
		/**
		 * 
		 * @author Team_
		 * @version 1.0
		 *
		 */
		class ParticAddButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				particsListModel.addElement(textFieldPartic.getText());
				textFieldPartic.setText(null);
			}
		}
				
		// Clicking Remove will remove the selected participant from the list
		/**
		 * 
		 * @author Team_
		 * @version 1.0
		 *
		 */
		class ParticRemoveButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				particsListModel.removeElement(listPartics.getSelectedValue());
			}
		}
		
		// Text field to enter a participant to add
		textFieldPartic = new JTextField();
		eventPanel.add(textFieldPartic, "cell 1 10 3 1,growx");
		textFieldPartic.setColumns(10);
		
		// Button to add a participant to the list, disabled until text is present
		btnAddPartic = new JButton("Add");
		eventPanel.add(btnAddPartic, "cell 4 10,growx");
		btnAddPartic.setEnabled(false);
		
		// Error label for adding participants
		lblParterror = new JLabel("");
		eventPanel.add(lblParterror, "cell 5 10");
		
		// Scroll pane for participants list
		scrollPanePartics = new JScrollPane();
		eventPanel.add(scrollPanePartics, "cell 1 11 3 1,grow");
		
		// List of participants to add to the event
		particsListModel = new DefaultListModel<String>();
		listPartics = new JList<String>(particsListModel);
		scrollPanePartics.setViewportView(listPartics);
		
		// Button to remove participants
		btnRemovePartic = new JButton("Remove");
		eventPanel.add(btnRemovePartic, "cell 4 11,growx,aligny top");

		// Button to submit changes
		eventPanel.add(btnSubmit, "cell 1 12 2 1,growx");
		
		// Button listeners
		btnSubmit.addActionListener(new SubmitButtonListener());
		btnAddPartic.addActionListener(new ParticAddButtonListener());
		btnRemovePartic.addActionListener(new ParticRemoveButtonListener());
		
		
		// If the participants text field is not empty, allow the Add button to be pressed
		// Uses a DocumentListener to check for changes, specifically to check if empty
		textFieldPartic.getDocument().addDocumentListener(new DocumentListener() {
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
				if ((textFieldPartic.getText().equals(""))){
					btnAddPartic.setEnabled(false);
				}
				else btnAddPartic.setEnabled(true);
			}
		});
		

	}

	// Checks for valid input and displays messages next to
	// TODO: Fields that may need correcting, right now only checks 
	// For blank fields,I'll add some more sophisticated checks
	private boolean checkValid() {
		boolean isValid = true;
		if (eventName.getText().trim().length() == 0) {
			lblEventnamemsg.setText("Enter event name");
			isValid = false;
		}
		else {
			lblEventnamemsg.setText("");
		}
		if (descriptionPane.getText().trim().length() == 0) {
			lblDescmsg.setText("Enter a description");
			isValid = false; 
		}
		else {
			lblDescmsg.setText("");
		}
		// This is a rather risky check, it should be ok for this
		// Dates will not be equal if they aren't the same down to
		// Milliseconds, but should be no problem since it will always
		// Generate dates from the beginning of the selected day
		try {
			if (datePickerStartMonth.getDate().getTime() == datePickerEndMonth
					.getDate().getTime()) {
				if (parseTime((String) comboBoxStartHour.getSelectedItem(),
						(String) comboBoxStartMinutes.getSelectedItem(),
						(String) comboBoxStartAMPM.getSelectedItem()) >= parseTime(
						(String) comboBoxEndHour.getSelectedItem(),
						(String) comboBoxEndMinutes.getSelectedItem(),
						(String) comboBoxEndAMPM.getSelectedItem())) {
					lblTimemsg.setForeground(Color.red);
					lblTimemsg
							.setText("Start time can't be after or equal to end time");
					isValid = false;
				} else {
					lblTimemsg.setText("");
				}
			} else if (datePickerStartMonth.getDate().getTime() > datePickerEndMonth
					.getDate().getTime()) {
				lblTimemsg.setForeground(Color.red);
				lblTimemsg.setText("Start day can't be after end day");
				isValid = false;
			} else {
				lblTimemsg.setText("");
			}
		} catch (NullPointerException e) {
			if (datePickerStartMonth.getDate() == null) {
				lblDatemsg.setForeground(Color.red);
				lblDatemsg.setText("Invalid Date");
				isValid = false; 
			}
			if (datePickerEndMonth.getDate() == null) {
				lblDateEndMsg.setForeground(Color.red);
				lblDateEndMsg.setText("Invalid Date");
				isValid = false; 
			}
		}
		return isValid;
	}

	// Parse the String times into integers so they can be compared
	// Used to check if the start time and end times are valid
	private int parseTime(String hour, String minutes, String AMPM) {
		int time;
		if (AMPM.equals("AM")) {
			time= 0;
		}
		else {
			time = 24; 
		}
		if (Integer.parseInt(hour) == 12) {}
		else {
			time += 2 * Integer.parseInt(hour);
		}
		time += Integer.parseInt(minutes) / 30;
		return time;
	}
}
