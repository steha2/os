package com.trickle.os.util;

public class StrUtil {
	public static String toSnakeCase(String input) {
	    if (input == null) {
	        return null;
	    }
	    StringBuilder result = new StringBuilder();
	    boolean isFirst = true;
	    for (char c : input.toCharArray()) {
	        if (Character.isUpperCase(c)) {
	            if (!isFirst) {
	                result.append('_');
	            }
	            result.append(Character.toLowerCase(c));
	        } else {
	            result.append(c);
	        }
	        isFirst = false;
	    }
	    return result.toString();
	}
}
