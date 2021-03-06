package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:19 PM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MRole generated by hbm2java
 */
@Entity
@Table(name="m_role"
,catalog="shg"
, uniqueConstraints = {  }
		)
public class MRole  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1859038640347493416L;
	private int MRoleId;
	private String MRole;
	private String MRoleCategory;
	private String belongTo;
	private String system;
	private String MRoleDesc;
	private long defaultWsAccessRights;
	private long defaultUiAccessRights;

	// Constructors

	/** default constructor */
	public MRole() {
	}

	/** minimal constructor */
	public MRole(String mRole, String mRoleCategory, String belongTo,
			String system, String mRoleDesc, long defaultWsAccessRights,
			long defaultUiAccessRights) {
		super();
		MRole = mRole;
		MRoleCategory = mRoleCategory;
		this.belongTo = belongTo;
		this.system = system;
		MRoleDesc = mRoleDesc;
		this.defaultWsAccessRights = defaultWsAccessRights;
		this.defaultUiAccessRights = defaultUiAccessRights;
	}

	/** full constructor */
	public MRole(int mRoleId, String mRole, String mRoleCategory,
			String belongTo, String system, String mRoleDesc,
			long defaultWsAccessRights, long defaultUiAccessRights) {
		super();
		MRoleId = mRoleId;
		MRole = mRole;
		MRoleCategory = mRoleCategory;
		this.belongTo = belongTo;
		this.system = system;
		MRoleDesc = mRoleDesc;
		this.defaultWsAccessRights = defaultWsAccessRights;
		this.defaultUiAccessRights = defaultUiAccessRights;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="m_role_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getMRoleId() {
		return this.MRoleId;
	}

	public void setMRoleId(int MRoleId) {
		this.MRoleId = MRoleId;
	}
	@Column(name="m_role", unique=false, nullable=false, insertable=true, updatable=true, length=30)

	public String getMRole() {
		return this.MRole;
	}

	public void setMRole(String MRole) {
		this.MRole = MRole;
	}
	@Column(name="m_role_category", unique=false, nullable=false, insertable=true, updatable=true, length=20)

	public String getMRoleCategory() {
		return this.MRoleCategory;
	}

	public void setMRoleCategory(String MRoleCategory) {
		this.MRoleCategory = MRoleCategory;
	}
	@Column(name="belong_to", unique=false, nullable=false, insertable=true, updatable=true, length=20)

	public String getBelongTo() {
		return this.belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}
	@Column(name="system", unique=false, nullable=false, insertable=true, updatable=true, length=20)

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Column(name="m_role_desc", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getMRoleDesc() {
		return this.MRoleDesc;
	}

	public void setMRoleDesc(String MRoleDesc) {
		this.MRoleDesc = MRoleDesc;
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

}
