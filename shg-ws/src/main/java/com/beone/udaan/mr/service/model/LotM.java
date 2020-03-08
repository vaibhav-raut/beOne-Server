package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.Lot;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

public class LotM {

	private long lotId;
	private long invoiceId;
	private long stockTypeId;
	private String lotStatus;
	private String name;
	private int noPerSet;
	private int noOfSets;
	private int noPurchased;
	private int noDelivered;
	private int noToStock;
	private int noCreated;
	private int noStocked;
	private int noSold;
	private int noDamaged;
	private BigDecimal itemPriceAm;
	private BigDecimal lotPriceAm;
	private float discountPer;
	private BigDecimal discountAm;
	private BigDecimal vatAm;
	private BigDecimal avgMrItemSoldAm;
	private int returnCounter;
	private float performanceIndex;
	private float returnIndex;
	private float salesIndex;
	private float sales20PerDays;
	private float sales40PerDays;
	private float sales60PerDays;
	private float sales80PerDays;
	private float sales100PerDays;
	
	public long getLotId() {
		return lotId;
	}
	public void setLotId(long lotId) {
		this.lotId = lotId;
	}
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public long getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNoPerSet() {
		return noPerSet;
	}
	public void setNoPerSet(int noPerSet) {
		this.noPerSet = noPerSet;
	}
	public int getNoOfSets() {
		return noOfSets;
	}
	public void setNoOfSets(int noOfSets) {
		this.noOfSets = noOfSets;
	}
	public int getNoPurchased() {
		return noPurchased;
	}
	public void setNoPurchased(int noPurchased) {
		this.noPurchased = noPurchased;
	}
	public int getNoDelivered() {
		return noDelivered;
	}
	public void setNoDelivered(int noDelivered) {
		this.noDelivered = noDelivered;
	}
	public int getNoToStock() {
		return noToStock;
	}
	public void setNoToStock(int noToStock) {
		this.noToStock = noToStock;
	}
	public int getNoCreated() {
		return noCreated;
	}
	public void setNoCreated(int noCreated) {
		this.noCreated = noCreated;
	}
	public int getNoStocked() {
		return noStocked;
	}
	public void setNoStocked(int noStocked) {
		this.noStocked = noStocked;
	}
	public int getNoSold() {
		return noSold;
	}
	public void setNoSold(int noSold) {
		this.noSold = noSold;
	}
	public int getNoDamaged() {
		return noDamaged;
	}
	public void setNoDamaged(int noDamaged) {
		this.noDamaged = noDamaged;
	}
	public BigDecimal getItemPriceAm() {
		return itemPriceAm;
	}
	public void setItemPriceAm(BigDecimal itemPriceAm) {
		this.itemPriceAm = itemPriceAm;
	}
	public BigDecimal getLotPriceAm() {
		return lotPriceAm;
	}
	public void setLotPriceAm(BigDecimal lotPriceAm) {
		this.lotPriceAm = lotPriceAm;
	}
	public float getDiscountPer() {
		return discountPer;
	}
	public void setDiscountPer(float discountPer) {
		this.discountPer = discountPer;
	}
	public BigDecimal getDiscountAm() {
		return discountAm;
	}
	public void setDiscountAm(BigDecimal discountAm) {
		this.discountAm = discountAm;
	}
	public BigDecimal getVatAm() {
		return vatAm;
	}
	public void setVatAm(BigDecimal vatAm) {
		this.vatAm = vatAm;
	}
	public BigDecimal getAvgMrItemSoldAm() {
		return avgMrItemSoldAm;
	}
	public void setAvgMrItemSoldAm(BigDecimal avgMrItemSoldAm) {
		this.avgMrItemSoldAm = avgMrItemSoldAm;
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

	public static LotM buildLot(Lot lot) {
		LotM lotM = new LotM();
		
		lotM.setLotId(lot.getLotId());
		lotM.setInvoiceId(lot.getPInvoice().getPurchasedInvoiceId());
		lotM.setStockTypeId(lot.getStockType().getStockTypeId());
		lotM.setLotStatus(EnumCacheMr.getNameOfStatusValue(EnumConstMr.LotStatus, lot.getLotStatusId()));
		lotM.setName(lot.getName());
		lotM.setNoPerSet(lot.getNoPerSet());
		lotM.setNoOfSets(lot.getNoOfSets());
		lotM.setNoPurchased(lot.getNoPurchased());
		lotM.setNoDelivered(lot.getNoDelivered());
		lotM.setNoToStock(lot.getNoToStock());
		lotM.setNoCreated(lot.getNoCreated());
		lotM.setNoStocked(lot.getNoStocked());
		lotM.setNoSold(lot.getNoSold());
		lotM.setNoDamaged(lot.getNoDamaged());
		lotM.setItemPriceAm(lot.getItemPriceAm());
		lotM.setLotPriceAm(lot.getLotPriceAm());
		lotM.setDiscountPer(lot.getDiscountPer());
		lotM.setDiscountAm(lot.getDiscountAm());
		lotM.setVatAm(lot.getVatAm());
		lotM.setReturnCounter(lot.getReturnCounter());
		lotM.setPerformanceIndex(lot.getPerformanceIndex());
		lotM.setReturnIndex(lot.getReturnIndex());
		lotM.setSalesIndex(lot.getSalesIndex());
		lotM.setSales20PerDays(lot.getSales20PerDays());
		lotM.setSales40PerDays(lot.getSales40PerDays());
		lotM.setSales60PerDays(lot.getSales60PerDays());
		lotM.setSales80PerDays(lot.getSales80PerDays());
		lotM.setSales100PerDays(lot.getSales100PerDays());
		
		return lotM;
	}
	
	public Map<String,String> toStringInfo() {
		
		Map<String,String> lotInfo = new HashMap<String,String>();
		lotInfo.put("Lot Id", Long.toString(lotId));
		lotInfo.put("Purchase Invoice Id", Long.toString(invoiceId));
		lotInfo.put("Stock Type Id", Long.toString(stockTypeId));
		lotInfo.put("Name", name);

		return lotInfo;
	}
	
	public static boolean isLotCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("pInvoiceId") &&
				csvData.isColumnPresent("stockTypeId") &&
				csvData.isColumnPresent("name") &&
				csvData.isColumnPresent("noPerSet") &&
				csvData.isColumnPresent("noOfSets") &&
				csvData.isColumnPresent("itemPriceAm"));
	}
	
	public static LotM buildLot(CSVDataCollector csvData, int row) throws BadRequestException {
		
		String invoiceId = csvData.getCellValue("pInvoiceId", row).trim();
		String stockTypeId = csvData.getCellValue("stockTypeId", row).trim();
		String name = csvData.getCellValue("name", row).trim();
		String noPerSet = csvData.getCellValue("noPerSet", row).trim();
		String noOfSets = csvData.getCellValue("noOfSets", row).trim();
		String itemPriceAm = csvData.getCellValue("itemPriceAm", row).trim();
		
		if(invoiceId == null || invoiceId.isEmpty() ||
				stockTypeId == null || stockTypeId.isEmpty() ||
				name == null || name.isEmpty() ||
				noPerSet == null || noPerSet.isEmpty() ||
				noOfSets == null || noOfSets.isEmpty() ||
				itemPriceAm == null || itemPriceAm.isEmpty()) {
			return null;
		}

		LotM lot = new LotM();
		
		// Add Name Info
		lot.setInvoiceId(DataUtil.toLong(invoiceId));
		lot.setStockTypeId(DataUtil.toLong(stockTypeId));
		lot.setLotStatus(csvData.getCellValue("lotStatus", row));
		lot.setName(name);
		lot.setNoPerSet(DataUtil.toInteger(noPerSet));
		lot.setNoOfSets(DataUtil.toInteger(noOfSets));
		lot.setItemPriceAm(DataUtil.toBigDecimal(itemPriceAm));
		lot.setDiscountPer(DataUtil.toFloat(csvData.getCellValue("discountPer", row)));
		
		return lot;
	}	
}
