package com.beone.shg.net.persistence.dao;

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

import com.beone.shg.net.persistence.model.MonthlyMSavingAc;

/**
 * Home object for domain model class MonthlyMSavingAc.
 * @see .MonthlyMSavingAc
 * @author Hibernate Tools
 */
@Repository("monthlyMSavingAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MonthlyMSavingAcDAO extends GenericDAO<MonthlyMSavingAc> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyMSavingAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MonthlyMSavingAc> getTClass() {
    	return MonthlyMSavingAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.monthly_m_saving_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MonthlyMSavingAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MonthlyMSavingAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MonthlyMSavingAc merge(MonthlyMSavingAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MonthlyMSavingAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    public MonthlyMSavingAc findById(long memberSavingAcNo, String month) {
        getLogger().debug("getting Member Monthly Saving Account");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_saving_ac_no = " + memberSavingAcNo
        			+ " AND month = '" + month + "'", getTClass());
        	
            getLogger().debug("create Query successful");
            return (MonthlyMSavingAc)query.getSingleResult();
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
	public List<MonthlyMSavingAc> getAllForMember(long memberAcNo) {
        getLogger().debug("getting Member Monthly Saving Account");
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