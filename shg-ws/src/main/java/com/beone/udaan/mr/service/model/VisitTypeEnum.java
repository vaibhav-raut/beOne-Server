package com.beone.udaan.mr.service.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class VisitTypeEnum {

	private String enumName;
	private List<VisitTypeValue> enumValues;

	public VisitTypeEnum() {
		enumValues = new ArrayList<VisitTypeValue>();
	}

	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<VisitTypeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<VisitTypeValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(VisitTypeValue enumValue) {
		this.enumValues.add(enumValue);
	}	

	public static class VisitTypeValue {

		// Fields    
		private int visitTypeId;
		private String visitType;
		private String visitFor;
		private String visitTypeDesc;
		public VisitTypeValue() {
			super();
		}
		public VisitTypeValue(String visitType, String visitFor,
				String visitTypeDesc) {
			super();
			this.visitType = visitType;
			this.visitFor = visitFor;
			this.visitTypeDesc = visitTypeDesc;
		}
		public VisitTypeValue(int visitTypeId, String visitType, String visitFor,
				String visitTypeDesc) {
			super();
			this.visitTypeId = visitTypeId;
			this.visitType = visitType;
			this.visitFor = visitFor;
			this.visitTypeDesc = visitTypeDesc;
		}
		public int getVisitTypeId() {
			return visitTypeId;
		}
		public void setVisitTypeId(int visitTypeId) {
			this.visitTypeId = visitTypeId;
		}
		public String getVisitType() {
			return visitType;
		}
		public void setVisitType(String visitType) {
			this.visitType = visitType;
		}
		public String getVisitFor() {
			return visitFor;
		}
		public void setVisitFor(String visitFor) {
			this.visitFor = visitFor;
		}
		public String getVisitTypeDesc() {
			return visitTypeDesc;
		}
		public void setVisitTypeDesc(String visitTypeDesc) {
			this.visitTypeDesc = visitTypeDesc;
		}
	}
}
