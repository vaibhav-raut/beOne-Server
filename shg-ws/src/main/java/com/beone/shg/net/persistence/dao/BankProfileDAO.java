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

import com.beone.shg.net.persistence.model.BankProfile;
import com.beone.shg.net.persistence.support.BankInfoCollector;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class BankProfile.
 * @see .BankProfile
 * @author Hibernate Tools
 */
@Repository("bankProfileDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class BankProfileDAO extends GenericDAO<BankProfile> {

    private static final Logger log = LoggerFactory.getLogger(BankProfileDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<BankProfile> getTClass() {
    	return BankProfile.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.bank_profile";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(BankProfile transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(BankProfile transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public BankProfile merge(BankProfile transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<BankProfile> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }


    @SuppressWarnings("unchecked")
	public List<BankProfile> getBankForIFCSCode(String ifcsCode) {
        getLogger().debug("getting Bank For IFCS Code");
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName()
        			+ " WHERE ifcs_code = '" + ifcsCode.toUpperCase() + "'", getTClass());
        	
            getLogger().debug("create Query successful");
               
            return query1.getResultList();
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
	public List<BankProfile> getActiveBankForIFCSCode(String ifcsCode) {
        getLogger().debug("getting Active Bank For IFCS Code");
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " B"
        			+ " JOIN g_profile G"
        			+ " WHERE B.ifcs_code = '" + ifcsCode.toUpperCase() + "'"
        			+ " AND B.g_ac_no = G.g_ac_no"
        			+ " AND G.active_status_id IN " + EnumUtil.getActiveStatusIDs(), getTClass());
        	
            getLogger().debug("create Query successful");
               
            return query1.getResultList();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            throw re;
        }
    }

	public BankProfile getBankForIFCSCode(String ifcsCode, String branch) {
        getLogger().debug("getting Bank For IFCS Code");
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName()
        			+ " WHERE ifcs_code = '" + ifcsCode.toUpperCase() + "'"
        			+ " AND LOWER(branch_name) LIKE '%" + branch.toLowerCase() + "%'", getTClass());
        	
            getLogger().debug("create Query successful");
               
            return (BankProfile) query1.getSingleResult();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            throw re;
        }
    }

	public BankProfile getActiveBankForIFCSCode(String ifcsCode, String branch) {
        getLogger().debug("getting Active Bank For IFCS Code");
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " B"
        			+ " JOIN g_profile G"
        			+ " WHERE B.ifcs_code = '" + ifcsCode.toUpperCase() + "'"
        			+ " AND LOWER(B.branch_name) LIKE '%" + branch.toLowerCase() + "%'"
        			+ " AND B.g_ac_no = G.g_ac_no"
        			+ " AND G.active_status_id IN " + EnumUtil.getActiveStatusIDs(), getTClass());
        	
            getLogger().debug("create Query successful");
               
            return (BankProfile) query1.getSingleResult();
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
	public List<BankProfile> getBankForDistrict(int districtId, String bankName) {
        getLogger().debug("getting Bank For District");
        List<BankProfile> list = new ArrayList<BankProfile>();
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName()
        			+ " WHERE g_ac_no > " + ConversionUtil.getStartRangeGroupAc(districtId) 
        			+ " AND g_ac_no < " + ConversionUtil.getEndRangeGroupAc(districtId)
        			+ " AND LOWER(bank_name) LIKE '%" + bankName.toLowerCase() + "%'", getTClass());

        	getLogger().debug("create Query successful");
            List<BankProfile> out1 = query1.getResultList();
            
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
	public List<BankProfile> getActiveBankForDistrict(int districtId, String bankName) {
        getLogger().debug("getting Bank For District");
        List<BankProfile> list = new ArrayList<BankProfile>();
        try {
        	Query query1 = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " B"
        			+ " JOIN g_profile G"
        			+ " WHERE B.g_ac_no > " + ConversionUtil.getStartRangeGroupAc(districtId) 
        			+ " AND B.g_ac_no < " + ConversionUtil.getEndRangeGroupAc(districtId)
        			+ " AND LOWER(B.bank_name) LIKE '%" + bankName.toLowerCase() + "%'"
        			+ " AND B.g_ac_no = G.g_ac_no"
        			+ " AND G.active_status_id IN " + EnumUtil.getActiveStatusIDs(), getTClass());

        	getLogger().debug("create Query successful");
            List<BankProfile> out1 = query1.getResultList();
            
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

	public void loadBankProfile(BankInfoCollector collector) {
		if(collector != null && collector.getDistrictId() > DataUtil.ZERO_LONG) {
			List<BankProfile> profiles = getBankForDistrict(collector.getDistrictId(), collector.getBankName());

			for(BankProfile profile : profiles) {
				collector.putBankProfile(profile.getGProfile().getGAcNo(), profile);
			}
		}
	}
}