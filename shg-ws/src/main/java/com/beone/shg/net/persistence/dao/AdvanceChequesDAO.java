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

import com.beone.shg.net.persistence.model.AdvanceCheques;

/**
 * Home object for domain model class AdvanceCheques.
 * @see .AdvanceCheques
 * @author Hibernate Tools
 */
@Repository("advanceChequesDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class AdvanceChequesDAO extends GenericDAO<AdvanceCheques> {

    private static final Logger log = LoggerFactory.getLogger(AdvanceChequesDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<AdvanceCheques> getTClass() {
    	return AdvanceCheques.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.advance_cheques";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(AdvanceCheques transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(AdvanceCheques transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public AdvanceCheques merge(AdvanceCheques transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<AdvanceCheques> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
}