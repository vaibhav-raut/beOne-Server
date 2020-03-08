package com.beone.shg.net.persistence.util;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class LoanData {
	private BigDecimal recInterest;
	private BigDecimal projInterest;
	public LoanData(BigDecimal recInterest, BigDecimal projInterest) {
		super();
		this.recInterest = recInterest;
		this.projInterest = projInterest;
	}
	public BigDecimal getRecInterest() {
		return recInterest;
	}
	public void setRecInterest(BigDecimal recInterest) {
		this.recInterest = recInterest;
	}
	public BigDecimal getProjInterest() {
		return projInterest;
	}
	public void setProjInterest(BigDecimal projInterest) {
		this.projInterest = projInterest;
	}
}
