package com.beone.shg.common.web.security;

import java.io.Serializable;

public class ShgPassToken implements Serializable {

	private static final long serialVersionUID = 1117320457123660437L;
	private long timestamp;
	private String key;
	private long memberAcNo;
	private long selectedGroupAcNo;
	private long wsAccess;
	private long uiAccess;	

	public ShgPassToken() {	
	}

	public ShgPassToken(long timestamp, String key, long memberAcNo,
			long selectedGroupAcNo, long wsAccess, long uiAccess) {
		super();
		this.timestamp = timestamp;
		this.key = key;
		this.memberAcNo = memberAcNo;
		this.selectedGroupAcNo = selectedGroupAcNo;
		this.wsAccess = wsAccess;
		this.uiAccess = uiAccess;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getMemberAcNo() {
		return memberAcNo;
	}

	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}

	public long getSelectedGroupAcNo() {
		return selectedGroupAcNo;
	}

	public void setSelectedGroupAcNo(long selectedGroupAcNo) {
		this.selectedGroupAcNo = selectedGroupAcNo;
	}

	public long getWsAccess() {
		return wsAccess;
	}

	public void setWsAccess(long wsAccess) {
		this.wsAccess = wsAccess;
	}

	public long getUiAccess() {
		return uiAccess;
	}

	public void setUiAccess(long uiAccess) {
		this.uiAccess = uiAccess;
	}
}
