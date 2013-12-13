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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;







/**
 * @author Team Underscore
 * @version $Revision: 1.0$
 * Creates the category editor
 */
public class CategoryEditor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField textFieldCat;
	private final JTextField textFieldExistCat;

	/**
	 * Create the panel.
	 */
	public CategoryEditor() {
		setLayout(new MigLayout("", "[][grow]", "[][][][][][]"));
		
		final JLabel lblNewCategory = new JLabel("New Category:");
		add(lblNewCategory, "cell 0 0,alignx right");
		
		textFieldCat = new JTextField();
		add(textFieldCat, "cell 1 0,growx");
		textFieldCat.setColumns(10);
		
		final JButton btnSubmitNew = new JButton("Submit");
		add(btnSubmitNew, "cell 1 1");
		
		final JLabel lblCurrentCategories = new JLabel("Current Categories:");
		add(lblCurrentCategories, "cell 0 3,alignx right");
		
		final JComboBox<String> comboBoxCats = new JComboBox<String>();
		add(comboBoxCats, "cell 1 3,growx");
		
		final JLabel lblEditCategory = new JLabel("Edit Category:");
		add(lblEditCategory, "cell 0 4,alignx trailing");
		
		textFieldExistCat = new JTextField();
		add(textFieldExistCat, "cell 1 4,growx");
		textFieldExistCat.setColumns(10);
		
		final JButton btnSubmitEdit = new JButton("Submit");
		add(btnSubmitEdit, "cell 1 5");
	}
	
	/**
	 * 
	 * @author Team_
	 * @version 1.0
	 *
	 */
	class CatSubmitButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//TODO add functionality to build a Category from data and add to data base
		}
	}
}