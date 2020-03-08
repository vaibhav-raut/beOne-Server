package com.beone.shg.net.persistence.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.util.DataUtil;

public class MemberIdGenerator implements IdentifierGenerator {

	private static Logger log = LoggerFactory.getLogger(MemberIdGenerator.class);

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		
		MProfile mProfile = (MProfile) object;
		Long memberId = -1L;
				
		log.debug("MemberIdGenerator-> GroupMemberCounter:" + mProfile.getGroupMemberCounter() +
				", ParentGroupAcNo:" + mProfile.getParentGroupAcNo());
		
		if(mProfile.getGroupMemberCounter() >= 0 && mProfile.getParentGroupAcNo() > 0) {
			memberId = ((DataUtil.MEMBER_RANGE_FOR_GROUP * mProfile.getParentGroupAcNo()) + mProfile.getGroupMemberCounter() + 1);
		}
		
		return memberId;
	}
}
