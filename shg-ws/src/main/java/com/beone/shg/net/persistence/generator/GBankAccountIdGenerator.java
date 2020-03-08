package com.beone.shg.net.persistence.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.beone.shg.net.persistence.model.GBankAccount;

public class GBankAccountIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		
		GBankAccount gBankAccount = (GBankAccount) object;
		
		return gBankAccount.getBankAccount().getBankAccountNo();
	}
}
