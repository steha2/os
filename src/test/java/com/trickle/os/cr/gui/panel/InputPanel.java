package com.trickle.os.cr.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

import com.trickle.os.cr.gui.panel.input.InputComponent;

public abstract class InputPanel extends CustomPanel implements InputComponent{
	protected JLabel label;
	protected JComponent inputComp;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public void setLabelText(String text) {
		label.setText(text);
	}
	
	public void setLabelOption(Font font, Color fgColor, Dimension size, Border border) {
		if(font!=null)label.setFont(font);
		if(fgColor!=null)label.setForeground(fgColor);
		if(border!=null)label.setBorder(border);
		if(size!=null)label.setPreferredSize(size);
	}
	
	public void setFont(Font font) {
		label.setFont(font);
	}
	
	public JComponent getInput() {
		return inputComp;
	}
}
