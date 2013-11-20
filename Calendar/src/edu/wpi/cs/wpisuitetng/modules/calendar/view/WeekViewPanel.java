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
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Creates the Week View calendar tab
 */
public class WeekViewPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// Millis for day in Calendar class
	// Going to use this to calculate first day of week
	private static final long ONE_DAY = 86400000;
	
	private final DayView dayOne;
	private final DayView dayTwo;
	private final DayView dayThree;
	private final DayView dayFour;
	private final DayView dayFive;
	private final DayView daySix;
	private final DayView daySeven;
	
	private final JButton nextWeek;
	private final JButton prevWeek;
	
	private final Calendar shiftWeek; 

	/**
	 * Create the panel.
	 */
	public WeekViewPanel() {
		
		shiftWeek = Calendar.getInstance();
		
		//final int day = shiftWeek.get(Calendar.DAY_OF_YEAR);
	     // While loop through the week to obtain the first day of the week
	     // Why is this a thiiiiiiiiiiiiingggggggg
	    while(shiftWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	         shiftWeek.setTimeInMillis(shiftWeek.getTimeInMillis() - ONE_DAY);  
	    }  
		
	    final JPanel weekContainer = new JPanel();
	    final JPanel buttonContainer = new JPanel();

		weekContainer.setLayout(new GridLayout(0, 7, 0, 0));
		buttonContainer.setLayout(new GridLayout(0, 2, 0, 0));
		
		// Just set the days to the calculated week above
		// Maybe I should have kept an array?
		
		dayOne = new DayView(true);
		dayOne.refreshDay(shiftWeek);
		
		dayTwo = new DayView(true);
		shiftWeek.add(Calendar.DATE, 1);
		dayTwo.refreshDay(shiftWeek);
		
		dayThree = new DayView(true); 
		shiftWeek.add(Calendar.DATE, 1);
		dayThree.refreshDay(shiftWeek);
		
		dayFour = new DayView(true);
		shiftWeek.add(Calendar.DATE, 1);
		dayFour.refreshDay(shiftWeek);
		
		dayFive = new DayView(true);
		shiftWeek.add(Calendar.DATE, 1);
		dayFive.refreshDay(shiftWeek);
		
		daySix = new DayView(true);
		shiftWeek.add(Calendar.DATE, 1);
		daySix.refreshDay(shiftWeek);
		
		daySeven = new DayView(true);
		shiftWeek.add(Calendar.DATE, 1);
		daySeven.refreshDay(shiftWeek);
		
		nextWeek = new JButton("Next");
		nextWeek.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeWeek(1);
			}
		});
		prevWeek = new JButton("Previous");
		prevWeek.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				changeWeek(-1); 
			}
		});
		
		buttonContainer.add(prevWeek);
		buttonContainer.add(nextWeek);
		
		setLayout(new MigLayout("", "[626px,grow]", "[29.00px][grow]"));

		weekContainer.add(dayOne);
		weekContainer.add(dayTwo);
		weekContainer.add(dayThree);
		weekContainer.add(dayFour);
		weekContainer.add(dayFive);
		weekContainer.add(daySix);
		weekContainer.add(daySeven);
		
		this.add(buttonContainer, "cell 0 0,alignx center"); 
		this.add(weekContainer, "cell 0 1,grow");
		weekContainer.setVisible(true);

	}
	
	
	// Given an integer x, if the x is negative
	// All collected day views will be updated 
	// To display the previous week, and a positive
	// Will display the next week
	private void changeWeek(int x) {
		int dayWeight;
		if (x > 0) {
			dayWeight = 1;
		}
		else {
			dayWeight = -13; 
		}
		shiftWeek.add(Calendar.DATE, dayWeight);
		dayOne.refreshDay(shiftWeek);
		shiftWeek.add(Calendar.DATE, 1);
		dayTwo.refreshDay(shiftWeek);
		shiftWeek.add(Calendar.DATE, 1);
		dayThree.refreshDay(shiftWeek);
		shiftWeek.add(Calendar.DATE, 1);
		dayFour.refreshDay(shiftWeek);
		shiftWeek.add(Calendar.DATE, 1);
		dayFive.refreshDay(shiftWeek);
		shiftWeek.add(Calendar.DATE, 1);
		daySix.refreshDay(shiftWeek);
		shiftWeek.add(Calendar.DATE, 1);
		daySeven.refreshDay(shiftWeek);
	}

}
