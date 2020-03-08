package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class AccountBook {

	private String type;
	private long acNo;
	private long startTime;
	private long endTime;
	private int noOfTxs;
	private int noOfApprovedTxs;
	private BigDecimal totalReceivedAmount;
	private BigDecimal totalPaidAmount;
	private BigDecimal openingClearBalance;
	private BigDecimal openingBalanceSubjectedToClearing;
	private BigDecimal closingClearBalance;
	private BigDecimal closingBalanceSubjectedToClearing;
	private List<Transaction> transactions;
	private Map<String, String> displayNames;
	
	public AccountBook(){
		transactions = new ArrayList<Transaction>();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getAcNo() {
		return acNo;
	}
	public void setAcNo(long acNo) {
		this.acNo = acNo;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getNoOfTxs() {
		return noOfTxs;
	}

	public void setNoOfTxs(int noOfTxs) {
		this.noOfTxs = noOfTxs;
	}

	public int getNoOfApprovedTxs() {
		return noOfApprovedTxs;
	}

	public void setNoOfApprovedTxs(int noOfApprovedTxs) {
		this.noOfApprovedTxs = noOfApprovedTxs;
	}

	public BigDecimal getTotalReceivedAmount() {
		return totalReceivedAmount;
	}

	public void setTotalReceivedAmount(BigDecimal totalReceivedAmount) {
		this.totalReceivedAmount = totalReceivedAmount;
	}

	public BigDecimal getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	public BigDecimal getOpeningClearBalance() {
		return openingClearBalance;
	}
	public void setOpeningClearBalance(BigDecimal openingClearBalance) {
		this.openingClearBalance = openingClearBalance;
	}
	public BigDecimal getOpeningBalanceSubjectedToClearing() {
		return openingBalanceSubjectedToClearing;
	}
	public void setOpeningBalanceSubjectedToClearing(
			BigDecimal openingBalanceSubjectedToClearing) {
		this.openingBalanceSubjectedToClearing = openingBalanceSubjectedToClearing;
	}
	public BigDecimal getClosingClearBalance() {
		return closingClearBalance;
	}
	public void setClosingClearBalance(BigDecimal closingClearBalance) {
		this.closingClearBalance = closingClearBalance;
	}
	public BigDecimal getClosingBalanceSubjectedToClearing() {
		return closingBalanceSubjectedToClearing;
	}
	public void setClosingBalanceSubjectedToClearing(
			BigDecimal closingBalanceSubjectedToClearing) {
		this.closingBalanceSubjectedToClearing = closingBalanceSubjectedToClearing;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}

	public Map<String, String> getDisplayNames() {
		return displayNames;
	}

	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}
	
}
