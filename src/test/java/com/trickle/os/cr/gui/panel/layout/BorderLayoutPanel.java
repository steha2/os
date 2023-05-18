package com.trickle.os.cr.gui.panel.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.trickle.os.cr.gui.panel.CustomPanel;

public class BorderLayoutPanel extends CustomPanel{
	{ rootPanel.setLayout(new BorderLayout()); }
	
	public void addNorth(CustomPanel panel) {
		addNorth(panel.getPanel());
	}
	
	public void addWest(CustomPanel panel) {
		addWest(panel.getPanel());
	}
	
	public void addCenter(CustomPanel panel) {
		addCenter(panel.getPanel());
	}
	
	public void addEast(CustomPanel panel) {
		addEast(panel.getPanel());
	}
	
	public void addSouth(CustomPanel panel) {
		addSouth(panel.getPanel());
	}
	
	public void addNorth(JComponent comp) {
		rootPanel.add(comp, BorderLayout.NORTH);
	}
	
	public void addWest(JComponent comp) {
		rootPanel.add(comp, BorderLayout.WEST);
	}
	
	public void addCenter(JComponent comp) {
		rootPanel.add(comp, BorderLayout.CENTER);
	}
	
	public void addEast(JComponent comp) {
		rootPanel.add(comp, BorderLayout.EAST);
	}
	
	public void addSouth(JComponent comp) {
		rootPanel.add(comp, BorderLayout.SOUTH);
	}
	
	public void setBackgrounds(Color color) {
		rootPanel.setBackground(color);
		for(Component comp : rootPanel.getComponents()) {
			comp.setBackground(color);
		}
	}
	
	public JPanel newPanel(String direction, int width, int height) {
		return newPanel(direction, new Dimension(width, height), 0);
	}
	
	public JPanel newPanel(String direction, int alignment) {
		return newPanel(direction, null, alignment);
	}
	
	public JPanel newPanel(String direction, Dimension dim, int alignment) {
		JPanel panel = new JPanel(new FlowLayout(alignment, 0, 0));
		if(dim != null) panel.setPreferredSize(dim);
		rootPanel.add(panel, direction);
		return panel;
	}
	
	public JScrollPane newScroll(CustomPanel customPanel, String direction) {
		return newScroll(customPanel.getPanel(), direction);
	}
	
	public JScrollPane newScroll(JComponent comp, String direction) {
		JScrollPane scrollPane = new JScrollPane(comp);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		rootPanel.add(scrollPane, direction);
		return scrollPane;
	}
	
	public JPanel newPanel(String direction) {
		return newPanel(direction, null, 0);
	}
}
