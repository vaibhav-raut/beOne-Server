package com.beone.udaan.mr.ppo;

import java.util.Map;

public abstract class IMrProcessing {
	
	public abstract void processUpdateFormula(ProcessJobMr pJob, Map<String,String> formulaMap);
}
