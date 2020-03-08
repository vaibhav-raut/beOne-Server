package com.beone.shg.net.persistence.ppo.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PPOFormula {
	
	private Map<String, Map<String, String>> formulaMap;

	public PPOFormula() {
		formulaMap = new LinkedHashMap<String, Map<String,String>>();
	}
	
	public void addFormula(String tableName, String columnName, String algo) {
		
		if(!formulaMap.keySet().contains(tableName)) {
			formulaMap.put(tableName, new LinkedHashMap<String,String>());
		}
		
		formulaMap.get(tableName).put(columnName, algo);
	}
	
	public Map<String,String> getTableFormula(String tableName) {
		return formulaMap.get(tableName);
	}
	
	public boolean isTablePresent(String tableName) {
		return formulaMap.keySet().contains(tableName);
	}
	
	public Set<String> getTableNames() {
		return formulaMap.keySet();
	}
}
