package com.beone.shg.net.webservice.rest.model.resp;

public class FundTypeValue {

	private int fundTypeId;
	private String fundType;
	private String fundCategory;
	private String fundTypeDesc;
	
	public FundTypeValue() {
	}

	public FundTypeValue(String fundType, String fundCategory, String fundTypeDesc) {
		super();
		this.fundType = fundType;
		this.fundCategory = fundCategory;
		this.fundTypeDesc = fundTypeDesc;
	}

	public FundTypeValue(int fundTypeId, String fundType, String fundCategory, String fundTypeDesc) {
		super();
		this.fundTypeId = fundTypeId;
		this.fundType = fundType;
		this.fundCategory = fundCategory;
		this.fundTypeDesc = fundTypeDesc;
	}

	public int getFundTypeId() {
		return fundTypeId;
	}

	public void setFundTypeId(int fundTypeId) {
		this.fundTypeId = fundTypeId;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getFundCategory() {
		return fundCategory;
	}

	public void setFundCategory(String fundTypeCategory) {
		this.fundCategory = fundTypeCategory;
	}

	public String getFundTypeDesc() {
		return fundTypeDesc;
	}

	public void setFundTypeDesc(String fundTypeDesc) {
		this.fundTypeDesc = fundTypeDesc;
	}
}
