package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class CalendarSidebar extends JPanel {
	private JTable eventTable;
	private JTable commitmentTable;

	/**
	 * Create the panel.
	 */
	public CalendarSidebar() {
		setLayout(new MigLayout("", "[grow]", "[10:237.00:3853294,center][246.00,grow]"));

		JScrollPane eventScroll = new JScrollPane();
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
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
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
			boolean[] columnEditables = new boolean[] {
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
