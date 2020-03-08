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

import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class GLoanAc.
 * @see .GLoanAc
 * @author Hibernate Tools
 */
@Repository("gLoanAcDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GLoanAcDAO extends GenericDAO<GLoanAc> {

    private static final Logger log = LoggerFactory.getLogger(GLoanAcDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GLoanAc> getTClass() {
    	return GLoanAc.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.g_loan_ac";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GLoanAc transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GLoanAc transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GLoanAc merge(GLoanAc transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GLoanAc> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<GLoanAc> getAllAcForGroup(long groupAcNo) {
        getLogger().debug("getting All Loan Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo, getTClass());
        	
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
	public List<GLoanAc> getAllActiveAcForGroup(long groupAcNo) {
        getLogger().debug("getting All Active Loan Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND account_status_id IN " + EnumUtil.getActiveAccountStatus(), getTClass());
        	
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
	public List<GLoanAc> getLoanApplications(long groupAcNo) {
        getLogger().debug("getting All Loan Accounts for the Group");
        List<GLoanAc> list = new ArrayList<GLoanAc>();
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND account_status_id IN " + EnumUtil.getStatusToApprove(), getTClass());
        	
//        	query.setParameter(TABLE_NAME, getTableName());
//        	query.setParameter(ROW_LIMIT, limit);
        	
            getLogger().debug("create Query successful");
            List<GLoanAc> out1 = query1.getResultList();
            
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
            List<GLoanAc> out2 = query2.getResultList();
            
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