package com.beone.udaan.mr.service.model;

import java.util.ArrayList;
import java.util.List;

public class StockTransfer {

	private List<StockItemM> stockItems;
	private long ownerAcNo;
	private long authAcNo;
	private long mrVisitId;
	private String itemCondition;
	private String stockTxType;
	private String description;
	private String entryLocation;
	
	public List<StockItemM> getStockItems() {
		return stockItems;
	}
	public void setStockItems(List<StockItemM> stockItems) {
		this.stockItems = stockItems;
	}
	public void addStockItems(StockItemM stockItemId) {
		if(this.stockItems == null) {
			this.stockItems = new ArrayList<StockItemM>();
		}
		this.stockItems.add(stockItemId);
	}
	public long getOwnerAcNo() {
		return ownerAcNo;
	}
	public void setOwnerAcNo(long ownerAcNo) {
		this.ownerAcNo = ownerAcNo;
	}
	public long getAuthAcNo() {
		return authAcNo;
	}
	public void setAuthAcNo(long authAcNo) {
		this.authAcNo = authAcNo;
	}
	public long getMrVisitId() {
		return mrVisitId;
	}
	public void setMrVisitId(long mrVisitId) {
		this.mrVisitId = mrVisitId;
	}
	public String getItemCondition() {
		return itemCondition;
	}
	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}
	public String getStockTxType() {
		return stockTxType;
	}
	public void setStockTxType(String stockTxType) {
		this.stockTxType = stockTxType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEntryLocation() {
		return entryLocation;
	}
	public void setEntryLocation(String entryLocation) {
		this.entryLocation = entryLocation;
	}	
	
}
