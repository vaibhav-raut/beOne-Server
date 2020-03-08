package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MonthlyGAcByTxtypeId
 */
@Embeddable

public class MonthlyMSavingAcId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1274172422148970870L;
	private long MSavingAcNo;
	private String month;

	// Constructors

	/** default constructor */
	public MonthlyMSavingAcId() {
	}

	/** full constructor */
	public MonthlyMSavingAcId(long MSavingAcNo, String month) {
		this.MSavingAcNo = MSavingAcNo;
		this.month = month;
	}

	// Property accessors
	@Column(name="m_saving_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getMSavingAcNo() {
		return this.MSavingAcNo;
	}

	public void setMSavingAcNo(long MSavingAcNo) {
		this.MSavingAcNo = MSavingAcNo;
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
		if ( !(other instanceof MonthlyMSavingAcId) ) return false;
		MonthlyMSavingAcId castOther = ( MonthlyMSavingAcId ) other; 

		return (this.getMSavingAcNo()==castOther.getMSavingAcNo())
				&& (this.getMonth()==castOther.getMonth());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getMSavingAcNo();
		result = 37 * result + this.getMonth().hashCode();
		return result;
	}   
}
