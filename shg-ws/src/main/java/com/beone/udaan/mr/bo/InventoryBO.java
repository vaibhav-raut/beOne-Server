package com.beone.udaan.mr.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.bo.BaseBO;
import com.beone.shg.net.persistence.bo.GroupBO;
import com.beone.shg.net.persistence.model.District;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.support.DistrictInfoCollector;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;
import com.beone.shg.net.webservice.rest.model.rest.GroupName;
import com.beone.shg.net.webservice.rest.model.rest.GroupREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.config.DBConstMr;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.Brand;
import com.beone.udaan.mr.persistence.model.ItemTag;
import com.beone.udaan.mr.persistence.model.Lot;
import com.beone.udaan.mr.persistence.model.PHubAc;
import com.beone.udaan.mr.persistence.model.PInvoice;
import com.beone.udaan.mr.persistence.model.PMAc;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.model.StockType;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;
import com.beone.udaan.mr.persistence.support.EnumUtilMr;
import com.beone.udaan.mr.persistence.support.StockTxTypeFormula;
import com.beone.udaan.mr.ppo.util.MrProcessJobBuilder;
import com.beone.udaan.mr.service.model.BrandM;
import com.beone.udaan.mr.service.model.BrandName;
import com.beone.udaan.mr.service.model.ItemConditionEnum.ItemConditionValue;
import com.beone.udaan.mr.service.model.ItemTagM;
import com.beone.udaan.mr.service.model.ItemTagO;
import com.beone.udaan.mr.service.model.LotM;
import com.beone.udaan.mr.service.model.ProductPricingEnum.ProductPricingValue;
import com.beone.udaan.mr.service.model.PurchaseInvoice;
import com.beone.udaan.mr.service.model.StatusEnum.StatusValue;
import com.beone.udaan.mr.service.model.StockItemM;
import com.beone.udaan.mr.service.model.StockTxTypeEnum.StockTxTypeValue;
import com.beone.udaan.mr.service.model.StockTypeM;
import com.beone.udaan.mr.service.model.StockTypeName;

@Component("inventoryBO")
public class InventoryBO extends BaseBO {

	@Autowired
	@Qualifier("groupBO")
	private GroupBO groupBO;

	@Autowired
	@Qualifier("mrProcessJobBuilder")
	private MrProcessJobBuilder mrProcessJobBuilder;

    public PurchaseInvoice addPurchaseInvoice(PurchaseInvoice request) throws BadRequestException {
		
		if(request == null) {
			throw new BadRequestException("Null Purchase Invoice Request!");
		}
		if(!ConversionUtil.isValidGroupAcNo(request.getManufactureAcNo())) {
			throw new BadRequestException("Invalid Manufacture Ac No!");
		}
		if(!ConversionUtil.isValidMemberAcNo(request.getEntryByAcNo())) {
			throw new BadRequestException("Invalid Entry By Ac No!");
		}
		
		PInvoice pInvoice = new PInvoice();
		
		{
			GProfile vender = daoFactory.getGProfileDAO().findById(request.getManufactureAcNo());
			if(vender != null) {
				pInvoice.setVender(vender);
			} else {
				throw new BadRequestException("Invalid Vender Ac No!");
			}
		}
		
		pInvoice.setInvoiceNo(request.getInvoiceNo());
		pInvoice.setInvoiceStatusId(EnumCacheMr.getStatusValue(EnumConstMr.InvoiceStatus, request.getInvoiceStatus()).getStatusIndex());
		
		pInvoice.setInvoiceTs(DateUtil.convertTimeToDateWithCurrentDefault(request.getInvoiceTs()));
		pInvoice.setEntryTs(DateUtil.getCurrentTimeDate());
//		pInvoice.setStockTs(request.getStockTs());

		{
			MProfile entryBy = daoFactory.getMProfileDAO().findById(request.getEntryByAcNo());
			if(entryBy != null) {
				pInvoice.setEntryBy(entryBy);
			} else {
				throw new BadRequestException("Invalid Entry By Ac No!");
			}
		}
		
		pInvoice.setNoOfLots(request.getNoOfLots());
		pInvoice.setNoOfItems(request.getNoOfItems());
		pInvoice.setDiscountPer(request.getDiscountPer());
		pInvoice.setVatPer(request.getVatPer());
		pInvoice.setTotal(request.getTotal());
		
		if(request.getVat() != null && request.getVat().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
			pInvoice.setVat(request.getVat());
		} else if(request.getVatPer() > 0) {
			pInvoice.setVat(BDUtil.multiply(request.getTotal(), request.getVatPer()));
		} else {
			pInvoice.setVat(DataUtil.ZERO_BIG_DECIMAL);
		}
		if(request.getOtherTaxes() != null) {
			pInvoice.setOtherTaxes(request.getOtherTaxes());
		} else {
			pInvoice.setOtherTaxes(DataUtil.ZERO_BIG_DECIMAL);
		}
		
		if(request.getGrossTotal() != null && request.getGrossTotal().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
			pInvoice.setGrossTotal(request.getGrossTotal());
		} else {
			pInvoice.setGrossTotal(BDUtil.add(BDUtil.add(pInvoice.getTotal(), pInvoice.getVat()), pInvoice.getOtherTaxes()));
		}
		
		if(request.getDiscount() != null && request.getDiscount().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
			pInvoice.setDiscount(request.getDiscount());
		} else {
			pInvoice.setDiscount(BDUtil.multiply(pInvoice.getGrossTotal(), pInvoice.getDiscountPer()));
		}
		
		if(request.getNetTotal() != null && request.getNetTotal().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
			pInvoice.setNetTotal(request.getNetTotal());
		} else {
			pInvoice.setNetTotal(BDUtil.add(pInvoice.getGrossTotal(), pInvoice.getDiscount()));
		}

		pInvoice.setNetTotalCalculated(request.getNetTotalCalculated());
		pInvoice.setAdvance(request.getAdvance());
		pInvoice.setNetPayment(request.getNetPayment());
		// TODO
//		pInvoice.setPaymentModeId(request.getPaymentModeId());
//		pInvoice.setPaymentTs(DateUtil.convertTimeToDate(request.getPaymentTs()));
		pInvoice.setChequeNo(request.getChequeNo());
		pInvoice.setDescription(request.getDescription());
		pInvoice.setAttachment(request.getAttachment());
		pInvoice.setEntryLocation(request.getEntryLocation());
		
		daoFactory.getPInvoiceDAO().persist(pInvoice);		
		
		{
			PHubAc pHubAc = daoFactory.getPHubAcDAO().findById(DBConstMr.P_MEGA_HUB_GROUP_AC_NO);

			pHubAc.setTotalPurchasedStockAm(BDUtil.add(pHubAc.getTotalPurchasedStockAm(), pInvoice.getNetTotal()));
			pHubAc.setTotalPurchasedStockNo(pHubAc.getTotalPurchasedStockNo() + (int)pInvoice.getNoOfItems());

			daoFactory.getPHubAcDAO().merge(pHubAc);
		}

		request.setPurchasedInvoiceId(pInvoice.getPurchasedInvoiceId());

    	return PurchaseInvoice.buildPurchaseInvoice(pInvoice);
    }

    public PurchaseInvoice updatePurchaseInvoice(PurchaseInvoice request) throws BadRequestException {
		
		if(request == null) {
			throw new BadRequestException("Null Purchase Invoice");
		}
		
    	return request;
    }

    public PurchaseInvoice updatePurchaseInvoiceStatus(PurchaseInvoice request) throws BadRequestException {
		
		if(request == null || request.getPurchasedInvoiceId() <= 0) {
			throw new BadRequestException("Null or Invalid Purchase Invoice");
		}
		PInvoice pInvoice = daoFactory.getPInvoiceDAO().findById(request.getPurchasedInvoiceId());
		if(pInvoice == null) {
			throw new BadRequestException("Invalid Purchase Invoice Id : " + request.getPurchasedInvoiceId());
		}
		
		StatusValue newStatus = EnumCacheMr.getStatusValue(EnumConstMr.InvoiceStatus, request.getInvoiceStatus());
		if(newStatus == null) {
			throw new BadRequestException("Invalid New Purchase Invoice Stauts : " + request.getInvoiceStatus() + "!");
		}
		
		StatusValue oldStatus = EnumCacheMr.getStatusValue(EnumConstMr.InvoiceStatus, pInvoice.getInvoiceStatusId());
		if(!oldStatus.getNextStatus().contains(request.getInvoiceStatus())) {
			throw new BadRequestException("Invalid Transition of Purchase Invoice Stauts from : " + 
					oldStatus.getStatusValue() + " to " + request.getInvoiceStatus() + "!");
		}
		
		pInvoice.setInvoiceStatusId(newStatus.getStatusIndex());
		
		daoFactory.getPInvoiceDAO().merge(pInvoice);
		
		StatusValue oldLotStatus = EnumCacheMr.getStatusValue(EnumConstMr.LotStatus, EnumUtilMr.getLotStatus(oldStatus.getStatusValue()));
		StatusValue newLotStatus = EnumCacheMr.getStatusValue(EnumConstMr.LotStatus, EnumUtilMr.getLotStatus(newStatus.getStatusValue()));
		
		boolean updateValue = EnumUtilMr.isLotStatusToUpdate(newLotStatus.getStatusValue());
		BigDecimal stockValue = DataUtil.ZERO_BIG_DECIMAL;
		int stockNo = DataUtil.ZERO_INTEGER;
		
		if(newLotStatus != null) {
			
			List<Lot> lots = daoFactory.getLotDAO().getLotsForPurchaseInvoice(request.getPurchasedInvoiceId());
			
			if(lots != null && !lots.isEmpty()) {
				for(Lot lot: lots) {
					lot.setLotStatusId(newLotStatus.getStatusIndex());

					if(updateValue) {

						StockType stockType = lot.getStockType();
						ProductPricingValue pricing = EnumCacheMr.getProductPricingValue(stockType.getProductPricingId());

						BigDecimal wsPrice = GenAlgoUtil.roundUp(BDUtil.multiply(lot.getItemPriceAm(), (1 - lot.getDiscountPer())), 4);

						BigDecimal lotValue = BDUtil.multiply(GenAlgoUtil.roundUp(BDUtil.multiply(wsPrice, pricing.getWpPercent()), 0), lot.getNoPurchased());
						stockValue = BDUtil.add(stockValue, lotValue);
						stockNo = stockNo + lot.getNoPurchased();

						Brand brand = stockType.getBrand();
						
						if(EnumUtilMr.isLotStatusOfOrderLevel(oldLotStatus.getStatusValue())) {
							
							stockType.setCurrentOrderedStockAm(BDUtil.sub(stockType.getCurrentOrderedStockAm(), lotValue));
							stockType.setCurrentOrderedStockNo(stockType.getCurrentOrderedStockNo() - lot.getNoPurchased());
							
							brand.setCurrentOrderedStockAm(BDUtil.sub(brand.getCurrentOrderedStockAm(), lotValue));
							brand.setCurrentOrderedStockNo(brand.getCurrentOrderedStockNo() - lot.getNoPurchased());
						}
						else if(oldLotStatus.getStatusValue().equals(EnumConstMr.LotStatus_Delivered)) {
							
							stockType.setCurrentDeliveredStockAm(BDUtil.sub(stockType.getCurrentDeliveredStockAm(), lotValue));
							stockType.setCurrentDeliveredStockNo(stockType.getCurrentDeliveredStockNo() - lot.getNoPurchased());
							
							brand.setCurrentDeliveredStockAm(BDUtil.sub(brand.getCurrentDeliveredStockAm(), lotValue));
							brand.setCurrentDeliveredStockNo(brand.getCurrentDeliveredStockNo() - lot.getNoPurchased());
						}

						if(newLotStatus.getStatusValue().equals(EnumConstMr.LotStatus_Ordered)) {
							
							stockType.setCurrentOrderedStockAm(BDUtil.add(stockType.getCurrentOrderedStockAm(), lotValue));
							stockType.setCurrentOrderedStockNo(stockType.getCurrentOrderedStockNo() + lot.getNoPurchased());
							
							brand.setCurrentOrderedStockAm(BDUtil.add(brand.getCurrentOrderedStockAm(), lotValue));
							brand.setCurrentOrderedStockNo(brand.getCurrentOrderedStockNo() + lot.getNoPurchased());
						}
						else if(newLotStatus.getStatusValue().equals(EnumConstMr.LotStatus_Delivered)) {
							
							lot.setNoDelivered(lot.getNoPurchased());

							stockType.setCurrentDeliveredStockAm(BDUtil.add(stockType.getCurrentDeliveredStockAm(), lotValue));
							stockType.setCurrentDeliveredStockNo(stockType.getCurrentDeliveredStockNo() + lot.getNoPurchased());

							brand.setCurrentDeliveredStockAm(BDUtil.add(brand.getCurrentDeliveredStockAm(), lotValue));
							brand.setCurrentDeliveredStockNo(brand.getCurrentDeliveredStockNo() + lot.getNoPurchased());
						}
						else if(newLotStatus.getStatusValue().equals(EnumConstMr.LotStatus_To_Stock)) {
							
							lot.setNoToStock(lot.getNoPurchased());

							stockType.setCurrentToStockAm(BDUtil.add(stockType.getCurrentToStockAm(), lotValue));
							stockType.setCurrentToStockNo(stockType.getCurrentToStockNo() + lot.getNoPurchased());

							brand.setCurrentToStockAm(BDUtil.add(brand.getCurrentToStockAm(), lotValue));
							brand.setCurrentToStockNo(brand.getCurrentToStockNo() + lot.getNoPurchased());
						}
						
						daoFactory.getStockTypeDAO().merge(stockType);
						daoFactory.getBrandDAO().merge(brand);
					}
					daoFactory.getLotDAO().merge(lot);
				}
			} else {
				updateValue = false;
			}
		}
		
		if(updateValue) {
			PHubAc pHubAc = daoFactory.getPHubAcDAO().findById(DBConstMr.P_MEGA_HUB_GROUP_AC_NO);

			if(EnumUtilMr.isLotStatusOfOrderLevel(oldLotStatus.getStatusValue())) {
				
				pHubAc.setCurrentOrderedStockAm(BDUtil.sub(pHubAc.getCurrentOrderedStockAm(), stockValue));
				pHubAc.setCurrentOrderedStockNo(pHubAc.getCurrentOrderedStockNo() - stockNo);
			}
			else if(oldLotStatus.getStatusValue().equals(EnumConstMr.LotStatus_Delivered)) {
				
				pHubAc.setCurrentDeliveredStockAm(BDUtil.sub(pHubAc.getCurrentDeliveredStockAm(), stockValue));
				pHubAc.setCurrentDeliveredStockNo(pHubAc.getCurrentDeliveredStockNo() - stockNo);
			}

			if(newLotStatus.getStatusValue().equals(EnumConstMr.LotStatus_Ordered)) {
				
				pHubAc.setCurrentOrderedStockAm(BDUtil.add(pHubAc.getCurrentOrderedStockAm(), stockValue));
				pHubAc.setCurrentOrderedStockNo(pHubAc.getCurrentOrderedStockNo() + stockNo);
			}
			else if(newLotStatus.getStatusValue().equals(EnumConstMr.LotStatus_Delivered)) {

				pHubAc.setCurrentDeliveredStockAm(BDUtil.add(pHubAc.getCurrentDeliveredStockAm(), stockValue));
				pHubAc.setCurrentDeliveredStockNo(pHubAc.getCurrentDeliveredStockNo() + stockNo);
			}
			else if(newLotStatus.getStatusValue().equals(EnumConstMr.LotStatus_To_Stock)) {

				pHubAc.setCurrentToStockAm(BDUtil.add(pHubAc.getCurrentToStockAm(), stockValue));
				pHubAc.setCurrentToStockNo(pHubAc.getCurrentToStockNo() + stockNo);
			}
			
			daoFactory.getPHubAcDAO().merge(pHubAc);
		}
		
    	return PurchaseInvoice.buildPurchaseInvoice(pInvoice);
    }

    protected List<PurchaseInvoice> buildPurchaseInvoiceList(List<PInvoice> pInvoices) {
		
    	List<PurchaseInvoice> purchaseInvoices = new ArrayList<PurchaseInvoice>();
		
    	if(pInvoices != null && !pInvoices.isEmpty()) {
    		for(PInvoice pInvoice : pInvoices) {
    			purchaseInvoices.add(PurchaseInvoice.buildPurchaseInvoice(pInvoice));
    		}
    	}
    	
    	return purchaseInvoices;
    }

    public List<PurchaseInvoice> getOpenPurchaseInvoice() throws BadRequestException {
				
    	List<PInvoice> pInvoices = daoFactory.getPInvoiceDAO().getOpenPurchaseInvoice();
    	
    	return buildPurchaseInvoiceList(pInvoices);
    }

    public List<PurchaseInvoice> getOpenPurchaseInvoiceForVendor(long vendorAcNo) throws BadRequestException {

    	if(!ConversionUtil.isValidGroupAcNo(vendorAcNo)) {
			throw new BadRequestException("Invalid Manufacture Account Number!");
		}
		GProfile manufacture = daoFactory.getGProfileDAO().findById(vendorAcNo);
		if(manufacture == null) {
			throw new BadRequestException("Invalid Manufacture Account Number: " + vendorAcNo + " !");
		}
				
    	List<PInvoice> pInvoices = daoFactory.getPInvoiceDAO().getOpenPurchaseInvoiceForVendor(vendorAcNo);
    	
    	return buildPurchaseInvoiceList(pInvoices);
    }

    public List<PurchaseInvoice> getAllPurchaseInvoiceForVendor(long vendorAcNo) throws BadRequestException {

    	if(!ConversionUtil.isValidGroupAcNo(vendorAcNo)) {
			throw new BadRequestException("Invalid Manufacture Account Number!");
		}
		GProfile manufacture = daoFactory.getGProfileDAO().findById(vendorAcNo);
		if(manufacture == null) {
			throw new BadRequestException("Invalid Manufacture Account Number: " + vendorAcNo + " !");
		}
				
    	List<PInvoice> pInvoices = daoFactory.getPInvoiceDAO().getAllPurchaseInvoiceForVendor(vendorAcNo);
    	
    	return buildPurchaseInvoiceList(pInvoices);
    }

    public List<LotM> addLots(List<LotM> request) throws BadRequestException {
    	
    	if(request == null || request.isEmpty()) {
			throw new BadRequestException("Invalid Request Data!");
    	}
    	
    	List<LotM> lotMs = new ArrayList<LotM>();
    	
    	for(LotM lotM : request) {
    		LotM lot = addLot(lotM);
    		if(lot != null) {
    			lotMs.add(lotM);
    		}
    	}
    	
    	return lotMs;
    }

    public LotM addLot(LotM request) throws BadRequestException {
		
    	Lot lot = new Lot();
		
    	PInvoice invoice = daoFactory.getPInvoiceDAO().findById(request.getInvoiceId());
    	if(invoice != null) {
    		lot.setPInvoice(invoice);
    	} else {
    		throw new BadRequestException("Invalid Purchase Invoice!");
    	}
    	
    	String lotStatusStr = EnumUtilMr.getLotStatus(EnumCacheMr.getStatusValue(EnumConstMr.InvoiceStatus, invoice.getInvoiceStatusId()).getStatusValue());
    	StatusValue lotStatus = EnumCacheMr.getStatusValue(EnumConstMr.LotStatus, lotStatusStr);
    	if(lotStatus == null) {
    		throw new BadRequestException("Invalid Purchase Invoice Status!");
    	}
    	
    	StockType stockType = daoFactory.getStockTypeDAO().findById(request.getStockTypeId());
    	if(stockType != null) {
    		lot.setStockType(stockType);
    	} else {
    		throw new BadRequestException("Invalid Stock Type!");
    	}
     	
    	lot.setLotStatusId(EnumCacheMr.getStatusValue(EnumConstMr.LotStatus, request.getLotStatus()).getStatusIndex());
    	lot.setName(request.getName());
    	lot.setNoPerSet(request.getNoPerSet());
    	lot.setNoOfSets(request.getNoOfSets());
    	lot.setNoPurchased(request.getNoPerSet() * request.getNoOfSets());
    	lot.setNoCreated(DataUtil.ZERO_INTEGER);
    	lot.setNoStocked(DataUtil.ZERO_INTEGER);
    	lot.setNoSold(DataUtil.ZERO_INTEGER);
    	lot.setNoDamaged(DataUtil.ZERO_INTEGER);
    	lot.setItemPriceAm(request.getItemPriceAm());
    	lot.setLotPriceAm(GenAlgoUtil.roundUp(BDUtil.multiply(request.getItemPriceAm(), lot.getNoPurchased()), 4));
    	lot.setVatAm(GenAlgoUtil.roundUp(BDUtil.multiply(lot.getLotPriceAm(), invoice.getVatPer()), 4));
    	lot.setDiscountPer(invoice.getDiscountPer());
    	lot.setDiscountAm(GenAlgoUtil.roundUp(BDUtil.multiply(BDUtil.add(lot.getLotPriceAm(), lot.getVatAm()), invoice.getDiscountPer()), 4));
    	lot.setAvgMrItemSoldAm(DataUtil.ZERO_BIG_DECIMAL);
    	
		if(lotStatus.getStatusValue().equals(EnumConstMr.LotStatus_Delivered)) {
			
	    	lot.setNoDelivered(lot.getNoPurchased());
	    	lot.setNoToStock(DataUtil.ZERO_INTEGER);
		}
		else if(lotStatus.getStatusValue().equals(EnumConstMr.LotStatus_To_Stock)) {
			
	    	lot.setNoDelivered(lot.getNoPurchased());
	    	lot.setNoToStock(lot.getNoPurchased());
		}
    	
		daoFactory.getLotDAO().persist(lot);

		invoice.setNetTotalCalculated(BDUtil.add(invoice.getNetTotalCalculated(), BDUtil.sub(BDUtil.add(lot.getLotPriceAm(), lot.getVatAm()), lot.getDiscountAm())));
		daoFactory.getPInvoiceDAO().merge(invoice);
		

		ProductPricingValue pricing = EnumCacheMr.getProductPricingValue(stockType.getProductPricingId());

		BigDecimal wsPrice = GenAlgoUtil.roundUp(BDUtil.multiply(lot.getItemPriceAm(), (1 - lot.getDiscountPer())), 4);
		BigDecimal stockValue = BDUtil.multiply(GenAlgoUtil.roundUp(BDUtil.multiply(wsPrice, pricing.getWpPercent()), 0), lot.getNoPurchased());

		stockType.setNoLots(stockType.getNoLots() + 1);
		
		Brand brand = stockType.getBrand();
		brand.setNoLots(brand.getNoLots() + 1);
		
		{
			PHubAc pHubAc = daoFactory.getPHubAcDAO().findById(DBConstMr.P_MEGA_HUB_GROUP_AC_NO);

			if(EnumUtilMr.isLotStatusOrdered(lotStatus.getStatusValue())) {
				
				stockType.setCurrentOrderedStockAm(BDUtil.add(stockType.getCurrentOrderedStockAm(), stockValue));
				stockType.setCurrentOrderedStockNo(stockType.getCurrentOrderedStockNo() + lot.getNoPurchased());
				
				brand.setCurrentOrderedStockAm(BDUtil.add(brand.getCurrentOrderedStockAm(), stockValue));
				brand.setCurrentOrderedStockNo(brand.getCurrentOrderedStockNo() + lot.getNoPurchased());
				
				pHubAc.setCurrentOrderedStockAm(BDUtil.add(pHubAc.getCurrentOrderedStockAm(), stockValue));
				pHubAc.setCurrentOrderedStockNo(pHubAc.getCurrentOrderedStockNo() + lot.getNoPurchased());
			}
			else if(lotStatus.getStatusValue().equals(EnumConstMr.LotStatus_Delivered)) {

				stockType.setCurrentDeliveredStockAm(BDUtil.add(stockType.getCurrentDeliveredStockAm(), stockValue));
				stockType.setCurrentDeliveredStockNo(stockType.getCurrentDeliveredStockNo() + lot.getNoPurchased());

				brand.setCurrentDeliveredStockAm(BDUtil.add(brand.getCurrentDeliveredStockAm(), stockValue));
				brand.setCurrentDeliveredStockNo(brand.getCurrentDeliveredStockNo() + lot.getNoPurchased());

				pHubAc.setCurrentDeliveredStockAm(BDUtil.add(pHubAc.getCurrentDeliveredStockAm(), stockValue));
				pHubAc.setCurrentDeliveredStockNo(pHubAc.getCurrentDeliveredStockNo() + lot.getNoPurchased());
			}
			else if(lotStatus.getStatusValue().equals(EnumConstMr.LotStatus_To_Stock)) {

				stockType.setCurrentToStockAm(BDUtil.add(stockType.getCurrentToStockAm(), stockValue));
				stockType.setCurrentToStockNo(stockType.getCurrentToStockNo() + lot.getNoPurchased());

				brand.setCurrentToStockAm(BDUtil.add(brand.getCurrentToStockAm(), stockValue));
				brand.setCurrentToStockNo(brand.getCurrentToStockNo() + lot.getNoPurchased());

				pHubAc.setCurrentToStockAm(BDUtil.add(pHubAc.getCurrentToStockAm(), stockValue));
				pHubAc.setCurrentToStockNo(pHubAc.getCurrentToStockNo() + lot.getNoPurchased());
			}
			
			daoFactory.getStockTypeDAO().merge(stockType);
			daoFactory.getBrandDAO().merge(brand);
			daoFactory.getPHubAcDAO().merge(pHubAc);
		}

		request.setLotId(lot.getLotId());
		
		return LotM.buildLot(lot);
    }

    public LotM updateLot(LotM request) throws BadRequestException {
		
    	if(request == null) {
			throw new BadRequestException("Null Request!");
    	}  	
    	Lot lot = daoFactory.getLotDAO().findById(request.getLotId());
    	if(lot == null) {
			throw new BadRequestException("Invalid Lot ID : " + request.getLotId() + " !");
    	}  	
		
    	lot.setLotStatusId(EnumCacheMr.getStatusValue(EnumConstMr.LotStatus, request.getLotStatus()).getStatusIndex());
    	lot.setNoPerSet(request.getNoPerSet());
    	lot.setNoOfSets(request.getNoOfSets());
    	lot.setNoPurchased(request.getNoPurchased());
    	lot.setItemPriceAm(request.getItemPriceAm());
    	lot.setLotPriceAm(request.getLotPriceAm());
//    	lot.setDiscountPer(request.getDiscountPer());
//    	lot.setDiscountAm(request.getDiscountAm());
//    	lot.setVatAm(request.getVatAm());
     	
		daoFactory.getLotDAO().persist(lot);

		return LotM.buildLot(lot);
    }

    public List<LotM> getLots(long purchaseInvoiceId) throws BadRequestException {
    	
    	List<LotM> lotMs = new ArrayList<LotM>();
    	
    	PInvoice pInvoice = daoFactory.getPInvoiceDAO().findById(purchaseInvoiceId);
    	if(pInvoice == null) {
			throw new BadRequestException("Invalid Purchase Invoice Id!");
    	}
    	
    	List<Lot> lots = daoFactory.getLotDAO().getLotsForPurchaseInvoice(purchaseInvoiceId);
    	
    	if(lots != null && !lots.isEmpty()) {
    		for(Lot lot : lots) {
    			lotMs.add(LotM.buildLot(lot));
    		}
    	}
    	return lotMs;
    }

    public LotM getLot(long lotId) throws BadRequestException {
    	    	
    	Lot lot = daoFactory.getLotDAO().findById(lotId);
    	if(lot == null) {
			throw new BadRequestException("Invalid Lot Id!");
    	}

    	return LotM.buildLot(lot);
    }

    public StockTypeM getStockType(long stockTypeId) throws BadRequestException {
    	    	
    	StockType stockType = daoFactory.getStockTypeDAO().findById(stockTypeId);
    	if(stockType == null) {
			throw new BadRequestException("Invalid StockType Id!");
    	}

    	return StockTypeM.buildStockType(stockType);
    }

    public BrandM getBrand(long brandId) throws BadRequestException {
    	    	
    	Brand brand = daoFactory.getBrandDAO().findById(brandId);
    	if(brand == null) {
			throw new BadRequestException("Invalid Brand Id!");
    	}

    	return BrandM.buildBrand(brand);
    }

    public List<LotM> getAllLotsToStock() throws BadRequestException {
    	
    	List<LotM> lotMs = new ArrayList<LotM>();
    	
    	List<Lot> lots = daoFactory.getLotDAO().getAllLotsToStock();
    	
    	if(lots != null && !lots.isEmpty()) {
    		for(Lot lot : lots) {
    			lotMs.add(LotM.buildLot(lot));
    		}
    	}
    	return lotMs;
    }

    public List<LotM> getLotsToStockForType(long stockTypeId) throws BadRequestException {
    	
    	List<LotM> lotMs = new ArrayList<LotM>();
    	
    	StockType stockType = daoFactory.getStockTypeDAO().findById(stockTypeId);
    	if(stockType == null) {
			throw new BadRequestException("Invalid StockType Id!");
    	}
    	
    	List<Lot> lots = daoFactory.getLotDAO().getLotsToStockForType(stockTypeId);
    	
    	if(lots != null && !lots.isEmpty()) {
    		for(Lot lot : lots) {
    			lotMs.add(LotM.buildLot(lot));
    		}
    	}
    	return lotMs;
    }

    public List<LotM> getLotsToStockForBrand(long brandId) throws BadRequestException {
    	
    	List<LotM> lotMs = new ArrayList<LotM>();
    	
    	Brand brand = daoFactory.getBrandDAO().findById(brandId);
    	if(brand == null) {
			throw new BadRequestException("Invalid Brand Id!");
    	}
    	
    	List<Lot> lots = daoFactory.getLotDAO().getLotsToStockForBrand(brandId);
    	
    	if(lots != null && !lots.isEmpty()) {
    		for(Lot lot : lots) {
    			lotMs.add(LotM.buildLot(lot));
    		}
    	}
    	return lotMs;
    }
    
    public List<LotM> generateStockFromLots(List<LotM> request) throws BadRequestException {
    	
    	if(request == null || request.isEmpty()) {
			throw new BadRequestException("Invalid Request Lot List!");
    	}
    	
    	List<LotM> result = new ArrayList<LotM>(request.size());  	
    	for(LotM lotM: request) {
    		result.add(generateStockForNo(lotM, lotM.getNoToStock()));
    	}
    	
    	return result;
    }

    public LotM generateStock(LotM request) throws BadRequestException {
    	
    	if(request == null || request.getLotId() <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Request Lot!");
    	}
    	Lot lot = daoFactory.getLotDAO().findById(request.getLotId());
    	if(lot == null) {
			throw new BadRequestException("Invalid Request Lot!");
    	}
    	
    	int noToCreate = lot.getNoPurchased() - lot.getNoCreated();
    	if(noToCreate <= 0) {
			throw new BadRequestException("No More Items Outstanding To Create!");
    	}
   	
    	return generateStock(lot, noToCreate);	
    }
    
    public LotM generateStockForNo(LotM request, int noToCreate) throws BadRequestException {
    	
    	if(request == null || request.getLotId() <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Request Lot!");
    	}
    	Lot lot = daoFactory.getLotDAO().findById(request.getLotId());
    	if(lot == null) {
			throw new BadRequestException("Invalid Request Lot!");
    	}
   	
    	return generateStock(lot, noToCreate);	
    }
    
    protected LotM generateStock(Lot lot, int noToCreate) throws BadRequestException {
    	
    	if(!EnumCacheMr.getStatusValue(EnumConstMr.LotStatus, lot.getLotStatusId()).getStatusValue().equals(EnumConstMr.LotStatus_To_Stock)) {
			throw new BadRequestException("Invalid Lot Status for Stock Generation!");
    	}	
    	
    	PMAc hubOwner = daoFactory.getPMAcDAO().findById(DBConstMr.P_MEGA_HUB_ADMIN_AC_NO);
    	if(hubOwner == null) {
			throw new BadRequestException("Invalid HUB Owner!");
    	}
    	
    	if(noToCreate > lot.getNoToStock()) {
    		noToCreate = lot.getNoToStock();
    	}
    	
    	StockType stockType = lot.getStockType();
    	Brand brand = stockType.getBrand();
    	
    	ItemConditionValue itemConditionValue = EnumCacheMr.getItemConditionValue(stockType.getItemConditionId());    	
    	ProductPricingValue pricing = EnumCacheMr.getProductPricingValue(stockType.getProductPricingId());
    	
    	BigDecimal wsPrice = GenAlgoUtil.roundUp(BDUtil.divide(BDUtil.sub(BDUtil.add(lot.getLotPriceAm(), lot.getVatAm()), lot.getDiscountAm()), lot.getNoPurchased()), 4);
    	BigDecimal mrPrice = GenAlgoUtil.roundUp(BDUtil.multiply(wsPrice, pricing.getWpPercent()), 0);
    	BigDecimal mrpPrice = GenAlgoUtil.roundUp(BDUtil.multiply(mrPrice, pricing.getMrPercent()), 0);
    	BigDecimal disMRPPrice = GenAlgoUtil.roundUp(BDUtil.multiply(mrpPrice, (1 - itemConditionValue.getMrpDiscountPer())), 0);
    	BigDecimal discountAm = GenAlgoUtil.roundUp(BDUtil.multiply(mrPrice, itemConditionValue.getSpDiscountPer()), 0);
    	BigDecimal vatAm = GenAlgoUtil.roundUp(BDUtil.multiply(mrPrice, pricing.getVatPercent()), 0);
    	
    	for(int i = 0; i < noToCreate; i++) {
    		
    		StockItem item = new StockItem();
    		
    		item.setStockType(stockType);
    		item.setLot(lot);
//    		item.setSaleInvoice(lot.get);
    		item.setItemStatusId(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Created));
    		item.setItemConditionId(itemConditionValue.getItemConditionId());
    		item.setOwner(hubOwner);
    		item.setName(stockType.getNameDisplay());
//    		item.setDesignNo();
    		item.setWsPrice(wsPrice);
    		item.setMrPrice(mrPrice);
    		item.setMrpPrice(mrpPrice);
    		item.setDisMrpPrice(disMRPPrice);
    		item.setSoldPrice(null);
    		item.setMrSoldPrice(null);
    		item.setDiscountPer(itemConditionValue.getSpDiscountPer());
    		item.setDiscountAm(discountAm);
    		item.setVatPer(pricing.getVatPercent());
    		item.setVatAm(vatAm);
    		item.setCheckTs(DateUtil.getCurrentTimeDate());
    		item.setCheckFlag(EnumConstMr.CheckFlag_Unchecked);
    		
    		daoFactory.getStockItemDAO().persist(item);
    		
    		createItemTag(item);
    	}
    	
    	// Update Lot Status as Stocked
    	{
    		lot.setNoToStock(lot.getNoToStock() - noToCreate);
    		lot.setNoCreated(lot.getNoCreated() + noToCreate);
    		
    		if(lot.getNoPurchased() == (lot.getNoCreated() + lot.getNoStocked())) {
    			lot.setLotStatusId(EnumCacheMr.getStatusValue(EnumConstMr.LotStatus, EnumConstMr.LotStatus_Created).getStatusIndex());
    		}
    		daoFactory.getLotDAO().merge(lot);
    	}

		BigDecimal updateValue = BDUtil.multiply(mrPrice, noToCreate);

		{
    		stockType.setStockStatusId(EnumCacheMr.getStatusValue(EnumConstMr.StockTypeStatus, EnumConstMr.StockTypeStatus_Created).getStatusIndex());
			
			stockType.setCurrentToStockAm(BDUtil.sub(stockType.getCurrentToStockAm(), updateValue));
			stockType.setCurrentToStockNo(stockType.getCurrentToStockNo() - noToCreate);

			stockType.setCurrentCreatedStockAm(BDUtil.add(stockType.getCurrentCreatedStockAm(), updateValue));
			stockType.setCurrentCreatedStockNo(stockType.getCurrentCreatedStockNo() + noToCreate);

    		daoFactory.getStockTypeDAO().merge(stockType);
    	}

    	{			
			brand.setCurrentToStockAm(BDUtil.sub(brand.getCurrentToStockAm(), updateValue));
			brand.setCurrentToStockNo(brand.getCurrentToStockNo() - noToCreate);

			brand.setCurrentCreatedStockAm(BDUtil.add(brand.getCurrentCreatedStockAm(), updateValue));
			brand.setCurrentCreatedStockNo(brand.getCurrentCreatedStockNo() + noToCreate);

    		daoFactory.getBrandDAO().merge(brand);
    	}
		
		{
			PHubAc pHubAc = daoFactory.getPHubAcDAO().findById(DBConstMr.P_MEGA_HUB_GROUP_AC_NO);
			
			pHubAc.setCurrentToStockAm(BDUtil.sub(pHubAc.getCurrentToStockAm(), updateValue));
			pHubAc.setCurrentToStockNo(pHubAc.getCurrentToStockNo() - noToCreate);

			pHubAc.setCurrentCreatedStockAm(BDUtil.add(pHubAc.getCurrentCreatedStockAm(), updateValue));
			pHubAc.setCurrentCreatedStockNo(pHubAc.getCurrentCreatedStockNo() + noToCreate);

			daoFactory.getPHubAcDAO().merge(pHubAc);
		}
		
		return LotM.buildLot(lot);
    }

    public StockItemM verifyStock(long entryByAcNo, StockItemM request) throws BadRequestException {
		
    	if(request == null || request.getStockItemId() <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Request Stock Item!");
    	}
    	
    	return verifyStock(entryByAcNo, request.getStockItemId());
    }
    public StockItemM verifyStock(long entryByAcNo, long stockItemId) throws BadRequestException {
		
    	if(stockItemId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Request Stock Item!");
    	}
		StockItem item = daoFactory.getStockItemDAO().findById(stockItemId);
    	if(item == null) {
			throw new BadRequestException("Invalid Request Stock Item!");
    	}
    	
    	if(!EnumCacheMr.getStatusValue(EnumConstMr.ItemStatus, item.getItemStatusId()).getStatusValue().equals(EnumConstMr.ItemStatus_Created)) {
			throw new BadRequestException("Invalid Item Status for Stock Verification!");
    	}
		
		StockTxTypeValue stockTxTypeValue = EnumCacheMr.getStockTxTypeValue(EnumConstMr.StockTxType_Stock_Verified);
		if(stockTxTypeValue == null) {
			throw new BadRequestException("Invalid StockTxType: " + EnumConstMr.StockTxType_Stock_Verified);
		}
		StockTxTypeFormula stockTxTypeFormula = EnumCacheMr.getStockTxTypeFormula(stockTxTypeValue.getStockTxTypeId());
  	
		{
    		
    		item.setItemStatusId(EnumCacheMr.getStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Stocked).getStatusIndex());
    		item.setCheckTs(DateUtil.getCurrentTimeDate());
    		item.setCheckFlag(EnumConstMr.CheckFlag_Checked);
    		
    		daoFactory.getStockItemDAO().merge(item);
    		
    		StockType stockType = item.getStockType();
    		
    		// Create Stock Transaction for Stock Creation
			StockTx stockTx = new StockTx();
			
			stockTx.setStockTxTypeId(stockTxTypeValue.getStockTxTypeId());
			stockTx.setStockItemId(item.getStockItemId());
			stockTx.setStockTypeId(stockType.getStockTypeId());
			stockTx.setBrandId(stockType.getBrand().getBrandId());
			stockTx.setLotId(item.getLot().getLotId());
			stockTx.setAmount(item.getMrPrice());
			stockTx.setExtraAm(item.getWsPrice());
			stockTx.setEntryByAcNo(entryByAcNo);
			stockTx.setOwnerAcNo(item.getOwner().getMemberAcNo());
			
			stockTx.setItemStatusId(item.getItemStatusId());
			stockTx.setPrevItemStatusId(0);
			stockTx.setTxTs(DateUtil.getCurrentTimeDate());
			stockTx.setEntryTs(DateUtil.getCurrentTimeDate());
			stockTx.setVerifyTs(DateUtil.getCurrentTimeDate());
			stockTx.setDescription("Stock Verification!");
			
			daoFactory.getStockTxDAO().persist(stockTx);
			
			mrProcessJobBuilder.pushNowProcessJobMr(stockTxTypeFormula.getPreP1ActionFormula(), 
					stockTxTypeFormula.getPreP2ActionFormula(), 
					stockTxTypeFormula.getPreP3ActionFormula(), stockTx);
			
			mrProcessJobBuilder.pushPostProcessJobMr(stockTxTypeFormula.getPostP1ActionFormula(), 
					stockTxTypeFormula.getPostP2ActionFormula(), 
					stockTxTypeFormula.getPostP3ActionFormula(), stockTx);
    		
    	}
		
		List<ItemTag> itemTags = daoFactory.getItemTagDAO().getActiveItemTagForStockItem(item.getStockItemId());
		if(itemTags != null && !itemTags.isEmpty()) {
			for(ItemTag itemTag: itemTags) {
	        	
	        	itemTag.setTagStatusId(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.TagStatus, EnumConstMr.TagStatus_Printed));
	        	itemTag.setPrintedTs(DateUtil.getCurrentTimeDate());
	        	
	        	daoFactory.getItemTagDAO().merge(itemTag);
			}
		}
		
		return StockItemM.buildStockItemM(item, daoFactory);
    }

    public List<StockTypeName> searchStockType(long brandId, String stockNameShort) throws BadRequestException {
		
		if(stockNameShort == null || stockNameShort.isEmpty()) {
			throw new BadRequestException("Null or Empty Search String for Stock Name!");
		}
		if(stockNameShort.length() < 3) {
			throw new BadRequestException("Search String for Stock Name Less that 4 Char!");
		}

		if(brandId < 0) {
			throw new BadRequestException("Invalid BrandId!");
		}
		Brand brand = daoFactory.getBrandDAO().findById(brandId);
		if(brand == null) {
			throw new BadRequestException("Invalid BrandId!");
		}
		
		List<StockType> stockTypes = daoFactory.getStockTypeDAO().getStockForBrandByName(brandId, stockNameShort);
		
		List<StockTypeName> nameList = new ArrayList<StockTypeName>();
		
		if(stockTypes != null && !stockTypes.isEmpty()) {

			for(StockType stockType : stockTypes) {
				nameList.add(StockTypeName.buildStockTypeName(stockType, brand));
			}
		}
		
    	return nameList;
    }

    public List<StockTypeName> searchStockType(String stockNameShort) throws BadRequestException {
		
		if(stockNameShort == null || stockNameShort.isEmpty()) {
			throw new BadRequestException("Null or Empty Search String for Stock Name!");
		}
		if(stockNameShort.length() < 3) {
			throw new BadRequestException("Search String for Stock Name Less that 3 Char!");
		}
		
		List<StockType> stockTypes = daoFactory.getStockTypeDAO().getStockByName(stockNameShort);
		
		List<StockTypeName> nameList = new ArrayList<StockTypeName>();
		
		if(stockTypes != null && !stockTypes.isEmpty()) {

			for(StockType stockType : stockTypes) {
				
				Brand brand = stockType.getBrand();
				if(brand == null) {
					continue;
				}
				
				nameList.add(StockTypeName.buildStockTypeName(stockType, brand));
			}
		}
		
    	return nameList;
    }

    public StockTypeM addStockType(StockTypeM request) throws BadRequestException {
		
		if(request == null) {
			throw new BadRequestException("Null StockType Object!");
		}	
		if(request.getBrandId() <= 0) {
			throw new BadRequestException("Invalid Manufacture Account Number!");
		}
		if(request.getName() == null || request.getName().isEmpty()) {
			throw new BadRequestException("Null Or Empty Stock Type Name!");
		}
		if(request.getCategory() == null || request.getCategory().isEmpty()) {
			throw new BadRequestException("Null Or Empty Stock Type Category!");
		}

		Brand brand = daoFactory.getBrandDAO().findById(request.getBrandId());
		if(brand == null) {
			throw new BadRequestException("Invalid Manufacture Account Number: " + request.getBrandId() + " !");
		}
		
		int categoryId = EnumCacheMr.getIndexOfProductCategory(request.getCategory());
		if(categoryId <= 0) {
			throw new BadRequestException("Invalid Stock Type Category!");
		}
		
		if(request.getProductPricing() == null || request.getProductPricing().isEmpty()) {
			request.setProductPricing(EnumConstMr.ProductPricing_Default);
		} 
		int productPricingId = EnumCacheMr.getIndexOfProductPricing(request.getProductPricing());
		if(productPricingId <= 0) {
			productPricingId = EnumCacheMr.getIndexOfProductPricing(EnumConstMr.ProductPricing_Default);
		}
		
		List<StockTypeName> stockTypeNames = searchStockType(request.getBrandId(), request.getName());
		boolean isDuplicate = false;
		if(stockTypeNames != null && !stockTypeNames.isEmpty()) {
			for(StockTypeName stockTypeName : stockTypeNames) {
				if(stockTypeName.getStockTypeName().equals(request.getName())) {
					isDuplicate = true;
					break;
				}
			}
		}
		if(isDuplicate) {
			throw new BadRequestException("StockType Name: " + request.getName() + " already Exists for the Brand!");
		}
		
		if(request.getNameDisplay() == null || request.getNameDisplay().isEmpty()) {
			String nameDisplay = null;
			if(request.getName().length() > 15) {
				nameDisplay = request.getName().substring(0, 14);
			}
			request.setNameDisplay(nameDisplay);
		}
		
		StockType stockType = new StockType();

		stockType.setStockStatusId(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.StockTypeStatus, EnumConstMr.StockTypeStatus_Created));
		stockType.setCategoryId(categoryId);
		stockType.setProductPricingId(productPricingId);
		stockType.setItemConditionId(EnumCacheMr.getIndexOfItemCondition(EnumConstMr.ItemCondition_Fresh));
		stockType.setBrand(brand);
		
		stockType.setName(request.getName());
		stockType.setNameDisplay(request.getNameDisplay());
		stockType.setDescription(request.getDescription());
		stockType.setProperties(request.getProperties());
		stockType.setPics(request.getPics());
		stockType.setLink(request.getLink());
		
		stockType.setNoLots(DataUtil.ZERO_INTEGER);
		stockType.setNoPerSet(DataUtil.ZERO_INTEGER);
		stockType.setNoMinSets(DataUtil.ZERO_INTEGER);
		
		stockType.setTotalPurchaseAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setTotalSoldAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setTotalSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setTotalDamageAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setTotalPurchaseNo(DataUtil.ZERO_INTEGER);
		stockType.setTotalStockNo(DataUtil.ZERO_INTEGER);
		stockType.setTotalSoldNo(DataUtil.ZERO_INTEGER);
		stockType.setTotalSoldDiscountNo(DataUtil.ZERO_INTEGER);
		stockType.setTotalDamageNo(DataUtil.ZERO_INTEGER);
		stockType.setCurrentOrderedStockAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setCurrentDeliveredStockAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setCurrentToStockAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setCurrentCreatedStockAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setCurrentStockAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setCurrentStockDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setCurrentOrderedStockNo(DataUtil.ZERO_INTEGER);
		stockType.setCurrentDeliveredStockNo(DataUtil.ZERO_INTEGER);
		stockType.setCurrentToStockNo(DataUtil.ZERO_INTEGER);
		stockType.setCurrentCreatedStockNo(DataUtil.ZERO_INTEGER);
		stockType.setCurrentStockNo(DataUtil.ZERO_INTEGER);
		stockType.setCurrentStockDiscountNo(DataUtil.ZERO_INTEGER);
		stockType.setAvgMrItemSoldAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setFirstStockPriceAm(DataUtil.ZERO_BIG_DECIMAL);
		stockType.setLastStockPriceAm(DataUtil.ZERO_BIG_DECIMAL);
		
		stockType.setFirstLotTs(null);
		stockType.setLastLotTs(null);	
		stockType.setReturnCounter(DataUtil.ZERO_INTEGER);
		stockType.setPerformanceIndex(DataUtil.ZERO_FLOAT);
		stockType.setReturnIndex(DataUtil.ZERO_FLOAT);
		stockType.setSalesIndex(DataUtil.ZERO_FLOAT);
		stockType.setSales20PerDays(DataUtil.ZERO_FLOAT);
		stockType.setSales40PerDays(DataUtil.ZERO_FLOAT);
		stockType.setSales60PerDays(DataUtil.ZERO_FLOAT);
		stockType.setSales80PerDays(DataUtil.ZERO_FLOAT);
		stockType.setSales100PerDays(DataUtil.ZERO_FLOAT);
		
		daoFactory.getStockTypeDAO().persist(stockType);
		
		brand.setNoStockedTypes(brand.getNoStockedTypes() + 1);
		daoFactory.getBrandDAO().merge(brand);
		
		request.setStockTypeId(stockType.getStockTypeId());
		
    	return StockTypeM.buildStockType(stockType);
    }

    public List<BrandName> searchBrand(long manufactureAcNo, String brandShort) throws BadRequestException {
		
		if(brandShort == null || brandShort.isEmpty()) {
			throw new BadRequestException("Null or Empty Search String for Brand Name!");
		}
		if(brandShort.length() < 3) {
			throw new BadRequestException("Search String for Brand Name Less that 4 Char!");
		}
		if(!ConversionUtil.isValidGroupAcNo(manufactureAcNo)) {
			throw new BadRequestException("Invalid Manufacture Account Number!");
		}

		GProfile manufacture = daoFactory.getGProfileDAO().findById(manufactureAcNo);
		if(manufacture == null) {
			throw new BadRequestException("Invalid Manufacture Account Number: " + manufactureAcNo + " !");
		}
		
		String manufactureName = daoFactory.getGroupContactDAO().getNameOfGroup(EnumConst.Lang_English, manufactureAcNo);
		
		List<BrandName> brandNames = new ArrayList<BrandName>();
		
		List<Brand> brands = daoFactory.getBrandDAO().getBrandForManufacture(manufactureAcNo, brandShort);
		
		if(brands != null && !brands.isEmpty()) {
			for(Brand brand : brands) {
				brandNames.add(BrandName.buildBrandName(brand, manufactureName));
			}
		}
		
    	return brandNames;
    }

    public List<BrandName> searchBrand(String brandShort) throws BadRequestException {
		
		if(brandShort == null || brandShort.isEmpty()) {
			throw new BadRequestException("Null or Empty Search String for Brand Name!");
		}
		if(brandShort.length() < 3) {
			throw new BadRequestException("Search String for Brand Name Less that 3 Char!");
		}
		
		List<BrandName> brandNames = new ArrayList<BrandName>();
		
		List<Brand> brands = daoFactory.getBrandDAO().getBrandByName(brandShort);
		
		if(brands != null && !brands.isEmpty()) {
			long manufactureAcNo = 0;
			String manufactureName = null;
			Map<Long, String> manufNamesMap = new HashMap<Long, String>();
			
			for(Brand brand : brands) {
				
				manufactureAcNo = brand.getManufacture().getGAcNo();
				if(!ConversionUtil.isValidGroupAcNo(manufactureAcNo)) {
					continue;
				}
				
				if(manufNamesMap.containsKey(manufactureAcNo)) {
					manufactureName = manufNamesMap.get(manufactureAcNo);
				} else {
					manufactureName = daoFactory.getGroupContactDAO().getNameOfGroup(EnumConst.Lang_English, manufactureAcNo);

					if(manufactureName == null || manufactureName.isEmpty()) {
						continue;
					}
					manufNamesMap.put(manufactureAcNo, manufactureName);
				}
				
				brandNames.add(BrandName.buildBrandName(brand, manufactureName));
			}
		}
		
    	return brandNames;
    }

    public BrandM addBrand(BrandM request) throws BadRequestException {
		
		if(request == null) {
			throw new BadRequestException("Null Brand Request Object!");
		}
		if(!ConversionUtil.isValidGroupAcNo(request.getManufactureAcNo())) {
			throw new BadRequestException("Invalid Manufacture Account Number!");
		}
		GProfile manufacture = daoFactory.getGProfileDAO().findById(request.getManufactureAcNo());
		if(manufacture == null) {
			throw new BadRequestException("Invalid Manufacture Account Number: " + request.getManufactureAcNo() + " !");
		}
		if(request.getName() == null || request.getName().isEmpty()) {
			throw new BadRequestException("Null Or Empty Brand Name!");
		}
		
		List<BrandName> brandNames = searchBrand(request.getManufactureAcNo(), request.getName());
		boolean isDuplicate = false;
		if(brandNames != null && !brandNames.isEmpty()) {
			for(BrandName brandName : brandNames) {
				if(brandName.getBrandName().equals(request.getName())) {
					isDuplicate = true;
					break;
				}
			}
		}
		if(isDuplicate) {
			throw new BadRequestException("Brand Name: " + request.getName() + " already Exists for the Manufacture!");
		}
		
		if(request.getNameDisplay() == null || request.getNameDisplay().isEmpty()) {
			String nameDisplay = null;
			if(request.getName().length() > 15) {
				nameDisplay = request.getName().substring(0, 14);
			}
			request.setNameDisplay(nameDisplay);
		}
		
		Brand brand = new Brand();
		
		brand.setManufacture(manufacture);
		brand.setName(request.getName());
		brand.setNameDisplay(request.getNameDisplay());
		brand.setDescription(request.getDescription());
		brand.setProperties(request.getProperties());
		brand.setLink(request.getLink());
		
		brand.setNoStockedTypes(DataUtil.ZERO_INTEGER);
		brand.setNoLots(DataUtil.ZERO_INTEGER);
		brand.setNoPerSet(DataUtil.ZERO_INTEGER);
		brand.setNoMinSets(DataUtil.ZERO_INTEGER);
		
		brand.setTotalPurchaseAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setTotalStockAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setTotalSoldAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setTotalSoldDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setTotalDamageAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setTotalPurchaseNo(DataUtil.ZERO_INTEGER);
		brand.setTotalStockNo(DataUtil.ZERO_INTEGER);
		brand.setTotalSoldNo(DataUtil.ZERO_INTEGER);
		brand.setTotalSoldDiscountNo(DataUtil.ZERO_INTEGER);
		brand.setTotalDamageNo(DataUtil.ZERO_INTEGER);
		brand.setCurrentOrderedStockAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setCurrentDeliveredStockAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setCurrentToStockAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setCurrentCreatedStockAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setCurrentStockAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setCurrentStockDiscountAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setCurrentOrderedStockNo(DataUtil.ZERO_INTEGER);
		brand.setCurrentDeliveredStockNo(DataUtil.ZERO_INTEGER);
		brand.setCurrentToStockNo(DataUtil.ZERO_INTEGER);
		brand.setCurrentCreatedStockNo(DataUtil.ZERO_INTEGER);
		brand.setCurrentStockNo(DataUtil.ZERO_INTEGER);
		brand.setCurrentStockDiscountNo(DataUtil.ZERO_INTEGER);
		brand.setAvgMrItemSoldAm(DataUtil.ZERO_BIG_DECIMAL);
		
		brand.setFirstStockPriceAm(DataUtil.ZERO_BIG_DECIMAL);
		brand.setLastStockPriceAm(DataUtil.ZERO_BIG_DECIMAL);		
		brand.setFirstLotTs(null);
		brand.setLastLotTs(null);
		brand.setReturnCounter(DataUtil.ZERO_INTEGER);
		brand.setPerformanceIndex(DataUtil.ZERO_FLOAT);
		brand.setReturnIndex(DataUtil.ZERO_FLOAT);
		brand.setSalesIndex(DataUtil.ZERO_FLOAT);
		brand.setSales20PerDays(DataUtil.ZERO_FLOAT);
		brand.setSales40PerDays(DataUtil.ZERO_FLOAT);
		brand.setSales60PerDays(DataUtil.ZERO_FLOAT);
		brand.setSales80PerDays(DataUtil.ZERO_FLOAT);
		brand.setSales100PerDays(DataUtil.ZERO_FLOAT);
		
		daoFactory.getBrandDAO().persist(brand);
		
		request.setBrandId(brand.getBrandId());
		
    	return BrandM.buildBrand(brand);
    }

    public List<GroupName> searchManufacturer(String manufacturerShort) throws BadRequestException {
		
		if(manufacturerShort == null || manufacturerShort.isEmpty()) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}
		if(manufacturerShort.length() < 3) {
			throw new BadRequestException("Search String for Manufacturer Name Less that 4 Char!");
		}
		
		DistrictInfoCollector collector = new DistrictInfoCollector();
		collector.setGroupNameShort(manufacturerShort);
		
		daoFactory.getGroupContactDAO().loadVendorNames(collector);
		
		List<GroupName> groupNames = collector.getAllVendorsName();

		return groupNames;
    }
    
    public GroupREST addManufacturer(GroupREST request) throws BadRequestException {
    	return addManufacturer(request, false);
    }
    
    public GroupREST addManufacturer(GroupREST request, boolean oldData) throws BadRequestException {
		
		if(request == null) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}
		
		if(request.getGroupType() == null || request.getGroupType().isEmpty()) {
			request.setGroupType(EnumConstMr.GroupType_Manufacturer);
		}
		
		District district = daoFactory.getDistrictDAO().findById(EnumCache.getDistrictValue(EnumConst.District_Code_Vendor).getDistrictId());
		
		request = groupBO.addGroup(request, oldData, district);
		
    	return request;
    }

    public StockItemM getStockItem(long stockItemId) throws BadRequestException {
		
		if(stockItemId <= 0) {
			throw new BadRequestException("Invalid StockItemId : " + stockItemId + "!");
		}
		StockItem stockItem = daoFactory.getStockItemDAO().findById(stockItemId);
		
		if(stockItem == null) {
			throw new BadRequestException("Invalid StockItemId : " + stockItemId + "!");
		}
		
		return StockItemM.buildStockItemM(stockItem, daoFactory);
    }

    public ItemTagO getActiveItemTagByType() {
    	return ItemTagO.buildItemTagO(getActiveItemTag());
    }

    public ItemTagO getActiveItemTagByType(long stockTypeId) throws BadRequestException {
    	return ItemTagO.buildItemTagO(getActiveItemTagForStockType(stockTypeId));
    }

    public ItemTagO getActiveItemTagByBrand(String brandName) throws BadRequestException {
    	return ItemTagO.buildItemTagO(getActiveItemTagForBrand(brandName));
    }
    
    public List<ItemTagM> getActiveItemTag() {
		
    	List<ItemTag> itemTags = daoFactory.getItemTagDAO().getActiveItemTag();
    	
    	List<ItemTagM> itemTagMs = new ArrayList<ItemTagM>();
		
		if(itemTags != null && !itemTags.isEmpty()) {
			for(ItemTag itemTag: itemTags) {
				itemTagMs.add(ItemTagM.buildItemTag(itemTag));
			}
		}
		
		return itemTagMs;
    }

    public List<StockTypeM> getStockTypesForActiveItemTags() {
		
    	List<ItemTag> itemTags = daoFactory.getItemTagDAO().getActiveItemTag();
    	
		Set<Long> stockTypeIds = new HashSet<Long>();
    	List<StockTypeM> stockTypeMs = new ArrayList<StockTypeM>();
		
		if(itemTags != null && !itemTags.isEmpty()) {
			for(ItemTag itemTag: itemTags) {
				stockTypeIds.add(itemTag.getStockTypeId());
			}
			
			for(Long stockTypeId: stockTypeIds) {
				stockTypeMs.add(StockTypeM.buildStockType(daoFactory.getStockTypeDAO().findById(stockTypeId)));
			}
		}
		
		return stockTypeMs;
    }

    public List<ItemTagM> getActiveItemTagForStockType(long stockTypeId) throws BadRequestException {
		
		if(stockTypeId <= 0) {
			throw new BadRequestException("Invalid stockTypeId : " + stockTypeId + "!");
		}
		StockType stockType = daoFactory.getStockTypeDAO().findById(stockTypeId);
		
		if(stockType == null) {
			throw new BadRequestException("Invalid StockTypeId : " + stockTypeId + "!");
		}
		
    	List<ItemTag> itemTags = daoFactory.getItemTagDAO().getActiveItemTagForStockType(stockTypeId);
    	
    	List<ItemTagM> itemTagMs = new ArrayList<ItemTagM>();
		
		if(itemTags != null && !itemTags.isEmpty()) {
			for(ItemTag itemTag: itemTags) {
				itemTagMs.add(ItemTagM.buildItemTag(itemTag));
			}
		}
		
		return itemTagMs;
    }

    public List<ItemTagM> getActiveItemTagForBrand(String brandName) throws BadRequestException {
		
		if(brandName == null || brandName.isEmpty()) {
			throw new BadRequestException("Invalid Brand : " + brandName + "!");
		}
		
    	List<ItemTag> itemTags = daoFactory.getItemTagDAO().getActiveItemTagForBrand(brandName);
    	
    	List<ItemTagM> itemTagMs = new ArrayList<ItemTagM>();
		
		if(itemTags != null && !itemTags.isEmpty()) {
			for(ItemTag itemTag: itemTags) {
				itemTagMs.add(ItemTagM.buildItemTag(itemTag));
			}
		}
		
		return itemTagMs;
    }

    public void markItemTagsPrinted(List<ItemTagM> itemTagMs) throws BadRequestException {
    	
    	if(itemTagMs == null || itemTagMs.isEmpty()) {
			throw new BadRequestException("Null or Empty ItemTag List!");
    	}
    	
    	for(ItemTagM itemTagM: itemTagMs) {
    		
    		ItemTag itemTag = daoFactory.getItemTagDAO().findById(itemTagM.getItemTagId());
    		
        	if(itemTag == null) {
    			throw new BadRequestException("Invalid ItemTag with Id: " + itemTagM.getItemTagId() + " !");
        	}
        	
        	itemTag.setTagStatusId(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.TagStatus, EnumConstMr.TagStatus_Printed));
        	itemTag.setPrintedTs(DateUtil.getCurrentTimeDate());
        	
        	daoFactory.getItemTagDAO().merge(itemTag);
    	}	
    }

    public ItemTagM createItemTagForStockItem(long stockItemId) throws BadRequestException {
		
		if(stockItemId <= 0) {
			throw new BadRequestException("Invalid StockItemId : " + stockItemId + "!");
		}
		StockItem item = daoFactory.getStockItemDAO().findById(stockItemId);
		
		if(item == null) {
			throw new BadRequestException("Invalid StockItemId : " + stockItemId + "!");
		}
		
		return createItemTag(item);
    }

    public List<StockItemM> getStockItemsForType(long entryAcNo, long stockTypeId) throws BadRequestException {
		
		StockType stockType = daoFactory.getStockTypeDAO().findById(stockTypeId);
		if(stockType == null) {
			throw new BadRequestException("Invalid StockType Id" + stockTypeId + "!");
		}
		
		List<StockItem> items = daoFactory.getStockItemDAO().getActiveItemForOwnerForType(entryAcNo, stockTypeId);		
				
		List<StockItemM> stockItemMs = new ArrayList<StockItemM>(items.size());
		
		for(StockItem item: items) {			
	    	List<ItemTag> activeTags = daoFactory.getItemTagDAO().getActiveItemTagForStockItem(item.getStockItemId());
	    	if(activeTags == null || activeTags.isEmpty()) {
				stockItemMs.add(StockItemM.buildStockItemM(item, daoFactory));
	    	}
		}
		
		return stockItemMs;
    }

    public List<ItemTagM> createItemTagForStockItems(List<StockItemM> stockItemMs) throws BadRequestException {
		
		if(stockItemMs == null) {
			throw new BadRequestException("Invalid StockItem list!");
		}
		
		List<ItemTagM> resultList = new ArrayList<ItemTagM>(stockItemMs.size());
		
		for(StockItemM stockItemM: stockItemMs) {
			
			StockItem item = daoFactory.getStockItemDAO().findById(stockItemM.getStockItemId());		
			if(item != null) {
				resultList.add(createItemTag(item));
			}
		}
		
		return resultList;
    }
    
    protected ItemTagM createItemTag(StockItem item) {
    	
    	List<ItemTag> activeTags = daoFactory.getItemTagDAO().getActiveItemTagForStockItem(item.getStockItemId());
    	if(activeTags != null && !activeTags.isEmpty()) {
    		return ItemTagM.buildItemTag(activeTags.get(0));
    	}
    	
		StockType stockType = item.getStockType();
		Brand brand = stockType.getBrand();
		
		ItemTag itemTag = new ItemTag();
		
		itemTag.setStockItemId(item.getStockItemId());
		itemTag.setStockTypeId(stockType.getStockTypeId());
		itemTag.setTagStatusId(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.TagStatus, EnumConstMr.TagStatus_Created));
		itemTag.setStockTypeName(stockType.getNameDisplay());
		itemTag.setDesignNo(item.getDesignNo());
		itemTag.setBrandName(brand.getNameDisplay());
		itemTag.setMrpPriceAm(item.getMrpPrice());
		itemTag.setDisMrpPriceAm(item.getDisMrpPrice());
		itemTag.setDiscountPer(item.getDiscountPer());
		itemTag.setCreatedTs(DateUtil.getCurrentTimeDate());
		itemTag.setPrintedTs(null);
		
		daoFactory.getItemTagDAO().persist(itemTag);
		
		return ItemTagM.buildItemTag(itemTag);
    }
}
