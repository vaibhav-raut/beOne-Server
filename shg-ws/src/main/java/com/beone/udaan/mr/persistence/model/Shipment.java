package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="shipment"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class Shipment implements java.io.Serializable {

	private static final long serialVersionUID = 5655118506771796434L;
	
	private long pkgId;
	private Pkg pkg;
	private int shipmentStatusId;
	private String title;
	private String to;
	private String toAddress;
	private String from;
	private String fromAddress;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="shipment_id", unique=true, nullable=false, insertable=true, updatable=true)
	
	public long getPkgId() {
		return pkgId;
	}
	public void setPkgId(long pkgId) {
		this.pkgId = pkgId;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="pkg_id", unique=false, nullable=false, insertable=true, updatable=true)
	public Pkg getPkg() {
		return pkg;
	}
	public void setPkg(Pkg pkg) {
		this.pkg = pkg;
	}

	@Column(name="shipment_status_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getShipmentStatusId() {
		return shipmentStatusId;
	}
	public void setShipmentStatusId(int shipmentStatusId) {
		this.shipmentStatusId = shipmentStatusId;
	}

	@Column(name="title", unique=false, nullable=false, insertable=true, updatable=true, length=200)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="to", unique=false, nullable=false, insertable=true, updatable=true, length=100)
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	@Column(name="to_address", unique=false, nullable=false, insertable=true, updatable=true, length=300)
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	@Column(name="from", unique=false, nullable=false, insertable=true, updatable=true, length=100)
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}

	@Column(name="from_address", unique=false, nullable=false, insertable=true, updatable=true, length=300)
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

}
