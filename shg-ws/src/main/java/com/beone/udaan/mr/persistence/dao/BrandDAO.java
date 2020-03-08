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
import com.beone.udaan.mr.persistence.model.Brand;

/**
 * Home object for domain model class Brand.
 * @see Brand
 */
@Repository("brandDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class BrandDAO extends GenericDAO<Brand> {

    private static final Logger log = LoggerFactory.getLogger(BrandDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<Brand> getTClass() {
    	return Brand.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.brand";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(Brand transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(Brand transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public Brand merge(Brand transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<Brand> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
	@SuppressWarnings("unchecked")
	public List<Brand> getBrandForManufacture(long manufactureAcNo, String brandShort) {
		getLogger().debug("getting Brand List For Manufacture");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
					+ " WHERE manufacture_ac_no = " + manufactureAcNo
					+ " AND LOWER(name) LIKE '%" + brandShort.toLowerCase() + "%'", getTClass());

			return (List<Brand>) query.getResultList();       
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
	public List<Brand> getBrandByName(String brandShort) {
		getLogger().debug("getting Brand List For Manufacture");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
					+ " WHERE LOWER(name) LIKE '%" + brandShort.toLowerCase() + "%'", getTClass());

			return (List<Brand>) query.getResultList();       
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