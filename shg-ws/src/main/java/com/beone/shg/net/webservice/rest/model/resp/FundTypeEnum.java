package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class FundTypeEnum {

	private String enumName;
	private List<FundTypeValue> enumValues;
	
	public FundTypeEnum() {
		enumValues = new ArrayList<FundTypeValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<FundTypeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<FundTypeValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(FundTypeValue enumValue) {
		this.enumValues.add(enumValue);
	}	
}
