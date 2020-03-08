package com.beone.udaan.mr.persistence.support;


import com.beone.udaan.mr.ppo.util.PPOMrFormula;
import com.beone.udaan.mr.ppo.util.PPOMrFormulaUtil;
import com.beone.udaan.mr.service.model.StockTxTypeEnum.StockTxTypeValue;

public class StockTxTypeFormula {

	private int stockTxTypeId;
	private String stockTxType;
	private PPOMrFormula preP1ActionFormula;
	private PPOMrFormula preP2ActionFormula;
	private PPOMrFormula preP3ActionFormula;
	private PPOMrFormula postP1ActionFormula;
	private PPOMrFormula postP2ActionFormula;
	private PPOMrFormula postP3ActionFormula;
	
	public StockTxTypeFormula(StockTxTypeValue stockTxType) {
		stockTxTypeId = stockTxType.getStockTxTypeId();
		this.stockTxType = stockTxType.getStockTxType();		
		preP1ActionFormula = PPOMrFormulaUtil.parseUpdateFormula(stockTxType.getPreP1ActionFormula());
		preP2ActionFormula = PPOMrFormulaUtil.parseUpdateFormula(stockTxType.getPreP2ActionFormula());
		preP3ActionFormula = PPOMrFormulaUtil.parseUpdateFormula(stockTxType.getPreP3ActionFormula());
		postP1ActionFormula = PPOMrFormulaUtil.parseUpdateFormula(stockTxType.getPostP1ActionFormula());
		postP2ActionFormula = PPOMrFormulaUtil.parseUpdateFormula(stockTxType.getPostP2ActionFormula());
		postP3ActionFormula = PPOMrFormulaUtil.parseUpdateFormula(stockTxType.getPostP3ActionFormula());
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
	public PPOMrFormula getPreP1ActionFormula() {
		return preP1ActionFormula;
	}
	public void setPreP1ActionFormula(PPOMrFormula preP1ActionFormula) {
		this.preP1ActionFormula = preP1ActionFormula;
	}
	public PPOMrFormula getPreP2ActionFormula() {
		return preP2ActionFormula;
	}
	public void setPreP2ActionFormula(PPOMrFormula preP2ActionFormula) {
		this.preP2ActionFormula = preP2ActionFormula;
	}
	public PPOMrFormula getPreP3ActionFormula() {
		return preP3ActionFormula;
	}
	public void setPreP3ActionFormula(PPOMrFormula preP3ActionFormula) {
		this.preP3ActionFormula = preP3ActionFormula;
	}
	public PPOMrFormula getPostP1ActionFormula() {
		return postP1ActionFormula;
	}
	public void setPostP1ActionFormula(PPOMrFormula postP1ActionFormula) {
		this.postP1ActionFormula = postP1ActionFormula;
	}
	public PPOMrFormula getPostP2ActionFormula() {
		return postP2ActionFormula;
	}
	public void setPostP2ActionFormula(PPOMrFormula postP2ActionFormula) {
		this.postP2ActionFormula = postP2ActionFormula;
	}
	public PPOMrFormula getPostP3ActionFormula() {
		return postP3ActionFormula;
	}
	public void setPostP3ActionFormula(PPOMrFormula postP3ActionFormula) {
		this.postP3ActionFormula = postP3ActionFormula;
	}
}
