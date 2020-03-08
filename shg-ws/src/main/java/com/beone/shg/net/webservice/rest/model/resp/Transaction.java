package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Transaction {

	private long txId;
	private long relatedTxTodoId;
	private String txType;
	private String slipType;
	private String txWith;
	private String slipNo;
	private long memberAcNo;
	private long savingAcNo;
	private long memberLoanAcNo;
	private long groupLoanAcNo;
	private long groupInvtAcNo;
	private String txAcType;
	private long groupAcNo;
	private long externalGroupAcNo;
	private long doneByMemberAcNo;
	private long approvedByMemberAcNo;
	private String memberName;
	private String doneByMemberName;
	private String approvedByMemberName;
	private BigDecimal amount;
	private String paymentMode;
	private String chequeNo;
	private String reasonToUndo;
	private long groupBankAcNo;
	private long memberBankAcNo;
	private long externalGroupBankAcNo;
	private BankAccountShort groupBankAcDisplay;
	private BankAccountShort memberBankAcDisplay;
	private BankAccountShort externalGroupBankAcDisplay;
	private String status;
	private String description;
	private String location;	
	private long paymentTs;
	private long entryTs;
	private long approvedTs;
	private BigDecimal clearBalance;
	private List<Attachment> attachments;
	private List<BankAccountShort> memberBankAcNos;
	
	public long getTxId() {
		return txId;
	}
	public void setTxId(long txId) {
		this.txId = txId;
	}
	public long getRelatedTxTodoId() {
		return relatedTxTodoId;
	}
	public void setRelatedTxTodoId(long relatedTxTodoId) {
		this.relatedTxTodoId = relatedTxTodoId;
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
	public String getTxWith() {
		return txWith;
	}
	public void setTxWith(String txWith) {
		this.txWith = txWith;
	}
	public String getSlipNo() {
		return slipNo;
	}
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public long getSavingAcNo() {
		return savingAcNo;
	}
	public void setSavingAcNo(long savingAcNo) {
		this.savingAcNo = savingAcNo;
	}
	public long getMemberLoanAcNo() {
		return memberLoanAcNo;
	}
	public void setMemberLoanAcNo(long memberLoanAcNo) {
		this.memberLoanAcNo = memberLoanAcNo;
	}
	public long getGroupLoanAcNo() {
		return groupLoanAcNo;
	}
	public void setGroupLoanAcNo(long groupLoanAcNo) {
		this.groupLoanAcNo = groupLoanAcNo;
	}
	public long getGroupInvtAcNo() {
		return groupInvtAcNo;
	}
	public void setGroupInvtAcNo(long groupInvtAcNo) {
		this.groupInvtAcNo = groupInvtAcNo;
	}
	public String getTxAcType() {
		return txAcType;
	}
	public void setTxAcType(String txAcType) {
		this.txAcType = txAcType;
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
	public long getDoneByMemberAcNo() {
		return doneByMemberAcNo;
	}
	public void setDoneByMemberAcNo(long doneByMemberAcNo) {
		this.doneByMemberAcNo = doneByMemberAcNo;
	}
	public long getApprovedByMemberAcNo() {
		return approvedByMemberAcNo;
	}
	public void setApprovedByMemberAcNo(long approvedByMemberAcNo) {
		this.approvedByMemberAcNo = approvedByMemberAcNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getDoneByMemberName() {
		return doneByMemberName;
	}
	public void setDoneByMemberName(String doneByMemberName) {
		this.doneByMemberName = doneByMemberName;
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
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getReasonToUndo() {
		return reasonToUndo;
	}
	public void setReasonToUndo(String reasonToUndo) {
		this.reasonToUndo = reasonToUndo;
	}
	public long getGroupBankAcNo() {
		return groupBankAcNo;
	}
	public void setGroupBankAcNo(long groupBankAcNo) {
		this.groupBankAcNo = groupBankAcNo;
	}
	public long getMemberBankAcNo() {
		return memberBankAcNo;
	}
	public void setMemberBankAcNo(long memberBankAcNo) {
		this.memberBankAcNo = memberBankAcNo;
	}
	public long getExternalGroupBankAcNo() {
		return externalGroupBankAcNo;
	}
	public void setExternalGroupBankAcNo(long externalGroupBankAcNo) {
		this.externalGroupBankAcNo = externalGroupBankAcNo;
	}
	public BankAccountShort getGroupBankAcDisplay() {
		return groupBankAcDisplay;
	}
	public void setGroupBankAcDisplay(BankAccountShort groupBankAcDisplay) {
		this.groupBankAcDisplay = groupBankAcDisplay;
	}
	public BankAccountShort getMemberBankAcDisplay() {
		return memberBankAcDisplay;
	}
	public void setMemberBankAcDisplay(BankAccountShort memberBankAcDisplay) {
		this.memberBankAcDisplay = memberBankAcDisplay;
	}
	public BankAccountShort getExternalGroupBankAcDisplay() {
		return externalGroupBankAcDisplay;
	}
	public void setExternalGroupBankAcDisplay(
			BankAccountShort externalGroupBankAcDisplay) {
		this.externalGroupBankAcDisplay = externalGroupBankAcDisplay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public long getPaymentTs() {
		return paymentTs;
	}
	public void setPaymentTs(long paymentTs) {
		this.paymentTs = paymentTs;
	}
	public long getEntryTs() {
		return entryTs;
	}
	public void setEntryTs(long entryTs) {
		this.entryTs = entryTs;
	}
	public long getApprovedTs() {
		return approvedTs;
	}
	public void setApprovedTs(long approvedTs) {
		this.approvedTs = approvedTs;
	}
	public BigDecimal getClearBalance() {
		return clearBalance;
	}
	public void setClearBalance(BigDecimal clearBalance) {
		this.clearBalance = clearBalance;
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
	
	public Map<String,String> toStringArray() {
		
		Map<String,String> info = new HashMap<String,String>();
		info.put("txId", Long.toString(txId));
		info.put("memberAcNo", Long.toString(memberAcNo));
		info.put("TxTpye", txType);
		
		return info;
	}
	
	public static boolean isTransactionCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("Payment Date") &&
				csvData.isColumnPresent("Receipt No") &&
				csvData.isColumnPresent("Transaction Type") &&
				csvData.isColumnPresent("Member") &&
				csvData.isColumnPresent("Payment Mode") &&
				csvData.isColumnPresent("Amount") &&
				csvData.isColumnPresent("Bank Ac No"));
	}
	
	public static Tx buildTransaction(long groupAcNo, CSVDataCollector csvData, int row, GroupInfoCollector collector) throws BadRequestException {
		
		Tx tx = new Tx();
		
		tx.setGroupAcNo(groupAcNo);
		String[] memberNo = csvData.getCellValue("Member", row).split(" - ");
		tx.setMemberAcNo(collector.getMemberAc(memberNo[1]));
		String[] groupBankAc = csvData.getCellValue("Bank Ac No", row).split(" - ");
		tx.setGroupBankAccount(collector.getBankAcNo(groupBankAc[2]));

		tx.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Auto_Approved));
		tx.setPaymentModeId(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, csvData.getCellValue("Payment Mode", row)));
		tx.setTxTypeId(EnumCache.getIndexOfTxType(csvData.getCellValue("Transaction Type", row)));
		tx.setReceiptVoucherNo(csvData.getCellValue("Receipt No", row));
		tx.setAmount(DataUtil.toBigDecimal(csvData.getCellValue("Amount", row)));

		tx.setPaymentTs(DateUtil.convertStringToDate(csvData.getCellValue("Payment Date", row)));
		tx.setEntryTs(DateUtil.convertStringToDate(csvData.getCellValue("Payment Date", row)));
		tx.setApprovedTs(DateUtil.convertStringToDate(csvData.getCellValue("Payment Date", row)));

		tx.setDoneByMemberAcNo(EnumConst.SystemAcNo);
		tx.setApprovedByMemberAcNo(EnumConst.SystemAcNo);
		tx.setDescription("Old Transaction Added on: " + DateUtil.getCurrentDisplayDateStr());
		tx.setChequeNo(DataUtil.EMPTY_STRING);
		tx.setEntryLocation(DataUtil.EMPTY_STRING);

//		tx.set*bankGroupAcNo(csvData.getCellValue("", row));
//		tx.set*bankGroupBankAccount(csvData.getCellValue("", row));
//		tx.set*memberLoanAcNo(csvData.getCellValue("", row));
//		tx.set*memberSavingAcNo(csvData.getCellValue("", row));
//		tx.set*groupInvtAcNo(csvData.getCellValue("", row));
//		tx.set*groupLoanAcNo(csvData.getCellValue("", row));

		return tx;
	}	
	
	public static String getLocation(String str) {
		if(str != null && !str.isEmpty()) {
			String[] parts = str.split(DBConst.ATTACH_ADDED_DILIMITER);
			if(parts != null && parts.length > 0) {
				return parts[0];
			}
		}
		return DataUtil.EMPTY_STRING;
	}
	
	public static List<Attachment> getAttachments(String str) {
		List<Attachment> attachments = new ArrayList<Attachment>(0);
		
		if(str != null && !str.isEmpty()) {
			String[] parts = str.split(DBConst.ATTACH_ADDED_DILIMITER);
			if(parts != null && parts.length > 1 && !parts[1].isEmpty()) {
				attachments = Attachment.buildAttachments(parts[1]);
			}
		}
		return attachments;
	}
}
