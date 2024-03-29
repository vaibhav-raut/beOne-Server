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
import com.beone.udaan.mr.persistence.model.PCategory;

/**
 * Home object for domain model class PCategory.
 * @see PCategory
 */
@Repository("pCategoryDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class PCategoryDAO extends GenericDAO<PCategory> {

    private static final Logger log = LoggerFactory.getLogger(PCategoryDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<PCategory> getTClass() {
    	return PCategory.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.p_category";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(PCategory transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(PCategory transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public PCategory merge(PCategory transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<PCategory> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public PCategory findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.PCategory, enumValue));
//    }
//    
//    public PCategory getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.PCategory, enumValue));
//    }
}