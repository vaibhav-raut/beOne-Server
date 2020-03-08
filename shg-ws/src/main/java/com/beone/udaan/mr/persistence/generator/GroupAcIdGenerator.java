package com.beone.udaan.mr.persistence.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.beone.udaan.mr.persistence.model.PHubAc;

public class GroupAcIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		
		PHubAc groupAc = (PHubAc) object;
		
		return groupAc.getProfile().getGAcNo();
	}
}
