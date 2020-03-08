package com.beone.shg.net.webservice.rest.model.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CSVImportResult {
	
	private List<String> columns;
	private List<Map<String,String>> resultList;
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public void addColumn(String column) {
		if(this.columns == null) {
			this.columns = new ArrayList<String>();
		}
		this.columns.add(column);
	}
	public List<Map<String, String>> getResultList() {
		return resultList;
	}
	public void setResultList(List<Map<String, String>> resultList) {
		this.resultList = resultList;
	}
	public void addResult(Map<String, String> result) {
		if(this.resultList == null) {
			this.resultList = new ArrayList<Map<String, String>>();
		}
		this.resultList.add(result);
	}
}
