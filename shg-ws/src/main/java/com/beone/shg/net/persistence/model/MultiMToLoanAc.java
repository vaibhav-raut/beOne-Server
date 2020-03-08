package com.beone.shg.net.persistence.model;
// Generated May 22, 2014 2:05:14 AM by Hibernate Tools 3.1.0.beta4

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * MultiMToLoanAc generated by hbm2java
 */
@Entity
@Table(name="multi_m_to_loan_ac"
,catalog="shg"
		)

public class MultiMToLoanAc  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -7835423434107113038L;
	private MultiMToLoanAcId id;
	private MAc MAc;
	private MLoanAc MLoanAc;

	// Constructors

	/** default constructor */
	public MultiMToLoanAc() {
	}


	/** full constructor */
	public MultiMToLoanAc(MultiMToLoanAcId id, MAc MAc, MLoanAc MLoanAc) {
		this.id = id;
		this.MAc = MAc;
		this.MLoanAc = MLoanAc;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name="MLoanAcNo", column=@Column(name="m_loan_ac_no", unique=false, nullable=false, insertable=true, updatable=true) ), 
		@AttributeOverride(name="MAcNo", column=@Column(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true) ), 
		@AttributeOverride(name="loanShare", column=@Column(name="loan_share", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0) ) } )

	public MultiMToLoanAcId getId() {
		return this.id;
	}

	public void setId(MultiMToLoanAcId id) {
		this.id = id;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="m_ac_no", unique=false, nullable=false, insertable=false, updatable=false)

	public MAc getMAc() {
		return this.MAc;
	}

	public void setMAc(MAc MAc) {
		this.MAc = MAc;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="m_loan_ac_no", unique=false, nullable=false, insertable=false, updatable=false)

	public MLoanAc getMLoanAc() {
		return this.MLoanAc;
	}

	public void setMLoanAc(MLoanAc MLoanAc) {
		this.MLoanAc = MLoanAc;
	}
}
