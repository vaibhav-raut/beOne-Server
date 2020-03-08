package com.beone.shg.net.persistence.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.beone.shg.net.persistence.model.ScheduleProcessError;


/**
 * Home object for domain model class ScheduleProcessError.
 * @see .ScheduleProcessError
 */
@Repository("scheduleProcessErrorDAO")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ScheduleProcessErrorDAO extends GenericDAO<ScheduleProcessError> {

	protected static final Logger log = LoggerFactory.getLogger(ScheduleProcessErrorDAO.class);
    
    @PersistenceContext 
    protected EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<ScheduleProcessError> getTClass() {
    	return ScheduleProcessError.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.schedule_process_error";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(ScheduleProcessError transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(ScheduleProcessError transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public ScheduleProcessError merge(ScheduleProcessError transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<ScheduleProcessError> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

}