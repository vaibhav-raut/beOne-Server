package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MonthlyReportSheetsEnum {

	private String lang;
	private List<MonthlyReportSheetInfo> monthlyReportSheets;
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public List<MonthlyReportSheetInfo> getMonthlyReportSheets() {
		return monthlyReportSheets;
	}
	public void setMonthlyReportSheets(List<MonthlyReportSheetInfo> monthlyReportSheets) {
		this.monthlyReportSheets = monthlyReportSheets;
	}
	public void addMonthlyReportSheet(MonthlyReportSheetInfo monthlyReportSheet) {

		if(monthlyReportSheets == null) {
			monthlyReportSheets = new ArrayList<MonthlyReportSheetInfo>();
		}
		this.monthlyReportSheets.add(monthlyReportSheet);
	}	
}
