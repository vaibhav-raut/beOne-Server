package com.beone.shg.net.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="m_message"
,catalog="shg"
, uniqueConstraints = {  }
		)
public class MMessage  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1859038640347493416L;

	private long memberAcNo;
	private int mroleId;
	private int groupTypeId;
	private long mobileNo;
	private String email;
	private int mobileVerified;
	private int emailVerified;
	private long smsSubKey;
	private long emailSubKey;
	private Date lastUpdated;

	private long memberAcNoTran;
	// Constructors

	/** default constructor */
	public MMessage() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMessageIdGenerator")
	@GenericGenerator(name = "MMessageIdGenerator", strategy = "com.beone.shg.net.persistence.generator.MMessageIdGenerator")
	@Column(name="m_ac_no", unique=true, nullable=false, insertable=true, updatable=true)
	public long getMemberAcNo() {
		return memberAcNo;
	}

	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}

	@Column(name="m_role_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getMroleId() {
		return mroleId;
	}

	public void setMroleId(int mroleId) {
		this.mroleId = mroleId;
	}

	@Column(name="group_type_id", unique=false, nullable=false, insertable=true, updatable=true)
	public int getGroupTypeId() {
		return groupTypeId;
	}

	public void setGroupTypeId(int groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	@Column(name="mobile_no", unique=false, nullable=true, insertable=true, updatable=true)
	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name="email", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="mobile_verified", unique=false, nullable=true, insertable=true, updatable=true)
	public int getMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(int mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	@Column(name="email_verified", unique=false, nullable=true, insertable=true, updatable=true)
	public int getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(int emailverified) {
		this.emailVerified = emailverified;
	}

	@Column(name="sms_sub_key", unique=false, nullable=true, insertable=true, updatable=true)
	public long getSmsSubKey() {
		return smsSubKey;
	}

	public void setSmsSubKey(long smsSubKey) {
		this.smsSubKey = smsSubKey;
	}

	@Column(name="email_sub_key", unique=false, nullable=true, insertable=true, updatable=true)
	public long getEmailSubKey() {
		return emailSubKey;
	}

	public void setEmailSubKey(long emailSubKey) {
		this.emailSubKey = emailSubKey;
	}

	@Column(name="last_updated", unique=false, nullable=true, insertable=true, updatable=true, length=19)
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Transient
	public long getMemberAcNoTran() {
		return memberAcNoTran;
	}

	public void setMemberAcNoTran(long memberAcNoTran) {
		this.memberAcNoTran = memberAcNoTran;
	}
}
