package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.AddCalendarDataController;

/**List of Calendars pulled from the server
 * 
 * @author cporell, adapted from RequirementModel.java
 *
 * @version $Revision: 1.0 $
 */

public class CalendarDataModel extends AbstractListModel{

	// ********************************************************************
	// Construct the Calendar Model
	
	/**
	 * The list in which all the calendar data for a single project
	 * are contained
	 */
	private List<CalendarData> calendarData;
	private int nextID; // the next available ID number for the calendar data
						// that are added
	// the static object that allows the calendar data model to be
	private static CalendarDataModel instance;
	
	/**
	 * Constructs an empty list of calendar data
	 */
	private CalendarDataModel(){
		calendarData = new ArrayList<CalendarData>();
		nextID = 0;
	}
	
	// **********************************************************************
	// Manipulate calendar data
	
	/**
	 * @return the instance of the calendar data model singleton
	 */
	public static CalendarDataModel getInstance(){
		if(instance == null){
			instance = new CalendarDataModel();
		}
		return instance;
	}
	
	/**
	 * Adds a single calendar datum to the data of the project
	 * 
	 * @param newCldrData The calendar datum to be added to the list
	 * 						of calendar data in the project
	 */
	public void addCalendarData(CalendarData newCldrData){
		calendarData.add(newCldrData);
		try{
			AddCalendarDataController.getInstance().addCalendarData(newCldrData);
//			ViewEventController.getInstance().refreshTable();
//			ViewEventController.getInstance().refreshTree();
		}
		catch(Exception e){}
	}
	
	/**
	 * Returns the Calendar Data with the given ID
	 * 
	 * @param id The ID number of the calendar data to be returned
	 * 
	
	 * @return the calendar data for the ID, or null if the data is not
	 * 			found. */
	public CalendarData getCalendarData(int id){
		CalendarData temp = null;
		for(int i = 0; i < this.calendarData.size(); i++){
			temp = calendarData.get(i);
			if(temp.getId() == id){
				break;
			}
		}
		return temp;
	}
	
	/**
	 * Removes the calendar data with the given ID
	 * 
	 * @param removeID The ID number of the cldr data to be removed
	 */
	public void removeCalendarData(int removeID){
		for(int i = 0; i < this.calendarData.size(); i++){
			if(calendarData.get(i).getId() == removeID){
				calendarData.remove(i);
				break;
			}
		}
		try{
//			ViewEventController.getInstance().refreshTable();
//			ViewEventController.getInstance().refreshTree();
		}
		catch(Exception e){}
	}
		
	/**
	 * Removes all the calendar data from a model
	 * Each calendar datum is removed individually
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<CalendarData> iterator = calendarData.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		try{
//			ViewEventController.getInstance().refreshTable();
//			ViewEventController.getInstance().refreshTree();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Adds the given array of calendar data to the list
	 * @param calendarData the array of data to add
	 */
	public void addCalendarData(CalendarData[] calendarData){
		for (int i = 0; i < calendarData.length; i++) {
			this.calendarData.add(calendarData[i]);
			if(calendarData[i].getId() >= nextID){ 
				nextID = calendarData[i].getId() + 1;
			}
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
//		ViewEventController.getInstance().refreshTable();
//		ViewEventController.getInstance().refreshTree();
	}
	
	// ******************************************************************
	// Getters for the calendar data
	
	/**
	 * Provides the number of elements in the list of calendar data for 
	 * this project. Elements are returned from the newest to the oldest.
	 * 
	 * @return the number of calendar data in the project
	 */
	public int getSize() {
		return calendarData.size();
	}
	
	/**
	 * Provides the next ID number the should be used for the next 
	 * calendar data generated.
	 * 
	 * @return the next avail. ID number
	 */
	public int getNextID(){
		return this.nextID++;
	}

	/**
	 * Takes in an index and find the calendar data in the list for the
	 * project. 
	 * 
	 * @param index The index of the calendar data to be returned
	 * @return the calendar data associated with the given index
	 */
	public Object getElementAt(int index) {
		return calendarData.get(calendarData.size() - 1 - index);
	}
		
	/**
	 * Returns the list of calendar data
	 * @return the requirements held within the calendar data model. */
	public List<CalendarData> getCalendarData() {
		return calendarData;
	}
	
	//**************************************************************************
	//Everything below this point involves use of data fields that
	//our calendar data is not likely to have.
	//In case we do need these functions, adapt the following from the
	//Req't Manager Model:
	//    * getChildren()
	//    * getPossibleChildren()
	//    * getPossibleParents()
	//    * getCalendarDataForIteration()
	//    * getCalendarEstimateForIteration()
	
}
