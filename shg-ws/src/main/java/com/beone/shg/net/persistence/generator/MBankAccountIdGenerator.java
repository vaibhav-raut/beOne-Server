package com.beone.shg.net.persistence.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.beone.shg.net.persistence.model.MBankAccount;

public class MBankAccountIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		
		MBankAccount mBankAccount = (MBankAccount) object;
		
		return mBankAccount.getBankAccount().getBankAccountNo();
	}
}
