package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MonthlyGAcByTxtypeId
 */
@Embeddable

public class MonthlyGAcByTxtypeId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1274172422148970870L;
	private long GAcNo;
	private int txTypeId;
	private String month;

	// Constructors

	/** default constructor */
	public MonthlyGAcByTxtypeId() {
	}

	/** full constructor */
	public MonthlyGAcByTxtypeId(long GAcNo, int txTypeId, String month) {
		this.GAcNo = GAcNo;
		this.txTypeId = txTypeId;
		this.month = month;
	}

	// Property accessors
	@Column(name="g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getGAcNo() {
		return this.GAcNo;
	}

	public void setGAcNo(long GAcNo) {
		this.GAcNo = GAcNo;
	}
	@Column(name="tx_type_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getTxTypeId() {
		return this.txTypeId;
	}

	public void setTxTypeId(int txTypeId) {
		this.txTypeId = txTypeId;
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
		if ( !(other instanceof MonthlyGAcByTxtypeId) ) return false;
		MonthlyGAcByTxtypeId castOther = ( MonthlyGAcByTxtypeId ) other; 

		return (this.getGAcNo()==castOther.getGAcNo())
				&& (this.getTxTypeId()==castOther.getTxTypeId())
				&& (this.getMonth()==castOther.getMonth());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getGAcNo();
		result = 37 * result + this.getTxTypeId();
		result = 37 * result + this.getMonth().hashCode();
		return result;
	}   
}
