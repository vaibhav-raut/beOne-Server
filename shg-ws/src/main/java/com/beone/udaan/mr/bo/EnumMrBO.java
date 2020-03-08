package com.beone.udaan.mr.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.persistence.bo.BaseBO;
import com.beone.shg.net.webservice.rest.model.resp.GenericEnum;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.InternalServerErrorException;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;
import com.beone.udaan.mr.service.model.ItemConditionEnum;
import com.beone.udaan.mr.service.model.MrTypeEnum;
import com.beone.udaan.mr.service.model.ProductCategoryEnum;
import com.beone.udaan.mr.service.model.ProductCategoryTree;
import com.beone.udaan.mr.service.model.ProductPricingEnum;
import com.beone.udaan.mr.service.model.StatusEnum;
import com.beone.udaan.mr.service.model.StockTxTypeEnum;
import com.beone.udaan.mr.service.model.VisitTypeEnum;

@Component("enumMrBO")
public class EnumMrBO extends BaseBO {

    private InitEnumMrBO initEnumMrBO;

	@Autowired
	@Qualifier("initEnumMrBO")
	public void setInitEnumBO(InitEnumMrBO initEnumMrBO) {
		this.initEnumMrBO = initEnumMrBO;
	}

    //****************** GenericEnum ***********************************
    public void addEnumValues(GenericEnum addEnum) throws BadRequestException {
    	initEnumMrBO.addEnumValues(addEnum);
	}

    public void updateEnumValues(GenericEnum updateEnum) throws BadRequestException {
    	initEnumMrBO.updateEnumValues(updateEnum);
	}

    public void loadAllEnumValues() {
    	initEnumMrBO.loadAllEnumValues();
    }

    public GenericEnum getEnumValues(String enumName) throws BadRequestException, InternalServerErrorException {
		
		if(enumName == null || enumName.isEmpty()) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}
		
		GenericEnum gEnum = EnumCacheMr.getEnumValues(enumName);
		
		if(gEnum == null) {	
			throw new InternalServerErrorException("Invalid Enum Name");
		}
		
    	return gEnum;
    }

    //****************** StatusEnum ***********************************
    public void addStatusValues(StatusEnum addStatus) throws BadRequestException {
    	initEnumMrBO.addStatusValues(addStatus);
	}

    public void updateStatusValues(StatusEnum updateStatus) throws BadRequestException {
    	initEnumMrBO.updateStatusValues(updateStatus);
	}

    public StatusEnum getStatusValues(String enumName) throws BadRequestException, InternalServerErrorException {
		
		if(enumName == null || enumName.isEmpty()) {
			throw new BadRequestException("Null Or Empty Status Name");
		}
		
		StatusEnum gStatus = EnumCacheMr.getStatusValues(enumName);
		
		if(gStatus == null) {	
			throw new InternalServerErrorException("Invalid Status Name");
		}
		
    	return gStatus;
    }

    //****************** ItemCondition ***********************************
    public void addItemCondition(ItemConditionEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.addItemCondition(enu);
	}

    public void updateItemCondition(ItemConditionEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.updateItemCondition(enu);
	}

    public ItemConditionEnum getItemCondition() throws InternalServerErrorException {
    	
    	ItemConditionEnum txTypeEnum = EnumCacheMr.getItemCondition();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Name Title Enum");
		}
		
    	return txTypeEnum;
	}

    //****************** ProductCategory ***********************************
    public void addProductCategory(ProductCategoryEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.addProductCategory(enu);
	}

    public void updateProductCategory(ProductCategoryEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.updateProductCategory(enu);
	}

    public ProductCategoryEnum getProductCategory() throws InternalServerErrorException {
    	
    	ProductCategoryEnum txTypeEnum = EnumCacheMr.getProductCategory();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Product Category Enum Not loaded!");
		}
		
    	return txTypeEnum;
	}

    public ProductCategoryTree getProductCategoryTree() throws InternalServerErrorException {
    	
    	ProductCategoryTree productCategoryTree = EnumCacheMr.getProductCategoryTree();
		
		if(productCategoryTree == null) {	
			throw new InternalServerErrorException("Product Category Tree Not loaded!");
		}
		
    	return productCategoryTree;
	}

    //****************** ProductPricing ***********************************
    public void addProductPricing(ProductPricingEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.addProductPricing(enu);
	}

    public void updateProductPricing(ProductPricingEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.updateProductPricing(enu);
	}

    public ProductPricingEnum getProductPricing() throws InternalServerErrorException {
    	
    	ProductPricingEnum txTypeEnum = EnumCacheMr.getProductPricing();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Name Title Enum");
		}
		
    	return txTypeEnum;
	}

    //****************** StockTxType ***********************************
    public void addStockTxType(StockTxTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.addStockTxType(enu);
	}

    public void updateStockTxType(StockTxTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.updateStockTxType(enu);
	}

    public StockTxTypeEnum getStockTxType() throws InternalServerErrorException {
    	
    	StockTxTypeEnum txTypeEnum = EnumCacheMr.getStockTxType();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Name Title Enum");
		}
		
    	return txTypeEnum;
	}

    //****************** MrType ***********************************
    public void addMrType(MrTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.addMrType(enu);
	}

    public void updateMrType(MrTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.updateMrType(enu);
	}

    public MrTypeEnum getMrType() throws InternalServerErrorException {
    	
    	MrTypeEnum txTypeEnum = EnumCacheMr.getMrType();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Name Title Enum");
		}
		
    	return txTypeEnum;
	}

    //****************** VisitType ***********************************
    public void addVisitType(VisitTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.addVisitType(enu);
	}

    public void updateVisitType(VisitTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumMrBO.updateVisitType(enu);
	}

    public VisitTypeEnum getVisitType() throws InternalServerErrorException {
    	
    	VisitTypeEnum txTypeEnum = EnumCacheMr.getVisitType();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Name Title Enum");
		}
		
    	return txTypeEnum;
	}
}
