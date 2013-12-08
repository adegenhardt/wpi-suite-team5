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

package edu.wpi.cs.wpisuitetng.modules.calendar.categoryobserver;

import edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller.AddCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.CategoryModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to add an instance of Category.
 *
 * @version $Revision: 1.0 $
 * @author Team Underscore
 */
public class AddCategoryRequestObserver implements RequestObserver {

	private final AddCategoryController controller;
	
	/**
	 * Constructs the observer given an AddCategoryController
	 * @param controller the controller used to add Categories
	 */
	public AddCategoryRequestObserver( AddCategoryController controller ) {
		this.controller = controller;
	}

	/**
	 * Parse the Categories that were received from the server, then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the Category out of the response body
		final Category category = Category.fromJson( response.getBody() );
		
		// Pass the Category back to the controller
		CategoryModel.getInstance().addCategory( category );
	}
	
	/**
	 * Takes an action if the response results in an error.
	 * Specifically, outputs that the request failed.
	 * @param iReq IRequest
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(IRequest) 
	 */
	@Override //
	public void responseError( IRequest iReq ) {
		System.err.println( "The request to add a Category failed." );
	}
	
	/**
	 * Takes an action if the response fails.
	 * Specifically, outputs that the request failed.
	 * @param iReq IRequest
	 * @param exception Exception
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest, Exception) 
	 */
	@Override
	public void fail( IRequest iReq, Exception exception ) {
		System.err.println( "The request to add a Category failed." );
	}
	
}
	