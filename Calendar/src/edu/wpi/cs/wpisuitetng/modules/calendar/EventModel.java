/**
 * See RequirementModel.java as a reference 
 */

package edu.wpi.cs.wpisuitetng.modules.calendar;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

public class EventModel extends AbstractListModel{

	/**
	 * The list which contains all the events for a project
	 */
	private List<Event> events;
	private int nextID; // the ID number of the next event
	private static EventModel instance;
		// Allows the model to be
	
	/**
	 * Constructs an empty list of events for the project
	 */
	private EventModel(){
		this.events = new ArrayList<Event>();
		this.nextID = 0;
	}
	
	/**
	 * get an Instance of Calendar Data, or create a new one if there is
	 * no instance already.
	 * @return the Instance of calendar data.
	 */
	public static EventModel getInstance(){
		if (instance == null){
			instance = new EventModel();
		}
		return instance;
	}
	
	/**
	 * Adds a single event to the event list of the project
	 * @param newEvent The event to be added to the list of events of
	 * 	 			   the project.
	 */
	public void addEvent(Event newEvent){
		// add the event
		events.add(newEvent);
		try{
			AddEventController.getInstance().addEvent(newEvent);
			ViewEventController.getInstance().refreshTable();
			ViewEventController.getInstance().refreshTree();
		}
		catch(Exception e){}
	}
	
	/**
	 * Returns the Event with a given ID
	 * @param id The ID of the event to be returned
	 * @return the event with the given ID, or null if the ID is
	 * 			not linked to an event
	 */
	public Event getEvent(int id){
		Event tempEv = null;
		// iterate through the events until the desired one is found
		for (int i = 0; i < this.events.size(); i++){
			tempEv = events.get(i);
			if (tempEv.getEventID() == id){
				break;
			}
		}
		return tempEv;
	}
	
	/**
	 * Given an event's ID, removes the event from the list
	 * @param eventID The ID number of the event to be removed from the list
	 */
	public void removeEvent(int eventID){
		// iterate through the events until the desired eventID is found
		for (int i = 0; i < this.events.size(); i++){
			if (events.get(i).getEventID() == eventID){
				events.remove(i);
				break;
			}
		}
		try{
			ViewEventController.getInstance().refreshTable();
			ViewEventController.getInstance().refreshTree();
		}
		catch(Exception e){}
	}
	
	
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
