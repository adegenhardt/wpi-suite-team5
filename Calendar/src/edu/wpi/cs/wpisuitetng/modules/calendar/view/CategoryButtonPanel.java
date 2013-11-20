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
/**
 *  * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 */

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * 
 * Create the event buttons
 */
public class CategoryButtonPanel extends ToolbarGroupView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	final JButton createCancelButton = new JButton("<html>Cancel<br />Changes</html>");
	JButton createCategoryButton;

	public void disableCreateCategoryButton() {
		createCategoryButton.setEnabled(false);
	}
	
	public void enableCreateCategoryButton() {
		createCategoryButton.setEnabled(true);
	}

	public CategoryButtonPanel(){
		super("");
		
		createCategoryButton = new JButton("<html>Create <br/>Category</html>");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(350);
		
		createCategoryButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		createCategoryButton.setVisible(true);
		
		// the action listener for the Create Event Button
		createCategoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		contentPanel.add(createCategoryButton);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
	}
	
	/**
	 * Method getCreateButton.
	
	 * @return JButton */
	public JButton getCreateEventButton() {
		return createCategoryButton;
	}
}
