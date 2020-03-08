package com.beone.shg.net.persistence.bo;

import org.springframework.stereotype.Component;

import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingData;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("profileBO")
public class ProfileBO extends BaseBO {
	
	public MProfilingData getMProfilingData(long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);		
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		
		return (new MProfilingData(memberAcNo, mProfile.getProfileCode()));
	}

	public MProfilingData updateMProfilingData(MProfilingData data) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(data.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		MProfile mProfile = daoFactory.getMProfileDAO().findById(data.getMemberAcNo());		
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		
		mProfile.setProfileCode(data.getPointsInBytes());
		
		daoFactory.getMProfileDAO().merge(mProfile);
		
		return data;
	}
}
