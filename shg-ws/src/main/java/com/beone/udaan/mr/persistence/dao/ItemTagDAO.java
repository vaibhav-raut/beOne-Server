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
import com.beone.udaan.mr.persistence.model.ItemTag;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

/**
 * Home object for domain model class ItemTag.
 * @see ItemTag
 */
@Repository("itemTagDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ItemTagDAO extends GenericDAO<ItemTag> {

    private static final Logger log = LoggerFactory.getLogger(ItemTagDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<ItemTag> getTClass() {
    	return ItemTag.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.item_tag";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(ItemTag transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(ItemTag transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public ItemTag merge(ItemTag transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<ItemTag> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
	@SuppressWarnings("unchecked")
	public List<ItemTag> getActiveItemTag() {
		getLogger().debug("getting Active ItemTag List");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
					+ " WHERE tag_status_id = " + EnumCacheMr.getIndexOfStatusValue(EnumConstMr.TagStatus, EnumConstMr.TagStatus_Created), getTClass());

			return (List<ItemTag>) query.getResultList();       
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
	public List<ItemTag> getActiveItemTagForStockType(long stockTypeId) {
		getLogger().debug("getting Active ItemTag List for the StockType");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
					+ " WHERE tag_status_id = " + EnumCacheMr.getIndexOfStatusValue(EnumConstMr.TagStatus, EnumConstMr.TagStatus_Created)
					+ " AND stock_type_id = " + stockTypeId, getTClass());

			return (List<ItemTag>) query.getResultList();       
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
	public List<ItemTag> getActiveItemTagForBrand(String brandName) {
		getLogger().debug("getting Active ItemTag List for the Brand");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
					+ " WHERE tag_status_id = " + EnumCacheMr.getIndexOfStatusValue(EnumConstMr.TagStatus, EnumConstMr.TagStatus_Created)
					+ " AND brand_name = '" + brandName + "'", getTClass());

			return (List<ItemTag>) query.getResultList();       
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
	public List<ItemTag> getActiveItemTagForStockItem(long stockItemId) {
		getLogger().debug("getting Active ItemTag List for the StockItem");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
					+ " WHERE tag_status_id = " + EnumCacheMr.getIndexOfStatusValue(EnumConstMr.TagStatus, EnumConstMr.TagStatus_Created)
					+ " AND stock_item_id = " + stockItemId, getTClass());

			return (List<ItemTag>) query.getResultList();       
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