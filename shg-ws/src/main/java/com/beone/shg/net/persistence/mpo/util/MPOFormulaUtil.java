package com.beone.shg.net.persistence.mpo.util;

public class MPOFormulaUtil {
	
	public final static String FORMULA_START = "@";
	public final static String FORMULA_END = "#";
	public final static String FORMULA_SUB = "!";

	public static MPOFormula parseMessageFormula(String formulaString) {
		MPOFormula formula = new MPOFormula();

		if(formulaString == null || formulaString.isEmpty()) {
			return formula;
		}
		int fromIndex = 0;

		do {
			int start = formulaString.indexOf(FORMULA_START, fromIndex);
			int end = formulaString.indexOf(FORMULA_END, fromIndex);

			if(start < 0 || end < 0) {
				break;
			}
			fromIndex = end + 1;

			String formulaValue = formulaString.substring(start + 1, end);
			String[] formulaValues = formulaValue.split(FORMULA_SUB);

			if(formulaValues.length == 2) {
				formula.addFormula(formulaValues[0], formulaValues[1]);
			} 
		} while(true);
		
		return formula;
	}
}
