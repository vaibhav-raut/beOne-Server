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

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class Tx.
 * @see .Tx
 * @author Hibernate Tools
 */
@Repository("txDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class TxDAO extends GenericDAO<Tx> {

    private static final Logger log = LoggerFactory.getLogger(TxDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<Tx> getTClass() {
    	return Tx.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.tx";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(Tx transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(Tx transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public Tx merge(Tx transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<Tx> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<Tx> getAllTxsForGroup(long groupAcNo, long startTime, long endTime) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND entry_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND entry_ts <= from_unixtime(" + endTime/1000 + ")"), getTClass());
        	
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
	public List<Tx> getReadyTxsForGroup(long groupAcNo) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND tx_status_id = " + EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Draft)), getTClass());
        	
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
	public List<Tx> getCashTxsForGroup(long groupAcNo, long startTime, long endTime) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND entry_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND entry_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND ( payment_mode_id = " + EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_CASH))
        			+ " OR tx_type_id IN " + EnumUtil.getTransferTxTypeIds() + " )", getTClass());
        	
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
	public List<Tx> getAllBankTxsForGroup(long groupAcNo, long startTime, long endTime) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND entry_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND entry_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND payment_mode_id IN " + EnumUtil.getBankPaymentModeIDs()), getTClass());
        	
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
	public List<Tx> getBankTxsForGroup(long groupAcNo, long startTime, long endTime, long bankAcNo) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND entry_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND entry_ts <= from_unixtime(" + endTime/1000 + ")"
                	+ " AND payment_mode_id IN " + EnumUtil.getBankPaymentModeIDs()
        			+ " AND g_bank_account = " + bankAcNo), getTClass());
        	
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
	public List<Tx> getAllTxsForGroup(long groupAcNo, long startTime, long endTime, int txTypeId) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND entry_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND entry_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND tx_type_id = " + txTypeId), getTClass());
        	
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
	public List<Tx> getAllTxsForGroup(long groupAcNo, 
			long startTime, 
			long endTime, 
			int txTypeId, 
			int txStatusId, 
			int paymentModeId,
			long memberAcNo, 
			long doneByMember, 
			long approvedByMember,
			int rangeStart,
			int limit) {
    	
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	
        	String whereClause = "";
        	if(txTypeId > DataUtil.ZERO_INTEGER) {
        		whereClause += " AND tx_type_id = " + txTypeId;
        	}
        	if(txStatusId > DataUtil.ZERO_INTEGER) {
        		whereClause += " AND tx_status_id = " + txStatusId;
        	}
        	if(paymentModeId > DataUtil.ZERO_INTEGER) {
        		whereClause += " AND payment_mode_id = " + paymentModeId;
        	}
        	if(memberAcNo > DataUtil.ZERO_LONG) {
        		whereClause += " AND m_ac_no = " + memberAcNo;
        	}
        	if(doneByMember > DataUtil.ZERO_LONG) {
        		whereClause += " AND done_by_m = " + doneByMember;
        	}
        	if(approvedByMember > DataUtil.ZERO_LONG) {
        		whereClause += " AND approved_by_m = " + approvedByMember;
        	}
        	if(limit == DataUtil.ZERO_LONG) {
        		limit = DataUtil.DEFAULT_DB_LIMIT;
        	}
        	
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND entry_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND entry_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ whereClause
        			+ " LIMIT " + rangeStart + ", " + (rangeStart + limit)), getTClass());
        	
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
	public List<Tx> getAllTxsForMember(long memberAcNo, long startTime, long endTime) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo
        			+ " AND entry_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND entry_ts <= from_unixtime(" + endTime/1000 + ")"), getTClass());
        	
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
	public List<Tx> getAllTxsForMember(long memberAcNo, long startTime, long endTime, int txTypeId) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo
        			+ " AND entry_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND entry_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND tx_type_id = " + txTypeId), getTClass());
        	
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