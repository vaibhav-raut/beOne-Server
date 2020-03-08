package com.beone.shg.net.webservice.rest.model.rest;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.persistence.model.MMessage;
import com.beone.shg.net.persistence.support.EnumCache;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MMessageREST {

	private long memberAcNo;
	private String mrole;
	private String groupType;
	private long mobileNo;
	private String email;
	private boolean mobileVerified;
	private boolean emailVerified;
	private int mobileVeriCode;
	private int emailVeriCode;
	
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public String getMrole() {
		return mrole;
	}
	public void setMrole(String mrole) {
		this.mrole = mrole;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isMobileVerified() {
		return mobileVerified;
	}
	public void setMobileVerified(boolean mobileVerified) {
		this.mobileVerified = mobileVerified;
	}
	public boolean isEmailVerified() {
		return emailVerified;
	}
	public void setEmailVerified(boolean emailverified) {
		this.emailVerified = emailverified;
	}
	public int getMobileVeriCode() {
		return mobileVeriCode;
	}
	public void setMobileVeriCode(int mobileVeriCode) {
		this.mobileVeriCode = mobileVeriCode;
	}
	public int getEmailVeriCode() {
		return emailVeriCode;
	}
	public void setEmailVeriCode(int emailVeriCode) {
		this.emailVeriCode = emailVeriCode;
	}
	
	public static void loadMMessageREST(MMessage mMessage, MMessageREST rest) {
		
		rest.setMemberAcNo(mMessage.getMobileNo());
		rest.setMrole(EnumCache.getNameOfMRole(mMessage.getMroleId()));
		rest.setGroupType(EnumCache.getNameOfGroupType(mMessage.getGroupTypeId()));
		rest.setMobileNo(mMessage.getMobileNo());
		rest.setEmail(mMessage.getEmail());
		rest.setMobileVerified(mMessage.getMobileVerified() == 1);
		rest.setEmailVerified(mMessage.getEmailVerified() == 1);
	}
}
