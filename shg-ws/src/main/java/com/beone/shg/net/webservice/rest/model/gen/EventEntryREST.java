package com.beone.shg.net.webservice.rest.model.gen;

import java.util.List;

public class EventEntryREST {
    private String event;
    private List<String> selectColumns;
    private List<FilterEntryREST> filter;
    
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public List<String> getSelectColumns() {
		return selectColumns;
	}
	
	public void setSelectColumns(List<String> selectColumns) {
		this.selectColumns = selectColumns;
	}
	
	public List<FilterEntryREST> getFilter() {
		return filter;
	}
	
	public void setFilter(List<FilterEntryREST> filter) {
		this.filter = filter;
	}

}
