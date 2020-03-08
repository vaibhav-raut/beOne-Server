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

import com.beone.shg.net.persistence.model.GRules;

/**
 * Home object for domain model class GRules.
 * @see .GRules
 * @author Hibernate Tools
 */
@Repository("gRulesDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GRulesDAO extends GenericDAO<GRules> {

    private static final Logger log = LoggerFactory.getLogger(GRulesDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GRules> getTClass() {
    	return GRules.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.g_rules";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GRules transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GRules transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GRules merge(GRules transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GRules> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
}