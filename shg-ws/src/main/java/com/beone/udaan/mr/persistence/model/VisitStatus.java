package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VisitStatus
 */
@Entity
@Table(name="visit_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class VisitStatus  implements java.io.Serializable {

	private static final long serialVersionUID = 3053613343492767762L;
	// Fields    
	private int visitStatusId;
	private String visitStatus;
	private String visitStatusDesc;
		private String nextStatus;

	// Constructors

	/** default constructor */
	public VisitStatus() {
	}

	/** minimal constructor */
	public VisitStatus(int visitStatusId, String visitStatus) {
		this.visitStatusId = visitStatusId;
		this.visitStatus = visitStatus;
	}

	/** full constructor */
	public VisitStatus(String visitStatus, String visitStatusDesc,
			String nextStatus) {
		super();
		this.visitStatus = visitStatus;
		this.visitStatusDesc = visitStatusDesc;
		this.nextStatus = nextStatus;
	}

	/** full constructor */
	public VisitStatus(int visitStatusId, String visitStatus,
			String visitStatusDesc, String nextStatus) {
		super();
		this.visitStatusId = visitStatusId;
		this.visitStatus = visitStatus;
		this.visitStatusDesc = visitStatusDesc;
		this.nextStatus = nextStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="visit_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getVisitStatusId() {
		return this.visitStatusId;
	}

	public void setVisitStatusId(int visitStatusId) {
		this.visitStatusId = visitStatusId;
	}
	@Column(name="visit_status", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getVisitStatus() {
		return this.visitStatus;
	}

	public void setVisitStatus(String visitStatus) {
		this.visitStatus = visitStatus;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getVisitStatusDesc() {
		return this.visitStatusDesc;
	}

	public void setVisitStatusDesc(String visitStatusDesc) {
		this.visitStatusDesc = visitStatusDesc;
	}
	@Column(name="next_status", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}
}
