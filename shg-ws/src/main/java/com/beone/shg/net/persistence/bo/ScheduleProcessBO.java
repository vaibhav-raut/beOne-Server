package com.beone.shg.net.persistence.bo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.job.JobQueueManager;
import com.beone.shg.net.persistence.model.District;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.ProcessScheduler;
import com.beone.shg.net.persistence.model.ProcessSchedulerSprint;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.model.resp.DistrictPS;
import com.beone.shg.net.webservice.rest.model.resp.ProcessSchedulerRequest;
import com.beone.shg.net.webservice.rest.model.resp.SPRequest;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("scheduleProcessBO")
public class ScheduleProcessBO extends BaseBO {
	private final static Logger LOGGER = LoggerFactory.getLogger(ScheduleProcessBO.class);

	private ProcessSchedulerRequest psObject;
	
	public void process(SPRequest request) throws BadRequestException {
		
		if(request.getGroupAcNos() == null || request.getGroupAcNos().isEmpty()) {
			throw new BadRequestException("Invalid Group Account NOs");
		}
		for(Long groupAcNo : request.getGroupAcNos()) {
			if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
				throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
			}
		}
		if(request.getPoConstList() == null || request.getPoConstList().isEmpty()) {
			throw new BadRequestException("Invalid Process Names");
		}
		if(ConversionUtil.isTimeValid(request.getStartTime())) {
			throw new BadRequestException("Invalid Start Time");
		}
		if(ConversionUtil.isTimeValid(request.getEndTime())) {
			throw new BadRequestException("Invalid End Time");
		}

		// Transaction Post Processing
		scheduleProcess(request);
	}

	public SPRequest backDateProcess(SPRequest request) throws BadRequestException {
		
		if(request.getGroupAcNos() == null || request.getGroupAcNos().isEmpty()) {
			throw new BadRequestException("Invalid Group Account NOs");
		}
		for(Long groupAcNo : request.getGroupAcNos()) {
			if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
				throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
			}
		}
		if(request.getPoConstList() == null || request.getPoConstList().isEmpty()) {
			throw new BadRequestException("Invalid Process Names");
		}
		if(request.getBackProcessDate() == null || request.getBackProcessDate().isEmpty()) {
			throw new BadRequestException("Invalid Back Process Date");
		}
		
		request.setStartTime(DateUtil.getStartTimeOfMonth(request.getBackProcessDate()));
		request.setEndTime(DateUtil.getEndTimeOfMonth(request.getBackProcessDate()));
		
		// Transaction Post Processing
		scheduleProcess(request);
		
		return request;

	}
	
	protected void scheduleProcess(SPRequest request) {

		// Transaction Post Processing
		for(Long groupAcNo: request.getGroupAcNos()) {
			JobQueueManager.addToJobQueue(groupAcNo, processJobBuilder.buildScheduleProcessJob(groupAcNo, 
					request.getStartTime(), request.getEndTime(), request.getPoConstList()));
		}		
	}

    private static List<String> poConstList = new ArrayList<String>();
    	
    static {
    	poConstList.add("M_AUTO_APPROVE_TXS");
    	poConstList.add("M_SAVING_INTEREST_COMPUTE");
    	poConstList.add("M_TODO_TX_OVERDUE");
    	poConstList.add("M_TODO_TX_MISSED");
    	poConstList.add("M_CREDIT_RATING");
    	poConstList.add("G_CREDIT_RATING");
    	poConstList.add("M_SAVING_MONTHLY_ACCOUNT");
    	poConstList.add("G_SAVING_MONTHLY_ACCOUNT");
    	poConstList.add("M_BUILD_TODO_TXS");
    };

	public ProcessSchedulerRequest processScheduler(ProcessSchedulerRequest psRequest) throws BadRequestException {
		
		if(this.psObject != null) {
			throw new BadRequestException("Process Scheduler Already Active!");
		} else {
			this.psObject = psRequest;
		}
		
		List<District> districts = daoFactory.getDistrictDAO().getAllDistricts(EnumConst.Lang_English);
		
		long startTime = DateUtil.getStartTimeOfMonth(psRequest.getProcessDate());
		long endTime = DateUtil.getEndTimeOfMonth(psRequest.getProcessDate());
		
		for(District district : districts) {
			psRequest.addDistrictPS(DistrictPS.buildDistrict(district));
		}
		
		ProcessScheduler processScheduler = new ProcessScheduler();
		processScheduler.setStartTime(DateUtil.getCurrentTimeDate());
		processScheduler.setDoneByMemberAcNo(psRequest.getDoneByMemberAcNo());
		processScheduler.setSchedulerKey(psRequest.getSchedulerKey());
		daoFactory.getProcessSchedulerDAO().persist(processScheduler);

		int sprintIndex;
		int groupIndex;
		ProcessSchedulerSprint sprint = null;
		StringBuilder info = null;

		for(DistrictPS district: psRequest.getDistrictPSs()) {
			district.setStartTime(System.currentTimeMillis());
			sprintIndex = DataUtil.ZERO_INTEGER;
			groupIndex = DataUtil.ZERO_INTEGER;
			
			do {
				if(JobQueueManager.getGroupsInJobQueue() < 20) {
					
					if(sprint != null) {
						sprint.setEndTime(DateUtil.getCurrentTimeDate());
						sprint.setInfo(info.toString());
						daoFactory.getProcessSchedulerSprintDAO().merge(sprint);

						district.setDoneNoOfGroup(district.getDoneNoOfGroup() + sprint.getNoOfGroups());
					}

					sprint = null;
					info = new StringBuilder();
					
					List<GProfile> gProfiles = daoFactory.getGProfileDAO().getGroupByRange(district.getDistrictId(), groupIndex, 100);
					
					if(gProfiles == null || gProfiles.isEmpty()) {
						break;
					}
					
					sprint = new ProcessSchedulerSprint();
					sprint.setProcessSchedulerId(processScheduler.getProcessSchedulerId());
					sprint.setDistrictId(district.getDistrictId());
					sprint.setSprintIndex(sprintIndex);
					sprint.setNoOfGroups(gProfiles.size());
					sprint.setStartTime(DateUtil.getCurrentTimeDate());
					daoFactory.getProcessSchedulerSprintDAO().persist(sprint);
					
					for(GProfile gProfile: gProfiles) {
						if((EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()).equals(EnumConst.ActiveStatus_Active))) {
							
							JobQueueManager.addToJobQueue(gProfile.getGAcNo(), processJobBuilder.buildScheduleProcessJob(gProfile.getGAcNo(), 
									startTime, endTime, poConstList));
							
							info.append(gProfile.getGAcNo() + ", ");
						}
					}
					
					sprintIndex++;
					groupIndex += 100;
				} else {
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						LOGGER.error(e.toString());
					}
				}
			} while(true);
		}
		
		this.psObject = null;
		
		return psRequest;
	}
}
