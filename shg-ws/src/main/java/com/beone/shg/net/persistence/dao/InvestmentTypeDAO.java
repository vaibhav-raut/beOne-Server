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
import com.beone.shg.net.persistence.model.InvestmentType;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class InvestmentType.
 * @see .InvestmentType
 * @author Hibernate Tools
 */
@Repository("investmentTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class InvestmentTypeDAO extends GenericDAO<InvestmentType> {

    private static final Logger log = LoggerFactory.getLogger(InvestmentTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<InvestmentType> getTClass() {
    	return InvestmentType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.investment_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(InvestmentType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(InvestmentType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public InvestmentType merge(InvestmentType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<InvestmentType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public InvestmentType findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.InvestmentType, enumValue));
    }
    
    public InvestmentType getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.InvestmentType, enumValue));
    }
}