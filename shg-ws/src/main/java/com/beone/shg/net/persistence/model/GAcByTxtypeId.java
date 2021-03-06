package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * GAcByTxtypeId generated by hbm2java
 */
@Embeddable

public class GAcByTxtypeId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1274172422148970870L;
	private long groupAcNo;
	private int txTypeId;

	// Constructors

	/** default constructor */
	public GAcByTxtypeId() {
	}

	/** full constructor */
	public GAcByTxtypeId(long groupAcNo, int txTypeId) {
		this.groupAcNo = groupAcNo;
		this.txTypeId = txTypeId;
	}

	// Property accessors
	@Column(name="g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getGroupAcNo() {
		return this.groupAcNo;
	}

	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	@Column(name="tx_type_id", unique=false, nullable=false, insertable=true, updatable=true)

	public int getTxTypeId() {
		return this.txTypeId;
	}

	public void setTxTypeId(int txTypeId) {
		this.txTypeId = txTypeId;
	}

	public boolean equals(Object other) {
		if ( (this == other ) ) return true;
		if ( (other == null ) ) return false;
		if ( !(other instanceof GAcByTxtypeId) ) return false;
		GAcByTxtypeId castOther = ( GAcByTxtypeId ) other; 

		return (this.getGroupAcNo()==castOther.getGroupAcNo())
				&& (this.getTxTypeId()==castOther.getTxTypeId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getGroupAcNo();
		result = 37 * result + this.getTxTypeId();
		return result;
	}   
}
