package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ItemStatus
 */
@Entity
@Table(name="item_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ItemStatus  implements java.io.Serializable {

	private static final long serialVersionUID = 3053613343492767762L;
	// Fields    
	private int itemStatusId;
	private String itemStatus;
	private String itemStatusDesc;
		private String nextStatus;

	// Constructors

	/** default constructor */
	public ItemStatus() {
	}

	/** minimal constructor */
	public ItemStatus(int itemStatusId, String itemStatus) {
		this.itemStatusId = itemStatusId;
		this.itemStatus = itemStatus;
	}

	/** full constructor */
	public ItemStatus(String itemStatus, String itemStatusDesc,
			String nextStatus) {
		super();
		this.itemStatus = itemStatus;
		this.itemStatusDesc = itemStatusDesc;
		this.nextStatus = nextStatus;
	}

	/** full constructor */
	public ItemStatus(int itemStatusId, String itemStatus,
			String itemStatusDesc, String nextStatus) {
		super();
		this.itemStatusId = itemStatusId;
		this.itemStatus = itemStatus;
		this.itemStatusDesc = itemStatusDesc;
		this.nextStatus = nextStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="item_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getItemStatusId() {
		return this.itemStatusId;
	}

	public void setItemStatusId(int itemStatusId) {
		this.itemStatusId = itemStatusId;
	}
	@Column(name="item_status", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getItemStatus() {
		return this.itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getItemStatusDesc() {
		return this.itemStatusDesc;
	}

	public void setItemStatusDesc(String itemStatusDesc) {
		this.itemStatusDesc = itemStatusDesc;
	}
	@Column(name="next_status", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}
}
