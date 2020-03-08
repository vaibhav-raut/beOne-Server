package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MemberOtherAcInfo {

	private List<PenaltyDetail> penaltyDetail;
	
	public List<PenaltyDetail> getPenaltyDetail() {
		return penaltyDetail;
	}

	public void addPenaltyDetail(PenaltyDetail penaltyDetail) {
		if(this.penaltyDetail != null) {
			this.penaltyDetail = new ArrayList<PenaltyDetail>();
		}
		this.penaltyDetail.add(penaltyDetail);
	}
	public void setPenaltyDetail(List<PenaltyDetail> penaltyDetail) {
		this.penaltyDetail = penaltyDetail;
	}

	public static class PenaltyDetail {
		private String txType;
		private BigDecimal amount;
		
		public PenaltyDetail() {
		}
		public PenaltyDetail(String txType, BigDecimal amount) {
			this.txType = txType;
			this.amount = amount;
		}
		public String getTxType() {
			return txType;
		}
		public void setTxType(String txType) {
			this.txType = txType;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}	
	}
}

