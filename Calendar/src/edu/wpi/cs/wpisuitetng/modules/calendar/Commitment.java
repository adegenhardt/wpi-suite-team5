package edu.wpi.cs.wpisuitetng.modules.calendar;

import java.util.Date;
import java.util.List;

public class Commitment {
	String commitName;
	Date dueDate;
	List<String> tasks;
	
	public Commitment (String commitName, Date dueDate, List<String> tasks){
		this.commitName = commitName;
		this.dueDate = dueDate;
		this.tasks = tasks;
	}
	
}
