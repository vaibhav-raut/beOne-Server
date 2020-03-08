package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PaymentType
 */
@Entity
@Table(name="payment_type"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class PaymentType  implements java.io.Serializable {

	private static final long serialVersionUID = 4089807408312615554L;
	// Fields    
	private int paymentTypeId;
	private String paymentType;
	private String paymentTypeDesc;

	// Constructors

	/** default constructor */
	public PaymentType() {
	}

	/** minimal constructor */
	public PaymentType(int paymentTypeId, String paymentType) {
		this.paymentTypeId = paymentTypeId;
		this.paymentType = paymentType;
	}

	/** full constructor */
	public PaymentType(String paymentType, String paymentTypeDesc) {
		this.paymentType = paymentType;
		this.paymentTypeDesc = paymentTypeDesc;
	}

	/** full constructor */
	public PaymentType(int paymentTypeId, String paymentType, String paymentTypeDesc) {
		this.paymentTypeId = paymentTypeId;
		this.paymentType = paymentType;
		this.paymentTypeDesc = paymentTypeDesc;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="payment_type_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getPaymentTypeId() {
		return this.paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	@Column(name="payment_type", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getPaymentTypeDesc() {
		return this.paymentTypeDesc;
	}

	public void setPaymentTypeDesc(String paymentTypeDesc) {
		this.paymentTypeDesc = paymentTypeDesc;
	}
}
