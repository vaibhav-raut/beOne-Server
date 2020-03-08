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

import com.beone.shg.net.persistence.model.GInvtAc;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class GInvtAc.
 * @see .GInvtAc
 * @author Hibernate Tools
 */
@Repository("gInvtAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GInvtAcDAO extends GenericDAO<GInvtAc> {

    private static final Logger log = LoggerFactory.getLogger(GInvtAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GInvtAc> getTClass() {
    	return GInvtAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.g_invt_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GInvtAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GInvtAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GInvtAc merge(GInvtAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GInvtAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<GInvtAc> getAllAcForGroup(long groupAcNo) {
        getLogger().debug("getting Group Invt Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo), getTClass());
        	
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

    @SuppressWarnings("unchecked")
	public List<GInvtAc> getAllActiveAcForGroup(long groupAcNo) {
        getLogger().debug("getting Active Group Invt Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND account_status_id IN " + EnumUtil.getActiveAccountStatus()), getTClass());
        	
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