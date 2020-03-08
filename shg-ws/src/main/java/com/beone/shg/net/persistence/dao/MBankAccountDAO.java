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

import com.beone.shg.net.persistence.model.MBankAccount;
import com.beone.shg.net.persistence.support.BankAccountInfoCollector;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;

/**
 * Home object for domain model class MBankAccount.
 * @see .MBankAccount
 * @author Hibernate Tools
 */
@Repository("mBankAccountDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MBankAccountDAO extends GenericDAO<MBankAccount> {

    private static final Logger log = LoggerFactory.getLogger(MBankAccountDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MBankAccount> getTClass() {
    	return MBankAccount.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.m_bank_account";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MBankAccount transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MBankAccount transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MBankAccount merge(MBankAccount transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MBankAccount> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    public BankAccountShort findByIdForDisplay(long id) {
    	MBankAccount mBankAccount = super.findById(id);
    	return ConversionUtil.convertToDisplay(mBankAccount);
    }
    
    public MBankAccount findBankAccountOfMember(long memberAcNo) {
        getLogger().debug("getting Member BankAccount");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo), getTClass());
        	
            getLogger().debug("create Query successful");
            
            return (MBankAccount) query.getSingleResult();
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
	public List<BankAccountShort> getMemberBankAccountsDisplay(long memberAcNo) {
        getLogger().debug("getting Members BankAccount List");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo), getTClass());

        	List<MBankAccount> mBankAccounts = query.getResultList();   
        	if(mBankAccounts != null) {
        		List<BankAccountShort> bankAccountShorts = new ArrayList<BankAccountShort>();
        		for(MBankAccount mBankAccount: mBankAccounts) {
        			bankAccountShorts.add(ConversionUtil.convertToDisplay(mBankAccount));
        		}
        		return bankAccountShorts;
        	}       	
        	return null;
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
	public List<MBankAccount> getMembersBankAccountList(Object[] memberAcNos) {
        getLogger().debug("getting Members BankAccount List");
        
        try {
     		Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no IN " + ConversionUtil.convertArrayToInString(memberAcNos)), getTClass());
        	
    		return (List<MBankAccount>) query.getResultList();       
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
	public List<MBankAccount> getActiveMembersBankAccountListByGroup(long groupAcNo) {
        getLogger().debug("getting Members BankAccount List");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() + " B" 
        			+ " JOIN m_profile M"
        			+ " WHERE B.m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND B.m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo))
        			+ " AND B.m_ac_no = M.m_ac_no"
					+ " AND M.active_status_id IN " + EnumUtil.getActiveStatusIDs(), getTClass());

        	return (List<MBankAccount>) query.getResultList();       
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
	public List<MBankAccount> getMembersBankAccountListByGroup(long groupAcNo) {
        getLogger().debug("getting Members BankAccount List");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)), getTClass());

        	return (List<MBankAccount>) query.getResultList();       
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            throw re;
        }
    }

	public void loadMBankAccount(BankAccountInfoCollector collector) {

		List<MBankAccount> bankAccounts = getMembersBankAccountList(collector.getMemberAcNos().toArray());

		for(MBankAccount bankAccount : bankAccounts) {
			collector.putMemberBankAccounts(bankAccount.getMProfile().getMemberAcNo(), bankAccount);
		}
	}

	public void loadActiveMBankAccountForGroup(BankAccountInfoCollector collector) {

		List<MBankAccount> bankAccounts = getActiveMembersBankAccountListByGroup(collector.getGroupAcNo());

		for(MBankAccount bankAccount : bankAccounts) {
			collector.putMemberBankAccounts(bankAccount.getMProfile().getMemberAcNo(), bankAccount);
		}
	}

	public void loadMBankAccountForGroup(BankAccountInfoCollector collector) {

		List<MBankAccount> bankAccounts = getMembersBankAccountListByGroup(collector.getGroupAcNo());

		for(MBankAccount bankAccount : bankAccounts) {
			collector.putMemberBankAccounts(bankAccount.getMProfile().getMemberAcNo(), bankAccount);
		}
	}
	
}