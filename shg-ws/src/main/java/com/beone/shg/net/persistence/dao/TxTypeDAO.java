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

import com.beone.shg.net.persistence.model.TxType;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class TxType.
 * @see .TxType
 * @author Hibernate Tools
 */
@Repository("txTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class TxTypeDAO extends GenericDAO<TxType> {

    private static final Logger log = LoggerFactory.getLogger(TxTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<TxType> getTClass() {
    	return TxType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.tx_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(TxType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(TxType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public TxType merge(TxType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<TxType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public TxType findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfTxType(enumValue));
    }
    
    public TxType getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfTxType(enumValue));
    }
}