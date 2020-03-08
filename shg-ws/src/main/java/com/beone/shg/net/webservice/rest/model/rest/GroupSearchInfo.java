package com.beone.shg.net.webservice.rest.model.rest;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GroupSearchInfo {
	private long memberAcNo;
	private String searchDistrictCode;
	private long searchGroupNo;
	private String searchGroupNameShort;
	private String groupType;
	private List<GroupName> foundGroups;
	
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public String getSearchDistrictCode() {
		return searchDistrictCode;
	}
	public void setSearchDistrictCode(String searchDistrictCode) {
		this.searchDistrictCode = searchDistrictCode;
	}
	public long getSearchGroupNo() {
		return searchGroupNo;
	}
	public void setSearchGroupNo(long searchGroupNo) {
		this.searchGroupNo = searchGroupNo;
	}
	public String getSearchGroupNameShort() {
		return searchGroupNameShort;
	}
	public void setSearchGroupNameShort(String searchGroupNameShort) {
		this.searchGroupNameShort = searchGroupNameShort;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public List<GroupName> getFoundGroups() {
		return foundGroups;
	}
	public void setFoundGroups(List<GroupName> foundGroups) {
		this.foundGroups = foundGroups;
	}
	public void addFoundGroup(GroupName foundGroup) {
		if(this.foundGroups == null) {
			this.foundGroups = new ArrayList<GroupName>();
		}
		this.foundGroups.add(foundGroup);
	}
}
