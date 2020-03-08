package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * MLoanAc generated by hbm2java
 */
@Entity
@Table(name="m_loan_ac"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class MLoanAc  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 4129936973443680446L;
	private long MLoanAcNo;
	private MAc MAc;
	private int fundTypeId;
	private int loanCalculationId;
	private int accountStatusId;
	private int recoveryPeriodId;
	private int loanSourceId;
	private long approvedByMember;
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
	private Date requestedDate;
	private Date approvedDate;
	private Date disbursementDate;
	private Date instStartDate;
	private Date expCompletionDate;
	private Date closureDate;
	private String developmentPlan;
	private String attachmentUrl;
	private String description;
	private Set<MultiMToLoanAc> multiMToLoanAcs = new HashSet<MultiMToLoanAc>(0);

	// Constructors

	/** default constructor */
	public MLoanAc() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="m_loan_ac_no", unique=true, nullable=false, insertable=true, updatable=true)

	public long getMLoanAcNo() {
		return this.MLoanAcNo;
	}

	public void setMLoanAcNo(long MLoanAcNo) {
		this.MLoanAcNo = MLoanAcNo;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public MAc getMAc() {
		return this.MAc;
	}

	public void setMAc(MAc MAc) {
		this.MAc = MAc;
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
	@Column(name="approved_by_m", unique=false, nullable=true, insertable=true, updatable=true)

	public long getApprovedByMember() {
		return this.approvedByMember;
	}

	public void setApprovedByMember(long approvedByMember) {
		this.approvedByMember = approvedByMember;
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
	@Column(name="requested_date", unique=false, nullable=true, insertable=true, updatable=true, length=10)

	public Date getRequestedDate() {
		return this.requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}
	@Column(name="approved_date", unique=false, nullable=true, insertable=true, updatable=true, length=10)

	public Date getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@Column(name="disbursement_date", unique=false, nullable=true, insertable=true, updatable=true, length=10)

	public Date getDisbursementDate() {
		return this.disbursementDate;
	}

	public void setDisbursementDate(Date disbursementDate) {
		this.disbursementDate = disbursementDate;
	}
	@Column(name="inst_start_date", unique=false, nullable=true, insertable=true, updatable=true, length=10)

	public Date getInstStartDate() {
		return this.instStartDate;
	}

	public void setInstStartDate(Date instStartDate) {
		this.instStartDate = instStartDate;
	}
	@Column(name="exp_completion_date", unique=false, nullable=true, insertable=true, updatable=true, length=10)

	public Date getExpCompletionDate() {
		return this.expCompletionDate;
	}

	public void setExpCompletionDate(Date expCompletionDate) {
		this.expCompletionDate = expCompletionDate;
	}
	@Column(name="closure_date", unique=false, nullable=true, insertable=true, updatable=true, length=10)

	public Date getClosureDate() {
		return this.closureDate;
	}

	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}
	@Column(name="development_plan", unique=false, nullable=true, insertable=true, updatable=true, length=500)

	public String getDevelopmentPlan() {
		return this.developmentPlan;
	}

	public void setDevelopmentPlan(String developmentPlan) {
		this.developmentPlan = developmentPlan;
	}
	@Column(name="attachment_url", unique=false, nullable=true, insertable=true, updatable=true, length=500)

	public String getAttachmentUrl() {
		return this.attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		if(description != null && description.length() > 100) {
			this.description = description.substring(0, 99);
		} else {
			this.description = description;
		}
	}
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="MLoanAc")

	public Set<MultiMToLoanAc> getMultiMToLoanAcs() {
		return this.multiMToLoanAcs;
	}

	public void setMultiMToLoanAcs(Set<MultiMToLoanAc> multiMToLoanAcs) {
		this.multiMToLoanAcs = multiMToLoanAcs;
	}
}
