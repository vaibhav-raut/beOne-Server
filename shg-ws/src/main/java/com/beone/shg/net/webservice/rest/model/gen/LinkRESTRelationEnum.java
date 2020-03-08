package com.beone.shg.net.webservice.rest.model.gen;

public enum LinkRESTRelationEnum {
	SELF("self"),
	ALTERNATE("alternate");
	
	private final String restValue;

	private LinkRESTRelationEnum(String restValue){
		this.restValue = restValue;
	}

	public String getRestValue() {
		return restValue;
	}
}
