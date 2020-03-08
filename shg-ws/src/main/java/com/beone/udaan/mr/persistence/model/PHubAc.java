package com.beone.udaan.mr.persistence.model;

import java.math.BigDecimal;

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

import com.beone.shg.net.persistence.model.GProfile;

@Entity
@Table(name="p_hub_ac"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class PHubAc implements java.io.Serializable {

	private static final long serialVersionUID = -6479552673241365985L;
	private long hubAcNo;
	private GProfile profile;
	private BigDecimal totalPurchasedStockAm;
	private BigDecimal totalStockAm;
	private BigDecimal totalStockReturnedAm;
	private BigDecimal totalStockDamagedAm;
	private BigDecimal totalStockSoldAm;
	private BigDecimal totalStockMrSoldAm;
	private BigDecimal totalStockDirectSoldAm;
	private BigDecimal totalSoldDiscountAm;
	private BigDecimal currentStockAm;
	private BigDecimal currentStockDiscountAm;
	private BigDecimal currentStockUnsettledAm;
	private BigDecimal currentStockToReturnAm;
	private int totalPurchasedStockNo;
	private int totalStockNo;
	private int totalStockReturnedNo;
	private int totalStockDamagedNo;
	private int totalStockSoldNo;
	private int totalStockMrSoldNo;
	private int totalStockDirectSoldNo;
	private int totalSoldDiscountNo;
	private int currentStockNo;
	private int currentStockDiscountNo;
	private int currentStockUnsettledNo;
	private int currentStockToReturnNo;
	private int returnCounter;
	private BigDecimal currentOrderedStockAm;
	private int currentOrderedStockNo;
	private BigDecimal currentDeliveredStockAm;
	private int currentDeliveredStockNo;
	private BigDecimal currentToStockAm;
	private int currentToStockNo;
	private BigDecimal currentCreatedStockAm;
	private int currentCreatedStockNo;
	private BigDecimal mhTotalCollectedAm;
	private BigDecimal mhTotalPaidCollectedAm;
	private BigDecimal mhCurrentCollectedAm;
	private BigDecimal mhSoldPaidAm;
	private BigDecimal mhSoldPendingAm;
	private BigDecimal mhTotalStockAm;
	private BigDecimal mhTotalStockReturnedAm;
	private BigDecimal mhTotalStockDamagedAm;
	private BigDecimal mhTotalStockSoldAm;
	private BigDecimal mhTotalStockSoldDiscountAm;
	private BigDecimal mhCurrentStockAm;
	private BigDecimal mhCurrentStockDiscountAm;
	private BigDecimal mhCurrentStockUnsettledAm;
	private BigDecimal mhCurrentStockToReturnAm;
	private int mhTotalStockNo;
	private int mhTotalStockReturnedNo;
	private int mhTotalStockDamagedNo;
	private int mhTotalStockSoldNo;
	private int mhTotalStockSoldDiscountNo;
	private int mhCurrentStockNo;
	private int mhCurrentStockDiscountNo;
	private int mhCurrentStockUnsettledNo;
	private int mhCurrentStockToReturnNo;
	private int mhReturnCounter;
	private BigDecimal lhTotalCollectedAm;
	private BigDecimal lhTotalPaidCollectedAm;
	private BigDecimal lhCurrentCollectedAm;
	private BigDecimal lhSoldPaidAm;
	private BigDecimal lhSoldPendingAm;
	private BigDecimal lhTotalStockAm;
	private BigDecimal lhTotalStockReturnedAm;
	private BigDecimal lhTotalStockDamagedAm;
	private BigDecimal lhTotalStockSoldAm;
	private BigDecimal lhTotalStockSoldDiscountAm;
	private BigDecimal lhCurrentStockAm;
	private BigDecimal lhCurrentStockDiscountAm;
	private BigDecimal lhCurrentStockUnsettledAm;
	private BigDecimal lhCurrentStockToReturnAm;
	private int lhTotalStockNo;
	private int lhTotalStockReturnedNo;
	private int lhTotalStockDamagedNo;
	private int lhTotalStockSoldNo;
	private int lhTotalStockSoldDiscountNo;
	private int lhCurrentStockNo;
	private int lhCurrentStockDiscountNo;
	private int lhCurrentStockUnsettledNo;
	private int lhCurrentStockToReturnNo;
	private int lhReturnCounter;
	private BigDecimal slhTotalCollectedAm;
	private BigDecimal slhTotalPaidCollectedAm;
	private BigDecimal slhCurrentCollectedAm;
	private BigDecimal slhSoldPaidAm;
	private BigDecimal slhSoldPendingAm;
	private BigDecimal slhTotalStockAm;
	private BigDecimal slhTotalStockReturnedAm;
	private BigDecimal slhTotalStockDamagedAm;
	private BigDecimal slhTotalStockSoldAm;
	private BigDecimal slhTotalStockSoldDiscountAm;
	private BigDecimal slhCurrentStockAm;
	private BigDecimal slhCurrentStockDiscountAm;
	private BigDecimal slhCurrentStockUnsettledAm;
	private BigDecimal slhCurrentStockToReturnAm;
	private int slhTotalStockNo;
	private int slhTotalStockReturnedNo;
	private int slhTotalStockDamagedNo;
	private int slhTotalStockSoldNo;
	private int slhTotalStockSoldDiscountNo;
	private int slhCurrentStockNo;
	private int slhCurrentStockDiscountNo;
	private int slhCurrentStockUnsettledNo;
	private int slhCurrentStockToReturnNo;
	private int slhReturnCounter;
	private BigDecimal seTotalCollectedAm;
	private BigDecimal seTotalPaidCollectedAm;
	private BigDecimal seCurrentCollectedAm;
	private BigDecimal seSoldPaidAm;
	private BigDecimal seSoldPendingAm;
	private BigDecimal seTotalStockAm;
	private BigDecimal seTotalStockReturnedAm;
	private BigDecimal seTotalStockDamagedAm;
	private BigDecimal seTotalStockSoldAm;
	private BigDecimal seTotalStockSoldDiscountAm;
	private BigDecimal seCurrentStockAm;
	private BigDecimal seCurrentStockDiscountAm;
	private BigDecimal seCurrentStockUnsettledAm;
	private BigDecimal seCurrentStockToReturnAm;
	private int seTotalStockNo;
	private int seTotalStockReturnedNo;
	private int seTotalStockDamagedNo;
	private int seTotalStockSoldNo;
	private int seTotalStockSoldDiscountNo;
	private int seCurrentStockNo;
	private int seCurrentStockDiscountNo;
	private int seCurrentStockUnsettledNo;
	private int seCurrentStockToReturnNo;
	private int seReturnCounter;
	private int mrTotalNo;
	private int mrActiveNo;
	private int mrInactiveNo;
	private int mrTobeClosedNo;
	private int mrClosedNo;
	private BigDecimal mrRegistrationPaidAm;
	private BigDecimal mrRegistrationPendingAm;
	private BigDecimal mrDepositPaidAm;
	private BigDecimal mrDepositPendingAm;
	private BigDecimal mrDepositReturnAm;
	private BigDecimal mrCreditLimitAm;
	private BigDecimal mrSoldPaidAm;
	private BigDecimal mrSoldPendingAm;
	private BigDecimal mrPaidInterestPenaltyAm;
	private BigDecimal mrPendingInterestPenaltyAm;
	private BigDecimal mrTotalStockAm;
	private BigDecimal mrTotalStockReturnedAm;
	private BigDecimal mrTotalStockDamagedAm;
	private BigDecimal mrTotalStockSoldAm;
	private BigDecimal mrTotalStockSoldDiscountAm;
	private BigDecimal mrCurrentStockAm;
	private BigDecimal mrCurrentStockDiscountAm;
	private BigDecimal mrCurrentStockUnsettledAm;
	private BigDecimal mrCurrentStockToReturnAm;
	private int mrTotalStockNo;
	private int mrTotalStockReturnedNo;
	private int mrTotalStockDamagedNo;
	private int mrTotalStockSoldNo;
	private int mrTotalStockSoldDiscountNo;
	private int mrCurrentStockNo;
	private int mrCurrentStockDiscountNo;
	private int mrCurrentStockUnsettledNo;
	private int mrCurrentStockToReturnNo;
	private int mrReturnCounter;
	private float performanceIndex;
	private float returnIndex;
	private float salesIndex;
	private float sales50PerDays;
	private float sales70PerDays;
	private float sales80PerDays;
	private float sales90PerDays;
	private float sales100PerDays;
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GroupAcIdGenerator")
	@GenericGenerator(name = "GroupAcIdGenerator", strategy = "com.beone.udaan.mr.persistence.generator.GroupAcIdGenerator")
	@Column(name="p_hub_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getHubAcNo() {
		return hubAcNo;
	}
	public void setHubAcNo(long hubAcNo) {
		this.hubAcNo = hubAcNo;
	}

	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="p_hub_ac_no", unique=true, nullable=false, insertable=false, updatable=false)
	public GProfile getProfile() {
		return profile;
	}
	public void setProfile(GProfile profile) {
		this.profile = profile;
	}

	@Column(name="total_purchased_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalPurchasedStockAm() {
		return totalPurchasedStockAm;
	}
	public void setTotalPurchasedStockAm(BigDecimal totalPurchasedStockAm) {
		this.totalPurchasedStockAm = totalPurchasedStockAm;
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

	@Column(name="total_stock_mr_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalStockMrSoldAm() {
		return totalStockMrSoldAm;
	}
	public void setTotalStockMrSoldAm(BigDecimal totalStockMrSoldAm) {
		this.totalStockMrSoldAm = totalStockMrSoldAm;
	}

	@Column(name="total_stock_direct_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalStockDirectSoldAm() {
		return totalStockDirectSoldAm;
	}
	public void setTotalStockDirectSoldAm(BigDecimal totalStockDirectSoldAm) {
		this.totalStockDirectSoldAm = totalStockDirectSoldAm;
	}

	@Column(name="total_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalSoldDiscountAm() {
		return totalSoldDiscountAm;
	}
	public void setTotalSoldDiscountAm(BigDecimal totalSoldDiscountAm) {
		this.totalSoldDiscountAm = totalSoldDiscountAm;
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

	@Column(name="total_purchased_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalPurchasedStockNo() {
		return totalPurchasedStockNo;
	}
	public void setTotalPurchasedStockNo(int totalPurchasedStockNo) {
		this.totalPurchasedStockNo = totalPurchasedStockNo;
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

	@Column(name="total_stock_mr_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalStockMrSoldNo() {
		return totalStockMrSoldNo;
	}
	public void setTotalStockMrSoldNo(int totalStockMrSoldNo) {
		this.totalStockMrSoldNo = totalStockMrSoldNo;
	}

	@Column(name="total_stock_direct_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalStockDirectSoldNo() {
		return totalStockDirectSoldNo;
	}
	public void setTotalStockDirectSoldNo(int totalStockDirectSoldNo) {
		this.totalStockDirectSoldNo = totalStockDirectSoldNo;
	}

	@Column(name="total_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalSoldDiscountNo() {
		return totalSoldDiscountNo;
	}
	public void setTotalSoldDiscountNo(int totalSoldDiscountNo) {
		this.totalSoldDiscountNo = totalSoldDiscountNo;
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

	@Column(name="return_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getReturnCounter() {
		return returnCounter;
	}
	public void setReturnCounter(int returnCounter) {
		this.returnCounter = returnCounter;
	}
	
	@Column(name="current_ordered_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentOrderedStockAm() {
		return currentOrderedStockAm;
	}
	public void setCurrentOrderedStockAm(BigDecimal currentOrderedStockAm) {
		this.currentOrderedStockAm = currentOrderedStockAm;
	}

	@Column(name="current_ordered_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentOrderedStockNo() {
		return currentOrderedStockNo;
	}
	public void setCurrentOrderedStockNo(int currentOrderedStockNo) {
		this.currentOrderedStockNo = currentOrderedStockNo;
	}
	
	@Column(name="current_delivered_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentDeliveredStockAm() {
		return currentDeliveredStockAm;
	}
	public void setCurrentDeliveredStockAm(BigDecimal currentDeliveredStockAm) {
		this.currentDeliveredStockAm = currentDeliveredStockAm;
	}

	@Column(name="current_delivered_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentDeliveredStockNo() {
		return currentDeliveredStockNo;
	}
	public void setCurrentDeliveredStockNo(int currentDeliveredStockNo) {
		this.currentDeliveredStockNo = currentDeliveredStockNo;
	}
	
	@Column(name="current_to_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentToStockAm() {
		return currentToStockAm;
	}
	public void setCurrentToStockAm(BigDecimal currentToStockAm) {
		this.currentToStockAm = currentToStockAm;
	}

	@Column(name="current_to_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentToStockNo() {
		return currentToStockNo;
	}
	public void setCurrentToStockNo(int currentToStockNo) {
		this.currentToStockNo = currentToStockNo;
	}
	
	@Column(name="current_created_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentCreatedStockAm() {
		return currentCreatedStockAm;
	}
	public void setCurrentCreatedStockAm(BigDecimal currentCreatedStockAm) {
		this.currentCreatedStockAm = currentCreatedStockAm;
	}

	@Column(name="current_created_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentCreatedStockNo() {
		return currentCreatedStockNo;
	}
	public void setCurrentCreatedStockNo(int currentCreatedStockNo) {
		this.currentCreatedStockNo = currentCreatedStockNo;
	}

	@Column(name="mh_total_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhTotalCollectedAm() {
		return mhTotalCollectedAm;
	}
	public void setMhTotalCollectedAm(BigDecimal mhTotalCollectedAm) {
		this.mhTotalCollectedAm = mhTotalCollectedAm;
	}

	@Column(name="mh_total_paid_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhTotalPaidCollectedAm() {
		return mhTotalPaidCollectedAm;
	}
	public void setMhTotalPaidCollectedAm(BigDecimal mhTotalPaidCollectedAm) {
		this.mhTotalPaidCollectedAm = mhTotalPaidCollectedAm;
	}

	@Column(name="mh_current_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhCurrentCollectedAm() {
		return mhCurrentCollectedAm;
	}
	public void setMhCurrentCollectedAm(BigDecimal mhCurrentCollectedAm) {
		this.mhCurrentCollectedAm = mhCurrentCollectedAm;
	}

	@Column(name="mh_sold_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhSoldPaidAm() {
		return mhSoldPaidAm;
	}
	public void setMhSoldPaidAm(BigDecimal mhSoldPaidAm) {
		this.mhSoldPaidAm = mhSoldPaidAm;
	}

	@Column(name="mh_sold_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhSoldPendingAm() {
		return mhSoldPendingAm;
	}
	public void setMhSoldPendingAm(BigDecimal mhSoldPendingAm) {
		this.mhSoldPendingAm = mhSoldPendingAm;
	}
	
	@Column(name="mh_total_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhTotalStockAm() {
		return mhTotalStockAm;
	}
	public void setMhTotalStockAm(BigDecimal mhTotalStockAm) {
		this.mhTotalStockAm = mhTotalStockAm;
	}
	
	@Column(name="mh_total_stock_returned_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhTotalStockReturnedAm() {
		return mhTotalStockReturnedAm;
	}
	public void setMhTotalStockReturnedAm(BigDecimal mhTotalStockReturnedAm) {
		this.mhTotalStockReturnedAm = mhTotalStockReturnedAm;
	}
	
	@Column(name="mh_total_stock_damaged_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhTotalStockDamagedAm() {
		return mhTotalStockDamagedAm;
	}
	public void setMhTotalStockDamagedAm(BigDecimal mhTotalStockDamagedAm) {
		this.mhTotalStockDamagedAm = mhTotalStockDamagedAm;
	}
	
	@Column(name="mh_total_stock_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhTotalStockSoldAm() {
		return mhTotalStockSoldAm;
	}
	public void setMhTotalStockSoldAm(BigDecimal mhTotalStockSoldAm) {
		this.mhTotalStockSoldAm = mhTotalStockSoldAm;
	}
	
	@Column(name="mh_total_stock_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhTotalStockSoldDiscountAm() {
		return mhTotalStockSoldDiscountAm;
	}
	public void setMhTotalStockSoldDiscountAm(BigDecimal mhTotalStockSoldDiscountAm) {
		this.mhTotalStockSoldDiscountAm = mhTotalStockSoldDiscountAm;
	}

	@Column(name="mh_current_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhCurrentStockAm() {
		return mhCurrentStockAm;
	}
	public void setMhCurrentStockAm(BigDecimal mhCurrentStockAm) {
		this.mhCurrentStockAm = mhCurrentStockAm;
	}

	@Column(name="mh_current_stock_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhCurrentStockDiscountAm() {
		return mhCurrentStockDiscountAm;
	}
	public void setMhCurrentStockDiscountAm(BigDecimal mhCurrentStockDiscountAm) {
		this.mhCurrentStockDiscountAm = mhCurrentStockDiscountAm;
	}

	@Column(name="mh_current_stock_unsettled_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhCurrentStockUnsettledAm() {
		return mhCurrentStockUnsettledAm;
	}
	public void setMhCurrentStockUnsettledAm(BigDecimal mhCurrentStockUnsettledAm) {
		this.mhCurrentStockUnsettledAm = mhCurrentStockUnsettledAm;
	}

	@Column(name="mh_current_stock_to_return_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMhCurrentStockToReturnAm() {
		return mhCurrentStockToReturnAm;
	}
	public void setMhCurrentStockToReturnAm(BigDecimal mhCurrentStockToReturnAm) {
		this.mhCurrentStockToReturnAm = mhCurrentStockToReturnAm;
	}

	@Column(name="mh_total_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhTotalStockNo() {
		return mhTotalStockNo;
	}
	public void setMhTotalStockNo(int mhTotalStockNo) {
		this.mhTotalStockNo = mhTotalStockNo;
	}

	@Column(name="mh_total_stock_returned_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhTotalStockReturnedNo() {
		return mhTotalStockReturnedNo;
	}
	public void setMhTotalStockReturnedNo(int mhTotalStockReturnedNo) {
		this.mhTotalStockReturnedNo = mhTotalStockReturnedNo;
	}

	@Column(name="mh_total_stock_damaged_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhTotalStockDamagedNo() {
		return mhTotalStockDamagedNo;
	}
	public void setMhTotalStockDamagedNo(int mhTotalStockDamagedNo) {
		this.mhTotalStockDamagedNo = mhTotalStockDamagedNo;
	}

	@Column(name="mh_total_stock_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhTotalStockSoldNo() {
		return mhTotalStockSoldNo;
	}
	public void setMhTotalStockSoldNo(int mhTotalStockSoldNo) {
		this.mhTotalStockSoldNo = mhTotalStockSoldNo;
	}

	@Column(name="mh_total_stock_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhTotalStockSoldDiscountNo() {
		return mhTotalStockSoldDiscountNo;
	}
	public void setMhTotalStockSoldDiscountNo(int mhTotalStockSoldDiscountNo) {
		this.mhTotalStockSoldDiscountNo = mhTotalStockSoldDiscountNo;
	}

	@Column(name="mh_current_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhCurrentStockNo() {
		return mhCurrentStockNo;
	}
	public void setMhCurrentStockNo(int mhCurrentStockNo) {
		this.mhCurrentStockNo = mhCurrentStockNo;
	}

	@Column(name="mh_current_stock_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhCurrentStockDiscountNo() {
		return mhCurrentStockDiscountNo;
	}
	public void setMhCurrentStockDiscountNo(int mhCurrentStockDiscountNo) {
		this.mhCurrentStockDiscountNo = mhCurrentStockDiscountNo;
	}

	@Column(name="mh_current_stock_unsettled_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhCurrentStockUnsettledNo() {
		return mhCurrentStockUnsettledNo;
	}
	public void setMhCurrentStockUnsettledNo(int mhCurrentStockUnsettledNo) {
		this.mhCurrentStockUnsettledNo = mhCurrentStockUnsettledNo;
	}
	@Column(name="mh_current_stock_to_return_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhCurrentStockToReturnNo() {
		return mhCurrentStockToReturnNo;
	}
	public void setMhCurrentStockToReturnNo(int mhCurrentStockToReturnNo) {
		this.mhCurrentStockToReturnNo = mhCurrentStockToReturnNo;
	}

	@Column(name="mh_return_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMhReturnCounter() {
		return mhReturnCounter;
	}
	public void setMhReturnCounter(int mhReturnCounter) {
		this.mhReturnCounter = mhReturnCounter;
	}

	@Column(name="lh_total_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhTotalCollectedAm() {
		return lhTotalCollectedAm;
	}
	public void setLhTotalCollectedAm(BigDecimal lhTotalCollectedAm) {
		this.lhTotalCollectedAm = lhTotalCollectedAm;
	}

	@Column(name="lh_total_paid_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhTotalPaidCollectedAm() {
		return lhTotalPaidCollectedAm;
	}
	public void setLhTotalPaidCollectedAm(BigDecimal lhTotalPaidCollectedAm) {
		this.lhTotalPaidCollectedAm = lhTotalPaidCollectedAm;
	}

	@Column(name="lh_current_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhCurrentCollectedAm() {
		return lhCurrentCollectedAm;
	}
	public void setLhCurrentCollectedAm(BigDecimal lhCurrentCollectedAm) {
		this.lhCurrentCollectedAm = lhCurrentCollectedAm;
	}

	@Column(name="lh_sold_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhSoldPaidAm() {
		return lhSoldPaidAm;
	}
	public void setLhSoldPaidAm(BigDecimal lhSoldPaidAm) {
		this.lhSoldPaidAm = lhSoldPaidAm;
	}

	@Column(name="lh_sold_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhSoldPendingAm() {
		return lhSoldPendingAm;
	}
	public void setLhSoldPendingAm(BigDecimal lhSoldPendingAm) {
		this.lhSoldPendingAm = lhSoldPendingAm;
	}
	
	@Column(name="lh_total_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhTotalStockAm() {
		return lhTotalStockAm;
	}
	public void setLhTotalStockAm(BigDecimal lhTotalStockAm) {
		this.lhTotalStockAm = lhTotalStockAm;
	}

	@Column(name="lh_total_stock_returned_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhTotalStockReturnedAm() {
		return lhTotalStockReturnedAm;
	}
	public void setLhTotalStockReturnedAm(BigDecimal lhTotalStockReturnedAm) {
		this.lhTotalStockReturnedAm = lhTotalStockReturnedAm;
	}

	@Column(name="lh_total_stock_damaged_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhTotalStockDamagedAm() {
		return lhTotalStockDamagedAm;
	}
	public void setLhTotalStockDamagedAm(BigDecimal lhTotalStockDamagedAm) {
		this.lhTotalStockDamagedAm = lhTotalStockDamagedAm;
	}

	@Column(name="lh_total_stock_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhTotalStockSoldAm() {
		return lhTotalStockSoldAm;
	}
	public void setLhTotalStockSoldAm(BigDecimal lhTotalStockSoldAm) {
		this.lhTotalStockSoldAm = lhTotalStockSoldAm;
	}

	@Column(name="lh_total_stock_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhTotalStockSoldDiscountAm() {
		return lhTotalStockSoldDiscountAm;
	}
	public void setLhTotalStockSoldDiscountAm(BigDecimal lhTotalStockSoldDiscountAm) {
		this.lhTotalStockSoldDiscountAm = lhTotalStockSoldDiscountAm;
	}

	@Column(name="lh_current_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhCurrentStockAm() {
		return lhCurrentStockAm;
	}
	public void setLhCurrentStockAm(BigDecimal lhCurrentStockAm) {
		this.lhCurrentStockAm = lhCurrentStockAm;
	}

	@Column(name="lh_current_stock_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhCurrentStockDiscountAm() {
		return lhCurrentStockDiscountAm;
	}
	public void setLhCurrentStockDiscountAm(BigDecimal lhCurrentStockDiscountAm) {
		this.lhCurrentStockDiscountAm = lhCurrentStockDiscountAm;
	}

	@Column(name="lh_current_stock_unsettled_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhCurrentStockUnsettledAm() {
		return lhCurrentStockUnsettledAm;
	}
	public void setLhCurrentStockUnsettledAm(BigDecimal lhCurrentStockUnsettledAm) {
		this.lhCurrentStockUnsettledAm = lhCurrentStockUnsettledAm;
	}

	@Column(name="lh_current_stock_to_return_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLhCurrentStockToReturnAm() {
		return lhCurrentStockToReturnAm;
	}
	public void setLhCurrentStockToReturnAm(BigDecimal lhCurrentStockToReturnAm) {
		this.lhCurrentStockToReturnAm = lhCurrentStockToReturnAm;
	}

	@Column(name="lh_total_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhTotalStockNo() {
		return lhTotalStockNo;
	}
	public void setLhTotalStockNo(int lhTotalStockNo) {
		this.lhTotalStockNo = lhTotalStockNo;
	}

	@Column(name="lh_total_stock_returned_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhTotalStockReturnedNo() {
		return lhTotalStockReturnedNo;
	}
	public void setLhTotalStockReturnedNo(int lhTotalStockReturnedNo) {
		this.lhTotalStockReturnedNo = lhTotalStockReturnedNo;
	}

	@Column(name="lh_total_stock_damaged_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhTotalStockDamagedNo() {
		return lhTotalStockDamagedNo;
	}
	public void setLhTotalStockDamagedNo(int lhTotalStockDamagedNo) {
		this.lhTotalStockDamagedNo = lhTotalStockDamagedNo;
	}

	@Column(name="lh_total_stock_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhTotalStockSoldNo() {
		return lhTotalStockSoldNo;
	}
	public void setLhTotalStockSoldNo(int lhTotalStockSoldNo) {
		this.lhTotalStockSoldNo = lhTotalStockSoldNo;
	}

	@Column(name="lh_total_stock_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhTotalStockSoldDiscountNo() {
		return lhTotalStockSoldDiscountNo;
	}
	public void setLhTotalStockSoldDiscountNo(int lhTotalStockSoldDiscountNo) {
		this.lhTotalStockSoldDiscountNo = lhTotalStockSoldDiscountNo;
	}

	@Column(name="lh_current_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhCurrentStockNo() {
		return lhCurrentStockNo;
	}
	public void setLhCurrentStockNo(int lhCurrentStockNo) {
		this.lhCurrentStockNo = lhCurrentStockNo;
	}

	@Column(name="lh_current_stock_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhCurrentStockDiscountNo() {
		return lhCurrentStockDiscountNo;
	}
	public void setLhCurrentStockDiscountNo(int lhCurrentStockDiscountNo) {
		this.lhCurrentStockDiscountNo = lhCurrentStockDiscountNo;
	}

	@Column(name="lh_current_stock_unsettled_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhCurrentStockUnsettledNo() {
		return lhCurrentStockUnsettledNo;
	}
	public void setLhCurrentStockUnsettledNo(int lhCurrentStockUnsettledNo) {
		this.lhCurrentStockUnsettledNo = lhCurrentStockUnsettledNo;
	}
	
	@Column(name="lh_current_stock_to_return_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhCurrentStockToReturnNo() {
		return lhCurrentStockToReturnNo;
	}
	public void setLhCurrentStockToReturnNo(int lhCurrentStockToReturnNo) {
		this.lhCurrentStockToReturnNo = lhCurrentStockToReturnNo;
	}

	@Column(name="lh_return_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getLhReturnCounter() {
		return lhReturnCounter;
	}
	public void setLhReturnCounter(int lhReturnCounter) {
		this.lhReturnCounter = lhReturnCounter;
	}

	@Column(name="slh_total_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhTotalCollectedAm() {
		return slhTotalCollectedAm;
	}
	public void setSlhTotalCollectedAm(BigDecimal slhTotalCollectedAm) {
		this.slhTotalCollectedAm = slhTotalCollectedAm;
	}

	@Column(name="slh_total_paid_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhTotalPaidCollectedAm() {
		return slhTotalPaidCollectedAm;
	}
	public void setSlhTotalPaidCollectedAm(BigDecimal slhTotalPaidCollectedAm) {
		this.slhTotalPaidCollectedAm = slhTotalPaidCollectedAm;
	}

	@Column(name="slh_current_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhCurrentCollectedAm() {
		return slhCurrentCollectedAm;
	}
	public void setSlhCurrentCollectedAm(BigDecimal slhCurrentCollectedAm) {
		this.slhCurrentCollectedAm = slhCurrentCollectedAm;
	}

	@Column(name="slh_sold_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhSoldPaidAm() {
		return slhSoldPaidAm;
	}
	public void setSlhSoldPaidAm(BigDecimal slhSoldPaidAm) {
		this.slhSoldPaidAm = slhSoldPaidAm;
	}

	@Column(name="slh_sold_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhSoldPendingAm() {
		return slhSoldPendingAm;
	}
	public void setSlhSoldPendingAm(BigDecimal slhSoldPendingAm) {
		this.slhSoldPendingAm = slhSoldPendingAm;
	}

	@Column(name="slh_total_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhTotalStockAm() {
		return slhTotalStockAm;
	}
	public void setSlhTotalStockAm(BigDecimal slhTotalStockAm) {
		this.slhTotalStockAm = slhTotalStockAm;
	}

	@Column(name="slh_total_stock_returned_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhTotalStockReturnedAm() {
		return slhTotalStockReturnedAm;
	}
	public void setSlhTotalStockReturnedAm(BigDecimal slhTotalStockReturnedAm) {
		this.slhTotalStockReturnedAm = slhTotalStockReturnedAm;
	}

	@Column(name="slh_total_stock_damaged_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhTotalStockDamagedAm() {
		return slhTotalStockDamagedAm;
	}
	public void setSlhTotalStockDamagedAm(BigDecimal slhTotalStockDamagedAm) {
		this.slhTotalStockDamagedAm = slhTotalStockDamagedAm;
	}

	@Column(name="slh_total_stock_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhTotalStockSoldAm() {
		return slhTotalStockSoldAm;
	}
	public void setSlhTotalStockSoldAm(BigDecimal slhTotalStockSoldAm) {
		this.slhTotalStockSoldAm = slhTotalStockSoldAm;
	}

	@Column(name="slh_total_stock_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhTotalStockSoldDiscountAm() {
		return slhTotalStockSoldDiscountAm;
	}
	public void setSlhTotalStockSoldDiscountAm(
			BigDecimal slhTotalStockSoldDiscountAm) {
		this.slhTotalStockSoldDiscountAm = slhTotalStockSoldDiscountAm;
	}

	@Column(name="slh_current_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhCurrentStockAm() {
		return slhCurrentStockAm;
	}
	public void setSlhCurrentStockAm(BigDecimal slhCurrentStockAm) {
		this.slhCurrentStockAm = slhCurrentStockAm;
	}

	@Column(name="slh_current_stock_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhCurrentStockDiscountAm() {
		return slhCurrentStockDiscountAm;
	}
	public void setSlhCurrentStockDiscountAm(BigDecimal slhCurrentStockDiscountAm) {
		this.slhCurrentStockDiscountAm = slhCurrentStockDiscountAm;
	}

	@Column(name="slh_current_stock_unsettled_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhCurrentStockUnsettledAm() {
		return slhCurrentStockUnsettledAm;
	}
	public void setSlhCurrentStockUnsettledAm(BigDecimal slhCurrentStockUnsettledAm) {
		this.slhCurrentStockUnsettledAm = slhCurrentStockUnsettledAm;
	}
	@Column(name="slh_current_stock_to_return_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSlhCurrentStockToReturnAm() {
		return slhCurrentStockToReturnAm;
	}
	public void setSlhCurrentStockToReturnAm(BigDecimal slhCurrentStockToReturnAm) {
		this.slhCurrentStockToReturnAm = slhCurrentStockToReturnAm;
	}

	@Column(name="slh_total_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhTotalStockNo() {
		return slhTotalStockNo;
	}
	public void setSlhTotalStockNo(int slhTotalStockNo) {
		this.slhTotalStockNo = slhTotalStockNo;
	}

	@Column(name="slh_total_stock_returned_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhTotalStockReturnedNo() {
		return slhTotalStockReturnedNo;
	}
	public void setSlhTotalStockReturnedNo(int slhTotalStockReturnedNo) {
		this.slhTotalStockReturnedNo = slhTotalStockReturnedNo;
	}

	@Column(name="slh_total_stock_damaged_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhTotalStockDamagedNo() {
		return slhTotalStockDamagedNo;
	}
	public void setSlhTotalStockDamagedNo(int slhTotalStockDamagedNo) {
		this.slhTotalStockDamagedNo = slhTotalStockDamagedNo;
	}

	@Column(name="slh_total_stock_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhTotalStockSoldNo() {
		return slhTotalStockSoldNo;
	}
	public void setSlhTotalStockSoldNo(int slhTotalStockSoldNo) {
		this.slhTotalStockSoldNo = slhTotalStockSoldNo;
	}

	@Column(name="slh_total_stock_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhTotalStockSoldDiscountNo() {
		return slhTotalStockSoldDiscountNo;
	}
	public void setSlhTotalStockSoldDiscountNo(int slhTotalStockSoldDiscountNo) {
		this.slhTotalStockSoldDiscountNo = slhTotalStockSoldDiscountNo;
	}

	@Column(name="slh_current_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhCurrentStockNo() {
		return slhCurrentStockNo;
	}
	public void setSlhCurrentStockNo(int slhCurrentStockNo) {
		this.slhCurrentStockNo = slhCurrentStockNo;
	}

	@Column(name="slh_current_stock_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhCurrentStockDiscountNo() {
		return slhCurrentStockDiscountNo;
	}
	public void setSlhCurrentStockDiscountNo(int slhCurrentStockDiscountNo) {
		this.slhCurrentStockDiscountNo = slhCurrentStockDiscountNo;
	}

	@Column(name="slh_current_stock_unsettled_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhCurrentStockUnsettledNo() {
		return slhCurrentStockUnsettledNo;
	}
	public void setSlhCurrentStockUnsettledNo(int slhCurrentStockUnsettledNo) {
		this.slhCurrentStockUnsettledNo = slhCurrentStockUnsettledNo;
	}

	@Column(name="slh_current_stock_to_return_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhCurrentStockToReturnNo() {
		return slhCurrentStockToReturnNo;
	}
	public void setSlhCurrentStockToReturnNo(int slhCurrentStockToReturnNo) {
		this.slhCurrentStockToReturnNo = slhCurrentStockToReturnNo;
	}

	@Column(name="slh_return_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSlhReturnCounter() {
		return slhReturnCounter;
	}
	public void setSlhReturnCounter(int slhReturnCounter) {
		this.slhReturnCounter = slhReturnCounter;
	}

	@Column(name="se_total_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeTotalCollectedAm() {
		return seTotalCollectedAm;
	}
	public void setSeTotalCollectedAm(BigDecimal seTotalCollectedAm) {
		this.seTotalCollectedAm = seTotalCollectedAm;
	}

	@Column(name="se_total_paid_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeTotalPaidCollectedAm() {
		return seTotalPaidCollectedAm;
	}
	public void setSeTotalPaidCollectedAm(BigDecimal seTotalPaidCollectedAm) {
		this.seTotalPaidCollectedAm = seTotalPaidCollectedAm;
	}

	@Column(name="se_current_collected_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeCurrentCollectedAm() {
		return seCurrentCollectedAm;
	}
	public void setSeCurrentCollectedAm(BigDecimal seCurrentCollectedAm) {
		this.seCurrentCollectedAm = seCurrentCollectedAm;
	}

	@Column(name="se_sold_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeSoldPaidAm() {
		return seSoldPaidAm;
	}
	public void setSeSoldPaidAm(BigDecimal seSoldPaidAm) {
		this.seSoldPaidAm = seSoldPaidAm;
	}

	@Column(name="se_sold_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeSoldPendingAm() {
		return seSoldPendingAm;
	}
	public void setSeSoldPendingAm(BigDecimal seSoldPendingAm) {
		this.seSoldPendingAm = seSoldPendingAm;
	}
	
	@Column(name="se_total_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeTotalStockAm() {
		return seTotalStockAm;
	}
	public void setSeTotalStockAm(BigDecimal seTotalStockAm) {
		this.seTotalStockAm = seTotalStockAm;
	}

	@Column(name="se_total_stock_returned_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeTotalStockReturnedAm() {
		return seTotalStockReturnedAm;
	}
	public void setSeTotalStockReturnedAm(BigDecimal seTotalStockReturnedAm) {
		this.seTotalStockReturnedAm = seTotalStockReturnedAm;
	}

	@Column(name="se_total_stock_damaged_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeTotalStockDamagedAm() {
		return seTotalStockDamagedAm;
	}
	public void setSeTotalStockDamagedAm(BigDecimal seTotalStockDamagedAm) {
		this.seTotalStockDamagedAm = seTotalStockDamagedAm;
	}

	@Column(name="se_total_stock_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeTotalStockSoldAm() {
		return seTotalStockSoldAm;
	}
	public void setSeTotalStockSoldAm(BigDecimal seTotalStockSoldAm) {
		this.seTotalStockSoldAm = seTotalStockSoldAm;
	}

	@Column(name="se_total_stock_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeTotalStockSoldDiscountAm() {
		return seTotalStockSoldDiscountAm;
	}
	public void setSeTotalStockSoldDiscountAm(BigDecimal seTotalStockSoldDiscountAm) {
		this.seTotalStockSoldDiscountAm = seTotalStockSoldDiscountAm;
	}

	@Column(name="se_current_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeCurrentStockAm() {
		return seCurrentStockAm;
	}
	public void setSeCurrentStockAm(BigDecimal seCurrentStockAm) {
		this.seCurrentStockAm = seCurrentStockAm;
	}

	@Column(name="se_current_stock_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeCurrentStockDiscountAm() {
		return seCurrentStockDiscountAm;
	}
	public void setSeCurrentStockDiscountAm(BigDecimal seCurrentStockDiscountAm) {
		this.seCurrentStockDiscountAm = seCurrentStockDiscountAm;
	}

	@Column(name="se_current_stock_unsettled_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeCurrentStockUnsettledAm() {
		return seCurrentStockUnsettledAm;
	}
	public void setSeCurrentStockUnsettledAm(BigDecimal seCurrentStockUnsettledAm) {
		this.seCurrentStockUnsettledAm = seCurrentStockUnsettledAm;
	}

	@Column(name="se_current_stock_to_return_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSeCurrentStockToReturnAm() {
		return seCurrentStockToReturnAm;
	}
	public void setSeCurrentStockToReturnAm(BigDecimal seCurrentStockToReturnAm) {
		this.seCurrentStockToReturnAm = seCurrentStockToReturnAm;
	}

	@Column(name="se_total_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeTotalStockNo() {
		return seTotalStockNo;
	}
	public void setSeTotalStockNo(int seTotalStockNo) {
		this.seTotalStockNo = seTotalStockNo;
	}

	@Column(name="se_total_stock_returned_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeTotalStockReturnedNo() {
		return seTotalStockReturnedNo;
	}
	public void setSeTotalStockReturnedNo(int seTotalStockReturnedNo) {
		this.seTotalStockReturnedNo = seTotalStockReturnedNo;
	}

	@Column(name="se_total_stock_damaged_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeTotalStockDamagedNo() {
		return seTotalStockDamagedNo;
	}
	public void setSeTotalStockDamagedNo(int seTotalStockDamagedNo) {
		this.seTotalStockDamagedNo = seTotalStockDamagedNo;
	}

	@Column(name="se_total_stock_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeTotalStockSoldNo() {
		return seTotalStockSoldNo;
	}
	public void setSeTotalStockSoldNo(int seTotalStockSoldNo) {
		this.seTotalStockSoldNo = seTotalStockSoldNo;
	}

	@Column(name="se_total_stock_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeTotalStockSoldDiscountNo() {
		return seTotalStockSoldDiscountNo;
	}
	public void setSeTotalStockSoldDiscountNo(int seTotalStockSoldDiscountNo) {
		this.seTotalStockSoldDiscountNo = seTotalStockSoldDiscountNo;
	}

	@Column(name="se_current_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeCurrentStockNo() {
		return seCurrentStockNo;
	}
	public void setSeCurrentStockNo(int seCurrentStockNo) {
		this.seCurrentStockNo = seCurrentStockNo;
	}

	@Column(name="se_current_stock_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeCurrentStockDiscountNo() {
		return seCurrentStockDiscountNo;
	}
	public void setSeCurrentStockDiscountNo(int seCurrentStockDiscountNo) {
		this.seCurrentStockDiscountNo = seCurrentStockDiscountNo;
	}
	
	@Column(name="se_current_stock_unsettled_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeCurrentStockUnsettledNo() {
		return seCurrentStockUnsettledNo;
	}
	public void setSeCurrentStockUnsettledNo(int seCurrentStockUnsettledNo) {
		this.seCurrentStockUnsettledNo = seCurrentStockUnsettledNo;
	}
	
	@Column(name="se_current_stock_to_return_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeCurrentStockToReturnNo() {
		return seCurrentStockToReturnNo;
	}
	public void setSeCurrentStockToReturnNo(int seCurrentStockToReturnNo) {
		this.seCurrentStockToReturnNo = seCurrentStockToReturnNo;
	}

	@Column(name="se_return_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getSeReturnCounter() {
		return seReturnCounter;
	}
	public void setSeReturnCounter(int seReturnCounter) {
		this.seReturnCounter = seReturnCounter;
	}

	@Column(name="mr_total_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrTotalNo() {
		return mrTotalNo;
	}
	public void setMrTotalNo(int mrTotalNo) {
		this.mrTotalNo = mrTotalNo;
	}

	@Column(name="mr_active_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrActiveNo() {
		return mrActiveNo;
	}
	public void setMrActiveNo(int mrActiveNo) {
		this.mrActiveNo = mrActiveNo;
	}

	@Column(name="mr_inactive_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrInactiveNo() {
		return mrInactiveNo;
	}
	public void setMrInactiveNo(int mrInactiveNo) {
		this.mrInactiveNo = mrInactiveNo;
	}

	@Column(name="mr_tobe_closed_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrTobeClosedNo() {
		return mrTobeClosedNo;
	}
	public void setMrTobeClosedNo(int mrTobeClosedNo) {
		this.mrTobeClosedNo = mrTobeClosedNo;
	}

	@Column(name="mr_closed_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrClosedNo() {
		return mrClosedNo;
	}
	public void setMrClosedNo(int mrClosedNo) {
		this.mrClosedNo = mrClosedNo;
	}

	@Column(name="mr_registration_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrRegistrationPaidAm() {
		return mrRegistrationPaidAm;
	}
	public void setMrRegistrationPaidAm(BigDecimal mrRegistrationPaidAm) {
		this.mrRegistrationPaidAm = mrRegistrationPaidAm;
	}

	@Column(name="mr_registration_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrRegistrationPendingAm() {
		return mrRegistrationPendingAm;
	}
	public void setMrRegistrationPendingAm(BigDecimal mrRegistrationPendingAm) {
		this.mrRegistrationPendingAm = mrRegistrationPendingAm;
	}

	@Column(name="mr_deposit_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrDepositPaidAm() {
		return mrDepositPaidAm;
	}
	public void setMrDepositPaidAm(BigDecimal mrDepositPaidAm) {
		this.mrDepositPaidAm = mrDepositPaidAm;
	}

	@Column(name="mr_deposit_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrDepositPendingAm() {
		return mrDepositPendingAm;
	}
	public void setMrDepositPendingAm(BigDecimal mrDepositPendingAm) {
		this.mrDepositPendingAm = mrDepositPendingAm;
	}

	@Column(name="mr_deposit_return_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrDepositReturnAm() {
		return mrDepositReturnAm;
	}
	public void setMrDepositReturnAm(BigDecimal mrDepositReturnAm) {
		this.mrDepositReturnAm = mrDepositReturnAm;
	}

	@Column(name="mr_credit_limit_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrCreditLimitAm() {
		return mrCreditLimitAm;
	}
	public void setMrCreditLimitAm(BigDecimal mrCreditLimitAm) {
		this.mrCreditLimitAm = mrCreditLimitAm;
	}

	@Column(name="mr_sold_paid_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrSoldPaidAm() {
		return mrSoldPaidAm;
	}
	public void setMrSoldPaidAm(BigDecimal mrSoldPaidAm) {
		this.mrSoldPaidAm = mrSoldPaidAm;
	}

	@Column(name="mr_sold_pending_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrSoldPendingAm() {
		return mrSoldPendingAm;
	}
	public void setMrSoldPendingAm(BigDecimal mrSoldPendingAm) {
		this.mrSoldPendingAm = mrSoldPendingAm;
	}

	@Column(name="mr_paid_interest_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrPaidInterestPenaltyAm() {
		return mrPaidInterestPenaltyAm;
	}
	public void setMrPaidInterestPenaltyAm(BigDecimal mrPaidInterestPenaltyAm) {
		this.mrPaidInterestPenaltyAm = mrPaidInterestPenaltyAm;
	}

	@Column(name="mr_pending_interest_penalty_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrPendingInterestPenaltyAm() {
		return mrPendingInterestPenaltyAm;
	}
	public void setMrPendingInterestPenaltyAm(BigDecimal mrPendingInterestPenaltyAm) {
		this.mrPendingInterestPenaltyAm = mrPendingInterestPenaltyAm;
	}

	@Column(name="mr_total_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrTotalStockAm() {
		return mrTotalStockAm;
	}
	public void setMrTotalStockAm(BigDecimal mrTotalStockAm) {
		this.mrTotalStockAm = mrTotalStockAm;
	}

	@Column(name="mr_total_stock_returned_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrTotalStockReturnedAm() {
		return mrTotalStockReturnedAm;
	}
	public void setMrTotalStockReturnedAm(BigDecimal mrTotalStockReturnedAm) {
		this.mrTotalStockReturnedAm = mrTotalStockReturnedAm;
	}

	@Column(name="mr_total_stock_damaged_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrTotalStockDamagedAm() {
		return mrTotalStockDamagedAm;
	}
	public void setMrTotalStockDamagedAm(BigDecimal mrTotalStockDamagedAm) {
		this.mrTotalStockDamagedAm = mrTotalStockDamagedAm;
	}

	@Column(name="mr_total_stock_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrTotalStockSoldAm() {
		return mrTotalStockSoldAm;
	}
	public void setMrTotalStockSoldAm(BigDecimal mrTotalStockSoldAm) {
		this.mrTotalStockSoldAm = mrTotalStockSoldAm;
	}

	@Column(name="mr_total_stock_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrTotalStockSoldDiscountAm() {
		return mrTotalStockSoldDiscountAm;
	}
	public void setMrTotalStockSoldDiscountAm(BigDecimal mrTotalStockSoldDiscountAm) {
		this.mrTotalStockSoldDiscountAm = mrTotalStockSoldDiscountAm;
	}

	@Column(name="mr_current_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrCurrentStockAm() {
		return mrCurrentStockAm;
	}
	public void setMrCurrentStockAm(BigDecimal mrCurrentStockAm) {
		this.mrCurrentStockAm = mrCurrentStockAm;
	}

	@Column(name="mr_current_stock_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrCurrentStockDiscountAm() {
		return mrCurrentStockDiscountAm;
	}
	public void setMrCurrentStockDiscountAm(BigDecimal mrCurrentStockDiscountAm) {
		this.mrCurrentStockDiscountAm = mrCurrentStockDiscountAm;
	}
	
	@Column(name="mr_current_stock_unsettled_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrCurrentStockUnsettledAm() {
		return mrCurrentStockUnsettledAm;
	}
	public void setMrCurrentStockUnsettledAm(BigDecimal mrCurrentStockUnsettledAm) {
		this.mrCurrentStockUnsettledAm = mrCurrentStockUnsettledAm;
	}
	
	@Column(name="mr_current_stock_to_return_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrCurrentStockToReturnAm() {
		return mrCurrentStockToReturnAm;
	}
	public void setMrCurrentStockToReturnAm(BigDecimal mrCurrentStockToReturnAm) {
		this.mrCurrentStockToReturnAm = mrCurrentStockToReturnAm;
	}

	@Column(name="mr_total_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrTotalStockNo() {
		return mrTotalStockNo;
	}
	public void setMrTotalStockNo(int mrTotalStockNo) {
		this.mrTotalStockNo = mrTotalStockNo;
	}

	@Column(name="mr_total_stock_returned_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrTotalStockReturnedNo() {
		return mrTotalStockReturnedNo;
	}
	public void setMrTotalStockReturnedNo(int mrTotalStockReturnedNo) {
		this.mrTotalStockReturnedNo = mrTotalStockReturnedNo;
	}

	@Column(name="mr_total_stock_damaged_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrTotalStockDamagedNo() {
		return mrTotalStockDamagedNo;
	}
	public void setMrTotalStockDamagedNo(int mrTotalStockDamagedNo) {
		this.mrTotalStockDamagedNo = mrTotalStockDamagedNo;
	}

	@Column(name="mr_total_stock_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrTotalStockSoldNo() {
		return mrTotalStockSoldNo;
	}
	public void setMrTotalStockSoldNo(int mrTotalStockSoldNo) {
		this.mrTotalStockSoldNo = mrTotalStockSoldNo;
	}

	@Column(name="mr_total_stock_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrTotalStockSoldDiscountNo() {
		return mrTotalStockSoldDiscountNo;
	}
	public void setMrTotalStockSoldDiscountNo(int mrTotalStockSoldDiscountNo) {
		this.mrTotalStockSoldDiscountNo = mrTotalStockSoldDiscountNo;
	}
	
	@Column(name="mr_current_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrCurrentStockNo() {
		return mrCurrentStockNo;
	}
	public void setMrCurrentStockNo(int mrCurrentStockNo) {
		this.mrCurrentStockNo = mrCurrentStockNo;
	}

	@Column(name="mr_current_stock_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrCurrentStockDiscountNo() {
		return mrCurrentStockDiscountNo;
	}
	public void setMrCurrentStockDiscountNo(int mrCurrentStockDiscountNo) {
		this.mrCurrentStockDiscountNo = mrCurrentStockDiscountNo;
	}
	
	@Column(name="mr_current_stock_unsettled_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrCurrentStockUnsettledNo() {
		return mrCurrentStockUnsettledNo;
	}
	public void setMrCurrentStockUnsettledNo(int mrCurrentStockUnsettledNo) {
		this.mrCurrentStockUnsettledNo = mrCurrentStockUnsettledNo;
	}
	
	@Column(name="mr_current_stock_to_return_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrCurrentStockToReturnNo() {
		return mrCurrentStockToReturnNo;
	}
	public void setMrCurrentStockToReturnNo(int mrCurrentStockToReturnNo) {
		this.mrCurrentStockToReturnNo = mrCurrentStockToReturnNo;
	}

	@Column(name="mr_return_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMrReturnCounter() {
		return mrReturnCounter;
	}
	public void setMrReturnCounter(int mrReturnCounter) {
		this.mrReturnCounter = mrReturnCounter;
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

	@Column(name="sales_50_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales50PerDays() {
		return sales50PerDays;
	}
	public void setSales50PerDays(float sales50PerDays) {
		this.sales50PerDays = sales50PerDays;
	}

	@Column(name="sales_70_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales70PerDays() {
		return sales70PerDays;
	}
	public void setSales70PerDays(float sales70PerDays) {
		this.sales70PerDays = sales70PerDays;
	}

	@Column(name="sales_80_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales80PerDays() {
		return sales80PerDays;
	}
	public void setSales80PerDays(float sales80PerDays) {
		this.sales80PerDays = sales80PerDays;
	}

	@Column(name="sales_90_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales90PerDays() {
		return sales90PerDays;
	}
	public void setSales90PerDays(float sales90PerDays) {
		this.sales90PerDays = sales90PerDays;
	}

	@Column(name="sales_100_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales100PerDays() {
		return sales100PerDays;
	}
	public void setSales100PerDays(float sales100PerDays) {
		this.sales100PerDays = sales100PerDays;
	}	


}
