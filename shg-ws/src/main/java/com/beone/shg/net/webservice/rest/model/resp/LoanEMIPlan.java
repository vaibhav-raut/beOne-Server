package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class LoanEMIPlan {

	private String loanCalculation;
	private float loanProcessingFeePercent;
	private float rateOfInterest;
	private BigDecimal principle;
	private BigDecimal fixedEMI;
	private int noOfEMIs;
	private int noOfStartUpEMI;
	private String requestedDate;
	private String startDate;
	private String expCompletionDate;
	private int dueDay;
	private BigDecimal preEMI;
	private BigDecimal totalInterest;
	private BigDecimal loanProcessingFee;
	private List<LoanEMI> loanEMIs;

	public LoanEMIPlan() {
	}
	public LoanEMIPlan(String loanCalculation, float rateOfInterest,
			BigDecimal principle, int noOfEMIs) {
		super();
		this.loanCalculation = loanCalculation;
		this.rateOfInterest = rateOfInterest;
		this.principle = principle;
		this.noOfEMIs = noOfEMIs;
	}
	public LoanEMIPlan(String loanCalculation, float rateOfInterest,
			BigDecimal principle, BigDecimal fixedEMI) {
		super();
		this.loanCalculation = loanCalculation;
		this.rateOfInterest = rateOfInterest;
		this.principle = principle;
		this.fixedEMI = fixedEMI;
	}
	public LoanEMIPlan(String loanCalculation, float rateOfInterest,
			BigDecimal principle, int noOfEMIs, BigDecimal fixedEMI) {
		super();
		this.loanCalculation = loanCalculation;
		this.rateOfInterest = rateOfInterest;
		this.principle = principle;
		this.noOfEMIs = noOfEMIs;
		this.fixedEMI = fixedEMI;
	}
	public String getLoanCalculation() {
		return loanCalculation;
	}
	public void setLoanCalculation(String loanCalculation) {
		this.loanCalculation = loanCalculation;
	}
	public float getLoanProcessingFeePercent() {
		return loanProcessingFeePercent;
	}
	public void setLoanProcessingFeePercent(float loanProcessingFeePercent) {
		this.loanProcessingFeePercent = loanProcessingFeePercent;
	}
	public float getRateOfInterest() {
		return rateOfInterest;
	}
	public void setRateOfInterest(float rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}
	public BigDecimal getPrinciple() {
		return principle;
	}
	public void setPrinciple(BigDecimal principle) {
		this.principle = principle;
	}
	public BigDecimal getFixedEMI() {
		return fixedEMI;
	}
	public void setFixedEMI(BigDecimal fixedEMI) {
		this.fixedEMI = fixedEMI;
	}
	public int getNoOfEMIs() {
		return noOfEMIs;
	}
	public void setNoOfEMIs(int noOfEMIs) {
		this.noOfEMIs = noOfEMIs;
	}
	public int getNoOfStartUpEMI() {
		return noOfStartUpEMI;
	}
	public void setNoOfStartUpEMI(int noOfStartUpEMI) {
		this.noOfStartUpEMI = noOfStartUpEMI;
	}
	public String getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getExpCompletionDate() {
		return expCompletionDate;
	}
	public void setExpCompletionDate(String expCompletionDate) {
		this.expCompletionDate = expCompletionDate;
	}
	public int getDueDay() {
		return dueDay;
	}
	public void setDueDay(int dueDay) {
		this.dueDay = dueDay;
	}
	public BigDecimal getPreEMI() {
		return preEMI;
	}
	public void setPreEMI(BigDecimal preEMI) {
		this.preEMI = preEMI;
	}
	public BigDecimal getTotalInterest() {
		return totalInterest;
	}
	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}
	public BigDecimal getLoanProcessingFee() {
		return loanProcessingFee;
	}
	public void setLoanProcessingFee(BigDecimal loanProcessingFee) {
		this.loanProcessingFee = loanProcessingFee;
	}
	public List<LoanEMI> getLoanEMIs() {
		return loanEMIs;
	}
	public void setLoanEMIs(List<LoanEMI> loanEMIs) {
		this.loanEMIs = loanEMIs;
	}
	public void addLoanEMI(LoanEMI loanEMI) {
		if(this.loanEMIs == null) {
			this.loanEMIs = new ArrayList<LoanEMI>();
		}
		this.loanEMIs.add(loanEMI);
	}
}
