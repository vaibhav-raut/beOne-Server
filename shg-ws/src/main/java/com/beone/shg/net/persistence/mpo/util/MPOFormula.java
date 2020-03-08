package com.beone.shg.net.persistence.mpo.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.beone.shg.net.persistence.util.DataUtil;

public class MPOFormula {
	
	private Map<String, Map<String, String>> formulaMap;

	public MPOFormula() {
		formulaMap = new LinkedHashMap<String, Map<String,String>>();
	}
	
	public void addFormula(String tableName, String columnName) {
		
		if(!formulaMap.keySet().contains(tableName)) {
			formulaMap.put(tableName, new LinkedHashMap<String,String>());
		}
		
		formulaMap.get(tableName).put(columnName, DataUtil.EMPTY_STRING);
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
