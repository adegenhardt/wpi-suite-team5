/**
 *  * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.buttons;

/**
 *
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * @author justinhess
 * @version $Revision: 1.0 $
 */
public class ChartButtonsPanel extends JPanel{
	
	JButton pieChart;
	JButton barChart;
	
	public ChartButtonsPanel(){
		setBorder(BorderFactory.createTitledBorder("Charts")); // add a border so you can see the panel
		SpringLayout toolbarLayout = new SpringLayout();
		this.setLayout(toolbarLayout);
		
		pieChart = new JButton("View Pie Charts");
		
		pieChart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				ViewEventController.getInstance().createPieChart("Status");
			}
		});
		
		barChart = new JButton("View Bar Charts");
		
		barChart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				ViewEventController.getInstance().createBarChart("Status");
				
				
				
				
			}
		});
		
		barChart.setIcon(new ImageIcon("../RequirementManager/resources/view-bar-charts-icon.png"));
		barChart.setHorizontalTextPosition(AbstractButton.CENTER);
		barChart.setVerticalTextPosition(AbstractButton.BOTTOM);
		
		pieChart.setIcon(new ImageIcon("../RequirementManager/resources/view-pie-charts-icon.png"));
		pieChart.setHorizontalTextPosition(AbstractButton.CENTER);
		pieChart.setVerticalTextPosition(AbstractButton.BOTTOM);
		
		toolbarLayout.putConstraint(SpringLayout.WEST, barChart, 35,SpringLayout.WEST, this);
		toolbarLayout.putConstraint(SpringLayout.WEST, pieChart, 30, SpringLayout.EAST, barChart);
		
		this.add(pieChart);
		this.add(barChart);
	}

	/**
	 * Method getBarChartButton.
	 * @return JButton
	 */
	public JButton getBarChartButton() {
		return barChart;
	}

	/**
	 * Method getPieChartButton.
	 * @return JButton
	 */
	public JButton getPieChartButton() {
		return pieChart;
	}
	
}
