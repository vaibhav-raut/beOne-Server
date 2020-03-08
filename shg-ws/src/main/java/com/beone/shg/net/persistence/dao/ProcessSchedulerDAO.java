package com.beone.shg.net.persistence.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.beone.shg.net.persistence.model.ProcessScheduler;


/**
 * Home object for domain model class ProcessScheduler.
 * @see .ProcessScheduler
 */
@Repository("processSchedulerDAO")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProcessSchedulerDAO extends GenericDAO<ProcessScheduler> {

	protected static final Logger log = LoggerFactory.getLogger(ProcessSchedulerDAO.class);
    
    @PersistenceContext 
    protected EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<ProcessScheduler> getTClass() {
    	return ProcessScheduler.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.process_scheduler";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(ProcessScheduler transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(ProcessScheduler transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public ProcessScheduler merge(ProcessScheduler transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<ProcessScheduler> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

}