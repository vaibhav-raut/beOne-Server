package com.beone.shg.net.webservice.rest.model.rest;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.persistence.model.MProfile;

@JsonSerialize(include = Inclusion.NON_NULL)
public class LoginInfoREST {
	
	private long currentServerTime;
	private long memberAcNo;
	private String memberDistrictCode;
	private int memberDistrictId;
	private long selectedGroupAcNo;
	private String groupDistrictCode;
	private int groupDistrictId;
	private String groupName;
	private String groupType;
	private String authToken;
	private MemberREST member;	

	public long getCurrentServerTime() {
		return currentServerTime;
	}

	public void setCurrentServerTime(long currentServerTime) {
		this.currentServerTime = currentServerTime;
	}

	public long getMemberAcNo() {
		return memberAcNo;
	}

	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}

	public String getMemberDistrictCode() {
		return memberDistrictCode;
	}

	public void setMemberDistrictCode(String memberDistrictCode) {
		this.memberDistrictCode = memberDistrictCode;
	}

	public int getMemberDistrictId() {
		return memberDistrictId;
	}

	public void setMemberDistrictId(int memberDistrictId) {
		this.memberDistrictId = memberDistrictId;
	}

	public long getSelectedGroupAcNo() {
		return selectedGroupAcNo;
	}

	public void setSelectedGroupAcNo(long selectedGroupAcNo) {
		this.selectedGroupAcNo = selectedGroupAcNo;
	}

	public String getGroupDistrictCode() {
		return groupDistrictCode;
	}

	public void setGroupDistrictCode(String groupDistrictCode) {
		this.groupDistrictCode = groupDistrictCode;
	}

	public int getGroupDistrictId() {
		return groupDistrictId;
	}

	public void setGroupDistrictId(int groupDistrictId) {
		this.groupDistrictId = groupDistrictId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public MemberREST getMember() {
		return member;
	}

	public void setMember(MemberREST member) {
		this.member = member;
	}

	public static void loadAuthInfo(MProfile mProfile, LoginInfoREST authInfo) {
		
		MemberREST member = new MemberREST();
		
		MemberREST.loadMemberProfile(mProfile, member);
		MemberREST.loadMemberContacts(mProfile, member);		
		authInfo.setCurrentServerTime(System.currentTimeMillis());
		authInfo.setMember(member);
	}
}
