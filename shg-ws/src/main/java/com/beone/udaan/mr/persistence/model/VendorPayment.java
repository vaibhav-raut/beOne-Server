package com.beone.udaan.mr.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.MProfile;

/**
 * VendorPayment
 */
@Entity
@Table(name="vendor_payment"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class VendorPayment implements java.io.Serializable {

	private static final long serialVersionUID = 6411669696216115362L;
	// Fields    
	private long vendorPaymentId;
	private PInvoice pInvoice;
	private int paymentTypeId;
	private GProfile venderAcNo;
	private MProfile doneByAcNo;
	private MProfile enteredByAcNo;
	private MProfile verifiedByAcNo;
	private int amount;
	private Date creationTs;
	private Date paymentTs;
	private Date verifyTs;
	private String attachment;
	private String description;

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="vendor_payment_id", unique=true, nullable=false, insertable=true, updatable=true)
	public long getVendorPaymentId() {
		return vendorPaymentId;
	}
	public void setVendorPaymentId(long vendorPaymentId) {
		this.vendorPaymentId = vendorPaymentId;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="p_invoice_id", unique=false, nullable=false, insertable=true, updatable=true)
	public PInvoice getPInvoice() {
		return pInvoice;
	}
	public void setPInvoice(PInvoice pInvoice) {
		this.pInvoice = pInvoice;
	}

	@Column(name="payment_type_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="vender_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public GProfile getVenderAcNo() {
		return venderAcNo;
	}
	public void setVenderAcNo(GProfile venderAcNo) {
		this.venderAcNo = venderAcNo;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="done_by_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public MProfile getDoneByAcNo() {
		return doneByAcNo;
	}
	public void setDoneByAcNo(MProfile doneByAcNo) {
		this.doneByAcNo = doneByAcNo;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="entered_by_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public MProfile getEnteredByAcNo() {
		return enteredByAcNo;
	}
	public void setEnteredByAcNo(MProfile enteredByAcNo) {
		this.enteredByAcNo = enteredByAcNo;
	}
	
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="verified_by_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public MProfile getVerifiedByAcNo() {
		return verifiedByAcNo;
	}
	public void setVerifiedByAcNo(MProfile verifiedByAcNo) {
		this.verifiedByAcNo = verifiedByAcNo;
	}

	@Column(name="amount", unique=false, nullable=true, insertable=true, updatable=true, precision=16)
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Column(name="creation_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getCreationTs() {
		return creationTs;
	}
	public void setCreationTs(Date creationTs) {
		this.creationTs = creationTs;
	}

	@Column(name="payment_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getPaymentTs() {
		return paymentTs;
	}
	public void setPaymentTs(Date paymentTs) {
		this.paymentTs = paymentTs;
	}

	@Column(name="verify_ts", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public Date getVerifyTs() {
		return verifyTs;
	}
	public void setVerifyTs(Date verifyTs) {
		this.verifyTs = verifyTs;
	}

	@Column(name="attachment", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
