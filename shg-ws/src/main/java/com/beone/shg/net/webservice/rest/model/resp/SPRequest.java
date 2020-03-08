package com.beone.shg.net.webservice.rest.model.resp;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class SPRequest {

	private List<Long> groupAcNos; 
	private long startTime;
	private long endTime;
	private String backProcessDate;
	private List<String> poConstList;
	
	public List<Long> getGroupAcNos() {
		return groupAcNos;
	}
	public void setGroupAcNos(List<Long> groupAcNos) {
		this.groupAcNos = groupAcNos;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getBackProcessDate() {
		return backProcessDate;
	}
	public void setBackProcessDate(String backProcessDate) {
		this.backProcessDate = backProcessDate;
	}
	public List<String> getPoConstList() {
		return poConstList;
	}
	public void setPoConstList(List<String> poConstList) {
		this.poConstList = poConstList;
	}
	
}
