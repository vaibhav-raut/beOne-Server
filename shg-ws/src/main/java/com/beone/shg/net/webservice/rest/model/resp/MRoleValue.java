package com.beone.shg.net.webservice.rest.model.resp;

public class MRoleValue {

	private int roleId;
	private String role;
	private String roleCategory;
	private String belongTo;
	private String system;
	private String roleDesc;
	private long defaultWsAccessRights;
	private long defaultUiAccessRights;
	
	public MRoleValue() {
	}
	
	public MRoleValue(int roleId, String role, String roleCategory,
			String belongTo, String system, String roleDesc,
			long defaultWsAccessRights, long defaultUiAccessRights) {
		super();
		this.roleId = roleId;
		this.role = role;
		this.roleCategory = roleCategory;
		this.belongTo = belongTo;
		this.system = system;
		this.roleDesc = roleDesc;
		this.defaultWsAccessRights = defaultWsAccessRights;
		this.defaultUiAccessRights = defaultUiAccessRights;
	}

	public MRoleValue(MRoleValue value) {
		this.roleId = value.roleId;
		this.role = value.role;
		this.roleCategory = value.roleCategory;
		this.belongTo = value.belongTo;
		this.roleDesc = value.roleDesc;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleCategory() {
		return roleCategory;
	}

	public void setRoleCategory(String roleCategory) {
		this.roleCategory = roleCategory;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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

}
