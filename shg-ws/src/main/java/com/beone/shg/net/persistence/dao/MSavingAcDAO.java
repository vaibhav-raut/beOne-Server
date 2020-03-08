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

import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class MSavingAc.
 * @see .MSavingAc
 * @author Hibernate Tools
 */
@Repository("mSavingAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MSavingAcDAO extends GenericDAO<MSavingAc> {

    private static final Logger log = LoggerFactory.getLogger(MSavingAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MSavingAc> getTClass() {
    	return MSavingAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.m_saving_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MSavingAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MSavingAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MSavingAc merge(MSavingAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MSavingAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<MSavingAc> getAllAcForMember(long memberAcNo) {
        getLogger().debug("getting All Saving Accounts for the Member");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo, getTClass());
        	
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
	public List<MSavingAc> getAllActiveAcForMember(long memberAcNo) {
        getLogger().debug("getting All Saving Accounts for the Member");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo
        			+ " AND account_status_id IN " + EnumUtil.getActiveAccountStatus(), getTClass());
        	
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
	public List<MSavingAc> getAllAcForGroup(long groupAcNo) {
        getLogger().debug("getting All Saving Accounts for the Group");
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

    @SuppressWarnings("unchecked")
	public List<MSavingAc> getAllActiveAcForGroup(long groupAcNo) {
        getLogger().debug("getting All Saving Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
        			+ " AND account_status_id IN " + EnumUtil.getActiveAccountStatus(), getTClass());
        	
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