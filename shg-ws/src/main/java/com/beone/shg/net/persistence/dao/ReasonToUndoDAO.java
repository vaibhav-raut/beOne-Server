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
import com.beone.shg.net.persistence.model.ReasonToUndo;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class ReasonToUndo.
 * @see .ReasonToUndo
 * @author Hibernate Tools
 */
@Repository("reasonToUndoDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ReasonToUndoDAO extends GenericDAO<ReasonToUndo> {

    private static final Logger log = LoggerFactory.getLogger(ReasonToUndoDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<ReasonToUndo> getTClass() {
    	return ReasonToUndo.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.reason_to_undo";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(ReasonToUndo transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(ReasonToUndo transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public ReasonToUndo merge(ReasonToUndo transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<ReasonToUndo> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public ReasonToUndo findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.ReasonToUndo, enumValue));
    }
    
    public ReasonToUndo getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.ReasonToUndo, enumValue));
    }
}