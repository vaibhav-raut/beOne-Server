package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * StockTypeStatus
 */
@Entity
@Table(name="stock_type_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class StockTypeStatus  implements java.io.Serializable {

	private static final long serialVersionUID = 2590889221933731458L;
	// Fields    
	private int stockTypeStatusId;
	private String stockTypeStatus;
	private String stockTypeStatusDesc;
	private String nextStatus;

	// Constructors

	/** default constructor */
	public StockTypeStatus() {
	}

	/** minimal constructor */
	public StockTypeStatus(int stockTypeStatusId, String stockTypeStatus) {
		this.stockTypeStatusId = stockTypeStatusId;
		this.stockTypeStatus = stockTypeStatus;
	}

	/** full constructor */
	public StockTypeStatus(String stockTypeStatus, String stockTypeStatusDesc,
			String nextStatus) {
		super();
		this.stockTypeStatus = stockTypeStatus;
		this.stockTypeStatusDesc = stockTypeStatusDesc;
		this.nextStatus = nextStatus;
	}

	/** full constructor */
	public StockTypeStatus(int stockTypeStatusId, String stockTypeStatus,
			String stockTypeStatusDesc, String nextStatus) {
		super();
		this.stockTypeStatusId = stockTypeStatusId;
		this.stockTypeStatus = stockTypeStatus;
		this.stockTypeStatusDesc = stockTypeStatusDesc;
		this.nextStatus = nextStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="stock_type_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getStockTypeStatusId() {
		return this.stockTypeStatusId;
	}

	public void setStockTypeStatusId(int stockTypeStatusId) {
		this.stockTypeStatusId = stockTypeStatusId;
	}
	@Column(name="stock_type_status", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getStockTypeStatus() {
		return this.stockTypeStatus;
	}

	public void setStockTypeStatus(String stockTypeStatus) {
		this.stockTypeStatus = stockTypeStatus;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getStockTypeStatusDesc() {
		return this.stockTypeStatusDesc;
	}

	public void setStockTypeStatusDesc(String stockTypeStatusDesc) {
		this.stockTypeStatusDesc = stockTypeStatusDesc;
	}
	@Column(name="next_status", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}
}
