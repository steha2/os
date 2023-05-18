package com.trickle.os.cr.gui.panel.layout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.trickle.os.cr.gui.panel.CustomPanel;

public class GridBagPanel extends CustomPanel{
	public GridBagConstraints gbc = new GridBagConstraints();
	private int maxgridx;
	
	{ rootPanel.setLayout(new GridBagLayout()); alignLeft(); }

	public GridBagPanel() {}
	
	public GridBagPanel(int width, int height) {
		setSize(width, height);
	}
	
	public void alignLeft() { gbc.anchor = GridBagConstraints.WEST; }
	public void alignCenter() { gbc.anchor = GridBagConstraints.CENTER; }
	public void alignRight() { gbc.anchor = GridBagConstraints.EAST; }
	
	public void add(CustomPanel panel, int gridx, int gridy, int weightx, int weighty) {
		add(panel.getPanel(), gridx, gridy, weightx, weighty);
	}
	
	public void add(JComponent comp, int gridx, int gridy, int weightx, int weighty) {
		gbc.gridx = gridx;
		if(gridx > maxgridx) maxgridx = gridx;
		
		gbc.gridy = gridy;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		rootPanel.add(comp, gbc);
	}
	
	public void add(CustomPanel panel, int gridx, int gridy) {
		add(panel, gridx, gridy, 1, 1);
	}
	
	public void add(JComponent comp, int gridx, int gridy) {
		add(comp, gridx, gridy, 1, 1);
	}
	
	public JPanel newPanel(int row, int weighty) {
		JPanel panel = new JPanel();
		add(panel, 1, row, 1, weighty);
		return panel;
	}
	
	public JPanel newPanel() {
		return newPanel(++gbc.gridx, 1);
	}
	
	public void addNextRow(CustomPanel panel) {
		addNextRow(panel.getPanel());
	}
	
	public void addNextRow(JComponent comp) {
		gbc.gridwidth = maxgridx;
		add(comp, 1, ++gbc.gridy, 1, 1);
	}
	
	public void removeAll() {
		super.removeAll();
		gbc = new GridBagConstraints();
	}
}
