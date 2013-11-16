package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JToolBar;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.calendar.toolbar.EventButtonsPanel;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ToolbarView extends DefaultToolbarView {
	
	/** The panel containing toolbar buttons */
	private final ToolbarPanel toolbarPanel;
	
	public EventButtonsPanel eventButton = new EventButtonsPanel();


	/**
	 * Construct this view and all components in it.
	 */
	public ToolbarView() {
		eventButton.getCreateEventButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final JDialog eventWindow  = new JDialog();
				final EventEditor eventWindowContent = new EventEditor(); 
				eventWindow.setContentPane(eventWindowContent);
				eventWindow.setBounds(0, 0, 680, 480);
				eventWindow.setLocationRelativeTo(null);
				eventWindow.setTitle("Create Event");
				eventWindow.setVisible(true);
			}
		});
		
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
