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
import com.beone.udaan.mr.persistence.model.VendorPayment;

/**
 * Home object for domain model class VendorPayment.
 * @see VendorPayment
 */
@Repository("vendorPaymentDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class VendorPaymentDAO extends GenericDAO<VendorPayment> {

    private static final Logger log = LoggerFactory.getLogger(VendorPaymentDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<VendorPayment> getTClass() {
    	return VendorPayment.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.vendor_payment";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(VendorPayment transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(VendorPayment transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public VendorPayment merge(VendorPayment transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<VendorPayment> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public VendorPayment findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.VendorPayment, enumValue));
//    }
//    
//    public VendorPayment getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.VendorPayment, enumValue));
//    }
}