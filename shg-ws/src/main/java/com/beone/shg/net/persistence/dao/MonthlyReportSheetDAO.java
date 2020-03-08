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

import com.beone.shg.net.persistence.model.MonthlyReportSheet;

/**
 * Home object for domain model class MonthlyReportSheet.
 * @see .MonthlyReportSheet
 * @author Hibernate Tools
 */
@Repository("monthlyReportSheetDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MonthlyReportSheetDAO extends GenericDAO<MonthlyReportSheet> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyReportSheetDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MonthlyReportSheet> getTClass() {
    	return MonthlyReportSheet.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.monthly_report_sheet";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MonthlyReportSheet transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MonthlyReportSheet transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MonthlyReportSheet merge(MonthlyReportSheet transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MonthlyReportSheet> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
}