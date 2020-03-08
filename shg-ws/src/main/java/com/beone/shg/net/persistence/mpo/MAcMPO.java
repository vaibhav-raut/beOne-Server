package com.beone.shg.net.persistence.mpo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.MessageJob;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;

@Repository("mAcMPO")
public class MAcMPO extends IMProcessing {

	private static final Logger log = LoggerFactory.getLogger(MAcMPO.class);

	@Override
	public void processMessageFormula(MessageJob mJob, Map<String,String> formulaMap, String lang) {

		MAc mAccount = null;

		if(mJob.getRelatedObject().containsKey(Tx.class)) {
			Tx transaction = (Tx)mJob.getRelatedObject().get(Tx.class);
			mAccount = mJob.getDaoFactory().getMAcDAO().findById(transaction.getMemberAcNo());
		}
		if(mAccount != null && mJob.getRelatedObject().containsKey(MAc.class)) {
			MAc mAc = (MAc)mJob.getRelatedObject().get(MAc.class);
			mAccount = mJob.getDaoFactory().getMAcDAO().findById(mAc.getMAcNo());
		}
		if(mAccount != null && mJob.getRelatedObject().containsKey(MProfile.class)) {
			MProfile mProfile = (MProfile)mJob.getRelatedObject().get(MProfile.class);
			mAccount = mJob.getDaoFactory().getMAcDAO().findById(mProfile.getMemberAcNo());
		}
		if(mAccount != null && mJob.getRelatedObject().containsKey(MLoanAc.class)) {
			MLoanAc mLoanAc = (MLoanAc)mJob.getRelatedObject().get(MLoanAc.class);
			mAccount = mJob.getDaoFactory().getMAcDAO().findById(mLoanAc.getMAc().getMAcNo());
		}

		if(mAccount != null) {
			try {

				if(formulaMap.keySet().contains("planned_monthly_saving_am")) {
					formulaMap.put("planned_monthly_saving_am", DataUtil.toString(mAccount.getPlannedMonthlySavingAm()));
				}

				if(formulaMap.keySet().contains("saved_am")) {
					formulaMap.put("saved_am", DataUtil.toString(mAccount.getSavedAm()));
				}

				if(formulaMap.keySet().contains("outstanding_saving_am")) {
					formulaMap.put("outstanding_saving_am", DataUtil.toString(mAccount.getOutstandingSavedAm()));
				}

				if(formulaMap.keySet().contains("prov_int_en_am")) {
					formulaMap.put("prov_int_en_am", DataUtil.toString(mAccount.getProvIntEnAm()));
				}

				if(formulaMap.keySet().contains("prev_month_int_en_am")) {
					formulaMap.put("prev_month_int_en_am", DataUtil.toString(mAccount.getPrevMonthIntEnAm()));
				}

				if(formulaMap.keySet().contains("returned_saved_am")) {
					formulaMap.put("returned_saved_am", DataUtil.toString(mAccount.getReturnedSavedAm()));
				}

				if(formulaMap.keySet().contains("returned_int_en_am")) {
					formulaMap.put("returned_int_en_am", DataUtil.toString(mAccount.getReturnedIntEnAm()));
				}

				if(formulaMap.keySet().contains("divided_profit_declared_am")) {
					formulaMap.put("divided_profit_declared_am", DataUtil.toString(mAccount.getDividedProfitDeclaredAm()));
				}

				if(formulaMap.keySet().contains("divided_profit_paid_am")) {
					formulaMap.put("divided_profit_paid_am", DataUtil.toString(mAccount.getDividedProfitPaidAm()));
				}

				if(formulaMap.keySet().contains("no_of_loans")) {
					formulaMap.put("no_of_loans", DataUtil.toString(mAccount.getNoOfLoans()));
				}

				if(formulaMap.keySet().contains("no_of_active_loans")) {
					formulaMap.put("no_of_active_loans", DataUtil.toString(mAccount.getNoOfActiveLoans()));
				}

				if(formulaMap.keySet().contains("loan_am")) {
					formulaMap.put("loan_am", DataUtil.toString(mAccount.getLoanAm()));
				}

				if(formulaMap.keySet().contains("rec_loan_am")) {
					formulaMap.put("rec_loan_am", DataUtil.toString(mAccount.getRecLoanAm()));
				}

				if(formulaMap.keySet().contains("rec_int_on_loan_am")) {
					formulaMap.put("rec_int_on_loan_am", DataUtil.toString(mAccount.getRecIntOnLoanAm()));
				}

				if(formulaMap.keySet().contains("proj_int_on_loan_am")) {
					formulaMap.put("proj_int_on_loan_am", DataUtil.toString(mAccount.getProjIntOnLoanAm()));
				}

				if(formulaMap.keySet().contains("bad_dept_closed_am")) {
					formulaMap.put("bad_dept_closed_am", DataUtil.toString(mAccount.getBadDeptClosedAm()));
				}

				if(formulaMap.keySet().contains("rec_penalty_am")) {
					formulaMap.put("rec_penalty_am", DataUtil.toString(mAccount.getRecPenaltyAm()));
				}

				if(formulaMap.keySet().contains("pending_penalty_am")) {
					formulaMap.put("pending_penalty_am", DataUtil.toString(mAccount.getPendingPenaltyAm()));
				}

				if(formulaMap.keySet().contains("last_updated_ts")) {
					formulaMap.put("last_updated_ts", DateUtil.getDisplaySMSDateStr(mAccount.getLastUpdatedTs()));
				}

				if(formulaMap.keySet().contains("name")) {
					formulaMap.put("name", mJob.getDaoFactory().getMemberContactDAO().getShortNameOfMember(lang, mAccount.getMAcNo()));
				}

			} catch (Exception e) {
				log.error(e.toString() + "; for MemberAcNo: " + mAccount.getMAcNo());
			}
		}
	}

}