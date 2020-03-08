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

@Entity
@Table(name="stock_item"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class StockItem implements java.io.Serializable {

	private static final long serialVersionUID = -9197746789470241191L;
	private long stockItemId;
	private StockType stockType;
	private Lot lot;
	private SInvoice saleInvoice;
	private int itemStatusId;
	private int itemConditionId;
	private Pkg pkg;
	private PMAc owner;
	private String name;
	private String designNo;
	private BigDecimal wsPrice;
	private BigDecimal mrPrice;
	private BigDecimal mrpPrice;
	private BigDecimal disMrpPrice;
	private BigDecimal soldPrice;
	private BigDecimal mrSoldPrice;
	private float discountPer;
	private BigDecimal discountAm;
	private float vatPer;
	private BigDecimal vatAm;
	private Date checkTs;
	private int checkFlag;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="stock_item_id", unique=true, nullable=false, insertable=true, updatable=true)
	public long getStockItemId() {
		return stockItemId;
	}
	public void setStockItemId(long stockItemId) {
		this.stockItemId = stockItemId;
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
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="lot_id", unique=false, nullable=false, insertable=true, updatable=true)
	public Lot getLot() {
		return lot;
	}
	public void setLot(Lot lot) {
		this.lot = lot;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="s_invoice_id", unique=false, nullable=true, insertable=true, updatable=true)
	public SInvoice getSaleInvoice() {
		return saleInvoice;
	}
	public void setSaleInvoice(SInvoice saleInvoice) {
		this.saleInvoice = saleInvoice;
	}

	@Column(name="item_status_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getItemStatusId() {
		return itemStatusId;
	}
	public void setItemStatusId(int itemStatusId) {
		this.itemStatusId = itemStatusId;
	}
	
	@Column(name="item_condition_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getItemConditionId() {
		return itemConditionId;
	}
	public void setItemConditionId(int itemConditionId) {
		this.itemConditionId = itemConditionId;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="pkg_id", unique=false, nullable=true, insertable=true, updatable=true)
	public Pkg getPkg() {
		return pkg;
	}
	public void setPkg(Pkg pkg) {
		this.pkg = pkg;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="owner_ac_no", unique=false, nullable=true, insertable=true, updatable=true)
	public PMAc getOwner() {
		return owner;
	}
	public void setOwner(PMAc owner) {
		this.owner = owner;
	}
	@Column(name="name", unique=false, nullable=false, insertable=true, updatable=true, length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="design_no", unique=false, nullable=false, insertable=true, updatable=true, length=20)
	public String getDesignNo() {
		return designNo;
	}
	public void setDesignNo(String designNo) {
		this.designNo = designNo;
	}

	@Column(name="ws_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getWsPrice() {
		return wsPrice;
	}
	public void setWsPrice(BigDecimal wsPrice) {
		this.wsPrice = wsPrice;
	}

	@Column(name="mr_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrPrice() {
		return mrPrice;
	}
	public void setMrPrice(BigDecimal mrPrice) {
		this.mrPrice = mrPrice;
	}

	@Column(name="mrp_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrpPrice() {
		return mrpPrice;
	}
	public void setMrpPrice(BigDecimal mrpPrice) {
		this.mrpPrice = mrpPrice;
	}

	@Column(name="dis_mrp_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getDisMrpPrice() {
		return disMrpPrice;
	}
	public void setDisMrpPrice(BigDecimal disMrpPrice) {
		this.disMrpPrice = disMrpPrice;
	}

	@Column(name="sold_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getSoldPrice() {
		return soldPrice;
	}
	public void setSoldPrice(BigDecimal soldPrice) {
		this.soldPrice = soldPrice;
	}

	@Column(name="mr_sold_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrSoldPrice() {
		return mrSoldPrice;
	}
	public void setMrSoldPrice(BigDecimal mrSoldPrice) {
		this.mrSoldPrice = mrSoldPrice;
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

	@Column(name="vat_per", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getVatPer() {
		return vatPer;
	}
	public void setVatPer(float vatPer) {
		this.vatPer = vatPer;
	}

	@Column(name="vat_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getVatAm() {
		return vatAm;
	}
	public void setVatAm(BigDecimal vatAm) {
		this.vatAm = vatAm;
	}

	@Column(name="check_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getCheckTs() {
		return checkTs;
	}
	public void setCheckTs(Date checkTs) {
		this.checkTs = checkTs;
	}

	@Column(name="check_flag", unique=false, nullable=false, insertable=true, updatable=true)
	public int getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(int checkFlag) {
		this.checkFlag = checkFlag;
	}

}
