package com.beone.shg.net.persistence.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.beone.shg.net.persistence.model.ProcessSchedulerSprint;


/**
 * Home object for domain model class ProcessSchedulerSprint.
 * @see .ProcessSchedulerSprint
 */
@Repository("processSchedulerSprintDAO")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProcessSchedulerSprintDAO extends GenericDAO<ProcessSchedulerSprint> {

	protected static final Logger log = LoggerFactory.getLogger(ProcessSchedulerSprintDAO.class);
    
    @PersistenceContext 
    protected EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<ProcessSchedulerSprint> getTClass() {
    	return ProcessSchedulerSprint.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.process_scheduler_sprint";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(ProcessSchedulerSprint transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(ProcessSchedulerSprint transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public ProcessSchedulerSprint merge(ProcessSchedulerSprint transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<ProcessSchedulerSprint> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

}