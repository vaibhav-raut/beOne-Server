package com.beone.udaan.mr.persistence.support;

import java.util.ArrayList;
import java.util.List;

import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.udaan.mr.config.DBConstMr;
import com.beone.udaan.mr.config.EnumConstMr;

public class EnumUtilMr {

	public static String getStockedMrStock() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Stocked));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Mark_Sold));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Damaged));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	

	public static String getActiveMrStock() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Created));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Stocked));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Mark_Sold));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Damaged));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	

	public static String getOpenPInvoice() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.InvoiceStatus, EnumConstMr.InvoiceStatus_Draft));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.InvoiceStatus, EnumConstMr.InvoiceStatus_Quotation));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.InvoiceStatus, EnumConstMr.InvoiceStatus_Ordered));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.InvoiceStatus, EnumConstMr.InvoiceStatus_Processing));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.InvoiceStatus, EnumConstMr.InvoiceStatus_Shipped));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.InvoiceStatus, EnumConstMr.InvoiceStatus_Delivered));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.InvoiceStatus, EnumConstMr.InvoiceStatus_To_Stock));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	

	public static String getLotStatus(String invoiceStatus) {
		switch(invoiceStatus) {
			
		case EnumConstMr.InvoiceStatus_Quotation:
			return EnumConstMr.LotStatus_Quotation;
			
		case EnumConstMr.InvoiceStatus_Ordered:
			return EnumConstMr.LotStatus_Ordered;
			
		case EnumConstMr.InvoiceStatus_Processing:
			return EnumConstMr.LotStatus_Processing;
			
		case EnumConstMr.InvoiceStatus_Shipped:
			return EnumConstMr.LotStatus_Shipped;
			
		case EnumConstMr.InvoiceStatus_Delivered:
			return EnumConstMr.LotStatus_Delivered;
			
		case EnumConstMr.InvoiceStatus_To_Stock:
			return EnumConstMr.LotStatus_To_Stock;
			
		case EnumConstMr.InvoiceStatus_Cancelled:
			return EnumConstMr.LotStatus_Cancelled;	
			
		}
		return DataUtil.EMPTY_STRING;
	}
	
	public static int getItemStatusForStockTxType(String stockTxType, int curStockItemId) {

		int newStatus = DBConstMr.INVALID_DB_ID;
		
		switch(stockTxType) {

		case EnumConstMr.StockTxType_Created:
			break;

		case EnumConstMr.StockTxType_Stock_Verified:
			if(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Created) == curStockItemId) {
				newStatus = curStockItemId;
			}
			break;

		case EnumConstMr.StockTxType_Given:
			if(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Stocked) == curStockItemId) {
				newStatus = curStockItemId;
			}
			break;

		case EnumConstMr.StockTxType_Returned:
			if(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Stocked) == curStockItemId) {
				newStatus = curStockItemId;
			}
			break;

		case EnumConstMr.StockTxType_Sold:
			if(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Stocked) == curStockItemId) {
				newStatus = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Sold);
			}
			break;

		case EnumConstMr.StockTxType_Sold_Returned:
			if(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Sold) == curStockItemId) {
				newStatus = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Stocked);
			}
			break;

		case EnumConstMr.StockTxType_Discounted:
			if(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Stocked) == curStockItemId) {
				newStatus = EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Stocked);
			}
			break;

		}
		return newStatus;
	}

	public static boolean isStockTxTypeSold(String type) {		
		return (type.equals(EnumConstMr.StockTxType_Sold));
	}

	public static boolean isLotStatusToUpdate(String lotStatus) {		
		return (lotStatus.equals(EnumConstMr.LotStatus_Ordered) ||
				lotStatus.equals(EnumConstMr.LotStatus_Delivered) ||
				lotStatus.equals(EnumConstMr.LotStatus_To_Stock));
	}

	public static boolean isLotStatusOfOrderLevel(String lotStatus) {		
		return (lotStatus.equals(EnumConstMr.LotStatus_Ordered) ||
				lotStatus.equals(EnumConstMr.LotStatus_Processing) ||
				lotStatus.equals(EnumConstMr.LotStatus_Shipped));
	}

	public static boolean isVisitStatusToCreate(String visitStatus) {		
		return (visitStatus.equals(EnumConstMr.VisitStatus_Requested) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Expected) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Unscheduled));
	}

	public static boolean isVisitStatusToScheduled(String visitStatus) {		
		return (visitStatus.equals(EnumConstMr.VisitStatus_Requested) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Expected) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Not_Available) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Missed));
	}

	public static boolean isVisitStatusToStart(String visitStatus) {		
		return (visitStatus.equals(EnumConstMr.VisitStatus_Requested) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Expected) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Scheduled) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Unscheduled) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Not_Available) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Missed));
	}

	public static boolean isVisitStatusToEnd(String visitStatus) {		
		return (visitStatus.equals(EnumConstMr.VisitStatus_Ended) ||
				visitStatus.equals(EnumConstMr.VisitStatus_Auto_Closed));
	}

	public static String getActiveVisit() {
		
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Requested));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Expected));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Scheduled));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Unscheduled));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Started));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Not_Available));
		list.add(EnumCacheMr.getIndexOfStatusValue(EnumConstMr.VisitStatus, EnumConstMr.VisitStatus_Missed));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	

	public static boolean isLotStatusOrdered(String lotStatus) {		
		return (lotStatus.equals(EnumConstMr.LotStatus_Ordered) ||
				lotStatus.equals(EnumConstMr.LotStatus_Processing) ||
				lotStatus.equals(EnumConstMr.LotStatus_Shipped));
	}

	public static String getStockTxTypeIds() {
		
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Given));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Returned));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Sold));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	

	public static String getPayTxTypeIds() {
		
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Payment));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Sold_Paid));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Collection_Paid));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Registration_Fee));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Deposit));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Return_Deposit));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Cash_Deposit));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Cash_Advance));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Interest_Penalty));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Late_Fee));
		list.add(EnumCacheMr.getIndexOfStockTxType(EnumConstMr.StockTxType_Other_Fee));

		return ConversionUtil.convertArrayToInString(list.toArray());
	}	
}
