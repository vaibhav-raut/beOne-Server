package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TodoTransactions {

	private long startTime;
	private long endTime;
	private int toPayMembersNo;
	private int paidMembersNo;
	private BigDecimal pendingAmount;
	private BigDecimal paidAmount;
	private BigDecimal appliedPenalty;
	private List<TodoTransaction> todoTransactions;
	private Map<String, String> displayNames;
	private Map<String, BigDecimal> ruleAmounts;
	private List<BankAccountShort> groupBankAcNos;
	private Set<String> txStatus;
	
	public TodoTransactions() {
		todoTransactions = new ArrayList<TodoTransaction>();
	}
	
	public TodoTransactions(int size) {
		todoTransactions = new ArrayList<TodoTransaction>(size);
	}
	
	public TodoTransactions(List<TodoTransaction> todoTransactions) {
		this.todoTransactions = todoTransactions;
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
	public int getToPayMembersNo() {
		return toPayMembersNo;
	}

	public void setToPayMembersNo(int toPayMembersNo) {
		this.toPayMembersNo = toPayMembersNo;
	}

	public int getPaidMembersNo() {
		return paidMembersNo;
	}

	public void setPaidMembersNo(int paidMembersNo) {
		this.paidMembersNo = paidMembersNo;
	}

	public BigDecimal getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(BigDecimal pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getAppliedPenalty() {
		return appliedPenalty;
	}

	public void setAppliedPenalty(BigDecimal appliedPenalty) {
		this.appliedPenalty = appliedPenalty;
	}

	public List<TodoTransaction> getTodoTransactions() {
		return todoTransactions;
	}

	public void setTodoTransactions(List<TodoTransaction> todoTransactions) {
		this.todoTransactions = todoTransactions;
	}
	
	public void addTodoTransactions(TodoTransaction todoTransaction) {
		this.todoTransactions.add(todoTransaction);
	}

	public Map<String, String> getDisplayNames() {
		return displayNames;
	}

	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}

	public void putDisplayName(String key, String displayName) {
		if(this.displayNames == null) {
			this.displayNames = new HashMap<String, String>();
		}
		this.displayNames.put(key, displayName);
	}

	public Map<String, BigDecimal> getRuleAmounts() {
		return ruleAmounts;
	}

	public void setRuleAmounts(Map<String, BigDecimal> ruleAmounts) {
		this.ruleAmounts = ruleAmounts;
	}

	public void addRuleAmount(String key, BigDecimal ruleAmount) {
		if(this.ruleAmounts == null) {
			this.ruleAmounts = new HashMap<String, BigDecimal>();
		}
		this.ruleAmounts.put(key, ruleAmount);
	}

	public List<BankAccountShort> getGroupBankAcNos() {
		return groupBankAcNos;
	}

	public void setGroupBankAcNos(List<BankAccountShort> groupBankAcNos) {
		this.groupBankAcNos = groupBankAcNos;
	}

	public void addGroupBankAcNo(BankAccountShort groupBankAcNo) {
		if(this.groupBankAcNos == null) {
			this.groupBankAcNos = new ArrayList<BankAccountShort>();
		}
		this.groupBankAcNos.add(groupBankAcNo);
	}

	public Set<String> getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Set<String> txStatus) {
		this.txStatus = txStatus;
	}

	public void addTxStatus(String txStatus) {
		if(this.txStatus == null) {
			this.txStatus = new HashSet<String>();
		}
		this.txStatus.add(txStatus);
	}
}
