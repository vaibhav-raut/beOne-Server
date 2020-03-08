package com.beone.shg.net.persistence.ppo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PPOFormulaUtil {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PPOFormulaUtil.class);

	public static PPOFormula parseUpdateFormula(String formulaString) {
		PPOFormula formula = new PPOFormula();
		
		if(formulaString == null || formulaString.isEmpty()) {
			return formula;
		}
		
		String[] formulaArray = formulaString.split(";");
		
		for(String formulaEntry : formulaArray) {
			String[] formulaValues = formulaEntry.split(":");
			
			if(formulaValues.length == 3) {
				formula.addFormula(formulaValues[0], formulaValues[1], formulaValues[2]);
			} 
			else if (formulaValues.length == 0) {
				LOGGER.warn("Missing Formula: " + formulaEntry);
			}
			else {
				LOGGER.error("Invalid Formula: " + formulaEntry + "; with formulaValues = " + formulaValues.length);
			}
		}
		
		return formula;
	}
}
