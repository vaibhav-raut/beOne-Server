package com.beone.shg.net.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ws_access_code"
,catalog="shg"
, uniqueConstraints = {  }
		)
public class WsAccessCode  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1859038640347493416L;
	private int accessCodeId;
	private String accessCode;
	private String accessCodeDisplay;
	private String accessCodeCategory;
	private String accessCodeDesc;
	private String accessLevel;

	// Constructors

	/** default constructor */
	public WsAccessCode() {
	}

	public WsAccessCode(String accessCode, String accessCodeDisplay,
			String accessCodeCategory, String accessCodeDesc, String accessLevel) {
		super();
		this.accessCode = accessCode;
		this.accessCodeDisplay = accessCodeDisplay;
		this.accessCodeCategory = accessCodeCategory;
		this.accessCodeDesc = accessCodeDesc;
		this.accessLevel = accessLevel;
	}

	public WsAccessCode(int accessCodeId, String accessCode,
			String accessCodeDisplay, String accessCodeCategory,
			String accessCodeDesc, String accessLevel) {
		super();
		this.accessCodeId = accessCodeId;
		this.accessCode = accessCode;
		this.accessCodeDisplay = accessCodeDisplay;
		this.accessCodeCategory = accessCodeCategory;
		this.accessCodeDesc = accessCodeDesc;
		this.accessLevel = accessLevel;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="access_code_id", unique=true, nullable=false, insertable=true, updatable=true)
	public int getAccessCodeId() {
		return accessCodeId;
	}
	public void setAccessCodeId(int accessCodeId) {
		this.accessCodeId = accessCodeId;
	}

	@Column(name="access_code", unique=false, nullable=false, insertable=true, updatable=true, length=45)
	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	@Column(name="access_code_display", unique=false, nullable=false, insertable=true, updatable=true, length=45)
	public String getAccessCodeDisplay() {
		return accessCodeDisplay;
	}

	public void setAccessCodeDisplay(String accessCodeDisplay) {
		this.accessCodeDisplay = accessCodeDisplay;
	}

	@Column(name="access_code_category", unique=false, nullable=false, insertable=true, updatable=true, length=45)
	public String getAccessCodeCategory() {
		return accessCodeCategory;
	}

	public void setAccessCodeCategory(String accessCodeCategory) {
		this.accessCodeCategory = accessCodeCategory;
	}

	@Column(name="access_code_desc", unique=false, nullable=false, insertable=true, updatable=true, length=200)
	public String getAccessCodeDesc() {
		return accessCodeDesc;
	}

	public void setAccessCodeDesc(String accessCodeDesc) {
		this.accessCodeDesc = accessCodeDesc;
	}

	@Column(name="access_level", unique=false, nullable=false, insertable=true, updatable=true, length=45)
	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
}
