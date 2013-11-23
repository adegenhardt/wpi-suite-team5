package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.UIManager;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class DayView2 extends JPanel {

	/**
	 * Create the panel.
	 */
	public DayView2() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new MigLayout("gap 0px 0px", "[43px][2px][393px,grow]", "[25px][2px][15px][15px][15px][15px][15px][15px][15px][15px]"));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		add(separator_1, "cell 1 0 1 2097051,alignx center,growy");
		
		JLabel lblDate = new JLabel("Date");
		add(lblDate, "cell 2 0,alignx center,aligny center");
		
		JSeparator separator = new JSeparator();
		add(separator, "cell 2 1,growx,aligny center");
		
		JLabel lblam = new JLabel("12:00AM");
		add(lblam, "cell 0 2,alignx center,aligny center");
		
		JLabel twelveAM = new JLabel();
		twelveAM.setOpaque(true);
		twelveAM.setBackground(new Color(255, 255, 255));
		twelveAM.setPreferredSize(new Dimension(0, 15));
		add(twelveAM, "cell 2 2,growx,aligny center");
		
		JLabel twelveHalfAM = new JLabel();
		twelveHalfAM.setOpaque(true);
		twelveHalfAM.setPreferredSize(new Dimension(0, 15));
		twelveHalfAM.setBackground(new Color(185, 209, 234));
		add(twelveHalfAM, "cell 2 3,growx,aligny center");
		
		JLabel lblam_1 = new JLabel("1:00AM");
		add(lblam_1, "cell 0 4,alignx center,aligny center");
		
		JLabel oneAM = new JLabel();
		oneAM.setPreferredSize(new Dimension(0, 15));
		oneAM.setOpaque(true);
		oneAM.setBackground(new Color(255, 255, 255));
		add(oneAM, "cell 2 4,growx,aligny center");
		
		JLabel oneHalfAM = new JLabel();
		oneHalfAM.setPreferredSize(new Dimension(0, 15));
		oneHalfAM.setOpaque(true);
		oneHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(oneHalfAM, "cell 2 5,growx,aligny center");
		
		JLabel lblam_2 = new JLabel("2:00AM");
		add(lblam_2, "cell 0 6,alignx center,aligny center");
		
		JLabel twoAM = new JLabel();
		twoAM.setPreferredSize(new Dimension(0, 15));
		twoAM.setOpaque(true);
		twoAM.setBackground(new Color(255, 255, 255));
		add(twoAM, "cell 2 6,growx,aligny center");
		
		JLabel twoHalfAM = new JLabel();
		twoHalfAM.setPreferredSize(new Dimension(0, 15));
		twoHalfAM.setOpaque(true);
		twoHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(twoHalfAM, "cell 2 7,growx,aligny center");
		
		JLabel lblam_3 = new JLabel("3:00AM");
		add(lblam_3, "cell 0 8,alignx center,aligny center");
		
		JLabel threeAM = new JLabel();
		threeAM.setPreferredSize(new Dimension(0, 15));
		threeAM.setOpaque(true);
		threeAM.setBackground(new Color(255, 255, 255));
		add(threeAM, "cell 2 8,growx,aligny center");
		
		JLabel threeHalfAM = new JLabel();
		threeHalfAM.setPreferredSize(new Dimension(0, 15));
		threeHalfAM.setOpaque(true);
		threeHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(threeHalfAM, "cell 2 9,growx,aligny center");

	}

}
