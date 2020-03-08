package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AccountStatus generated by hbm2java
 */
@Entity
@Table(name="account_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class AccountStatus  implements java.io.Serializable {

	private static final long serialVersionUID = -7424424928118154315L;
	// Fields    
	private int accountStatusId;
	private String accountStatus;
	private String accountStatusDesc;

	// Constructors

	/** default constructor */
	public AccountStatus() {
	}

	/** minimal constructor */
	public AccountStatus(int accountStatusId, String accountStatus) {
		this.accountStatusId = accountStatusId;
		this.accountStatus = accountStatus;
	}

	/** full constructor */
	public AccountStatus(String accountStatus, String accountStatusDesc) {
		this.accountStatus = accountStatus;
		this.accountStatusDesc = accountStatusDesc;
	}

	/** full constructor */
	public AccountStatus(int accountStatusId, String accountStatus, String accountStatusDesc) {
		this.accountStatusId = accountStatusId;
		this.accountStatus = accountStatus;
		this.accountStatusDesc = accountStatusDesc;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="account_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getAccountStatusId() {
		return this.accountStatusId;
	}

	public void setAccountStatusId(int accountStatusId) {
		this.accountStatusId = accountStatusId;
	}
	@Column(name="account_status", unique=false, nullable=false, insertable=true, updatable=true, length=20)

	public String getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	@Column(name="account_status_desc", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getAccountStatusDesc() {
		return this.accountStatusDesc;
	}

	public void setAccountStatusDesc(String accountStatusDesc) {
		this.accountStatusDesc = accountStatusDesc;
	}
}
