package com.beone.shg.net.persistence.bo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.bo.util.RestDisplayTitle;
import com.beone.shg.net.persistence.job.DataFeildsUpdateJob;
import com.beone.shg.net.persistence.job.JobQueueManager;
import com.beone.shg.net.persistence.model.BankProfile;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.LoanEMIPlan;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.resp.Transactions;
import com.beone.shg.net.webservice.rest.model.rest.GroupLoanAcREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("groupLoanAcBO")
public class GroupLoanAcBO extends BaseBO {

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;

	@Autowired
	@Qualifier("attachmentBO")
	private AttachmentBO attachmentBO;

	public GroupLoanAcREST add(GroupLoanAcREST accountREST) throws BadRequestException {
		return add(accountREST, false);
	}
	
	public GroupLoanAcREST add(GroupLoanAcREST accountREST, boolean oldData) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}
		if(accountREST.getBankGroupAcNo() <= 0 && 
				daoFactory.getGProfileDAO().getReferenceById(accountREST.getBankGroupAcNo()) != null) {
			throw new BadRequestException("Invalid Bank Group Ac No: " + accountREST.getBankGroupAcNo());
		}
		if(accountREST.getInstallment().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
			throw new BadRequestException("Invalid Installment Amount");
		}
		if(accountREST.getRecoveryPeriod() == null || accountREST.getRecoveryPeriod().isEmpty()) {
			throw new BadRequestException("Invalid Recovery Period");
		}
		if(accountREST.getLoanType() == null || accountREST.getLoanType().isEmpty()) {
			throw new BadRequestException("Invalid Loan Type");
		}
		if(accountREST.getLoanCalculation() == null || accountREST.getLoanCalculation().isEmpty()) {
			throw new BadRequestException("Invalid Loan Calculation");
		}
		if(accountREST.getLoanSource() == null || accountREST.getLoanSource().isEmpty()) {
			accountREST.setLoanSource(EnumConst.LoanSource_SHG);
		}

		GLoanAc account = new GLoanAc();		
		account.setGAc(daoFactory.getGAcDAO().getReferenceById(accountREST.getGroupAcNo()));
		account.setBGroupAcNo(accountREST.getBankGroupAcNo());
		
		if(accountREST.getLoanBankAcNo() > 0 && daoFactory.getGBankAccountDAO().getReferenceById(accountREST.getLoanBankAcNo()) != null) {
			account.setLoanBankAcNo(accountREST.getLoanBankAcNo());
		}
				
		if(oldData) {
			if(accountREST.getAccountStatus() == null ||
					accountREST.getAccountStatus().isEmpty() ||
					EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()) <= 0) {
				throw new BadRequestException("Invalid Account Status: " + accountREST.getAccountStatus());
			}

			account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()));
			addOldAccount(account, accountREST);
		} else {
			account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Submitted));
			accountREST.setAccountStatus(EnumConst.AccountStatus_Submitted);
			addNewAccount(account, accountREST);
		}
		
		daoFactory.getGLoanAcDAO().persist(account);
		
		// Load Account ID back
		accountREST.setGroupLoanAcNo(account.getGLoanAcNo());
		
		return accountREST;
	}
	
	protected void addOldAccount(GLoanAc account, GroupLoanAcREST accountREST) throws BadRequestException {

		int recoveryPeriodId = EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, accountREST.getRecoveryPeriod());
		if(recoveryPeriodId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Recovery Period: " + accountREST.getRecoveryPeriod());
		}
		int loanTypeId = EnumCache.getIndexOfFundType(accountREST.getLoanType());
		if(loanTypeId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Loan Type: " + accountREST.getLoanType());
		}
		int loanCalculationId = EnumCache.getIndexOfEnumValue(EnumConst.LoanCalculation, accountREST.getLoanCalculation());
		if(loanCalculationId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Loan Calculation: " + accountREST.getLoanCalculation());
		}
				
		account.setRecoveryPeriodId(recoveryPeriodId);
		account.setLoanSourceId(EnumCache.getIndexOfEnumValue(EnumConst.LoanSource, accountREST.getLoanSource()));
		account.setLoanAcName(accountREST.getLoanAcName());
		account.setFundTypeId(loanTypeId);
		account.setLoanCalculationId(loanCalculationId);
		account.setPrincipleAm(accountREST.getPrinciple());
		account.setPendingPrincipleAm(accountREST.getPendingPrinciple());
		account.setPaidInterestAm(accountREST.getPaidInterest());
		account.setProjInterestAm(accountREST.getProjectedInterest());
		account.setInstallmentAm(accountREST.getInstallment());
		account.setPreEmiInterestAm(accountREST.getPreEmiInterest());
		account.setPendingInterestDueAm(accountREST.getPendingInterestDue());
		account.setLoanProcessingFee(accountREST.getLoanProcessingFee());
		account.setOtherFee(accountREST.getOtherFee());
		account.setInterestRate(accountREST.getRateOfInterest());
		account.setStartupNoOfInst(accountREST.getStartupNoOfInst());
		account.setExpNoOfInst(accountREST.getExpNoOfInst());
		account.setNoOfInstPaid(accountREST.getNoOfInstPaid());
		account.setNoOfInsallLate(accountREST.getNoOfInsallLate());
		account.setNoOfInsallMissed(accountREST.getNoOfInsallMissed());
		account.setRequestedDate(DateUtil.convertStringToDate(accountREST.getRequestedDate()));
		account.setApprovedDate(DateUtil.convertStringToDate(accountREST.getApprovedDate()));
		account.setDisbursementDate(DateUtil.convertStringToDate(accountREST.getDisbursementDate()));
		account.setInstStartDate(DateUtil.convertStringToDate(accountREST.getInstStartDate()));
		account.setExpCompletionDate(DateUtil.convertStringToDate(accountREST.getExpCompletionDate()));
		account.setClosureDate(DateUtil.convertStringToDate(accountREST.getClosureDate()));
		account.setDevelopmentPlan(accountREST.getDevelopmentPlan());
		account.setDescription(accountREST.getStatusMessage());
	}
	
	protected void addNewAccount(GLoanAc account, GroupLoanAcREST accountREST) throws BadRequestException {

		int recoveryPeriodId = EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, accountREST.getRecoveryPeriod());
		if(recoveryPeriodId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Recovery Period: " + accountREST.getRecoveryPeriod());
		}
		int loanTypeId = EnumCache.getIndexOfFundType(accountREST.getLoanType());
		if(loanTypeId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Loan Type: " + accountREST.getLoanType());
		}
		int loanCalculationId = EnumCache.getIndexOfEnumValue(EnumConst.LoanCalculation, accountREST.getLoanCalculation());
		if(loanCalculationId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Loan Calculation: " + accountREST.getLoanCalculation());
		}
		
		LoanEMIPlan plan = new LoanEMIPlan();
		plan.setLoanCalculation(accountREST.getLoanCalculation());
		plan.setRateOfInterest(accountREST.getRateOfInterest());
		plan.setPrinciple(accountREST.getPrinciple());
		plan.setFixedEMI(accountREST.getInstallment());
		plan.setNoOfEMIs(accountREST.getExpNoOfInst());
		plan.setStartDate(accountREST.getRequestedDate());
		plan.setNoOfStartUpEMI(accountREST.getStartupNoOfInst());
		plan.setDueDay(10);
		plan = getLoanPlanning(plan);
				
		account.setRecoveryPeriodId(recoveryPeriodId);
		account.setLoanSourceId(EnumCache.getIndexOfEnumValue(EnumConst.LoanSource, accountREST.getLoanSource()));
		account.setLoanAcName(accountREST.getLoanAcName());
		account.setFundTypeId(loanTypeId);
		account.setLoanCalculationId(loanCalculationId);
		account.setPrincipleAm(accountREST.getPrinciple());
		account.setPendingPrincipleAm(accountREST.getPrinciple());
		account.setPaidInterestAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setProjInterestAm(plan.getTotalInterest());
		account.setInstallmentAm(plan.getFixedEMI());
		account.setPreEmiInterestAm(plan.getPreEMI());
		account.setPendingInterestDueAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setLoanProcessingFee(accountREST.getLoanProcessingFee());
		account.setOtherFee(accountREST.getOtherFee());
		account.setInterestRate(accountREST.getRateOfInterest());
		account.setStartupNoOfInst(accountREST.getStartupNoOfInst());
		account.setExpNoOfInst(plan.getNoOfEMIs());
		account.setNoOfInstPaid(DataUtil.ZERO_INTEGER);
		account.setNoOfInsallLate(DataUtil.ZERO_INTEGER);
		account.setNoOfInsallMissed(DataUtil.ZERO_INTEGER);
		account.setRequestedDate(DateUtil.convertStringToDate(accountREST.getRequestedDate()));
		account.setInstStartDate(DateUtil.convertStringToDate(plan.getStartDate()));
		account.setExpCompletionDate(DateUtil.convertStringToDate(plan.getExpCompletionDate()));
		account.setDevelopmentPlan(accountREST.getDevelopmentPlan());
		account.setDescription(accountREST.getStatusMessage());
		
		// Return Info Updated
		accountREST.setPendingPrinciple(accountREST.getPendingPrinciple());
		accountREST.setPaidInterest(DataUtil.ZERO_BIG_DECIMAL);
		accountREST.setProjectedInterest(plan.getTotalInterest());
		accountREST.setPreEmiInterest(accountREST.getPreEmiInterest());
		accountREST.setPendingInterestDue(DataUtil.ZERO_BIG_DECIMAL);
	}

	public GroupLoanAcREST approveReject(GroupLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Type");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}
		if(accountREST.getApprovedByMember() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Approved By Member Account");
		}
		if(accountREST.getAccountStatus() == null ||
				accountREST.getAccountStatus().isEmpty() ||
				EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()) <= 0) {
			throw new BadRequestException("Invalid Account Status: " + accountREST.getAccountStatus());
		}
		
		if(!EnumUtil.isAccountStatusApproveReject(accountREST.getAccountStatus())) {
			throw new BadRequestException("Invalid Requested Account Status: " + accountREST.getAccountStatus() + " ; Expected Approve or Reject");
		}
		int accountStatusId = EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus());
		if(accountStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Account Status:" + accountREST.getAccountStatus());
		}

		GLoanAc account = daoFactory.getGLoanAcDAO().findById(accountREST.getGroupLoanAcNo());
		
		if(!EnumUtil.isAccountStatusApprovable(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()))) {
			throw new BadRequestException("Can't Update Current Loan Account Status: " + 
					EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}
		
		account.setAccountStatusId(accountStatusId);
		account.setApprovedByMember(accountREST.getApprovedByMember());
		account.setApprovedDate(DateUtil.convertStringToDateWithCurrentDefault(accountREST.getApprovedDate()));

		if(accountREST.getDevelopmentPlan() != null && !accountREST.getDevelopmentPlan().isEmpty()) {
			account.setDevelopmentPlan(accountREST.getDevelopmentPlan());
		}
		if(accountREST.getStatusMessage() != null && !accountREST.getStatusMessage().isEmpty()) {
			account.setDescription(accountREST.getStatusMessage());
		}
		
		daoFactory.getGLoanAcDAO().merge(account);
		
		return accountREST;
	}

	public GroupLoanAcREST activate(GroupLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Account");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}
		
		GLoanAc account = daoFactory.getGLoanAcDAO().findById(accountREST.getGroupLoanAcNo());
		
		if(account == null) {
			throw new BadRequestException("Invalid Loan Account: " + accountREST.getGroupLoanAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Approved)) {
			throw new BadRequestException("Account status Not 'Approved' it is : " + 
					EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}
		
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Active));
		accountREST.setAccountStatus(EnumConst.AccountStatus_Active);

		if(accountREST.getDevelopmentPlan() != null && !accountREST.getDevelopmentPlan().isEmpty()) {
			account.setDevelopmentPlan(accountREST.getDevelopmentPlan());
		}
		if(accountREST.getStatusMessage() != null && !accountREST.getStatusMessage().isEmpty()) {
			account.setDescription(accountREST.getStatusMessage());
		}
		
		daoFactory.getGLoanAcDAO().merge(account);
		
		// Update Group Account
		GAc gAc = daoFactory.getGAcDAO().findById(accountREST.getGroupAcNo());
		if(gAc == null) {
			throw new BadRequestException("Invalid Group Account");
		}
		gAc.setNoOfBankLoan(gAc.getNoOfBankLoan() + 1);
		gAc.setNoOfActiveBankLoan(gAc.getNoOfActiveBankLoan() + 1);
		gAc.setProjIntOnBorrowedLoanAm(BDUtil.add(gAc.getProjIntOnBorrowedLoanAm(), accountREST.getProjectedInterest()));
		daoFactory.getGAcDAO().merge(gAc);
		
		// Do Transactions for Activation
		if(accountREST.getTransactions() != null && !accountREST.getTransactions().isEmpty()) {
			transactionBO.addTransactions(accountREST.getTransactions());
		}

		return accountREST;
	}

	public Transactions getTxsToActivate(GroupLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Account");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}

		GLoanAc account = daoFactory.getGLoanAcDAO().findById(accountREST.getGroupLoanAcNo());

		if(account == null) {
			throw new BadRequestException("Invalid Loan Account: " + accountREST.getGroupLoanAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Approved)) {
			throw new BadRequestException("Account status Not 'Approved' it is : " + 
					EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}

		BigDecimal processingFee = accountREST.getLoanProcessingFee();
		BigDecimal applicationFee = accountREST.getOtherFee();
		BigDecimal netAmount = BDUtil.sub(accountREST.getPrinciple(), BDUtil.add(processingFee, BDUtil.add(accountREST.getPreEmiInterest(), applicationFee)));

		Transactions transactions = new Transactions();

		if(processingFee.compareTo(DataUtil.ZERO_BIG_DECIMAL) != DataUtil.ZERO_INTEGER) {
			// Adding Loan_Processing_Fee
			Transaction tx1 = new Transaction();
			tx1.setTxType(EnumConst.TxType_Bank_Charges_Expense);
			tx1.setSlipType(EnumConst.SlipType_VOUCHER);
			tx1.setGroupLoanAcNo(account.getGLoanAcNo());
			tx1.setGroupAcNo(account.getGAc().getGAcNo());
			tx1.setExternalGroupAcNo(account.getBGroupAcNo());
			tx1.setExternalGroupBankAcNo(account.getLoanBankAcNo());
			tx1.setTxAcType(EnumConst.TxAcType_Group_Loan);
			tx1.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
			tx1.setDescription("Loan Processing Fee for Fund No : " + account.getGLoanAcNo());
			tx1.setAmount(processingFee);
			transactions.addTransaction(tx1);
		}

		if(applicationFee.compareTo(DataUtil.ZERO_BIG_DECIMAL) != DataUtil.ZERO_INTEGER) {
			// Adding Application_Fee
			Transaction tx11 = new Transaction();
			tx11.setTxType(EnumConst.TxType_Bank_Charges_Expense);
			tx11.setSlipType(EnumConst.SlipType_VOUCHER);
			tx11.setGroupLoanAcNo(account.getGLoanAcNo());
			tx11.setGroupAcNo(account.getGAc().getGAcNo());
			tx11.setExternalGroupAcNo(account.getBGroupAcNo());
			tx11.setExternalGroupBankAcNo(account.getLoanBankAcNo());
			tx11.setTxAcType(EnumConst.TxAcType_Group_Loan);
			tx11.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
			tx11.setDescription("Application Fee for Fund No : " + account.getGLoanAcNo());
			tx11.setAmount(applicationFee);
			transactions.addTransaction(tx11);
		}
		
		if(accountREST.getPreEmiInterest().compareTo(DataUtil.ZERO_BIG_DECIMAL) != DataUtil.ZERO_INTEGER) {
			// Adding Loan_Disbursement
			Transaction tx2 = new Transaction();
			tx2.setTxType(EnumConst.TxType_Bank_Pre_Interest_Installment);
			tx2.setSlipType(EnumConst.SlipType_VOUCHER);
			tx2.setGroupLoanAcNo(account.getGLoanAcNo());
			tx2.setGroupAcNo(account.getGAc().getGAcNo());
			tx2.setExternalGroupAcNo(account.getBGroupAcNo());
			tx2.setExternalGroupBankAcNo(account.getLoanBankAcNo());
			tx2.setTxAcType(EnumConst.TxAcType_Group_Loan);
			tx2.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
			tx2.setDescription("Pre EMI Interest");
			tx2.setAmount(accountREST.getPreEmiInterest());
			transactions.addTransaction(tx2);
		}
		
		// Adding Loan_Disbursement
		Transaction tx3 = new Transaction();
		tx3.setTxType(EnumConst.TxType_Bank_Loan_Disbursement);
		tx3.setSlipType(EnumConst.SlipType_RECEIPT);
		tx3.setGroupLoanAcNo(account.getGLoanAcNo());
		tx3.setGroupAcNo(account.getGAc().getGAcNo());
		tx3.setExternalGroupAcNo(account.getBGroupAcNo());
		tx3.setExternalGroupBankAcNo(account.getLoanBankAcNo());
		tx3.setTxAcType(EnumConst.TxAcType_Group_Loan);
		tx3.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
		tx3.setDescription(netAmount + " - Net Loan Disbursement after Adjustments");
		tx3.setAmount(accountREST.getPrinciple());
		transactions.addTransaction(tx3);
		
		transactions.setGroupBankAcNos(daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(accountREST.getGroupAcNo()));
		transactions.setDisplayNames(RestDisplayTitle.getTransactionRDT());
		
		return transactions;
	}

	public GroupLoanAcREST close(GroupLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Account");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}
		
		GLoanAc account = daoFactory.getGLoanAcDAO().findById(accountREST.getGroupLoanAcNo());		
		if(account == null) {
			throw new BadRequestException("Invalid Loan Account: " + accountREST.getGroupLoanAcNo());
		}
		String currentStatus = EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId());
		if(!EnumUtil.isAccountStatusToClosure(currentStatus)) {
			throw new BadRequestException("Account status Not 'Active' it is : " + 
					EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}

		int accountStatusId = -1;
		
		switch(currentStatus) {
		case EnumConst.AccountStatus_Active:
		case EnumConst.AccountStatus_Sub_Standard:
			accountStatusId = EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Foreclosed);
			break;
			
		case EnumConst.AccountStatus_Bad_Debt:
			accountStatusId = EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Bad_Debt_Closed);
			break;
			
		}
	
		if(accountStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Requested Investment Account Status: " + accountREST.getAccountStatus());
		}			
		account.setAccountStatusId(accountStatusId);

		if(accountREST.getStatusMessage() != null && !accountREST.getStatusMessage().isEmpty()) {
			account.setDescription(accountREST.getStatusMessage());
		}
		
		daoFactory.getGLoanAcDAO().merge(account);
		
		if(accountREST.getTransactions() != null) {
			transactionBO.addTransactions(accountREST.getTransactions());
		}

		return accountREST;
	}

	public Transactions getTxsToClose(GroupLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Account");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}

		GLoanAc account = daoFactory.getGLoanAcDAO().findById(accountREST.getGroupLoanAcNo());

		if(account == null) {
			throw new BadRequestException("Invalid Loan Account: " + accountREST.getGroupLoanAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Active)) {
			throw new BadRequestException("Account status Not 'Active' it is : " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}
		BankProfile bankProfile = daoFactory.getBankProfileDAO().findById(account.getBGroupAcNo());
		BigDecimal prepaymentCharges = DataUtil.ZERO_BIG_DECIMAL;
		if(bankProfile != null) {
			prepaymentCharges = new BigDecimal(bankProfile.getLoanPrepaymentCharges());
		}

		Transactions transactions = new Transactions();

		if(prepaymentCharges.compareTo(account.getPaidInterestAm()) != DataUtil.ZERO_INTEGER) {
			// Adding Expected Min Interest Payment
			Transaction tx1 = new Transaction();
			tx1.setTxType(EnumConst.TxType_Bank_Charges_Expense);
			tx1.setSlipType(EnumConst.SlipType_RECEIPT);
			tx1.setGroupLoanAcNo(account.getGLoanAcNo());
			tx1.setGroupAcNo(account.getGAc().getGAcNo());
			tx1.setExternalGroupAcNo(account.getBGroupAcNo());
			tx1.setExternalGroupBankAcNo(account.getLoanBankAcNo());
			tx1.setTxAcType(EnumConst.TxAcType_Group_Loan);
			tx1.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
			tx1.setDescription("Expected Min Interest Payment");
			tx1.setAmount(prepaymentCharges);
			transactions.addTransaction(tx1);
		}

		// Adding Outstanding Loan Principle Payment
		Transaction tx2 = new Transaction();
		tx2.setTxType(EnumConst.TxType_Bank_Loan_Prepayment);
		tx2.setSlipType(EnumConst.SlipType_RECEIPT);
		tx2.setGroupLoanAcNo(account.getGLoanAcNo());
		tx2.setGroupAcNo(account.getGAc().getGAcNo());
		tx2.setExternalGroupAcNo(account.getBGroupAcNo());
		tx2.setExternalGroupBankAcNo(account.getLoanBankAcNo());
		tx2.setTxAcType(EnumConst.TxAcType_Group_Loan);
		tx2.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
		tx2.setDescription("Outstanding Loan Pre Payment");
		tx2.setAmount(account.getPendingPrincipleAm());
		transactions.addTransaction(tx2);
		
		transactions.setGroupBankAcNos(daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(accountREST.getGroupAcNo()));
		transactions.setDisplayNames(RestDisplayTitle.getTransactionRDT());
		
		return transactions;
	}
	
	public GroupLoanAcREST updateGroupLoanAc(GroupLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupLoanAcNo() <= 0) {
			throw new BadRequestException("Invalid Group Installment Account No");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GLoanAc account = daoFactory.getGLoanAcDAO().findById(accountREST.getGroupLoanAcNo());
		
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Active));
		
		if(accountREST.getRecoveryPeriod() != null && !accountREST.getRecoveryPeriod().isEmpty()) {
			account.setRecoveryPeriodId(EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, accountREST.getRecoveryPeriod()));
		}
		if(accountREST.getLoanBankAcNo() > 0 && daoFactory.getGBankAccountDAO().getReferenceById(accountREST.getLoanBankAcNo()) != null) {
			account.setLoanBankAcNo(accountREST.getLoanBankAcNo());
		}
		
		if(accountREST.getInstallment() != null) {
			account.setInstallmentAm(accountREST.getInstallment());
		}
		if(accountREST.getLoanProcessingFee() != null) {
			account.setLoanProcessingFee(accountREST.getLoanProcessingFee());
		}
		if(accountREST.getOtherFee() != null) {
			account.setOtherFee(accountREST.getOtherFee());
		}
		if(accountREST.getExpNoOfInst() > 0) {
			account.setExpNoOfInst(accountREST.getExpNoOfInst());
		}
		if(accountREST.getDevelopmentPlan() != null && !accountREST.getDevelopmentPlan().isEmpty()) {
			account.setDevelopmentPlan(accountREST.getDevelopmentPlan());
		}
		if(accountREST.getStatusMessage() != null && !accountREST.getStatusMessage().isEmpty()) {
			account.setDescription(accountREST.getStatusMessage());
		}
		
		daoFactory.getGLoanAcDAO().merge(account);
		
		return accountREST;
	}
	
	public GroupLoanAcREST updateGroupLoanAcData(GroupLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupLoanAcNo() <= 0) {
			throw new BadRequestException("Invalid Group Installment Account No");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GLoanAc account = daoFactory.getGLoanAcDAO().findById(accountREST.getGroupLoanAcNo());
		
		account.setFundTypeId(EnumCache.getIndexOfFundType(accountREST.getLoanType()));
		account.setLoanCalculationId(EnumCache.getIndexOfEnumValue(EnumConst.LoanCalculation, accountREST.getLoanCalculation()));
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()));
		account.setRecoveryPeriodId(EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, accountREST.getRecoveryPeriod()));
		account.setLoanSourceId(EnumCache.getIndexOfEnumValue(EnumConst.LoanSource, accountREST.getLoanSource()));
		if(accountREST.getApprovedByMember() > DataUtil.ZERO_LONG && 
				daoFactory.getMProfileDAO().getReferenceById(accountREST.getApprovedByMember()) != null) {
			account.setApprovedByMember(accountREST.getApprovedByMember());
		}
		account.setPrincipleAm(accountREST.getPrinciple());
		account.setPendingPrincipleAm(accountREST.getPendingPrinciple());
		account.setPaidInterestAm(accountREST.getPaidInterest());
		account.setProjInterestAm(accountREST.getProjectedInterest());
		account.setInstallmentAm(accountREST.getInstallment());
		account.setPreEmiInterestAm(accountREST.getPreEmiInterest());
		account.setPendingInterestDueAm(accountREST.getPendingInterestDue());
		account.setLoanProcessingFee(accountREST.getLoanProcessingFee());
		account.setOtherFee(accountREST.getOtherFee());
		account.setInterestRate(accountREST.getRateOfInterest());
		account.setStartupNoOfInst(accountREST.getStartupNoOfInst());
		account.setExpNoOfInst(accountREST.getExpNoOfInst());
		account.setNoOfInstPaid(accountREST.getNoOfInstPaid());
		account.setNoOfInsallLate(accountREST.getNoOfInsallLate());
		account.setNoOfInsallMissed(accountREST.getNoOfInsallMissed());
		account.setRequestedDate(DateUtil.convertStringToDate(accountREST.getRequestedDate()));
		account.setApprovedDate(DateUtil.convertStringToDate(accountREST.getApprovedDate()));
		account.setDisbursementDate(DateUtil.convertStringToDate(accountREST.getDisbursementDate()));
		account.setInstStartDate(DateUtil.convertStringToDate(accountREST.getInstStartDate()));
		account.setExpCompletionDate(DateUtil.convertStringToDate(accountREST.getExpCompletionDate()));
		account.setClosureDate(DateUtil.convertStringToDate(accountREST.getClosureDate()));
		account.setDevelopmentPlan(accountREST.getDevelopmentPlan());
		account.setDescription(accountREST.getStatusMessage());
//		account.setMultiMToLoanAcs(multiMToLoanAcs);
		
		daoFactory.getGLoanAcDAO().merge(account);
		
		return accountREST;
	}

	public GroupLoanAcREST getGroupLoanAc(String lang, long groupLoanAcNo) throws BadRequestException {

		if(groupLoanAcNo <= 0) {
			throw new BadRequestException("Invalid Group Installment Account No");
		}

		GLoanAc account = daoFactory.getGLoanAcDAO().findById(groupLoanAcNo);
		
		return GroupLoanAcREST.convertAccountToREST(account);
	}

	public List<GroupLoanAcREST> getGroupLoanAcs(String lang, long groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}

		List<GLoanAc> accounts = daoFactory.getGLoanAcDAO().getAllAcForGroup(groupAcNo);
		
		List<GroupLoanAcREST> accountRESTList = new ArrayList<GroupLoanAcREST>();
		
		if(accounts != null && !accounts.isEmpty()) {
			for(GLoanAc account: accounts) {
				accountRESTList.add(GroupLoanAcREST.convertAccountToREST(account));
			}
		}
		
		return accountRESTList;
	}
	
	public LoanEMIPlan getLoanPlanning(LoanEMIPlan plan) throws BadRequestException {
		if(plan == null || plan.getStartDate().isEmpty()) {
			throw new BadRequestException("Invalid Start Date!");
		}
		
		plan.setDueDay(DateUtil.getDayOfMonth(DateUtil.convertStringToDate(plan.getStartDate())));
		return GenAlgoUtil.getLoanPlanning(plan);
	}
	
    public List<Attachment> attachFile(long groupAcNo, long groupLoanAcNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	
    	String attachmentInfo = attachmentBO.saveFile(groupAcNo, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_ADD, DBConst.G_LOAN_AC, groupLoanAcNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public List<Attachment> updateAttachFile(long groupAcNo, long docId, long groupLoanAcNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	GLoanAc gLoanAc = daoFactory.getGLoanAcDAO().findById(groupLoanAcNo);
    	if(!gLoanAc.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(gLoanAc.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Group Loan Account!");
    	}
    	
    	String attachmentInfo = attachmentBO.updateFile(groupAcNo, docId, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_UPDATE, DBConst.G_LOAN_AC, groupLoanAcNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public void deleteAttachFile(long groupAcNo, long docId, long groupLoanAcNo) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	GLoanAc gLoanAc = daoFactory.getGLoanAcDAO().findById(groupLoanAcNo);
    	if(!gLoanAc.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(gLoanAc.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Group Loan Account!");
    	}
    	
    	attachmentBO.deleteFile(groupAcNo, docId);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, Long.toString(docId));
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_DELETE, DBConst.G_LOAN_AC, groupLoanAcNo, dataFeilds));
    }
}
