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

import com.beone.shg.net.persistence.model.GG;

/**
 * Home object for domain model class ParentChildGroup.
 * @see .ParentChildGroup
 * @author Hibernate Tools
 */
@Repository("GGDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GGDAO extends GenericDAO<GG> {

    private static final Logger log = LoggerFactory.getLogger(GGDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GG> getTClass() {
    	return GG.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.g_g";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GG transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GG transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GG merge(GG transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GG> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
    public GG findById(long topGroupAcNo, long downGroupAcNo) {
        getLogger().debug("getting Top Group to Down Group Mapping");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE top_g_ac_no = " + topGroupAcNo
        			+ " AND down_g_ac_no = " + downGroupAcNo, getTClass());
            getLogger().debug("create Query successful");
            return (GG)query.getSingleResult();
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
	public List<GG> findByMember(long topGroupAcNo) {
        getLogger().debug("getting All Down Groups Mapped to Top Group");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " AND top_g_ac_no = " + topGroupAcNo, getTClass());
            getLogger().debug("create Query successful");
            return (List<GG>)query.getResultList();
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
	public List<GG> findByGroup(long downGroupAcNo) {
        getLogger().debug("getting All Top Groups Mapped to Down Group");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE down_g_ac_no = " + downGroupAcNo, getTClass());
            getLogger().debug("create Query successful");
            return (List<GG>)query.getResultList();
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