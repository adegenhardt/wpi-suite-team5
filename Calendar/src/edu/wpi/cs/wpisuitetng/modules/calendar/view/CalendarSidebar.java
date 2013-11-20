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

	/**
	 * Create the panel.
	 */
	public CalendarSidebar() {
		setLayout(new MigLayout("", "[grow]", "[10:237.00:3853294,center][246.00,grow][grow][]"));

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
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 0 2,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JList list = new JList();
		panel.add(list, "cell 0 0,grow");
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Team", "Personal", "Things"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JButton btnNewButton = new JButton("Create Category");
		add(btnNewButton, "cell 0 3,alignx center");

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
