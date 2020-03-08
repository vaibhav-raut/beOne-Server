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
import com.beone.udaan.mr.persistence.model.PkgStatus;

/**
 * Home object for domain model class PkgStatus.
 * @see PkgStatus
 */
@Repository("pkgStatusDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class PkgStatusDAO extends GenericDAO<PkgStatus> {

    private static final Logger log = LoggerFactory.getLogger(PkgStatusDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<PkgStatus> getTClass() {
    	return PkgStatus.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.pkg_status";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(PkgStatus transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(PkgStatus transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public PkgStatus merge(PkgStatus transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<PkgStatus> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public PkgStatus findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.PkgStatus, enumValue));
//    }
//    
//    public PkgStatus getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.PkgStatus, enumValue));
//    }
}