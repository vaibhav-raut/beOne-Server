package com.beone.shg.net.persistence.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.beone.shg.net.persistence.model.BankProfile;

public class BankProfileIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		
		BankProfile bankProfile = (BankProfile) object;
		
		return bankProfile.getGProfile().getGAcNo();
	}
}
