package com.beone.shg.net.persistence.spo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.model.MonthlyMLoanAc;
import com.beone.shg.net.persistence.model.MonthlyMLoanAcId;
import com.beone.shg.net.persistence.model.MonthlyMAc;
import com.beone.shg.net.persistence.model.MonthlyMAcId;
import com.beone.shg.net.persistence.model.MonthlyMSavingAc;
import com.beone.shg.net.persistence.model.MonthlyMSavingAcId;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;

public class MonthlyMAcSavePO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(MonthlyMAcSavePO.class);
	
	public MonthlyMAcSavePO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);
	}

	@Override
	final public void executeMemberPO(MemberAcInfo memberAcInfo) {

		try {					
			// Saving Member Account 
			saveMonthlyMAc(memberAcInfo);

			// Saving Member Saving Account 
			saveMonthlyMSavingAcs(memberAcInfo);
			
			// Saving Member Loan Account 
			saveMonthlyMLoanAcs(memberAcInfo);
			
		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getAc().getMAcNo());
		}
	}
	
	protected void saveMonthlyMAc(MemberAcInfo memberAcInfo) {
		boolean newObject = false;
		MonthlyMAc monthlyMAc = null;
		monthlyMAc = groupAcInfo.getDaoFactory().getMonthlyMAcDAO().findById(memberAcInfo.getAc().getMAcNo(), groupAcInfo.getMonth()); 

		if(monthlyMAc == null) {
			monthlyMAc = new MonthlyMAc();
			monthlyMAc.setId(new MonthlyMAcId(memberAcInfo.getAc().getMAcNo(), groupAcInfo.getMonth()));
			monthlyMAc.setMAcNo(memberAcInfo.getAc().getMAcNo());
			monthlyMAc.setMonth(groupAcInfo.getMonth());
			newObject = true;
		}

		monthlyMAc.setCreditRating(memberAcInfo.getAc().getCreditRating());
		monthlyMAc.setMeetingAttendance(memberAcInfo.getAc().getMeetingAttendance());
		monthlyMAc.setPlannedMonthlySavingAm(memberAcInfo.getAc().getPlannedMonthlySavingAm());
		monthlyMAc.setSavedAm(memberAcInfo.getAc().getSavedAm());
		monthlyMAc.setOutstandingSavedAm(memberAcInfo.getAc().getOutstandingSavedAm());
		monthlyMAc.setProvIntEnAm(memberAcInfo.getAc().getProvIntEnAm());
		monthlyMAc.setPrevMonthIntEnAm(memberAcInfo.getAc().getPrevMonthIntEnAm());
		monthlyMAc.setReturnedSavedAm(memberAcInfo.getAc().getReturnedSavedAm());
		monthlyMAc.setReturnedIntEnAm(memberAcInfo.getAc().getReturnedIntEnAm());
		monthlyMAc.setDividedProfitDeclaredAm(memberAcInfo.getAc().getDividedProfitDeclaredAm());
		monthlyMAc.setDividedProfitPaidAm(memberAcInfo.getAc().getDividedProfitPaidAm());
		monthlyMAc.setNoOfLoans(memberAcInfo.getAc().getNoOfLoans());
		monthlyMAc.setNoOfActiveLoans(memberAcInfo.getAc().getNoOfActiveLoans());
		monthlyMAc.setLoanAm(memberAcInfo.getAc().getLoanAm());
		monthlyMAc.setRecLoanAm(memberAcInfo.getAc().getRecLoanAm());
		monthlyMAc.setRecIntOnLoanAm(memberAcInfo.getAc().getRecIntOnLoanAm());
		monthlyMAc.setProjIntOnLoanAm(memberAcInfo.getAc().getProjIntOnLoanAm());
		monthlyMAc.setBadDeptClosedAm(memberAcInfo.getAc().getBadDeptClosedAm());
		monthlyMAc.setRecPenaltyAm(memberAcInfo.getAc().getRecPenaltyAm());
		monthlyMAc.setPendingPenaltyAm(memberAcInfo.getAc().getPendingPenaltyAm());
		monthlyMAc.setMeetingAttended(memberAcInfo.getAc().getMeetingAttended());
		monthlyMAc.setMeetingMissed(memberAcInfo.getAc().getMeetingMissed());
		monthlyMAc.setLastUpdatedTs(memberAcInfo.getAc().getLastUpdatedTs());
		
		if(newObject) {
			groupAcInfo.getDaoFactory().getMonthlyMAcDAO().persist(monthlyMAc);
		} else {
			groupAcInfo.getDaoFactory().getMonthlyMAcDAO().merge(monthlyMAc);
		}
	}
	
	protected void saveMonthlyMSavingAcs(MemberAcInfo memberAcInfo) {
		List<MSavingAc> mSavingAcs = groupAcInfo.getDaoFactory().getMSavingAcDAO().getAllAcForMember(memberAcInfo.getMprofile().getMemberAcNo());
		
		for(MSavingAc mSavingAc: mSavingAcs) {
			saveMonthlyMSavingAc(mSavingAc);
		}
	}
	
	protected void saveMonthlyMSavingAc(MSavingAc mSavingAc) {
		boolean newObject = false;
		MonthlyMSavingAc monthlyMSavingAc = groupAcInfo.getDaoFactory().getMonthlyMSavingAcDAO().findById(mSavingAc.getMSavingAcNo(), groupAcInfo.getMonth());

		if(monthlyMSavingAc == null) {
			monthlyMSavingAc = new MonthlyMSavingAc();
			monthlyMSavingAc.setId(new MonthlyMSavingAcId(mSavingAc.getMSavingAcNo(), groupAcInfo.getMonth()));
			monthlyMSavingAc.setMSavingAcNo(mSavingAc.getMSavingAcNo());
			monthlyMSavingAc.setMonth(groupAcInfo.getMonth());
			newObject = true;
		}

		monthlyMSavingAc.setMAcNo(mSavingAc.getMAc().getMAcNo());
		monthlyMSavingAc.setAccountStatusId(mSavingAc.getAccountStatusId());
		monthlyMSavingAc.setRecoveryPeriodId(mSavingAc.getRecoveryPeriodId());
		monthlyMSavingAc.setSavedAm(mSavingAc.getSavedAm());
		monthlyMSavingAc.setCumulativeSavedAm(mSavingAc.getCumulativeSavedAm());
		monthlyMSavingAc.setSavingInstallmentAm(mSavingAc.getSavingInstallmentAm());
		monthlyMSavingAc.setTotalIntEnAm(mSavingAc.getTotalIntEnAm());
		monthlyMSavingAc.setCurrentFyIntEnAm(mSavingAc.getCurrentFyIntEnAm());
		monthlyMSavingAc.setPrevMonthIntAm(mSavingAc.getPrevMonthIntAm());
		monthlyMSavingAc.setReturnedSavedAm(mSavingAc.getReturnedSavedAm());
		monthlyMSavingAc.setReturnedIntEnAm(mSavingAc.getReturnedIntEnAm());
		monthlyMSavingAc.setExpNoOfInst(mSavingAc.getExpNoOfInst());
		monthlyMSavingAc.setNoOfInstPaid(mSavingAc.getNoOfInstPaid());
		monthlyMSavingAc.setNoOfInsallLate(mSavingAc.getNoOfInsallLate());
		monthlyMSavingAc.setNoOfInsallMissed(mSavingAc.getNoOfInsallMissed());
		
		if(newObject) {
			groupAcInfo.getDaoFactory().getMonthlyMSavingAcDAO().persist(monthlyMSavingAc);
		} else {
			groupAcInfo.getDaoFactory().getMonthlyMSavingAcDAO().merge(monthlyMSavingAc);
		}
	}
	
	protected void saveMonthlyMLoanAcs(MemberAcInfo memberAcInfo) {
		List<MLoanAc> mLoanAcs = groupAcInfo.getDaoFactory().getMLoanAcDAO().getAllAcForMember(memberAcInfo.getMprofile().getMemberAcNo());
		
		for(MLoanAc mLoanAc: mLoanAcs) {
			saveMonthlyMLoanAc(mLoanAc);
		}
	}
	
	protected void saveMonthlyMLoanAc(MLoanAc mLoanAc) {
		boolean newObject = false;
		MonthlyMLoanAc monthlyMLoanAc = groupAcInfo.getDaoFactory().getMonthlyMLoanAcDAO().findById(mLoanAc.getMLoanAcNo(), groupAcInfo.getMonth());

		if(monthlyMLoanAc == null) {
			monthlyMLoanAc = new MonthlyMLoanAc();
			monthlyMLoanAc.setId(new MonthlyMLoanAcId(mLoanAc.getMLoanAcNo(), groupAcInfo.getMonth()));
			monthlyMLoanAc.setMLoanAcNo(mLoanAc.getMLoanAcNo());
			monthlyMLoanAc.setMonth(groupAcInfo.getMonth());
			newObject = true;
		}

		monthlyMLoanAc.setMAcNo(mLoanAc.getMAc().getMAcNo());
		monthlyMLoanAc.setFundTypeId(mLoanAc.getFundTypeId());
		monthlyMLoanAc.setLoanCalculationId(mLoanAc.getLoanCalculationId());
		monthlyMLoanAc.setAccountStatusId(mLoanAc.getAccountStatusId());
		monthlyMLoanAc.setRecoveryPeriodId(mLoanAc.getRecoveryPeriodId());
		monthlyMLoanAc.setLoanSourceId(mLoanAc.getLoanSourceId());
		monthlyMLoanAc.setPrincipleAm(mLoanAc.getPrincipleAm());
		monthlyMLoanAc.setPendingPrincipleAm(mLoanAc.getPendingPrincipleAm());
		monthlyMLoanAc.setRecInterestAm(mLoanAc.getRecInterestAm());
		monthlyMLoanAc.setProjInterestAm(mLoanAc.getProjInterestAm());
		monthlyMLoanAc.setInstallmentAm(mLoanAc.getInstallmentAm());
		monthlyMLoanAc.setPreEmiInterestAm(mLoanAc.getPreEmiInterestAm());
		monthlyMLoanAc.setPendingInterestDueAm(mLoanAc.getPendingInterestDueAm());
		monthlyMLoanAc.setRateOfInterest(mLoanAc.getRateOfInterest());
		monthlyMLoanAc.setStartupNoOfInst(mLoanAc.getStartupNoOfInst());
		monthlyMLoanAc.setExpNoOfInst(mLoanAc.getExpNoOfInst());
		monthlyMLoanAc.setNoOfInstPaid(mLoanAc.getNoOfInstPaid());
		monthlyMLoanAc.setNoOfInsallLate(mLoanAc.getNoOfInsallLate());
		monthlyMLoanAc.setNoOfInsallMissed(mLoanAc.getNoOfInsallMissed());
		
		if(newObject) {
			groupAcInfo.getDaoFactory().getMonthlyMLoanAcDAO().persist(monthlyMLoanAc);
		} else {
			groupAcInfo.getDaoFactory().getMonthlyMLoanAcDAO().merge(monthlyMLoanAc);
		}
	}
}
