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

import com.beone.shg.net.persistence.model.TxTodo;

/**
 * Home object for domain model class TxTodo.
 * @see .TxTodo
 * @author Hibernate Tools
 */
@Repository("txTodoDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class TxTodoDAO extends GenericDAO<TxTodo> {

    private static final Logger log = LoggerFactory.getLogger(TxTodoDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<TxTodo> getTClass() {
    	return TxTodo.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.tx_todo";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(TxTodo transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(TxTodo transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public TxTodo merge(TxTodo transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<TxTodo> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

    @SuppressWarnings("unchecked")
	public List<TxTodo> getAllTxsForGroup(long groupAcNo, long startTime, long endTime) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND due_date >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND due_date <= from_unixtime(" + endTime/1000 + ")"), getTClass());
        	
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

    @SuppressWarnings("unchecked")
	public List<TxTodo> getAllTxsForGroup(long groupAcNo, long startTime, long endTime, int txTypeId) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE g_ac_no = " + groupAcNo
        			+ " AND due_date >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND due_date <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND tx_type_id = " + txTypeId), getTClass());
        	
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

    @SuppressWarnings("unchecked")
	public List<TxTodo> getAllTxsForMember(long memberAcNo, long startTime, long endTime) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo
        			+ " AND due_date >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND due_date <= from_unixtime(" + endTime/1000 + ")"), getTClass());
        	
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

    @SuppressWarnings("unchecked")
	public List<TxTodo> getAllTxsForMember(long memberAcNo, long startTime, long endTime, int txTypeId) {
        getLogger().debug("getting Member Accounts for the Group");
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo
        			+ " AND due_date >= from_unixtime(" + startTime/1000 + ")"
        			+ " AND due_date <= from_unixtime(" + endTime/1000 + ")"
        			+ " AND tx_type_id = " + txTypeId), getTClass());
        	
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