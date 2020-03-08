package com.beone.shg.net.webservice.rest.model.resp;

public class DistrictValue {

	private int districtId;
	private String lang;
	private String state;
	private String division;
	private String district;
	private String districtCode;
	
	public DistrictValue() {
		super();
	}
	public DistrictValue(int districtId, String lang, String state,
			String division, String district, String districtCode) {
		super();
		this.districtId = districtId;
		this.lang = lang;
		this.state = state;
		this.division = division;
		this.district = district;
		this.districtCode = districtCode;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
}
