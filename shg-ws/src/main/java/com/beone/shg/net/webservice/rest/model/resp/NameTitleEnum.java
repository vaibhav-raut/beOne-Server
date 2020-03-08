package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class NameTitleEnum {

	private String enumName;
	private List<NameTitleValue> enumValues;
	
	public NameTitleEnum() {
		enumValues = new ArrayList<NameTitleValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<NameTitleValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<NameTitleValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(NameTitleValue enumValue) {
		this.enumValues.add(enumValue);
	}	
}
