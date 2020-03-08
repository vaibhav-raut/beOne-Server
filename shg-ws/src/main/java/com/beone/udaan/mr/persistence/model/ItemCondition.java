package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ItemCondition
 */
@Entity
@Table(name="item_condition"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ItemCondition  implements java.io.Serializable {

	private static final long serialVersionUID = 3053613434933673762L;
	// Fields    
	private int itemConditionId;
	private String itemCondition;
	private String description;
	private float spDiscountPer;
	private float mrpDiscountPer;

	// Constructors

	/** default constructor */
	public ItemCondition() {
	}

	public ItemCondition(String itemCondition, String description,
			float spDiscountPer, float mrpDiscountPer) {
		super();
		this.itemCondition = itemCondition;
		this.description = description;
		this.spDiscountPer = spDiscountPer;
		this.mrpDiscountPer = mrpDiscountPer;
	}

	public ItemCondition(int itemConditionId, String itemCondition,
			String description, float spDiscountPer, float mrpDiscountPer) {
		super();
		this.itemConditionId = itemConditionId;
		this.itemCondition = itemCondition;
		this.description = description;
		this.spDiscountPer = spDiscountPer;
		this.mrpDiscountPer = mrpDiscountPer;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="item_condition_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getItemConditionId() {
		return this.itemConditionId;
	}

	public void setItemConditionId(int itemConditionId) {
		this.itemConditionId = itemConditionId;
	}
	@Column(name="item_condition", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getItemCondition() {
		return this.itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}

	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="sp_discount_per", unique=false, nullable=true, insertable=true, updatable=true)
	public float getSpDiscountPer() {
		return spDiscountPer;
	}

	public void setSpDiscountPer(float spDiscountPer) {
		this.spDiscountPer = spDiscountPer;
	}

	@Column(name="mrp_discount_per", unique=false, nullable=true, insertable=true, updatable=true)
	public float getMrpDiscountPer() {
		return mrpDiscountPer;
	}

	public void setMrpDiscountPer(float mrpDiscountPer) {
		this.mrpDiscountPer = mrpDiscountPer;
	}

}
