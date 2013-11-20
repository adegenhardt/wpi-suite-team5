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



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

/**
 * @author justinhess
 * @version $Revision: 1.0 $
 * 
 */
public class YearViewCalendarPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;

	private final YearViewCalendar calendarView;
	
	private final JButton nextYear;
	private final JButton prevYear;
	//private final JButton nextMonth;
	//private final JButton prevMonth;
	private final JButton year; 
	
	/**
	 * Constructor for IterationCalendarPanel.
	 * @param parent IterationPanel
	 * @param vm ViewMode
	 * @param displayIteration Iteration
	 */
	public YearViewCalendarPanel()
	{
		
		final JPanel contentPanel = new JPanel();

		contentPanel.setLayout(new MigLayout("", "[924.00px]", "[30.00][500px]"));
		
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 5));
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		nextYear = new JButton("Next Year");
		nextYear.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		/*
		nextMonth = new JButton(">");
		nextMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		*/	
		year = new JButton("This Year");
		year.setBounds(0, 0, 400, 400);
		/*	
		prevMonth = new JButton("<");
		prevMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		buttonPanel.setLayout(new GridLayout(0, 5, 0, 0));
		*/			
		prevYear = new JButton("Previous");
		prevYear.setPreferredSize(new Dimension(200, 23));
		prevYear.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						
		buttonPanel.add(prevYear);
		//buttonPanel.add(prevMonth);
		buttonPanel.add(year);
		//buttonPanel.add(nextMonth);
		buttonPanel.add(nextYear);
						
		contentPanel.add(buttonPanel, "cell 0 0,alignx center,aligny center");
		 		
		final JPanel calendarPanel = new JPanel(new BorderLayout());
		calendarView = new YearViewCalendar();
		calendarPanel.add(calendarView, BorderLayout.CENTER);
				
		calendarPanel.add(calendarView, BorderLayout.CENTER);
		contentPanel.add(calendarPanel, "cell 0 1,alignx left,aligny top");
		
		setupButtonListeners();

		this.setViewportView(contentPanel);
	}
	
	/**
	 * Adds action listeners to the year control buttons for the calendar view.
	 **/
	
	private void setupButtonListeners()
	{
		nextYear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				nextYear();
			}
		});
		
		prevYear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				previousYear();
			}
		});
		year.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				today();
			}
		});
		prevMonth.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				previousMonth();
			}
		});
		
		nextMonth.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				nextMonth();
			}
		});
		
	}
	
	/**
	 * Switches the calendar to the previous year.
	 */
	private void previousYear()
	{
		final Calendar cal = calendarView.getCalendar();
		cal.add(Calendar.YEAR, -1);
		calendarView.setFirstDisplayedDay(cal.getTime());
	}
	
	/**
	 * Switches the calendar to the next month.
	 */
	private void nextMonth()
	{
		final Calendar cal = calendarView.getCalendar();
		cal.add(Calendar.MONTH, 1);
		calendarView.setFirstDisplayedDay(cal.getTime());
	}
	/**
	 * Changed the calendar view to the current year and back to
	 * a regular single year calendar view
	 */
	private void today() {
		final Calendar tempCal = Calendar.getInstance();
		tempCal.set(Calendar.DAY_OF_YEAR, 1);
		calendarView.setFirstDisplayedDay(tempCal.getTime());
	}
	
	/**
	 * Switches the calendar to the previous month.
	 */
	private void previousMonth()
	{
		final Calendar cal = calendarView.getCalendar();
		cal.add(Calendar.MONTH, -1);
		calendarView.setFirstDisplayedDay(cal.getTime());
	}
	
	/**
	 * Switches the calendar to the next year.
	 */
	private void nextYear()
	{
		final Calendar cal = calendarView.getCalendar();
		cal.add(Calendar.YEAR, +1);
		calendarView.setFirstDisplayedDay(cal.getTime());
	}
	
	/**
	 * Returns the iteration calendar
	
	 * @return the iteration calendar */
	public YearViewCalendar getIterationCalendar()
	{
		return calendarView;
	}
}
