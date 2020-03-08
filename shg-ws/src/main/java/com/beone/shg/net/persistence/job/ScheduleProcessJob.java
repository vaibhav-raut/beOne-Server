package com.beone.shg.net.persistence.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.bo.ReportBO;
import com.beone.shg.net.persistence.ppo.util.ProcessJobBuilder;
import com.beone.shg.net.persistence.spo.GroupSPO;
import com.beone.shg.net.persistence.util.DAOFactory;

public class ScheduleProcessJob implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(ScheduleProcessJob.class);
	
	protected DAOFactory daoFactory;	
	protected ReportBO reportBO;
	protected ProcessJobBuilder processJobBuilder;
	protected long groupAcNo;
	protected long startTime;
	protected long endTime;
	protected List<String> poConstList;
	
	public ScheduleProcessJob(DAOFactory daoFactory, ReportBO reportBO, ProcessJobBuilder processJobBuilder, long groupAcNo, long startTime, long endTime, List<String> poConstList) {
		super();
		this.daoFactory = daoFactory;
		this.reportBO = reportBO;
		this.processJobBuilder = processJobBuilder;
		this.groupAcNo = groupAcNo;
		this.startTime = startTime;
		this.endTime = endTime;	
		this.poConstList = poConstList;	
	}

	@Override
	public void run() {		
		// Execute Scheduled Processing for All Given Groups
		try {
			new GroupSPO(daoFactory, reportBO, processJobBuilder, groupAcNo, startTime, endTime, poConstList).execute();
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
}
