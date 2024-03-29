package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GroupRelation generated by hbm2java
 */
@Entity
@Table(name="group_relation"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class GroupRelation  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -7171543778310890440L;
	private int groupRelationId;
	private String groupRelation;
	private String groupRelationDesc;

	// Constructors

	/** default constructor */
	public GroupRelation() {
	}

	/** minimal constructor */
	public GroupRelation(int groupRelationId, String groupRelation) {
		this.groupRelationId = groupRelationId;
		this.groupRelation = groupRelation;
	}

	/** constructor */
	public GroupRelation(String groupRelation, String groupRelationDesc) {
		this.groupRelation = groupRelation;
		this.groupRelationDesc = groupRelationDesc;
	}

	/** full constructor */
	public GroupRelation(int groupRelationId, String groupRelation, String groupRelationDesc) {
		this.groupRelationId = groupRelationId;
		this.groupRelation = groupRelation;
		this.groupRelationDesc = groupRelationDesc;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="group_relation_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getGroupRelationId() {
		return this.groupRelationId;
	}

	public void setGroupRelationId(int groupRelationId) {
		this.groupRelationId = groupRelationId;
	}
	@Column(name="group_relation", unique=false, nullable=false, insertable=true, updatable=true, length=20)

	public String getGroupRelation() {
		return this.groupRelation;
	}

	public void setGroupRelation(String groupRelation) {
		this.groupRelation = groupRelation;
	}
	@Column(name="group_relation_desc", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getGroupRelationDesc() {
		return this.groupRelationDesc;
	}

	public void setGroupRelationDesc(String groupRelationDesc) {
		this.groupRelationDesc = groupRelationDesc;
	}
}
