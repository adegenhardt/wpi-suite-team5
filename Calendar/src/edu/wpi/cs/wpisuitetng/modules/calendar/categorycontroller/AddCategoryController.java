/*********************************************************************************************
 * Copyright (c) 2013 WPI Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *********************************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller;

import edu.wpi.cs.wpisuitetng.modules.calendar.categoryobserver.AddCategoryRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user creates a new category,
 * for a new event within a calendar.
 * @version $Revision: 1.0 $
 * @author awitt
 */
public class AddCategoryController {
	
	private static AddCategoryController instance = null;
	private AddCategoryRequestObserver observer = null;
	
	/**
	 * Construct an AddCategoryController for the given model
	 */
	private AddCategoryController() {
		observer = new AddCategoryRequestObserver( this );
	}
	
	/**
	 * @return the instance of the AddCategoryController or
	 * creates one if it does not exist.
	 */
	public static AddCategoryController getInstance()
	{
		if( instance == null )
		{
			instance = new AddCategoryController();
		}
		
		return instance;
	}
	
	/**
	 * This method adds a Category to the server.
	 * @param newCategory is the Category to be added to the server.
	 */
	public void addCategory( Category newCategory ) 
	{
		// PUT == create // $codepro.audit.disable codeInComments
		final Request request =
				Network.getInstance().makeRequest( "calendar/category", HttpMethod.PUT );
		request.setBody( newCategory.toJSON() ); // put the new Event in the body of request		
		request.addObserver( observer ); // add an observer to process the response
		request.send(); 
	}
	
	/**
	 * When the new category is received back from the server, add it to the local model.
	 * @param category the category successfully added on the server
	 */
	public void addMessageToModel( Category category ) {
		CategoryModel.getInstance().addCategory( category );
	}
}
	