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

import com.beone.shg.net.persistence.model.GM;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class GM.
 * @see .GM
 * @author Hibernate Tools
 */
@Repository("gMDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GMDAO extends GenericDAO<GM> {

    private static final Logger log = LoggerFactory.getLogger(GMDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GM> getTClass() {
    	return GM.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.g_m";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GM transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GM transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GM merge(GM transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GM> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public GM findById(long groupAcNo, long memberAcNo) {
        getLogger().debug("getting Group Account By TxType");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND m_ac_no = " + memberAcNo, getTClass());
            getLogger().debug("create Query successful");
            return (GM)query.getSingleResult();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<GM> findByMember(long memberAcNo) {
        getLogger().debug("getting Group Account By TxType");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " GM"
        			+ " JOIN g_profile G"
//					+ " LIMIT 0, 100"
        			+ " WHERE GM.m_ac_no = " + memberAcNo
        			+ " AND GM.g_ac_no = G.g_ac_no"
					+ " AND G.active_status_id IN " + EnumUtil.getActiveStatusIDs(), getTClass());

        	getLogger().debug("create Query successful");
            return (List<GM>)query.getResultList();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<GM> findByGroup(long groupAcNo) {
        getLogger().debug("getting Group Account By TxType");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
//					+ " LIMIT 0, 100"
        			+ " WHERE g_ac_no = " + groupAcNo, getTClass());
            getLogger().debug("create Query successful");
            return (List<GM>)query.getResultList();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            return null;
        }
    }
}