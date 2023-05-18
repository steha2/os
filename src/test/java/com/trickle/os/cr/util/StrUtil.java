package com.trickle.os.cr.util;

import java.util.Date;

public class StrUtil {
	private StrUtil() {}
	public static String removeLast(String str, int n) {
	    if (str == null || n >= str.length()) {
	        return "";
	    }
	    return str.substring(0, str.length() - n);
	}
	
	public static String removeLast(String str) {
		return removeLast(str, 1);
	}
	
	public static String selectQuery(String tableName, String[] columns) {
		return selectQuery(tableName, columns, "");
	}
	
	public static String selectQuery(String tableName, String[] columns, String where) {
		return "SELECT " + String.join(",", columns) +
			   " FROM " + tableName +" "+ where;
	}
	
	public static Date now() {
		long currentTimeSeconds = (System.currentTimeMillis() / 1000) * 1000;
		Date currentDate = new Date(currentTimeSeconds);
		return currentDate; 
	}
	
	public static String addBr(String... strings) {
	    StringBuilder sb = new StringBuilder("<html>");
	    for (String s : strings) {
	    	sb.append(s).append("<br>");
	    }
	    return sb.append("</html>").toString();
	}
	
	public static String shorten(String str, int n) {
	    if (str.length() > n) {
	        return str.substring(0, n-1) + "...";
	    } else {
	        return str;
	    }
	}
}
