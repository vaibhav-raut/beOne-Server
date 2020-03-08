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

import com.beone.shg.net.persistence.model.GProfile;

@Entity
@Table(name="brand"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class Brand  implements java.io.Serializable {

	private static final long serialVersionUID = 8836664920982476719L;
	private long brandId;
	private GProfile manufacture;
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
	private Date firstLotTs;
	private Date lastLotTs;
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
	@Column(name="brand_id", unique=true, nullable=false, insertable=true, updatable=true)
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="manufacture_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public GProfile getManufacture() {
		return manufacture;
	}
	public void setManufacture(GProfile manufacture) {
		this.manufacture = manufacture;
	}

	@Column(name="name", unique=false, nullable=true, insertable=true, updatable=true, length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="name_display", unique=false, nullable=true, insertable=true, updatable=true, length=15)
	public String getNameDisplay() {
		return nameDisplay;
	}
	public void setNameDisplay(String nameDisplay) {
		this.nameDisplay = nameDisplay;
	}
	
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="properties", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}

	@Column(name="link", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	@Column(name="no_stocked_types", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoStockedTypes() {
		return noStockedTypes;
	}
	public void setNoStockedTypes(int noStockedTypes) {
		this.noStockedTypes = noStockedTypes;
	}

	@Column(name="no_lots", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoLots() {
		return noLots;
	}
	public void setNoLots(int noLots) {
		this.noLots = noLots;
	}

	@Column(name="no_per_set", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoPerSet() {
		return noPerSet;
	}
	public void setNoPerSet(int noPerSet) {
		this.noPerSet = noPerSet;
	}

	@Column(name="no_min_sets", unique=false, nullable=true, insertable=true, updatable=true)
	public int getNoMinSets() {
		return noMinSets;
	}
	public void setNoMinSets(int noMinSets) {
		this.noMinSets = noMinSets;
	}

	@Column(name="total_purchase_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalPurchaseAm() {
		return totalPurchaseAm;
	}
	public void setTotalPurchaseAm(BigDecimal totalPurchaseAm) {
		this.totalPurchaseAm = totalPurchaseAm;
	}

	@Column(name="total_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalStockAm() {
		return totalStockAm;
	}
	public void setTotalStockAm(BigDecimal totalStockAm) {
		this.totalStockAm = totalStockAm;
	}

	@Column(name="total_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalSoldAm() {
		return totalSoldAm;
	}
	public void setTotalSoldAm(BigDecimal totalSoldAm) {
		this.totalSoldAm = totalSoldAm;
	}

	@Column(name="total_sold_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalSoldDiscountAm() {
		return totalSoldDiscountAm;
	}
	public void setTotalSoldDiscountAm(BigDecimal totalSoldDiscountAm) {
		this.totalSoldDiscountAm = totalSoldDiscountAm;
	}
	
	@Column(name="total_damage_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getTotalDamageAm() {
		return totalDamageAm;
	}
	public void setTotalDamageAm(BigDecimal totalDamageAm) {
		this.totalDamageAm = totalDamageAm;
	}

	@Column(name="total_purchase_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalPurchaseNo() {
		return totalPurchaseNo;
	}
	public void setTotalPurchaseNo(int totalPurchaseNo) {
		this.totalPurchaseNo = totalPurchaseNo;
	}

	@Column(name="total_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalStockNo() {
		return totalStockNo;
	}
	public void setTotalStockNo(int totalStockNo) {
		this.totalStockNo = totalStockNo;
	}

	@Column(name="total_sold_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalSoldNo() {
		return totalSoldNo;
	}
	public void setTotalSoldNo(int totalSoldNo) {
		this.totalSoldNo = totalSoldNo;
	}

	@Column(name="total_sold_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalSoldDiscountNo() {
		return totalSoldDiscountNo;
	}
	public void setTotalSoldDiscountNo(int totalSoldDiscountNo) {
		this.totalSoldDiscountNo = totalSoldDiscountNo;
	}
	
	@Column(name="total_damage_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getTotalDamageNo() {
		return totalDamageNo;
	}
	public void setTotalDamageNo(int totalDamageNo) {
		this.totalDamageNo = totalDamageNo;
	}

	@Column(name="current_ordered_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentOrderedStockAm() {
		return currentOrderedStockAm;
	}
	public void setCurrentOrderedStockAm(BigDecimal currentOrderedStockAm) {
		this.currentOrderedStockAm = currentOrderedStockAm;
	}

	@Column(name="current_delivered_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentDeliveredStockAm() {
		return currentDeliveredStockAm;
	}
	public void setCurrentDeliveredStockAm(BigDecimal currentDeliveredStockAm) {
		this.currentDeliveredStockAm = currentDeliveredStockAm;
	}

	@Column(name="current_to_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentToStockAm() {
		return currentToStockAm;
	}
	public void setCurrentToStockAm(BigDecimal currentToStockAm) {
		this.currentToStockAm = currentToStockAm;
	}

	@Column(name="current_created_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentCreatedStockAm() {
		return currentCreatedStockAm;
	}
	public void setCurrentCreatedStockAm(BigDecimal currentCreatedStockAm) {
		this.currentCreatedStockAm = currentCreatedStockAm;
	}

	@Column(name="current_stock_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentStockAm() {
		return currentStockAm;
	}
	public void setCurrentStockAm(BigDecimal currentStockAm) {
		this.currentStockAm = currentStockAm;
	}

	@Column(name="current_stock_discount_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getCurrentStockDiscountAm() {
		return currentStockDiscountAm;
	}
	public void setCurrentStockDiscountAm(BigDecimal currentStockDiscountAm) {
		this.currentStockDiscountAm = currentStockDiscountAm;
	}
	
	@Column(name="current_ordered_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentOrderedStockNo() {
		return currentOrderedStockNo;
	}
	public void setCurrentOrderedStockNo(int currentOrderedStockNo) {
		this.currentOrderedStockNo = currentOrderedStockNo;
	}

	@Column(name="current_delivered_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentDeliveredStockNo() {
		return currentDeliveredStockNo;
	}
	public void setCurrentDeliveredStockNo(int currentDeliveredStockNo) {
		this.currentDeliveredStockNo = currentDeliveredStockNo;
	}

	@Column(name="current_to_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentToStockNo() {
		return currentToStockNo;
	}
	public void setCurrentToStockNo(int currentToStockNo) {
		this.currentToStockNo = currentToStockNo;
	}

	@Column(name="current_created_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentCreatedStockNo() {
		return currentCreatedStockNo;
	}
	public void setCurrentCreatedStockNo(int currentCreatedStockNo) {
		this.currentCreatedStockNo = currentCreatedStockNo;
	}

	@Column(name="current_stock_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentStockNo() {
		return currentStockNo;
	}
	public void setCurrentStockNo(int currentStockNo) {
		this.currentStockNo = currentStockNo;
	}

	@Column(name="current_stock_discount_no", unique=false, nullable=true, insertable=true, updatable=true)
	public int getCurrentStockDiscountNo() {
		return currentStockDiscountNo;
	}
	public void setCurrentStockDiscountNo(int currentStockDiscountNo) {
		this.currentStockDiscountNo = currentStockDiscountNo;
	}
	
	@Column(name="first_stock_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getFirstStockPriceAm() {
		return firstStockPriceAm;
	}
	public void setFirstStockPriceAm(BigDecimal firstStockPriceAm) {
		this.firstStockPriceAm = firstStockPriceAm;
	}

	@Column(name="last_stock_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getLastStockPriceAm() {
		return lastStockPriceAm;
	}
	public void setLastStockPriceAm(BigDecimal lastStockPriceAm) {
		this.lastStockPriceAm = lastStockPriceAm;
	}

	@Column(name="avg_mr_item_sold_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getAvgMrItemSoldAm() {
		return avgMrItemSoldAm;
	}
	public void setAvgMrItemSoldAm(BigDecimal avgMrItemSoldAm) {
		this.avgMrItemSoldAm = avgMrItemSoldAm;
	}

	@Column(name="first_lot_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getFirstLotTs() {
		return firstLotTs;
	}
	public void setFirstLotTs(Date firstLotTs) {
		this.firstLotTs = firstLotTs;
	}

	@Column(name="last_lot_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getLastLotTs() {
		return lastLotTs;
	}
	public void setLastLotTs(Date lastLotTs) {
		this.lastLotTs = lastLotTs;
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
