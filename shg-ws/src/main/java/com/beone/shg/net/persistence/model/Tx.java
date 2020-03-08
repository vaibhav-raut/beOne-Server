package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:18 PM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.beone.shg.net.persistence.support.EnumCache;

/**
 * MTx generated by hbm2java
 */
@Entity
@Table(name="tx"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class Tx  implements java.io.Serializable, Comparable<Tx> {

	// Fields    
	private static final long serialVersionUID = -5454319490471636897L;
	private long txId;
	private long groupAcNo;
	private long bankGroupAcNo;
	private TxTodo txTodo;
	private long counterTxId;
	private long memberAcNo;
	private int txStatusId;
	private long doneByMemberAcNo;
	private int paymentModeId;
	private long approvedByMemberAcNo;
	private int txTypeId;
	private long memberBankAccount;
	private int reasonToUndoId;
	private long groupBankAccount;
	private long bankGroupBankAccount;
	private long memberLoanAcNo;
	private long memberSavingAcNo;
	private long groupInvtAcNo;
	private long groupLoanAcNo;
	private String receiptVoucherNo;
	private String chequeNo;
	private BigDecimal amount;
	private Date paymentTs;
	private Date entryTs;
	private Date approvedTs;
	private String description;
	private String entryLocation;

	// Constructors

	/** default constructor */
	public Tx() {
	}

	/** minimal constructor */


	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="tx_id", unique=true, nullable=false, insertable=true, updatable=true)

	public long getTxId() {
		return this.txId;
	}

	public void setTxId(long txId) {
		this.txId = txId;
	}
	@Column(name="g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getGroupAcNo() {
		return this.groupAcNo;
	}

	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	@Column(name="b_g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getBankGroupAcNo() {
		return bankGroupAcNo;
	}

	public void setBankGroupAcNo(long bankGroupAcNo) {
		this.bankGroupAcNo = bankGroupAcNo;
	}

	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="tx_todo_id", unique=false, nullable=true, insertable=true, updatable=true)

	public TxTodo getTxTodo() {
		return this.txTodo;
	}

	public void setTxTodo(TxTodo txTodo) {
		this.txTodo = txTodo;
	}
	@Column(name="counter_tx_id", unique=false, nullable=true, insertable=true, updatable=true)

	public long getCounterTxId() {
		return counterTxId;
	}

	public void setCounterTxId(long counterTxId) {
		this.counterTxId = counterTxId;
	}
	@Column(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getMemberAcNo() {
		return this.memberAcNo;
	}

	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	@Column(name="tx_status_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getTxStatusId() {
		return this.txStatusId;
	}

	public void setTxStatusId(int txStatusId) {
		this.txStatusId = txStatusId;
	}
	@Column(name="done_by_m", unique=false, nullable=false, insertable=true, updatable=true)

	public long getDoneByMemberAcNo() {
		return this.doneByMemberAcNo;
	}

	public void setDoneByMemberAcNo(long doneByMemberAcNo) {
		this.doneByMemberAcNo = doneByMemberAcNo;
	}
	@Column(name="payment_mode_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getPaymentModeId() {
		return this.paymentModeId;
	}

	public void setPaymentModeId(int paymentModeId) {
		this.paymentModeId = paymentModeId;
	}
	@Column(name="approved_by_m", unique=false, nullable=true, insertable=true, updatable=true)

	public long getApprovedByMemberAcNo() {
		return this.approvedByMemberAcNo;
	}

	public void setApprovedByMemberAcNo(long approvedByMemberAcNo) {
		this.approvedByMemberAcNo = approvedByMemberAcNo;
	}
	@Column(name="tx_type_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getTxTypeId() {
		return this.txTypeId;
	}

	public void setTxTypeId(int txTypeId) {
		this.txTypeId = txTypeId;
	}
	@Column(name="m_bank_account", unique=false, nullable=true, insertable=true, updatable=true)

	public long getMemberBankAccount() {
		return this.memberBankAccount;
	}

	public void setMemberBankAccount(long memberBankAccount) {
		this.memberBankAccount = memberBankAccount;
	}
	@Column(name="reason_to_undo", unique=false, nullable=true, insertable=true, updatable=true)

	public int getReasonToUndoId() {
		return this.reasonToUndoId;
	}

	public void setReasonToUndoId(int reasonToUndoId) {
		this.reasonToUndoId = reasonToUndoId;
	}
	@Column(name="g_bank_account", unique=false, nullable=true, insertable=true, updatable=true)

	public long getGroupBankAccount() {
		return this.groupBankAccount;
	}

	public void setGroupBankAccount(long groupBankAccount) {
		this.groupBankAccount = groupBankAccount;
	}
	@Column(name="b_g_bank_account", unique=false, nullable=false, insertable=true, updatable=true)

	public long getBankGroupBankAccount() {
		return bankGroupBankAccount;
	}

	public void setBankGroupBankAccount(long bankGroupBankAccount) {
		this.bankGroupBankAccount = bankGroupBankAccount;
	}
	@Column(name="m_loan_ac_no", unique=false, nullable=true, insertable=true, updatable=true)

	public long getMemberLoanAcNo() {
		return this.memberLoanAcNo;
	}

	public void setMemberLoanAcNo(long memberLoanAcNo) {
		this.memberLoanAcNo = memberLoanAcNo;
	}
	@Column(name="m_saving_ac_no", unique=false, nullable=true, insertable=true, updatable=true)

	public long getMemberSavingAcNo() {
		return this.memberSavingAcNo;
	}

	public void setMemberSavingAcNo(long memberSavingAcNo) {
		this.memberSavingAcNo = memberSavingAcNo;
	}
	@Column(name="g_invt_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getGroupInvtAcNo() {
		return groupInvtAcNo;
	}

	public void setGroupInvtAcNo(long groupInvtAcNo) {
		this.groupInvtAcNo = groupInvtAcNo;
	}
	@Column(name="g_loan_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getGroupLoanAcNo() {
		return groupLoanAcNo;
	}

	public void setGroupLoanAcNo(long groupLoanAcNo) {
		this.groupLoanAcNo = groupLoanAcNo;
	}

	@Column(name="receipt_voucher_no", unique=false, nullable=true, insertable=true, updatable=true, length=12)

	public String getReceiptVoucherNo() {
		return this.receiptVoucherNo;
	}

	public void setReceiptVoucherNo(String receiptVoucherNo) {
		this.receiptVoucherNo = receiptVoucherNo;
	}
	@Column(name="cheque_no", unique=false, nullable=true, insertable=true, updatable=true, length=12)

	public String getChequeNo() {
		return this.chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	@Column(name="amount", unique=false, nullable=true, insertable=true, updatable=true, precision=16)

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Column(name="payment_ts", unique=false, nullable=true, insertable=true, updatable=true, length=19)

	public Date getPaymentTs() {
		return this.paymentTs;
	}

	public void setPaymentTs(Date paymentTs) {
		this.paymentTs = paymentTs;
	}
	@Column(name="entry_ts", unique=false, nullable=true, insertable=true, updatable=true, length=19)

	public Date getEntryTs() {
		return this.entryTs;
	}

	public void setEntryTs(Date entryTs) {
		this.entryTs = entryTs;
	}
	@Column(name="approved_ts", unique=false, nullable=true, insertable=true, updatable=true, length=19)

	public Date getApprovedTs() {
		return this.approvedTs;
	}

	public void setApprovedTs(Date approvedTs) {
		this.approvedTs = approvedTs;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		if(description != null && description.length() > 100) {
			this.description = description.substring(0, 99);
		} else {
			this.description = description;
		}
	}
	@Column(name="entry_location", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getEntryLocation() {
		return this.entryLocation;
	}

	public void setEntryLocation(String entryLocation) {
		this.entryLocation = entryLocation;
	}

	@Override
	public int compareTo(Tx o) {
		if(o == null) {
			return 1;
		}
		if (this.txId < o.txId) {
			return -1;
		} else if (this.txId > o.txId) {
			return 1;
		}
		return 0;
	}
		
	public Map<String,String> toStringInfo() {
		
		Map<String,String> info = new HashMap<String,String>();
		info.put("txId", Long.toString(txId));
		info.put("txTypeId", EnumCache.getNameOfTxType(txTypeId));
		return info;
	}
}