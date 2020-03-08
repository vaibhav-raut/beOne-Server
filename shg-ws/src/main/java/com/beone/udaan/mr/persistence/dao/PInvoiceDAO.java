package com.beone.udaan.mr.persistence.dao;
// default package
// Generated Mar 22, 2014 6:10:20 PM by Hibernate Tools 3.1.0.beta4

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.beone.shg.net.persistence.dao.GenericDAO;
import com.beone.udaan.mr.persistence.model.PInvoice;
import com.beone.udaan.mr.persistence.support.EnumUtilMr;

/**
 * Home object for domain model class PInvoice.
 * @see PInvoice
 */
@Repository("pInvoiceDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class PInvoiceDAO extends GenericDAO<PInvoice> {

    private static final Logger log = LoggerFactory.getLogger(PInvoiceDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<PInvoice> getTClass() {
    	return PInvoice.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.p_invoice";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(PInvoice transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(PInvoice transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public PInvoice merge(PInvoice transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<PInvoice> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
    public List<PInvoice> getOpenPurchaseInvoice() {
    	getLogger().debug("getting Open Purchase Invoice List");

    	try {
    		Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
    				+ " WHERE invoice_status_id IN " + EnumUtilMr.getOpenPInvoice()), getTClass());

    		return (List<PInvoice>) query.getResultList();       
    	}
    	catch (NoResultException re) {
    		return null;
    	}
    	catch (RuntimeException re) {
    		getLogger().error("create Query failed", re);
    		throw re;
    	}
    }

    @SuppressWarnings("unchecked")
    public List<PInvoice> getOpenPurchaseInvoiceForVendor(long vendorAcNo) {
    	getLogger().debug("getting Open Purchase Invoice List for Vendor");

    	try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE vender_ac_no = " + vendorAcNo
					+ " AND invoice_status_id IN " + EnumUtilMr.getOpenPInvoice()), getTClass());

    		return (List<PInvoice>) query.getResultList();       
    	}
    	catch (NoResultException re) {
    		return null;
    	}
    	catch (RuntimeException re) {
    		getLogger().error("create Query failed", re);
    		throw re;
    	}
    }

    @SuppressWarnings("unchecked")
    public List<PInvoice> getAllPurchaseInvoiceForVendor(long vendorAcNo) {
    	getLogger().debug("getting Purchase Invoice List for Vendor");

    	try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE vender_ac_no = " + vendorAcNo), getTClass());

    		return (List<PInvoice>) query.getResultList();       
    	}
    	catch (NoResultException re) {
    		return null;
    	}
    	catch (RuntimeException re) {
    		getLogger().error("create Query failed", re);
    		throw re;
    	}
    }
}