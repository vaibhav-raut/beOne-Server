package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GroupTypeEnum {

	private String enumName;
	private List<GroupTypeValue> enumValues;
	
	public GroupTypeEnum() {
		enumValues = new ArrayList<GroupTypeValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<GroupTypeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<GroupTypeValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(GroupTypeValue enumValue) {
		this.enumValues.add(enumValue);
	}	
}
