package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.PInvoice;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

public class PurchaseInvoice {

	private long purchasedInvoiceId;
	private long manufactureAcNo;
	private String invoiceNo;
	private String invoiceStatus;
	private long invoiceTs;
	private long entryTs;
	private long stockTs;
	private long expectedDeliveryTs;
	private String lrSlips;
	private long entryByAcNo;
	private String entryByName;
	private int noOfLots;
	private int noOfItems;
	private float vatPer;
	private float discountPer;
	private BigDecimal total;
	private BigDecimal discount;
	private BigDecimal grossTotal;
	private BigDecimal vat;
	private BigDecimal otherTaxes;
	private BigDecimal netTotal;
	private BigDecimal netTotalCalculated;
	private BigDecimal advance;
	private BigDecimal netPayment;
	private String paymentMode;
	private long paymentTs;
	private String chequeNo;
	private String description;
	private String attachment;
	private String entryLocation;
	private List<LotM> lots;
	
	public long getPurchasedInvoiceId() {
		return purchasedInvoiceId;
	}
	public void setPurchasedInvoiceId(long purchasedInvoiceId) {
		this.purchasedInvoiceId = purchasedInvoiceId;
	}
	public long getManufactureAcNo() {
		return manufactureAcNo;
	}
	public void setManufactureAcNo(long manufactureAcNo) {
		this.manufactureAcNo = manufactureAcNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public long getInvoiceTs() {
		return invoiceTs;
	}
	public void setInvoiceTs(long invoiceTs) {
		this.invoiceTs = invoiceTs;
	}
	public long getEntryTs() {
		return entryTs;
	}
	public void setEntryTs(long entryTs) {
		this.entryTs = entryTs;
	}
	public long getStockTs() {
		return stockTs;
	}
	public void setStockTs(long stockTs) {
		this.stockTs = stockTs;
	}
	public long getExpectedDeliveryTs() {
		return expectedDeliveryTs;
	}
	public void setExpectedDeliveryTs(long expectedDeliveryTs) {
		this.expectedDeliveryTs = expectedDeliveryTs;
	}
	public String getLrSlips() {
		return lrSlips;
	}
	public void setLrSlips(String lrSlips) {
		this.lrSlips = lrSlips;
	}
	public long getEntryByAcNo() {
		return entryByAcNo;
	}
	public void setEntryByAcNo(long entryByAcNo) {
		this.entryByAcNo = entryByAcNo;
	}
	public String getEntryByName() {
		return entryByName;
	}
	public void setEntryByName(String entryByName) {
		this.entryByName = entryByName;
	}
	public int getNoOfLots() {
		return noOfLots;
	}
	public void setNoOfLots(int noOfLots) {
		this.noOfLots = noOfLots;
	}
	public int getNoOfItems() {
		return noOfItems;
	}
	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}
	public float getVatPer() {
		return vatPer;
	}
	public void setVatPer(float vatPer) {
		this.vatPer = vatPer;
	}
	public float getDiscountPer() {
		return discountPer;
	}
	public void setDiscountPer(float discountPer) {
		this.discountPer = discountPer;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getGrossTotal() {
		return grossTotal;
	}
	public void setGrossTotal(BigDecimal grossTotal) {
		this.grossTotal = grossTotal;
	}
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	public BigDecimal getOtherTaxes() {
		return otherTaxes;
	}
	public void setOtherTaxes(BigDecimal otherTaxes) {
		this.otherTaxes = otherTaxes;
	}
	public BigDecimal getNetTotal() {
		return netTotal;
	}
	public void setNetTotal(BigDecimal netTotal) {
		this.netTotal = netTotal;
	}
	public BigDecimal getNetTotalCalculated() {
		return netTotalCalculated;
	}
	public void setNetTotalCalculated(BigDecimal netTotalCalculated) {
		this.netTotalCalculated = netTotalCalculated;
	}
	public BigDecimal getAdvance() {
		return advance;
	}
	public void setAdvance(BigDecimal advance) {
		this.advance = advance;
	}
	public BigDecimal getNetPayment() {
		return netPayment;
	}
	public void setNetPayment(BigDecimal netPayment) {
		this.netPayment = netPayment;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public long getPaymentTs() {
		return paymentTs;
	}
	public void setPaymentTs(long paymentTs) {
		this.paymentTs = paymentTs;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getEntryLocation() {
		return entryLocation;
	}
	public void setEntryLocation(String entryLocation) {
		this.entryLocation = entryLocation;
	}
	public List<LotM> getLots() {
		return lots;
	}
	public void setLots(List<LotM> lots) {
		this.lots = lots;
	}
	public void addLot(LotM lot) {
		if(lots == null) {
			lots = new ArrayList<LotM>();
		}
		this.lots.add(lot);
	}

	public static PurchaseInvoice buildPurchaseInvoice(PInvoice pInvoice) {
		
		PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
		
		purchaseInvoice.setPurchasedInvoiceId(pInvoice.getPurchasedInvoiceId());
		purchaseInvoice.setManufactureAcNo(pInvoice.getVender().getGAcNo());
		purchaseInvoice.setInvoiceNo(pInvoice.getInvoiceNo());
		purchaseInvoice.setInvoiceStatus(EnumCacheMr.getNameOfStatusValue(EnumConstMr.InvoiceStatus, pInvoice.getInvoiceStatusId()));
		purchaseInvoice.setInvoiceTs(DateUtil.convertDateToTime(pInvoice.getInvoiceTs()));
		purchaseInvoice.setEntryTs(DateUtil.convertDateToTime(pInvoice.getEntryTs()));
		purchaseInvoice.setStockTs(DateUtil.convertDateToTime(pInvoice.getStockTs()));
		purchaseInvoice.setExpectedDeliveryTs(DateUtil.convertDateToTime(pInvoice.getExpectedDeliveryTs()));
		purchaseInvoice.setLrSlips(pInvoice.getLrSlips());
		purchaseInvoice.setEntryByAcNo(pInvoice.getEntryBy().getMemberAcNo());
//		purchaseInvoice.setEntryByName(pInvoice.getentryByName);
		purchaseInvoice.setNoOfLots(pInvoice.getNoOfLots());
		purchaseInvoice.setNoOfItems(pInvoice.getNoOfItems());
		purchaseInvoice.setVatPer(pInvoice.getVatPer());
		purchaseInvoice.setDiscountPer(pInvoice.getDiscountPer());
		purchaseInvoice.setTotal(pInvoice.getTotal());
		purchaseInvoice.setDiscount(pInvoice.getDiscount());
		purchaseInvoice.setGrossTotal(pInvoice.getGrossTotal());
		purchaseInvoice.setVat(pInvoice.getVat());
		purchaseInvoice.setOtherTaxes(pInvoice.getOtherTaxes());
		purchaseInvoice.setNetTotal(pInvoice.getNetTotal());
		purchaseInvoice.setNetTotalCalculated(pInvoice.getNetTotalCalculated());
		purchaseInvoice.setAdvance(pInvoice.getAdvance());
		purchaseInvoice.setNetPayment(pInvoice.getNetPayment());
		purchaseInvoice.setPaymentMode(EnumCacheMr.getNameOfEnumValue(EnumConstMr.PaymentType, pInvoice.getPaymentModeId()));
		purchaseInvoice.setPaymentTs(DateUtil.convertDateToTime(pInvoice.getPaymentTs()));
		purchaseInvoice.setChequeNo(pInvoice.getChequeNo());
		purchaseInvoice.setDescription(pInvoice.getDescription());
		purchaseInvoice.setAttachment(pInvoice.getAttachment());
		purchaseInvoice.setEntryLocation(pInvoice.getEntryLocation());
		
		return purchaseInvoice;
	}
	
	public Map<String,String> toStringInfo() {
		
		Map<String,String> purchaseInvoiceInfo = new HashMap<String,String>();
		purchaseInvoiceInfo.put("Purchase Invoice Id", Long.toString(purchasedInvoiceId));
		purchaseInvoiceInfo.put("Manufacture Ac No", Long.toString(manufactureAcNo));
		purchaseInvoiceInfo.put("Net Total", DataUtil.toString(netTotal));

		return purchaseInvoiceInfo;
	}
	
	public static boolean isPurchaseInvoiceCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("manufactureAcNo") &&
				csvData.isColumnPresent("noOfLots") &&
				csvData.isColumnPresent("noOfItems") &&
				csvData.isColumnPresent("netTotal"));
	}
	
	public static PurchaseInvoice buildPurchaseInvoice(long entryByAcNo, CSVDataCollector csvData, int row) throws BadRequestException {
		
		String manufactureAcNo = csvData.getCellValue("manufactureAcNo", row).trim();
		String noOfLots = csvData.getCellValue("noOfLots", row).trim();
		String noOfItems = csvData.getCellValue("noOfItems", row).trim();
		String total = csvData.getCellValue("total", row).trim();
		
		if(manufactureAcNo == null || manufactureAcNo.isEmpty() ||
				noOfLots == null || noOfLots.isEmpty() ||
				noOfItems == null || noOfItems.isEmpty() ||
				total == null || total.isEmpty()) {
			return null;
		}
		
		PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
		
		purchaseInvoice.setManufactureAcNo(DataUtil.toLong(manufactureAcNo));
		purchaseInvoice.setInvoiceNo(csvData.getCellValue("invoiceNo", row));
		purchaseInvoice.setInvoiceStatus(csvData.getCellValue("invoiceStatus", row));
		purchaseInvoice.setInvoiceTs(DateUtil.convertStringToLong(csvData.getCellValue("invoiceDate", row)));
		purchaseInvoice.setExpectedDeliveryTs(DateUtil.convertStringToLong(csvData.getCellValue("expectedDeliveryTs", row)));
		purchaseInvoice.setLrSlips(csvData.getCellValue("lrSlips", row));
		purchaseInvoice.setEntryByAcNo(entryByAcNo);
		purchaseInvoice.setNoOfLots(DataUtil.toInteger(noOfLots));
		purchaseInvoice.setNoOfItems(DataUtil.toInteger(noOfItems));
		purchaseInvoice.setVatPer(DataUtil.toFloat(csvData.getCellValue("vatPer", row)));
		purchaseInvoice.setDiscountPer(DataUtil.toFloat(csvData.getCellValue("discountPer", row)));
		purchaseInvoice.setTotal(DataUtil.toBigDecimal(total));
		purchaseInvoice.setDiscount(DataUtil.toBigDecimal(csvData.getCellValue("discount", row)));
		purchaseInvoice.setGrossTotal(DataUtil.toBigDecimal(csvData.getCellValue("grossTotal", row)));
		purchaseInvoice.setVat(DataUtil.toBigDecimal(csvData.getCellValue("vat", row)));
		purchaseInvoice.setOtherTaxes(DataUtil.toBigDecimal(csvData.getCellValue("otherTaxes", row)));
		purchaseInvoice.setNetTotal(DataUtil.toBigDecimal(csvData.getCellValue("netTotal", row)));
		purchaseInvoice.setNetTotalCalculated(DataUtil.ZERO_BIG_DECIMAL);
		purchaseInvoice.setAdvance(DataUtil.toBigDecimal(csvData.getCellValue("advance", row)));
		purchaseInvoice.setNetPayment(DataUtil.toBigDecimal(csvData.getCellValue("netPayment", row)));
		purchaseInvoice.setPaymentMode(csvData.getCellValue("paymentMode", row));
		purchaseInvoice.setPaymentTs(DateUtil.convertStringToLong(csvData.getCellValue("paymentDate", row)));
		purchaseInvoice.setChequeNo(csvData.getCellValue("chequeNo", row));
		
		return purchaseInvoice;
	}	
	
}
