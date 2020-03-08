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
import com.beone.shg.net.persistence.model.District;
import com.beone.shg.net.persistence.support.EnumCache;

/**
 * Home object for domain model class District.
 * @see .District
 * @author Hibernate Tools
 */
@Repository("districtDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class DistrictDAO extends GenericDAO<District> {

    private static final Logger log = LoggerFactory.getLogger(DistrictDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<District> getTClass() {
    	return District.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.district";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(District transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(District transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public District merge(District transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<District> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<District> getAllDistricts(String lang) {
        getLogger().debug("getting All Districts");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang), getTClass());
        	
            getLogger().debug("create Query successful");
            return query.getResultList();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            return null;
        }
    }

    public District getDistrictFromCode(String districtCode) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE district_code = '" + districtCode.trim().toUpperCase() + "'", getTClass());
        	
            getLogger().debug("create Query successful");
            return (District)query.getSingleResult();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            return null;
        }
    }

    public District getDistrictFromName(String state, String district) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
        			+ " WHERE district = '" + district.trim() + "'"
        			+ " AND state = '" + state.trim() + "'", getTClass());
        	
            getLogger().debug("create Query successful");
            return (District)query.getSingleResult();
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            return null;
        }
    }
}