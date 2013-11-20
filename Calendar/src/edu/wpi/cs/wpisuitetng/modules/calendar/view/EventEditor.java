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
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;

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
	

	/**
	 * Create the panel. Created using WindowBuilder
	 */
	public EventEditor() {
		// Set the layout
		setLayout(new MigLayout("", "[114px][50px:125.00:50px][50px:60.00:50px][60px:75.00px:60px]"
				+ "[40px:40px:40px][150px:150.00:150px]", "[50.00px][125px:125:150px][25.00][][][]"
						+ "[][][][100px:100:100px][]"));

		// Set the Event label and text editor (single line)
		final JLabel lblEventName = new JLabel("Event Name:");
		add(lblEventName, "cell 0 0,alignx trailing");

		eventName = new JTextField();
		add(eventName, "cell 1 0 5 1,growx,aligny center");
		eventName.setColumns(10);

		// Set the description label and text editor
		final JLabel lblDescription = new JLabel("Description:");
		add(lblDescription, "cell 0 1,alignx trailing");

		// The text editor should scroll vertically but not horizontally; it should not resize
		final JScrollPane scrollPaneDesc = new JScrollPane();
		scrollPaneDesc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPaneDesc, "cell 1 1 5 1,grow");
		// Put the text editor into the scroll pane
		final JEditorPane descriptionPane = new JEditorPane();
		scrollPaneDesc.setViewportView(descriptionPane);

		// Create the date picker
		final JLabel lblDate = new JLabel("Date:");
		add(lblDate, "cell 0 3,alignx trailing");

		// Date picker
		final JXDatePicker comboBoxMonth = new JXDatePicker();
		add(comboBoxMonth, "cell 1 3,growx, span 2");

		// Set the Start and End time fields
		final JLabel lblTime = new JLabel("Start Time:");
		add(lblTime, "cell 0 4,alignx trailing");

		final JComboBox<String> comboBoxStartHour = new JComboBox<String>();
		comboBoxStartHour.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3",
				"4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		add(comboBoxStartHour, "cell 1 4,growx");

		final JComboBox<String> comboBoxStartMinutes = new JComboBox<String>();
		comboBoxStartMinutes.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "30"}));
		add(comboBoxStartMinutes, "cell 2 4,growx");

		final JComboBox<String> comboBoxStartAMPM = new JComboBox<String>();
		comboBoxStartAMPM.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		add(comboBoxStartAMPM, "cell 3 4,growx");

		final JLabel lblEndTime = new JLabel("End Time:");
		add(lblEndTime, "cell 0 5,alignx trailing");

		final JComboBox<String> comboBoxEndHour = new JComboBox<String>();
		comboBoxEndHour.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3",
				"4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		add(comboBoxEndHour, "cell 1 5,growx");

		final JComboBox<String> comboBoxEndMinutes = new JComboBox<String>();
		comboBoxEndMinutes.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "30"}));
		add(comboBoxEndMinutes, "cell 2 5,growx");

		final JComboBox<String> comboBoxEndAMPM = new JComboBox<String>();
		comboBoxEndAMPM.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		add(comboBoxEndAMPM, "cell 3 5,growx");

		// Set the Category picker; will be populated by current categories
		final JLabel lblCategory = new JLabel("Category:");
		add(lblCategory, "cell 0 7,alignx trailing");

		final JComboBox<String> comboBoxCategory = new JComboBox<String>();
		comboBoxCategory.setModel(new DefaultComboBoxModel<String>(new String[] {"Important",
				"Not Important", "Even Less Important", "Party!"}));
		add(comboBoxCategory, "cell 1 7 2 1,growx");

		// Label and create the Participants text editor
		// TODO: This is a bit unintuitive; we should come up with a
		// better way to do this
		final JLabel lblParticipants = new JLabel("Event Information:");
		add(lblParticipants, "cell 0 9,alignx trailing");

		final JScrollPane scrollPaneParticipants = new JScrollPane();
		scrollPaneParticipants.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPaneParticipants, "cell 1 9 4 1,grow");

		final JEditorPane editorPane_1 = new JEditorPane();
		scrollPaneParticipants.setViewportView(editorPane_1);

		final JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Add code to add event
				// Get the arguments out of their respective fields
				final CalendarData eventCal = new CalendarData(
					ConfigManager.getConfig().getProjectName(), "Personal", 10);
				Date startDate = new Date();
				startDate = (Date) comboBoxMonth.getDate().clone();
				Date endDate = new Date();
				endDate = (Date) comboBoxMonth.getDate().clone();

				final String startHour = (String) comboBoxStartHour.getSelectedItem();
				final String startMinutes = (String) comboBoxStartMinutes.getSelectedItem();

				startDate.setHours(Integer.parseInt(startHour));
				startDate.setMinutes(Integer.parseInt(startMinutes));
				//System.out.println(startDate);

				final String endHour = (String) comboBoxEndHour.getSelectedItem(); 
				final String endMinutes = (String) comboBoxEndMinutes.getSelectedItem();

				endDate.setHours(Integer.parseInt(endHour));
				endDate.setMinutes(Integer.parseInt(endMinutes));

				final Event makeEvent = new Event(50, eventName.getText(),
						descriptionPane.getText(), startDate, endDate);
				eventCal.addEvent(makeEvent);
				//GetCalendarDataController.getInstance().retrieveCalendarData();
				//int size = CalendarDataModel.getInstance().getCalendarData().size();
				//List<CalendarData> retCal = CalendarDataModel.getInstance().getCalendarData();
				//System.out.println(size);
				//String s= ""+size;
				//editorPane_1.setText(s);
				//boolean calendarFound = false;
				//UpdateCalendarDataController.getInstance().updateCalendarData(eventCal);
				//GetCalendarDataController.getInstance().retrieveCalendarData();
				/*for (int i = 0; i < size; i++) {
					System.out.println("got into first loop");
					editorPane_1.setText("into for loop");
					//editorPane_1.setText(eventCal.getType()+eventCal.getName()+eventCal.getId()+((CalendarData) ((CalendarDataModel) retCal).getElementAt(i)).getType()+((Component) ((CalendarDataModel) retCal).getElementAt(i)).getName() + ((AWTEvent) ((CalendarDataModel) retCal).getElementAt(i)).getID());
					if ((eventCal.getType().equals((
							(CalendarData) 
							((CalendarDataModel) retCal).getElementAt(i)).getType())) &&
							(eventCal.getName().equals(((CalendarData) 
									((CalendarDataModel) retCal).getElementAt(i)).getName())) &&
									(eventCal.getId() == 
									(((CalendarData) 
											((CalendarDataModel) retCal).getElementAt(i)).getId())))
					{
						calendarFound = true;
						UpdateCalendarDataController.getInstance().updateCalendarData(eventCal);
						editorPane_1.setText("true");
						System.out.println("You successfully updated your calendar...");
					}
					
				}
				if (calendarFound==false){
					AddCalendarDataController.getInstance().addCalendarData(eventCal);
					System.out.println("You successfully added a new calendar...");
				}
				System.out.println("got here again");
				GetCalendarDataController.getInstance().retrieveCalendarData();
				List<CalendarData> fromDbCal = CalendarDataModel.getInstance().getCalendarData();

				for (int j = 0; j < size; j++) {
					System.out.println("got into second loop");
					if (eventCal.getType().equals((
							(CalendarData) 
							((CalendarDataModel) retCal).getElementAt(j)).getType()) &&
							eventCal.getName().equals(((CalendarData) 
									((CalendarDataModel) retCal).getElementAt(j)).getName()) &&
									eventCal.getId() == 
									(((CalendarData) 
											((CalendarDataModel) retCal).getElementAt(j)).getId()))
					{
						System.out.println("Here are the contents of what you added");
					}
					System.out.println("Nothing found matching what you input to the db... :(");
				}

				//editorPane_1.setText(eventName.getText()+descriptionPane.getText());
				System.out.println(eventName.getText());
				System.out.println(descriptionPane.getText());
				System.out.println(endDate); */
				//do these interact with the database as required
				
				//attepts to replace DB CalendarData with Updated Calendar Data evntCal
				UpdateCalendarDataController.getInstance().updateCalendarData(eventCal);
				//db40 storage confic
				//activation and update depth
				//atempts 
				//searc code for activation
				//send queery to support commiters about issue
				//activation depth
				//num way from original object
				//methods to allow to either dbo reference for java
				//method in wpi suite for this
				//load next layers
				//one says load everything
				//wpi suite exposed it 
				//db40 home, db4o reference for java
				//explict activation container.activate how deep to activate
				//or confic activation globaly or 
				//could have for each day has a pointer
				//give year then pointer to all days
				//do year continas days by date string 
				GetCalendarDataController.getInstance().retrieveCalendarData();
				editorPane_1.setText("PointB");//program got to here in test
				CalendarData retrievedEventData = CalendarDataModel.getInstance().getCalendarData().get(0);
				editorPane_1.setText("PointC");
				List<Event> eventList = retrievedEventData.getEventsPerView("year", new DateInfo(makeEvent.getStartYear(), -1, -1, -1));
				editorPane_1.setText("PointA");
				Event retrievedEventDB = eventList.get(0);
				//editorPane_1.setText(makeEvent.getEventName()+" "+makeEvent.getEventDescr()+" "+makeEvent.getStartDate().toString());
				editorPane_1.setText(retrievedEventDB.getEventName()+" "+retrievedEventDB.getEventDescr()+" "+retrievedEventDB.getStartDate().toString());
				//getInstance and getCalendar are not returning anything correctly we cannot recieve things from database
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		add(btnSubmit, "cell 1 10 2 1,growx");

		final JButton btnCancel = new JButton("Make Calendar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final CalendarData eventCal = new CalendarData(
						ConfigManager.getConfig().getProjectName(), "Personal", 10);
				AddCalendarDataController.getInstance().addCalendarData(eventCal);
			}
		});
		add(btnCancel, "cell 3 10 2 1,growx");

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
