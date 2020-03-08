package com.beone.shg.net.webservice.rest.model.resp;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.persistence.model.District;
import com.beone.shg.net.persistence.util.DataUtil;

@JsonSerialize(include = Inclusion.NON_NULL)
public class DistrictPS {

	private int districtId;
	private String state;
	private String division;
	private String district;
	private String districtCode;
	private int totalNoOfGroup;
	private int doneNoOfGroup;
	private long startTime;
	private long endTime;
	
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public int getTotalNoOfGroup() {
		return totalNoOfGroup;
	}
	public void setTotalNoOfGroup(int totalNoOfGroup) {
		this.totalNoOfGroup = totalNoOfGroup;
	}
	public int getDoneNoOfGroup() {
		return doneNoOfGroup;
	}
	public void setDoneNoOfGroup(int doneNoOfGroup) {
		this.doneNoOfGroup = doneNoOfGroup;
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
	public static DistrictPS buildDistrict(District district) {
		DistrictPS districtPS = new DistrictPS();
		
		districtPS.setDistrictId(district.getDistrictId());
		districtPS.setDistrict(district.getDistrict());
		districtPS.setDivision(district.getDivision());
		districtPS.setState(district.getState());
		districtPS.setDistrictCode(district.getDistrictCode());
		districtPS.setTotalNoOfGroup(district.getGroupCounter() - 1);
		districtPS.setDoneNoOfGroup(DataUtil.ZERO_INTEGER);
		districtPS.setStartTime(DataUtil.ZERO_LONG);
		districtPS.setEndTime(DataUtil.ZERO_LONG);
		
		return districtPS;
	}

}
