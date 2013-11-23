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
package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

import java.util.Date;
import java.util.List;

/**
 * @author Team Underscore
 * @version 1.0
 */
public class Commitment {
	private String commitName;
	private Date dueDate;
	private List<String> tasks;
	//private CalendarItemLevel type;
	
	/**
	 * Constructor for Commitment.
	 * @param commitName String Name of the commitment
	 * @param dueDate Date Due date for the commitment 
	 * @param tasks List<String> Tasks that are to be completed for the commitment
	 * @param type 
	 * @param type An enum representing whether the commitment is personal or team
	 *             The functionality for type will be implemented now, but
	 *             it may be removed later. It depends on whether commitments are
	 *             purely personal, or whether commitments can exist on both personal
	 *             and team calendars. -Connor 
	 */
	public Commitment (String commitName, Date dueDate, List<String> tasks 
			/*CalendarItemLevel type*/){
		this.commitName = commitName;
		this.dueDate = dueDate;
		this.tasks = tasks;
		//this.type = type;
	}
	
	public String getCommitName() {
		return commitName;
	}

	public void setCommitName(String commitName) {
		this.commitName = commitName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public List<String> getTasks() {
		return tasks;
	}

	public void setTasks(List<String> tasks) {
		this.tasks = tasks;
	}
		
	/*public CalendarItemLevel getType(){
		return type;
	}*/
	/*public void setType(CalendarItemLevel type){
		this.type = type;
	}*/
}
