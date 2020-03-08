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
import com.beone.udaan.mr.persistence.model.PHubAc;

/**
 * Home object for domain model class PHubAc.
 * @see PHubAc
 */
@Repository("pHubAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class PHubAcDAO extends GenericDAO<PHubAc> {

    private static final Logger log = LoggerFactory.getLogger(PHubAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<PHubAc> getTClass() {
    	return PHubAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.p_hub_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(PHubAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(PHubAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public PHubAc merge(PHubAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<PHubAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public PHubAc findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.PHubAc, enumValue));
//    }
//    
//    public PHubAc getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.PHubAc, enumValue));
//    }
}