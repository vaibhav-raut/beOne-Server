package com.beone.udaan.mr.persistence.dao;
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

import com.beone.shg.net.persistence.dao.GenericDAO;
import com.beone.udaan.mr.persistence.model.StockTx;
import com.beone.udaan.mr.persistence.support.EnumUtilMr;

/**
 * Home object for domain model class StockTx.
 * @see StockTx
 */
@Repository("stockTxDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class StockTxDAO extends GenericDAO<StockTx> {

    private static final Logger log = LoggerFactory.getLogger(StockTxDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<StockTx> getTClass() {
    	return StockTx.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.stock_tx";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(StockTx transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(StockTx transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public StockTx merge(StockTx transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<StockTx> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
	@SuppressWarnings("unchecked")
	public List<StockTx> getAllTxForVisit(long visitId) {
		getLogger().debug("getting StockTx List For Visit");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
					+ " WHERE mr_visit_id = " + visitId, getTClass());

			return (List<StockTx>) query.getResultList();       
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
	public List<StockTx> getStockTxForVisit(long visitId) {
		getLogger().debug("getting Stock Type StockTx List For Visit");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE mr_visit_id = " + visitId
					+ " AND stock_tx_type_id IN " + EnumUtilMr.getStockTxTypeIds()), getTClass());

			return (List<StockTx>) query.getResultList();       
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
	public List<StockTx> getPayTxForVisit(long visitId) {
		getLogger().debug("getting Pay Type StockTx List For Visit");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE mr_visit_id = " + visitId
					+ " AND stock_tx_type_id IN " + EnumUtilMr.getPayTxTypeIds()), getTClass());

			return (List<StockTx>) query.getResultList();       
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
	public List<StockTx> getTxsForOwnerInRange(long ownerAcNo, long startTime, long endTime) {
        getLogger().debug("getting All Tx for Owner of given Range");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName()  
        			+ " WHERE tx_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND tx_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND owner_ac_no = " + ownerAcNo), getTClass());
        	
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
	public List<StockTx> getStockTxsForOwnerInRange(long ownerAcNo, long startTime, long endTime) {
        getLogger().debug("getting All StockTx for Owner of given Range");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName()  
        			+ " WHERE tx_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND tx_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND owner_ac_no = " + ownerAcNo
        			+ " AND stock_tx_type_id IN " + EnumUtilMr.getStockTxTypeIds()), getTClass());
        	
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
	public List<StockTx> getPayTxsForOwnerInRange(long ownerAcNo, long startTime, long endTime) {
        getLogger().debug("getting All StockTx for Owner of given Range");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName()  
        			+ " WHERE tx_ts >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND tx_ts <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND owner_ac_no = " + ownerAcNo
        			+ " AND stock_tx_type_id IN " + EnumUtilMr.getPayTxTypeIds()), getTClass());
        	
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