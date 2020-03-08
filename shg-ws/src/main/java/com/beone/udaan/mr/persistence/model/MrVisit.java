package com.beone.udaan.mr.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mr_visit"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class MrVisit implements java.io.Serializable {

	private static final long serialVersionUID = -9197746789470241191L;
	private long mrVisitId;
	private long ownerAcNo;
	private long authAcNo;
	private int visitStatusId;
	private int visitTypeId;
	private Date scheduledTs;
	private Date startTs;
	private Date endTs;
	private Date latestUpdateTs;
	
	private BigDecimal openingOutstandingAm;
	private BigDecimal soldPaidAm;
	private BigDecimal soldPendingAm;
	private BigDecimal closingOutstandingAm;
	private BigDecimal openingInterestPenaltyAm;
	private BigDecimal paidInterestPenaltyAm;
	private BigDecimal closingInterestPenaltyAm;
	
	private BigDecimal openingCollectedAm;
	private BigDecimal paidCollectedAm;
	private BigDecimal receivedCollectedAm;
	private BigDecimal closingCollectedAm;
	private BigDecimal openingRegistrationAm;
	private BigDecimal paidRegistrationAm;
	private BigDecimal closingRegistrationAm;
	
	private BigDecimal openingDepositAm;
	private BigDecimal paidDepositAm;
	private BigDecimal returnedDepositAm;
	private BigDecimal closingDepositAm;
	
	private BigDecimal openingStockAm;
	private BigDecimal returnStockAm;
	private BigDecimal soldStockAm;
	private BigDecimal soldStockDiscountAm;
	private BigDecimal mrSoldStockAm;
	private BigDecimal givenStockAm;
	private BigDecimal closingStockAm;
	
	private int openingStockNo;
	private int returnStockNo;
	private int soldStockNo;
	private int soldStockDiscountNo;
	private int givenStockNo;
	private int closingStockNo;

	private BigDecimal authBalanceAm;
	private BigDecimal authStockAm;
	private int authStockNo;
	
	private BigDecimal totalStockAm;
	private BigDecimal totalStockReturnedAm;
	private BigDecimal totalStockDamagedAm;
	private BigDecimal totalStockSoldAm;
	private BigDecimal totalStockSoldDiscountAm;
	private int totalStockNo;
	private int totalStockReturnedNo;
	private int totalStockDamagedNo;
	private int totalStockSoldNo;
	private int totalStockSoldDiscountNo;
	private BigDecimal totalPaidSoldAm;
	private BigDecimal totalPaidInterestPenaltyAm;
	private BigDecimal totalPaidCollectedAm;
	private BigDecimal totalRegistrationPaidAm;
	private BigDecimal totalDepositPaidAm;
	private int totalVisitCounter;
	private String location;
	private String description;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="mr_visit_id", unique=true, nullable=false, insertable=true, updatable=true)
	public long getMrVisitId() {
		return mrVisitId;
	}
	public void setMrVisitId(long mrVisitId) {
		this.mrVisitId = mrVisitId;
	}
	
	@Column(name="owner_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public long getOwnerAcNo() {
		return ownerAcNo;
	}
	public void setOwnerAcNo(long ownerAcNo) {
		this.ownerAcNo = ownerAcNo;
	}
	
	@Column(name="auth_ac_no", unique=false, nullable=true, insertable=true, updatable=true)
	public long getAuthAcNo() {
		return authAcNo;
	}
	public void setAuthAcNo(long authAcNo) {
		this.authAcNo = authAcNo;
	}

	@Column(name="visit_status_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getVisitStatusId() {
		return visitStatusId;
	}
	public void setVisitStatusId(int visitStatusId) {
		this.visitStatusId = visitStatusId;
	}

	@Column(name="visit_type_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getVisitTypeId() {
		return visitTypeId;
	}
	public void setVisitTypeId(int visitTypeId) {
		this.visitTypeId = visitTypeId;
	}

	@Column(name="scheduled_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getScheduledTs() {
		return scheduledTs;
	}
	public void setScheduledTs(Date scheduledTs) {
		this.scheduledTs = scheduledTs;
	}
	@Column(name="start_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getStartTs() {
		return startTs;
	}
	public void setStartTs(Date startTs) {
		this.startTs = startTs;
	}

	@Column(name="end_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getEndTs() {
		return endTs;
	}
	public void setEndTs(Date endTs) {
		this.endTs = endTs;
	}

	@Column(name="latest_update_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getLatestUpdateTs() {
		return latestUpdateTs;
	}
	public void setLatestUpdateTs(Date latestUpdateTs) {
		this.latestUpdateTs = latestUpdateTs;
	}

	@Column(name="opening_outstanding_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getOpeningOutstandingAm() {
		return openingOutstandingAm;
	}
	public void setOpeningOutstandingAm(BigDecimal openingOutstandingAm) {
		this.openingOutstandingAm = openingOutstandingAm;
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
	@Column(name="closing_outstanding_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getClosingOutstandingAm() {
		return closingOutstandingAm;
	}
	public void setClosingOutstandingAm(BigDecimal closingOutstandingAm) {
		this.closingOutstandingAm = closingOutstandingAm;
	}

	@Column(name="opening_interest_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getOpeningInterestPenaltyAm() {
		return openingInterestPenaltyAm;
	}
	public void setOpeningInterestPenaltyAm(BigDecimal openingInterestPenaltyAm) {
		this.openingInterestPenaltyAm = openingInterestPenaltyAm;
	}

	@Column(name="paid_interest_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getPaidInterestPenaltyAm() {
		return paidInterestPenaltyAm;
	}
	public void setPaidInterestPenaltyAm(BigDecimal paidInterestPenaltyAm) {
		this.paidInterestPenaltyAm = paidInterestPenaltyAm;
	}

	@Column(name="closing_interest_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getClosingInterestPenaltyAm() {
		return closingInterestPenaltyAm;
	}
	public void setClosingInterestPenaltyAm(BigDecimal closingInterestPenaltyAm) {
		this.closingInterestPenaltyAm = closingInterestPenaltyAm;
	}

	@Column(name="opening_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getOpeningCollectedAm() {
		return openingCollectedAm;
	}
	public void setOpeningCollectedAm(BigDecimal openingCollectedAm) {
		this.openingCollectedAm = openingCollectedAm;
	}

	@Column(name="paid_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getPaidCollectedAm() {
		return paidCollectedAm;
	}
	public void setPaidCollectedAm(BigDecimal paidCollectedAm) {
		this.paidCollectedAm = paidCollectedAm;
	}

	@Column(name="received_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getReceivedCollectedAm() {
		return receivedCollectedAm;
	}
	public void setReceivedCollectedAm(BigDecimal receivedCollectedAm) {
		this.receivedCollectedAm = receivedCollectedAm;
	}

	@Column(name="closing_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getClosingCollectedAm() {
		return closingCollectedAm;
	}
	public void setClosingCollectedAm(BigDecimal closingCollectedAm) {
		this.closingCollectedAm = closingCollectedAm;
	}

	@Column(name="opening_registration_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getOpeningRegistrationAm() {
		return openingRegistrationAm;
	}
	public void setOpeningRegistrationAm(BigDecimal openingRegistrationAm) {
		this.openingRegistrationAm = openingRegistrationAm;
	}

	@Column(name="paid_registration_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getPaidRegistrationAm() {
		return paidRegistrationAm;
	}
	public void setPaidRegistrationAm(BigDecimal paidRegistrationAm) {
		this.paidRegistrationAm = paidRegistrationAm;
	}

	@Column(name="closing_registration_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getClosingRegistrationAm() {
		return closingRegistrationAm;
	}
	public void setClosingRegistrationAm(BigDecimal closingRegistrationAm) {
		this.closingRegistrationAm = closingRegistrationAm;
	}

	@Column(name="opening_deposit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getOpeningDepositAm() {
		return openingDepositAm;
	}
	public void setOpeningDepositAm(BigDecimal openingDepositAm) {
		this.openingDepositAm = openingDepositAm;
	}

	@Column(name="paid_deposit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getPaidDepositAm() {
		return paidDepositAm;
	}
	public void setPaidDepositAm(BigDecimal paidDepositAm) {
		this.paidDepositAm = paidDepositAm;
	}

	@Column(name="returned_deposit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getReturnedDepositAm() {
		return returnedDepositAm;
	}
	public void setReturnedDepositAm(BigDecimal returnedDepositAm) {
		this.returnedDepositAm = returnedDepositAm;
	}

	@Column(name="closing_deposit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getClosingDepositAm() {
		return closingDepositAm;
	}
	public void setClosingDepositAm(BigDecimal closingDepositAm) {
		this.closingDepositAm = closingDepositAm;
	}

	@Column(name="opening_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getOpeningStockAm() {
		return openingStockAm;
	}
	public void setOpeningStockAm(BigDecimal openingStockAm) {
		this.openingStockAm = openingStockAm;
	}

	@Column(name="return_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getReturnStockAm() {
		return returnStockAm;
	}
	public void setReturnStockAm(BigDecimal returnStockAm) {
		this.returnStockAm = returnStockAm;
	}

	@Column(name="sold_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSoldStockAm() {
		return soldStockAm;
	}
	public void setSoldStockAm(BigDecimal soldStockAm) {
		this.soldStockAm = soldStockAm;
	}

	@Column(name="sold_stock_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSoldStockDiscountAm() {
		return soldStockDiscountAm;
	}
	public void setSoldStockDiscountAm(BigDecimal soldStockDiscountAm) {
		this.soldStockDiscountAm = soldStockDiscountAm;
	}
	
	@Column(name="mr_sold_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrSoldStockAm() {
		return mrSoldStockAm;
	}
	public void setMrSoldStockAm(BigDecimal mrSoldStockAm) {
		this.mrSoldStockAm = mrSoldStockAm;
	}

	@Column(name="given_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getGivenStockAm() {
		return givenStockAm;
	}
	public void setGivenStockAm(BigDecimal givenStockAm) {
		this.givenStockAm = givenStockAm;
	}

	@Column(name="closing_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getClosingStockAm() {
		return closingStockAm;
	}
	public void setClosingStockAm(BigDecimal closingStockAm) {
		this.closingStockAm = closingStockAm;
	}

	@Column(name="opening_stock_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getOpeningStockNo() {
		return openingStockNo;
	}
	public void setOpeningStockNo(int openingStockNo) {
		this.openingStockNo = openingStockNo;
	}

	@Column(name="return_stock_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getReturnStockNo() {
		return returnStockNo;
	}
	public void setReturnStockNo(int returnStockNo) {
		this.returnStockNo = returnStockNo;
	}

	@Column(name="sold_stock_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getSoldStockNo() {
		return soldStockNo;
	}
	public void setSoldStockNo(int soldStockNo) {
		this.soldStockNo = soldStockNo;
	}

	@Column(name="sold_stock_discount_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getSoldStockDiscountNo() {
		return soldStockDiscountNo;
	}
	public void setSoldStockDiscountNo(int soldStockDiscountNo) {
		this.soldStockDiscountNo = soldStockDiscountNo;
	}
	
	@Column(name="given_stock_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getGivenStockNo() {
		return givenStockNo;
	}
	public void setGivenStockNo(int givenStockNo) {
		this.givenStockNo = givenStockNo;
	}

	@Column(name="closing_stock_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getClosingStockNo() {
		return closingStockNo;
	}
	public void setClosingStockNo(int closingStockNo) {
		this.closingStockNo = closingStockNo;
	}

	@Column(name="auth_balance_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getAuthBalanceAm() {
		return authBalanceAm;
	}
	public void setAuthBalanceAm(BigDecimal authBalanceAm) {
		this.authBalanceAm = authBalanceAm;
	}
	
	@Column(name="auth_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getAuthStockAm() {
		return authStockAm;
	}
	public void setAuthStockAm(BigDecimal authStockAm) {
		this.authStockAm = authStockAm;
	}
	
	@Column(name="auth_stock_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getAuthStockNo() {
		return authStockNo;
	}
	public void setAuthStockNo(int authStockNo) {
		this.authStockNo = authStockNo;
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
	
	@Column(name="total_stock_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getTotalStockNo() {
		return totalStockNo;
	}
	public void setTotalStockNo(int totalStockNo) {
		this.totalStockNo = totalStockNo;
	}

	@Column(name="total_stock_returned_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getTotalStockReturnedNo() {
		return totalStockReturnedNo;
	}
	public void setTotalStockReturnedNo(int totalStockReturnedNo) {
		this.totalStockReturnedNo = totalStockReturnedNo;
	}

	@Column(name="total_stock_damaged_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getTotalStockDamagedNo() {
		return totalStockDamagedNo;
	}
	public void setTotalStockDamagedNo(int totalStockDamagedNo) {
		this.totalStockDamagedNo = totalStockDamagedNo;
	}

	@Column(name="total_stock_sold_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getTotalStockSoldNo() {
		return totalStockSoldNo;
	}
	public void setTotalStockSoldNo(int totalStockSoldNo) {
		this.totalStockSoldNo = totalStockSoldNo;
	}

	@Column(name="total_stock_sold_discount_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getTotalStockSoldDiscountNo() {
		return totalStockSoldDiscountNo;
	}
	public void setTotalStockSoldDiscountNo(int totalStockSoldDiscountNo) {
		this.totalStockSoldDiscountNo = totalStockSoldDiscountNo;
	}
	
	@Column(name="total_paid_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalPaidSoldAm() {
		return totalPaidSoldAm;
	}
	public void setTotalPaidSoldAm(BigDecimal totalPaidSoldAm) {
		this.totalPaidSoldAm = totalPaidSoldAm;
	}

	@Column(name="total_paid_interest_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalPaidInterestPenaltyAm() {
		return totalPaidInterestPenaltyAm;
	}
	public void setTotalPaidInterestPenaltyAm(BigDecimal totalPaidInterestPenaltyAm) {
		this.totalPaidInterestPenaltyAm = totalPaidInterestPenaltyAm;
	}

	@Column(name="total_paid_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalPaidCollectedAm() {
		return totalPaidCollectedAm;
	}
	public void setTotalPaidCollectedAm(BigDecimal totalPaidCollectedAm) {
		this.totalPaidCollectedAm = totalPaidCollectedAm;
	}

	@Column(name="total_registration_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalRegistrationPaidAm() {
		return totalRegistrationPaidAm;
	}
	public void setTotalRegistrationPaidAm(BigDecimal totalRegistrationPaidAm) {
		this.totalRegistrationPaidAm = totalRegistrationPaidAm;
	}

	@Column(name="total_deposit_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalDepositPaidAm() {
		return totalDepositPaidAm;
	}
	public void setTotalDepositPaidAm(BigDecimal totalDepositPaidAm) {
		this.totalDepositPaidAm = totalDepositPaidAm;
	}

	@Column(name="total_visit_counter", unique=false, nullable=false, insertable=true, updatable=true)
	public int getTotalVisitCounter() {
		return totalVisitCounter;
	}
	public void setTotalVisitCounter(int totalVisitCounter) {
		this.totalVisitCounter = totalVisitCounter;
	}

	@Column(name="location", unique=false, nullable=false, insertable=true, updatable=true, length=200)
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name="description", unique=false, nullable=false, insertable=true, updatable=true, length=200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
