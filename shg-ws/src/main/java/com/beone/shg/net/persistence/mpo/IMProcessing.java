package com.beone.shg.net.persistence.mpo;

import java.util.Map;

import com.beone.shg.net.persistence.job.MessageJob;

public abstract class IMProcessing {
	
	public abstract void processMessageFormula(MessageJob mJob, Map<String,String> formulaMap, String lang);
}
