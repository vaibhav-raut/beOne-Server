package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * LotStatus
 */
@Entity
@Table(name="lot_status"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class LotStatus  implements java.io.Serializable {

	private static final long serialVersionUID = 4309032599394233508L;
	// Fields    
	private int lotStatusId;
	private String lotStatus;
	private String lotStatusDesc;
	private String nextStatus;

	// Constructors

	/** default constructor */
	public LotStatus() {
	}

	/** minimal constructor */
	public LotStatus(int lotStatusId, String lotStatus) {
		this.lotStatusId = lotStatusId;
		this.lotStatus = lotStatus;
	}

	/** full constructor */
	public LotStatus(String lotStatus, String lotStatusDesc, String nextStatus) {
		super();
		this.lotStatus = lotStatus;
		this.lotStatusDesc = lotStatusDesc;
		this.nextStatus = nextStatus;
	}

	/** full constructor */
	public LotStatus(int lotStatusId, String lotStatus, String lotStatusDesc,
			String nextStatus) {
		super();
		this.lotStatusId = lotStatusId;
		this.lotStatus = lotStatus;
		this.lotStatusDesc = lotStatusDesc;
		this.nextStatus = nextStatus;
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="lot_status_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getLotStatusId() {
		return this.lotStatusId;
	}

	public void setLotStatusId(int lotStatusId) {
		this.lotStatusId = lotStatusId;
	}
	@Column(name="lot_status", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getLotStatus() {
		return this.lotStatus;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)

	public String getLotStatusDesc() {
		return this.lotStatusDesc;
	}

	public void setLotStatusDesc(String lotStatusDesc) {
		this.lotStatusDesc = lotStatusDesc;
	}
	@Column(name="next_status", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}
}
