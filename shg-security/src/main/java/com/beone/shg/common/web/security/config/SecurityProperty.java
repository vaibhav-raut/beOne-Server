package com.beone.shg.common.web.security.config;

import com.beone.shg.config.EnvConfigProperty;
import com.beone.shg.config.EnvConfigPropertyType;

public enum SecurityProperty implements EnvConfigProperty{
	
	AUTH_TOKEN("security.auth.token.name"),
	MEMBER_AC_NO("security.auth.member.account.number"),
	SELECTED_GROUP_AC_NO("security.auth.group.account.number"),
	UI_ACCESS_HEADER("security.auth.ui.access.code");
	
	private String nameInFile;
	private String defaultValue;
	private EnvConfigPropertyType type; 
	
	private SecurityProperty(String nameInFile) {
		this(nameInFile,null);
	}
	private SecurityProperty(String nameInFile, String defaultValue) {
		this(nameInFile, defaultValue, EnvConfigPropertyType.PUBLIC);
	}
	
	private SecurityProperty(String nameInFile, String defaultValue, EnvConfigPropertyType type) {
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
