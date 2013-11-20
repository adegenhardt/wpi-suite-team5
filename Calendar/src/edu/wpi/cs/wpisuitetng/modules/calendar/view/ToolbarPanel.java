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
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JPanel;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Creates the Toolbar panel
 */
@SuppressWarnings("serial")
public class ToolbarPanel extends JPanel {

	
	/**
	 * Construct the panel.
	 */
	public ToolbarPanel() {
		
		// Make this panel transparent, we want to see the JToolbar gradient beneath it
		this.setOpaque(false);
		
	}
}