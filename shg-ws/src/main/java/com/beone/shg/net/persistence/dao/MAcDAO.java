package com.beone.shg.net.persistence.dao;
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

import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.util.ConversionUtil;

/**
 * Home object for domain model class MAc.
 * @see .MAc
 * @author Hibernate Tools
 */
@Repository("mAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MAcDAO extends GenericDAO<MAc> {

    private static final Logger log = LoggerFactory.getLogger(MAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MAc> getTClass() {
    	return MAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.m_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MAc merge(MAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<MAc> getAllGroupMembers(long groupAcNo) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo), getTClass());
        	
//        	query.setParameter(TABLE_NAME, getTableName());
//        	query.setParameter(ROW_LIMIT, limit);
        	
            getLogger().debug("create Query successful");
            return query.getResultList();
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