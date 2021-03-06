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

import com.beone.shg.net.persistence.model.Doc;

/**
 * Home object for domain model class Doc.
 * @see .Doc
 * @author Hibernate Tools
 */
@Repository("docDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class DocDAO extends GenericDAO<Doc> {

    private static final Logger log = LoggerFactory.getLogger(DocDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<Doc> getTClass() {
    	return Doc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.doc_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(Doc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(Doc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public Doc merge(Doc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<Doc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
}