package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PkgStatus
 */
@Entity
@Table(name="pkg_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class PkgStatus  implements java.io.Serializable {

	private static final long serialVersionUID = 4605539155541258690L;
	// Fields    
	private int pkgStatusId;
	private String pkgStatus;
	private String pkgStatusDesc;
	private String nextStatus;

	// Constructors

	/** default constructor */
	public PkgStatus() {
	}

	/** minimal constructor */
	public PkgStatus(int pkgStatusId, String pkgStatus) {
		this.pkgStatusId = pkgStatusId;
		this.pkgStatus = pkgStatus;
	}

	/** full constructor */
	public PkgStatus(String pkgStatus, String pkgStatusDesc, String nextStatus) {
		super();
		this.pkgStatus = pkgStatus;
		this.pkgStatusDesc = pkgStatusDesc;
		this.nextStatus = nextStatus;
	}

	/** full constructor */
	public PkgStatus(int pkgStatusId, String pkgStatus, String pkgStatusDesc,
			String nextStatus) {
		super();
		this.pkgStatusId = pkgStatusId;
		this.pkgStatus = pkgStatus;
		this.pkgStatusDesc = pkgStatusDesc;
		this.nextStatus = nextStatus;
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pkg_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getPkgStatusId() {
		return this.pkgStatusId;
	}

	public void setPkgStatusId(int pkgStatusId) {
		this.pkgStatusId = pkgStatusId;
	}
	@Column(name="pkg_status", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getPkgStatus() {
		return this.pkgStatus;
	}

	public void setPkgStatus(String pkgStatus) {
		this.pkgStatus = pkgStatus;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getPkgStatusDesc() {
		return this.pkgStatusDesc;
	}

	public void setPkgStatusDesc(String pkgStatusDesc) {
		this.pkgStatusDesc = pkgStatusDesc;
	}
	@Column(name="next_status", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}
}
