package com.beone.udaan.mr.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.beone.shg.net.persistence.model.MProfile;

@Entity
@Table(name="s_invoice"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class SInvoice {

	private long invoiceId;
	private MProfile soldBy;
	private MProfile entryBy;
	private MProfile purchasedBy;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="s_invoice_id", unique=true, nullable=false, insertable=true, updatable=true)
	
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="sold_by_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public MProfile getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(MProfile soldBy) {
		this.soldBy = soldBy;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="entry_by_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public MProfile getEntryBy() {
		return entryBy;
	}
	public void setEntryBy(MProfile entryBy) {
		this.entryBy = entryBy;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="purchased_by_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public MProfile getPurchasedBy() {
		return purchasedBy;
	}
	public void setPurchasedBy(MProfile purchasedBy) {
		this.purchasedBy = purchasedBy;
	}

	@Column(name="invoice_status_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getInvoiceStatusId() {
		return invoiceStatusId;
	}
	public void setInvoiceStatusId(int invoiceStatusId) {
		this.invoiceStatusId = invoiceStatusId;
	}

	@Column(name="invoice_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getInvoiceTs() {
		return invoiceTs;
	}
	public void setInvoiceTs(Date invoiceTs) {
		this.invoiceTs = invoiceTs;
	}

	@Column(name="entry_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getEntryTs() {
		return entryTs;
	}
	public void setEntryTs(Date entryTs) {
		this.entryTs = entryTs;
	}

	@Column(name="delivery_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getDeliveryTs() {
		return deliveryTs;
	}
	public void setDeliveryTs(Date deliveryTs) {
		this.deliveryTs = deliveryTs;
	}

	@Column(name="no_of_lots", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoOfLots() {
		return noOfLots;
	}
	public void setNoOfLots(int noOfLots) {
		this.noOfLots = noOfLots;
	}

	@Column(name="no_of_items", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoOfItems() {
		return noOfItems;
	}
	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	@Column(name="total_am", unique=false, nullable=true, insertable=true, updatable=true)
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Column(name="discount_am", unique=false, nullable=true, insertable=true, updatable=true)
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Column(name="gross_total_am", unique=false, nullable=true, insertable=true, updatable=true)
	public BigDecimal getGrossTotal() {
		return grossTotal;
	}
	public void setGrossTotal(BigDecimal grossTotal) {
		this.grossTotal = grossTotal;
	}

	@Column(name="vat_am", unique=false, nullable=true, insertable=true, updatable=true)
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	@Column(name="other_tax_am", unique=false, nullable=true, insertable=true, updatable=true)
	public BigDecimal getOtherTax() {
		return otherTax;
	}
	public void setOtherTax(BigDecimal otherTax) {
		this.otherTax = otherTax;
	}

	@Column(name="net_total_am", unique=false, nullable=true, insertable=true, updatable=true)
	public BigDecimal getNetTotal() {
		return netTotal;
	}
	public void setNetTotal(BigDecimal netTotal) {
		this.netTotal = netTotal;
	}

	@Column(name="advance_am", unique=false, nullable=true, insertable=true, updatable=true)
	public BigDecimal getAdvance() {
		return advance;
	}
	public void setAdvance(BigDecimal advance) {
		this.advance = advance;
	}

	@Column(name="net_payment_am", unique=false, nullable=true, insertable=true, updatable=true)
	public BigDecimal getNetPayment() {
		return netPayment;
	}
	public void setNetPayment(BigDecimal netPayment) {
		this.netPayment = netPayment;
	}

	@Column(name="payment_mode_id", unique=false, nullable=true, insertable=true, updatable=true)
	public int getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(int paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	@Column(name="payment_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getPaymentTs() {
		return paymentTs;
	}
	public void setPaymentTs(Date paymentTs) {
		this.paymentTs = paymentTs;
	}

	@Column(name="cheque_no", unique=false, nullable=true, insertable=true, updatable=true, length=20)
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="entry_location", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getEntryLocation() {
		return entryLocation;
	}
	public void setEntryLocation(String entryLocation) {
		this.entryLocation = entryLocation;
	}

}
