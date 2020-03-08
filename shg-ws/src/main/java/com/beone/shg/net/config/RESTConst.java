package com.beone.shg.net.config;

public class RESTConst {
	
	public static final String[] SUCCESS = {"Success"};
	public static final String[] FAILURE = {"Failure"};
	public static final String[] ERROR = {"Error"};

	public static String[] message(String msg) {
		String[] responce = new String[1];
		responce[0] = "Success: " + msg;
		return responce;
	}
}
