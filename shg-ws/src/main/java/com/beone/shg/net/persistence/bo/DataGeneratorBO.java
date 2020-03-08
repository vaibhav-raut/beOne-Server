package com.beone.shg.net.persistence.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.District;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.DistrictValue;
import com.beone.shg.net.webservice.rest.model.resp.DistrictsEnum;
import com.beone.shg.net.webservice.rest.model.rest.GroupContactREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberContactREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("dataGeneratorBO")
public class DataGeneratorBO extends BaseBO {
    private final static Logger LOGGER = LoggerFactory.getLogger(DataGeneratorBO.class);

	@Autowired
	@Qualifier("groupBO")
    private GroupBO groupBO;

	@Autowired
	@Qualifier("memberBO")
    private MemberBO memberBO;

	@Autowired
	@Qualifier("enumBO")
    private EnumBO enumBO;
    
    public void addDefaultGroups() throws BadRequestException {
    	DistrictsEnum districts = enumBO.getDistricts(EnumConst.Lang_English);
    	
    	{
    		addGroup(EnumCache.getDistrictValue(EnumConst.District_Code_Super_Admin), EnumConst.GroupType_SHG_One_Admin, EnumConst.MRole_Super_Admin);
    		addGroup(EnumCache.getDistrictValue(EnumConst.District_Code_Super_Admin), EnumConst.GroupType_Super_Area_Admin, EnumConst.MRole_Super_Area_Admin);

    		// For Udaan
    		addGroup(EnumCache.getDistrictValue(EnumConst.District_Code_Udaan), EnumConst.GroupType_Udaan_Admin, EnumConst.MRole_Udaan_Admin);
    		addGroup(EnumCache.getDistrictValue(EnumConst.District_Code_Udaan), EnumConst.GroupType_Mega_HUB, EnumConst.MRole_Mega_HUB_Manager);
    		addGroup(EnumCache.getDistrictValue(EnumConst.District_Code_Udaan), EnumConst.GroupType_Super_Sales_Executive, null);
    	}

    	for(DistrictValue district: districts.getDistrictValues()) {
    		
    		if(district.getState().equals(EnumConst.District_State_Admin)) {
    			continue;
    		}
    		
    		addGroup(district, EnumConst.GroupType_Area_Admin, EnumConst.MRole_Area_Admin);
    		addGroup(district, EnumConst.GroupType_SHG_One_Agent, null);
    		
    		// For Udaan
    		addGroup(district, EnumConst.GroupType_Local_HUB, EnumConst.MRole_Local_HUB_Manager);
    		addGroup(district, EnumConst.GroupType_Sales_Executive, null);
    		addGroup(district, EnumConst.GroupType_Micro_Retailer, null);
   		
    	}
	}
    
    public void addGroup(String district, String groupType, String mRole) throws BadRequestException {
    	
    	addGroup(EnumCache.getDistrictValue(district), groupType, mRole.trim());
	}
    
    protected void addGroup(DistrictValue district, String groupType, String mrole) {
    	try {
    		District dis = daoFactory.getDistrictDAO().findById(district.getDistrictId());
    		if(dis.getGroupCounter() <= 4) {
    			GroupREST group = groupBO.addGroup(buildGroupREST(district, groupType), true);
    			if(mrole != null && !mrole.isEmpty()) {
    				memberBO.addMember(buildMemberREST(district, group.getGroupAcNo(), mrole), true);
    			}
    		}
    	} catch (BadRequestException e) {
    		LOGGER.error(e.toString());
    	}    	
    }
    
    protected GroupREST buildGroupREST(DistrictValue district, String groupType) {
    	GroupREST group = new GroupREST();
    	group.setGroupType(groupType);
    	group.setApprovalStatus(EnumConst.ApprovalStatus_Approved);
    	group.setActiveStatus(EnumConst.ActiveStatus_Active);
    	group.setStatusMessage(groupType + " Group for District: " +  district.getDistrict());
    	
    	GroupContactREST contact = new GroupContactREST();
    	contact.setLang(EnumConst.Lang_English);
    	contact.setName(groupType + " " +  district.getDistrict());
    	contact.setDescription(groupType + " Group for District: " +  district.getDistrict());
    	contact.setAddress("SHG-One " +  district.getDistrict());
    	contact.setDistrict(district.getDistrict());
    	contact.setState(district.getState());
    	contact.setPinCode("");
    	contact.setPriMobile("0");
    	contact.setPhone("0");
    	contact.setEmail("support.beone@gmail.com");
    	
    	group.addContact(contact);
    	return group;
    }
    
    protected MemberREST buildMemberREST(DistrictValue district, long parentGroupAcNo, String mrole) {
    	MemberREST member = new MemberREST();
    	member.setParentGroupAcNo(parentGroupAcNo);
    	member.setMrole(mrole);
    	member.setApprovalStatus(EnumConst.ApprovalStatus_Approved);
    	member.setActiveStatus(EnumConst.ActiveStatus_Active);
    	member.setStatusMessage(mrole + " for Group for District: " +  district.getDistrict());
    	member.setPasscode("@" + district.getDistrict().replaceAll(" ", "_") + "#");
    	member.setGender(EnumConst.Gender_Unknown);
    	member.setDateOfBirth("01/01/2000");
    	member.setMonthlySaving(DataUtil.ZERO_BIG_DECIMAL);
    	MemberContactREST contact = new MemberContactREST();
    	contact.setLang(EnumConst.Lang_English);
    	contact.setNameTitle("");
    	contact.setFirstName(mrole);
    	contact.setLastName(district.getDistrict());
    	contact.setAddress("SHG-One " +  district.getDistrict());
    	contact.setDistrict(district.getDistrict());
    	contact.setState(district.getState());
    	contact.setPinCode("");
    	contact.setPriMobile("0");
    	contact.setPhone("0");
    	contact.setEmail("support.beone@gmail.com");
    	
    	member.addContact(contact);
    	return member;
    }
}
