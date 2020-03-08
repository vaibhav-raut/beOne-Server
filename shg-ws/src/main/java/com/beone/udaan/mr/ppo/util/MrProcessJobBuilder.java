package com.beone.udaan.mr.ppo.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.persistence.job.JobQueueManager;
import com.beone.shg.net.persistence.util.DAOFactory;
import com.beone.udaan.mr.config.DBConstMr;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.ppo.NowProcessJobMr;
import com.beone.udaan.mr.ppo.PostProcessJobMr;


@Component("mrProcessJobBuilder")
public class MrProcessJobBuilder {

	@Autowired
	@Qualifier("daoFactory")
	protected DAOFactory daoFactory;

	@Autowired
	@Qualifier("ppoMrFactory")
	protected PPOMrFactory ppoMrFactory;

	
	public void pushNowProcessJobMr(PPOMrFormula preP1ActionFormula, PPOMrFormula preP2ActionFormula,
			PPOMrFormula preP3ActionFormula, StockTx stockTx) {
		
		NowProcessJobMr job = new NowProcessJobMr(daoFactory, ppoMrFactory, preP1ActionFormula, preP2ActionFormula, preP3ActionFormula);
		
		job.setRelatedObject(getRelatedObjects(stockTx, null));
		
		job.execute();
	}
	
	public void pushPostProcessJobMr(PPOMrFormula postP1ActionFormula, PPOMrFormula postP2ActionFormula,
			PPOMrFormula postP3ActionFormula, StockTx stockTx) {
		
		PostProcessJobMr job = new PostProcessJobMr(daoFactory, ppoMrFactory, postP1ActionFormula, postP2ActionFormula, postP3ActionFormula);
		
		job.setRelatedObject(getRelatedObjects(stockTx, null));
		
		JobQueueManager.addToJobQueue(DBConstMr.P_MEGA_HUB_GROUP_AC_NO, job);	
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedObjects(StockTx stockTx, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(StockTx.class, stockTx);
		
		return relatedObject;
	}
}
