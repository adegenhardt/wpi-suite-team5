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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.DateInfo;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;

/**
 * @author Team Underscore
 * @version 1.0
 */
public class Commitment extends AbstractModel implements ICalendarEntry {
	// Parameters 2
	// ID Parameters
	private int absoluteId;
	private String projectId;
	private String userId;
	private String typeId;
	private boolean isDeleted;

	// Date Region information
	private int year;
	private int month;
	private int day;
	private int halfHour;

	// Descriptive Parameters
	private String name;
	private String description;
	private DateInfo dueDate;
	private List<Category> category;

	private List<String> tasks;

	/**
	 * Full Specification Constructor for Commitment.
	 * @param projectId String  name of project Commitment is Linked to.
	 * @param userId String name of User Commitment is Linked to (either by creation or by personal calendar)
	 * @param typeId String  "personal" or "project" string to ID which form of calendar it is related to
	 * @param name String name of commitment
	 * @param description String description of commitment
	 * @param dueDate DateInfo dateInfo parameter for holding date commitment is due
	 * @param category List<Category> list of categories commitment is part of
	 * @param tasks List<String> list of tasks for commitment
	 * @param absoluteId int
	 */
	public Commitment(int absoluteId, String projectId, String userId, String typeId,
			String name, String description, DateInfo dueDate,
			List<Category> category, List<String> tasks
	/* CalendarItemLevel type */) {

		this.absoluteId = absoluteId;
		this.projectId = projectId;
		this.userId = userId;
		this.typeId = typeId;
		this.isDeleted = false;

		// Date Region information
		this.year = dueDate.getYear();
		this.month = dueDate.getMonth();
		this.day = dueDate.getDay();
		this.halfHour = dueDate.getHalfHour();

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.category = category;
		this.tasks = tasks;
	}

	/**
	 * System Implemented Constructor for Commitment.
	 * Gets and sets the project and user Id fields based on system's current project and user
	 * To be used in User commitment creation
	 * @param name String name of commitment
	 * @param description String description of commitment
	 * @param dueDate DateInfo dateInfo parameter for holding date commitment is due
	 * @param typeId String  "personal" or "project" string to ID which form of calendar it is related to
	 * @param category List<Category> list of categories commitment is part of
	 * @param tasks List<String> list of tasks for commitment
	 */
	public Commitment( String name, String description, DateInfo dueDate,
			String typeId,List<Category> category, List<String> tasks) {

		//this.absoluteId = TODO this.hashCode();// generate new id;
		//this.projectId = TODO getProjectName();
		//this.userId = TODO getUserName();
		this.typeId = typeId;
		this.isDeleted = false;

		// Date Region information
		this.year = dueDate.getYear();
		this.month = dueDate.getMonth();
		this.day = dueDate.getDay();
		this.halfHour = dueDate.getHalfHour();

		// Descriptive Parameters
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.category = category;
		this.tasks = tasks;
	}
	
	// GetterSetter Functions
	// ---------------------------------

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public DateInfo getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(DateInfo dueDate) {
		this.dueDate = dueDate;
	}

	public List<String> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<String> tasks) {
		this.tasks = tasks;
	}

	public void setType(String typeId) {
		this.typeId = typeId;
	}

	public String getIdProject() {
		return projectId;
	}

	public void setIdProject(String idProject) {
		this.projectId = idProject;
	}

	public String getIdUser() {
		return userId;
	}

	public void setIdUser(String idUser) {
		this.userId = idUser;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	// END Get/Set Functions
	// ------------------------------------------

	public int getAbsoluteId() {
		return absoluteId;
	}

	public void setAbsoluteId(int absoluteId) {
		this.absoluteId = absoluteId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHalfHour() {
		return halfHour;
	}

	public void setHalfHour(int halfHour) {
		this.halfHour = halfHour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	// Required Functions Database Interaction
	// -----------------------------------------
	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean identify(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean occursOnYear(int year) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean occursOnMonth(int year, int month) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean occursOnDate(int year, int month, int day) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<String> getUserIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDeleted(boolean isDeleted) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasAccess(String userId) {
		// TODO Auto-generated method stub
		return false;
	}



	// END Required Functions Database Interaction
	// -----------------------------------------

}
