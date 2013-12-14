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
package edu.wpi.cs.wpisuitetng.modules.calendar.view.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Creates closable JPanel tabs
 * @author Team_
 * @version 1.0
 */
public class ClosableTabCreator {

	private final JTabbedPane tabbedPane;
	
	private static ClosableTabCreator tabCreatorInstance = null;
	
	/**
	 * 
	 * @param _tabbedPane
	 * @return instance
	 */
	public static ClosableTabCreator getInstance(JTabbedPane _tabbedPane){
		if(tabCreatorInstance==null){
			tabCreatorInstance = new ClosableTabCreator(_tabbedPane);
		}
		return tabCreatorInstance;
	}

	/**
	 * @param _tabbedPane
	 */
	private ClosableTabCreator(JTabbedPane _tabbedPane) {
		tabbedPane = _tabbedPane;
	}

	/**
	 * @param c
	 * @param title
	 */
	public void addClosableTab(final JComponent c, final String title) {
		// Add the tab to the pane without any label
		tabbedPane.addTab(null, c);
		final int pos = tabbedPane.indexOfComponent(c);

		// Create a FlowLayout that will space things 5px apart
		final FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);

		// Make a small JPanel with the layout and make it non-opaque
		final JPanel pnlTab = new JPanel(f);
		pnlTab.setOpaque(false);

		// Add a JLabel with title
		final JLabel lblTitle = new JLabel(title);

		// Create a JButton for the close tab button
		final JButton btnClose = new JButton("\u2716");
		btnClose.setOpaque(false);

		// Configure icon and rollover stuff for later
		btnClose.setRolloverEnabled(true);
		btnClose.setPreferredSize(new Dimension(11, 15));

		// Set border null so the button doesn't make the tab too big
		btnClose.setBorder(BorderFactory.createLineBorder(Color.black));
		
		btnClose.setFont(btnClose.getFont().deriveFont((float) 8));
		btnClose.setMargin(new Insets(0, 0, 0, 0));
		// Make sure the button can't get focus, otherwise it looks funny
		btnClose.setFocusable(false);

		// Put the panel together
		pnlTab.add(lblTitle);
		pnlTab.add(btnClose);

		// Add a thin border to keep the image below the top edge of the tab
		// when the tab is selected
		pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

		// Now assign the component for the tab
		tabbedPane.setTabComponentAt(pos, pnlTab);

		// Add the listener that removes the tab
		final ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// The component parameter must be declared "final" so that it
				// can be
				// referenced in the anonymous listener class like this.
				tabbedPane.remove(c);
			}
		};
		btnClose.addActionListener(listener);

		// Bring created tab to view
		tabbedPane.setSelectedComponent(c);
	}

	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
}
