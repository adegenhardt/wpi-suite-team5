package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;

public class WeekViewPanel extends JPanel {
	
	private DayView dayOne;
	private DayView dayTwo;
	private DayView dayThree;
	private DayView dayFour;
	private DayView dayFive;
	private DayView daySix;
	private DayView daySeven;
	
	private final JButton nextWeek;
	private final JButton prevWeek;
	
	private Calendar shiftWeek; 

	/**
	 * Create the panel.
	 */
	public WeekViewPanel() {
		
		JPanel weekContainer = new JPanel();
		JPanel buttonContainer = new JPanel();

		weekContainer.setLayout(new GridLayout(0, 7, 0, 0));
		buttonContainer.setLayout(new GridLayout(0, 2, 0, 0));
		
		dayOne = new DayView();
		shiftWeek = dayOne.getRealDay();
		dayTwo = new DayView();
		
		shiftWeek.add(Calendar.DATE, 1);
		dayTwo.refreshDay(shiftWeek);
		
		dayThree = new DayView(); 
		shiftWeek.add(Calendar.DATE, 1);
		dayThree.refreshDay(shiftWeek);
		
		dayFour = new DayView();
		shiftWeek.add(Calendar.DATE, 1);
		dayFour.refreshDay(shiftWeek);
		
		dayFive = new DayView();
		shiftWeek.add(Calendar.DATE, 1);
		dayFive.refreshDay(shiftWeek);
		
		daySix = new DayView();
		shiftWeek.add(Calendar.DATE, 1);
		daySix.refreshDay(shiftWeek);
		
		daySeven = new DayView();
		shiftWeek.add(Calendar.DATE, 1);
		daySeven.refreshDay(shiftWeek);
		
		nextWeek = new JButton(">");
		prevWeek = new JButton("<");
		
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
	
	private void nextWeek() {
		
	}
	
	private void prevWeek() {
		
	}

}
