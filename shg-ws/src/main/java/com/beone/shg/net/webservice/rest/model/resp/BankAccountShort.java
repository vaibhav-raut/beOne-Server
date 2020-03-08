package com.beone.shg.net.webservice.rest.model.resp;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class BankAccountShort {

	private long bankAcNo;
	private long bankGroupAcNo;
	private String displayAccount;
	private String bankAccountType;
	public BankAccountShort(long bankAcNo, long bankGroupAcNo,
			String displayAccount, String bankAccountType) {
		super();
		this.bankAcNo = bankAcNo;
		this.bankGroupAcNo = bankGroupAcNo;
		this.displayAccount = displayAccount;
		this.bankAccountType = bankAccountType;
	}
	public long getBankAcNo() {
		return bankAcNo;
	}
	public void setBankAcNo(long bankAcNo) {
		this.bankAcNo = bankAcNo;
	}
	public long getBankGroupAcNo() {
		return bankGroupAcNo;
	}
	public void setBankGroupAcNo(long bankGroupAcNo) {
		this.bankGroupAcNo = bankGroupAcNo;
	}
	public String getDisplayAccount() {
		return displayAccount;
	}
	public void setDisplayAccount(String displayAccount) {
		this.displayAccount = displayAccount;
	}
	public String getBankAccountType() {
		return bankAccountType;
	}
	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}
}
