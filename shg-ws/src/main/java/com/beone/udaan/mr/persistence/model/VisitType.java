package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VisitType
 */
@Entity
@Table(name="visit_type"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class VisitType  implements java.io.Serializable {

	private static final long serialVersionUID = 3053613343492767762L;
	// Fields    
	private int visitTypeId;
	private String visitType;
	private String visitFor;
	private String visitTypeDesc;

	// Constructors

	/** default constructor */
	public VisitType() {
	}

	/** minimal constructor */
	public VisitType(int visitTypeId, String visitType) {
		this.visitTypeId = visitTypeId;
		this.visitType = visitType;
	}

	/** full constructor */
	public VisitType(String visitType, String visitFor, String visitTypeDesc) {
		super();
		this.visitType = visitType;
		this.visitFor = visitFor;
		this.visitTypeDesc = visitTypeDesc;
	}

	/** full constructor */
	public VisitType(int visitTypeId, String visitType, String visitFor, String visitTypeDesc) {
		super();
		this.visitTypeId = visitTypeId;
		this.visitType = visitType;
		this.visitFor = visitFor;
		this.visitTypeDesc = visitTypeDesc;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="visit_type_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getVisitTypeId() {
		return this.visitTypeId;
	}

	public void setVisitTypeId(int visitTypeId) {
		this.visitTypeId = visitTypeId;
	}
	@Column(name="visit_type", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getVisitType() {
		return this.visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	@Column(name="visit_for", unique=false, nullable=true, insertable=true, updatable=true, length=50)

	public String getVisitFor() {
		return visitFor;
	}

	public void setVisitFor(String visitFor) {
		this.visitFor = visitFor;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getVisitTypeDesc() {
		return this.visitTypeDesc;
	}

	public void setVisitTypeDesc(String visitTypeDesc) {
		this.visitTypeDesc = visitTypeDesc;
	}
}
