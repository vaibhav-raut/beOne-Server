package com.beone.shg.net.persistence.dao;
// default package
// Generated Mar 22, 2014 6:10:20 PM by Hibernate Tools 3.1.0.beta4

import java.util.ArrayList;
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

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class GProfile.
 * @see .GProfile
 * @author Hibernate Tools
 */
@Repository("gProfileDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GProfileDAO extends GenericDAO<GProfile> {

    private static final Logger log = LoggerFactory.getLogger(GProfileDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GProfile> getTClass() {
    	return GProfile.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.g_profile";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GProfile transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GProfile transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GProfile merge(GProfile transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GProfile> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<GProfile> getGroupApplications(int districtId) {
        getLogger().debug("getting Group Application for the Group");
        List<GProfile> list = new ArrayList<GProfile>();
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no > " + ConversionUtil.getStartRangeGroupAc(districtId) 
        			+ " AND g_ac_no < " + ConversionUtil.getEndRangeGroupAc(districtId)
        			+ " AND approval_status_id IN " + EnumUtil.getStatusToApprove()
        			+ " AND active_status_id = " + EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Requested), getTClass());
        	
//        	query.setParameter(TABLE_NAME, getTableName());
//        	query.setParameter(ROW_LIMIT, limit);
        	
            getLogger().debug("create Query successful");
            List<GProfile> out1 = query1.getResultList();
            
            if(out1 != null && !out1.isEmpty()) {
            	list.addAll(out1);
            }
               
            return list;
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
	public List<GProfile> getGroupByRange(int districtId, long groupIndex, int range) {
        getLogger().debug("getting Group Application for the Group");
        List<GProfile> list = new ArrayList<GProfile>();
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no > " + ConversionUtil.getStartRangeGroupAc(districtId, groupIndex) 
        			+ " AND g_ac_no < " + ConversionUtil.getEndRangeGroupAc(districtId, groupIndex, range)
        			+ " AND active_status_id IN " + EnumUtil.getActiveStatusIDs(), getTClass());
        	
            getLogger().debug("create Query successful");
            List<GProfile> out1 = query1.getResultList();
            
            if(out1 != null && !out1.isEmpty()) {
            	list.addAll(out1);
            }
               
            return list;
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
	public List<GProfile> getGroupApplicationsForMember(long memberAcNo) {
        getLogger().debug("getting Group Application for the Group");
        List<GProfile> list = new ArrayList<GProfile>();
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " G"
        			+ " JOIN g_m GM"
        			+ " WHERE GM.m_ac_no = " + memberAcNo 
        			+ " AND GM.g_ac_no = G.g_ac_no" 
        			+ " AND G.approval_status_id IN " + EnumUtil.getStatusToApprove()
        			+ " AND G.active_status_id = " + EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Requested), getTClass());
        	
//        	query.setParameter(TABLE_NAME, getTableName());
//        	query.setParameter(ROW_LIMIT, limit);
        	
            getLogger().debug("create Query successful");
            List<GProfile> out1 = query1.getResultList();
            
            if(out1 != null && !out1.isEmpty()) {
            	list.addAll(out1);
            }
               
            return list;
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