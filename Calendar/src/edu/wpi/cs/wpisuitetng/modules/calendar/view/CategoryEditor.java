package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class CategoryEditor extends JPanel {
	private JTextField textFieldCat;
	private JTextField textFieldExistCat;

	/**
	 * Create the panel.
	 */
	public CategoryEditor() {
		setLayout(new MigLayout("", "[][grow]", "[][][][][][]"));
		
		JLabel lblNewCategory = new JLabel("New Category:");
		add(lblNewCategory, "cell 0 0,alignx right");
		
		textFieldCat = new JTextField();
		add(textFieldCat, "cell 1 0,growx");
		textFieldCat.setColumns(10);
		
		JButton btnSubmitNew = new JButton("Submit");
		add(btnSubmitNew, "cell 1 1");
		
		JLabel lblCurrentCategories = new JLabel("Current Categories:");
		add(lblCurrentCategories, "cell 0 3,alignx right");
		
		JComboBox comboBoxCats = new JComboBox();
		add(comboBoxCats, "cell 1 3,growx");
		
		JLabel lblEditCategory = new JLabel("Edit Category:");
		add(lblEditCategory, "cell 0 4,alignx trailing");
		
		textFieldExistCat = new JTextField();
		add(textFieldExistCat, "cell 1 4,growx");
		textFieldExistCat.setColumns(10);
		
		JButton btnSubmitEdit = new JButton("Submit");
		add(btnSubmitEdit, "cell 1 5");

	}

}
