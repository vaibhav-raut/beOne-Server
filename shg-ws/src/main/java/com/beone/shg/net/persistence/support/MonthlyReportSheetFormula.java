package com.beone.shg.net.persistence.support;

import com.beone.shg.net.persistence.ppo.util.PPOFormula;
import com.beone.shg.net.persistence.ppo.util.PPOFormulaUtil;
import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportSheetInfo;

public class MonthlyReportSheetFormula {

	private int monthlyReportSheetId;
	private String reportSheetName;
	private String reportSheetTopTitle;
	private String reportSheetBottomTitle;
	private String sheetFormat;
	private String reportSheetDesc;
	private PPOFormula reportSheetFormula;
	
	public MonthlyReportSheetFormula() {
		super();
	}

	public MonthlyReportSheetFormula(MonthlyReportSheetInfo value) {
		super();
		this.monthlyReportSheetId = value.getMonthlyReportSheetId();
		this.reportSheetName = value.getReportSheetName();
		this.reportSheetTopTitle = value.getReportSheetTopTitle();
		this.reportSheetBottomTitle = value.getReportSheetBottomTitle();
		this.sheetFormat = value.getSheetFormat();
		this.reportSheetDesc = value.getReportSheetDesc();
		this.reportSheetFormula = PPOFormulaUtil.parseUpdateFormula(value.getReportSheetFormula());
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

	public PPOFormula getReportSheetFormula() {
		return reportSheetFormula;
	}

	public void setReportSheetFormula(PPOFormula reportSheetFormula) {
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
		return (monthlyReportSheetId == ((MonthlyReportSheetFormula)obj).monthlyReportSheetId);
	}
}
