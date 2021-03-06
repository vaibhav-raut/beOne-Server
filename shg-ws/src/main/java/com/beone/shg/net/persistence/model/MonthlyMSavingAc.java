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
 * MSavingAc generated by hbm2java
 */
@Entity
@Table(name="monthly_m_saving_ac"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class MonthlyMSavingAc  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -7584246527312248590L;
	private MonthlyMSavingAcId id;
	private String month;
	private long MSavingAcNo;
	private long MAcNo;
	private int accountStatusId;
	private int recoveryPeriodId;
	private BigDecimal savedAm;
	private BigDecimal cumulativeSavedAm;
	private BigDecimal savingInstallmentAm;
	private BigDecimal totalIntEnAm;
	private BigDecimal currentFyIntEnAm;
	private BigDecimal prevMonthIntAm;
	private BigDecimal returnedSavedAm;
	private BigDecimal returnedIntEnAm;
	private Integer expNoOfInst;
	private Integer noOfInstPaid;
	private Integer noOfInsallLate;
	private Integer noOfInsallMissed;

	// Constructors

	/** default constructor */
	public MonthlyMSavingAc() {
	}

	/** minimal constructor */
	public MonthlyMSavingAc(long MSavingAcNo, String month) {
		this.MSavingAcNo = MSavingAcNo;
		this.month = month;
	}

	// Property accessors

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name="MSavingAcNo", column=@Column(name="m_saving_ac_no", unique=false, nullable=false, insertable=true, updatable=true) ), 
		@AttributeOverride(name="month", column=@Column(name="month", unique=false, nullable=false, insertable=true, updatable=true) ) } )

	public MonthlyMSavingAcId getId() {
		return this.id;
	}

	public void setId(MonthlyMSavingAcId id) {
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
	@Column(name="m_saving_ac_no", unique=false, nullable=false, insertable=false, updatable=false)

	public long getMSavingAcNo() {
		return this.MSavingAcNo;
	}

	public void setMSavingAcNo(long MSavingAcNo) {
		this.MSavingAcNo = MSavingAcNo;
	}
	@Column(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getMAcNo() {
		return this.MAcNo;
	}

	public void setMAcNo(long MAcNo) {
		this.MAcNo = MAcNo;
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
	@Column(name="saved_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getSavedAm() {
		return this.savedAm;
	}

	public void setSavedAm(BigDecimal savedAm) {
		this.savedAm = savedAm;
	}
	@Column(name="cumulative_saved_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCumulativeSavedAm() {
		return this.cumulativeSavedAm;
	}

	public void setCumulativeSavedAm(BigDecimal cumulativeSavedAm) {
		this.cumulativeSavedAm = cumulativeSavedAm;
	}
	@Column(name="saving_installment_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getSavingInstallmentAm() {
		return this.savingInstallmentAm;
	}

	public void setSavingInstallmentAm(BigDecimal savingInstallmentAm) {
		this.savingInstallmentAm = savingInstallmentAm;
	}
	@Column(name="total_int_en_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getTotalIntEnAm() {
		return this.totalIntEnAm;
	}

	public void setTotalIntEnAm(BigDecimal totalIntEnAm) {
		this.totalIntEnAm = totalIntEnAm;
	}
	@Column(name="current_fy_int_en_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCurrentFyIntEnAm() {
		return this.currentFyIntEnAm;
	}

	public void setCurrentFyIntEnAm(BigDecimal currentFyIntEnAm) {
		this.currentFyIntEnAm = currentFyIntEnAm;
	}
	@Column(name="prev_month_int_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPrevMonthIntAm() {
		return this.prevMonthIntAm;
	}

	public void setPrevMonthIntAm(BigDecimal prevMonthIntAm) {
		this.prevMonthIntAm = prevMonthIntAm;
	}
	@Column(name="returned_saved_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getReturnedSavedAm() {
		return this.returnedSavedAm;
	}

	public void setReturnedSavedAm(BigDecimal returnedSavedAm) {
		this.returnedSavedAm = returnedSavedAm;
	}
	@Column(name="returned_int_en_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getReturnedIntEnAm() {
		return this.returnedIntEnAm;
	}

	public void setReturnedIntEnAm(BigDecimal returnedIntEnAm) {
		this.returnedIntEnAm = returnedIntEnAm;
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
