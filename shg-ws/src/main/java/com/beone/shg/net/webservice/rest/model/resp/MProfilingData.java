package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


@JsonSerialize(include = Inclusion.NON_NULL)
public class MProfilingData {
	
	private final static String OPTION_SEPERATOR = ";";
	private final static String OPTION_INTERNAL_SEPERATOR = ":";
	private final static String OPTION_MULTI_SEPERATOR = "#";

	private final static String VALUE_BOOL = "bool";
	private final static String VALUE_INT = "int";
	private final static String VALUE_FLOAT = "float";
	private final static String VALUE_STRING = "string";
	private final static String VALUE_SINGLE_OPTION = "single_option";
	private final static String VALUE_MULTI_OPTION = "multi_option";

	private final static String VALUE_BOOL_PARSE = "b";
	private final static String VALUE_INT_PARSE = "i";
	private final static String VALUE_FLOAT_PARSE = "f";
	private final static String VALUE_STRING_PARSE = "s";
	private final static String VALUE_SINGLE_OPTION_PARSE = "o";
	private final static String VALUE_MULTI_OPTION_PARSE = "m";

	private long memberAcNo;
	private List<MProfilingPoint> points;
	
	public MProfilingData() {
		this.points = new ArrayList<MProfilingPoint>();
	}
	public MProfilingData(long memberAcNo, byte[] rawData) {
		this.memberAcNo = memberAcNo;
		if(rawData != null && rawData.length > 0) {
			this.points = parsePoints(new String(rawData));
		} else {
			this.points = new ArrayList<MProfilingPoint>();
		}
	}
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public List<MProfilingPoint> getPoints() {
		return points;
	}
	public void setPoints(List<MProfilingPoint> points) {
		this.points = points;
	}
	public byte[] getPointsInBytes() {
		return getOptionsString(points).getBytes();
	}

	protected static List<MProfilingPoint> parsePoints(String profileStr) {
		
		String[] profileStrs = profileStr.split(OPTION_SEPERATOR);
		List<MProfilingPoint> points = new ArrayList<MProfilingPoint>(profileStrs.length);
		
		for(String pointEntry : profileStrs) {
			String[] pointValues = pointEntry.split(OPTION_INTERNAL_SEPERATOR);
			
			if(pointValues.length == 3) {
				
				switch(pointValues[1]) {
				
				case VALUE_BOOL_PARSE :
					points.add(new MProfilingPoint(Integer.parseInt(pointValues[0]), pointValues[2], VALUE_BOOL));
					break;
					
				case VALUE_INT_PARSE :
					points.add(new MProfilingPoint(Integer.parseInt(pointValues[0]), pointValues[2], VALUE_INT));
					break;
					
				case VALUE_FLOAT_PARSE :
					points.add(new MProfilingPoint(Integer.parseInt(pointValues[0]), pointValues[2], VALUE_FLOAT));
					break;
					
				case VALUE_STRING_PARSE :
					points.add(new MProfilingPoint(Integer.parseInt(pointValues[0]), pointValues[2], VALUE_STRING));
					break;
					
				case VALUE_SINGLE_OPTION_PARSE :
					points.add(new MProfilingPoint(Integer.parseInt(pointValues[0]), VALUE_SINGLE_OPTION, getIntList(pointValues[2], false)));
					break;
					
				case VALUE_MULTI_OPTION_PARSE :
					points.add(new MProfilingPoint(Integer.parseInt(pointValues[0]), VALUE_MULTI_OPTION, getIntList(pointValues[2], true)));
					break;					
				}			
			} 
		}
		
		return points;
	}
	
	protected static String getOptionsString(List<MProfilingPoint> points) {
		StringBuilder sb = new StringBuilder();
		
		if(points != null) {
			for(MProfilingPoint point : points) {
				sb.append(point.getPointId());
				sb.append(OPTION_INTERNAL_SEPERATOR);
				
				switch(point.getValueType()) {
				
				case VALUE_BOOL :
					sb.append(VALUE_BOOL_PARSE);
					sb.append(OPTION_INTERNAL_SEPERATOR);
					sb.append(point.getValue());
					break;
					
				case VALUE_INT :
					sb.append(VALUE_INT_PARSE);
					sb.append(OPTION_INTERNAL_SEPERATOR);
					sb.append(point.getValue());
					break;
					
				case VALUE_FLOAT :
					sb.append(VALUE_FLOAT_PARSE);
					sb.append(OPTION_INTERNAL_SEPERATOR);
					sb.append(point.getValue());
					break;
					
				case VALUE_STRING :
					sb.append(VALUE_STRING_PARSE);
					sb.append(OPTION_INTERNAL_SEPERATOR);
					sb.append(point.getValue());
					break;
					
				case VALUE_SINGLE_OPTION :
					sb.append(VALUE_SINGLE_OPTION_PARSE);
					sb.append(OPTION_INTERNAL_SEPERATOR);
					sb.append(getStringFromList(point.getOptions()));
					break;
					
				case VALUE_MULTI_OPTION :
					sb.append(VALUE_MULTI_OPTION_PARSE);
					sb.append(OPTION_INTERNAL_SEPERATOR);
					sb.append(getStringFromList(point.getOptions()));
					break;					
				}			
								
				sb.append(OPTION_SEPERATOR);
			}
		}
		
		return sb.toString();
	}

	protected static List<Integer> getIntList(String str, boolean multi) {
		List<Integer> list = new ArrayList<Integer>();

		if(multi) {
			String[] optionsStrs = str.split(OPTION_MULTI_SEPERATOR);
			if(optionsStrs != null) {
				for(String option: optionsStrs) {
					list.add(Integer.parseInt(option));
				}
			}
		} else {
			list.add(Integer.parseInt(str));
		}
		return list;
	}

	protected static String getStringFromList(List<Integer> options) {
		StringBuilder sb = new StringBuilder();

		if(options != null) {
			boolean firstOptionDone = false;
			for(Integer option: options) {
				if(firstOptionDone) {
					sb.append(OPTION_MULTI_SEPERATOR);
				}
				sb.append(option.toString());
				firstOptionDone = true;
			}
		}
		return sb.toString();
	}
}
