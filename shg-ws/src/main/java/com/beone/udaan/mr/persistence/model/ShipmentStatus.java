package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShipmentStatus
 */
@Entity
@Table(name="shipment_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ShipmentStatus  implements java.io.Serializable {

	private static final long serialVersionUID = 8711550372552805614L;
	// Fields    
	private int shipmentStatusId;
	private String shipmentStatus;
	private String shipmentStatusDesc;
	private String nextStatus;

	// Constructors

	/** default constructor */
	public ShipmentStatus() {
	}

	/** minimal constructor */
	public ShipmentStatus(int shipmentStatusId, String shipmentStatus) {
		this.shipmentStatusId = shipmentStatusId;
		this.shipmentStatus = shipmentStatus;
	}

	/** full constructor */
	public ShipmentStatus(String shipmentStatus, String shipmentStatusDesc,
			String nextStatus) {
		super();
		this.shipmentStatus = shipmentStatus;
		this.shipmentStatusDesc = shipmentStatusDesc;
		this.nextStatus = nextStatus;
	}

	/** full constructor */
	public ShipmentStatus(int shipmentStatusId, String shipmentStatus,
			String shipmentStatusDesc, String nextStatus) {
		super();
		this.shipmentStatusId = shipmentStatusId;
		this.shipmentStatus = shipmentStatus;
		this.shipmentStatusDesc = shipmentStatusDesc;
		this.nextStatus = nextStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="shipment_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getShipmentStatusId() {
		return this.shipmentStatusId;
	}

	public void setShipmentStatusId(int shipmentStatusId) {
		this.shipmentStatusId = shipmentStatusId;
	}
	@Column(name="shipment_status", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getShipmentStatus() {
		return this.shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getShipmentStatusDesc() {
		return this.shipmentStatusDesc;
	}

	public void setShipmentStatusDesc(String shipmentStatusDesc) {
		this.shipmentStatusDesc = shipmentStatusDesc;
	}
	@Column(name="next_status", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}
}
