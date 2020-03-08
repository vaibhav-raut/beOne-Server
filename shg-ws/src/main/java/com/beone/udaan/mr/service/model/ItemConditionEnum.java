package com.beone.udaan.mr.service.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ItemConditionEnum {

	private String enumName;
	private List<ItemConditionValue> enumValues;
	
	public ItemConditionEnum() {
		enumValues = new ArrayList<ItemConditionValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<ItemConditionValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<ItemConditionValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(ItemConditionValue enumValue) {
		this.enumValues.add(enumValue);
	}
	
	public static class ItemConditionValue {
		
		private int itemConditionId;
		private String itemCondition;
		private String description;
		private float spDiscountPer;
		private float mrpDiscountPer;
		
		public ItemConditionValue() {
		}
		public ItemConditionValue(String itemCondition, String description,
				float spDiscountPer, float mrpDiscountPer) {
			super();
			this.itemCondition = itemCondition;
			this.description = description;
			this.spDiscountPer = spDiscountPer;
			this.mrpDiscountPer = mrpDiscountPer;
		}
		public ItemConditionValue(int itemConditionId, String itemCondition,
				String description, float spDiscountPer, float mrpDiscountPer) {
			super();
			this.itemConditionId = itemConditionId;
			this.itemCondition = itemCondition;
			this.description = description;
			this.spDiscountPer = spDiscountPer;
			this.mrpDiscountPer = mrpDiscountPer;
		}
		public int getItemConditionId() {
			return itemConditionId;
		}
		public void setItemConditionId(int itemConditionId) {
			this.itemConditionId = itemConditionId;
		}
		public String getItemCondition() {
			return itemCondition;
		}
		public void setItemCondition(String itemCondition) {
			this.itemCondition = itemCondition;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public float getSpDiscountPer() {
			return spDiscountPer;
		}
		public void setSpDiscountPer(float spDiscountPer) {
			this.spDiscountPer = spDiscountPer;
		}
		public float getMrpDiscountPer() {
			return mrpDiscountPer;
		}
		public void setMrpDiscountPer(float mrpDiscountPer) {
			this.mrpDiscountPer = mrpDiscountPer;
		}		
	}
}
