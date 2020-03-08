package com.beone.udaan.mr.service.model;

import com.beone.udaan.mr.persistence.model.Brand;
import com.beone.udaan.mr.persistence.model.StockType;

public class StockTypeName {

	private long stockTypeId;
	private String stockTypeName;
	private long brandId;
	private String brandName;
	
	public StockTypeName() {
		super();
	}	
	public StockTypeName(long stockTypeId, String stockTypeName, long brandId,
			String brandName) {
		super();
		this.stockTypeId = stockTypeId;
		this.stockTypeName = stockTypeName;
		this.brandId = brandId;
		this.brandName = brandName;
	}
	public long getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getStockTypeName() {
		return stockTypeName;
	}
	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
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
	public static StockTypeName buildStockTypeName(StockType stockType, Brand brand) {
		return (new StockTypeName(stockType.getStockTypeId(),
				stockType.getNameDisplay(),
				brand.getBrandId(),
				brand.getNameDisplay()));
	}
}
