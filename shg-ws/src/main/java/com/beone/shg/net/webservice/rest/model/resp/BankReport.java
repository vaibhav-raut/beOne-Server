package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

public class BankReport {

	private String bankReportName;
	private String reportType;
	private String langauge;
	private String date;
	private List<ReportSheet> sheets;
	
	public BankReport() {
		sheets = new ArrayList<ReportSheet>();
	}	
	public BankReport(int noReportSheet) {
		sheets = new ArrayList<ReportSheet>(noReportSheet);
	}
	
	public String getBankReportName() {
		return bankReportName;
	}
	public void setBankReportName(String bankReportName) {
		this.bankReportName = bankReportName;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getLangauge() {
		return langauge;
	}
	public void setLangauge(String langauge) {
		this.langauge = langauge;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<ReportSheet> getSheets() {
		return sheets;
	}
	public void setSheets(List<ReportSheet> sheets) {
		this.sheets = sheets;
	}
	public void addSheet(ReportSheet sheet) {
		this.sheets.add(sheet);
	}

}
