package com.beone.shg.net.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schedule_process_error"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ScheduleProcessError  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -5454319490471636897L;
	private long scheduleProcessErrorId;
	private int processSchedulerId;
	private long groupAcNo;
	private long memberAcNo;
	private Date time;
	private String errorJob;
	private String errorMessage;

	// Constructors

	/** default constructor */
	public ScheduleProcessError() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="schedule_process_error_id", unique=true, nullable=false, insertable=true, updatable=true)
	public long getScheduleProcessErrorId() {
		return scheduleProcessErrorId;
	}

	public void setScheduleProcessErrorId(long scheduleProcessErrorId) {
		this.scheduleProcessErrorId = scheduleProcessErrorId;
	}
	@Column(name="process_scheduler_id", unique=false, nullable=false, insertable=true, updatable=true)
	
	public int getProcessSchedulerId() {
		return processSchedulerId;
	}

	public void setProcessSchedulerId(int processSchedulerId) {
		this.processSchedulerId = processSchedulerId;
	}
	@Column(name="group_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getGroupAcNo() {
		return groupAcNo;
	}

	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	@Column(name="member_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public long getMemberAcNo() {
		return memberAcNo;
	}

	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	@Column(name="time", unique=false, nullable=true, insertable=true, updatable=true, length=19)

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	@Column(name="error_job", unique=false, nullable=true, insertable=true, updatable=true, length=50)

	public String getErrorJob() {
		return errorJob;
	}

	public void setErrorJob(String errorJob) {
		this.errorJob = errorJob;
	}
	@Column(name="error_message", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
