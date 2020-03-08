package com.beone.udaan.mr.persistence.model;

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

import org.hibernate.annotations.GenericGenerator;

import com.beone.shg.net.persistence.model.MProfile;

@Entity
@Table(name="p_m_ac"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class PMAc implements java.io.Serializable {

	private static final long serialVersionUID = -6479552673241365985L;
	private long memberAcNo;
	private MProfile MProfile;
	private int mrStatusId;
	private int mrTypeId;
	private BigDecimal registrationPaidAm;
	private BigDecimal registrationPendingAm;
	
	private BigDecimal depositPaidAm;
	private BigDecimal depositPendingAm;
	private BigDecimal depositReturnAm;
	
	private BigDecimal creditLimitAm;
	private BigDecimal totalCollectedAm;
	private BigDecimal totalPaidCollectedAm;
	private BigDecimal currentCollectedAm;
	
	private BigDecimal soldPaidAm;
	private BigDecimal soldPendingAm;
	
	private BigDecimal paidInterestPenaltyAm;
	private BigDecimal pendingInterestPenaltyAm;
	
	private Date lastVisitOn;
	private Date interestCalculatedOn;
	private BigDecimal currentStockAm;
	private BigDecimal currentStockDiscountAm;
	private BigDecimal currentStockUnsettledAm;
	private BigDecimal currentStockToReturnAm;
	private int currentStockNo;
	private int currentStockDiscountNo;
	private int currentStockUnsettledNo;
	private int currentStockToReturnNo;
	private BigDecimal totalStockAm;
	private BigDecimal totalStockReturnedAm;
	private BigDecimal totalStockDamagedAm;
	private BigDecimal totalStockSoldAm;
	private BigDecimal totalStockSoldDiscountAm;
	private BigDecimal totalStockMrSoldAm;
	private int totalStockNo;
	private int totalStockReturnedNo;
	private int totalStockDamagedNo;
	private int totalStockSoldNo;
	private int totalStockSoldDiscountNo;
	private int totalVisitCounter;
	private BigDecimal thisMonthStockAm;
	private BigDecimal thisMonthStockReturnedAm;
	private BigDecimal thisMonthStockDamagedAm;
	private BigDecimal thisMonthStockSoldAm;
	private BigDecimal thisMonthStockSoldDiscountAm;
	private BigDecimal thisMonthStockMrSoldAm;
	private int thisMonthStockNo;
	private int thisMonthStockReturnedNo;
	private int thisMonthStockDamagedNo;
	private int thisMonthStockSoldNo;
	private int thisMonthStockSoldDiscountNo;
	private int thisMonthVisitCounter;
	private BigDecimal lastMonthStockAm;
	private BigDecimal lastMonthStockReturnedAm;
	private BigDecimal lastMonthStockDamagedAm;
	private BigDecimal lastMonthStockSoldAm;
	private BigDecimal lastMonthStockSoldDiscountAm;
	private BigDecimal lastMonthStockMrSoldAm;
	private int lastMonthStockNo;
	private int lastMonthStockReturnedNo;
	private int lastMonthStockDamagedNo;
	private int lastMonthStockSoldNo;
	private int lastMonthStockSoldDiscountNo;
	private int lastMonthVisitCounter;
	private float performanceIndex;
	private float returnIndex;
	private float salesIndex;
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MemberAcIdGenerator")
	@GenericGenerator(name = "MemberAcIdGenerator", strategy = "com.beone.udaan.mr.persistence.generator.MemberAcIdGenerator")
	@Column(name="p_m_ac_no", unique=true, nullable=false, insertable=true, updatable=true)

	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}

	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="p_m_ac_no", unique=true, nullable=false, insertable=false, updatable=false)
	public MProfile getMProfile() {
		return this.MProfile;
	}

	public void setMProfile(MProfile MProfile) {
		this.MProfile = MProfile;
	}
	
	@Column(name="mr_status_id", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrStatusId() {
		return mrStatusId;
	}
	public void setMrStatusId(int mrStatusId) {
		this.mrStatusId = mrStatusId;
	}
	
	@Column(name="mr_type_id", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrTypeId() {
		return mrTypeId;
	}
	public void setMrTypeId(int mrTypeId) {
		this.mrTypeId = mrTypeId;
	}
	
	@Column(name="registration_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getRegistrationPaidAm() {
		return registrationPaidAm;
	}
	public void setRegistrationPaidAm(BigDecimal registrationPaidAm) {
		this.registrationPaidAm = registrationPaidAm;
	}

	@Column(name="registration_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getRegistrationPendingAm() {
		return registrationPendingAm;
	}
	public void setRegistrationPendingAm(BigDecimal registrationPendingAm) {
		this.registrationPendingAm = registrationPendingAm;
	}

	@Column(name="deposit_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getDepositPaidAm() {
		return depositPaidAm;
	}
	public void setDepositPaidAm(BigDecimal depositPaidAm) {
		this.depositPaidAm = depositPaidAm;
	}

	@Column(name="deposit_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getDepositPendingAm() {
		return depositPendingAm;
	}
	public void setDepositPendingAm(BigDecimal depositPendingAm) {
		this.depositPendingAm = depositPendingAm;
	}

	@Column(name="deposit_return_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getDepositReturnAm() {
		return depositReturnAm;
	}
	public void setDepositReturnAm(BigDecimal depositReturnAm) {
		this.depositReturnAm = depositReturnAm;
	}

	@Column(name="credit_limit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCreditLimitAm() {
		return creditLimitAm;
	}
	public void setCreditLimitAm(BigDecimal creditLimitAm) {
		this.creditLimitAm = creditLimitAm;
	}

	@Column(name="total_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalCollectedAm() {
		return totalCollectedAm;
	}
	public void setTotalCollectedAm(BigDecimal totalCollectedAm) {
		this.totalCollectedAm = totalCollectedAm;
	}

	@Column(name="total_paid_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalPaidCollectedAm() {
		return totalPaidCollectedAm;
	}
	public void setTotalPaidCollectedAm(BigDecimal totalPaidCollectedAm) {
		this.totalPaidCollectedAm = totalPaidCollectedAm;
	}

	@Column(name="current_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentCollectedAm() {
		return currentCollectedAm;
	}
	public void setCurrentCollectedAm(BigDecimal currentCollectedAm) {
		this.currentCollectedAm = currentCollectedAm;
	}

	@Column(name="sold_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSoldPaidAm() {
		return soldPaidAm;
	}
	public void setSoldPaidAm(BigDecimal soldPaidAm) {
		this.soldPaidAm = soldPaidAm;
	}

	@Column(name="sold_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSoldPendingAm() {
		return soldPendingAm;
	}
	public void setSoldPendingAm(BigDecimal soldPendingAm) {
		this.soldPendingAm = soldPendingAm;
	}

	@Column(name="paid_interest_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getPaidInterestPenaltyAm() {
		return paidInterestPenaltyAm;
	}
	public void setPaidInterestPenaltyAm(BigDecimal paidInterestPenaltyAm) {
		this.paidInterestPenaltyAm = paidInterestPenaltyAm;
	}

	@Column(name="pending_interest_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getPendingInterestPenaltyAm() {
		return pendingInterestPenaltyAm;
	}
	public void setPendingInterestPenaltyAm(BigDecimal pendingInterestPenaltyAm) {
		this.pendingInterestPenaltyAm = pendingInterestPenaltyAm;
	}

	@Column(name="last_visit_on", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getLastVisitOn() {
		return lastVisitOn;
	}
	public void setLastVisitOn(Date lastVisitOn) {
		this.lastVisitOn = lastVisitOn;
	}

	@Column(name="interest_calculated_on", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getInterestCalculatedOn() {
		return interestCalculatedOn;
	}
	public void setInterestCalculatedOn(Date interestCalculatedOn) {
		this.interestCalculatedOn = interestCalculatedOn;
	}
	@Column(name="current_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentStockAm() {
		return currentStockAm;
	}
	public void setCurrentStockAm(BigDecimal currentStockAm) {
		this.currentStockAm = currentStockAm;
	}

	@Column(name="current_stock_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentStockDiscountAm() {
		return currentStockDiscountAm;
	}
	public void setCurrentStockDiscountAm(BigDecimal currentStockDiscountAm) {
		this.currentStockDiscountAm = currentStockDiscountAm;
	}

	@Column(name="current_stock_unsettled_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentStockUnsettledAm() {
		return currentStockUnsettledAm;
	}
	public void setCurrentStockUnsettledAm(BigDecimal currentStockUnsettledAm) {
		this.currentStockUnsettledAm = currentStockUnsettledAm;
	}

	@Column(name="current_stock_to_return_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentStockToReturnAm() {
		return currentStockToReturnAm;
	}
	public void setCurrentStockToReturnAm(BigDecimal currentStockToReturnAm) {
		this.currentStockToReturnAm = currentStockToReturnAm;
	}

	@Column(name="current_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentStockNo() {
		return currentStockNo;
	}
	public void setCurrentStockNo(int currentStockNo) {
		this.currentStockNo = currentStockNo;
	}

	@Column(name="current_stock_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentStockDiscountNo() {
		return currentStockDiscountNo;
	}
	public void setCurrentStockDiscountNo(int currentStockDiscountNo) {
		this.currentStockDiscountNo = currentStockDiscountNo;
	}

	@Column(name="current_stock_unsettled_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentStockUnsettledNo() {
		return currentStockUnsettledNo;
	}
	public void setCurrentStockUnsettledNo(int currentStockUnsettledNo) {
		this.currentStockUnsettledNo = currentStockUnsettledNo;
	}
	@Column(name="current_stock_to_return_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentStockToReturnNo() {
		return currentStockToReturnNo;
	}
	public void setCurrentStockToReturnNo(int currentStockToReturnNo) {
		this.currentStockToReturnNo = currentStockToReturnNo;
	}

	@Column(name="total_visit_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalVisitCounter() {
		return totalVisitCounter;
	}
	public void setTotalVisitCounter(int totalVisitCounter) {
		this.totalVisitCounter = totalVisitCounter;
	}

	@Column(name="total_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalStockAm() {
		return totalStockAm;
	}
	public void setTotalStockAm(BigDecimal totalStockAm) {
		this.totalStockAm = totalStockAm;
	}

	@Column(name="total_stock_returned_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalStockReturnedAm() {
		return totalStockReturnedAm;
	}
	public void setTotalStockReturnedAm(BigDecimal totalStockReturnedAm) {
		this.totalStockReturnedAm = totalStockReturnedAm;
	}

	@Column(name="total_stock_damaged_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalStockDamagedAm() {
		return totalStockDamagedAm;
	}
	public void setTotalStockDamagedAm(BigDecimal totalStockDamagedAm) {
		this.totalStockDamagedAm = totalStockDamagedAm;
	}

	@Column(name="total_stock_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalStockSoldAm() {
		return totalStockSoldAm;
	}
	public void setTotalStockSoldAm(BigDecimal totalStockSoldAm) {
		this.totalStockSoldAm = totalStockSoldAm;
	}

	@Column(name="total_stock_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalStockSoldDiscountAm() {
		return totalStockSoldDiscountAm;
	}
	public void setTotalStockSoldDiscountAm(BigDecimal totalStockSoldDiscountAm) {
		this.totalStockSoldDiscountAm = totalStockSoldDiscountAm;
	}
	
	@Column(name="total_stock_mr_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalStockMrSoldAm() {
		return totalStockMrSoldAm;
	}
	public void setTotalStockMrSoldAm(BigDecimal totalStockMrSoldAm) {
		this.totalStockMrSoldAm = totalStockMrSoldAm;
	}

	@Column(name="total_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalStockNo() {
		return totalStockNo;
	}
	public void setTotalStockNo(int totalStockNo) {
		this.totalStockNo = totalStockNo;
	}

	@Column(name="total_stock_returned_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalStockReturnedNo() {
		return totalStockReturnedNo;
	}
	public void setTotalStockReturnedNo(int totalStockReturnedNo) {
		this.totalStockReturnedNo = totalStockReturnedNo;
	}

	@Column(name="total_stock_damaged_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalStockDamagedNo() {
		return totalStockDamagedNo;
	}
	public void setTotalStockDamagedNo(int totalStockDamagedNo) {
		this.totalStockDamagedNo = totalStockDamagedNo;
	}

	@Column(name="total_stock_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalStockSoldNo() {
		return totalStockSoldNo;
	}
	public void setTotalStockSoldNo(int totalStockSoldNo) {
		this.totalStockSoldNo = totalStockSoldNo;
	}

	@Column(name="total_stock_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalStockSoldDiscountNo() {
		return totalStockSoldDiscountNo;
	}
	public void setTotalStockSoldDiscountNo(int totalStockSoldDiscountNo) {
		this.totalStockSoldDiscountNo = totalStockSoldDiscountNo;
	}

	@Column(name="this_month_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getThisMonthStockAm() {
		return thisMonthStockAm;
	}
	public void setThisMonthStockAm(BigDecimal thisMonthStockAm) {
		this.thisMonthStockAm = thisMonthStockAm;
	}

	@Column(name="this_month_stock_returned_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getThisMonthStockReturnedAm() {
		return thisMonthStockReturnedAm;
	}
	public void setThisMonthStockReturnedAm(BigDecimal thisMonthStockReturnedAm) {
		this.thisMonthStockReturnedAm = thisMonthStockReturnedAm;
	}

	@Column(name="this_month_stock_damaged_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getThisMonthStockDamagedAm() {
		return thisMonthStockDamagedAm;
	}
	public void setThisMonthStockDamagedAm(BigDecimal thisMonthStockDamagedAm) {
		this.thisMonthStockDamagedAm = thisMonthStockDamagedAm;
	}

	@Column(name="this_month_stock_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getThisMonthStockSoldAm() {
		return thisMonthStockSoldAm;
	}
	public void setThisMonthStockSoldAm(BigDecimal thisMonthStockSoldAm) {
		this.thisMonthStockSoldAm = thisMonthStockSoldAm;
	}

	@Column(name="this_month_stock_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getThisMonthStockSoldDiscountAm() {
		return thisMonthStockSoldDiscountAm;
	}
	public void setThisMonthStockSoldDiscountAm(
			BigDecimal thisMonthStockSoldDiscountAm) {
		this.thisMonthStockSoldDiscountAm = thisMonthStockSoldDiscountAm;
	}

	@Column(name="this_month_stock_mr_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getThisMonthStockMrSoldAm() {
		return thisMonthStockMrSoldAm;
	}
	public void setThisMonthStockMrSoldAm(BigDecimal thisMonthStockMrSoldAm) {
		this.thisMonthStockMrSoldAm = thisMonthStockMrSoldAm;
	}

	@Column(name="this_month_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getThisMonthStockNo() {
		return thisMonthStockNo;
	}
	public void setThisMonthStockNo(int thisMonthStockNo) {
		this.thisMonthStockNo = thisMonthStockNo;
	}

	@Column(name="this_month_stock_returned_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getThisMonthStockReturnedNo() {
		return thisMonthStockReturnedNo;
	}
	public void setThisMonthStockReturnedNo(int thisMonthStockReturnedNo) {
		this.thisMonthStockReturnedNo = thisMonthStockReturnedNo;
	}

	@Column(name="this_month_stock_damaged_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getThisMonthStockDamagedNo() {
		return thisMonthStockDamagedNo;
	}
	public void setThisMonthStockDamagedNo(int thisMonthStockDamagedNo) {
		this.thisMonthStockDamagedNo = thisMonthStockDamagedNo;
	}

	@Column(name="this_month_stock_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getThisMonthStockSoldNo() {
		return thisMonthStockSoldNo;
	}
	public void setThisMonthStockSoldNo(int thisMonthStockSoldNo) {
		this.thisMonthStockSoldNo = thisMonthStockSoldNo;
	}

	@Column(name="this_month_stock_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getThisMonthStockSoldDiscountNo() {
		return thisMonthStockSoldDiscountNo;
	}
	public void setThisMonthStockSoldDiscountNo(int thisMonthStockSoldDiscountNo) {
		this.thisMonthStockSoldDiscountNo = thisMonthStockSoldDiscountNo;
	}

	@Column(name="this_month_visit_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getThisMonthVisitCounter() {
		return thisMonthVisitCounter;
	}
	public void setThisMonthVisitCounter(int thisMonthVisitCounter) {
		this.thisMonthVisitCounter = thisMonthVisitCounter;
	}

	@Column(name="last_month_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLastMonthStockAm() {
		return lastMonthStockAm;
	}
	public void setLastMonthStockAm(BigDecimal lastMonthStockAm) {
		this.lastMonthStockAm = lastMonthStockAm;
	}

	@Column(name="last_month_stock_returned_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLastMonthStockReturnedAm() {
		return lastMonthStockReturnedAm;
	}
	public void setLastMonthStockReturnedAm(BigDecimal lastMonthStockReturnedAm) {
		this.lastMonthStockReturnedAm = lastMonthStockReturnedAm;
	}

	@Column(name="last_month_stock_damaged_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLastMonthStockDamagedAm() {
		return lastMonthStockDamagedAm;
	}
	public void setLastMonthStockDamagedAm(BigDecimal lastMonthStockDamagedAm) {
		this.lastMonthStockDamagedAm = lastMonthStockDamagedAm;
	}

	@Column(name="last_month_stock_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLastMonthStockSoldAm() {
		return lastMonthStockSoldAm;
	}
	public void setLastMonthStockSoldAm(BigDecimal lastMonthStockSoldAm) {
		this.lastMonthStockSoldAm = lastMonthStockSoldAm;
	}

	@Column(name="last_month_stock_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLastMonthStockSoldDiscountAm() {
		return lastMonthStockSoldDiscountAm;
	}
	public void setLastMonthStockSoldDiscountAm(
			BigDecimal lastMonthStockSoldDiscountAm) {
		this.lastMonthStockSoldDiscountAm = lastMonthStockSoldDiscountAm;
	}

	@Column(name="last_month_stock_mr_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLastMonthStockMrSoldAm() {
		return lastMonthStockMrSoldAm;
	}
	public void setLastMonthStockMrSoldAm(BigDecimal lastMonthStockMrSoldAm) {
		this.lastMonthStockMrSoldAm = lastMonthStockMrSoldAm;
	}

	@Column(name="last_month_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLastMonthStockNo() {
		return lastMonthStockNo;
	}
	public void setLastMonthStockNo(int lastMonthStockNo) {
		this.lastMonthStockNo = lastMonthStockNo;
	}

	@Column(name="last_month_stock_returned_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLastMonthStockReturnedNo() {
		return lastMonthStockReturnedNo;
	}
	public void setLastMonthStockReturnedNo(int lastMonthStockReturnedNo) {
		this.lastMonthStockReturnedNo = lastMonthStockReturnedNo;
	}

	@Column(name="last_month_stock_damaged_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLastMonthStockDamagedNo() {
		return lastMonthStockDamagedNo;
	}
	public void setLastMonthStockDamagedNo(int lastMonthStockDamagedNo) {
		this.lastMonthStockDamagedNo = lastMonthStockDamagedNo;
	}

	@Column(name="last_month_stock_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLastMonthStockSoldNo() {
		return lastMonthStockSoldNo;
	}
	public void setLastMonthStockSoldNo(int lastMonthStockSoldNo) {
		this.lastMonthStockSoldNo = lastMonthStockSoldNo;
	}

	@Column(name="last_month_stock_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLastMonthStockSoldDiscountNo() {
		return lastMonthStockSoldDiscountNo;
	}
	public void setLastMonthStockSoldDiscountNo(int lastMonthStockSoldDiscountNo) {
		this.lastMonthStockSoldDiscountNo = lastMonthStockSoldDiscountNo;
	}

	@Column(name="last_month_visit_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLastMonthVisitCounter() {
		return lastMonthVisitCounter;
	}
	public void setLastMonthVisitCounter(int lastMonthVisitCounter) {
		this.lastMonthVisitCounter = lastMonthVisitCounter;
	}
	
	@Column(name="performance_index", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getPerformanceIndex() {
		return performanceIndex;
	}
	public void setPerformanceIndex(float performanceIndex) {
		this.performanceIndex = performanceIndex;
	}

	@Column(name="return_index", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getReturnIndex() {
		return returnIndex;
	}
	public void setReturnIndex(float returnIndex) {
		this.returnIndex = returnIndex;
	}

	@Column(name="sales_index", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSalesIndex() {
		return salesIndex;
	}
	public void setSalesIndex(float salesIndex) {
		this.salesIndex = salesIndex;
	}

}
