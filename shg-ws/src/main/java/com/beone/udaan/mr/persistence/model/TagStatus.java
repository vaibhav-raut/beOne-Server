package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TagStatus
 */
@Entity
@Table(name="tag_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class TagStatus  implements java.io.Serializable {

	private static final long serialVersionUID = 3053613343492767762L;
	// Fields    
	private int tagStatusId;
	private String tagStatus;
	private String tagStatusDesc;
		private String nextStatus;

	// Constructors

	/** default constructor */
	public TagStatus() {
	}

	/** minimal constructor */
	public TagStatus(int tagStatusId, String tagStatus) {
		this.tagStatusId = tagStatusId;
		this.tagStatus = tagStatus;
	}

	/** full constructor */
	public TagStatus(String tagStatus, String tagStatusDesc,
			String nextStatus) {
		super();
		this.tagStatus = tagStatus;
		this.tagStatusDesc = tagStatusDesc;
		this.nextStatus = nextStatus;
	}

	/** full constructor */
	public TagStatus(int tagStatusId, String tagStatus,
			String tagStatusDesc, String nextStatus) {
		super();
		this.tagStatusId = tagStatusId;
		this.tagStatus = tagStatus;
		this.tagStatusDesc = tagStatusDesc;
		this.nextStatus = nextStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="tag_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getTagStatusId() {
		return this.tagStatusId;
	}

	public void setTagStatusId(int tagStatusId) {
		this.tagStatusId = tagStatusId;
	}
	@Column(name="tag_status", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getTagStatus() {
		return this.tagStatus;
	}

	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getTagStatusDesc() {
		return this.tagStatusDesc;
	}

	public void setTagStatusDesc(String tagStatusDesc) {
		this.tagStatusDesc = tagStatusDesc;
	}
	@Column(name="next_status", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}
}
