package com.beone.shg.net.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="mobile_m"
,catalog="shg"
, uniqueConstraints = {  }
		)
public class MobileM  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1859038640347493416L;

	private long mobileNo;
	private long memberAcNo;
	private int mroleId;
	private int groupTypeId;

	private long mobileNoTran;
	// Constructors

	/** default constructor */
	public MobileM() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MobileMIdGenerator")
	@GenericGenerator(name = "MobileMIdGenerator", strategy = "com.beone.shg.net.persistence.generator.MobileMIdGenerator")
	@Column(name="mobile_no", unique=true, nullable=false, insertable=true, updatable=true)
	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name="m_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
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

	@Transient
	public long getMobileNoTran() {
		return mobileNoTran;
	}

	public void setMobileNoTran(long mobileNoTran) {
		this.mobileNoTran = mobileNoTran;
	}

}
