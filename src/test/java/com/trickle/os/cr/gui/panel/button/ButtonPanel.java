package com.trickle.os.cr.gui.panel.button;

import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonPanel extends AbstractButtonPanel{
	@Override
	public void addButton(String name) {
		settingButton(new JButton(name), null);
	}

	@Override
	public void addButton(String name, ImageIcon icon) {
		addButton(name, icon, null);		
	}

	@Override
	public void addButton(String name, Consumer<?> action) {
		settingButton(new JButton(name), action);
	}

	@Override
	public void addButton(ImageIcon icon, Consumer<?> action) {
		settingButton(new JButton(icon), action);
	}
	
	@Override
	public void addButton(String name, ImageIcon icon, Consumer<?> action) {
		JButton button = new JButton(icon);
		settingButton(button, action);
	}
	
	public void addRoundButton(String name, Consumer<?> action) {
		JButton button = new RoundButton(name);
		settingButton(button, action);
	}
}
