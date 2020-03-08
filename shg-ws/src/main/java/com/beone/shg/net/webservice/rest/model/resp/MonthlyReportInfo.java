package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MonthlyReportInfo {

    private int monthlyReportId;
    private String reportName;
    private String reportDisplayName;
    private String reportDesc;
    private List<MonthlyReportSheetInfo> monthlyReportSheets;
	
	public MonthlyReportInfo() {
		super();
	}

	public MonthlyReportInfo(int monthlyReportId, String reportName,
			String reportDisplayName, String reportDesc) {
		super();
		this.monthlyReportId = monthlyReportId;
		this.reportName = reportName;
		this.reportDisplayName = reportDisplayName;
		this.reportDesc = reportDesc;
	}

	public MonthlyReportInfo(MonthlyReportInfo value) {
		super();
		this.monthlyReportId = value.monthlyReportId;
		this.reportName = value.reportName;
		this.reportDisplayName = value.reportDisplayName;
		this.reportDesc = value.reportDesc;
		this.monthlyReportSheets = new ArrayList<MonthlyReportSheetInfo>();
		
		for(MonthlyReportSheetInfo sheet : value.getMonthlyReportSheets()) {
			monthlyReportSheets.add(new MonthlyReportSheetInfo(sheet));
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

	public List<MonthlyReportSheetInfo> getMonthlyReportSheets() {
		return monthlyReportSheets;
	}

	public void setMonthlyReportSheets(
			List<MonthlyReportSheetInfo> monthlyReportSheets) {
		this.monthlyReportSheets = monthlyReportSheets;
	}

	public void addMonthlyReportSheet(MonthlyReportSheetInfo monthlyReportSheet) {
		if(this.monthlyReportSheets == null) {
			this.monthlyReportSheets = new ArrayList<MonthlyReportSheetInfo>();
		}
		this.monthlyReportSheets.add(monthlyReportSheet);
	}
	
	public void sort() {
		 Collections.sort(this.monthlyReportSheets, 
				 new Comparator<MonthlyReportSheetInfo>() {
			    @Override
			    public int compare(MonthlyReportSheetInfo a, MonthlyReportSheetInfo b) {
			     return (a.getMonthlyReportSheetId() - b.getMonthlyReportSheetId());
			    }
		 });
	}

	@Override
	public int hashCode() {
		int hashCode = 37;
		hashCode += (monthlyReportId * 17);
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return (monthlyReportId == ((MonthlyReportInfo)obj).monthlyReportId);
	}
}
