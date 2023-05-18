package com.trickle.os.cr.util;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Style {
	public int width;
	public int height;

	public int num;
	public List<Colors> colors;

	public Color getColor(String name) {
		try {
			if (num >= colors.size() || num < 0)
				num = 0;
			Field field = Colors.class.getDeclaredField(name);
			String colorValue = (String) field.get(colors.get(num));
			return Color.decode(colorValue);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid color name: " + name, e);
		}
	}

	@Override
	public String toString() {
		return "Style [width=" + width + ", height=" + height + ", num=" + num + ", colors=" + colors + "]";
	}
}

class Colors {
	public String contBg = "#FAEECB";
	public String contBorder = "#7B630F";
	public String topBotColor = "#3e270c";
	public String fontColor = "#FFFFFF";
	public String subTitle = "#000000";

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Colors{");
		Arrays.asList(Colors.class.getDeclaredFields()).forEach(field -> {
			try {
				sb.append(field.getName()).append("=").append(field.get(this)).append(", ");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		sb.delete(sb.length() - 2, sb.length()).append("}");
		return sb.toString();
	}
}

