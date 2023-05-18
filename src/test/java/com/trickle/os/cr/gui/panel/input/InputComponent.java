package com.trickle.os.cr.gui.panel.input;

import javax.swing.JPanel;

public interface InputComponent {
	Object getValue();
	void setValue(Object value);
	void reset();
	String getName();
	void setName(String name);
	JPanel getPanel();
	void setEditable(boolean editable);
}