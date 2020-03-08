package com.beone.shg.net.webservice.rest.model.resp;

import java.util.List;

import com.beone.shg.net.persistence.util.DataUtil;

public class MProfilingPoint {
	
	private int pointId;
	private String value;
	private String valueType;
	private List<Integer> options;	
	public MProfilingPoint() {
	}
	public MProfilingPoint(int pointId, String value, String valueType) {
		super();
		this.pointId = pointId;
		this.value = value;
		this.valueType = valueType;
		this.options = null;
	}
	public MProfilingPoint(int pointId, String valueType, List<Integer> options) {
		super();
		this.pointId = pointId;
		this.value = DataUtil.EMPTY_STRING;
		this.valueType = valueType;
		this.options = options;
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public List<Integer> getOptions() {
		return options;
	}
	public void setOptions(List<Integer> options) {
		this.options = options;
	}
}
