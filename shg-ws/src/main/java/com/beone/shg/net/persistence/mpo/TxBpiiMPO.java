package com.beone.shg.net.persistence.mpo;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.MessageJob;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.mpo.model.TxBpii;

@Repository("txBpiiMPO")
public class TxBpiiMPO extends IMProcessing {

	@Override
	public void processMessageFormula(MessageJob mJob, Map<String,String> formulaMap, String lang) {

		Tx tx = null;

		if(mJob.getRelatedObject().containsKey(TxBpii.class)) {
			tx = (Tx)mJob.getRelatedObject().get(TxBpii.class);
		}

		TxMPO.processMessageFormula(tx, mJob, formulaMap, lang);
	}
}