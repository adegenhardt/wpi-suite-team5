/*********************************************************************************************
 * Copyright (c) 2013 WPI Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team _
 *  
 *********************************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller;

import edu.wpi.cs.wpisuitetng.modules.calendar.categoryobserver.UpdateCategoryRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user decides to update an event
 * on the calendar, specifically by modifying the category field of
 * that particular event.
 * @version $Revision: 1.0 $
 * @author Team Underscore
 */
public class UpdateCategoryController {
	
	private static UpdateCategoryController instance = null;
	private final UpdateCategoryRequestObserver observer;
	
	/**
	 * Construct an UpdateCategoryController for the given model
	 */
	private UpdateCategoryController() {
		observer = new UpdateCategoryRequestObserver( this );
	}
	
	/**
	 * @return the instance of the UpdateCategoryController or creates one if it does not
	 * exist. 
	 */
	public static UpdateCategoryController getInstance() {
		
		if( instance == null )
		{
			instance = new UpdateCategoryController();
		}
		
		return instance;
	}
	
	/**
	 * This method updates a Category to the server.
	 * @param newCategory is the Category to be updated to the server.
	 */
	public void updateCategory(Category newCategory) 
	{
		final Request request = Network.getInstance().makeRequest( "calendar/category",
				              HttpMethod.POST); // POST == update // $codepro.audit.disable codeInComments // $codepro.audit.disable codeInComments
		request.setBody( newCategory.toJSON() ); // put the new Event in the body of the request
		request.addObserver( observer ); // add an observer to process the response
		request.send(); 
	}
}
