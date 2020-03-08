package com.beone.udaan.mr.ppo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.Lot;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

@Repository("lotPPO")
public class LotPPO extends IMrProcessing {

	private static final Logger log = LoggerFactory.getLogger(LotPPO.class);

	@Override
	public void processUpdateFormula(ProcessJobMr pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(StockTx.class)) {

			StockTx stockTx = (StockTx)pJob.getRelatedObject().get(StockTx.class);

			try {
				Lot lot = pJob.getDaoFactory().getLotDAO().findById(stockTx.getLotId());
				StockItem item = pJob.getDaoFactory().getStockItemDAO().findById(stockTx.getStockItemId());

				if(formulaMap.keySet().contains("no_purchased")) {
					lot.setNoPurchased(PPOAlgoUtil.compute(lot.getNoPurchased(), formulaMap.get("no_purchased"), 1));
				}

				if(formulaMap.keySet().contains("no_delivered")) {
					lot.setNoDelivered(PPOAlgoUtil.compute(lot.getNoDelivered(), formulaMap.get("no_delivered"), 1));
				}

				if(formulaMap.keySet().contains("no_to_stock")) {
					lot.setNoToStock(PPOAlgoUtil.compute(lot.getNoToStock(), formulaMap.get("no_to_stock"), 1));
				}

				if(formulaMap.keySet().contains("no_created")) {
					lot.setNoCreated(PPOAlgoUtil.compute(lot.getNoCreated(), formulaMap.get("no_created"), 1));
				}

				if(formulaMap.keySet().contains("no_stocked")) {
					lot.setNoStocked(PPOAlgoUtil.compute(lot.getNoStocked(), formulaMap.get("no_stocked"), 1));
					if(lot.getNoPurchased() == lot.getNoStocked()) {
			        	lot.setLotStatusId(EnumCacheMr.getStatusValue(EnumConstMr.LotStatus, EnumConstMr.LotStatus_Stocked).getStatusIndex());
					}
				}

				if(formulaMap.keySet().contains("no_sold")) {
					lot.setNoSold(PPOAlgoUtil.compute(lot.getNoSold(), formulaMap.get("no_sold"), 1));
					if(lot.getNoPurchased() == lot.getNoSold()) {
			        	lot.setLotStatusId(EnumCacheMr.getStatusValue(EnumConstMr.LotStatus, EnumConstMr.LotStatus_Sold_Out).getStatusIndex());
					}
				}

				if(formulaMap.keySet().contains("no_damaged")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						int addDiscountSrockNo = 0;
						if(stockTx.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0 && stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							addDiscountSrockNo = -1;
						} else if(stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0) {
							addDiscountSrockNo = 1;
						}

						lot.setNoDamaged(PPOAlgoUtil.compute(lot.getNoDamaged(), formulaMap.get("no_damaged"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							lot.setNoDamaged(PPOAlgoUtil.compute(lot.getNoDamaged(), formulaMap.get("no_damaged"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("item_price_am")) {
					lot.setItemPriceAm(PPOAlgoUtil.compute(lot.getItemPriceAm(), formulaMap.get("item_price_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("lot_price_am")) {
					lot.setLotPriceAm(PPOAlgoUtil.compute(lot.getLotPriceAm(), formulaMap.get("lot_price_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("discount_am")) {
					lot.setDiscountAm(PPOAlgoUtil.compute(lot.getDiscountAm(), formulaMap.get("discount_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("vat_am")) {
					lot.setVatAm(PPOAlgoUtil.compute(lot.getVatAm(), formulaMap.get("vat_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("avg_mr_item_sold_am")) {
					lot.setAvgMrItemSoldAm(PPOAlgoUtil.computeAvg(lot.getAvgMrItemSoldAm(), formulaMap.get("avg_mr_item_sold_am"), stockTx.getExtraAm(), lot.getNoSold()));
				}

				if(formulaMap.keySet().contains("return_counter")) {
					lot.setReturnCounter(PPOAlgoUtil.compute(lot.getReturnCounter(), formulaMap.get("return_counter"), 1));
				}

				pJob.getDaoFactory().getLotDAO().merge(lot);

			} catch (Exception e) {
				log.error(e.toString() + "; for StockItemId: " + stockTx.getStockItemId() + "; for StockTx Id: " + stockTx.getStockTxId());
			}
		}
	}

}