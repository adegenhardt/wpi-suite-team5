package edu.wpi.cs.wpisuitetng.modules.calendar.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

public class ClosableTabCreator {
	
	// Going to need a prettier icon
	// private static final Icon CLOSE_TAB_ICON = new ImageIcon(ClosableTabCreator.class.getResource("placeholder.png"));
	private JTabbedPane tabbedPane;

	public ClosableTabCreator(JTabbedPane _tabbedPane) {
		tabbedPane = _tabbedPane;
	}

	public void addClosableTab(final JComponent c, final String title) {
		// Add the tab to the pane without any label
		tabbedPane.addTab(null, c);
		int pos = tabbedPane.indexOfComponent(c);

		// Create a FlowLayout that will space things 5px apart
		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);

		// Make a small JPanel with the layout and make it non-opaque
		JPanel pnlTab = new JPanel(f);
		pnlTab.setOpaque(false);

		// Add a JLabel with title
		JLabel lblTitle = new JLabel(title);

		// Create a JButton for the close tab button
		JButton btnClose = new JButton("\u2716");
		btnClose.setOpaque(false);

		// Configure icon and rollover icon for button
		btnClose.setRolloverEnabled(true);
		btnClose.setPreferredSize(new Dimension(11, 15));
		// btnClose.setIcon(CLOSE_TAB_ICON);

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
		ActionListener listener = new ActionListener() {
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

}
