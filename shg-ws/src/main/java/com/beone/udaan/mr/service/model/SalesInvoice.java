package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.Date;

public class SalesInvoice {

	private long invoiceId;
	private long soldByAcNo;
	private long entryByAcNo;
	private long purchasedByAcNo;
	private String invoiceNo;
	private int invoiceStatusId;
	private Date invoiceTs;
	private Date entryTs;
	private Date deliveryTs;
	private int noOfLots;
	private int noOfItems;
	private BigDecimal total;
	private BigDecimal discount;
	private BigDecimal grossTotal;
	private BigDecimal vat;
	private BigDecimal otherTax;
	private BigDecimal netTotal;
	private BigDecimal advance;
	private BigDecimal netPayment;
	private int paymentModeId;
	private Date paymentTs;
	private String chequeNo;
	private String description;
	private String entryLocation;
	
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public long getSoldByAcNo() {
		return soldByAcNo;
	}
	public void setSoldByAcNo(long soldByAcNo) {
		this.soldByAcNo = soldByAcNo;
	}
	public long getEntryByAcNo() {
		return entryByAcNo;
	}
	public void setEntryByAcNo(long entryByAcNo) {
		this.entryByAcNo = entryByAcNo;
	}
	public long getPurchasedByAcNo() {
		return purchasedByAcNo;
	}
	public void setPurchasedByAcNo(long purchasedByAcNo) {
		this.purchasedByAcNo = purchasedByAcNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public int getInvoiceStatusId() {
		return invoiceStatusId;
	}
	public void setInvoiceStatusId(int invoiceStatusId) {
		this.invoiceStatusId = invoiceStatusId;
	}
	public Date getInvoiceTs() {
		return invoiceTs;
	}
	public void setInvoiceTs(Date invoiceTs) {
		this.invoiceTs = invoiceTs;
	}
	public Date getEntryTs() {
		return entryTs;
	}
	public void setEntryTs(Date entryTs) {
		this.entryTs = entryTs;
	}
	public Date getDeliveryTs() {
		return deliveryTs;
	}
	public void setDeliveryTs(Date deliveryTs) {
		this.deliveryTs = deliveryTs;
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
	public BigDecimal getOtherTax() {
		return otherTax;
	}
	public void setOtherTax(BigDecimal otherTax) {
		this.otherTax = otherTax;
	}
	public BigDecimal getNetTotal() {
		return netTotal;
	}
	public void setNetTotal(BigDecimal netTotal) {
		this.netTotal = netTotal;
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
	public int getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(int paymentModeId) {
		this.paymentModeId = paymentModeId;
	}
	public Date getPaymentTs() {
		return paymentTs;
	}
	public void setPaymentTs(Date paymentTs) {
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
	public String getEntryLocation() {
		return entryLocation;
	}
	public void setEntryLocation(String entryLocation) {
		this.entryLocation = entryLocation;
	}

}
