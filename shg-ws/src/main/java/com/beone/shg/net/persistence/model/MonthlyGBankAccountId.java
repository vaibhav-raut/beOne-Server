package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MonthlyGBankAccountId
 */
@Embeddable

public class MonthlyGBankAccountId  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1274172422148970870L;
	private long bankAccountNo;
	private String month;

	// Constructors

	/** default constructor */
	public MonthlyGBankAccountId() {
	}

	/** full constructor */
	public MonthlyGBankAccountId(long bankAccountNo, String month) {
		this.bankAccountNo = bankAccountNo;
		this.month = month;
	}

	// Property accessors
	@Column(name="bank_account_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getBankAccountNo() {
		return this.bankAccountNo;
	}

	public void setBankAccountNo(long bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
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
		if ( !(other instanceof MonthlyGBankAccountId) ) return false;
		MonthlyGBankAccountId castOther = ( MonthlyGBankAccountId ) other; 

		return (this.getBankAccountNo()==castOther.getBankAccountNo())
				&& (this.getMonth()==castOther.getMonth());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getBankAccountNo();
		result = 37 * result + this.getMonth().hashCode();
		return result;
	}   
}
