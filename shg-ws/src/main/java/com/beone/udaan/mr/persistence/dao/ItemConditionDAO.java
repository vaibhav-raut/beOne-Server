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
import com.beone.udaan.mr.persistence.model.ItemCondition;

/**
 * Home object for domain model class ItemCondition.
 * @see ItemCondition
 */
@Repository("itemConditionDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ItemConditionDAO extends GenericDAO<ItemCondition> {

    private static final Logger log = LoggerFactory.getLogger(ItemConditionDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<ItemCondition> getTClass() {
    	return ItemCondition.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.item_condition";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(ItemCondition transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(ItemCondition transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public ItemCondition merge(ItemCondition transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<ItemCondition> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public ItemCondition findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.ItemCondition, enumValue));
//    }
//    
//    public ItemCondition getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.ItemCondition, enumValue));
//    }
}