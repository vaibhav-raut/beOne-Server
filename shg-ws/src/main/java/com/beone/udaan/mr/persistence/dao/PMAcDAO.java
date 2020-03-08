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
import com.beone.udaan.mr.persistence.model.PMAc;

/**
 * Home object for domain model class PMAc.
 * @see PMAc
 */
@Repository("pMAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class PMAcDAO extends GenericDAO<PMAc> {

    private static final Logger log = LoggerFactory.getLogger(PMAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<PMAc> getTClass() {
    	return PMAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.p_m_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(PMAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(PMAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public PMAc merge(PMAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<PMAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public PMAc findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.PMAc, enumValue));
//    }
//    
//    public PMAc getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.PMAc, enumValue));
//    }
}