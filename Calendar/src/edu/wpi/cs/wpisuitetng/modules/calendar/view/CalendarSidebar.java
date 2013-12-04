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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JComboBox;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.globalButtonVars.GlobalButtonVars;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.EventModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.controllers.GetEventController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * CalendarSidebar creates the list of events next to the calendar views
 */
@SuppressWarnings("serial")
public class CalendarSidebar extends JPanel {
	/**
	 * 
	 */
	private JTable eventTable;
	private JTable commitmentTable;
	private JTextField textField;
	private JTextField filterTextField;
	private boolean isUpdated = false;
	
	/**
	 * Create the panel.
	 */
	public CalendarSidebar() {
		
		setLayout(new MigLayout("", "[grow][grow]", "[][100.00,grow,center][100.00,grow][grow]"));
		
		JButton btnRefreshEvents = new JButton("Refresh Events");
		btnRefreshEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				populateTable();
			}
		});
		add(btnRefreshEvents, "cell 0 0 2097051 1,growx");

		final JScrollPane eventScroll = new JScrollPane();
		add(eventScroll, "cell 0 1 2 1,grow");
		
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
			new String[] {
				"Events", "Start Date", "End Date", "Description"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		eventTable.getColumnModel().getColumn(0).setResizable(false);
		eventTable.getColumnModel().getColumn(1).setResizable(false);
		eventTable.getColumnModel().getColumn(2).setResizable(false);
		eventTable.getColumnModel().getColumn(3).setResizable(false);
		
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
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 3 2 1,grow");
		
		JPanel filtersCatsPanel = new JPanel();
		scrollPane.setViewportView(filtersCatsPanel);
		filtersCatsPanel.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JPanel panelFilter = new JPanel();
		filtersCatsPanel.add(panelFilter, "cell 0 0,grow");
		panelFilter.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFilter.setLayout(new MigLayout("", "[grow][grow]", "[85.00px][][]"));
		
		JList list = new JList();
		panelFilter.add(list, "cell 0 0,grow");
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Team", "Personal", "Things"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JButton btnApply = new JButton("Apply");
		panelFilter.add(btnApply, "flowx,cell 0 1,alignx left");
		
		filterTextField = new JTextField();
		panelFilter.add(filterTextField, "cell 0 2,growx");
		filterTextField.setColumns(10);
		
		JButton btnNewFilter = new JButton("New Filter");
		panelFilter.add(btnNewFilter, "cell 1 2,alignx left");
		
		JButton btnDelete = new JButton("Delete");
		panelFilter.add(btnDelete, "cell 0 1,alignx left");
		
		JPanel panelCatCreate = new JPanel();
		filtersCatsPanel.add(panelCatCreate, "cell 0 1,grow");
		panelCatCreate.setBorder(new TitledBorder(null, "Categories", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCatCreate.setLayout(new MigLayout("", "[80.00,grow][100px,grow][]", "[][][][]"));
		
		JLabel lblCurrentCategories = new JLabel("Categories:");
		panelCatCreate.add(lblCurrentCategories, "cell 0 0,alignx right");
		
		JComboBox comboBox = new JComboBox();
		panelCatCreate.add(comboBox, "cell 1 0,growx");
		
		JButton btnDelete_1 = new JButton("Delete");
		panelCatCreate.add(btnDelete_1, "cell 1 1,alignx left");
		
		JLabel lblCategory = new JLabel("New Category:");
		panelCatCreate.add(lblCategory, "cell 0 2,alignx trailing");
		
		textField = new JTextField();
		panelCatCreate.add(textField, "cell 1 2,growx");
		textField.setColumns(10);
		
		JRadioButton rdbtnTeam = new JRadioButton("Team");
		panelCatCreate.add(rdbtnTeam, "flowx,cell 1 3");
		
		JRadioButton rdbtnPersonal = new JRadioButton("Personal");
		panelCatCreate.add(rdbtnPersonal, "cell 1 3");
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		panelCatCreate.add(btnSubmit, "cell 1 3,growx");

	}
	
	private void populateTable() {
		if(!isUpdated)
		{
			isUpdated=true;
			GetEventController.getInstance().retrieveEvents();
		}
		
		List<Event> events;
		
		if (GlobalButtonVars.isPersonalView) {
			String userId = ConfigManager.getConfig().getUserName();
			events = EventModel.getInstance().getPersonalEvents(userId);
		}
		else {
			String userId = ConfigManager.getConfig().getUserName();
			events = EventModel.getInstance().getTeamEvents(userId);
		}
		for(int i=0;i < events.size();i++)
		{
			for(int j=0;j<4;j++)
			{
				if(j==0)
				{
					try
					{
						eventTable.setValueAt(events.get(i).getName(), i, j);
					}
					catch(IndexOutOfBoundsException e)
					{	
					}
					eventTable.setValueAt(events.get(i).getName(), i, j);	
					
				}
				if(j==1)
				{	
					try
					{
						eventTable.setValueAt(events.get(i).getStartDate(), i, j);
					}
					catch(IndexOutOfBoundsException e)
					{
					}
					eventTable.setValueAt(events.get(i).getStartDate(), i, j);	
				}
				if(j==2)
				{
					try
					{
						eventTable.setValueAt(events.get(i).getEndDate(), i, j);
					}
					catch(IndexOutOfBoundsException e)
					{}
					eventTable.setValueAt(events.get(i).getEndDate(), i, j);	
				}
				if(j==3)
				{
					try
					{
						eventTable.setValueAt(events.get(i).getDescription(), i, j);
					}
					catch(IndexOutOfBoundsException e)
					{
					}
					eventTable.setValueAt(events.get(i).getDescription(), i, j);	
				}
			}
		}
	}

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
