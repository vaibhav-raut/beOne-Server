package com.beone.udaan.mr.persistence.dao;
// default package
// Generated Mar 22, 2014 6:10:20 PM by Hibernate Tools 3.1.0.beta4

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.beone.shg.net.persistence.dao.GenericDAO;
import com.beone.udaan.mr.persistence.model.ShipmentStatus;

/**
 * Home object for domain model class ShipmentStatus.
 * @see ShipmentStatus
 */
@Repository("shipmentStatusDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ShipmentStatusDAO extends GenericDAO<ShipmentStatus> {

    private static final Logger log = LoggerFactory.getLogger(ShipmentStatusDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<ShipmentStatus> getTClass() {
    	return ShipmentStatus.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.shipment_status";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(ShipmentStatus transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(ShipmentStatus transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public ShipmentStatus merge(ShipmentStatus transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<ShipmentStatus> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public ShipmentStatus findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.ShipmentStatus, enumValue));
//    }
//    
//    public ShipmentStatus getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.ShipmentStatus, enumValue));
//    }
}