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
import com.beone.udaan.mr.persistence.model.SInvoice;

/**
 * Home object for domain model class SInvoice.
 * @see SInvoice
 */
@Repository("sInvoiceDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class SInvoiceDAO extends GenericDAO<SInvoice> {

    private static final Logger log = LoggerFactory.getLogger(SInvoiceDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<SInvoice> getTClass() {
    	return SInvoice.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.s_invoice";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(SInvoice transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(SInvoice transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public SInvoice merge(SInvoice transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<SInvoice> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public SInvoice findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.SInvoice, enumValue));
//    }
//    
//    public SInvoice getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.SInvoice, enumValue));
//    }
}