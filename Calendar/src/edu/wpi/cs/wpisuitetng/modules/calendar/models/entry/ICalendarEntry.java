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

/**
 * @author Team Underscore
 * @version $Revision: 1.0 $
 */
public interface ICalendarEntry {

	// linking interface for Events Commitments, and InPlanningEvents
	
	/**
	 * Determine if an entry occurs at some point in a given year
	 * @param year the year to check for the entry
	
	 * @return true if the entry occurs at some point in the year,
	 * false otherwise */
	boolean occursOnYear( int year );
	
	/**
	 * Determine if an entry occurs at some point in a given year
	 * @param year the year to check for the entry
	 * @param month the month to check for the entry (0-11)
	
	 * @return true if the entry occurs during the given month and year,
	 * false otherwise */
	boolean occursOnMonth( int year, int month );
	
	/**
	 * Determine if an entry occurs at some point in a given year
	 * @param year the year to check for the entry
	 * @param month the month to check for the entry (0-11)
	 * @param day the day to check for the entry (0-30)
	
	 * @return true if the entry occurs during the given month and year,
	 * false otherwise */
	boolean occursOnDate( int year, int month, int day );
	
	/**
	 * Obtain the users involved with an entry
	 * @return a collection of user IDs (as strings) for users
	 * involved with an entry
	 */
	Collection<String> getUserIds();
	
	/**
	 * Determine if a user has access to an entry
	 * @param userId the ID of the user being tested
	
	 * @return true if the user has access, false otherwise */
	boolean hasAccess( String userId );
	
	/**
	 * Determine whether or not an entry is deleted
	 * @return true if the entry is deleted,
	 * false otherwise
	 */
	boolean isDeleted();
	
	/**
	 * Set an object to be deleted or not
	 * @param isDeleted a boolean that is true if an object is deleted
	 * or false if the object is not
	 */
	void setDeleted( boolean isDeleted );
	
}
