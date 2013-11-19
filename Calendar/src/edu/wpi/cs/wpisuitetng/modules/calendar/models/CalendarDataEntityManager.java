/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This is the entity manager for the CalendarData in the
 * Calendar module.
 *
 * @version $Revision: 1.0 $
 * @author srkodzis
 */
public class CalendarDataEntityManager implements EntityManager<CalendarData> {

	/** The database */
	Data db;
	
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public CalendarDataEntityManager( Data db ) {
		this.db = db; 
	}

	/**
	 * Saves an instance of CalendarData when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public CalendarData makeEntity( Session s, String content ) throws WPISuiteException {
		final CalendarData newCldrData = CalendarData.fromJson(content);
		if( !db.save( newCldrData, s.getProject() ) ) {
			throw new WPISuiteException();
		}
		return newCldrData;
	}
	
	/**
	 * Retrieves a single CalendarData from the database
	 * @param s the session
	 * @param id the id number of the CalendarData to retrieve
	 * @return the CalendarData matching the given id * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String) */
	@Override
	public CalendarData[] getEntity( Session s, String id ) throws NotFoundException {
		final int intId = Integer.parseInt( id );
		if( intId < 1 ) {
			throw new NotFoundException();
		}
		CalendarData[] cldrData = null;
		try {
			cldrData = db.retrieve( CalendarData.class, "id", intId, s.getProject()).toArray(new CalendarData[0] );
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if( cldrData.length < 1 || cldrData[0] == null ) {
			throw new NotFoundException();
		}
		return cldrData;
	}

	/**
	 * Retrieves all CalendarData from the database
	 * @param s the session
	 * @return array of all stored calendar data * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public CalendarData[] getAll( Session s ) {
		return db.retrieveAll( new CalendarData(), s.getProject()).toArray(new CalendarData[0] );
	}

	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save( Session s, CalendarData model ) {
		db.save( model, s.getProject() );
	}
	
	/**
	 * Ensures that a user is of the specified role
	 * @param session the session
	 * @param role the role being verified
	 * @throws WPISuiteException user isn't authorized for the given role */
	private void ensureRole( Session session, Role role ) throws WPISuiteException {
		User user = (User) db.retrieve( User.class, "username",
				               session.getUsername()).get(0);
		if( !user.getRole().equals( role ) ) {
			throw new UnauthorizedException();
		}
	}
	
	/**
	 * Deletes calendar data from the database
	 * @param s the session
	 * @param id the id of the calendar data to delete
	 * @return true if the deletion was successful * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String) */
	@Override
	public boolean deleteEntity( Session s, String id ) throws WPISuiteException {
		ensureRole( s, Role.ADMIN );
		return ( db.delete( getEntity( s, id )[0] ) != null ) ? true : false;
	}
	
	/**
	 * Deletes all calendar data from the database
	 * @param s the session
	
	
	 * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole( s, Role.ADMIN );
		db.deleteAll( new CalendarData(), s.getProject() );
	}
	
	/**
	 * Returns the number of calendar data instances in the database
	 * @return number of calendar data instances stored * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() */
	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll( new CalendarData() ).size();
	}

	/**
	 * Method update.
	 * @param session Session
	 * @param content String
	 * @return CalendarData * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String) * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	@Override
	public CalendarData update( Session session, String content ) throws WPISuiteException {
		
		CalendarData updatedCalendarData = CalendarData.fromJson( content );
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save CalendarDatas.
		 * We have to get the original defect from db4o, copy properties from updatedCalendarData,
		 * then save the original CalendarData again.
		 */
		List<Model> oldCalendarDatas = db.retrieve( CalendarData.class, "id",
				                           updatedCalendarData.getId(), session.getProject() );
		if( oldCalendarDatas.size() < 1 || oldCalendarDatas.get(0) == null ) {
			throw new BadRequestException( "CalendarData with ID does not exist." );
		}
				
		CalendarData existingCalendarData = (CalendarData)oldCalendarDatas.get(0);		

		// copy values to old calendar and fill in our changeset appropriately
		existingCalendarData.copyFrom( updatedCalendarData );
		
		if(!db.save(existingCalendarData, session.getProject())) {
			throw new WPISuiteException();
		}
		
		return existingCalendarData;
	}

	/**
	 * Method advancedGet.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[]) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[])
	 */
	@Override
	public String advancedGet( Session arg0, String[] arg1 ) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPost.
	 * @param arg0 Session
	 * @param arg1 String
	 * @param arg2 String
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String)
	 */
	@Override
	public String advancedPost( Session arg0, String arg1, String arg2 ) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPut.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @param arg2 String
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	@Override
	public String advancedPut( Session arg0, String[] arg1, String arg2 ) throws NotImplementedException {
		throw new NotImplementedException();
	}

}
