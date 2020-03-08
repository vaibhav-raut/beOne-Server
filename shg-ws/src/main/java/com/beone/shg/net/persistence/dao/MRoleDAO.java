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

import com.beone.shg.net.persistence.model.MRole;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class MRole.
 * @see .MRole
 * @author Hibernate Tools
 */
@Repository("mRoleDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MRoleDAO extends GenericDAO<MRole> {

    private static final Logger log = LoggerFactory.getLogger(MRoleDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MRole> getTClass() {
    	return MRole.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.m_role";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MRole transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MRole transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MRole merge(MRole transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MRole> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public MRole findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfMRole(enumValue));
    }
    
    public MRole getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfMRole(enumValue));
    }
}