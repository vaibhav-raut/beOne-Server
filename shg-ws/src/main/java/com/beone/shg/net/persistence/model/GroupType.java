package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GroupType generated by hbm2java
 */
@Entity
@Table(name="group_type"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class GroupType  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 7644409085511743236L;
	private int groupTypeId;
	private String groupType;
	private String category;
	private long defaultWsAccessRights;
	private long defaultUiAccessRights;
	private String groupTypeDesc;

	// Constructors

	/** default constructor */
	public GroupType() {
	}

	// Property accessors
	@Id
	@Column(name="group_type_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getGroupTypeId() {
		return this.groupTypeId;
	}

	public void setGroupTypeId(int groupTypeId) {
		this.groupTypeId = groupTypeId;
	}
	@Column(name="group_type", unique=false, nullable=false, insertable=true, updatable=true, length=20)

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	@Column(name="category", unique=false, nullable=false, insertable=true, updatable=true, length=20)

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	@Column(name="default_ws_access_rights", unique=false, nullable=true, insertable=true, updatable=true)

	public long getDefaultWsAccessRights() {
		return defaultWsAccessRights;
	}

	public void setDefaultWsAccessRights(long defaultWsAccessRights) {
		this.defaultWsAccessRights = defaultWsAccessRights;
	}
	@Column(name="default_ui_access_rights", unique=false, nullable=true, insertable=true, updatable=true)

	public long getDefaultUiAccessRights() {
		return defaultUiAccessRights;
	}

	public void setDefaultUiAccessRights(long defaultUiAccessRights) {
		this.defaultUiAccessRights = defaultUiAccessRights;
	}
	@Column(name="group_type_desc", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getGroupTypeDesc() {
		return this.groupTypeDesc;
	}

	public void setGroupTypeDesc(String groupTypeDesc) {
		this.groupTypeDesc = groupTypeDesc;
	}
}