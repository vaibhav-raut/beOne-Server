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
import com.beone.shg.net.persistence.model.AccountStatus;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class AccountStatus.
 * @see .AccountStatus
 * @author Hibernate Tools
 */
@Repository("accountStatusDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class AccountStatusDAO extends GenericDAO<AccountStatus> {

    private static final Logger log = LoggerFactory.getLogger(AccountStatusDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<AccountStatus> getTClass() {
    	return AccountStatus.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.account_status";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(AccountStatus transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(AccountStatus transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public AccountStatus merge(AccountStatus transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<AccountStatus> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public AccountStatus findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, enumValue));
    }
    
    public AccountStatus getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, enumValue));
    }
}