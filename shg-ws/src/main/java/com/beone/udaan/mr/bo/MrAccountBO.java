package com.beone.udaan.mr.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.bo.BaseBO;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.model.resp.GroupTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.MRoleValue;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.Brand;
import com.beone.udaan.mr.persistence.model.MrVisit;
import com.beone.udaan.mr.persistence.model.PHubAc;
import com.beone.udaan.mr.persistence.model.PMAc;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockType;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;
import com.beone.udaan.mr.service.model.MrAccount;
import com.beone.udaan.mr.service.model.MrStock;
import com.beone.udaan.mr.service.model.MrStock.ByBrand;
import com.beone.udaan.mr.service.model.MemberVisitAc;
import com.beone.udaan.mr.service.model.MrVisitM;

@Component("mrAccountBO")
public class MrAccountBO extends BaseBO {

    public void createPHubAccount(long pHubAcNo) throws BadRequestException {
    	
    	if(!ConversionUtil.isValidGroupAcNo(pHubAcNo)) {
			throw new BadRequestException("Invalid Hub Account No!");
    	}
    	GProfile hubGProfile = daoFactory.getGProfileDAO().findById(pHubAcNo);
    	if(hubGProfile == null) {
			throw new BadRequestException("Invalid Hub Account No : " + pHubAcNo + " !");
    	}
    	
    	GroupTypeValue groupType = EnumCache.getGroupTypeValue(hubGProfile.getGroupTypeId());
    	if(!groupType.getGroupType().equals(EnumConst.GroupType_Mega_HUB)) {
			throw new BadRequestException("Invalid Hub Group Type : " + groupType.getGroupType() + " !");
    	}
    	
    	PHubAc pHubAc = daoFactory.getPHubAcDAO().findById(pHubAcNo);
    	if(pHubAc != null) {
			throw new BadRequestException("Hub Account already exists for : " + pHubAcNo + " !");
    	}
    	
    	pHubAc = new PHubAc();
    	
    	pHubAc.setProfile(hubGProfile);
    	pHubAc.setTotalPurchasedStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setTotalStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setTotalStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setTotalStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setTotalStockMrSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setTotalStockDirectSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setTotalSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setCurrentStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setCurrentStockDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setCurrentStockUnsettledAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setCurrentStockToReturnAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setTotalPurchasedStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setTotalStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setTotalStockReturnedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setTotalStockDamagedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setTotalStockSoldNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setTotalStockMrSoldNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setTotalStockDirectSoldNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setTotalSoldDiscountNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setCurrentStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setCurrentStockDiscountNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setCurrentStockUnsettledNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setCurrentStockToReturnNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setReturnCounter(DataUtil.ZERO_INTEGER);
    	pHubAc.setCurrentOrderedStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setCurrentOrderedStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setCurrentDeliveredStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setCurrentDeliveredStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setCurrentToStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setCurrentToStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setCurrentCreatedStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setCurrentCreatedStockNo(DataUtil.ZERO_INTEGER);
    	
    	pHubAc.setMhTotalCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhTotalPaidCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhCurrentCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhSoldPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhSoldPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhTotalStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhTotalStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhTotalStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhTotalStockSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhCurrentStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhCurrentStockDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhCurrentStockUnsettledAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhCurrentStockToReturnAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMhTotalStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMhTotalStockReturnedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMhTotalStockDamagedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMhTotalStockSoldNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMhTotalStockSoldDiscountNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMhCurrentStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMhCurrentStockDiscountNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMhCurrentStockUnsettledNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMhCurrentStockToReturnNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMhReturnCounter(DataUtil.ZERO_INTEGER);
    	
    	pHubAc.setLhTotalCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhTotalPaidCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhCurrentCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhSoldPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhSoldPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhTotalStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhTotalStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhTotalStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhTotalStockSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);   	
    	pHubAc.setLhCurrentStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhCurrentStockDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhCurrentStockUnsettledAm(DataUtil.ZERO_BIG_DECIMAL);   	
    	pHubAc.setLhCurrentStockToReturnAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setLhTotalStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setLhTotalStockReturnedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setLhTotalStockDamagedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setLhTotalStockSoldNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setLhTotalStockSoldDiscountNo(DataUtil.ZERO_INTEGER);  	
    	pHubAc.setLhCurrentStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setLhCurrentStockDiscountNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setLhCurrentStockUnsettledNo(DataUtil.ZERO_INTEGER);   	
    	pHubAc.setLhCurrentStockToReturnNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setLhReturnCounter(DataUtil.ZERO_INTEGER);
    	
    	pHubAc.setSlhTotalCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhTotalPaidCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhCurrentCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhSoldPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhSoldPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhTotalStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhTotalStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhTotalStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhTotalStockSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);   	
    	pHubAc.setSlhCurrentStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhCurrentStockDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhCurrentStockUnsettledAm(DataUtil.ZERO_BIG_DECIMAL);   	
    	pHubAc.setSlhCurrentStockToReturnAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSlhTotalStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSlhTotalStockReturnedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSlhTotalStockDamagedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSlhTotalStockSoldNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSlhTotalStockSoldDiscountNo(DataUtil.ZERO_INTEGER);   	
    	pHubAc.setSlhCurrentStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSlhCurrentStockDiscountNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSlhCurrentStockUnsettledNo(DataUtil.ZERO_INTEGER);    	
    	pHubAc.setSlhCurrentStockToReturnNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSlhReturnCounter(DataUtil.ZERO_INTEGER);
    	
    	pHubAc.setSeTotalCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeTotalPaidCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeCurrentCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeSoldPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeSoldPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeTotalStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeTotalStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeTotalStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeTotalStockSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);    	
    	pHubAc.setSeCurrentStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeCurrentStockDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeCurrentStockUnsettledAm(DataUtil.ZERO_BIG_DECIMAL);    	
    	pHubAc.setSeCurrentStockToReturnAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setSeTotalStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSeTotalStockReturnedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSeTotalStockDamagedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSeTotalStockSoldNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSeTotalStockSoldDiscountNo(DataUtil.ZERO_INTEGER);   	
    	pHubAc.setSeCurrentStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSeCurrentStockDiscountNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSeCurrentStockUnsettledNo(DataUtil.ZERO_INTEGER);    	
    	pHubAc.setSeCurrentStockToReturnNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setSeReturnCounter(DataUtil.ZERO_INTEGER);
    	
    	pHubAc.setMrTotalNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrActiveNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrInactiveNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrTobeClosedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrClosedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrRegistrationPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrRegistrationPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrDepositPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrDepositPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrDepositReturnAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrCreditLimitAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrSoldPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrSoldPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrPaidInterestPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrPendingInterestPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrTotalStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrTotalStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrTotalStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrTotalStockSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);    	
    	pHubAc.setMrCurrentStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrCurrentStockDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrCurrentStockUnsettledAm(DataUtil.ZERO_BIG_DECIMAL);    	
    	pHubAc.setMrCurrentStockToReturnAm(DataUtil.ZERO_BIG_DECIMAL);
    	pHubAc.setMrTotalStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrTotalStockReturnedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrTotalStockDamagedNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrTotalStockSoldNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrTotalStockSoldDiscountNo(DataUtil.ZERO_INTEGER);   	
    	pHubAc.setMrCurrentStockNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrCurrentStockDiscountNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrCurrentStockUnsettledNo(DataUtil.ZERO_INTEGER);   	
    	pHubAc.setMrCurrentStockToReturnNo(DataUtil.ZERO_INTEGER);
    	pHubAc.setMrReturnCounter(DataUtil.ZERO_INTEGER);
    	
    	pHubAc.setPerformanceIndex(DataUtil.ZERO_FLOAT);
    	pHubAc.setReturnIndex(DataUtil.ZERO_FLOAT);
    	pHubAc.setSalesIndex(DataUtil.ZERO_FLOAT);
    	pHubAc.setSales50PerDays(DataUtil.ZERO_FLOAT);
    	pHubAc.setSales70PerDays(DataUtil.ZERO_FLOAT);
    	pHubAc.setSales80PerDays(DataUtil.ZERO_FLOAT);
    	pHubAc.setSales90PerDays(DataUtil.ZERO_FLOAT);
    	pHubAc.setSales100PerDays(DataUtil.ZERO_FLOAT);    	
    	
    	daoFactory.getPHubAcDAO().persist(pHubAc);
    }

    public MrAccount addPMAccount(MProfile mProfile) throws BadRequestException {
    	return addPMAccount(mProfile, true);
    }
    
    public MrAccount addPMAccount(MProfile mProfile, boolean returnData) throws BadRequestException {
    	
    	if(mProfile == null) {
			throw new BadRequestException("Null MProfile!");
    	}
    	
    	PMAc pMAccount = new PMAc();
    	int mrStatusId = 0;
    	int mrTypeId = 0;
    	if(EnumCache.getMRoleValue(mProfile.getMroleId()).getRole().equals(EnumConstMr.MRole_Micro_Retailer)) {

    		if(EnumCache.getEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()).equals(EnumConst.ActiveStatus_Active)) {
    			mrStatusId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.MrStatus, EnumConstMr.MrStatus_Active);
    		} else {
    			mrStatusId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.MrStatus, EnumConstMr.MrStatus_Closed);
    		}
        	mrTypeId = EnumCacheMr.getIndexOfMrType(EnumConstMr.MrType_Normal);
    	} else {
        	mrStatusId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.MrStatus, EnumConstMr.MrStatus_Non_MR);
        	mrTypeId = EnumCacheMr.getIndexOfMrType(EnumConstMr.MrType_Non_MR);
    	}
    	
    	pMAccount.setMrStatusId(mrStatusId);
    	pMAccount.setMrTypeId(mrTypeId);
    	
    	pMAccount.setMProfile(mProfile);
    	pMAccount.setRegistrationPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setRegistrationPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setDepositPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setDepositPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setDepositReturnAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setCreditLimitAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setTotalCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setTotalPaidCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setCurrentCollectedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setSoldPaidAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setSoldPendingAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setPaidInterestPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setPendingInterestPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setLastVisitOn(null);
    	pMAccount.setInterestCalculatedOn(null);
    	
    	pMAccount.setCurrentStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setCurrentStockDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setCurrentStockUnsettledAm(DataUtil.ZERO_BIG_DECIMAL);   	
    	pMAccount.setCurrentStockToReturnAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setCurrentStockNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setCurrentStockDiscountNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setCurrentStockUnsettledNo(DataUtil.ZERO_INTEGER);   	
    	pMAccount.setCurrentStockToReturnNo(DataUtil.ZERO_INTEGER);
    	
    	pMAccount.setTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setTotalStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setTotalStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setTotalStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setTotalStockSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);   	
    	pMAccount.setTotalStockMrSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setTotalStockNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setTotalStockReturnedNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setTotalStockDamagedNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setTotalStockSoldNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setTotalStockSoldDiscountNo(DataUtil.ZERO_INTEGER);    	
    	pMAccount.setTotalVisitCounter(DataUtil.ZERO_INTEGER);
    	
    	pMAccount.setThisMonthStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setThisMonthStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setThisMonthStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setThisMonthStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setThisMonthStockSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);    	
    	pMAccount.setThisMonthStockMrSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setThisMonthStockNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setThisMonthStockReturnedNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setThisMonthStockDamagedNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setThisMonthStockSoldNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setThisMonthStockSoldDiscountNo(DataUtil.ZERO_INTEGER);    	
    	pMAccount.setThisMonthVisitCounter(DataUtil.ZERO_INTEGER);
    	
    	pMAccount.setLastMonthStockAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setLastMonthStockReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setLastMonthStockDamagedAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setLastMonthStockSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setLastMonthStockSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);    	
    	pMAccount.setLastMonthStockMrSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	pMAccount.setLastMonthStockNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setLastMonthStockReturnedNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setLastMonthStockDamagedNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setLastMonthStockSoldNo(DataUtil.ZERO_INTEGER);
    	pMAccount.setLastMonthStockSoldDiscountNo(DataUtil.ZERO_INTEGER);   	
    	pMAccount.setLastMonthVisitCounter(DataUtil.ZERO_INTEGER);
    	
    	pMAccount.setPerformanceIndex(DataUtil.ZERO_FLOAT);
    	pMAccount.setReturnIndex(DataUtil.ZERO_FLOAT);
    	pMAccount.setSalesIndex(DataUtil.ZERO_FLOAT);

    	daoFactory.getPMAcDAO().persist(pMAccount);
    	
    	if(returnData) {
    		return MrAccount.getMrAccount(pMAccount);
    	} else {
    		return null;
    	}
    }

    public MrAccount addUpdatePMAccount(MrAccount mrAccount) throws BadRequestException {
    	
    	if(mrAccount == null) {
			throw new BadRequestException("Null or Invalid MrAccount!");
    	}
    	if(!ConversionUtil.isValidMemberAcNo(mrAccount.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No: " + mrAccount.getMemberAcNo() + "!");
    	}
		MProfile mProfile = daoFactory.getMProfileDAO().findById(mrAccount.getMemberAcNo());
    	if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account No: " + mrAccount.getMemberAcNo() + "!");
    	}
    	
    	int mrStatusId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.MrStatus, mrAccount.getMrStatus());
    	if(mrStatusId <= 0) {
			throw new BadRequestException("Invalid MR Status: " + mrAccount.getMrStatus() + "!");
    	}
    	
    	int mrTypeId = EnumCacheMr.getIndexOfMrType(mrAccount.getMrType());
    	if(mrTypeId <= 0) {
			throw new BadRequestException("Invalid MR Type: " + mrAccount.getMrType() + "!");
    	}
    	
    	PMAc pMAccount = daoFactory.getPMAcDAO().findById(mrAccount.getMemberAcNo());
    	boolean newAccount = false;
    	
    	if(pMAccount == null) { 			
    		pMAccount = new PMAc();
    		newAccount = true;
    		
        	pMAccount.setMProfile(mProfile);
    	}
    	
    	pMAccount.setMrStatusId(mrStatusId);
    	pMAccount.setMrTypeId(mrTypeId);
    	
    	pMAccount.setRegistrationPaidAm(mrAccount.getRegistrationPaidAm());
    	pMAccount.setRegistrationPendingAm(mrAccount.getRegistrationPendingAm());
    	pMAccount.setDepositPaidAm(mrAccount.getDepositPaidAm());
    	pMAccount.setDepositPendingAm(mrAccount.getDepositPendingAm());
    	pMAccount.setDepositReturnAm(mrAccount.getDepositReturnAm());
    	pMAccount.setCreditLimitAm(mrAccount.getCreditLimitAm());
    	pMAccount.setTotalCollectedAm(mrAccount.getTotalCollectedAm());
    	pMAccount.setTotalPaidCollectedAm(mrAccount.getTotalPaidCollectedAm());
    	pMAccount.setCurrentCollectedAm(mrAccount.getCurrentCollectedAm());
    	pMAccount.setSoldPaidAm(mrAccount.getSoldPaidAm());
    	pMAccount.setSoldPendingAm(mrAccount.getSoldPendingAm());
    	pMAccount.setPaidInterestPenaltyAm(mrAccount.getPaidInterestPenaltyAm());
    	pMAccount.setPendingInterestPenaltyAm(mrAccount.getPendingInterestPenaltyAm());
    	pMAccount.setLastVisitOn(DateUtil.convertTimeToDate(mrAccount.getLastVisitOn()));
    	pMAccount.setInterestCalculatedOn(DateUtil.convertTimeToDate(mrAccount.getInterestCalculatedOn()));
    	pMAccount.setCurrentStockAm(mrAccount.getCurrentStockAm());
    	pMAccount.setCurrentStockToReturnAm(mrAccount.getCurrentStockToReturnAm());
    	pMAccount.setCurrentStockNo(mrAccount.getCurrentStockNo());
    	pMAccount.setCurrentStockToReturnNo(mrAccount.getCurrentStockToReturnNo());

    	for(MrAccount.MrStat mrStat: mrAccount.getMrStats()) {

    		switch(mrStat.getTitle()) {

    		case EnumConstMr.MrStat_Titel_Total:
    		{
    			pMAccount.setTotalStockAm(mrStat.getStockAm());
    			pMAccount.setTotalStockReturnedAm(mrStat.getStockReturnedAm());
    			pMAccount.setTotalStockDamagedAm(mrStat.getStockDamagedAm());
    			pMAccount.setTotalStockSoldAm(mrStat.getStockSoldAm());
    			pMAccount.setTotalStockMrSoldAm(mrStat.getStockMrSoldAm());
    			pMAccount.setTotalStockNo(mrStat.getStockNo());
    			pMAccount.setTotalStockReturnedNo(mrStat.getStockReturnedNo());
    			pMAccount.setTotalStockDamagedNo(mrStat.getStockDamagedNo());
    			pMAccount.setTotalStockSoldNo(mrStat.getStockSoldNo());
    			pMAccount.setTotalVisitCounter(mrStat.getVisitCounter());
    		}

    		case EnumConstMr.MrStat_Titel_This_Month:
    		{
    			pMAccount.setThisMonthStockAm(mrStat.getStockAm());
    			pMAccount.setThisMonthStockReturnedAm(mrStat.getStockReturnedAm());
    			pMAccount.setThisMonthStockDamagedAm(mrStat.getStockDamagedAm());
    			pMAccount.setThisMonthStockSoldAm(mrStat.getStockSoldAm());
    			pMAccount.setThisMonthStockMrSoldAm(mrStat.getStockMrSoldAm());
    			pMAccount.setThisMonthStockNo(mrStat.getStockNo());
    			pMAccount.setThisMonthStockReturnedNo(mrStat.getStockReturnedNo());
    			pMAccount.setThisMonthStockDamagedNo(mrStat.getStockDamagedNo());
    			pMAccount.setThisMonthStockSoldNo(mrStat.getStockSoldNo());
    			pMAccount.setThisMonthVisitCounter(mrStat.getVisitCounter());
    		}

    		case EnumConstMr.MrStat_Titel_Last_Month:
    		{
    			pMAccount.setLastMonthStockAm(mrStat.getStockAm());
    			pMAccount.setLastMonthStockReturnedAm(mrStat.getStockReturnedAm());
    			pMAccount.setLastMonthStockDamagedAm(mrStat.getStockDamagedAm());
    			pMAccount.setLastMonthStockSoldAm(mrStat.getStockSoldAm());
    			pMAccount.setLastMonthStockMrSoldAm(mrStat.getStockMrSoldAm());
    			pMAccount.setLastMonthStockNo(mrStat.getStockNo());
    			pMAccount.setLastMonthStockReturnedNo(mrStat.getStockReturnedNo());
    			pMAccount.setLastMonthStockDamagedNo(mrStat.getStockDamagedNo());
    			pMAccount.setLastMonthStockSoldNo(mrStat.getStockSoldNo());
    			pMAccount.setLastMonthVisitCounter(mrStat.getVisitCounter());
    		}    		
    		}
    	}
    	
    	pMAccount.setPerformanceIndex(mrAccount.getPerformanceIndex());
    	pMAccount.setReturnIndex(mrAccount.getReturnIndex());
    	pMAccount.setSalesIndex(mrAccount.getSalesIndex());

    	if(newAccount) {
    		daoFactory.getPMAcDAO().persist(pMAccount);
    	} else {
        	daoFactory.getPMAcDAO().merge(pMAccount);
    	}
    	
    	return MrAccount.getMrAccount(pMAccount);
    }

    public MrAccount addToPMAccount(MrAccount mrAccount) throws BadRequestException {
    	
    	if(mrAccount == null) {
			throw new BadRequestException("Null or Invalid MrAccount!");
    	}
    	if(!ConversionUtil.isValidMemberAcNo(mrAccount.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No: " + mrAccount.getMemberAcNo() + "!");
    	}
		MProfile mProfile = daoFactory.getMProfileDAO().findById(mrAccount.getMemberAcNo());
    	if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account No: " + mrAccount.getMemberAcNo() + "!");
    	}
    	
    	int mrStatusId = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.MrStatus, mrAccount.getMrStatus());
    	if(mrStatusId <= 0) {
			throw new BadRequestException("Invalid MR Status: " + mrAccount.getMrStatus() + "!");
    	}
    	
    	int mrTypeId = EnumCacheMr.getIndexOfMrType(mrAccount.getMrType());
    	if(mrTypeId <= 0) {
			throw new BadRequestException("Invalid MR Type: " + mrAccount.getMrType() + "!");
    	}
    	
    	PMAc pMAccount = daoFactory.getPMAcDAO().findById(mrAccount.getMemberAcNo());
    	
    	if(pMAccount == null) { 			
			throw new BadRequestException("Invalid Member Account No: " + mrAccount.getMemberAcNo() + "!");
    	}
    	
    	pMAccount.setRegistrationPaidAm(BDUtil.add(pMAccount.getRegistrationPaidAm(), mrAccount.getRegistrationPaidAm()));
    	pMAccount.setRegistrationPendingAm(BDUtil.add(pMAccount.getRegistrationPendingAm(), mrAccount.getRegistrationPendingAm()));
    	pMAccount.setDepositPaidAm(BDUtil.add(pMAccount.getDepositPaidAm(), mrAccount.getDepositPaidAm()));
    	pMAccount.setDepositPendingAm(BDUtil.add(pMAccount.getDepositPendingAm(), mrAccount.getDepositPendingAm()));
    	pMAccount.setDepositReturnAm(BDUtil.add(pMAccount.getDepositReturnAm(), mrAccount.getDepositReturnAm()));
    	pMAccount.setCreditLimitAm(BDUtil.add(pMAccount.getCreditLimitAm(), mrAccount.getCreditLimitAm()));
    	pMAccount.setTotalCollectedAm(BDUtil.add(pMAccount.getTotalCollectedAm(), mrAccount.getTotalCollectedAm()));
    	pMAccount.setTotalPaidCollectedAm(BDUtil.add(pMAccount.getTotalPaidCollectedAm(), mrAccount.getTotalPaidCollectedAm()));
    	pMAccount.setCurrentCollectedAm(BDUtil.add(pMAccount.getCurrentCollectedAm(), mrAccount.getCurrentCollectedAm()));
    	pMAccount.setSoldPaidAm(BDUtil.add(pMAccount.getSoldPaidAm(), mrAccount.getSoldPaidAm()));
    	pMAccount.setSoldPendingAm(BDUtil.add(pMAccount.getSoldPendingAm(), mrAccount.getSoldPendingAm()));
    	pMAccount.setPaidInterestPenaltyAm(BDUtil.add(pMAccount.getPaidInterestPenaltyAm(), mrAccount.getPaidInterestPenaltyAm()));
    	pMAccount.setPendingInterestPenaltyAm(BDUtil.add(pMAccount.getPendingInterestPenaltyAm(), mrAccount.getPendingInterestPenaltyAm()));
    	pMAccount.setCurrentStockAm(BDUtil.add(pMAccount.getPendingInterestPenaltyAm(), mrAccount.getCurrentStockAm()));
    	pMAccount.setCurrentStockToReturnAm(BDUtil.add(pMAccount.getCurrentStockToReturnAm(), mrAccount.getCurrentStockToReturnAm()));
    	pMAccount.setCurrentStockNo(pMAccount.getCurrentStockNo() + mrAccount.getCurrentStockNo());
    	pMAccount.setCurrentStockToReturnNo(pMAccount.getCurrentStockToReturnNo() + mrAccount.getCurrentStockToReturnNo());

    	for(MrAccount.MrStat mrStat: mrAccount.getMrStats()) {

    		switch(mrStat.getTitle()) {

    		case EnumConstMr.MrStat_Titel_Total:
    		{
    			pMAccount.setTotalStockAm(BDUtil.add(pMAccount.getTotalStockAm(), mrStat.getStockAm()));
    			pMAccount.setTotalStockReturnedAm(BDUtil.add(pMAccount.getTotalStockReturnedAm(), mrStat.getStockReturnedAm()));
    			pMAccount.setTotalStockDamagedAm(BDUtil.add(pMAccount.getTotalStockDamagedAm(), mrStat.getStockDamagedAm()));
    			pMAccount.setTotalStockSoldAm(BDUtil.add(pMAccount.getTotalStockSoldAm(), mrStat.getStockSoldAm()));
    			pMAccount.setTotalStockMrSoldAm(BDUtil.add(pMAccount.getTotalStockMrSoldAm(), mrStat.getStockMrSoldAm()));
    			pMAccount.setTotalStockNo(pMAccount.getTotalStockNo() + mrStat.getStockNo());
    			pMAccount.setTotalStockReturnedNo(pMAccount.getTotalStockReturnedNo() + mrStat.getStockReturnedNo());
    			pMAccount.setTotalStockDamagedNo(pMAccount.getTotalStockDamagedNo() + mrStat.getStockDamagedNo());
    			pMAccount.setTotalStockSoldNo(pMAccount.getTotalStockSoldNo() + mrStat.getStockSoldNo());
    			pMAccount.setTotalVisitCounter(pMAccount.getTotalVisitCounter() + mrStat.getVisitCounter());
    		}

    		case EnumConstMr.MrStat_Titel_This_Month:
    		{
    			pMAccount.setThisMonthStockAm(BDUtil.add(pMAccount.getThisMonthStockAm(), mrStat.getStockAm()));
    			pMAccount.setThisMonthStockReturnedAm(BDUtil.add(pMAccount.getThisMonthStockReturnedAm(), mrStat.getStockReturnedAm()));
    			pMAccount.setThisMonthStockDamagedAm(BDUtil.add(pMAccount.getThisMonthStockDamagedAm(), mrStat.getStockDamagedAm()));
    			pMAccount.setThisMonthStockSoldAm(BDUtil.add(pMAccount.getThisMonthStockSoldAm(), mrStat.getStockSoldAm()));
    			pMAccount.setThisMonthStockMrSoldAm(BDUtil.add(pMAccount.getThisMonthStockMrSoldAm(), mrStat.getStockMrSoldAm()));
    			pMAccount.setThisMonthStockNo(pMAccount.getThisMonthStockNo() + mrStat.getStockNo());
    			pMAccount.setThisMonthStockReturnedNo(pMAccount.getThisMonthStockReturnedNo() + mrStat.getStockReturnedNo());
    			pMAccount.setThisMonthStockDamagedNo(pMAccount.getThisMonthStockDamagedNo() + mrStat.getStockDamagedNo());
    			pMAccount.setThisMonthStockSoldNo(pMAccount.getThisMonthStockSoldNo() + mrStat.getStockSoldNo());
    			pMAccount.setThisMonthVisitCounter(pMAccount.getThisMonthVisitCounter() + mrStat.getVisitCounter());
    		}

    		case EnumConstMr.MrStat_Titel_Last_Month:
    		{
    			pMAccount.setLastMonthStockAm(BDUtil.add(pMAccount.getLastMonthStockAm(), mrStat.getStockAm()));
    			pMAccount.setLastMonthStockReturnedAm(BDUtil.add(pMAccount.getLastMonthStockReturnedAm(), mrStat.getStockReturnedAm()));
    			pMAccount.setLastMonthStockDamagedAm(BDUtil.add(pMAccount.getLastMonthStockDamagedAm(), mrStat.getStockDamagedAm()));
    			pMAccount.setLastMonthStockSoldAm(BDUtil.add(pMAccount.getLastMonthStockSoldAm(), mrStat.getStockSoldAm()));
    			pMAccount.setLastMonthStockMrSoldAm(BDUtil.add(pMAccount.getLastMonthStockMrSoldAm(), mrStat.getStockMrSoldAm()));
    			pMAccount.setLastMonthStockNo(pMAccount.getLastMonthStockNo() + mrStat.getStockNo());
    			pMAccount.setLastMonthStockReturnedNo(pMAccount.getLastMonthStockReturnedNo() + mrStat.getStockReturnedNo());
    			pMAccount.setLastMonthStockDamagedNo(pMAccount.getLastMonthStockDamagedNo() + mrStat.getStockDamagedNo());
    			pMAccount.setLastMonthStockSoldNo(pMAccount.getLastMonthStockSoldNo() + mrStat.getStockSoldNo());
    			pMAccount.setLastMonthVisitCounter(pMAccount.getLastMonthVisitCounter() + mrStat.getVisitCounter());
    		}    		
    		}
    	}

    	daoFactory.getPMAcDAO().merge(pMAccount);
    	
    	return MrAccount.getMrAccount(pMAccount);
    }
    
    public MrAccount getMrAccount(long mrAccountNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidMemberAcNo(mrAccountNo)) {
			throw new BadRequestException("Invalid Account No!");
		}
		
		PMAc pMAc = daoFactory.getPMAcDAO().findById(mrAccountNo);
		if(pMAc == null) {
			throw new BadRequestException("Invalid Account No!");
		}
		
    	return MrAccount.getMrAccount(pMAc);
    }
    
    public MrStock getMrStock(long mrAccountNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidMemberAcNo(mrAccountNo)) {
			throw new BadRequestException("Invalid Entry By MR Ac No!");
		}
		String mrName = daoFactory.getMemberContactDAO().getNameOfMember(EnumConst.Lang_English, mrAccountNo);
		if(mrName == null) {
			throw new BadRequestException("Invalid Entry By MR Ac No!");
		}
		
		MrStock mrStock = new MrStock();
		mrStock.setOwnerAcNo(mrAccountNo);
		mrStock.setOwnerAcNoName(mrName);
		
		
		List<StockItem> stockItems = daoFactory.getStockItemDAO().getStockedItemsForOwner(mrAccountNo);
		
		convertToMrStock(stockItems, mrStock);
		
    	return mrStock;
    }

    protected MrStock convertToMrStock(List<StockItem> stockItems, MrStock mrStock) {
    	
    	if(stockItems != null && !stockItems.isEmpty()) {
    		
    		// Aggregate All StockItems by StockTypes
    		Map<Long, List<MrStock.Item>> byTypes = new HashMap<Long, List<MrStock.Item>>();

    		for(StockItem stockItem: stockItems) {

    			if(!byTypes.containsKey(stockItem.getStockType().getStockTypeId())) {
    				byTypes.put(stockItem.getStockType().getStockTypeId(), new ArrayList<MrStock.Item>());
    			}

    			byTypes.get(stockItem.getStockType().getStockTypeId()).add(MrStock.buildItem(stockItem));
    		}

    		// Aggregate All StockTypes by Brands
    		Map<Long, List<MrStock.ByType>> byBrands = new HashMap<Long, List<MrStock.ByType>>();
    		
    		for(Long stockTypeId: byTypes.keySet()) {
    			StockType stockType = daoFactory.getStockTypeDAO().findById(stockTypeId);
    			
    			if(!byBrands.containsKey(stockType.getBrand().getBrandId())) {
    				byBrands.put(stockType.getBrand().getBrandId(), new ArrayList<MrStock.ByType>());
    			}
    			
    			byBrands.get(stockType.getBrand().getBrandId()).add(MrStock.buildType(stockType, byTypes.get(stockTypeId)));
    		}

    		// Build Brands List
    		List<ByBrand> brands = new ArrayList<ByBrand>();
    		for(Long brandId: byBrands.keySet()) {
    			Brand brand = daoFactory.getBrandDAO().findById(brandId);
    			
    			brands.add(MrStock.buildBrand(brand, byBrands.get(brandId)));
    		}
    		
    		// Build final MrStock
    		mrStock = MrStock.buildMrStock(mrStock, brands);
    	}
    	
    	return mrStock;
    }
    
    public MemberVisitAc getAllMrVisitsForMember(long memberAcNo, long startTime, long endTime) throws BadRequestException {
    	
    	if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Owner Account No!");
    	}
    	PMAc memberAccount = daoFactory.getPMAcDAO().findById(memberAcNo);
    	if(memberAccount == null) {
			throw new BadRequestException("Invalid Member Account No: " + memberAcNo + "!");
    	}
   	
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
    	  	
    	List<MrVisit> mrVisits = daoFactory.getMrVisitDAO().getMrVisitsForOwner(memberAcNo, startTime, endTime);
    	List<MrVisit> mrVisits1 = daoFactory.getMrVisitDAO().getMrVisitsForAuth(memberAcNo, startTime, endTime);
    	
    	mrVisits.addAll(mrVisits1);
    	
    	List<MrVisitM> mrVisitMs = MrVisitM.buildMRVisits(mrVisits, daoFactory);
    	
    	MemberVisitAc memberVisitAc = new MemberVisitAc(memberAcNo, startTime, endTime, mrVisitMs);
    	
    	return memberVisitAc;
    }
    
    public MemberVisitAc getMrVisitsForOwner(long ownerAcNo, long startTime, long endTime) throws BadRequestException {
    	
    	if(!ConversionUtil.isValidMemberAcNo(ownerAcNo)) {
			throw new BadRequestException("Invalid Owner Account No!");
    	}
    	PMAc ownerAccount = daoFactory.getPMAcDAO().findById(ownerAcNo);
    	if(ownerAccount == null) {
			throw new BadRequestException("Invalid Owner Account No: " + ownerAcNo + "!");
    	}
   	
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
    	  	
    	List<MrVisit> mrVisits = daoFactory.getMrVisitDAO().getMrVisitsForOwner(ownerAcNo, startTime, endTime);
    	
    	List<MrVisitM> mrVisitMs = MrVisitM.buildMRVisits(mrVisits, daoFactory);
    	
    	MemberVisitAc memberVisitAc = new MemberVisitAc(ownerAcNo, startTime, endTime, mrVisitMs);
    	
    	return memberVisitAc;
    }
    
    public MemberVisitAc getMrVisitsForAuth(long authAcNo, long startTime, long endTime) throws BadRequestException {
    	
    	if(!ConversionUtil.isValidMemberAcNo(authAcNo)) {
			throw new BadRequestException("Invalid Auth Account No!");
    	}
    	PMAc authAccount = daoFactory.getPMAcDAO().findById(authAcNo);
    	if(authAccount == null) {
			throw new BadRequestException("Invalid Auth Account No: " + authAcNo + "!");
    	}
   	
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
    	  	
    	List<MrVisit> mrVisits = daoFactory.getMrVisitDAO().getMrVisitsForAuth(authAcNo, startTime, endTime);
    	
    	List<MrVisitM> mrVisitMs =MrVisitM.buildMRVisits(mrVisits, daoFactory);
    	
//    	MrVisitAc memberVisitAc = new MrVisitAc(startTime, endTime, mrVisitMs);
    	MemberVisitAc memberVisitAc = null;

    	return memberVisitAc;
    }
    
    public MemberVisitAc getAllMrVisits(long startTime, long endTime) throws BadRequestException {
    	
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
    	  	
    	List<MrVisit> mrVisits = daoFactory.getMrVisitDAO().getAllMrVisits(startTime, endTime);
    	
    	List<MrVisitM> mrVisitMs =MrVisitM.buildMRVisits(mrVisits, daoFactory);
    	
//    	MrVisitAc memberVisitAc = new MrVisitAc(startTime, endTime, mrVisitMs);
    	MemberVisitAc memberVisitAc = null;
   	
    	return memberVisitAc;
    }
    
    public MemberVisitAc getMrVisitsForOwnerForRole(String role, long startTime, long endTime) throws BadRequestException {
    	
    	MRoleValue roleValue = EnumCache.getMRoleValue(role);
    	if(roleValue == null) {
			throw new BadRequestException("Invalid Owner Role : " + role + "!");
    	}
    	if(!roleValue.getSystem().equals(EnumConstMr.MRole_System_Micro_Retailer)) {
			throw new BadRequestException("Owner role: " + role + ", don't belong to Micro Retailer System!");
    	}
   	
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
    	  	
    	List<MrVisit> mrVisits = daoFactory.getMrVisitDAO().getMrVisitsForOwnerForRole(roleValue.getRoleId(), startTime, endTime);
    	
    	List<MrVisitM> mrVisitMs =MrVisitM.buildMRVisits(mrVisits, daoFactory);
    	
//    	MrVisitAc memberVisitAc = new MrVisitAc(startTime, endTime, mrVisitMs);
    	MemberVisitAc memberVisitAc = null;
    	
    	return memberVisitAc;
    }
    
    public MemberVisitAc getMrVisitsForAuthForRole(String role, long startTime, long endTime) throws BadRequestException {
    	
    	MRoleValue roleValue = EnumCache.getMRoleValue(role);
    	if(roleValue == null) {
			throw new BadRequestException("Invalid Auth Role : " + role + "!");
    	}
    	if(!roleValue.getSystem().equals(EnumConstMr.MRole_System_Micro_Retailer)) {
			throw new BadRequestException("Auth role: " + role + ", don't belong to Micro Retailer System!");
    	}
   	
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
    	  	
    	List<MrVisit> mrVisits = daoFactory.getMrVisitDAO().getMrVisitsForAuthForRole(roleValue.getRoleId(), startTime, endTime);
    	
    	List<MrVisitM> mrVisitMs =MrVisitM.buildMRVisits(mrVisits, daoFactory);
    	
//    	MrVisitAc memberVisitAc = new MrVisitAc(startTime, endTime, mrVisitMs);
    	MemberVisitAc memberVisitAc = null;
    	
    	return memberVisitAc;
    }    
}
