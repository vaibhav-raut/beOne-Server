package com.beone.udaan.mr.service.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ProductPricingEnum {

	private String enumName;
	private List<ProductPricingValue> enumValues;
	
	public ProductPricingEnum() {
		enumValues = new ArrayList<ProductPricingValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<ProductPricingValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<ProductPricingValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(ProductPricingValue enumValue) {
		this.enumValues.add(enumValue);
	}	
	
	public static class ProductPricingValue {

		// Fields    
		private int productPricingId;
		private String productPricing;
		private float wpPercent;
		private float mrPercent;
		private float vatPercent;
		private float otherTaxesPercent;
		public ProductPricingValue() {
			super();
		}
		public ProductPricingValue(int productPricingId, String productPricing,
				float wpPercent, float mrPercent, float vatPercent,
				float otherTaxesPercent) {
			super();
			this.productPricingId = productPricingId;
			this.productPricing = productPricing;
			this.wpPercent = wpPercent;
			this.mrPercent = mrPercent;
			this.vatPercent = vatPercent;
			this.otherTaxesPercent = otherTaxesPercent;
		}
		public ProductPricingValue(String productPricing, float wpPercent,
				float mrPercent, float vatPercent, float otherTaxesPercent) {
			super();
			this.productPricing = productPricing;
			this.wpPercent = wpPercent;
			this.mrPercent = mrPercent;
			this.vatPercent = vatPercent;
			this.otherTaxesPercent = otherTaxesPercent;
		}
		public int getProductPricingId() {
			return productPricingId;
		}
		public void setProductPricingId(int productPricingId) {
			this.productPricingId = productPricingId;
		}
		public String getProductPricing() {
			return productPricing;
		}
		public void setProductPricing(String productPricing) {
			this.productPricing = productPricing;
		}
		public float getWpPercent() {
			return wpPercent;
		}
		public void setWpPercent(float wpPercent) {
			this.wpPercent = wpPercent;
		}
		public float getMrPercent() {
			return mrPercent;
		}
		public void setMrPercent(float mrPercent) {
			this.mrPercent = mrPercent;
		}
		public float getVatPercent() {
			return vatPercent;
		}
		public void setVatPercent(float vatPercent) {
			this.vatPercent = vatPercent;
		}
		public float getOtherTaxesPercent() {
			return otherTaxesPercent;
		}
		public void setOtherTaxesPercent(float otherTaxesPercent) {
			this.otherTaxesPercent = otherTaxesPercent;
		}

	}
}
