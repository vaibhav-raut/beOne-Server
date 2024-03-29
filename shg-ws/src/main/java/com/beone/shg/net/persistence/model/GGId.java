package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ParentChildGroupId generated by hbm2java
 */
@Embeddable

public class GGId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -1741987520063899908L;
	private long topGroupAcNo;
	private long downGroupAcNo;

	// Constructors

	/** default constructor */
	public GGId() {
	}

	/** full constructor */
	public GGId(long topGroupAcNo, long downGroupAcNo) {
		this.topGroupAcNo = topGroupAcNo;
		this.downGroupAcNo = downGroupAcNo;
	}

	// Property accessors
	@Column(name="top_g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getTopGroupAcNo() {
		return this.topGroupAcNo;
	}

	public void setTopGroupAcNo(long topGroupAcNo) {
		this.topGroupAcNo = topGroupAcNo;
	}
	@Column(name="down_g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getDownGroupAcNo() {
		return this.downGroupAcNo;
	}

	public void setDownGroupAcNo(long downGroupAcNo) {
		this.downGroupAcNo = downGroupAcNo;
	}

	public boolean equals(Object other) {
		if ( (this == other ) ) return true;
		if ( (other == null ) ) return false;
		if ( !(other instanceof GGId) ) return false;
		GGId castOther = ( GGId ) other; 

		return (this.getTopGroupAcNo()==castOther.getTopGroupAcNo())
				&& (this.getDownGroupAcNo()==castOther.getDownGroupAcNo());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getTopGroupAcNo();
		result = 37 * result + (int) this.getDownGroupAcNo();
		return result;
	}   
}
