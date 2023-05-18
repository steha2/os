package com.trickle.os.cr.test;

public final class Debug {
//	private static final Logger LOG = LogManager.getLogger(Debug.class);
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
//		LOG.info(sb.toString());
//	}
	
	public static void sysout(Object... args) {
		int i = 0;
		System.out.print("-- ");
		for(Object o : args) {
			System.out.print(i++ == 0 ? "" : "   ");
			System.out.print(o);
		}
		System.out.println();
	}
}