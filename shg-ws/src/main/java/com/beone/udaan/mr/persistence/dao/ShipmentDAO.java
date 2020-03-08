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
import com.beone.udaan.mr.persistence.model.Shipment;

/**
 * Home object for domain model class Shipment.
 * @see Shipment
 */
@Repository("shipmentDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ShipmentDAO extends GenericDAO<Shipment> {

    private static final Logger log = LoggerFactory.getLogger(ShipmentDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<Shipment> getTClass() {
    	return Shipment.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.shipment";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(Shipment transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(Shipment transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public Shipment merge(Shipment transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<Shipment> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }
    
//    public Shipment findByValue(String enumValue) {
//    	return findById(EnumCache.getIndexOfEnumValue(EnumConst.Shipment, enumValue));
//    }
//    
//    public Shipment getReferenceByValue(String enumValue) {
//    	return getReferenceById(EnumCache.getIndexOfEnumValue(EnumConst.Shipment, enumValue));
//    }
}