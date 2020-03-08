package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MRoleEnum {

	private String enumName;
	private List<MRoleValue> enumValues;
	
	public MRoleEnum() {
		enumValues = new ArrayList<MRoleValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<MRoleValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<MRoleValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(MRoleValue enumValue) {
		this.enumValues.add(enumValue);
	}	
}
