package com.beone.shg.config;

public class EnvConfigLocation {
	public static final String CLASSPATH = "classpath";
	public static final EnvConfigLocation INSTANCE = new EnvConfigLocation(); 

	private String path=CLASSPATH;

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
