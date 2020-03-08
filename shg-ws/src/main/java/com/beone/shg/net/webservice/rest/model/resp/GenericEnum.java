package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GenericEnum {

	private String enumName;
	private List<EnumValue> enumValues;
	
	public GenericEnum() {
		enumValues = new ArrayList<EnumValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<EnumValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<EnumValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(EnumValue enumValue) {
		this.enumValues.add(enumValue);
	}	
}
