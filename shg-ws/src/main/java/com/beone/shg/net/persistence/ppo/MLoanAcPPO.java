package com.beone.shg.net.persistence.ppo;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.job.PostProcessJob;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

@Repository("mLoanAcPPO")
public class MLoanAcPPO extends IProcessing {

	private static final Logger log = LoggerFactory.getLogger(MLoanAcPPO.class);

	@Override
	public void processUpdateFormula(PostProcessJob pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(Tx.class)) {

			Tx txDetached = (Tx)pJob.getRelatedObject().get(Tx.class);

			try {
				BigDecimal amount = txDetached.getAmount();

				MLoanAc mLoanAc = pJob.getDaoFactory().getMLoanAcDAO().findById(txDetached.getMemberLoanAcNo());

				if(formulaMap.keySet().contains("principle_am")) {
					mLoanAc.setPrincipleAm(PPOAlgoUtil.compute(mLoanAc.getPrincipleAm(), formulaMap.get("principle_am"), amount));
				}

				if(formulaMap.keySet().contains("pending_principle_am")) {
					mLoanAc.setPendingPrincipleAm(PPOAlgoUtil.compute(mLoanAc.getPendingPrincipleAm(), formulaMap.get("pending_principle_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_interest_am")) {
					mLoanAc.setRecInterestAm(PPOAlgoUtil.compute(mLoanAc.getRecInterestAm(), formulaMap.get("rec_interest_am"), amount));
				}

				if(formulaMap.keySet().contains("proj_interest_am")) {
					mLoanAc.setProjInterestAm(PPOAlgoUtil.compute(mLoanAc.getProjInterestAm(), formulaMap.get("proj_interest_am"), amount));
				}

				if(formulaMap.keySet().contains("pre_emi_interest_am")) {
					mLoanAc.setPreEmiInterestAm(PPOAlgoUtil.compute(mLoanAc.getPreEmiInterestAm(), formulaMap.get("pre_emi_interest_am"), amount));
				}

				if(formulaMap.keySet().contains("pending_interest_due_am")) {
					mLoanAc.setPendingInterestDueAm(PPOAlgoUtil.compute(mLoanAc.getPendingInterestDueAm(), formulaMap.get("pending_interest_due_am"), amount));
				}

				if(formulaMap.keySet().contains("exp_no_of_inst")) {
					mLoanAc.setExpNoOfInst(PPOAlgoUtil.compute(mLoanAc.getExpNoOfInst(), formulaMap.get("exp_no_of_inst"), 1));
				}

				if(formulaMap.keySet().contains("no_of_inst_paid")) {
					mLoanAc.setNoOfInstPaid(PPOAlgoUtil.compute(mLoanAc.getNoOfInstPaid(), formulaMap.get("no_of_inst_paid"), 1));
				}

				if(formulaMap.keySet().contains("no_of_insall_late")) {

					GRules gRules = pJob.getDaoFactory().getGRulesDAO().findById(txDetached.getGroupAcNo());

					// Check if the payment date is after Due date
					if(DateUtil.getDayOfMonth(txDetached.getPaymentTs()) > gRules.getDueDayOfMonth()) {
						mLoanAc.setNoOfInsallLate(PPOAlgoUtil.compute(mLoanAc.getNoOfInsallLate(), formulaMap.get("no_of_insall_late"), 1));
					}
				}

				if(mLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= DataUtil.ZERO_INTEGER) {
					
					MProfile mProfile = pJob.getDaoFactory().getMProfileDAO().findById(txDetached.getMemberAcNo());
					String role = EnumCache.getNameOfMRole(mProfile.getMroleId());
					GAc gAc = pJob.getDaoFactory().getGAcDAO().findById(txDetached.getGroupAcNo());
					
					if(EnumUtil.isCoreMember(role)) {
						gAc.setCMProjIntOnLoanAm(BDUtil.sub(gAc.getCMProjIntOnLoanAm(), mLoanAc.getProjInterestAm()));
					} else if(EnumUtil.isAssociateMember(role)) {
						gAc.setAMProjIntOnLoanAm(BDUtil.sub(gAc.getAMProjIntOnLoanAm(), mLoanAc.getProjInterestAm()));
					}
					pJob.getDaoFactory().getGAcDAO().merge(gAc);

					mLoanAc.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Complete));
					mLoanAc.setProjInterestAm(DataUtil.ZERO_BIG_DECIMAL);
				}
				
				pJob.getDaoFactory().getMLoanAcDAO().merge(mLoanAc);

			} catch (Exception e) {
				log.error(e.toString() + "; for MemberAcNo: " + txDetached.getMemberAcNo() + "; for Tx Id: " + txDetached.getTxId() + "; for TxType: " + EnumCache.getNameOfTxType(txDetached.getTxTypeId()));
			}
		}
	}
}