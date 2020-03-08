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
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.StockItem;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;
import com.beone.udaan.mr.persistence.support.EnumUtilMr;

/**
 * Home object for domain model class StockItem.
 * @see StockItem
 */
@Repository("stockItemDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class StockItemDAO extends GenericDAO<StockItem> {

    private static final Logger log = LoggerFactory.getLogger(StockItemDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<StockItem> getTClass() {
    	return StockItem.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.stock_item";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(StockItem transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(StockItem transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public StockItem merge(StockItem transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<StockItem> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
        
	@SuppressWarnings("unchecked")
	public List<StockItem> getStockedItemsForOwner(long ownerAcNo) {
		getLogger().debug("getting StockItem List For Micro Retailers");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE owner_ac_no = " + ownerAcNo
					+ " AND item_status_id IN " + EnumUtilMr.getStockedMrStock()), getTClass());

			return (List<StockItem>) query.getResultList();       
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
	public List<StockItem> getStockedItemsForOwnerForType(long ownerAcNo, long stockTypeId) {
		getLogger().debug("getting StockItem List For Micro Retailers");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE owner_ac_no = " + ownerAcNo
					+ " AND stock_type_id = " + stockTypeId
					+ " AND item_status_id IN " + EnumUtilMr.getStockedMrStock()), getTClass());

			return (List<StockItem>) query.getResultList();       
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
	public List<StockItem> getActiveItemForOwnerForType(long ownerAcNo, long stockTypeId) {
		getLogger().debug("getting Stocked Item List For Stock Type");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE owner_ac_no = " + ownerAcNo
					+ " AND stock_type_id = " + stockTypeId
					+ " AND item_status_id IN " + EnumUtilMr.getActiveMrStock()), getTClass());
	
			return (List<StockItem>) query.getResultList();       
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
	public List<StockItem> getSoldItemsForOwner(long ownerAcNo) {
		getLogger().debug("getting Stocked Item List For Owner");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE owner_ac_no = " + ownerAcNo
					+ " AND item_status_id = " + EnumCacheMr.getIndexOfStatusValue(EnumConstMr.ItemStatus, EnumConstMr.ItemStatus_Sold)), getTClass());

			return (List<StockItem>) query.getResultList();       
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
	public List<StockItem> getStockedItemForType(long stockTypeId) {
		getLogger().debug("getting Stocked Item List For Stock Type");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE stock_type_id = " + stockTypeId
					+ " AND item_status_id IN " + EnumUtilMr.getStockedMrStock()), getTClass());
	
			return (List<StockItem>) query.getResultList();       
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
	public List<StockItem> getStockedItemForLot(long lotId) {
		getLogger().debug("getting Stocked Item List For Stock Type");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE lot_id = " + lotId
					+ " AND item_status_id IN " + EnumUtilMr.getStockedMrStock()), getTClass());
	
			return (List<StockItem>) query.getResultList();       
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
	public List<StockItem> getActiveItemForType(long stockTypeId) {
		getLogger().debug("getting Stocked Item List For Stock Type");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE stock_type_id = " + stockTypeId
					+ " AND item_status_id IN " + EnumUtilMr.getActiveMrStock()), getTClass());
	
			return (List<StockItem>) query.getResultList();       
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
	public List<StockItem> getActiveItemForLot(long lotId) {
		getLogger().debug("getting Stocked Item List For Stock Type");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE lot_id = " + lotId
					+ " AND item_status_id IN " + EnumUtilMr.getActiveMrStock()), getTClass());
	
			return (List<StockItem>) query.getResultList();       
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