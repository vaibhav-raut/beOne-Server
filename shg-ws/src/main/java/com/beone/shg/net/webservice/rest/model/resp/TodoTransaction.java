package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.util.BDUtil;

@JsonSerialize(include = Inclusion.NON_NULL)
public class TodoTransaction {

	private long todoTxId;
	private List<Long> relatedTxIds;
	private String txType;
	private String slipType;
	private long memberAcNo;
	private long approvedByMemberAcNo;
	private long groupAcNo;
	private long externalGroupAcNo;
	private long instAcNo;
	private String txAcType;
	private String memberName;
	private String mRole;
	private String approvedByMemberName;
	private BigDecimal amount;
	private BigDecimal interestComponent;
	private String expectedPaymentMode;
	private String txTodoStatus;
	private BigDecimal penaltyAm;
	private BigDecimal penaltyPaidAm;
	private long dueDateTs;
	private long createdOnTs;
	private String description;
	private List<Transaction> transactions;
	private List<BankAccountShort> memberBankAcNos;
	
	public long getTodoTxId() {
		return todoTxId;
	}
	public void setTodoTxId(long todoTxId) {
		this.todoTxId = todoTxId;
	}
	public List<Long> getRelatedTxIds() {
		return relatedTxIds;
	}
	public void setRelatedTxIds(List<Long> relatedTxIds) {
		this.relatedTxIds = relatedTxIds;
	}
	public void addRelatedTxId(long relatedTxId) {
		if(this.relatedTxIds == null) {
			this.relatedTxIds = new ArrayList<Long>();
		}
		this.relatedTxIds.add(relatedTxId);
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public String getSlipType() {
		return slipType;
	}
	public void setSlipType(String slipType) {
		this.slipType = slipType;
	}
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public long getApprovedByMemberAcNo() {
		return approvedByMemberAcNo;
	}
	public void setApprovedByMemberAcNo(long approvedByMemberAcNo) {
		this.approvedByMemberAcNo = approvedByMemberAcNo;
	}
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	public long getExternalGroupAcNo() {
		return externalGroupAcNo;
	}
	public void setExternalGroupAcNo(long externalGroupAcNo) {
		this.externalGroupAcNo = externalGroupAcNo;
	}
	public long getInstAcNo() {
		return instAcNo;
	}
	public void setInstAcNo(long instAcNo) {
		this.instAcNo = instAcNo;
	}
	public String getTxAcType() {
		return txAcType;
	}
	public void setTxAcType(String txAcType) {
		this.txAcType = txAcType;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMRole() {
		return mRole;
	}
	public void setMRole(String mRole) {
		this.mRole = mRole;
	}
	public String getApprovedByMemberName() {
		return approvedByMemberName;
	}
	public void setApprovedByMemberName(String approvedByMemberName) {
		this.approvedByMemberName = approvedByMemberName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getInterestComponent() {
		return interestComponent;
	}
	public void setInterestComponent(BigDecimal interestComponent) {
		this.interestComponent = interestComponent;
	}
	public String getExpectedPaymentMode() {
		return expectedPaymentMode;
	}
	public void setExpectedPaymentMode(String expectedPaymentMode) {
		this.expectedPaymentMode = expectedPaymentMode;
	}
	public String getTxTodoStatus() {
		return txTodoStatus;
	}
	public void setTxTodoStatus(String txTodoStatus) {
		this.txTodoStatus = txTodoStatus;
	}
	public BigDecimal getPenaltyAm() {
		return penaltyAm;
	}
	public void setPenaltyAm(BigDecimal penaltyAm) {
		this.penaltyAm = penaltyAm;
	}
	public BigDecimal getPenaltyPaidAm() {
		return penaltyPaidAm;
	}
	public void setPenaltyPaidAm(BigDecimal penaltyPaidAm) {
		this.penaltyPaidAm = penaltyPaidAm;
	}
	public long getDueDateTs() {
		return dueDateTs;
	}
	public void setDueDateTs(long dueDateTs) {
		this.dueDateTs = dueDateTs;
	}
	public long getCreatedOnTs() {
		return createdOnTs;
	}
	public void setCreatedOnTs(long createdOnTs) {
		this.createdOnTs = createdOnTs;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public void addTransaction(Transaction transaction) {
		if(this.transactions == null) {
			this.transactions = new ArrayList<Transaction>();
		}
		this.transactions.add(transaction);
	}
	public List<BankAccountShort> getMemberBankAcNos() {
		return memberBankAcNos;
	}
	public void setMemberBankAcNos(List<BankAccountShort> memberBankAcNos) {
		this.memberBankAcNos = memberBankAcNos;
	}
	public void addMemberBankAcNo(BankAccountShort memberBankAcNo) {
		if(this.memberBankAcNos == null) {
			this.memberBankAcNos = new ArrayList<BankAccountShort>();
		}
		this.memberBankAcNos.add(memberBankAcNo);
	}
	
	protected static Transaction getTx(TodoTransaction todoTx) {
		Transaction tx = new Transaction();
		
		tx.setAmount(todoTx.getAmount());
		tx.setDescription(todoTx.getDescription());
		tx.setGroupAcNo(todoTx.getGroupAcNo());
		tx.setMemberAcNo(todoTx.getMemberAcNo());
		tx.setPaymentMode(todoTx.getExpectedPaymentMode());
		tx.setRelatedTxTodoId(todoTx.getTodoTxId());
		tx.setSlipType(todoTx.getSlipType());
		tx.setSlipNo("0");
		tx.setTxAcType(todoTx.getTxAcType());
		tx.setTxType(todoTx.getTxType());
		
		return tx;
	}
	
	public static List<Transaction> buildSavingTx(TodoTransaction todoTx, long doneBy, BigDecimal saving, 
			String slipNo, String paymentMode, long gBankAccountNo) {
    	List<Transaction> txs = new ArrayList<Transaction>();

    	if(todoTx.getTxType().equals(EnumConst.TxType_Saving_Installment)) {

    		Transaction tx = getTx(todoTx);
    		tx.setSavingAcNo(todoTx.getInstAcNo());
    		tx.setDoneByMemberAcNo(doneBy);
    		if(saving != null) {
    			tx.setAmount(saving);
    		}
    		if(slipNo != null && !slipNo.isEmpty()) {
    			tx.setSlipNo(slipNo);
    		}
    		if(paymentMode != null && !paymentMode.isEmpty()) {
    			tx.setPaymentMode(paymentMode);
    		}
    		if(gBankAccountNo > 0) {
    			tx.setGroupBankAcNo(gBankAccountNo);
    		}
    		txs.add(tx);
    	}
		
		return txs;
	}
	
	public static List<Transaction> buildLoanTx(TodoTransaction todoTx, long doneBy, BigDecimal principle, 
			BigDecimal interest, String slipNo, String paymentMode, long gBankAccountNo) {
    	List<Transaction> txs = new ArrayList<Transaction>();

    	if(todoTx.getTxType().equals(EnumConst.TxType_Loan_Installment)) {

    		{
    			Transaction tx1 = getTx(todoTx);
    			tx1.setTxType(EnumConst.TxType_Loan_Installment);
    			tx1.setMemberLoanAcNo(todoTx.getInstAcNo());
    			tx1.setDoneByMemberAcNo(doneBy);
    			if(principle == null) {
    				tx1.setAmount(BDUtil.sub(todoTx.getAmount(), todoTx.getInterestComponent()));
    			} else {
    				tx1.setAmount(principle);
    			}
    			if(slipNo != null && !slipNo.isEmpty()) {
    				tx1.setSlipNo(slipNo);
    			}
        		if(paymentMode != null && !paymentMode.isEmpty()) {
        			tx1.setPaymentMode(paymentMode);
        		}
        		if(gBankAccountNo > 0) {
        			tx1.setGroupBankAcNo(gBankAccountNo);
        		}
    			txs.add(tx1);
    		}

    		{
    			Transaction tx2 = getTx(todoTx);
    			tx2.setTxType(EnumConst.TxType_Loan_Interest_Installment);
    			tx2.setMemberLoanAcNo(todoTx.getInstAcNo());
    			tx2.setDoneByMemberAcNo(doneBy);
    			if(interest == null) {
    				tx2.setAmount(todoTx.getInterestComponent());
    			} else {
    				tx2.setAmount(interest);
    			}
    			if(slipNo != null && !slipNo.isEmpty()) {
    				tx2.setSlipNo(slipNo);
    			}
        		if(paymentMode != null && !paymentMode.isEmpty()) {
        			tx2.setPaymentMode(paymentMode);
        		}
        		if(gBankAccountNo > 0) {
        			tx2.setGroupBankAcNo(gBankAccountNo);
        		}
    			txs.add(tx2);
    		}
    	}

    	return txs;
	}
}
