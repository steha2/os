package com.trickle.os.cr.gui.panel.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import com.trickle.os.cr.gui.panel.CustomPanel;

public abstract class AbstractButtonPanel extends CustomPanel {
	protected List<AbstractButton> buttonList = new Vector<>();
	
	private Dimension buttonSize;
	
	private Color bgColor, fgColor;
	
	public abstract void addButton(String buttonName, Consumer<?> c);
	
	public abstract void addButton(String buttonName, ImageIcon icon, Consumer<?> c);
	
	public abstract void addButton(String buttonName);
	
	public abstract void addButton(String buttonName, ImageIcon icon);

	public abstract void addButton(ImageIcon icon, Consumer<?> c);

	public List<AbstractButton> getButtonList() {
		return buttonList;
	}
	
	public void setButtonSize(int width, int height) {
		buttonSize = new Dimension(width, height);
	}
	
	public void setColor(Color bgColor, Color fgColor) {
		this.bgColor = bgColor;
		this.fgColor = fgColor;
	}
	
	public void setProperty(int width, int height, Color bgColor, Color fgColor) {
		setButtonSize(width, height);
		setColor(bgColor, fgColor);
	}

	protected void settingButton(AbstractButton button, Consumer<?> c) {
		buttonList.add(button);
		button.setName(button.getText());
		if (c != null) 
			button.addActionListener(e -> c.accept(null));
		if(buttonSize != null)
			button.setPreferredSize(buttonSize);
		if(bgColor != null)
			button.setBackground(bgColor);
		if(fgColor != null)
			button.setForeground(fgColor);
		rootPanel.add(button);
	}

	public List<String> getValues() {
		return buttonList.stream().filter(AbstractButton::isSelected)
				.map(AbstractButton::getName).collect(Collectors.toList());
	}

	public AbstractButton getButton(int index) {
		return buttonList.get(index);
	}

	public AbstractButton getButton(String name) {
		for (AbstractButton button : buttonList) {
			if (button.getName() != null && button.getName().equals(name))
				return button;
		}
		return null;
	}

	public void setSelected(int index) {
		setSelected(true, index);
	}

	public void setSelected(boolean isSelected, String name) {
		buttonList.forEach(button -> {
			if (button.getName() != null && button.getName().equals(name)) {
				button.setSelected(isSelected);
				return;
			}
		});
	}

	public void setSelected(boolean isSelected, int... indexes) {
		for (int i = 0; i < indexes.length; i++) {
			int index = indexes[i];
			if (index >= 0 && index < buttonList.size())
				buttonList.get(index).setSelected(isSelected);
		}
	}

	public void setFont(int index, Font font) {
		buttonList.get(index).setFont(font);
	}

	public void setFonts(Font font) {
		buttonList.forEach(b -> b.setFont(font));
	}

	public void setColors(int index, Color foreground) {
		buttonList.get(index).setForeground(foreground);
	}

	public void setColors(Color foreground) {
		buttonList.forEach(b -> b.setForeground(foreground));
	}

	public void removeAll() {
		super.removeAll();
		buttonList.clear();
	}
	
	public void setBackgrounds(Color bgColor) {
		buttonList.forEach(b->b.setBackground(bgColor));
	}
}
