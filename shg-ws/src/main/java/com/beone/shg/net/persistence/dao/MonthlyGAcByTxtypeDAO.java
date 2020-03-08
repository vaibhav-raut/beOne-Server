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

import com.beone.shg.net.persistence.model.MonthlyGAcByTxtype;

/**
 * Home object for domain model class MonthlyGAcByTxtype.
 * @see .MonthlyGAcByTxtype
 * @author Hibernate Tools
 */
@Repository("monthlyGAcByTxtypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MonthlyGAcByTxtypeDAO extends GenericDAO<MonthlyGAcByTxtype> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyGAcByTxtypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MonthlyGAcByTxtype> getTClass() {
    	return MonthlyGAcByTxtype.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.monthly_g_ac_by_txtype";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MonthlyGAcByTxtype transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MonthlyGAcByTxtype transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MonthlyGAcByTxtype merge(MonthlyGAcByTxtype transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MonthlyGAcByTxtype> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<MonthlyGAcByTxtype> getAllForGroupForMonth(long groupAcNo, String month) {
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

    public MonthlyGAcByTxtype findById(long groupAcNo, int txTypeId, String month) {
        getLogger().debug("getting Group Account By TxType");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no =" + groupAcNo
        			+ " AND month = '" + month + "'"
        			+ " AND tx_type_id =" + txTypeId, getTClass());
        	
            getLogger().debug("create Query successful");
            return (MonthlyGAcByTxtype)query.getSingleResult();
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