package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:19 PM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ApprovalStatus generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name="approval_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ApprovalStatus  implements java.io.Serializable {

	// Fields    
//	private static final long serialVersionUID = -6526730706335176679L;
	private int approvalStatusId;
	private String approvalStatus;
	private String approvalStatusDesc;


	// Constructors

	/** default constructor */
	public ApprovalStatus() {
	}

	/** minimal constructor */
	public ApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/** full constructor */
	public ApprovalStatus(int approvalStatusId, String approvalStatus) {
		this.approvalStatusId = approvalStatusId;
		this.approvalStatus = approvalStatus;
	}

	/** full constructor */
	public ApprovalStatus(String approvalStatus, String approvalStatusDesc) {
		this.approvalStatus = approvalStatus;
		this.approvalStatusDesc = approvalStatusDesc;
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="approval_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getApprovalStatusId() {
		return this.approvalStatusId;
	}

	public void setApprovalStatusId(int approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}
	@Column(name="approval_status", unique=false, nullable=false, insertable=true, updatable=true, length=20)

	public String getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	@Column(name="approval_status_desc", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getApprovalStatusDesc() {
		return this.approvalStatusDesc;
	}

	public void setApprovalStatusDesc(String approvalStatusDesc) {
		this.approvalStatusDesc = approvalStatusDesc;
	}
}