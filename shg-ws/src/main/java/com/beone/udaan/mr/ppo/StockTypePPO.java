package com.beone.udaan.mr.ppo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.model.StockType;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

@Repository("stockTypePPO")
public class StockTypePPO extends IMrProcessing {

	private static final Logger log = LoggerFactory.getLogger(StockTypePPO.class);

	@Override
	public void processUpdateFormula(ProcessJobMr pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(StockTx.class)) {

			StockTx stockTx = (StockTx)pJob.getRelatedObject().get(StockTx.class);

			try {
				StockItem item = pJob.getDaoFactory().getStockItemDAO().findById(stockTx.getStockItemId());
				StockType stockType = pJob.getDaoFactory().getStockTypeDAO().findById(stockTx.getStockTypeId());

				int addDiscountSrockNo = 0;
				if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {		
					if(stockTx.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0 && stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
						addDiscountSrockNo = -1;
					} else if(stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0) {
						addDiscountSrockNo = 1;
					}
				}

				if(formulaMap.keySet().contains("no_lots")) {
					stockType.setNoLots(PPOAlgoUtil.compute(stockType.getNoLots(), formulaMap.get("no_lots"), 1));
				}

				if(formulaMap.keySet().contains("total_purchase_am")) {
					stockType.setTotalPurchaseAm(PPOAlgoUtil.compute(stockType.getTotalPurchaseAm(), formulaMap.get("total_purchase_am"), stockTx.getExtraAm()));
				}

				if(formulaMap.keySet().contains("total_stock_am")) {
					stockType.setTotalStockAm(PPOAlgoUtil.compute(stockType.getTotalStockAm(), formulaMap.get("total_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_sold_am")) {
					stockType.setTotalSoldAm(PPOAlgoUtil.compute(stockType.getTotalSoldAm(), formulaMap.get("total_sold_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_sold_discount_am")) {
					if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
						stockType.setTotalSoldDiscountAm(PPOAlgoUtil.compute(stockType.getTotalSoldDiscountAm(), formulaMap.get("total_sold_discount_am"), item.getDiscountAm()));
					}
				}

				if(formulaMap.keySet().contains("total_damage_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						stockType.setTotalDamageAm(PPOAlgoUtil.compute(stockType.getTotalDamageAm(), formulaMap.get("total_damage_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							stockType.setTotalDamageAm(PPOAlgoUtil.compute(stockType.getTotalDamageAm(), formulaMap.get("total_damage_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("total_purchase_no")) {
					stockType.setTotalPurchaseNo(PPOAlgoUtil.compute(stockType.getTotalPurchaseNo(), formulaMap.get("total_purchase_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_no")) {
					stockType.setTotalStockNo(PPOAlgoUtil.compute(stockType.getTotalStockNo(), formulaMap.get("total_stock_no"), 1));
					
					if(stockType.getCurrentOrderedStockNo() == 0 &&
							stockType.getCurrentDeliveredStockNo() == 0 &&
							stockType.getCurrentToStockNo() == 0 &&
							stockType.getCurrentCreatedStockNo() == 0 &&
							stockType.getCurrentStockNo() == 0 ) {
			    		stockType.setStockStatusId(EnumCacheMr.getStatusValue(EnumConstMr.StockTypeStatus, EnumConstMr.StockTypeStatus_Sold_Out).getStatusIndex());
					}
				}

				if(formulaMap.keySet().contains("total_sold_no")) {
					stockType.setTotalSoldNo(PPOAlgoUtil.compute(stockType.getTotalSoldNo(), formulaMap.get("total_sold_no"), 1));
				}

				if(formulaMap.keySet().contains("total_sold_discount_no")) {
					if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
						stockType.setTotalSoldDiscountNo(PPOAlgoUtil.compute(stockType.getTotalSoldDiscountNo(), formulaMap.get("total_sold_discount_no"), 1));
					}
				}

				if(formulaMap.keySet().contains("total_damage_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						stockType.setTotalDamageNo(PPOAlgoUtil.compute(stockType.getTotalDamageNo(), formulaMap.get("total_damage_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							stockType.setTotalDamageNo(PPOAlgoUtil.compute(stockType.getTotalDamageNo(), formulaMap.get("total_damage_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("current_ordered_stock_am")) {
					stockType.setCurrentOrderedStockAm(PPOAlgoUtil.compute(stockType.getCurrentOrderedStockAm(), formulaMap.get("current_ordered_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_delivered_stock_am")) {
					stockType.setCurrentDeliveredStockAm(PPOAlgoUtil.compute(stockType.getCurrentDeliveredStockAm(), formulaMap.get("current_delivered_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_to_stock_am")) {
					stockType.setCurrentToStockAm(PPOAlgoUtil.compute(stockType.getCurrentToStockAm(), formulaMap.get("current_to_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_created_stock_am")) {
					stockType.setCurrentCreatedStockAm(PPOAlgoUtil.compute(stockType.getCurrentCreatedStockAm(), formulaMap.get("current_created_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_stock_am")) {
					stockType.setCurrentStockAm(PPOAlgoUtil.compute(stockType.getCurrentStockAm(), formulaMap.get("current_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_stock_discount_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						stockType.setCurrentStockDiscountAm(PPOAlgoUtil.compute(stockType.getCurrentStockDiscountAm(), formulaMap.get("current_stock_discount_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							stockType.setCurrentStockDiscountAm(PPOAlgoUtil.compute(stockType.getCurrentStockDiscountAm(), formulaMap.get("current_stock_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("current_ordered_stock_no")) {
					stockType.setCurrentOrderedStockNo(PPOAlgoUtil.compute(stockType.getCurrentOrderedStockNo(), formulaMap.get("current_ordered_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_delivered_stock_no")) {
					stockType.setCurrentDeliveredStockNo(PPOAlgoUtil.compute(stockType.getCurrentDeliveredStockNo(), formulaMap.get("current_delivered_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_to_stock_no")) {
					stockType.setCurrentToStockNo(PPOAlgoUtil.compute(stockType.getCurrentToStockNo(), formulaMap.get("current_to_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_created_stock_no")) {
					stockType.setCurrentCreatedStockNo(PPOAlgoUtil.compute(stockType.getCurrentCreatedStockNo(), formulaMap.get("current_created_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_stock_no")) {
					stockType.setCurrentStockNo(PPOAlgoUtil.compute(stockType.getCurrentStockNo(), formulaMap.get("current_stock_no"), 1));
					if(stockType.getCurrentStockNo() > 0) {
						stockType.setStockStatusId(EnumCacheMr.getStatusValue(EnumConstMr.StockTypeStatus, EnumConstMr.StockTypeStatus_Stocked).getStatusIndex());
					}
				}

				if(formulaMap.keySet().contains("current_stock_discount_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						stockType.setCurrentStockDiscountNo(PPOAlgoUtil.compute(stockType.getCurrentStockDiscountNo(), formulaMap.get("current_stock_discount_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							stockType.setCurrentStockDiscountNo(PPOAlgoUtil.compute(stockType.getCurrentStockDiscountNo(), formulaMap.get("current_stock_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("first_stock_price_am")) {
					if(stockType.getFirstStockPriceAm() == null || (stockType.getFirstStockPriceAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0)) {
						stockType.setFirstStockPriceAm(PPOAlgoUtil.compute(stockType.getFirstStockPriceAm(), formulaMap.get("first_stock_price_am"), stockTx.getAmount()));
					}
				}

				if(formulaMap.keySet().contains("last_stock_price_am")) {
					stockType.setLastStockPriceAm(PPOAlgoUtil.compute(stockType.getLastStockPriceAm(), formulaMap.get("last_stock_price_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("avg_mr_item_sold_am")) {
					stockType.setAvgMrItemSoldAm(PPOAlgoUtil.computeAvg(stockType.getAvgMrItemSoldAm(), formulaMap.get("avg_mr_item_sold_am"), stockTx.getExtraAm(), stockType.getTotalSoldNo()));
				}

				if(formulaMap.keySet().contains("first_lot_ts")) {
					if(stockType.getFirstLotTs() == null) {
						stockType.setFirstLotTs(PPOAlgoUtil.compute(stockType.getFirstLotTs(), formulaMap.get("first_lot_ts"), DateUtil.getCurrentTimeDate()));
					}
				}

				if(formulaMap.keySet().contains("last_lot_ts")) {
					stockType.setLastLotTs(PPOAlgoUtil.compute(stockType.getLastLotTs(), formulaMap.get("last_lot_ts"), DateUtil.getCurrentTimeDate()));
				}

				if(formulaMap.keySet().contains("return_counter")) {
					stockType.setReturnCounter(PPOAlgoUtil.compute(stockType.getReturnCounter(), formulaMap.get("return_counter"), 1));
				}

				pJob.getDaoFactory().getStockTypeDAO().merge(stockType);

			} catch (Exception e) {
				log.error(e.toString() + "; for StockItemId: " + stockTx.getStockItemId() + "; for StockTx Id: " + stockTx.getStockTxId());
			}
		}
	}

}