package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ProcessSchedulerRequest {

	private int processSchedulerId;
	private long schedulerKey;
	private long doneByMemberAcNo;
	private String status;
	private String processDate;
	private long startTime;
	private long endTime;
	private List<DistrictPS> districtPSs;
	
	public int getProcessSchedulerId() {
		return processSchedulerId;
	}
	public void setProcessSchedulerId(int processSchedulerId) {
		this.processSchedulerId = processSchedulerId;
	}
	public long getSchedulerKey() {
		return schedulerKey;
	}
	public void setSchedulerKey(long schedulerKey) {
		this.schedulerKey = schedulerKey;
	}
	public long getDoneByMemberAcNo() {
		return doneByMemberAcNo;
	}
	public void setDoneByMemberAcNo(long doneByMemberAcNo) {
		this.doneByMemberAcNo = doneByMemberAcNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
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
	public List<DistrictPS> getDistrictPSs() {
		return districtPSs;
	}
	public void setDistrictPSs(List<DistrictPS> districtPSs) {
		this.districtPSs = districtPSs;
	}
	public void addDistrictPS(DistrictPS districtPS) {
		if(this.districtPSs == null) {
			this.districtPSs = new ArrayList<DistrictPS>();
		}
		this.districtPSs.add(districtPS);
	}
}
