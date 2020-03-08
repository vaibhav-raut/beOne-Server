package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * InvoiceStatus
 */
@Entity
@Table(name="invoice_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class InvoiceStatus  implements java.io.Serializable {

	private static final long serialVersionUID = 6411669696216115362L;
	// Fields    
	private int invoiceStatusId;
	private String invoiceStatus;
	private String invoiceStatusDesc;
	private String nextStatus;

	// Constructors

	/** default constructor */
	public InvoiceStatus() {
	}

	/** minimal constructor */
	public InvoiceStatus(int invoiceStatusId, String invoiceStatus) {
		this.invoiceStatusId = invoiceStatusId;
		this.invoiceStatus = invoiceStatus;
	}

	/** full constructor */
	public InvoiceStatus(String invoiceStatus, String invoiceStatusDesc,
			String nextStatus) {
		super();
		this.invoiceStatus = invoiceStatus;
		this.invoiceStatusDesc = invoiceStatusDesc;
		this.nextStatus = nextStatus;
	}

	/** full constructor */
	public InvoiceStatus(int invoiceStatusId, String invoiceStatus,
			String invoiceStatusDesc, String nextStatus) {
		super();
		this.invoiceStatusId = invoiceStatusId;
		this.invoiceStatus = invoiceStatus;
		this.invoiceStatusDesc = invoiceStatusDesc;
		this.nextStatus = nextStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="invoice_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getInvoiceStatusId() {
		return this.invoiceStatusId;
	}

	public void setInvoiceStatusId(int invoiceStatusId) {
		this.invoiceStatusId = invoiceStatusId;
	}
	@Column(name="invoice_status", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getInvoiceStatusDesc() {
		return this.invoiceStatusDesc;
	}

	public void setInvoiceStatusDesc(String invoiceStatusDesc) {
		this.invoiceStatusDesc = invoiceStatusDesc;
	}
	@Column(name="next_status", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}
}
