package com.beone.udaan.mr.persistence.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.beone.udaan.mr.persistence.model.PMAc;

public class MemberAcIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		
		PMAc memberAc = (PMAc) object;
		
		return memberAc.getMProfile().getMemberAcNo();
	}
}
