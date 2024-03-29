package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AdvanceCheques generated by hbm2java
 */
@Entity
@Table(name="advance_cheques"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class AdvanceCheques  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -5196971376224162259L;
	private int advanceChequesId;
	private BankAccount bankAccount;
	private int chequeNo;
	private Boolean used;
	private Date depositTs;
	private BigDecimal amount;

	// Constructors
	/** default constructor */
	public AdvanceCheques() {
	}

	/** minimal constructor */
	public AdvanceCheques(int advanceChequesId, BankAccount bankAccount, int chequeNo) {
		this.advanceChequesId = advanceChequesId;
		this.bankAccount = bankAccount;
		this.chequeNo = chequeNo;
	}

	/** full constructor */
	public AdvanceCheques(int advanceChequesId, BankAccount bankAccount, int chequeNo, Boolean used, Date depositTs) {
		this.advanceChequesId = advanceChequesId;
		this.bankAccount = bankAccount;
		this.chequeNo = chequeNo;
		this.used = used;
		this.depositTs = depositTs;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="advance_cheques_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getAdvanceChequesId() {
		return this.advanceChequesId;
	}

	public void setAdvanceChequesId(int advanceChequesId) {
		this.advanceChequesId = advanceChequesId;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="bank_account_no", unique=false, nullable=false, insertable=true, updatable=true)

	public BankAccount getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	@Column(name="cheque_no", unique=false, nullable=false, insertable=true, updatable=true)

	public int getChequeNo() {
		return this.chequeNo;
	}

	public void setChequeNo(int chequeNo) {
		this.chequeNo = chequeNo;
	}
	@Column(name="used", unique=false, nullable=true, insertable=true, updatable=true)

	public Boolean getUsed() {
		return this.used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}
	@Column(name="deposit_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)

	public Date getDepositTs() {
		return this.depositTs;
	}

	public void setDepositTs(Date depositTs) {
		this.depositTs = depositTs;
	}
	@Column(name="amount", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
