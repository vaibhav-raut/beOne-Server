package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MemberAcBook {

	private long memberAcNo;
	private List<MemberAcRecord> monthlyAcs;
	private Map<String, String> displayNames;
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public List<MemberAcRecord> getMonthlyAcs() {
		return monthlyAcs;
	}
	public void setMonthlyAcs(List<MemberAcRecord> monthlyAcs) {
		this.monthlyAcs = monthlyAcs;
	}
	public void addMonthlyAc(MemberAcRecord monthlyAc) {
		if(this.monthlyAcs == null) {
			this.monthlyAcs = new ArrayList<MemberAcRecord>();
		}
		this.monthlyAcs.add(monthlyAc);
	}
	public Map<String, String> getDisplayNames() {
		return displayNames;
	}
	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}
}
