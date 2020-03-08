package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import com.beone.shg.net.persistence.util.DataUtil;

public class Rule {

	private String name;
	private String displayName;
	private String value;
	private String type;
	private String help;

	private List<Rule> details = new ArrayList<Rule>(0);
	
	public Rule() {
	}
	
	public Rule(String name, String displayName, String value, String type) {
		this.name = name;
		this.displayName = displayName;
		this.value = value;
		this.type = type;
		this.help = DataUtil.EMPTY_STRING;
	}
	
	public Rule(String name, String displayName, String value, String type, String help) {
		this.name = name;
		this.displayName = displayName;
		this.value = value;
		this.type = type;
		this.help = help;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public List<Rule> getDetails() {
		return details;
	}

	public void setDetails(List<Rule> details) {
		this.details = details;
	}

	public void addDetail(Rule detail) {
		this.details.add(detail);
	}
	
}
