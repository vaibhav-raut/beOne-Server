package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MultiMToLoanAcId generated by hbm2java
 */
@Embeddable

public class MultiMToLoanAcId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -7924315099284602265L;
	private long MLoanAcNo;
	private long MAcNo;
	private Float loanShare;

	// Constructors

	/** default constructor */
	public MultiMToLoanAcId() {
	}

	/** minimal constructor */
	public MultiMToLoanAcId(long MLoanAcNo, long MAcNo) {
		this.MLoanAcNo = MLoanAcNo;
		this.MAcNo = MAcNo;
	}

	/** full constructor */
	public MultiMToLoanAcId(long MLoanAcNo, long MAcNo, Float loanShare) {
		this.MLoanAcNo = MLoanAcNo;
		this.MAcNo = MAcNo;
		this.loanShare = loanShare;
	}

	// Property accessors
	@Column(name="m_loan_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public long getMLoanAcNo() {
		return this.MLoanAcNo;
	}

	public void setMLoanAcNo(long MLoanAcNo) {
		this.MLoanAcNo = MLoanAcNo;
	}
	@Column(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getMAcNo() {
		return this.MAcNo;
	}

	public void setMAcNo(long MAcNo) {
		this.MAcNo = MAcNo;
	}
	@Column(name="loan_share", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public Float getLoanShare() {
		return this.loanShare;
	}

	public void setLoanShare(Float loanShare) {
		this.loanShare = loanShare;
	}

	public boolean equals(Object other) {
		if ( (this == other ) ) return true;
		if ( (other == null ) ) return false;
		if ( !(other instanceof MultiMToLoanAcId) ) return false;
		MultiMToLoanAcId castOther = ( MultiMToLoanAcId ) other; 

		return (this.getMLoanAcNo()==castOther.getMLoanAcNo())
				&& (this.getMAcNo()==castOther.getMAcNo())
				&& ( (this.getLoanShare()==castOther.getLoanShare()) || ( this.getLoanShare()!=null && castOther.getLoanShare()!=null && this.getLoanShare().equals(castOther.getLoanShare()) ) );
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getMLoanAcNo();
		result = 37 * result + (int) this.getMAcNo();
		result = 37 * result + ( getLoanShare() == null ? 0 : this.getLoanShare().hashCode() );
		return result;
	}   
}
