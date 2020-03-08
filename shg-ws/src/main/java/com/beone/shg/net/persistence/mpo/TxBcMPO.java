package com.beone.shg.net.persistence.mpo;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.MessageJob;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.mpo.model.TxBc;

@Repository("txBcMPO")
public class TxBcMPO extends IMProcessing {

	@Override
	public void processMessageFormula(MessageJob mJob, Map<String,String> formulaMap, String lang) {

		Tx tx = null;

		if(mJob.getRelatedObject().containsKey(TxBc.class)) {
			tx = (Tx)mJob.getRelatedObject().get(TxBc.class);
		}

		TxMPO.processMessageFormula(tx, mJob, formulaMap, lang);
	}
}