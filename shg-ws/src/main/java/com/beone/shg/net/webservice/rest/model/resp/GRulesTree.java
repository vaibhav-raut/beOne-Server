package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GRulesTree {

	private long groupAcNo;
	private List<Rule> rules;
	private boolean associatedMAllowed;
	private boolean associatedRulesDiff;
	
	public GRulesTree() {
		associatedMAllowed = false;
		associatedRulesDiff = false;	
	}
	public GRulesTree(long groupAcNo) {
		this.groupAcNo = groupAcNo;
		associatedMAllowed = false;
		associatedRulesDiff = false;	
	}
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	public void addRule(Rule rule) {
		if(rules == null) {
			rules = new ArrayList<Rule>();
		}
		this.rules.add(rule);
	}
	public boolean isAssociatedMAllowed() {
		return associatedMAllowed;
	}
	public void setAssociatedMAllowed(boolean associatedMAllowed) {
		this.associatedMAllowed = associatedMAllowed;
	}
	public boolean isAssociatedRulesDiff() {
		return associatedRulesDiff;
	}
	public void setAssociatedRulesDiff(boolean associatedRulesDiff) {
		this.associatedRulesDiff = associatedRulesDiff;
	}
}
