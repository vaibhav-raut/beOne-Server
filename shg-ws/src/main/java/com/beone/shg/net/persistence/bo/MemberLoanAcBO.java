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
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;
import com.beone.shg.net.webservice.rest.model.resp.LoanEMIPlan;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.resp.Transactions;
import com.beone.shg.net.webservice.rest.model.rest.MemberLoanAcREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("memberLoanAcBO")
public class MemberLoanAcBO extends BaseBO {

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;

	@Autowired
	@Qualifier("attachmentBO")
	private AttachmentBO attachmentBO;

	public MemberLoanAcREST add(MemberLoanAcREST accountREST) throws BadRequestException {
		return add(accountREST, false);
	}
	
	public MemberLoanAcREST add(MemberLoanAcREST accountREST, boolean oldData) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
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

		boolean newObject = false;
		MLoanAc account = null;

		if(accountREST.getMemberLoanAcNo() > 0) {
			account = daoFactory.getMLoanAcDAO().findById(accountREST.getMemberLoanAcNo());
		}
		
		if(account == null) {
			account = new MLoanAc();		
			account.setMAc(daoFactory.getMAcDAO().getReferenceById(accountREST.getMemberAcNo()));
			newObject = true;
		}
				
		if(oldData) {
			account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()));
			addOldAccount(account, accountREST);
		} else {
			account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Submitted));
			accountREST.setAccountStatus(EnumConst.AccountStatus_Submitted);
			addNewAccount(account, accountREST);
		}
		
		if(newObject) {
			daoFactory.getMLoanAcDAO().persist(account);
		} else {
			daoFactory.getMLoanAcDAO().merge(account);
		}
		
		// Load Account ID back
		accountREST.setMemberLoanAcNo(account.getMLoanAcNo());
		
		return accountREST;
	}
	
	protected void addOldAccount(MLoanAc account, MemberLoanAcREST accountREST) throws BadRequestException {

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
		account.setFundTypeId(loanTypeId);
		account.setLoanCalculationId(loanCalculationId);
		account.setPrincipleAm(accountREST.getPrinciple());
		account.setPendingPrincipleAm(accountREST.getPendingPrinciple());
		account.setRecInterestAm(accountREST.getRecoveredInterest());
		account.setProjInterestAm(accountREST.getProjectedInterest());
		account.setInstallmentAm(accountREST.getInstallment());
		account.setPreEmiInterestAm(accountREST.getPreEmiInterest());
		account.setPendingInterestDueAm(accountREST.getPendingInterestDue());
		account.setRateOfInterest(accountREST.getRateOfInterest());
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
	
	protected void addNewAccount(MLoanAc account, MemberLoanAcREST accountREST) throws BadRequestException {

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
		account.setFundTypeId(loanTypeId);
		account.setLoanCalculationId(loanCalculationId);
		account.setPrincipleAm(accountREST.getPrinciple());
		account.setPendingPrincipleAm(accountREST.getPrinciple());
		account.setRecInterestAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setProjInterestAm(plan.getTotalInterest());
		account.setInstallmentAm(plan.getFixedEMI());
		account.setPreEmiInterestAm(plan.getPreEMI());
		account.setPendingInterestDueAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setRateOfInterest(accountREST.getRateOfInterest());
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
		accountREST.setRecoveredInterest(DataUtil.ZERO_BIG_DECIMAL);
		accountREST.setProjectedInterest(plan.getTotalInterest());
		accountREST.setPreEmiInterest(accountREST.getPreEmiInterest());
		accountREST.setPendingInterestDue(DataUtil.ZERO_BIG_DECIMAL);
	}

	public MemberLoanAcREST approveReject(MemberLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Type");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}
		if(accountREST.getApprovedByMember() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Approved By Member Account");
		}
		if(accountREST.getAccountStatus() == null) {
			throw new BadRequestException("Invalid Account Status");
		}
		
		int accountStatusId = EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus());

		if(accountStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Account Status:" + accountREST.getAccountStatus());
		}

		MLoanAc account = daoFactory.getMLoanAcDAO().findById(accountREST.getMemberLoanAcNo());
		
		if(!EnumUtil.isAccountStatusApprovable(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()))) {
			throw new BadRequestException("Can't Update Current Loan Account Status: " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
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
		
		daoFactory.getMLoanAcDAO().merge(account);
		
		return accountREST;
	}

	public MemberLoanAcREST activate(MemberLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Account");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}
		
		MLoanAc account = daoFactory.getMLoanAcDAO().findById(accountREST.getMemberLoanAcNo());
		
		if(account == null) {
			throw new BadRequestException("Invalid Loan Account: " + accountREST.getMemberLoanAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Approved)) {
			throw new BadRequestException("Account status Not 'Approved' it is : " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}
		
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Active));
		accountREST.setAccountStatus(EnumConst.AccountStatus_Active);

		if(accountREST.getDevelopmentPlan() != null && !accountREST.getDevelopmentPlan().isEmpty()) {
			account.setDevelopmentPlan(accountREST.getDevelopmentPlan());
		}
		if(accountREST.getStatusMessage() != null && !accountREST.getStatusMessage().isEmpty()) {
			account.setDescription(accountREST.getStatusMessage());
		}
		
		daoFactory.getMLoanAcDAO().merge(account);
		
		// Updated MAc for the Loan
		MAc mAc = daoFactory.getMAcDAO().findById(accountREST.getMemberAcNo());
		if(mAc == null) {
			throw new BadRequestException("Invalid Member Account: " + accountREST.getMemberAcNo());
		}
		mAc.setNoOfLoans(mAc.getNoOfLoans() + 1);
		mAc.setNoOfActiveLoans(mAc.getNoOfActiveLoans() + 1);
		mAc.setProjIntOnLoanAm(mAc.getProjIntOnLoanAm().add(accountREST.getProjectedInterest()));
		daoFactory.getMAcDAO().merge(mAc);
		
		// Updated GAc for the Loan
		MProfile mProfile = daoFactory.getMProfileDAO().findById(accountREST.getMemberAcNo());
		GAc gAc = daoFactory.getGAcDAO().findById(ConversionUtil.getGroupAcFromMember(accountREST.getMemberAcNo()));
		if(gAc == null) {
			throw new BadRequestException("Invalid Group Account");
		}
		String mRole = EnumCache.getNameOfMRole(mProfile.getMroleId());
		
		if(EnumUtil.isCoreMember(mRole)) {
			gAc.setCMNoOfLoans(gAc.getAMNoOfLoans() + 1);
			gAc.setCMNoOfActiveLoans(gAc.getAMNoOfActiveLoans() + 1);
			gAc.setCMProjIntOnLoanAm(gAc.getAMProjIntOnLoanAm().add(accountREST.getProjectedInterest()));
		} else if(EnumUtil.isAssociateMember(mRole)) {
			gAc.setAMNoOfLoans(gAc.getAMNoOfLoans() + 1);
			gAc.setAMNoOfActiveLoans(gAc.getAMNoOfActiveLoans() + 1);
			gAc.setAMProjIntOnLoanAm(gAc.getAMProjIntOnLoanAm().add(accountREST.getProjectedInterest()));
		}
		daoFactory.getGAcDAO().merge(gAc);
				
		if(accountREST.getTransactions() != null) {
			transactionBO.addTransactions(accountREST.getTransactions());
		}

		return accountREST;
	}

	public Transactions getTxsToActivate(MemberLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Account");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}

		MLoanAc account = daoFactory.getMLoanAcDAO().findById(accountREST.getMemberLoanAcNo());
		MProfile mProfile = daoFactory.getMProfileDAO().findById(accountREST.getMemberAcNo());

		if(account == null) {
			throw new BadRequestException("Invalid Loan Account: " + accountREST.getMemberLoanAcNo());
		}
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account: " + accountREST.getMemberAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Approved)) {
			throw new BadRequestException("Account status Not 'Approved' it is : " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}

		long groupAcNo = ConversionUtil.getGroupAcFromMember(accountREST.getMemberAcNo());
		GRules rules = daoFactory.getGRulesDAO().findById(groupAcNo);
		BigDecimal fee = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal applicationFee = DataUtil.ZERO_BIG_DECIMAL;
		if(rules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER && EnumUtil.isAssociateMember(EnumCache.getNameOfMRole(mProfile.getMroleId()))) {
			fee = accountREST.getPrinciple().multiply(new BigDecimal(rules.getAmLoanProcessingFeePercent()/100.0));
			applicationFee = new BigDecimal(rules.getAmApplicationFee());
		} else {
			fee = accountREST.getPrinciple().multiply(new BigDecimal(rules.getCmLoanProcessingFeePercent()/100.0));
			applicationFee = new BigDecimal(rules.getCmApplicationFee());
		}
		fee = GenAlgoUtil.roundHalfUp(fee, 0);
		BigDecimal netAmount = accountREST.getPrinciple().subtract(fee).subtract(accountREST.getPreEmiInterest()).subtract(applicationFee);

		Transactions transactions = new Transactions();
		List<BankAccountShort> memberBankAcNos = daoFactory.getMBankAccountDAO().getMemberBankAccountsDisplay(accountREST.getMemberAcNo());

		if(fee.compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
			// Adding Loan_Processing_Fee
			Transaction tx1 = new Transaction();
			tx1.setTxType(EnumConst.TxType_Loan_Processing_Fee);
			tx1.setTxWith(EnumConst.TxType_MEMBER);
			tx1.setSlipType(EnumConst.SlipType_RECEIPT);
			tx1.setMemberLoanAcNo(accountREST.getMemberLoanAcNo());
			tx1.setMemberAcNo(accountREST.getMemberAcNo());
			tx1.setGroupAcNo(groupAcNo);
			tx1.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
			tx1.setTxAcType(EnumConst.TxAcType_Member_Loan);
			tx1.setDescription("Loan Processing Fee for Fund No : " + account.getMLoanAcNo());
			tx1.setAmount(fee);
			tx1.setMemberBankAcNos(memberBankAcNos);
			transactions.addTransaction(tx1);
		}

		if(applicationFee.compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
			// Adding Application_Fee
			Transaction tx11 = new Transaction();
			tx11.setTxType(EnumConst.TxType_Application_Fee);
			tx11.setTxWith(EnumConst.TxType_MEMBER);
			tx11.setSlipType(EnumConst.SlipType_RECEIPT);
			tx11.setMemberLoanAcNo(accountREST.getMemberLoanAcNo());
			tx11.setMemberAcNo(accountREST.getMemberAcNo());
			tx11.setGroupAcNo(groupAcNo);
			tx11.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
			tx11.setTxAcType(EnumConst.TxAcType_Member_Loan);
			tx11.setDescription("Application Fee for Fund No : " + account.getMLoanAcNo());
			tx11.setAmount(applicationFee);
			tx11.setMemberBankAcNos(memberBankAcNos);
			transactions.addTransaction(tx11);
		}
		
		if(accountREST.getPreEmiInterest().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
			// Adding Loan_Disbursement
			Transaction tx2 = new Transaction();
			tx2.setTxType(EnumConst.TxType_Pre_Interest_Installment);
			tx2.setTxWith(EnumConst.TxType_MEMBER);
			tx2.setSlipType(EnumConst.SlipType_RECEIPT);
			tx2.setMemberLoanAcNo(accountREST.getMemberLoanAcNo());
			tx2.setMemberAcNo(accountREST.getMemberAcNo());
			tx2.setGroupAcNo(groupAcNo);
			tx2.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
			tx2.setTxAcType(EnumConst.TxAcType_Member_Loan);
			tx2.setDescription("Pre EMI Interest");
			tx2.setAmount(accountREST.getPreEmiInterest());
			tx2.setMemberBankAcNos(memberBankAcNos);
			transactions.addTransaction(tx2);
		}
		
		// Adding Loan_Disbursement
		Transaction tx3 = new Transaction();
		tx3.setTxType(EnumConst.TxType_Loan_Disbursement);
		tx3.setTxWith(EnumConst.TxType_MEMBER);
		tx3.setSlipType(EnumConst.SlipType_VOUCHER);
		tx3.setMemberLoanAcNo(accountREST.getMemberLoanAcNo());
		tx3.setMemberAcNo(accountREST.getMemberAcNo());
		tx3.setGroupAcNo(groupAcNo);
		tx3.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
		tx3.setTxAcType(EnumConst.TxAcType_Member_Loan);
		tx3.setDescription(netAmount + " - Net Loan Disbursement after Adjustments");
		tx3.setAmount(accountREST.getPrinciple());
		tx3.setMemberBankAcNos(memberBankAcNos);
		transactions.addTransaction(tx3);
		
		transactions.setGroupBankAcNos(daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(groupAcNo));
		transactions.setDisplayNames(RestDisplayTitle.getTransactionRDT());
		
		return transactions;
	}

	public MemberLoanAcREST close(MemberLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Account");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}
		
		MLoanAc account = daoFactory.getMLoanAcDAO().findById(accountREST.getMemberLoanAcNo());
		
		if(account == null) {
			throw new BadRequestException("Invalid Loan Account: " + accountREST.getMemberLoanAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Active)) {
			throw new BadRequestException("Account status Not 'Active' it is : " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}
		
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Foreclosed));
		accountREST.setAccountStatus(EnumConst.AccountStatus_Foreclosed);

		if(accountREST.getStatusMessage() != null && !accountREST.getStatusMessage().isEmpty()) {
			account.setDescription(accountREST.getStatusMessage());
		}
		
		daoFactory.getMLoanAcDAO().merge(account);
		
		// Updated MAc for the Loan
		MAc mAc = daoFactory.getMAcDAO().findById(accountREST.getMemberAcNo());
		if(mAc == null) {
			throw new BadRequestException("Invalid Member Account: " + accountREST.getMemberAcNo());
		}
		mAc.setNoOfActiveLoans(mAc.getNoOfActiveLoans() - 1);
		mAc.setProjIntOnLoanAm(mAc.getProjIntOnLoanAm().subtract(account.getProjInterestAm()));
		daoFactory.getMAcDAO().merge(mAc);
		
		// Updated GAc for the Loan
		MProfile mProfile = daoFactory.getMProfileDAO().findById(accountREST.getMemberAcNo());
		GAc gAc = daoFactory.getGAcDAO().findById(ConversionUtil.getGroupAcFromMember(accountREST.getMemberAcNo()));
		if(gAc == null) {
			throw new BadRequestException("Invalid Group Account");
		}
		
		String mRole = EnumCache.getNameOfMRole(mProfile.getMroleId());
		
		if(EnumUtil.isCoreMember(mRole)) {
			gAc.setCMNoOfActiveLoans(gAc.getCMNoOfActiveLoans() - 1);
			gAc.setCMProjIntOnLoanAm(gAc.getCMProjIntOnLoanAm().subtract(accountREST.getProjectedInterest()));
		} else if(EnumUtil.isAssociateMember(mRole)) {
			gAc.setAMNoOfActiveLoans(gAc.getAMNoOfActiveLoans() - 1);
			gAc.setAMProjIntOnLoanAm(gAc.getAMProjIntOnLoanAm().subtract(accountREST.getProjectedInterest()));
		}
		daoFactory.getGAcDAO().merge(gAc);
				
		if(accountREST.getTransactions() != null) {
			transactionBO.addTransactions(accountREST.getTransactions());
		}

		return accountREST;
	}

	public Transactions getTxsToClose(MemberLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberLoanAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Loan Account");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}

		MLoanAc account = daoFactory.getMLoanAcDAO().findById(accountREST.getMemberLoanAcNo());
		MProfile mProfile = daoFactory.getMProfileDAO().findById(accountREST.getMemberAcNo());

		if(account == null) {
			throw new BadRequestException("Invalid Loan Account: " + accountREST.getMemberLoanAcNo());
		}
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account: " + accountREST.getMemberAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Active)) {
			throw new BadRequestException("Account status Not 'Active' it is : " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}
		
		Transactions transactions = getTxsToClose(mProfile, account);
		transactions.setGroupBankAcNos(daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(ConversionUtil.getGroupAcFromMember(mProfile.getMemberAcNo())));
		transactions.setDisplayNames(RestDisplayTitle.getTransactionRDT());
		
		return transactions;
	}
	
	public Transactions getTxsToClose(MProfile mProfile) throws BadRequestException {
		Transactions transactions = new Transactions();

		List<MLoanAc> accounts = daoFactory.getMLoanAcDAO().getAllAcForMember(mProfile.getMemberAcNo());

		if(accounts != null) {
			for(MLoanAc account : accounts) {
				transactions.getTransactions().addAll(getTxsToClose(mProfile, account).getTransactions());
			}
		}
		return transactions;
	}
	
	protected Transactions getTxsToClose(MProfile mProfile, MLoanAc account) throws BadRequestException {

		long groupAcNo = ConversionUtil.getGroupAcFromMember(mProfile.getMemberAcNo());
		GRules rules = daoFactory.getGRulesDAO().findById(groupAcNo);

		Transactions transactions = new Transactions();
		List<BankAccountShort> memberBankAcNos = daoFactory.getMBankAccountDAO().getMemberBankAccountsDisplay(mProfile.getMemberAcNo());
		
		int minMonthsIntOnLoan = DataUtil.ZERO_INTEGER;		
		if(EnumUtil.isAssociateMember(EnumCache.getNameOfMRole(mProfile.getMroleId()))) {
			minMonthsIntOnLoan = rules.getAmMinMonthsIntOnLoan();
		} else {
			minMonthsIntOnLoan = rules.getCmMinMonthsIntOnLoan();
		}
		
		if(minMonthsIntOnLoan > DataUtil.ZERO_INTEGER) {
			BigDecimal minIntOnLoan = GenAlgoUtil.computeInterestOnLoan(account.getPrincipleAm(), (account.getRateOfInterest() * minMonthsIntOnLoan / 1200.0f));

			if(minIntOnLoan.compareTo(account.getRecInterestAm()) > DataUtil.ZERO_INTEGER) {
				// Adding Expected Min Interest Payment
				Transaction tx1 = new Transaction();
				tx1.setTxType(EnumConst.TxType_Loan_Interest_Installment);
				tx1.setSlipType(EnumConst.SlipType_RECEIPT);
				tx1.setMemberLoanAcNo(account.getMLoanAcNo());
				tx1.setMemberAcNo(mProfile.getMemberAcNo());
				tx1.setGroupAcNo(groupAcNo);
				tx1.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
				tx1.setTxAcType(EnumConst.TxAcType_Member_Loan);
				tx1.setDescription("Expected Min Interest Payment");
				tx1.setAmount(minIntOnLoan.subtract(account.getRecInterestAm()));
				tx1.setMemberBankAcNos(memberBankAcNos);
				transactions.addTransaction(tx1);
			}
		}

		// Adding Outstanding Loan Principle Payment
		Transaction tx2 = new Transaction();
		tx2.setTxType(EnumConst.TxType_Loan_Prepayment);
		tx2.setSlipType(EnumConst.SlipType_RECEIPT);
		tx2.setMemberLoanAcNo(account.getMLoanAcNo());
		tx2.setMemberAcNo(mProfile.getMemberAcNo());
		tx2.setGroupAcNo(groupAcNo);
		tx2.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
		tx2.setTxAcType(EnumConst.TxAcType_Member_Loan);
		tx2.setDescription("Outstanding Loan Principle Payment");
		tx2.setAmount(account.getPendingPrincipleAm());
		tx2.setMemberBankAcNos(memberBankAcNos);
		transactions.addTransaction(tx2);
		
		return transactions;
	}
	
	public MemberLoanAcREST updateMemberLoanAc(MemberLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberLoanAcNo() <= 0) {
			throw new BadRequestException("Invalid Member Installment Account No");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MLoanAc account = daoFactory.getMLoanAcDAO().findById(accountREST.getMemberLoanAcNo());
		
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Active));
		
		if(accountREST.getRecoveryPeriod() != null && !accountREST.getRecoveryPeriod().isEmpty()) {
			account.setRecoveryPeriodId(EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, accountREST.getRecoveryPeriod()));
		}
		
		if(accountREST.getInstallment() != null) {
			account.setInstallmentAm(accountREST.getInstallment());
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
		
		daoFactory.getMLoanAcDAO().merge(account);
		
		return accountREST;
	}
	
	public MemberLoanAcREST updateMemberLoanAcData(MemberLoanAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberLoanAcNo() <= 0) {
			throw new BadRequestException("Invalid Member Installment Account No");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MLoanAc account = daoFactory.getMLoanAcDAO().findById(accountREST.getMemberLoanAcNo());
		
		account.setFundTypeId(EnumCache.getIndexOfFundType(accountREST.getLoanType()));
		account.setLoanCalculationId(EnumCache.getIndexOfEnumValue(EnumConst.LoanCalculation, accountREST.getLoanCalculation()));
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()));
		account.setRecoveryPeriodId(EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, accountREST.getRecoveryPeriod()));
		account.setLoanSourceId(EnumCache.getIndexOfEnumValue(EnumConst.LoanSource, accountREST.getLoanSource()));
		if(accountREST.getApprovedByMember() > DataUtil.ZERO_LONG && daoFactory.getMProfileDAO().getReferenceById(accountREST.getApprovedByMember()) != null) {
			account.setApprovedByMember(accountREST.getApprovedByMember());
		}
		account.setPrincipleAm(accountREST.getPrinciple());
		account.setPendingPrincipleAm(accountREST.getPendingPrinciple());
		account.setRecInterestAm(accountREST.getRecoveredInterest());
		account.setProjInterestAm(accountREST.getProjectedInterest());
		account.setInstallmentAm(accountREST.getInstallment());
		account.setPreEmiInterestAm(accountREST.getPreEmiInterest());
		account.setPendingInterestDueAm(accountREST.getPendingInterestDue());
		account.setRateOfInterest(accountREST.getRateOfInterest());
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
		
		daoFactory.getMLoanAcDAO().merge(account);
		
		return accountREST;
	}

	public MemberLoanAcREST getMemberLoanAc(String lang, long memberLoanAcNo) throws BadRequestException {

		if(memberLoanAcNo <= 0) {
			throw new BadRequestException("Invalid Member Installment Account No");
		}

		MLoanAc account = daoFactory.getMLoanAcDAO().findById(memberLoanAcNo);
		
		return MemberLoanAcREST.convertAccountToREST(account);
	}

	public List<MemberLoanAcREST> getMemberLoanAcsForMember(String lang, long memberAcNo, String status) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No");
		}

		List<MLoanAc> accounts = daoFactory.getMLoanAcDAO().getAllAcForMember(memberAcNo);
		
		List<MemberLoanAcREST> accountRESTList = new ArrayList<MemberLoanAcREST>();
		
		if(accounts != null && !accounts.isEmpty()) {
			for(MLoanAc account: accounts) {
				if(!status.equalsIgnoreCase("Active") ||
						EnumUtil.isAccountStatusActive(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()))) {
					accountRESTList.add(MemberLoanAcREST.convertAccountToREST(account));
				}
			}
		}
		
		return accountRESTList;
	}

	public List<MemberLoanAcREST> getMemberLoanAcsForGroup(String lang, long groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}

		List<MLoanAc> accounts = daoFactory.getMLoanAcDAO().getAllAcForGroup(groupAcNo);
		
		List<MemberLoanAcREST> accountRESTList = new ArrayList<MemberLoanAcREST>();
		
		if(accounts != null && !accounts.isEmpty()) {
			for(MLoanAc account: accounts) {
				accountRESTList.add(MemberLoanAcREST.convertAccountToREST(account));
			}
		}
		
		return accountRESTList;
	}
	
	public LoanEMIPlan getLoanPlanning(LoanEMIPlan plan) throws BadRequestException {		
		return GenAlgoUtil.getLoanPlanning(plan);
	}
	
    public List<Attachment> attachFile(long memberAcNo, long memberLoanAcNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	
    	String attachmentInfo = attachmentBO.saveFile(ConversionUtil.getGroupAcFromMember(memberAcNo), 
    			fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(ConversionUtil.getGroupAcFromMember(memberAcNo), new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_ADD, DBConst.M_LOAN_AC, memberLoanAcNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public List<Attachment> updateAttachFile(long memberAcNo, long docId, long memberLoanAcNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	MLoanAc mLoanAc = daoFactory.getMLoanAcDAO().findById(memberLoanAcNo);
    	if(!mLoanAc.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(mLoanAc.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Member Loan Account!");
    	}
   	
    	String attachmentInfo = attachmentBO.updateFile(ConversionUtil.getGroupAcFromMember(memberAcNo), 
    			docId, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(ConversionUtil.getGroupAcFromMember(memberAcNo), new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_UPDATE, DBConst.M_LOAN_AC, memberLoanAcNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public void deleteAttachFile(long memberAcNo, long docId, long memberLoanAcNo) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	MLoanAc mLoanAc = daoFactory.getMLoanAcDAO().findById(memberLoanAcNo);
    	if(!mLoanAc.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(mLoanAc.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Member Loan Account!");
    	}
   	
    	attachmentBO.deleteFile(ConversionUtil.getGroupAcFromMember(memberAcNo), docId);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, Long.toString(docId));
    	
		JobQueueManager.addToJobQueue(ConversionUtil.getGroupAcFromMember(memberAcNo), new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_DELETE, DBConst.M_LOAN_AC, memberLoanAcNo, dataFeilds));
    }
}
