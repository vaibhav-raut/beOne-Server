package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MProfilingTypeCatEnum {

	private String enumName;
	private List<MProfilingTypeCategory> enumValues;
	
	public MProfilingTypeCatEnum() {
		enumValues = new ArrayList<MProfilingTypeCategory>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<MProfilingTypeCategory> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<MProfilingTypeCategory> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(MProfilingTypeCategory enumValue) {
		this.enumValues.add(enumValue);
	}	
}
