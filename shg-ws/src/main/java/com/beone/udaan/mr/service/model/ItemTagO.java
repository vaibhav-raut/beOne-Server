package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTagO {

	private String tagStatus;
	private String brandName;
	private int itemNos;
	private List<ByType> byTypes;
	
	public String getTagStatus() {
		return tagStatus;
	}
	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public int getItemNos() {
		return itemNos;
	}
	public void setItemNos(int itemNos) {
		this.itemNos = itemNos;
	}
	public List<ByType> getByTypes() {
		return byTypes;
	}
	public void setByTypes(List<ByType> byTypes) {
		this.byTypes = byTypes;
	}
	public void addByType(ByType byType) {
		if(this.byTypes == null) {
			this.byTypes = new ArrayList<ByType>();
		}
		this.byTypes.add(byType);
	}
	
	public static class ByType {

		private long stockTypeId;
		private String tagStatus;
		private String stockTypeName;
		private String brandName;
		private BigDecimal mrpPriceAm;
		private int itemNos;
		private List<ItemTagM> itemTags;
		
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
		public int getItemNos() {
			return itemNos;
		}
		public void setItemNos(int itemNos) {
			this.itemNos = itemNos;
		}
		public List<ItemTagM> getItemTags() {
			return itemTags;
		}
		public void setItemTags(List<ItemTagM> itemTags) {
			this.itemTags = itemTags;
		}
		public void addItemTag(ItemTagM itemTag) {
			if(this.itemTags == null) {
				this.itemTags = new ArrayList<ItemTagM>();
			}
			this.itemTags.add(itemTag);
		}
	}
	
	public static ItemTagO buildItemTagO(List<ItemTagM> itemTags) {
		
		ItemTagO itemTagO = new ItemTagO();
		
		Map<Long, ByType> byTypeMap = new HashMap<Long, ByType>();
		int itemNos = 0;
		boolean multiBrand = false;
		
		for(ItemTagM itemTag : itemTags) {
			
			if(!byTypeMap.containsKey(itemTag.getStockTypeId())) {
				ByType byType = new ByType(); 
				
				byType.setStockTypeId(itemTag.getStockTypeId());
				byType.setTagStatus(itemTag.getTagStatus());
				byType.setStockTypeName(itemTag.getStockTypeName());
				byType.setBrandName(itemTag.getBrandName());
				byType.setMrpPriceAm(itemTag.getMrpPriceAm());
				byType.setItemNos(1);
				byType.addItemTag(itemTag);
				
				byTypeMap.put(itemTag.getStockTypeId(), byType);
								
				if(!multiBrand) {
					if(itemTagO.getBrandName() == null) {
						itemTagO.setTagStatus(itemTag.getTagStatus());
						itemTagO.setBrandName(itemTag.getBrandName());
					} else if(!itemTagO.getBrandName().equals(itemTag.getBrandName())) {
						itemTagO.setBrandName(null);
						multiBrand = true;
					}
				}
			}
			else {
				ByType byType = byTypeMap.get(itemTag.getStockTypeId());
				
				if(itemTag.getMrpPriceAm().compareTo(byType.getMrpPriceAm()) > 0) {
					byType.setMrpPriceAm(itemTag.getMrpPriceAm());
				}
				byType.setItemNos(byType.getItemNos() + 1);
				byType.addItemTag(itemTag);
			}
			
			itemNos++;
		}
		
		for(ByType byType: byTypeMap.values()) {
			itemTagO.addByType(byType);
		}		
		itemTagO.setItemNos(itemNos);
		
		return itemTagO;
	}
}
