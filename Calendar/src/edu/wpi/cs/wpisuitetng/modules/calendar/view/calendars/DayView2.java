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
package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendars;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Team_
 * @version 1.0
 *
 */
public class DayView2 extends JPanel {

	private static final long serialVersionUID = -982401714583847865L;

	/**
	 * Create the panel.
	 */
	public DayView2() {
		setBorder(new LineBorder(Color.black));
		setLayout(new MigLayout("gap 0px 0px", "[43px][2px][393px,grow]", "[25px][2px][15px][15px][15px][15px][15px][15px][15px][15px][][][][][][][][][][][][][][][][]"));
		
		final JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		add(separator_1, "cell 1 0 1 30000,alignx center,growy");
		
		final JLabel lblDate = new JLabel("Date");
		add(lblDate, "cell 2 0,alignx center,aligny center");
		
		final JSeparator separator = new JSeparator();
		add(separator, "cell 2 1,growx,aligny center");
		
		final JLabel lblam = new JLabel("12:00AM");
		add(lblam, "cell 0 2,alignx center,aligny center");
		
		final JLabel twelveAM = new JLabel();
		twelveAM.setOpaque(true);
		twelveAM.setBackground(new Color(255, 255, 255));
		twelveAM.setPreferredSize(new Dimension(0, 15));
		add(twelveAM, "cell 2 2,growx,aligny center");
		
		final JLabel twelveHalfAM = new JLabel();
		twelveHalfAM.setOpaque(true);
		twelveHalfAM.setPreferredSize(new Dimension(0, 15));
		twelveHalfAM.setBackground(new Color(185, 209, 234));
		add(twelveHalfAM, "cell 2 3,growx,aligny center");
		
		final JLabel lblam_1 = new JLabel("1:00AM");
		add(lblam_1, "cell 0 4,alignx center,aligny center");
		
		final JLabel oneAM = new JLabel();
		oneAM.setPreferredSize(new Dimension(0, 15));
		oneAM.setOpaque(true);
		oneAM.setBackground(new Color(255, 255, 255));
		add(oneAM, "cell 2 4,growx,aligny center");
		
		final JLabel oneHalfAM = new JLabel();
		oneHalfAM.setPreferredSize(new Dimension(0, 15));
		oneHalfAM.setOpaque(true);
		oneHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(oneHalfAM, "cell 2 5,growx,aligny center");
		
		final JLabel lblam_2 = new JLabel("2:00AM");
		add(lblam_2, "cell 0 6,alignx center,aligny center");
		
		final JLabel twoAM = new JLabel();
		twoAM.setPreferredSize(new Dimension(0, 15));
		twoAM.setOpaque(true);
		twoAM.setBackground(new Color(255, 255, 255));
		add(twoAM, "cell 2 6,growx,aligny center");
		
		final JLabel twoHalfAM = new JLabel();
		twoHalfAM.setPreferredSize(new Dimension(0, 15));
		twoHalfAM.setOpaque(true);
		twoHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(twoHalfAM, "cell 2 7,growx,aligny center");
		
		final JLabel lblam_3 = new JLabel("3:00AM");
		add(lblam_3, "cell 0 8,alignx center,aligny center");
		
		final JLabel threeAM = new JLabel();
		threeAM.setPreferredSize(new Dimension(0, 15));
		threeAM.setOpaque(true);
		threeAM.setBackground(new Color(255, 255, 255));
		add(threeAM, "cell 2 8,growx,aligny center");
		
		final JLabel threeHalfAM = new JLabel();
		threeHalfAM.setPreferredSize(new Dimension(0, 15));
		threeHalfAM.setOpaque(true);
		threeHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(threeHalfAM, "cell 2 9,growx,aligny center");
		
		final JLabel lblam_4 = new JLabel("4:00AM");
		add(lblam_4, "cell 0 10");
		
		final JLabel fourAM = new JLabel();
		fourAM.setPreferredSize(new Dimension(0, 15));
		fourAM.setOpaque(true);
		fourAM.setBackground(Color.WHITE);
		add(fourAM, "cell 2 10,growx,aligny center");
		
		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(0, 15));
		label.setOpaque(true);
		label.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(label, "cell 2 11,growx,aligny center");
		
		final JLabel lblam_5 = new JLabel("5:00AM");
		add(lblam_5, "cell 0 12");
		
		final JLabel fiveAM = new JLabel();
		fiveAM.setPreferredSize(new Dimension(0, 15));
		fiveAM.setOpaque(true);
		fiveAM.setBackground(Color.WHITE);
		add(fiveAM, "cell 2 12,growx,aligny center");
		
		final JLabel fiveHalfAM = new JLabel();
		fiveHalfAM.setPreferredSize(new Dimension(0, 15));
		fiveHalfAM.setOpaque(true);
		fiveHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(fiveHalfAM, "cell 2 13,growx,aligny center");
		
		final JLabel lblam_6 = new JLabel("6:00AM");
		add(lblam_6, "cell 0 14");
		
		final JLabel sixAM = new JLabel();
		sixAM.setPreferredSize(new Dimension(0, 15));
		sixAM.setOpaque(true);
		sixAM.setBackground(Color.WHITE);
		add(sixAM, "cell 2 14,growx,aligny center");
		
		final JLabel sixHalfAM = new JLabel();
		sixHalfAM.setPreferredSize(new Dimension(0, 15));
		sixHalfAM.setOpaque(true);
		sixHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(sixHalfAM, "cell 2 15,growx,aligny center");
		
		final JLabel lblam_7 = new JLabel("7:00AM");
		add(lblam_7, "cell 0 16");
		
		final JLabel sevenAM = new JLabel();
		sevenAM.setPreferredSize(new Dimension(0, 15));
		sevenAM.setOpaque(true);
		sevenAM.setBackground(Color.WHITE);
		add(sevenAM, "cell 2 16,growx,aligny center");
		
		final JLabel sevenHalfAM = new JLabel();
		sevenHalfAM.setPreferredSize(new Dimension(0, 15));
		sevenHalfAM.setOpaque(true);
		sevenHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(sevenHalfAM, "cell 2 17,growx,aligny center");
		
		final JLabel lblam_8 = new JLabel("8:00AM");
		add(lblam_8, "cell 0 18");
		
		final JLabel eightAM = new JLabel();
		eightAM.setPreferredSize(new Dimension(0, 15));
		eightAM.setOpaque(true);
		eightAM.setBackground(Color.WHITE);
		add(eightAM, "cell 2 18,growx,aligny center");
		
		final JLabel eightHalfAM = new JLabel();
		eightHalfAM.setPreferredSize(new Dimension(0, 15));
		eightHalfAM.setOpaque(true);
		eightHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(eightHalfAM, "cell 2 19,growx,aligny center");
		
		final JLabel lblam_9 = new JLabel("9:00AM");
		add(lblam_9, "cell 0 20");
		
		final JLabel nineAM = new JLabel();
		nineAM.setPreferredSize(new Dimension(0, 15));
		nineAM.setOpaque(true);
		nineAM.setBackground(Color.WHITE);
		add(nineAM, "cell 2 20,growx,aligny center");
		
		final JLabel nineHalfAM = new JLabel();
		nineHalfAM.setPreferredSize(new Dimension(0, 15));
		nineHalfAM.setOpaque(true);
		nineHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(nineHalfAM, "cell 2 21,growx,aligny center");
		
		final JLabel lblam_10 = new JLabel("10:00AM");
		add(lblam_10, "cell 0 22");
		
		final JLabel tenAM = new JLabel();
		tenAM.setPreferredSize(new Dimension(0, 15));
		tenAM.setOpaque(true);
		tenAM.setBackground(Color.WHITE);
		add(tenAM, "cell 2 22,growx,aligny center");
		
		final JLabel tenHalfAM = new JLabel();
		tenHalfAM.setPreferredSize(new Dimension(0, 15));
		tenHalfAM.setOpaque(true);
		tenHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(tenHalfAM, "cell 2 23,growx,aligny center");
		
		final JLabel lblam_11 = new JLabel("11:00AM");
		add(lblam_11, "cell 0 24");
		
		final JLabel elevenAM = new JLabel();
		elevenAM.setPreferredSize(new Dimension(0, 15));
		elevenAM.setOpaque(true);
		elevenAM.setBackground(Color.WHITE);
		add(elevenAM, "cell 2 24,growx,aligny center");
		
		final JLabel elevenHalfAM = new JLabel();
		elevenHalfAM.setPreferredSize(new Dimension(0, 15));
		elevenHalfAM.setOpaque(true);
		elevenHalfAM.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		add(elevenHalfAM, "cell 2 25,growx,aligny center");

	}

}
