package com.beone.udaan.mr.ppo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.MRoleValue;
import com.beone.udaan.mr.config.DBConstMr;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.PHubAc;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

@Repository("pHubAcPPO")
public class PHubAcPPO extends IMrProcessing {

	private static final Logger log = LoggerFactory.getLogger(PHubAcPPO.class);

	@Override
	public void processUpdateFormula(ProcessJobMr pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(StockTx.class)) {

			StockTx stockTx = (StockTx)pJob.getRelatedObject().get(StockTx.class);

			try {
				PHubAc pHubAc = pJob.getDaoFactory().getPHubAcDAO().findById(DBConstMr.P_MEGA_HUB_GROUP_AC_NO);
				MProfile auth = null;
				MRoleValue authRole = null;
				boolean authAvaliable = false;

				if(ConversionUtil.isValidMemberAcNo(stockTx.getAuthAcNo())) {
					auth = pJob.getDaoFactory().getMProfileDAO().findById(stockTx.getAuthAcNo());
					if(auth != null) {
						authRole = EnumCache.getMRoleValue(auth.getMroleId());
						authAvaliable = true;
					}
				}

				StockItem item = null;
				if(stockTx.getStockItemId() > 0) {
					item = pJob.getDaoFactory().getStockItemDAO().findById(stockTx.getStockItemId());
				}
				MProfile owner = pJob.getDaoFactory().getMProfileDAO().findById(stockTx.getOwnerAcNo());
				MRoleValue ownerRole = EnumCache.getMRoleValue(owner.getMroleId());

				int addDiscountSrockNo = 0;
				if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {		
					if(stockTx.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0 && stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
						addDiscountSrockNo = -1;
					} else if(stockTx.getExtraAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0) {
						addDiscountSrockNo = 1;
					}
				}

				// ******************* Total *******************
				if(formulaMap.keySet().contains("total_purchased_stock_am")) {
					pHubAc.setTotalPurchasedStockAm(PPOAlgoUtil.compute(pHubAc.getTotalPurchasedStockAm(), formulaMap.get("total_purchased_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_am")) {
					pHubAc.setTotalStockAm(PPOAlgoUtil.compute(pHubAc.getTotalStockAm(), formulaMap.get("total_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_returned_am")) {
					pHubAc.setTotalStockReturnedAm(PPOAlgoUtil.compute(pHubAc.getTotalStockReturnedAm(), formulaMap.get("total_stock_returned_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_stock_damaged_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pHubAc.setTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getTotalStockDamagedAm(), formulaMap.get("total_stock_damaged_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pHubAc.setTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getTotalStockDamagedAm(), formulaMap.get("total_stock_damaged_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("total_stock_sold_am")) {
					pHubAc.setTotalStockSoldAm(PPOAlgoUtil.compute(pHubAc.getTotalStockSoldAm(), formulaMap.get("total_stock_sold_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_sold_discount_am")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pHubAc.setTotalSoldDiscountAm(PPOAlgoUtil.compute(pHubAc.getTotalSoldDiscountAm(), formulaMap.get("total_sold_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("current_stock_am")) {
					pHubAc.setCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getCurrentStockAm(), formulaMap.get("current_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_stock_discount_am")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pHubAc.setCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getCurrentStockDiscountAm(), formulaMap.get("current_stock_discount_am"), 
								BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
					} else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pHubAc.setCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getCurrentStockDiscountAm(), formulaMap.get("current_stock_discount_am"), item.getDiscountAm()));
						}
					}
				}

				if(formulaMap.keySet().contains("current_stock_unsettled_am")) {
					pHubAc.setCurrentStockUnsettledAm(PPOAlgoUtil.compute(pHubAc.getCurrentStockUnsettledAm(), formulaMap.get("current_stock_unsettled_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_stock_to_return_am")) {
					pHubAc.setCurrentStockToReturnAm(PPOAlgoUtil.compute(pHubAc.getCurrentStockToReturnAm(), formulaMap.get("current_stock_to_return_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("total_purchased_stock_no")) {
					pHubAc.setTotalPurchasedStockNo(PPOAlgoUtil.compute(pHubAc.getTotalPurchasedStockNo(), formulaMap.get("total_purchased_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_no")) {
					pHubAc.setTotalStockNo(PPOAlgoUtil.compute(pHubAc.getTotalStockNo(), formulaMap.get("total_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_returned_no")) {
					pHubAc.setTotalStockReturnedNo(PPOAlgoUtil.compute(pHubAc.getTotalStockReturnedNo(), formulaMap.get("total_stock_returned_no"), 1));
				}

				if(formulaMap.keySet().contains("total_stock_damaged_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pHubAc.setTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getTotalStockDamagedNo(), formulaMap.get("total_stock_damaged_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pHubAc.setTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getTotalStockDamagedNo(), formulaMap.get("total_stock_damaged_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("total_stock_sold_no")) {
					pHubAc.setTotalStockSoldNo(PPOAlgoUtil.compute(pHubAc.getTotalStockSoldNo(), formulaMap.get("total_stock_sold_no"), 1));
				}

				if(formulaMap.keySet().contains("total_sold_discount_no")) {
					if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pHubAc.setTotalSoldDiscountNo(PPOAlgoUtil.compute(pHubAc.getTotalSoldDiscountNo(), formulaMap.get("total_sold_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("current_stock_no")) {
					pHubAc.setCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getCurrentStockNo(), formulaMap.get("current_stock_no"), 1));
				}

				if(formulaMap.keySet().contains("current_stock_discount_no")) {
					if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
						pHubAc.setCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getCurrentStockDiscountNo(), formulaMap.get("current_stock_discount_no"), addDiscountSrockNo));
					}
					else if(item != null) {
						if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							pHubAc.setCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getCurrentStockDiscountNo(), formulaMap.get("current_stock_discount_no"), 1));
						}
					}
				}

				if(formulaMap.keySet().contains("current_stock_unsettled_no")) {
					pHubAc.setCurrentStockUnsettledNo(PPOAlgoUtil.compute(pHubAc.getCurrentStockUnsettledNo(), formulaMap.get("current_stock_unsettled_no"), 1));
				}

				if(formulaMap.keySet().contains("current_stock_to_return_no")) {
					pHubAc.setCurrentStockToReturnNo(PPOAlgoUtil.compute(pHubAc.getCurrentStockToReturnNo(), formulaMap.get("current_stock_to_return_no"), 1));
				}

				if(formulaMap.keySet().contains("return_counter")) {
					pHubAc.setReturnCounter(PPOAlgoUtil.compute(pHubAc.getReturnCounter(), formulaMap.get("return_counter"), 1));
				}

				if(ownerRole.getBelongTo().equals(EnumConstMr.GroupType_Micro_Retailer)) {

					if(formulaMap.keySet().contains("total_stock_mr_sold_am")) {
						if(item != null) {
							pHubAc.setTotalStockMrSoldAm(PPOAlgoUtil.compute(pHubAc.getTotalStockMrSoldAm(), formulaMap.get("total_stock_mr_sold_am"), item.getMrSoldPrice()));
						}
					}

					if(formulaMap.keySet().contains("total_stock_mr_sold_no")) {
						if(item != null) {
							pHubAc.setTotalStockMrSoldNo(PPOAlgoUtil.compute(pHubAc.getTotalStockMrSoldNo(), formulaMap.get("total_stock_mr_sold_no"), 1));
						}
					}

				} else {

					if(formulaMap.keySet().contains("total_stock_direct_sold_am")) {
						if(item != null) {
							pHubAc.setTotalStockDirectSoldAm(PPOAlgoUtil.compute(pHubAc.getTotalStockDirectSoldAm(), formulaMap.get("total_stock_direct_sold_am"), item.getMrSoldPrice()));
						}
					}

					if(formulaMap.keySet().contains("total_stock_direct_sold_no")) {
						pHubAc.setTotalStockDirectSoldNo(PPOAlgoUtil.compute(pHubAc.getTotalStockDirectSoldNo(), formulaMap.get("total_stock_direct_sold_no"), 1));
					}
				}

				if(formulaMap.keySet().contains("current_created_stock_am")) {
					pHubAc.setCurrentCreatedStockAm(PPOAlgoUtil.compute(pHubAc.getCurrentCreatedStockAm(), formulaMap.get("current_created_stock_am"), stockTx.getAmount()));
				}

				if(formulaMap.keySet().contains("current_created_stock_no")) {
					pHubAc.setCurrentCreatedStockNo(PPOAlgoUtil.compute(pHubAc.getCurrentCreatedStockNo(), formulaMap.get("current_created_stock_no"), 1));
				}

				// ******************* Mega Hub - Total *******************			
				if(authAvaliable && authRole.getBelongTo().equals(EnumConstMr.GroupType_Mega_HUB)) {

					if(formulaMap.keySet().contains("a_mh_total_collected_am")) {
						pHubAc.setMhTotalCollectedAm(PPOAlgoUtil.compute(pHubAc.getMhTotalCollectedAm(), formulaMap.get("a_mh_total_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_mh_total_paid_collected_am")) {
						pHubAc.setMhTotalPaidCollectedAm(PPOAlgoUtil.compute(pHubAc.getMhTotalPaidCollectedAm(), formulaMap.get("a_mh_total_paid_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_mh_current_collected_am")) {
						pHubAc.setMhCurrentCollectedAm(PPOAlgoUtil.compute(pHubAc.getMhCurrentCollectedAm(), formulaMap.get("a_mh_current_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_mh_current_stock_am")) {
						pHubAc.setMhCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockAm(), formulaMap.get("a_mh_current_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_mh_current_stock_discount_am")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMhCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockDiscountAm(), formulaMap.get("a_mh_current_stock_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("a_mh_current_stock_no")) {
						pHubAc.setMhCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockNo(), formulaMap.get("a_mh_current_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("a_mh_current_stock_discount_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setMhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockDiscountNo(), formulaMap.get("a_mh_current_stock_discount_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockDiscountNo(), formulaMap.get("a_mh_current_stock_discount_no"), 1));
							}
						}
					}
				}

				if(ownerRole.getBelongTo().equals(EnumConstMr.GroupType_Mega_HUB)) {

					if(formulaMap.keySet().contains("mh_total_collected_am")) {
						pHubAc.setMhTotalCollectedAm(PPOAlgoUtil.compute(pHubAc.getMhTotalCollectedAm(), formulaMap.get("mh_total_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_total_paid_collected_am")) {
						pHubAc.setMhTotalPaidCollectedAm(PPOAlgoUtil.compute(pHubAc.getMhTotalPaidCollectedAm(), formulaMap.get("mh_total_paid_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_current_collected_am")) {
						pHubAc.setMhCurrentCollectedAm(PPOAlgoUtil.compute(pHubAc.getMhCurrentCollectedAm(), formulaMap.get("mh_current_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_sold_paid_am")) {
						pHubAc.setMhSoldPaidAm(PPOAlgoUtil.compute(pHubAc.getMhSoldPaidAm(), formulaMap.get("mh_sold_paid_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_sold_pending_am")) {
						if(item != null) {
							pHubAc.setMhSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getMhSoldPendingAm(), formulaMap.get("mh_sold_pending_am"), item.getSoldPrice()));
						} else {
							pHubAc.setMhSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getMhSoldPendingAm(), formulaMap.get("mh_sold_pending_am"), stockTx.getAmount()));
						}
					}

					if(formulaMap.keySet().contains("mh_total_stock_am")) {
						pHubAc.setMhTotalStockAm(PPOAlgoUtil.compute(pHubAc.getMhTotalStockAm(), formulaMap.get("mh_total_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_total_stock_returned_am")) {
						pHubAc.setMhTotalStockReturnedAm(PPOAlgoUtil.compute(pHubAc.getMhTotalStockReturnedAm(), formulaMap.get("mh_total_stock_returned_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_total_stock_damaged_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setMhTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getMhTotalStockDamagedAm(), formulaMap.get("mh_total_stock_damaged_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMhTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getMhTotalStockDamagedAm(), formulaMap.get("mh_total_stock_damaged_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("mh_total_stock_sold_am")) {
						pHubAc.setMhTotalStockSoldAm(PPOAlgoUtil.compute(pHubAc.getMhTotalStockSoldAm(), formulaMap.get("mh_total_stock_sold_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_total_stock_sold_discount_am")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMhTotalStockSoldDiscountAm(PPOAlgoUtil.compute(pHubAc.getMhTotalStockSoldDiscountAm(), formulaMap.get("mh_total_stock_sold_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("mh_current_stock_am")) {
						pHubAc.setMhCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockAm(), formulaMap.get("mh_current_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_current_stock_discount_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setMhCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockDiscountAm(), formulaMap.get("mh_current_stock_discount_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMhCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockDiscountAm(), formulaMap.get("mh_current_stock_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("mh_current_stock_unsettled_am")) {
						pHubAc.setMhCurrentStockUnsettledAm(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockUnsettledAm(), formulaMap.get("mh_current_stock_unsettled_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_current_stock_to_return_am")) {
						pHubAc.setMhCurrentStockToReturnAm(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockToReturnAm(), formulaMap.get("mh_current_stock_to_return_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mh_total_stock_no")) {
						pHubAc.setMhTotalStockNo(PPOAlgoUtil.compute(pHubAc.getMhTotalStockNo(), formulaMap.get("mh_total_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("mh_total_stock_returned_no")) {
						pHubAc.setMhTotalStockReturnedNo(PPOAlgoUtil.compute(pHubAc.getMhTotalStockReturnedNo(), formulaMap.get("mh_total_stock_returned_no"), 1));
					}

					if(formulaMap.keySet().contains("mh_total_stock_damaged_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setMhTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getMhTotalStockDamagedNo(), formulaMap.get("mh_total_stock_damaged_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMhTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getMhTotalStockDamagedNo(), formulaMap.get("mh_total_stock_damaged_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("mh_total_stock_sold_no")) {
						pHubAc.setMhTotalStockSoldNo(PPOAlgoUtil.compute(pHubAc.getMhTotalStockSoldNo(), formulaMap.get("mh_total_stock_sold_no"), 1));
					}

					if(formulaMap.keySet().contains("mh_total_stock_sold_discount_no")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMhTotalStockSoldDiscountNo(PPOAlgoUtil.compute(pHubAc.getMhTotalStockSoldDiscountNo(), formulaMap.get("mh_total_stock_sold_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("mh_current_stock_no")) {
						pHubAc.setMhCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockNo(), formulaMap.get("mh_current_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("mh_current_stock_discount_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setMhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockDiscountNo(), formulaMap.get("mh_current_stock_discount_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockDiscountNo(), formulaMap.get("mh_current_stock_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("mh_current_stock_unsettled_no")) {
						pHubAc.setMhCurrentStockUnsettledNo(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockUnsettledNo(), formulaMap.get("mh_current_stock_unsettled_no"), 1));
					}

					if(formulaMap.keySet().contains("mh_current_stock_to_return_no")) {
						pHubAc.setMhCurrentStockToReturnNo(PPOAlgoUtil.compute(pHubAc.getMhCurrentStockToReturnNo(), formulaMap.get("mh_current_stock_to_return_no"), 1));
					}

					if(formulaMap.keySet().contains("mh_return_counter")) {
						pHubAc.setMhReturnCounter(PPOAlgoUtil.compute(pHubAc.getMhReturnCounter(), formulaMap.get("mh_return_counter"), 1));
					}
				}

				// ******************* Local Hub - Total *******************			
				if(authAvaliable && authRole.getBelongTo().equals(EnumConstMr.GroupType_Local_HUB)) {

					if(formulaMap.keySet().contains("a_lh_total_collected_am")) {
						pHubAc.setLhTotalCollectedAm(PPOAlgoUtil.compute(pHubAc.getLhTotalCollectedAm(), formulaMap.get("a_lh_total_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_lh_total_paid_collected_am")) {
						pHubAc.setLhTotalPaidCollectedAm(PPOAlgoUtil.compute(pHubAc.getLhTotalPaidCollectedAm(), formulaMap.get("a_lh_total_paid_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_lh_current_collected_am")) {
						pHubAc.setLhCurrentCollectedAm(PPOAlgoUtil.compute(pHubAc.getLhCurrentCollectedAm(), formulaMap.get("a_lh_current_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_lh_current_stock_am")) {
						pHubAc.setLhCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockAm(), formulaMap.get("a_lh_current_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_lh_current_stock_discount_am")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setLhCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockDiscountAm(), formulaMap.get("a_lh_current_stock_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("a_lh_current_stock_no")) {
						pHubAc.setLhCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockNo(), formulaMap.get("a_lh_current_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("a_lh_current_stock_discount_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setLhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockDiscountNo(), formulaMap.get("a_lh_current_stock_discount_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setLhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockDiscountNo(), formulaMap.get("a_lh_current_stock_discount_no"), 1));
							}
						}
					}
				}

				if(ownerRole.getBelongTo().equals(EnumConstMr.GroupType_Local_HUB)) {

					if(formulaMap.keySet().contains("lh_total_collected_am")) {
						pHubAc.setLhTotalCollectedAm(PPOAlgoUtil.compute(pHubAc.getLhTotalCollectedAm(), formulaMap.get("lh_total_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_total_paid_collected_am")) {
						pHubAc.setLhTotalPaidCollectedAm(PPOAlgoUtil.compute(pHubAc.getLhTotalPaidCollectedAm(), formulaMap.get("lh_total_paid_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_current_collected_am")) {
						pHubAc.setLhCurrentCollectedAm(PPOAlgoUtil.compute(pHubAc.getLhCurrentCollectedAm(), formulaMap.get("lh_current_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_sold_paid_am")) {
						pHubAc.setLhSoldPaidAm(PPOAlgoUtil.compute(pHubAc.getLhSoldPaidAm(), formulaMap.get("lh_sold_paid_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_sold_pending_am")) {
						if(item != null) {
							pHubAc.setLhSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getLhSoldPendingAm(), formulaMap.get("lh_sold_pending_am"), item.getSoldPrice()));
						} else {
							pHubAc.setLhSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getLhSoldPendingAm(), formulaMap.get("lh_sold_pending_am"), stockTx.getAmount()));
						}
					}

					if(formulaMap.keySet().contains("lh_total_stock_am")) {
						pHubAc.setLhTotalStockAm(PPOAlgoUtil.compute(pHubAc.getLhTotalStockAm(), formulaMap.get("lh_total_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_total_stock_returned_am")) {
						pHubAc.setLhTotalStockReturnedAm(PPOAlgoUtil.compute(pHubAc.getLhTotalStockReturnedAm(), formulaMap.get("lh_total_stock_returned_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_total_stock_damaged_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setLhTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getLhTotalStockDamagedAm(), formulaMap.get("lh_total_stock_damaged_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setLhTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getLhTotalStockDamagedAm(), formulaMap.get("lh_total_stock_damaged_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("lh_total_stock_sold_am")) {
						pHubAc.setLhTotalStockSoldAm(PPOAlgoUtil.compute(pHubAc.getLhTotalStockSoldAm(), formulaMap.get("lh_total_stock_sold_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_total_stock_sold_discount_am")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setLhTotalStockSoldDiscountAm(PPOAlgoUtil.compute(pHubAc.getLhTotalStockSoldDiscountAm(), formulaMap.get("lh_total_stock_sold_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("lh_current_stock_am")) {
						pHubAc.setLhCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockAm(), formulaMap.get("lh_current_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_current_stock_discount_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setLhCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockDiscountAm(), formulaMap.get("lh_current_stock_discount_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setLhCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockDiscountAm(), formulaMap.get("lh_current_stock_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("lh_current_stock_unsettled_am")) {
						pHubAc.setLhCurrentStockUnsettledAm(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockUnsettledAm(), formulaMap.get("lh_current_stock_unsettled_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_current_stock_to_return_am")) {
						pHubAc.setLhCurrentStockToReturnAm(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockToReturnAm(), formulaMap.get("lh_current_stock_to_return_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("lh_total_stock_no")) {
						pHubAc.setLhTotalStockNo(PPOAlgoUtil.compute(pHubAc.getLhTotalStockNo(), formulaMap.get("lh_total_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("lh_total_stock_returned_no")) {
						pHubAc.setLhTotalStockReturnedNo(PPOAlgoUtil.compute(pHubAc.getLhTotalStockReturnedNo(), formulaMap.get("lh_total_stock_returned_no"), 1));
					}

					if(formulaMap.keySet().contains("lh_total_stock_damaged_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setLhTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getLhTotalStockDamagedNo(), formulaMap.get("lh_total_stock_damaged_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setLhTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getLhTotalStockDamagedNo(), formulaMap.get("lh_total_stock_damaged_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("lh_total_stock_sold_no")) {
						pHubAc.setLhTotalStockSoldNo(PPOAlgoUtil.compute(pHubAc.getLhTotalStockSoldNo(), formulaMap.get("lh_total_stock_sold_no"), 1));
					}

					if(formulaMap.keySet().contains("lh_total_stock_sold_discount_no")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setLhTotalStockSoldDiscountNo(PPOAlgoUtil.compute(pHubAc.getLhTotalStockSoldDiscountNo(), formulaMap.get("lh_total_stock_sold_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("lh_current_stock_no")) {
						pHubAc.setLhCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockNo(), formulaMap.get("lh_current_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("lh_current_stock_discount_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setLhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockDiscountNo(), formulaMap.get("lh_current_stock_discount_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setLhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockDiscountNo(), formulaMap.get("lh_current_stock_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("lh_current_stock_unsettled_no")) {
						pHubAc.setLhCurrentStockUnsettledNo(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockUnsettledNo(), formulaMap.get("lh_current_stock_unsettled_no"), 1));
					}

					if(formulaMap.keySet().contains("lh_current_stock_to_return_no")) {
						pHubAc.setLhCurrentStockToReturnNo(PPOAlgoUtil.compute(pHubAc.getLhCurrentStockToReturnNo(), formulaMap.get("lh_current_stock_to_return_no"), 1));
					}

					if(formulaMap.keySet().contains("lh_return_counter")) {
						pHubAc.setLhReturnCounter(PPOAlgoUtil.compute(pHubAc.getLhReturnCounter(), formulaMap.get("lh_return_counter"), 1));
					}
				}

				// ******************* Sub-Local Hub - Total *******************
				if(authAvaliable && authRole.getBelongTo().equals(EnumConstMr.GroupType_Sub_Local_HUB)) {

					if(formulaMap.keySet().contains("a_slh_total_collected_am")) {
						pHubAc.setSlhTotalCollectedAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalCollectedAm(), formulaMap.get("a_slh_total_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_slh_total_paid_collected_am")) {
						pHubAc.setSlhTotalPaidCollectedAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalPaidCollectedAm(), formulaMap.get("a_slh_total_paid_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_slh_current_collected_am")) {
						pHubAc.setSlhCurrentCollectedAm(PPOAlgoUtil.compute(pHubAc.getSlhCurrentCollectedAm(), formulaMap.get("a_slh_current_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_slh_current_stock_am")) {
						pHubAc.setSlhCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockAm(), formulaMap.get("a_slh_current_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_slh_current_stock_discount_am")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSlhCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockDiscountAm(), formulaMap.get("a_slh_current_stock_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("a_slh_current_stock_no")) {
						pHubAc.setSlhCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockNo(), formulaMap.get("a_slh_current_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("a_slh_current_stock_discount_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSlhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockDiscountNo(), formulaMap.get("a_slh_current_stock_discount_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSlhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockDiscountNo(), formulaMap.get("a_slh_current_stock_discount_no"), 1));
							}
						}
					}
				}

				if(ownerRole.getBelongTo().equals(EnumConstMr.GroupType_Sub_Local_HUB)) {

					if(formulaMap.keySet().contains("slh_total_collected_am")) {
						pHubAc.setSlhTotalCollectedAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalCollectedAm(), formulaMap.get("slh_total_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_total_paid_collected_am")) {
						pHubAc.setSlhTotalPaidCollectedAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalPaidCollectedAm(), formulaMap.get("slh_total_paid_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_current_collected_am")) {
						pHubAc.setSlhCurrentCollectedAm(PPOAlgoUtil.compute(pHubAc.getSlhCurrentCollectedAm(), formulaMap.get("slh_current_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_sold_paid_am")) {
						pHubAc.setSlhSoldPaidAm(PPOAlgoUtil.compute(pHubAc.getSlhSoldPaidAm(), formulaMap.get("slh_sold_paid_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_sold_pending_am")) {
						if(item != null) {
							pHubAc.setSlhSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getSlhSoldPendingAm(), formulaMap.get("slh_sold_pending_am"), item.getSoldPrice()));
						} else {
							pHubAc.setSlhSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getSlhSoldPendingAm(), formulaMap.get("slh_sold_pending_am"), stockTx.getAmount()));
						}
					}

					if(formulaMap.keySet().contains("slh_total_stock_am")) {
						pHubAc.setSlhTotalStockAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockAm(), formulaMap.get("slh_total_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_total_stock_returned_am")) {
						pHubAc.setSlhTotalStockReturnedAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockReturnedAm(), formulaMap.get("slh_total_stock_returned_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_total_stock_damaged_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSlhTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockDamagedAm(), formulaMap.get("slh_total_stock_damaged_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSlhTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockDamagedAm(), formulaMap.get("slh_total_stock_damaged_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("slh_total_stock_sold_am")) {
						pHubAc.setSlhTotalStockSoldAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockSoldAm(), formulaMap.get("slh_total_stock_sold_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_total_stock_sold_discount_am")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSlhTotalStockSoldDiscountAm(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockSoldDiscountAm(), formulaMap.get("slh_total_stock_sold_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("slh_current_stock_am")) {
						pHubAc.setSlhCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockAm(), formulaMap.get("slh_current_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_current_stock_discount_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSlhCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockDiscountAm(), formulaMap.get("slh_current_stock_discount_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSlhCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockDiscountAm(), formulaMap.get("slh_current_stock_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("slh_current_stock_unsettled_am")) {
						pHubAc.setSlhCurrentStockUnsettledAm(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockUnsettledAm(), formulaMap.get("slh_current_stock_unsettled_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_current_stock_to_return_am")) {
						pHubAc.setSlhCurrentStockToReturnAm(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockToReturnAm(), formulaMap.get("slh_current_stock_to_return_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("slh_total_stock_no")) {
						pHubAc.setSlhTotalStockNo(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockNo(), formulaMap.get("slh_total_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("slh_total_stock_returned_no")) {
						pHubAc.setSlhTotalStockReturnedNo(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockReturnedNo(), formulaMap.get("slh_total_stock_returned_no"), 1));
					}

					if(formulaMap.keySet().contains("slh_total_stock_damaged_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSlhTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockDamagedNo(), formulaMap.get("slh_total_stock_damaged_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSlhTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockDamagedNo(), formulaMap.get("slh_total_stock_damaged_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("slh_total_stock_sold_no")) {
						pHubAc.setSlhTotalStockSoldNo(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockSoldNo(), formulaMap.get("slh_total_stock_sold_no"), 1));
					}

					if(formulaMap.keySet().contains("slh_total_stock_sold_discount_no")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSlhTotalStockSoldDiscountNo(PPOAlgoUtil.compute(pHubAc.getSlhTotalStockSoldDiscountNo(), formulaMap.get("slh_total_stock_sold_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("slh_current_stock_no")) {
						pHubAc.setSlhCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockNo(), formulaMap.get("slh_current_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("slh_current_stock_discount_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSlhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockDiscountNo(), formulaMap.get("slh_current_stock_discount_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSlhCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockDiscountNo(), formulaMap.get("slh_current_stock_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("slh_current_stock_unsettled_no")) {
						pHubAc.setSlhCurrentStockUnsettledNo(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockUnsettledNo(), formulaMap.get("slh_current_stock_unsettled_no"), 1));
					}

					if(formulaMap.keySet().contains("slh_current_stock_to_return_no")) {
						pHubAc.setSlhCurrentStockToReturnNo(PPOAlgoUtil.compute(pHubAc.getSlhCurrentStockToReturnNo(), formulaMap.get("slh_current_stock_to_return_no"), 1));
					}

					if(formulaMap.keySet().contains("slh_return_counter")) {
						pHubAc.setSlhReturnCounter(PPOAlgoUtil.compute(pHubAc.getSlhReturnCounter(), formulaMap.get("slh_return_counter"), 1));
					}
				}

				// ******************* Sales Executive - Total *******************
				if(authAvaliable && authRole.getBelongTo().equals(EnumConstMr.GroupType_Sales_Executive)) {

					if(formulaMap.keySet().contains("a_se_total_collected_am")) {
						pHubAc.setSeTotalCollectedAm(PPOAlgoUtil.compute(pHubAc.getSeTotalCollectedAm(), formulaMap.get("a_se_total_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_se_total_paid_collected_am")) {
						pHubAc.setSeTotalPaidCollectedAm(PPOAlgoUtil.compute(pHubAc.getSeTotalPaidCollectedAm(), formulaMap.get("a_se_total_paid_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_se_current_collected_am")) {
						pHubAc.setSeCurrentCollectedAm(PPOAlgoUtil.compute(pHubAc.getSeCurrentCollectedAm(), formulaMap.get("a_se_current_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_se_current_stock_am")) {
						pHubAc.setSeCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockAm(), formulaMap.get("a_se_current_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("a_se_current_stock_discount_am")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSeCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockDiscountAm(), formulaMap.get("a_se_current_stock_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("a_se_current_stock_no")) {
						pHubAc.setSeCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockNo(), formulaMap.get("a_se_current_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("a_se_current_stock_discount_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSeCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockDiscountNo(), formulaMap.get("a_se_current_stock_discount_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSeCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockDiscountNo(), formulaMap.get("a_se_current_stock_discount_no"), 1));
							}
						}
					}
				}

				if(ownerRole.getBelongTo().equals(EnumConstMr.GroupType_Sales_Executive)) {

					if(formulaMap.keySet().contains("se_total_collected_am")) {
						pHubAc.setSeTotalCollectedAm(PPOAlgoUtil.compute(pHubAc.getSeTotalCollectedAm(), formulaMap.get("se_total_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_total_paid_collected_am")) {
						pHubAc.setSeTotalPaidCollectedAm(PPOAlgoUtil.compute(pHubAc.getSeTotalPaidCollectedAm(), formulaMap.get("se_total_paid_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_current_collected_am")) {
						pHubAc.setSeCurrentCollectedAm(PPOAlgoUtil.compute(pHubAc.getSeCurrentCollectedAm(), formulaMap.get("se_current_collected_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_sold_paid_am")) {
						pHubAc.setSeSoldPaidAm(PPOAlgoUtil.compute(pHubAc.getSeSoldPaidAm(), formulaMap.get("se_sold_paid_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_sold_pending_am")) {
						if(item != null) {
							pHubAc.setSeSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getSeSoldPendingAm(), formulaMap.get("se_sold_pending_am"), item.getSoldPrice()));
						} else {
							pHubAc.setSeSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getSeSoldPendingAm(), formulaMap.get("se_sold_pending_am"), stockTx.getAmount()));
						}
					}

					if(formulaMap.keySet().contains("se_total_stock_am")) {
						pHubAc.setSeTotalStockAm(PPOAlgoUtil.compute(pHubAc.getSeTotalStockAm(), formulaMap.get("se_total_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_total_stock_returned_am")) {
						pHubAc.setSeTotalStockReturnedAm(PPOAlgoUtil.compute(pHubAc.getSeTotalStockReturnedAm(), formulaMap.get("se_total_stock_returned_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_total_stock_damaged_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSeTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getSeTotalStockDamagedAm(), formulaMap.get("se_total_stock_damaged_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSeTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getSeTotalStockDamagedAm(), formulaMap.get("se_total_stock_damaged_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("se_total_stock_sold_am")) {
						pHubAc.setSeTotalStockSoldAm(PPOAlgoUtil.compute(pHubAc.getSeTotalStockSoldAm(), formulaMap.get("se_total_stock_sold_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_total_stock_sold_discount_am")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSeTotalStockSoldDiscountAm(PPOAlgoUtil.compute(pHubAc.getSeTotalStockSoldDiscountAm(), formulaMap.get("se_total_stock_sold_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("se_current_stock_am")) {
						pHubAc.setSeCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockAm(), formulaMap.get("se_current_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_current_stock_discount_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSeCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockDiscountAm(), formulaMap.get("se_current_stock_discount_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSeCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockDiscountAm(), formulaMap.get("se_current_stock_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("se_current_stock_unsettled_am")) {
						pHubAc.setSeCurrentStockUnsettledAm(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockUnsettledAm(), formulaMap.get("se_current_stock_unsettled_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_current_stock_to_return_am")) {
						pHubAc.setSeCurrentStockToReturnAm(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockToReturnAm(), formulaMap.get("se_current_stock_to_return_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("se_total_stock_no")) {
						pHubAc.setSeTotalStockNo(PPOAlgoUtil.compute(pHubAc.getSeTotalStockNo(), formulaMap.get("se_total_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("se_total_stock_returned_no")) {
						pHubAc.setSeTotalStockReturnedNo(PPOAlgoUtil.compute(pHubAc.getSeTotalStockReturnedNo(), formulaMap.get("se_total_stock_returned_no"), 1));
					}

					if(formulaMap.keySet().contains("se_total_stock_damaged_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSeTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getSeTotalStockDamagedNo(), formulaMap.get("se_total_stock_damaged_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSeTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getSeTotalStockDamagedNo(), formulaMap.get("se_total_stock_damaged_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("se_total_stock_sold_no")) {
						pHubAc.setSeTotalStockSoldNo(PPOAlgoUtil.compute(pHubAc.getSeTotalStockSoldNo(), formulaMap.get("se_total_stock_sold_no"), 1));
					}

					if(formulaMap.keySet().contains("se_total_stock_sold_discount_no")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSeTotalStockSoldDiscountNo(PPOAlgoUtil.compute(pHubAc.getSeTotalStockSoldDiscountNo(), formulaMap.get("se_total_stock_sold_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("se_current_stock_no")) {
						pHubAc.setSeCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockNo(), formulaMap.get("se_current_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("se_current_stock_discount_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setSeCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockDiscountNo(), formulaMap.get("se_current_stock_discount_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setSeCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockDiscountNo(), formulaMap.get("se_current_stock_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("se_current_stock_unsettled_no")) {
						pHubAc.setSeCurrentStockUnsettledNo(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockUnsettledNo(), formulaMap.get("se_current_stock_unsettled_no"), 1));
					}

					if(formulaMap.keySet().contains("se_current_stock_to_return_no")) {
						pHubAc.setSeCurrentStockToReturnNo(PPOAlgoUtil.compute(pHubAc.getSeCurrentStockToReturnNo(), formulaMap.get("se_current_stock_to_return_no"), 1));
					}

					if(formulaMap.keySet().contains("se_return_counter")) {
						pHubAc.setSeReturnCounter(PPOAlgoUtil.compute(pHubAc.getSeReturnCounter(), formulaMap.get("se_return_counter"), 1));
					}
				}

				// ******************* Micro Retailer - Total *******************
				if(ownerRole.getBelongTo().equals(EnumConstMr.GroupType_Micro_Retailer)) {

					if(formulaMap.keySet().contains("mr_registration_paid_am")) {
						pHubAc.setMrRegistrationPaidAm(PPOAlgoUtil.compute(pHubAc.getMrRegistrationPaidAm(), formulaMap.get("mr_registration_paid_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_registration_pending_am")) {
						pHubAc.setMrRegistrationPendingAm(PPOAlgoUtil.compute(pHubAc.getMrRegistrationPendingAm(), formulaMap.get("mr_registration_pending_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_deposit_paid_am")) {
						pHubAc.setMrDepositPaidAm(PPOAlgoUtil.compute(pHubAc.getMrDepositPaidAm(), formulaMap.get("mr_deposit_paid_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_deposit_pending_am")) {
						pHubAc.setMrDepositPendingAm(PPOAlgoUtil.compute(pHubAc.getMrDepositPendingAm(), formulaMap.get("mr_deposit_pending_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_deposit_return_am")) {
						pHubAc.setMrDepositReturnAm(PPOAlgoUtil.compute(pHubAc.getMrDepositReturnAm(), formulaMap.get("mr_deposit_return_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_credit_limit_am")) {
						pHubAc.setMrCreditLimitAm(PPOAlgoUtil.compute(pHubAc.getMrCreditLimitAm(), formulaMap.get("mr_credit_limit_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_sold_paid_am")) {
						pHubAc.setMrSoldPaidAm(PPOAlgoUtil.compute(pHubAc.getMrSoldPaidAm(), formulaMap.get("mr_sold_paid_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_sold_pending_am")) {
						if(item != null) {
							pHubAc.setMrSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getMrSoldPendingAm(), formulaMap.get("mr_sold_pending_am"), item.getSoldPrice()));
						} else {
							pHubAc.setMrSoldPendingAm(PPOAlgoUtil.compute(pHubAc.getMrSoldPendingAm(), formulaMap.get("mr_sold_pending_am"), stockTx.getAmount()));
						}
					}

					if(formulaMap.keySet().contains("mr_paid_interest_penalty_am")) {
						pHubAc.setMrPaidInterestPenaltyAm(PPOAlgoUtil.compute(pHubAc.getMrPaidInterestPenaltyAm(), formulaMap.get("mr_paid_interest_penalty_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_pending_interest_penalty_am")) {
						pHubAc.setMrPendingInterestPenaltyAm(PPOAlgoUtil.compute(pHubAc.getMrPendingInterestPenaltyAm(), formulaMap.get("mr_pending_interest_penalty_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_total_stock_am")) {
						pHubAc.setMrTotalStockAm(PPOAlgoUtil.compute(pHubAc.getMrTotalStockAm(), formulaMap.get("mr_total_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_total_stock_returned_am")) {
						pHubAc.setMrTotalStockReturnedAm(PPOAlgoUtil.compute(pHubAc.getMrTotalStockReturnedAm(), formulaMap.get("mr_total_stock_returned_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_total_stock_damaged_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setMrTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getMrTotalStockDamagedAm(), formulaMap.get("mr_total_stock_damaged_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMrTotalStockDamagedAm(PPOAlgoUtil.compute(pHubAc.getMrTotalStockDamagedAm(), formulaMap.get("mr_total_stock_damaged_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("mr_total_stock_sold_am")) {
						pHubAc.setMrTotalStockSoldAm(PPOAlgoUtil.compute(pHubAc.getMrTotalStockSoldAm(), formulaMap.get("mr_total_stock_sold_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_total_stock_sold_discount_am")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMrTotalStockSoldDiscountAm(PPOAlgoUtil.compute(pHubAc.getMrTotalStockSoldDiscountAm(), formulaMap.get("mr_total_stock_sold_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("mr_current_stock_am")) {
						pHubAc.setMrCurrentStockAm(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockAm(), formulaMap.get("mr_current_stock_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_current_stock_discount_am")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setMrCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockDiscountAm(), formulaMap.get("mr_current_stock_discount_am"), 
									BDUtil.sub(stockTx.getAmount(), stockTx.getExtraAm())));
						} else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMrCurrentStockDiscountAm(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockDiscountAm(), formulaMap.get("mr_current_stock_discount_am"), item.getDiscountAm()));
							}
						}
					}

					if(formulaMap.keySet().contains("mr_current_stock_unsettled_am")) {
						pHubAc.setMrCurrentStockUnsettledAm(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockUnsettledAm(), formulaMap.get("mr_current_stock_unsettled_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_current_stock_to_return_am")) {
						pHubAc.setMrCurrentStockToReturnAm(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockToReturnAm(), formulaMap.get("mr_current_stock_to_return_am"), stockTx.getAmount()));
					}

					if(formulaMap.keySet().contains("mr_total_stock_no")) {
						pHubAc.setMrTotalStockNo(PPOAlgoUtil.compute(pHubAc.getMrTotalStockNo(), formulaMap.get("mr_total_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("mr_total_stock_returned_no")) {
						pHubAc.setMrTotalStockReturnedNo(PPOAlgoUtil.compute(pHubAc.getMrTotalStockReturnedNo(), formulaMap.get("mr_total_stock_returned_no"), 1));
					}

					if(formulaMap.keySet().contains("mr_total_stock_damaged_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setMrTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getMrTotalStockDamagedNo(), formulaMap.get("mr_total_stock_damaged_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMrTotalStockDamagedNo(PPOAlgoUtil.compute(pHubAc.getMrTotalStockDamagedNo(), formulaMap.get("mr_total_stock_damaged_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("mr_total_stock_sold_no")) {
						pHubAc.setMrTotalStockSoldNo(PPOAlgoUtil.compute(pHubAc.getMrTotalStockSoldNo(), formulaMap.get("mr_total_stock_sold_no"), 1));
					}

					if(formulaMap.keySet().contains("mr_total_stock_sold_discount_no")) {
						if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMrTotalStockSoldDiscountNo(PPOAlgoUtil.compute(pHubAc.getMrTotalStockSoldDiscountNo(), formulaMap.get("mr_total_stock_sold_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("mr_current_stock_no")) {
						pHubAc.setMrCurrentStockNo(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockNo(), formulaMap.get("mr_current_stock_no"), 1));
					}

					if(formulaMap.keySet().contains("mr_current_stock_discount_no")) {
						if(EnumCacheMr.getNameOfStockTxType(stockTx.getStockTxTypeId()).equals(EnumConstMr.StockTxType_Discounted)) {
							pHubAc.setMrCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockDiscountNo(), formulaMap.get("mr_current_stock_discount_no"), addDiscountSrockNo));
						}
						else if(item != null) {
							if(item.getDiscountAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								pHubAc.setMrCurrentStockDiscountNo(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockDiscountNo(), formulaMap.get("mr_current_stock_discount_no"), 1));
							}
						}
					}

					if(formulaMap.keySet().contains("mr_current_stock_unsettled_no")) {
						pHubAc.setMrCurrentStockUnsettledNo(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockUnsettledNo(), formulaMap.get("mr_current_stock_unsettled_no"), 1));
					}

					if(formulaMap.keySet().contains("mr_current_stock_to_return_no")) {
						pHubAc.setMrCurrentStockToReturnNo(PPOAlgoUtil.compute(pHubAc.getMrCurrentStockToReturnNo(), formulaMap.get("mr_current_stock_to_return_no"), 1));
					}

					if(formulaMap.keySet().contains("mr_return_counter")) {
						pHubAc.setMrReturnCounter(PPOAlgoUtil.compute(pHubAc.getMrReturnCounter(), formulaMap.get("mr_return_counter"), 1));
					}
				}

				pJob.getDaoFactory().getPHubAcDAO().merge(pHubAc);

			} catch (Exception e) {
				log.error(e.toString() + "; for PHubAc update with StockItemId: " + stockTx.getStockItemId() + "; for StockTx Id: " + stockTx.getStockTxId());
			}
		}
	}

}