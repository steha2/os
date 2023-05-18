package com.trickle.os.cr.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.trickle.os.cr.gui.panel.InputPanel;
import com.trickle.os.cr.gui.panel.input.InputComponent;
import com.trickle.os.cr.gui.panel.input.PasswordPanel;
import com.trickle.os.cr.gui.panel.input.TextAreaPanel;
import com.trickle.os.cr.gui.panel.input.TextFieldPanel;
import com.trickle.os.cr.test.Debug;

public class InputForm<T> {
	private Map<String,InputComponent> compMap = new HashMap<>();

	public int columns = 15, rows = 10, labelWidth = 80, labelHeight = 30;
	public String labelDirection = "West";
	public Color labelFgColor = Color.BLACK, fgColor = Color.BLACK, 
			bgColor = Color.WHITE, panelBgColor;
	public Font font = Gui.font(12);
	
	public void setLabelProp(int labelWidth, int labelHeight, Font font, Color labelFgColor, String labelDirection) {
		this.font = font;
		this.labelWidth = labelWidth;
		this.labelHeight = labelHeight;
		this.labelFgColor = labelFgColor;
		this.labelDirection = labelDirection;
	}
	public void setInputProp(int columns, int rows, Color fgColor, Color bgColor) {
		this.columns = columns;
		this.rows = rows;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}
	
	public InputForm() {}
		
	public InputForm(int columns, int labelWidth, Font font) {
		this.columns = columns;
		this.labelWidth = labelWidth;
		this.font = font;
	}

	public TextAreaPanel createTAP(String name) {
		return createTAP(name, null);
	}
	
	public TextAreaPanel createTAP(String name, String labelText) {
		TextAreaPanel taPanel = new TextAreaPanel(name, columns, rows, labelText, labelDirection);
		setProperties(taPanel);
		return taPanel;
	}
	
	public TextFieldPanel createTFP(String name) {
		return createTFP(name, null);
	}
	
	public TextFieldPanel createTFP(String name, String labelText) {
		TextFieldPanel tfPanel = new TextFieldPanel(name, columns, labelText, labelDirection);
		setProperties(tfPanel);
		return tfPanel;
	}
	
	private void setProperties(InputPanel inputPanel) {
		inputPanel.getInput().setForeground(fgColor);
		inputPanel.getLabel().setForeground(labelFgColor);
		inputPanel.getLabel().setFont(font);
		inputPanel.getLabel().setPreferredSize(new Dimension(labelWidth, labelHeight));
		if(panelBgColor != null) inputPanel.setBackground(panelBgColor);
		addInputComp(inputPanel);
	}
	
	public PasswordPanel createPWP(String name, String labelText) {
		PasswordPanel pwPanel = new PasswordPanel(name, columns, labelText, labelDirection);
		setProperties(pwPanel);
		return pwPanel;
	}
	
	public InputComponent addInputComp(InputComponent inputComp) {
		compMap.put(inputComp.getName(), inputComp);
		return inputComp;
	}
	
	public InputComponent getInputComp(String name) {
		return compMap.get(name);
	}
	
	public void setEditable(boolean editable) {
		compMap.forEach((name,comp)->comp.setEditable(editable));
	}
	
	public void set(String name, Object value) {
		if(compMap.containsKey(name))
			compMap.get(name).setValue(value);
	}
	
	public void resetForm() {
		compMap.forEach((name,comp)->comp.reset());
	}

	public boolean validate() {
		return true;
	}
	
	public void setData(T dto) {
		for(Field field : dto.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if(compMap.containsKey(field.getName())) {
				try {
					compMap.get(field.getName()).setValue(field.get(dto));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public T saveTo(T t) {
		if(t == null) return null;
		for(Field field : t.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			if(!compMap.containsKey(fieldName)) continue;
			Class<?> type = field.getType();
			try {
				if(type.equals(int.class) || type.equals(Integer.class)) {
					field.set(t, getInt(fieldName));
				}else if(type.equals(long.class) || type.equals(Long.class)) {
					field.setLong(t, getLong(fieldName));
				}else if(type.equals(double.class) || type.equals(Double.class)) {
					field.setDouble(t, getDouble(fieldName));
				}else if(type.equals(float.class) || type.equals(Float.class)) {
					field.setFloat(t, getFloat(fieldName));
				}else if(type.equals(String.class)){
					field.set(t, getValue(fieldName));
				}
			} catch (Exception e) {
				Debug.sysout(e);
				return null;
			}
		}
		return t;
	}
	
	public String findEmptyFields() {
	    List<String> keys = new ArrayList<>();
	    for (String key : compMap.keySet()) {
	        InputComponent inputComponent = compMap.get(key);
	        String value = inputComponent.getValue().toString();
	        if (value.isEmpty()) keys.add(key);
	    }
	    return String.join(" ", keys);
	}
	
	public Object getValue(String name) {
		if(compMap.containsKey(name))
			return compMap.get(name).getValue();
		else
			return "";
	}
	
	public String getString(String name) {
		return getValue(name).toString();
	}
	
	public int getInt(String name) {
		if(getString(name).isEmpty()) return 0;
		return Integer.parseInt(getString(name));
	}
	
	public long getLong(String name) {
		if(getString(name).isEmpty()) return 0;
		return Long.parseLong(getString(name));
	}
	
	public float getFloat(String name) {
		if(getString(name).isEmpty()) return 0;
		return Float.parseFloat(getString(name));
	}
	
	public double getDouble(String name) {
		if(getString(name).isEmpty()) return 0;
		return Double.parseDouble(getString(name));
	}
}