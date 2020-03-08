package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class LoanEMI {
	private String dueDate;
	private int monthNo;
	private BigDecimal principle;
	private BigDecimal emi;
	private BigDecimal principleEMI;
	private BigDecimal interestEMI;
	
	public LoanEMI() {
		super();
	}
	public LoanEMI(String dueDate, int monthNo, BigDecimal principle) {
		super();
		this.dueDate = dueDate;
		this.monthNo = monthNo;
		this.principle = principle;
	}
	public LoanEMI(String dueDate, int monthNo, BigDecimal principle, BigDecimal emi) {
		super();
		this.dueDate = dueDate;
		this.monthNo = monthNo;
		this.principle = principle;
		this.emi = emi;
	}
	public LoanEMI(String dueDate, int monthNo, BigDecimal principle, BigDecimal emi,
			BigDecimal principleEMI, BigDecimal interestEMI) {
		super();
		this.dueDate = dueDate;
		this.monthNo = monthNo;
		this.principle = principle;
		this.emi = emi;
		this.principleEMI = principleEMI;
		this.interestEMI = interestEMI;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public int getMonthNo() {
		return monthNo;
	}
	public void setMonthNo(int monthNo) {
		this.monthNo = monthNo;
	}
	public BigDecimal getPrinciple() {
		return principle;
	}
	public void setPrinciple(BigDecimal principle) {
		this.principle = principle;
	}
	public BigDecimal getEmi() {
		return emi;
	}
	public void setEmi(BigDecimal emi) {
		this.emi = emi;
	}
	public BigDecimal getPrincipleEMI() {
		return principleEMI;
	}
	public void setPrincipleEMI(BigDecimal principleEMI) {
		this.principleEMI = principleEMI;
	}
	public BigDecimal getInterestEMI() {
		return interestEMI;
	}
	public void setInterestEMI(BigDecimal interestEMI) {
		this.interestEMI = interestEMI;
	}	
}
