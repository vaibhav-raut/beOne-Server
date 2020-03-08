package com.beone.udaan.mr.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.persistence.bo.BaseBO;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;
import com.beone.shg.net.webservice.rest.model.resp.MRoleValue;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.config.DBConstMr;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.Lot;
import com.beone.udaan.mr.persistence.model.MrVisit;
import com.beone.udaan.mr.persistence.model.PMAc;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.model.StockType;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;
import com.beone.udaan.mr.persistence.support.EnumUtilMr;
import com.beone.udaan.mr.persistence.support.StockTxTypeFormula;
import com.beone.udaan.mr.ppo.util.MrProcessJobBuilder;
import com.beone.udaan.mr.service.model.ItemConditionEnum.ItemConditionValue;
import com.beone.udaan.mr.service.model.LotM;
import com.beone.udaan.mr.service.model.MrStock;
import com.beone.udaan.mr.service.model.MrVisitM;
import com.beone.udaan.mr.service.model.PayTransfer;
import com.beone.udaan.mr.service.model.ProductPricingEnum.ProductPricingValue;
import com.beone.udaan.mr.service.model.StockTypeM;
import com.beone.udaan.mr.service.model.PayTransfer.PayTx;
import com.beone.udaan.mr.service.model.StockItemM;
import com.beone.udaan.mr.service.model.StockTransfer;
import com.beone.udaan.mr.service.model.StockTxM;
import com.beone.udaan.mr.service.model.StockTxTypeEnum.StockTxTypeValue;
import com.beone.udaan.mr.service.model.VisitPrintData;
import com.beone.udaan.mr.service.model.VisitTypeEnum.VisitTypeValue;

@Component("mrVisitBO")
public class MrVisitBO extends BaseBO {

	@Autowired
	@Qualifier("mrAccountBO")
	private MrAccountBO mrAccountBO;

	@Autowired
	@Qualifier("mrProcessJobBuilder")
	private MrProcessJobBuilder mrProcessJobBuilder;

	
    public MrVisitM getActiveVisitsForOwner(long ownerAcNo, long authAcNo) throws BadRequestException {
    	
    	if(!ConversionUtil.isValidMemberAcNo(ownerAcNo)) {
			throw new BadRequestException("Invalid Owner Account No!");
    	}
    	PMAc ownerAccount = daoFactory.getPMAcDAO().findById(ownerAcNo);
    	if(ownerAccount == null) {
			throw new BadRequestException("Invalid Owner Account No: " + ownerAcNo + "!");
    	}
       	
    	List<MrVisit> openMrVisits = daoFactory.getMrVisitDAO().getActiveVisitsForOwner(ownerAcNo);
    	
    	List<MrVisitM> mrVisitMs = new ArrayList<MrVisitM>();
    	    	
    	if(openMrVisits != null) {
    		for(MrVisit mrVisit : openMrVisits) {
    			mrVisitMs.add(MrVisitM.buildMRVisit(mrVisit));
    		}
    	}
    	
    	MrVisitM selectedMrVisitM = null;
    	
    	for(MrVisitM mrVisitM : mrVisitMs) {
    		
    		if(mrVisitM.getVisitStatus().equals(EnumConstMr.VisitStatus_Started)) {
    			
    			if(((System.currentTimeMillis() - mrVisitM.getLatestUpdateTs()) - DateUtil.HOUR) > 0) {
    				endVisit(mrVisitM, EnumConstMr.VisitStatus_Auto_Closed);
    			} 
    			else if(mrVisitM.getAuthAcNo() != authAcNo) {
    				throw new BadRequestException("Visit of Auth : " + mrVisitM.getAuthAcNo() + " is in progress with Owner Account No: " + ownerAcNo + "!");
    			}
    			else {
    				selectedMrVisitM = mrVisitM;
    			}
    		} else {
				selectedMrVisitM = mrVisitM;
			}
    	}
    	
    	return selectedMrVisitM;
    }
    
    public MrVisitM createVisit(MrVisitM mrVisitM) throws BadRequestException {
    
    	if(mrVisitM == null) {
			throw new BadRequestException("Invalid MrVisit Request!");
    	}
    	
    	if(!ConversionUtil.isValidMemberAcNo(mrVisitM.getOwnerAcNo())) {
			throw new BadRequestException("Invalid Owner Account No!");
    	}
    	PMAc ownerAccount = daoFactory.getPMAcDAO().findById(mrVisitM.getOwnerAcNo());
    	if(ownerAccount == null) {
			throw new BadRequestException("Invalid Owner Account No: " + mrVisitM.getOwnerAcNo() + "!");
    	}
    	
    	PMAc authAccount = null;
    	if(ConversionUtil.isValidMemberAcNo(mrVisitM.getAuthAcNo())) {
        	authAccount = daoFactory.getPMAcDAO().findById(mrVisitM.getAuthAcNo());
        	if(authAccount == null) {
    			throw new BadRequestException("Invalid Auth Account No: " + mrVisitM.getAuthAcNo() + "!");
        	}
    	}
       	
    	MrVisitM openMrVisit = getActiveVisitsForOwner(mrVisitM.getOwnerAcNo(), mrVisitM.getAuthAcNo());
    	if(openMrVisit != null) {
			throw new BadRequestException("Owner : " + mrVisitM.getOwnerAcNo() + " have Active Visit; thus not allowed to Start New!");
    	}
    	
    	if(mrVisitM.getVisitStatus() == null || mrVisitM.getVisitStatus().isEmpty()) {
			throw new BadRequestException("Invalid VisitStatus!");
    	}
    	int visitStatusId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, mrVisitM.getVisitStatus());
    	if(visitStatusId <= 0) {
			throw new BadRequestException("Invalid VisitStatus: " + mrVisitM.getVisitStatus() + "!");
    	}
    	if(!EnumUtilMr.isVisitStatusToCreate(mrVisitM.getVisitStatus())) {
			throw new BadRequestException("Invalid VisitStatus to Create Visit!");
    	}
    	
    	if(mrVisitM.getVisitType() == null || mrVisitM.getVisitType().isEmpty()) {
			throw new BadRequestException("Invalid VisitType!");
    	}
    	VisitTypeValue visitType = EnumCacheMr.getVisitTypeValue(mrVisitM.getVisitType());
    	if(visitType == null) {
			throw new BadRequestException("Invalid VisitType: " + mrVisitM.getVisitType() + "!");
    	}
    	MRoleValue ownerRole = EnumCache.getMRoleValue(ownerAccount.getMProfile().getMroleId());
    	if(!visitType.getVisitFor().equals(ownerRole.getRole())) {
			throw new BadRequestException("Invalid VisitType: " + mrVisitM.getVisitType() + " for Owner with Role: " + ownerRole.getRole() + "!");
    	}
    	
    	// MR Visit Creation
    	MrVisit mrVisit = new MrVisit();
    	
    	mrVisit.setOwnerAcNo(mrVisitM.getOwnerAcNo());
    	if(authAccount != null) {
    		mrVisit.setAuthAcNo(mrVisitM.getAuthAcNo());
    	}
    	mrVisit.setVisitStatusId(visitStatusId);
    	mrVisit.setVisitTypeId(visitType.getVisitTypeId());
    	mrVisit.setScheduledTs(null);
    	mrVisit.setStartTs(null);
    	mrVisit.setEndTs(null);
    	mrVisit.setLatestUpdateTs(DateUtil.getCurrentTimeDate());
    	mrVisit.setOpeningOutstandingAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setSoldPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setSoldPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingOutstandingAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setOpeningInterestPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setPaidInterestPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingInterestPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setOpeningCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setPaidCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setReceivedCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setOpeningRegistrationAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setPaidRegistrationAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingRegistrationAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setOpeningDepositAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setPaidDepositAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setReturnedDepositAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingDepositAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setOpeningStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setReturnStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setSoldStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setMrSoldStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setGivenStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setOpeningStockNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setReturnStockNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setSoldStockNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setGivenStockNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setClosingStockNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setTotalStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setTotalStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setTotalStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setTotalStockNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setTotalStockReturnedNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setTotalStockDamagedNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setTotalStockSoldNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setTotalPaidSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setTotalPaidInterestPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setTotalPaidCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setTotalRegistrationPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setTotalDepositPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setTotalVisitCounter(DataUtil.ZERO_INTEGER);
    	mrVisit.setDescription(mrVisitM.getDescription());

    	daoFactory.getMrVisitDAO().persist(mrVisit);    	
    	
    	return MrVisitM.buildMRVisit(mrVisit);
    }
    
    public MrVisitM scheduledVisit(MrVisitM mrVisitM) throws BadRequestException {
    
    	if(mrVisitM == null) {
			throw new BadRequestException("Invalid MrVisit Request!");
    	}
    	if(mrVisitM.getMrVisitId() <= 0) {
			throw new BadRequestException("Invalid MrVisit ID!");
    	}
    	MrVisit mrVisit = daoFactory.getMrVisitDAO().findById(mrVisitM.getMrVisitId());
    	if(mrVisit == null) {
			throw new BadRequestException("Invalid MrVisit ID!");
    	}
    	
        String status = EnumCacheMr.getNameOfStatusValue(EnumConstMr.VisitStatus, mrVisit.getVisitStatusId());
    	if(!EnumUtilMr.isVisitStatusToScheduled(status)) {
			throw new BadRequestException("Invalid Current MrVisit Status :" + status + " to Schedule Visit!");
    	}
    	
    	if(mrVisitM.getScheduledTs() <= DateUtil.getCurrentTimeDate().getTime()) {
			throw new BadRequestException("Invalid Scheduled Time!");
    	}
    	
    	if(mrVisitM.getAuthAcNo() != mrVisit.getAuthAcNo()) {
        	if(ConversionUtil.isValidMemberAcNo(mrVisitM.getAuthAcNo())) {
        		PMAc authAccount = daoFactory.getPMAcDAO().findById(mrVisitM.getAuthAcNo());
        		if(authAccount != null) {
        	    	mrVisit.setAuthAcNo(mrVisitM.getAuthAcNo());
        		}
        	}
    	}
    	   	
    	mrVisit.setVisitStatusId(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Scheduled));
    	mrVisit.setScheduledTs(DateUtil.convertTimeToDate(mrVisitM.getScheduledTs()));
    	mrVisit.setLatestUpdateTs(DateUtil.getCurrentTimeDate());
    	mrVisit.setDescription(mrVisitM.getDescription());

    	daoFactory.getMrVisitDAO().merge(mrVisit);    	
    	
    	return MrVisitM.buildMRVisit(mrVisit);
    }
    
    public MrVisitM startVisit(MrVisitM mrVisitM) throws BadRequestException {
           
    	if(mrVisitM == null) {
			throw new BadRequestException("Invalid MrVisit Request!");
    	}
    	if(mrVisitM.getMrVisitId() <= 0) {
			throw new BadRequestException("Invalid MrVisit ID!");
    	}
    	MrVisit mrVisit = daoFactory.getMrVisitDAO().findById(mrVisitM.getMrVisitId());
    	if(mrVisit == null) {
			throw new BadRequestException("Invalid MrVisit ID!");
    	}
    	
        String status = EnumCacheMr.getNameOfStatusValue(EnumConstMr.VisitStatus, mrVisit.getVisitStatusId());
    	if(!EnumUtilMr.isVisitStatusToStart(status)) {
			throw new BadRequestException("Invalid Current MrVisit Status :" + status + " to Start Visit!");
    	}
    	
    	if(mrVisitM.getAuthAcNo() != mrVisit.getAuthAcNo()) {
        	if(ConversionUtil.isValidMemberAcNo(mrVisitM.getAuthAcNo())) {
        		PMAc authAccount = daoFactory.getPMAcDAO().findById(mrVisitM.getAuthAcNo());
        		if(authAccount != null) {
        	    	mrVisit.setAuthAcNo(mrVisitM.getAuthAcNo());
        		}
        	}
    	}
    	
    	PMAc ownerAccount = daoFactory.getPMAcDAO().findById(mrVisit.getOwnerAcNo());
    	if(ownerAccount == null) {
			throw new BadRequestException("Invalid Owner Account No: " + mrVisit.getOwnerAcNo() + "!");
    	}
    	
    	PMAc authAccount = daoFactory.getPMAcDAO().findById(mrVisit.getAuthAcNo());
    	if(authAccount == null) {
			throw new BadRequestException("Invalid Auth Account No: " + mrVisit.getAuthAcNo() + "!");
    	}
    	
    	// Calculate Interest Penalty for Outstanding Amount for Micro Retailer
    	if(EnumCache.getNameOfMRole(ownerAccount.getMProfile().getMroleId()).equals(EnumConstMr.MRole_Micro_Retailer)) {
    		calculateInterestPenalty(ownerAccount);
    	}
    	
    	mrVisit.setVisitStatusId(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Started));
    	
    	loadVisitAtStart(mrVisit, ownerAccount);
    	
    	mrVisit.setLocation(mrVisitM.getLocation());
    	mrVisit.setDescription(mrVisitM.getDescription());

    	daoFactory.getMrVisitDAO().merge(mrVisit);    	
    	
    	// Increment Visit Counter
    	ownerAccount.setThisMonthVisitCounter(ownerAccount.getThisMonthVisitCounter() + 1);
    	ownerAccount.setTotalVisitCounter(ownerAccount.getTotalVisitCounter() + 1);
    	ownerAccount.setLastVisitOn(DateUtil.getCurrentTimeDate());   	
    	daoFactory.getPMAcDAO().merge(ownerAccount);
    	
    	List<StockItem> stockItems = daoFactory.getStockItemDAO().getStockedItemsForOwner(mrVisit.getOwnerAcNo());
    	for(StockItem stockItem : stockItems) {
    		stockItem.setCheckFlag(EnumConstMr.CheckFlag_Unchecked);
    		stockItem.setCheckTs(DateUtil.getCurrentTimeDate());
    		daoFactory.getStockItemDAO().merge(stockItem);
    	}
    	
    	return MrVisitM.buildMRVisit(mrVisit);
    }
    
    protected void loadVisitAtStart(MrVisit mrVisit, PMAc ownerAccount) {
    	
    	mrVisit.setStartTs(DateUtil.getCurrentTimeDate());
    	mrVisit.setLatestUpdateTs(DateUtil.getCurrentTimeDate());
    	
    	mrVisit.setOpeningOutstandingAm(ownerAccount.getSoldPendingAm());
    	mrVisit.setSoldPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setSoldPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingOutstandingAm(ownerAccount.getSoldPendingAm());
    	mrVisit.setOpeningInterestPenaltyAm(ownerAccount.getPendingInterestPenaltyAm());
    	mrVisit.setPaidInterestPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingInterestPenaltyAm(ownerAccount.getPendingInterestPenaltyAm());
    	
    	mrVisit.setOpeningCollectedAm(ownerAccount.getCurrentCollectedAm());
    	mrVisit.setPaidCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setReceivedCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingCollectedAm(ownerAccount.getCurrentCollectedAm());
    	
    	mrVisit.setOpeningRegistrationAm(ownerAccount.getRegistrationPendingAm());
    	mrVisit.setPaidRegistrationAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingRegistrationAm(ownerAccount.getRegistrationPendingAm());
    	
    	mrVisit.setOpeningDepositAm(ownerAccount.getDepositPendingAm());
    	mrVisit.setPaidDepositAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setReturnedDepositAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingDepositAm(ownerAccount.getDepositPendingAm());
    	
    	mrVisit.setOpeningStockAm(ownerAccount.getCurrentStockAm());
    	mrVisit.setReturnStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setSoldStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setMrSoldStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setGivenStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	mrVisit.setClosingStockAm(ownerAccount.getCurrentStockAm());
    	
    	mrVisit.setOpeningStockNo(ownerAccount.getCurrentStockNo());
    	mrVisit.setReturnStockNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setSoldStockNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setGivenStockNo(DataUtil.ZERO_INTEGER);
    	mrVisit.setClosingStockNo(ownerAccount.getCurrentStockNo());
    	
    	mrVisit.setTotalStockAm(ownerAccount.getTotalStockAm());
    	mrVisit.setTotalStockReturnedAm(ownerAccount.getTotalStockReturnedAm());
    	mrVisit.setTotalStockDamagedAm(ownerAccount.getTotalStockDamagedAm());
    	mrVisit.setTotalStockSoldAm(ownerAccount.getTotalStockSoldAm());
    	mrVisit.setTotalStockNo(ownerAccount.getTotalStockNo());
    	mrVisit.setTotalStockReturnedNo(ownerAccount.getTotalStockReturnedNo());
    	mrVisit.setTotalStockDamagedNo(ownerAccount.getTotalStockDamagedNo());
    	mrVisit.setTotalStockSoldNo(ownerAccount.getTotalStockSoldNo());
     	mrVisit.setTotalPaidSoldAm(ownerAccount.getSoldPaidAm());
    	mrVisit.setTotalPaidInterestPenaltyAm(ownerAccount.getPaidInterestPenaltyAm());
    	mrVisit.setTotalPaidCollectedAm(ownerAccount.getTotalPaidCollectedAm());
    	mrVisit.setTotalRegistrationPaidAm(ownerAccount.getRegistrationPaidAm());
    	mrVisit.setTotalDepositPaidAm(ownerAccount.getDepositPaidAm());
    	
    	mrVisit.setTotalVisitCounter(ownerAccount.getTotalVisitCounter() + 1);    	
    }
    
    public void calculateInterestPenalty(PMAc ownerAccount) {

    	long currentDayStart = DateUtil.getCurrentDayStartTime();

    	if(ownerAccount.getInterestCalculatedOn() != null) {
    		long noOfDays = DateUtil.getNoOfDaysDifference(ownerAccount.getInterestCalculatedOn().getTime(), currentDayStart);

    		BigDecimal currentOutstanding = BDUtil.add(ownerAccount.getSoldPendingAm(), 
    				BDUtil.add(ownerAccount.getDepositPendingAm(), ownerAccount.getRegistrationPendingAm()));

    		BigDecimal currentInterest = BDUtil.divide(BDUtil.multiply(currentOutstanding, noOfDays), 1000);

    		ownerAccount.setPendingInterestPenaltyAm(GenAlgoUtil.roundUp(BDUtil.add(ownerAccount.getPendingInterestPenaltyAm(), currentInterest), 2));
    	}
    	
    	ownerAccount.setInterestCalculatedOn(DateUtil.convertTimeToDate(currentDayStart));

    	daoFactory.getPMAcDAO().merge(ownerAccount);
    }
    
    public MrVisitM endVisit(MrVisitM mrVisitM) throws BadRequestException {
    	return endVisit(mrVisitM, EnumConstMr.VisitStatus_Ended);
    }
    
    public MrVisitM endVisit(MrVisitM mrVisitM, String endType) throws BadRequestException {
           
    	if(mrVisitM == null) {
			throw new BadRequestException("Invalid MrVisit Request!");
    	}
    	if(mrVisitM.getMrVisitId() <= 0) {
			throw new BadRequestException("Invalid MrVisit ID!");
    	}
    	MrVisit mrVisit = daoFactory.getMrVisitDAO().findById(mrVisitM.getMrVisitId());
    	if(mrVisit == null) {
			throw new BadRequestException("Invalid MrVisit ID!");
    	}
    	
        String status = EnumCacheMr.getNameOfStatusValue(EnumConstMr.VisitStatus, mrVisit.getVisitStatusId());
    	if(!status.equals(EnumConstMr.VisitStatus_Started)) {
			throw new BadRequestException("Invalid Current MrVisit Status :" + status + " to End Visit!");
    	}
    	
    	PMAc ownerAccount = daoFactory.getPMAcDAO().findById(mrVisit.getOwnerAcNo());
    	if(ownerAccount == null) {
			throw new BadRequestException("Invalid Owner Account No: " + mrVisit.getOwnerAcNo() + "!");
    	}
   	    	
    	
    	if(endType.equals(EnumConstMr.VisitStatus_Ended)) {
    		mrVisit.setEndTs(DateUtil.getCurrentTimeDate());
    	} else {
    		mrVisit.setEndTs(mrVisit.getLatestUpdateTs());
    	}
		PMAc authAccount = null;
		if(ConversionUtil.isValidMemberAcNo(mrVisit.getAuthAcNo())) {
			authAccount = daoFactory.getPMAcDAO().findById(mrVisit.getAuthAcNo());
		}
    	
    	mrVisit.setVisitStatusId(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, endType));
    	mrVisit.setLatestUpdateTs(DateUtil.getCurrentTimeDate());
    	mrVisit.setLocation(mrVisitM.getLocation());
    	mrVisit.setDescription(mrVisitM.getDescription());

		if(authAccount != null) {
			BigDecimal authBalanceAm = BDUtil.add(authAccount.getSoldPendingAm(), 
					BDUtil.add(authAccount.getSoldPendingAm(), 
							BDUtil.add(authAccount.getPendingInterestPenaltyAm(), 
									BDUtil.add(authAccount.getDepositPendingAm(), 
											BDUtil.add(authAccount.getRegistrationPendingAm(), authAccount.getCurrentCollectedAm())))));
					
			mrVisit.setAuthBalanceAm(authBalanceAm);
			mrVisit.setAuthStockAm(authAccount.getCurrentStockAm());
			mrVisit.setAuthStockNo(authAccount.getCurrentStockNo());
		}

    	daoFactory.getMrVisitDAO().merge(mrVisit); 
    	
    	ownerAccount.setLastVisitOn(DateUtil.getCurrentTimeDate());   	
    	daoFactory.getPMAcDAO().merge(ownerAccount);
    	
    	return MrVisitM.buildMRVisit(mrVisit);
    }
      
    public StockItemM getStockItem(long stockItemId) throws BadRequestException {
    
    	if(stockItemId <= 0l) {
			throw new BadRequestException("Invalid Stock Item Id!");
    	}
    	
    	StockItem stockItem = daoFactory.getStockItemDAO().findById(stockItemId);
    	if(stockItem == null) {
			throw new BadRequestException("Invalid Stock Item Id :" + stockItem + "!");
    	}
    	
    	return StockItemM.buildStockItemM(stockItem, daoFactory);
    }

    public StockItemM checkStockItem(long stockItemId) throws BadRequestException {
    
    	if(stockItemId <= 0l) {
			throw new BadRequestException("Invalid Stock Item Id!");
    	}
    	
    	StockItem stockItem = daoFactory.getStockItemDAO().findById(stockItemId);
    	if(stockItem == null) {
			throw new BadRequestException("Invalid Stock Item Id :" + stockItem + "!");
    	}
    	
    	stockItem.setCheckTs(DateUtil.getCurrentTimeDate());
    	stockItem.setCheckFlag(EnumConstMr.CheckFlag_Checked);
    	
    	daoFactory.getStockItemDAO().merge(stockItem);
    	
    	return StockItemM.buildStockItemM(stockItem, daoFactory);
    }
    
    public MrStock checkStockItems(StockTransfer stockTransfer) throws BadRequestException {
    
    	
		if(stockTransfer.getStockItems() == null || stockTransfer.getStockItems().isEmpty()) {
			throw new BadRequestException("Empty StockItem list!");
		}
    	
		if(!ConversionUtil.isValidMemberAcNo(stockTransfer.getOwnerAcNo())) {
			throw new BadRequestException("Invalid Owner Ac No!");
		}
    	PMAc owner = daoFactory.getPMAcDAO().findById(stockTransfer.getOwnerAcNo());
		if(owner == null) {
			throw new BadRequestException("Invalid Owner Ac No: " + stockTransfer.getOwnerAcNo());
		}

		if(stockTransfer.getStockTxType() == null || !stockTransfer.getStockTxType().equals(EnumConstMr.StockTxType_Check)) {
			throw new BadRequestException("Invalid StockTxType: " + stockTransfer.getStockTxType());
		}
		StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(stockTransfer.getStockTxType());
		if(stockTxTypeValue == null) {
			throw new BadRequestException("Invalid StockTxType: " + stockTransfer.getStockTxType());
		}
    	    	
		for(StockItemM stockItemM : stockTransfer.getStockItems()) {
			
			StockItem stockItem = daoFactory.getStockItemDAO().findById(stockItemM.getStockItemId());
			if(stockItem == null) {
				continue;
			}

    		stockItem.setCheckTs(DateUtil.getCurrentTimeDate());
    		stockItem.setCheckFlag(EnumConstMr.CheckFlag_Checked);

    		daoFactory.getStockItemDAO().merge(stockItem);    		
    	}
    	
    	return mrAccountBO.getMrStock(stockTransfer.getOwnerAcNo());
    }
    
    public MrStock transferStock(long entryByAcNo, StockTransfer stockTransfer) throws BadRequestException {
    	
		if(stockTransfer.getStockItems() == null || stockTransfer.getStockItems().isEmpty()) {
			throw new BadRequestException("Empty StockItem list!");
		}
		
    	if(!ConversionUtil.isValidMemberAcNo(entryByAcNo)) {
			throw new BadRequestException("Invalid Entry By Ac No!");
		}
    	MProfile entryBy = daoFactory.getMProfileDAO().findById(entryByAcNo);
		if(entryBy == null) {
			throw new BadRequestException("Invalid Entry By Ac No: " + entryByAcNo);
		}
    	
		if(!ConversionUtil.isValidMemberAcNo(stockTransfer.getOwnerAcNo())) {
			throw new BadRequestException("Invalid Owner Ac No!");
		}
    	PMAc owner = daoFactory.getPMAcDAO().findById(stockTransfer.getOwnerAcNo());
		if(owner == null) {
			throw new BadRequestException("Invalid Owner Ac No: " + stockTransfer.getOwnerAcNo());
		}
    	
		PMAc auth = null;
		if(!EnumUtilMr.isStockTxTypeSold(stockTransfer.getStockTxType())) {
			if(!ConversionUtil.isValidMemberAcNo(stockTransfer.getAuthAcNo())) {
				throw new BadRequestException("Invalid Auth Ac No!");
			}
			auth = daoFactory.getPMAcDAO().findById(stockTransfer.getAuthAcNo());
			if(auth == null) {
				throw new BadRequestException("Invalid Auth Ac No: " + stockTransfer.getAuthAcNo());
			}
		}
    	
		MrVisit mrVisit = daoFactory.getMrVisitDAO().findById(stockTransfer.getMrVisitId());
		if(mrVisit == null) {
			throw new BadRequestException("Invalid MRVisit Id: " + stockTransfer.getMrVisitId());
		}
		String visitStatus = EnumCacheMr.getNameOfStatusValue(EnumConstMr.VisitStatus, mrVisit.getVisitStatusId());
		if(!visitStatus.equals(EnumConstMr.VisitStatus_Started)) {
			throw new BadRequestException("Invalid MRVisit Status: " + visitStatus + " for Stock Transfer!");
		}
		
		StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(stockTransfer.getStockTxType());
		if(stockTxTypeValue == null) {
			throw new BadRequestException("Invalid StockTxType: " + stockTransfer.getStockTxType());
		}
		StockTxTypeFormula stockTxTypeFormula = EnumCacheMr.getStockTxTypeFormula(stockTxTypeValue.getStockTxTypeId());
				
		for(StockItemM stockItemM : stockTransfer.getStockItems()) {
			
			StockItem stockItem = daoFactory.getStockItemDAO().findById(stockItemM.getStockItemId());
			if(stockItem == null) {
				continue;
			}
			
			int oldItemStatus = stockItem.getItemStatusId();
			int newItemStatus = EnumUtilMr.getItemStatusForStockTxType(stockTransfer.getStockTxType(), stockItem.getItemStatusId());
			if(newItemStatus == DBConstMr.INVALID_DB_ID) {
				continue;
			}
			if(stockTransfer.getStockTxType().equals(EnumConstMr.StockTxType_Given)) {
				if(stockItem.getOwner().getMemberAcNo() != stockTransfer.getAuthAcNo()) {
					throw new BadRequestException("StockItem Id : " + stockItem.getStockItemId() + " is Not Owned by Auth : " + stockTransfer.getAuthAcNo());
				}
			} else {
				if(stockItem.getOwner().getMemberAcNo() != stockTransfer.getOwnerAcNo()) {
					throw new BadRequestException("StockItem Id : " + stockItem.getStockItemId() + " is Not Owned by Owner : " + stockTransfer.getOwnerAcNo());
				}
			}
			
			stockItem.setCheckFlag(EnumConstMr.CheckFlag_Checked);
			stockItem.setCheckTs(DateUtil.getCurrentTimeDate());
			
			stockItem.setItemStatusId(newItemStatus);
			
			if(EnumUtilMr.isStockTxTypeSold(stockTransfer.getStockTxType())) {
				
				stockItem.setSoldPrice(BDUtil.sub(stockItem.getMrPrice(), stockItem.getDiscountAm()));
				
				if(stockItemM.getMrSoldPrice() != null && (stockItemM.getMrSoldPrice().compareTo(stockItem.getSoldPrice()) > 0)) {
					stockItem.setMrSoldPrice(stockItemM.getMrSoldPrice());
				} else {
					stockItem.setMrSoldPrice(BDUtil.multiply(stockItemM.getDisMrpPrice(), 0.9f));
				}
				
				// Change Check Flag to Sold
				stockItem.setCheckFlag(EnumConstMr.CheckFlag_Sold);				
			}
			else if(stockTransfer.getStockTxType().equals(EnumConstMr.StockTxType_Returned)) {
				stockItem.setOwner(auth);				
			}
			else {
				stockItem.setOwner(owner);				
			}
			
			daoFactory.getStockItemDAO().merge(stockItem);
			
			StockTx stockTx = new StockTx();
			
			stockTx.setStockTxTypeId(stockTxTypeValue.getStockTxTypeId());
			stockTx.setStockItemId(stockItemM.getStockItemId());
			stockTx.setStockTypeId(stockItem.getStockType().getStockTypeId());
			stockTx.setBrandId(stockItem.getStockType().getBrand().getBrandId());
			stockTx.setLotId(stockItem.getLot().getLotId());
			stockTx.setEntryByAcNo(entryByAcNo);
			stockTx.setOwnerAcNo(stockTransfer.getOwnerAcNo());
			stockTx.setName(stockItem.getStockType().getBrand().getNameDisplay() + " - " + stockItem.getStockType().getNameDisplay());
			stockTx.setAmount(stockItem.getMrPrice());
			
			if(EnumUtilMr.isStockTxTypeSold(stockTransfer.getStockTxType())) {
				stockTx.setExtraAm(stockItemM.getSoldPrice());
			} else {
				stockTx.setExtraAm(DataUtil.ZERO_BIG_DECIMAL);
				stockTx.setAuthAcNo(stockTransfer.getAuthAcNo());
			}
			
			stockTx.setMrVisitId(mrVisit.getMrVisitId());
			stockTx.setItemStatusId(newItemStatus);
			stockTx.setPrevItemStatusId(oldItemStatus);
			stockTx.setTxTs(DateUtil.getCurrentTimeDate());
			stockTx.setEntryTs(DateUtil.getCurrentTimeDate());
			stockTx.setVerifyTs(DateUtil.getCurrentTimeDate());
			stockTx.setDescription(stockTransfer.getDescription());
			stockTx.setEntryLocation(stockTransfer.getEntryLocation());			
			
			daoFactory.getStockTxDAO().persist(stockTx);
			
			mrProcessJobBuilder.pushNowProcessJobMr(stockTxTypeFormula.getPreP1ActionFormula(), 
					stockTxTypeFormula.getPreP2ActionFormula(), 
					stockTxTypeFormula.getPreP3ActionFormula(), stockTx);
			
			mrProcessJobBuilder.pushPostProcessJobMr(stockTxTypeFormula.getPostP1ActionFormula(), 
					stockTxTypeFormula.getPostP2ActionFormula(), 
					stockTxTypeFormula.getPostP3ActionFormula(), stockTx);
		}
		
    	return mrAccountBO.getMrStock(stockTransfer.getOwnerAcNo());
    }
    
    public MrStock directSold(long entryByAcNo, StockTransfer stockTransfer) throws BadRequestException {
    	
		if(stockTransfer.getStockItems() == null || stockTransfer.getStockItems().isEmpty()) {
			throw new BadRequestException("Empty StockItem list!");
		}
		
    	if(!ConversionUtil.isValidMemberAcNo(entryByAcNo)) {
			throw new BadRequestException("Invalid Entry By Ac No!");
		}
    	MProfile entryBy = daoFactory.getMProfileDAO().findById(entryByAcNo);
		if(entryBy == null) {
			throw new BadRequestException("Invalid Entry By Ac No: " + entryByAcNo);
		}
    	
		if(!ConversionUtil.isValidMemberAcNo(stockTransfer.getOwnerAcNo())) {
			throw new BadRequestException("Invalid Owner Ac No!");
		}
    	PMAc owner = daoFactory.getPMAcDAO().findById(stockTransfer.getOwnerAcNo());
		if(owner == null) {
			throw new BadRequestException("Invalid Owner Ac No: " + stockTransfer.getOwnerAcNo());
		}
		
		if(!EnumUtilMr.isStockTxTypeSold(stockTransfer.getStockTxType())) {
			throw new BadRequestException("Invalid StockTxType: " + stockTransfer.getStockTxType());
		}
		StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(stockTransfer.getStockTxType());
		if(stockTxTypeValue == null) {
			throw new BadRequestException("Invalid StockTxType: " + stockTransfer.getStockTxType());
		}
		StockTxTypeFormula stockTxTypeFormula = EnumCacheMr.getStockTxTypeFormula(stockTxTypeValue.getStockTxTypeId());
		
		MrVisit mrVisit = new MrVisit();
		{
			mrVisit.setOwnerAcNo(stockTransfer.getOwnerAcNo());
			mrVisit.setVisitTypeId(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Auto_Closed));
			mrVisit.setVisitTypeId(EnumCacheMr.getIndexOfVisitType(EnumConstMr.VisitType_Direct_Sold));
			
			loadVisitAtStart(mrVisit, owner);
			
			mrVisit.setEndTs(DateUtil.getCurrentTimeDate());
			
			daoFactory.getMrVisitDAO().persist(mrVisit);
		}
		
		for(StockItemM stockItemM : stockTransfer.getStockItems()) {
			
			StockItem stockItem = daoFactory.getStockItemDAO().findById(stockItemM.getStockItemId());
			if(stockItem == null) {
				continue;
			}
			
			int oldItemStatus = stockItem.getItemStatusId();
			int newItemStatus = EnumUtilMr.getItemStatusForStockTxType(stockTransfer.getStockTxType(), stockItem.getItemStatusId());
			if(newItemStatus == DBConstMr.INVALID_DB_ID) {
				continue;
			}
			if(stockItem.getOwner().getMemberAcNo() != stockTransfer.getOwnerAcNo()) {
				throw new BadRequestException("StockItem Id : " + stockItem.getStockItemId() + " is Not Owned by Owner : " + stockTransfer.getOwnerAcNo());
			}

			stockItem.setItemStatusId(newItemStatus);
			stockItem.setSoldPrice(stockItem.getMrPrice());

			if(stockItemM.getSoldPrice() != null && (stockItemM.getSoldPrice().compareTo(stockItem.getMrPrice()) > 0)) {
				stockItem.setSoldPrice(stockItemM.getSoldPrice());
			} else {
				stockItem.setSoldPrice(BDUtil.sub(stockItem.getMrPrice(), stockItem.getDiscountAm()));
			}
			stockItem.setMrSoldPrice(stockItem.getSoldPrice());

			// Change Check Flag to Sold
			stockItem.setCheckFlag(EnumConstMr.CheckFlag_Sold);				
			stockItem.setCheckTs(DateUtil.getCurrentTimeDate());

			daoFactory.getStockItemDAO().merge(stockItem);
			
			StockTx stockTx = new StockTx();
			
			stockTx.setStockTxTypeId(stockTxTypeValue.getStockTxTypeId());
			stockTx.setStockItemId(stockItemM.getStockItemId());
			stockTx.setStockTypeId(stockItem.getStockType().getStockTypeId());
			stockTx.setBrandId(stockItem.getStockType().getBrand().getBrandId());
			stockTx.setLotId(stockItem.getLot().getLotId());
			stockTx.setEntryByAcNo(entryByAcNo);
			stockTx.setOwnerAcNo(stockTransfer.getOwnerAcNo());
			stockTx.setMrVisitId(mrVisit.getMrVisitId());
			stockTx.setName(stockItem.getStockType().getBrand().getNameDisplay() + " - " + stockItem.getStockType().getNameDisplay());
			stockTx.setAmount(stockItem.getMrPrice());
			stockTx.setExtraAm(stockItemM.getMrSoldPrice());
			stockTx.setItemStatusId(newItemStatus);
			stockTx.setPrevItemStatusId(oldItemStatus);
			stockTx.setTxTs(DateUtil.getCurrentTimeDate());
			stockTx.setEntryTs(DateUtil.getCurrentTimeDate());
			stockTx.setVerifyTs(DateUtil.getCurrentTimeDate());
			stockTx.setDescription(stockTransfer.getDescription());
			stockTx.setEntryLocation(stockTransfer.getEntryLocation());			
			
			daoFactory.getStockTxDAO().persist(stockTx);
			
			mrProcessJobBuilder.pushNowProcessJobMr(stockTxTypeFormula.getPreP1ActionFormula(), 
					stockTxTypeFormula.getPreP2ActionFormula(), 
					stockTxTypeFormula.getPreP3ActionFormula(), stockTx);
			
			mrProcessJobBuilder.pushPostProcessJobMr(stockTxTypeFormula.getPostP1ActionFormula(), 
					stockTxTypeFormula.getPostP2ActionFormula(), 
					stockTxTypeFormula.getPostP3ActionFormula(), stockTx);
		}
		
    	return mrAccountBO.getMrStock(stockTransfer.getOwnerAcNo());
    }
    
    public List<StockItemM> discountItems(long entryByAcNo, StockTransfer stockTransfer) throws BadRequestException {
    	return discountItemMs(entryByAcNo, stockTransfer.getItemCondition(), stockTransfer.getStockTxType(), stockTransfer.getStockItems());
    }
    
    public StockTypeM discountStockType(long entryByAcNo, StockTypeM stockTypeM) throws BadRequestException {

    	if(stockTypeM == null) {
    		throw new BadRequestException("Invalid StockType!");
    	}
    	StockType stockType = daoFactory.getStockTypeDAO().findById(stockTypeM.getStockTypeId());
    	if(stockType == null) {
    		throw new BadRequestException("Invalid StockType!");
    	}

    	if(!ConversionUtil.isValidMemberAcNo(entryByAcNo)) {
    		throw new BadRequestException("Invalid Entry By Ac No!");
    	}
    	MProfile entryBy = daoFactory.getMProfileDAO().findById(entryByAcNo);
    	if(entryBy == null) {
    		throw new BadRequestException("Invalid Entry By Ac No: " + entryByAcNo);
    	}
    	MRoleValue entryRole = EnumCache.getMRoleValue(entryBy.getMroleId());
    	if(!entryRole.getRoleCategory().equals(EnumConstMr.MRole_Category_HUB_Manager)) {
    		throw new BadRequestException("Entry By Ac No: " + entryByAcNo + " invalid Role Category : " + entryRole.getRoleCategory());
    	}

    	boolean isItemConditionChanged = false;
    	boolean isDisplayNameChanged = false;
    	ItemConditionValue itemConditionValue = null;
    	
    	if(stockTypeM.getItemCondition() != null) {
    		itemConditionValue = EnumCacheMr.getItemConditionValue(stockTypeM.getItemCondition());
    		if(itemConditionValue.getItemConditionId() != stockType.getItemConditionId()) {
    	    	stockType.setItemConditionId(itemConditionValue.getItemConditionId());
    	    	isItemConditionChanged = true;
    		}
    	}
    	
    	if(stockTypeM.getProductPricing() != null) {
    		ProductPricingValue productPricingValue = EnumCacheMr.getProductPricingValue(stockTypeM.getProductPricing());
    		if(productPricingValue.getProductPricingId() != stockType.getProductPricingId()) {
    	    	stockType.setProductPricingId(productPricingValue.getProductPricingId());
    		}
    	}
    	
    	if(stockTypeM.getNameDisplay() != null && !stockType.getNameDisplay().equals(stockTypeM.getNameDisplay())) {
    		stockType.setNameDisplay(stockTypeM.getNameDisplay());
    		isDisplayNameChanged = true;
    	}

    	daoFactory.getStockTypeDAO().merge(stockType);

    	// Load Items for StockType
    	List<StockItem> stockItems = daoFactory.getStockItemDAO().getActiveItemForType(stockTypeM.getStockTypeId());

    	
    	StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(EnumConstMr.StockTxType_Discounted);
    	StockTxTypeFormula stockTxTypeFormula = EnumCacheMr.getStockTxTypeFormula(stockTxTypeValue.getStockTxTypeId());

    	int createdItemId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Created);

    	for(StockItem stockItem : stockItems) {
    		
    		// Don't Update status of Damaged Item
    		if(EnumCacheMr.getNameOfItemCondition(stockItem.getItemConditionId()).contains("Damage")) {
    			continue;
    		}

    		BigDecimal oldDiscountAm = DataUtil.ZERO_BIG_DECIMAL;			
    		if(stockItem.getDiscountAm() != null) {
    			oldDiscountAm = stockItem.getDiscountAm();
    		}

    		if(isDisplayNameChanged) {
    			stockItem.setName(stockType.getNameDisplay());
    		}
    		
    		if(isItemConditionChanged) {
    			stockItem.setItemConditionId(itemConditionValue.getItemConditionId());
    			stockItem.setDisMrpPrice(GenAlgoUtil.roundUp(BDUtil.multiply(stockItem.getMrpPrice(), (1.0f - itemConditionValue.getMrpDiscountPer())), 0));
    			stockItem.setDiscountAm(GenAlgoUtil.roundHalfUp(BDUtil.multiply(stockItem.getMrPrice(), itemConditionValue.getSpDiscountPer()), 0));
    			stockItem.setDiscountPer(itemConditionValue.getSpDiscountPer());
    		}

    		daoFactory.getStockItemDAO().merge(stockItem);

    		// Create StockTx for Items which are not in Created State
    		if(isItemConditionChanged && (stockItem.getItemStatusId() != createdItemId)) {

    			StockTx stockTx = new StockTx();

    			stockTx.setStockTxTypeId(stockTxTypeValue.getStockTxTypeId());
    			stockTx.setStockItemId(stockItem.getStockItemId());
    			stockTx.setStockTypeId(stockItem.getStockType().getStockTypeId());
    			stockTx.setBrandId(stockItem.getStockType().getBrand().getBrandId());
    			stockTx.setLotId(stockItem.getLot().getLotId());
    			stockTx.setOwnerAcNo(stockItem.getOwner().getMemberAcNo());
    			stockTx.setName(stockItem.getStockType().getBrand().getNameDisplay() + " - " + stockItem.getStockType().getNameDisplay());
    			stockTx.setAmount(stockItem.getDiscountAm());
    			stockTx.setExtraAm(oldDiscountAm);
    			stockTx.setTxTs(DateUtil.getCurrentTimeDate());
    			stockTx.setEntryTs(DateUtil.getCurrentTimeDate());
    			stockTx.setVerifyTs(DateUtil.getCurrentTimeDate());
    			stockTx.setDescription("Changed Item Condition : " + itemConditionValue.getItemCondition());

    			daoFactory.getStockTxDAO().persist(stockTx);

    			//			mrProcessJobBuilder.pushNowProcessJobMr(stockTxTypeFormula.getPreP1ActionFormula(), 
    			//					stockTxTypeFormula.getPreP2ActionFormula(), 
    			//					stockTxTypeFormula.getPreP3ActionFormula(), stockTx);

    			mrProcessJobBuilder.pushPostProcessJobMr(stockTxTypeFormula.getPostP1ActionFormula(), 
    					stockTxTypeFormula.getPostP2ActionFormula(), 
    					stockTxTypeFormula.getPostP3ActionFormula(), stockTx);

    		}
    	}

    	return StockTypeM.buildStockType(stockType);    	
    }
    
    public LotM discountStockLot(long entryByAcNo, String itemCondition, LotM lotM) throws BadRequestException {
    	
    	if(lotM == null) {
			throw new BadRequestException("Invalid Lot!");
    	}
    	Lot lot = daoFactory.getLotDAO().findById(lotM.getLotId());
    	if(lot == null) {
			throw new BadRequestException("Invalid Lot!");
    	}
    	
    	List<StockItem> stockItems = daoFactory.getStockItemDAO().getActiveItemForLot(lotM.getLotId());
    	   	
		if(stockItems == null || stockItems.isEmpty()) {
			throw new BadRequestException("Empty StockItem list!");
		}
		
    	if(!ConversionUtil.isValidMemberAcNo(entryByAcNo)) {
			throw new BadRequestException("Invalid Entry By Ac No!");
		}
    	MProfile entryBy = daoFactory.getMProfileDAO().findById(entryByAcNo);
		if(entryBy == null) {
			throw new BadRequestException("Invalid Entry By Ac No: " + entryByAcNo);
		}
		MRoleValue entryRole = EnumCache.getMRoleValue(entryBy.getMroleId());
		if(!entryRole.getRoleCategory().equals(EnumConstMr.MRole_Category_HUB_Manager)) {
			throw new BadRequestException("Entry By Ac No: " + entryByAcNo + " invalid Role Category : " + entryRole.getRoleCategory());
		}
		
		ItemConditionValue itemConditionValue = EnumCacheMr.getItemConditionValue(itemCondition);
		if(itemConditionValue == null) {
			throw new BadRequestException("Invalid itemCondition : " + itemCondition);
		}
		
		StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(EnumConstMr.StockTxType_Discounted);
		StockTxTypeFormula stockTxTypeFormula = EnumCacheMr.getStockTxTypeFormula(stockTxTypeValue.getStockTxTypeId());
		
		int createdItemId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Created);
		
		for(StockItem stockItem : stockItems) {
    		
    		// Don't Update status of Damaged Item
    		if(EnumCacheMr.getNameOfItemCondition(stockItem.getItemConditionId()).contains("Damage")) {
    			continue;
    		}

			BigDecimal oldDiscountAm = DataUtil.ZERO_BIG_DECIMAL;			
			if(stockItem.getDiscountAm() != null) {
				oldDiscountAm = stockItem.getDiscountAm();
			}

			stockItem.setItemConditionId(itemConditionValue.getItemConditionId());
			stockItem.setDisMrpPrice(GenAlgoUtil.roundUp(BDUtil.multiply(stockItem.getMrpPrice(), (1.0f - itemConditionValue.getMrpDiscountPer())), 0));
			stockItem.setDiscountAm(GenAlgoUtil.roundHalfUp(BDUtil.multiply(stockItem.getMrPrice(), itemConditionValue.getSpDiscountPer()), 0));
			stockItem.setDiscountPer(itemConditionValue.getSpDiscountPer());

			daoFactory.getStockItemDAO().merge(stockItem);

			// Create StockTx for Items which are not in Created State
			if(stockItem.getItemStatusId() != createdItemId) {

				StockTx stockTx = new StockTx();

				stockTx.setStockTxTypeId(stockTxTypeValue.getStockTxTypeId());
				stockTx.setStockItemId(stockItem.getStockItemId());
				stockTx.setStockTypeId(stockItem.getStockType().getStockTypeId());
				stockTx.setBrandId(stockItem.getStockType().getBrand().getBrandId());
				stockTx.setLotId(stockItem.getLot().getLotId());
				stockTx.setOwnerAcNo(stockItem.getOwner().getMemberAcNo());
				stockTx.setName(stockItem.getStockType().getBrand().getNameDisplay() + " - " + stockItem.getStockType().getNameDisplay());
				stockTx.setAmount(stockItem.getDiscountAm());
				stockTx.setExtraAm(oldDiscountAm);
				stockTx.setTxTs(DateUtil.getCurrentTimeDate());
				stockTx.setEntryTs(DateUtil.getCurrentTimeDate());
				stockTx.setVerifyTs(DateUtil.getCurrentTimeDate());
				stockTx.setDescription("Changed Item Condition : " + itemConditionValue.getItemCondition());

				daoFactory.getStockTxDAO().persist(stockTx);

				//			mrProcessJobBuilder.pushNowProcessJobMr(stockTxTypeFormula.getPreP1ActionFormula(), 
				//					stockTxTypeFormula.getPreP2ActionFormula(), 
				//					stockTxTypeFormula.getPreP3ActionFormula(), stockTx);

				mrProcessJobBuilder.pushPostProcessJobMr(stockTxTypeFormula.getPostP1ActionFormula(), 
						stockTxTypeFormula.getPostP2ActionFormula(), 
						stockTxTypeFormula.getPostP3ActionFormula(), stockTx);

			}
		}
		
		return LotM.buildLot(lot);
    }
    
    protected List<StockItemM> discountItems(long entryByAcNo, String itemCondition, List<StockItem> stockItems) throws BadRequestException {
    	
		if(stockItems == null || stockItems.isEmpty()) {
			throw new BadRequestException("Empty StockItem list!");
		}
		
    	if(!ConversionUtil.isValidMemberAcNo(entryByAcNo)) {
			throw new BadRequestException("Invalid Entry By Ac No!");
		}
    	MProfile entryBy = daoFactory.getMProfileDAO().findById(entryByAcNo);
		if(entryBy == null) {
			throw new BadRequestException("Invalid Entry By Ac No: " + entryByAcNo);
		}
		MRoleValue entryRole = EnumCache.getMRoleValue(entryBy.getMroleId());
		if(!entryRole.getRoleCategory().equals(EnumConstMr.MRole_Category_HUB_Manager)) {
			throw new BadRequestException("Entry By Ac No: " + entryByAcNo + " invalid Role Category : " + entryRole.getRoleCategory());
		}
		
		ItemConditionValue itemConditionValue = EnumCacheMr.getItemConditionValue(itemCondition);
		if(itemConditionValue == null) {
			throw new BadRequestException("Invalid itemCondition : " + itemCondition);
		}
		
		StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(EnumConstMr.StockTxType_Discounted);
		StockTxTypeFormula stockTxTypeFormula = EnumCacheMr.getStockTxTypeFormula(stockTxTypeValue.getStockTxTypeId());
		
		List<StockItemM> stockItemMs = new ArrayList<StockItemM>();
		
		int createdItemId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Created);
		
		for(StockItem stockItem : stockItems) {
    		
    		// Don't Update status of Damaged Item
    		if(EnumCacheMr.getNameOfItemCondition(stockItem.getItemConditionId()).contains("Damage")) {
    			continue;
    		}

			BigDecimal oldDiscountAm = DataUtil.ZERO_BIG_DECIMAL;			
			if(stockItem.getDiscountAm() != null) {
				oldDiscountAm = stockItem.getDiscountAm();
			}

			stockItem.setItemConditionId(itemConditionValue.getItemConditionId());
			stockItem.setDisMrpPrice(GenAlgoUtil.roundUp(BDUtil.multiply(stockItem.getMrpPrice(), (1.0f - itemConditionValue.getMrpDiscountPer())), 0));
			stockItem.setDiscountAm(GenAlgoUtil.roundHalfUp(BDUtil.multiply(stockItem.getMrPrice(), itemConditionValue.getSpDiscountPer()), 0));
			stockItem.setDiscountPer(itemConditionValue.getSpDiscountPer());

			daoFactory.getStockItemDAO().merge(stockItem);

			// Create StockTx for Items which are not in Created State
			if(stockItem.getItemStatusId() != createdItemId) {

				StockTx stockTx = new StockTx();

				stockTx.setStockTxTypeId(stockTxTypeValue.getStockTxTypeId());
				stockTx.setStockItemId(stockItem.getStockItemId());
				stockTx.setStockTypeId(stockItem.getStockType().getStockTypeId());
				stockTx.setBrandId(stockItem.getStockType().getBrand().getBrandId());
				stockTx.setLotId(stockItem.getLot().getLotId());
				stockTx.setOwnerAcNo(stockItem.getOwner().getMemberAcNo());
				stockTx.setName(stockItem.getStockType().getBrand().getNameDisplay() + " - " + stockItem.getStockType().getNameDisplay());
				stockTx.setAmount(stockItem.getDiscountAm());
				stockTx.setExtraAm(oldDiscountAm);
				stockTx.setTxTs(DateUtil.getCurrentTimeDate());
				stockTx.setEntryTs(DateUtil.getCurrentTimeDate());
				stockTx.setVerifyTs(DateUtil.getCurrentTimeDate());
				stockTx.setDescription("Changed Item Condition : " + itemConditionValue.getItemCondition());

				daoFactory.getStockTxDAO().persist(stockTx);

				//			mrProcessJobBuilder.pushNowProcessJobMr(stockTxTypeFormula.getPreP1ActionFormula(), 
				//					stockTxTypeFormula.getPreP2ActionFormula(), 
				//					stockTxTypeFormula.getPreP3ActionFormula(), stockTx);

				mrProcessJobBuilder.pushPostProcessJobMr(stockTxTypeFormula.getPostP1ActionFormula(), 
						stockTxTypeFormula.getPostP2ActionFormula(), 
						stockTxTypeFormula.getPostP3ActionFormula(), stockTx);

			}

			stockItemMs.add(StockItemM.buildStockItemM(stockItem, daoFactory));
		}
		
		return stockItemMs;
    }
   
    protected List<StockItemM> discountItemMs(long entryByAcNo, String itemCondition, String stockTxType, List<StockItemM> stockItems) throws BadRequestException {
    	
		if(stockItems == null || stockItems.isEmpty()) {
			throw new BadRequestException("Empty StockItem list!");
		}
		
    	if(!ConversionUtil.isValidMemberAcNo(entryByAcNo)) {
			throw new BadRequestException("Invalid Entry By Ac No!");
		}
    	MProfile entryBy = daoFactory.getMProfileDAO().findById(entryByAcNo);
		if(entryBy == null) {
			throw new BadRequestException("Invalid Entry By Ac No: " + entryByAcNo);
		}
		MRoleValue entryRole = EnumCache.getMRoleValue(entryBy.getMroleId());
		if(!entryRole.getRoleCategory().equals(EnumConstMr.MRole_Category_HUB_Manager)) {
			throw new BadRequestException("Entry By Ac No: " + entryByAcNo + " invalid Role Category : " + entryRole.getRoleCategory());
		}
		
		ItemConditionValue itemConditionValue = EnumCacheMr.getItemConditionValue(itemCondition);
		if(itemConditionValue == null) {
			throw new BadRequestException("Invalid itemCondition : " + itemCondition);
		}
		
		if(!stockTxType.equals(EnumConstMr.StockTxType_Discounted)) {
			throw new BadRequestException("Invalid StockTxType: " + stockTxType);
		}
		StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(stockTxType);
		if(stockTxTypeValue == null) {
			throw new BadRequestException("Invalid StockTxType: " + stockTxType);
		}
		StockTxTypeFormula stockTxTypeFormula = EnumCacheMr.getStockTxTypeFormula(stockTxTypeValue.getStockTxTypeId());
		
		int createdItemId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Created);
		
		List<StockItemM> stockItemMs = new ArrayList<StockItemM>();
		
		for(StockItemM stockItemM : stockItems) {
			
			StockItem stockItem = daoFactory.getStockItemDAO().findById(stockItemM.getStockItemId());
			if(stockItem == null) {
				continue;
			}
			
			BigDecimal oldDiscountAm = DataUtil.ZERO_BIG_DECIMAL;			
			if(stockItem.getDiscountAm() != null) {
				oldDiscountAm = stockItem.getDiscountAm();
			}
			
			stockItem.setItemConditionId(itemConditionValue.getItemConditionId());
			stockItem.setDisMrpPrice(GenAlgoUtil.roundUp(BDUtil.multiply(stockItem.getMrpPrice(), (1.0f - itemConditionValue.getMrpDiscountPer())), 0));
			stockItem.setDiscountAm(GenAlgoUtil.roundHalfUp(BDUtil.multiply(stockItem.getMrPrice(), itemConditionValue.getSpDiscountPer()), 0));
			stockItem.setDiscountPer(itemConditionValue.getSpDiscountPer());

			daoFactory.getStockItemDAO().merge(stockItem);

			// Create StockTx for Items which are not in Created State
			if(stockItem.getItemStatusId() != createdItemId) {

				StockTx stockTx = new StockTx();

				stockTx.setStockTxTypeId(stockTxTypeValue.getStockTxTypeId());
				stockTx.setStockItemId(stockItemM.getStockItemId());
				stockTx.setStockTypeId(stockItem.getStockType().getStockTypeId());
				stockTx.setBrandId(stockItem.getStockType().getBrand().getBrandId());
				stockTx.setLotId(stockItem.getLot().getLotId());
				stockTx.setOwnerAcNo(stockItem.getOwner().getMemberAcNo());
				stockTx.setName(stockItem.getStockType().getBrand().getNameDisplay() + " - " + stockItem.getStockType().getNameDisplay());
				stockTx.setAmount(stockItem.getDiscountAm());
				stockTx.setExtraAm(oldDiscountAm);
				stockTx.setTxTs(DateUtil.getCurrentTimeDate());
				stockTx.setEntryTs(DateUtil.getCurrentTimeDate());
				stockTx.setVerifyTs(DateUtil.getCurrentTimeDate());
				stockTx.setDescription("Changed Item Condition : " + itemConditionValue.getItemCondition());

				daoFactory.getStockTxDAO().persist(stockTx);

				//			mrProcessJobBuilder.pushNowProcessJobMr(stockTxTypeFormula.getPreP1ActionFormula(), 
				//					stockTxTypeFormula.getPreP2ActionFormula(), 
				//					stockTxTypeFormula.getPreP3ActionFormula(), stockTx);

				mrProcessJobBuilder.pushPostProcessJobMr(stockTxTypeFormula.getPostP1ActionFormula(), 
						stockTxTypeFormula.getPostP2ActionFormula(), 
						stockTxTypeFormula.getPostP3ActionFormula(), stockTx);
			}
			
			stockItemMs.add(StockItemM.buildStockItemM(stockItem, daoFactory));
		}
		
		return stockItemMs;
    }
    
    public PayTransfer getOutstandingPayTxs(long memberAcNo) throws BadRequestException {
    	
		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Owner Ac No!");
		}
    	PMAc owner = daoFactory.getPMAcDAO().findById(memberAcNo);
		if(owner == null) {
			throw new BadRequestException("Invalid Owner Ac No: " + memberAcNo);
		}
    	
    	PayTransfer payTransfer = new PayTransfer();
    	payTransfer.setOwnerAcNo(memberAcNo);
    	
    	BigDecimal totalAmount = DataUtil.ZERO_BIG_DECIMAL;
    	
    	if(owner.getCurrentCollectedAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
    		PayTx payTx = new PayTx();
    		payTx.setStockTxType(EnumConstMr.StockTxType_Collection_Paid);
    		payTx.setOutstandingAmount(GenAlgoUtil.roundHalfUp(owner.getCurrentCollectedAm(),0));
    		payTx.setPayAmount(DataUtil.ZERO_BIG_DECIMAL);
    		
    		payTransfer.addPayTx(payTx);
    		
    		totalAmount = BDUtil.add(totalAmount, owner.getCurrentCollectedAm());
    	}
    	if(owner.getRegistrationPendingAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
    		PayTx payTx = new PayTx();
    		payTx.setStockTxType(EnumConstMr.StockTxType_Registration_Fee);
    		payTx.setOutstandingAmount(GenAlgoUtil.roundHalfUp(owner.getRegistrationPendingAm(),0));
    		payTx.setPayAmount(DataUtil.ZERO_BIG_DECIMAL);
    		
    		payTransfer.addPayTx(payTx);
    		
    		totalAmount = BDUtil.add(totalAmount, owner.getRegistrationPendingAm());
    	}
    	if(owner.getDepositPendingAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
    		PayTx payTx = new PayTx();
    		payTx.setStockTxType(EnumConstMr.StockTxType_Deposit);
    		payTx.setOutstandingAmount(GenAlgoUtil.roundHalfUp(owner.getDepositPendingAm(),0));
    		payTx.setPayAmount(DataUtil.ZERO_BIG_DECIMAL);
    		
    		payTransfer.addPayTx(payTx);
    		
    		totalAmount = BDUtil.add(totalAmount, owner.getDepositPendingAm());
    	}
    	if(owner.getPendingInterestPenaltyAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
    		PayTx payTx = new PayTx();
    		payTx.setStockTxType(EnumConstMr.StockTxType_Interest_Penalty);
    		payTx.setOutstandingAmount(GenAlgoUtil.roundHalfUp(owner.getPendingInterestPenaltyAm(),0));
    		payTx.setPayAmount(DataUtil.ZERO_BIG_DECIMAL);
    		
    		payTransfer.addPayTx(payTx);
    		
    		totalAmount = BDUtil.add(totalAmount, owner.getPendingInterestPenaltyAm());
    	}
    	if(owner.getSoldPendingAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
    		PayTx payTx = new PayTx();
    		payTx.setStockTxType(EnumConstMr.StockTxType_Sold_Paid);
    		payTx.setOutstandingAmount(owner.getSoldPendingAm());
    		payTx.setPayAmount(DataUtil.ZERO_BIG_DECIMAL);
    		
    		payTransfer.addPayTx(payTx);
    		
    		totalAmount = BDUtil.add(totalAmount, owner.getSoldPendingAm());
    	}
    	
    	payTransfer.setAmount(totalAmount);
    	
    	return payTransfer;
    }
    
    public PayTransfer transferPay(long entryByAcNo, PayTransfer payTransfer) throws BadRequestException {
		
    	if(!ConversionUtil.isValidMemberAcNo(entryByAcNo)) {
			throw new BadRequestException("Invalid Entry By Ac No!");
		}
    	MProfile entryBy = daoFactory.getMProfileDAO().findById(entryByAcNo);
		if(entryBy == null) {
			throw new BadRequestException("Invalid Entry By Ac No: " + entryByAcNo);
		}
    	
		if(!ConversionUtil.isValidMemberAcNo(payTransfer.getOwnerAcNo())) {
			throw new BadRequestException("Invalid Owner Ac No!");
		}
    	PMAc owner = daoFactory.getPMAcDAO().findById(payTransfer.getOwnerAcNo());
		if(owner == null) {
			throw new BadRequestException("Invalid Owner Ac No: " + payTransfer.getOwnerAcNo());
		}
    	
		if(!ConversionUtil.isValidMemberAcNo(payTransfer.getAuthAcNo())) {
			throw new BadRequestException("Invalid Auth Ac No!");
		}
		PMAc auth = daoFactory.getPMAcDAO().findById(payTransfer.getAuthAcNo());
		if(auth == null) {
			throw new BadRequestException("Invalid Auth Ac No: " + payTransfer.getAuthAcNo());
		}
    	
		MrVisit mrVisit = daoFactory.getMrVisitDAO().findById(payTransfer.getMrVisitId());
		if(mrVisit == null) {
			throw new BadRequestException("Invalid MRVisit Id: " + payTransfer.getMrVisitId());
		}
		String visitStatus = EnumCacheMr.getNameOfStatusValue(EnumConstMr.VisitStatus, mrVisit.getVisitStatusId());
		if(!visitStatus.equals(EnumConstMr.VisitStatus_Started)) {
			throw new BadRequestException("Invalid MRVisit Status: " + visitStatus + " for Stock Transfer!");
		}
		
		for(PayTx payTx : payTransfer.getPayTxs()) {

			StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(payTx.getStockTxType());
			if(stockTxTypeValue == null) {
				throw new BadRequestException("Invalid StockTxType: " + payTx.getStockTxType());
			}
			
			if(payTx.getPayAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= DataUtil.ZERO_INTEGER) {
				throw new BadRequestException("Invalid PayAmount: " + payTx.getPayAmount() + " for StockTxType: " + payTx.getStockTxType());
			}
		}
		
		for(PayTx payTx : payTransfer.getPayTxs()) {
			
			if(payTx.getPayAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= DataUtil.ZERO_INTEGER) {
				continue;
			}
			
			StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(payTx.getStockTxType());
			StockTxTypeFormula stockTxTypeFormula = EnumCacheMr.getStockTxTypeFormula(stockTxTypeValue.getStockTxTypeId());
			
			StockTx stockTx = new StockTx();
			
			stockTx.setStockTxTypeId(stockTxTypeValue.getStockTxTypeId());
			stockTx.setEntryByAcNo(entryByAcNo);
			stockTx.setOwnerAcNo(payTransfer.getOwnerAcNo());
			stockTx.setAuthAcNo(payTransfer.getAuthAcNo());
			stockTx.setMrVisitId(mrVisit.getMrVisitId());
			stockTx.setName(stockTxTypeValue.getStockTxType());
			stockTx.setAmount(payTx.getPayAmount());				
			stockTx.setTxTs(DateUtil.getCurrentTimeDate());
			stockTx.setEntryTs(DateUtil.getCurrentTimeDate());
			stockTx.setVerifyTs(DateUtil.getCurrentTimeDate());
			stockTx.setDescription(payTransfer.getDescription());
			stockTx.setEntryLocation(payTransfer.getEntryLocation());
			
			daoFactory.getStockTxDAO().persist(stockTx);
			
			mrProcessJobBuilder.pushNowProcessJobMr(stockTxTypeFormula.getPreP1ActionFormula(), 
					stockTxTypeFormula.getPreP2ActionFormula(), 
					stockTxTypeFormula.getPreP3ActionFormula(), stockTx);
			
			mrProcessJobBuilder.pushPostProcessJobMr(stockTxTypeFormula.getPostP1ActionFormula(), 
					stockTxTypeFormula.getPostP2ActionFormula(), 
					stockTxTypeFormula.getPostP3ActionFormula(), stockTx);
		}
		
    	return getOutstandingPayTxs(payTransfer.getOwnerAcNo());
    }
    
    public List<StockTxM> getAllTxForVisit(long visitId) throws BadRequestException {
    	
    	if(visitId <= 0) {
			throw new BadRequestException("Invalid Visit Id!");
    	}
    	MrVisit mrVisit = daoFactory.getMrVisitDAO().findById(visitId);
    	if(mrVisit == null) {
			throw new BadRequestException("Invalid Visit Id!");
    	}
    	
    	List<StockTx> stockTxs = daoFactory.getStockTxDAO().getAllTxForVisit(visitId);
    	
    	return StockTxM.buildStockTxMList(stockTxs);
    }
    
    public List<StockTxM> getStockTxForVisit(long visitId) throws BadRequestException {
    	
    	if(visitId <= 0) {
			throw new BadRequestException("Invalid Visit Id!");
    	}
    	MrVisit mrVisit = daoFactory.getMrVisitDAO().findById(visitId);
    	if(mrVisit == null) {
			throw new BadRequestException("Invalid Visit Id!");
    	}
    	
    	List<StockTx> stockTxs = daoFactory.getStockTxDAO().getStockTxForVisit(visitId);
    	
    	return StockTxM.buildStockTxMList(stockTxs);
    }
    
    public List<StockTxM> getPayTxForVisit(long visitId) throws BadRequestException {
    	
    	if(visitId <= 0) {
			throw new BadRequestException("Invalid Visit Id!");
    	}
    	MrVisit mrVisit = daoFactory.getMrVisitDAO().findById(visitId);
    	if(mrVisit == null) {
			throw new BadRequestException("Invalid Visit Id!");
    	}
    	
    	List<StockTx> stockTxs = daoFactory.getStockTxDAO().getPayTxForVisit(visitId);
    	
    	return StockTxM.buildStockTxMList(stockTxs);
    }
    
    public List<StockTxM> getTxsForOwnerInRange(long ownerAcNo, long startTime, long endTime) throws BadRequestException {
    	
		if(!ConversionUtil.isValidMemberAcNo(ownerAcNo)) {
			throw new BadRequestException("Invalid Owner Ac No!");
		}
    	PMAc owner = daoFactory.getPMAcDAO().findById(ownerAcNo);
		if(owner == null) {
			throw new BadRequestException("Invalid Owner Ac No: " + ownerAcNo);
		}
    	
    	List<StockTx> stockTxs = daoFactory.getStockTxDAO().getTxsForOwnerInRange(ownerAcNo, startTime, endTime);
    	
    	return StockTxM.buildStockTxMList(stockTxs);
    }
    
    public List<StockTxM> getStockTxsForOwnerInRange(long ownerAcNo, long startTime, long endTime) throws BadRequestException {
    	
		if(!ConversionUtil.isValidMemberAcNo(ownerAcNo)) {
			throw new BadRequestException("Invalid Owner Ac No!");
		}
    	PMAc owner = daoFactory.getPMAcDAO().findById(ownerAcNo);
		if(owner == null) {
			throw new BadRequestException("Invalid Owner Ac No: " + ownerAcNo);
		}
    	
    	List<StockTx> stockTxs = daoFactory.getStockTxDAO().getStockTxsForOwnerInRange(ownerAcNo, startTime, endTime);
    	
    	return StockTxM.buildStockTxMList(stockTxs);
    }
    
    public List<StockTxM> getPayTxsForOwnerInRange(long ownerAcNo, long startTime, long endTime) throws BadRequestException {
    	
		if(!ConversionUtil.isValidMemberAcNo(ownerAcNo)) {
			throw new BadRequestException("Invalid Owner Ac No!");
		}
    	PMAc owner = daoFactory.getPMAcDAO().findById(ownerAcNo);
		if(owner == null) {
			throw new BadRequestException("Invalid Owner Ac No: " + ownerAcNo);
		}
    	
    	List<StockTx> stockTxs = daoFactory.getStockTxDAO().getPayTxsForOwnerInRange(ownerAcNo, startTime, endTime);
    	
    	return StockTxM.buildStockTxMList(stockTxs);
    }
    
    public VisitPrintData getVisitPrintData(long ownerAcNo, long fromDate) throws BadRequestException {
		
		if(!ConversionUtil.isValidMemberAcNo(ownerAcNo)) {
			throw new BadRequestException("Invalid Entry By MR Ac No!");
		}
		
		VisitPrintData visitPrintData = new VisitPrintData();
		visitPrintData.setOwner(ownerAcNo, daoFactory);
		
    	List<MrVisit> mrVisits = daoFactory.getMrVisitDAO().getMrVisitsForOwner(ownerAcNo, fromDate, System.currentTimeMillis());

    	List<StockItem> stockItems = daoFactory.getStockItemDAO().getStockedItemsForOwner(ownerAcNo);

		List<StockTx> stockTxs = daoFactory.getStockTxDAO().getStockTxsForOwnerInRange(ownerAcNo, fromDate, System.currentTimeMillis());
		
		visitPrintData.setVisitInfo(mrVisits, daoFactory);
		
		visitPrintData.setTxs(stockTxs, stockItems, daoFactory);
				
    	return visitPrintData;
    }
    
    public VisitPrintData getVisitPrintData(long ownerAcNo, long fromDate, long toDate) throws BadRequestException {
		
		if(!ConversionUtil.isValidMemberAcNo(ownerAcNo)) {
			throw new BadRequestException("Invalid Entry By MR Ac No!");
		}
		
		VisitPrintData visitPrintData = new VisitPrintData();
		visitPrintData.setOwner(ownerAcNo, daoFactory);
		
    	List<MrVisit> mrVisits = daoFactory.getMrVisitDAO().getMrVisitsForOwner(ownerAcNo, fromDate, toDate);

    	List<StockItem> stockItems = daoFactory.getStockItemDAO().getStockedItemsForOwner(ownerAcNo);

		List<StockTx> stockTxs = daoFactory.getStockTxDAO().getStockTxsForOwnerInRange(ownerAcNo, fromDate, toDate);
		
		visitPrintData.setVisitInfo(mrVisits, daoFactory);
		
		visitPrintData.setTxs(stockTxs, stockItems, daoFactory);
				
    	return visitPrintData;
    }

}
