package com.beone.shg.net.webservice.rest.model.resp;

public class MessageTypeValue {

	private int messageTypeId;
	private String lang;
	private String messageType;
	private String smsFormat;
	private String emailFormat;
	private long passKey;
	
	public MessageTypeValue() {
	}

	public MessageTypeValue(int messageTypeId, String lang, String messageType,
			String smsFormat, String emailFormat, long passKey) {
		super();
		this.messageTypeId = messageTypeId;
		this.lang = lang;
		this.messageType = messageType;
		this.smsFormat = smsFormat;
		this.emailFormat = emailFormat;
		this.passKey = passKey;
	}

	public int getMessageTypeId() {
		return messageTypeId;
	}

	public void setMessageTypeId(int messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getSmsFormat() {
		return smsFormat;
	}

	public void setSmsFormat(String smsFormat) {
		this.smsFormat = smsFormat;
	}

	public String getEmailFormat() {
		return emailFormat;
	}

	public void setEmailFormat(String emailFormat) {
		this.emailFormat = emailFormat;
	}

	public long getPassKey() {
		return passKey;
	}

	public void setPassKey(long passKey) {
		this.passKey = passKey;
	}
}
