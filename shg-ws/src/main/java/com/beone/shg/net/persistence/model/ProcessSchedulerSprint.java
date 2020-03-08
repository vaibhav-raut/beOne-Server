package com.beone.shg.net.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="process_scheduler_sprint"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class ProcessSchedulerSprint  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -5454319490471636897L;
	private long processSchedulerSprintId;
	private int processSchedulerId;
	private int districtId;
	private int sprintIndex;
	private int noOfGroups;
	private Date startTime;
	private Date endTime;
	private String info;

	// Constructors

	/** default constructor */
	public ProcessSchedulerSprint() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="process_scheduler_sprint_id", unique=true, nullable=false, insertable=true, updatable=true)
	public long getProcessSchedulerSprintId() {
		return processSchedulerSprintId;
	}

	public void setProcessSchedulerSprintId(long processSchedulerSprintId) {
		this.processSchedulerSprintId = processSchedulerSprintId;
	}
	@Column(name="process_scheduler_id", unique=false, nullable=false, insertable=true, updatable=true)
	
	public int getProcessSchedulerId() {
		return processSchedulerId;
	}

	public void setProcessSchedulerId(int processSchedulerId) {
		this.processSchedulerId = processSchedulerId;
	}
	@Column(name="district_id", unique=false, nullable=false, insertable=true, updatable=true)
	
	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	@Column(name="sprint_index", unique=false, nullable=false, insertable=true, updatable=true)

	public int getSprintIndex() {
		return sprintIndex;
	}

	public void setSprintIndex(int sprintIndex) {
		this.sprintIndex = sprintIndex;
	}
	@Column(name="no_of_groups", unique=false, nullable=false, insertable=true, updatable=true)

	public int getNoOfGroups() {
		return noOfGroups;
	}

	public void setNoOfGroups(int noOfGroups) {
		this.noOfGroups = noOfGroups;
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
	@Column(name="info", unique=false, nullable=true, insertable=true, updatable=true, length=500)

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
