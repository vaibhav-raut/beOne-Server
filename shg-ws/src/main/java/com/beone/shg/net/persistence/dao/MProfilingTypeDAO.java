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

import com.beone.shg.net.persistence.model.MProfilingType;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class MProfilingType.
 * @see .MProfilingType
 * @author Hibernate Tools
 */
@Repository("mProfilingTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MProfilingTypeDAO extends GenericDAO<MProfilingType> {

    private static final Logger log = LoggerFactory.getLogger(MProfilingTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MProfilingType> getTClass() {
    	return MProfilingType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.m_profiling_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MProfilingType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MProfilingType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MProfilingType merge(MProfilingType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MProfilingType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public MProfilingType findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfMProfilingType(enumValue));
    }
    
    public MProfilingType getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfMProfilingType(enumValue));
    }
}