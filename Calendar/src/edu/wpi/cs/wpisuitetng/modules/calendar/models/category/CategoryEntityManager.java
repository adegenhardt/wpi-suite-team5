/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Underscore 
 *    
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.models.category;

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
 * 
 * @author Team_
 * @version 1.0
 *
 */
public class CategoryEntityManager implements EntityManager<Category>{

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
	public CategoryEntityManager( Data db ) {
		this.db = db; 
	}

	/**
	 * Saves an instance of Event when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity
	 * (edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	public Category makeEntity( Session s, String content ) 
			throws WPISuiteException {
		final Category newCategory = Category.fromJson(content);
		if( !db.save( newCategory, s.getProject() ) ) {
			throw new WPISuiteException( "Unable to save session" );
		}
		return newCategory;
	}
	
	/**
	 * Retrieves a single Event from the database
	 * @param s the session
	 * @param id the id number of the Event to retrieve
	 * @return the Event matching the given id * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String) */
	@Override
	public Category[] getEntity( Session s, String id ) throws NotFoundException {
		final int intId = Integer.parseInt( id );
		if( intId < 1 ) {
			throw new NotFoundException( "ID must be greater than or equal to 1" );
		}
		Category[] categories = null;
		try {
			categories = db.retrieve( Category.class, "id",
					intId, s.getProject()).toArray(new Category[0] );
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if( categories.length < 1 || categories[0] == null ) {
			throw new NotFoundException( "Unable to find any categories" );
		}
		return categories;
	}

	/**
	 * Retrieves all Event from the database
	 * @param s the session
	 * @return array of all stored calendar data
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public Category[] getAll( Session s ) {
		return db.retrieveAll( new Category(), s.getProject()).toArray(new Category[0] );
	}

	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save( Session s, Category model ) {
		db.save( model, s.getProject() );
	}
	
	/**
	 * Ensures that a user is of the specified role
	 * @param session the session
	 * @param role the role being verified
	 * @throws WPISuiteException user isn't authorized for the given role */
	private void ensureRole( Session session, Role role ) throws WPISuiteException {
		final User user = (User) db.retrieve( User.class, "username",
				               session.getUsername()).get(0);
		if( !user.getRole().equals( role ) ) {
			throw new UnauthorizedException(
					"You do not have access to the requested session" );
		}
	}
	
	/**
	 * Deletes a category from the database
	 * @param s the session
	 * @param id the id of the calendar data to delete
	 * @return true if the deletion was successful
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String) */
	@Override
	public boolean deleteEntity( Session s, String id ) throws WPISuiteException {
		ensureRole( s, Role.ADMIN );
		return ( db.delete( getEntity( s, id )[0] ) != null ) ? true : false;
	}
	
	/**
	 * Deletes all categories from the database
	 * @param s the session
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole( s, Role.ADMIN );
		db.deleteAll( new Category(), s.getProject() );
	}
	
	/**
	 * Returns the number of category instances in the database
	 * @return number of calendar data instances stored
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() */
	@Override
	public int Count() {
		return db.retrieveAll( new Category() ).size();
	}

	/**
	 * Method update.
	 * @param session Session
	 * @param content String
	 * @return Event
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	@Override
	public Category update( Session session, String content ) throws WPISuiteException {
		
		final Category updatedCategory = Category.fromJson( content );
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save data.
		 * We have to get the original defect from db4o, copy properties from updatedCategory,
		 * then save the original Category again.
		 */
		final List<Model> oldCategories = db.retrieve( Category.class, "id",
				                           updatedCategory.getId(), session.getProject() );
		if( oldCategories.size() < 1 || oldCategories.get(0) == null ) {
			throw new BadRequestException( "Category with ID does not exist." );
		}
				
		final Category existingCategory = (Category)oldCategories.get(0);

		// copy values to old calendar and fill in our changeset appropriately
		existingCategory.copyFrom( updatedCategory );
		
		if(!db.save(existingCategory, session.getProject())) {
			throw new WPISuiteException( "Unable to save updated project" );
		}
		
		return existingCategory;
	}

	/**
	 * Method advancedGet.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @return String
	 * @throws NotImplementedException
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
	 * @return String
	 * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String)
	 */
	@Override
	public String advancedPost( Session arg0, String arg1, String arg2 )
			throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPut.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @param arg2 String
	 * @return String
	 * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	@Override
	public String advancedPut( Session arg0, String[] arg1, String arg2 )
			throws NotImplementedException {
		throw new NotImplementedException();
	}
}
