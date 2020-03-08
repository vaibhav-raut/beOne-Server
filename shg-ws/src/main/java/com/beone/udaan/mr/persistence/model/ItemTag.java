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
@Table(name="item_tag"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ItemTag  implements java.io.Serializable {

	private static final long serialVersionUID = 8836664920982476719L;
	private long itemTagId;
	private long stockItemId;
	private long stockTypeId;
	private int tagStatusId;
	private String stockTypeName;
	private String designNo;
	private String brandName;
	private BigDecimal mrpPriceAm;
	private BigDecimal disMrpPriceAm;
	private float discountPer;
	private Date createdTs;
	private Date printedTs;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="item_tag_id", unique=true, nullable=false, insertable=true, updatable=true)
	public long getItemTagId() {
		return itemTagId;
	}
	public void setItemTagId(long itemTagId) {
		this.itemTagId = itemTagId;
	}

	@Column(name="stock_item_id", unique=false, nullable=false, insertable=true, updatable=true)
	public long getStockItemId() {
		return stockItemId;
	}
	public void setStockItemId(long stockItemId) {
		this.stockItemId = stockItemId;
	}
	
	@Column(name="stock_type_id", unique=false, nullable=false, insertable=true, updatable=true)
	public long getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	
	@Column(name="tag_status_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getTagStatusId() {
		return tagStatusId;
	}
	public void setTagStatusId(int tagStatusId) {
		this.tagStatusId = tagStatusId;
	}

	@Column(name="stock_type_name", unique=false, nullable=true, insertable=true, updatable=true, length=100)
	public String getStockTypeName() {
		return stockTypeName;
	}
	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}

	@Column(name="design_no", unique=false, nullable=true, insertable=true, updatable=true, length=20)
	public String getDesignNo() {
		return designNo;
	}
	public void setDesignNo(String designNo) {
		this.designNo = designNo;
	}

	@Column(name="brand_name", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name="mrp_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrpPriceAm() {
		return mrpPriceAm;
	}
	public void setMrpPriceAm(BigDecimal mrpPriceAm) {
		this.mrpPriceAm = mrpPriceAm;
	}

	@Column(name="dis_mrp_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getDisMrpPriceAm() {
		return disMrpPriceAm;
	}
	public void setDisMrpPriceAm(BigDecimal disMrpPriceAm) {
		this.disMrpPriceAm = disMrpPriceAm;
	}

	@Column(name="discount_per", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getDiscountPer() {
		return discountPer;
	}
	public void setDiscountPer(float discountPer) {
		this.discountPer = discountPer;
	}

	@Column(name="created_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	@Column(name="printed_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getPrintedTs() {
		return printedTs;
	}
	public void setPrintedTs(Date printedTs) {
		this.printedTs = printedTs;
	}
}
