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
@Table(name="catalog_item"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class CatalogItem implements java.io.Serializable {

	private static final long serialVersionUID = -9197746789470241191L;
	private long catalogItemId;
	private StockType stockType;
	private String name;
	private String properties;
	private String pics;
	private String link;
	private BigDecimal mrPriceAm;
	private BigDecimal mrpPriceAm;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="catalog_item_id", unique=true, nullable=false, insertable=true, updatable=true)
	public long getCatalogItemId() {
		return catalogItemId;
	}
	public void setCatalogItemId(long catalogItemId) {
		this.catalogItemId = catalogItemId;
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

	@Column(name="name", unique=false, nullable=false, insertable=true, updatable=true, length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="properties", unique=false, nullable=false, insertable=true, updatable=true, length=200)
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}

	@Column(name="pics", unique=false, nullable=false, insertable=true, updatable=true, length=200)
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}

	@Column(name="link", unique=false, nullable=false, insertable=true, updatable=true, length=200)
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	@Column(name="mr_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrPriceAm() {
		return mrPriceAm;
	}
	public void setMrPriceAm(BigDecimal mrPriceAm) {
		this.mrPriceAm = mrPriceAm;
	}

	@Column(name="mrp_price_am", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public BigDecimal getMrpPriceAm() {
		return mrpPriceAm;
	}
	public void setMrpPriceAm(BigDecimal mrpPriceAm) {
		this.mrpPriceAm = mrpPriceAm;
	}

}
