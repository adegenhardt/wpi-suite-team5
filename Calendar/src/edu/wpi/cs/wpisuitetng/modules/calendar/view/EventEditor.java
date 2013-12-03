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

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.controllers.AddEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.controllers.GetEventController;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Creates the event editor tab
 */
public class EventEditor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JTextField eventName;
	private final JEditorPane descriptionPane;
	private final JXDatePicker comboBoxStartMonth;
	private final JXDatePicker comboBoxEndMonth;
	private final JComboBox<String> comboBoxStartHour;
	private final JComboBox<String> comboBoxStartMinutes;
	private final JComboBox<String> comboBoxStartAMPM;
	private final JComboBox<String> comboBoxEndHour;
	private final JComboBox<String> comboBoxEndMinutes;
	private final JComboBox<String> comboBoxEndAMPM;

	private final JLabel lblDatemsg;
	private final JLabel lblDescmsg;
	private final JLabel lblEventnamemsg;
	private final JLabel lblTimemsg;

	/**
	 * Create the panel. Created using WindowBuilder
	 */
	public EventEditor() {
		// Set the layout
		setLayout(new MigLayout("", "[114px][50px:125.00:50px][50px:60.00:50px][60px:75.00px:60px][40px:40px:40px][150px:150.00:150px][]", "[50.00px][125px:125:150px][25.00][][][][][][][][][100px:100:100px][]"));

		// Set the Event label and text editor (single line)
		final JLabel lblEventName = new JLabel("Event Name:");
		add(lblEventName, "cell 0 0,alignx trailing");

		eventName = new JTextField();
		add(eventName, "cell 1 0 5 1,growx,aligny center");
		eventName.setColumns(10);

		lblEventnamemsg = new JLabel("");
		lblEventnamemsg.setForeground(new Color(255, 0, 0));
		add(lblEventnamemsg, "cell 6 0");

		// Set the description label and text editor
		final JLabel lblDescription = new JLabel("Description:");
		add(lblDescription, "cell 0 1,alignx trailing");

		// The text editor should scroll vertically but not horizontally; it should not resize
		final JScrollPane scrollPaneDesc = new JScrollPane();
		scrollPaneDesc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPaneDesc, "cell 1 1 5 1,grow");
		// Put the text editor into the scroll pane
		descriptionPane = new JEditorPane();
		scrollPaneDesc.setViewportView(descriptionPane);

		lblDescmsg = new JLabel("");
		lblDescmsg.setForeground(new Color(255, 0, 0));
		add(lblDescmsg, "cell 6 1");

		// Create the date picker
		JLabel lblDate = new JLabel("Date:");
		add(lblDate, "cell 0 3,alignx trailing");

		// Date picker
		comboBoxStartMonth = new JXDatePicker();
		// Setting the date format to something more intuitive
		comboBoxStartMonth.setFormats(new SimpleDateFormat("MMM/dd/yyyy"));
		add(comboBoxStartMonth, "cell 1 3,growx, span 2");

		lblDatemsg = new JLabel("Ex. Oct/02/1993");
		lblDatemsg.setForeground(new Color(0, 0, 0));
		add(lblDatemsg, "cell 3 3 2 1,alignx center");

		comboBoxStartMonth.getEditor().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblDatemsg.setText("Ex. Oct/02/1993");
				lblDatemsg.setForeground(new Color(0, 0, 0));
			}
		});

		comboBoxEndMonth = new JXDatePicker();
		add(comboBoxEndMonth, "cell 1 4");

		// Set the Start and End time fields
		final JLabel lblTime = new JLabel("Start Time:");
		add(lblTime, "cell 0 6,alignx trailing");

		comboBoxStartHour = new JComboBox<String>();
		comboBoxStartHour.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3",
				"4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		add(comboBoxStartHour, "cell 1 6,growx");

		comboBoxStartMinutes = new JComboBox<String>();
		comboBoxStartMinutes.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "30"}));
		add(comboBoxStartMinutes, "cell 2 6,growx");

		comboBoxStartAMPM = new JComboBox<String>();
		comboBoxStartAMPM.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		add(comboBoxStartAMPM, "cell 3 6,growx");

		lblTimemsg = new JLabel("");
		lblTimemsg.setForeground(new Color(255, 0, 0));
		add(lblTimemsg, "cell 4 6");

		final JLabel lblEndTime = new JLabel("End Time:");
		add(lblEndTime, "cell 0 7,alignx trailing");

		comboBoxEndHour = new JComboBox<String>();
		comboBoxEndHour.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3",
				"4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		add(comboBoxEndHour, "cell 1 7,growx");

		comboBoxEndMinutes = new JComboBox<String>();
		comboBoxEndMinutes.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "30"}));
		add(comboBoxEndMinutes, "cell 2 7,growx");

		comboBoxEndAMPM = new JComboBox<String>();
		comboBoxEndAMPM.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		add(comboBoxEndAMPM, "cell 3 7,growx");

		// Set the Category picker; will be populated by current categories
		final JLabel lblCategory = new JLabel("Category:");
		add(lblCategory, "cell 0 9,alignx trailing");

		final JComboBox<String> comboBoxCategory = new JComboBox<String>();
		comboBoxCategory.setModel(new DefaultComboBoxModel<String>(new String[] {"Important",
				"Not Important", "Even Less Important", "Party!"}));
		add(comboBoxCategory, "cell 1 9 2 1,growx");

		// Label and create the Participants text editor
		// TODO: This is a bit unintuitive; we should come up with a
		// better way to do this
		final JLabel lblParticipants = new JLabel("Event Information:");
		add(lblParticipants, "cell 0 11,alignx trailing");

		final JScrollPane scrollPaneParticipants = new JScrollPane();
		scrollPaneParticipants.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPaneParticipants, "cell 1 11 4 1,grow");

		final JEditorPane editorPane_1 = new JEditorPane();
		scrollPaneParticipants.setViewportView(editorPane_1);

		final JButton btnSubmit = new JButton("Submit");
		
		class SubmitButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				// Check for validity of input
				if (!checkValid()) {
					return;
				}

				int startHalfHours = parseTime((String) comboBoxStartHour.getSelectedItem(),
						(String) comboBoxStartMinutes.getSelectedItem(),
						(String) comboBoxStartAMPM.getSelectedItem());

				// TODO: Replace code with something using new data model
				Date start = (Date) comboBoxStartMonth.getDate().clone();
				DateInfo startDate = new DateInfo(start.getYear(), start.getMonth(),
						start.getDay(), startHalfHours);

				int endHalfHours = parseTime((String) comboBoxEndHour.getSelectedItem(),
						(String) comboBoxEndMinutes.getSelectedItem(),
						(String) comboBoxEndAMPM.getSelectedItem());

				// TODO: Replace code with something using new data model
				Date end = (Date) comboBoxEndMonth.getDate().clone();
				DateInfo endDate = new DateInfo(end.getYear(), end.getMonth(),
						end.getDay(), endHalfHours);

				final Event makeEvent = new Event(eventName.getText(),
						descriptionPane.getText(), startDate, endDate, false,
						new Category("Place", 5));

				//GetEventController.getInstance().retrieveEvent();

				makeEvent.setId(EventModel.getInstance().getNextID());

				AddEventController.getInstance().addEvent(makeEvent);

				//GetEventController.getInstance().retrieveEvent();

				List<Event> events = EventModel.getInstance().getAllEvents();
				editorPane_1.setText(Integer.toString(events.size()));

				for(int i=0; i < events.size(); i++) {
					String poop = editorPane_1.getText();
					editorPane_1.setText(poop+events.get(i).getName());
				}

				
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
		btnSubmit.addActionListener(new SubmitButtonListener());
		add(btnSubmit, "cell 1 12 2 1,growx");
		// Will updat the appropriate view
		correctUpdateForView();
		
	}

	// Checks for valid input and displays messages next to
	// Fields that may need correcting, right now only checks 
	// For blank fields,I'll add some more sophisticated checks
	private boolean checkValid() {
		if (eventName.getText().trim().length() == 0) {
			lblEventnamemsg.setText("Enter event name");
			return false;
		}
		else {
			lblEventnamemsg.setText("");
		}
		if (descriptionPane.getText().trim().length() == 0) {
			lblDescmsg.setText("Enter a description");
			return false;
		}
		else {
			lblDescmsg.setText("");
		}
		if (comboBoxStartMonth.getDate() == null) {
			lblDatemsg.setText("Invalid Date");
			lblDatemsg.setForeground(new Color(255, 0, 0));
			return false;
		}
		else {
			lblDatemsg.setText("");
		}
		if (parseTime((String) comboBoxStartHour.getSelectedItem(), 
				(String) comboBoxStartMinutes.getSelectedItem(), (String) comboBoxStartAMPM.getSelectedItem()) 
				>= parseTime((String) comboBoxEndHour.getSelectedItem(), (String) comboBoxEndMinutes.getSelectedItem(),
						(String) comboBoxEndAMPM.getSelectedItem())) {
			lblTimemsg.setText("Start date can't be after end date");
			return false;
		}
		else {
			lblTimemsg.setText("");
		}
		return true;
	}
	
	// Determines the state of the calendar view (team or personal)
	// using the global boolean values contained in the
	// GlobalButtonVars class, and runs the correct update method.
	private void correctUpdateForView() {
		if ( GlobalButtonVars.isPersonalView &&
				!GlobalButtonVars.isTeamView) {
			
			// Changing the contents of local data models for
			// event and category objects.
			EventModel.getInstance().toPersonalEventModel();
			CategoryModel.getInstance().toPersonalCategoryModel();
			
		}
		
		if ( GlobalButtonVars.isTeamView && 
				!GlobalButtonVars.isPersonalView) {
			
			final String userId;
			
			// Acquire the username from the configuration class within
			// the Janeway module and store it in a variable.
			ConfigManager.getInstance();
			userId = ConfigManager.getConfig().getUserName();
			
			// Changing the contents of local data models for
			// event and category objects.
			EventModel.getInstance().toTeamEventModel( userId );
			CategoryModel.getInstance().toTeamCategoryModel( userId );
			
		}
		
		else {
			System.out.println("Both team and personal calendar views ");
			System.out.println("are either both selected or not selected!");
		}
		
	}

	// Parse the String times into ints so they can be compared
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
