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
import com.beone.udaan.mr.persistence.model.TagStatus;

/**
 * Home object for domain model class TagStatus.
 * @see TagStatus
 */
@Repository("tagStatusDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class TagStatusDAO extends GenericDAO<TagStatus> {

    private static final Logger log = LoggerFactory.getLogger(TagStatusDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<TagStatus> getTClass() {
    	return TagStatus.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.tag_status";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(TagStatus transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(TagStatus transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public TagStatus merge(TagStatus transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<TagStatus> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public TagStatus findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.TagStatus, enumValue));
//    }
//    
//    public TagStatus getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.TagStatus, enumValue));
//    }
}