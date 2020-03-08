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
import com.beone.shg.net.persistence.model.LoanSource;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class LoanSource.
 * @see .LoanSource
 * @author Hibernate Tools
 */
@Repository("loanSourceDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class LoanSourceDAO extends GenericDAO<LoanSource> {

    private static final Logger log = LoggerFactory.getLogger(LoanSourceDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<LoanSource> getTClass() {
    	return LoanSource.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.loan_source";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(LoanSource transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(LoanSource transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public LoanSource merge(LoanSource transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<LoanSource> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public LoanSource findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.LoanSource, enumValue));
    }
    
    public LoanSource getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.LoanSource, enumValue));
    }
}