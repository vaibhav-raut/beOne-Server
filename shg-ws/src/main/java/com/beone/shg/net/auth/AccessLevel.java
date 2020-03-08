package com.beone.shg.net.auth;

public enum AccessLevel {
	
	NO_ACCESS(0), READ(1), UPDATE(3), APPROVE(7);
	
	public static int ACCESS_TOP = 8;
	int accessLevelValue;
	
	private AccessLevel(int accessLevelValue) {
		this.accessLevelValue = accessLevelValue;
	}
	
	public int getAccessLevelValue() {
		return accessLevelValue;
	}
	
	public static int getAccessLevelValue(String level) {
		switch(level) {
		case "NO_ACCESS":
			return NO_ACCESS.getAccessLevelValue();

		case "READ":
			return READ.getAccessLevelValue();
		
		case "UPDATE":
			return UPDATE.getAccessLevelValue();
		
		case "APPROVE":
			return APPROVE.getAccessLevelValue();
		}
		return NO_ACCESS.getAccessLevelValue();
	}
	
	public static AccessLevel getAccessLevel(String level) {
		switch(level) {
		case "NO_ACCESS":
			return NO_ACCESS;

		case "READ":
			return READ;
		
		case "UPDATE":
			return UPDATE;
		
		case "APPROVE":
			return APPROVE;
		}
		return NO_ACCESS;
	}
}
