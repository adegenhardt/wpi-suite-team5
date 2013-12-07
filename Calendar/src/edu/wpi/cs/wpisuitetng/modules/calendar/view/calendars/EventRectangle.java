/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team _
 *    
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.entry.Event;

/**
 * 
 * @author Team Underscore
 * @version 1.0
 *
 */
public class EventRectangle {
	
	private Event event;
	private int x;
	private int y;
	private int width;
	private int height;
	
	/**
	 * 
	 * @param _event The event stored for this rectangle
	 * @param _x X position
	 * @param _y Y position
	 * @param _width Width of the rectangle 
	 * @param _height Height of the rectangle
	 */
	public EventRectangle(Event _event, int _x, int _y, int _width, int _height) {
		event = _event;
		x = _x;
		y = _y;
		width = _width;
		height = _height;
	}
	
	/**
	 * 
	 * @param _x The x of the point you want to check
	 * @param _y The y of the point you want to check
	 * @return True if the point lies within this EventRectangle, False otherwise
	 */
	public boolean isAtPoint(int _x, int _y) {
		return  ((_x >= x) && (_x <= x + width)) && ((_y >= y) && (_y <= y + height));
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
