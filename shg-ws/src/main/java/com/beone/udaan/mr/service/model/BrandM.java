package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.persistence.model.Brand;

public class BrandM {

	private long brandId;
	private long manufactureAcNo;
	private String name;
	private String nameDisplay;
	private String description;
	private String properties;
	private String link;
	private int noStockedTypes;
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
	
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public long getManufactureAcNo() {
		return manufactureAcNo;
	}
	public void setManufactureAcNo(long manufactureAcNo) {
		this.manufactureAcNo = manufactureAcNo;
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getNoStockedTypes() {
		return noStockedTypes;
	}
	public void setNoStockedTypes(int noStockedTypes) {
		this.noStockedTypes = noStockedTypes;
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
	
	public static BrandM buildBrand(Brand brand) {
		
		BrandM brandM = new BrandM();
		
		brandM.setBrandId(brand.getBrandId());
		brandM.setManufactureAcNo(brand.getManufacture().getGAcNo());
		brandM.setName(brand.getName());
		brandM.setNameDisplay(brand.getNameDisplay());
		brandM.setDescription(brand.getDescription());
		brandM.setProperties(brand.getProperties());
		brandM.setLink(brand.getLink());
		brandM.setNoStockedTypes(brand.getNoStockedTypes());
		brandM.setNoLots(brand.getNoLots());
		brandM.setNoPerSet(brand.getNoPerSet());
		brandM.setNoMinSets(brand.getNoMinSets());
		brandM.setTotalPurchaseAm(brand.getTotalPurchaseAm());
		brandM.setTotalStockAm(brand.getTotalStockAm());
		brandM.setTotalSoldAm(brand.getTotalSoldAm());
		brandM.setTotalSoldDiscountAm(brand.getTotalSoldDiscountAm());
		brandM.setTotalDamageAm(brand.getTotalDamageAm());
		brandM.setTotalPurchaseNo(brand.getTotalPurchaseNo());
		brandM.setTotalStockNo(brand.getTotalStockNo());
		brandM.setTotalSoldNo(brand.getTotalSoldNo());
		brandM.setTotalSoldDiscountNo(brand.getTotalSoldDiscountNo());
		brandM.setTotalDamageNo(brand.getTotalDamageNo());
		brandM.setCurrentOrderedStockAm(brand.getCurrentOrderedStockAm());
		brandM.setCurrentDeliveredStockAm(brand.getCurrentDeliveredStockAm());
		brandM.setCurrentToStockAm(brand.getCurrentToStockAm());
		brandM.setCurrentCreatedStockAm(brand.getCurrentCreatedStockAm());
		brandM.setCurrentStockAm(brand.getCurrentStockAm());
		brandM.setCurrentStockDiscountAm(brand.getCurrentStockDiscountAm());
		brandM.setCurrentOrderedStockNo(brand.getCurrentOrderedStockNo());
		brandM.setCurrentDeliveredStockNo(brand.getCurrentDeliveredStockNo());
		brandM.setCurrentToStockNo(brand.getCurrentToStockNo());
		brandM.setCurrentCreatedStockNo(brand.getCurrentCreatedStockNo());
		brandM.setCurrentStockNo(brand.getCurrentStockNo());
		brandM.setCurrentStockDiscountNo(brand.getCurrentStockDiscountNo());
		brandM.setFirstStockPriceAm(brand.getFirstStockPriceAm());
		brandM.setLastStockPriceAm(brand.getLastStockPriceAm());
		brandM.setAvgMrItemSoldAm(brand.getAvgMrItemSoldAm());
		brandM.setFirstLotTs(DateUtil.convertDateToTime(brand.getFirstLotTs()));
		brandM.setLastLotTs(DateUtil.convertDateToTime(brand.getLastLotTs()));
		brandM.setReturnCounter(brand.getReturnCounter());
		brandM.setPerformanceIndex(brand.getPerformanceIndex());
		brandM.setReturnIndex(brand.getReturnIndex());
		brandM.setSalesIndex(brand.getSalesIndex());
		brandM.setSales20PerDays(brand.getSales20PerDays());
		brandM.setSales40PerDays(brand.getSales40PerDays());
		brandM.setSales60PerDays(brand.getSales60PerDays());
		brandM.setSales80PerDays(brand.getSales80PerDays());
		brandM.setSales100PerDays(brand.getSales100PerDays());
		
		return brandM;
	}
	
	public Map<String,String> toStringInfo() {
		
		Map<String,String> brandInfo = new HashMap<String,String>();
		brandInfo.put("Brand Id", Long.toString(brandId));
		brandInfo.put("Manufacture Ac No", Long.toString(manufactureAcNo));
		brandInfo.put("Display Name", nameDisplay);

		return brandInfo;
	}
	
	public static boolean isBrandCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("manufactureAcNo") &&
				csvData.isColumnPresent("name") &&
				csvData.isColumnPresent("nameDisplay"));
	}
	
	public static BrandM buildBrand(CSVDataCollector csvData, int row) throws BadRequestException {
		
		String name = csvData.getCellValue("name", row).trim();
		String nameDisplay = csvData.getCellValue("nameDisplay", row).trim();
		String manufactureAcNo = csvData.getCellValue("manufactureAcNo", row).trim();
		
		if(name == null || name.isEmpty() ||
				manufactureAcNo == null || manufactureAcNo.isEmpty()) {
			return null;
		}
		
		BrandM brand = new BrandM();
		
		// Add Name Info
		brand.setManufactureAcNo(DataUtil.toLong(manufactureAcNo));
		brand.setName(name);
		brand.setNameDisplay(nameDisplay);
		brand.setDescription(csvData.getCellValue("description", row));
		brand.setProperties(csvData.getCellValue("properties", row));
		brand.setLink(csvData.getCellValue("link", row));
		
		return brand;
	}	
}
