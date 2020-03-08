package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MrStatus
 */
@Entity
@Table(name="mr_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class MrStatus  implements java.io.Serializable {

	private static final long serialVersionUID = 3053613343492767762L;
	// Fields    
	private int mrStatusId;
	private String mrStatus;
	private String mrStatusDesc;
		private String nextStatus;

	// Constructors

	/** default constructor */
	public MrStatus() {
	}

	/** minimal constructor */
	public MrStatus(int mrStatusId, String mrStatus) {
		this.mrStatusId = mrStatusId;
		this.mrStatus = mrStatus;
	}

	/** full constructor */
	public MrStatus(String mrStatus, String mrStatusDesc,
			String nextStatus) {
		super();
		this.mrStatus = mrStatus;
		this.mrStatusDesc = mrStatusDesc;
		this.nextStatus = nextStatus;
	}

	/** full constructor */
	public MrStatus(int mrStatusId, String mrStatus,
			String mrStatusDesc, String nextStatus) {
		super();
		this.mrStatusId = mrStatusId;
		this.mrStatus = mrStatus;
		this.mrStatusDesc = mrStatusDesc;
		this.nextStatus = nextStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="mr_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getMrStatusId() {
		return this.mrStatusId;
	}

	public void setMrStatusId(int mrStatusId) {
		this.mrStatusId = mrStatusId;
	}
	@Column(name="mr_status", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getMrStatus() {
		return this.mrStatus;
	}

	public void setMrStatus(String mrStatus) {
		this.mrStatus = mrStatus;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getMrStatusDesc() {
		return this.mrStatusDesc;
	}

	public void setMrStatusDesc(String mrStatusDesc) {
		this.mrStatusDesc = mrStatusDesc;
	}
	@Column(name="next_status", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}
}
