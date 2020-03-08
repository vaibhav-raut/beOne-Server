package com.beone.shg.net.webservice.rest.model.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;

public class GBankAccountREST {
	private long bankAccountId;
	private long bankProfileId;
	private String accountNumber;
	private String accountName;
	private String bankAccountType;
	private String bankName;
	private String bankBranchName;
	private String ifcsCode;
	private BigDecimal clearBalanceAm;
	private BigDecimal subjClearingBalanceAm;
	private BigDecimal verifiedBalanceAm;
	private BigDecimal interestAm;
	private BigDecimal penaltyChargesAm;
	private List<Attachment> attachments;
	
	public long getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public long getBankProfileId() {
		return bankProfileId;
	}
	public void setBankProfileId(long bankProfileId) {
		this.bankProfileId = bankProfileId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankAccountType() {
		return bankAccountType;
	}
	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getIfcsCode() {
		return ifcsCode;
	}
	public void setIfcsCode(String ifcsCode) {
		this.ifcsCode = ifcsCode;
	}
	public BigDecimal getClearBalanceAm() {
		return clearBalanceAm;
	}
	public void setClearBalanceAm(BigDecimal clearBalanceAm) {
		this.clearBalanceAm = clearBalanceAm;
	}
	public BigDecimal getSubjClearingBalanceAm() {
		return subjClearingBalanceAm;
	}
	public void setSubjClearingBalanceAm(BigDecimal subjClearingBalanceAm) {
		this.subjClearingBalanceAm = subjClearingBalanceAm;
	}
	public BigDecimal getVerifiedBalanceAm() {
		return verifiedBalanceAm;
	}
	public void setVerifiedBalanceAm(BigDecimal verifiedBalanceAm) {
		this.verifiedBalanceAm = verifiedBalanceAm;
	}
	public BigDecimal getInterestAm() {
		return interestAm;
	}
	public void setInterestAm(BigDecimal interestAm) {
		this.interestAm = interestAm;
	}
	public BigDecimal getPenaltyChargesAm() {
		return penaltyChargesAm;
	}
	public void setPenaltyChargesAm(BigDecimal penaltyChargesAm) {
		this.penaltyChargesAm = penaltyChargesAm;
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
	public Map<String,String> toStringInfo() {
		
		Map<String,String> info = new HashMap<String,String>();
		info.put("bankAccountId", Long.toString(bankAccountId));
		info.put("accountNumber", accountNumber);
		info.put("accountName", accountName);
		info.put("ifcsCode", ifcsCode);

		return info;
	}

	public static GBankAccountREST convertAccountToREST(GBankAccount account) {
		GBankAccountREST gBankAccount = new GBankAccountREST();
		
		gBankAccount.setBankAccountId(account.getBankAccountNo());
		gBankAccount.setBankProfileId(account.getBankAccount().getBankProfile().getGAcNo());
		gBankAccount.setAccountNumber(account.getBankAccount().getAccountNumber());
		gBankAccount.setAccountName(account.getBankAccount().getAccountName());
		gBankAccount.setBankAccountType(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, account.getBankAccount().getBankAccountTypeId()));
		gBankAccount.setBankName(account.getBankAccount().getBankProfile().getBankName());
		gBankAccount.setBankBranchName(account.getBankAccount().getBankProfile().getBranchName());
		gBankAccount.setIfcsCode(account.getBankAccount().getBankProfile().getIfcsCode());
		gBankAccount.setClearBalanceAm(account.getClearBalanceAm());
		gBankAccount.setSubjClearingBalanceAm(account.getSubjClearingBalanceAm());
		gBankAccount.setVerifiedBalanceAm(account.getVerifiedBalanceAm());
		gBankAccount.setInterestAm(account.getInterestAm());
		gBankAccount.setPenaltyChargesAm(account.getPenaltyChargesAm());
		gBankAccount.setAttachments(Attachment.buildAttachments(account.getBankAccount().getAttachmentUrl()));
		
		return gBankAccount;
	}
}
