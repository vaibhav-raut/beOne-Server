package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MLoanAc generated by hbm2java
 */
@Entity
@Table(name="monthly_m_loan_ac"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class MonthlyMLoanAc  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 4129936973443680446L;
	private MonthlyMLoanAcId id;
	private String month;
	private long MLoanAcNo;
	private long MAcNo;
	private int fundTypeId;
	private int loanCalculationId;
	private int accountStatusId;
	private int recoveryPeriodId;
	private int loanSourceId;
	private BigDecimal principleAm;
	private BigDecimal pendingPrincipleAm;
	private BigDecimal recInterestAm;
	private BigDecimal projInterestAm;
	private BigDecimal installmentAm;
	private BigDecimal preEmiInterestAm;
	private BigDecimal pendingInterestDueAm;
	private Float rateOfInterest;
	private Integer startupNoOfInst;
	private Integer expNoOfInst;
	private Integer noOfInstPaid;
	private Integer noOfInsallLate;
	private Integer noOfInsallMissed;

	// Constructors

	/** default constructor */
	public MonthlyMLoanAc() {
	}

	/** minimal constructor */
	public MonthlyMLoanAc(long MLoanAcNo, String month) {
		this.MLoanAcNo = MLoanAcNo;
		this.month = month;
	}

	// Property accessors

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name="MLoanAcNo", column=@Column(name="m_loan_ac_no", unique=false, nullable=false, insertable=true, updatable=true) ), 
		@AttributeOverride(name="month", column=@Column(name="month", unique=false, nullable=false, insertable=true, updatable=true) ) } )

	public MonthlyMLoanAcId getId() {
		return this.id;
	}

	public void setId(MonthlyMLoanAcId id) {
		this.id = id;
	}

	@Column(name="month", unique=false, nullable=false, insertable=false, updatable=false, length=10)
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	// Property accessors
	@Column(name="m_loan_ac_no", unique=false, nullable=false, insertable=false, updatable=false)

	public long getMLoanAcNo() {
		return this.MLoanAcNo;
	}

	public void setMLoanAcNo(long MLoanAcNo) {
		this.MLoanAcNo = MLoanAcNo;
	}
	@Column(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getMAcNo() {
		return this.MAcNo;
	}

	public void setMAcNo(long MAcNo) {
		this.MAcNo = MAcNo;
	}
	@Column(name="fund_type_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getFundTypeId() {
		return this.fundTypeId;
	}

	public void setFundTypeId(int fundTypeId) {
		this.fundTypeId = fundTypeId;
	}
	@Column(name="loan_calculation_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getLoanCalculationId() {
		return this.loanCalculationId;
	}

	public void setLoanCalculationId(int loanCalculationId) {
		this.loanCalculationId = loanCalculationId;
	}
	@Column(name="account_status_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getAccountStatusId() {
		return this.accountStatusId;
	}

	public void setAccountStatusId(int accountStatusId) {
		this.accountStatusId = accountStatusId;
	}
	@Column(name="recovery_period_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getRecoveryPeriodId() {
		return this.recoveryPeriodId;
	}

	public void setRecoveryPeriodId(int recoveryPeriodId) {
		this.recoveryPeriodId = recoveryPeriodId;
	}
	@Column(name="loan_source_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getLoanSourceId() {
		return this.loanSourceId;
	}

	public void setLoanSourceId(int loanSourceId) {
		this.loanSourceId = loanSourceId;
	}
	@Column(name="principle_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPrincipleAm() {
		return this.principleAm;
	}

	public void setPrincipleAm(BigDecimal principleAm) {
		this.principleAm = principleAm;
	}
	@Column(name="pending_principle_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPendingPrincipleAm() {
		return this.pendingPrincipleAm;
	}

	public void setPendingPrincipleAm(BigDecimal pendingPrincipleAm) {
		this.pendingPrincipleAm = pendingPrincipleAm;
	}
	@Column(name="rec_interest_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getRecInterestAm() {
		return this.recInterestAm;
	}

	public void setRecInterestAm(BigDecimal recInterestAm) {
		this.recInterestAm = recInterestAm;
	}
	@Column(name="proj_interest_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getProjInterestAm() {
		return this.projInterestAm;
	}

	public void setProjInterestAm(BigDecimal projInterestAm) {
		this.projInterestAm = projInterestAm;
	}
	@Column(name="installment_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getInstallmentAm() {
		return this.installmentAm;
	}

	public void setInstallmentAm(BigDecimal installmentAm) {
		this.installmentAm = installmentAm;
	}
	@Column(name="pre_emi_interest_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPreEmiInterestAm() {
		return this.preEmiInterestAm;
	}

	public void setPreEmiInterestAm(BigDecimal preEmiInterestAm) {
		this.preEmiInterestAm = preEmiInterestAm;
	}
	@Column(name="pending_interest_due_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPendingInterestDueAm() {
		return this.pendingInterestDueAm;
	}

	public void setPendingInterestDueAm(BigDecimal pendingInterestDueAm) {
		this.pendingInterestDueAm = pendingInterestDueAm;
	}
	@Column(name="rate_of_interest", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public Float getRateOfInterest() {
		return this.rateOfInterest;
	}

	public void setRateOfInterest(Float rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}
	@Column(name="startup_no_of_inst", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getStartupNoOfInst() {
		return this.startupNoOfInst;
	}

	public void setStartupNoOfInst(Integer startupNoOfInst) {
		this.startupNoOfInst = startupNoOfInst;
	}
	@Column(name="exp_no_of_inst", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getExpNoOfInst() {
		return this.expNoOfInst;
	}

	public void setExpNoOfInst(Integer expNoOfInst) {
		this.expNoOfInst = expNoOfInst;
	}
	@Column(name="no_of_inst_paid", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfInstPaid() {
		return this.noOfInstPaid;
	}

	public void setNoOfInstPaid(Integer noOfInstPaid) {
		this.noOfInstPaid = noOfInstPaid;
	}
	@Column(name="no_of_insall_late", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfInsallLate() {
		return this.noOfInsallLate;
	}

	public void setNoOfInsallLate(Integer noOfInsallLate) {
		this.noOfInsallLate = noOfInsallLate;
	}
	@Column(name="no_of_insall_missed", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfInsallMissed() {
		return this.noOfInsallMissed;
	}

	public void setNoOfInsallMissed(Integer noOfInsallMissed) {
		this.noOfInsallMissed = noOfInsallMissed;
	}
}