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

import com.beone.shg.net.persistence.model.MessageType;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class MessageType.
 * @see .MessageType
 * @author Vaibhav
 */
@Repository("messageTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MessageTypeDAO extends GenericDAO<MessageType> {

    private static final Logger log = LoggerFactory.getLogger(MessageTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MessageType> getTClass() {
    	return MessageType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.message_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MessageType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MessageType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MessageType merge(MessageType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MessageType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public MessageType findByValue(String enumValue, String lang) {
    	return findById(EnumCache.getIndexOfMessageType(enumValue, lang));
    }
    
    public MessageType getReferenceByValue(String enumValue, String lang) {
    	return getReferenceById(EnumCache.getIndexOfMessageType(enumValue, lang));
    }
}