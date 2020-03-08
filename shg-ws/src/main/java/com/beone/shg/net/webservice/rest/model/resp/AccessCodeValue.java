package com.beone.shg.net.webservice.rest.model.resp;

public class AccessCodeValue {

	private int accessCodeId;
	private String accessCode;
	private String accessCodeDisplay;
	private String accessCodeCategory;
	private String accessCodeDesc;
	private String accessLevel;
	
	public AccessCodeValue() {
	}

	public AccessCodeValue(String accessCode, String accessCodeDisplay,
			String accessCodeCategory, String accessCodeDesc, String accessLevel) {
		super();
		this.accessCode = accessCode;
		this.accessCodeDisplay = accessCodeDisplay;
		this.accessCodeCategory = accessCodeCategory;
		this.accessCodeDesc = accessCodeDesc;
		this.accessLevel = accessLevel;
	}

	public AccessCodeValue(int accessCodeId, String accessCode,
			String accessCodeDisplay, String accessCodeCategory,
			String accessCodeDesc, String accessLevel) {
		super();
		this.accessCodeId = accessCodeId;
		this.accessCode = accessCode;
		this.accessCodeDisplay = accessCodeDisplay;
		this.accessCodeCategory = accessCodeCategory;
		this.accessCodeDesc = accessCodeDesc;
		this.accessLevel = accessLevel;
	}

	public AccessCodeValue(AccessCodeValue value, String accessLevel) {
		super();
		this.accessCodeId = value.accessCodeId;
		this.accessCode = value.accessCode;
		this.accessCodeDisplay = value.accessCodeDisplay;
		this.accessCodeCategory = value.accessCodeCategory;
		this.accessCodeDesc = value.accessCodeDesc;
		this.accessLevel = accessLevel;
	}

	public int getAccessCodeId() {
		return accessCodeId;
	}

	public void setAccessCodeId(int accessCodeId) {
		this.accessCodeId = accessCodeId;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getAccessCodeDisplay() {
		return accessCodeDisplay;
	}

	public void setAccessCodeDisplay(String accessCodeDisplay) {
		this.accessCodeDisplay = accessCodeDisplay;
	}

	public String getAccessCodeCategory() {
		return accessCodeCategory;
	}

	public void setAccessCodeCategory(String accessCodeCategory) {
		this.accessCodeCategory = accessCodeCategory;
	}

	public String getAccessCodeDesc() {
		return accessCodeDesc;
	}

	public void setAccessCodeDesc(String accessCodeDesc) {
		this.accessCodeDesc = accessCodeDesc;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
}
