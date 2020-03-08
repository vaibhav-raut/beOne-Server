package com.beone.shg.net.webservice.rest.model.rest;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class PassChangeRequestREST {
	private long memberAcNo;
	private String oldPasscode;
	private String newPasscode;
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public String getOldPasscode() {
		return oldPasscode;
	}
	public void setOldPasscode(String oldPasscode) {
		this.oldPasscode = oldPasscode;
	}
	public String getNewPasscode() {
		return newPasscode;
	}
	public void setNewPasscode(String newPasscode) {
		this.newPasscode = newPasscode;
	}
}
