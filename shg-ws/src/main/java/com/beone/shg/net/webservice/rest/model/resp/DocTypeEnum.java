package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class DocTypeEnum {

	private String enumName;
	private List<DocTypeValue> enumValues;
	
	public DocTypeEnum() {
		enumValues = new ArrayList<DocTypeValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<DocTypeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<DocTypeValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(DocTypeValue enumValue) {
		this.enumValues.add(enumValue);
	}	
}
