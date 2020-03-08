package com.beone.shg.net.webservice.rest.model.resp;

public class MonthlyReportSheetInfo {

	private int monthlyReportSheetId;
	private String reportSheetName;
	private String reportSheetTopTitle;
	private String reportSheetBottomTitle;
	private String sheetFormat;
	private String reportSheetDesc;
	private String reportSheetFormula;
	
	public MonthlyReportSheetInfo() {
		super();
	}

	public MonthlyReportSheetInfo(int monthlyReportSheetId,
			String reportSheetName, 
			String reportSheetTopTitle, 
			String reportSheetBottomTitle, 
			String sheetFormat, 
			String reportSheetDesc,
			String reportSheetFormula) {
		super();
		this.monthlyReportSheetId = monthlyReportSheetId;
		this.reportSheetName = reportSheetName;
		this.reportSheetTopTitle = reportSheetTopTitle;
		this.reportSheetBottomTitle = reportSheetBottomTitle;
		this.sheetFormat = sheetFormat;
		this.reportSheetDesc = reportSheetDesc;
		this.reportSheetFormula = reportSheetFormula;
	}

	public MonthlyReportSheetInfo(MonthlyReportSheetInfo value) {
		super();
		this.monthlyReportSheetId = value.monthlyReportSheetId;
		this.reportSheetName = value.reportSheetName;
		this.reportSheetTopTitle = value.reportSheetTopTitle;
		this.reportSheetBottomTitle = value.reportSheetBottomTitle;
		this.sheetFormat = value.sheetFormat;
		this.reportSheetDesc = value.reportSheetDesc;
	}

	public int getMonthlyReportSheetId() {
		return monthlyReportSheetId;
	}

	public void setMonthlyReportSheetId(int monthlyReportSheetId) {
		this.monthlyReportSheetId = monthlyReportSheetId;
	}

	public String getReportSheetName() {
		return reportSheetName;
	}

	public void setReportSheetName(String reportSheetName) {
		this.reportSheetName = reportSheetName;
	}

	public String getReportSheetTopTitle() {
		return reportSheetTopTitle;
	}

	public void setReportSheetTopTitle(String reportSheetTopTitle) {
		this.reportSheetTopTitle = reportSheetTopTitle;
	}

	public String getReportSheetBottomTitle() {
		return reportSheetBottomTitle;
	}

	public void setReportSheetBottomTitle(String reportSheetBottomTitle) {
		this.reportSheetBottomTitle = reportSheetBottomTitle;
	}

	public String getSheetFormat() {
		return sheetFormat;
	}

	public void setSheetFormat(String sheetFormat) {
		this.sheetFormat = sheetFormat;
	}

	public String getReportSheetDesc() {
		return reportSheetDesc;
	}

	public void setReportSheetDesc(String reportSheetDesc) {
		this.reportSheetDesc = reportSheetDesc;
	}

	public String getReportSheetFormula() {
		return reportSheetFormula;
	}

	public void setReportSheetFormula(String reportSheetFormula) {
		this.reportSheetFormula = reportSheetFormula;
	}

	@Override
	public int hashCode() {
		int hashCode = 37;
		hashCode += (monthlyReportSheetId * 17);
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return (monthlyReportSheetId == ((MonthlyReportSheetInfo)obj).monthlyReportSheetId);
	}
}
