package com.beone.shg.config;


public interface EnvConfigProperty {
	String getNameInFile();
	String getDefaultValue();
	EnvConfigPropertyType getType();
}
