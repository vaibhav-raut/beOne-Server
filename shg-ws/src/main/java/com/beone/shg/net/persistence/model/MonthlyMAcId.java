package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MonthlyGAcByTxtypeId
 */
@Embeddable

public class MonthlyMAcId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1274172422148970870L;
	private long MAcNo;
	private String month;

	// Constructors

	/** default constructor */
	public MonthlyMAcId() {
	}

	/** full constructor */
	public MonthlyMAcId(long MAcNo, String month) {
		this.MAcNo = MAcNo;
		this.month = month;
	}

	// Property accessors
	@Column(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getMAcNo() {
		return this.MAcNo;
	}

	public void setMAcNo(long MAcNo) {
		this.MAcNo = MAcNo;
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
		if ( !(other instanceof MonthlyMAcId) ) return false;
		MonthlyMAcId castOther = ( MonthlyMAcId ) other; 

		return (this.getMAcNo()==castOther.getMAcNo())
				&& (this.getMonth()==castOther.getMonth());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getMAcNo();
		result = 37 * result + this.getMonth().hashCode();
		return result;
	}   
}
