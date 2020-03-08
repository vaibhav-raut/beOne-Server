package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PkgType
 */
@Entity
@Table(name="pkg_type"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class PkgType  implements java.io.Serializable {

	private static final long serialVersionUID = -1416377438555699902L;
	// Fields    
	private int pkgTypeId;
	private String pkgType;
	private String pkgTypeDesc;

	// Constructors

	/** default constructor */
	public PkgType() {
	}

	/** minimal constructor */
	public PkgType(int pkgTypeId, String pkgType) {
		this.pkgTypeId = pkgTypeId;
		this.pkgType = pkgType;
	}

	/** full constructor */
	public PkgType(String pkgType, String pkgTypeDesc) {
		this.pkgType = pkgType;
		this.pkgTypeDesc = pkgTypeDesc;
	}

	/** full constructor */
	public PkgType(int pkgTypeId, String pkgType, String pkgTypeDesc) {
		this.pkgTypeId = pkgTypeId;
		this.pkgType = pkgType;
		this.pkgTypeDesc = pkgTypeDesc;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pkg_type_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getPkgTypeId() {
		return this.pkgTypeId;
	}

	public void setPkgTypeId(int pkgTypeId) {
		this.pkgTypeId = pkgTypeId;
	}
	@Column(name="pkg_type", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getPkgType() {
		return this.pkgType;
	}

	public void setPkgType(String pkgType) {
		this.pkgType = pkgType;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getPkgTypeDesc() {
		return this.pkgTypeDesc;
	}

	public void setPkgTypeDesc(String pkgTypeDesc) {
		this.pkgTypeDesc = pkgTypeDesc;
	}
}
