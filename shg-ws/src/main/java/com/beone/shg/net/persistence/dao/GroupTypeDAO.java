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

import com.beone.shg.net.persistence.model.GroupType;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class GroupType.
 * @see .GroupType
 * @author Hibernate Tools
 */
@Repository("groupTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GroupTypeDAO extends GenericDAO<GroupType> {

    private static final Logger log = LoggerFactory.getLogger(GroupTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GroupType> getTClass() {
    	return GroupType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.group_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GroupType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GroupType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GroupType merge(GroupType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GroupType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public GroupType findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfGroupType(enumValue));
    }
    
    public GroupType getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfGroupType(enumValue));
    }
}