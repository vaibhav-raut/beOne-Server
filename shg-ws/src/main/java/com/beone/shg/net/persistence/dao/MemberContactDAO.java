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
import com.beone.shg.net.persistence.model.MemberContact;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

/**
 * Home object for domain model class MemberContact.
 * @see .MemberContact
 * @author Hibernate Tools
 */
@Repository("memberContactDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberContactDAO extends GenericDAO<MemberContact> {

    private static final Logger log = LoggerFactory.getLogger(MemberContactDAO.class);
    
    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    protected Logger getLogger() {
    	return log;
    }

    @Override
    protected Class<MemberContact> getTClass() {
    	return MemberContact.class;
    }

    @Override
    protected String getTableName() {
    	return "shg.member_contact";
    }
    
    @Override
    protected EntityManager getEntityManager(){
    	return entityManager;
    }

    @Override
    public void persist(MemberContact transientInstance) {
    	super.persist(transientInstance);
    }

    @Override
    public void remove(MemberContact transientInstance) {
    	super.remove(transientInstance);
    }

    @Override
    public MemberContact merge(MemberContact transientInstance) {
    	return super.merge(transientInstance);
    }

    @Override
    public List<MemberContact> getAllRowList(int limit) {
    	return super.getAllRowList(limit);
    }

	public MemberContact findContactOfMember(String lang, long memberAcNo) {
        getLogger().debug("getting Member Contact");
        
        try {
        	Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
        			+ " WHERE m_ac_no = " + memberAcNo
        			+ " AND lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)), getTClass());
        	
            getLogger().debug("create Query successful");
            
            return (MemberContact) query.getSingleResult();
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
	public List<MemberContact> getMembersContactList(String lang, Object[] memberAcNos) {
        getLogger().debug("getting Members Contact List");
        
        try {
     		Query query = getEntityManager().createNativeQuery(("SELECT * FROM " + getTableName() 
//					+ " LIMIT 0, 100"
        			+ " WHERE m_ac_no IN " + ConversionUtil.convertArrayToInString(memberAcNos)
        			+ " AND lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)), getTClass());
        	
    		return (List<MemberContact>) query.getResultList();       
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
	public List<MemberContact> getActiveMembersContactListByGroup(String lang, long groupAcNo) {
        getLogger().debug("getting Members Contact List");
        
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " C" 
        			+ " JOIN m_profile M"
//					+ " LIMIT 0, 100"
        			+ " WHERE C.m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND C.m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
        			+ " AND C.m_ac_no = M.m_ac_no"
        			+ " AND C.lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)
					+ " AND M.active_status_id IN " + EnumUtil.getActiveStatusIDs(), getTClass());
        	
        	return (List<MemberContact>) query.getResultList();       
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
	public List<MemberContact> getMembersContactListByGroup(String lang, long groupAcNo) {
        getLogger().debug("getting Members Contact List");
        
        try {
        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
//					+ " LIMIT 0, 100"
        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
        			+ " AND lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang), getTClass());

        	return (List<MemberContact>) query.getResultList();       
        }
        catch (NoResultException re) {
            return null;
        }
        catch (RuntimeException re) {
            getLogger().error("create Query failed", re);
            throw re;
        }
    }

//	@SuppressWarnings("unchecked")
//	public List<MemberContact> getActiveMembersContactListByName(String lang, long groupAcNo, String name) {
//        getLogger().debug("getting Members Contact List");
//        
//        try {
//        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() + " C" 
//        			+ " JOIN m_profile M"
////					+ " LIMIT 0, 100"
//        			+ " WHERE C.m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
//        			+ " AND C.m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
//        			+ " AND C.m_ac_no = M.m_ac_no"
//        			+ " AND C.lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang)
//					+ " AND M.active_status_id IN " + EnumUtil.getActiveStatusIDs(), getTClass());
//        	
//        	return (List<MemberContact>) query.getResultList();       
//        }
//        catch (NoResultException re) {
//            return null;
//        }
//        catch (RuntimeException re) {
//            getLogger().error("create Query failed", re);
//            throw re;
//        }
//    }
//
//	@SuppressWarnings("unchecked")
//	public List<MemberContact> getMembersContactListByName(String lang, long groupAcNo, String name) {
//        getLogger().debug("getting Members Contact List");
//        
//        try {
//        	Query query = getEntityManager().createNativeQuery("SELECT * FROM " + getTableName() 
////					+ " LIMIT 0, 100"
//        			+ " WHERE m_ac_no > " + ConversionUtil.getStartRangeMemberAc(groupAcNo) 
//        			+ " AND m_ac_no < " + ConversionUtil.getEndRangeMemberAc(groupAcNo)
//        			+ " AND lang_id = " + EnumCache.getIndexOfEnumValue(EnumConst.Lang, lang), getTClass());
//
//        	return (List<MemberContact>) query.getResultList();       
//        }
//        catch (NoResultException re) {
//            return null;
//        }
//        catch (RuntimeException re) {
//            getLogger().error("create Query failed", re);
//            throw re;
//        }
//    }

	public void loadMemberNames(String lang, GroupInfoCollector collector) {
		if(collector != null && collector.getMemberAcNos() != null && !collector.getMemberAcNos().isEmpty()) {
			List<MemberContact> contacts = getMembersContactList(lang, collector.getMemberAcNos().toArray());

			for(MemberContact contact : contacts) {
				collector.putMamberProfile(contact.getMProfile().getMemberAcNo(), contact.getMProfile(), 
						(contact.getNameTitle().getTitle() + " " + contact.getFirstName() + " " + contact.getLastName()));
			}
		}
	}

	public void loadMemberFullNames(String lang, GroupInfoCollector collector) {
		if(collector != null && collector.getMemberAcNos() != null && !collector.getMemberAcNos().isEmpty()) {
			List<MemberContact> contacts = getMembersContactList(lang, collector.getMemberAcNos().toArray());

			for(MemberContact contact : contacts) {
				collector.putMamberProfile(contact.getMProfile().getMemberAcNo(), contact.getMProfile(), 
						(contact.getNameTitle().getTitle() + " " + contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName()));
			}
		}
	}

	public void loadActiveMemberNamesForGroup(String lang, GroupInfoCollector collector) {
		if(collector != null && collector.getGroupAcNo() > DataUtil.ZERO_LONG) {
			List<MemberContact> contacts = getActiveMembersContactListByGroup(lang, collector.getGroupAcNo());

			for(MemberContact contact : contacts) {
				collector.putMamberProfile(contact.getMProfile().getMemberAcNo(), contact.getMProfile(), 
						(contact.getNameTitle().getTitle() + " " + contact.getFirstName() + " " + contact.getLastName()));
			}
		}
	}

	public void loadMemberNamesForGroup(String lang, GroupInfoCollector collector) {
		if(collector != null && collector.getGroupAcNo() > DataUtil.ZERO_LONG) {
			List<MemberContact> contacts = getMembersContactListByGroup(lang, collector.getGroupAcNo());

			for(MemberContact contact : contacts) {
				collector.putMamberProfile(contact.getMProfile().getMemberAcNo(), contact.getMProfile(), 
						(contact.getNameTitle().getTitle() + " " + contact.getFirstName() + " " + contact.getLastName()));
			}
		}
	}

	public void loadMemberFullNamesForGroup(String lang, GroupInfoCollector collector) {
		if(collector != null && collector.getGroupAcNo() > DataUtil.ZERO_LONG) {
			List<MemberContact> contacts = getMembersContactListByGroup(lang, collector.getGroupAcNo());

			for(MemberContact contact : contacts) {
				collector.putMamberProfile(contact.getMProfile().getMemberAcNo(), contact.getMProfile(),
						(contact.getNameTitle().getTitle() + " " + contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName()));
			}
		}
	}

	public void loadMemberNonTilelFullNamesForGroup(String lang, GroupInfoCollector collector) {
		if(collector != null && collector.getGroupAcNo() > DataUtil.ZERO_LONG) {
			List<MemberContact> contacts = getMembersContactListByGroup(lang, collector.getGroupAcNo());

			for(MemberContact contact : contacts) {
				collector.putMamberProfile(contact.getMProfile().getMemberAcNo(), contact.getMProfile(), 
						(contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName()));
			}
		}
	}

	public void loadMemberLiveNonTilelFullNamesForGroup(String lang, GroupInfoCollector collector) {
		if(collector != null && collector.getGroupAcNo() > DataUtil.ZERO_LONG) {
			List<MemberContact> contacts = getMembersContactListByGroup(lang, collector.getGroupAcNo());

			for(MemberContact contact : contacts) {
				if(EnumUtil.isLive(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, contact.getMProfile().getActiveStatusId()))) {

					collector.putMamberProfile(contact.getMProfile().getMemberAcNo(), contact.getMProfile(), 
							(contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName()));
				}
			}
		}
	}

	public String getNameOfMember(String lang, long memberAcNo) {
		MemberContact contact = findContactOfMember(lang, memberAcNo);
		
		if(contact != null)
			return (contact.getNameTitle().getTitle() + " " + contact.getFirstName() + " " + contact.getLastName());
		else 
			return null;
	}

	public String getShortNameOfMember(String lang, long memberAcNo) {
		MemberContact contact = findContactOfMember(lang, memberAcNo);
		
		if(contact != null)
			return (contact.getFirstName());
		else 
			return null;
	}

	public String getFullNameOfMember(String lang, long memberAcNo) {
		MemberContact contact = findContactOfMember(lang, memberAcNo);
		
		if(contact != null)
			return (contact.getNameTitle().getTitle() + " " + contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName());
		else 
			return null;
	}
	
}