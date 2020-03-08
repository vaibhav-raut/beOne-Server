package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.util.DataUtil;

public class MProfilingTypeValue {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MProfilingTypeValue.class);
	private final static String OPTION_SEPERATOR = ";";
	private final static String OPTION_INTERNAL_SEPERATOR = ":";
	
	private int pointId;
	private String point;
	private String profileFor;
	private String category;
	private String valueType;
	private List<Option> options;
	
	public MProfilingTypeValue() {
	}
	public MProfilingTypeValue(String point, String profileFor, String category, String valueType,
			String options) {
		super();
		this.point = point;
		this.profileFor = profileFor;
		this.category = category;
		this.valueType = valueType;
		this.options = parseOptions(options);
	}
	public MProfilingTypeValue(int pointId, String point, String profileFor, String category,
			String valueType, String options) {
		super();
		this.pointId = pointId;
		this.point = point;
		this.profileFor = profileFor;
		this.category = category;
		this.valueType = valueType;
		this.options = parseOptions(options);
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getProfileFor() {
		return profileFor;
	}
	public void setProfileFor(String profileFor) {
		this.profileFor = profileFor;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public String pullOptionsString() {
		return getOptionsString(options);
	}
	public void pushOptionString(String options) {
		this.options = parseOptions(options);
	}

	public static class Option {
		private int optionId;
		private String option;
		private String category;
		public Option() {
			super();
		}
		public Option(int optionId, String option) {
			super();
			this.optionId = optionId;
			this.option = option;
			this.category = DataUtil.EMPTY_STRING;
		}
		public Option(int optionId, String option, String category) {
			super();
			this.optionId = optionId;
			this.option = option;
			this.category = category;
		}
		public int getOptionId() {
			return optionId;
		}
		public void setOptionId(int optionId) {
			this.optionId = optionId;
		}
		public String getOption() {
			return option;
		}
		public void setOption(String option) {
			this.option = option;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
	}

	protected static List<Option> parseOptions(String optionsStr) {
		
		if(optionsStr == null || optionsStr.isEmpty()) {
			return null;
		}
		
		String[] optionStr = optionsStr.split(OPTION_SEPERATOR);
		List<Option> options = new ArrayList<Option>(optionStr.length);
		
		for(String optionEntry : optionStr) {
			String[] optionValues = optionEntry.split(OPTION_INTERNAL_SEPERATOR);
			
			if(optionValues.length == 3) {
				options.add(new Option(Integer.parseInt(optionValues[0]), optionValues[1], optionValues[2]));
			} 
			else if(optionValues.length == 2) {
				options.add(new Option(Integer.parseInt(optionValues[0]), optionValues[1]));
			} 
			else if (optionValues.length == 0) {
				LOGGER.warn("Missing Formula: " + optionEntry);
			}
			else {
				LOGGER.error("Invalid Formula: " + optionEntry + "; with optionValues = " + optionValues.length);
			}
		}
		
		return options;
	}

	protected static String getOptionsString(List<Option> options) {
		
		if(options == null || options.isEmpty()) {
			return DataUtil.EMPTY_STRING;
		}
		
		StringBuilder sb = new StringBuilder();
		
		if(options != null) {
			for(Option option : options) {
				sb.append(option.getOptionId());
				sb.append(OPTION_INTERNAL_SEPERATOR);
				sb.append(option.getOption());
				
				if(!option.getCategory().isEmpty()) {
				sb.append(OPTION_INTERNAL_SEPERATOR);
				sb.append(option.getCategory());
				}
				sb.append(OPTION_SEPERATOR);
			}
		}
		
		return sb.toString();
	}
}
