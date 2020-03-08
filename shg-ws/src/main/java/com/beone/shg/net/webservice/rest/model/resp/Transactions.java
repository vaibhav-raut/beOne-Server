package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Transactions {

	private long startTime;
	private long endTime;
	private List<Transaction> transactions;
	private List<BankAccountShort> groupBankAcNos;
	private Map<String, String> displayNames;
	
	public Transactions() {
		transactions = new ArrayList<Transaction>();
	}
	
	public Transactions(int size) {
		transactions = new ArrayList<Transaction>(size);
	}
	
	public Transactions(List<Transaction> transactions) {
		this.transactions = transactions;
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
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}

	public List<BankAccountShort> getGroupBankAcNos() {
		return groupBankAcNos;
	}

	public void setGroupBankAcNos(List<BankAccountShort> groupBankAcNos) {
		this.groupBankAcNos = groupBankAcNos;
	}

	public Map<String, String> getDisplayNames() {
		return displayNames;
	}

	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}
	
}
