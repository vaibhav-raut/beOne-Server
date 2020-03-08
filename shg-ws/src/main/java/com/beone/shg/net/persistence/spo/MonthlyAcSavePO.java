package com.beone.shg.net.persistence.spo;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GAcByTxtype;
import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.model.GInvtAc;
import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.model.MonthlyGAc;
import com.beone.shg.net.persistence.model.MonthlyGAcByTxtype;
import com.beone.shg.net.persistence.model.MonthlyGAcByTxtypeId;
import com.beone.shg.net.persistence.model.MonthlyGAcId;
import com.beone.shg.net.persistence.model.MonthlyGBankAccount;
import com.beone.shg.net.persistence.model.MonthlyGBankAccountId;
import com.beone.shg.net.persistence.model.MonthlyGInvtAc;
import com.beone.shg.net.persistence.model.MonthlyGInvtAcId;
import com.beone.shg.net.persistence.model.MonthlyGLoanAc;
import com.beone.shg.net.persistence.model.MonthlyGLoanAcId;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

public class MonthlyAcSavePO extends GenericPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(MonthlyAcSavePO.class);
	
	public MonthlyAcSavePO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);
	}

	@Override
	final public void execute() {

		try {					
			// Save Current Net Profit 
			saveNetProfit();
			
			// Save Current Net Profit Projected
			saveNetProfitProjected();
			
			// Saving Group Account Info
			saveMonthlyGAc();
			
			// Saving Group Loan Accounts
			saveMonthlyGLoanAcs();
			
			// Saving Group Investment Accounts
			saveMonthlyGInvtAcs();
			
			// Saving Group Bank Accounts
			saveMonthlyGBankAccounts();
			
			// Saving Group Transaction Wise Info
			saveMonthlyGAcByTxtypes();
			
		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for GroupAcNo:" + groupAcInfo.getGroupAcNo());
		}
	}

	protected void saveNetProfit() throws BadRequestException {

		BigDecimal netProfit = groupAcInfo.getReportBO().computeNetProfit((int)groupAcInfo.getAc().getGAcNo());
		
		GAc gAccount = groupAcInfo.getDaoFactory().getGAcDAO().findById(groupAcInfo.getAc().getGAcNo()); 
		gAccount.setNetProfitAm(netProfit);
		groupAcInfo.getAc().setNetProfitAm(netProfit);
		groupAcInfo.getDaoFactory().getGAcDAO().merge(gAccount);
	}

	protected void saveNetProfitProjected() throws BadRequestException {

		BigDecimal netProfitProj = groupAcInfo.getReportBO().computeNetProfitProjected((int)groupAcInfo.getAc().getGAcNo());
		
		GAc gAccount = groupAcInfo.getDaoFactory().getGAcDAO().findById(groupAcInfo.getAc().getGAcNo()); 
		gAccount.setNetProfitProjAm(netProfitProj);
		groupAcInfo.getAc().setNetProfitProjAm(netProfitProj);
		groupAcInfo.getDaoFactory().getGAcDAO().merge(gAccount);
	}
	
	protected void saveMonthlyGAc() {
		boolean newObject = false;
		MonthlyGAc monthlyGAc = null;
		monthlyGAc = groupAcInfo.getDaoFactory().getMonthlyGAcDAO().findById((long)groupAcInfo.getAc().getGAcNo(), groupAcInfo.getMonth()); 

		if(monthlyGAc == null) {
			monthlyGAc = new MonthlyGAc();
			monthlyGAc.setId(new MonthlyGAcId(groupAcInfo.getAc().getGAcNo(), groupAcInfo.getMonth()));
			monthlyGAc.setGAcNo(groupAcInfo.getAc().getGAcNo());
			monthlyGAc.setMonth(groupAcInfo.getMonth());
			newObject = true;
		}

		monthlyGAc.setCreditRating(groupAcInfo.getAc().getCreditRating());
		monthlyGAc.setApprovalRating(groupAcInfo.getAc().getApprovalRating());
		monthlyGAc.setMeetingAttendance(groupAcInfo.getAc().getMeetingAttendance());
		monthlyGAc.setApprovalRating(groupAcInfo.getAc().getApprovalRating());
		monthlyGAc.setCMPlannedMonthlySaving(groupAcInfo.getAc().getCMPlannedMonthlySaving());
		monthlyGAc.setCMSavedAm(groupAcInfo.getAc().getCMSavedAm());
		monthlyGAc.setCMOutstandingSavedAm(groupAcInfo.getAc().getCMOutstandingSavedAm());
		monthlyGAc.setCMProvIntEnAm(groupAcInfo.getAc().getCMProvIntEnAm());
		monthlyGAc.setCMReturnedSavedAm(groupAcInfo.getAc().getCMReturnedSavedAm());
		monthlyGAc.setCMReturnedIntEnAm(groupAcInfo.getAc().getCMReturnedIntEnAm());
		monthlyGAc.setCMProfitShareDeclaredAm(groupAcInfo.getAc().getCMProfitShareDeclaredAm());
		monthlyGAc.setCMProfitSharePaidAm(groupAcInfo.getAc().getCMProfitSharePaidAm());
		monthlyGAc.setCMNoOfLoans(groupAcInfo.getAc().getCMNoOfLoans());
		monthlyGAc.setCMNoOfActiveLoans(groupAcInfo.getAc().getCMNoOfActiveLoans());
		monthlyGAc.setCMLoanAm(groupAcInfo.getAc().getCMLoanAm());
		monthlyGAc.setCMPendingLoanAm(groupAcInfo.getAc().getCMLoanAm().subtract(groupAcInfo.getAc().getCMRecLoanAm()));
		monthlyGAc.setCMRecLoanAm(groupAcInfo.getAc().getCMRecLoanAm());
		monthlyGAc.setCMRecIntOnLoanAm(groupAcInfo.getAc().getCMRecIntOnLoanAm());
		monthlyGAc.setCMProjIntOnLoanAm(groupAcInfo.getAc().getCMProjIntOnLoanAm());
		monthlyGAc.setCMSubStdDeptAm(groupAcInfo.getAc().getCMSubStdDeptAm());
		monthlyGAc.setCMNoOfSubStdDept(groupAcInfo.getAc().getCMNoOfSubStdDept());
		monthlyGAc.setCMBadDeptExpAm(groupAcInfo.getAc().getCMBadDeptExpAm());
		monthlyGAc.setCMNoOfBadDeptExp(groupAcInfo.getAc().getCMNoOfBadDeptExp());
		monthlyGAc.setCMBadDeptClosedAm(groupAcInfo.getAc().getCMBadDeptClosedAm());
		monthlyGAc.setCMNoOfBadDeptClosed(groupAcInfo.getAc().getCMNoOfBadDeptClosed());
		
		monthlyGAc.setAMPlannedMonthlySaving(groupAcInfo.getAc().getAMPlannedMonthlySaving());
		monthlyGAc.setAMSavedAm(groupAcInfo.getAc().getAMSavedAm());
		monthlyGAc.setAMOutstandingSavedAm(groupAcInfo.getAc().getAMOutstandingSavedAm());
		monthlyGAc.setAMProvIntEnAm(groupAcInfo.getAc().getAMProvIntEnAm());
		monthlyGAc.setAMReturnedSavedAm(groupAcInfo.getAc().getAMReturnedSavedAm());
		monthlyGAc.setAMReturnedIntEnAm(groupAcInfo.getAc().getAMReturnedIntEnAm());
		monthlyGAc.setAMDividedDeclaredAm(groupAcInfo.getAc().getAMDividedDeclaredAm());
		monthlyGAc.setAMDividedPaidAm(groupAcInfo.getAc().getAMDividedPaidAm());
		monthlyGAc.setAMNoOfLoans(groupAcInfo.getAc().getAMNoOfLoans());
		monthlyGAc.setAMNoOfActiveLoans(groupAcInfo.getAc().getAMNoOfActiveLoans());
		monthlyGAc.setAMLoanAm(groupAcInfo.getAc().getAMLoanAm());
		monthlyGAc.setAMPendingLoanAm(groupAcInfo.getAc().getAMLoanAm().subtract(groupAcInfo.getAc().getAMRecLoanAm()));
		monthlyGAc.setAMRecLoanAm(groupAcInfo.getAc().getAMRecLoanAm());
		monthlyGAc.setAMRecIntOnLoanAm(groupAcInfo.getAc().getAMRecIntOnLoanAm());
		monthlyGAc.setAMProjIntOnLoanAm(groupAcInfo.getAc().getAMProjIntOnLoanAm());
		monthlyGAc.setAMSubStdDeptAm(groupAcInfo.getAc().getAMSubStdDeptAm());
		monthlyGAc.setAMNoOfSubStdDept(groupAcInfo.getAc().getAMNoOfSubStdDept());
		monthlyGAc.setAMBadDeptExpAm(groupAcInfo.getAc().getAMBadDeptExpAm());
		monthlyGAc.setAMNoOfBadDeptExp(groupAcInfo.getAc().getAMNoOfBadDeptExp());
		monthlyGAc.setAMBadDeptClosedAm(groupAcInfo.getAc().getCMBadDeptClosedAm());
		monthlyGAc.setAMNoOfBadDeptClosed(groupAcInfo.getAc().getCMNoOfBadDeptClosed());
		
		monthlyGAc.setPLoanAm(groupAcInfo.getAc().getPLoanAm());
		monthlyGAc.setPPendingLoanAm(groupAcInfo.getAc().getPLoanAm().subtract(groupAcInfo.getAc().getPRecLoanAm()));
		monthlyGAc.setPRecLoanAm(groupAcInfo.getAc().getPRecLoanAm());
		monthlyGAc.setPRecIntOnLoanAm(groupAcInfo.getAc().getPRecIntOnLoanAm());
		monthlyGAc.setPProjIntOnLoanAm(groupAcInfo.getAc().getPProjIntOnLoanAm());
		monthlyGAc.setNoOfProject(groupAcInfo.getAc().getNoOfProject());
		monthlyGAc.setNoOfActiveProject(groupAcInfo.getAc().getNoOfActiveProject());
		
		monthlyGAc.setFixDepositInvAm(groupAcInfo.getAc().getFixDepositInvAm());
		monthlyGAc.setPendingFixDepositAm(groupAcInfo.getAc().getFixDepositInvAm().subtract(groupAcInfo.getAc().getRecFixDepositAm()));
		monthlyGAc.setRecFixDepositAm(groupAcInfo.getAc().getRecFixDepositAm());
		monthlyGAc.setRecIntOnFixDepositAm(groupAcInfo.getAc().getRecIntOnFixDepositAm());
		monthlyGAc.setProjIntOnFixDepositAm(groupAcInfo.getAc().getProjIntOnFixDepositAm());
		monthlyGAc.setNoOfFixDeposit(groupAcInfo.getAc().getNoOfFixDeposit());
		monthlyGAc.setNoOfActiveFixDeposit(groupAcInfo.getAc().getNoOfActiveFixDeposit());
		
		monthlyGAc.setOtherInvAm(groupAcInfo.getAc().getOtherInvAm());
		monthlyGAc.setPendingOtherInvAm(groupAcInfo.getAc().getOtherInvAm().subtract(groupAcInfo.getAc().getRecOtherInvAm()));
		monthlyGAc.setRecOtherInvAm(groupAcInfo.getAc().getRecOtherInvAm());
		monthlyGAc.setRecIntOnOtherInvAm(groupAcInfo.getAc().getRecIntOnOtherInvAm());
		monthlyGAc.setProjIntOnOtherInvAm(groupAcInfo.getAc().getProjIntOnOtherInvAm());
		monthlyGAc.setNoOfOtherInv(groupAcInfo.getAc().getNoOfOtherInv());
		monthlyGAc.setNoOfActiveOtherInv(groupAcInfo.getAc().getNoOfActiveOtherInv());
		
		monthlyGAc.setIntEnOnSavingAcAm(groupAcInfo.getAc().getIntEnOnSavingAcAm());
		monthlyGAc.setClearBankBalanceAm(groupAcInfo.getAc().getClearBankBalanceAm());
		monthlyGAc.setSubjClearingBankBalanceAm(groupAcInfo.getAc().getSubjClearingBankBalanceAm());
		monthlyGAc.setClearCashInHandAm(groupAcInfo.getAc().getClearCashInHandAm());
		monthlyGAc.setSubjClearingCashInHandAm(groupAcInfo.getAc().getSubjClearingCashInHandAm());
		monthlyGAc.setNetProfitAm(groupAcInfo.getAc().getNetProfitAm());
		monthlyGAc.setNetProfitProjAm(groupAcInfo.getAc().getNetProfitProjAm());
		monthlyGAc.setExpensesAm(groupAcInfo.getAc().getExpensesAm());
		monthlyGAc.setOutstandingExpensesAm(groupAcInfo.getAc().getOutstandingExpensesAm());
		monthlyGAc.setRecPenaltyAm(groupAcInfo.getAc().getRecPenaltyAm());
		monthlyGAc.setPendingPenaltyAm(groupAcInfo.getAc().getPendingPenaltyAm());
		
		monthlyGAc.setBorrowedLoanAm(groupAcInfo.getAc().getBorrowedLoanAm());
		monthlyGAc.setPendingBorrowedLoanAm(groupAcInfo.getAc().getBorrowedLoanAm().subtract(groupAcInfo.getAc().getPaidBorrowedLoanAm()));
		monthlyGAc.setPaidBorrowedLoanAm(groupAcInfo.getAc().getPaidBorrowedLoanAm());
		monthlyGAc.setPaidIntOnBorrowedLoanAm(groupAcInfo.getAc().getPaidIntOnBorrowedLoanAm());
		monthlyGAc.setProjIntOnBorrowedLoanAm(groupAcInfo.getAc().getProjIntOnBorrowedLoanAm());
		monthlyGAc.setNoOfBankLoan(groupAcInfo.getAc().getNoOfBankLoan());
		monthlyGAc.setNoOfActiveBankLoan(groupAcInfo.getAc().getNoOfActiveBankLoan());
		monthlyGAc.setBankSubStdDeptAm(groupAcInfo.getAc().getBankSubStdDeptAm());
		monthlyGAc.setBankNoOfSubStdDept(groupAcInfo.getAc().getBankNoOfSubStdDept());
		monthlyGAc.setBankBadDeptExpAm(groupAcInfo.getAc().getBankBadDeptExpAm());
		monthlyGAc.setBankNoOfBadDeptExp(groupAcInfo.getAc().getBankNoOfBadDeptExp());
		monthlyGAc.setBankBadDeptClosedAm(groupAcInfo.getAc().getBankBadDeptClosedAm());
		monthlyGAc.setBankNoOfBadDeptClosed(groupAcInfo.getAc().getBankNoOfBadDeptClosed());

		monthlyGAc.setGovRevolvingFundAm(groupAcInfo.getAc().getGovRevolvingFundAm());
		monthlyGAc.setGovRevolvingFundReturnedAm(groupAcInfo.getAc().getGovRevolvingFundReturnedAm());
		monthlyGAc.setNoOfGovRevolvingFund(groupAcInfo.getAc().getNoOfGovRevolvingFund());
		monthlyGAc.setNoOfActiveGovRevolvingFund(groupAcInfo.getAc().getNoOfActiveGovRevolvingFund());
		monthlyGAc.setGovDevelopmentFundAm(groupAcInfo.getAc().getGovDevelopmentFundAm());
		monthlyGAc.setNoOfGovDevelopmentFund(groupAcInfo.getAc().getNoOfGovDevelopmentFund());

		monthlyGAc.setPenShgOneMemRegFee(groupAcInfo.getAc().getPenShgOneMemRegFee());
		monthlyGAc.setPenShgOneServiceCharges(groupAcInfo.getAc().getPenShgOneServiceCharges());

		monthlyGAc.setNoOfTxsMonthlyExp(groupAcInfo.getAc().getNoOfTxsMonthlyExp());
		monthlyGAc.setNoOfTxsMonthlyDone(groupAcInfo.getAc().getNoOfTxsMonthlyDone());
		monthlyGAc.setNoOfTxsMonthlyApproved(groupAcInfo.getAc().getNoOfTxsMonthlyApproved());

		if(newObject) {
			groupAcInfo.getDaoFactory().getMonthlyGAcDAO().persist(monthlyGAc);
		} else {
			groupAcInfo.getDaoFactory().getMonthlyGAcDAO().merge(monthlyGAc);
		}
	}
	
	protected void saveMonthlyGLoanAcs() {
		List<GLoanAc> gLoanAcs = groupAcInfo.getDaoFactory().getGLoanAcDAO().getAllAcForGroup(groupAcInfo.getGroupAcNo());
		
		for(GLoanAc gLoanAc: gLoanAcs) {
			saveMonthlyGLoanAc(gLoanAc);
		}
	}
	
	protected void saveMonthlyGLoanAc(GLoanAc gLoanAc) {
		boolean newObject = false;
		MonthlyGLoanAc monthlyGLoanAc = groupAcInfo.getDaoFactory().getMonthlyGLoanAcDAO().findById(gLoanAc.getGLoanAcNo(), groupAcInfo.getMonth());

		if(monthlyGLoanAc == null) {
			monthlyGLoanAc = new MonthlyGLoanAc();
			monthlyGLoanAc.setId(new MonthlyGLoanAcId(gLoanAc.getGLoanAcNo(), groupAcInfo.getMonth()));
			monthlyGLoanAc.setGLoanAcNo(gLoanAc.getGLoanAcNo());
			monthlyGLoanAc.setMonth(groupAcInfo.getMonth());
			newObject = true;
		}

		monthlyGLoanAc.setGroupAcNo(gLoanAc.getGAc().getGAcNo());
		monthlyGLoanAc.setBGroupAcNo(gLoanAc.getBGroupAcNo());
		monthlyGLoanAc.setFundTypeId(gLoanAc.getFundTypeId());
		monthlyGLoanAc.setAccountStatusId(gLoanAc.getAccountStatusId());
		monthlyGLoanAc.setLoanSourceId(gLoanAc.getLoanSourceId());
		monthlyGLoanAc.setLoanAcName(gLoanAc.getLoanAcName());
		monthlyGLoanAc.setPrincipleAm(gLoanAc.getPrincipleAm());
		monthlyGLoanAc.setPendingPrincipleAm(gLoanAc.getPendingPrincipleAm());
		monthlyGLoanAc.setPaidInterestAm(gLoanAc.getPaidInterestAm());
		monthlyGLoanAc.setProjInterestAm(gLoanAc.getProjInterestAm());
		monthlyGLoanAc.setInstallmentAm(gLoanAc.getInstallmentAm());
		monthlyGLoanAc.setPreEmiInterestAm(gLoanAc.getPreEmiInterestAm());
		monthlyGLoanAc.setPendingInterestDueAm(gLoanAc.getPendingInterestDueAm());
		
		if(newObject) {
			groupAcInfo.getDaoFactory().getMonthlyGLoanAcDAO().persist(monthlyGLoanAc);
		} else {
			groupAcInfo.getDaoFactory().getMonthlyGLoanAcDAO().merge(monthlyGLoanAc);
		}
	}
	
	protected void saveMonthlyGInvtAcs() {
		List<GInvtAc> gInvtAcs = groupAcInfo.getDaoFactory().getGInvtAcDAO().getAllAcForGroup(groupAcInfo.getGroupAcNo());
		
		for(GInvtAc gInvtAc: gInvtAcs) {
			saveMonthlyGInvtAc(gInvtAc);
		}
	}
	
	protected void saveMonthlyGInvtAc(GInvtAc gInvtAc) {
		boolean newObject = false;
		MonthlyGInvtAc monthlyGInvtAc = groupAcInfo.getDaoFactory().getMonthlyGInvtAcDAO().findById(gInvtAc.getGInvtAcNo(), groupAcInfo.getMonth());

		if(monthlyGInvtAc == null) {
			monthlyGInvtAc = new MonthlyGInvtAc();
			monthlyGInvtAc.setId(new MonthlyGInvtAcId(gInvtAc.getGInvtAcNo(), groupAcInfo.getMonth()));
			monthlyGInvtAc.setGInvtAcNo(gInvtAc.getGInvtAcNo());
			monthlyGInvtAc.setMonth(groupAcInfo.getMonth());
			newObject = true;
		}

		monthlyGInvtAc.setGroupAcNo(gInvtAc.getGAc().getGAcNo());
		monthlyGInvtAc.setBGroupAcNo(gInvtAc.getBGroupAcNo());
		monthlyGInvtAc.setInvestmentTypeId(gInvtAc.getInvestmentTypeId());
		monthlyGInvtAc.setAccountStatus(gInvtAc.getAccountStatusId());
		monthlyGInvtAc.setInvestmentAcName(gInvtAc.getInvestmentAcName());
		monthlyGInvtAc.setInvtAm(gInvtAc.getInvtAm());
		monthlyGInvtAc.setRecInvtAm(gInvtAc.getRecInvtAm());
		monthlyGInvtAc.setRecInterestAm(gInvtAc.getRecInterestAm());
		monthlyGInvtAc.setProjInterestAm(gInvtAc.getProjInterestAm());
		
		if(newObject) {
			groupAcInfo.getDaoFactory().getMonthlyGInvtAcDAO().persist(monthlyGInvtAc);
		} else {
			groupAcInfo.getDaoFactory().getMonthlyGInvtAcDAO().merge(monthlyGInvtAc);
		}
	}
	
	protected void saveMonthlyGBankAccounts() {
		List<GBankAccount> gBankAccounts = groupAcInfo.getDaoFactory().getGBankAccountDAO().getGroupBankAccounts(groupAcInfo.getGroupAcNo());
		
		for(GBankAccount gBankAccount: gBankAccounts) {
			saveMonthlyGBankAccount(gBankAccount);
		}
	}
	
	protected void saveMonthlyGBankAccount(GBankAccount gBankAccount) {
		boolean newObject = false;
		MonthlyGBankAccount monthlyGBankAccount = groupAcInfo.getDaoFactory().getMonthlyGBankAccountDAO().findById(gBankAccount.getBankAccountNo(), groupAcInfo.getMonth());

		if(monthlyGBankAccount == null) {
			monthlyGBankAccount = new MonthlyGBankAccount();
			monthlyGBankAccount.setId(new MonthlyGBankAccountId(gBankAccount.getBankAccountNo(), groupAcInfo.getMonth()));
			monthlyGBankAccount.setBankAccountNo(gBankAccount.getBankAccountNo());
			monthlyGBankAccount.setMonth(groupAcInfo.getMonth());
			newObject = true;
		}

		monthlyGBankAccount.setGroupAcNo(gBankAccount.getGProfile().getGAcNo());
		monthlyGBankAccount.setBankAccount(groupAcInfo.getDaoFactory().getBankAccountDAO().getReferenceById(gBankAccount.getBankAccountNo()));
		monthlyGBankAccount.setClearBalanceAm(gBankAccount.getClearBalanceAm());
		monthlyGBankAccount.setSubjClearingBalanceAm(gBankAccount.getSubjClearingBalanceAm());
		monthlyGBankAccount.setVerifiedBalanceAm(gBankAccount.getVerifiedBalanceAm());
		
		if(newObject) {
			groupAcInfo.getDaoFactory().getMonthlyGBankAccountDAO().persist(monthlyGBankAccount);
		} else {
			groupAcInfo.getDaoFactory().getMonthlyGBankAccountDAO().merge(monthlyGBankAccount);
		}
	}
	
	protected void saveMonthlyGAcByTxtypes() {
		List<GAcByTxtype> gAcByTxtypes = groupAcInfo.getDaoFactory().getGAcByTxtypeDAO().getAllForGroup(groupAcInfo.getGroupAcNo());
		
		for(GAcByTxtype gAcByTxtyp: gAcByTxtypes) {
			saveMonthlyGAcByTxtype(gAcByTxtyp);
		}
	}
	
	protected void saveMonthlyGAcByTxtype(GAcByTxtype gAcByTxtyp) {
		
		if(gAcByTxtyp.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
			boolean newObject = false;
			MonthlyGAcByTxtype monthlyGAcByTxtype = groupAcInfo.getDaoFactory().getMonthlyGAcByTxtypeDAO().findById(gAcByTxtyp.getGroupAcNo(), gAcByTxtyp.getTxTypeId(), groupAcInfo.getMonth());

			if(monthlyGAcByTxtype == null) {
				monthlyGAcByTxtype = new MonthlyGAcByTxtype();
				monthlyGAcByTxtype.setId(new MonthlyGAcByTxtypeId(gAcByTxtyp.getGroupAcNo(), gAcByTxtyp.getTxTypeId(), groupAcInfo.getMonth()));
				monthlyGAcByTxtype.setGAcNo(groupAcInfo.getAc().getGAcNo());
				monthlyGAcByTxtype.setMonth(groupAcInfo.getMonth());
				newObject = true;
			}

			monthlyGAcByTxtype.setAmount(gAcByTxtyp.getAmount());


			if(newObject) {
				groupAcInfo.getDaoFactory().getMonthlyGAcByTxtypeDAO().persist(monthlyGAcByTxtype);
			} else {
				groupAcInfo.getDaoFactory().getMonthlyGAcByTxtypeDAO().merge(monthlyGAcByTxtype);
			}
		}
	}
}
