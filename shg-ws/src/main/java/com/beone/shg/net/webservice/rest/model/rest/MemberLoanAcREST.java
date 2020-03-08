package com.beone.shg.net.webservice.rest.model.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MemberLoanAcREST {

	private long memberLoanAcNo;
	private long memberAcNo;
	private String loanType;
	private String loanCalculation;
	private String accountStatus;
	private String recoveryPeriod;
	private String loanSource;
	private long approvedByMember;
	private String mrole;
	private String memberName;
	private String approvedByMemberName;
	private BigDecimal principle;
	private BigDecimal pendingPrinciple;
	private BigDecimal recoveredInterest;
	private BigDecimal projectedInterest;
	private BigDecimal installment;
	private BigDecimal preEmiInterest;
	private BigDecimal pendingInterestDue;
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
	private List<Attachment> attachments;
	private Set<Long> multiMToLoanAcs = new HashSet<Long>(0);
	private MemberAcREST memberAc;
	List<Transaction> transactions;
	
	public long getMemberLoanAcNo() {
		return memberLoanAcNo;
	}
	public void setMemberLoanAcNo(long memberLoanAcNo) {
		this.memberLoanAcNo = memberLoanAcNo;
	}
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
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
	public long getApprovedByMember() {
		return approvedByMember;
	}
	public void setApprovedByMember(long approvedByMember) {
		this.approvedByMember = approvedByMember;
	}
	public String getMrole() {
		return mrole;
	}
	public void setMrole(String mrole) {
		this.mrole = mrole;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	public BigDecimal getRecoveredInterest() {
		return recoveredInterest;
	}
	public void setRecoveredInterest(BigDecimal recoveredInterest) {
		this.recoveredInterest = recoveredInterest;
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
	public Set<Long> getMultiMToLoanAcs() {
		return multiMToLoanAcs;
	}
	public void setMultiMToLoanAcs(Set<Long> multiMToLoanAcs) {
		this.multiMToLoanAcs = multiMToLoanAcs;
	}

	public MemberAcREST getMemberAc() {
		return memberAc;
	}
	public void setMemberAc(MemberAcREST memberAc) {
		this.memberAc = memberAc;
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
				DataUtil.toString(memberLoanAcNo),
				DataUtil.toString(memberAcNo),
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
//		account.setRecoveredInterest(DataUtil.toBigDecimal(rawMember[index++].trim()));
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
	
	public static MemberLoanAcREST convertAccountToREST(MLoanAc account) {
		MemberLoanAcREST accountREST = new MemberLoanAcREST();
		
		accountREST.setMemberLoanAcNo(account.getMLoanAcNo());
		accountREST.setMemberAcNo(account.getMAc().getMAcNo());
		accountREST.setLoanType(EnumCache.getNameOfFundType(account.getFundTypeId()));
		accountREST.setLoanCalculation(EnumCache.getNameOfEnumValue(EnumConst.LoanCalculation, account.getLoanCalculationId()));
		accountREST.setAccountStatus(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		accountREST.setRecoveryPeriod(EnumCache.getNameOfEnumValue(EnumConst.RecoveryPeriod, account.getRecoveryPeriodId()));
		accountREST.setLoanSource(EnumCache.getNameOfEnumValue(EnumConst.LoanSource, account.getLoanSourceId()));
		if(account.getApprovedByMember() > 0) {
			accountREST.setApprovedByMember(account.getApprovedByMember());
		}
		accountREST.setPrinciple(account.getPrincipleAm());
		accountREST.setPendingPrinciple(account.getPendingPrincipleAm());
		accountREST.setRecoveredInterest(account.getRecInterestAm());
		accountREST.setProjectedInterest(account.getProjInterestAm());
		accountREST.setInstallment(account.getInstallmentAm());
		accountREST.setPreEmiInterest(account.getPreEmiInterestAm());
		accountREST.setPendingInterestDue(account.getPendingInterestDueAm());
		accountREST.setRateOfInterest(account.getRateOfInterest());
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
//		accountREST.setMultiMToLoanAcs(multiMToLoanAcs);
		accountREST.setAttachments(Attachment.buildAttachments(account.getAttachmentUrl()));
		
		return accountREST;
	}
}
