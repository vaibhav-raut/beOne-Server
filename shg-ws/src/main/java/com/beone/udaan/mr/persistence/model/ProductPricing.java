package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProductPricing
 */
@Entity
@Table(name="product_pricing"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ProductPricing  implements java.io.Serializable {

	private static final long serialVersionUID = 8448719383218389242L;
	// Fields    
	private int productPricingId;
	private String productPricing;
	private float wpPercent;
	private float mrPercent;
	private float vatPercent;
	private float otherTaxesPercent;
	
	// Constructors

	/** default constructor */
	public ProductPricing() {
	}

	/** minimal constructor */
	public ProductPricing(int productPricingId, String productPricing) {
		this.productPricingId = productPricingId;
		this.productPricing = productPricing;
	}

	/** full constructor */

	public ProductPricing(String productPricing,
			float wpPercent, float mrPercent, float vatPercent,
			float otherTaxesPercent) {
		super();
		this.productPricing = productPricing;
		this.wpPercent = wpPercent;
		this.mrPercent = mrPercent;
		this.vatPercent = vatPercent;
		this.otherTaxesPercent = otherTaxesPercent;
	}

	/** full constructor */
	public ProductPricing(int productPricingId, String productPricing,
			float wpPercent, float mrPercent, float vatPercent,
			float otherTaxesPercent) {
		super();
		this.productPricingId = productPricingId;
		this.productPricing = productPricing;
		this.wpPercent = wpPercent;
		this.mrPercent = mrPercent;
		this.vatPercent = vatPercent;
		this.otherTaxesPercent = otherTaxesPercent;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="product_pricing_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getProductPricingId() {
		return this.productPricingId;
	}

	public void setProductPricingId(int productPricingId) {
		this.productPricingId = productPricingId;
	}
	@Column(name="product_pricing", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getProductPricing() {
		return this.productPricing;
	}

	public void setProductPricing(String productPricing) {
		this.productPricing = productPricing;
	}
	@Column(name="wp_per", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public float getWpPercent() {
		return wpPercent;
	}

	public void setWpPercent(float wpPercent) {
		this.wpPercent = wpPercent;
	}
	@Column(name="mr_per", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public float getMrPercent() {
		return mrPercent;
	}

	public void setMrPercent(float mrPercent) {
		this.mrPercent = mrPercent;
	}
	@Column(name="vat_per", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public float getVatPercent() {
		return vatPercent;
	}

	public void setVatPercent(float vatPercent) {
		this.vatPercent = vatPercent;
	}
	@Column(name="other_taxes_per", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=0)

	public float getOtherTaxesPercent() {
		return otherTaxesPercent;
	}

	public void setOtherTaxesPercent(float otherTaxesPercent) {
		this.otherTaxesPercent = otherTaxesPercent;
	}
}
