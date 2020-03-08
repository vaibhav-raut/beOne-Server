package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class AccessCodeEnum {

	private String enumName;
	private List<AccessCodeValue> enumValues;
	
	public AccessCodeEnum() {
		enumValues = new ArrayList<AccessCodeValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<AccessCodeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<AccessCodeValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(AccessCodeValue enumValue) {
		this.enumValues.add(enumValue);
	}	
}
