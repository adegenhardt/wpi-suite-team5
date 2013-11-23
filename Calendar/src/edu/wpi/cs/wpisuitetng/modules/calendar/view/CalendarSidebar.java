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

	/**
	 * Create the panel.
	 */
	public CalendarSidebar() {
		setLayout(new MigLayout("", "[grow]", "[10:237.00:3853294,center][246.00,grow][grow][grow][grow][grow][]"));

		final JScrollPane eventScroll = new JScrollPane();
		add(eventScroll, "cell 0 0,grow");
		
		eventTable = new JTable();
		eventScroll.setViewportView(eventTable);
		eventTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"Test", "Nov 19, 2013", "12:00", "1:00"},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Events", "Date", "Start Time", "End Time"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private final boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		final JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "cell 0 1,grow");
		
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
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private final boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(commitmentTable);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 2,grow");
		
		JPanel panelFilter = new JPanel();
		scrollPane.setViewportView(panelFilter);
		panelFilter.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFilter.setLayout(new MigLayout("", "[grow]", "[grow][grow][]"));
		
		JList list = new JList();
		panelFilter.add(list, "cell 0 1,grow");
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
		panelFilter.add(btnApply, "flowx,cell 0 2");
		
		JButton btnDelete = new JButton("Delete");
		panelFilter.add(btnDelete, "cell 0 2");
		
		JButton btnNewFilter = new JButton("New Filter");
		panelFilter.add(btnNewFilter, "cell 0 2");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		add(scrollPane_2, "cell 0 3,grow");
		
		JPanel panelCatCreate = new JPanel();
		scrollPane_2.setViewportView(panelCatCreate);
		panelCatCreate.setBorder(new TitledBorder(null, "Create a Category", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCatCreate.setLayout(new MigLayout("", "[][][]", "[][][][]"));
		
		JLabel lblCategory = new JLabel("Category:");
		panelCatCreate.add(lblCategory, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		panelCatCreate.add(textField, "cell 1 0 2 1,growx");
		textField.setColumns(10);
		
		JRadioButton rdbtnTeam = new JRadioButton("Team");
		panelCatCreate.add(rdbtnTeam, "flowx,cell 1 1");
		
		JRadioButton rdbtnPersonal = new JRadioButton("Personal");
		panelCatCreate.add(rdbtnPersonal, "cell 1 1");
		
		JButton btnSubmit = new JButton("Submit");
		panelCatCreate.add(btnSubmit, "cell 2 1");

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
