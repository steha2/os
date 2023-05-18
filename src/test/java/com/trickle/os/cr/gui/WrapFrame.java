package com.trickle.os.cr.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import com.trickle.os.cr.gui.panel.CustomPanel;

public class WrapFrame implements Runnable {
	private JPanel rootPanel = new JPanel(new BorderLayout());
	private JFrame rootFrame = new JFrame();
	private float alpha = 0;
	private float changeRate = 0.07f;
	private float maxAlpha = 0.5f;
	private long waitMilliseconds= 150;
	private boolean dispose = true;
	private JComponent parent;
	
	public static final Font DEFAULT_FONT = new Font("맑은 고딕", Font.PLAIN, 20);
	
	public WrapFrame(JComponent parent, JComponent comp) {
		if(comp != null) 
			rootPanel.add(comp);
		this.parent = parent;
	}
	
	public WrapFrame(JComponent parent) {
		this(parent, null);
	}
	
	{
		rootFrame.setUndecorated(true);
		rootFrame.setAlwaysOnTop(true);
		rootFrame.setOpacity(0f);
		rootFrame.setContentPane(rootPanel);
	}
	
	public void setProperties(float alpha, float changeRate, float maxAlpha, long waitMilliseconds) {
		if(alpha >= 0) this.alpha = alpha;
		if(changeRate > 0) this.changeRate = changeRate;
		if(maxAlpha > 0 && maxAlpha <= 1) this.maxAlpha = maxAlpha;
		if(waitMilliseconds > 0) this.waitMilliseconds = waitMilliseconds;
	}
	
	public void setPanelColor(Color color) {
		rootPanel.setBackground(color);
	}
	
	public JFrame getFrame() {
		return rootFrame; 
	}
	
	public void start() {
		start(0, 0, 0, 0);
	}
	
	public void start(int dx, int dy, int sx, int sy) {
		if(!parent.isShowing()) return;
		Point p = parent.getLocationOnScreen();
		if(dx != 0) p.x = dx;
		if(dx != 0) p.y = dy;
		rootFrame.setLocation(p);
		Dimension dimension = parent.getSize();
		if(sx != 0) dimension.width = sx;
		if(sy != 0) dimension.height = sy;
		rootFrame.setSize(dimension);
		rootFrame.setVisible(true);
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(33);
			alpha += changeRate;
			if(alpha > maxAlpha) {
				alpha = maxAlpha;
				changeRate *= -1;
				Thread.sleep(waitMilliseconds);
			}else if(alpha < 0) {
				break;
			}
			if(alpha >=0 && alpha <= 1) rootFrame.setOpacity(alpha);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(dispose)
			rootFrame.dispose();
		else
			rootFrame.setVisible(false);
	}
	
	public void setDispose(boolean dispose) {
		this.dispose = dispose;
	}

	
	public static void alert(JComponent... parents) {
		alert(null, null, null, null, parents);
	}
	
	public static void alert(String msg, JComponent... parents) {
		alert(msg, null, null, null, parents);
	}
	
	public static void alert(String msg, Font font, JComponent... parents) {
		alert(msg, null, null, font, parents);
	}
	
	public static void alert(String msg, Color bgColor, Color fgColor, JComponent... parents) {
		alert(msg, bgColor, fgColor, null, parents);
	}
	
	public static void alert(String msg, Color bgColor, Color fgColor, Font font, JComponent... parents) {
		for(JComponent parent : parents) {
			WrapFrame frame = new WrapFrame(parent);
			if(msg != null) {
				JLabel label = new JLabel(msg);
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setForeground(fgColor == null ? Color.YELLOW : fgColor);
				if(font == null) 
					font = Gui.font(40);
				label.setFont(font);
				frame.getFrame().getContentPane().add(label);
			}
			frame.setPanelColor(bgColor == null ? Color.RED : bgColor);
			frame.setProperties(0, 0.12f, 0.7f, 170);
			frame.start();	
		}
	}
	
	public static void mouseTooltip(CustomPanel customPanel, String text) {
		mouseTooltip(customPanel.getPanel(), text, -1, -1, null, -1);
	}
	
	public static void mouseTooltip(JComponent parent, String text) {
		mouseTooltip(parent, text, -1, -1, null, -1);
	}
	
	public static void mouseTooltip(JComponent parent, String text, int width, int height, Font font, int waitMS) {
		MouseAdapter adapter = new MouseAdapter() {
			Timer timer;
			WrapFrame frame = new WrapFrame(parent);
			MouseEvent me;
			{
		        timer = new Timer(1000, e->{
		        	if(frame.getFrame().isVisible()) return;
					frame.setProperties(0, 0.1f, 1f, waitMS < 0 ? 1200 : waitMS);
					frame.start(me.getLocationOnScreen().x, me.getLocationOnScreen().y + 30,
						width < 0 ? parent.getWidth() : width, height < 0 ? parent.getHeight() : height);
		        });
		        timer.setRepeats(false);
		        
				JPanel panel = new JPanel(new BorderLayout());
				frame.getFrame().setContentPane(panel);
				JLabel label = new JLabel(text);
				panel.add(label, BorderLayout.CENTER);
				label.setHorizontalAlignment(JLabel.LEFT);
				label.setFont(font == null ? Gui.font(23) : font);
				label.setForeground(Color.BLACK);
				panel.setBorder(new LineBorder(Color.DARK_GRAY, 1));
				frame.getFrame().pack();
			}
			public void mouseEntered(MouseEvent e) { timer.start(); }
			public void mouseExited(MouseEvent e) { timer.stop(); }
			public void mouseMoved(MouseEvent e) { me = e; }
		};
		parent.addMouseListener(adapter);
		parent.addMouseMotionListener(adapter);
	}
	
	public static void greenAlert(JComponent parent) {
		greenAlert(null, parent, null);
	}
	
	public static void greenAlert(CustomPanel customPanel) {
		greenAlert(null, customPanel.getPanel(), null);
	}
	
	public static void greenAlert(String msg, CustomPanel customPanel) {
		greenAlert(msg, customPanel.getPanel(), null);
	}

	
	public static void greenAlert(String msg, JComponent parent) {
		greenAlert(msg, parent, null);
	}
	
	public static void greenAlert(String msg, JComponent parent, Font font) {
		greenAlert(msg, parent, font, -1, -1);
	}
	
	public static void greenAlert(String msg, JComponent parent, Font font, int inWidth, int inHeight) {
		WrapFrame frame = new WrapFrame(parent);
		Color outColor = new Color(245, 255, 240);
		frame.getFrame().getContentPane().setLayout(null);
		JPanel inPanel = new JPanel(new FlowLayout(1,7,7));
		inPanel.setBackground(new Color(220, 255, 220));
		Dimension size = parent.getSize();
		if(msg == null) msg = "";
		if(inWidth == -1) inWidth = msg.getBytes().length * 20;
		if(inHeight == -1) inHeight = 70;
		JLabel iconLabel = Gui.createIconLabel(Gui.IMG_PATH+"success.png");
		if(size.width < 50 || size.height < 50)
			iconLabel.setIcon(Gui.getResizedIcon(Gui.IMG_PATH+"success.png", Math.min(size.width, size.height), Math.min(size.width, size.height)));
		inPanel.add(iconLabel);
		if(msg != null && !msg.isEmpty()) {
			inPanel.setBorder(new LineBorder(new Color(70,150,70), 3));
			JLabel label = new JLabel(msg);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setForeground(new Color(0, 123, 0));
			if(font == null) font = Gui.font(40);
			label.setFont(font);
			inPanel.add(label);
		} else {
			inWidth = 70;
			inPanel.setBackground(outColor);
		}
		inPanel.setBounds(size.width/2-inWidth/2, size.height/2 - inHeight/2 , inWidth, inHeight);
		frame.getFrame().getContentPane().add(inPanel, BorderLayout.CENTER);
		frame.setPanelColor(outColor);
		frame.setProperties(0, 0.16f, 0.9f, 230);
		frame.start();
	}
}
