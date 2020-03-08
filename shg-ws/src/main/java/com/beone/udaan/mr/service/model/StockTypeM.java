package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.StockType;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

public class StockTypeM {
	
	private long stockTypeId;
	private String stockStatus;
	private String category;
	private String productPricing;
	private String itemCondition;
	private long brandId;
	private String name;
	private String nameDisplay;
	private String brandName;
	private String description;
	private String properties;
	private String pics;
	private String link;
	private int noLots;
	private int noPerSet;
	private int noMinSets;
	private BigDecimal totalPurchaseAm;
	private BigDecimal totalStockAm;
	private BigDecimal totalSoldAm;
	private BigDecimal totalSoldDiscountAm;
	private BigDecimal totalDamageAm;
	private int totalPurchaseNo;
	private int totalStockNo;
	private int totalSoldNo;
	private int totalSoldDiscountNo;
	private int totalDamageNo;
	private BigDecimal currentOrderedStockAm;
	private BigDecimal currentDeliveredStockAm;
	private BigDecimal currentToStockAm;
	private BigDecimal currentCreatedStockAm;
	private BigDecimal currentStockAm;
	private BigDecimal currentStockDiscountAm;
	private int currentOrderedStockNo;
	private int currentDeliveredStockNo;
	private int currentToStockNo;
	private int currentCreatedStockNo;
	private int currentStockNo;	
	private int currentStockDiscountNo;
	private BigDecimal firstStockPriceAm;
	private BigDecimal lastStockPriceAm;
	private BigDecimal avgMrItemSoldAm;
	private long firstLotTs;
	private long lastLotTs;
	private int returnCounter;
	private float performanceIndex;
	private float returnIndex;
	private float salesIndex;
	private float sales20PerDays;
	private float sales40PerDays;
	private float sales60PerDays;
	private float sales80PerDays;
	private float sales100PerDays;
	
	public long getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProductPricing() {
		return productPricing;
	}
	public void setProductPricing(String productPricing) {
		this.productPricing = productPricing;
	}
	public String getItemCondition() {
		return itemCondition;
	}
	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameDisplay() {
		return nameDisplay;
	}
	public void setNameDisplay(String nameDisplay) {
		this.nameDisplay = nameDisplay;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getNoLots() {
		return noLots;
	}
	public void setNoLots(int noLots) {
		this.noLots = noLots;
	}
	public int getNoPerSet() {
		return noPerSet;
	}
	public void setNoPerSet(int noPerSet) {
		this.noPerSet = noPerSet;
	}
	public int getNoMinSets() {
		return noMinSets;
	}
	public void setNoMinSets(int noMinSets) {
		this.noMinSets = noMinSets;
	}
	public BigDecimal getTotalPurchaseAm() {
		return totalPurchaseAm;
	}
	public void setTotalPurchaseAm(BigDecimal totalPurchaseAm) {
		this.totalPurchaseAm = totalPurchaseAm;
	}
	public BigDecimal getTotalStockAm() {
		return totalStockAm;
	}
	public void setTotalStockAm(BigDecimal totalStockAm) {
		this.totalStockAm = totalStockAm;
	}
	public BigDecimal getTotalSoldAm() {
		return totalSoldAm;
	}
	public void setTotalSoldAm(BigDecimal totalSoldAm) {
		this.totalSoldAm = totalSoldAm;
	}
	public BigDecimal getTotalSoldDiscountAm() {
		return totalSoldDiscountAm;
	}
	public void setTotalSoldDiscountAm(BigDecimal totalSoldDiscountAm) {
		this.totalSoldDiscountAm = totalSoldDiscountAm;
	}
	public BigDecimal getTotalDamageAm() {
		return totalDamageAm;
	}
	public void setTotalDamageAm(BigDecimal totalDamageAm) {
		this.totalDamageAm = totalDamageAm;
	}
	public int getTotalPurchaseNo() {
		return totalPurchaseNo;
	}
	public void setTotalPurchaseNo(int totalPurchaseNo) {
		this.totalPurchaseNo = totalPurchaseNo;
	}
	public int getTotalStockNo() {
		return totalStockNo;
	}
	public void setTotalStockNo(int totalStockNo) {
		this.totalStockNo = totalStockNo;
	}
	public int getTotalSoldNo() {
		return totalSoldNo;
	}
	public void setTotalSoldNo(int totalSoldNo) {
		this.totalSoldNo = totalSoldNo;
	}
	public int getTotalSoldDiscountNo() {
		return totalSoldDiscountNo;
	}
	public void setTotalSoldDiscountNo(int totalSoldDiscountNo) {
		this.totalSoldDiscountNo = totalSoldDiscountNo;
	}
	public int getTotalDamageNo() {
		return totalDamageNo;
	}
	public void setTotalDamageNo(int totalDamageNo) {
		this.totalDamageNo = totalDamageNo;
	}
	public BigDecimal getCurrentOrderedStockAm() {
		return currentOrderedStockAm;
	}
	public void setCurrentOrderedStockAm(BigDecimal currentOrderedStockAm) {
		this.currentOrderedStockAm = currentOrderedStockAm;
	}
	public BigDecimal getCurrentDeliveredStockAm() {
		return currentDeliveredStockAm;
	}
	public void setCurrentDeliveredStockAm(BigDecimal currentDeliveredStockAm) {
		this.currentDeliveredStockAm = currentDeliveredStockAm;
	}
	public BigDecimal getCurrentToStockAm() {
		return currentToStockAm;
	}
	public void setCurrentToStockAm(BigDecimal currentToStockAm) {
		this.currentToStockAm = currentToStockAm;
	}
	public BigDecimal getCurrentCreatedStockAm() {
		return currentCreatedStockAm;
	}
	public void setCurrentCreatedStockAm(BigDecimal currentCreatedStockAm) {
		this.currentCreatedStockAm = currentCreatedStockAm;
	}
	public BigDecimal getCurrentStockAm() {
		return currentStockAm;
	}
	public void setCurrentStockAm(BigDecimal currentStockAm) {
		this.currentStockAm = currentStockAm;
	}
	public BigDecimal getCurrentStockDiscountAm() {
		return currentStockDiscountAm;
	}
	public void setCurrentStockDiscountAm(BigDecimal currentStockDiscountAm) {
		this.currentStockDiscountAm = currentStockDiscountAm;
	}
	public int getCurrentOrderedStockNo() {
		return currentOrderedStockNo;
	}
	public void setCurrentOrderedStockNo(int currentOrderedStockNo) {
		this.currentOrderedStockNo = currentOrderedStockNo;
	}
	public int getCurrentDeliveredStockNo() {
		return currentDeliveredStockNo;
	}
	public void setCurrentDeliveredStockNo(int currentDeliveredStockNo) {
		this.currentDeliveredStockNo = currentDeliveredStockNo;
	}
	public int getCurrentToStockNo() {
		return currentToStockNo;
	}
	public void setCurrentToStockNo(int currentToStockNo) {
		this.currentToStockNo = currentToStockNo;
	}
	public int getCurrentCreatedStockNo() {
		return currentCreatedStockNo;
	}
	public void setCurrentCreatedStockNo(int currentCreatedStockNo) {
		this.currentCreatedStockNo = currentCreatedStockNo;
	}
	public int getCurrentStockNo() {
		return currentStockNo;
	}
	public void setCurrentStockNo(int currentStockNo) {
		this.currentStockNo = currentStockNo;
	}
	public int getCurrentStockDiscountNo() {
		return currentStockDiscountNo;
	}
	public void setCurrentStockDiscountNo(int currentStockDiscountNo) {
		this.currentStockDiscountNo = currentStockDiscountNo;
	}
	public BigDecimal getFirstStockPriceAm() {
		return firstStockPriceAm;
	}
	public void setFirstStockPriceAm(BigDecimal firstStockPriceAm) {
		this.firstStockPriceAm = firstStockPriceAm;
	}
	public BigDecimal getLastStockPriceAm() {
		return lastStockPriceAm;
	}
	public void setLastStockPriceAm(BigDecimal lastStockPriceAm) {
		this.lastStockPriceAm = lastStockPriceAm;
	}
	public BigDecimal getAvgMrItemSoldAm() {
		return avgMrItemSoldAm;
	}
	public void setAvgMrItemSoldAm(BigDecimal avgMrItemSoldAm) {
		this.avgMrItemSoldAm = avgMrItemSoldAm;
	}
	public long getFirstLotTs() {
		return firstLotTs;
	}
	public void setFirstLotTs(long firstLotTs) {
		this.firstLotTs = firstLotTs;
	}
	public long getLastLotTs() {
		return lastLotTs;
	}
	public void setLastLotTs(long lastLotTs) {
		this.lastLotTs = lastLotTs;
	}
	public int getReturnCounter() {
		return returnCounter;
	}
	public void setReturnCounter(int returnCounter) {
		this.returnCounter = returnCounter;
	}
	public float getPerformanceIndex() {
		return performanceIndex;
	}
	public void setPerformanceIndex(float performanceIndex) {
		this.performanceIndex = performanceIndex;
	}
	public float getReturnIndex() {
		return returnIndex;
	}
	public void setReturnIndex(float returnIndex) {
		this.returnIndex = returnIndex;
	}
	public float getSalesIndex() {
		return salesIndex;
	}
	public void setSalesIndex(float salesIndex) {
		this.salesIndex = salesIndex;
	}
	public float getSales20PerDays() {
		return sales20PerDays;
	}
	public void setSales20PerDays(float sales20PerDays) {
		this.sales20PerDays = sales20PerDays;
	}
	public float getSales40PerDays() {
		return sales40PerDays;
	}
	public void setSales40PerDays(float sales40PerDays) {
		this.sales40PerDays = sales40PerDays;
	}
	public float getSales60PerDays() {
		return sales60PerDays;
	}
	public void setSales60PerDays(float sales60PerDays) {
		this.sales60PerDays = sales60PerDays;
	}
	public float getSales80PerDays() {
		return sales80PerDays;
	}
	public void setSales80PerDays(float sales80PerDays) {
		this.sales80PerDays = sales80PerDays;
	}
	public float getSales100PerDays() {
		return sales100PerDays;
	}
	public void setSales100PerDays(float sales100PerDays) {
		this.sales100PerDays = sales100PerDays;
	}
	
	public static StockTypeM buildStockType(StockType stockType) {
		
		StockTypeM stockTypeM = new StockTypeM();
		
		stockTypeM.setStockTypeId(stockType.getStockTypeId());
		stockTypeM.setStockStatus(EnumCacheMr.getNameOfStatusValue(EnumConstMr.StockTypeStatus, stockType.getStockStatusId()));
		stockTypeM.setCategory(EnumCacheMr.getNameOfProductCategory(stockType.getCategoryId()));
		stockTypeM.setProductPricing(EnumCacheMr.getNameOfProductPricing(stockType.getProductPricingId()));
		stockTypeM.setItemCondition(EnumCacheMr.getNameOfItemCondition(stockType.getItemConditionId()));
		stockTypeM.setBrandId(stockType.getBrand().getBrandId());
		stockTypeM.setName(stockType.getName());
		stockTypeM.setNameDisplay(stockType.getNameDisplay());
		stockTypeM.setBrandName(stockType.getBrand().getNameDisplay());
		stockTypeM.setDescription(stockType.getDescription());
		stockTypeM.setProperties(stockType.getProperties());
		stockTypeM.setPics(stockType.getPics());
		stockTypeM.setLink(stockType.getLink());
		stockTypeM.setNoLots(stockType.getNoLots());
		stockTypeM.setNoPerSet(stockType.getNoPerSet());
		stockTypeM.setNoMinSets(stockType.getNoMinSets());
		stockTypeM.setTotalPurchaseAm(stockType.getTotalPurchaseAm());
		stockTypeM.setTotalStockAm(stockType.getTotalStockAm());
		stockTypeM.setTotalSoldAm(stockType.getTotalSoldAm());
		stockTypeM.setTotalSoldDiscountAm(stockType.getTotalSoldDiscountAm());
		stockTypeM.setTotalDamageAm(stockType.getTotalDamageAm());
		stockTypeM.setTotalPurchaseNo(stockType.getTotalPurchaseNo());
		stockTypeM.setTotalStockNo(stockType.getTotalStockNo());
		stockTypeM.setTotalSoldNo(stockType.getTotalSoldNo());
		stockTypeM.setTotalSoldDiscountNo(stockType.getTotalSoldDiscountNo());
		stockTypeM.setTotalDamageNo(stockType.getTotalDamageNo());
		stockTypeM.setCurrentOrderedStockAm(stockType.getCurrentOrderedStockAm());
		stockTypeM.setCurrentDeliveredStockAm(stockType.getCurrentDeliveredStockAm());
		stockTypeM.setCurrentToStockAm(stockType.getCurrentToStockAm());
		stockTypeM.setCurrentCreatedStockAm(stockType.getCurrentCreatedStockAm());
		stockTypeM.setCurrentStockAm(stockType.getCurrentStockAm());
		stockTypeM.setCurrentStockDiscountAm(stockType.getCurrentStockDiscountAm());
		stockTypeM.setCurrentOrderedStockNo(stockType.getCurrentOrderedStockNo());
		stockTypeM.setCurrentDeliveredStockNo(stockType.getCurrentDeliveredStockNo());
		stockTypeM.setCurrentToStockNo(stockType.getCurrentToStockNo());
		stockTypeM.setCurrentCreatedStockNo(stockType.getCurrentCreatedStockNo());
		stockTypeM.setCurrentStockNo(stockType.getCurrentStockNo());
		stockTypeM.setCurrentStockDiscountNo(stockType.getCurrentStockDiscountNo());
		stockTypeM.setFirstStockPriceAm(stockType.getFirstStockPriceAm());
		stockTypeM.setLastStockPriceAm(stockType.getLastStockPriceAm());
		stockTypeM.setAvgMrItemSoldAm(stockType.getAvgMrItemSoldAm());
		stockTypeM.setFirstLotTs(DateUtil.convertDateToTime(stockType.getFirstLotTs()));
		stockTypeM.setLastLotTs(DateUtil.convertDateToTime(stockType.getLastLotTs()));
		stockTypeM.setReturnCounter(stockType.getReturnCounter());
		stockTypeM.setPerformanceIndex(stockType.getPerformanceIndex());
		stockTypeM.setReturnIndex(stockType.getReturnIndex());
		stockTypeM.setSalesIndex(stockType.getSalesIndex());
		stockTypeM.setSales20PerDays(stockType.getSales20PerDays());
		stockTypeM.setSales40PerDays(stockType.getSales40PerDays());
		stockTypeM.setSales60PerDays(stockType.getSales60PerDays());
		stockTypeM.setSales80PerDays(stockType.getSales80PerDays());
		stockTypeM.setSales100PerDays(stockType.getSales100PerDays());
		
		return stockTypeM;
	}
	
	public Map<String,String> toStringInfo() {
		
		Map<String,String> stockTypeInfo = new HashMap<String,String>();
		stockTypeInfo.put("Stock Type Id", Long.toString(stockTypeId));
		stockTypeInfo.put("Brand Id", Long.toString(brandId));
		stockTypeInfo.put("Display Name", nameDisplay);

		return stockTypeInfo;
	}
	
	public static boolean isStockTypeCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("brandId") &&
				csvData.isColumnPresent("category") &&
				csvData.isColumnPresent("productPricing") &&
				csvData.isColumnPresent("name") &&
				csvData.isColumnPresent("nameDisplay"));
	}
	
	public static StockTypeM buildStockType(CSVDataCollector csvData, int row) throws BadRequestException {
		
		String brandId = csvData.getCellValue("brandId", row).trim();
		String category = csvData.getCellValue("category", row).trim();
		String productPricing = csvData.getCellValue("productPricing", row).trim();
		String name = csvData.getCellValue("name", row).trim();
		String nameDisplay = csvData.getCellValue("nameDisplay", row).trim();
		
		if(brandId == null || brandId.isEmpty() ||
				category == null || category.isEmpty() ||
				productPricing == null || productPricing.isEmpty() ||
				name == null || name.isEmpty() ||
				nameDisplay == null || nameDisplay.isEmpty()) {
			return null;
		}
		
		StockTypeM stockType = new StockTypeM();
		
		// Add Name Info
		stockType.setBrandId(DataUtil.toLong(brandId));
		stockType.setCategory(category);
		stockType.setProductPricing(productPricing);
		stockType.setName(name);
		stockType.setNameDisplay(nameDisplay);
		stockType.setDescription(csvData.getCellValue("description", row));
		stockType.setProperties(csvData.getCellValue("properties", row));
		stockType.setLink(csvData.getCellValue("link", row));
		
		return stockType;
	}	
	
}
