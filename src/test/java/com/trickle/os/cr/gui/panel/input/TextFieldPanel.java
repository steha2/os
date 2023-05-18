package com.trickle.os.cr.gui.panel.input;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.trickle.os.cr.gui.panel.InputPanel;

public class TextFieldPanel extends InputPanel{
	private JTextField textField = new JTextField();
	
	{ 
		rootPanel.setLayout(new BorderLayout());
		inputComp = textField;
	}

	public TextFieldPanel(String name) {
		this(name, 10, null, null);
	}
	
	public TextFieldPanel(String name, int columns) {
		this(name, columns, null, null);
	}
	
	public TextFieldPanel(String name, String labelText) {
		this(name, 10, labelText, BorderLayout.WEST);
	}
	
	public TextFieldPanel(String name, int columns, String labelText) {
		this(name, columns, labelText, BorderLayout.WEST);
	}
	
	public TextFieldPanel(String name, int columns, String labelText, String labelDirection) {
		setName(name);
		label = new JLabel(labelText);
		textField.setColumns(columns);
		label.setPreferredSize(new Dimension(80, 25));
		rootPanel.add(label, labelDirection);
		rootPanel.add(textField, BorderLayout.CENTER);
	}
	
	public JTextField getTextField() {
		return textField;
	}

	@Override
	public String getValue() {
		return textField.getText();
	}

	@Override
	public void setValue(Object value) {
		if(value == null)
			textField.setText(null);
		else 
			textField.setText(value.toString());
	}
	
	public void reset() {
		textField.setText("");
	}
	
	public void setEditable(boolean editable) {
		textField.setEditable(editable);
	};
}
