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

import com.beone.shg.net.persistence.model.MonthlyReport;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class MonthlyReport.
 * @see .MonthlyReport
 * @author Hibernate Tools
 */
@Repository("monthlyReportDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MonthlyReportDAO extends GenericDAO<MonthlyReport> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyReportDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MonthlyReport> getTClass() {
    	return MonthlyReport.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.monthly_report";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MonthlyReport transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MonthlyReport transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MonthlyReport merge(MonthlyReport transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MonthlyReport> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public MonthlyReport findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfMonthlyReports(enumValue));
    }
    
    public MonthlyReport getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfMonthlyReports(enumValue));
    }
}