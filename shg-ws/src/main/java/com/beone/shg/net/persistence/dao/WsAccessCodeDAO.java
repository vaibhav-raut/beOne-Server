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

import com.beone.shg.net.persistence.model.WsAccessCode;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class WsAccessCode.
 * @see .WsAccessCode
 * @author Hibernate Tools
 */
@Repository("wsAccessCodeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class WsAccessCodeDAO extends GenericDAO<WsAccessCode> {

    private static final Logger log = LoggerFactory.getLogger(WsAccessCodeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<WsAccessCode> getTClass() {
    	return WsAccessCode.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.ws_access_code";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(WsAccessCode transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(WsAccessCode transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public WsAccessCode merge(WsAccessCode transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<WsAccessCode> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public WsAccessCode findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfWsAccessCode(enumValue));
    }
    
    public WsAccessCode getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfWsAccessCode(enumValue));
    }
}