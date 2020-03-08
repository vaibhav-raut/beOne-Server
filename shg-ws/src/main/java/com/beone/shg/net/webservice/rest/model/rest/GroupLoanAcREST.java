package com.beone.shg.net.webservice.rest.model.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GroupLoanAcREST {

	private long groupLoanAcNo;
	private long groupAcNo;
	private long bankGroupAcNo;
	private long loanBankAcNo;
	private String loanType;
	private String loanCalculation;
	private String accountStatus;
	private String recoveryPeriod;
	private String loanSource;
	private String loanAcName;
	private long approvedByMember;
	private String approvedByMemberName;
	private BigDecimal principle;
	private BigDecimal pendingPrinciple;
	private BigDecimal paidInterest;
	private BigDecimal projectedInterest;
	private BigDecimal installment;
	private BigDecimal preEmiInterest;
	private BigDecimal pendingInterestDue;
	private BigDecimal loanProcessingFee;
	private BigDecimal otherFee;
	private float rateOfInterest;
	private int startupNoOfInst;
	private int expNoOfInst;
	private int noOfInstPaid;
	private int noOfInsallLate;
	private int noOfInsallMissed;
	private String requestedDate;
	private String approvedDate;
	private String disbursementDate;
	private String instStartDate;
	private String expCompletionDate;
	private String closureDate;
	private String developmentPlan;
	private String statusMessage;
	private GBankAccountREST bankAccount;
	private List<Attachment> attachments;
	private List<Transaction> transactions;
	
	public long getGroupLoanAcNo() {
		return groupLoanAcNo;
	}
	public void setGroupLoanAcNo(long groupLoanAcNo) {
		this.groupLoanAcNo = groupLoanAcNo;
	}
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	public long getBankGroupAcNo() {
		return bankGroupAcNo;
	}
	public void setBankGroupAcNo(long bankGroupAcNo) {
		this.bankGroupAcNo = bankGroupAcNo;
	}
	public long getLoanBankAcNo() {
		return loanBankAcNo;
	}
	public void setLoanBankAcNo(long loanBankAcNo) {
		this.loanBankAcNo = loanBankAcNo;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getLoanCalculation() {
		return loanCalculation;
	}
	public void setLoanCalculation(String loanCalculation) {
		this.loanCalculation = loanCalculation;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getRecoveryPeriod() {
		return recoveryPeriod;
	}
	public void setRecoveryPeriod(String recoveryPeriod) {
		this.recoveryPeriod = recoveryPeriod;
	}
	public String getLoanSource() {
		return loanSource;
	}
	public void setLoanSource(String loanSource) {
		this.loanSource = loanSource;
	}
	public String getLoanAcName() {
		return loanAcName;
	}
	public void setLoanAcName(String loanAcName) {
		this.loanAcName = loanAcName;
	}
	public long getApprovedByMember() {
		return approvedByMember;
	}
	public void setApprovedByMember(long approvedByMember) {
		this.approvedByMember = approvedByMember;
	}
	public String getApprovedByMemberName() {
		return approvedByMemberName;
	}
	public void setApprovedByMemberName(String approvedByMemberName) {
		this.approvedByMemberName = approvedByMemberName;
	}
	public BigDecimal getPrinciple() {
		return principle;
	}
	public void setPrinciple(BigDecimal principle) {
		this.principle = principle;
	}
	public BigDecimal getPendingPrinciple() {
		return pendingPrinciple;
	}
	public void setPendingPrinciple(BigDecimal pendingPrinciple) {
		this.pendingPrinciple = pendingPrinciple;
	}
	public BigDecimal getPaidInterest() {
		return paidInterest;
	}
	public void setPaidInterest(BigDecimal paidInterest) {
		this.paidInterest = paidInterest;
	}
	public BigDecimal getProjectedInterest() {
		return projectedInterest;
	}
	public void setProjectedInterest(BigDecimal projectedInterest) {
		this.projectedInterest = projectedInterest;
	}
	public BigDecimal getInstallment() {
		return installment;
	}
	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}
	public BigDecimal getPreEmiInterest() {
		return preEmiInterest;
	}
	public void setPreEmiInterest(BigDecimal preEmiInterest) {
		this.preEmiInterest = preEmiInterest;
	}
	public BigDecimal getPendingInterestDue() {
		return pendingInterestDue;
	}
	public void setPendingInterestDue(BigDecimal pendingInterestDue) {
		this.pendingInterestDue = pendingInterestDue;
	}
	public BigDecimal getLoanProcessingFee() {
		return loanProcessingFee;
	}
	public void setLoanProcessingFee(BigDecimal loanProcessingFee) {
		this.loanProcessingFee = loanProcessingFee;
	}
	public BigDecimal getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	public float getRateOfInterest() {
		return rateOfInterest;
	}
	public void setRateOfInterest(float rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}
	public int getStartupNoOfInst() {
		return startupNoOfInst;
	}
	public void setStartupNoOfInst(int startupNoOfInst) {
		this.startupNoOfInst = startupNoOfInst;
	}
	public int getExpNoOfInst() {
		return expNoOfInst;
	}
	public void setExpNoOfInst(int expNoOfInst) {
		this.expNoOfInst = expNoOfInst;
	}
	public int getNoOfInstPaid() {
		return noOfInstPaid;
	}
	public void setNoOfInstPaid(int noOfInstPaid) {
		this.noOfInstPaid = noOfInstPaid;
	}
	public int getNoOfInsallLate() {
		return noOfInsallLate;
	}
	public void setNoOfInsallLate(int noOfInsallLate) {
		this.noOfInsallLate = noOfInsallLate;
	}
	public int getNoOfInsallMissed() {
		return noOfInsallMissed;
	}
	public void setNoOfInsallMissed(int noOfInsallMissed) {
		this.noOfInsallMissed = noOfInsallMissed;
	}
	public String getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getDisbursementDate() {
		return disbursementDate;
	}
	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}
	public String getInstStartDate() {
		return instStartDate;
	}
	public void setInstStartDate(String instStartDate) {
		this.instStartDate = instStartDate;
	}
	public String getExpCompletionDate() {
		return expCompletionDate;
	}
	public void setExpCompletionDate(String expCompletionDate) {
		this.expCompletionDate = expCompletionDate;
	}
	public String getClosureDate() {
		return closureDate;
	}
	public void setClosureDate(String closureDate) {
		this.closureDate = closureDate;
	}
	public String getDevelopmentPlan() {
		return developmentPlan;
	}
	public void setDevelopmentPlan(String developmentPlan) {
		this.developmentPlan = developmentPlan;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public GBankAccountREST getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(GBankAccountREST bankAccount) {
		this.bankAccount = bankAccount;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public void addAttachment(Attachment attachment) {
		if(this.attachments == null) {
			this.attachments = new ArrayList<Attachment>();
		}
		this.attachments.add(attachment);
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public void addTransaction(Transaction transaction) {
		if(transactions == null) {
			transactions = new ArrayList<Transaction>();
		}
		this.transactions.add(transaction);
	}
	public String[] toStringArray() {
		
		String[] memberRaw = {
				DataUtil.toString(groupLoanAcNo),
				DataUtil.toString(groupAcNo),
				DataUtil.toString(principle)
		};
		return memberRaw;
	}
	
//	public static MemberLoanAcREST buildMemberLoanAc(String[] rawMember) throws BadRequestException {
//		
//		MemberLoanAcREST account = new MemberLoanAcREST();
//		int index = DataUtil.ZERO_INTEGER;
//		
//		if(rawMember.length == DataUtil.ZERO_INTEGER) {
//			throw new BadRequestException("Invalid CSV Data:" + rawMember);
//		}
//		
//		account.setMemberAcNo(DataUtil.toLong(rawMember[index++].trim()));
//		account.setAccountStatus(rawMember[index++].trim());
//		account.setRecoveryPeriod(rawMember[index++].trim());
//		account.setLoanType(rawMember[index++].trim());
//		account.setLoanCalculation(rawMember[index++].trim());
//		account.setPrinciple(DataUtil.toBigDecimal(rawMember[index++].trim()));
//		account.setPendingPrinciple(DataUtil.toBigDecimal(rawMember[index++].trim()));
//		account.setPaidInterest(DataUtil.toBigDecimal(rawMember[index++].trim()));
//		account.setProjectedInterest(DataUtil.toBigDecimal(rawMember[index++].trim()));
//		account.setInstallment(DataUtil.toBigDecimal(rawMember[index++].trim()));
//		account.setPreEmiInterest(DataUtil.toBigDecimal(rawMember[index++].trim()));
//		account.setPendingInterestDue(DataUtil.toBigDecimal(rawMember[index++].trim()));
//		account.setRateOfInterest(DataUtil.toFloat(rawMember[index++].trim()));
//		account.setStartupNoOfInst(DataUtil.toInteger(rawMember[index++].trim()));
//		account.setExpNoOfInst(DataUtil.toInteger(rawMember[index++].trim()));
//		account.setNoOfInstPaid(DataUtil.toInteger(rawMember[index++].trim()));
//		account.setNoOfInsallLate(DataUtil.toInteger(rawMember[index++].trim()));
//		account.setNoOfInsallMissed(DataUtil.toInteger(rawMember[index++].trim()));
//		account.setRequestedDate(rawMember[index++].trim());
//		account.setApprovedDate(rawMember[index++].trim());
//		account.setDisbursementDate(rawMember[index++].trim());
//		account.setInstStartDate(rawMember[index++].trim());
//		account.setExpCompletionDate(rawMember[index++].trim());
//		account.setClosureDate(rawMember[index++].trim());
//		account.setAttachmentUrl(rawMember[index++].trim());
//
//		return account;
//	}	
	
	public static GroupLoanAcREST convertAccountToREST(GLoanAc account) {
		GroupLoanAcREST accountREST = new GroupLoanAcREST();
		
		accountREST.setGroupLoanAcNo(account.getGLoanAcNo());
		accountREST.setGroupAcNo(account.getGAc().getGAcNo());
		accountREST.setBankGroupAcNo(account.getBGroupAcNo());
		accountREST.setLoanBankAcNo(account.getLoanBankAcNo());
		accountREST.setLoanType(EnumCache.getNameOfFundType(account.getFundTypeId()));
		accountREST.setLoanCalculation(EnumCache.getNameOfEnumValue(EnumConst.LoanCalculation, account.getLoanCalculationId()));
		accountREST.setAccountStatus(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		accountREST.setRecoveryPeriod(EnumCache.getNameOfEnumValue(EnumConst.RecoveryPeriod, account.getRecoveryPeriodId()));
		accountREST.setLoanSource(EnumCache.getNameOfEnumValue(EnumConst.LoanSource, account.getLoanSourceId()));
		accountREST.setLoanAcName(account.getLoanAcName());
		if(account.getApprovedByMember() > 0) {
			accountREST.setApprovedByMember(account.getApprovedByMember());
		}
		accountREST.setPrinciple(account.getPrincipleAm());
		accountREST.setPendingPrinciple(account.getPendingPrincipleAm());
		accountREST.setPaidInterest(account.getPaidInterestAm());
		accountREST.setProjectedInterest(account.getProjInterestAm());
		accountREST.setInstallment(account.getInstallmentAm());
		accountREST.setPreEmiInterest(account.getPreEmiInterestAm());
		accountREST.setPendingInterestDue(account.getPendingInterestDueAm());
		accountREST.setLoanProcessingFee(account.getLoanProcessingFee());
		accountREST.setOtherFee(account.getOtherFee());
		accountREST.setRateOfInterest(account.getInterestRate());
		accountREST.setStartupNoOfInst(account.getStartupNoOfInst());
		accountREST.setExpNoOfInst(account.getExpNoOfInst());
		accountREST.setNoOfInstPaid(account.getNoOfInstPaid());
		accountREST.setNoOfInsallLate(account.getNoOfInsallLate());
		accountREST.setNoOfInsallMissed(account.getNoOfInsallMissed());
		accountREST.setRequestedDate(DateUtil.convertDateToString(account.getRequestedDate()));
		accountREST.setApprovedDate(DateUtil.convertDateToString(account.getApprovedDate()));
		accountREST.setDisbursementDate(DateUtil.convertDateToString(account.getDisbursementDate()));
		accountREST.setInstStartDate(DateUtil.convertDateToString(account.getInstStartDate()));
		accountREST.setExpCompletionDate(DateUtil.convertDateToString(account.getExpCompletionDate()));
		accountREST.setClosureDate(DateUtil.convertDateToString(account.getClosureDate()));
		accountREST.setDevelopmentPlan(account.getDevelopmentPlan());
		accountREST.setStatusMessage(account.getDescription());
		accountREST.setAttachments(Attachment.buildAttachments(account.getAttachmentUrl()));
		
		return accountREST;
	}
}
