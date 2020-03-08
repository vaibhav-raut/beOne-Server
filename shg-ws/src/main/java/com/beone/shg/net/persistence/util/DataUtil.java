package com.beone.shg.net.persistence.util;

import java.math.BigDecimal;
import java.util.Date;

public class DataUtil {

	public final static long MEMBER_RANGE_FOR_GROUP = 10000L;
	public final static long GROUP_RANGE_FOR_DISTRICT = 100000L;
	public final static int MAX_MEMBERS_PER_GROUP = 9998;
	public final static int MAX_GROUP_PER_DISTRICT = 99998;
	public final static int MAX_DISTRICT = 999;
	public final static long MEMBER_RANGE_FOR_DISTRICT = MEMBER_RANGE_FOR_GROUP * GROUP_RANGE_FOR_DISTRICT;
	
	public final static long LONG_TIME_01_01_2000 = 946665000000L;
	public final static long LONG_TIME_01_01_2040 = 2208969000000L;

	public final static long LONG_TIME_MIN = 1009843200000L;
	public final static long LONG_TIME_MAX = 2145916800000L;
		
	public final static double ZERO_DOUBLE = 0.0;
	public final static float ZERO_FLOAT = 0.0F;
	public final static int ZERO_INTEGER = 0;
	public final static long ZERO_LONG = 0L;
	public final static BigDecimal ZERO_BIG_DECIMAL = new BigDecimal(0);
	public final static Date INVALID_DATE = new Date(32503660200000L);
	public final static String EMPTY_STRING = "";
	public final static int DEFAULT_DB_LIMIT = 500;
    
	// TODO - TEMPERORY ACCESS TOKEN
	public final static String ACCESS_TOKEN = "TEMPERORY ACCESS TOKEN";
	
	
	public static String toString(Short i) {
		if(i != null) {
			return i.toString();
		}
		return "0";
	}
	
	public static String toString(Integer i) {
		if(i != null) {
			return i.toString();
		}
		return "0";
	}
	
	public static String toString(Long l) {
		if(l != null) {
			return l.toString();
		}
		return "0";
	}
	
	public static String toString(Float f) {
		if(f != null) {
			return f.toString();
		}
		return "0";
	}
	
	public static String toString(Double d) {
		if(d != null) {
			return d.toString();
		}
		return "0";
	}
	
	public static String toString(BigDecimal bd) {
		if(bd != null) {
			return BDUtil.format(GenAlgoUtil.roundHalfUp(bd, 0));
		}
		return "0";
	}
	
	public static String toString(BigDecimal bd, int dec) {
		if(bd != null) {
			return BDUtil.format(GenAlgoUtil.roundHalfUp(bd, dec));
		}
		return "0";
	}
	
	public static String toString(String s) {
		if(s != null) {
			return s;
		}
		return "";
	}
	
	public static BigDecimal toBigDecimal(BigDecimal a) {	
		if(a != null) {
			return a;
		}	
		return ZERO_BIG_DECIMAL;
	}
	
	public static BigDecimal toBigDecimal(String s) {	
		if(s != null && !s.isEmpty()) {
			return new BigDecimal(s);
		}	
		return ZERO_BIG_DECIMAL;
	}
	
	public static int toInteger(String s) {	
		if(s != null && !s.isEmpty()) {
			return new Integer(s);
		}	
		return ZERO_INTEGER;
	}
	
	public static long toLong(String s) {	
		if(s != null && !s.isEmpty()) {
			return new Long(s);
		}	
		return ZERO_INTEGER;
	}
	
	public static float toFloat(String s) {	
		if(s != null && !s.isEmpty()) {
			return new Float(s);
		}	
		return ZERO_FLOAT;
	}
	
	public static BigDecimal add(BigDecimal a, BigDecimal b) {
		if(a == null && b != null) {
			return b;
		}
		if(a != null && b == null) {
			return a;
		}
		if(a != null && b != null) {
			return a.add(b);
		}
		return ZERO_BIG_DECIMAL;
	}
	
	public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		if(a == null && b != null) {
			return b.negate();
		}
		if(a != null && b == null) {
			return a;
		}
		if(a != null && b != null) {
			return a.subtract(b);
		}
		return ZERO_BIG_DECIMAL;
	}
	
}
