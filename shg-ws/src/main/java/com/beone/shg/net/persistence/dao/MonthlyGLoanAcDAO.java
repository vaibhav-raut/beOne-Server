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

import com.beone.shg.net.persistence.model.MonthlyGLoanAc;

/**
 * Home object for domain model class MonthlyGLoanAc.
 * @see .MonthlyGLoanAc
 * @author Hibernate Tools
 */
@Repository("monthlyGLoanAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MonthlyGLoanAcDAO extends GenericDAO<MonthlyGLoanAc> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyGLoanAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MonthlyGLoanAc> getTClass() {
    	return MonthlyGLoanAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.monthly_g_loan_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MonthlyGLoanAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MonthlyGLoanAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MonthlyGLoanAc merge(MonthlyGLoanAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MonthlyGLoanAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

	public MonthlyGLoanAc findById(long groupLoanAcNo, String month) {
        getLogger().debug("getting Group Account per Month");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_loan_ac_no = " + groupLoanAcNo
        			+ " AND month = '" + month + "'", getTClass());
        	
            getLogger().debug("create Query successful");
            return (MonthlyGLoanAc)query.getSingleResult();
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
	public List<MonthlyGLoanAc> getAllForGroupForMonth(long groupAcNo, String month) {
        getLogger().debug("getting Group Account per Month");
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
	public List<MonthlyGLoanAc> getAllForGroup(long groupAcNo) {
        getLogger().debug("getting Group Accounts for All Months");
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