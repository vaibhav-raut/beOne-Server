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
import com.beone.shg.net.persistence.model.Lang;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class Lang.
 * @see .Lang
 * @author Hibernate Tools
 */
@Repository("langDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class LangDAO extends GenericDAO<Lang> {

    private static final Logger log = LoggerFactory.getLogger(LangDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<Lang> getTClass() {
    	return Lang.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.lang";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(Lang transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(Lang transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public Lang merge(Lang transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<Lang> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public Lang findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.Lang, enumValue));
    }
    
    public Lang getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.Lang, enumValue));
    }

}