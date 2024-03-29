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

import com.beone.shg.net.persistence.model.GAc;

/**
 * Home object for domain model class GAc.
 * @see .GAc
 * @author Hibernate Tools
 */
@Repository("gAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GAcDAO extends GenericDAO<GAc> {

    private static final Logger log = LoggerFactory.getLogger(GAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GAc> getTClass() {
    	return GAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.g_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GAc merge(GAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
}