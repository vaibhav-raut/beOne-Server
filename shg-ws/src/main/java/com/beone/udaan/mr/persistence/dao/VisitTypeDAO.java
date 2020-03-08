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
import com.beone.udaan.mr.persistence.model.VisitType;

/**
 * Home object for domain model class VisitType.
 * @see VisitType
 */
@Repository("visitTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class VisitTypeDAO extends GenericDAO<VisitType> {

    private static final Logger log = LoggerFactory.getLogger(VisitTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<VisitType> getTClass() {
    	return VisitType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.visit_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(VisitType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(VisitType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public VisitType merge(VisitType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<VisitType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public VisitType findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.VisitType, enumValue));
//    }
//    
//    public VisitType getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.VisitType, enumValue));
//    }
}