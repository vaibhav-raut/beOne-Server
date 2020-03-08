package com.beone.shg.net.persistence.ppo.util;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.DataUtil;

public class PPOAlgoUtil {

	private static final Logger log = LoggerFactory.getLogger(PPOAlgoUtil.class);

	public static final String ADD = "ADD";
	public static final String SUB = "SUB";
	public static final String MULTI = "MULTI";
	public static final String DIV = "DIV";
	public static final String DECR = "DECR";
	public static final String INCR = "INCR";
	public static final String THIS = "THIS";
	public static final String ZERO = "ZERO";
	public static final String SUB_ZERO = "SUB_ZERO";
	
	public static float compute(float prevValue, String algo, float delta) {
		
		switch(algo) {
		case ADD: {
			return (prevValue + delta);
		}
		case SUB: {
			return (prevValue - delta);
		}
		case MULTI: {
			return (prevValue / delta);
		}
		case DIV: {
			return (prevValue / delta);
		}
		case DECR: {
			return (prevValue - 1);
		}
		case INCR: {
			return (prevValue + 1);
		}
		case THIS: {
			return (delta);
		}
		case ZERO: {
			return (DataUtil.ZERO_FLOAT);
		}
		case SUB_ZERO: {
			float value = (prevValue - delta);
			if(value < DataUtil.ZERO_FLOAT) {
				value = DataUtil.ZERO_FLOAT;
			}
			return value;
		}
		default: {
			log.error("PPOAlgoUtil.compute -Float Invalid Algo : " + algo + 
					"; prevValue : " + DataUtil.toString(prevValue) + 
					"; delta : " + DataUtil.toString(delta) );
		}
		}
		
		return DataUtil.ZERO_FLOAT;
	}

	public static double compute(double prevValue, String algo, double delta) {
		
		switch(algo) {
		case ADD: {
			return (prevValue + delta);
		}
		case SUB: {
			return (prevValue - delta);
		}
		case MULTI: {
			return (prevValue / delta);
		}
		case DIV: {
			return (prevValue / delta);
		}
		case DECR: {
			return (prevValue - 1);
		}
		case INCR: {
			return (prevValue + 1);
		}
		case THIS: {
			return (delta);
		}
		case ZERO: {
			return (DataUtil.ZERO_DOUBLE);
		}
		case SUB_ZERO: {
			double value = (prevValue - delta);
			if(value < DataUtil.ZERO_DOUBLE) {
				value = DataUtil.ZERO_DOUBLE;
			}
			return value;
		}
		default: {
			log.error("PPOAlgoUtil.compute -Double Invalid Algo : " + algo + 
					"; prevValue : " + DataUtil.toString(prevValue) + 
					"; delta : " + DataUtil.toString(delta) );
		}
		}
		
		return DataUtil.ZERO_DOUBLE;
	}

	public static int compute(int prevValue, String algo, int delta) {
		
		switch(algo) {
		case ADD: {
			return (prevValue + delta);
		}
		case SUB: {
			return (prevValue - delta);
		}
		case MULTI: {
			return (prevValue / delta);
		}
		case DIV: {
			return (prevValue / delta);
		}
		case DECR: {
			return (prevValue - 1);
		}
		case INCR: {
			return (prevValue + 1);
		}
		case THIS: {
			return (delta);
		}
		case ZERO: {
			return (DataUtil.ZERO_INTEGER);
		}
		case SUB_ZERO: {
			int value = (prevValue - delta);
			if(value < DataUtil.ZERO_INTEGER) {
				value = DataUtil.ZERO_INTEGER;
			}
			return value;
		}
		default: {
			log.error("PPOAlgoUtil.compute -int Invalid Algo : " + algo + 
					"; prevValue : " + DataUtil.toString(prevValue) + 
					"; delta : " + DataUtil.toString(delta) );
		}
		}
		
		return DataUtil.ZERO_INTEGER;
	}

	public static long compute(long prevValue, String algo, long delta) {
		
		switch(algo) {
		case ADD: {
			return (prevValue + delta);
		}
		case SUB: {
			return (prevValue - delta);
		}
		case MULTI: {
			return (prevValue / delta);
		}
		case DIV: {
			return (prevValue / delta);
		}
		case DECR: {
			return (prevValue - 1);
		}
		case INCR: {
			return (prevValue + 1);
		}
		case THIS: {
			return (delta);
		}
		case ZERO: {
			return (DataUtil.ZERO_LONG);
		}
		case SUB_ZERO: {
			long value = (prevValue - delta);
			if(value < DataUtil.ZERO_LONG) {
				value = DataUtil.ZERO_LONG;
			}
			return value;
		}
		default: {
			log.error("PPOAlgoUtil.compute -long Invalid Algo : " + algo + 
					"; prevValue : " + DataUtil.toString(prevValue) + 
					"; delta : " + DataUtil.toString(delta) );
		}
		}
		
		return DataUtil.ZERO_LONG;
	}

	public static BigDecimal compute(BigDecimal prevValue, String algo, BigDecimal delta) {
		
		switch(algo) {
		case ADD: {
			return (BDUtil.add(prevValue, delta));
		}
		case SUB: {
			return (BDUtil.sub(prevValue, delta));
		}
		case MULTI: {
			return (BDUtil.multiply(prevValue, delta));
		}
		case DIV: {
			return (BDUtil.divide(prevValue, delta));
		}
		case DECR: {
			return (BDUtil.sub(prevValue, new BigDecimal(1)));
		}
		case INCR: {
			return (BDUtil.add(prevValue, new BigDecimal(1)));
		}
		case THIS: {
			return (delta);
		}
		case ZERO: {
			return (DataUtil.ZERO_BIG_DECIMAL);
		}
		case SUB_ZERO: {
			BigDecimal value = BDUtil.sub(prevValue, delta);
			if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) < 0) {
				value = DataUtil.ZERO_BIG_DECIMAL;
			}
			return value;
		}
		default: {
			log.error("PPOAlgoUtil.compute -BigDecimal Invalid Algo : " + algo + 
					"; prevValue : " + DataUtil.toString(prevValue) + 
					"; delta : " + DataUtil.toString(delta) );
		}
		}
		
		return DataUtil.ZERO_BIG_DECIMAL;
	}

	public static Date compute(Date prevValue, String algo, Date delta) {
		
		switch(algo) {
		case "CURRENT_TS": {
			return (new Date(System.currentTimeMillis()));
		}
		case "UPDATE": {
			return (new Date(System.currentTimeMillis()));
		}
		default: {
			log.error("PPOAlgoUtil.compute -Date Invalid Algo : " + algo);
		}
		}
		
		return prevValue;
	}
	
	public static String inverseAlgo(String algo) {

		switch(algo) {
		case ADD: {
			return SUB;
		}
		case SUB: {
			return ADD;
		}
		case MULTI: {
			return DIV;
		}
		case DIV: {
			return MULTI;
		}
		case DECR: {
			return INCR;
		}
		case INCR: {
			return DECR;
		}
		case SUB_ZERO: {
			return ADD;
		}
		}

		return algo;
	}

	public static BigDecimal computeAvg(BigDecimal prevValue, String algo, BigDecimal delta, int n) {

		return compute(prevValue, algo, BDUtil.divide(BDUtil.add(BDUtil.multiply(prevValue, n), delta), (n+1)));
	}
}
