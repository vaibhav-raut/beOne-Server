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
import com.beone.udaan.mr.persistence.model.StockType;

/**
 * Home object for domain model class StockType.
 * @see StockType
 */
@Repository("stockTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class StockTypeDAO extends GenericDAO<StockType> {

    private static final Logger log = LoggerFactory.getLogger(StockTypeDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<StockType> getTClass() {
    	return StockType.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.stock_type";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(StockType transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(StockType transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public StockType merge(StockType transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<StockType> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }    
    
	@SuppressWarnings("unchecked")
	public List<StockType> getStockForBrandByName(long brandId, String stockNameShort) {
		getLogger().debug("getting StockType List For Brand");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
					+ " WHERE brand_id = " + brandId
					+ " AND (LOWER(name) LIKE '%" + stockNameShort.toLowerCase() + "%'"
					+ " OR LOWER(name_display) LIKE '%" + stockNameShort.toLowerCase() + "%')", getTClass());

			return (List<StockType>) query.getResultList();       
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
	public List<StockType> getStockByName(String stockNameShort) {
		getLogger().debug("getting StockType List For Brand");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
					+ " WHERE LOWER(name) LIKE '%" + stockNameShort.toLowerCase() + "%'"
					+ " OR LOWER(name_display) LIKE '%" + stockNameShort.toLowerCase() + "%'", getTClass());

			return (List<StockType>) query.getResultList();       
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