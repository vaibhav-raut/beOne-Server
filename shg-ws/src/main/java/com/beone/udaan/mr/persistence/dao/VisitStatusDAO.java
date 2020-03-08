package com.beone.udaan.mr.persistence.dao;
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

import com.beone.shg.net.persistence.dao.GenericDAO;
import com.beone.udaan.mr.persistence.model.VisitStatus;

/**
 * Home object for domain model class VisitStatus.
 * @see VisitStatus
 */
@Repository("visitStatusDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class VisitStatusDAO extends GenericDAO<VisitStatus> {

    private static final Logger log = LoggerFactory.getLogger(VisitStatusDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<VisitStatus> getTClass() {
    	return VisitStatus.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.visit_status";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(VisitStatus transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(VisitStatus transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public VisitStatus merge(VisitStatus transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<VisitStatus> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public VisitStatus findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.VisitStatus, enumValue));
//    }
//    
//    public VisitStatus getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.VisitStatus, enumValue));
//    }
}