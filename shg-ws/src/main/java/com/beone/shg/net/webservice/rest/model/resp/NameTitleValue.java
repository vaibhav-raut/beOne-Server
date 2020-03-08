package com.beone.shg.net.webservice.rest.model.resp;

public class NameTitleValue {

	private int titleId;
	private String lang;
	private String title;
	private String gender;
	
	public NameTitleValue() {
	}
	
	public NameTitleValue(int titleId, String lang, String title, String gender) {
		super();
		this.titleId = titleId;
		this.lang = lang;
		this.title = title;
		this.gender = gender;
	}
	public int getTitleId() {
		return titleId;
	}
	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
