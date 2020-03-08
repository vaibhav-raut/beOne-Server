package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class InvtPlan {

	private String investmentType;
	private float rateOfInterest;
	private int noOfMonths;
	private BigDecimal invtAm;
	private BigDecimal projInterestAm;
	private String requestedDate;
	private String maturityDate;
	
	public InvtPlan() {
	}
	public String getInvestmentType() {
		return investmentType;
	}
	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}
	public float getRateOfInterest() {
		return rateOfInterest;
	}
	public void setRateOfInterest(float rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}
	public int getNoOfMonths() {
		return noOfMonths;
	}
	public void setNoOfMonths(int noOfMonths) {
		this.noOfMonths = noOfMonths;
	}
	public BigDecimal getInvtAm() {
		return invtAm;
	}
	public void setInvtAm(BigDecimal invtAm) {
		this.invtAm = invtAm;
	}
	public BigDecimal getProjInterestAm() {
		return projInterestAm;
	}
	public void setProjInterestAm(BigDecimal projInterestAm) {
		this.projInterestAm = projInterestAm;
	}
	public String getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
}
