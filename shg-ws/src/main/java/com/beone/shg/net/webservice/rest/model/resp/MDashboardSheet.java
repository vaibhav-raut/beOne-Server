package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

public class MDashboardSheet {

	private String langauge;
	private List<Record> headerRecords;
	private List<Record> records;
	
	public MDashboardSheet() {
		headerRecords = new ArrayList<Record>();
		records = new ArrayList<Record>();
	}
	public MDashboardSheet(int noOfRecords) {
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
}
