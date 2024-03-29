package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:18 PM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ActiveStatus generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name="active_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ActiveStatus  implements java.io.Serializable {

	// Fields    
//	private static final long serialVersionUID = -1581680385245732064L;
	private int activeStatusId;
	private String activeStatus;
	private String activeStatusDesc;


	// Constructors

	/** default constructor */
	public ActiveStatus() {
	}

	/** minimal constructor */
	public ActiveStatus(int activeStatusId, String activeStatus) {
		this.activeStatusId = activeStatusId;
		this.activeStatus = activeStatus;
	}

	/** minimal constructor */
	public ActiveStatus(String activeStatus, String activeStatusDesc) {
		this.activeStatus = activeStatus;
		this.activeStatusDesc = activeStatusDesc;
	}

	/** full constructor */
	public ActiveStatus(int activeStatusId, String activeStatus, String activeStatusDesc) {
		this.activeStatusId = activeStatusId;
		this.activeStatus = activeStatus;
		this.activeStatusDesc = activeStatusDesc;
	}



	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="active_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getActiveStatusId() {
		return this.activeStatusId;
	}

	public void setActiveStatusId(int activeStatusId) {
		this.activeStatusId = activeStatusId;
	}
	@Column(name="active_status", unique=false, nullable=false, insertable=true, updatable=true, length=20)

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	@Column(name="active_status_desc", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getActiveStatusDesc() {
		return this.activeStatusDesc;
	}

	public void setActiveStatusDesc(String activeStatusDesc) {
		this.activeStatusDesc = activeStatusDesc;
	}

}
