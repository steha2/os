package com.trickle.os.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.trickle.os.vo.ItemVo;

public class StrUtil {
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

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
	
	public static String time() {
		return TIME_FORMAT.format(new Date());
	}
	
	public static String getPagingQuery(String tableName, String orderByColumn, String[] columns) {
	  String columnList = String.join(",", columns);
	  return String.format(
		"SELECT %s FROM (" +
		"SELECT %s FROM (" +
	    "SELECT %s, ROWNUM AS RN FROM (" +
	    "SELECT %s FROM %s " +
	    "ORDER BY %s DESC ) )" +
	    "WHERE RN >= ?) WHERE ROWNUM <= ?",
	    columnList, columnList, columnList, columnList, tableName, orderByColumn);
	}
	
	public static String createTableQuery(Class<?> dataClass) {
	    StringBuilder sb = new StringBuilder();
	    sb.append("CREATE TABLE ");
	    sb.append(dataClass.getSimpleName().toLowerCase());
	    sb.append(" (");
	    for (Field field : dataClass.getDeclaredFields()) {
	        field.setAccessible(true);
	        sb.append(field.getName());
	        sb.append(" ");
	        // 이 부분에서 각 필드의 타입에 맞는 데이터베이스 타입을 결정하여 쿼리에 추가합니다.
	        sb.append(getType(field.getType()));
	        sb.append(",");
	    }
	    sb.deleteCharAt(sb.length() - 1);
	    sb.append(");");
	    return sb.toString();
	}

	private static String getType(Class<?> type) {
	    // 각 필드의 타입에 맞는 데이터베이스 타입을 결정하여 반환합니다.
	    if (type == Integer.class || type == int.class || type == Long.class || type == long.class) {
	        return "NUMBER";
	    }else if(type == Double.class || type == double.class || type == Float.class || type == float.class) {
	    	return "NUMBER(20,38)";
	    }else if(type == Date.class || type == LocalDate.class || type == LocalDateTime.class) {
	    	return "DATE";
	    }else if(type == byte[].class) {
	    	return "BLOB";
	    } else {
	        return "VARCHAR2(255)";
	    }
	}
	
	public static String extractInitialSound(String word) {
	    String initialSound = "";
	    char[] charArray = word.toCharArray();
	    for (char c : charArray) {
	        // 초성 유니코드 범위: AC00-D7AF (가-힣)
	        if (c >= 0xAC00 && c <= 0xD7AF) {
	            int initialSoundIndex = (c - 0xAC00) / 28 / 21;
	            initialSound += (char) (initialSoundIndex + 0x1100);
	        }
	    }
	    return initialSound;
	}

	public static void main(String[] args) {
		System.out.println(createTableQuery(ItemVo.class));
	}
}
