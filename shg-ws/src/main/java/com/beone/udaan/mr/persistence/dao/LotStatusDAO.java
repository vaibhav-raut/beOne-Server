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
import com.beone.udaan.mr.persistence.model.LotStatus;

/**
 * Home object for domain model class LotStatus.
 * @see LotStatus
 */
@Repository("lotStatusDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class LotStatusDAO extends GenericDAO<LotStatus> {

    private static final Logger log = LoggerFactory.getLogger(LotStatusDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<LotStatus> getTClass() {
    	return LotStatus.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.lot_status";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(LotStatus transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(LotStatus transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public LotStatus merge(LotStatus transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<LotStatus> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public LotStatus findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.LotStatus, enumValue));
//    }
//    
//    public LotStatus getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.LotStatus, enumValue));
//    }
}