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
import com.beone.shg.net.persistence.model.PaymentMode;
import com.beone.shg.net.persistence.support.EnumCache;


/**
 * Home object for domain model class PaymentMode.
 * @see .PaymentMode
 * @author Hibernate Tools
 */
@Repository("paymentModeDAO")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PaymentModeDAO extends GenericDAO<PaymentMode> {

	protected static final Logger log = LoggerFactory.getLogger(PaymentModeDAO.class);
    
    @PersistenceContext 
    protected EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<PaymentMode> getTClass() {
    	return PaymentMode.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.payment_mode";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(PaymentMode transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(PaymentMode transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public PaymentMode merge(PaymentMode transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<PaymentMode> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public PaymentMode findByValue(String enumValue) {
    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, enumValue));
    }
    
    public PaymentMode getReferenceByValue(String enumValue) {
    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, enumValue));
    }

}