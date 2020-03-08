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

import com.beone.shg.net.persistence.model.LanguageMassage;

/**
 * Home object for domain model class LanguageMassage.
 * @see .LanguageMassage
 * @author Hibernate Tools
 */
@Repository("languageMassageDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class LanguageMassageDAO extends GenericDAO<LanguageMassage> {

    private static final Logger log = LoggerFactory.getLogger(LanguageMassageDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<LanguageMassage> getTClass() {
    	return LanguageMassage.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.language_massage";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(LanguageMassage transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(LanguageMassage transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public LanguageMassage merge(LanguageMassage transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<LanguageMassage> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
}