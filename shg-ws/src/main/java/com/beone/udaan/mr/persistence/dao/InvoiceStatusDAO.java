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
import com.beone.udaan.mr.persistence.model.InvoiceStatus;

/**
 * Home object for domain model class InvoiceStatus.
 * @see InvoiceStatus
 */
@Repository("invoiceStatusDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class InvoiceStatusDAO extends GenericDAO<InvoiceStatus> {

    private static final Logger log = LoggerFactory.getLogger(InvoiceStatusDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<InvoiceStatus> getTClass() {
    	return InvoiceStatus.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.invoice_status";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(InvoiceStatus transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(InvoiceStatus transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public InvoiceStatus merge(InvoiceStatus transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<InvoiceStatus> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public InvoiceStatus findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.InvoiceStatus, enumValue));
//    }
//    
//    public InvoiceStatus getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.InvoiceStatus, enumValue));
//    }
}