package com.beone.shg.net.persistence.ppo;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.PostProcessJob;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DateUtil;

@Repository("gLoanAcPPO")
public class GLoanAcPPO extends IProcessing {

	private static final Logger log = LoggerFactory.getLogger(GLoanAcPPO.class);

	@Override
	public void processUpdateFormula(PostProcessJob pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(Tx.class)) {

			Tx txDetached = (Tx)pJob.getRelatedObject().get(Tx.class);

			try {
				BigDecimal amount = txDetached.getAmount();
				GLoanAc gLoanAc = pJob.getDaoFactory().getGLoanAcDAO().findById(txDetached.getGroupLoanAcNo());

				if(formulaMap.keySet().contains("principle_am")) {
					gLoanAc.setPrincipleAm(PPOAlgoUtil.compute(gLoanAc.getPrincipleAm(), formulaMap.get("principle_am"), amount));
				}

				if(formulaMap.keySet().contains("pending_principle_am")) {
					gLoanAc.setPendingPrincipleAm(PPOAlgoUtil.compute(gLoanAc.getPendingPrincipleAm(), formulaMap.get("pending_principle_am"), amount));
				}

				if(formulaMap.keySet().contains("paid_interest_am")) {
					gLoanAc.setPaidInterestAm(PPOAlgoUtil.compute(gLoanAc.getPaidInterestAm(), formulaMap.get("paid_interest_am"), amount));
				}

				if(formulaMap.keySet().contains("proj_interest_am")) {
					gLoanAc.setProjInterestAm(PPOAlgoUtil.compute(gLoanAc.getProjInterestAm(), formulaMap.get("proj_interest_am"), amount));
				}

				if(formulaMap.keySet().contains("pre_emi_interest_am")) {
					gLoanAc.setPreEmiInterestAm(PPOAlgoUtil.compute(gLoanAc.getPreEmiInterestAm(), formulaMap.get("pre_emi_interest_am"), amount));
				}

				if(formulaMap.keySet().contains("pending_interest_due_am")) {
					gLoanAc.setPendingInterestDueAm(PPOAlgoUtil.compute(gLoanAc.getPendingInterestDueAm(), formulaMap.get("pending_interest_due_am"), amount));
				}

				if(formulaMap.keySet().contains("no_of_inst_paid")) {
					gLoanAc.setNoOfInstPaid(PPOAlgoUtil.compute(gLoanAc.getNoOfInstPaid(), formulaMap.get("no_of_inst_paid"), 1));
				}

				if(formulaMap.keySet().contains("no_of_insall_late")) {

					GRules gRules = pJob.getDaoFactory().getGRulesDAO().findById(txDetached.getGroupAcNo());

					// Check if the payment date is after Due date
					if(DateUtil.getDayOfMonth(txDetached.getPaymentTs()) > gRules.getDueDayOfMonth()) {
						gLoanAc.setNoOfInsallLate(PPOAlgoUtil.compute(gLoanAc.getNoOfInsallLate(), formulaMap.get("no_of_insall_late"), 1));
					}
				}

				pJob.getDaoFactory().getGLoanAcDAO().merge(gLoanAc);

			} catch (Exception e) {
				log.error(e.toString() + "; for GroupAcNo: " + txDetached.getGroupAcNo() + "; for Tx Id: " + txDetached.getTxId() + "; for TxType: " + EnumCache.getNameOfTxType(txDetached.getTxTypeId()));
			}
		}
	}
}