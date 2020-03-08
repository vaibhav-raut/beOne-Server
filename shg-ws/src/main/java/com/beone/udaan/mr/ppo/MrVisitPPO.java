package com.beone.udaan.mr.ppo;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.udaan.mr.persistence.model.MrVisit;
import com.beone.udaan.mr.persistence.model.PMAc;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;

@Repository("mrVisitPPO")
public class MrVisitPPO extends IMrProcessing {

	private static final Logger log = LoggerFactory.getLogger(MrVisitPPO.class);

	@Override
	public void processUpdateFormula(ProcessJobMr pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(StockTx.class)) {

			StockTx stockTx = (StockTx)pJob.getRelatedObject().get(StockTx.class);

			try {
				StockItem item = pJob.getDaoFactory().getStockItemDAO().findById(stockTx.getStockItemId());
				MrVisit mrVisit = pJob.getDaoFactory().getMrVisitDAO().findById(stockTx.getMrVisitId());
				
				if(mrVisit == null) {
					return;
				}
				
				if(formulaMap.keySet().contains("latest_update_ts")) {
					mrVisit.setLatestUpdateTs(PPOAlgoUtil.compute(mrVisit.getLatestUpdateTs(), formulaMap.get("latest_update_ts"), DateUtil.getCurrentTimeDate()));
				}

				if(formulaMap.keySet().contains("sold_paid_am")) {
					mrVisit.setSoldPaidAm(PPOAlgoUtil.compute(mrVisit.getSoldPaidAm(), formulaMap.get("sold_paid_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("sold_pending_am")) {
					if(item != null) {
						mrVisit.setSoldPendingAm(PPOAlgoUtil.compute(mrVisit.getSoldPendingAm(), formulaMap.get("sold_pending_am"), BDUtil.sub(stockTx.getAmount(), item.getDiscountAm())));
					} else {
						mrVisit.setSoldPendingAm(PPOAlgoUtil.compute(mrVisit.getSoldPendingAm(), formulaMap.get("sold_pending_am"), stockTx.getAmount()));
					}
				}

				if(formulaMap.keySet().contains("closing_outstanding_am")) {
					mrVisit.setClosingOutstandingAm(PPOAlgoUtil.compute(mrVisit.getClosingOutstandingAm(), formulaMap.get("closing_outstanding_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("paid_interest_penalty_am")) {
					mrVisit.setPaidInterestPenaltyAm(PPOAlgoUtil.compute(mrVisit.getPaidInterestPenaltyAm(), formulaMap.get("paid_interest_penalty_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("closing_interest_penalty_am")) {
					mrVisit.setClosingInterestPenaltyAm(PPOAlgoUtil.compute(mrVisit.getClosingInterestPenaltyAm(), formulaMap.get("closing_interest_penalty_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("paid_collected_am")) {
					mrVisit.setPaidCollectedAm(PPOAlgoUtil.compute(mrVisit.getPaidCollectedAm(), formulaMap.get("paid_collected_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("received_collected_am")) {
					mrVisit.setReceivedCollectedAm(PPOAlgoUtil.compute(mrVisit.getReceivedCollectedAm(), formulaMap.get("received_collected_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("closing_collected_am")) {
					mrVisit.setClosingCollectedAm(PPOAlgoUtil.compute(mrVisit.getClosingCollectedAm(), formulaMap.get("closing_collected_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("paid_registration_am")) {
					mrVisit.setPaidRegistrationAm(PPOAlgoUtil.compute(mrVisit.getPaidRegistrationAm(), formulaMap.get("paid_registration_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("closing_registration_am")) {
					mrVisit.setClosingRegistrationAm(PPOAlgoUtil.compute(mrVisit.getClosingRegistrationAm(), formulaMap.get("closing_registration_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("paid_deposit_am")) {
					mrVisit.setPaidDepositAm(PPOAlgoUtil.compute(mrVisit.getPaidDepositAm(), formulaMap.get("paid_deposit_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("returned_deposit_am")) {
					mrVisit.setReturnedDepositAm(PPOAlgoUtil.compute(mrVisit.getReturnedDepositAm(), formulaMap.get("returned_deposit_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("closing_deposit_am")) {
					mrVisit.setClosingDepositAm(PPOAlgoUtil.compute(mrVisit.getClosingDepositAm(), formulaMap.get("closing_deposit_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("return_stock_am")) {
					mrVisit.setReturnStockAm(PPOAlgoUtil.compute(mrVisit.getReturnStockAm(), formulaMap.get("return_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("sold_stock_am")) {
					mrVisit.setSoldStockAm(PPOAlgoUtil.compute(mrVisit.getSoldStockAm(), formulaMap.get("sold_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("sold_stock_discount_am")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							mrVisit.setSoldStockDiscountAm(PPOAlgoUtil.compute(mrVisit.getSoldStockDiscountAm(), formulaMap.get("sold_stock_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("mr_sold_stock_am")) {
					mrVisit.setMrSoldStockAm(PPOAlgoUtil.compute(mrVisit.getMrSoldStockAm(), formulaMap.get("mr_sold_stock_am"), item.getMrSoldPrice()));
				}

				if(formulaMap.keySet().contains("given_stock_am")) {
					mrVisit.setGivenStockAm(PPOAlgoUtil.compute(mrVisit.getGivenStockAm(), formulaMap.get("given_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("closing_stock_am")) {
					mrVisit.setClosingStockAm(PPOAlgoUtil.compute(mrVisit.getClosingStockAm(), formulaMap.get("closing_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("return_stock_no")) {
					mrVisit.setReturnStockNo(PPOAlgoUtil.compute(mrVisit.getReturnStockNo(), formulaMap.get("return_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("sold_stock_no")) {
					mrVisit.setSoldStockNo(PPOAlgoUtil.compute(mrVisit.getSoldStockNo(), formulaMap.get("sold_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("sold_stock_discount_no")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							mrVisit.setSoldStockDiscountNo(PPOAlgoUtil.compute(mrVisit.getSoldStockDiscountNo(), formulaMap.get("sold_stock_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("given_stock_no")) {
					mrVisit.setGivenStockNo(PPOAlgoUtil.compute(mrVisit.getGivenStockNo(), formulaMap.get("given_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("closing_stock_no")) {
					mrVisit.setClosingStockNo(PPOAlgoUtil.compute(mrVisit.getClosingStockNo(), formulaMap.get("closing_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_am")) {
					mrVisit.setTotalStockAm(PPOAlgoUtil.compute(mrVisit.getTotalStockAm(), formulaMap.get("total_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_returned_am")) {
					mrVisit.setTotalStockReturnedAm(PPOAlgoUtil.compute(mrVisit.getTotalStockReturnedAm(), formulaMap.get("total_stock_returned_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_damaged_am")) {
					mrVisit.setTotalStockDamagedAm(PPOAlgoUtil.compute(mrVisit.getTotalStockDamagedAm(), formulaMap.get("total_stock_damaged_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_sold_am")) {
					mrVisit.setTotalStockSoldAm(PPOAlgoUtil.compute(mrVisit.getTotalStockSoldAm(), formulaMap.get("total_stock_sold_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_sold_discount_am")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							mrVisit.setTotalStockSoldDiscountAm(PPOAlgoUtil.compute(mrVisit.getTotalStockSoldDiscountAm(), formulaMap.get("total_stock_sold_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("total_stock_no")) {
					mrVisit.setTotalStockNo(PPOAlgoUtil.compute(mrVisit.getTotalStockNo(), formulaMap.get("total_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_returned_no")) {
					mrVisit.setTotalStockReturnedNo(PPOAlgoUtil.compute(mrVisit.getTotalStockReturnedNo(), formulaMap.get("total_stock_returned_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_damaged_no")) {
					mrVisit.setTotalStockDamagedNo(PPOAlgoUtil.compute(mrVisit.getTotalStockDamagedNo(), formulaMap.get("total_stock_damaged_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_sold_no")) {
					mrVisit.setTotalStockSoldNo(PPOAlgoUtil.compute(mrVisit.getTotalStockSoldNo(), formulaMap.get("total_stock_sold_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_sold_discount_no")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							mrVisit.setTotalStockSoldDiscountNo(PPOAlgoUtil.compute(mrVisit.getTotalStockSoldDiscountNo(), formulaMap.get("total_stock_sold_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("total_paid_sold_am")) {
					mrVisit.setTotalPaidSoldAm(PPOAlgoUtil.compute(mrVisit.getTotalPaidSoldAm(), formulaMap.get("total_paid_sold_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_paid_interest_penalty_am")) {
					mrVisit.setTotalPaidInterestPenaltyAm(PPOAlgoUtil.compute(mrVisit.getTotalPaidInterestPenaltyAm(), formulaMap.get("total_paid_interest_penalty_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_paid_collected_am")) {
					mrVisit.setTotalPaidCollectedAm(PPOAlgoUtil.compute(mrVisit.getTotalPaidCollectedAm(), formulaMap.get("total_paid_collected_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_registration_paid_am")) {
					mrVisit.setTotalRegistrationPaidAm(PPOAlgoUtil.compute(mrVisit.getTotalRegistrationPaidAm(), formulaMap.get("total_registration_paid_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_deposit_paid_am")) {
					mrVisit.setTotalDepositPaidAm(PPOAlgoUtil.compute(mrVisit.getTotalDepositPaidAm(), formulaMap.get("total_deposit_paid_am"), stockTx.getAmount()));
				}

				pJob.getDaoFactory().getMrVisitDAO().merge(mrVisit);

			} catch (Exception e) {
				log.error(e.toString() + "; for StockItemId: " + stockTx.getStockItemId() + "; for StockTx Id: " + stockTx.getStockTxId());
			}
		}
	}

}