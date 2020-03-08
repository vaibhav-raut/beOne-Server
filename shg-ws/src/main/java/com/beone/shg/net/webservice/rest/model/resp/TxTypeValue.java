package com.beone.shg.net.webservice.rest.model.resp;

public class TxTypeValue {

//	public enum SlipType {RECEIPT, VOUCHER};
//	public enum AmountType {FEE, EXPENSE, PROFIT, LOSS, PAYABLE, WEALTH, TRANSFER};
//	public enum TxWith {MEMBER, BANK, VENDOR, NET, OTHER_GROUP};

	private int txTypeId;
	private String txType;
	private String txCategory;
	private String txDescription;
	private String slipType;
	private String amountType;
	private String txWith;
	private String updateFormulaOnDone;
	private String updateFormulaOnUndone;
	private String updateFormulaOnApprove;
	private String updateFormulaOnReject;
	
	public TxTypeValue() {
	}
	
	public TxTypeValue(int txTypeId, String txType, String txCategory, String txDescription) {
		super();
		this.txTypeId = txTypeId;
		this.txType = txType;
		this.txCategory = txCategory;
		this.txDescription = txDescription;
	}
	public TxTypeValue(int txTypeId, String txType, String txCategory, String txDescription,
			String slipType, String amountType, String txWith) {
		super();
		this.txTypeId = txTypeId;
		this.txType = txType;
		this.txCategory = txCategory;
		this.txDescription = txDescription;
		this.slipType = slipType;
		this.amountType = amountType;
		this.txWith = txWith;
	}
	public TxTypeValue(TxTypeValue value) {
		this.txTypeId = value.txTypeId;
		this.txType = value.txType;
		this.txCategory = value.txCategory;
		this.txDescription = value.txDescription;
		this.slipType = value.slipType;
		this.amountType = value.amountType;
		this.txWith = value.txWith;
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
	public String getTxCategory() {
		return txCategory;
	}

	public void setTxCategory(String txCategory) {
		this.txCategory = txCategory;
	}

	public String getTxDescription() {
		return txDescription;
	}
	public void setTxDescription(String txDescription) {
		this.txDescription = txDescription;
	}
	public String getSlipType() {
		return slipType;
	}
	public void setSlipType(String slipType) {
		this.slipType = slipType;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public String getTxWith() {
		return txWith;
	}
	public void setTxWith(String txWith) {
		this.txWith = txWith;
	}

	public String getUpdateFormulaOnDone() {
		return updateFormulaOnDone;
	}

	public void setUpdateFormulaOnDone(String updateFormulaOnDone) {
		this.updateFormulaOnDone = updateFormulaOnDone;
	}

	public String getUpdateFormulaOnUndone() {
		return updateFormulaOnUndone;
	}

	public void setUpdateFormulaOnUndone(String updateFormulaOnUndone) {
		this.updateFormulaOnUndone = updateFormulaOnUndone;
	}

	public String getUpdateFormulaOnApprove() {
		return updateFormulaOnApprove;
	}

	public void setUpdateFormulaOnApprove(String updateFormulaOnApprove) {
		this.updateFormulaOnApprove = updateFormulaOnApprove;
	}

	public String getUpdateFormulaOnReject() {
		return updateFormulaOnReject;
	}

	public void setUpdateFormulaOnReject(String updateFormulaOnReject) {
		this.updateFormulaOnReject = updateFormulaOnReject;
	}

}
