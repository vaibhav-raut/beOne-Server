package com.beone.shg.net.webservice.rest.model.rest;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GroupAc {
	private float creditRating;
	private float approvalRating;
	private float meetingAttendance;
	private BigDecimal CMSavedAm;
	private BigDecimal CMOutstandingSavedAm;
	private BigDecimal CMReturnedSavedAm;
	private BigDecimal CMReturnedIntEnAm;
	private BigDecimal CMProvIntEnAm;
	private BigDecimal CMProfitShareDeclaredAm;
	private BigDecimal CMProfitSharePaidAm;
	private BigDecimal CMLoanAm;
	private BigDecimal CMRecLoanAm;
	private BigDecimal CMRecIntOnLoanAm;
	private BigDecimal CMProjIntOnLoanAm;
	private BigDecimal CMPlannedMonthlySaving;
	private int CMNoOfLoans;
	private int CMNoOfActiveLoans;
	private BigDecimal CMSubStdDeptAm;
	private int CMNoOfSubStdDept;
	private BigDecimal CMBadDeptExpAm;
	private int CMNoOfBadDeptExp;
	private BigDecimal CMBadDeptClosedAm;
	private int CMNoOfBadDeptClosed;
	private BigDecimal AMSavedAm;
	private BigDecimal AMOutstandingSavedAm;
	private BigDecimal AMProvIntEnAm;
	private BigDecimal AMDividedDeclaredAm;
	private BigDecimal AMDividedPaidAm;
	private BigDecimal AMReturnedSavedAm;
	private BigDecimal AMReturnedIntEnAm;
	private BigDecimal AMLoanAm;
	private BigDecimal AMRecLoanAm;
	private BigDecimal AMRecIntOnLoanAm;
	private BigDecimal AMProjIntOnLoanAm;
	private BigDecimal AMPlannedMonthlySaving;
	private int AMNoOfLoans;
	private int AMNoOfActiveLoans;
	private BigDecimal AMSubStdDeptAm;
	private int AMNoOfSubStdDept;
	private BigDecimal AMBadDeptExpAm;
	private int AMNoOfBadDeptExp;
	private BigDecimal AMBadDeptClosedAm;
	private int AMNoOfBadDeptClosed;
	private BigDecimal PLoanAm;
	private BigDecimal PRecLoanAm;
	private BigDecimal PRecIntOnLoanAm;
	private BigDecimal PProjIntOnLoanAm;
	private int noOfProject;
	private int noOfActiveProject;
	private BigDecimal fixDepositInvAm;
	private BigDecimal recFixDepositAm;
	private BigDecimal recIntOnFixDepositAm;
	private BigDecimal projIntOnFixDepositAm;
	private int noOfFixDeposit;
	private int noOfActiveFixDeposit;
	private BigDecimal otherInvAm;
	private BigDecimal recOtherInvAm;
	private BigDecimal recIntOnOtherInvAm;
	private BigDecimal projIntOnOtherInvAm;
	private int noOfOtherInv;
	private int noOfActiveOtherInv;
	private BigDecimal intEnOnSavingAcAm;
	private BigDecimal bankBalanceAm;
	private BigDecimal cashInHandAm;
	private BigDecimal subjClearingBankBalanceAm;
	private BigDecimal subjClearingCashInHandAm;
	private BigDecimal netProfitAm;
	private BigDecimal netProfitProjAm;
	private BigDecimal expensesAm;
	private BigDecimal outstandingExpensesAm;
	private BigDecimal penaltyAm;
	private BigDecimal borrowedLoanAm;
	private BigDecimal paidBorrowedLoanAm;
	private BigDecimal paidIntOnBorrowedLoanAm;
	private BigDecimal projIntOnBorrowedLoanAm;
	private BigDecimal bankSubStdDeptAm;
	private int bankNoOfSubStdDept;
	private BigDecimal bankBadDeptExpAm;
	private int bankNoOfBadDeptExp;
	private BigDecimal bankBadDeptClosedAm;
	private int bankNoOfBadDeptClosed;
	private int noOfBankLoan;
	private int noOfActiveBankLoan;
	private BigDecimal govRevolvingFundAm;
	private BigDecimal govRevolvingFundReturnedAm;
	private int noOfGovRevolvingFund;
	private int noOfActiveGovRevolvingFund;
	private BigDecimal govDevelopmentFundAm;
	private int noOfGovDevelopmentFund;
	private BigDecimal penShgOneMemRegFee;
	private BigDecimal penShgOneServiceCharges;
	private int noOfTxsMonthlyExp;
	private int noOfTxsMonthlyDone;
	private int noOfTxsMonthlyApproved;
	
	public float getCreditRating() {
		return creditRating;
	}
	public void setCreditRating(float creditRating) {
		this.creditRating = creditRating;
	}
	public float getApprovalRating() {
		return approvalRating;
	}
	public void setApprovalRating(float approvalRating) {
		this.approvalRating = approvalRating;
	}
	public float getMeetingAttendance() {
		return meetingAttendance;
	}
	public void setMeetingAttendance(float meetingAttendance) {
		this.meetingAttendance = meetingAttendance;
	}
	public BigDecimal getCMSavedAm() {
		return CMSavedAm;
	}
	public void setCMSavedAm(BigDecimal cMSavedAm) {
		CMSavedAm = cMSavedAm;
	}
	public BigDecimal getCMOutstandingSavedAm() {
		return CMOutstandingSavedAm;
	}
	public void setCMOutstandingSavedAm(BigDecimal cMOutstandingSavedAm) {
		CMOutstandingSavedAm = cMOutstandingSavedAm;
	}
	public BigDecimal getCMReturnedSavedAm() {
		return CMReturnedSavedAm;
	}
	public void setCMReturnedSavedAm(BigDecimal cMReturnedSavedAm) {
		CMReturnedSavedAm = cMReturnedSavedAm;
	}
	public BigDecimal getCMReturnedIntEnAm() {
		return CMReturnedIntEnAm;
	}
	public void setCMReturnedIntEnAm(BigDecimal cMReturnedIntEnAm) {
		CMReturnedIntEnAm = cMReturnedIntEnAm;
	}
	public BigDecimal getCMProvIntEnAm() {
		return CMProvIntEnAm;
	}
	public void setCMProvIntEnAm(BigDecimal cMProvIntEnAm) {
		CMProvIntEnAm = cMProvIntEnAm;
	}
	public BigDecimal getCMProfitShareDeclaredAm() {
		return CMProfitShareDeclaredAm;
	}
	public void setCMProfitShareDeclaredAm(BigDecimal cMProfitShareDeclaredAm) {
		CMProfitShareDeclaredAm = cMProfitShareDeclaredAm;
	}
	public BigDecimal getCMProfitSharePaidAm() {
		return CMProfitSharePaidAm;
	}
	public void setCMProfitSharePaidAm(BigDecimal cMProfitSharePaidAm) {
		CMProfitSharePaidAm = cMProfitSharePaidAm;
	}
	public BigDecimal getCMLoanAm() {
		return CMLoanAm;
	}
	public void setCMLoanAm(BigDecimal cMLoanAm) {
		CMLoanAm = cMLoanAm;
	}
	public BigDecimal getCMRecLoanAm() {
		return CMRecLoanAm;
	}
	public void setCMRecLoanAm(BigDecimal cMRecLoanAm) {
		CMRecLoanAm = cMRecLoanAm;
	}
	public BigDecimal getCMRecIntOnLoanAm() {
		return CMRecIntOnLoanAm;
	}
	public void setCMRecIntOnLoanAm(BigDecimal cMRecIntOnLoanAm) {
		CMRecIntOnLoanAm = cMRecIntOnLoanAm;
	}
	public BigDecimal getCMProjIntOnLoanAm() {
		return CMProjIntOnLoanAm;
	}
	public void setCMProjIntOnLoanAm(BigDecimal cMProjIntOnLoanAm) {
		CMProjIntOnLoanAm = cMProjIntOnLoanAm;
	}
	public BigDecimal getCMPlannedMonthlySaving() {
		return CMPlannedMonthlySaving;
	}
	public void setCMPlannedMonthlySaving(BigDecimal cMPlannedMonthlySaving) {
		CMPlannedMonthlySaving = cMPlannedMonthlySaving;
	}
	public int getCMNoOfLoans() {
		return CMNoOfLoans;
	}
	public void setCMNoOfLoans(int cMNoOfLoans) {
		CMNoOfLoans = cMNoOfLoans;
	}
	public int getCMNoOfActiveLoans() {
		return CMNoOfActiveLoans;
	}
	public void setCMNoOfActiveLoans(int cMNoOfActiveLoans) {
		CMNoOfActiveLoans = cMNoOfActiveLoans;
	}
	public BigDecimal getCMSubStdDeptAm() {
		return CMSubStdDeptAm;
	}
	public void setCMSubStdDeptAm(BigDecimal CMSubStdDeptAm) {
		this.CMSubStdDeptAm = CMSubStdDeptAm;
	}
	public int getCMNoOfSubStdDept() {
		return CMNoOfSubStdDept;
	}
	public void setCMNoOfSubStdDept(int cMNoOfSubStdDept) {
		CMNoOfSubStdDept = cMNoOfSubStdDept;
	}
	public BigDecimal getCMBadDeptExpAm() {
		return CMBadDeptExpAm;
	}
	public void setCMBadDeptExpAm(BigDecimal cMBadDeptExpAm) {
		CMBadDeptExpAm = cMBadDeptExpAm;
	}
	public int getCMNoOfBadDeptExp() {
		return CMNoOfBadDeptExp;
	}
	public void setCMNoOfBadDeptExp(int cMNoOfBadDeptExp) {
		CMNoOfBadDeptExp = cMNoOfBadDeptExp;
	}
	public BigDecimal getCMBadDeptClosedAm() {
		return CMBadDeptClosedAm;
	}
	public void setCMBadDeptClosedAm(BigDecimal cMBadDeptClosedAm) {
		CMBadDeptClosedAm = cMBadDeptClosedAm;
	}
	public int getCMNoOfBadDeptClosed() {
		return CMNoOfBadDeptClosed;
	}
	public void setCMNoOfBadDeptClosed(int cMNoOfBadDeptClosed) {
		CMNoOfBadDeptClosed = cMNoOfBadDeptClosed;
	}
	public BigDecimal getAMSavedAm() {
		return AMSavedAm;
	}
	public void setAMSavedAm(BigDecimal aMSavedAm) {
		AMSavedAm = aMSavedAm;
	}
	public BigDecimal getAMOutstandingSavedAm() {
		return AMOutstandingSavedAm;
	}
	public void setAMOutstandingSavedAm(BigDecimal aMOutstandingSavedAm) {
		AMOutstandingSavedAm = aMOutstandingSavedAm;
	}
	public BigDecimal getAMProvIntEnAm() {
		return AMProvIntEnAm;
	}
	public void setAMProvIntEnAm(BigDecimal aMProvIntEnAm) {
		AMProvIntEnAm = aMProvIntEnAm;
	}
	public BigDecimal getAMDividedDeclaredAm() {
		return AMDividedDeclaredAm;
	}
	public void setAMDividedDeclaredAm(BigDecimal aMDividedDeclaredAm) {
		AMDividedDeclaredAm = aMDividedDeclaredAm;
	}
	public BigDecimal getAMDividedPaidAm() {
		return AMDividedPaidAm;
	}
	public void setAMDividedPaidAm(BigDecimal aMDividedPaidAm) {
		AMDividedPaidAm = aMDividedPaidAm;
	}
	public BigDecimal getAMReturnedSavedAm() {
		return AMReturnedSavedAm;
	}
	public void setAMReturnedSavedAm(BigDecimal aMReturnedSavedAm) {
		AMReturnedSavedAm = aMReturnedSavedAm;
	}
	public BigDecimal getAMReturnedIntEnAm() {
		return AMReturnedIntEnAm;
	}
	public void setAMReturnedIntEnAm(BigDecimal aMReturnedIntEnAm) {
		AMReturnedIntEnAm = aMReturnedIntEnAm;
	}
	public BigDecimal getAMLoanAm() {
		return AMLoanAm;
	}
	public void setAMLoanAm(BigDecimal aMLoanAm) {
		AMLoanAm = aMLoanAm;
	}
	public BigDecimal getAMRecLoanAm() {
		return AMRecLoanAm;
	}
	public void setAMRecLoanAm(BigDecimal aMRecLoanAm) {
		AMRecLoanAm = aMRecLoanAm;
	}
	public BigDecimal getAMRecIntOnLoanAm() {
		return AMRecIntOnLoanAm;
	}
	public void setAMRecIntOnLoanAm(BigDecimal aMRecIntOnLoanAm) {
		AMRecIntOnLoanAm = aMRecIntOnLoanAm;
	}
	public BigDecimal getAMProjIntOnLoanAm() {
		return AMProjIntOnLoanAm;
	}
	public void setAMProjIntOnLoanAm(BigDecimal aMProjIntOnLoanAm) {
		AMProjIntOnLoanAm = aMProjIntOnLoanAm;
	}
	public BigDecimal getAMPlannedMonthlySaving() {
		return AMPlannedMonthlySaving;
	}
	public void setAMPlannedMonthlySaving(BigDecimal aMPlannedMonthlySaving) {
		AMPlannedMonthlySaving = aMPlannedMonthlySaving;
	}
	public int getAMNoOfLoans() {
		return AMNoOfLoans;
	}
	public void setAMNoOfLoans(int aMNoOfLoans) {
		AMNoOfLoans = aMNoOfLoans;
	}
	public int getAMNoOfActiveLoans() {
		return AMNoOfActiveLoans;
	}
	public void setAMNoOfActiveLoans(int aMNoOfActiveLoans) {
		AMNoOfActiveLoans = aMNoOfActiveLoans;
	}
	public BigDecimal getAMSubStdDeptAm() {
		return AMSubStdDeptAm;
	}
	public void setAMSubStdDeptAm(BigDecimal aMSubStdDeptAm) {
		AMSubStdDeptAm = aMSubStdDeptAm;
	}
	public int getAMNoOfSubStdDept() {
		return AMNoOfSubStdDept;
	}
	public void setAMNoOfSubStdDept(int aMNoOfSubStdDept) {
		AMNoOfSubStdDept = aMNoOfSubStdDept;
	}
	public BigDecimal getAMBadDeptExpAm() {
		return AMBadDeptExpAm;
	}
	public void setAMBadDeptExpAm(BigDecimal aMBadDeptExpAm) {
		AMBadDeptExpAm = aMBadDeptExpAm;
	}
	public int getAMNoOfBadDeptExp() {
		return AMNoOfBadDeptExp;
	}
	public void setAMNoOfBadDeptExp(int aMNoOfBadDeptExp) {
		AMNoOfBadDeptExp = aMNoOfBadDeptExp;
	}
	public BigDecimal getAMBadDeptClosedAm() {
		return AMBadDeptClosedAm;
	}
	public void setAMBadDeptClosedAm(BigDecimal aMBadDeptClosedAm) {
		AMBadDeptClosedAm = aMBadDeptClosedAm;
	}
	public int getAMNoOfBadDeptClosed() {
		return AMNoOfBadDeptClosed;
	}
	public void setAMNoOfBadDeptClosed(int aMNoOfBadDeptClosed) {
		AMNoOfBadDeptClosed = aMNoOfBadDeptClosed;
	}
	public BigDecimal getPLoanAm() {
		return PLoanAm;
	}
	public void setPLoanAm(BigDecimal pLoanAm) {
		PLoanAm = pLoanAm;
	}
	public BigDecimal getPRecLoanAm() {
		return PRecLoanAm;
	}
	public void setPRecLoanAm(BigDecimal pRecLoanAm) {
		PRecLoanAm = pRecLoanAm;
	}
	public BigDecimal getPRecIntOnLoanAm() {
		return PRecIntOnLoanAm;
	}
	public void setPRecIntOnLoanAm(BigDecimal pRecIntOnLoanAm) {
		PRecIntOnLoanAm = pRecIntOnLoanAm;
	}
	public BigDecimal getPProjIntOnLoanAm() {
		return PProjIntOnLoanAm;
	}
	public void setPProjIntOnLoanAm(BigDecimal pProjIntOnLoanAm) {
		PProjIntOnLoanAm = pProjIntOnLoanAm;
	}
	public int getNoOfProject() {
		return noOfProject;
	}
	public void setNoOfProject(int noOfProject) {
		this.noOfProject = noOfProject;
	}
	public int getNoOfActiveProject() {
		return noOfActiveProject;
	}
	public void setNoOfActiveProject(int noOfActiveProject) {
		this.noOfActiveProject = noOfActiveProject;
	}
	public BigDecimal getFixDepositInvAm() {
		return fixDepositInvAm;
	}
	public void setFixDepositInvAm(BigDecimal fixDepositInvAm) {
		this.fixDepositInvAm = fixDepositInvAm;
	}
	public BigDecimal getRecFixDepositAm() {
		return recFixDepositAm;
	}
	public void setRecFixDepositAm(BigDecimal recFixDepositAm) {
		this.recFixDepositAm = recFixDepositAm;
	}
	public BigDecimal getRecIntOnFixDepositAm() {
		return recIntOnFixDepositAm;
	}
	public void setRecIntOnFixDepositAm(BigDecimal recIntOnFixDepositAm) {
		this.recIntOnFixDepositAm = recIntOnFixDepositAm;
	}
	public BigDecimal getProjIntOnFixDepositAm() {
		return projIntOnFixDepositAm;
	}
	public void setProjIntOnFixDepositAm(BigDecimal projIntOnFixDepositAm) {
		this.projIntOnFixDepositAm = projIntOnFixDepositAm;
	}
	public int getNoOfFixDeposit() {
		return noOfFixDeposit;
	}
	public void setNoOfFixDeposit(int noOfFixDeposit) {
		this.noOfFixDeposit = noOfFixDeposit;
	}
	public int getNoOfActiveFixDeposit() {
		return noOfActiveFixDeposit;
	}
	public void setNoOfActiveFixDeposit(int noOfActiveFixDeposit) {
		this.noOfActiveFixDeposit = noOfActiveFixDeposit;
	}
	public BigDecimal getOtherInvAm() {
		return otherInvAm;
	}
	public void setOtherInvAm(BigDecimal otherInvAm) {
		this.otherInvAm = otherInvAm;
	}
	public BigDecimal getRecOtherInvAm() {
		return recOtherInvAm;
	}
	public void setRecOtherInvAm(BigDecimal recOtherInvAm) {
		this.recOtherInvAm = recOtherInvAm;
	}
	public BigDecimal getRecIntOnOtherInvAm() {
		return recIntOnOtherInvAm;
	}
	public void setRecIntOnOtherInvAm(BigDecimal recIntOnOtherInvAm) {
		this.recIntOnOtherInvAm = recIntOnOtherInvAm;
	}
	public BigDecimal getProjIntOnOtherInvAm() {
		return projIntOnOtherInvAm;
	}
	public void setProjIntOnOtherInvAm(BigDecimal projIntOnOtherInvAm) {
		this.projIntOnOtherInvAm = projIntOnOtherInvAm;
	}
	public int getNoOfOtherInv() {
		return noOfOtherInv;
	}
	public void setNoOfOtherInv(int noOfOtherInv) {
		this.noOfOtherInv = noOfOtherInv;
	}
	public int getNoOfActiveOtherInv() {
		return noOfActiveOtherInv;
	}
	public void setNoOfActiveOtherInv(int noOfActiveOtherInv) {
		this.noOfActiveOtherInv = noOfActiveOtherInv;
	}
	public BigDecimal getIntEnOnSavingAcAm() {
		return intEnOnSavingAcAm;
	}
	public void setIntEnOnSavingAcAm(BigDecimal intEnOnSavingAcAm) {
		this.intEnOnSavingAcAm = intEnOnSavingAcAm;
	}
	public BigDecimal getBankBalanceAm() {
		return bankBalanceAm;
	}
	public void setBankBalanceAm(BigDecimal bankBalanceAm) {
		this.bankBalanceAm = bankBalanceAm;
	}
	public BigDecimal getCashInHandAm() {
		return cashInHandAm;
	}
	public void setCashInHandAm(BigDecimal cashInHandAm) {
		this.cashInHandAm = cashInHandAm;
	}
	public BigDecimal getSubjClearingBankBalanceAm() {
		return subjClearingBankBalanceAm;
	}
	public void setSubjClearingBankBalanceAm(BigDecimal subjClearingBankBalanceAm) {
		this.subjClearingBankBalanceAm = subjClearingBankBalanceAm;
	}
	public BigDecimal getSubjClearingCashInHandAm() {
		return subjClearingCashInHandAm;
	}
	public void setSubjClearingCashInHandAm(BigDecimal subjClearingCashInHandAm) {
		this.subjClearingCashInHandAm = subjClearingCashInHandAm;
	}
	public BigDecimal getNetProfitAm() {
		return netProfitAm;
	}
	public void setNetProfitAm(BigDecimal netProfitAm) {
		this.netProfitAm = netProfitAm;
	}
	public BigDecimal getNetProfitProjAm() {
		return netProfitProjAm;
	}
	public void setNetProfitProjAm(BigDecimal netProfitProjAm) {
		this.netProfitProjAm = netProfitProjAm;
	}
	public BigDecimal getExpensesAm() {
		return expensesAm;
	}
	public void setExpensesAm(BigDecimal expensesAm) {
		this.expensesAm = expensesAm;
	}
	public BigDecimal getOutstandingExpensesAm() {
		return outstandingExpensesAm;
	}
	public void setOutstandingExpensesAm(BigDecimal outstandingExpensesAm) {
		this.outstandingExpensesAm = outstandingExpensesAm;
	}
	public BigDecimal getPenaltyAm() {
		return penaltyAm;
	}
	public void setPenaltyAm(BigDecimal penaltyAm) {
		this.penaltyAm = penaltyAm;
	}
	public BigDecimal getBorrowedLoanAm() {
		return borrowedLoanAm;
	}
	public void setBorrowedLoanAm(BigDecimal borrowedLoanAm) {
		this.borrowedLoanAm = borrowedLoanAm;
	}
	public BigDecimal getPaidBorrowedLoanAm() {
		return paidBorrowedLoanAm;
	}
	public void setPaidBorrowedLoanAm(BigDecimal paidBorrowedLoanAm) {
		this.paidBorrowedLoanAm = paidBorrowedLoanAm;
	}
	public BigDecimal getPaidIntOnBorrowedLoanAm() {
		return paidIntOnBorrowedLoanAm;
	}
	public void setPaidIntOnBorrowedLoanAm(BigDecimal paidIntOnBorrowedLoanAm) {
		this.paidIntOnBorrowedLoanAm = paidIntOnBorrowedLoanAm;
	}
	public BigDecimal getProjIntOnBorrowedLoanAm() {
		return projIntOnBorrowedLoanAm;
	}
	public void setProjIntOnBorrowedLoanAm(BigDecimal projIntOnBorrowedLoanAm) {
		this.projIntOnBorrowedLoanAm = projIntOnBorrowedLoanAm;
	}
	public BigDecimal getBankSubStdDeptAm() {
		return bankSubStdDeptAm;
	}
	public void setBankSubStdDeptAm(BigDecimal bankSubStdDeptAm) {
		this.bankSubStdDeptAm = bankSubStdDeptAm;
	}
	public int getBankNoOfSubStdDept() {
		return bankNoOfSubStdDept;
	}
	public void setBankNoOfSubStdDept(int bankNoOfSubStdDept) {
		this.bankNoOfSubStdDept = bankNoOfSubStdDept;
	}
	public BigDecimal getBankBadDeptExpAm() {
		return bankBadDeptExpAm;
	}
	public void setBankBadDeptExpAm(BigDecimal bankBadDeptExpAm) {
		this.bankBadDeptExpAm = bankBadDeptExpAm;
	}
	public int getBankNoOfBadDeptExp() {
		return bankNoOfBadDeptExp;
	}
	public void setBankNoOfBadDeptExp(int bankNoOfBadDeptExp) {
		this.bankNoOfBadDeptExp = bankNoOfBadDeptExp;
	}
	public BigDecimal getBankBadDeptClosedAm() {
		return bankBadDeptClosedAm;
	}
	public void setBankBadDeptClosedAm(BigDecimal bankBadDeptClosedAm) {
		this.bankBadDeptClosedAm = bankBadDeptClosedAm;
	}
	public int getBankNoOfBadDeptClosed() {
		return bankNoOfBadDeptClosed;
	}
	public void setBankNoOfBadDeptClosed(int bankNoOfBadDeptClosed) {
		this.bankNoOfBadDeptClosed = bankNoOfBadDeptClosed;
	}
	public int getNoOfBankLoan() {
		return noOfBankLoan;
	}
	public void setNoOfBankLoan(int noOfBankLoan) {
		this.noOfBankLoan = noOfBankLoan;
	}
	public int getNoOfActiveBankLoan() {
		return noOfActiveBankLoan;
	}
	public void setNoOfActiveBankLoan(int noOfActiveBankLoan) {
		this.noOfActiveBankLoan = noOfActiveBankLoan;
	}
	public BigDecimal getGovRevolvingFundAm() {
		return govRevolvingFundAm;
	}
	public void setGovRevolvingFundAm(BigDecimal govRevolvingFundAm) {
		this.govRevolvingFundAm = govRevolvingFundAm;
	}
	public BigDecimal getGovRevolvingFundReturnedAm() {
		return govRevolvingFundReturnedAm;
	}
	public void setGovRevolvingFundReturnedAm(BigDecimal govRevolvingFundReturnedAm) {
		this.govRevolvingFundReturnedAm = govRevolvingFundReturnedAm;
	}
	public int getNoOfGovRevolvingFund() {
		return noOfGovRevolvingFund;
	}
	public void setNoOfGovRevolvingFund(int noOfGovRevolvingFund) {
		this.noOfGovRevolvingFund = noOfGovRevolvingFund;
	}
	public int getNoOfActiveGovRevolvingFund() {
		return noOfActiveGovRevolvingFund;
	}
	public void setNoOfActiveGovRevolvingFund(int noOfActiveGovRevolvingFund) {
		this.noOfActiveGovRevolvingFund = noOfActiveGovRevolvingFund;
	}
	public BigDecimal getGovDevelopmentFundAm() {
		return govDevelopmentFundAm;
	}
	public void setGovDevelopmentFundAm(BigDecimal govDevelopmentFundAm) {
		this.govDevelopmentFundAm = govDevelopmentFundAm;
	}
	public int getNoOfGovDevelopmentFund() {
		return noOfGovDevelopmentFund;
	}
	public void setNoOfGovDevelopmentFund(int noOfGovDevelopmentFund) {
		this.noOfGovDevelopmentFund = noOfGovDevelopmentFund;
	}
	public BigDecimal getPenShgOneMemRegFee() {
		return penShgOneMemRegFee;
	}
	public void setPenShgOneMemRegFee(BigDecimal penShgOneMemRegFee) {
		this.penShgOneMemRegFee = penShgOneMemRegFee;
	}
	public BigDecimal getPenShgOneServiceCharges() {
		return penShgOneServiceCharges;
	}
	public void setPenShgOneServiceCharges(BigDecimal penShgOneServiceCharges) {
		this.penShgOneServiceCharges = penShgOneServiceCharges;
	}
	public int getNoOfTxsMonthlyExp() {
		return noOfTxsMonthlyExp;
	}
	public void setNoOfTxsMonthlyExp(int noOfTxsMonthlyExp) {
		this.noOfTxsMonthlyExp = noOfTxsMonthlyExp;
	}
	public int getNoOfTxsMonthlyDone() {
		return noOfTxsMonthlyDone;
	}
	public void setNoOfTxsMonthlyDone(int noOfTxsMonthlyDone) {
		this.noOfTxsMonthlyDone = noOfTxsMonthlyDone;
	}
	public int getNoOfTxsMonthlyApproved() {
		return noOfTxsMonthlyApproved;
	}
	public void setNoOfTxsMonthlyApproved(int noOfTxsMonthlyApproved) {
		this.noOfTxsMonthlyApproved = noOfTxsMonthlyApproved;
	}

}
