package com.beone.udaan.mr.persistence.dao;
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

import com.beone.shg.net.persistence.dao.GenericDAO;
import com.beone.udaan.mr.persistence.model.MrVisit;
import com.beone.udaan.mr.persistence.support.EnumUtilMr;

/**
 * Home object for domain model class MrVisit.
 * @see MrVisit
 */
@Repository("mrVisitDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MrVisitDAO extends GenericDAO<MrVisit> {

    private static final Logger log = LoggerFactory.getLogger(MrVisitDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MrVisit> getTClass() {
    	return MrVisit.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.mr_visit";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MrVisit transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MrVisit transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MrVisit merge(MrVisit transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MrVisit> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<MrVisit> getMrVisitForMR(long ownerAcNo, long fromTime) {
        getLogger().debug("getting MR Visits for the MR");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE owner_ac_no = " + ownerAcNo
        			+ " AND end_ts <= from_unixtime(" + fromTime/1000 + ")"), getTClass());
        	
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

    @SuppressWarnings("unchecked")
    public List<MrVisit> getActiveVisitsForOwner(long ownerAcNo) {
    	getLogger().debug("getting MrVisit List For Micro Retailers");

    	try {
    		Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
    				+ " WHERE owner_ac_no = " + ownerAcNo
    				+ " AND visit_status_id IN " + EnumUtilMr.getActiveVisit()), getTClass());

    		return (List<MrVisit>) query.getResultList();       
    	}
    	catch (NoResultException re) {
    		return null;
    	}
    	catch (RuntimeException re) {
    		getLogger().error("create Query failed", re);
    		throw re;
    	}
    }

    @SuppressWarnings("unchecked")
	public List<MrVisit> getMrVisitsForOwner(long ownerAcNo, long startTime, long endTime) {
        getLogger().debug("getting MR Visit for Owner");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE owner_ac_no = " + ownerAcNo
        			+ " AND start_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND start_ts <= from_unixtime(" + endTime/1000 + ")"), getTClass());
        	
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

    @SuppressWarnings("unchecked")
	public List<MrVisit> getMrVisitsForAuth(long authAcNo, long startTime, long endTime) {
        getLogger().debug("getting MR Visit for Auth");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE auth_ac_no = " + authAcNo
        			+ " AND start_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND start_ts <= from_unixtime(" + endTime/1000 + ")"), getTClass());
        	
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

    @SuppressWarnings("unchecked")
	public List<MrVisit> getAllMrVisits(long startTime, long endTime) {
        getLogger().debug("getting All MR Visit");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE start_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND start_ts <= from_unixtime(" + endTime/1000 + ")"), getTClass());
        	
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

    @SuppressWarnings("unchecked")
	public List<MrVisit> getMrVisitsForOwnerForRole(int roleId, long startTime, long endTime) {
        getLogger().debug("getting All MR Visit for Owner of given Role Id");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() + " V"  
        			+ " JOIN m_profile P"
        			+ " WHERE V.start_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND V.start_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND V.owner_ac_no = P.m_ac_no"
        			+ " AND V.m_role_id = " + roleId), getTClass());
        	
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

    @SuppressWarnings("unchecked")
	public List<MrVisit> getMrVisitsForAuthForRole(int roleId, long startTime, long endTime) {
        getLogger().debug("getting All MR Visit for Auth of given Role Id");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() + " V"  
        			+ " JOIN m_profile P"
        			+ " WHERE V.start_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND V.start_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND V.auth_ac_no = P.m_ac_no"
        			+ " AND V.m_role_id = " + roleId), getTClass());
        	
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