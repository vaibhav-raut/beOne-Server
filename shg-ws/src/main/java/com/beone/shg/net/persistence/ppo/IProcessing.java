package com.beone.shg.net.persistence.ppo;

import java.util.Map;

import com.beone.shg.net.persistence.job.PostProcessJob;

public abstract class IProcessing {
	
	public abstract void processUpdateFormula(PostProcessJob pJob, Map<String,String> formulaMap);
}
