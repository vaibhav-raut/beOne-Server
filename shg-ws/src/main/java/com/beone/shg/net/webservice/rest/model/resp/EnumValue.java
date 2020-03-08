package com.beone.shg.net.webservice.rest.model.resp;

public class EnumValue {

	private int enumIndex;
	private String enumValue;
	private String description;
	
	public EnumValue() {
		super();
	}
	public EnumValue(int enumIndex, String enumValue, String description) {
		super();
		this.enumIndex = enumIndex;
		this.enumValue = enumValue;
		this.description = description;
	}
	public int getEnumIndex() {
		return enumIndex;
	}
	public void setEnumIndex(int enumIndex) {
		this.enumIndex = enumIndex;
	}
	public String getEnumValue() {
		return enumValue;
	}
	public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
