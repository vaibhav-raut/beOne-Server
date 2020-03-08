package com.beone.shg.net.persistence.model;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="monthly_g_ac"
,catalog="shg"
		)

public class MonthlyGAc  implements java.io.Serializable {
	// Fields    

	private MonthlyGAcId id;
	private long GAcNo;
	private String month;
	private Float creditRating;
	private Float approvalRating;
	private Float meetingAttendance;
	private BigDecimal CMPlannedMonthlySaving;
	private BigDecimal CMSavedAm;
	private BigDecimal CMOutstandingSavedAm;
	private BigDecimal CMProvIntEnAm;
	private BigDecimal CMReturnedSavedAm;
	private BigDecimal CMReturnedIntEnAm;
	private BigDecimal CMProfitShareDeclaredAm;
	private BigDecimal CMProfitSharePaidAm;
	private Integer CMNoOfLoans;
	private Integer CMNoOfActiveLoans;
	private BigDecimal CMLoanAm;
	private BigDecimal CMPendingLoanAm;
	private BigDecimal CMRecLoanAm;
	private BigDecimal CMRecIntOnLoanAm;
	private BigDecimal CMProjIntOnLoanAm;
	private BigDecimal CMSubStdDeptAm;
	private int CMNoOfSubStdDept;
	private BigDecimal CMBadDeptExpAm;
	private int CMNoOfBadDeptExp;
	private BigDecimal CMBadDeptClosedAm;
	private int CMNoOfBadDeptClosed;
	private BigDecimal AMPlannedMonthlySaving;
	private BigDecimal AMSavedAm;
	private BigDecimal AMOutstandingSavedAm;
	private BigDecimal AMProvIntEnAm;
	private BigDecimal AMReturnedSavedAm;
	private BigDecimal AMReturnedIntEnAm;
	private BigDecimal AMDividedDeclaredAm;
	private BigDecimal AMDividedPaidAm;
	private Integer AMNoOfLoans;
	private Integer AMNoOfActiveLoans;
	private BigDecimal AMLoanAm;
	private BigDecimal AMPendingLoanAm;
	private BigDecimal AMRecLoanAm;
	private BigDecimal AMRecIntOnLoanAm;
	private BigDecimal AMProjIntOnLoanAm;
	private BigDecimal AMSubStdDeptAm;
	private int AMNoOfSubStdDept;
	private BigDecimal AMBadDeptExpAm;
	private int AMNoOfBadDeptExp;
	private BigDecimal AMBadDeptClosedAm;
	private int AMNoOfBadDeptClosed;
	private BigDecimal PLoanAm;
	private BigDecimal PPendingLoanAm;
	private BigDecimal PRecLoanAm;
	private BigDecimal PRecIntOnLoanAm;
	private BigDecimal PProjIntOnLoanAm;
	private Integer noOfProject;
	private Integer noOfActiveProject;
	private BigDecimal fixDepositInvAm;
	private BigDecimal pendingFixDepositAm;
	private BigDecimal recFixDepositAm;
	private BigDecimal recIntOnFixDepositAm;
	private BigDecimal projIntOnFixDepositAm;
	private Integer noOfFixDeposit;
	private Integer noOfActiveFixDeposit;
	private BigDecimal otherInvAm;
	private BigDecimal pendingOtherInvAm;
	private BigDecimal recOtherInvAm;
	private BigDecimal recIntOnOtherInvAm;
	private BigDecimal projIntOnOtherInvAm;
	private Integer noOfOtherInv;
	private Integer noOfActiveOtherInv;
	private BigDecimal intEnOnSavingAcAm;
	private BigDecimal clearBankBalanceAm;
	private BigDecimal subjClearingBankBalanceAm;
	private BigDecimal clearCashInHandAm;
	private BigDecimal subjClearingCashInHandAm;
	private BigDecimal netProfitAm;
	private BigDecimal netProfitProjAm;
	private BigDecimal expensesAm;
	private BigDecimal outstandingExpensesAm;
	private BigDecimal recPenaltyAm;
	private BigDecimal pendingPenaltyAm;
	private BigDecimal borrowedLoanAm;
	private BigDecimal pendingBorrowedLoanAm;
	private BigDecimal paidBorrowedLoanAm;
	private BigDecimal paidIntOnBorrowedLoanAm;
	private BigDecimal projIntOnBorrowedLoanAm;
	private BigDecimal bankSubStdDeptAm;
	private int bankNoOfSubStdDept;
	private BigDecimal bankBadDeptExpAm;
	private int bankNoOfBadDeptExp;
	private BigDecimal bankBadDeptClosedAm;
	private int bankNoOfBadDeptClosed;
	private Integer noOfBankLoan;
	private Integer noOfActiveBankLoan;
	private BigDecimal govRevolvingFundAm;
	private BigDecimal govRevolvingFundReturnedAm;
	private int noOfGovRevolvingFund;
	private int noOfActiveGovRevolvingFund;
	private BigDecimal govDevelopmentFundAm;
	private int noOfGovDevelopmentFund;
	private BigDecimal penShgOneMemRegFee;
	private BigDecimal penShgOneServiceCharges;
	private int noOfTxsMonthlyExp;
	private int noOfTxsMonthlyDone;
	private int noOfTxsMonthlyApproved;
	private String attachmentUrl;

	// Constructors

	/** default constructor */
	public MonthlyGAc() {
	}

	/** minimal constructor */
	public MonthlyGAc(long GAcNo, String month) {
		this.GAcNo = GAcNo;
		this.month = month;
	}

	// Property accessors

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name="GAcNo", column=@Column(name="g_ac_no", unique=false, nullable=false, insertable=true, updatable=true) ), 
		@AttributeOverride(name="month", column=@Column(name="month", unique=false, nullable=false, insertable=true, updatable=true) ) } )

	public MonthlyGAcId getId() {
		return this.id;
	}

	public void setId(MonthlyGAcId id) {
		this.id = id;
	}

	@Column(name="g_ac_no", unique=false, nullable=false, insertable=false, updatable=false)

	public long getGAcNo() {
		return this.GAcNo;
	}

	public void setGAcNo(long GAcNo) {
		this.GAcNo = GAcNo;
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
	@Column(name="approval_rating", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public Float getApprovalRating() {
		return this.approvalRating;
	}

	public void setApprovalRating(Float approvalRating) {
		this.approvalRating = approvalRating;
	}
	@Column(name="meeting_attendance", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public Float getMeetingAttendance() {
		return this.meetingAttendance;
	}

	public void setMeetingAttendance(Float meetingAttendance) {
		this.meetingAttendance = meetingAttendance;
	}
	@Column(name="c_m_planned_monthly_saving", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMPlannedMonthlySaving() {
		return CMPlannedMonthlySaving;
	}

	public void setCMPlannedMonthlySaving(BigDecimal CMPlannedMonthlySaving) {
		this.CMPlannedMonthlySaving = CMPlannedMonthlySaving;
	}
	@Column(name="c_m_saved_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMSavedAm() {
		return this.CMSavedAm;
	}

	public void setCMSavedAm(BigDecimal CMSavedAm) {
		this.CMSavedAm = CMSavedAm;
	}

	@Column(name="c_m_outstanding_saving_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMOutstandingSavedAm() {
		return CMOutstandingSavedAm;
	}

	public void setCMOutstandingSavedAm(BigDecimal cMOutstandingSavedAm) {
		CMOutstandingSavedAm = cMOutstandingSavedAm;
	}
	@Column(name="c_m_prov_int_en_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMProvIntEnAm() {
		return this.CMProvIntEnAm;
	}

	public void setCMProvIntEnAm(BigDecimal CMProvIntEnAm) {
		this.CMProvIntEnAm = CMProvIntEnAm;
	}
	@Column(name="c_m_returned_saved_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMReturnedSavedAm() {
		return this.CMReturnedSavedAm;
	}

	public void setCMReturnedSavedAm(BigDecimal CMReturnedSavedAm) {
		this.CMReturnedSavedAm = CMReturnedSavedAm;
	}
	@Column(name="c_m_returned_int_en_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMReturnedIntEnAm() {
		return this.CMReturnedIntEnAm;
	}

	public void setCMReturnedIntEnAm(BigDecimal CMReturnedIntEnAm) {
		this.CMReturnedIntEnAm = CMReturnedIntEnAm;
	}
	@Column(name="c_m_profit_share_declared_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMProfitShareDeclaredAm() {
		return this.CMProfitShareDeclaredAm;
	}

	public void setCMProfitShareDeclaredAm(BigDecimal CMProfitShareDeclaredAm) {
		this.CMProfitShareDeclaredAm = CMProfitShareDeclaredAm;
	}
	@Column(name="c_m_profit_share_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMProfitSharePaidAm() {
		return this.CMProfitSharePaidAm;
	}

	public void setCMProfitSharePaidAm(BigDecimal CMProfitSharePaidAm) {
		this.CMProfitSharePaidAm = CMProfitSharePaidAm;
	}
	@Column(name="c_m_no_of_loans", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getCMNoOfLoans() {
		return CMNoOfLoans;
	}

	public void setCMNoOfLoans(Integer cMNoOfLoans) {
		CMNoOfLoans = cMNoOfLoans;
	}
	@Column(name="c_m_no_of_active_loans", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getCMNoOfActiveLoans() {
		return CMNoOfActiveLoans;
	}

	public void setCMNoOfActiveLoans(Integer cMNoOfActiveLoans) {
		CMNoOfActiveLoans = cMNoOfActiveLoans;
	}

	@Column(name="c_m_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMLoanAm() {
		return this.CMLoanAm;
	}

	public void setCMLoanAm(BigDecimal CMLoanAm) {
		this.CMLoanAm = CMLoanAm;
	}

	@Column(name="c_m_pending_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMPendingLoanAm() {
		return this.CMPendingLoanAm;
	}

	public void setCMPendingLoanAm(BigDecimal CMPendingLoanAm) {
		this.CMPendingLoanAm = CMPendingLoanAm;
	}
	@Column(name="c_m_rec_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMRecLoanAm() {
		return this.CMRecLoanAm;
	}

	public void setCMRecLoanAm(BigDecimal CMRecLoanAm) {
		this.CMRecLoanAm = CMRecLoanAm;
	}
	@Column(name="c_m_rec_int_on_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMRecIntOnLoanAm() {
		return this.CMRecIntOnLoanAm;
	}

	public void setCMRecIntOnLoanAm(BigDecimal CMRecIntOnLoanAm) {
		this.CMRecIntOnLoanAm = CMRecIntOnLoanAm;
	}
	@Column(name="c_m_proj_int_on_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMProjIntOnLoanAm() {
		return this.CMProjIntOnLoanAm;
	}

	public void setCMProjIntOnLoanAm(BigDecimal CMProjIntOnLoanAm) {
		this.CMProjIntOnLoanAm = CMProjIntOnLoanAm;
	}
	@Column(name="c_m_sub_std_dept_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMSubStdDeptAm() {
		return CMSubStdDeptAm;
	}

	public void setCMSubStdDeptAm(BigDecimal cMSubStdDeptAm) {
		CMSubStdDeptAm = cMSubStdDeptAm;
	}
	@Column(name="c_m_no_of_sub_std_dept", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public int getCMNoOfSubStdDept() {
		return CMNoOfSubStdDept;
	}

	public void setCMNoOfSubStdDept(int cMNoOfSubStdDept) {
		CMNoOfSubStdDept = cMNoOfSubStdDept;
	}
	@Column(name="c_m_bad_dept_exp_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMBadDeptExpAm() {
		return CMBadDeptExpAm;
	}

	public void setCMBadDeptExpAm(BigDecimal cMBadDeptExpAm) {
		CMBadDeptExpAm = cMBadDeptExpAm;
	}
	@Column(name="c_m_no_of_bad_dept_exp", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public int getCMNoOfBadDeptExp() {
		return CMNoOfBadDeptExp;
	}

	public void setCMNoOfBadDeptExp(int cMNoOfBadDeptExp) {
		CMNoOfBadDeptExp = cMNoOfBadDeptExp;
	}
	@Column(name="c_m_bad_dept_closed_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getCMBadDeptClosedAm() {
		return CMBadDeptClosedAm;
	}

	public void setCMBadDeptClosedAm(BigDecimal cMBadDeptClosedAm) {
		CMBadDeptClosedAm = cMBadDeptClosedAm;
	}
	@Column(name="c_m_no_of_bad_dept_closed", unique=false, nullable=true, insertable=true, updatable=true)

	public int getCMNoOfBadDeptClosed() {
		return CMNoOfBadDeptClosed;
	}

	public void setCMNoOfBadDeptClosed(int CMNoOfBadDeptClosed) {
		this.CMNoOfBadDeptClosed = CMNoOfBadDeptClosed;
	}
	@Column(name="a_m_planned_monthly_saving", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMPlannedMonthlySaving() {
		return AMPlannedMonthlySaving;
	}

	public void setAMPlannedMonthlySaving(BigDecimal AMPlannedMonthlySaving) {
		this.AMPlannedMonthlySaving = AMPlannedMonthlySaving;
	}
	@Column(name="a_m_saved_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMSavedAm() {
		return this.AMSavedAm;
	}

	public void setAMSavedAm(BigDecimal AMSavedAm) {
		this.AMSavedAm = AMSavedAm;
	}

	@Column(name="a_m_outstanding_saving_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMOutstandingSavedAm() {
		return AMOutstandingSavedAm;
	}

	public void setAMOutstandingSavedAm(BigDecimal aMOutstandingSavedAm) {
		AMOutstandingSavedAm = aMOutstandingSavedAm;
	}
	@Column(name="a_m_prov_int_en_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMProvIntEnAm() {
		return this.AMProvIntEnAm;
	}

	public void setAMProvIntEnAm(BigDecimal AMProvIntEnAm) {
		this.AMProvIntEnAm = AMProvIntEnAm;
	}
	@Column(name="a_m_returned_saved_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMReturnedSavedAm() {
		return this.AMReturnedSavedAm;
	}

	public void setAMReturnedSavedAm(BigDecimal AMReturnedSavedAm) {
		this.AMReturnedSavedAm = AMReturnedSavedAm;
	}
	@Column(name="a_m_returned_int_en_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMReturnedIntEnAm() {
		return this.AMReturnedIntEnAm;
	}

	public void setAMReturnedIntEnAm(BigDecimal AMReturnedIntEnAm) {
		this.AMReturnedIntEnAm = AMReturnedIntEnAm;
	}
	@Column(name="a_m_divided_declared_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMDividedDeclaredAm() {
		return this.AMDividedDeclaredAm;
	}

	public void setAMDividedDeclaredAm(BigDecimal AMDividedDeclaredAm) {
		this.AMDividedDeclaredAm = AMDividedDeclaredAm;
	}
	@Column(name="a_m_divided_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMDividedPaidAm() {
		return this.AMDividedPaidAm;
	}

	public void setAMDividedPaidAm(BigDecimal AMDividedPaidAm) {
		this.AMDividedPaidAm = AMDividedPaidAm;
	}
	@Column(name="a_m_no_of_loans", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getAMNoOfLoans() {
		return AMNoOfLoans;
	}

	public void setAMNoOfLoans(Integer aMNoOfLoans) {
		AMNoOfLoans = aMNoOfLoans;
	}
	@Column(name="a_m_no_of_active_loans", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getAMNoOfActiveLoans() {
		return AMNoOfActiveLoans;
	}

	public void setAMNoOfActiveLoans(Integer aMNoOfActiveLoans) {
		AMNoOfActiveLoans = aMNoOfActiveLoans;
	}

	@Column(name="a_m_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMLoanAm() {
		return this.AMLoanAm;
	}

	public void setAMLoanAm(BigDecimal AMLoanAm) {
		this.AMLoanAm = AMLoanAm;
	}

	@Column(name="a_m_pending_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMPendingLoanAm() {
		return this.AMPendingLoanAm;
	}

	public void setAMPendingLoanAm(BigDecimal AMPendingLoanAm) {
		this.AMPendingLoanAm = AMPendingLoanAm;
	}
	@Column(name="a_m_rec_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMRecLoanAm() {
		return this.AMRecLoanAm;
	}

	public void setAMRecLoanAm(BigDecimal AMRecLoanAm) {
		this.AMRecLoanAm = AMRecLoanAm;
	}
	@Column(name="a_m_rec_int_on_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMRecIntOnLoanAm() {
		return this.AMRecIntOnLoanAm;
	}

	public void setAMRecIntOnLoanAm(BigDecimal AMRecIntOnLoanAm) {
		this.AMRecIntOnLoanAm = AMRecIntOnLoanAm;
	}
	@Column(name="a_m_proj_int_on_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMProjIntOnLoanAm() {
		return this.AMProjIntOnLoanAm;
	}

	public void setAMProjIntOnLoanAm(BigDecimal AMProjIntOnLoanAm) {
		this.AMProjIntOnLoanAm = AMProjIntOnLoanAm;
	}
	@Column(name="a_m_sub_std_dept_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMSubStdDeptAm() {
		return AMSubStdDeptAm;
	}

	public void setAMSubStdDeptAm(BigDecimal aMSubStdDeptAm) {
		AMSubStdDeptAm = aMSubStdDeptAm;
	}
	@Column(name="a_m_no_of_sub_std_dept", unique=false, nullable=true, insertable=true, updatable=true)

	public int getAMNoOfSubStdDept() {
		return AMNoOfSubStdDept;
	}

	public void setAMNoOfSubStdDept(int aMNoOfSubStdDept) {
		AMNoOfSubStdDept = aMNoOfSubStdDept;
	}
	@Column(name="a_m_bad_dept_exp_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMBadDeptExpAm() {
		return AMBadDeptExpAm;
	}

	public void setAMBadDeptExpAm(BigDecimal aMBadDeptExpAm) {
		AMBadDeptExpAm = aMBadDeptExpAm;
	}
	@Column(name="a_m_no_of_bad_dept_exp", unique=false, nullable=true, insertable=true, updatable=true)

	public int getAMNoOfBadDeptExp() {
		return AMNoOfBadDeptExp;
	}

	public void setAMNoOfBadDeptExp(int aMNoOfBadDeptExp) {
		AMNoOfBadDeptExp = aMNoOfBadDeptExp;
	}
	@Column(name="a_m_bad_dept_closed_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAMBadDeptClosedAm() {
		return AMBadDeptClosedAm;
	}

	public void setAMBadDeptClosedAm(BigDecimal aMBadDeptClosedAm) {
		AMBadDeptClosedAm = aMBadDeptClosedAm;
	}
	@Column(name="a_m_no_of_bad_dept_closed", unique=false, nullable=true, insertable=true, updatable=true)

	public int getAMNoOfBadDeptClosed() {
		return AMNoOfBadDeptClosed;
	}

	public void setAMNoOfBadDeptClosed(int AMNoOfBadDeptClosed) {
		this.AMNoOfBadDeptClosed = AMNoOfBadDeptClosed;
	}
	@Column(name="p_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPLoanAm() {
		return this.PLoanAm;
	}

	public void setPLoanAm(BigDecimal PLoanAm) {
		this.PLoanAm = PLoanAm;
	}
	@Column(name="p_pending_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPPendingLoanAm() {
		return this.PPendingLoanAm;
	}

	public void setPPendingLoanAm(BigDecimal PPendingLoanAm) {
		this.PPendingLoanAm = PPendingLoanAm;
	}
	@Column(name="p_rec_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPRecLoanAm() {
		return this.PRecLoanAm;
	}

	public void setPRecLoanAm(BigDecimal PRecLoanAm) {
		this.PRecLoanAm = PRecLoanAm;
	}
	@Column(name="p_rec_int_on_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPRecIntOnLoanAm() {
		return this.PRecIntOnLoanAm;
	}

	public void setPRecIntOnLoanAm(BigDecimal PRecIntOnLoanAm) {
		this.PRecIntOnLoanAm = PRecIntOnLoanAm;
	}
	@Column(name="p_proj_int_on_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPProjIntOnLoanAm() {
		return this.PProjIntOnLoanAm;
	}

	public void setPProjIntOnLoanAm(BigDecimal PProjIntOnLoanAm) {
		this.PProjIntOnLoanAm = PProjIntOnLoanAm;
	}
	@Column(name="no_of_project", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfProject() {
		return noOfProject;
	}

	public void setNoOfProject(Integer noOfProject) {
		this.noOfProject = noOfProject;
	}
	@Column(name="no_of_active_project", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfActiveProject() {
		return noOfActiveProject;
	}

	public void setNoOfActiveProject(Integer noOfActiveProject) {
		this.noOfActiveProject = noOfActiveProject;
	}
	@Column(name="fix_deposit_inv_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getFixDepositInvAm() {
		return this.fixDepositInvAm;
	}

	public void setFixDepositInvAm(BigDecimal fixDepositInvAm) {
		this.fixDepositInvAm = fixDepositInvAm;
	}
	@Column(name="pending_fix_deposit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPendingFixDepositAm() {
		return this.pendingFixDepositAm;
	}

	public void setPendingFixDepositAm(BigDecimal pendingFixDepositAm) {
		this.pendingFixDepositAm = pendingFixDepositAm;
	}
	@Column(name="rec_fix_deposit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getRecFixDepositAm() {
		return this.recFixDepositAm;
	}

	public void setRecFixDepositAm(BigDecimal recFixDepositAm) {
		this.recFixDepositAm = recFixDepositAm;
	}
	@Column(name="rec_int_on_fix_deposit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getRecIntOnFixDepositAm() {
		return this.recIntOnFixDepositAm;
	}

	public void setRecIntOnFixDepositAm(BigDecimal recIntOnFixDepositAm) {
		this.recIntOnFixDepositAm = recIntOnFixDepositAm;
	}
	@Column(name="proj_int_on_fix_deposit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getProjIntOnFixDepositAm() {
		return this.projIntOnFixDepositAm;
	}

	public void setProjIntOnFixDepositAm(BigDecimal projIntOnFixDepositAm) {
		this.projIntOnFixDepositAm = projIntOnFixDepositAm;
	}
	@Column(name="no_of_fix_deposit", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfFixDeposit() {
		return noOfFixDeposit;
	}

	public void setNoOfFixDeposit(Integer noOfFixDeposit) {
		this.noOfFixDeposit = noOfFixDeposit;
	}
	@Column(name="no_of_active_fix_deposit", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfActiveFixDeposit() {
		return noOfActiveFixDeposit;
	}

	public void setNoOfActiveFixDeposit(Integer noOfActiveFixDeposit) {
		this.noOfActiveFixDeposit = noOfActiveFixDeposit;
	}
	@Column(name="other_inv_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getOtherInvAm() {
		return this.otherInvAm;
	}

	public void setOtherInvAm(BigDecimal otherInvAm) {
		this.otherInvAm = otherInvAm;
	}
	@Column(name="pending_other_inv_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPendingOtherInvAm() {
		return this.pendingOtherInvAm;
	}

	public void setPendingOtherInvAm(BigDecimal pendingOtherInvAm) {
		this.pendingOtherInvAm = pendingOtherInvAm;
	}
	@Column(name="rec_other_inv_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getRecOtherInvAm() {
		return this.recOtherInvAm;
	}

	public void setRecOtherInvAm(BigDecimal recOtherInvAm) {
		this.recOtherInvAm = recOtherInvAm;
	}
	@Column(name="rec_int_on_other_inv_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getRecIntOnOtherInvAm() {
		return this.recIntOnOtherInvAm;
	}

	public void setRecIntOnOtherInvAm(BigDecimal recIntOnOtherInvAm) {
		this.recIntOnOtherInvAm = recIntOnOtherInvAm;
	}
	@Column(name="proj_int_on_other_inv_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getProjIntOnOtherInvAm() {
		return this.projIntOnOtherInvAm;
	}

	public void setProjIntOnOtherInvAm(BigDecimal projIntOnOtherInvAm) {
		this.projIntOnOtherInvAm = projIntOnOtherInvAm;
	}
	@Column(name="no_of_other_inv", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfOtherInv() {
		return noOfOtherInv;
	}

	public void setNoOfOtherInv(Integer noOfOtherInv) {
		this.noOfOtherInv = noOfOtherInv;
	}
	@Column(name="no_of_active_other_inv", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfActiveOtherInv() {
		return noOfActiveOtherInv;
	}

	public void setNoOfActiveOtherInv(Integer noOfActiveOtherInv) {
		this.noOfActiveOtherInv = noOfActiveOtherInv;
	}
	@Column(name="int_en_on_saving_ac_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getIntEnOnSavingAcAm() {
		return this.intEnOnSavingAcAm;
	}

	public void setIntEnOnSavingAcAm(BigDecimal intEnOnSavingAcAm) {
		this.intEnOnSavingAcAm = intEnOnSavingAcAm;
	}
	@Column(name="clear_bank_balance_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getClearBankBalanceAm() {
		return this.clearBankBalanceAm;
	}

	public void setClearBankBalanceAm(BigDecimal clearBankBalanceAm) {
		this.clearBankBalanceAm = clearBankBalanceAm;
	}
	@Column(name="subj_clearing_bank_balance_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getSubjClearingBankBalanceAm() {
		return this.subjClearingBankBalanceAm;
	}

	public void setSubjClearingBankBalanceAm(BigDecimal subjClearingBankBalanceAm) {
		this.subjClearingBankBalanceAm = subjClearingBankBalanceAm;
	}
	@Column(name="clear_cash_in_hand_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getClearCashInHandAm() {
		return this.clearCashInHandAm;
	}

	public void setClearCashInHandAm(BigDecimal clearCashInHandAm) {
		this.clearCashInHandAm = clearCashInHandAm;
	}
	@Column(name="net_profit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getNetProfitAm() {
		return this.netProfitAm;
	}

	public void setNetProfitAm(BigDecimal netProfitAm) {
		this.netProfitAm = netProfitAm;
	}
	@Column(name="net_profit_proj_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getNetProfitProjAm() {
		return netProfitProjAm;
	}

	public void setNetProfitProjAm(BigDecimal netProfitProjAm) {
		this.netProfitProjAm = netProfitProjAm;
	}

	@Column(name="expenses_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getExpensesAm() {
		return this.expensesAm;
	}

	public void setExpensesAm(BigDecimal expensesAm) {
		this.expensesAm = expensesAm;
	}

	@Column(name="outstanding_expenses_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getOutstandingExpensesAm() {
		return outstandingExpensesAm;
	}

	public void setOutstandingExpensesAm(BigDecimal outstandingExpensesAm) {
		this.outstandingExpensesAm = outstandingExpensesAm;
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
	@Column(name="subj_clearing_cash_in_hand_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getSubjClearingCashInHandAm() {
		return this.subjClearingCashInHandAm;
	}

	public void setSubjClearingCashInHandAm(BigDecimal subjClearingCashInHandAm) {
		this.subjClearingCashInHandAm = subjClearingCashInHandAm;
	}
	@Column(name="borrowed_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getBorrowedLoanAm() {
		return borrowedLoanAm;
	}

	public void setBorrowedLoanAm(BigDecimal borrowedLoanAm) {
		this.borrowedLoanAm = borrowedLoanAm;
	}
	@Column(name="pending_borrowed_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPendingBorrowedLoanAm() {
		return pendingBorrowedLoanAm;
	}

	public void setPendingBorrowedLoanAm(BigDecimal pendingBorrowedLoanAm) {
		this.pendingBorrowedLoanAm = pendingBorrowedLoanAm;
	}
	@Column(name="paid_borrowed_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPaidBorrowedLoanAm() {
		return paidBorrowedLoanAm;
	}

	public void setPaidBorrowedLoanAm(BigDecimal paidBorrowedLoanAm) {
		this.paidBorrowedLoanAm = paidBorrowedLoanAm;
	}
	@Column(name="paid_int_on_borrowed_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPaidIntOnBorrowedLoanAm() {
		return paidIntOnBorrowedLoanAm;
	}

	public void setPaidIntOnBorrowedLoanAm(BigDecimal paidIntOnBorrowedLoanAm) {
		this.paidIntOnBorrowedLoanAm = paidIntOnBorrowedLoanAm;
	}
	@Column(name="proj_int_on_borrowed_loan_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getProjIntOnBorrowedLoanAm() {
		return projIntOnBorrowedLoanAm;
	}

	public void setProjIntOnBorrowedLoanAm(BigDecimal projIntOnBorrowedLoanAm) {
		this.projIntOnBorrowedLoanAm = projIntOnBorrowedLoanAm;
	}
	@Column(name="bank_sub_std_dept_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getBankSubStdDeptAm() {
		return bankSubStdDeptAm;
	}

	public void setBankSubStdDeptAm(BigDecimal bankSubStdDeptAm) {
		this.bankSubStdDeptAm = bankSubStdDeptAm;
	}
	@Column(name="bank_no_of_sub_std_dept", unique=false, nullable=true, insertable=true, updatable=true)

	public int getBankNoOfSubStdDept() {
		return bankNoOfSubStdDept;
	}

	public void setBankNoOfSubStdDept(int bankNoOfSubStdDept) {
		this.bankNoOfSubStdDept = bankNoOfSubStdDept;
	}
	@Column(name="bank_bad_dept_exp_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getBankBadDeptExpAm() {
		return bankBadDeptExpAm;
	}

	public void setBankBadDeptExpAm(BigDecimal bankBadDeptExpAm) {
		this.bankBadDeptExpAm = bankBadDeptExpAm;
	}
	@Column(name="bank_no_of_bad_dept_exp", unique=false, nullable=true, insertable=true, updatable=true)

	public int getBankNoOfBadDeptExp() {
		return bankNoOfBadDeptExp;
	}

	public void setBankNoOfBadDeptExp(int bankNoOfBadDeptExp) {
		this.bankNoOfBadDeptExp = bankNoOfBadDeptExp;
	}
	@Column(name="bank_bad_dept_closed_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getBankBadDeptClosedAm() {
		return bankBadDeptClosedAm;
	}

	public void setBankBadDeptClosedAm(BigDecimal bankBadDeptClosedAm) {
		this.bankBadDeptClosedAm = bankBadDeptClosedAm;
	}
	@Column(name="bank_no_of_bad_dept_closed", unique=false, nullable=true, insertable=true, updatable=true)

	public int getBankNoOfBadDeptClosed() {
		return bankNoOfBadDeptClosed;
	}

	public void setBankNoOfBadDeptClosed(int bankNoOfBadDeptClosed) {
		this.bankNoOfBadDeptClosed = bankNoOfBadDeptClosed;
	}
	@Column(name="no_of_bank_loan", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfBankLoan() {
		return noOfBankLoan;
	}

	public void setNoOfBankLoan(Integer noOfBankLoan) {
		this.noOfBankLoan = noOfBankLoan;
	}
	@Column(name="no_of_active_bank_loan", unique=false, nullable=true, insertable=true, updatable=true)

	public Integer getNoOfActiveBankLoan() {
		return noOfActiveBankLoan;
	}

	public void setNoOfActiveBankLoan(Integer noOfActiveBankLoan) {
		this.noOfActiveBankLoan = noOfActiveBankLoan;
	}
	@Column(name="gov_revolving_fund_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getGovRevolvingFundAm() {
		return govRevolvingFundAm;
	}

	public void setGovRevolvingFundAm(BigDecimal govRevolvingFundAm) {
		this.govRevolvingFundAm = govRevolvingFundAm;
	}
	@Column(name="gov_revolving_fund_returned_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getGovRevolvingFundReturnedAm() {
		return govRevolvingFundReturnedAm;
	}

	public void setGovRevolvingFundReturnedAm(BigDecimal govRevolvingFundReturnedAm) {
		this.govRevolvingFundReturnedAm = govRevolvingFundReturnedAm;
	}
	@Column(name="no_of_gov_revolving_fund", unique=false, nullable=true, insertable=true, updatable=true)

	public int getNoOfGovRevolvingFund() {
		return noOfGovRevolvingFund;
	}

	public void setNoOfGovRevolvingFund(int noOfGovRevolvingFund) {
		this.noOfGovRevolvingFund = noOfGovRevolvingFund;
	}
	@Column(name="no_of_active_gov_revolving_fund", unique=false, nullable=true, insertable=true, updatable=true)

	public int getNoOfActiveGovRevolvingFund() {
		return noOfActiveGovRevolvingFund;
	}

	public void setNoOfActiveGovRevolvingFund(int noOfActiveGovRevolvingFund) {
		this.noOfActiveGovRevolvingFund = noOfActiveGovRevolvingFund;
	}
	@Column(name="gov_development_fund_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getGovDevelopmentFundAm() {
		return govDevelopmentFundAm;
	}

	public void setGovDevelopmentFundAm(BigDecimal govDevelopmentFundAm) {
		this.govDevelopmentFundAm = govDevelopmentFundAm;
	}
	@Column(name="no_of_gov_development_fund", unique=false, nullable=true, insertable=true, updatable=true)

	public int getNoOfGovDevelopmentFund() {
		return noOfGovDevelopmentFund;
	}

	public void setNoOfGovDevelopmentFund(int noOfGovDevelopmentFund) {
		this.noOfGovDevelopmentFund = noOfGovDevelopmentFund;
	}

	@Column(name="pen_shg_one_mem_reg_fee", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPenShgOneMemRegFee() {
		return penShgOneMemRegFee;
	}

	public void setPenShgOneMemRegFee(BigDecimal penShgOneMemRegFee) {
		this.penShgOneMemRegFee = penShgOneMemRegFee;
	}
	@Column(name="pen_shg_one_service_charges", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getPenShgOneServiceCharges() {
		return penShgOneServiceCharges;
	}

	public void setPenShgOneServiceCharges(BigDecimal penShgOneServiceCharges) {
		this.penShgOneServiceCharges = penShgOneServiceCharges;
	}
	@Column(name="no_of_txs_monthly_exp", unique=false, nullable=true, insertable=true, updatable=true)

	public int getNoOfTxsMonthlyExp() {
		return noOfTxsMonthlyExp;
	}

	public void setNoOfTxsMonthlyExp(int noOfTxsMonthlyExp) {
		this.noOfTxsMonthlyExp = noOfTxsMonthlyExp;
	}
	@Column(name="no_of_txs_monthly_done", unique=false, nullable=true, insertable=true, updatable=true)

	public int getNoOfTxsMonthlyDone() {
		return noOfTxsMonthlyDone;
	}

	public void setNoOfTxsMonthlyDone(int noOfTxsMonthlyDone) {
		this.noOfTxsMonthlyDone = noOfTxsMonthlyDone;
	}
	@Column(name="no_of_txs_monthly_approved", unique=false, nullable=true, insertable=true, updatable=true)

	public int getNoOfTxsMonthlyApproved() {
		return noOfTxsMonthlyApproved;
	}

	public void setNoOfTxsMonthlyApproved(int noOfTxsMonthlyApproved) {
		this.noOfTxsMonthlyApproved = noOfTxsMonthlyApproved;
	}
	@Column(name="attachment_url", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getAttachmentUrl() {
		return this.attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
}
