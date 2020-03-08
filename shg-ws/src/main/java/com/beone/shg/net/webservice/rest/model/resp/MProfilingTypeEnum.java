package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MProfilingTypeEnum {

	private String enumName;
	private List<MProfilingTypeValue> enumValues;
	
	public MProfilingTypeEnum() {
		enumValues = new ArrayList<MProfilingTypeValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<MProfilingTypeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<MProfilingTypeValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(MProfilingTypeValue enumValue) {
		this.enumValues.add(enumValue);
	}	
}
