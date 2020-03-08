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

import com.beone.shg.net.persistence.model.BankAcUpload;

/**
 * Home object for domain model class BankAcUpload.
 * @see .BankAcUpload
 * @author vaibhav
 */
@Repository("bankAcUploadDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class BankAcUploadDAO extends GenericDAO<BankAcUpload> {

    private static final Logger log = LoggerFactory.getLogger(BankAcUploadDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<BankAcUpload> getTClass() {
    	return BankAcUpload.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.bank_ac_upload";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(BankAcUpload transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(BankAcUpload transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public BankAcUpload merge(BankAcUpload transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<BankAcUpload> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
}