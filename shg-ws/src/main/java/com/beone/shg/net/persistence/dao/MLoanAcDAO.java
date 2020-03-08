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

import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class MLoanAc.
 * @see .MLoanAc
 * @author Hibernate Tools
 */
@Repository("mLoanAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MLoanAcDAO extends GenericDAO<MLoanAc> {

    private static final Logger log = LoggerFactory.getLogger(MLoanAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MLoanAc> getTClass() {
    	return MLoanAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.m_loan_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MLoanAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MLoanAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MLoanAc merge(MLoanAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MLoanAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<MLoanAc> getAllAcForMember(long memberAcNo) {
        getLogger().debug("getting All Loan Accounts for the Member");
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
	public List<MLoanAc> getAllActiveAcForMember(long memberAcNo) {
        getLogger().debug("getting All Loan Accounts for the Member");
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
	public List<MLoanAc> getAllAcForGroup(long groupAcNo) {
        getLogger().debug("getting All Loan Accounts for the Group");
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
	public List<MLoanAc> getAllAtiveAcForGroup(long groupAcNo) {
        getLogger().debug("getting All Loan Accounts for the Group");
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

    @SuppressWarnings("unchecked")
	public List<MLoanAc> getLoanApplications(long groupAcNo) {
        getLogger().debug("getting All Loan Accounts for the Group");
        List<MLoanAc> list = new ArrayList<MLoanAc>();
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
        			+ " AND account_status_id IN " + EnumUtil.getAccountStatusToActive(), getTClass());
        	
//        	query.setParameter(TABLE_NAME, getTableName());
//        	query.setParameter(ROW_LIMIT, limit);
        	
            getLogger().debug("create Query successful");
            List<MLoanAc> out1 = query1.getResultList();
            
            if(out1 != null && !out1.isEmpty()) {
            	list.addAll(out1);
            }
            
        	Query query2 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
        			+ " AND approved_date >= '" + DateUtil.getCurrentDBDateStr(30) + "'"
        			+ " AND account_status_id IN " + EnumUtil.getAccountToStatusDisplay(), getTClass());
        	
//        	query.setParameter(TABLE_NAME, getTableName());
//        	query.setParameter(ROW_LIMIT, limit);
        	
            getLogger().debug("create Query successful");
            List<MLoanAc> out2 = query2.getResultList();
            
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
}