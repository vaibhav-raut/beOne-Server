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

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.RecoveryPeriod;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class RecoveryPeriod.
 * @see .RecoveryPeriod
 * @author Hibernate Tools
 */
@Repository("recoveryPeriodDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class RecoveryPeriodDAO extends GenericDAO<RecoveryPeriod> {

    private static final Logger log = LoggerFactory.getLogger(RecoveryPeriodDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<RecoveryPeriod> getTClass() {
    	return RecoveryPeriod.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.recovery_period";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(RecoveryPeriod transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(RecoveryPeriod transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public RecoveryPeriod merge(RecoveryPeriod transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<RecoveryPeriod> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public RecoveryPeriod findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, enumValue));
    }
    
    public RecoveryPeriod getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, enumValue));
    }
}