package com.beone.udaan.mr.service.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MrTypeEnum {

	private String enumName;
	private List<MrTypeValue> enumValues;
	
	public MrTypeEnum() {
		enumValues = new ArrayList<MrTypeValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<MrTypeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<MrTypeValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(MrTypeValue enumValue) {
		this.enumValues.add(enumValue);
	}
	
	public static class MrTypeValue {

		// Fields    
		private int mrTypeId;
		private String mrType;
		private String description;
		private int registrationFee;
		private float depositCreditMultiplie;
		
		public MrTypeValue(String mrType, String description, int registrationFee,
				float depositCreditMultiplie) {
			super();
			this.mrType = mrType;
			this.description = description;
			this.registrationFee = registrationFee;
			this.depositCreditMultiplie = depositCreditMultiplie;
		}
		
		public MrTypeValue(int mrTypeId, String mrType, String description,
				int registrationFee, float depositCreditMultiplie) {
			super();
			this.mrTypeId = mrTypeId;
			this.mrType = mrType;
			this.description = description;
			this.registrationFee = registrationFee;
			this.depositCreditMultiplie = depositCreditMultiplie;
		}
		
		public int getMrTypeId() {
			return mrTypeId;
		}
		public void setMrTypeId(int mrTypeId) {
			this.mrTypeId = mrTypeId;
		}
		public String getMrType() {
			return mrType;
		}
		public void setMrType(String mrType) {
			this.mrType = mrType;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getRegistrationFee() {
			return registrationFee;
		}
		public void setRegistrationFee(int registrationFee) {
			this.registrationFee = registrationFee;
		}
		public float getDepositCreditMultiplie() {
			return depositCreditMultiplie;
		}
		public void setDepositCreditMultiplie(float depositCreditMultiplie) {
			this.depositCreditMultiplie = depositCreditMultiplie;
		}	
	}	
}
