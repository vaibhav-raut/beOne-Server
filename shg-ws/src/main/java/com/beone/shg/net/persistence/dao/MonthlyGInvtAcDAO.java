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

import com.beone.shg.net.persistence.model.MonthlyGInvtAc;

/**
 * Home object for domain model class MonthlyGInvtAc.
 * @see .MonthlyGInvtAc
 * @author Hibernate Tools
 */
@Repository("monthlyGInvtAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MonthlyGInvtAcDAO extends GenericDAO<MonthlyGInvtAc> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyGInvtAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MonthlyGInvtAc> getTClass() {
    	return MonthlyGInvtAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.monthly_g_invt_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MonthlyGInvtAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MonthlyGInvtAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MonthlyGInvtAc merge(MonthlyGInvtAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MonthlyGInvtAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

	public MonthlyGInvtAc findById(long groupInvtAcNo, String month) {
        getLogger().debug("getting Group Account per Month");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_invt_ac_no = " + groupInvtAcNo
        			+ " AND month = '" + month + "'", getTClass());
        	
            getLogger().debug("create Query successful");
            return (MonthlyGInvtAc)query.getSingleResult();
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
	public List<MonthlyGInvtAc> getAllForGroupForMonth(long groupAcNo, String month) {
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
	public List<MonthlyGInvtAc> getAllForGroup(long groupAcNo) {
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