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

import com.beone.shg.net.persistence.model.MMessage;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class MMessage.
 * @see .MMessage
 * @author Vaibhav
 */
@Repository("mMessageDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MMessageDAO extends GenericDAO<MMessage> {

    private static final Logger log = LoggerFactory.getLogger(MMessageDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MMessage> getTClass() {
    	return MMessage.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.m_message";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MMessage transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MMessage transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MMessage merge(MMessage transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MMessage> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

	@SuppressWarnings("unchecked")
	public List<MMessage> getActiveMMessageListByGroup(long groupAcNo) {
        getLogger().debug("getting Members Message List");
        
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " M" 
        			+ " JOIN m_profile P"
//					+ " LIMIT 0, 100"
        			+ " WHERE M.m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND M.m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
        			+ " AND M.m_ac_no = M.m_ac_no"
					+ " AND P.active_status_id IN " + EnumUtil.getActiveStatusIDs(), getTClass());
        	
        	return (List<MMessage>) query.getResultList();       
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