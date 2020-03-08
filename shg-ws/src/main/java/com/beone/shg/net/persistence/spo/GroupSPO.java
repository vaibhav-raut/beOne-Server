package com.beone.shg.net.persistence.spo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.bo.ReportBO;
import com.beone.shg.net.persistence.ppo.util.ProcessJobBuilder;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.util.DAOFactory;


public class GroupSPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(GroupSPO.class);
	
	protected DAOFactory daoFactory;
	protected ReportBO reportBO;
	protected ProcessJobBuilder processJobBuilder;
	protected long groupAcNo;
	protected GroupAcInfo groupAcInfo;
	protected long startTime;
	protected long endTime;
	protected List<String> poConstList;
	
	public GroupSPO(DAOFactory daoFactory, ReportBO reportBO, ProcessJobBuilder processJobBuilder, long groupAcNo, long startTime, long endTime, List<String> poConstList) {
		super();
		this.daoFactory = daoFactory;		
		this.reportBO = reportBO;		
		this.processJobBuilder = processJobBuilder;
		this.groupAcNo = groupAcNo;		
		this.startTime = startTime;
		this.endTime = endTime;	
		this.poConstList = poConstList;	
	}

	public void execute() {		
		try {
			this.groupAcInfo = new GroupAcInfo(daoFactory, reportBO, processJobBuilder, groupAcNo, startTime, endTime, poConstList);

			for(GenericPO po: groupAcInfo.getProcessObjects()) {
				po.execute();
			}

			groupAcInfo.clear();
			
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
}
