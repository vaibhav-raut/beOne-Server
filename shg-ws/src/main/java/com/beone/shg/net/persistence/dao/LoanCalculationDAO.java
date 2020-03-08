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
import com.beone.shg.net.persistence.model.LoanCalculation;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class LoanCalculation.
 * @see .LoanCalculation
 * @author Hibernate Tools
 */
@Repository("loanCalculationDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class LoanCalculationDAO extends GenericDAO<LoanCalculation> {

    private static final Logger log = LoggerFactory.getLogger(LoanCalculationDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<LoanCalculation> getTClass() {
    	return LoanCalculation.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.loan_calculation";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(LoanCalculation transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(LoanCalculation transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public LoanCalculation merge(LoanCalculation transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<LoanCalculation> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public LoanCalculation findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.LoanCalculation, enumValue));
    }
    
    public LoanCalculation getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.LoanCalculation, enumValue));
    }
}