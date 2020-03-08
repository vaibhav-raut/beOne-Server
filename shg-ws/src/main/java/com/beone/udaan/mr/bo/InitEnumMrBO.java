package com.beone.udaan.mr.bo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.beone.shg.net.persistence.bo.BaseBO;
import com.beone.shg.net.webservice.rest.model.resp.EnumValue;
import com.beone.shg.net.webservice.rest.model.resp.GenericEnum;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.InvoiceStatus;
import com.beone.udaan.mr.persistence.model.ItemCondition;
import com.beone.udaan.mr.persistence.model.ItemStatus;
import com.beone.udaan.mr.persistence.model.LotStatus;
import com.beone.udaan.mr.persistence.model.MrStatus;
import com.beone.udaan.mr.persistence.model.MrType;
import com.beone.udaan.mr.persistence.model.PCategory;
import com.beone.udaan.mr.persistence.model.PaymentType;
import com.beone.udaan.mr.persistence.model.PkgStatus;
import com.beone.udaan.mr.persistence.model.PkgType;
import com.beone.udaan.mr.persistence.model.ProductPricing;
import com.beone.udaan.mr.persistence.model.ShipmentStatus;
import com.beone.udaan.mr.persistence.model.StockTxType;
import com.beone.udaan.mr.persistence.model.StockTypeStatus;
import com.beone.udaan.mr.persistence.model.TagStatus;
import com.beone.udaan.mr.persistence.model.VisitStatus;
import com.beone.udaan.mr.persistence.model.VisitType;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;
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

@Component("initEnumMrBO")
public class InitEnumMrBO extends BaseBO {

	public void loadAllEnumValues() {

		// Load All Enums to Cache
		loadPaymentType();
		loadPkgType();

		loadInvoiceStatus();
		loadItemStatus();
		loadLotStatus();
		loadMrStatus();
		loadPkgStatus();
		loadShipmentStatus();
		loadStockTypeStatus();
		loadTagStatus();
		loadVisitStatus();
		
		loadItemCondition();
		loadProductCategory();
		loadProductPricing();
		loadStockTxType();
		loadMrType();
		loadVisitType();
	}

	public void addEnumValues(GenericEnum addEnum) throws BadRequestException {

		if(addEnum.getEnumName() == null || addEnum.getEnumName().isEmpty()) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}

		GenericEnum oEnum = EnumCacheMr.getEnumValues(addEnum.getEnumName());
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(EnumValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getEnumValue());
			}
		}

		if(addEnum.getEnumName().equals(EnumConstMr.PaymentType)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					PaymentType type = new PaymentType(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getPaymentTypeDAO().persist(type);
				}
			}

			// Reload enum PaymentType after updates
			loadPaymentType();

		} else if(addEnum.getEnumName().equals(EnumConstMr.PkgType)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					PkgType type = new PkgType(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getPkgTypeDAO().persist(type);
				}
			}

			// Reload enum PkgType after updates
			loadPkgType();

		} else {	
			throw new BadRequestException("Invalid Enum Name");
		}
	}

	public void updateEnumValues(GenericEnum updateEnum) throws BadRequestException {

		if(updateEnum.getEnumName() == null || updateEnum.getEnumName().isEmpty()) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}

		if(updateEnum.getEnumName().equals(EnumConstMr.PaymentType)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					PaymentType enumType = new PaymentType(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getPaymentTypeDAO().merge(enumType);
				}
			}

			// Reload enum PaymentType after updates
			loadPaymentType();

		} else if(updateEnum.getEnumName().equals(EnumConstMr.PkgType)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					PkgType enumType = new PkgType(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getPkgTypeDAO().merge(enumType);
				}
			}

			// Reload enum PkgType after updates
			loadPkgType();

		} else {	
			throw new BadRequestException("Invalid Enum Name");
		}   	
	}

	public void addStatusValues(StatusEnum addEnum) throws BadRequestException {

		if(addEnum.getStatusName() == null || addEnum.getStatusName().isEmpty()) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}

		StatusEnum oEnum = EnumCacheMr.getStatusValues(addEnum.getStatusName());
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getStatusValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getStatusValues().size());
			for(StatusValue eVal : oEnum.getStatusValues()) {
				oldEnums.add(eVal.getStatusValue());
			}
		}

		if(addEnum.getStatusName().equals(EnumConstMr.InvoiceStatus)) {
			for(StatusValue eVal : addEnum.getStatusValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getStatusValue())) {
					InvoiceStatus status = new InvoiceStatus(eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getInvoiceStatusDAO().persist(status);
				}
			}

			// Reload enum InvoiceStatus after updates
			loadInvoiceStatus();

		} else if(addEnum.getStatusName().equals(EnumConstMr.ItemStatus)) {
			for(StatusValue eVal : addEnum.getStatusValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getStatusValue())) {
					ItemStatus status = new ItemStatus(eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getItemStatusDAO().persist(status);
				}
			}

			// Reload enum ItemStatus after updates
			loadItemStatus();

		} else if(addEnum.getStatusName().equals(EnumConstMr.LotStatus)) {
			for(StatusValue eVal : addEnum.getStatusValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getStatusValue())) {
					LotStatus status = new LotStatus(eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getLotStatusDAO().persist(status);
				}
			}

			// Reload enum MrStatus after updates
			loadLotStatus();

		} else if(addEnum.getStatusName().equals(EnumConstMr.MrStatus)) {
			for(StatusValue eVal : addEnum.getStatusValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getStatusValue())) {
					MrStatus status = new MrStatus(eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getMrStatusDAO().persist(status);
				}
			}

			// Reload enum MrStatus after updates
			loadMrStatus();

		} else if(addEnum.getStatusName().equals(EnumConstMr.PkgStatus)) {
			for(StatusValue eVal : addEnum.getStatusValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getStatusValue())) {
					PkgStatus status = new PkgStatus(eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getPkgStatusDAO().persist(status);
				}
			}

			// Reload enum PkgStatus after updates
			loadPkgStatus();

		} else if(addEnum.getStatusName().equals(EnumConstMr.ShipmentStatus)) {
			for(StatusValue eVal : addEnum.getStatusValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getStatusValue())) {
					ShipmentStatus status = new ShipmentStatus(eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getShipmentStatusDAO().persist(status);
				}
			}

			// Reload enum ShipmentStatus after updates
			loadShipmentStatus();

		} else if(addEnum.getStatusName().equals(EnumConstMr.StockTypeStatus)) {
			for(StatusValue eVal : addEnum.getStatusValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getStatusValue())) {
					StockTypeStatus status = new StockTypeStatus(eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getStockTypeStatusDAO().persist(status);
				}
			}

			// Reload enum StockTypeStatus after updates
			loadStockTypeStatus();

		} else if(addEnum.getStatusName().equals(EnumConstMr.TagStatus)) {
			for(StatusValue eVal : addEnum.getStatusValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getStatusValue())) {
					TagStatus status = new TagStatus(eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getTagStatusDAO().persist(status);
				}
			}

			// Reload enum TagStatus after updates
			loadTagStatus();

		} else if(addEnum.getStatusName().equals(EnumConstMr.VisitStatus)) {
			for(StatusValue eVal : addEnum.getStatusValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getStatusValue())) {
					VisitStatus status = new VisitStatus(eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getVisitStatusDAO().persist(status);
				}
			}

			// Reload enum VisitStatus after updates
			loadVisitStatus();

		} else {	
			throw new BadRequestException("Invalid Enum Name");
		}
	}

	public void updateStatusValues(StatusEnum updateEnum) throws BadRequestException {

		if(updateEnum.getStatusName() == null || updateEnum.getStatusName().isEmpty()) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}

		if(updateEnum.getStatusName().equals(EnumConstMr.InvoiceStatus)) {
			for(StatusValue eVal : updateEnum.getStatusValues()) {
				if(eVal.getStatusIndex() > 0) {
					InvoiceStatus enumType = new InvoiceStatus(eVal.getStatusIndex(), eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getInvoiceStatusDAO().merge(enumType);
				}
			}

			// Reload enum InvoiceStatus after updates
			loadInvoiceStatus();

		} else if(updateEnum.getStatusName().equals(EnumConstMr.ItemStatus)) {
			for(StatusValue eVal : updateEnum.getStatusValues()) {
				if(eVal.getStatusIndex() > 0) {
					ItemStatus enumType = new ItemStatus(eVal.getStatusIndex(), eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getItemStatusDAO().merge(enumType);
				}
			}

			// Reload enum ItemStatus after updates
			loadItemStatus();

		} else if(updateEnum.getStatusName().equals(EnumConstMr.LotStatus)) {
			for(StatusValue eVal : updateEnum.getStatusValues()) {
				if(eVal.getStatusIndex() > 0) {
					LotStatus enumType = new LotStatus(eVal.getStatusIndex(), eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getLotStatusDAO().merge(enumType);
				}
			}

			// Reload enum LotStatus after updates
			loadLotStatus();

		} else if(updateEnum.getStatusName().equals(EnumConstMr.MrStatus)) {
			for(StatusValue eVal : updateEnum.getStatusValues()) {
				if(eVal.getStatusIndex() > 0) {
					MrStatus enumType = new MrStatus(eVal.getStatusIndex(), eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getMrStatusDAO().merge(enumType);
				}
			}

			// Reload enum MrStatus after updates
			loadMrStatus();

		} else if(updateEnum.getStatusName().equals(EnumConstMr.PkgStatus)) {
			for(StatusValue eVal : updateEnum.getStatusValues()) {
				if(eVal.getStatusIndex() > 0) {
					PkgStatus enumType = new PkgStatus(eVal.getStatusIndex(), eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getPkgStatusDAO().merge(enumType);
				}
			}

			// Reload enum PkgStatus after updates
			loadPkgStatus();

		} else if(updateEnum.getStatusName().equals(EnumConstMr.ShipmentStatus)) {
			for(StatusValue eVal : updateEnum.getStatusValues()) {
				if(eVal.getStatusIndex() > 0) {
					ShipmentStatus enumType = new ShipmentStatus(eVal.getStatusIndex(), eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getShipmentStatusDAO().merge(enumType);
				}
			}

			// Reload enum ShipmentStatus after updates
			loadShipmentStatus();

		} else if(updateEnum.getStatusName().equals(EnumConstMr.StockTypeStatus)) {
			for(StatusValue eVal : updateEnum.getStatusValues()) {
				if(eVal.getStatusIndex() > 0) {
					StockTypeStatus enumType = new StockTypeStatus(eVal.getStatusIndex(), eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getStockTypeStatusDAO().merge(enumType);
				}
			}

			// Reload enum StockTypeStatus after updates
			loadStockTypeStatus();

		} else if(updateEnum.getStatusName().equals(EnumConstMr.TagStatus)) {
			for(StatusValue eVal : updateEnum.getStatusValues()) {
				if(eVal.getStatusIndex() > 0) {
					TagStatus enumType = new TagStatus(eVal.getStatusIndex(), eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getTagStatusDAO().merge(enumType);
				}
			}

			// Reload enum TagStatus after updates
			loadTagStatus();

		} else if(updateEnum.getStatusName().equals(EnumConstMr.VisitStatus)) {
			for(StatusValue eVal : updateEnum.getStatusValues()) {
				if(eVal.getStatusIndex() > 0) {
					VisitStatus enumType = new VisitStatus(eVal.getStatusIndex(), eVal.getStatusValue(), eVal.getDescription(), eVal.getNextStatus());
					daoFactory.getVisitStatusDAO().merge(enumType);
				}
			}

			// Reload enum VisitStatus after updates
			loadVisitStatus();

		} else {	
			throw new BadRequestException("Invalid Enum Name");
		}   	
	}

	// Load PaymentType type Enum as Generic Enum to Cache
	public void loadPaymentType() {
		String enumName = EnumConstMr.PaymentType;
		GenericEnum enu =  new GenericEnum();
		List<PaymentType> rows = daoFactory.getPaymentTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(PaymentType row: rows) {
			EnumValue eVal = new EnumValue(row.getPaymentTypeId(), row.getPaymentType(), row.getPaymentTypeDesc());
			enu.addEnumValue(eVal);
		}
		EnumCacheMr.addEnumValues(enumName, enu);
	}

	// Load PkgType type Enum as Generic Enum to Cache
	public void loadPkgType() {
		String enumName = EnumConstMr.PkgType;
		GenericEnum enu =  new GenericEnum();
		List<PkgType> rows = daoFactory.getPkgTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(PkgType row: rows) {
			EnumValue eVal = new EnumValue(row.getPkgTypeId(), row.getPkgType(), row.getPkgTypeDesc());
			enu.addEnumValue(eVal);
		}
		EnumCacheMr.addEnumValues(enumName, enu);
	}

	// Load InvoiceStatus type Status as StatusEnum to Cache
	public void loadInvoiceStatus() {
		String statusName = EnumConstMr.InvoiceStatus;
		StatusEnum enu =  new StatusEnum();
		List<InvoiceStatus> rows = daoFactory.getInvoiceStatusDAO().getAllRowList(100);

		enu.setStatusName(statusName);
		for(InvoiceStatus row: rows) {
			StatusValue eVal = new StatusValue(row.getInvoiceStatusId(), row.getInvoiceStatus(), row.getInvoiceStatusDesc(), row.getNextStatus());
			enu.addStatusValue(eVal);
		}
		EnumCacheMr.addStatusValues(statusName, enu);
	}

	// Load ItemStatus type Status as StatusEnum to Cache
	public void loadItemStatus() {
		String statusName = EnumConstMr.ItemStatus;
		StatusEnum enu =  new StatusEnum();
		List<ItemStatus> rows = daoFactory.getItemStatusDAO().getAllRowList(100);

		enu.setStatusName(statusName);
		for(ItemStatus row: rows) {
			StatusValue eVal = new StatusValue(row.getItemStatusId(), row.getItemStatus(), row.getItemStatusDesc(), row.getNextStatus());
			enu.addStatusValue(eVal);
		}
		EnumCacheMr.addStatusValues(statusName, enu);
	}

	// Load LotStatus type Status as StatusEnum to Cache
	public void loadLotStatus() {
		String statusName = EnumConstMr.LotStatus;
		StatusEnum enu =  new StatusEnum();
		List<LotStatus> rows = daoFactory.getLotStatusDAO().getAllRowList(100);

		enu.setStatusName(statusName);
		for(LotStatus row: rows) {
			StatusValue eVal = new StatusValue(row.getLotStatusId(), row.getLotStatus(), row.getLotStatusDesc(), row.getNextStatus());
			enu.addStatusValue(eVal);
		}
		EnumCacheMr.addStatusValues(statusName, enu);
	}

	// Load MrStatus type Status as StatusEnum to Cache
	public void loadMrStatus() {
		String statusName = EnumConstMr.MrStatus;
		StatusEnum enu =  new StatusEnum();
		List<MrStatus> rows = daoFactory.getMrStatusDAO().getAllRowList(100);

		enu.setStatusName(statusName);
		for(MrStatus row: rows) {
			StatusValue eVal = new StatusValue(row.getMrStatusId(), row.getMrStatus(), row.getMrStatusDesc(), row.getNextStatus());
			enu.addStatusValue(eVal);
		}
		EnumCacheMr.addStatusValues(statusName, enu);
	}

	// Load PkgStatus type Enum as StatusEnum to Cache
	public void loadPkgStatus() {
		String statusName = EnumConstMr.PkgStatus;
		StatusEnum enu =  new StatusEnum();
		List<PkgStatus> rows = daoFactory.getPkgStatusDAO().getAllRowList(100);

		enu.setStatusName(statusName);
		for(PkgStatus row: rows) {
			StatusValue eVal = new StatusValue(row.getPkgStatusId(), row.getPkgStatus(), row.getPkgStatusDesc(), row.getNextStatus());
			enu.addStatusValue(eVal);
		}
		EnumCacheMr.addStatusValues(statusName, enu);
	}

	// Load ShipmentStatus type Status as StatusEnum to Cache
	public void loadShipmentStatus() {
		String statusName = EnumConstMr.ShipmentStatus;
		StatusEnum enu =  new StatusEnum();
		List<ShipmentStatus> rows = daoFactory.getShipmentStatusDAO().getAllRowList(100);

		enu.setStatusName(statusName);
		for(ShipmentStatus row: rows) {
			StatusValue eVal = new StatusValue(row.getShipmentStatusId(), row.getShipmentStatus(), row.getShipmentStatusDesc(), row.getNextStatus());
			enu.addStatusValue(eVal);
		}
		EnumCacheMr.addStatusValues(statusName, enu);
	}

	// Load StockTypeStatus type Status as StatusEnum to Cache
	public void loadStockTypeStatus() {
		String statusName = EnumConstMr.StockTypeStatus;
		StatusEnum enu =  new StatusEnum();
		List<StockTypeStatus> rows = daoFactory.getStockTypeStatusDAO().getAllRowList(100);

		enu.setStatusName(statusName);
		for(StockTypeStatus row: rows) {
			StatusValue eVal = new StatusValue(row.getStockTypeStatusId(), row.getStockTypeStatus(), row.getStockTypeStatusDesc(), row.getNextStatus());
			enu.addStatusValue(eVal);
		}
		EnumCacheMr.addStatusValues(statusName, enu);
	}

	// Load TagStatus type Status as StatusEnum to Cache
	public void loadTagStatus() {
		String statusName = EnumConstMr.TagStatus;
		StatusEnum enu =  new StatusEnum();
		List<TagStatus> rows = daoFactory.getTagStatusDAO().getAllRowList(100);

		enu.setStatusName(statusName);
		for(TagStatus row: rows) {
			StatusValue eVal = new StatusValue(row.getTagStatusId(), row.getTagStatus(), row.getTagStatusDesc(), row.getNextStatus());
			enu.addStatusValue(eVal);
		}
		EnumCacheMr.addStatusValues(statusName, enu);
	}

	// Load VisitStatus type Status as StatusEnum to Cache
	public void loadVisitStatus() {
		String statusName = EnumConstMr.VisitStatus;
		StatusEnum enu =  new StatusEnum();
		List<VisitStatus> rows = daoFactory.getVisitStatusDAO().getAllRowList(100);

		enu.setStatusName(statusName);
		for(VisitStatus row: rows) {
			StatusValue eVal = new StatusValue(row.getVisitStatusId(), row.getVisitStatus(), row.getVisitStatusDesc(), row.getNextStatus());
			enu.addStatusValue(eVal);
		}
		EnumCacheMr.addStatusValues(statusName, enu);
	}

	//*************** ItemCondition ********************
	public void addItemCondition(ItemConditionEnum enu) {

		ItemConditionEnum oEnum = EnumCacheMr.getItemCondition();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(ItemConditionValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getItemCondition());
			}
		}

		for(ItemConditionValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getItemCondition())) {
				ItemCondition itemCondition = new ItemCondition(eVal.getItemCondition(),
						eVal.getDescription(),
						eVal.getSpDiscountPer(),
						eVal.getMrpDiscountPer());

				daoFactory.getItemConditionDAO().persist(itemCondition);
			}
		}

		// Reload enum ItemCondition after updates
		loadItemCondition();
	}

	public void updateItemCondition(ItemConditionEnum enu) {

		for(ItemConditionValue eVal : enu.getEnumValues()) {
			ItemCondition itemCondition = daoFactory.getItemConditionDAO().findById(eVal.getItemConditionId());
			
			itemCondition.setItemCondition(eVal.getItemCondition());
			itemCondition.setDescription(eVal.getDescription());
			itemCondition.setSpDiscountPer(eVal.getSpDiscountPer());
			itemCondition.setMrpDiscountPer(eVal.getMrpDiscountPer());

			daoFactory.getItemConditionDAO().merge(itemCondition);
		}

		// Reload enum ItemCondition after updates
		loadItemCondition();
	}

	// Load ItemCondition type Enum as Generic Enum to Cache
	public void loadItemCondition() {
		String enumName = EnumConstMr.ItemCondition;
		ItemConditionEnum enu =  new ItemConditionEnum();
		List<ItemCondition> rows = daoFactory.getItemConditionDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(ItemCondition row: rows) {
			enu.addEnumValue(new ItemConditionValue(row.getItemConditionId(), 
					row.getItemCondition(), 
					row.getDescription(),
					row.getSpDiscountPer(),
					row.getMrpDiscountPer()));
		}
		EnumCacheMr.addItemCondition(enu);
	}

	//*************** ProductCategory ********************
	public void addProductCategory(ProductCategoryEnum enu) {

		ProductCategoryEnum oEnum = EnumCacheMr.getProductCategory();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(ProductCategoryValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getProductCategory());
			}
		}

		for(ProductCategoryValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getProductCategory())) {
				PCategory pCategory = new PCategory(eVal.getProductCategory(),
						eVal.getSubCategoryLevel1(),
						eVal.getSubCategoryLevel2(),
						eVal.getSubCategoryLevel3(),
						eVal.getSubCategoryLevel4());

				daoFactory.getPCategoryDAO().persist(pCategory);
			}
		}

		// Reload enum ProductCategory after updates
		loadProductCategory();
	}

	public void updateProductCategory(ProductCategoryEnum enu) {

		for(ProductCategoryValue eVal : enu.getEnumValues()) {
			PCategory pCategory = daoFactory.getPCategoryDAO().findById(eVal.getProductCategoryId());
			pCategory.setPCategory(eVal.getProductCategory());
			pCategory.setSubCategoryLevel1(eVal.getSubCategoryLevel1());
			pCategory.setSubCategoryLevel2(eVal.getSubCategoryLevel2());
			pCategory.setSubCategoryLevel3(eVal.getSubCategoryLevel3());
			pCategory.setSubCategoryLevel4(eVal.getSubCategoryLevel4());

			daoFactory.getPCategoryDAO().merge(pCategory);
		}

		// Reload enum ProductCategory after updates
		loadProductCategory();
	}

	// Load ProductCategory type Enum as Generic Enum to Cache
	public void loadProductCategory() {
		String enumName = EnumConstMr.ProductCategory;
		ProductCategoryEnum enu =  new ProductCategoryEnum();
		List<PCategory> rows = daoFactory.getPCategoryDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(PCategory row: rows) {
			enu.addEnumValue(new ProductCategoryValue(row.getPCategoryId(), 
					row.getPCategory(), 
					row.getSubCategoryLevel1(),
					row.getSubCategoryLevel2(),
					row.getSubCategoryLevel3(),
					row.getSubCategoryLevel4()));
		}
		EnumCacheMr.addProductCategory(enu);
		
		EnumCacheMr.setProductCategoryTree(ProductCategoryTree.buildTree(rows));
	}

	//*************** ProductPricing ********************
	public void addProductPricing(ProductPricingEnum enu) {

		ProductPricingEnum oEnum = EnumCacheMr.getProductPricing();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(ProductPricingValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getProductPricing());
			}
		}

		for(ProductPricingValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getProductPricing())) {
				ProductPricing productPricing = new ProductPricing(eVal.getProductPricing(),
						eVal.getWpPercent(),
						eVal.getMrPercent(),
						eVal.getVatPercent(),
						eVal.getOtherTaxesPercent());

				daoFactory.getProductPricingDAO().persist(productPricing);
			}
		}

		// Reload enum ProductPricing after updates
		loadProductPricing();
	}

	public void updateProductPricing(ProductPricingEnum enu) {

		for(ProductPricingValue eVal : enu.getEnumValues()) {
			ProductPricing productPricing = daoFactory.getProductPricingDAO().findById(eVal.getProductPricingId());
			productPricing.setProductPricing(eVal.getProductPricing());
			productPricing.setWpPercent(eVal.getWpPercent());
			productPricing.setMrPercent(eVal.getMrPercent());
			productPricing.setVatPercent(eVal.getVatPercent());
			productPricing.setOtherTaxesPercent(eVal.getOtherTaxesPercent());

			daoFactory.getProductPricingDAO().merge(productPricing);
		}

		// Reload enum ProductPricing after updates
		loadProductPricing();
	}

	// Load ProductPricing type Enum as Generic Enum to Cache
	public void loadProductPricing() {
		String enumName = EnumConstMr.ProductPricing;
		ProductPricingEnum enu =  new ProductPricingEnum();
		List<ProductPricing> rows = daoFactory.getProductPricingDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(ProductPricing row: rows) {
			enu.addEnumValue(new ProductPricingValue(row.getProductPricingId(), 
					row.getProductPricing(), 
					row.getWpPercent(),
					row.getMrPercent(),
					row.getVatPercent(),
					row.getOtherTaxesPercent()));
		}
		EnumCacheMr.addProductPricing(enu);
	}

	//*************** StockTxType ********************
	public void addStockTxType(StockTxTypeEnum enu) {

		StockTxTypeEnum oEnum = EnumCacheMr.getStockTxType();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(StockTxTypeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getStockTxType());
			}
		}

		for(StockTxTypeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getStockTxType())) {
				StockTxType stockTxType = new StockTxType(eVal.getStockTxType(),
						eVal.getTxFor(),
						eVal.getStockTxTypeDesc(),
						eVal.getPreP1ActionFormula(),
						eVal.getPreP2ActionFormula(),
						eVal.getPreP3ActionFormula(),
						eVal.getPostP1ActionFormula(),
						eVal.getPostP2ActionFormula(),
						eVal.getPostP3ActionFormula());

				daoFactory.getStockTxTypeDAO().persist(stockTxType);
			}
		}

		// Reload enum StockTxType after updates
		loadStockTxType();
	}

	public void updateStockTxType(StockTxTypeEnum enu) {

		for(StockTxTypeValue eVal : enu.getEnumValues()) {
			StockTxType stockTxType = daoFactory.getStockTxTypeDAO().findById(eVal.getStockTxTypeId());
			stockTxType.setStockTxType(eVal.getStockTxType());
			stockTxType.setTxFor(eVal.getTxFor());
			stockTxType.setStockTxTypeDesc(eVal.getStockTxTypeDesc());
			stockTxType.setPreP1ActionFormula(eVal.getPreP1ActionFormula());
			stockTxType.setPreP2ActionFormula(eVal.getPreP2ActionFormula());
			stockTxType.setPreP3ActionFormula(eVal.getPreP3ActionFormula());
			stockTxType.setPostP1ActionFormula(eVal.getPostP1ActionFormula());
			stockTxType.setPostP2ActionFormula(eVal.getPostP2ActionFormula());
			stockTxType.setPostP3ActionFormula(eVal.getPostP3ActionFormula());

			daoFactory.getStockTxTypeDAO().merge(stockTxType);
		}

		// Reload enum StockTxType after updates
		loadStockTxType();
	}

	// Load StockTxType type Enum as Generic Enum to Cache
	public void loadStockTxType() {
		String enumName = EnumConstMr.StockTxType;
		StockTxTypeEnum enu =  new StockTxTypeEnum();
		List<StockTxType> rows = daoFactory.getStockTxTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(StockTxType row: rows) {
			enu.addEnumValue(new StockTxTypeValue(row.getStockTxTypeId(), 
					row.getStockTxType(), 
					row.getTxFor(),
					row.getStockTxTypeDesc(),
					row.getPreP1ActionFormula(),
					row.getPreP2ActionFormula(),
					row.getPreP3ActionFormula(),
					row.getPostP1ActionFormula(),
					row.getPostP2ActionFormula(),
					row.getPostP3ActionFormula()));
		}
		EnumCacheMr.addStockTxType(enu);
	}

	//*************** MrType ********************
	public void addMrType(MrTypeEnum enu) {

		MrTypeEnum oEnum = EnumCacheMr.getMrType();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(MrTypeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getMrType());
			}
		}

		for(MrTypeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getMrType())) {
				MrType mrType = new MrType(eVal.getMrType(),
						eVal.getDescription(),
						eVal.getRegistrationFee(),
						eVal.getDepositCreditMultiplie());

				daoFactory.getMrTypeDAO().persist(mrType);
			}
		}

		// Reload enum MrType after updates
		loadMrType();
	}

	public void updateMrType(MrTypeEnum enu) {

		for(MrTypeValue eVal : enu.getEnumValues()) {
			MrType mrType = daoFactory.getMrTypeDAO().findById(eVal.getMrTypeId());
			
			mrType.setMrType(eVal.getMrType());
			mrType.setDescription(eVal.getDescription());
			mrType.setRegistrationFee(eVal.getRegistrationFee());
			mrType.setDepositCreditMultiplie(eVal.getDepositCreditMultiplie());

			daoFactory.getMrTypeDAO().merge(mrType);
		}

		// Reload enum MrType after updates
		loadMrType();
	}

	// Load MrType type Enum as Generic Enum to Cache
	public void loadMrType() {
		String enumName = EnumConstMr.MrType;
		MrTypeEnum enu =  new MrTypeEnum();
		List<MrType> rows = daoFactory.getMrTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(MrType row: rows) {
			enu.addEnumValue(new MrTypeValue(row.getMrTypeId(), 
					row.getMrType(), 
					row.getDescription(),
					row.getRegistrationFee(),
					row.getDepositCreditMultiplie()));
		}
		EnumCacheMr.addMrType(enu);
	}

	//*************** VisitType ********************
	public void addVisitType(VisitTypeEnum enu) {

		VisitTypeEnum oEnum = EnumCacheMr.getVisitType();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(VisitTypeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getVisitType());
			}
		}

		for(VisitTypeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getVisitType())) {
				VisitType visitType = new VisitType(eVal.getVisitType(),
						eVal.getVisitFor(),
						eVal.getVisitTypeDesc());

				daoFactory.getVisitTypeDAO().persist(visitType);
			}
		}

		// Reload enum VisitType after updates
		loadVisitType();
	}

	public void updateVisitType(VisitTypeEnum enu) {

		for(VisitTypeValue eVal : enu.getEnumValues()) {
			VisitType visitType = daoFactory.getVisitTypeDAO().findById(eVal.getVisitTypeId());
			
			visitType.setVisitType(eVal.getVisitType());
			visitType.setVisitFor(eVal.getVisitFor());
			visitType.setVisitTypeDesc(eVal.getVisitTypeDesc());

			daoFactory.getVisitTypeDAO().merge(visitType);
		}

		// Reload enum VisitType after updates
		loadVisitType();
	}

	// Load VisitType type Enum as Generic Enum to Cache
	public void loadVisitType() {
		String enumName = EnumConstMr.VisitType;
		VisitTypeEnum enu =  new VisitTypeEnum();
		List<VisitType> rows = daoFactory.getVisitTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(VisitType row: rows) {
			enu.addEnumValue(new VisitTypeValue(row.getVisitTypeId(), 
					row.getVisitType(), 
					row.getVisitFor(),
					row.getVisitTypeDesc()));
		}
		EnumCacheMr.addVisitType(enu);
	}

}
