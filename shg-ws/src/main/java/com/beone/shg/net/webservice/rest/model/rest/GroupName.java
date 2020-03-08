package com.beone.shg.net.webservice.rest.model.rest;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GroupName {
	private String districtCode;
	private int districtId;
	private int groupAcNo;
	private String groupName;
	private String groupType;
	private String status;
	private String place;
	
	public GroupName() {
		super();
	}
	public GroupName(int districtId, int groupAcNo, String groupName, String groupType, String status, String place) {
		super();
		this.districtId = districtId;
		this.groupAcNo = groupAcNo;
		this.groupName = groupName;
		this.groupType = groupType;
		this.status = status;
		this.place = place;
	}
	public GroupName(long districtId, long groupAcNo, String groupName, String groupType, String status, String place) {
		super();
		this.districtId = (int)districtId;
		this.groupAcNo = (int)groupAcNo;
		this.groupName = groupName;
		this.groupType = groupType;
		this.status = status;
		this.place = place;
	}
	public GroupName(String districtCode, int districtId, int groupAcNo,
			String groupName, String groupType, String status, String place) {
		super();
		this.districtCode = districtCode;
		this.districtId = districtId;
		this.groupAcNo = groupAcNo;
		this.groupName = groupName;
		this.groupType = groupType;
		this.status = status;
		this.place = place;
	}
	public GroupName(String districtCode, long districtId, long groupAcNo,
			String groupName, String groupType, String status, String place) {
		super();
		this.districtCode = districtCode;
		this.districtId = (int)districtId;
		this.groupAcNo = (int)groupAcNo;
		this.groupName = groupName;
		this.groupType = groupType;
		this.status = status;
		this.place = place;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public int getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(int groupAcNo) {
		this.groupAcNo = groupAcNo;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}	
}
