package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.Brand;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockType;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

public class MrStock {

	private long ownerAcNo;
	private String ownerAcNoName;
	private BigDecimal cumMrPrice;
	private BigDecimal cumSelectedMrPrice;
	private int cumNoStock;
	private int cumSelectedNoStock;
	private Map<Long, ByBrand> brands;

	public MrStock() {
		super();
		cumMrPrice = DataUtil.ZERO_BIG_DECIMAL;
		cumSelectedMrPrice = DataUtil.ZERO_BIG_DECIMAL;
		cumNoStock = DataUtil.ZERO_INTEGER;
		cumSelectedNoStock = DataUtil.ZERO_INTEGER;
		this.brands = new LinkedHashMap<Long, ByBrand>();
	}
	public long getOwnerAcNo() {
		return ownerAcNo;
	}
	public void setOwnerAcNo(long ownerAcNo) {
		this.ownerAcNo = ownerAcNo;
	}
	public String getOwnerAcNoName() {
		return ownerAcNoName;
	}
	public void setOwnerAcNoName(String ownerAcNoName) {
		this.ownerAcNoName = ownerAcNoName;
	}
	public BigDecimal getCumMrPrice() {
		return cumMrPrice;
	}
	public void setCumMrPrice(BigDecimal cumMrPrice) {
		this.cumMrPrice = cumMrPrice;
	}
	public BigDecimal getCumSelectedMrPrice() {
		return cumSelectedMrPrice;
	}
	public void setCumSelectedMrPrice(BigDecimal cumSelectedMrPrice) {
		this.cumSelectedMrPrice = cumSelectedMrPrice;
	}
	public int getCumNoStock() {
		return cumNoStock;
	}
	public void setCumNoStock(int cumNoStock) {
		this.cumNoStock = cumNoStock;
	}
	public int getCumSelectedNoStock() {
		return cumSelectedNoStock;
	}
	public void setCumSelectedNoStock(int cumSelectedNoStock) {
		this.cumSelectedNoStock = cumSelectedNoStock;
	}
	public Map<Long, ByBrand> getBrands() {
		return brands;
	}
	public void setBrands(Map<Long, ByBrand> brands) {
		this.brands = brands;
	}
	public void putBrand(Long brandId, ByBrand brand) {
		this.brands.put(brandId, brand);
	}

	static public class ByBrand {
		private long brandId;
		private String name;
		private String description;
		private String properties;
		private BigDecimal cumMrPrice;
		private BigDecimal cumSelectedMrPrice;
		private int cumNoStock;
		private int cumSelectedNoStock;
		private Map<Long, ByType> types;
		
		public ByBrand() {
			super();
			cumMrPrice = DataUtil.ZERO_BIG_DECIMAL;
			cumSelectedMrPrice = DataUtil.ZERO_BIG_DECIMAL;
			cumNoStock = DataUtil.ZERO_INTEGER;
			cumSelectedNoStock = DataUtil.ZERO_INTEGER;
			this.types = new LinkedHashMap<Long, ByType>();
		}
		public long getBrandId() {
			return brandId;
		}
		public void setBrandId(long brandId) {
			this.brandId = brandId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getProperties() {
			return properties;
		}
		public void setProperties(String properties) {
			this.properties = properties;
		}
		public BigDecimal getCumMrPrice() {
			return cumMrPrice;
		}
		public void setCumMrPrice(BigDecimal cumMrPrice) {
			this.cumMrPrice = cumMrPrice;
		}
		public BigDecimal getCumSelectedMrPrice() {
			return cumSelectedMrPrice;
		}
		public void setCumSelectedMrPrice(BigDecimal cumSelectedMrPrice) {
			this.cumSelectedMrPrice = cumSelectedMrPrice;
		}
		public int getCumNoStock() {
			return cumNoStock;
		}
		public void setCumNoStock(int cumNoStock) {
			this.cumNoStock = cumNoStock;
		}
		public int getCumSelectedNoStock() {
			return cumSelectedNoStock;
		}
		public void setCumSelectedNoStock(int cumSelectedNoStock) {
			this.cumSelectedNoStock = cumSelectedNoStock;
		}
		public Map<Long, ByType> getTypes() {
			return types;
		}
		public void setTypes(Map<Long, ByType> types) {
			this.types = types;
		}
		public void putType(Long typeId, ByType type) {
			this.types.put(typeId, type);
		}
	}

	static public class ByType {
		private long stockTypeId;
		private String name;
		private String category;
		private String description;
		private String properties;
		private String pics;
		private BigDecimal cumMrPrice;
		private BigDecimal cumSelectedMrPrice;
		private int cumNoStock;
		private int cumSelectedNoStock;
		private Map<Long, Item> items;
		
		public ByType() {
			super();
			cumMrPrice = DataUtil.ZERO_BIG_DECIMAL;
			cumSelectedMrPrice = DataUtil.ZERO_BIG_DECIMAL;
			cumNoStock = DataUtil.ZERO_INTEGER;
			cumSelectedNoStock = DataUtil.ZERO_INTEGER;
			this.items = new LinkedHashMap<Long, Item>();
		}
		public long getStockTypeId() {
			return stockTypeId;
		}
		public void setStockTypeId(long stockTypeId) {
			this.stockTypeId = stockTypeId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getProperties() {
			return properties;
		}
		public void setProperties(String properties) {
			this.properties = properties;
		}
		public String getPics() {
			return pics;
		}
		public void setPics(String pics) {
			this.pics = pics;
		}
		public BigDecimal getCumMrPrice() {
			return cumMrPrice;
		}
		public void setCumMrPrice(BigDecimal cumMrPrice) {
			this.cumMrPrice = cumMrPrice;
		}
		public BigDecimal getCumSelectedMrPrice() {
			return cumSelectedMrPrice;
		}
		public void setCumSelectedMrPrice(BigDecimal cumSelectedMrPrice) {
			this.cumSelectedMrPrice = cumSelectedMrPrice;
		}
		public int getCumNoStock() {
			return cumNoStock;
		}
		public void setCumNoStock(int cumNoStock) {
			this.cumNoStock = cumNoStock;
		}
		public int getCumSelectedNoStock() {
			return cumSelectedNoStock;
		}
		public void setCumSelectedNoStock(int cumSelectedNoStock) {
			this.cumSelectedNoStock = cumSelectedNoStock;
		}
		public Map<Long, Item> getItems() {
			return items;
		}
		public void setItems(Map<Long, Item> items) {
			this.items = items;
		}
		public void putItem(Long itemId, Item item) {
			this.items.put(itemId, item);
		}
	}

	static public class Item {
		private long stockItemId;
		private String name;
		private String itemStatus;
		private String itemCondition;
		private String designNo;
		private BigDecimal wsPrice;
		private BigDecimal mrPrice;
		private BigDecimal disMrPrice;
		private BigDecimal mrpPrice;
		private BigDecimal disMrpPrice;
		private BigDecimal soldPrice;
		private BigDecimal mrSoldPrice;
		private float discountPer;
		private BigDecimal discountAm;
		private float vatPer;
		private BigDecimal vatAm;
		private long checkTs;
		private int checkFlag;
		
		public Item() {
			super();
			wsPrice = DataUtil.ZERO_BIG_DECIMAL;
			mrPrice = DataUtil.ZERO_BIG_DECIMAL;
			disMrPrice = DataUtil.ZERO_BIG_DECIMAL;
			mrpPrice = DataUtil.ZERO_BIG_DECIMAL;
			disMrpPrice = DataUtil.ZERO_BIG_DECIMAL;
			soldPrice = DataUtil.ZERO_BIG_DECIMAL;
			mrSoldPrice = DataUtil.ZERO_BIG_DECIMAL;
			discountPer = DataUtil.ZERO_FLOAT;
			discountAm = DataUtil.ZERO_BIG_DECIMAL;
			vatPer = DataUtil.ZERO_FLOAT;
			vatAm = DataUtil.ZERO_BIG_DECIMAL;
		}
		public long getStockItemId() {
			return stockItemId;
		}
		public void setStockItemId(long stockItemId) {
			this.stockItemId = stockItemId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getItemStatus() {
			return itemStatus;
		}
		public void setItemStatus(String itemStatus) {
			this.itemStatus = itemStatus;
		}
		public String getItemCondition() {
			return itemCondition;
		}
		public void setItemCondition(String itemCondition) {
			this.itemCondition = itemCondition;
		}
		public String getDesignNo() {
			return designNo;
		}
		public void setDesignNo(String designNo) {
			this.designNo = designNo;
		}
		public BigDecimal getWsPrice() {
			return wsPrice;
		}
		public void setWsPrice(BigDecimal wsPrice) {
			this.wsPrice = wsPrice;
		}
		public BigDecimal getMrPrice() {
			return mrPrice;
		}
		public void setMrPrice(BigDecimal mrPrice) {
			this.mrPrice = mrPrice;
		}
		public BigDecimal getDisMrPrice() {
			return disMrPrice;
		}
		public void setDisMrPrice(BigDecimal disMrPrice) {
			this.disMrPrice = disMrPrice;
		}
		public BigDecimal getMrpPrice() {
			return mrpPrice;
		}
		public void setMrpPrice(BigDecimal mrpPrice) {
			this.mrpPrice = mrpPrice;
		}
		public BigDecimal getDisMrpPrice() {
			return disMrpPrice;
		}
		public void setDisMrpPrice(BigDecimal disMrpPrice) {
			this.disMrpPrice = disMrpPrice;
		}
		public BigDecimal getSoldPrice() {
			return soldPrice;
		}
		public void setSoldPrice(BigDecimal soldPrice) {
			this.soldPrice = soldPrice;
		}
		public BigDecimal getMrSoldPrice() {
			return mrSoldPrice;
		}
		public void setMrSoldPrice(BigDecimal mrSoldPrice) {
			this.mrSoldPrice = mrSoldPrice;
		}
		public float getDiscountPer() {
			return discountPer;
		}
		public void setDiscountPer(float discountPer) {
			this.discountPer = discountPer;
		}
		public BigDecimal getDiscountAm() {
			return discountAm;
		}
		public void setDiscountAm(BigDecimal discountAm) {
			this.discountAm = discountAm;
		}
		public float getVatPer() {
			return vatPer;
		}
		public void setVatPer(float vatPer) {
			this.vatPer = vatPer;
		}
		public BigDecimal getVatAm() {
			return vatAm;
		}
		public void setVatAm(BigDecimal vatAm) {
			this.vatAm = vatAm;
		}
		public long getCheckTs() {
			return checkTs;
		}
		public void setCheckTs(long checkTs) {
			this.checkTs = checkTs;
		}
		public int getCheckFlag() {
			return checkFlag;
		}
		public void setCheckFlag(int checkFlag) {
			this.checkFlag = checkFlag;
		}
	}
	
	public static Item buildItem(StockItem stockItem) {
		Item item = new Item();
		
		item.setStockItemId(stockItem.getStockItemId());
		item.setName(stockItem.getName());
		item.setItemStatus(EnumCacheMr.getStatusValue(EnumConstMr.ItemStatus, stockItem.getItemStatusId()).getStatusValue());
		item.setItemCondition(EnumCacheMr.getNameOfItemCondition(stockItem.getItemConditionId()));
		item.setDesignNo(stockItem.getDesignNo());
		item.setWsPrice(stockItem.getWsPrice());
		item.setMrPrice(stockItem.getMrPrice());
		item.setDisMrPrice(BDUtil.sub(stockItem.getMrPrice(), stockItem.getDiscountAm()));
		item.setMrpPrice(stockItem.getMrpPrice());
		item.setDisMrpPrice(stockItem.getDisMrpPrice());
		item.setSoldPrice(stockItem.getSoldPrice());
		item.setMrSoldPrice(stockItem.getMrSoldPrice());
		item.setDiscountPer(stockItem.getDiscountPer());
		item.setDiscountAm(stockItem.getDiscountAm());
		item.setVatPer(stockItem.getVatPer());
		item.setVatAm(stockItem.getVatAm());
		item.setCheckFlag(stockItem.getCheckFlag());
		item.setCheckTs(stockItem.getCheckTs().getTime());
		
		return item;
	}
	
	public static ByType buildType(StockType stockType, List<Item> items) {
		ByType type = new ByType();
		
		type.setStockTypeId(stockType.getStockTypeId());
		type.setName(stockType.getName());
		type.setCategory(EnumCacheMr.getProductCategoryValue(stockType.getCategoryId()).getProductCategory());
		type.setDescription(stockType.getDescription());
		type.setProperties(stockType.getProperties());
		type.setPics(stockType.getPics());
		
		BigDecimal cumMrPrice = DataUtil.ZERO_BIG_DECIMAL;
		int cumNoStock = DataUtil.ZERO_INTEGER;
		
		if(items != null && !items.isEmpty()) {
			for(Item item : items) {
				cumMrPrice = BDUtil.add(cumMrPrice, item.getDisMrPrice());
				cumNoStock++;
				type.putItem(item.getStockItemId(), item);
			}
		}
		
		type.setCumMrPrice(cumMrPrice);
		type.setCumSelectedMrPrice(DataUtil.ZERO_BIG_DECIMAL);
		type.setCumNoStock(cumNoStock);
		type.setCumSelectedNoStock(DataUtil.ZERO_INTEGER);
		
		return type;
	}
	
	public static ByBrand buildBrand(Brand brand, List<ByType> types) {
		ByBrand byBrand = new ByBrand();
		
		byBrand.setBrandId(brand.getBrandId());
		byBrand.setName(brand.getName());
		byBrand.setDescription(brand.getDescription());
		byBrand.setProperties(brand.getProperties());
		
		BigDecimal cumMrPrice = DataUtil.ZERO_BIG_DECIMAL;
		int cumNoStock = DataUtil.ZERO_INTEGER;
		
		if(types != null && !types.isEmpty()) {
			for(ByType type : types) {
				cumMrPrice = BDUtil.add(cumMrPrice, type.getCumMrPrice());
				cumNoStock = cumNoStock + type.getCumNoStock();
				byBrand.putType(type.getStockTypeId(), type);
			}
		}
		
		byBrand.setCumMrPrice(cumMrPrice);
		byBrand.setCumSelectedMrPrice(DataUtil.ZERO_BIG_DECIMAL);
		byBrand.setCumNoStock(cumNoStock);
		byBrand.setCumSelectedNoStock(DataUtil.ZERO_INTEGER);
		
		return byBrand;
	}
	
	public static MrStock buildMrStock(MrStock mrStock, List<ByBrand> brands) {
		
		BigDecimal cumMrPrice = DataUtil.ZERO_BIG_DECIMAL;
		int cumNoStock = DataUtil.ZERO_INTEGER;
		
		if(brands != null && !brands.isEmpty()) {
			for(ByBrand brand : brands) {
				cumMrPrice = BDUtil.add(cumMrPrice, brand.getCumMrPrice());
				cumNoStock = cumNoStock + brand.getCumNoStock();
				mrStock.putBrand(brand.getBrandId(), brand);
			}
		}
		
		mrStock.setCumMrPrice(cumMrPrice);
		mrStock.setCumSelectedMrPrice(DataUtil.ZERO_BIG_DECIMAL);
		mrStock.setCumNoStock(cumNoStock);
		mrStock.setCumSelectedNoStock(DataUtil.ZERO_INTEGER);
		
		return mrStock;
	}
}
