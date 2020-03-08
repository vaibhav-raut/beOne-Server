package com.beone.shg.net.persistence.dao;
// default package
// Generated Mar 22, 2014 6:10:20 PM by Hibernate Tools 3.1.0.beta4

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.beone.shg.net.persistence.model.BankAccount;

/**
 * Home object for domain model class BankAccount.
 * @see .BankAccount
 * @author Hibernate Tools
 */
@Repository("bankAccountDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class BankAccountDAO extends GenericDAO<BankAccount> {

    private static final Logger log = LoggerFactory.getLogger(BankAccountDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<BankAccount> getTClass() {
    	return BankAccount.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.bank_account";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(BankAccount transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(BankAccount transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public BankAccount merge(BankAccount transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<BankAccount> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
}