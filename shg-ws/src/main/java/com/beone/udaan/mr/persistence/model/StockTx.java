package com.beone.udaan.mr.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stock_tx"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class StockTx implements java.io.Serializable {

	private static final long serialVersionUID = 3154118767654126265L;
	private long stockTxId;
	private int stockTxTypeId;
	private long stockItemId;
	private long stockTypeId;
	private long brandId;
	private long lotId;
	private String name;
	private BigDecimal amount;
	private BigDecimal extraAm;
	private long entryByAcNo;
	private long ownerAcNo;
	private long authAcNo;
	private long mrVisitId;
	private int itemStatusId;
	private int prevItemStatusId;
	private Date txTs;
	private Date entryTs;
	private Date verifyTs;
	private String description;
	private String entryLocation;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="stock_tx_id", unique=true, nullable=false, insertable=true, updatable=true)
	
	public long getStockTxId() {
		return stockTxId;
	}
	public void setStockTxId(long stockTxId) {
		this.stockTxId = stockTxId;
	}

	@Column(name="stock_tx_type_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getStockTxTypeId() {
		return stockTxTypeId;
	}
	public void setStockTxTypeId(int stockTxTypeId) {
		this.stockTxTypeId = stockTxTypeId;
	}

	@Column(name="stock_item_id", unique=false, nullable=true, insertable=true, updatable=true)
	public long getStockItemId() {
		return stockItemId;
	}
	public void setStockItemId(long stockItemId) {
		this.stockItemId = stockItemId;
	}

	@Column(name="stock_type_id", unique=false, nullable=true, insertable=true, updatable=true)
	public long getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	@Column(name="brand_id", unique=false, nullable=true, insertable=true, updatable=true)
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	@Column(name="lot_id", unique=false, nullable=true, insertable=true, updatable=true)
	public long getLotId() {
		return lotId;
	}
	public void setLotId(long lotId) {
		this.lotId = lotId;
	}
	
	@Column(name="name", unique=false, nullable=true, insertable=true, updatable=true, length=35)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="amount", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name="extra_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getExtraAm() {
		return extraAm;
	}
	public void setExtraAm(BigDecimal extraAm) {
		this.extraAm = extraAm;
	}
	
	@Column(name="entry_by_ac_no", unique=false, nullable=true, insertable=true, updatable=true)
	public long getEntryByAcNo() {
		return entryByAcNo;
	}
	public void setEntryByAcNo(long entryByAcNo) {
		this.entryByAcNo = entryByAcNo;
	}

	@Column(name="owner_ac_no", unique=false, nullable=true, insertable=true, updatable=true)
	public long getOwnerAcNo() {
		return ownerAcNo;
	}
	public void setOwnerAcNo(long ownerAcNo) {
		this.ownerAcNo = ownerAcNo;
	}

	@Column(name="auth_ac_no", unique=false, nullable=true, insertable=true, updatable=true)
	public long getAuthAcNo() {
		return authAcNo;
	}
	public void setAuthAcNo(long authAcNo) {
		this.authAcNo = authAcNo;
	}

	@Column(name="mr_visit_id", unique=false, nullable=true, insertable=true, updatable=true)
	public long getMrVisitId() {
		return mrVisitId;
	}
	public void setMrVisitId(long mrVisitId) {
		this.mrVisitId = mrVisitId;
	}
	
	@Column(name="item_status_id", unique=false, nullable=true, insertable=true, updatable=true)
	public int getItemStatusId() {
		return itemStatusId;
	}
	public void setItemStatusId(int itemStatusId) {
		this.itemStatusId = itemStatusId;
	}

	@Column(name="prev_item_status_id", unique=false, nullable=true, insertable=true, updatable=true)
	public int getPrevItemStatusId() {
		return prevItemStatusId;
	}
	public void setPrevItemStatusId(int prevItemStatusId) {
		this.prevItemStatusId = prevItemStatusId;
	}

	@Column(name="tx_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getTxTs() {
		return txTs;
	}
	public void setTxTs(Date txTs) {
		this.txTs = txTs;
	}

	@Column(name="entry_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getEntryTs() {
		return entryTs;
	}
	public void setEntryTs(Date entryTs) {
		this.entryTs = entryTs;
	}

	@Column(name="verify_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getVerifyTs() {
		return verifyTs;
	}
	public void setVerifyTs(Date verifyTs) {
		this.verifyTs = verifyTs;
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
