package com.beone.shg.net.persistence.spo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.util.DataUtil;

public class GCRComputPO extends GenericPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(GCRComputPO.class);
	
	public GCRComputPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);
	}

	@Override
	final public void execute() {

		try {

			float agregateCreditRating = DataUtil.ZERO_FLOAT;
			int noOfMembers = DataUtil.ZERO_INTEGER;

			for(MemberAcInfo memberAcInfo: groupAcInfo.getMemberAcInfos().values()) {
				if(memberAcInfo.getAc().getCreditRating() > 1.0F) {
					agregateCreditRating += memberAcInfo.getAc().getCreditRating();
					noOfMembers++;
				}
			}

			int newCreditRating = (int)(agregateCreditRating/noOfMembers);
			
			GAc gac = groupAcInfo.getDaoFactory().getGAcDAO().findById(groupAcInfo.getGroupAcNo());
			gac.setCreditRating((float)newCreditRating);
			groupAcInfo.getDaoFactory().getGAcDAO().merge(gac);

		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for GroupAcNo:" + groupAcInfo.getGroupAcNo());
		}
	}
}
