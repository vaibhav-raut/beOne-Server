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

import com.beone.shg.net.persistence.model.MonthlyMLoanAc;

/**
 * Home object for domain model class MonthlyMLoanAc.
 * @see .MonthlyMLoanAc
 * @author Hibernate Tools
 */
@Repository("monthlyMLoanAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MonthlyMLoanAcDAO extends GenericDAO<MonthlyMLoanAc> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyMLoanAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MonthlyMLoanAc> getTClass() {
    	return MonthlyMLoanAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.monthly_m_loan_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MonthlyMLoanAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MonthlyMLoanAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MonthlyMLoanAc merge(MonthlyMLoanAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MonthlyMLoanAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    public MonthlyMLoanAc findById(long memberLoanAcNo, String month) {
        getLogger().debug("getting Member Monthly Loan Account");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_loan_ac_no = " + memberLoanAcNo
        			+ " AND month = '" + month + "'", getTClass());
        	
            getLogger().debug("create Query successful");
            return (MonthlyMLoanAc)query.getSingleResult();
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
	public List<MonthlyMLoanAc> getAllForMember(long memberAcNo) {
        getLogger().debug("getting Member Monthly Loan Account");
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