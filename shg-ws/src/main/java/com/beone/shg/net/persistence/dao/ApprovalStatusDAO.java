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
import com.beone.shg.net.persistence.model.ApprovalStatus;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class ApprovalStatus.
 * @see .ApprovalStatus
 * @author Hibernate Tools
 */
@Repository("approvalStatusDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ApprovalStatusDAO extends GenericDAO<ApprovalStatus> {

    private static final Logger log = LoggerFactory.getLogger(ApprovalStatusDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<ApprovalStatus> getTClass() {
    	return ApprovalStatus.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.approval_status";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(ApprovalStatus transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(ApprovalStatus transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public ApprovalStatus merge(ApprovalStatus transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<ApprovalStatus> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public ApprovalStatus findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, enumValue));
    }
    
    public ApprovalStatus getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, enumValue));
    }
}