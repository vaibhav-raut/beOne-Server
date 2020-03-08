package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MProfilingTypeCategory {

	private String category;
	private List<MProfilingTypeValue> enumValues;
	
	public MProfilingTypeCategory() {
		enumValues = new ArrayList<MProfilingTypeValue>();
	}	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
