package com.beone.shg.net.persistence.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.util.DataUtil;

public class GroupIdGenerator implements IdentifierGenerator {

	private static Logger log = LoggerFactory.getLogger(GroupIdGenerator.class);

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		
		GProfile gProfile = (GProfile) object;
		Long groupId = -1L;
		
		log.debug("GroupIdGenerator-> DistrictGroupCounter:" + gProfile.getDistrictGroupCounter() +
				", HomeDistrictId:" + gProfile.getHomeDistrictId());
		
		if(gProfile.getDistrictGroupCounter() >= 0 && gProfile.getHomeDistrictId() > 0) {
			
			groupId = ((DataUtil.GROUP_RANGE_FOR_DISTRICT * gProfile.getHomeDistrictId()) + gProfile.getDistrictGroupCounter() + 1);

		}
		return groupId;
	}
}
