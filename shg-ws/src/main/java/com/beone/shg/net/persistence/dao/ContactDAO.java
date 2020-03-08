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

import com.beone.shg.net.persistence.model.Contact;

/**
 * Home object for domain model class Contact.
 * @see .Contact
 * @author Hibernate Tools
 */
@Repository("contactDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ContactDAO extends GenericDAO<Contact> {

    private static final Logger log = LoggerFactory.getLogger(ContactDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<Contact> getTClass() {
    	return Contact.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.contact";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(Contact transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(Contact transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public Contact merge(Contact transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<Contact> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
}