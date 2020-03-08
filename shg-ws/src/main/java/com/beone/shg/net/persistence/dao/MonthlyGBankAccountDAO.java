package com.beone.shg.net.persistence.dao;
// default package
// Generated Mar 22, 2014 6:10:20 PM by Hibernate Tools 3.1.0.beta4

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.beone.shg.net.persistence.model.MonthlyGBankAccount;

/**
 * Home object for domain model class MonthlyGBankAccount.
 * @see .MonthlyGBankAccount
 * @author Hibernate Tools
 */
@Repository("monthlyGBankAccountDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MonthlyGBankAccountDAO extends GenericDAO<MonthlyGBankAccount> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyGBankAccountDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MonthlyGBankAccount> getTClass() {
    	return MonthlyGBankAccount.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.monthly_g_bank_account";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MonthlyGBankAccount transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MonthlyGBankAccount transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MonthlyGBankAccount merge(MonthlyGBankAccount transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MonthlyGBankAccount> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

	public MonthlyGBankAccount findById(long gGBankAccountNo, String month) {
        getLogger().debug("getting Group Bank Account per Month");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE bank_account_no = " + gGBankAccountNo
        			+ " AND month = '" + month + "'", getTClass());
        	
            getLogger().debug("create Query successful");
            return (MonthlyGBankAccount)query.getSingleResult();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
	public List<MonthlyGBankAccount> getAllForGroupForMonth(long groupAcNo, String month) {
        getLogger().debug("getting Group Account By TxType");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND month = '" + month + "'", getTClass());
        	
            getLogger().debug("create Query successful");
            return query.getResultList();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
	public List<MonthlyGBankAccount> getAllForGroup(long groupAcNo) {
        getLogger().debug("getting Group Account By TxType");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo, getTClass());
        	
            getLogger().debug("create Query successful");
            return query.getResultList();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            return null;
        }
    }
}