package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class TxTypeEnum {

	private String enumName;
	private List<TxTypeValue> enumValues;
	
	public TxTypeEnum() {
		enumValues = new ArrayList<TxTypeValue>();
	}
	
	public TxTypeEnum(String enumName, List<TxTypeValue> enumValues) {
		super();
		this.enumName = enumName;
		this.enumValues = enumValues;
	}

	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<TxTypeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<TxTypeValue> enumValues) {
		this.enumValues = enumValues;
	}
}
