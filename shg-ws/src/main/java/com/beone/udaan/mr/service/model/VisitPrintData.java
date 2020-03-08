package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DAOFactory;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.MrVisit;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;
import com.beone.udaan.mr.service.model.VisitPrintData.CurrentStock.StockTypeAg;
import com.beone.udaan.mr.service.model.VisitPrintData.PayTxAgInfo.PayTxInfo;
import com.beone.udaan.mr.service.model.VisitPrintData.StockTxAgInfo.StockTxInfo;

public class VisitPrintData {

	private String ownerAcNo;
	private String ownerName;
	private long fromDate;
	private long toDate;
	private List<VisitInfo> visitInfos;
	private PayTxAgInfo payTxs;
	private StockTxAgInfo soldTxs;
	private StockTxAgInfo returnTxs;
	private StockTxAgInfo givenTxs;
	private CurrentStock currentStock;

	public String getOwnerAcNo() {
		return ownerAcNo;
	}

	public void setOwnerAcNo(String ownerAcNo) {
		this.ownerAcNo = ownerAcNo;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public long getFromDate() {
		return fromDate;
	}

	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}

	public long getToDate() {
		return toDate;
	}

	public void setToDate(long toDate) {
		this.toDate = toDate;
	}

	public List<VisitInfo> getVisitInfos() {
		return visitInfos;
	}

	public void setVisitInfos(List<VisitInfo> visitInfos) {
		this.visitInfos = visitInfos;
	}

	public PayTxAgInfo getPayTxs() {
		return payTxs;
	}

	public void setPayTxs(PayTxAgInfo payTxs) {
		this.payTxs = payTxs;
	}

	public StockTxAgInfo getSoldTxs() {
		return soldTxs;
	}

	public void setSoldTxs(StockTxAgInfo soldTxs) {
		this.soldTxs = soldTxs;
	}

	public StockTxAgInfo getReturnTxs() {
		return returnTxs;
	}

	public void setReturnTxs(StockTxAgInfo returnTxs) {
		this.returnTxs = returnTxs;
	}

	public StockTxAgInfo getGivenTxs() {
		return givenTxs;
	}

	public void setGivenTxs(StockTxAgInfo givenTxs) {
		this.givenTxs = givenTxs;
	}

	public CurrentStock getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(CurrentStock currentStock) {
		this.currentStock = currentStock;
	}

	public static class StockTxAgInfo {
		private String stockTxType;
		private int noOfItems;
		private BigDecimal avgMrPrice;
		private BigDecimal avgMrpPrice;
		private BigDecimal total;
		private List<StockTxInfo> stockTxs;
		public StockTxAgInfo() {
			super();
			stockTxs = new ArrayList<StockTxInfo>();
		}
		public StockTxAgInfo(int no) {
			super();
			stockTxs = new ArrayList<StockTxInfo>(no);
		}
		public String getStockTxType() {
			return stockTxType;
		}
		public void setStockTxType(String stockTxType) {
			this.stockTxType = stockTxType;
		}
		public int getNoOfItems() {
			return noOfItems;
		}
		public void setNoOfItems(int noOfItems) {
			this.noOfItems = noOfItems;
		}
		public BigDecimal getAvgMrPrice() {
			return avgMrPrice;
		}
		public void setAvgMrPrice(BigDecimal avgMrPrice) {
			this.avgMrPrice = avgMrPrice;
		}
		public BigDecimal getAvgMrpPrice() {
			return avgMrpPrice;
		}
		public void setAvgMrpPrice(BigDecimal avgMrpPrice) {
			this.avgMrpPrice = avgMrpPrice;
		}
		public BigDecimal getTotal() {
			return total;
		}
		public void setTotal(BigDecimal total) {
			this.total = total;
		}
		public List<StockTxInfo> getStockTxs() {
			return stockTxs;
		}
		public void setStockTxs(List<StockTxInfo> stockTxs) {
			this.stockTxs = stockTxs;
		}
		public void addStockTx(StockTxInfo stockTx) {
			if(this.stockTxs == null) {
				this.stockTxs = new ArrayList<StockTxInfo>();
			}
			this.stockTxs.add(stockTx);
		}
		
		public static class StockTxInfo {
			private int no;
			private String itemName;
			private int noOfItems;
			private BigDecimal mrPrice;
			private BigDecimal mrpPrice;
			private BigDecimal mrSoldPrice;
			private BigDecimal total;
			public int getNo() {
				return no;
			}
			public void setNo(int no) {
				this.no = no;
			}
			public String getItemName() {
				return itemName;
			}
			public void setItemName(String itemName) {
				this.itemName = itemName;
			}
			public int getNoOfItems() {
				return noOfItems;
			}
			public void setNoOfItems(int noOfItems) {
				this.noOfItems = noOfItems;
			}
			public BigDecimal getMrPrice() {
				return mrPrice;
			}
			public void setMrPrice(BigDecimal mrPrice) {
				this.mrPrice = mrPrice;
			}
			public BigDecimal getMrpPrice() {
				return mrpPrice;
			}
			public void setMrpPrice(BigDecimal mrpPrice) {
				this.mrpPrice = mrpPrice;
			}
			public BigDecimal getMrSoldPrice() {
				return mrSoldPrice;
			}
			public void setMrSoldPrice(BigDecimal mrSoldPrice) {
				this.mrSoldPrice = mrSoldPrice;
			}
			public BigDecimal getTotal() {
				return total;
			}
			public void setTotal(BigDecimal total) {
				this.total = total;
			}		
		}
	}

	public static class PayTxAgInfo {
		private String stockTxType;
		private BigDecimal totalOutstanding;
		private BigDecimal totalPaid;
		private BigDecimal totalBalance;
		private List<PayTxInfo> payTxs;
		public PayTxAgInfo() {
			super();
			payTxs = new ArrayList<PayTxInfo>();
		}
		public PayTxAgInfo(int no) {
			super();
			payTxs = new ArrayList<PayTxInfo>(no);
		}
		public String getStockTxType() {
			return stockTxType;
		}
		public void setStockTxType(String stockTxType) {
			this.stockTxType = stockTxType;
		}
		public BigDecimal getTotalOutstanding() {
			return totalOutstanding;
		}
		public void setTotalOutstanding(BigDecimal totalOutstanding) {
			this.totalOutstanding = totalOutstanding;
		}
		public BigDecimal getTotalPaid() {
			return totalPaid;
		}
		public void setTotalPaid(BigDecimal totalPaid) {
			this.totalPaid = totalPaid;
		}
		public BigDecimal getTotalBalance() {
			return totalBalance;
		}
		public void setTotalBalance(BigDecimal totalBalance) {
			this.totalBalance = totalBalance;
		}
		public List<PayTxInfo> getPayTxs() {
			return payTxs;
		}
		public void setPayTxs(List<PayTxInfo> payTxs) {
			this.payTxs = payTxs;
		}
		public void addPayTx(PayTxInfo payTx) {
			if(this.payTxs == null) {
				this.payTxs = new ArrayList<PayTxInfo>();
			}
			this.payTxs.add(payTx);
		}
		
		public static class PayTxInfo {
			private int no;
			private String payName;
			private BigDecimal outstanding;
			private BigDecimal paid;
			private BigDecimal balance;
			public int getNo() {
				return no;
			}
			public void setNo(int no) {
				this.no = no;
			}
			public String getPayName() {
				return payName;
			}
			public void setPayName(String payName) {
				this.payName = payName;
			}
			public BigDecimal getOutstanding() {
				return outstanding;
			}
			public void setOutstanding(BigDecimal outstanding) {
				this.outstanding = outstanding;
			}
			public BigDecimal getPaid() {
				return paid;
			}
			public void setPaid(BigDecimal paid) {
				this.paid = paid;
			}
			public BigDecimal getBalance() {
				return balance;
			}
			public void setBalance(BigDecimal balance) {
				this.balance = balance;
			}		
		}
	}	

	public static class CurrentStock {
		private int noOfItems;
		private BigDecimal avgMrPrice;
		private BigDecimal avgMrpPrice;
		private BigDecimal totalMrPrice;
		private BigDecimal totalMrpPrice;
		private List<StockTypeAg> stockTypeAgs;
		public int getNoOfItems() {
			return noOfItems;
		}
		public void setNoOfItems(int noOfItems) {
			this.noOfItems = noOfItems;
		}
		public BigDecimal getAvgMrPrice() {
			return avgMrPrice;
		}
		public void setAvgMrPrice(BigDecimal avgMrPrice) {
			this.avgMrPrice = avgMrPrice;
		}
		public BigDecimal getAvgMrpPrice() {
			return avgMrpPrice;
		}
		public void setAvgMrpPrice(BigDecimal avgMrpPrice) {
			this.avgMrpPrice = avgMrpPrice;
		}
		public BigDecimal getTotalMrPrice() {
			return totalMrPrice;
		}
		public void setTotalMrPrice(BigDecimal totalMrPrice) {
			this.totalMrPrice = totalMrPrice;
		}
		public BigDecimal getTotalMrpPrice() {
			return totalMrpPrice;
		}
		public void setTotalMrpPrice(BigDecimal totalMrpPrice) {
			this.totalMrpPrice = totalMrpPrice;
		}
		public List<StockTypeAg> getStockTypeAgs() {
			return stockTypeAgs;
		}
		public void setStockTypeAgs(List<StockTypeAg> stockTypeAgs) {
			this.stockTypeAgs = stockTypeAgs;
		}
		public void addStockTypeAg(StockTypeAg stockTypeAg) {
			if(this.stockTypeAgs == null) {
				this.stockTypeAgs = new ArrayList<StockTypeAg>();
			}
			this.stockTypeAgs.add(stockTypeAg);
		}
		
		static public class StockTypeAg {
			private int no;
			private String stockType;
			private int noOfItems;
			private BigDecimal mrPrice;
			private BigDecimal mrpPrice;
			private BigDecimal cumMrPrice;
			private BigDecimal cumMrpPrice;
			public int getNo() {
				return no;
			}
			public void setNo(int no) {
				this.no = no;
			}
			public String getStockType() {
				return stockType;
			}
			public void setStockType(String stockType) {
				this.stockType = stockType;
			}
			public int getNoOfItems() {
				return noOfItems;
			}
			public void setNoOfItems(int noOfItems) {
				this.noOfItems = noOfItems;
			}
			public BigDecimal getMrPrice() {
				return mrPrice;
			}
			public void setMrPrice(BigDecimal mrPrice) {
				this.mrPrice = mrPrice;
			}
			public BigDecimal getMrpPrice() {
				return mrpPrice;
			}
			public void setMrpPrice(BigDecimal mrpPrice) {
				this.mrpPrice = mrpPrice;
			}
			public BigDecimal getCumMrPrice() {
				return cumMrPrice;
			}
			public void setCumMrPrice(BigDecimal cumMrPrice) {
				this.cumMrPrice = cumMrPrice;
			}
			public BigDecimal getCumMrpPrice() {
				return cumMrpPrice;
			}
			public void setCumMrpPrice(BigDecimal cumMrpPrice) {
				this.cumMrpPrice = cumMrpPrice;
			}
		}
	}

	public static class VisitInfo {
		private int no;
		private String authAcNo;
		private String authName;
		private long scheduledTs;
		private long startTs;
		private long endTs;
		
		public int getNo() {
			return no;
		}
		public void setNo(int no) {
			this.no = no;
		}
		public String getAuthAcNo() {
			return authAcNo;
		}
		public void setAuthAcNo(String authAcNo) {
			this.authAcNo = authAcNo;
		}
		public String getAuthName() {
			return authName;
		}
		public void setAuthName(String authName) {
			this.authName = authName;
		}
		public long getScheduledTs() {
			return scheduledTs;
		}
		public void setScheduledTs(long scheduledTs) {
			this.scheduledTs = scheduledTs;
		}
		public long getStartTs() {
			return startTs;
		}
		public void setStartTs(long startTs) {
			this.startTs = startTs;
		}
		public long getEndTs() {
			return endTs;
		}
		public void setEndTs(long endTs) {
			this.endTs = endTs;
		}
	}

	public static class ItemHolder {
		
		private HashMap<Long, StockItem> itemMap = new HashMap<Long, StockItem>();
		DAOFactory daoFactory;
		
		public ItemHolder(List<StockItem> stockItems, DAOFactory daoFactory) {
			this.daoFactory = daoFactory;
			
			for(StockItem stockItem: stockItems) {
				itemMap.put(stockItem.getStockItemId(), stockItem);
			}
		}
		
		public StockItem getStockItem(long stockItemId) {
			StockItem item = itemMap.get(stockItemId);
			
			if(item == null) {
				item = daoFactory.getStockItemDAO().findById(stockItemId);
				itemMap.put(item.getStockItemId(), item);
			}
			
			return item;
		}
	}
	
	public void setOwner(long ownerAcNo, DAOFactory daoFactory) {
		
		this.ownerAcNo = ConversionUtil.getDisplayMemberAcNo(ownerAcNo);
		this.ownerName = daoFactory.getMemberContactDAO().getNameOfMember(EnumConst.Lang_English, ownerAcNo);
	}
	
	public void setVisitInfo(List<MrVisit> mrVisits, DAOFactory daoFactory) {
		visitInfos = new ArrayList<VisitInfo>();
		payTxs = new PayTxAgInfo();

		BigDecimal openingOutstandingAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal soldPaidAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal soldPendingAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal closingOutstandingAm = DataUtil.ZERO_BIG_DECIMAL;
		
		BigDecimal openingInterestPenaltyAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal paidInterestPenaltyAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal closingInterestPenaltyAm = DataUtil.ZERO_BIG_DECIMAL;
		
		BigDecimal openingCollectedAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal paidCollectedAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal receivedCollectedAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal closingCollectedAm = DataUtil.ZERO_BIG_DECIMAL;
		
		BigDecimal openingRegistrationAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal paidRegistrationAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal closingRegistrationAm = DataUtil.ZERO_BIG_DECIMAL;
		
		BigDecimal openingDepositAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal paidDepositAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal returnedDepositAm = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal closingDepositAm = DataUtil.ZERO_BIG_DECIMAL;
		
		if(mrVisits != null && !mrVisits.isEmpty()) {
			int index = 1;
			for(MrVisit mrVisit: mrVisits) {
				
				if(mrVisit.getEndTs() == null || !ConversionUtil.isTimeValid(mrVisit.getEndTs().getTime())) {
					continue;
				}

				VisitInfo visitInfo = new VisitInfo();
				
				visitInfo.setNo(index);
				visitInfo.setAuthAcNo(ConversionUtil.getDisplayMemberAcNo(mrVisit.getAuthAcNo()));
				visitInfo.setAuthName(daoFactory.getMemberContactDAO().getNameOfMember(EnumConst.Lang_English, mrVisit.getAuthAcNo()));
				visitInfo.setScheduledTs(mrVisit.getScheduledTs().getTime());
				visitInfo.setStartTs(mrVisit.getStartTs().getTime());
				visitInfo.setEndTs(mrVisit.getEndTs().getTime());
				
				visitInfos.add(visitInfo);
				index++;
				
				{
					openingOutstandingAm = BDUtil.add(openingOutstandingAm, mrVisit.getOpeningOutstandingAm());
					soldPaidAm = BDUtil.add(soldPaidAm, mrVisit.getSoldPaidAm());
					soldPendingAm = BDUtil.add(soldPendingAm, mrVisit.getSoldPendingAm());
					closingOutstandingAm = BDUtil.add(closingOutstandingAm, mrVisit.getClosingOutstandingAm());
						
					openingInterestPenaltyAm = BDUtil.add(openingInterestPenaltyAm, mrVisit.getOpeningInterestPenaltyAm());
					paidInterestPenaltyAm = BDUtil.add(paidInterestPenaltyAm, mrVisit.getPaidInterestPenaltyAm());
					closingInterestPenaltyAm = BDUtil.add(closingInterestPenaltyAm, mrVisit.getClosingInterestPenaltyAm());
						
					openingCollectedAm = BDUtil.add(openingCollectedAm, mrVisit.getOpeningCollectedAm());
					paidCollectedAm = BDUtil.add(paidCollectedAm, mrVisit.getPaidCollectedAm());
					receivedCollectedAm = BDUtil.add(receivedCollectedAm, mrVisit.getReceivedCollectedAm());
					closingCollectedAm = BDUtil.add(closingCollectedAm, mrVisit.getClosingCollectedAm());
						
					openingRegistrationAm = BDUtil.add(openingRegistrationAm, mrVisit.getOpeningRegistrationAm());
					paidRegistrationAm = BDUtil.add(paidRegistrationAm, mrVisit.getPaidRegistrationAm());
					closingRegistrationAm = BDUtil.add(closingRegistrationAm, mrVisit.getClosingRegistrationAm());
						
					openingDepositAm = BDUtil.add(openingDepositAm, mrVisit.getOpeningDepositAm());
					paidDepositAm = BDUtil.add(paidDepositAm, mrVisit.getPaidDepositAm());
					returnedDepositAm = BDUtil.add(returnedDepositAm, mrVisit.getReturnedDepositAm());
					closingDepositAm = BDUtil.add(closingDepositAm, mrVisit.getClosingDepositAm());
				}				
			}
			
			int payNo = 1;
			
			if(openingOutstandingAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0 || closingOutstandingAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
				PayTxInfo payTxInfo1 = new PayTxInfo();
				
				payTxInfo1.setNo(payNo);
				payTxInfo1.setPayName("New Sold");
				payTxInfo1.setOutstanding(openingOutstandingAm);
				payTxInfo1.setPaid(BDUtil.add(soldPendingAm, soldPaidAm));
				payTxInfo1.setBalance(closingOutstandingAm);
				
				payTxs.addPayTx(payTxInfo1);
				payNo++;

				PayTxInfo payTxInfo2 = new PayTxInfo();
				
				payTxInfo2.setNo(payNo);
				payTxInfo2.setPayName(EnumConstMr.StockTxType_Sold_Paid);
				payTxInfo2.setOutstanding(openingOutstandingAm);
				payTxInfo2.setPaid(soldPaidAm);
				payTxInfo2.setBalance(closingOutstandingAm);
				
				payTxs.addPayTx(payTxInfo2);
				payTxs.setTotalOutstanding(BDUtil.add(payTxs.getTotalOutstanding(), openingOutstandingAm));
				payTxs.setTotalPaid(BDUtil.add(payTxs.getTotalPaid(), soldPaidAm));
				payTxs.setTotalBalance(BDUtil.add(payTxs.getTotalBalance(), closingOutstandingAm));
				payNo++;
			}
			
			if(openingInterestPenaltyAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0 || closingInterestPenaltyAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
				PayTxInfo payTxInfo = new PayTxInfo();
				
				payTxInfo.setNo(payNo);
				payTxInfo.setPayName(EnumConstMr.StockTxType_Interest_Penalty);
				payTxInfo.setOutstanding(openingInterestPenaltyAm);
				payTxInfo.setPaid(paidInterestPenaltyAm);
				payTxInfo.setBalance(closingInterestPenaltyAm);
				
				payTxs.addPayTx(payTxInfo);
				payTxs.setTotalOutstanding(BDUtil.add(payTxs.getTotalOutstanding(), openingInterestPenaltyAm));
				payTxs.setTotalPaid(BDUtil.add(payTxs.getTotalPaid(), paidInterestPenaltyAm));
				payTxs.setTotalBalance(BDUtil.add(payTxs.getTotalBalance(), closingInterestPenaltyAm));
				payNo++;
			}
			
			if(openingRegistrationAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0 || closingRegistrationAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
				PayTxInfo payTxInfo = new PayTxInfo();
				
				payTxInfo.setNo(payNo);
				payTxInfo.setPayName(EnumConstMr.StockTxType_Registration_Fee);
				payTxInfo.setOutstanding(openingRegistrationAm);
				payTxInfo.setPaid(paidRegistrationAm);
				payTxInfo.setBalance(closingRegistrationAm);
				
				payTxs.addPayTx(payTxInfo);
				payTxs.setTotalOutstanding(BDUtil.add(payTxs.getTotalOutstanding(), openingRegistrationAm));
				payTxs.setTotalPaid(BDUtil.add(payTxs.getTotalPaid(), paidRegistrationAm));
				payTxs.setTotalBalance(BDUtil.add(payTxs.getTotalBalance(), closingRegistrationAm));
				payNo++;
			}
			
			if(openingDepositAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0 || closingDepositAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
				PayTxInfo payTxInfo = new PayTxInfo();
				
				payTxInfo.setNo(payNo);
				payTxInfo.setPayName(EnumConstMr.StockTxType_Deposit);
				payTxInfo.setOutstanding(openingDepositAm);
				payTxInfo.setPaid(paidDepositAm);
				payTxInfo.setBalance(closingDepositAm);
				
				payTxs.addPayTx(payTxInfo);
				payTxs.setTotalOutstanding(BDUtil.add(payTxs.getTotalOutstanding(), openingDepositAm));
				payTxs.setTotalPaid(BDUtil.add(payTxs.getTotalPaid(), paidDepositAm));
				payTxs.setTotalBalance(BDUtil.add(payTxs.getTotalBalance(), closingDepositAm));
				payNo++;
			}
			
			if(openingCollectedAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0 || closingCollectedAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
				PayTxInfo payTxInfo = new PayTxInfo();
				
				payTxInfo.setNo(payNo);
				payTxInfo.setPayName(EnumConstMr.StockTxType_Collection_Paid);
				payTxInfo.setOutstanding(openingCollectedAm);
				payTxInfo.setPaid(paidCollectedAm);
				payTxInfo.setBalance(closingCollectedAm);
				
				payTxs.addPayTx(payTxInfo);
				payTxs.setTotalOutstanding(BDUtil.add(payTxs.getTotalOutstanding(), openingCollectedAm));
				payTxs.setTotalPaid(BDUtil.add(payTxs.getTotalPaid(), paidCollectedAm));
				payTxs.setTotalBalance(BDUtil.add(payTxs.getTotalBalance(), closingCollectedAm));
				payNo++;
			}
		}
	}
	
	public void setTxs(List<StockTx> stockTxs, List<StockItem> stockItems, DAOFactory daoFactory) {
		
		soldTxs = new StockTxAgInfo();
		returnTxs = new StockTxAgInfo();
		givenTxs = new StockTxAgInfo();
		currentStock = new CurrentStock();
		
		soldTxs.setStockTxType(EnumConstMr.StockTxType_Sold);
		returnTxs.setStockTxType(EnumConstMr.StockTxType_Returned);
		givenTxs.setStockTxType(EnumConstMr.StockTxType_Given);

		ItemHolder itemHolder = new ItemHolder(stockItems, daoFactory);
		
		LinkedHashMap<String, StockTxInfo> soldItemMap = new LinkedHashMap<String, StockTxInfo>();
		
		if(stockTxs != null && !stockTxs.isEmpty()) {
			int soldIndex = 1;
			int returnIndex = 1;
			int givenIndex = 1;
			
			for(StockTx stockTx: stockTxs) {
				
				StockItem item = itemHolder.getStockItem(stockTx.getStockItemId());
				
				switch(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId())) {
				
				case EnumConstMr.StockTxType_Sold: {
					
					if(!soldItemMap.containsKey(stockTx.getName())) {
						
						StockTxInfo stockTxInfo = new StockTxInfo();
						
						stockTxInfo.setNo(soldIndex);
						stockTxInfo.setItemName(stockTx.getName());
						stockTxInfo.setNoOfItems(1);
						stockTxInfo.setMrPrice(BDUtil.sub(item.getMrPrice(), item.getDiscountAm()));
						stockTxInfo.setMrpPrice(item.getDisMrpPrice());
						stockTxInfo.setMrSoldPrice(item.getSoldPrice());
						stockTxInfo.setTotal(stockTxInfo.getMrPrice());

						soldIndex++;
						soldTxs.addStockTx(stockTxInfo);
						
					} else {
						StockTxInfo stockTxInfo = soldItemMap.get(stockTx.getName());
						
						BigDecimal mrPrice = BDUtil.sub(item.getMrPrice(), item.getDiscountAm());
						
						if(mrPrice.compareTo(stockTxInfo.getMrPrice()) > 0) {
							stockTxInfo.setMrPrice(mrPrice);
							stockTxInfo.setMrpPrice(item.getDisMrpPrice());
							stockTxInfo.setMrSoldPrice(item.getSoldPrice());
						}
						
						stockTxInfo.setNoOfItems(stockTxInfo.getNoOfItems() + 1);
						stockTxInfo.setTotal(BDUtil.add(soldTxs.getTotal(), stockTxInfo.getMrPrice()));
					}
					
					soldTxs.setNoOfItems(soldTxs.getNoOfItems() + 1);
					soldTxs.setAvgMrPrice(BDUtil.add(soldTxs.getAvgMrPrice(), BDUtil.sub(item.getMrPrice(), item.getDiscountAm())));
					soldTxs.setAvgMrpPrice(BDUtil.add(soldTxs.getAvgMrpPrice(), item.getDisMrpPrice()));
					soldTxs.setTotal(BDUtil.add(soldTxs.getTotal(), item.getMrPrice()));
					break;
				}
				
				case EnumConstMr.StockTxType_Returned: {
					
					if(!soldItemMap.containsKey(stockTx.getName())) {
						
						StockTxInfo stockTxInfo = new StockTxInfo();
						
						stockTxInfo.setNo(returnIndex);
						stockTxInfo.setItemName(stockTx.getName());
						stockTxInfo.setNoOfItems(1);
						stockTxInfo.setMrPrice(BDUtil.sub(item.getMrPrice(), item.getDiscountAm()));
						stockTxInfo.setMrpPrice(item.getDisMrpPrice());
						stockTxInfo.setMrSoldPrice(item.getSoldPrice());
						stockTxInfo.setTotal(stockTxInfo.getMrPrice());

						returnIndex++;
						returnTxs.addStockTx(stockTxInfo);
						
					} else {
						StockTxInfo stockTxInfo = soldItemMap.get(stockTx.getName());
						
						BigDecimal mrPrice = BDUtil.sub(item.getMrPrice(), item.getDiscountAm());
						
						if(mrPrice.compareTo(stockTxInfo.getMrPrice()) > 0) {
							stockTxInfo.setMrPrice(mrPrice);
							stockTxInfo.setMrpPrice(item.getDisMrpPrice());
							stockTxInfo.setMrSoldPrice(item.getSoldPrice());
						}
						
						stockTxInfo.setTotal(BDUtil.add(returnTxs.getTotal(), stockTxInfo.getMrPrice()));
					}
					
					returnTxs.setNoOfItems(returnTxs.getNoOfItems() + 1);
					returnTxs.setAvgMrPrice(BDUtil.add(returnTxs.getAvgMrPrice(), BDUtil.sub(item.getMrPrice(), item.getDiscountAm())));
					returnTxs.setAvgMrpPrice(BDUtil.add(returnTxs.getAvgMrpPrice(), item.getDisMrpPrice()));
					returnTxs.setTotal(BDUtil.add(returnTxs.getTotal(), item.getMrPrice()));
					break;
				}
				
				case EnumConstMr.StockTxType_Given: {
					
					if(!soldItemMap.containsKey(stockTx.getName())) {
						
						StockTxInfo stockTxInfo = new StockTxInfo();
						
						stockTxInfo.setNo(givenIndex);
						stockTxInfo.setItemName(stockTx.getName());
						stockTxInfo.setNoOfItems(1);
						stockTxInfo.setMrPrice(BDUtil.sub(item.getMrPrice(), item.getDiscountAm()));
						stockTxInfo.setMrpPrice(item.getDisMrpPrice());
						stockTxInfo.setMrSoldPrice(item.getSoldPrice());
						stockTxInfo.setTotal(stockTxInfo.getMrPrice());

						givenIndex++;
						givenTxs.addStockTx(stockTxInfo);
						
					} else {
						StockTxInfo stockTxInfo = soldItemMap.get(stockTx.getName());
						
						BigDecimal mrPrice = BDUtil.sub(item.getMrPrice(), item.getDiscountAm());
						
						if(mrPrice.compareTo(stockTxInfo.getMrPrice()) > 0) {
							stockTxInfo.setMrPrice(mrPrice);
							stockTxInfo.setMrpPrice(item.getDisMrpPrice());
							stockTxInfo.setMrSoldPrice(item.getSoldPrice());
						}
						
						stockTxInfo.setTotal(BDUtil.add(givenTxs.getTotal(), stockTxInfo.getMrPrice()));
					}
					
					givenTxs.setNoOfItems(givenTxs.getNoOfItems() + 1);
					givenTxs.setAvgMrPrice(BDUtil.add(givenTxs.getAvgMrPrice(), BDUtil.sub(item.getMrPrice(), item.getDiscountAm())));
					givenTxs.setAvgMrpPrice(BDUtil.add(givenTxs.getAvgMrpPrice(), item.getDisMrpPrice()));
					givenTxs.setTotal(BDUtil.add(givenTxs.getTotal(), item.getMrPrice()));
					break;
				}
				
				}
			}
			
			soldTxs.setAvgMrPrice(BDUtil.divide(soldTxs.getAvgMrPrice(), soldTxs.getNoOfItems()));
			soldTxs.setAvgMrpPrice(BDUtil.divide(soldTxs.getAvgMrpPrice(), soldTxs.getNoOfItems()));
			
			returnTxs.setAvgMrPrice(BDUtil.divide(soldTxs.getAvgMrPrice(), soldTxs.getNoOfItems()));
			returnTxs.setAvgMrpPrice(BDUtil.divide(soldTxs.getAvgMrpPrice(), soldTxs.getNoOfItems()));
			
			givenTxs.setAvgMrPrice(BDUtil.divide(soldTxs.getAvgMrPrice(), soldTxs.getNoOfItems()));
			givenTxs.setAvgMrpPrice(BDUtil.divide(soldTxs.getAvgMrpPrice(), soldTxs.getNoOfItems()));
		}
		
		if(stockItems != null && !stockItems.isEmpty()) {
			
			HashMap<String, StockTypeAg> stockTypeAgMap = new HashMap<String, StockTypeAg>();
			int index = 1;
			
			for(StockItem stockItem: stockItems) {

				BigDecimal mrPrice = BDUtil.sub(stockItem.getMrPrice(), stockItem.getDiscountAm());
				
				if(!stockTypeAgMap.containsKey(stockItem.getName())) {

					StockTypeAg stockTypeAg = new StockTypeAg();

					stockTypeAg.setNo(index);
					stockTypeAg.setStockType(stockItem.getName());
					stockTypeAg.setNoOfItems(1);
					stockTypeAg.setMrPrice(mrPrice);
					stockTypeAg.setMrpPrice(stockItem.getDisMrpPrice());
					stockTypeAg.setCumMrPrice(mrPrice);
					stockTypeAg.setCumMrpPrice(stockItem.getDisMrpPrice());
					
					currentStock.addStockTypeAg(stockTypeAg);
					index++;
				} else {
					
					StockTypeAg stockTypeAg = stockTypeAgMap.get(stockItem.getName());

					if(mrPrice.compareTo(stockTypeAg.getMrPrice()) > 0) {
						stockTypeAg.setMrPrice(mrPrice);
						stockTypeAg.setMrpPrice(stockItem.getDisMrpPrice());
					}

					stockTypeAg.setNoOfItems(stockTypeAg.getNoOfItems() + 1);
					stockTypeAg.setCumMrPrice(stockItem.getMrPrice());
					stockTypeAg.setCumMrpPrice(stockItem.getDisMrpPrice());					
				}
				
				currentStock.setNoOfItems(currentStock.getNoOfItems() + 1);
				currentStock.setTotalMrPrice(BDUtil.add(currentStock.getTotalMrPrice(), mrPrice));
				currentStock.setTotalMrpPrice(BDUtil.add(currentStock.getTotalMrpPrice(), stockItem.getDisMrpPrice()));
			}
			
			currentStock.setAvgMrPrice(BDUtil.divide(currentStock.getTotalMrPrice(), currentStock.getNoOfItems()));
			currentStock.setAvgMrpPrice(BDUtil.divide(currentStock.getTotalMrpPrice(), currentStock.getNoOfItems()));
		}
	}
}
