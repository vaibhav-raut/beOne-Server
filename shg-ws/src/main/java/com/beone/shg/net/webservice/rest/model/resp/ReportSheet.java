package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReportSheet {

	private String topTitle;
	private String bottomTitle;
	private String totalAmount;
	private BigDecimal totalAmountBD;
	private List<Record> records;
	
	public ReportSheet() {
		records = new ArrayList<Record>();
	}
	public String getTopTitle() {
		return topTitle;
	}
	public void setTopTitle(String topTitle) {
		this.topTitle = topTitle;
	}
	public String getBottomTitle() {
		return bottomTitle;
	}
	public void setBottomTitle(String bottomTitle) {
		this.bottomTitle = bottomTitle;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getTotalAmountBD() {
		return totalAmountBD;
	}
	public void setTotalAmountBD(BigDecimal totalAmountBD) {
		this.totalAmountBD = totalAmountBD;
	}
	public ReportSheet(int noOfRecords) {
		records = new ArrayList<Record>(noOfRecords);
	}
	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	public void addRecord(Record record) {
		this.records.add(record);
	}
}
