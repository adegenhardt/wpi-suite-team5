package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

public class DayViewPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DayView dayView;
	
	private final JPanel buttonsPanel;
	
	private final JButton nextDay;
	private final JButton prevDay;
	
	private final JLabel currentDate;

	/**
	 * Create the panel.
	 */
	public DayViewPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{555, 0};
		gridBagLayout.rowHeights = new int[]{37, 657, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		buttonsPanel = new JPanel(); 
		buttonsPanel.setLayout(new MigLayout());
		
		dayView = new DayView();
		currentDate = new JLabel(dayView.getStringDay());
		prevDay = new JButton("<");
		prevDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Calendar currentDisplay = dayView.getRealDay();
				currentDisplay.add(Calendar.DATE, -1);
				dayView.refreshDay(currentDisplay);
				currentDate.setText(dayView.getStringDay());
			}
		});
		nextDay = new JButton(">");
		nextDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Calendar currentDisplay = dayView.getRealDay();
				currentDisplay.add(Calendar.DATE, 1);
				dayView.refreshDay(currentDisplay);
				currentDate.setText(dayView.getStringDay());
			}
		});

		
		buttonsPanel.add(prevDay, "span 2");
		buttonsPanel.add(currentDate, "span 2");
		buttonsPanel.add(nextDay, "span 2");
		GridBagConstraints gbc_buttonsPanel = new GridBagConstraints();
		gbc_buttonsPanel.anchor = GridBagConstraints.SOUTH;
		gbc_buttonsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_buttonsPanel.gridx = 0;
		gbc_buttonsPanel.gridy = 0;
		this.add(buttonsPanel, gbc_buttonsPanel);
		GridBagConstraints gbc_dayView = new GridBagConstraints();
		gbc_dayView.fill = GridBagConstraints.BOTH;
		gbc_dayView.gridx = 0;
		gbc_dayView.gridy = 1;
		this.add(dayView, gbc_dayView);
	}

}
