package com.beone.shg.net.persistence.spo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;

public abstract class MemberPO extends GenericPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(MemberPO.class);
	
	public MemberPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);
	}

	@Override
	final public void execute() {
		for(MemberAcInfo memberAcInfo: groupAcInfo.getMemberAcInfos().values()) {
			try {
				executeMemberPO(memberAcInfo);
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		}
	}
	
	public abstract void executeMemberPO(MemberAcInfo memberAcInfo);	
}
