package com.beone.shg.net.persistence.ppo;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.PostProcessJob;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.support.EnumCache;

@Repository("mAcPPO")
public class MAcPPO extends IProcessing {

	private static final Logger log = LoggerFactory.getLogger(MAcPPO.class);

	@Override
	public void processUpdateFormula(PostProcessJob pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(Tx.class)) {

			Tx transaction = (Tx)pJob.getRelatedObject().get(Tx.class);

			try {
				BigDecimal amount = transaction.getAmount();
				MAc mAccount = pJob.getDaoFactory().getMAcDAO().findById(transaction.getMemberAcNo());

				if(formulaMap.keySet().contains("planned_monthly_saving_am")) {
					mAccount.setPlannedMonthlySavingAm(PPOAlgoUtil.compute(mAccount.getPlannedMonthlySavingAm(), formulaMap.get("planned_monthly_saving_am"), amount));
				}

				if(formulaMap.keySet().contains("saved_am")) {
					mAccount.setSavedAm(PPOAlgoUtil.compute(mAccount.getSavedAm(), formulaMap.get("saved_am"), amount));
				}

				if(formulaMap.keySet().contains("outstanding_saving_am")) {
					mAccount.setOutstandingSavedAm(PPOAlgoUtil.compute(mAccount.getOutstandingSavedAm(), formulaMap.get("outstanding_saving_am"), amount));
				}

				if(formulaMap.keySet().contains("prov_int_en_am")) {
					mAccount.setProvIntEnAm(PPOAlgoUtil.compute(mAccount.getProvIntEnAm(), formulaMap.get("prov_int_en_am"), amount));
				}

				if(formulaMap.keySet().contains("prev_month_int_en_am")) {
					mAccount.setPrevMonthIntEnAm(PPOAlgoUtil.compute(mAccount.getPrevMonthIntEnAm(), formulaMap.get("prev_month_int_en_am"), amount));
				}

				if(formulaMap.keySet().contains("returned_saved_am")) {
					mAccount.setReturnedSavedAm(PPOAlgoUtil.compute(mAccount.getReturnedSavedAm(), formulaMap.get("returned_saved_am"), amount));
				}

				if(formulaMap.keySet().contains("returned_int_en_am")) {
					mAccount.setReturnedIntEnAm(PPOAlgoUtil.compute(mAccount.getReturnedIntEnAm(), formulaMap.get("returned_int_en_am"), amount));
				}

				if(formulaMap.keySet().contains("divided_profit_declared_am")) {
					mAccount.setDividedProfitDeclaredAm(PPOAlgoUtil.compute(mAccount.getDividedProfitDeclaredAm(), formulaMap.get("divided_profit_declared_am"), amount));
				}

				if(formulaMap.keySet().contains("divided_profit_paid_am")) {
					mAccount.setDividedProfitPaidAm(PPOAlgoUtil.compute(mAccount.getDividedProfitPaidAm(), formulaMap.get("divided_profit_paid_am"), amount));
				}

				if(formulaMap.keySet().contains("no_of_loans")) {
					mAccount.setNoOfLoans(PPOAlgoUtil.compute(mAccount.getNoOfLoans(), formulaMap.get("no_of_loans"), 1));
				}

				if(formulaMap.keySet().contains("no_of_active_loans")) {
					mAccount.setNoOfActiveLoans(PPOAlgoUtil.compute(mAccount.getNoOfActiveLoans(), formulaMap.get("no_of_active_loans"), 1));
				}

				if(formulaMap.keySet().contains("loan_am")) {
					mAccount.setLoanAm(PPOAlgoUtil.compute(mAccount.getLoanAm(), formulaMap.get("loan_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_loan_am")) {
					mAccount.setRecLoanAm(PPOAlgoUtil.compute(mAccount.getRecLoanAm(), formulaMap.get("rec_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_int_on_loan_am")) {
					mAccount.setRecIntOnLoanAm(PPOAlgoUtil.compute(mAccount.getRecIntOnLoanAm(), formulaMap.get("rec_int_on_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("proj_int_on_loan_am")) {
					mAccount.setProjIntOnLoanAm(PPOAlgoUtil.compute(mAccount.getProjIntOnLoanAm(), formulaMap.get("proj_int_on_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("bad_dept_closed_am")) {
					mAccount.setBadDeptClosedAm(PPOAlgoUtil.compute(mAccount.getBadDeptClosedAm(), formulaMap.get("bad_dept_closed_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_penalty_am")) {
					mAccount.setRecPenaltyAm(PPOAlgoUtil.compute(mAccount.getRecPenaltyAm(), formulaMap.get("rec_penalty_am"), amount));
				}

				if(formulaMap.keySet().contains("pending_penalty_am")) {
					mAccount.setPendingPenaltyAm(PPOAlgoUtil.compute(mAccount.getPendingPenaltyAm(), formulaMap.get("pending_penalty_am"), amount));
				}

				if(formulaMap.keySet().contains("last_updated_ts")) {
					mAccount.setLastUpdatedTs(PPOAlgoUtil.compute(mAccount.getLastUpdatedTs(), formulaMap.get("last_updated_ts"), null));
				}

				pJob.getDaoFactory().getMAcDAO().merge(mAccount);

			} catch (Exception e) {
				log.error(e.toString() + "; for MemberAcNo: " + transaction.getMemberAcNo() + "; for Tx Id: " + transaction.getTxId() + "; for TxType: " + EnumCache.getNameOfTxType(transaction.getTxTypeId()));
			}
		}
	}

}