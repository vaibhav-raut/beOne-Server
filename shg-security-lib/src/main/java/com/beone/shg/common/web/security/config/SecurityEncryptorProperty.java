package com.beone.shg.common.web.security.config;

import com.beone.shg.config.EnvConfigProperty;
import com.beone.shg.config.EnvConfigPropertyType;

public enum SecurityEncryptorProperty implements EnvConfigProperty {
	
	KEY("security.encription.key"),
	SALT("security.encryption.salt");
	private String nameInFile;
	private String defaultValue;
	private EnvConfigPropertyType type; 
	
	private SecurityEncryptorProperty(String nameInFile) {
		this(nameInFile,null);
	}
	private SecurityEncryptorProperty(String nameInFile, String defaultValue) {
		this(nameInFile, defaultValue, EnvConfigPropertyType.PUBLIC);
	}
	
	private SecurityEncryptorProperty(String nameInFile, String defaultValue, EnvConfigPropertyType type) {
		this.nameInFile = nameInFile;
		this.defaultValue = defaultValue;
		this.type = type;

	}
	@Override
	public String getNameInFile() {
		
		return nameInFile;
	}
	@Override
	public String getDefaultValue() {
		
		return defaultValue;
	}
	@Override
	public EnvConfigPropertyType getType() {
		
		return type;
	}
}
