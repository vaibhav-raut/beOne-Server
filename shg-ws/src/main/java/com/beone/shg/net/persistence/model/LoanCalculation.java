package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * LoanCalculation generated by hbm2java
 */
@Entity
@Table(name="loan_calculation"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class LoanCalculation  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -8176740223481059248L;
	private int loanCalculationId;
	private String loanCalculation;
	private String loanCalculationDesc;

	// Constructors

	/** default constructor */
	public LoanCalculation() {
	}

	/** minimal constructor */
	public LoanCalculation(int loanCalculationId, String loanCalculation) {
		this.loanCalculationId = loanCalculationId;
		this.loanCalculation = loanCalculation;
	}

	/** full constructor */
	public LoanCalculation(String loanCalculation, String loanCalculationDesc) {
		this.loanCalculation = loanCalculation;
		this.loanCalculationDesc = loanCalculationDesc;
	}

	/** full constructor */
	public LoanCalculation(int loanCalculationId, String loanCalculation, String loanCalculationDesc) {
		this.loanCalculationId = loanCalculationId;
		this.loanCalculation = loanCalculation;
		this.loanCalculationDesc = loanCalculationDesc;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="loan_calculation_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getLoanCalculationId() {
		return this.loanCalculationId;
	}

	public void setLoanCalculationId(int loanCalculationId) {
		this.loanCalculationId = loanCalculationId;
	}
	@Column(name="loan_calculation", unique=false, nullable=false, insertable=true, updatable=true, length=30)

	public String getLoanCalculation() {
		return this.loanCalculation;
	}

	public void setLoanCalculation(String loanCalculation) {
		this.loanCalculation = loanCalculation;
	}
	@Column(name="loan_calculation_desc", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getLoanCalculationDesc() {
		return this.loanCalculationDesc;
	}

	public void setLoanCalculationDesc(String loanCalculationDesc) {
		this.loanCalculationDesc = loanCalculationDesc;
	}
}
