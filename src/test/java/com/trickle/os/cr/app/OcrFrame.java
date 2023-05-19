package com.trickle.os.cr.app;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.Border;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.trickle.os.cr.util.entity.WindowInfo;

public class OcrFrame {
	private JDialog frame = new JDialog();
	private WindowResizer windowResizer = new WindowResizer(frame);
	private MouseHooker mouseHooker = new MouseHooker();
	
	private int thickness = 5;
	private Border border1 = BorderFactory.createLineBorder(new Color(255, 0, 0), thickness);
	private Border border2 = BorderFactory.createLineBorder(new Color(0, 255, 0), thickness);
	
	private boolean lock = true;
	private JPanel cp = new JPanel();

	private User32 user32 = User32.INSTANCE;

	private final Color emptyColor = new Color(0,0,0,0); 
	
	public OcrFrame() {
		frame.setUndecorated(true);
		frame.setBackground(emptyColor);
		
		frame.setSize(200, 200);
		
		cp.setBackground(emptyColor);
		cp.setBorder(border1);
		
		frame.setContentPane(cp);
		
//		frame.setMinimumSize(new Dimension(10, 10));
		
		frame.setAlwaysOnTop(true);
		frame.setLocation(100, 200);
		frame.setVisible(true);
		
		cp.addMouseListener(windowResizer);
		cp.addMouseMotionListener(windowResizer);
		frame.addKeyListener(windowResizer);
		
		startMouseHooker();
		toggleLock();
	}

	public JDialog getFrame() {
		return frame;
	}
	
	public Rectangle getMacroBounds() {
		Rectangle r = frame.getBounds();
		return new Rectangle(r.x + thickness, r.y + thickness, r.width - (thickness * 2), r.height - (thickness * 2));
	}
	
	public void startMouseHooker() {
		try {
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		GlobalScreen.addNativeMouseListener(mouseHooker);
	}
	
	public void removeHooker() {
		GlobalScreen.removeNativeMouseListener(mouseHooker);
	}
	
	public void setBounds(Rectangle rect) {
		setBounds(rect.x, rect.y, rect.width, rect.height);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		if(!lock) frame.setBounds(x, y, width, height);
	}
	
	public void toggleLock() {
		lock = !lock;
		setLock(lock);
	}
	
	public void setLock(boolean lock) {
		this.lock = lock;
		if(lock) {
			cp.setBorder(border1); 
			cp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}else {
			cp.setBorder(border2);
			cp.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		}
	}
	
	public WindowInfo getActiveWindowInfo() {
		WindowInfo info = new WindowInfo();
		HWND hwnd = user32.GetForegroundWindow();
		char[] wt = new char[512];
		user32.GetWindowText(hwnd, wt, 512);
		
		info.setTitle(new String(wt));

		RECT rect = new RECT();
		user32.GetWindowRect(hwnd, rect);

		info.setRect(rect.toRectangle());
		return info;
	}
	
	private class WindowResizer extends MouseAdapter implements MouseMotionListener, KeyListener {
		int dirX, dirY, x, y, width, height;
		
		Window window;
		
		public WindowResizer(Window window) {
			this.window = window;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
		    Point p2 = e.getPoint();
		    dirX = 0; dirY = 0;
		    x = window.getX();
		    y = window.getY();
		    width = window.getWidth();
		    height = window.getHeight();
		    
			int t = 15; // thickness
		    if(p2.x < t) dirX = -1; 
		    if(p2.y < t) dirY = -1;
		    if(p2.x > window.getWidth() - t) dirX = +1;
		    if(p2.y > window.getHeight() - t) dirY = +1;
	   }

		public void mouseDragged(MouseEvent e) {
			resize(e.getLocationOnScreen());
		}
		
		public void resize(Point p) {
		    if(dirX == -1) { //왼쪽
		        width = width + x - p.x;
		        x = p.x;
		    }
		    if(dirY == -1) { //위쪽
		        height = height + y - p.y;
		        y = p.y;
		    }
		    if(dirX == +1) { //오른쪽
		        width = p.x - x;
		    }
		    if(dirY == +1) { //아래쪽
		        height = p.y - y;
		    }

		    if(width < 0) {
		    	dirX *= -1;
		    }
		    if(height < 0) {
		    	dirY *= -1;
		    }
		    
		    setBounds(x, y, width, height);
		}

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			Rectangle r = window.getBounds();
			int key = e.getKeyCode();
			switch (key) {
				case KeyEvent.VK_NUMPAD4: r.x--; r.width++; break;
				case KeyEvent.VK_NUMPAD6: r.width++; break;
				case KeyEvent.VK_NUMPAD8: r.y--; r.height++; break;
				case KeyEvent.VK_NUMPAD2: r.height++; break;
				case KeyEvent.VK_LEFT: r.x++; r.width--; break;
				case KeyEvent.VK_RIGHT: r.width--; break;
				case KeyEvent.VK_UP: r.y++; r.height--; break;
				case KeyEvent.VK_DOWN: r.height--; break;
				case KeyEvent.VK_DIVIDE: toggleLock();
			}
			if(width > 0 && height > 0);
		    setBounds(r);
		}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private class MouseHooker implements NativeMouseListener {
		public void nativeMouseClicked(NativeMouseEvent e) {
			WindowInfo info = getActiveWindowInfo();
			System.out.println(info.getTitle() + " rect : " + info.getRect());
			if(info.getTitle().startsWith("Crawling Tool")) return;
			Rectangle r = info.getRect();	
			r.y += 45;
			r.x += 10;
			r.width -= 40;
			r.height -= 40 * 2 + 5 * 2;
			setBounds(r);
		}

		public void nativeMousePressed(NativeMouseEvent e) {}

		public void nativeMouseReleased(NativeMouseEvent e) {}
	}
	
//	public static void main(String[] args) {
//		new OcrFrame();
//	}
}
