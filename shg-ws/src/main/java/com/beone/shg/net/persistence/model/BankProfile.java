	package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:18 PM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * BankProfile generated by hbm2java
 */
@Entity
@Table(name="bank_profile"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class BankProfile  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 4567624361417457307L;
	private long GAcNo;
	private String ifcsCode;
	private String bankName;
	private String branchName;
	private Float bankLoanIntRate;
	private Float bankFdIntRate;
	private Float bankRating;
	private Integer loanProcessingFee;
	private Integer loanApplicationCharges;
	private Integer loanPrepaymentCharges;
	private Integer latePaymentCharges;
	private Integer missedPaymentCharges;
	private GProfile GProfile;

	// Constructors

	/** default constructor */
	public BankProfile() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BankProfileIdGenerator")
	@GenericGenerator(name = "BankProfileIdGenerator", strategy = "com.beone.shg.net.persistence.generator.BankProfileIdGenerator")
	@Column(name="g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getGAcNo() {
		return GAcNo;
	}
	public void setGAcNo(long GAcNo) {
		this.GAcNo = GAcNo;
	}
	@Column(name="ifcs_code", unique=false, nullable=false, insertable=true, updatable=true, length=12)

	public String getIfcsCode() {
		return this.ifcsCode;
	}

	public void setIfcsCode(String ifcsCode) {
		this.ifcsCode = ifcsCode;
	}
	@Column(name="bank_name", unique=false, nullable=false, insertable=true, updatable=true, length=100)

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Column(name="branch_name", unique=false, nullable=false, insertable=true, updatable=true, length=100)

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	@Column(name="bank_loan_int_rate", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public Float getBankLoanIntRate() {
		return this.bankLoanIntRate;
	}

	public void setBankLoanIntRate(Float bankLoanIntRate) {
		this.bankLoanIntRate = bankLoanIntRate;
	}
	@Column(name="bank_fd_int_rate", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public Float getBankFdIntRate() {
		return this.bankFdIntRate;
	}

	public void setBankFdIntRate(Float bankFdIntRate) {
		this.bankFdIntRate = bankFdIntRate;
	}
	@Column(name="bank_rating", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public Float getBankRating() {
		return this.bankRating;
	}

	public void setBankRating(Float bankRating) {
		this.bankRating = bankRating;
	}
	@Column(name="loan_processing_fee", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getLoanProcessingFee() {
		return loanProcessingFee;
	}

	public void setLoanProcessingFee(Integer loanProcessingFee) {
		this.loanProcessingFee = loanProcessingFee;
	}
	@Column(name="loan_application_charges", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getLoanApplicationCharges() {
		return loanApplicationCharges;
	}

	public void setLoanApplicationCharges(Integer loanApplicationCharges) {
		this.loanApplicationCharges = loanApplicationCharges;
	}
	@Column(name="loan_prepayment_charges", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getLoanPrepaymentCharges() {
		return loanPrepaymentCharges;
	}

	public void setLoanPrepaymentCharges(Integer loanPrepaymentCharges) {
		this.loanPrepaymentCharges = loanPrepaymentCharges;
	}
	@Column(name="late_payment_charges", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getLatePaymentCharges() {
		return latePaymentCharges;
	}

	public void setLatePaymentCharges(Integer latePaymentCharges) {
		this.latePaymentCharges = latePaymentCharges;
	}
	@Column(name="missed_payment_charges", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getMissedPaymentCharges() {
		return missedPaymentCharges;
	}

	public void setMissedPaymentCharges(Integer missedPaymentCharges) {
		this.missedPaymentCharges = missedPaymentCharges;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="g_ac_no", unique=false, nullable=false, insertable=false, updatable=false)

	public GProfile getGProfile() {
		return this.GProfile;
	}

	public void setGProfile(GProfile GProfile) {
		this.GProfile = GProfile;
	}
}
