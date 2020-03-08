package com.beone.udaan.mr.persistence.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.EnumValue;
import com.beone.shg.net.webservice.rest.model.resp.GenericEnum;
import com.beone.udaan.mr.service.model.ItemConditionEnum;
import com.beone.udaan.mr.service.model.ItemConditionEnum.ItemConditionValue;
import com.beone.udaan.mr.service.model.MrTypeEnum;
import com.beone.udaan.mr.service.model.MrTypeEnum.MrTypeValue;
import com.beone.udaan.mr.service.model.ProductCategoryEnum;
import com.beone.udaan.mr.service.model.ProductCategoryEnum.ProductCategoryValue;
import com.beone.udaan.mr.service.model.ProductCategoryTree;
import com.beone.udaan.mr.service.model.ProductPricingEnum;
import com.beone.udaan.mr.service.model.ProductPricingEnum.ProductPricingValue;
import com.beone.udaan.mr.service.model.StatusEnum;
import com.beone.udaan.mr.service.model.StatusEnum.StatusValue;
import com.beone.udaan.mr.service.model.StockTxTypeEnum;
import com.beone.udaan.mr.service.model.StockTxTypeEnum.StockTxTypeValue;
import com.beone.udaan.mr.service.model.VisitTypeEnum;
import com.beone.udaan.mr.service.model.VisitTypeEnum.VisitTypeValue;

public class EnumCacheMr {
	private final static Logger LOGGER = LoggerFactory.getLogger(EnumCacheMr.class);

	private static Map<String, GenericEnum> enumCache;
	private static Map<String, Map<String, EnumValue>> enumByNameCache;
	private static Map<String, Map<Integer, EnumValue>> enumByIdCache;

	private static Map<String, StatusEnum> statusCache;
	private static Map<String, Map<String, StatusValue>> statusByNameCache;
	private static Map<String, Map<Integer, StatusValue>> statusByIdCache;

	private static ItemConditionEnum itemConditionCache;
	private static Map<String, ItemConditionValue> itemConditionByNameCache;
	private static Map<Integer, ItemConditionValue> itemConditionByIdCache;

	private static ProductCategoryEnum productCategoryCache;
	private static Map<String, ProductCategoryValue> productCategoryByNameCache;
	private static Map<Integer, ProductCategoryValue> productCategoryByIdCache;
	private static ProductCategoryTree productCategoryTree;

	private static ProductPricingEnum productPricingCache;
	private static Map<String, ProductPricingValue> productPricingByNameCache;
	private static Map<Integer, ProductPricingValue> productPricingByIdCache;

	private static StockTxTypeEnum stockTxTypeCache;
	private static StockTxTypeEnum stockTxTypeShareCache;
	private static Map<String, StockTxTypeValue> stockTxTypeByNameCache;
	private static Map<Integer, StockTxTypeValue> stockTxTypeByIdCache;
	private static Map<Integer, StockTxTypeFormula> stockTxTypeFormulaByIdCache;

	private static MrTypeEnum mrTypeCache;
	private static Map<String, MrTypeValue> mrTypeByNameCache;
	private static Map<Integer, MrTypeValue> mrTypeByIdCache;

	private static VisitTypeEnum visitTypeCache;
	private static Map<String, VisitTypeValue> visitTypeByNameCache;
	private static Map<Integer, VisitTypeValue> visitTypeByIdCache;

	static {
		LOGGER.debug("Create Generic Enum Cache");

		enumCache = new ConcurrentHashMap<String, GenericEnum>();
		enumByNameCache = new ConcurrentHashMap<String, Map<String, EnumValue>>();
		enumByIdCache = new ConcurrentHashMap<String, Map<Integer, EnumValue>>();

		statusCache = new ConcurrentHashMap<String, StatusEnum>();
		statusByNameCache = new ConcurrentHashMap<String, Map<String, StatusValue>>();
		statusByIdCache = new ConcurrentHashMap<String, Map<Integer, StatusValue>>();

		itemConditionByNameCache = new ConcurrentHashMap<String, ItemConditionValue>();
		itemConditionByIdCache = new ConcurrentHashMap<Integer, ItemConditionValue>();

		productCategoryByNameCache = new ConcurrentHashMap<String, ProductCategoryValue>();
		productCategoryByIdCache = new ConcurrentHashMap<Integer, ProductCategoryValue>();
		productCategoryTree = new ProductCategoryTree();

		productPricingByNameCache = new ConcurrentHashMap<String, ProductPricingValue>();
		productPricingByIdCache = new ConcurrentHashMap<Integer, ProductPricingValue>();

		stockTxTypeByNameCache = new ConcurrentHashMap<String, StockTxTypeValue>();
		stockTxTypeByIdCache = new ConcurrentHashMap<Integer, StockTxTypeValue>();
		stockTxTypeFormulaByIdCache = new ConcurrentHashMap<Integer, StockTxTypeFormula>();

		mrTypeByNameCache = new ConcurrentHashMap<String, MrTypeValue>();
		mrTypeByIdCache = new ConcurrentHashMap<Integer, MrTypeValue>();

		visitTypeByNameCache = new ConcurrentHashMap<String, VisitTypeValue>();
		visitTypeByIdCache = new ConcurrentHashMap<Integer, VisitTypeValue>();
	}

	// ************************ GenericEnum *****************************
	public static void addEnumValues(String enumName, GenericEnum gEnum) {
		synchronized(EnumCacheMr.class) {
			enumCache.put(enumName, gEnum);

			Map<String, EnumValue> byName = new ConcurrentHashMap<String, EnumValue>();
			Map<Integer, EnumValue> byId = new ConcurrentHashMap<Integer, EnumValue>();

			for(EnumValue value : gEnum.getEnumValues()) {
				byName.put(value.getEnumValue(), value);
				byId.put(value.getEnumIndex(), value);
			}

			enumByNameCache.put(enumName, byName);
			enumByIdCache.put(enumName, byId);
		}
	}

	public static GenericEnum getEnumValues(String enumName) {
		return enumCache.get(enumName);
	}

	public static int getIndexOfEnumValue(String enumName, String enumValue) {
		int index = -1;  	
		if(enumByNameCache.containsKey(enumName) && enumByNameCache.get(enumName).containsKey(enumValue)) {
			return enumByNameCache.get(enumName).get(enumValue).getEnumIndex();
		} 	
		return index;
	}

	public static EnumValue getEnumValue(String enumName, String enumValue) {
		if(enumByNameCache.containsKey(enumName) && enumByNameCache.get(enumName).containsKey(enumValue)) {
			return enumByNameCache.get(enumName).get(enumValue);
		} 	
		return null;
	}

	public static String getNameOfEnumValue(String enumName, int enumIndex) {
		if(enumByIdCache.containsKey(enumName) && enumByIdCache.get(enumName).containsKey(enumIndex)) {
			return enumByIdCache.get(enumName).get(enumIndex).getEnumValue();
		} 	
		return DataUtil.EMPTY_STRING;
	}

	public static EnumValue getEnumValue(String enumName, int enumIndex) {
		if(enumByIdCache.containsKey(enumName) && enumByIdCache.get(enumName).containsKey(enumIndex)) {
			return enumByIdCache.get(enumName).get(enumIndex);
		} 	
		return null;
	}

	// ************************ StatusEnum *****************************
	public static void addStatusValues(String statusName, StatusEnum gEnum) {
		synchronized(EnumCacheMr.class) {
			statusCache.put(statusName, gEnum);

			Map<String, StatusValue> byName = new ConcurrentHashMap<String, StatusValue>();
			Map<Integer, StatusValue> byId = new ConcurrentHashMap<Integer, StatusValue>();

			for(StatusValue value : gEnum.getStatusValues()) {
				byName.put(value.getStatusValue(), value);
				byId.put(value.getStatusIndex(), value);
			}

			statusByNameCache.put(statusName, byName);
			statusByIdCache.put(statusName, byId);
		}
	}

	public static StatusEnum getStatusValues(String statusName) {
		return statusCache.get(statusName);
	}

	public static int getIndexOfStatusValue(String statusName, String statusValue) {
		int index = -1;  	
		if(statusByNameCache.containsKey(statusName) && statusByNameCache.get(statusName).containsKey(statusValue)) {
			return statusByNameCache.get(statusName).get(statusValue).getStatusIndex();
		} 	
		return index;
	}

	public static StatusValue getStatusValue(String statusName, String statusValue) {
		if(statusByNameCache.containsKey(statusName) && statusByNameCache.get(statusName).containsKey(statusValue)) {
			return statusByNameCache.get(statusName).get(statusValue);
		} 	
		return null;
	}

	public static String getNameOfStatusValue(String statusName, int statusIndex) {
		if(statusByIdCache.containsKey(statusName) && statusByIdCache.get(statusName).containsKey(statusIndex)) {
			return statusByIdCache.get(statusName).get(statusIndex).getStatusValue();
		} 	
		return DataUtil.EMPTY_STRING;
	}

	public static StatusValue getStatusValue(String statusName, int statusIndex) {
		if(statusByIdCache.containsKey(statusName) && statusByIdCache.get(statusName).containsKey(statusIndex)) {
			return statusByIdCache.get(statusName).get(statusIndex);
		} 	
		return null;
	}

	// ************************ ItemConditionEnum *****************************
	public static ItemConditionEnum getItemCondition() {
		return itemConditionCache;
	}

	public static void addItemCondition(ItemConditionEnum itemCondition) {
		synchronized(EnumCache.class) {
			itemConditionCache = itemCondition;
			itemConditionByNameCache.clear();
			itemConditionByIdCache.clear();

			for(ItemConditionValue value: itemCondition.getEnumValues()) {
				itemConditionByNameCache.put(value.getItemCondition(), value);
				itemConditionByIdCache.put(value.getItemConditionId(), value);
			}
		}
	}

	public static int getIndexOfItemCondition(String enumValue) {
		int index = -1;
		if(itemConditionByNameCache.containsKey(enumValue)) {
			return itemConditionByNameCache.get(enumValue).getItemConditionId();
		}
		return index;
	}

	public static String getNameOfItemCondition(int enumIndex) {
		if(itemConditionByIdCache.containsKey(enumIndex)) {
			return itemConditionByIdCache.get(enumIndex).getItemCondition();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static ItemConditionValue getItemConditionValue(String enumValue) {
		if(itemConditionByNameCache.containsKey(enumValue)) {
			return itemConditionByNameCache.get(enumValue);
		}
		return null;
	}

	public static ItemConditionValue getItemConditionValue(int enumIndex) {
		if(itemConditionByIdCache.containsKey(enumIndex)) {
			return itemConditionByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ ProductCategoryEnum *****************************
	public static ProductCategoryEnum getProductCategory() {
		return productCategoryCache;
	}

	public static void addProductCategory(ProductCategoryEnum productCategory) {
		synchronized(EnumCache.class) {
			productCategoryCache = productCategory;
			productCategoryByNameCache.clear();
			productCategoryByIdCache.clear();

			for(ProductCategoryValue value: productCategory.getEnumValues()) {
				productCategoryByNameCache.put(value.getProductCategory(), value);
				productCategoryByIdCache.put(value.getProductCategoryId(), value);
			}
		}
	}

	public static int getIndexOfProductCategory(String enumValue) {
		int index = -1;
		if(productCategoryByNameCache.containsKey(enumValue)) {
			return productCategoryByNameCache.get(enumValue).getProductCategoryId();
		}
		return index;
	}

	public static String getNameOfProductCategory(int enumIndex) {
		if(productCategoryByIdCache.containsKey(enumIndex)) {
			return productCategoryByIdCache.get(enumIndex).getProductCategory();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static ProductCategoryValue getProductCategoryValue(String enumValue) {
		if(productCategoryByNameCache.containsKey(enumValue)) {
			return productCategoryByNameCache.get(enumValue);
		}
		return null;
	}

	public static ProductCategoryValue getProductCategoryValue(int enumIndex) {
		if(productCategoryByIdCache.containsKey(enumIndex)) {
			return productCategoryByIdCache.get(enumIndex);
		}
		return null;
	}

	public static ProductCategoryTree getProductCategoryTree() {
		return productCategoryTree;
	}

	public static void setProductCategoryTree(ProductCategoryTree productCategoryTree) {
		EnumCacheMr.productCategoryTree = productCategoryTree;
	}

	// ************************ ProductPricingEnum *****************************
	public static ProductPricingEnum getProductPricing() {
		return productPricingCache;
	}

	public static void addProductPricing(ProductPricingEnum productPricing) {
		synchronized(EnumCache.class) {
			productPricingCache = productPricing;
			productPricingByNameCache.clear();
			productPricingByIdCache.clear();

			for(ProductPricingValue value: productPricing.getEnumValues()) {
				productPricingByNameCache.put(value.getProductPricing(), value);
				productPricingByIdCache.put(value.getProductPricingId(), value);
			}
		}
	}

	public static int getIndexOfProductPricing(String enumValue) {
		int index = -1;
		if(productPricingByNameCache.containsKey(enumValue)) {
			return productPricingByNameCache.get(enumValue).getProductPricingId();
		}
		return index;
	}

	public static String getNameOfProductPricing(int enumIndex) {
		if(productPricingByIdCache.containsKey(enumIndex)) {
			return productPricingByIdCache.get(enumIndex).getProductPricing();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static ProductPricingValue getProductPricingValue(String enumValue) {
		if(productPricingByNameCache.containsKey(enumValue)) {
			return productPricingByNameCache.get(enumValue);
		}
		return null;
	}

	public static ProductPricingValue getProductPricingValue(int enumIndex) {
		if(productPricingByIdCache.containsKey(enumIndex)) {
			return productPricingByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ StockTxTypeEnum *****************************
	public static StockTxTypeEnum getStockTxType() {
		return stockTxTypeCache;
	}

	public static StockTxTypeEnum getStockTxTypeShare() {
		return stockTxTypeShareCache;
	}

	public static void addStockTxType(StockTxTypeEnum stockTxType) {
		synchronized(EnumCache.class) {
			stockTxTypeCache = stockTxType;
			stockTxTypeByNameCache.clear();
			stockTxTypeByIdCache.clear();
			stockTxTypeFormulaByIdCache.clear();

			stockTxTypeShareCache = new StockTxTypeEnum();
			stockTxTypeShareCache.setEnumName(stockTxTypeCache.getEnumName());

			for(StockTxTypeValue value: stockTxType.getEnumValues()) {
				stockTxTypeShareCache.getEnumValues().add(new StockTxTypeValue(value));

				stockTxTypeByNameCache.put(value.getStockTxType(), value);
				stockTxTypeByIdCache.put(value.getStockTxTypeId(), value);
				stockTxTypeFormulaByIdCache.put(value.getStockTxTypeId(), new StockTxTypeFormula(value));
			}
		}
	}

	public static int getIndexOfStockTxType(String enumValue) {
		int index = -1;
		if(stockTxTypeByNameCache.containsKey(enumValue)) {
			return stockTxTypeByNameCache.get(enumValue).getStockTxTypeId();
		}
		return index;
	}

	public static String getNameOfStockTxType(int enumIndex) {
		if(stockTxTypeByIdCache.containsKey(enumIndex)) {
			return stockTxTypeByIdCache.get(enumIndex).getStockTxType();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static StockTxTypeValue getStockTxTypeValue(String enumValue) {
		if(stockTxTypeByNameCache.containsKey(enumValue)) {
			return stockTxTypeByNameCache.get(enumValue);
		}
		return null;
	}

	public static StockTxTypeValue getStockTxTypeValue(int enumIndex) {
		if(stockTxTypeByIdCache.containsKey(enumIndex)) {
			return stockTxTypeByIdCache.get(enumIndex);
		}
		return null;
	}

	public static StockTxTypeFormula getStockTxTypeFormula(int enumIndex) {
		if(stockTxTypeFormulaByIdCache.containsKey(enumIndex)) {
			return stockTxTypeFormulaByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ MrTypeEnum *****************************
	public static MrTypeEnum getMrType() {
		return mrTypeCache;
	}

	public static void addMrType(MrTypeEnum mrType) {
		synchronized(EnumCache.class) {
			mrTypeCache = mrType;
			mrTypeByNameCache.clear();
			mrTypeByIdCache.clear();

			for(MrTypeValue value: mrType.getEnumValues()) {
				mrTypeByNameCache.put(value.getMrType(), value);
				mrTypeByIdCache.put(value.getMrTypeId(), value);
			}
		}
	}

	public static int getIndexOfMrType(String enumValue) {
		int index = -1;
		if(mrTypeByNameCache.containsKey(enumValue)) {
			return mrTypeByNameCache.get(enumValue).getMrTypeId();
		}
		return index;
	}

	public static String getNameOfMrType(int enumIndex) {
		if(mrTypeByIdCache.containsKey(enumIndex)) {
			return mrTypeByIdCache.get(enumIndex).getMrType();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static MrTypeValue getMrTypeValue(String enumValue) {
		if(mrTypeByNameCache.containsKey(enumValue)) {
			return mrTypeByNameCache.get(enumValue);
		}
		return null;
	}

	public static MrTypeValue getMrTypeValue(int enumIndex) {
		if(mrTypeByIdCache.containsKey(enumIndex)) {
			return mrTypeByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ VisitTypeEnum *****************************
	public static VisitTypeEnum getVisitType() {
		return visitTypeCache;
	}

	public static void addVisitType(VisitTypeEnum visitType) {
		synchronized(EnumCache.class) {
			visitTypeCache = visitType;
			visitTypeByNameCache.clear();
			visitTypeByIdCache.clear();

			for(VisitTypeValue value: visitType.getEnumValues()) {
				visitTypeByNameCache.put(value.getVisitType(), value);
				visitTypeByIdCache.put(value.getVisitTypeId(), value);
			}
		}
	}

	public static int getIndexOfVisitType(String enumValue) {
		int index = -1;
		if(visitTypeByNameCache.containsKey(enumValue)) {
			return visitTypeByNameCache.get(enumValue).getVisitTypeId();
		}
		return index;
	}

	public static String getNameOfVisitType(int enumIndex) {
		if(visitTypeByIdCache.containsKey(enumIndex)) {
			return visitTypeByIdCache.get(enumIndex).getVisitType();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static VisitTypeValue getVisitTypeValue(String enumValue) {
		if(visitTypeByNameCache.containsKey(enumValue)) {
			return visitTypeByNameCache.get(enumValue);
		}
		return null;
	}

	public static VisitTypeValue getVisitTypeValue(int enumIndex) {
		if(visitTypeByIdCache.containsKey(enumIndex)) {
			return visitTypeByIdCache.get(enumIndex);
		}
		return null;
	}
}
