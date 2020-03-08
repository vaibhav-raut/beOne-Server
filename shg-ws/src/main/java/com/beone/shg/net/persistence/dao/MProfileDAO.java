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
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class MProfile.
 * @see .MProfile
 * @author Hibernate Tools
 */
@Repository("mProfileDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MProfileDAO extends GenericDAO<MProfile> {

    private static final Logger log = LoggerFactory.getLogger(MProfileDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MProfile> getTClass() {
    	return MProfile.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.m_profile";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MProfile transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MProfile transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MProfile merge(MProfile transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MProfile> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<MProfile> getAllGroupMembers(long groupAcNo) {
        getLogger().debug("getting Member Profile for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
        			+ " AND approval_status_id != " + EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Rejected), getTClass());
        	
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
	public List<MProfile> getMemberApplications(long groupAcNo) {
        getLogger().debug("getting Member Application for the Group");
        List<MProfile> list = new ArrayList<MProfile>();
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
        			+ " AND approval_status_id IN " + EnumUtil.getStatusToApprove()
        			+ " AND active_status_id = " + EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Requested), getTClass());
        	
//        	query.setParameter(TABLE_NAME, getTableName());
//        	query.setParameter(ROW_LIMIT, limit);
        	
            getLogger().debug("create Query successful");
            List<MProfile> out1 = query1.getResultList();
            
            if(out1 != null && !out1.isEmpty()) {
            	list.addAll(out1);
            }
            
        	Query query2 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
        			+ " AND date_of_enroll >= '" + DateUtil.getCurrentDBDateStr(30) + "'"
        			+ " AND approval_status_id IN " + EnumUtil.getStatusToDisplay(), getTClass());
        	
//        	query.setParameter(TABLE_NAME, getTableName());
//        	query.setParameter(ROW_LIMIT, limit);
        	
            getLogger().debug("create Query successful");
            List<MProfile> out2 = query2.getResultList();
            
            if(out2 != null && !out2.isEmpty()) {
            	list.addAll(out2);
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
	public List<MProfile> getMembersProfile(Object[] memberAcNos) {
        getLogger().debug("getting Members Contact List");
        
        try {
     		Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no IN " + ConversionUtil.convertArrayToInString(memberAcNos)), getTClass());
        	
    		return (List<MProfile>) query.getResultList();       
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
	public List<MProfile> getMembersProfileByGroup(long groupAcNo) {
        getLogger().debug("getting Members Contact List");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo))
        			+ " AND approval_status_id != " + EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Rejected), getTClass());

        	return (List<MProfile>) query.getResultList();       
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
	public List<MProfile> getMembersProfileByRoleByGroup(long groupAcNo, int roleId) {
        getLogger().debug("getting Members Contact List");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo))
        			+ " AND active_status_id IN " + EnumUtil.getActiveStatusIDs()
        			+ " AND m_role_id = " + roleId, getTClass());

        	return (List<MProfile>) query.getResultList();       
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            throw re;
        }
    }

	public void loadMembersProfile(GroupInfoCollector collector) {
		if(collector != null && collector.getMemberAcNos() != null && !collector.getMemberAcNos().isEmpty()) {
			List<MProfile> profiles = getMembersProfile(collector.getMemberAcNos().toArray());

			for(MProfile profile : profiles) {
				collector.putMamberProfile(profile.getMemberAcNo(), profile);
			}
		}
	}

	public void loadMembersProfileByGroup(GroupInfoCollector collector) {
		if(collector != null && collector.getGroupAcNo() > DataUtil.ZERO_LONG) {
			List<MProfile> profiles = getMembersProfileByGroup(collector.getGroupAcNo());

			for(MProfile profile : profiles) {
				collector.putMamberProfile(profile.getMemberAcNo(), profile);
			}
		}
	}
}