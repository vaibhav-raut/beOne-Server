package com.beone.udaan.mr.ppo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.PMAc;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

public abstract class PMAcPPO extends IMrProcessing {

	private static final Logger log = LoggerFactory.getLogger(PMAcPPO.class);

	protected void processUpdateFormula(ProcessJobMr pJob, Map<String,String> formulaMap, StockTx stockTx, PMAc pMAc) {

		if(pJob.getRelatedObject().containsKey(StockTx.class)) {

			try {
				StockItem item = null;
				if(stockTx.getStockItemId() > 0) {
					item = pJob.getDaoFactory().getStockItemDAO().findById(stockTx.getStockItemId());
				}

				int addDiscountSrockNo = 0;
				if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {		
					if(stockTx.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0 && stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
						addDiscountSrockNo = -1;
					} else if(stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0) {
						addDiscountSrockNo = 1;
					}
				}

				if(formulaMap.keySet().contains("registration_paid_am")) {
					pMAc.setRegistrationPaidAm(PPOAlgoUtil.compute(pMAc.getRegistrationPaidAm(), formulaMap.get("registration_paid_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("registration_pending_am")) {
					pMAc.setRegistrationPendingAm(PPOAlgoUtil.compute(pMAc.getRegistrationPendingAm(), formulaMap.get("registration_pending_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("deposit_paid_am")) {
					pMAc.setDepositPaidAm(PPOAlgoUtil.compute(pMAc.getDepositPaidAm(), formulaMap.get("deposit_paid_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("deposit_pending_am")) {
					pMAc.setDepositPendingAm(PPOAlgoUtil.compute(pMAc.getDepositPendingAm(), formulaMap.get("deposit_pending_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("deposit_return_am")) {
					pMAc.setDepositReturnAm(PPOAlgoUtil.compute(pMAc.getDepositReturnAm(), formulaMap.get("deposit_return_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("credit_limit_am")) {
					pMAc.setCreditLimitAm(PPOAlgoUtil.compute(pMAc.getCreditLimitAm(), formulaMap.get("credit_limit_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_collected_am")) {
					pMAc.setTotalCollectedAm(PPOAlgoUtil.compute(pMAc.getTotalCollectedAm(), formulaMap.get("total_collected_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_paid_collected_am")) {
					pMAc.setTotalPaidCollectedAm(PPOAlgoUtil.compute(pMAc.getTotalPaidCollectedAm(), formulaMap.get("total_paid_collected_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_collected_am")) {
					pMAc.setCurrentCollectedAm(PPOAlgoUtil.compute(pMAc.getCurrentCollectedAm(), formulaMap.get("current_collected_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("sold_paid_am")) {
					pMAc.setSoldPaidAm(PPOAlgoUtil.compute(pMAc.getSoldPaidAm(), formulaMap.get("sold_paid_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("sold_pending_am")) {
					if(item != null) {
						pMAc.setSoldPendingAm(PPOAlgoUtil.compute(pMAc.getSoldPendingAm(), formulaMap.get("sold_pending_am"), item.getSoldPrice()));
					} else {
						pMAc.setSoldPendingAm(PPOAlgoUtil.compute(pMAc.getSoldPendingAm(), formulaMap.get("sold_pending_am"), stockTx.getAmount()));
					}
				}

				if(formulaMap.keySet().contains("paid_interest_penalty_am")) {
					pMAc.setPaidInterestPenaltyAm(PPOAlgoUtil.compute(pMAc.getPaidInterestPenaltyAm(), formulaMap.get("paid_interest_penalty_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("pending_interest_penalty_am")) {
					pMAc.setPendingInterestPenaltyAm(PPOAlgoUtil.compute(pMAc.getPendingInterestPenaltyAm(), formulaMap.get("pending_interest_penalty_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("last_visit_on")) {
					pMAc.setLastVisitOn(PPOAlgoUtil.compute(pMAc.getLastVisitOn(), formulaMap.get("last_visit_on"), DateUtil.getCurrentTimeDate()));
				}

				if(formulaMap.keySet().contains("interest_calculated_on")) {
					pMAc.setInterestCalculatedOn(PPOAlgoUtil.compute(pMAc.getInterestCalculatedOn(), formulaMap.get("interest_calculated_on"), DateUtil.getCurrentTimeDate()));
				}

				if(formulaMap.keySet().contains("current_stock_am")) {
					pMAc.setCurrentStockAm(PPOAlgoUtil.compute(pMAc.getCurrentStockAm(), formulaMap.get("current_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_stock_discount_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pMAc.setCurrentStockDiscountAm(PPOAlgoUtil.compute(pMAc.getCurrentStockDiscountAm(), formulaMap.get("current_stock_discount_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setCurrentStockDiscountAm(PPOAlgoUtil.compute(pMAc.getCurrentStockDiscountAm(), formulaMap.get("current_stock_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("current_stock_unsettled_am")) {
					pMAc.setCurrentStockUnsettledAm(PPOAlgoUtil.compute(pMAc.getCurrentStockUnsettledAm(), formulaMap.get("current_stock_unsettled_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_stock_to_return_am")) {
					pMAc.setCurrentStockToReturnAm(PPOAlgoUtil.compute(pMAc.getCurrentStockToReturnAm(), formulaMap.get("current_stock_to_return_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_stock_no")) {
					pMAc.setCurrentStockNo(PPOAlgoUtil.compute(pMAc.getCurrentStockNo(), formulaMap.get("current_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_stock_discount_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pMAc.setCurrentStockDiscountNo(PPOAlgoUtil.compute(pMAc.getCurrentStockDiscountNo(), formulaMap.get("current_stock_discount_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setCurrentStockDiscountNo(PPOAlgoUtil.compute(pMAc.getCurrentStockDiscountNo(), formulaMap.get("current_stock_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("current_stock_unsettled_no")) {
					pMAc.setCurrentStockUnsettledNo(PPOAlgoUtil.compute(pMAc.getCurrentStockUnsettledNo(), formulaMap.get("current_stock_unsettled_no"), 1));
				}

				if(formulaMap.keySet().contains("current_stock_to_return_no")) {
					pMAc.setCurrentStockToReturnNo(PPOAlgoUtil.compute(pMAc.getCurrentStockToReturnNo(), formulaMap.get("current_stock_to_return_no"), 1));
				}


				// ************ Total Stock ******************
				if(formulaMap.keySet().contains("total_stock_am")) {
					pMAc.setTotalStockAm(PPOAlgoUtil.compute(pMAc.getTotalStockAm(), formulaMap.get("total_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_returned_am")) {
					pMAc.setTotalStockReturnedAm(PPOAlgoUtil.compute(pMAc.getTotalStockReturnedAm(), formulaMap.get("total_stock_returned_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_damaged_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pMAc.setTotalStockDamagedAm(PPOAlgoUtil.compute(pMAc.getTotalStockDamagedAm(), formulaMap.get("total_stock_damaged_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setTotalStockDamagedAm(PPOAlgoUtil.compute(pMAc.getTotalStockDamagedAm(), formulaMap.get("total_stock_damaged_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("total_stock_sold_am")) {
					pMAc.setTotalStockSoldAm(PPOAlgoUtil.compute(pMAc.getTotalStockSoldAm(), formulaMap.get("total_stock_sold_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_sold_discount_am")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setTotalStockSoldDiscountAm(PPOAlgoUtil.compute(pMAc.getTotalStockSoldDiscountAm(), formulaMap.get("total_stock_sold_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("total_stock_mr_sold_am")) {
					if(item != null) {
						pMAc.setTotalStockMrSoldAm(PPOAlgoUtil.compute(pMAc.getTotalStockMrSoldAm(), formulaMap.get("total_stock_mr_sold_am"), item.getMrSoldPrice()));
					}
				}

				if(formulaMap.keySet().contains("total_stock_no")) {
					pMAc.setTotalStockNo(PPOAlgoUtil.compute(pMAc.getTotalStockNo(), formulaMap.get("total_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_returned_no")) {
					pMAc.setTotalStockReturnedNo(PPOAlgoUtil.compute(pMAc.getTotalStockReturnedNo(), formulaMap.get("total_stock_returned_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_damaged_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pMAc.setTotalStockDamagedNo(PPOAlgoUtil.compute(pMAc.getTotalStockDamagedNo(), formulaMap.get("total_stock_damaged_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setTotalStockDamagedNo(PPOAlgoUtil.compute(pMAc.getTotalStockDamagedNo(), formulaMap.get("total_stock_damaged_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("total_stock_sold_no")) {
					pMAc.setTotalStockSoldNo(PPOAlgoUtil.compute(pMAc.getTotalStockSoldNo(), formulaMap.get("total_stock_sold_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_sold_discount_no")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setTotalStockSoldDiscountNo(PPOAlgoUtil.compute(pMAc.getTotalStockSoldDiscountNo(), formulaMap.get("total_stock_sold_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("total_visit_counter")) {
					pMAc.setTotalVisitCounter(PPOAlgoUtil.compute(pMAc.getTotalVisitCounter(), formulaMap.get("total_visit_counter"), 1));
				}


				// ************ This Month Stock ******************
				if(formulaMap.keySet().contains("this_month_stock_am")) {
					pMAc.setThisMonthStockAm(PPOAlgoUtil.compute(pMAc.getThisMonthStockAm(), formulaMap.get("this_month_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("this_month_stock_returned_am")) {
					pMAc.setThisMonthStockReturnedAm(PPOAlgoUtil.compute(pMAc.getThisMonthStockReturnedAm(), formulaMap.get("this_month_stock_returned_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("this_month_stock_damaged_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pMAc.setThisMonthStockDamagedAm(PPOAlgoUtil.compute(pMAc.getThisMonthStockDamagedAm(), formulaMap.get("this_month_stock_damaged_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setThisMonthStockDamagedAm(PPOAlgoUtil.compute(pMAc.getThisMonthStockDamagedAm(), formulaMap.get("this_month_stock_damaged_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("this_month_stock_sold_am")) {
					pMAc.setThisMonthStockSoldAm(PPOAlgoUtil.compute(pMAc.getThisMonthStockSoldAm(), formulaMap.get("this_month_stock_sold_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("this_month_stock_sold_discount_am")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setThisMonthStockSoldDiscountAm(PPOAlgoUtil.compute(pMAc.getThisMonthStockSoldDiscountAm(), formulaMap.get("this_month_stock_sold_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("this_month_stock_mr_sold_am")) {
					if(item != null) {
						pMAc.setThisMonthStockMrSoldAm(PPOAlgoUtil.compute(pMAc.getThisMonthStockMrSoldAm(), formulaMap.get("this_month_stock_mr_sold_am"), item.getMrSoldPrice()));
					}
				}

				if(formulaMap.keySet().contains("this_month_stock_no")) {
					pMAc.setThisMonthStockNo(PPOAlgoUtil.compute(pMAc.getThisMonthStockNo(), formulaMap.get("this_month_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("this_month_stock_returned_no")) {
					pMAc.setThisMonthStockReturnedNo(PPOAlgoUtil.compute(pMAc.getThisMonthStockReturnedNo(), formulaMap.get("this_month_stock_returned_no"), 1));
				}

				if(formulaMap.keySet().contains("this_month_stock_damaged_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pMAc.setThisMonthStockDamagedNo(PPOAlgoUtil.compute(pMAc.getThisMonthStockDamagedNo(), formulaMap.get("this_month_stock_damaged_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setThisMonthStockDamagedNo(PPOAlgoUtil.compute(pMAc.getThisMonthStockDamagedNo(), formulaMap.get("this_month_stock_damaged_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("this_month_stock_sold_no")) {
					pMAc.setThisMonthStockSoldNo(PPOAlgoUtil.compute(pMAc.getThisMonthStockSoldNo(), formulaMap.get("this_month_stock_sold_no"), 1));
				}

				if(formulaMap.keySet().contains("this_month_stock_sold_discount_no")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setThisMonthStockSoldDiscountNo(PPOAlgoUtil.compute(pMAc.getThisMonthStockSoldDiscountNo(), formulaMap.get("this_month_stock_sold_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("this_month_visit_counter")) {
					pMAc.setThisMonthVisitCounter(PPOAlgoUtil.compute(pMAc.getThisMonthVisitCounter(), formulaMap.get("this_month_visit_counter"), 1));
				}


				// ************ Last Month Stock ******************
				if(formulaMap.keySet().contains("last_month_stock_am")) {
					pMAc.setLastMonthStockAm(PPOAlgoUtil.compute(pMAc.getLastMonthStockAm(), formulaMap.get("last_month_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("last_month_stock_returned_am")) {
					pMAc.setLastMonthStockReturnedAm(PPOAlgoUtil.compute(pMAc.getLastMonthStockReturnedAm(), formulaMap.get("last_month_stock_returned_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("last_month_stock_damaged_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pMAc.setLastMonthStockDamagedAm(PPOAlgoUtil.compute(pMAc.getLastMonthStockDamagedAm(), formulaMap.get("last_month_stock_damaged_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setLastMonthStockDamagedAm(PPOAlgoUtil.compute(pMAc.getLastMonthStockDamagedAm(), formulaMap.get("last_month_stock_damaged_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("last_month_stock_sold_am")) {
					pMAc.setLastMonthStockSoldAm(PPOAlgoUtil.compute(pMAc.getLastMonthStockSoldAm(), formulaMap.get("last_month_stock_sold_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("last_month_stock_sold_discount_am")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setLastMonthStockSoldDiscountAm(PPOAlgoUtil.compute(pMAc.getLastMonthStockSoldDiscountAm(), formulaMap.get("last_month_stock_sold_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("last_month_stock_mr_sold_am")) {
					if(item != null) {
						pMAc.setLastMonthStockMrSoldAm(PPOAlgoUtil.compute(pMAc.getLastMonthStockMrSoldAm(), formulaMap.get("last_month_stock_mr_sold_am"), item.getMrSoldPrice()));
					}
				}

				if(formulaMap.keySet().contains("last_month_stock_no")) {
					pMAc.setLastMonthStockNo(PPOAlgoUtil.compute(pMAc.getLastMonthStockNo(), formulaMap.get("last_month_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("last_month_stock_returned_no")) {
					pMAc.setLastMonthStockReturnedNo(PPOAlgoUtil.compute(pMAc.getLastMonthStockReturnedNo(), formulaMap.get("last_month_stock_returned_no"), 1));
				}

				if(formulaMap.keySet().contains("last_month_stock_damaged_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pMAc.setLastMonthStockDamagedNo(PPOAlgoUtil.compute(pMAc.getLastMonthStockDamagedNo(), formulaMap.get("last_month_stock_damaged_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setLastMonthStockDamagedNo(PPOAlgoUtil.compute(pMAc.getLastMonthStockDamagedNo(), formulaMap.get("last_month_stock_damaged_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("last_month_stock_sold_no")) {
					pMAc.setLastMonthStockSoldNo(PPOAlgoUtil.compute(pMAc.getLastMonthStockSoldNo(), formulaMap.get("last_month_stock_sold_no"), 1));
				}

				if(formulaMap.keySet().contains("last_month_stock_sold_discount_no")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pMAc.setLastMonthStockSoldDiscountNo(PPOAlgoUtil.compute(pMAc.getLastMonthStockSoldDiscountNo(), formulaMap.get("last_month_stock_sold_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("last_month_visit_counter")) {
					pMAc.setLastMonthVisitCounter(PPOAlgoUtil.compute(pMAc.getLastMonthVisitCounter(), formulaMap.get("last_month_visit_counter"), 1));
				}


				pJob.getDaoFactory().getPMAcDAO().merge(pMAc);

			} catch (Exception e) {
				log.error(e.toString() + "; for StockItemId: " + stockTx.getStockItemId() + "; for StockTx Id: " + stockTx.getStockTxId());
			}
		}
	}
}