package com.beone.shg.net.persistence.bo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.config.GroupAccountConst;
import com.beone.shg.net.config.MemberAccountConst;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.GroupContact;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.model.resp.DashboardSheet;
import com.beone.shg.net.webservice.rest.model.resp.MDashboardSheet;
import com.beone.shg.net.webservice.rest.model.resp.MWiseDashboard;
import com.beone.shg.net.webservice.rest.model.resp.Record;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("dashboardBO")
public class DashboardBO extends BaseBO {
	private final static Logger LOGGER = LoggerFactory.getLogger(DashboardBO.class);
	private final static int DASHBOARD_PAGE_SIZE = 50;

	public DashboardSheet getGroupDashboard(String lang, long groupAcNo) throws BadRequestException {
		LOGGER.debug("getGroupDashboard(" + lang + ", " + groupAcNo + ")");

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}

		DashboardSheet dashboard = new DashboardSheet(60);

		// Find Group Account & Profile 
		GAc gAccount = daoFactory.getGAcDAO().findById(groupAcNo);
		if(gAccount == null) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		GProfile gProfile = gAccount.getGProfile();
		GRules gRules = null;
		for(GRules rule: gProfile.getGRuleses()) {
			gRules = rule;
			break;
		}

		GroupContact contact = daoFactory.getGroupContactDAO().findContactOfGroup(lang, groupAcNo);

		dashboard.setLangauge(lang);
		dashboard.setGroupName(DataUtil.toString(contact.getName()));
		dashboard.setMemberCount(gProfile.getActiveCoreMembers() + gProfile.getActiveAssociateMembers());
		dashboard.setGroupCreditRating(gAccount.getCreditRating());
		dashboard.setMonthlySaving(DataUtil.toString(DataUtil.add(gAccount.getCMPlannedMonthlySaving(), gAccount.getAMPlannedMonthlySaving())));
		dashboard.setCurrentBalance(DataUtil.toString(DataUtil.add(DataUtil.add(gAccount.getClearBankBalanceAm(), gAccount.getSubjClearingBankBalanceAm()),
				DataUtil.add(gAccount.getClearCashInHandAm(), gAccount.getSubjClearingCashInHandAm()))));
		dashboard.setTotalSaving(DataUtil.toString(DataUtil.add(gAccount.getCMSavedAm(), gAccount.getAMSavedAm())));

		dashboard.addHeaderRecord(new Record(GroupAccountConst.Name_of_Self_Help_Group, DataUtil.toString(contact.getName())));
		if(contact.getVision() != null && ! contact.getVision().isEmpty()) {
			dashboard.addHeaderRecord(new Record(GroupAccountConst.Vision, DataUtil.toString(contact.getVision())));
		}
		if(contact.getDescription() != null && ! contact.getDescription().isEmpty()) {
			dashboard.addHeaderRecord(new Record(GroupAccountConst.Description, DataUtil.toString(contact.getDescription())));
		}
		dashboard.addHeaderRecord(new Record(GroupAccountConst.Date_Of_Establishment, DateUtil.toString(gProfile.getEstablishmentDate())));
		dashboard.addHeaderRecord(new Record(GroupAccountConst.Group_Type, EnumCache.getNameOfGroupType(gProfile.getGroupTypeId())));

		dashboard.addRecord(new Record(GroupAccountConst.Credit_Rating, DataUtil.toString(gAccount.getCreditRating())));

		if(gRules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
			dashboard.addRecord(new Record(GroupAccountConst.Approval_Rating, DataUtil.toString(gAccount.getApprovalRating())));

			{
				Record cmInfo = new Record(GroupAccountConst.Core_Member_Info, DataUtil.EMPTY_STRING);
				dashboard.addRecord(cmInfo);			
				cmInfo.addDetail(new Record(GroupAccountConst.Number_of_Active_Core_Members, DataUtil.toString(gProfile.getActiveCoreMembers())));
				{
					Record savingInfo = new Record(GroupAccountConst.Member_Saving_Info, DataUtil.toString(gAccount.getCMSavedAm()));
					cmInfo.addDetail(savingInfo);

					savingInfo.addDetail(new Record(GroupAccountConst.CM_Planned_Monthly_Saving, DataUtil.toString(gAccount.getCMPlannedMonthlySaving())));
					savingInfo.addDetail(new Record(GroupAccountConst.CM_Saved_Amount, DataUtil.toString(gAccount.getCMSavedAm())));
					savingInfo.addDetail(new Record(GroupAccountConst.Outstanding_Saving, DataUtil.toString(gAccount.getCMOutstandingSavedAm())));
					savingInfo.addDetail(new Record(GroupAccountConst.CM_Provisional_Interest_Earned, DataUtil.toString(gAccount.getCMProvIntEnAm())));
					savingInfo.addDetail(new Record(GroupAccountConst.CM_Returned_Saved_Amount, DataUtil.toString(gAccount.getCMReturnedSavedAm())));
					savingInfo.addDetail(new Record(GroupAccountConst.CM_Returned_Interest_Earned, DataUtil.toString(gAccount.getCMReturnedIntEnAm())));
				}
				{
					Record loanInfo = new Record(GroupAccountConst.Member_Loan_Info, DataUtil.toString(gAccount.getCMLoanAm()));
					cmInfo.addDetail(loanInfo);

					if(gAccount.getCMLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
						loanInfo.addDetail(new Record(GroupAccountConst.CM_Loan_Amount, DataUtil.toString(gAccount.getCMLoanAm())));
						loanInfo.addDetail(new Record(GroupAccountConst.CM_Recovered_Loan_Amount, DataUtil.toString(gAccount.getCMRecLoanAm())));
						loanInfo.addDetail(new Record(GroupAccountConst.CM_Recovered_Interest_On_Loan, DataUtil.toString(gAccount.getCMRecIntOnLoanAm())));
						loanInfo.addDetail(new Record(GroupAccountConst.CM_Projected_Interest_On_Loan, DataUtil.toString(gAccount.getCMProjIntOnLoanAm())));
						loanInfo.addDetail(new Record(GroupAccountConst.CM_Number_of_Loans, DataUtil.toString(gAccount.getCMNoOfLoans())));
						loanInfo.addDetail(new Record(GroupAccountConst.CM_Number_of_Active_Loans, DataUtil.toString(gAccount.getCMNoOfActiveLoans())));
					}
				}
				cmInfo.addDetail(new Record(GroupAccountConst.CM_Profit_Share_Declared, DataUtil.toString(gAccount.getCMProfitShareDeclaredAm())));
				cmInfo.addDetail(new Record(GroupAccountConst.CM_Profit_Share_Paid, DataUtil.toString(gAccount.getCMProfitSharePaidAm())));
			}

			{
				Record amInfo = new Record(GroupAccountConst.Associate_Member_Info, DataUtil.EMPTY_STRING);
				dashboard.addRecord(amInfo);			
				amInfo.addDetail(new Record(GroupAccountConst.Number_of_Active_Associate_Members, DataUtil.toString(gProfile.getActiveAssociateMembers())));
				{
					Record savingInfo = new Record(GroupAccountConst.Member_Saving_Info, DataUtil.toString(gAccount.getAMSavedAm()));
					amInfo.addDetail(savingInfo);

					savingInfo.addDetail(new Record(GroupAccountConst.AM_Planned_Monthly_Saving, DataUtil.toString(gAccount.getAMPlannedMonthlySaving())));
					savingInfo.addDetail(new Record(GroupAccountConst.AM_Saved_Amount, DataUtil.toString(gAccount.getAMSavedAm())));
					savingInfo.addDetail(new Record(GroupAccountConst.AM_Provisional_Interest_Earned, DataUtil.toString(gAccount.getAMProvIntEnAm())));
					savingInfo.addDetail(new Record(GroupAccountConst.AM_Returned_Saved_Amount, DataUtil.toString(gAccount.getAMReturnedSavedAm())));
					savingInfo.addDetail(new Record(GroupAccountConst.AM_Returned_Interest_Earned, DataUtil.toString(gAccount.getAMReturnedIntEnAm())));
				}
				{
					Record loanInfo = new Record(GroupAccountConst.Member_Loan_Info, DataUtil.toString(gAccount.getAMLoanAm()));
					amInfo.addDetail(loanInfo);

					if(gAccount.getAMLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
						loanInfo.addDetail(new Record(GroupAccountConst.AM_Loan_Amount, DataUtil.toString(gAccount.getAMLoanAm())));
						loanInfo.addDetail(new Record(GroupAccountConst.AM_Recovered_Loan_Amount, DataUtil.toString(gAccount.getAMRecLoanAm())));
						loanInfo.addDetail(new Record(GroupAccountConst.AM_Recovered_Interest_On_Loan, DataUtil.toString(gAccount.getAMRecIntOnLoanAm())));
						loanInfo.addDetail(new Record(GroupAccountConst.AM_Projected_Interest_On_Loan, DataUtil.toString(gAccount.getAMProjIntOnLoanAm())));
						loanInfo.addDetail(new Record(GroupAccountConst.AM_Number_of_Loans, DataUtil.toString(gAccount.getAMNoOfLoans())));
						loanInfo.addDetail(new Record(GroupAccountConst.AM_Number_of_Active_Loans, DataUtil.toString(gAccount.getAMNoOfActiveLoans())));
					}
				}
				dashboard.addRecord(new Record(GroupAccountConst.AM_Divided_Declared, DataUtil.toString(gAccount.getAMDividedDeclaredAm())));
				dashboard.addRecord(new Record(GroupAccountConst.AM_Divided_Paid, DataUtil.toString(gAccount.getAMDividedPaidAm())));
			}

		} else {
			dashboard.addRecord(new Record(GroupAccountConst.Meeting_Attendance, DataUtil.toString(gAccount.getMeetingAttendance())));
			dashboard.addRecord(new Record(GroupAccountConst.Number_of_Active_Core_Members, DataUtil.toString(gProfile.getActiveCoreMembers())));

			{
				Record savingInfo = new Record(GroupAccountConst.Member_Saving_Info, DataUtil.toString(gAccount.getCMSavedAm()));
				dashboard.addRecord(savingInfo);

				savingInfo.addDetail(new Record(GroupAccountConst.Planned_Monthly_Saving, DataUtil.toString(gAccount.getCMPlannedMonthlySaving())));
				savingInfo.addDetail(new Record(GroupAccountConst.Saved_Amount, DataUtil.toString(gAccount.getCMSavedAm())));
				savingInfo.addDetail(new Record(GroupAccountConst.Outstanding_Saving, DataUtil.toString(gAccount.getCMOutstandingSavedAm())));
				savingInfo.addDetail(new Record(GroupAccountConst.Provisional_Interest_Earned, DataUtil.toString(gAccount.getCMProvIntEnAm())));
				savingInfo.addDetail(new Record(GroupAccountConst.Returned_Saved_Amount, DataUtil.toString(gAccount.getCMReturnedSavedAm())));
				savingInfo.addDetail(new Record(GroupAccountConst.Returned_Interest_Earned, DataUtil.toString(gAccount.getCMReturnedIntEnAm())));
			}
			{
				Record loanInfo = new Record(GroupAccountConst.Member_Loan_Info, DataUtil.toString(gAccount.getCMLoanAm()));
				dashboard.addRecord(loanInfo);

				if(gAccount.getCMLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
					loanInfo.addDetail(new Record(GroupAccountConst.Loan_Amount, DataUtil.toString(gAccount.getCMLoanAm())));
					loanInfo.addDetail(new Record(GroupAccountConst.Number_of_Loans, DataUtil.toString(gAccount.getCMNoOfLoans())));
					loanInfo.addDetail(new Record(GroupAccountConst.Number_of_Active_Loans, DataUtil.toString(gAccount.getCMNoOfActiveLoans())));
					loanInfo.addDetail(new Record(GroupAccountConst.Recovered_Loan_Amount, DataUtil.toString(gAccount.getCMRecLoanAm())));
					loanInfo.addDetail(new Record(GroupAccountConst.Recovered_Interest_On_Loan, DataUtil.toString(gAccount.getCMRecIntOnLoanAm())));
					loanInfo.addDetail(new Record(GroupAccountConst.Projected_Interest_On_Loan, DataUtil.toString(gAccount.getCMProjIntOnLoanAm())));
				}
			}
			dashboard.addRecord(new Record(GroupAccountConst.Profit_Share_Declared, DataUtil.toString(gAccount.getCMProfitShareDeclaredAm())));
			dashboard.addRecord(new Record(GroupAccountConst.Profit_Share_Paid, DataUtil.toString(gAccount.getCMProfitSharePaidAm())));
		}

		{
			Record projectInfo = new Record(GroupAccountConst.SHG_Project_Info, DataUtil.toString(gAccount.getPLoanAm()));
			dashboard.addRecord(projectInfo);			
			if(gAccount.getPLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
				projectInfo.addDetail(new Record(GroupAccountConst.No_of_Project_Loans, DataUtil.toString(gAccount.getNoOfProject())));
				projectInfo.addDetail(new Record(GroupAccountConst.No_of_Active_Project_Loans, DataUtil.toString(gAccount.getNoOfActiveProject())));
				projectInfo.addDetail(new Record(GroupAccountConst.Project_Loan_Amount, DataUtil.toString(gAccount.getPLoanAm())));
				projectInfo.addDetail(new Record(GroupAccountConst.Project_Recovered_Loan_Amount, DataUtil.toString(gAccount.getPRecLoanAm())));
				projectInfo.addDetail(new Record(GroupAccountConst.Project_Recovered_Interest_On_Loan, DataUtil.toString(gAccount.getPRecIntOnLoanAm())));
				projectInfo.addDetail(new Record(GroupAccountConst.Project_Projected_Interest_On_Loan, DataUtil.toString(gAccount.getPProjIntOnLoanAm())));
			}
		}
		{
			Record fixInfo = new Record(GroupAccountConst.SHG_Fix_Deposit_Info, DataUtil.toString(gAccount.getFixDepositInvAm()));
			dashboard.addRecord(fixInfo);			
			if(gAccount.getFixDepositInvAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
				fixInfo.addDetail(new Record(GroupAccountConst.No_of_Fixed_Deposits, DataUtil.toString(gAccount.getNoOfFixDeposit())));
				fixInfo.addDetail(new Record(GroupAccountConst.No_of_Active_Fixed_Deposits, DataUtil.toString(gAccount.getNoOfActiveFixDeposit())));
				fixInfo.addDetail(new Record(GroupAccountConst.Fixed_Deposit_Amount, DataUtil.toString(gAccount.getFixDepositInvAm())));
				fixInfo.addDetail(new Record(GroupAccountConst.Recovered_Fixed_Deposit_Amount, DataUtil.toString(gAccount.getRecFixDepositAm())));
				fixInfo.addDetail(new Record(GroupAccountConst.Recovered_Interest_On_Fixed_Deposit, DataUtil.toString(gAccount.getRecIntOnFixDepositAm())));
				fixInfo.addDetail(new Record(GroupAccountConst.Projected_Interest_On_Fixed_Deposit, DataUtil.toString(gAccount.getProjIntOnFixDepositAm())));
			}
		}
		{
			Record otherInfo = new Record(GroupAccountConst.SHG_Other_Investment_Info, DataUtil.toString(gAccount.getOtherInvAm()));
			dashboard.addRecord(otherInfo);			
			if(gAccount.getOtherInvAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
				otherInfo.addDetail(new Record(GroupAccountConst.No_of_Other_Investments, DataUtil.toString(gAccount.getNoOfOtherInv())));
				otherInfo.addDetail(new Record(GroupAccountConst.No_of_Active_Other_Investments, DataUtil.toString(gAccount.getNoOfActiveOtherInv())));
				otherInfo.addDetail(new Record(GroupAccountConst.Other_Investment_Amount, DataUtil.toString(gAccount.getOtherInvAm())));
				otherInfo.addDetail(new Record(GroupAccountConst.Recovered_Other_Investment_Amount, DataUtil.toString(gAccount.getRecOtherInvAm())));
				otherInfo.addDetail(new Record(GroupAccountConst.Recovered_Interest_On_Other_Investment, DataUtil.toString(gAccount.getRecIntOnOtherInvAm())));
				otherInfo.addDetail(new Record(GroupAccountConst.Projected_Interest_On_Other_Investments, DataUtil.toString(gAccount.getProjIntOnOtherInvAm())));
			}
		}
		{
			Record bankInfo = new Record(GroupAccountConst.Bank_Loan_Info, DataUtil.toString(gAccount.getBorrowedLoanAm()));
			dashboard.addRecord(bankInfo);			
			if(gAccount.getBorrowedLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
				bankInfo.addDetail(new Record(GroupAccountConst.No_of_Bank_Loans, DataUtil.toString(gAccount.getNoOfBankLoan())));
				bankInfo.addDetail(new Record(GroupAccountConst.No_of_Active_Bank_Loans, DataUtil.toString(gAccount.getNoOfActiveBankLoan())));
				bankInfo.addDetail(new Record(GroupAccountConst.Bank_Loan_Amount, DataUtil.toString(gAccount.getBorrowedLoanAm())));
				bankInfo.addDetail(new Record(GroupAccountConst.Paid_Bank_Loan_Amount, DataUtil.toString(gAccount.getPaidBorrowedLoanAm())));
				bankInfo.addDetail(new Record(GroupAccountConst.Paid_Interest_On_Bank_Loan, DataUtil.toString(gAccount.getPaidIntOnBorrowedLoanAm())));
				bankInfo.addDetail(new Record(GroupAccountConst.Projected_Interest_On_Bank_Loan, DataUtil.toString(gAccount.getProjIntOnBorrowedLoanAm())));
			}
		}
		dashboard.addRecord(new Record(GroupAccountConst.Interest_Earned_On_Saving_Account, DataUtil.toString(gAccount.getIntEnOnSavingAcAm())));
		dashboard.addRecord(new Record(GroupAccountConst.Expense_Amount, DataUtil.toString(gAccount.getExpensesAm())));
		dashboard.addRecord(new Record(GroupAccountConst.Outstanding_Saving, DataUtil.toString(gAccount.getOutstandingExpensesAm())));
		dashboard.addRecord(new Record(GroupAccountConst.Recovered_Penalty_Fee, DataUtil.toString(gAccount.getRecPenaltyAm())));
		dashboard.addRecord(new Record(GroupAccountConst.Outstanding_Penalty_Fee, DataUtil.toString(gAccount.getPendingPenaltyAm())));
		dashboard.addRecord(new Record(GroupAccountConst.Net_Profit, DataUtil.toString(gAccount.getNetProfitAm())));
		dashboard.addRecord(new Record(GroupAccountConst.Net_Profit_Projected, DataUtil.toString(gAccount.getNetProfitProjAm())));
		dashboard.addRecord(new Record(GroupAccountConst.Bank_Balance, DataUtil.toString(DataUtil.add(gAccount.getClearBankBalanceAm(), gAccount.getSubjClearingBankBalanceAm()))));
		dashboard.addRecord(new Record(GroupAccountConst.Cash_In_Hand, DataUtil.toString(DataUtil.add(gAccount.getClearCashInHandAm(), gAccount.getSubjClearingCashInHandAm()))));

		return dashboard;
	}

	public DashboardSheet getGroupTopDashboard(String lang, long groupAcNo) throws BadRequestException {
		LOGGER.debug("getGroupDashboard(" + lang + ", " + groupAcNo + ")");

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}

		DashboardSheet dashboard = new DashboardSheet(60);

		// Find Group Account & Profile 
		GAc gAccount = daoFactory.getGAcDAO().findById(groupAcNo);
		if(gAccount == null) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		GProfile gProfile = gAccount.getGProfile();

		GroupContact contact = daoFactory.getGroupContactDAO().findContactOfGroup(lang, groupAcNo);

		dashboard.setLangauge(lang);
		dashboard.setGroupName(DataUtil.toString(contact.getName()));
		dashboard.setMemberCount(gProfile.getActiveCoreMembers() + gProfile.getActiveAssociateMembers());
		dashboard.setGroupCreditRating(gAccount.getCreditRating());
		dashboard.setMonthlySaving(DataUtil.toString(DataUtil.add(gAccount.getCMPlannedMonthlySaving(), gAccount.getAMPlannedMonthlySaving())));
		dashboard.setCurrentBalance(DataUtil.toString(DataUtil.add(DataUtil.add(gAccount.getClearBankBalanceAm(), gAccount.getSubjClearingBankBalanceAm()),
				DataUtil.add(gAccount.getClearCashInHandAm(), gAccount.getSubjClearingCashInHandAm()))));
		dashboard.setTotalSaving(DataUtil.toString(DataUtil.add(gAccount.getCMSavedAm(), gAccount.getAMSavedAm())));

		return dashboard;
	}

	public DashboardSheet getMemberDashboard(String lang, long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No: " + memberAcNo);
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}

		// Find Member Account & Profile 
		MAc mAccount = daoFactory.getMAcDAO().findById(memberAcNo);

		return getMemberDashboard(lang, mAccount);
	}

	protected DashboardSheet getMemberDashboard(String lang, MAc mAccount) throws BadRequestException {

		if(mAccount == null) {
			throw new BadRequestException("Invalid Member Account");
		}

		DashboardSheet dashboard = new DashboardSheet(25);
		dashboard.setLangauge(lang);

		MProfile mProfile = mAccount.getMProfile();

		dashboard.addHeaderRecord(new Record(MemberAccountConst.Member_Account_Number, DataUtil.toString(mAccount.getMAcNo())));
		dashboard.addHeaderRecord(new Record(MemberAccountConst.Member_Name, DataUtil.toString(daoFactory.getMemberContactDAO().getNameOfMember(lang, mAccount.getMAcNo()))));
		dashboard.addHeaderRecord(new Record(MemberAccountConst.Active_Status, EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId())));
		dashboard.addHeaderRecord(new Record(MemberAccountConst.Membership_Role, DataUtil.toString(EnumCache.getNameOfMRole(mProfile.getMroleId()))));
		dashboard.addHeaderRecord(new Record(MemberAccountConst.Date_Of_Joining, DateUtil.toString(mProfile.getDateOfEnroll())));
		dashboard.addHeaderRecord(new Record(MemberAccountConst.Birthday, DateUtil.toString(mProfile.getDateOfBirth())));
		dashboard.addHeaderRecord(new Record(MemberAccountConst.Annivarcy, DateUtil.toString(mProfile.getDateOfAnni())));

		dashboard.addRecord(new Record(MemberAccountConst.Credit_Rating, DataUtil.toString(mAccount.getCreditRating())));
		{
			Record savingInfo = new Record(GroupAccountConst.Member_Saving_Info, DataUtil.toString(mAccount.getSavedAm()));
			dashboard.addRecord(savingInfo);

			savingInfo.addDetail(new Record(MemberAccountConst.Planned_Monthly_Saving, DataUtil.toString(mAccount.getPlannedMonthlySavingAm())));
			savingInfo.addDetail(new Record(MemberAccountConst.Saved_Amount, DataUtil.toString(mAccount.getSavedAm())));
			savingInfo.addDetail(new Record(GroupAccountConst.Outstanding_Saving, DataUtil.toString(mAccount.getOutstandingSavedAm())));
			savingInfo.addDetail(new Record(MemberAccountConst.Provisional_Interest_Earned, DataUtil.toString(mAccount.getProvIntEnAm())));
			savingInfo.addDetail(new Record(MemberAccountConst.Previous_Months_Interest_Earned, DataUtil.toString(mAccount.getPrevMonthIntEnAm())));
			savingInfo.addDetail(new Record(MemberAccountConst.Returned_Saved_Amount, DataUtil.toString(mAccount.getReturnedSavedAm())));
			savingInfo.addDetail(new Record(MemberAccountConst.Returned_Interest_Earned, DataUtil.toString(mAccount.getReturnedIntEnAm())));
		}
		{
			Record loanInfo = new Record(GroupAccountConst.Member_Loan_Info, DataUtil.toString(mAccount.getLoanAm()));
			dashboard.addRecord(loanInfo);

			if(mAccount.getLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
				loanInfo.addDetail(new Record(MemberAccountConst.Loan_Amount, DataUtil.toString(mAccount.getLoanAm())));
				loanInfo.addDetail(new Record(MemberAccountConst.Recovered_Loan_Amount, DataUtil.toString(mAccount.getRecLoanAm())));
				loanInfo.addDetail(new Record(MemberAccountConst.Recovered_Interest_On_Loan, DataUtil.toString(mAccount.getRecIntOnLoanAm())));
				loanInfo.addDetail(new Record(MemberAccountConst.Projected_Interest_On_Loan, DataUtil.toString(mAccount.getProjIntOnLoanAm())));
				loanInfo.addDetail(new Record(MemberAccountConst.Number_of_Loans, DataUtil.toString(mAccount.getNoOfLoans())));
				loanInfo.addDetail(new Record(MemberAccountConst.Number_of_Active_Loans, DataUtil.toString(mAccount.getNoOfActiveLoans())));
			}
		}
		dashboard.addRecord(new Record(MemberAccountConst.Paid_Penalty_Fee, DataUtil.toString(mAccount.getRecPenaltyAm())));
		dashboard.addRecord(new Record(MemberAccountConst.Outstanding_Penalty_Fee, DataUtil.toString(mAccount.getPendingPenaltyAm())));    	
		dashboard.addRecord(new Record(MemberAccountConst.Divided_Profit_Share_Declared, DataUtil.toString(mAccount.getDividedProfitDeclaredAm())));
		dashboard.addRecord(new Record(MemberAccountConst.Divided_Profit_Share_Paid, DataUtil.toString(mAccount.getDividedProfitPaidAm())));

		return dashboard;
	}

	public List<DashboardSheet> getMemberWiseDashboard(String lang, long groupAcNo) throws BadRequestException {
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}

		List<DashboardSheet> memDashboards = new ArrayList<DashboardSheet>();

		// Load all Member Accounts for the Group
		List<MAc> mAccounts = daoFactory.getMAcDAO().getAllGroupMembers(groupAcNo);

		GroupInfoCollector collector = new GroupInfoCollector();
		collector.setGroupAcNo(groupAcNo);
		daoFactory.getMemberContactDAO().loadMemberNamesForGroup(lang, collector);

		int noOfAcs = mAccounts.size();

		for(int i = 0; (i < noOfAcs && i < DASHBOARD_PAGE_SIZE); i ++) {
			MAc mAccount = mAccounts.get(i);

			if(mAccount == null) {
				throw new BadRequestException("Invalid Member Account");
			}

			// Skip Rejected Members, However show Closed Member Accounts
			if(EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mAccount.getMProfile().getApprovalStatusId()).equals(EnumConst.ApprovalStatus_Rejected)) {
				continue;
			}

			DashboardSheet dashboard = new DashboardSheet(25);
			dashboard.setLangauge(lang);

			MProfile mProfile = mAccount.getMProfile();

			dashboard.addRecord(new Record(MemberAccountConst.Member_Account_Number, DataUtil.toString(mAccount.getMAcNo())));
			dashboard.addRecord(new Record(MemberAccountConst.Member_Name, DataUtil.toString(collector.getMemberName(mAccount.getMAcNo()))));
			dashboard.addRecord(new Record(MemberAccountConst.Active_Status, EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId())));
			dashboard.addRecord(new Record(MemberAccountConst.Membership_Role, DataUtil.toString(EnumCache.getNameOfMRole(mProfile.getMroleId()))));
			dashboard.addRecord(new Record(MemberAccountConst.Credit_Rating, DataUtil.toString(mAccount.getCreditRating())));
			dashboard.addRecord(new Record(MemberAccountConst.Planned_Monthly_Saving, DataUtil.toString(mAccount.getPlannedMonthlySavingAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Saved_Amount, DataUtil.toString(mAccount.getSavedAm())));
			dashboard.addRecord(new Record(GroupAccountConst.Outstanding_Saving, DataUtil.toString(mAccount.getOutstandingSavedAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Provisional_Interest_Earned, DataUtil.toString(mAccount.getProvIntEnAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Previous_Months_Interest_Earned, DataUtil.toString(mAccount.getPrevMonthIntEnAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Returned_Saved_Amount, DataUtil.toString(mAccount.getReturnedSavedAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Returned_Interest_Earned, DataUtil.toString(mAccount.getReturnedIntEnAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Divided_Profit_Share_Declared, DataUtil.toString(mAccount.getDividedProfitDeclaredAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Divided_Profit_Share_Paid, DataUtil.toString(mAccount.getDividedProfitPaidAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Loan_Amount, DataUtil.toString(mAccount.getLoanAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Recovered_Loan_Amount, DataUtil.toString(mAccount.getRecLoanAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Recovered_Interest_On_Loan, DataUtil.toString(mAccount.getRecIntOnLoanAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Projected_Interest_On_Loan, DataUtil.toString(mAccount.getProjIntOnLoanAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Number_of_Loans, DataUtil.toString(mAccount.getNoOfLoans())));
			dashboard.addRecord(new Record(MemberAccountConst.Number_of_Active_Loans, DataUtil.toString(mAccount.getNoOfActiveLoans())));
			dashboard.addRecord(new Record(MemberAccountConst.Paid_Penalty_Fee, DataUtil.toString(mAccount.getRecPenaltyAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Outstanding_Penalty_Fee, DataUtil.toString(mAccount.getPendingPenaltyAm())));    	
			dashboard.addRecord(new Record(MemberAccountConst.Date_Of_Joining, DateUtil.toString(mProfile.getDateOfEnroll())));
			dashboard.addRecord(new Record(MemberAccountConst.Birthday, DateUtil.toString(mProfile.getDateOfBirth())));
			dashboard.addRecord(new Record(MemberAccountConst.Annivarcy, DateUtil.toString(mProfile.getDateOfAnni())));

			memDashboards.add(dashboard);
		}

		return memDashboards;
	}

	public MWiseDashboard getMemberWiseDashboard(String lang, long groupAcNo, int page) throws BadRequestException {
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}

		// Load all Member Accounts for the Group
		List<MAc> mAccounts = daoFactory.getMAcDAO().getAllGroupMembers(groupAcNo);

		GroupInfoCollector collector = new GroupInfoCollector();
		collector.setGroupAcNo(groupAcNo);
		daoFactory.getMemberContactDAO().loadMemberNamesForGroup(lang, collector);

		int noOfAcs = mAccounts.size();
		int startOfPage = ((page - 1) * DASHBOARD_PAGE_SIZE);
		int endOfPage = startOfPage + DASHBOARD_PAGE_SIZE - 1 ;
		int noOfPages = (noOfAcs / DASHBOARD_PAGE_SIZE);
		if(noOfAcs % DASHBOARD_PAGE_SIZE > 0) {
			noOfPages++;
		}
		
		MWiseDashboard mwDashboard = new MWiseDashboard(70);
		mwDashboard.setCurrentPageIndex(page);
		mwDashboard.setNoOfPages(noOfPages);
		
		for(int i = startOfPage; (i < noOfAcs && i <= endOfPage); i ++) {
			MAc mAccount = mAccounts.get(i);
			if(mAccount == null) {
				throw new BadRequestException("Invalid Member Account");
			}

			// Skip Rejected Members, However show Closed Member Accounts
			if(EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mAccount.getMProfile().getApprovalStatusId()).equals(EnumConst.ApprovalStatus_Rejected)) {
				continue;
			}

			MDashboardSheet dashboard = new MDashboardSheet(25);
			dashboard.setLangauge(lang);

			MProfile mProfile = mAccount.getMProfile();

			dashboard.addRecord(new Record(MemberAccountConst.Member_Account_Number, DataUtil.toString(mAccount.getMAcNo())));
			dashboard.addRecord(new Record(MemberAccountConst.Member_Name, DataUtil.toString(collector.getMemberName(mAccount.getMAcNo()))));
			dashboard.addRecord(new Record(MemberAccountConst.Active_Status, EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId())));
			dashboard.addRecord(new Record(MemberAccountConst.Membership_Role, DataUtil.toString(EnumCache.getNameOfMRole(mProfile.getMroleId()))));
			dashboard.addRecord(new Record(MemberAccountConst.Credit_Rating, DataUtil.toString(mAccount.getCreditRating())));
			dashboard.addRecord(new Record(MemberAccountConst.Planned_Monthly_Saving, DataUtil.toString(mAccount.getPlannedMonthlySavingAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Saved_Amount, DataUtil.toString(mAccount.getSavedAm())));
			dashboard.addRecord(new Record(GroupAccountConst.Outstanding_Saving, DataUtil.toString(mAccount.getOutstandingSavedAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Provisional_Interest_Earned, DataUtil.toString(mAccount.getProvIntEnAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Previous_Months_Interest_Earned, DataUtil.toString(mAccount.getPrevMonthIntEnAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Returned_Saved_Amount, DataUtil.toString(mAccount.getReturnedSavedAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Returned_Interest_Earned, DataUtil.toString(mAccount.getReturnedIntEnAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Divided_Profit_Share_Declared, DataUtil.toString(mAccount.getDividedProfitDeclaredAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Divided_Profit_Share_Paid, DataUtil.toString(mAccount.getDividedProfitPaidAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Loan_Amount, DataUtil.toString(mAccount.getLoanAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Recovered_Loan_Amount, DataUtil.toString(mAccount.getRecLoanAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Recovered_Interest_On_Loan, DataUtil.toString(mAccount.getRecIntOnLoanAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Projected_Interest_On_Loan, DataUtil.toString(mAccount.getProjIntOnLoanAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Number_of_Loans, DataUtil.toString(mAccount.getNoOfLoans())));
			dashboard.addRecord(new Record(MemberAccountConst.Number_of_Active_Loans, DataUtil.toString(mAccount.getNoOfActiveLoans())));
			dashboard.addRecord(new Record(MemberAccountConst.Paid_Penalty_Fee, DataUtil.toString(mAccount.getRecPenaltyAm())));
			dashboard.addRecord(new Record(MemberAccountConst.Outstanding_Penalty_Fee, DataUtil.toString(mAccount.getPendingPenaltyAm())));    	
			dashboard.addRecord(new Record(MemberAccountConst.Date_Of_Joining, DateUtil.toString(mProfile.getDateOfEnroll())));
			dashboard.addRecord(new Record(MemberAccountConst.Birthday, DateUtil.toString(mProfile.getDateOfBirth())));
			dashboard.addRecord(new Record(MemberAccountConst.Annivarcy, DateUtil.toString(mProfile.getDateOfAnni())));

			mwDashboard.addMDashboard(dashboard);
		}

		return mwDashboard;
	}
}
