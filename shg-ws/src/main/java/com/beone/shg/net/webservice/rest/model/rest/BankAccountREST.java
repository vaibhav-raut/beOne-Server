package com.beone.shg.net.webservice.rest.model.rest;

import java.util.ArrayList;
import java.util.List;

import com.beone.shg.net.webservice.rest.model.resp.Attachment;

public class BankAccountREST {
	private long bankAccountId;
	private long bankProfileId;
	private String accountNumber;
	private String accountName;
	private String bankAccountType;
	private String bankName;
	private String bankBranchName;
	private String ifcsCode;
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

}
