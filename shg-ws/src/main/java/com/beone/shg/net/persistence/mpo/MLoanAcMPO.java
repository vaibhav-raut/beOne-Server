package com.beone.shg.net.persistence.mpo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.MessageJob;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.util.DataUtil;

@Repository("mLoanAcMPO")
public class MLoanAcMPO extends IMProcessing {

	private static final Logger log = LoggerFactory.getLogger(MLoanAcMPO.class);

	@Override
	public void processMessageFormula(MessageJob mJob, Map<String,String> formulaMap, String lang) {

		MLoanAc mLoanAc = null;

		if(mJob.getRelatedObject().containsKey(Tx.class)) {
			Tx transaction = (Tx)mJob.getRelatedObject().get(Tx.class);
			mLoanAc = mJob.getDaoFactory().getMLoanAcDAO().findById(transaction.getMemberLoanAcNo());
		}
		if(mLoanAc != null && mJob.getRelatedObject().containsKey(MLoanAc.class)) {
			mLoanAc = (MLoanAc)mJob.getRelatedObject().get(MLoanAc.class);
		}

		if(mLoanAc != null) {

			try {

				if(formulaMap.keySet().contains("principle_am")) {
					formulaMap.put("principle_am", DataUtil.toString(mLoanAc.getPrincipleAm()));
				}

				if(formulaMap.keySet().contains("pending_principle_am")) {
					formulaMap.put("pending_principle_am", DataUtil.toString(mLoanAc.getPendingPrincipleAm()));
				}

				if(formulaMap.keySet().contains("rec_interest_am")) {
					formulaMap.put("rec_interest_am", DataUtil.toString(mLoanAc.getRecInterestAm()));
				}

				if(formulaMap.keySet().contains("proj_interest_am")) {
					formulaMap.put("proj_interest_am", DataUtil.toString(mLoanAc.getProjInterestAm()));
				}

				if(formulaMap.keySet().contains("pre_emi_interest_am")) {
					formulaMap.put("pre_emi_interest_am", DataUtil.toString(mLoanAc.getPreEmiInterestAm()));
				}

				if(formulaMap.keySet().contains("pending_interest_due_am")) {
					formulaMap.put("pending_interest_due_am", DataUtil.toString(mLoanAc.getPendingInterestDueAm()));
				}

				if(formulaMap.keySet().contains("exp_no_of_inst")) {
					formulaMap.put("exp_no_of_inst", DataUtil.toString(mLoanAc.getExpNoOfInst()));
				}

				if(formulaMap.keySet().contains("no_of_inst_paid")) {
					formulaMap.put("no_of_inst_paid", DataUtil.toString(mLoanAc.getNoOfInstPaid()));
				}

				if(formulaMap.keySet().contains("no_of_insall_late")) {
					formulaMap.put("no_of_insall_late", DataUtil.toString(mLoanAc.getNoOfInsallLate()));
				}

			} catch (Exception e) {
				log.error(e.toString() + "; for MemberAcNo: " + mLoanAc.getMAc().getMAcNo());
			}
		}
	}
}