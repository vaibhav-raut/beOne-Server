package com.beone.shg.net.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="process_scheduler"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ProcessScheduler  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -5454319490471636897L;
	private int processSchedulerId;
	private Date startTime;
	private Date endTime;
	private long doneByMemberAcNo;
	private long schedulerKey;

	// Constructors

	/** default constructor */
	public ProcessScheduler() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="process_scheduler_id", unique=true, nullable=false, insertable=true, updatable=true)
	
	public int getProcessSchedulerId() {
		return processSchedulerId;
	}

	public void setProcessSchedulerId(int processSchedulerId) {
		this.processSchedulerId = processSchedulerId;
	}
	@Column(name="start_time", unique=false, nullable=true, insertable=true, updatable=true, length=19)

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Column(name="end_time", unique=false, nullable=true, insertable=true, updatable=true, length=19)

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name="done_by_member", unique=false, nullable=false, insertable=true, updatable=true)

	public long getDoneByMemberAcNo() {
		return this.doneByMemberAcNo;
	}

	public void setDoneByMemberAcNo(long doneByMemberAcNo) {
		this.doneByMemberAcNo = doneByMemberAcNo;
	}
	@Column(name="scheduler_key", unique=false, nullable=false, insertable=true, updatable=true)

	public long getSchedulerKey() {
		return schedulerKey;
	}

	public void setSchedulerKey(long schedulerKey) {
		this.schedulerKey = schedulerKey;
	}
}
