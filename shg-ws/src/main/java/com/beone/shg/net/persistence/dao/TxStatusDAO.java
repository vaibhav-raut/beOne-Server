package com.beone.shg.net.persistence.dao;
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
import com.beone.shg.net.persistence.model.TxStatus;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class TxStatus.
 * @see .TxStatus
 * @author Hibernate Tools
 */
@Repository("txStatusDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class TxStatusDAO extends GenericDAO<TxStatus> {

    private static final Logger log = LoggerFactory.getLogger(TxStatusDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<TxStatus> getTClass() {
    	return TxStatus.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.tx_status";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(TxStatus transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(TxStatus transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public TxStatus merge(TxStatus transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<TxStatus> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public TxStatus findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, enumValue));
    }
    
    public TxStatus getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, enumValue));
    }
}