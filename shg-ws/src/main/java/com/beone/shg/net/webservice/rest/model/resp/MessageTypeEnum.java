package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MessageTypeEnum {

	private String enumName;
	private List<MessageTypeValue> enumValues;
	
	public MessageTypeEnum() {
		enumValues = new ArrayList<MessageTypeValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<MessageTypeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<MessageTypeValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(MessageTypeValue enumValue) {
		this.enumValues.add(enumValue);
	}	
}
