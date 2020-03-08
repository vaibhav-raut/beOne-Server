package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

public class MWiseDashboard {

	private String langauge;
	private List<MDashboardSheet> mDashboard;
	private int currentPageIndex;
	private int noOfPages;
	
	public MWiseDashboard() {
		mDashboard = new ArrayList<MDashboardSheet>();
	}
	public MWiseDashboard(int noOfRecords) {
		mDashboard = new ArrayList<MDashboardSheet>(noOfRecords);
	}
	public String getLangauge() {
		return langauge;
	}
	public void setLangauge(String langauge) {
		this.langauge = langauge;
	}
	public List<MDashboardSheet> getMDashboard() {
		return mDashboard;
	}
	public void setMDashboard(List<MDashboardSheet> mDashboard) {
		this.mDashboard = mDashboard;
	}
	public void addMDashboard(MDashboardSheet dashboard) {
		this.mDashboard.add(dashboard);
	}
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}
	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}
	public int getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}
}
