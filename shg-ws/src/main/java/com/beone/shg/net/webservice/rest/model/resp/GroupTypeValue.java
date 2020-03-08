package com.beone.shg.net.webservice.rest.model.resp;

public class GroupTypeValue {

	private int groupTypeId;
	private String groupType;
	private String category;
	private long defaultWsAccessRights;
	private long defaultUiAccessRights;
	private String groupTypeDesc;
	
	public GroupTypeValue() {
	}

	public GroupTypeValue(int groupTypeId, String groupType, String category,
			long defaultWsAccessRights, long defaultUiAccessRights,
			String groupTypeDesc) {
		super();
		this.groupTypeId = groupTypeId;
		this.groupType = groupType;
		this.category = category;
		this.defaultWsAccessRights = defaultWsAccessRights;
		this.defaultUiAccessRights = defaultUiAccessRights;
		this.groupTypeDesc = groupTypeDesc;
	}
	public GroupTypeValue(GroupTypeValue value) {
		this.groupTypeId = value.groupTypeId;
		this.groupType = value.groupType;
		this.category = value.category;
		this.groupTypeDesc = value.groupTypeDesc;
	}

	public int getGroupTypeId() {
		return groupTypeId;
	}

	public void setGroupTypeId(int groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getDefaultWsAccessRights() {
		return defaultWsAccessRights;
	}

	public void setDefaultWsAccessRights(long defaultWsAccessRights) {
		this.defaultWsAccessRights = defaultWsAccessRights;
	}

	public long getDefaultUiAccessRights() {
		return defaultUiAccessRights;
	}

	public void setDefaultUiAccessRights(long defaultUiAccessRights) {
		this.defaultUiAccessRights = defaultUiAccessRights;
	}

	public String getGroupTypeDesc() {
		return groupTypeDesc;
	}

	public void setGroupTypeDesc(String groupTypeDesc) {
		this.groupTypeDesc = groupTypeDesc;
	}	
}
