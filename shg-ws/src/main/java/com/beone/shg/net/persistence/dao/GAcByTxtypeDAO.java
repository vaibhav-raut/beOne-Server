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

import com.beone.shg.net.persistence.model.GAcByTxtype;

/**
 * Home object for domain model class GAcByTxtype.
 * @see .GAcByTxtype
 * @author Hibernate Tools
 */
@Repository("gAcByTxtypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GAcByTxtypeDAO extends GenericDAO<GAcByTxtype> {

    private static final Logger log = LoggerFactory.getLogger(GAcByTxtypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GAcByTxtype> getTClass() {
    	return GAcByTxtype.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.g_ac_by_txtype";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GAcByTxtype transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GAcByTxtype transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GAcByTxtype merge(GAcByTxtype transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GAcByTxtype> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    public GAcByTxtype findById(long groupAcNo, int txTypeId) {
        getLogger().debug("getting Group Account By TxType");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND tx_type_id = " + txTypeId, getTClass());
        	
            getLogger().debug("create Query successful");
            return (GAcByTxtype)query.getSingleResult();
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
	public List<GAcByTxtype> getAllForGroup(long groupAcNo) {
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