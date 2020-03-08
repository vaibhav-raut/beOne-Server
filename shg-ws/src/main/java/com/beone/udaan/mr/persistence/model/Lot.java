package com.beone.udaan.mr.persistence.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="lot"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class Lot implements java.io.Serializable {

	private static final long serialVersionUID = 3057607684722827888L;
	private long lotId;
	private PInvoice pInvoice;
	private StockType stockType;
	private int lotStatusId;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="lot_id", unique=true, nullable=false, insertable=true, updatable=true)
	
	public long getLotId() {
		return lotId;
	}
	public void setLotId(long lotId) {
		this.lotId = lotId;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="p_invoice_id", unique=false, nullable=false, insertable=true, updatable=true)
	public PInvoice getPInvoice() {
		return pInvoice;
	}
	public void setPInvoice(PInvoice pInvoice) {
		this.pInvoice = pInvoice;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="stock_type_id", unique=false, nullable=false, insertable=true, updatable=true)
	public StockType getStockType() {
		return stockType;
	}
	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	@Column(name="lot_status_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getLotStatusId() {
		return lotStatusId;
	}
	public void setLotStatusId(int lotStatusId) {
		this.lotStatusId = lotStatusId;
	}

	@Column(name="name", unique=false, nullable=false, insertable=true, updatable=true, length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="no_per_set", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoPerSet() {
		return noPerSet;
	}
	public void setNoPerSet(int noPerSet) {
		this.noPerSet = noPerSet;
	}

	@Column(name="no_of_sets", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoOfSets() {
		return noOfSets;
	}
	public void setNoOfSets(int noOfSets) {
		this.noOfSets = noOfSets;
	}

	@Column(name="no_purchased", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoPurchased() {
		return noPurchased;
	}
	public void setNoPurchased(int noPurchased) {
		this.noPurchased = noPurchased;
	}

	@Column(name="no_delivered", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoDelivered() {
		return noDelivered;
	}
	public void setNoDelivered(int noDelivered) {
		this.noDelivered = noDelivered;
	}

	@Column(name="no_to_stock", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoToStock() {
		return noToStock;
	}
	public void setNoToStock(int noToStock) {
		this.noToStock = noToStock;
	}

	@Column(name="no_created", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoCreated() {
		return noCreated;
	}
	public void setNoCreated(int noCreated) {
		this.noCreated = noCreated;
	}
	
	@Column(name="no_stocked", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoStocked() {
		return noStocked;
	}
	public void setNoStocked(int noStocked) {
		this.noStocked = noStocked;
	}

	@Column(name="no_sold", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoSold() {
		return noSold;
	}
	public void setNoSold(int noSold) {
		this.noSold = noSold;
	}

	@Column(name="no_damaged", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoDamaged() {
		return noDamaged;
	}
	public void setNoDamaged(int noDamaged) {
		this.noDamaged = noDamaged;
	}

	@Column(name="item_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getItemPriceAm() {
		return itemPriceAm;
	}
	public void setItemPriceAm(BigDecimal itemPriceAm) {
		this.itemPriceAm = itemPriceAm;
	}

	@Column(name="lot_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLotPriceAm() {
		return lotPriceAm;
	}
	public void setLotPriceAm(BigDecimal lotPriceAm) {
		this.lotPriceAm = lotPriceAm;
	}

	@Column(name="discount_per", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getDiscountPer() {
		return discountPer;
	}
	public void setDiscountPer(float discountPer) {
		this.discountPer = discountPer;
	}

	@Column(name="discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getDiscountAm() {
		return discountAm;
	}
	public void setDiscountAm(BigDecimal discountAm) {
		this.discountAm = discountAm;
	}

	@Column(name="vat_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getVatAm() {
		return vatAm;
	}
	public void setVatAm(BigDecimal vatAm) {
		this.vatAm = vatAm;
	}

	@Column(name="avg_mr_item_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getAvgMrItemSoldAm() {
		return avgMrItemSoldAm;
	}
	public void setAvgMrItemSoldAm(BigDecimal avgMrItemSoldAm) {
		this.avgMrItemSoldAm = avgMrItemSoldAm;
	}
	
	@Column(name="return_counter", unique=false, nullable=true, insertable=true, updatable=true)
	public int getReturnCounter() {
		return returnCounter;
	}
	public void setReturnCounter(int returnCounter) {
		this.returnCounter = returnCounter;
	}

	@Column(name="performance_index", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getPerformanceIndex() {
		return performanceIndex;
	}
	public void setPerformanceIndex(float performanceIndex) {
		this.performanceIndex = performanceIndex;
	}

	@Column(name="return_index", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getReturnIndex() {
		return returnIndex;
	}
	public void setReturnIndex(float returnIndex) {
		this.returnIndex = returnIndex;
	}

	@Column(name="sales_index", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSalesIndex() {
		return salesIndex;
	}
	public void setSalesIndex(float salesIndex) {
		this.salesIndex = salesIndex;
	}

	@Column(name="sales_20_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales20PerDays() {
		return sales20PerDays;
	}
	public void setSales20PerDays(float sales20PerDays) {
		this.sales20PerDays = sales20PerDays;
	}

	@Column(name="sales_40_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales40PerDays() {
		return sales40PerDays;
	}
	public void setSales40PerDays(float sales40PerDays) {
		this.sales40PerDays = sales40PerDays;
	}

	@Column(name="sales_60_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales60PerDays() {
		return sales60PerDays;
	}
	public void setSales60PerDays(float sales60PerDays) {
		this.sales60PerDays = sales60PerDays;
	}

	@Column(name="sales_80_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales80PerDays() {
		return sales80PerDays;
	}
	public void setSales80PerDays(float sales80PerDays) {
		this.sales80PerDays = sales80PerDays;
	}

	@Column(name="sales_100_per_days", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getSales100PerDays() {
		return sales100PerDays;
	}
	public void setSales100PerDays(float sales100PerDays) {
		this.sales100PerDays = sales100PerDays;
	}

}
