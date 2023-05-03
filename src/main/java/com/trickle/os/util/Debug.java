package com.trickle.os.util;

import java.util.Date;

public final class Debug {
//
//	public static void log(Object... args) {
//		StringBuilder sb = new StringBuilder();
//		int i = 0;
//		for (Object o : args) {
//			if (i++ > 0) {
//				sb.append(", ");
//			}
//			sb.append(o);
//		}
//		LOG.debug(sb.toString());
//	}
	
	public static void log(Object... args) {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
        String className = ste.getClassName().substring(ste.getClassName().lastIndexOf(".")+1);
        String methodName = ste.getMethodName();
        int lineNumber = ste.getLineNumber();
        String timestamp = StrUtil.TIME_FORMAT.format(new Date());
        String log = String.format("[%s] %s.%s() #line: %d ", timestamp, className, methodName, lineNumber);
        
        System.out.print(log);
        sysout(args);
	}
	
//    public static void log(Object... args) {
//    	StackTraceElement ste = new Throwable().getStackTrace()[1];
//        String className = ste.getClassName();
//        String methodName = ste.getMethodName();
//        int lineNumber = ste.getLineNumber();
//        String timestamp = StrUtil.TIMESTAMP_FORMAT.format(new Date());
//        String log = String.format("[%s] %s.%s() #line: %d", timestamp, className, methodName, lineNumber);
//        
//        System.out.println(log);
//        sysout(args);
//    }
	public static String sysout(Object... args) {
        StringBuilder sb = new StringBuilder();
		for(Object o : args) {
			if(sb.length() == 0) sb.append(">> ");
			else sb.append(" ");
			if (o instanceof Float) {
		        float f = (Float) o;
		        o = String.format("%.3f", f);
		    } else if (o instanceof Double) {
		        double d = (Double) o;
		        o = String.format("%.3f", d);
		    }
			sb.append(o);
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public static void main(String[] args) {
		log();
		log();
	}
}