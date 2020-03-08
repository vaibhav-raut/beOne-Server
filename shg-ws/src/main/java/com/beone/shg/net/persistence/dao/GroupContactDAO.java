package com.beone.shg.net.persistence.dao;

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
import com.beone.shg.net.persistence.model.GroupContact;
import com.beone.shg.net.persistence.support.DistrictInfoCollector;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.rest.GroupName;
import com.beone.udaan.mr.config.EnumConstMr;

/**
 * Home object for domain model class GroupContact.
 * @see .GroupContact
 * @author Hibernate Tools
 */
@Repository("groupContactDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GroupContactDAO extends GenericDAO<GroupContact> {

	private static final Logger log = LoggerFactory.getLogger(GroupContactDAO.class);

	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	protected Logger getLogger() {
		return log;
	}

	@Override
	protected Class<GroupContact> getTClass() {
		return GroupContact.class;
	}

	@Override
	protected String getTableName() {
		return "shg.group_contact";
	}

	@Override
	protected EntityManager getEntityManager(){
		return entityManager;
	}

	@Override
	public void persist(GroupContact transientInstance) {
		super.persist(transientInstance);
	}

	@Override
	public void remove(GroupContact transientInstance) {
		super.remove(transientInstance);
	}

	@Override
	public GroupContact merge(GroupContact transientInstance) {
		return super.merge(transientInstance);
	}

	@Override
	public List<GroupContact> getAllRowList(int limit) {
		return super.getAllRowList(limit);
	}

	public GroupContact findContactOfGroup(String lang, long groupAcNo) {
		getLogger().debug("getting Member Contact");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
					+ " WHERE g_ac_no = " + groupAcNo
					+ " AND lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)), getTClass());

			getLogger().debug("create Query successful");

			return (GroupContact) query.getSingleResult();
		}
		catch (NoResultException re) {
			return null;
		}
		catch (RuntimeException re) {
			getLogger().error("create Query failed", re);
			throw re;
		}
	}

	public String getNameOfGroup(String lang, long groupAcNo) {
		GroupContact contact = findContactOfGroup(lang, groupAcNo);

		return (contact.getName());
	}

	@SuppressWarnings("unchecked")
	public List<GroupContact> getGroupsContactList(String lang, Object[] groupAcNos) {
		getLogger().debug("getting Groups Contact List");

		try {
			Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
//					+ " LIMIT 0, 100"
					+ " WHERE g_ac_no IN " + ConversionUtil.convertArrayToInString(groupAcNos)
					+ " AND lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)), getTClass());

			return (List<GroupContact>) query.getResultList();       
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
	public List<GroupContact> getGroupsContactListByDistrict(String lang, int districtId, String groupNameShort) {
		getLogger().debug("getting Group Contact List");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " C"  
//					+ " LIMIT 0, 100"
        			+ " JOIN g_profile G"
					+ " WHERE C.g_ac_no > " + ConversionUtil.getStartRangeGroupAc(districtId) 
					+ " AND C.g_ac_no < " + ConversionUtil.getEndRangeGroupAc(districtId)
					+ " AND LOWER(C.name) LIKE '%" + groupNameShort.toLowerCase() + "%'"
					+ " AND C.lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)
					+ " AND C.g_ac_no = G.g_ac_no"
					+ " AND G.active_status_id <> " + EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Junk), getTClass());

			return (List<GroupContact>) query.getResultList();       
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
	public List<GroupContact> getGroupsContactListByDistrictForType(String lang, int districtId, String groupNameShort, int groupTypeId) {
		getLogger().debug("getting Group Contact List");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " C"  
        			+ " JOIN g_profile G"
//					+ " LIMIT 0, 100"
					+ " WHERE C.g_ac_no > " + ConversionUtil.getStartRangeGroupAc(districtId) 
					+ " AND C.g_ac_no < " + ConversionUtil.getEndRangeGroupAc(districtId)
					+ " AND LOWER(C.name) LIKE '%" + groupNameShort.toLowerCase() + "%'"
					+ " AND C.lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)
					+ " AND C.g_ac_no = G.g_ac_no"
					+ " AND G.group_type_id = " + groupTypeId
					+ " AND G.active_status_id <> " + EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Junk), getTClass());

			return (List<GroupContact>) query.getResultList();       
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
	public List<GroupContact> getGroupsContactListByDistrictForMember(String lang, int districtId, long memberAcNo, String groupNameShort) {
		getLogger().debug("getting Group Contact List");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " C"  
        			+ " JOIN g_profile G"
        			+ " JOIN g_m GM"
//					+ " LIMIT 0, 100"
					+ " WHERE C.g_ac_no > " + ConversionUtil.getStartRangeGroupAc(districtId) 
					+ " AND C.g_ac_no < " + ConversionUtil.getEndRangeGroupAc(districtId)
					+ " AND LOWER(C.name) LIKE '%" + groupNameShort.toLowerCase() + "%'"
					+ " AND C.lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)
					+ " AND C.g_ac_no = G.g_ac_no"
					+ " AND C.g_ac_no = GM.g_ac_no"
					+ " AND GM.m_ac_no = " + memberAcNo
					+ " AND G.active_status_id <> " + EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Junk), getTClass());

			return (List<GroupContact>) query.getResultList();       
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
	public List<GroupContact> getGroupsContactListByDistrictForMemberForType(String lang, int districtId, long memberAcNo, String groupNameShort, int groupTypeId) {
		getLogger().debug("getting Group Contact List");

		try {
			Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " C"  
        			+ " JOIN g_profile G"
        			+ " JOIN g_m GM"
//					+ " LIMIT 0, 100"
					+ " WHERE C.g_ac_no > " + ConversionUtil.getStartRangeGroupAc(districtId) 
					+ " AND C.g_ac_no < " + ConversionUtil.getEndRangeGroupAc(districtId)
					+ " AND LOWER(C.name) LIKE '%" + groupNameShort.toLowerCase() + "%'"
					+ " AND C.lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)
					+ " AND C.g_ac_no = G.g_ac_no"
					+ " AND C.g_ac_no = GM.g_ac_no"
					+ " AND GM.m_ac_no = " + memberAcNo
					+ " AND G.group_type_id = " + groupTypeId
					+ " AND G.active_status_id <> " + EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Junk), getTClass());

			return (List<GroupContact>) query.getResultList();       
		}
		catch (NoResultException re) {
			return null;
		}
		catch (RuntimeException re) {
			getLogger().error("create Query failed", re);
			throw re;
		}
	}

	public void loadGroupNames(String lang, DistrictInfoCollector collector) {
		if(collector != null && collector.getGroupAcNos() != null && !collector.getGroupAcNos().isEmpty()) {
			List<GroupContact> contacts = getGroupsContactList(lang, collector.getGroupAcNos().toArray());

			for(GroupContact contact : contacts) {
				long groupAcNo = contact.getGProfile().getGAcNo();
				collector.putGroupName(groupAcNo, 
						new GroupName(ConversionUtil.getDistrictFromGroupAc(groupAcNo), 
								groupAcNo, 
								contact.getName(),
								EnumCache.getNameOfGroupType(contact.getGProfile().getGroupTypeId()),
								EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, contact.getGProfile().getActiveStatusId()),
								ConversionUtil.getGroupPlace(contact)));
			}
		}
	}

	public void loadGroupNamesForDistrict(String lang, DistrictInfoCollector collector) {
		if(collector != null && collector.getDistrictId() > DataUtil.ZERO_LONG) {
			int groupTypeId = -1;
			if(collector.getGroupType() != null && !collector.getGroupType().isEmpty()) {
				groupTypeId = EnumCache.getIndexOfGroupType(collector.getGroupType());
			}
			List<GroupContact> contacts = null;
			if(groupTypeId > 0) {
				contacts = getGroupsContactListByDistrictForType(lang, collector.getDistrictId(), collector.getGroupNameShort(), groupTypeId);
			} else {
				contacts = getGroupsContactListByDistrict(lang, collector.getDistrictId(), collector.getGroupNameShort());
			}

			if(contacts != null) {
				for(GroupContact contact : contacts) {
					long groupAcNo = contact.getGProfile().getGAcNo();
					collector.putGroupName(groupAcNo, 
							new GroupName(ConversionUtil.getDistrictFromGroupAc(groupAcNo), 
									groupAcNo, 
									contact.getName(), 
									EnumCache.getNameOfGroupType(contact.getGProfile().getGroupTypeId()),
									EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, contact.getGProfile().getActiveStatusId()),
									ConversionUtil.getGroupPlace(contact)));
				}
			}
		}
	}

	public void loadGroupNamesForDistrictForMember(String lang, DistrictInfoCollector collector) {
		if(collector != null && collector.getDistrictId() > DataUtil.ZERO_LONG) {
			int groupTypeId = -1;
			if(collector.getGroupType() != null && !collector.getGroupType().isEmpty()) {
				groupTypeId = EnumCache.getIndexOfGroupType(collector.getGroupType());
			}
			List<GroupContact> contacts = null;
			if(groupTypeId > 0) {
				contacts = getGroupsContactListByDistrictForMemberForType(lang, collector.getDistrictId(), collector.getReqMemberAcNos(), collector.getGroupNameShort(), groupTypeId);
			} else {
				contacts = getGroupsContactListByDistrictForMember(lang, collector.getDistrictId(), collector.getReqMemberAcNos(), collector.getGroupNameShort());
			}

			if(contacts != null) {
				for(GroupContact contact : contacts) {
					long groupAcNo = contact.getGProfile().getGAcNo();
					collector.putGroupName(groupAcNo, 
							new GroupName(ConversionUtil.getDistrictFromGroupAc(groupAcNo), 
									groupAcNo, 
									contact.getName(), 
									EnumCache.getNameOfGroupType(contact.getGProfile().getGroupTypeId()),
									EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, contact.getGProfile().getActiveStatusId()),
									ConversionUtil.getGroupPlace(contact)));
				}
			}
		}
	}

	public void loadVendorNames(DistrictInfoCollector collector) {
		
		int groupTypeId = EnumCache.getGroupTypeValue(EnumConstMr.GroupType_Manufacturer).getGroupTypeId();
		int districtId = EnumCache.getDistrictValue(EnumConst.District_Code_Udaan).getDistrictId();
		List<GroupContact> contacts = getGroupsContactListByDistrictForType(EnumConst.Lang_English, districtId, collector.getGroupNameShort(), groupTypeId);

		if(contacts != null) {
			for(GroupContact contact : contacts) {
				long groupAcNo = contact.getGProfile().getGAcNo();
				collector.putGroupName(groupAcNo, 
						new GroupName(ConversionUtil.getDistrictFromGroupAc(groupAcNo), 
								groupAcNo, 
								contact.getName(), 
								EnumCache.getNameOfGroupType(contact.getGProfile().getGroupTypeId()),
								EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, contact.getGProfile().getActiveStatusId()),
								ConversionUtil.getGroupPlace(contact)));
			}
		}
	}
}