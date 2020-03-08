package com.beone.shg.net.persistence.ppo;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.PostProcessJob;
import com.beone.shg.net.persistence.model.GInvtAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.support.EnumCache;

@Repository("gInvtAcPPO")
public class GInvtAcPPO extends IProcessing {

	private static final Logger log = LoggerFactory.getLogger(GInvtAcPPO.class);

	@Override
	public void processUpdateFormula(PostProcessJob pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(Tx.class)) {

			Tx txDetached = (Tx)pJob.getRelatedObject().get(Tx.class);

			try {
				BigDecimal amount = txDetached.getAmount();

				GInvtAc gInvtAc = pJob.getDaoFactory().getGInvtAcDAO().findById(txDetached.getGroupInvtAcNo());

				if(formulaMap.keySet().contains("invt_am")) {
					gInvtAc.setInvtAm(PPOAlgoUtil.compute(gInvtAc.getInvtAm(), formulaMap.get("invt_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_invt_am")) {
					gInvtAc.setRecInvtAm(PPOAlgoUtil.compute(gInvtAc.getRecInvtAm(), formulaMap.get("rec_invt_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_interest_am")) {
					gInvtAc.setRecInterestAm(PPOAlgoUtil.compute(gInvtAc.getRecInterestAm(), formulaMap.get("rec_interest_am"), amount));
				}

				if(formulaMap.keySet().contains("proj_interest_am")) {
					gInvtAc.setProjInterestAm(PPOAlgoUtil.compute(gInvtAc.getProjInterestAm(), formulaMap.get("proj_interest_am"), amount));
				}

				pJob.getDaoFactory().getGInvtAcDAO().merge(gInvtAc);

			} catch (Exception e) {
				log.error(e.toString() + "; for GroupAcNo: " + txDetached.getGroupAcNo() + "; for Tx Id: " + txDetached.getTxId() + "; for TxType: " + EnumCache.getNameOfTxType(txDetached.getTxTypeId()));
			}
		}
	}
}