package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MonthlyGAcByTxtypeId
 */
@Embeddable

public class MonthlyMLoanAcId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1274172422148970870L;
	private long MLoanAcNo;
	private String month;

	// Constructors

	/** default constructor */
	public MonthlyMLoanAcId() {
	}

	/** full constructor */
	public MonthlyMLoanAcId(long MLoanAcNo, String month) {
		this.MLoanAcNo = MLoanAcNo;
		this.month = month;
	}

	// Property accessors
	@Column(name="m_loan_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getMLoanAcNo() {
		return this.MLoanAcNo;
	}

	public void setMLoanAcNo(long MLoanAcNo) {
		this.MLoanAcNo = MLoanAcNo;
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
		if ( !(other instanceof MonthlyMLoanAcId) ) return false;
		MonthlyMLoanAcId castOther = ( MonthlyMLoanAcId ) other; 

		return (this.getMLoanAcNo()==castOther.getMLoanAcNo())
				&& (this.getMonth()==castOther.getMonth());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getMLoanAcNo();
		result = 37 * result + this.getMonth().hashCode();
		return result;
	}   
}
