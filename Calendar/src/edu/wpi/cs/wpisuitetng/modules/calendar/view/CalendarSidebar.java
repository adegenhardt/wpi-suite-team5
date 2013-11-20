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
		setLayout(new MigLayout("", "[grow]", "[10:237.00:3853294,center][246.00,grow]"));

		final JScrollPane eventScroll = new JScrollPane();
		add(eventScroll, "cell 0 0,grow");
		
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
