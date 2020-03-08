package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.udaan.mr.config.EnumConstMr;

public class MemberVisitAc {

	private long startTs;
	private long endTs;
	private long duration;
	private int totalVisits;
	private int ownerVisits;
	private int authVisits;
	private int properVisits;

	private String ownerRole;
	private String authRole;

	private long ownerAcNo;
	private long authAcNo;
	private String ownerAcNoDisplay;
	private String ownerName;
	private String authAcNoDisplay;
	private String authName;

	private BigDecimal openingBalanceAm;
	private BigDecimal paidRegistrationAm;
	private BigDecimal paidDepositAm;
	private BigDecimal paidCollectedAm;
	private BigDecimal paidSoldAm;
	private BigDecimal paidInterestPenaltyAm;
	private BigDecimal collectedReturnedDepositAm;
	
	private BigDecimal collectedRegistrationAm;
	private BigDecimal collectedDepositAm;
	private BigDecimal collectedCollectedAm;
	private BigDecimal collectedSoldAm;
	private BigDecimal collectedInterestPenaltyAm;
	private BigDecimal paidReturnedDepositAm;
	private BigDecimal closingBalanceAm;

	private BigDecimal openingStockAm;
	private BigDecimal givenStockAm;
	private BigDecimal returnStockAm;
	private BigDecimal soldStockAm;
	private BigDecimal soldStockDiscountAm;
	private BigDecimal mrSoldStockAm;
	private BigDecimal givenAsAuthStockAm;
	private BigDecimal returnAsAuthStockAm;	
	private BigDecimal soldAsAuthStockAm;	
	private BigDecimal closingStockAm;

	private int openingStockNo;
	private int givenStockNo;
	private int returnStockNo;
	private int soldStockNo;
	private int soldStockDiscountNo;
	private int givenAsAuthStockNo;
	private int returnAsAuthStockNo;
	private int soldAsAuthStockNo;
	private int closingStockNo;	
	
	private List<MrVisitM> mrVisits;

	public MemberVisitAc() {
		super();
	}
	public MemberVisitAc(long ownerAcNo, long startTs, long endTs, List<MrVisitM> mrVisits) {
		super();
		this.startTs = startTs;
		this.endTs = endTs;
		this.mrVisits = new ArrayList<MrVisitM>(mrVisits.size());
		this.ownerAcNo = ownerAcNo;
		totalVisits = 0;
		ownerVisits = 0;
		authVisits = 0;
		properVisits = 0;
		duration = 0;

		if(mrVisits != null) {
			
			Collections.sort(mrVisits, new Comparator<MrVisitM>() {
				@Override
				public int compare(MrVisitM lhs, MrVisitM rhs) {
					return (lhs.getStartTs() > rhs.getStartTs() ? 1 : (rhs.getStartTs() > lhs.getStartTs()) ? -1 : 0);
				}
			});
			
			boolean initAuth = true;
			boolean uniqueAuth = true;

			for(MrVisitM mrVisit: mrVisits) {

				if(!ConversionUtil.isTimeValid(mrVisit.getEndTs())) {
					continue;
				}
				totalVisits++;
				this.mrVisits.add(mrVisit);
				
				if(mrVisit.getVisitStatus().equals(EnumConstMr.VisitStatus_Ended)) {
					properVisits++;
				}
				duration += (endTs - startTs);
				
//				if(totalVisits == 0) {
//					ownerAcNo = mrVisit.getOwnerAcNo();
//					ownerAcNoDisplay = mrVisit.getOwnerAcNoDisplay();
//					ownerName = mrVisit.getOwnerName();
//				} else if(uniqueOwner && ownerAcNo != mrVisit.getOwnerAcNo()) {
//					ownerAcNo = DataUtil.ZERO_LONG;
//					ownerAcNoDisplay = DataUtil.EMPTY_STRING;
//					ownerName = DataUtil.EMPTY_STRING;
//					uniqueOwner = false;
//				}

				if(ownerAcNo == mrVisit.getOwnerAcNo()) {

					if(initAuth) {
						initAuth = false;
						authAcNo = mrVisit.getAuthAcNo();
						authAcNoDisplay = mrVisit.getAuthAcNoDisplay();
						authName = mrVisit.getAuthName();
					} else if(uniqueAuth && authAcNo != mrVisit.getAuthAcNo()) {
						authAcNo = DataUtil.ZERO_LONG;
						authAcNoDisplay = DataUtil.EMPTY_STRING;
						authName = DataUtil.EMPTY_STRING;
						uniqueAuth = false;
					}
					
					ownerVisits++;

					paidRegistrationAm = BDUtil.add(paidRegistrationAm, mrVisit.getPaidRegistrationAm());
					paidDepositAm = BDUtil.add(paidDepositAm, mrVisit.getPaidDepositAm());
					paidCollectedAm = BDUtil.add(paidCollectedAm, mrVisit.getPaidCollectedAm());
					paidSoldAm = BDUtil.add(paidSoldAm, mrVisit.getSoldPaidAm());
					paidInterestPenaltyAm = BDUtil.add(paidInterestPenaltyAm, mrVisit.getPaidInterestPenaltyAm());
					collectedReturnedDepositAm = BDUtil.add(collectedReturnedDepositAm, mrVisit.getReturnedDepositAm());

					givenStockAm = BDUtil.add(givenStockAm, mrVisit.getGivenStockAm());
					returnStockAm = BDUtil.add(returnStockAm, mrVisit.getReturnStockAm());
					soldStockAm = BDUtil.add(soldStockAm, mrVisit.getSoldStockAm());
					soldStockDiscountAm = BDUtil.add(soldStockDiscountAm, mrVisit.getSoldStockDiscountAm());
					mrSoldStockAm = BDUtil.add(mrSoldStockAm, mrVisit.getMrSoldStockAm());

					givenStockNo += mrVisit.getGivenStockNo();
					returnStockNo += mrVisit.getReturnStockNo();
					soldStockNo += mrVisit.getSoldStockNo();
					soldStockDiscountNo += mrVisit.getSoldStockDiscountNo();

				} else if(ownerAcNo == mrVisit.getAuthAcNo()) {

					authVisits++;
					
					collectedRegistrationAm = BDUtil.add(collectedRegistrationAm, mrVisit.getPaidRegistrationAm());
					collectedDepositAm = BDUtil.add(collectedDepositAm, mrVisit.getPaidInterestPenaltyAm());
					collectedCollectedAm = BDUtil.add(collectedCollectedAm, mrVisit.getPaidCollectedAm());
					collectedSoldAm = BDUtil.add(collectedSoldAm, mrVisit.getSoldPaidAm());
					collectedInterestPenaltyAm = BDUtil.add(collectedInterestPenaltyAm, mrVisit.getPaidInterestPenaltyAm());
					paidReturnedDepositAm = BDUtil.add(paidReturnedDepositAm, mrVisit.getReturnedDepositAm());
					
					givenAsAuthStockAm = BDUtil.add(givenAsAuthStockAm, mrVisit.getGivenStockAm());
					returnAsAuthStockAm = BDUtil.add(returnAsAuthStockAm, mrVisit.getReturnStockAm());
					soldAsAuthStockAm = BDUtil.add(soldAsAuthStockAm, mrVisit.getSoldStockAm());
					
					givenAsAuthStockNo += mrVisit.getGivenStockNo();
					returnAsAuthStockNo += mrVisit.getReturnStockNo();					
					soldAsAuthStockNo += mrVisit.getSoldStockNo();					
				}
				
//				openingBalanceAm = BDUtil.add(openingBalanceAm, mrVisit.get);
//				closingBalanceAm = BDUtil.add(closingBalanceAm, mrVisit.get);
//				openingStockAm = BDUtil.add(openingStockAm, mrVisit.getOpeningStockAm());
//				closingStockAm = BDUtil.add(closingStockAm, mrVisit.getClosingStockAm());
//				openingStockNo += mrVisit.getOpeningStockNo();
//				closingStockNo += mrVisit.getClosingStockNo();				
			}
		}
	}
	public List<MrVisitM> getMrVisits() {
		return mrVisits;
	}
	public void setMrVisits(List<MrVisitM> mrVisits) {
		this.mrVisits = mrVisits;
	}
	public void addMrVisit(MrVisitM mrVisit) {
		if(this.mrVisits == null) {
			this.mrVisits = new ArrayList<MrVisitM>();
		}
		this.mrVisits.add(mrVisit);
	}
	public long getStartTs() {
		return startTs;
	}
	public void setStartTs(long startTs) {
		this.startTs = startTs;
	}
	public long getEndTs() {
		return endTs;
	}
	public void setEndTs(long endTs) {
		this.endTs = endTs;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public int getTotalVisits() {
		return totalVisits;
	}
	public void setTotalVisits(int totalVisits) {
		this.totalVisits = totalVisits;
	}
	public int getOwnerVisits() {
		return ownerVisits;
	}
	public void setOwnerVisits(int ownerVisits) {
		this.ownerVisits = ownerVisits;
	}
	public int getAuthVisits() {
		return authVisits;
	}
	public void setAuthVisits(int authVisits) {
		this.authVisits = authVisits;
	}
	public int getProperVisits() {
		return properVisits;
	}
	public void setProperVisits(int properVisits) {
		this.properVisits = properVisits;
	}
	public String getOwnerRole() {
		return ownerRole;
	}
	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}
	public String getAuthRole() {
		return authRole;
	}
	public void setAuthRole(String authRole) {
		this.authRole = authRole;
	}
	public long getOwnerAcNo() {
		return ownerAcNo;
	}
	public void setOwnerAcNo(long ownerAcNo) {
		this.ownerAcNo = ownerAcNo;
	}
	public long getAuthAcNo() {
		return authAcNo;
	}
	public void setAuthAcNo(long authAcNo) {
		this.authAcNo = authAcNo;
	}
	public String getOwnerAcNoDisplay() {
		return ownerAcNoDisplay;
	}
	public void setOwnerAcNoDisplay(String ownerAcNoDisplay) {
		this.ownerAcNoDisplay = ownerAcNoDisplay;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getAuthAcNoDisplay() {
		return authAcNoDisplay;
	}
	public void setAuthAcNoDisplay(String authAcNoDisplay) {
		this.authAcNoDisplay = authAcNoDisplay;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public BigDecimal getOpeningBalanceAm() {
		return openingBalanceAm;
	}
	public void setOpeningBalanceAm(BigDecimal openingBalanceAm) {
		this.openingBalanceAm = openingBalanceAm;
	}
	public BigDecimal getPaidRegistrationAm() {
		return paidRegistrationAm;
	}
	public void setPaidRegistrationAm(BigDecimal paidRegistrationAm) {
		this.paidRegistrationAm = paidRegistrationAm;
	}
	public BigDecimal getPaidDepositAm() {
		return paidDepositAm;
	}
	public void setPaidDepositAm(BigDecimal paidDepositAm) {
		this.paidDepositAm = paidDepositAm;
	}
	public BigDecimal getPaidCollectedAm() {
		return paidCollectedAm;
	}
	public void setPaidCollectedAm(BigDecimal paidCollectedAm) {
		this.paidCollectedAm = paidCollectedAm;
	}
	public BigDecimal getPaidSoldAm() {
		return paidSoldAm;
	}
	public void setPaidSoldAm(BigDecimal paidSoldAm) {
		this.paidSoldAm = paidSoldAm;
	}
	public BigDecimal getPaidInterestPenaltyAm() {
		return paidInterestPenaltyAm;
	}
	public void setPaidInterestPenaltyAm(BigDecimal paidInterestPenaltyAm) {
		this.paidInterestPenaltyAm = paidInterestPenaltyAm;
	}
	public BigDecimal getCollectedReturnedDepositAm() {
		return collectedReturnedDepositAm;
	}
	public void setCollectedReturnedDepositAm(BigDecimal collectedReturnedDepositAm) {
		this.collectedReturnedDepositAm = collectedReturnedDepositAm;
	}
	public BigDecimal getPaidReturnedDepositAm() {
		return paidReturnedDepositAm;
	}
	public void setPaidReturnedDepositAm(BigDecimal paidReturnedDepositAm) {
		this.paidReturnedDepositAm = paidReturnedDepositAm;
	}
	public BigDecimal getCollectedRegistrationAm() {
		return collectedRegistrationAm;
	}
	public void setCollectedRegistrationAm(BigDecimal collectedRegistrationAm) {
		this.collectedRegistrationAm = collectedRegistrationAm;
	}
	public BigDecimal getCollectedDepositAm() {
		return collectedDepositAm;
	}
	public void setCollectedDepositAm(BigDecimal collectedDepositAm) {
		this.collectedDepositAm = collectedDepositAm;
	}
	public BigDecimal getCollectedCollectedAm() {
		return collectedCollectedAm;
	}
	public void setCollectedCollectedAm(BigDecimal collectedCollectedAm) {
		this.collectedCollectedAm = collectedCollectedAm;
	}
	public BigDecimal getCollectedSoldAm() {
		return collectedSoldAm;
	}
	public void setCollectedSoldAm(BigDecimal collectedSoldAm) {
		this.collectedSoldAm = collectedSoldAm;
	}
	public BigDecimal getCollectedInterestPenaltyAm() {
		return collectedInterestPenaltyAm;
	}
	public void setCollectedInterestPenaltyAm(BigDecimal collectedInterestPenaltyAm) {
		this.collectedInterestPenaltyAm = collectedInterestPenaltyAm;
	}
	public BigDecimal getClosingBalanceAm() {
		return closingBalanceAm;
	}
	public void setClosingBalanceAm(BigDecimal closingBalanceAm) {
		this.closingBalanceAm = closingBalanceAm;
	}
	public BigDecimal getOpeningStockAm() {
		return openingStockAm;
	}
	public void setOpeningStockAm(BigDecimal openingStockAm) {
		this.openingStockAm = openingStockAm;
	}
	public BigDecimal getGivenStockAm() {
		return givenStockAm;
	}
	public void setGivenStockAm(BigDecimal givenStockAm) {
		this.givenStockAm = givenStockAm;
	}
	public BigDecimal getReturnStockAm() {
		return returnStockAm;
	}
	public void setReturnStockAm(BigDecimal returnStockAm) {
		this.returnStockAm = returnStockAm;
	}
	public BigDecimal getSoldStockAm() {
		return soldStockAm;
	}
	public void setSoldStockAm(BigDecimal soldStockAm) {
		this.soldStockAm = soldStockAm;
	}
	public BigDecimal getSoldStockDiscountAm() {
		return soldStockDiscountAm;
	}
	public void setSoldStockDiscountAm(BigDecimal soldStockDiscountAm) {
		this.soldStockDiscountAm = soldStockDiscountAm;
	}
	public BigDecimal getMrSoldStockAm() {
		return mrSoldStockAm;
	}
	public void setMrSoldStockAm(BigDecimal mrSoldStockAm) {
		this.mrSoldStockAm = mrSoldStockAm;
	}
	public BigDecimal getGivenAsAuthStockAm() {
		return givenAsAuthStockAm;
	}
	public void setGivenAsAuthStockAm(BigDecimal givenAsAuthStockAm) {
		this.givenAsAuthStockAm = givenAsAuthStockAm;
	}
	public BigDecimal getReturnAsAuthStockAm() {
		return returnAsAuthStockAm;
	}
	public void setReturnAsAuthStockAm(BigDecimal returnAsAuthStockAm) {
		this.returnAsAuthStockAm = returnAsAuthStockAm;
	}
	public BigDecimal getSoldAsAuthStockAm() {
		return soldAsAuthStockAm;
	}
	public void setSoldAsAuthStockAm(BigDecimal soldAsAuthStockAm) {
		this.soldAsAuthStockAm = soldAsAuthStockAm;
	}
	public BigDecimal getClosingStockAm() {
		return closingStockAm;
	}
	public void setClosingStockAm(BigDecimal closingStockAm) {
		this.closingStockAm = closingStockAm;
	}
	public int getOpeningStockNo() {
		return openingStockNo;
	}
	public void setOpeningStockNo(int openingStockNo) {
		this.openingStockNo = openingStockNo;
	}
	public int getGivenStockNo() {
		return givenStockNo;
	}
	public void setGivenStockNo(int givenStockNo) {
		this.givenStockNo = givenStockNo;
	}
	public int getReturnStockNo() {
		return returnStockNo;
	}
	public void setReturnStockNo(int returnStockNo) {
		this.returnStockNo = returnStockNo;
	}
	public int getSoldStockNo() {
		return soldStockNo;
	}
	public void setSoldStockNo(int soldStockNo) {
		this.soldStockNo = soldStockNo;
	}
	public int getSoldStockDiscountNo() {
		return soldStockDiscountNo;
	}
	public void setSoldStockDiscountNo(int soldStockDiscountNo) {
		this.soldStockDiscountNo = soldStockDiscountNo;
	}
	public int getGivenAsAuthStockNo() {
		return givenAsAuthStockNo;
	}
	public void setGivenAsAuthStockNo(int givenAsAuthStockNo) {
		this.givenAsAuthStockNo = givenAsAuthStockNo;
	}
	public int getReturnAsAuthStockNo() {
		return returnAsAuthStockNo;
	}
	public void setReturnAsAuthStockNo(int returnAsAuthStockNo) {
		this.returnAsAuthStockNo = returnAsAuthStockNo;
	}
	public int getSoldAsAuthStockNo() {
		return soldAsAuthStockNo;
	}
	public void setSoldAsAuthStockNo(int soldAsAuthStockNo) {
		this.soldAsAuthStockNo = soldAsAuthStockNo;
	}
	public int getClosingStockNo() {
		return closingStockNo;
	}
	public void setClosingStockNo(int closingStockNo) {
		this.closingStockNo = closingStockNo;
	}
}
