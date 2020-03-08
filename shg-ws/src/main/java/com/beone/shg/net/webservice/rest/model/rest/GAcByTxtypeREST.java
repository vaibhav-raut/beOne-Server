package com.beone.shg.net.webservice.rest.model.rest;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GAcByTxtypeREST {
	
	private long groupAcNo;
	private String txType;
	private BigDecimal amount;
	
	public GAcByTxtypeREST() {
		super();
	}
	public GAcByTxtypeREST(long groupAcNo, String txType, BigDecimal amount) {
		super();
		this.groupAcNo = groupAcNo;
		this.txType = txType;
		this.amount = amount;
	}
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}	
}
