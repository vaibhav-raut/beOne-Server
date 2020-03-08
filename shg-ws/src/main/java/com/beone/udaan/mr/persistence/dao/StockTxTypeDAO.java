package com.beone.udaan.mr.persistence.dao;
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

import com.beone.shg.net.persistence.dao.GenericDAO;
import com.beone.udaan.mr.persistence.model.StockTxType;

/**
 * Home object for domain model class StockTxType.
 * @see StockTxType
 */
@Repository("stockTxTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class StockTxTypeDAO extends GenericDAO<StockTxType> {

    private static final Logger log = LoggerFactory.getLogger(StockTxTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<StockTxType> getTClass() {
    	return StockTxType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.stock_tx_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(StockTxType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(StockTxType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public StockTxType merge(StockTxType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<StockTxType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public StockTxType findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.StockTxType, enumValue));
//    }
//    
//    public StockTxType getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.StockTxType, enumValue));
//    }
}