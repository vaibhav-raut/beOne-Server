package com.beone.shg.net.persistence.ppo;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.PostProcessJob;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DateUtil;

@Repository("mSavingAcPPO")
public class MSavingAcPPO extends IProcessing {

	private static final Logger log = LoggerFactory.getLogger(MSavingAcPPO.class);

	@Override
	public void processUpdateFormula(PostProcessJob pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(Tx.class)) {

			Tx transaction = (Tx)pJob.getRelatedObject().get(Tx.class);

			try {
				BigDecimal amount = transaction.getAmount();
				MSavingAc mSavingAc = pJob.getDaoFactory().getMSavingAcDAO().findById(transaction.getMemberSavingAcNo());

				if(formulaMap.keySet().contains("saved_am")) {
					mSavingAc.setSavedAm(PPOAlgoUtil.compute(mSavingAc.getSavedAm(), formulaMap.get("saved_am"), amount));
				}

				if(formulaMap.keySet().contains("cumulative_saved_am")) {
					mSavingAc.setCumulativeSavedAm(PPOAlgoUtil.compute(mSavingAc.getCumulativeSavedAm(), formulaMap.get("cumulative_saved_am"), amount));
				}

				if(formulaMap.keySet().contains("saving_installment_am")) {
					mSavingAc.setSavingInstallmentAm(PPOAlgoUtil.compute(mSavingAc.getSavingInstallmentAm(), formulaMap.get("saving_installment_am"), amount));
				}

				if(formulaMap.keySet().contains("total_int_en_am")) {
					mSavingAc.setTotalIntEnAm(PPOAlgoUtil.compute(mSavingAc.getTotalIntEnAm(), formulaMap.get("total_int_en_am"), amount));
				}

				if(formulaMap.keySet().contains("current_fy_int_en_am")) {
					mSavingAc.setCurrentFyIntEnAm(PPOAlgoUtil.compute(mSavingAc.getCurrentFyIntEnAm(), formulaMap.get("current_fy_int_en_am"), amount));
				}

				if(formulaMap.keySet().contains("prev_month_int_am")) {
					mSavingAc.setPrevMonthIntAm(PPOAlgoUtil.compute(mSavingAc.getPrevMonthIntAm(), formulaMap.get("prev_month_int_am"), amount));
				}

				if(formulaMap.keySet().contains("returned_saved_am")) {
					mSavingAc.setReturnedSavedAm(PPOAlgoUtil.compute(mSavingAc.getReturnedSavedAm(), formulaMap.get("returned_saved_am"), amount));
				}

				if(formulaMap.keySet().contains("returned_int_en_am")) {
					mSavingAc.setReturnedIntEnAm(PPOAlgoUtil.compute(mSavingAc.getReturnedIntEnAm(), formulaMap.get("returned_int_en_am"), amount));
				}

				if(formulaMap.keySet().contains("exp_no_of_inst")) {
					mSavingAc.setExpNoOfInst(PPOAlgoUtil.compute(mSavingAc.getExpNoOfInst(), formulaMap.get("exp_no_of_inst"), 1));
				}

				if(formulaMap.keySet().contains("no_of_inst_paid")) {
					mSavingAc.setNoOfInstPaid(PPOAlgoUtil.compute(mSavingAc.getNoOfInstPaid(), formulaMap.get("no_of_inst_paid"), 1));
				}

				if(formulaMap.keySet().contains("no_of_insall_late")) {

					GRules gRules = pJob.getDaoFactory().getGRulesDAO().findById(transaction.getGroupAcNo());

					// Check if the payment date is after Due date
					if(DateUtil.getDayOfMonth(transaction.getPaymentTs()) > gRules.getDueDayOfMonth()) {
						mSavingAc.setNoOfInsallLate(PPOAlgoUtil.compute(mSavingAc.getNoOfInsallLate(), formulaMap.get("no_of_insall_late"), 1));
					}
				}

				pJob.getDaoFactory().getMSavingAcDAO().merge(mSavingAc);
				
			} catch (Exception e) {
				log.error(e.toString() + "; for MemberAcNo: " + transaction.getMemberAcNo() + "; for Tx Id: " + transaction.getTxId() + "; for TxType: " + EnumCache.getNameOfTxType(transaction.getTxTypeId()));
			}
		}
	}
}