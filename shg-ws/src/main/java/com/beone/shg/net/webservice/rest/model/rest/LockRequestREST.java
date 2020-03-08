package com.beone.shg.net.webservice.rest.model.rest;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class LockRequestREST {
	
	private long memberAcNo;
	private String serviceName;
	private String action;
	private int lockTimeInMin;
	private String timeRemaining;
	
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getLockTimeInMin() {
		return lockTimeInMin;
	}
	public void setLockTimeInMin(int lockTimeInMin) {
		this.lockTimeInMin = lockTimeInMin;
	}
	public String getTimeRemaining() {
		return timeRemaining;
	}
	public void setTimeRemaining(String timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
}
