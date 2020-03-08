package com.beone.udaan.mr.service.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ProductCategoryEnum {

	private String enumName;
	private List<ProductCategoryValue> enumValues;
	
	public ProductCategoryEnum() {
		enumValues = new ArrayList<ProductCategoryValue>();
	}
	
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<ProductCategoryValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<ProductCategoryValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(ProductCategoryValue enumValue) {
		this.enumValues.add(enumValue);
	}	
	
	public static class ProductCategoryValue {

		// Fields    
		private int productCategoryId;
		private String productCategory;
		private String subCategoryLevel1;
		private String subCategoryLevel2;
		private String subCategoryLevel3;
		private String subCategoryLevel4;
		
		public ProductCategoryValue() {
			super();
		}
		public ProductCategoryValue(int productCategoryId, String productCategory,
				String subCategoryLevel1, String subCategoryLevel2,
				String subCategoryLevel3, String subCategoryLevel4) {
			super();
			this.productCategoryId = productCategoryId;
			this.productCategory = productCategory;
			this.subCategoryLevel1 = subCategoryLevel1;
			this.subCategoryLevel2 = subCategoryLevel2;
			this.subCategoryLevel3 = subCategoryLevel3;
			this.subCategoryLevel4 = subCategoryLevel4;
		}
		public ProductCategoryValue(String productCategory, String subCategoryLevel1,
				String subCategoryLevel2, String subCategoryLevel3,
				String subCategoryLevel4) {
			super();
			this.productCategory = productCategory;
			this.subCategoryLevel1 = subCategoryLevel1;
			this.subCategoryLevel2 = subCategoryLevel2;
			this.subCategoryLevel3 = subCategoryLevel3;
			this.subCategoryLevel4 = subCategoryLevel4;
		}
		public int getProductCategoryId() {
			return productCategoryId;
		}
		public void setProductCategoryId(int productCategoryId) {
			this.productCategoryId = productCategoryId;
		}
		public String getProductCategory() {
			return productCategory;
		}
		public void setProductCategory(String productCategory) {
			this.productCategory = productCategory;
		}
		public String getSubCategoryLevel1() {
			return subCategoryLevel1;
		}
		public void setSubCategoryLevel1(String subCategoryLevel1) {
			this.subCategoryLevel1 = subCategoryLevel1;
		}
		public String getSubCategoryLevel2() {
			return subCategoryLevel2;
		}
		public void setSubCategoryLevel2(String subCategoryLevel2) {
			this.subCategoryLevel2 = subCategoryLevel2;
		}
		public String getSubCategoryLevel3() {
			return subCategoryLevel3;
		}
		public void setSubCategoryLevel3(String subCategoryLevel3) {
			this.subCategoryLevel3 = subCategoryLevel3;
		}
		public String getSubCategoryLevel4() {
			return subCategoryLevel4;
		}
		public void setSubCategoryLevel4(String subCategoryLevel4) {
			this.subCategoryLevel4 = subCategoryLevel4;
		}

	}
}
