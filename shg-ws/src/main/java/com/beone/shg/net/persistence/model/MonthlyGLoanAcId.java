package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MonthlyGAcByTxtypeId
 */
@Embeddable

public class MonthlyGLoanAcId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1274172422148970870L;
	private long GLoanAcId;
	private String month;

	// Constructors

	/** default constructor */
	public MonthlyGLoanAcId() {
	}

	/** full constructor */
	public MonthlyGLoanAcId(long GLoanAcId, String month) {
		this.GLoanAcId = GLoanAcId;
		this.month = month;
	}

	// Property accessors
	@Column(name="g_loan_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getGLoanAcId() {
		return this.GLoanAcId;
	}

	public void setGLoanAcId(long GLoanAcId) {
		this.GLoanAcId = GLoanAcId;
	}

	@Column(name="month", unique=false, nullable=false, insertable=false, updatable=false, length=10)
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public boolean equals(Object other) {
		if ( (this == other ) ) return true;
		if ( (other == null ) ) return false;
		if ( !(other instanceof MonthlyGLoanAcId) ) return false;
		MonthlyGLoanAcId castOther = ( MonthlyGLoanAcId ) other; 

		return (this.getGLoanAcId()==castOther.getGLoanAcId())
				&& (this.getMonth()==castOther.getMonth());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getGLoanAcId();
		result = 37 * result + this.getMonth().hashCode();
		return result;
	}   
}
