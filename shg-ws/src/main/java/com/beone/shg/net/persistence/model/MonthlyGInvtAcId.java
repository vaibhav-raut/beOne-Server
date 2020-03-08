package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MonthlyGAcByTxtypeId
 */
@Embeddable

public class MonthlyGInvtAcId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1274172422148970870L;
	private long GInvtAcNo;
	private String month;

	// Constructors

	/** default constructor */
	public MonthlyGInvtAcId() {
	}

	/** full constructor */
	public MonthlyGInvtAcId(long GInvtAcNo, String month) {
		this.GInvtAcNo = GInvtAcNo;
		this.month = month;
	}

	// Property accessors
	@Column(name="g_invt_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getGInvtAcNo() {
		return this.GInvtAcNo;
	}

	public void setGInvtAcNo(long GInvtAcNo) {
		this.GInvtAcNo = GInvtAcNo;
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
		if ( !(other instanceof MonthlyGInvtAcId) ) return false;
		MonthlyGInvtAcId castOther = ( MonthlyGInvtAcId ) other; 

		return (this.getGInvtAcNo()==castOther.getGInvtAcNo())
				&& (this.getMonth()==castOther.getMonth());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getGInvtAcNo();
		result = 37 * result + this.getMonth().hashCode();
		return result;
	}   
}
