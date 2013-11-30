/*********************************************************************************************
 * Copyright (c) 2013 WPI Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *********************************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.categoryobserver;

import edu.wpi.cs.wpisuitetng.modules.calendar.categorycontroller.GetCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer handles responses to requests for all Category instance
 *
 * @version $Revision: 1.0 $
 * @author awitt
 */
public class GetCategoryRequestObserver implements RequestObserver {

	private GetCategoryController controller;
	
	/**
	 * Constructs the observer given a GetCategoryController
	 * @param controller the controller used to retrieve Categories
	 */
	public GetCategoryRequestObserver(GetCategoryController controller) {
		this.controller = controller;
	}
	
	/**
	 * Parse the Categories out of the response body and pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Convert the JSON array of Categories to a Category object array
		Category[] calendarData = Category.fromJsonArray(iReq.getResponse().getBody());
		
		// Pass these Categories to the controller
		controller.receivedCategory( calendarData );
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}
	
	/**
	 * Put an error Category in the PostBoardPanel if the request fails.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		//TODO: generate an error form of Category
		Category[] errorCategory = { new Category() };
		controller.receivedCategory(errorCategory);
	}
}
