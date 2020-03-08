package com.beone.shg.net.persistence.ppo;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.job.PostProcessJob;
import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.support.EnumCache;

@Repository("gBankAccountPPO")
public class GBankAccountPPO extends IProcessing {

	private static final Logger log = LoggerFactory.getLogger(GBankAccountPPO.class);

	@Override
	public void processUpdateFormula(PostProcessJob pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(Tx.class)) {

			Tx tx = (Tx)pJob.getRelatedObject().get(Tx.class);
			try {
				// Update Balance in Transaction for Bank Payment
				if(tx.getGroupBankAccount() > 0 &&
						(!EnumCache.getNameOfEnumValue(EnumConst.PaymentMode, tx.getPaymentModeId()).equals(EnumConst.PaymentMode_CASH) ||
								EnumCache.getTxTypeValue(tx.getTxTypeId()).getTxCategory().equals(EnumConst.TxType_TRANSFER))) {
					
					BigDecimal amount = tx.getAmount();
					GBankAccount gBankAc = pJob.getDaoFactory().getGBankAccountDAO().findById(tx.getGroupBankAccount());


					if(formulaMap.keySet().contains("balance_update")) {			
						gBankAc.setSubjClearingBalanceAm(PPOAlgoUtil.compute(gBankAc.getSubjClearingBalanceAm(), formulaMap.get("balance_update"), amount));	
					}			

					if(formulaMap.keySet().contains("balance_approve")) {
						gBankAc.setClearBalanceAm(PPOAlgoUtil.compute(gBankAc.getClearBalanceAm(), formulaMap.get("balance_approve"), amount));

						gBankAc.setSubjClearingBalanceAm(PPOAlgoUtil.compute(gBankAc.getSubjClearingBalanceAm(), 
								PPOAlgoUtil.inverseAlgo(formulaMap.get("balance_approve")), amount));	
					}			

					if(formulaMap.keySet().contains("interest_am")) {			
						gBankAc.setInterestAm(PPOAlgoUtil.compute(gBankAc.getInterestAm(), formulaMap.get("interest_am"), amount));	
					}			

					if(formulaMap.keySet().contains("penalty_charges_am")) {			
						gBankAc.setPenaltyChargesAm(PPOAlgoUtil.compute(gBankAc.getPenaltyChargesAm(), formulaMap.get("penalty_charges_am"), amount));	
					}			

					pJob.getDaoFactory().getGBankAccountDAO().merge(gBankAc);
				}

			} catch (Exception e) {
				log.error(e.toString() + "; for GroupAcNo: " + tx.getGroupAcNo() + "; for Tx Id: " + tx.getTxId() + "; for TxType: " + EnumCache.getNameOfTxType(tx.getTxTypeId()));
			}
		}
	}
}