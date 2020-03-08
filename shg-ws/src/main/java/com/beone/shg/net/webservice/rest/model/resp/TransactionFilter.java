package com.beone.shg.net.webservice.rest.model.resp;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class TransactionFilter {

	private String lang;
	private long groupAcNo;
	private long startTime;
	private long endTime;
	private String txType;
	private String txStatus;
	private String paymentMode;
	private long memberAcNo;
	private long doneByMember;
	private long approvedByMember;
	private int rangeStart;
	private int limit;
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
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
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public long getDoneByMember() {
		return doneByMember;
	}
	public void setDoneByMember(long doneByMember) {
		this.doneByMember = doneByMember;
	}
	public long getApprovedByMember() {
		return approvedByMember;
	}
	public void setApprovedByMember(long approvedByMember) {
		this.approvedByMember = approvedByMember;
	}
	public int getRangeStart() {
		return rangeStart;
	}
	public void setRangeStart(int rangeStart) {
		this.rangeStart = rangeStart;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
