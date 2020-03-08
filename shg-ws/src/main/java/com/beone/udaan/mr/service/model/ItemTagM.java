package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;

import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.ItemTag;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

public class ItemTagM {

	private long itemTagId;
	private long stockItemId;
	private long stockTypeId;
	private String tagStatus;
	private String stockTypeName;
	private String designNo;
	private String brandName;
	private BigDecimal mrpPriceAm;
	private BigDecimal disMrpPriceAm;
	private float discountPer;
	private long createdTs;
	private long printedTs;

	public long getItemTagId() {
		return itemTagId;
	}
	public void setItemTagId(long itemTagId) {
		this.itemTagId = itemTagId;
	}
	public long getStockItemId() {
		return stockItemId;
	}
	public void setStockItemId(long stockItemId) {
		this.stockItemId = stockItemId;
	}
	public long getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getTagStatus() {
		return tagStatus;
	}
	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}
	public String getStockTypeName() {
		return stockTypeName;
	}
	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}
	public String getDesignNo() {
		return designNo;
	}
	public void setDesignNo(String designNo) {
		this.designNo = designNo;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public BigDecimal getMrpPriceAm() {
		return mrpPriceAm;
	}
	public void setMrpPriceAm(BigDecimal mrpPriceAm) {
		this.mrpPriceAm = mrpPriceAm;
	}
	public BigDecimal getDisMrpPriceAm() {
		return disMrpPriceAm;
	}
	public void setDisMrpPriceAm(BigDecimal disMrpPriceAm) {
		this.disMrpPriceAm = disMrpPriceAm;
	}
	public float getDiscountPer() {
		return discountPer;
	}
	public void setDiscountPer(float discountPer) {
		this.discountPer = discountPer;
	}
	public long getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(long createdTs) {
		this.createdTs = createdTs;
	}
	public long getPrintedTs() {
		return printedTs;
	}
	public void setPrintedTs(long printedTs) {
		this.printedTs = printedTs;
	}
	
	public static ItemTagM buildItemTag(ItemTag itemTag) {
		
		ItemTagM itemTagM = new ItemTagM();
		
		itemTagM.setItemTagId(itemTag.getItemTagId());
		itemTagM.setStockItemId(itemTag.getStockItemId());
		itemTagM.setStockTypeId(itemTag.getStockTypeId());
		itemTagM.setTagStatus(EnumCacheMr.getNameOfStatusValue(EnumConstMr.TagStatus, itemTag.getTagStatusId()));
		itemTagM.setStockTypeName(itemTag.getStockTypeName());
		itemTagM.setDesignNo(itemTag.getDesignNo());
		itemTagM.setBrandName(itemTag.getBrandName());
		itemTagM.setMrpPriceAm(itemTag.getMrpPriceAm());
		itemTagM.setDisMrpPriceAm(itemTag.getDisMrpPriceAm());
		itemTagM.setDiscountPer(itemTag.getDiscountPer());
		itemTagM.setCreatedTs(DateUtil.convertDateToTime(itemTag.getCreatedTs()));
		itemTagM.setPrintedTs(DateUtil.convertDateToTime(itemTag.getPrintedTs()));
		
		return itemTagM;
	}
}
