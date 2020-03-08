package com.beone.shg.net.persistence.support;

import java.util.ArrayList;
import java.util.List;

import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportInfo;
import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportSheetInfo;

public class MonthlyReportFormula {

    private int monthlyReportId;
    private String reportName;
    private String reportDisplayName;
    private String reportDesc;
    private List<MonthlyReportSheetFormula> monthlyReportSheets;
	
	public MonthlyReportFormula(MonthlyReportInfo value) {
	    monthlyReportId = value.getMonthlyReportId();
	    reportName = value.getReportName();
	    reportDisplayName = value.getReportDisplayName();
	    reportDesc = value.getReportDesc();
	    
		monthlyReportSheets = new ArrayList<MonthlyReportSheetFormula>();
	    for(MonthlyReportSheetInfo sheet: value.getMonthlyReportSheets()) {
	    	monthlyReportSheets.add(new MonthlyReportSheetFormula(sheet));
	    }
	}

	public int getMonthlyReportId() {
		return monthlyReportId;
	}

	public void setMonthlyReportId(int monthlyReportId) {
		this.monthlyReportId = monthlyReportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportDisplayName() {
		return reportDisplayName;
	}

	public void setReportDisplayName(String reportDisplayName) {
		this.reportDisplayName = reportDisplayName;
	}

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	public List<MonthlyReportSheetFormula> getMonthlyReportSheets() {
		return monthlyReportSheets;
	}

	public void setMonthlyReportSheets(
			List<MonthlyReportSheetFormula> monthlyReportSheets) {
		this.monthlyReportSheets = monthlyReportSheets;
	}

	public void addMonthlyReportSheet(MonthlyReportSheetFormula monthlyReportSheet) {
		if(monthlyReportSheets == null) {
			monthlyReportSheets = new ArrayList<MonthlyReportSheetFormula>();
		}
		
		monthlyReportSheets.add(monthlyReportSheet);
	}
}
