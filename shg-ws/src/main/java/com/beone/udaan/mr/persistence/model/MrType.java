package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MrType
 */
@Entity
@Table(name="mr_type"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class MrType  implements java.io.Serializable {

	private static final long serialVersionUID = 3053613343492767762L;
	// Fields    
	private int mrTypeId;
	private String mrType;
	private String description;
	private int registrationFee;
	private float depositCreditMultiplie;

	// Constructors

	/** default constructor */
	public MrType() {
	}

	public MrType(String mrType, String description, int registrationFee,
			float depositCreditMultiplie) {
		super();
		this.mrType = mrType;
		this.description = description;
		this.registrationFee = registrationFee;
		this.depositCreditMultiplie = depositCreditMultiplie;
	}

	public MrType(int mrTypeId, String mrType, String description,
			int registrationFee, float depositCreditMultiplie) {
		super();
		this.mrTypeId = mrTypeId;
		this.mrType = mrType;
		this.description = description;
		this.registrationFee = registrationFee;
		this.depositCreditMultiplie = depositCreditMultiplie;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="mr_type_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getMrTypeId() {
		return this.mrTypeId;
	}

	public void setMrTypeId(int mrTypeId) {
		this.mrTypeId = mrTypeId;
	}
	@Column(name="mr_type", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getMrType() {
		return this.mrType;
	}

	public void setMrType(String mrType) {
		this.mrType = mrType;
	}

	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="registration_fee", unique=false, nullable=true, insertable=true, updatable=true)
	public int getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(int registrationFee) {
		this.registrationFee = registrationFee;
	}
	
	@Column(name="deposit_credit_multiplier", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)
	public float getDepositCreditMultiplie() {
		return depositCreditMultiplie;
	}

	public void setDepositCreditMultiplie(float depositCreditMultiplie) {
		this.depositCreditMultiplie = depositCreditMultiplie;
	}
}
