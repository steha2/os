package com.trickle.os.cr.gui.panel.button.input;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import com.trickle.os.cr.gui.panel.input.InputComponent;

public class CheckBoxPanel extends InputButtonPanel implements InputComponent{
	
	@Override
	public void addButton(String buttonName, Consumer<?> c) {
		settingButton(new JCheckBox(buttonName), c);
	}

	@Override
	public void addButton(String buttonName, ImageIcon icon, Consumer<?> c) {
		settingButton(new JCheckBox(buttonName, icon), c);
	}

	@Override
	public void addButton(String buttonName) {
		settingButton(new JCheckBox(buttonName), null);
	}

	@Override
	public void addButton(String buttonName, ImageIcon icon) {
		settingButton(new JCheckBox(buttonName, icon), null);
	}
	
	@Override
	public void addButton(ImageIcon icon, Consumer<?> c) {
		settingButton(new JCheckBox(icon), null);
	}

	public List<AbstractButton> getSelectedButtons() {
		return getButtonList().stream().filter(AbstractButton::isSelected)
				.collect(Collectors.toList());
	}

	@Override
	public String getValue() {
		return getButtonList().stream().filter(AbstractButton::isSelected)
				.map(b->b.getName()).collect(Collectors.joining(","));
	}
	
	@Override
	public void reset() {
		buttonList.forEach(b->b.setSelected(false));
	}
}
