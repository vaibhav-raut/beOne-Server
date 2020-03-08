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

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.GroupRelation;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class GroupRelation.
 * @see .GroupRelation
 * @author Hibernate Tools
 */
@Repository("groupRelationDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GroupRelationDAO extends GenericDAO<GroupRelation> {

    private static final Logger log = LoggerFactory.getLogger(GroupRelationDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GroupRelation> getTClass() {
    	return GroupRelation.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.group_relation";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GroupRelation transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GroupRelation transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GroupRelation merge(GroupRelation transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GroupRelation> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public GroupRelation findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.GroupRelation, enumValue));
    }
    
    public GroupRelation getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.GroupRelation, enumValue));
    }
}