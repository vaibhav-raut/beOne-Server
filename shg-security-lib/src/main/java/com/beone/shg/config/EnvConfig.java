package com.beone.shg.config;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvConfig<T extends Enum<T>> {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(EnvConfig.class);

	private Class<T> enumClass; 
	private Properties properties;
	
	public EnvConfig(Class<T> enumClass) {
		this.enumClass = enumClass;
	}
	
	void setProperties(Properties globalProps) {
		this.properties = globalProps;
	}
	
	public T[] getPropertyEnums(){
		return enumClass.getEnumConstants();
	}

	public String getStringValue(T prop) {
		EnvConfigProperty property = (EnvConfigProperty) prop;
		String key = property.getNameInFile();
		String value = properties.getProperty(key);
		if (value==null) value=property.getDefaultValue();
		return value;
	}

	public Boolean getBooleanValue(T property) {
		String value = getStringValue(property);
 		return (value==null) ? null: Boolean.valueOf(value);
	}

	public Long getLongValue(T property) {
		String value = getStringValue(property);
 		return (value==null) ? null: Long.valueOf(value);
	}
	
	public Integer getIntegerValue(T property) {
		String value = getStringValue(property);
 		return (value==null) ? null: Integer.valueOf(value);
	}
	
	public String getValueForDisplay(T prop){
		String value = getStringValue(prop);
		EnvConfigProperty property = (EnvConfigProperty) prop;
		
		if (EnvConfigPropertyType.SECURED!=property.getType() || value==null || value.isEmpty()) return value;
		
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i=i+2) {
			chars[i]='#';
		}
		return String.valueOf(chars);
	}

	public Map<String,String> getValuesForDisplay(){
		TreeMap<String, String> effectiveProperties = new TreeMap<String, String>();
		T[] propertyEnums = getPropertyEnums();
		for (T propertyEnum : propertyEnums) {
			String valueForDisplay = getValueForDisplay(propertyEnum);
			String nameInFile = ((EnvConfigProperty) propertyEnum).getNameInFile();
			if (valueForDisplay==null && properties.containsKey(nameInFile)==false) continue;
			effectiveProperties.put(nameInFile, valueForDisplay);
		}
		return effectiveProperties;
		
	}

}

