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
package edu.wpi.cs.wpisuitetng.modules.calendar;

import java.util.List;
import java.util.HashMap;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

/** The entity mananger for Calendar events.
 * 
 * @author cporell
 * @version 1.0
 *
 */
public class CldrEntityManager implements EntityManager<EventCldr>{
	Data db;
	public CldrEntityManager(Data db){
		this.db = db;
	}
	
	// Methods for saving, updating, etc, will be called via IO ********
	
	public void makeSampleCldr() throws WPISuiteException{
		final EventCldr ourCldr = new EventCldr("Demo", null, null);
		this.save(null, ourCldr);
		System.out.println("Created a demo Calendar.");
	}
	
	public void getSampleCldr() throws WPISuiteException{
		final EventCldr ourCldr = new EventCldr("Demo", null, null);
		final EventCldr retCldr = this.retrieveCldr(null, ourCldr);
		System.out.println("Retrieved the Calendar named " + retCldr.getName());
	}
	
	@Override
	public void save(Session s, EventCldr model) throws WPISuiteException {
		db.save(model);
		System.out.println("Events saved.");
	}
		
	public EventCldr retrieveCldr(Session s, EventCldr content) throws WPISuiteException{
		final List<Model> cldrData = db.retrieve(Calendar.class, null, 0);
		final EventCldr cldr = (EventCldr) cldrData.get(0);
		System.out.println("Retrieved the Calendar.");
		return cldr;
	}
	// Updates a Calendar using the content input.
	// Because of the occasional quirkiness of db4o 
	// We delete the old Calendar and save the updated one as a new Cldr.
	public EventCldr updateCldr(Session s, EventCldr content) throws WPISuiteException{
		final List<Model> oldEventData = db.retrieve(Calendar.class, null, 0);
		if(oldEventData.size() < 1){
			//Calendar existingCalendar = (Calendar)oldEventData.get(0);
			// ^^ We probably don't need this.
			db.save(content);
		}
		if((oldEventData.size() >= 1) && (content != null)){
			db.delete(oldEventData);
			db.save(content);
		}
		else {
			db.save(content);
		}
		System.out.println("Events saved.");
		return content;
	}

	
	public void deleteCldr(Session s, EventCldr content) throws WPISuiteException{
		db.delete(content);
	}
	
	@Override
	public EventCldr makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventCldr[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventCldr[] getAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int Count() throws WPISuiteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventCldr update(Session s, String content) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}


