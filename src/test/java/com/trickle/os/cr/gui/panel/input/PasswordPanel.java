package com.trickle.os.cr.gui.panel.input;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

import com.trickle.os.cr.gui.panel.InputPanel;

public class PasswordPanel extends InputPanel{
	private JLabel label;
	private JPasswordField passwordField = new JPasswordField();
	
	{ 
		rootPanel.setLayout(new BorderLayout());
		inputComp = passwordField;
	}

	public PasswordPanel(String name) {
		this(name, 10, null, null);
	}
	
	public PasswordPanel(String name, int columns) {
		this(name, columns, null, null);
	}
	
	public PasswordPanel(String name, String labelText) {
		this(name, 10, labelText, BorderLayout.WEST);
	}
	
	public PasswordPanel(String name, int columns, String labelText) {
		this(name, columns, labelText, BorderLayout.WEST);
	}
	
	public PasswordPanel(String name, int columns, String labelText, String labelDirection) {
		setName(name);
		label = new JLabel(labelText);
		passwordField.setColumns(columns);
		label.setPreferredSize(new Dimension(80, 25));
		rootPanel.add(label, labelDirection);
		rootPanel.add(passwordField, BorderLayout.CENTER);
	}
	
	public void setLabelText(String text) {
		label.setText(text);
	}

	public JLabel getLabel() {
		return label;
	}
	
	public JPasswordField getPasswordField() {
		return passwordField;
	}

	@Override
	public String getValue() {
		return new String(passwordField.getPassword());
	}

	@Override
	public void setValue(Object value) {
		passwordField.setText(value.toString());
	}
	
	public void reset() {
		passwordField.setText("");
	}
	
	public void setEditable(boolean editable) {
		passwordField.setEditable(editable);
	};
}
