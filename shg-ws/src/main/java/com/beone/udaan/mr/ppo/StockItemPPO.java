package com.beone.udaan.mr.ppo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;

@Repository("stockItemPPO")
public class StockItemPPO extends IMrProcessing {

	private static final Logger log = LoggerFactory.getLogger(StockItemPPO.class);

	@Override
	public void processUpdateFormula(ProcessJobMr pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(StockTx.class)) {

			StockTx stockTx = (StockTx)pJob.getRelatedObject().get(StockTx.class);

			try {
				StockItem stockItem = pJob.getDaoFactory().getStockItemDAO().findById(stockTx.getStockItemId());

				if(formulaMap.keySet().contains("item_status_id")) {
					stockItem.setItemStatusId(PPOAlgoUtil.compute(stockItem.getItemStatusId(), formulaMap.get("item_status_id"), 1));
				}

				if(formulaMap.keySet().contains("owner_ac_no")) {
					stockItem.setOwner(pJob.getDaoFactory().getPMAcDAO().findById(stockTx.getOwnerAcNo()));
				}

				if(formulaMap.keySet().contains("sold_price_am")) {
					stockItem.setSoldPrice(PPOAlgoUtil.compute(stockItem.getSoldPrice(), formulaMap.get("sold_price_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("mr_sold_price_am")) {
					stockItem.setMrSoldPrice(PPOAlgoUtil.compute(stockItem.getMrSoldPrice(), formulaMap.get("mr_sold_price_am"), stockTx.getExtraAm()));
				}

				if(formulaMap.keySet().contains("check_ts")) {
					stockItem.setCheckTs(PPOAlgoUtil.compute(stockItem.getCheckTs(), formulaMap.get("check_ts"), DateUtil.getCurrentTimeDate()));
				}

				if(formulaMap.keySet().contains("check_flag")) {
					stockItem.setCheckFlag(PPOAlgoUtil.compute(stockItem.getCheckFlag(), formulaMap.get("check_flag"), 1));
				}

				pJob.getDaoFactory().getStockItemDAO().merge(stockItem);

			} catch (Exception e) {
				log.error(e.toString() + "; for StockItemId: " + stockTx.getStockItemId() + "; for StockTx Id: " + stockTx.getStockTxId());
			}
		}
	}

}