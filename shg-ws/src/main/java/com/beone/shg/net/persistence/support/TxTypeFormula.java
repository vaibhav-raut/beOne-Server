package com.beone.shg.net.persistence.support;

import com.beone.shg.net.persistence.ppo.util.PPOFormula;
import com.beone.shg.net.persistence.ppo.util.PPOFormulaUtil;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeValue;

public class TxTypeFormula {

	private int txTypeId;
	private String txType;
	private PPOFormula formulaOnDone;
	private PPOFormula formulaOnUndone;
	private PPOFormula formulaOnApprove;
	private PPOFormula formulaOnReject;
	
	public TxTypeFormula(TxTypeValue txType) {
		txTypeId = txType.getTxTypeId();
		this.txType = txType.getTxType();		
		formulaOnDone = PPOFormulaUtil.parseUpdateFormula(txType.getUpdateFormulaOnDone());
		formulaOnUndone = PPOFormulaUtil.parseUpdateFormula(txType.getUpdateFormulaOnUndone());
		formulaOnApprove = PPOFormulaUtil.parseUpdateFormula(txType.getUpdateFormulaOnApprove());
		formulaOnReject = PPOFormulaUtil.parseUpdateFormula(txType.getUpdateFormulaOnReject());
	}
	public int getTxTypeId() {
		return txTypeId;
	}
	public void setTxTypeId(int txTypeId) {
		this.txTypeId = txTypeId;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public PPOFormula getFormulaOnDone() {
		return formulaOnDone;
	}
	public void setFormulaOnDone(PPOFormula formulaOnDone) {
		this.formulaOnDone = formulaOnDone;
	}
	public PPOFormula getFormulaOnUndone() {
		return formulaOnUndone;
	}
	public void setFormulaOnUndone(PPOFormula formulaOnUndone) {
		this.formulaOnUndone = formulaOnUndone;
	}
	public PPOFormula getFormulaOnApprove() {
		return formulaOnApprove;
	}
	public void setFormulaOnApprove(PPOFormula formulaOnApprove) {
		this.formulaOnApprove = formulaOnApprove;
	}
	public PPOFormula getFormulaOnReject() {
		return formulaOnReject;
	}
	public void setFormulaOnReject(PPOFormula formulaOnReject) {
		this.formulaOnReject = formulaOnReject;
	}
}
