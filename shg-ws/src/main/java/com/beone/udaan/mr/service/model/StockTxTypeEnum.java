package com.beone.udaan.mr.service.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class StockTxTypeEnum {

	private String enumName;
	private List<StockTxTypeValue> enumValues;

	public StockTxTypeEnum() {
		enumValues = new ArrayList<StockTxTypeValue>();
	}
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public List<StockTxTypeValue> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(List<StockTxTypeValue> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(StockTxTypeValue enumValue) {
		this.enumValues.add(enumValue);
	}	

	public static class StockTxTypeValue {

		// Fields    
		private int stockTxTypeId;
		private String stockTxType;
		private String txFor;
		private String stockTxTypeDesc;
		private String preP1ActionFormula;
		private String preP2ActionFormula;
		private String preP3ActionFormula;
		private String postP1ActionFormula;
		private String postP2ActionFormula;
		private String postP3ActionFormula;
		public StockTxTypeValue() {
		}
		public StockTxTypeValue(StockTxTypeValue value) {
			super();
			this.stockTxTypeId = value.getStockTxTypeId();
			this.stockTxType = value.getStockTxType();
			this.stockTxTypeDesc = value.getStockTxTypeDesc();
			this.preP1ActionFormula = value.getPreP1ActionFormula();
			this.preP2ActionFormula = value.getPreP2ActionFormula();
			this.preP3ActionFormula = value.getPreP3ActionFormula();
			this.postP1ActionFormula = value.getPostP1ActionFormula();
			this.postP2ActionFormula = value.getPostP2ActionFormula();
			this.postP3ActionFormula = value.getPostP3ActionFormula();
		}

		public StockTxTypeValue(String stockTxType, String txFor,
				String stockTxTypeDesc, String preP1ActionFormula,
				String preP2ActionFormula, String preP3ActionFormula,
				String postP1ActionFormula, String postP2ActionFormula,
				String postP3ActionFormula) {
			super();
			this.stockTxType = stockTxType;
			this.txFor = txFor;
			this.stockTxTypeDesc = stockTxTypeDesc;
			this.preP1ActionFormula = preP1ActionFormula;
			this.preP2ActionFormula = preP2ActionFormula;
			this.preP3ActionFormula = preP3ActionFormula;
			this.postP1ActionFormula = postP1ActionFormula;
			this.postP2ActionFormula = postP2ActionFormula;
			this.postP3ActionFormula = postP3ActionFormula;
		}

		public StockTxTypeValue(int stockTxTypeId, String stockTxType,
				String txFor, String stockTxTypeDesc, String preP1ActionFormula,
				String preP2ActionFormula, String preP3ActionFormula,
				String postP1ActionFormula, String postP2ActionFormula,
				String postP3ActionFormula) {
			super();
			this.stockTxTypeId = stockTxTypeId;
			this.stockTxType = stockTxType;
			this.txFor = txFor;
			this.stockTxTypeDesc = stockTxTypeDesc;
			this.preP1ActionFormula = preP1ActionFormula;
			this.preP2ActionFormula = preP2ActionFormula;
			this.preP3ActionFormula = preP3ActionFormula;
			this.postP1ActionFormula = postP1ActionFormula;
			this.postP2ActionFormula = postP2ActionFormula;
			this.postP3ActionFormula = postP3ActionFormula;
		}

		public int getStockTxTypeId() {
			return stockTxTypeId;
		}

		public void setStockTxTypeId(int stockTxTypeId) {
			this.stockTxTypeId = stockTxTypeId;
		}

		public String getStockTxType() {
			return stockTxType;
		}

		public void setStockTxType(String stockTxType) {
			this.stockTxType = stockTxType;
		}

		public String getTxFor() {
			return txFor;
		}

		public void setTxFor(String txFor) {
			this.txFor = txFor;
		}

		public String getStockTxTypeDesc() {
			return stockTxTypeDesc;
		}

		public void setStockTxTypeDesc(String stockTxTypeDesc) {
			this.stockTxTypeDesc = stockTxTypeDesc;
		}

		public String getPreP1ActionFormula() {
			return preP1ActionFormula;
		}

		public void setPreP1ActionFormula(String preP1ActionFormula) {
			this.preP1ActionFormula = preP1ActionFormula;
		}

		public String getPreP2ActionFormula() {
			return preP2ActionFormula;
		}

		public void setPreP2ActionFormula(String preP2ActionFormula) {
			this.preP2ActionFormula = preP2ActionFormula;
		}

		public String getPreP3ActionFormula() {
			return preP3ActionFormula;
		}

		public void setPreP3ActionFormula(String preP3ActionFormula) {
			this.preP3ActionFormula = preP3ActionFormula;
		}

		public String getPostP1ActionFormula() {
			return postP1ActionFormula;
		}

		public void setPostP1ActionFormula(String postP1ActionFormula) {
			this.postP1ActionFormula = postP1ActionFormula;
		}

		public String getPostP2ActionFormula() {
			return postP2ActionFormula;
		}

		public void setPostP2ActionFormula(String postP2ActionFormula) {
			this.postP2ActionFormula = postP2ActionFormula;
		}

		public String getPostP3ActionFormula() {
			return postP3ActionFormula;
		}

		public void setPostP3ActionFormula(String postP3ActionFormula) {
			this.postP3ActionFormula = postP3ActionFormula;
		}
	}
}
