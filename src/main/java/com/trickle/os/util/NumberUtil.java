package com.trickle.os.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	public static int random(int range) {
		if(range <= 0) range = 10;
		return new Random().nextInt(range);
	}
	
	public static int extractInt(String input, String regex) {
	    int result = 0;
	    Matcher matcher = Pattern.compile(regex).matcher(input);
	    if (matcher.find()) {
	        result = Integer.parseInt(matcher.group(1));
	    }
	    return result;
	}
}
