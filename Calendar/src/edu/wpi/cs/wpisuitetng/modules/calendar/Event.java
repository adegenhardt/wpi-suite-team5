package edu.wpi.cs.wpisuitetng.modules.calendar;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
//import edu.wpi.cs.wpisuitetng.modules.postboard.model.PostBoardMessage; I'm not sure if we need this, but
//I'll just comment it out to be safe.
//import edu.wpi.cs.wpisuitetng.database.data;
import java.util.List;
import java.util.Date;


public class Event {
	String eventName;
	String eventDescr;
	Date startDate;
	Date endDate;	
	//String category;
	//Repeat repeat;
	//List<String> participants;
	//boolean committed;
	
	public Event(String eventName, String eventDescr, Date startDate, Date endDate, 
			boolean committed, List<String> participants, String category, Repeat repeat){
		this.eventName = eventName;
		this.eventDescr = eventDescr;
		this.startDate = startDate;
		this.endDate = endDate;
		//this.category = category;
		//this.repeat = repeat;
		//this.committed = committed;
		//this.participants = participants;
	}
	

	//***It seems that these are all kept in EventCldr, but I will keep these here for safekeeping***
	
	//Stores a new event in the database
	//public void storeEvent(){
	//	save(this);
	//	System.out.println("Stored "+this.eventName);
	//}
	
	//Uses input from the GUI to update the event
	//Find the event, delete it, then create the updated ver. of it
	//public void updateEvent(){
	//	save(this);	
	//}
	
	//Deletes an event from the database
	//public void deleteEvent(){
	//	delete(this);
	//}

}
