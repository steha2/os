package com.trickle.os.cr.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.trickle.os.cr.gui.Gui;

@SuppressWarnings("serial")
public class RoundPanel extends JPanel{
	private int arcWidth = 100, arcHeight = 100;
	
	public void setting(Color bgColor, int width, int height, int arcWidth, int arcHeight) {
		setBackground(bgColor);
		setPreferredSize(new Dimension(width, height));
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
	}
	
	protected void paintComponent(Graphics g) {
	    int width = getWidth();
	    int height = getHeight();

	    Graphics2D graphics = (Graphics2D) g;
	    graphics.getBackground();
	    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    graphics.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);
	    graphics.dispose();
	    super.paintComponent(g);
	}
	public static void main(String[] args) {
		RoundPanel  p =  new RoundPanel() ;
		p.setPreferredSize(new Dimension(400, 100));
		Gui.createFrame(p);
	}
}

