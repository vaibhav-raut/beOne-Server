package com.beone.shg.net.webservice.rest.model.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.GInvtAc;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GroupInvtAcREST {

	private long groupInvtAcNo;
	private long groupAcNo;
	private long bankGroupAcNo;
	private long invtBankAcNo;
	private String investmentType;
	private String accountStatus;
	private long approvedByMember;
	private String investmentNo;
	private String investmentAcName;
	private String investmentDesc;
	private BigDecimal invtAm;
	private BigDecimal recInvtAm;
	private BigDecimal recInterestAm;
	private BigDecimal projInterestAm;
	private Float interestRate;
	private String requestedDate;
	private String approvedDate;
	private String maturityDate;
	private String closureDate;
	private String developmentPlan;
	private int invtDuration;
	private GBankAccountREST bankAccount;
	private List<Attachment> attachments;
	private List<Transaction> transactions;
	
	public GroupInvtAcREST() {
		super();
	}
	public long getGroupInvtAcNo() {
		return groupInvtAcNo;
	}
	public void setGroupInvtAcNo(long groupInvtAcNo) {
		this.groupInvtAcNo = groupInvtAcNo;
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
	public long getInvtBankAcNo() {
		return invtBankAcNo;
	}
	public void setInvtBankAcNo(long invtBankAcNo) {
		this.invtBankAcNo = invtBankAcNo;
	}
	public String getInvestmentType() {
		return investmentType;
	}
	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public long getApprovedByMember() {
		return approvedByMember;
	}
	public void setApprovedByMember(long approvedByMember) {
		this.approvedByMember = approvedByMember;
	}
	public String getInvestmentNo() {
		return investmentNo;
	}
	public void setInvestmentNo(String investmentNo) {
		this.investmentNo = investmentNo;
	}
	public String getInvestmentAcName() {
		return investmentAcName;
	}
	public void setInvestmentAcName(String investmentAcName) {
		this.investmentAcName = investmentAcName;
	}
	public String getInvestmentDesc() {
		return investmentDesc;
	}
	public void setInvestmentDesc(String investmentDesc) {
		this.investmentDesc = investmentDesc;
	}
	public BigDecimal getInvtAm() {
		return invtAm;
	}
	public void setInvtAm(BigDecimal invtAm) {
		this.invtAm = invtAm;
	}
	public BigDecimal getRecInvtAm() {
		return recInvtAm;
	}
	public void setRecInvtAm(BigDecimal recInvtAm) {
		this.recInvtAm = recInvtAm;
	}
	public BigDecimal getRecInterestAm() {
		return recInterestAm;
	}
	public void setRecInterestAm(BigDecimal recInterestAm) {
		this.recInterestAm = recInterestAm;
	}
	public BigDecimal getProjInterestAm() {
		return projInterestAm;
	}
	public void setProjInterestAm(BigDecimal projInterestAm) {
		this.projInterestAm = projInterestAm;
	}
	public Float getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
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
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
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
	public int getInvtDuration() {
		return invtDuration;
	}
	public void setInvtDuration(int invtDuration) {
		this.invtDuration = invtDuration;
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
	public String[] toStringArray() {
		
		String[] memberRaw = {
				new Long(groupInvtAcNo).toString(),
				new Long(groupAcNo).toString()
		};
		return memberRaw;
	}
	
	public static GroupInvtAcREST convertAccountToREST(GInvtAc account) {
		GroupInvtAcREST accountREST = new GroupInvtAcREST();

		accountREST.setGroupInvtAcNo(account.getGInvtAcNo());
		accountREST.setGroupAcNo(account.getGAc().getGAcNo());
		accountREST.setBankGroupAcNo(account.getBGroupAcNo());
		accountREST.setInvtBankAcNo(account.getInvtBankAcNo());
		accountREST.setInvestmentType(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, account.getInvestmentTypeId()));
		accountREST.setAccountStatus(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		accountREST.setApprovedByMember(account.getApprovedByMember());
		accountREST.setInvestmentNo(account.getInvestmentNo());
		accountREST.setInvestmentAcName(account.getInvestmentAcName());
		accountREST.setInvestmentDesc(account.getInvestmentDesc());
		accountREST.setInvtAm(account.getInvtAm());
		accountREST.setRecInvtAm(account.getRecInvtAm());
		accountREST.setRecInterestAm(account.getRecInterestAm());
		accountREST.setProjInterestAm(account.getProjInterestAm());
		accountREST.setInterestRate(account.getInterestRate());
		accountREST.setRequestedDate(DateUtil.convertDateToString(account.getRequestedDate()));
		accountREST.setApprovedDate(DateUtil.convertDateToString(account.getApprovedDate()));
		accountREST.setMaturityDate(DateUtil.convertDateToString(account.getMaturityDate()));
		accountREST.setClosureDate(DateUtil.convertDateToString(account.getClosureDate()));
		accountREST.setDevelopmentPlan(account.getDevelopmentPlan());
		accountREST.setInvtDuration(DateUtil.getDiffInMonths(account.getRequestedDate(), account.getMaturityDate()));
		accountREST.setAttachments(Attachment.buildAttachments(account.getAttachmentUrl()));
		
		return accountREST;
	}
}
