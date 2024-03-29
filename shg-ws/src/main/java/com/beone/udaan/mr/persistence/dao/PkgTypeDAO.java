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
import com.beone.udaan.mr.persistence.model.PkgType;

/**
 * Home object for domain model class PkgType.
 * @see PkgType
 */
@Repository("pkgTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class PkgTypeDAO extends GenericDAO<PkgType> {

    private static final Logger log = LoggerFactory.getLogger(PkgTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<PkgType> getTClass() {
    	return PkgType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.pkg_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(PkgType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(PkgType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public PkgType merge(PkgType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<PkgType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public PkgType findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.PkgType, enumValue));
//    }
//    
//    public PkgType getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.PkgType, enumValue));
//    }
}