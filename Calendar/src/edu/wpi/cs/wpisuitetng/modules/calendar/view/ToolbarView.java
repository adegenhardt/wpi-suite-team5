package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JToolBar;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.calendar.toolbar.EventButtonsPanel;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class ToolbarView extends DefaultToolbarView {
	
	/** The panel containing toolbar buttons */
	private final ToolbarPanel toolbarPanel;
	
	public EventButtonsPanel eventButton = new EventButtonsPanel();


	/**
	 * Construct this view and all components in it.
	 */
	public ToolbarView() {
		
		this.addGroup(eventButton);
		
		// Prevent this toolbar from being moved
		setFloatable(false);
		
		// Add the panel containing the toolbar buttons
		toolbarPanel = new ToolbarPanel();
		add(toolbarPanel);
	
	}
	
	/**
	 * Method getEditButton.
	
	 * @return EditButtonsPanel */
	public EventButtonsPanel getEventButton(){
		return eventButton;
	}
	
}
