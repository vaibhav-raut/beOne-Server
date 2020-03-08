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

import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;

/**
 * Home object for domain model class GBankAccount.
 * @see .GBankAccount
 * @author Hibernate Tools
 */
@Repository("gBankAccountDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GBankAccountDAO extends GenericDAO<GBankAccount> {

    private static final Logger log = LoggerFactory.getLogger(GBankAccountDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<GBankAccount> getTClass() {
    	return GBankAccount.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.g_bank_account";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(GBankAccount transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(GBankAccount transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public GBankAccount merge(GBankAccount transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<GBankAccount> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

	@SuppressWarnings("unchecked")
	public List<GBankAccount> getGroupBankAccounts(long groupAcNo) {
        getLogger().debug("getting Group BankAccount List");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo), getTClass());

        	return (List<GBankAccount>) query.getResultList();       
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
	public List<BankAccountShort> getGroupBankAccountsDisplay(long groupAcNo) {
        getLogger().debug("getting Group BankAccount List");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo), getTClass());

        	List<GBankAccount> gBankAccounts = query.getResultList();   
        	if(gBankAccounts != null) {
        		List<BankAccountShort> bankAccountShorts = new ArrayList<BankAccountShort>();
        		for(GBankAccount gBankAccount: gBankAccounts) {
        			bankAccountShorts.add(ConversionUtil.convertToDisplay(gBankAccount));
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

	public BankAccountShort getGroupBankAcDisplayForAcNo(long groupBankAcNo) {
        getLogger().debug("getting Group BankAccount List");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE bank_account_no = " + groupBankAcNo), getTClass());

        	GBankAccount gBankAccount = (GBankAccount)query.getSingleResult();   
        	if(gBankAccount != null) {
        		return ConversionUtil.convertToDisplay(gBankAccount);
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

	public void loadGroupBankAccountNo(String lang, GroupInfoCollector collector) {
		if(collector != null && collector.getGroupAcNo() > DataUtil.ZERO_LONG) {
			List<GBankAccount> accounts = getGroupBankAccounts(collector.getGroupAcNo());

			for(GBankAccount account : accounts) {
				collector.putBankAcNumber(account.getBankAccount().getAccountNumber(), account.getBankAccountNo());
			}
		}
	}
}