package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

public class Record {

	private String name;
	private String value;
	private List<Record> details;
	
	public Record() {
	}
	
	public Record(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Record> getDetails() {
		return details;
	}

	public void setDetails(List<Record> details) {
		this.details = details;
	}

	public void addDetail(Record detail) {
		if(details == null) {
			details = new ArrayList<Record>();
		}
		this.details.add(detail);
	}
	
}
