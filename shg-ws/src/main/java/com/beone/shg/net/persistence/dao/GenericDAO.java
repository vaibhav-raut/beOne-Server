package com.beone.shg.net.persistence.dao;

import java.util.List;

import org.slf4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Home object for domain model class PaymentMode.
 * @see .PaymentMode
 * @author Hibernate Tools
 */
public abstract class GenericDAO <T> {

	protected static final String TABLE_NAME = "TABLE_NAME";
	protected static final String ROW_LIMIT = "ROW_LIMIT";
	
    protected abstract Logger getLogger();
    protected abstract Class<T> getTClass();
    protected abstract String getTableName();
    protected abstract EntityManager getEntityManager();   

    public void persist(T transientInstance) {
        getLogger().debug("persisting " + getTClass() + " instance");
        
        try {
            getEntityManager().persist(transientInstance);

        	getLogger().debug("persist successful");
        }
        catch (RuntimeException re) {
            getLogger().error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(T persistentInstance) {
        getLogger().debug("removing " + getTClass() + " instance");
        try {
        	getEntityManager().remove(getEntityManager().contains(persistentInstance) ? persistentInstance : getEntityManager().merge(persistentInstance));
            getLogger().debug("remove successful");
        }
        catch (RuntimeException re) {
            getLogger().error("remove failed", re);
            throw re;
        }
    }
    
    public T merge(T detachedInstance) {
        getLogger().debug("merging " + getTClass() + " instance");
        try {
            T result = (T) getEntityManager().merge(detachedInstance);
            getLogger().debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            getLogger().error("merge failed", re);
            throw re;
        }
    }
    
    public T findById( int id) {
        getLogger().debug("getting " + getTClass() + " instance with id: " + id);
        try {
            T instance = (T) getEntityManager().find(getTClass(), id);
            getLogger().debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            getLogger().error("get failed", re);
            throw re;
        }
    }
    
    public T findById( long id) {
        getLogger().debug("getting " + getTClass() + " instance with id: " + id);
        try {
            T instance = (T) getEntityManager().find(getTClass(), id);
            getLogger().debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            getLogger().error("get failed", re);
            throw re;
        }
    }
    
    public T getReferenceById( int id) {
        getLogger().debug("getting " + getTClass() + " Reference with id: " + id);
        try {
            T instance = (T) getEntityManager().getReference(getTClass(), id);
            getLogger().debug("get Reference successful");
            return instance;
        }
        catch (RuntimeException re) {
            getLogger().error("get Reference failed", re);
            throw re;
        }
    }
    
    public T getReferenceById( long id) {
        getLogger().debug("getting " + getTClass() + " Reference with id: " + id);
        try {
            T instance = (T) getEntityManager().getReference(getTClass(), id);
            getLogger().debug("get Reference successful");
            return instance;
        }
        catch (RuntimeException re) {
            getLogger().error("get Reference failed", re);
            throw re;
        }
    }
    
    public Query createQuery(String queryStr) {
        getLogger().debug("getting " + getTClass() + " Reference with queryStr: " + queryStr);
        try {
        	Query query = getEntityManager().createQuery(queryStr);
            getLogger().debug("create Query successful");
            return query;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            throw re;
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<T> getAllRowList(int limit) {
        getLogger().debug("getting Row List with limit:" + limit);
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " LIMIT 0, " + limit, getTClass());
//        	query.setParameter(TABLE_NAME, getTableName());
//        	query.setParameter(ROW_LIMIT, limit);
        	
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