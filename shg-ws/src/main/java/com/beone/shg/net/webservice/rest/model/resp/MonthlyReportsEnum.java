package com.beone.shg.net.webservice.rest.model.resp;

import java.util.LinkedHashSet;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MonthlyReportsEnum {

	private String lang;
	private Set<MonthlyReportInfo> monthlyReports;
	
	public MonthlyReportsEnum() {
		super();
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Set<MonthlyReportInfo> getMonthlyReports() {
		return monthlyReports;
	}
	public void setMonthlyReports(LinkedHashSet<MonthlyReportInfo> monthlyReports) {
		this.monthlyReports = monthlyReports;
	}
	public void addMonthlyReport(MonthlyReportInfo monthlyReport) {

		if(monthlyReports == null) {
			monthlyReports = new LinkedHashSet<MonthlyReportInfo>();
		}
		this.monthlyReports.add(monthlyReport);
	}
}
