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

import com.beone.shg.net.persistence.model.MrUiAccessCode;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class MrUiAccessCode.
 * @see .MrUiAccessCode
 * @author Hibernate Tools
 */
@Repository("mrUiAccessCodeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MrUiAccessCodeDAO extends GenericDAO<MrUiAccessCode> {

    private static final Logger log = LoggerFactory.getLogger(MrUiAccessCodeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MrUiAccessCode> getTClass() {
    	return MrUiAccessCode.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.mr_ui_access_code";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MrUiAccessCode transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MrUiAccessCode transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MrUiAccessCode merge(MrUiAccessCode transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MrUiAccessCode> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public MrUiAccessCode findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfMrUiAccessCode(enumValue));
    }
    
    public MrUiAccessCode getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfMrUiAccessCode(enumValue));
    }
}