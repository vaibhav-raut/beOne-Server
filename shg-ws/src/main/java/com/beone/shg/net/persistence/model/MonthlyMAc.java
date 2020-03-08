package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:19 PM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="monthly_m_ac"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class MonthlyMAc  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -291382533954468348L;
	private MonthlyMAcId id;
	private String month;
	private long MAcNo;
	private Float creditRating;
	private Float meetingAttendance;
	private BigDecimal plannedMonthlySavingAm;
	private BigDecimal savedAm;
	private BigDecimal outstandingSavedAm;
	private BigDecimal provIntEnAm;
	private BigDecimal prevMonthIntEnAm;
	private BigDecimal returnedSavedAm;
	private BigDecimal returnedIntEnAm;
	private BigDecimal dividedProfitDeclaredAm;
	private BigDecimal dividedProfitPaidAm;
	private Integer noOfLoans;
	private Integer noOfActiveLoans;
	private BigDecimal loanAm;
	private BigDecimal recLoanAm;
	private BigDecimal recIntOnLoanAm;
	private BigDecimal projIntOnLoanAm;
	private BigDecimal badDeptClosedAm;
	private BigDecimal recPenaltyAm;
	private BigDecimal pendingPenaltyAm;
	private Integer meetingAttended;
	private Integer meetingMissed;
	private Date lastUpdatedTs;

	// Constructors
	/** default constructor */
	public MonthlyMAc() {
	}

	/** minimal constructor */
	public MonthlyMAc(long MAcNo, String month) {
		this.MAcNo = MAcNo;
		this.month = month;
	}

	// Property accessors

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name="MAcNo", column=@Column(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true) ), 
		@AttributeOverride(name="month", column=@Column(name="month", unique=false, nullable=false, insertable=true, updatable=true) ) } )

	public MonthlyMAcId getId() {
		return this.id;
	}

	public void setId(MonthlyMAcId id) {
		this.id = id;
	}
	@Column(name="m_ac_no", unique=false, nullable=false, insertable=false, updatable=false)

	public long getMAcNo() {
		return this.MAcNo;
	}

	public void setMAcNo(long MAcNo) {
		this.MAcNo = MAcNo;
	}

	@Column(name="month", unique=false, nullable=false, insertable=false, updatable=false, length=10)
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	@Column(name="credit_rating", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public Float getCreditRating() {
		return this.creditRating;
	}

	public void setCreditRating(Float creditRating) {
		this.creditRating = creditRating;
	}
	@Column(name="meeting_attendance", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public Float getMeetingAttendance() {
		return this.meetingAttendance;
	}

	public void setMeetingAttendance(Float meetingAttendance) {
		this.meetingAttendance = meetingAttendance;
	}
	@Column(name="planned_monthly_saving_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPlannedMonthlySavingAm() {
		return plannedMonthlySavingAm;
	}

	public void setPlannedMonthlySavingAm(BigDecimal plannedMonthlySavingAm) {
		this.plannedMonthlySavingAm = plannedMonthlySavingAm;
	}
	@Column(name="saved_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getSavedAm() {
		return this.savedAm;
	}

	public void setSavedAm(BigDecimal savedAm) {
		this.savedAm = savedAm;
	}
	@Column(name="outstanding_saving_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getOutstandingSavedAm() {
		return outstandingSavedAm;
	}

	public void setOutstandingSavedAm(BigDecimal outstandingSavedAm) {
		this.outstandingSavedAm = outstandingSavedAm;
	}
	@Column(name="prov_int_en_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getProvIntEnAm() {
		return this.provIntEnAm;
	}

	public void setProvIntEnAm(BigDecimal provIntEnAm) {
		this.provIntEnAm = provIntEnAm;
	}
	@Column(name="prev_month_int_en_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPrevMonthIntEnAm() {
		return prevMonthIntEnAm;
	}

	public void setPrevMonthIntEnAm(BigDecimal prevMonthIntEnAm) {
		this.prevMonthIntEnAm = prevMonthIntEnAm;
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
	@Column(name="divided_profit_declared_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getDividedProfitDeclaredAm() {
		return this.dividedProfitDeclaredAm;
	}

	public void setDividedProfitDeclaredAm(BigDecimal dividedProfitDeclaredAm) {
		this.dividedProfitDeclaredAm = dividedProfitDeclaredAm;
	}
	@Column(name="divided_profit_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getDividedProfitPaidAm() {
		return this.dividedProfitPaidAm;
	}

	public void setDividedProfitPaidAm(BigDecimal dividedProfitPaidAm) {
		this.dividedProfitPaidAm = dividedProfitPaidAm;
	}
	@Column(name="no_of_loans", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfLoans() {
		return noOfLoans;
	}

	public void setNoOfLoans(Integer noOfLoans) {
		this.noOfLoans = noOfLoans;
	}
	@Column(name="no_of_active_loans", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfActiveLoans() {
		return noOfActiveLoans;
	}

	public void setNoOfActiveLoans(Integer noOfActiveLoans) {
		this.noOfActiveLoans = noOfActiveLoans;
	}

	@Column(name="loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getLoanAm() {
		return this.loanAm;
	}

	public void setLoanAm(BigDecimal loanAm) {
		this.loanAm = loanAm;
	}
	@Column(name="rec_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getRecLoanAm() {
		return this.recLoanAm;
	}

	public void setRecLoanAm(BigDecimal recLoanAm) {
		this.recLoanAm = recLoanAm;
	}
	@Column(name="rec_int_on_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getRecIntOnLoanAm() {
		return this.recIntOnLoanAm;
	}

	public void setRecIntOnLoanAm(BigDecimal recIntOnLoanAm) {
		this.recIntOnLoanAm = recIntOnLoanAm;
	}
	@Column(name="proj_int_on_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getProjIntOnLoanAm() {
		return this.projIntOnLoanAm;
	}

	public void setProjIntOnLoanAm(BigDecimal projIntOnLoanAm) {
		this.projIntOnLoanAm = projIntOnLoanAm;
	}
	@Column(name="bad_dept_closed_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getBadDeptClosedAm() {
		return badDeptClosedAm;
	}

	public void setBadDeptClosedAm(BigDecimal badDeptClosedAm) {
		this.badDeptClosedAm = badDeptClosedAm;
	}

	@Column(name="rec_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getRecPenaltyAm() {
		return this.recPenaltyAm;
	}

	public void setRecPenaltyAm(BigDecimal recPenaltyAm) {
		this.recPenaltyAm = recPenaltyAm;
	}
	@Column(name="pending_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPendingPenaltyAm() {
		return this.pendingPenaltyAm;
	}

	public void setPendingPenaltyAm(BigDecimal pendingPenaltyAm) {
		this.pendingPenaltyAm = pendingPenaltyAm;
	}
	@Column(name="meeting_attended", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getMeetingAttended() {
		return meetingAttended;
	}

	public void setMeetingAttended(Integer meetingAttended) {
		this.meetingAttended = meetingAttended;
	}
	@Column(name="meeting_missed", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getMeetingMissed() {
		return meetingMissed;
	}

	public void setMeetingMissed(Integer meetingMissed) {
		this.meetingMissed = meetingMissed;
	}
	@Column(name="last_updated_ts", unique=false, nullable=true, insertable=true, updatable=true, length=19)

	public Date getLastUpdatedTs() {
		return this.lastUpdatedTs;
	}

	public void setLastUpdatedTs(Date lastUpdatedTs) {
		this.lastUpdatedTs = lastUpdatedTs;
	}
}
