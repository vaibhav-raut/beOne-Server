package com.beone.udaan.mr.ppo;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.beone.udaan.mr.persistence.model.PMAc;
import com.beone.udaan.mr.persistence.model.StockTx;

@Repository("pOwnerAcPPO")
public class POwnerAcPPO extends PMAcPPO {

	@Override
	public void processUpdateFormula(ProcessJobMr pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(StockTx.class)) {
			
			StockTx stockTx = (StockTx)pJob.getRelatedObject().get(StockTx.class);
			PMAc pMAc = pJob.getDaoFactory().getPMAcDAO().findById(stockTx.getOwnerAcNo());
			
			processUpdateFormula(pJob, formulaMap, stockTx, pMAc);
		}
	}
}