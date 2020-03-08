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

import com.beone.shg.net.persistence.model.MonthlyMAc;

/**
 * Home object for domain model class MonthlyMAc.
 * @see .MonthlyMAc
 * @author Hibernate Tools
 */
@Repository("monthlyMAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MonthlyMAcDAO extends GenericDAO<MonthlyMAc> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyMAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MonthlyMAc> getTClass() {
    	return MonthlyMAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.monthly_m_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MonthlyMAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MonthlyMAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MonthlyMAc merge(MonthlyMAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MonthlyMAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    public MonthlyMAc findById(long memberAcNo, String month) {
        getLogger().debug("getting Member Account By Month");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo
        			+ " AND month = '" + month + "'", getTClass());
        	
            getLogger().debug("create Query successful");
            return (MonthlyMAc)query.getSingleResult();
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
	public List<MonthlyMAc> getAllForMember(long memberAcNo) {
        getLogger().debug("getting Monthly Member Accounts");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo, getTClass());
        	
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