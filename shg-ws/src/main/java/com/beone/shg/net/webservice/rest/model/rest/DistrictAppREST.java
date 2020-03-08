package com.beone.shg.net.webservice.rest.model.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class DistrictAppREST {
	
	private long districtId;
	private List<GroupREST> groups;	
	private Map<String, String> displayNames;

	public DistrictAppREST(long districtId) {
		super();
		this.districtId = districtId;
	}
	public long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}
	public List<GroupREST> getGroups() {
		return groups;
	}
	public void setGroups(List<GroupREST> groups) {
		this.groups = groups;
	}
	public void addGroup(GroupREST group) {
		if(this.groups == null) {
			this.groups = new ArrayList<GroupREST>();
		}
		this.groups.add(group);
	}
	public Map<String, String> getDisplayNames() {
		return displayNames;
	}
	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}
}
