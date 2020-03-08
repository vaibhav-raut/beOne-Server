package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

public class DashboardSheet {

	private String langauge;
	private List<Record> headerRecords;
	private List<Record> records;
	private String groupName;
	private long memberCount;
	private float groupCreditRating;
	private String monthlySaving;
	private String currentBalance;
	private String totalSaving;
	
	public DashboardSheet() {
		headerRecords = new ArrayList<Record>();
		records = new ArrayList<Record>();
	}
	public DashboardSheet(int noOfRecords) {
		headerRecords = new ArrayList<Record>();
		records = new ArrayList<Record>(noOfRecords);
	}
	public String getLangauge() {
		return langauge;
	}
	public void setLangauge(String langauge) {
		this.langauge = langauge;
	}
	public List<Record> getHeaderRecords() {
		return headerRecords;
	}
	public void setHeaderRecords(List<Record> headerRecords) {
		this.headerRecords = headerRecords;
	}
	public void addHeaderRecord(Record headerRecord) {
		this.headerRecords.add(headerRecord);
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public long getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(long memberCount) {
		this.memberCount = memberCount;
	}
	public float getGroupCreditRating() {
		return groupCreditRating;
	}
	public void setGroupCreditRating(float groupCreditRating) {
		this.groupCreditRating = groupCreditRating;
	}
	public String getMonthlySaving() {
		return monthlySaving;
	}
	public void setMonthlySaving(String monthlySaving) {
		this.monthlySaving = monthlySaving;
	}
	public String getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}
	public String getTotalSaving() {
		return totalSaving;
	}
	public void setTotalSaving(String totalSaving) {
		this.totalSaving = totalSaving;
	}
}
