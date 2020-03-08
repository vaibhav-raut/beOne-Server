package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

public class StockTxM {
	
	private long stockTxId;
	private String stockTxType;
	private long stockItemId;
	private long stockTypeId;
	private long brandId;
	private long lotId;
	private String name;
	private BigDecimal amount;
	private BigDecimal extraAm;
	private long entryByAcNo;
	private long ownerAcNo;
	private long authAcNo;
	private long mrVisitId;
	private String itemStatus;
	private String prevItemStatus;
	private long txTs;
	private long entryTs;
	private long verifyTs;
	private String description;
	private String entryLocation;
	
	public long getStockTxId() {
		return stockTxId;
	}
	public void setStockTxId(long stockTxId) {
		this.stockTxId = stockTxId;
	}
	public String getStockTxType() {
		return stockTxType;
	}
	public void setStockTxType(String stockTxType) {
		this.stockTxType = stockTxType;
	}
	public long getStockItemId() {
		return stockItemId;
	}
	public void setStockItemId(long stockItemId) {
		this.stockItemId = stockItemId;
	}
	public long getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public long getLotId() {
		return lotId;
	}
	public void setLotId(long lotId) {
		this.lotId = lotId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getExtraAm() {
		return extraAm;
	}
	public void setExtraAm(BigDecimal extraAm) {
		this.extraAm = extraAm;
	}
	public long getEntryByAcNo() {
		return entryByAcNo;
	}
	public void setEntryByAcNo(long entryByAcNo) {
		this.entryByAcNo = entryByAcNo;
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
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getPrevItemStatus() {
		return prevItemStatus;
	}
	public void setPrevItemStatus(String prevItemStatus) {
		this.prevItemStatus = prevItemStatus;
	}
	public long getTxTs() {
		return txTs;
	}
	public void setTxTs(long txTs) {
		this.txTs = txTs;
	}
	public long getEntryTs() {
		return entryTs;
	}
	public void setEntryTs(long entryTs) {
		this.entryTs = entryTs;
	}
	public long getVerifyTs() {
		return verifyTs;
	}
	public void setVerifyTs(long verifyTs) {
		this.verifyTs = verifyTs;
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

	public static StockTxM buildStockTxM(StockTx stockTx) {
		
		StockTxM stockTxM = new StockTxM();

		stockTxM.setStockTxId(stockTx.getStockTxId());
		stockTxM.setStockTxType(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()));
		stockTxM.setStockItemId(stockTx.getStockItemId());
		stockTxM.setStockTypeId(stockTx.getStockTypeId());
		stockTxM.setBrandId(stockTx.getBrandId());
		stockTxM.setLotId(stockTx.getLotId());
		stockTxM.setName(stockTx.getName());
		stockTxM.setAmount(stockTx.getAmount());
		stockTxM.setExtraAm(stockTx.getExtraAm());
		stockTxM.setEntryByAcNo(stockTx.getEntryByAcNo());
		stockTxM.setOwnerAcNo(stockTx.getOwnerAcNo());
		stockTxM.setAuthAcNo(stockTx.getAuthAcNo());
		stockTxM.setMrVisitId(stockTx.getMrVisitId());
		stockTxM.setItemStatus(EnumCacheMr.getNameOfStatusValue(EnumConstMr.ItemStatus, stockTx.getItemStatusId()));
		stockTxM.setPrevItemStatus(EnumCacheMr.getNameOfStatusValue(EnumConstMr.ItemStatus, stockTx.getPrevItemStatusId()));
		stockTxM.setTxTs(stockTx.getTxTs().getTime());
		stockTxM.setEntryTs(stockTx.getEntryTs().getTime());
		stockTxM.setVerifyTs(stockTx.getVerifyTs().getTime());
		stockTxM.setDescription(stockTx.getDescription());
		stockTxM.setEntryLocation(stockTx.getEntryLocation());
		
		return stockTxM;
	}

	public static List<StockTxM> buildStockTxMList(List<StockTx> stockTxs) {

		List<StockTxM> stockTxMs = new ArrayList<StockTxM>(stockTxs.size());

		if(stockTxs != null && !stockTxs.isEmpty()) {
			for(StockTx stockTx: stockTxs) {
				stockTxMs.add(buildStockTxM(stockTx));
			}
		}

		return stockTxMs;
	}
}
