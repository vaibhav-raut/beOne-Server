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
import com.beone.udaan.mr.persistence.model.Brand;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

@Repository("brandPPO")
public class BrandPPO extends IMrProcessing {

	private static final Logger log = LoggerFactory.getLogger(BrandPPO.class);

	@Override
	public void processUpdateFormula(ProcessJobMr pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(StockTx.class)) {

			StockTx stockTx = (StockTx)pJob.getRelatedObject().get(StockTx.class);

			try {
				StockItem item = pJob.getDaoFactory().getStockItemDAO().findById(stockTx.getStockItemId());
				Brand brand = pJob.getDaoFactory().getBrandDAO().findById(stockTx.getBrandId());

				int addDiscountSrockNo = 0;
				if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {		
					if(stockTx.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0 && stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
						addDiscountSrockNo = -1;
					} else if(stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0) {
						addDiscountSrockNo = 1;
					}
				}

				if(formulaMap.keySet().contains("no_stocked_types")) {
					brand.setNoStockedTypes(PPOAlgoUtil.compute(brand.getNoStockedTypes(), formulaMap.get("no_stocked_types"), 1));
				}

				if(formulaMap.keySet().contains("no_lots")) {
					brand.setNoLots(PPOAlgoUtil.compute(brand.getNoLots(), formulaMap.get("no_lots"), 1));
				}

				if(formulaMap.keySet().contains("no_per_set")) {
					brand.setNoPerSet(PPOAlgoUtil.compute(brand.getNoPerSet(), formulaMap.get("no_per_set"), 1));
				}

				if(formulaMap.keySet().contains("no_min_sets")) {
					brand.setNoMinSets(PPOAlgoUtil.compute(brand.getNoMinSets(), formulaMap.get("no_min_sets"), 1));
				}

				if(formulaMap.keySet().contains("total_purchase_am")) {
					brand.setTotalPurchaseAm(PPOAlgoUtil.compute(brand.getTotalPurchaseAm(), formulaMap.get("total_purchase_am"), stockTx.getExtraAm()));
				}

				if(formulaMap.keySet().contains("total_stock_am")) {
					brand.setTotalStockAm(PPOAlgoUtil.compute(brand.getTotalStockAm(), formulaMap.get("total_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_sold_am")) {
					brand.setTotalSoldAm(PPOAlgoUtil.compute(brand.getTotalSoldAm(), formulaMap.get("total_sold_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_sold_discount_am")) {
					if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
						brand.setTotalSoldDiscountAm(PPOAlgoUtil.compute(brand.getTotalSoldDiscountAm(), formulaMap.get("total_sold_discount_am"), item.getDiscountAm()));
					}
				}

				if(formulaMap.keySet().contains("total_damage_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						brand.setTotalDamageAm(PPOAlgoUtil.compute(brand.getTotalDamageAm(), formulaMap.get("total_damage_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							brand.setTotalDamageAm(PPOAlgoUtil.compute(brand.getTotalDamageAm(), formulaMap.get("total_damage_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("total_purchase_no")) {
					brand.setTotalPurchaseNo(PPOAlgoUtil.compute(brand.getTotalPurchaseNo(), formulaMap.get("total_purchase_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_no")) {
					brand.setTotalStockNo(PPOAlgoUtil.compute(brand.getTotalStockNo(), formulaMap.get("total_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("total_sold_no")) {
					brand.setTotalSoldNo(PPOAlgoUtil.compute(brand.getTotalSoldNo(), formulaMap.get("total_sold_no"), 1));
				}

				if(formulaMap.keySet().contains("total_sold_discount_no")) {
					if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
						brand.setTotalSoldDiscountNo(PPOAlgoUtil.compute(brand.getTotalSoldDiscountNo(), formulaMap.get("total_sold_discount_no"), 1));
					}
				}

				if(formulaMap.keySet().contains("total_damage_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						brand.setTotalDamageNo(PPOAlgoUtil.compute(brand.getTotalDamageNo(), formulaMap.get("total_damage_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							brand.setTotalDamageNo(PPOAlgoUtil.compute(brand.getTotalDamageNo(), formulaMap.get("total_damage_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("current_ordered_stock_am")) {
					brand.setCurrentOrderedStockAm(PPOAlgoUtil.compute(brand.getCurrentOrderedStockAm(), formulaMap.get("current_ordered_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_delivered_stock_am")) {
					brand.setCurrentDeliveredStockAm(PPOAlgoUtil.compute(brand.getCurrentDeliveredStockAm(), formulaMap.get("current_delivered_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_to_stock_am")) {
					brand.setCurrentToStockAm(PPOAlgoUtil.compute(brand.getCurrentToStockAm(), formulaMap.get("current_to_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_created_stock_am")) {
					brand.setCurrentCreatedStockAm(PPOAlgoUtil.compute(brand.getCurrentCreatedStockAm(), formulaMap.get("current_created_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_stock_am")) {
					brand.setCurrentStockAm(PPOAlgoUtil.compute(brand.getCurrentStockAm(), formulaMap.get("current_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_stock_discount_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						brand.setCurrentStockDiscountAm(PPOAlgoUtil.compute(brand.getCurrentStockDiscountAm(), formulaMap.get("current_stock_discount_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							brand.setCurrentStockDiscountAm(PPOAlgoUtil.compute(brand.getCurrentStockDiscountAm(), formulaMap.get("current_stock_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("current_ordered_stock_no")) {
					brand.setCurrentOrderedStockNo(PPOAlgoUtil.compute(brand.getCurrentOrderedStockNo(), formulaMap.get("current_ordered_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_delivered_stock_no")) {
					brand.setCurrentDeliveredStockNo(PPOAlgoUtil.compute(brand.getCurrentDeliveredStockNo(), formulaMap.get("current_delivered_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_to_stock_no")) {
					brand.setCurrentToStockNo(PPOAlgoUtil.compute(brand.getCurrentToStockNo(), formulaMap.get("current_to_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_created_stock_no")) {
					brand.setCurrentCreatedStockNo(PPOAlgoUtil.compute(brand.getCurrentCreatedStockNo(), formulaMap.get("current_created_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_stock_no")) {
					brand.setCurrentStockNo(PPOAlgoUtil.compute(brand.getCurrentStockNo(), formulaMap.get("current_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_stock_discount_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						brand.setCurrentStockDiscountNo(PPOAlgoUtil.compute(brand.getCurrentStockDiscountNo(), formulaMap.get("current_stock_discount_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							brand.setCurrentStockDiscountNo(PPOAlgoUtil.compute(brand.getCurrentStockDiscountNo(), formulaMap.get("current_stock_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("first_stock_price_am")) {
					if(brand.getFirstStockPriceAm() == null || (brand.getFirstStockPriceAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0)) {
						brand.setFirstStockPriceAm(PPOAlgoUtil.compute(brand.getFirstStockPriceAm(), formulaMap.get("first_stock_price_am"), stockTx.getAmount()));
					}
				}

				if(formulaMap.keySet().contains("last_stock_price_am")) {
					brand.setLastStockPriceAm(PPOAlgoUtil.compute(brand.getLastStockPriceAm(), formulaMap.get("last_stock_price_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("first_lot_ts")) {
					if(brand.getFirstLotTs() == null) {
						brand.setFirstLotTs(PPOAlgoUtil.compute(brand.getFirstLotTs(), formulaMap.get("first_lot_ts"), DateUtil.getCurrentTimeDate()));
					}
				}

				if(formulaMap.keySet().contains("last_lot_ts")) {
					brand.setLastLotTs(PPOAlgoUtil.compute(brand.getLastLotTs(), formulaMap.get("last_lot_ts"), DateUtil.getCurrentTimeDate()));
				}

				if(formulaMap.keySet().contains("return_counter")) {
					brand.setReturnCounter(PPOAlgoUtil.compute(brand.getReturnCounter(), formulaMap.get("return_counter"), 1));
				}

				pJob.getDaoFactory().getBrandDAO().merge(brand);

			} catch (Exception e) {
				log.error(e.toString() + "; for StockItemId: " + stockTx.getStockItemId() + "; for StockTx Id: " + stockTx.getStockTxId());
			}
		}
	}

}