package com.beone.udaan.mr.service.model;

import com.beone.udaan.mr.persistence.model.Brand;

public class BrandName {

	private long brandId;
	private String brandName;
	private long manufactureAcNo;
	private String manufactureName;
	
	public BrandName() {
		super();
	}
	public BrandName(long brandId, String brandName, long manufactureAcNo,
			String manufactureName) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
		this.manufactureAcNo = manufactureAcNo;
		this.manufactureName = manufactureName;
	}
	
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public long getManufactureAcNo() {
		return manufactureAcNo;
	}
	public void setManufactureAcNo(long manufactureAcNo) {
		this.manufactureAcNo = manufactureAcNo;
	}
	public String getManufactureName() {
		return manufactureName;
	}
	public void setManufactureName(String manufactureName) {
		this.manufactureName = manufactureName;
	}
	
	public static BrandName buildBrandName(Brand brand, String manufactureName) {
		return (new BrandName(brand.getBrandId(), 
				brand.getNameDisplay(), 
				brand.getManufacture().getGAcNo(), 
				manufactureName));
	}
}
