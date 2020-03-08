package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import com.beone.shg.net.persistence.util.DataUtil;

public class AcRecord {

	private String date;
	private String slipNo;
	private String txNo;
	private String particular;
	private String amount;
	private String receivedAm;
	private String paidAm;
	private String balance;
	private String txStatus;
	private Transaction transaction;
	private List<AcRecord> records;
	
	public AcRecord() {
		date = DataUtil.EMPTY_STRING;
		slipNo = DataUtil.EMPTY_STRING;
		txNo = DataUtil.EMPTY_STRING;
		particular = DataUtil.EMPTY_STRING;
		amount = DataUtil.EMPTY_STRING;
		receivedAm = DataUtil.EMPTY_STRING;
		paidAm = DataUtil.EMPTY_STRING;
		balance = DataUtil.EMPTY_STRING;
		txStatus = DataUtil.EMPTY_STRING;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSlipNo() {
		return slipNo;
	}
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	public String getTxNo() {
		return txNo;
	}
	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getReceivedAm() {
		return receivedAm;
	}
	public void setReceivedAm(String receivedAm) {
		this.receivedAm = receivedAm;
	}
	public String getPaidAm() {
		return paidAm;
	}
	public void setPaidAm(String paidAm) {
		this.paidAm = paidAm;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public List<AcRecord> getRecords() {
		return records;
	}
	public void setRecords(List<AcRecord> records) {
		this.records = records;
	}
	public void addRecord(AcRecord record) {
		if(this.records == null) {
			this.records = new ArrayList<AcRecord>();
		}
		this.records.add(record);
	}
}
