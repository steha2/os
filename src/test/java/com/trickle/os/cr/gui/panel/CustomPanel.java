package com.trickle.os.cr.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class CustomPanel {
	protected JPanel rootPanel = new JPanel();
	
	public void add(JComponent comp) {
		rootPanel.add(comp);
	}
	
	public void add(CustomPanel custom) {
		add(custom.rootPanel);
	}
	
	public void setSize(int width, int height) {
		rootPanel.setPreferredSize(new Dimension(width, height));
	}
	
	public void setLayout(LayoutManager layout) {
		rootPanel.setLayout(layout);
	}
	
	public JPanel getPanel() {
		return rootPanel;
	}
	
	public CustomPanel setBackground(Color bgColor) {
		rootPanel.setBackground(bgColor);
		return this;
	}
	
	public Color getBackground() {
		return rootPanel.getBackground();
	}
	
	public void removeAll() {
		rootPanel.removeAll();
	}
	
	public void setBorder(Border border) {
		rootPanel.setBorder(border);
	}
	
	public void add(JComponent comp, Object constraints) {
		rootPanel.add(comp, constraints);
	}
}
