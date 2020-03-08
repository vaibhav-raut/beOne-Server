package com.beone.udaan.mr.persistence.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.MProfile;

@Entity
@Table(name="p_invoice"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class PInvoice  implements java.io.Serializable {

	private static final long serialVersionUID = 7131628588470832346L;
	private long purchasedInvoiceId;
	private GProfile vender;
	private String invoiceNo;
	private int invoiceStatusId;
	private Date invoiceTs;
	private Date entryTs;
	private Date stockTs;
	private Date expectedDeliveryTs;
	private String lrSlips;
	private MProfile entryBy;
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
	private int paymentModeId;
	private Date paymentTs;
	private String chequeNo;
	private String description;
	private String attachment;
	private String entryLocation;
	private Set<Lot> lots = new HashSet<Lot>(0);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="p_invoice_id", unique=true, nullable=false, insertable=true, updatable=true)
	
	public long getPurchasedInvoiceId() {
		return purchasedInvoiceId;
	}
	public void setPurchasedInvoiceId(long purchasedInvoiceId) {
		this.purchasedInvoiceId = purchasedInvoiceId;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="vender_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public GProfile getVender() {
		return vender;
	}
	public void setVender(GProfile vender) {
		this.vender = vender;
	}

	@Column(name="invoice_no", unique=false, nullable=true, insertable=true, updatable=true, length=20)
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	@Column(name="stock_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getStockTs() {
		return stockTs;
	}
	public void setStockTs(Date stockTs) {
		this.stockTs = stockTs;
	}

	@Column(name="expected_delivery_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getExpectedDeliveryTs() {
		return expectedDeliveryTs;
	}
	public void setExpectedDeliveryTs(Date expectedDeliveryTs) {
		this.expectedDeliveryTs = expectedDeliveryTs;
	}

	@Column(name="lr_slips", unique=false, nullable=true, insertable=true, updatable=true, length=100)
	public String getLrSlips() {
		return lrSlips;
	}
	public void setLrSlips(String lrSlips) {
		this.lrSlips = lrSlips;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="entry_by", unique=false, nullable=false, insertable=true, updatable=true)
	public MProfile getEntryBy() {
		return entryBy;
	}
	public void setEntryBy(MProfile entryBy) {
		this.entryBy = entryBy;
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

	@Column(name="vat_per", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getVatPer() {
		return vatPer;
	}
	public void setVatPer(float vatPer) {
		this.vatPer = vatPer;
	}

	@Column(name="discount_per", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getDiscountPer() {
		return discountPer;
	}
	public void setDiscountPer(float discountPer) {
		this.discountPer = discountPer;
	}
	
	@Column(name="total_am", unique=false, nullable=true, insertable=true, updatable=true)
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Column(name="discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Column(name="gross_total_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getGrossTotal() {
		return grossTotal;
	}
	public void setGrossTotal(BigDecimal grossTotal) {
		this.grossTotal = grossTotal;
	}

	@Column(name="vat_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	@Column(name="other_taxes_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getOtherTaxes() {
		return otherTaxes;
	}
	public void setOtherTaxes(BigDecimal otherTaxes) {
		this.otherTaxes = otherTaxes;
	}

	@Column(name="net_total_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getNetTotal() {
		return netTotal;
	}
	public void setNetTotal(BigDecimal netTotal) {
		this.netTotal = netTotal;
	}

	@Column(name="net_total_calculated_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getNetTotalCalculated() {
		return netTotalCalculated;
	}
	public void setNetTotalCalculated(BigDecimal netTotalCalculated) {
		this.netTotalCalculated = netTotalCalculated;
	}

	@Column(name="advance_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getAdvance() {
		return advance;
	}
	public void setAdvance(BigDecimal advance) {
		this.advance = advance;
	}

	@Column(name="net_payment_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getNetPayment() {
		return netPayment;
	}
	public void setNetPayment(BigDecimal netPayment) {
		this.netPayment = netPayment;
	}

	@Column(name="payment_mode_id", unique=false, nullable=false, insertable=true, updatable=true)
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

	@Column(name="attachment", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@Column(name="entry_location", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getEntryLocation() {
		return entryLocation;
	}
	public void setEntryLocation(String entryLocation) {
		this.entryLocation = entryLocation;
	}

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="PInvoice")
	public Set<Lot> getLots() {
		return lots;
	}
	public void setLots(Set<Lot> lots) {
		this.lots = lots;
	}

}
