package com.beone.udaan.mr.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.bo.BaseBO;
import com.beone.shg.net.persistence.bo.MemberBO;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.webservice.rest.model.rest.GroupREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberContactREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.service.model.BrandM;
import com.beone.udaan.mr.service.model.LotM;
import com.beone.udaan.mr.service.model.MrAccount;
import com.beone.udaan.mr.service.model.PurchaseInvoice;
import com.beone.udaan.mr.service.model.StockTypeM;

@Component("mrManageDataBO")
public class MrManageDataBO extends BaseBO {

	@Autowired
	@Qualifier("inventoryBO")
	private InventoryBO inventoryBO;

	@Autowired
	@Qualifier("memberBO")
	private MemberBO memberBO;

	@Autowired
	@Qualifier("mrAccountBO")
	private MrAccountBO mrAccountBO;

	
	public List<String> getMrCSVTypes() {
		
		List<String> types = new ArrayList<String>();
		
		types.add(EnumConstMr.MEMBERS_CSV);
		types.add(EnumConstMr.MEMBERS_NO_AC_DATA_CSV);
		types.add(EnumConstMr.UPDATE_MR_AC_DATA_CSV);
		types.add(EnumConstMr.ADD_TO_MR_AC_DATA_CSV);
		types.add(EnumConstMr.MANUFACTURER_CSV);
		types.add(EnumConstMr.BRAND_CSV);
		types.add(EnumConstMr.STOCK_TYPE_CSV);
		types.add(EnumConstMr.PURCHASE_INVOICE_CSV);
		types.add(EnumConstMr.LOT_CSV);
		
		return types;
	}
	
	public List<Map<String,String>> addMrCSVForType(long entryByAcNo, String csvType, List<String[]> csvData) throws BadRequestException {
		
		switch(csvType) {
		
		case EnumConstMr.MANUFACTURER_CSV:
			return addManufacturerCSV(csvData);
			
		case EnumConstMr.BRAND_CSV:
			return addBrandCSV(csvData);

		case EnumConstMr.STOCK_TYPE_CSV:
			return addStockTypeCSV(csvData);

		case EnumConstMr.PURCHASE_INVOICE_CSV:
			return addPurchaseInvoiceCSV(entryByAcNo, csvData);

		case EnumConstMr.LOT_CSV:
			return addLotCSV(csvData);

		case EnumConstMr.MEMBERS_CSV:
			return addMembersCSV(csvData);

		case EnumConstMr.MEMBERS_NO_AC_DATA_CSV:
			return addMembersNoAcDataCSV(csvData);

		case EnumConstMr.UPDATE_MR_AC_DATA_CSV:
			return updateMrAcDataCSV(csvData);

		case EnumConstMr.ADD_TO_MR_AC_DATA_CSV:
			return addToMrAcDataCSV(csvData);
			
		default:
			throw new BadRequestException("Invalid CSV Type!");
		}
	}
	
	public List<Map<String,String>> addManufacturerCSV(List<String[]> rawGroups) throws BadRequestException {

		List<GroupREST> groups = new ArrayList<GroupREST>(rawGroups.size());
		
		CSVDataCollector csvData = new CSVDataCollector(rawGroups);

		if(!GroupREST.isManufacturerCSVValid(csvData)) {
			throw new BadRequestException("Invalid Manufacturer CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			groups.add(GroupREST.buildGroup(null, csvData, row));		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(groups.size());

		int rowIndex = 0;
		for(GroupREST group: groups) { 
			try {
				returnList.add(inventoryBO.addManufacturer(group, true).toStringInfo());
			} catch (Exception e) {
				throw new BadRequestException("Row: " + rowIndex + ", Error: " + e.getMessage() + " - caused for - " + group.toStringInfo());				
			}
			rowIndex++;
		}

		return returnList;
	}
	
	public List<Map<String,String>> addBrandCSV(List<String[]> rawBrands) throws BadRequestException {
	
		List<BrandM> brands = new ArrayList<BrandM>(rawBrands.size());
		
		CSVDataCollector csvData = new CSVDataCollector(rawBrands);

		if(!BrandM.isBrandCSVValid(csvData)) {
			throw new BadRequestException("Invalid Brand CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			BrandM brand = BrandM.buildBrand(csvData, row);
			
			if(brand == null) {
				continue;
			}
			
			brands.add(brand);		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(brands.size());

		int rowIndex = 0;
		for(BrandM brand: brands) { 
			try {
				returnList.add(inventoryBO.addBrand(brand).toStringInfo());
			} catch (Exception e) {
				throw new BadRequestException("Row: " + rowIndex + ", Error: " + e.getMessage() + " - caused for - " + brand.toStringInfo());				
			}
			rowIndex++;
		}

		return returnList;
	}
	
	public List<Map<String,String>> addStockTypeCSV(List<String[]> rawStockTypes) throws BadRequestException {
	
		List<StockTypeM> stockTypes = new ArrayList<StockTypeM>(rawStockTypes.size());
		
		CSVDataCollector csvData = new CSVDataCollector(rawStockTypes);

		if(!StockTypeM.isStockTypeCSVValid(csvData)) {
			throw new BadRequestException("Invalid StockType CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			StockTypeM stockType = StockTypeM.buildStockType(csvData, row);
			
			if(stockType == null) {
				continue;
			}
			
			stockTypes.add(stockType);		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(stockTypes.size());

		int rowIndex = 0;
		for(StockTypeM stockType: stockTypes) { 
			try {
				returnList.add(inventoryBO.addStockType(stockType).toStringInfo());
			} catch (Exception e) {
				throw new BadRequestException("Row: " + rowIndex + ", Error: " + e.getMessage() + " - caused for - " + stockType.toStringInfo());				
			}
			rowIndex++;
		}

		return returnList;
	}
	
	public List<Map<String,String>> addPurchaseInvoiceCSV(long entryByAcNo, List<String[]> rawPurchaseInvoices) throws BadRequestException {
	
		List<PurchaseInvoice> purchaseInvoices = new ArrayList<PurchaseInvoice>(rawPurchaseInvoices.size());
		
		CSVDataCollector csvData = new CSVDataCollector(rawPurchaseInvoices);

		if(!PurchaseInvoice.isPurchaseInvoiceCSVValid(csvData)) {
			throw new BadRequestException("Invalid PurchaseInvoice CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			PurchaseInvoice purchaseInvoice = PurchaseInvoice.buildPurchaseInvoice(entryByAcNo, csvData, row);
			
			if(purchaseInvoice == null) {
				continue;
			}
			
			purchaseInvoices.add(purchaseInvoice);		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(purchaseInvoices.size());

		int rowIndex = 0;
		for(PurchaseInvoice purchaseInvoice: purchaseInvoices) { 
			try {
				returnList.add(inventoryBO.addPurchaseInvoice(purchaseInvoice).toStringInfo());
			} catch (Exception e) {
				throw new BadRequestException("Row: " + rowIndex + ", Error: " + e.getMessage() + " - caused for - " + purchaseInvoice.toStringInfo());				
			}
			rowIndex++;
		}

		return returnList;
	}
	
	public List<Map<String,String>> addLotCSV(List<String[]> rawLots) throws BadRequestException {
	
		List<LotM> lots = new ArrayList<LotM>(rawLots.size());
		
		CSVDataCollector csvData = new CSVDataCollector(rawLots);

		if(!LotM.isLotCSVValid(csvData)) {
			throw new BadRequestException("Invalid Lot CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			LotM lot = LotM.buildLot(csvData, row);
			
			if(lot == null) {
				continue;
			}
			
			lots.add(lot);		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(lots.size());

		int rowIndex = 0;
		for(LotM lot: lots) { 
			try {
				returnList.add(inventoryBO.addLot(lot).toStringInfo());
			} catch (Exception e) {
				throw new BadRequestException("Row: " + rowIndex + ", Error: " + e.getMessage() + " - caused for - " + lot.toStringInfo());				
			}
			rowIndex++;
		}

		return returnList;
	}

	public List<Map<String,String>> addMembersCSV(List<String[]> rawMembers) throws BadRequestException {

		List<MrAccount> mrAccounts = new ArrayList<MrAccount>(rawMembers.size());

		CSVDataCollector csvData = new CSVDataCollector(rawMembers);

		if(!MrAccount.isMemberCSVValid(csvData)) {
			throw new BadRequestException("Invalid Member CSV Data!");
		}

		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			mrAccounts.add(MrAccount.buildMrAccount(csvData, row));		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(mrAccounts.size());

		Map<Long, GroupInfoCollector> groupsNameMap = new HashMap<Long, GroupInfoCollector>();
		
		int rowIndex = 0;
		for(MrAccount mrAccount: mrAccounts) {
			
			if(!groupsNameMap.containsKey(mrAccount.getMemberInfo().getParentGroupAcNo())) {
				GroupInfoCollector memberNameCollector = new GroupInfoCollector();
				memberNameCollector.setGroupAcNo(mrAccount.getMemberInfo().getParentGroupAcNo());
				daoFactory.getMemberContactDAO().loadMemberLiveNonTilelFullNamesForGroup(EnumConst.Lang_English, memberNameCollector);
				
				groupsNameMap.put(mrAccount.getMemberInfo().getParentGroupAcNo(), memberNameCollector);
			}
		}

		for(MrAccount mrAccount: mrAccounts) {
			try {				
				boolean newAdded = true;
				// Check for Duplicate Member Names in Group
				for(MemberContactREST contactREST : mrAccount.getMemberInfo().getContacts()) {
					if(contactREST.getLang().equals(EnumConst.Lang_English)) {
						String name = contactREST.getFirstName() + " " + contactREST.getMiddleName() + " " + contactREST.getLastName();
						if(groupsNameMap.get(mrAccount.getMemberInfo().getParentGroupAcNo()).isNamePresent(name)) {
							newAdded = false;
							long memberAcNo = groupsNameMap.get(mrAccount.getMemberInfo().getParentGroupAcNo()).getMemberAc(name);
							mrAccount.setMemberAcNo(memberAcNo);
							mrAccount.getMemberInfo().setMemberAcNo(memberAcNo);
						}
						break;
					}
				}

				if(newAdded) {
					// Add Member Account
					returnList.add(memberBO.addMember(mrAccount.getMemberInfo(), true).toStringInfo());
					mrAccount.setMemberAcNo(mrAccount.getMemberInfo().getMemberAcNo());
					
					// Add MR Account
					mrAccountBO.addUpdatePMAccount(mrAccount);
				} else {
					// Edit MR Account
					returnList.add(mrAccountBO.addUpdatePMAccount(mrAccount).toStringInfo());
				}
				
			} catch (Exception e) {
				throw new BadRequestException("Row: " + rowIndex + ", Error: " + e.getMessage() + " - caused for - " + mrAccount.getMemberInfo().toStringInfo());				
			}
			rowIndex++;
		}

		return returnList;
	}

	public List<Map<String,String>> addMembersNoAcDataCSV(List<String[]> rawMembers) throws BadRequestException {

		List<MrAccount> mrAccounts = new ArrayList<MrAccount>(rawMembers.size());

		CSVDataCollector csvData = new CSVDataCollector(rawMembers);

		if(!MrAccount.isMemberCSVValid(csvData)) {
			throw new BadRequestException("Invalid Member CSV Data!");
		}

		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			mrAccounts.add(MrAccount.buildMrAccount(csvData, row));		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(mrAccounts.size());

		Map<Long, GroupInfoCollector> groupsNameMap = new HashMap<Long, GroupInfoCollector>();
		
		int rowIndex = 0;
		for(MrAccount mrAccount: mrAccounts) {
			
			if(!groupsNameMap.containsKey(mrAccount.getMemberInfo().getParentGroupAcNo())) {
				GroupInfoCollector memberNameCollector = new GroupInfoCollector();
				memberNameCollector.setGroupAcNo(mrAccount.getMemberInfo().getParentGroupAcNo());
				daoFactory.getMemberContactDAO().loadMemberLiveNonTilelFullNamesForGroup(EnumConst.Lang_English, memberNameCollector);
				
				groupsNameMap.put(mrAccount.getMemberInfo().getParentGroupAcNo(), memberNameCollector);
			}
		}

		for(MrAccount mrAccount: mrAccounts) {
			try {				
				boolean newAdded = true;
				// Check for Duplicate Member Names in Group
				for(MemberContactREST contactREST : mrAccount.getMemberInfo().getContacts()) {
					if(contactREST.getLang().equals(EnumConst.Lang_English)) {
						String name = contactREST.getFirstName() + " " + contactREST.getMiddleName() + " " + contactREST.getLastName();
						if(groupsNameMap.get(mrAccount.getMemberInfo().getParentGroupAcNo()).isNamePresent(name)) {
							newAdded = false;
							long memberAcNo = groupsNameMap.get(mrAccount.getMemberInfo().getParentGroupAcNo()).getMemberAc(name);
							mrAccount.setMemberAcNo(memberAcNo);
							mrAccount.getMemberInfo().setMemberAcNo(memberAcNo);
						}
						break;
					}
				}

				if(newAdded) {
					// Add Member Account
					returnList.add(memberBO.addMember(mrAccount.getMemberInfo(), true).toStringInfo());
				}
				
			} catch (Exception e) {
				throw new BadRequestException("Row: " + rowIndex + ", Error: " + e.getMessage() + " - caused for - " + mrAccount.getMemberInfo().toStringInfo());				
			}
			rowIndex++;
		}

		return returnList;
	}

	public List<Map<String,String>> updateMrAcDataCSV(List<String[]> rawMembers) throws BadRequestException {

		List<MrAccount> mrAccounts = new ArrayList<MrAccount>(rawMembers.size());

		CSVDataCollector csvData = new CSVDataCollector(rawMembers);

		if(!MrAccount.isMemberCSVValid(csvData)) {
			throw new BadRequestException("Invalid Member CSV Data!");
		}

		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			mrAccounts.add(MrAccount.buildMrAccount(csvData, row));		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(mrAccounts.size());
		
		int rowIndex = 0;

		for(MrAccount mrAccount: mrAccounts) {
			try {
				
				if(ConversionUtil.isValidMemberAcNo(mrAccount.getMemberAcNo())) {
					// Add MR Account
					mrAccountBO.addUpdatePMAccount(mrAccount);
				}
				
			} catch (Exception e) {
				throw new BadRequestException("Row: " + rowIndex + ", Error: " + e.getMessage() + " - caused for - " + mrAccount.getMemberInfo().toStringInfo());				
			}
			rowIndex++;
		}

		return returnList;
	}

	public List<Map<String,String>> addToMrAcDataCSV(List<String[]> rawMembers) throws BadRequestException {

		List<MrAccount> mrAccounts = new ArrayList<MrAccount>(rawMembers.size());

		CSVDataCollector csvData = new CSVDataCollector(rawMembers);

		if(!MrAccount.isMemberCSVValid(csvData)) {
			throw new BadRequestException("Invalid Member CSV Data!");
		}

		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			mrAccounts.add(MrAccount.buildMrAccount(csvData, row));		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(mrAccounts.size());
		
		int rowIndex = 0;

		for(MrAccount mrAccount: mrAccounts) {
			try {
				
				if(ConversionUtil.isValidMemberAcNo(mrAccount.getMemberAcNo())) {
					// Add MR Account
					mrAccountBO.addUpdatePMAccount(mrAccount);
				}
				
			} catch (Exception e) {
				throw new BadRequestException("Row: " + rowIndex + ", Error: " + e.getMessage() + " - caused for - " + mrAccount.getMemberInfo().toStringInfo());				
			}
			rowIndex++;
		}

		return returnList;
	}
  
}
