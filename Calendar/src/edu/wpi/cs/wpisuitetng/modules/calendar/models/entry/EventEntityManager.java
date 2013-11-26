/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.models.entry;

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
public class EventEntityManager implements EntityManager<Event> {

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
	public EventEntityManager( Data db ) {
		this.db = db; 
	}

	/**
	 * Saves an instance of Event when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Event makeEntity( Session s, String content ) throws WPISuiteException {
		final Event newCldrData = Event.fromJson(content);
		if( !db.save( newCldrData, s.getProject() ) ) {
			throw new WPISuiteException();
		}
		return newCldrData;
	}
	
	/**
	 * Retrieves a single Event from the database
	 * @param s the session
	 * @param id the id number of the Event to retrieve
	 * @return the Event matching the given id * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String) */
	@Override
	public Event[] getEntity( Session s, String id ) throws NotFoundException {
		final int intId = Integer.parseInt( id );
		if( intId < 1 ) {
			throw new NotFoundException();
		}
		Event[] events = null;
		try {
			events = db.retrieve( Event.class, "id", intId, s.getProject()).toArray(new Event[0] );
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if( events.length < 1 || events[0] == null ) {
			throw new NotFoundException();
		}
		return events;
	}

	/**
	 * Retrieves all Event from the database
	 * @param s the session
	 * @return array of all stored calendar data * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public Event[] getAll( Session s ) {
		return db.retrieveAll( new Event(), s.getProject()).toArray(new Event[0] );
	}

	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save( Session s, Event model ) {
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
		db.deleteAll( new Event(), s.getProject() );
	}
	
	/**
	 * Returns the number of calendar data instances in the database
	 * @return number of calendar data instances stored * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() */
	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll( new Event() ).size();
	}

	/**
	 * Method update.
	 * @param session Session
	 * @param content String
	 * @return Event * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String) * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	@Override
	public Event update( Session session, String content ) throws WPISuiteException {
		
		Event updatedEvent = Event.fromJson( content );
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save Events.
		 * We have to get the original defect from db4o, copy properties from updatedEvent,
		 * then save the original Event again.
		 */
		List<Model> oldEvents = db.retrieve( Event.class, "id",
				                           updatedEvent.getId(), session.getProject() );
		if( oldEvents.size() < 1 || oldEvents.get(0) == null ) {
			throw new BadRequestException( "Event with ID does not exist." );
		}
				
		Event existingEvent = (Event)oldEvents.get(0);		

		// copy values to old calendar and fill in our changeset appropriately
		existingEvent.copyFrom( updatedEvent );
		
		if(!db.save(existingEvent, session.getProject())) {
			throw new WPISuiteException();
		}
		
		return existingEvent;
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
