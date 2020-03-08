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
import com.beone.udaan.mr.persistence.model.Pkg;

/**
 * Home object for domain model class Pkg.
 * @see Pkg
 */
@Repository("pkgDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class PkgDAO extends GenericDAO<Pkg> {

    private static final Logger log = LoggerFactory.getLogger(PkgDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<Pkg> getTClass() {
    	return Pkg.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.pkg";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(Pkg transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(Pkg transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public Pkg merge(Pkg transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<Pkg> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public Pkg findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.Pkg, enumValue));
//    }
//    
//    public Pkg getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.Pkg, enumValue));
//    }
}