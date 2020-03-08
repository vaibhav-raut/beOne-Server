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
import com.beone.udaan.mr.persistence.model.Lot;

/**
 * Home object for domain model class Lot.
 * @see Lot
 */
@Repository("lotDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class LotDAO extends GenericDAO<Lot> {

    private static final Logger log = LoggerFactory.getLogger(LotDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<Lot> getTClass() {
    	return Lot.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.lot";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(Lot transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(Lot transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public Lot merge(Lot transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<Lot> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
    public List<Lot> getLotsForPurchaseInvoice(long purchaseInvoiceId) {
    	getLogger().debug("getting All Lots for Purchase Invoice");

    	try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE p_invoice_id = " + purchaseInvoiceId), getTClass());

    		return (List<Lot>) query.getResultList();       
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
    public List<Lot> getAllLotsToStock() {
    	getLogger().debug("getting All Lots To Stock");

    	try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE no_to_stock > 0"), getTClass());

    		return (List<Lot>) query.getResultList();       
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
    public List<Lot> getLotsToStockForType(long stockTypeId) {
    	getLogger().debug("getting All Lots To Stock");

    	try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE stock_type_id = " + stockTypeId
					+ " AND no_to_stock > 0"), getTClass());

    		return (List<Lot>) query.getResultList();       
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
    public List<Lot> getLotsToStockForBrand(long brandId) {
    	getLogger().debug("getting All Lots To Stock");

    	try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() + " L" 
        			+ " JOIN stock_type S"
					+ " WHERE L.no_to_stock > 0"
					+ " AND L.stock_type_id = S.stock_type_id"
					+ " AND S.brand_id = " + brandId), getTClass());

    		return (List<Lot>) query.getResultList();       
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