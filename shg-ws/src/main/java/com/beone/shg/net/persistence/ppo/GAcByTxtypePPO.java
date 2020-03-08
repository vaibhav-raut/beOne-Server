package com.beone.shg.net.persistence.ppo;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.PostProcessJob;
import com.beone.shg.net.persistence.model.GAcByTxtype;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.support.EnumCache;

@Repository("gAcByTxtypePPO")
public class GAcByTxtypePPO extends IProcessing {

	private static final Logger log = LoggerFactory.getLogger(GAcByTxtypePPO.class);

	@Override
	public void processUpdateFormula(PostProcessJob pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(Tx.class)) {

			Tx transaction = (Tx)pJob.getRelatedObject().get(Tx.class);

			try {
				BigDecimal amount = transaction.getAmount();
				GAcByTxtype object = pJob.getDaoFactory().getGAcByTxtypeDAO().findById(transaction.getGroupAcNo(), transaction.getTxTypeId());

				if(object != null) {
					if(formulaMap.keySet().contains("amount")) {
						object.setAmount(PPOAlgoUtil.compute(object.getAmount(), formulaMap.get("amount"), amount));
					}
				}

				pJob.getDaoFactory().getGAcByTxtypeDAO().merge(object);

			} catch (Exception e) {
				log.error(e.toString() + "; for GroupAcNo: " + transaction.getGroupAcNo() + "; for Tx Id: " + transaction.getTxId() + "; for TxType: " + EnumCache.getNameOfTxType(transaction.getTxTypeId()));
			}
		}
	}
}