package com.beone.udaan.mr.service.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class StatusEnum {

	private String statusName;
	private List<StatusValue> statusValues;
	
	public StatusEnum() {
		statusValues = new ArrayList<StatusValue>();
	}
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public List<StatusValue> getStatusValues() {
		return statusValues;
	}
	public void setStatusValues(List<StatusValue> statusValues) {
		this.statusValues = statusValues;
	}
	public void addStatusValue(StatusValue statusValue) {
		this.statusValues.add(statusValue);
	}	
	
	public static class StatusValue {

		private int statusIndex;
		private String statusValue;
		private String description;
		private String nextStatus;
		
		public StatusValue() {
			super();
		}
		public StatusValue(int statusIndex, String statusValue, String description,
				String nextStatus) {
			super();
			this.statusIndex = statusIndex;
			this.statusValue = statusValue;
			this.description = description;
			this.nextStatus = nextStatus;
		}
		public int getStatusIndex() {
			return statusIndex;
		}
		public void setStatusIndex(int statusIndex) {
			this.statusIndex = statusIndex;
		}
		public String getStatusValue() {
			return statusValue;
		}
		public void setStatusValue(String statusValue) {
			this.statusValue = statusValue;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getNextStatus() {
			return nextStatus;
		}
		public void setNextStatus(String nextStatus) {
			this.nextStatus = nextStatus;
		}
		
	}
}
