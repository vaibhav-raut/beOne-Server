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

import com.beone.shg.net.persistence.model.FundType;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class FundType.
 * @see .FundType
 * @author Hibernate Tools
 */
@Repository("fundTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class FundTypeDAO extends GenericDAO<FundType> {

    private static final Logger log = LoggerFactory.getLogger(FundTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<FundType> getTClass() {
    	return FundType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.fund_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(FundType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(FundType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public FundType merge(FundType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<FundType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public FundType findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfFundType(enumValue));
    }
    
    public FundType getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfFundType(enumValue));
    }
}