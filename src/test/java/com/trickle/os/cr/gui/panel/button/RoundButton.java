package com.trickle.os.cr.gui.panel.button;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;

import com.trickle.os.cr.gui.Gui;

@SuppressWarnings("serial")
public class RoundButton extends JButton {
	private int arc;
	
	public RoundButton() {
		this("", 15);
    }
    
    public RoundButton(String text) {
    	this(text, 15);
    }
    
    public RoundButton(String text, int arc) {
        super(text);
        this.arc = arc;
        decorate();
    }

    protected void decorate() {
        setBorderPainted(false);
        setOpaque(false);
    }
    
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            graphics.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            graphics.setColor(getBackground().brighter());
        } else {
            graphics.setColor(getBackground());
        }

        graphics.fillRoundRect(0, 0, width, height, arc, arc);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();

        int textX = (width - stringBounds.width) / 2;
        int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();

        graphics.setColor(getForeground());
        graphics.setFont(getFont());
        graphics.drawString(getText(), textX, textY);
        graphics.dispose();

        super.paintComponent(g);
    }
    
    public static void main(String[] args) {
    	JButton b= new RoundButton("n1.png", 14);
    	b.setFont(Gui.font(20));
    	b.setForeground(Color.white);
    	b.setBackground(new Color(10,20,60));
		Gui.createFrame(b);
	}
}

