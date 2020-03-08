package com.beone.udaan.mr.persistence.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pkg"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class Pkg implements java.io.Serializable {

	private static final long serialVersionUID = 5655118506771796434L;
	
	private long pkgId;
	private int pkgStatusId;
	private int pkgTypeId;
	private Pkg superPkg;
	private PMAc owner;
	private String name;
	private BigDecimal value;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pkg_id", unique=true, nullable=false, insertable=true, updatable=true)
	public long getPkgId() {
		return pkgId;
	}
	public void setPkgId(long pkgId) {
		this.pkgId = pkgId;
	}

	@Column(name="pkg_status_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getPkgStatusId() {
		return pkgStatusId;
	}
	public void setPkgStatusId(int pkgStatusId) {
		this.pkgStatusId = pkgStatusId;
	}

	@Column(name="pkg_type_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getPkgTypeId() {
		return pkgTypeId;
	}
	public void setPkgTypeId(int pkgTypeId) {
		this.pkgTypeId = pkgTypeId;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="super_pkg_id", unique=false, nullable=false, insertable=true, updatable=true)
	public Pkg getSuperPkg() {
		return superPkg;
	}
	public void setSuperPkg(Pkg superPkg) {
		this.superPkg = superPkg;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="owner_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public PMAc getOwner() {
		return owner;
	}
	public void setOwner(PMAc owner) {
		this.owner = owner;
	}

	@Column(name="name", unique=false, nullable=false, insertable=true, updatable=true, length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="value_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
