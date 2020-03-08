package com.beone.shg.net.persistence.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.common.web.security.AuthKeyManager;
import com.beone.shg.common.web.security.Encryptor;
import com.beone.shg.common.web.security.ShgPassToken;
import com.beone.shg.net.auth.MrUiAccessCodeUtil;
import com.beone.shg.net.auth.MrWsAccessCodeUtil;
import com.beone.shg.net.auth.UiAccessCodeUtil;
import com.beone.shg.net.auth.WsAccessCodeUtil;
import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.District;
import com.beone.shg.net.persistence.model.GM;
import com.beone.shg.net.persistence.model.GMId;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.GroupContact;
import com.beone.shg.net.persistence.model.MMessage;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.MobileM;
import com.beone.shg.net.persistence.support.DistrictInfoCollector;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.util.RandomString;
import com.beone.shg.net.util.WSLock;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeValue;
import com.beone.shg.net.webservice.rest.model.resp.DistrictValue;
import com.beone.shg.net.webservice.rest.model.resp.MRoleValue;
import com.beone.shg.net.webservice.rest.model.rest.DistrictSearchInfo;
import com.beone.shg.net.webservice.rest.model.rest.GroupName;
import com.beone.shg.net.webservice.rest.model.rest.GroupSearchInfo;
import com.beone.shg.net.webservice.rest.model.rest.LockRequestREST;
import com.beone.shg.net.webservice.rest.model.rest.LoginInfoREST;
import com.beone.shg.net.webservice.rest.model.rest.LoginRequestREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberAccessREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberAccessREST.MapRole;
import com.beone.shg.net.webservice.rest.model.rest.PassChangeRequestREST;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.InternalServerErrorException;

@Component("authBO")
public class AuthBO extends BaseBO {
	
	@Autowired
	@Qualifier("uiAccessCodeUtil")
	protected UiAccessCodeUtil uiAccessCodeUtil;

	@Autowired
	@Qualifier("wsAccessCodeUtil")
	protected WsAccessCodeUtil wsAccessCodeUtil;

	@Autowired
	@Qualifier("mrUiAccessCodeUtil")
	protected MrUiAccessCodeUtil mrUiAccessCodeUtil;

	@Autowired
	@Qualifier("mrWsAccessCodeUtil")
	protected MrWsAccessCodeUtil mrWsAccessCodeUtil;
	
    private static Encryptor encryptor;
	private int keyShift = 0;
    private RandomString randomString = new RandomString(6, true, false);
    private WSLock loginLock = new WSLock();

    private void initEncryptor() {
        try {
            encryptor = new Encryptor(AuthKeyManager.getInstance().getKey());
        	keyShift = AuthKeyManager.getInstance().getKeyShift();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
    private void checkEncryptor() {
    	if(keyShift != AuthKeyManager.getInstance().getKeyShift()) {
    		initEncryptor();
    	}
    }
    
    public LockRequestREST lockService(LockRequestREST request) throws BadRequestException, AccessDeniedException {
    	
    	if(!ConversionUtil.isValidMemberAcNo(request.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		if(request.getServiceName() == null || request.getServiceName().isEmpty()) {
			throw new BadRequestException("Invalid Service Name!");
		}
		if(request.getLockTimeInMin() <= 0) {
			throw new BadRequestException("Invalid Lock Duration!");
		}

		long groupAcNo = ConversionUtil.getGroupAcFromMember(request.getMemberAcNo());		
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);		
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account Number!");
		}
    	if(!EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()).equals(EnumConst.GroupType_SHG_One_Admin)) {
			throw new AccessDeniedException("Invalid Privilege to Lock Service!");
    	}
    	
    	switch(request.getServiceName()) {
    	case EnumConst.Lock_Service_Login:
    	{
    		lockAction(loginLock, request);
    		break;
    	}
    	default:
    	{
			throw new BadRequestException("Invalid Service Name!");
    	}
    	}
    	
    	return request;
    }
    
    protected void lockAction(WSLock lock, LockRequestREST request) {
    	
		switch(request.getAction()) {
    	case EnumConst.Lock_Action_LOCK:
    	{
    		lock.lock(request.getLockTimeInMin());
    		
    		if(request.getServiceName().equals(EnumConst.Lock_Service_Login)) {
        		AuthKeyManager.getInstance().resetKey();
    		}
    		break;
    	}
    	case EnumConst.Lock_Action_UNLOCK:
    	{
    		lock.unLock();
    		break;
    	}
    	case EnumConst.Lock_Action_CHECK:
    	{
    		request.setTimeRemaining(DateUtil.convertTimeToDisplayString(lock.getSecondsRemaining()));
    		break;
    	}
		}
    }
    
    public LoginInfoREST login(LoginRequestREST request) throws BadRequestException, InternalServerErrorException, AccessDeniedException {
		
		if(request.getMemberDistrictCode() == null) {
			throw new BadRequestException("Invalid Member District Code");
		}
		if(request.getMemberDistrictCode().length() != 4) {
			throw new BadRequestException("Invalid Member District Code : " + request.getMemberDistrictCode());
		}
		if(request.getMemberNo() <= DataUtil.ZERO_INTEGER || request.getMemberNo() >= DataUtil.MEMBER_RANGE_FOR_GROUP) {
			throw new BadRequestException("Invalid Member Account Number!");
		}
		if(request.getGroupNo() <= DataUtil.ZERO_INTEGER || request.getGroupNo() >= DataUtil.GROUP_RANGE_FOR_DISTRICT) {
			throw new BadRequestException("Invalid Group Account Number!");
		}
		if(request.getPasscode() == null || request.getPasscode().length() < 6) {
			throw new BadRequestException("Invalid Passcode!");
		}
		
		District district = daoFactory.getDistrictDAO().getDistrictFromCode(request.getMemberDistrictCode());
		if(district == null) {
			throw new BadRequestException("Invalid Member District Code!");
		}
		
		int districtId = district.getDistrictId();
		
		long memberAcNo = ConversionUtil.getMemberAc(districtId, request.getGroupNo(), request.getMemberNo());
		long groupAcNo = ConversionUtil.getGroupAc(districtId, request.getGroupNo());
		
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);		
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account Number!");
		}
		if(!EnumUtil.isStatusActive(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()))) {
			if(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()).equals(EnumConst.ActiveStatus_Locked)) {
				throw new BadRequestException("Group is Locked, Please contact Admin Support Team!");
			}
			throw new BadRequestException("Group is Not Active!");
		}
		
		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);		
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account Number!");
		}
		if(!EnumUtil.isStatusActive(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()))) {
			throw new BadRequestException("Member Account Not Active!");
		}
    	if(!EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()).equals(EnumConst.GroupType_SHG_One_Admin) && loginLock.isLocked()) {
			throw new AccessDeniedException("SHG-One Login is Locked for next: " + DateUtil.convertTimeToDisplayString(loginLock.getSecondsRemaining()));
    	}

		GM gm = daoFactory.getGMDAO().findById(groupAcNo, memberAcNo);
		if(gm == null || gm.getUiAccessRights() == DataUtil.ZERO_LONG || gm.getWsAccessRights() == DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Member Access to Selected Group");
		}
		
		gProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Active));
		gProfile.setLastLoggedInTs(DateUtil.getCurrentTimeDate());
		daoFactory.getGProfileDAO().merge(gProfile);
		
		mProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Active));
		mProfile.setLastLoggedInTs(DateUtil.getCurrentTimeDate());
		daoFactory.getMProfileDAO().merge(mProfile);

		if(mProfile.getPasscode().equals(request.getPasscode())) {
			LoginInfoREST authInfo = new LoginInfoREST();
			long currentTime = System.currentTimeMillis();
			checkEncryptor();
			
			// Build SHG Pass Token 
	    	ShgPassToken authToken = new ShgPassToken();
	    	authToken.setTimestamp(currentTime + DateUtil.HOUR);
	    	authToken.setKey(AuthKeyManager.getInstance().getKey());
	    	authToken.setMemberAcNo(memberAcNo);
	    	authToken.setSelectedGroupAcNo(groupAcNo);
	    	authToken.setWsAccess(gm.getWsAccessRights());
	    	authToken.setUiAccess(gm.getUiAccessRights());
	    	
	    	// Load Login Info
			authInfo.setCurrentServerTime(currentTime);
	    	authInfo.setMemberAcNo(memberAcNo);
	    	authInfo.setSelectedGroupAcNo(groupAcNo);
	    	authInfo.setAuthToken(encryptor.encrypt(authToken));
			authInfo.setMemberDistrictCode(district.getDistrictCode());
			authInfo.setMemberDistrictId(districtId);
			authInfo.setGroupDistrictCode(district.getDistrictCode());
			authInfo.setGroupDistrictId(districtId);
			authInfo.setGroupName(daoFactory.getGroupContactDAO().getNameOfGroup(EnumConst.Lang_English, groupAcNo));
			authInfo.setGroupType(EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()));
			LoginInfoREST.loadAuthInfo(mProfile, authInfo);
			
			mProfile.setLastLoggedInTs(DateUtil.convertTimeToDate(currentTime));
			daoFactory.getMProfileDAO().merge(mProfile);

			return authInfo;
		} else {
			throw new BadRequestException("Invalid Password");
		}
		
    }

    public LoginInfoREST selectGroup(LoginRequestREST request) throws BadRequestException, InternalServerErrorException, AccessDeniedException {
		
    	if(!ConversionUtil.isValidMemberAcNo(request.getMemberNo())) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		if(request.getGroupDistrictCode() == null) {
			throw new BadRequestException("Null Or Empty Selected Group District Code");
		}
		if(request.getGroupDistrictCode().length() != 4) {
			throw new BadRequestException("Invalid Selected Group District Code : " + request.getGroupDistrictCode());
		}
		if(request.getSelectedGroupNo() <= DataUtil.ZERO_INTEGER || request.getSelectedGroupNo() >= DataUtil.GROUP_RANGE_FOR_DISTRICT) {
			throw new BadRequestException("Invalid Selected Group No");
		}
		if(request.getPasscode() == null || request.getPasscode().length() < 6) {
			throw new BadRequestException("Null Or Empty Password");
		}
		
		District groupDistrict = daoFactory.getDistrictDAO().getDistrictFromCode(request.getGroupDistrictCode());
		if(groupDistrict == null) {
			throw new BadRequestException("Invalid Selected Group District Code");
		}
		int groupDistrictId = groupDistrict.getDistrictId();
		
		// ** Note: for "select_group" API - MemberNo contains MemberAcNo **
		long memberAcNo = request.getMemberNo();
		int memberDistrictId = (int)ConversionUtil.getDistrictFromMemberAc(memberAcNo);
		District memberDistrict = daoFactory.getDistrictDAO().findById(memberDistrictId);

		long selectedGroupNo = ConversionUtil.getGroupAc(groupDistrictId, request.getSelectedGroupNo());
		
		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);		
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account Number");
		}
		
		GProfile gProfile = daoFactory.getGProfileDAO().findById(selectedGroupNo);		
		if(gProfile == null) {
			throw new BadRequestException("Invalid Selected Group Account Number");
		}
		if(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()).equals(EnumConst.ActiveStatus_Junk)) {
			throw new BadRequestException("Group Data is Junk and Not Usable!");
		}

		GM gm = getGMForAccess(selectedGroupNo, memberAcNo);
		if(gm == null || gm.getUiAccessRights() == DataUtil.ZERO_LONG || gm.getWsAccessRights() == DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Member Access to Selected Group");
		}

		if(mProfile.getPasscode().equals(request.getPasscode())) {
			
			LoginInfoREST authInfo = new LoginInfoREST();
			long currentTime = System.currentTimeMillis();
			checkEncryptor();

			// Build SHG Pass Token 
	    	ShgPassToken authToken = new ShgPassToken();
	    	authToken.setTimestamp(currentTime + DateUtil.HOUR);
	    	authToken.setKey(AuthKeyManager.getInstance().getKey());
	    	authToken.setMemberAcNo(memberAcNo);
	    	authToken.setSelectedGroupAcNo(selectedGroupNo);
	    	authToken.setWsAccess(gm.getWsAccessRights());
	    	authToken.setUiAccess(gm.getUiAccessRights());
	    	
	    	// Load Login Info
			authInfo.setCurrentServerTime(currentTime);
	    	authInfo.setMemberAcNo(memberAcNo);
	    	authInfo.setSelectedGroupAcNo(selectedGroupNo);
	    	authInfo.setAuthToken(encryptor.encrypt(authToken));
			authInfo.setMemberDistrictCode(memberDistrict.getDistrictCode());
			authInfo.setMemberDistrictId(memberDistrictId);
			authInfo.setGroupDistrictCode(groupDistrict.getDistrictCode());
			authInfo.setGroupDistrictId(groupDistrictId);
			authInfo.setGroupName(daoFactory.getGroupContactDAO().getNameOfGroup(EnumConst.Lang_English, selectedGroupNo));
			authInfo.setGroupType(EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()));
			LoginInfoREST.loadAuthInfo(mProfile, authInfo);
			
			mProfile.setLastLoggedInTs(DateUtil.convertTimeToDate(currentTime));
			daoFactory.getMProfileDAO().merge(mProfile);

			return authInfo;
		} else {
			throw new BadRequestException("Invalid Password");
		}
    }

    public GroupSearchInfo searchGroupNoInDistrict(GroupSearchInfo request) throws BadRequestException, AccessDeniedException {		

    	if(request.getSearchDistrictCode() == null) {
			throw new BadRequestException("Null Or Empty Search District Code");
		}
		if(request.getSearchDistrictCode().length() != 4) {
			throw new BadRequestException("Invalid Search District Code : " + request.getSearchDistrictCode());
		}
		if(request.getSearchGroupNo() <= 0) {
			throw new BadRequestException("Invalid Group No");
		}
    	if(!ConversionUtil.isValidMemberAcNo(request.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		
		request.setSearchDistrictCode(request.getSearchDistrictCode().toUpperCase());
		District searchDistrict = daoFactory.getDistrictDAO().getDistrictFromCode(request.getSearchDistrictCode());
		if(searchDistrict == null) {
			throw new BadRequestException("Invalid Search District Code");
		}
		int searchDistrictId = searchDistrict.getDistrictId();
		long searchGroupNo = ConversionUtil.getGroupAc(searchDistrictId, request.getSearchGroupNo());

		GProfile gProfile = daoFactory.getGProfileDAO().findById(searchGroupNo);
		if(gProfile == null) {
			throw new BadRequestException("Invalid Search Group Account No");
		}
		if(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()).equals(EnumConst.ActiveStatus_Junk)) {
			throw new BadRequestException("Group Data is Junk and Not Usable!");
		}

		GM gm = getGMForAccess(searchGroupNo, request.getMemberAcNo());
		if(gm == null) {
			throw new BadRequestException("Invalid Member Access to Search Group");
		}
		GroupContact gContact = daoFactory.getGroupContactDAO().findContactOfGroup(EnumConst.Lang_English, searchGroupNo);  	
		if(gContact == null) {
			throw new BadRequestException("Invalid Search Group Account No");
		}
		
		GroupName groupInfo = new GroupName();
		groupInfo.setDistrictCode(request.getSearchDistrictCode());
		groupInfo.setDistrictId(searchDistrictId);
		groupInfo.setGroupAcNo((int)searchGroupNo);
		groupInfo.setGroupName(gContact.getName());
		groupInfo.setGroupType(EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()));
		groupInfo.setStatus(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()));
		groupInfo.setPlace(ConversionUtil.getGroupPlace(gContact));
		request.addFoundGroup(groupInfo);
    	
    	return request;
    }

    public GroupSearchInfo searchGroupsForMember(GroupSearchInfo request) throws BadRequestException, AccessDeniedException {

    	if(!ConversionUtil.isValidMemberAcNo(request.getMemberAcNo())) {
    		throw new BadRequestException("Invalid Member Ac No");
    	}

    	List<GM> gms = daoFactory.getGMDAO().findByMember(request.getMemberAcNo());

    	DistrictInfoCollector collector = new DistrictInfoCollector();
    	for(GM gm : gms) {
    		collector.addGroupAcNo(gm.getGroupAcNo());
    	}
    	daoFactory.getGroupContactDAO().loadGroupNames(EnumConst.Lang_English, collector);

    	List<GroupName> groupNames = collector.getAllGroupName();

    	for(GroupName groupName : groupNames) {
    		GM gm = getGMForAccess(groupName.getGroupAcNo(), request.getMemberAcNo());
    		if(gm == null) {
    			continue;
    		}
    		request.addFoundGroup(groupName);
    	}

    	return request;
    }

    public GroupSearchInfo searchGroupByNameInDistrict(GroupSearchInfo request) throws BadRequestException, AccessDeniedException {

    	if(request.getSearchDistrictCode() == null) {
			throw new BadRequestException("Null Or Empty Search District Code");
		}
		if(request.getSearchDistrictCode().length() != 4) {
			throw new BadRequestException("Invalid Search District Code : " + request.getSearchDistrictCode());
		}
		if(request.getSearchGroupNameShort() == null || request.getSearchGroupNameShort().isEmpty()) {
			throw new BadRequestException("Null Or Empty Group Name");
		}
    	if(!ConversionUtil.isValidMemberAcNo(request.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		
		request.setSearchDistrictCode(request.getSearchDistrictCode().toUpperCase());
		District searchDistrict = daoFactory.getDistrictDAO().getDistrictFromCode(request.getSearchDistrictCode());
		if(searchDistrict == null) {
			throw new BadRequestException("Invalid Search District Code");
		}
		int searchDistrictId = searchDistrict.getDistrictId();
		
		GM gmAdmin = getGMForSHGOneAdmin((searchDistrictId * DataUtil.GROUP_RANGE_FOR_DISTRICT), 
				request.getMemberAcNo());

		DistrictInfoCollector collector = new DistrictInfoCollector();
		collector.setDistrictId(searchDistrictId);
		collector.setGroupNameShort(request.getSearchGroupNameShort());
		collector.setGroupType(request.getGroupType());
		
		if(gmAdmin == null) {
			collector.setReqMemberAcNos(request.getMemberAcNo());
			daoFactory.getGroupContactDAO().loadGroupNamesForDistrictForMember(EnumConst.Lang_English, collector);
		} else {
			daoFactory.getGroupContactDAO().loadGroupNamesForDistrict(EnumConst.Lang_English, collector);
		}
		List<GroupName> groupNames = collector.getAllGroupName(searchDistrictId, request.getSearchDistrictCode());
		
		if(gmAdmin != null) {
			request.setFoundGroups(groupNames);
		} 
		else {
			for(GroupName groupName : groupNames) {
				GM gm = daoFactory.getGMDAO().findById(groupName.getGroupAcNo(), request.getMemberAcNo());
				if(gm == null) {
					continue;
				}
				request.addFoundGroup(groupName);
			}
		}
		
		if(request.getFoundGroups() == null || request.getFoundGroups().isEmpty() ) {
			throw new BadRequestException("Invalid Group Name!");
		}
		return request;
    }

    public DistrictSearchInfo getAccessToDistrict(DistrictSearchInfo request) throws BadRequestException, AccessDeniedException {		

    	if(request.getDistrictId() <= -1) {
			throw new BadRequestException("Invalid Search District");
		}
    	if(!ConversionUtil.isValidMemberAcNo(request.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		
    	DistrictValue district = EnumCache.getDistrictValue(request.getDistrictId());
		if(district == null) {
			throw new BadRequestException("Invalid Search District");
		}

		if(getDistrictForAccess(request.getDistrictId(), request.getMemberAcNo())) {
			request.setDistrictCode(district.getDistrictCode());
		} else {
			throw new BadRequestException("No Access To Requested District!");
		}
    	
    	return request;
    }

    public PassChangeRequestREST resetMemberPassword(PassChangeRequestREST request) throws BadRequestException {

    	if(!ConversionUtil.isValidMemberAcNo(request.getMemberAcNo())) {
    		throw new BadRequestException("Invalid Member Account Number!");
    	}
    	MProfile mProfile = daoFactory.getMProfileDAO().findById(request.getMemberAcNo());		
    	if(mProfile == null) {
    		throw new BadRequestException("Invalid Member Account Number : " + request.getMemberAcNo());
    	}

    	request.setNewPasscode(randomString.nextString().toUpperCase());
    	mProfile.setPasscode(request.getNewPasscode());
    	mProfile.setPassSet((byte)0);
    	daoFactory.getMProfileDAO().merge(mProfile);
    	
    	return request;
    }

    public void changeMemberPassword(PassChangeRequestREST request) throws BadRequestException {
		
		if(request.getOldPasscode() == null || request.getOldPasscode().isEmpty()) {
			throw new BadRequestException("Invalid Old Password!");
		}
    	if(!ConversionUtil.isValidMemberAcNo(request.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account Number!");
		}
		MProfile mProfile = daoFactory.getMProfileDAO().findById(request.getMemberAcNo());		
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account Number : " + request.getMemberAcNo());
		}
		
		if(mProfile.getPasscode().equals(request.getOldPasscode())) {
			if(request.getNewPasscode() == null || request.getNewPasscode().isEmpty()) {
				throw new BadRequestException("Invalid New Password!");
			}
			if(request.getOldPasscode().equals(request.getNewPasscode())) {
				throw new BadRequestException("New Password can't be same as Old Password!");
			}
			if(request.getNewPasscode().length() < 6) {
				throw new BadRequestException("Password should have minimum 6 characters!");
			}
			if(request.getNewPasscode().length() > 20) {
				throw new BadRequestException("Password should have maximum 20 characters!");
			}
			
			mProfile.setPasscode(request.getNewPasscode());
			mProfile.setPassSet((byte)1);
			daoFactory.getMProfileDAO().merge(mProfile);
		} else {
			throw new BadRequestException("Invalid Old Password!");
		}
    }
    
    protected GM getGMForAccess(long selectedGroupNo, long memberAcNo) throws AccessDeniedException, BadRequestException {
    	GM gm = getGMForSHGOneAdmin(selectedGroupNo, memberAcNo);
    	
    	if(gm == null) { 	
    		gm = daoFactory.getGMDAO().findById(selectedGroupNo, memberAcNo);
    	}
    	
    	return gm;
    }
    
    protected GM getGMForSHGOneAdmin(long selectedGroupNo, long memberAcNo) throws AccessDeniedException, BadRequestException {
    	GM gm = null;

    	long groupAcNo = ConversionUtil.getGroupAcFromMember(memberAcNo);
    	GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		if(gProfile == null) {
			throw new BadRequestException("Invalid Member Group Account!");
		}
    	String groupType = EnumCache.getNameOfGroupType(gProfile.getGroupTypeId());
    	
    	GProfile selectedGProfile = daoFactory.getGProfileDAO().findById(selectedGroupNo);
		if(selectedGProfile == null) {
			throw new BadRequestException("Invalid Selected Group Account!");
		}
    	String selectedGroupType = EnumCache.getNameOfGroupType(selectedGProfile.getGroupTypeId());
    	
    	if(EnumUtil.isMrGroupType(selectedGroupType)) {

    		// Check for SHG-One Admin  & Area Admin
    		if((groupType.equals(EnumConst.GroupType_Udaan_Admin)) || 
    				(groupType.equals(EnumConst.GroupType_Mega_HUB)) || 
    				(groupType.equals(EnumConst.GroupType_Local_HUB) && 
    						(ConversionUtil.getDistrictFromGroupAc(selectedGroupNo) == ConversionUtil.getDistrictFromMemberAc(memberAcNo))) || 
    						(groupType.equals(EnumConst.GroupType_Sales_Executive) && 
    								selectedGroupType.equals(EnumConst.GroupType_Micro_Retailer) && 
    								(ConversionUtil.getDistrictFromGroupAc(selectedGroupNo) == ConversionUtil.getDistrictFromMemberAc(memberAcNo)))) {

    			gm = daoFactory.getGMDAO().findById(groupAcNo, memberAcNo);
    		}

    		// Check for Super Area Admin
    		else if	(groupType.equals(EnumConst.GroupType_Super_Sales_Executive)) {

    			gm = daoFactory.getGMDAO().findById(ConversionUtil.getMicroRetailerGroupAcFromGroup(selectedGroupNo), memberAcNo);
    		}
    	} 
    	else {

    		if(!groupType.equals(EnumConst.GroupType_SHG_One_Admin) && loginLock.isLocked()) {
    			throw new AccessDeniedException("SHG-One is Locked for next: " + DateUtil.convertTimeToDisplayString(loginLock.getSecondsRemaining()));
    		}

    		// Check for SHG-One Admin  & Area Admin
    		if((groupType.equals(EnumConst.GroupType_SHG_One_Admin)) || 
    				(groupType.equals(EnumConst.GroupType_Area_Admin) &&
    						(ConversionUtil.getDistrictFromGroupAc(selectedGroupNo) == ConversionUtil.getDistrictFromMemberAc(memberAcNo)))) {

    			gm = daoFactory.getGMDAO().findById(groupAcNo, memberAcNo);
    		}

    		// Check for Super Area Admin
    		else if	(groupType.equals(EnumConst.GroupType_Super_Area_Admin)) {

    			gm = daoFactory.getGMDAO().findById(ConversionUtil.getAreaAdminGroupAcFromGroup(selectedGroupNo), memberAcNo);
    		}
    	}

    	return gm;
    }
    
    protected boolean getDistrictForAccess(long selectedDistrictId, long memberAcNo) throws AccessDeniedException, BadRequestException {
    	boolean access = (ConversionUtil.getDistrictFromMemberAc(memberAcNo) == selectedDistrictId);
    	
    	if(!access) { 	
    		access = getDistrictForSHGOneAdmin(selectedDistrictId, memberAcNo);
    	}
    	
    	return access;
    }
    
    protected boolean getDistrictForSHGOneAdmin(long selectedDistrictId, long memberAcNo) throws AccessDeniedException, BadRequestException {

    	long groupAcNo = ConversionUtil.getGroupAcFromMember(memberAcNo);
    	GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		if(gProfile == null) {
			throw new BadRequestException("Invalid Member Group Account!");
		}

    	String groupType = EnumCache.getNameOfGroupType(gProfile.getGroupTypeId());
    	
    	if(groupType.equals(EnumConst.GroupType_SHG_One_Admin)) {
			return true;
    	}

    	// Check for Super Area Admin
    	else if	(groupType.equals(EnumConst.GroupType_Super_Area_Admin)) {
    		
    		GM gm = daoFactory.getGMDAO().findById(ConversionUtil.getAreaAdminGroupAcFromDistrict(selectedDistrictId), memberAcNo);
    		if(gm != null 
    				&& gm.getUiAccessRights() > DataUtil.ZERO_LONG
    				 && gm.getWsAccessRights() > DataUtil.ZERO_LONG) {
    			return true;
    		}
    			
    	}
    	return false;
    }
    
    public MemberAccessREST getMemberParentAccess(long memberAcNo, long reqMemberAcNo) throws BadRequestException {
    	
		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account Number!");
		}
		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);		
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account Number!");
		}
		
		GProfile gProfile = daoFactory.getGProfileDAO().findById(ConversionUtil.getGroupAcFromMember(memberAcNo));		
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account!");
		}
		
		MemberAccessREST access = new MemberAccessREST();
		
		access.setMemberAcNo(memberAcNo);
		access.setMrole(EnumCache.getNameOfMRole(mProfile.getMroleId()));
		access.setStatus(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()));
		
		access.setGroupAcNo(gProfile.getGAcNo());
		access.setGroupType(EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()));
		access.setGroupStatus(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()));
		
		{
			if(!ConversionUtil.isValidMemberAcNo(reqMemberAcNo)) {
				throw new BadRequestException("Invalid Request Member Account Number!");
			}
			MProfile reqProfile = daoFactory.getMProfileDAO().findById(reqMemberAcNo);		
			if(reqProfile == null) {
				throw new BadRequestException("Invalid Request Member Account Number!");
			}
			GRules gRules = daoFactory.getGRulesDAO().findById(ConversionUtil.getGroupAcFromMember(memberAcNo));
			
			access.setPossibleRoles(MemberAccessREST.getPossibleRoles(EnumCache.getNameOfMRole(reqProfile.getMroleId()), access.getGroupType(), gRules));
		}
		
    	return access;
    }
    
    public MemberAccessREST getAllMemberAccessToGroups(long memberAcNo, long reqMemberAcNo) throws BadRequestException {
    	
		MemberAccessREST access = getMemberParentAccess(memberAcNo, reqMemberAcNo);
		
		if(access.getGroupType().equals(EnumConst.GroupType_SHG)) {
			return access;
		}
		
		List<GM> gms = daoFactory.getGMDAO().findByMember(memberAcNo);		
		MapRole mapRole = null;
		
		for(GM gm: gms) {

			if(!ConversionUtil.isValidGroupAcNo(gm.getGroupAcNo())) {
				throw new BadRequestException("Invalid Member Group Account!");
			}
			if(access.getGroupAcNo() == gm.getGroupAcNo()) {
				continue;
			}
			GProfile gProfile = daoFactory.getGProfileDAO().findById(gm.getGroupAcNo());		
			if(gProfile == null) {
				throw new BadRequestException("Invalid Group Account!");
			}
			
			mapRole = new MapRole();
			mapRole.setGroupAcNo(gm.getGroupAcNo());
			mapRole.setGroupType(EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()));
			mapRole.setGroupStatus(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()));
			
			MRoleValue role = MemberAccessREST.getMRoleValue(access.getGroupType(), gm.getUiAccessRights(), gm.getWsAccessRights());
			if(role == null) {
				role = MemberAccessREST.getMRoleValueDefault(access.getGroupType());
			}
			mapRole.setMrole(role.getRole());
			
			access.addGroupsMapping(mapRole);
		}
		
    	return access;
    }
    
    public MemberAccessREST updateMemberParentAccess(MemberAccessREST access, long reqMemberAcNo) throws BadRequestException {
    	
		if(!ConversionUtil.isValidMemberAcNo(access.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account!");
		}
		if(access.getMrole() == null || access.getMrole().isEmpty()) {
			throw new BadRequestException("Invalid Member Role!");
		}
		MRoleValue role = EnumCache.getMRoleValue(access.getMrole());
		if(role == null) {
			throw new BadRequestException("Invalid Member Role: " + access.getMrole() + "!");
		}
		if(access.getStatus() == null || access.getStatus().isEmpty()) {
			throw new BadRequestException("Invalid Member Status!");
		}
		int activeStatusId = EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, access.getStatus());
		if(activeStatusId < 0) {
			throw new BadRequestException("Invalid Member Status!");
		}
		
		MProfile mProfile = daoFactory.getMProfileDAO().findById(access.getMemberAcNo());
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account!");
		}

		if(mProfile.getMroleId() != role.getRoleId()) {

			long groupAcNo = ConversionUtil.getGroupAcFromMember(access.getMemberAcNo());
			GRules gRules = daoFactory.getGRulesDAO().findById(groupAcNo);
			{
				if(!ConversionUtil.isValidMemberAcNo(reqMemberAcNo)) {
					throw new BadRequestException("Invalid Request Member Account Number!");
				}
				MProfile reqProfile = daoFactory.getMProfileDAO().findById(reqMemberAcNo);		
				if(reqProfile == null) {
					throw new BadRequestException("Invalid Request Member Account Number!");
				}
				if(!MemberAccessREST.isPossibleRole(EnumCache.getNameOfMRole(reqProfile.getMroleId()), access.getGroupType(), role.getRole(), gRules)) {
					throw new BadRequestException("Invalid Request Member Role!");
				}
			}
			
			// Make sure that Special Core Member Role is only one in SHG
			if(EnumUtil.isSpecialCoreMember(role.getRole())) {
				if(!EnumUtil.isCoreMember(EnumCache.getNameOfMRole(mProfile.getMroleId()))) {
					throw new BadRequestException("Invalid Member Role!");
				}
				
				List<MProfile> mProfiles = daoFactory.getMProfileDAO().getMembersProfileByRoleByGroup(
						ConversionUtil.getGroupAcFromMember(access.getMemberAcNo()),
						role.getRoleId());

				if(mProfiles != null && !mProfiles.isEmpty()) {
					for(MProfile mProfileOther : mProfiles) {
						mProfileOther.setMroleId(EnumCache.getIndexOfMRole(EnumConst.MRole_Core_Member));
						daoFactory.getMProfileDAO().merge(mProfileOther);

						updateGMAccess(groupAcNo, access.getMemberAcNo(), EnumCache.getMRoleValue(EnumConst.MRole_Core_Member));
					}
				}
			}
			
			else if(EnumUtil.isAssociateMember(EnumCache.getNameOfMRole(mProfile.getMroleId())) && EnumUtil.isCoreMember(role.getRole())) {
				
				GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
				
				if(gProfile.getActiveCoreMembers() >= gRules.getMaxNoOfCoreMembers()) {
					throw new BadRequestException("Max Number Of Core Members can't Excide Limit of : " + gRules.getMaxNoOfCoreMembers() + "!");
				}

				gProfile.setActiveCoreMembers(gProfile.getActiveCoreMembers() + 1);
				gProfile.setActiveAssociateMembers(gProfile.getActiveAssociateMembers() - 1);
				daoFactory.getGProfileDAO().merge(gProfile);
			}
			
			else if(EnumUtil.isCoreMember(EnumCache.getNameOfMRole(mProfile.getMroleId())) && EnumUtil.isAssociateMember(role.getRole())) {
				
				GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
				
				if(gRules.getAllowAssociateMembers() <= DataUtil.ZERO_INTEGER) {
					throw new BadRequestException("Associate Member Not Allowed!");
				}
				
				gProfile.setActiveCoreMembers(gProfile.getActiveCoreMembers() - 1);
				gProfile.setActiveAssociateMembers(gProfile.getActiveAssociateMembers() + 1);
				daoFactory.getGProfileDAO().merge(gProfile);
			}
			
			mProfile.setMroleId(role.getRoleId());
			daoFactory.getMProfileDAO().merge(mProfile);
			
			MMessage mMessage = daoFactory.getMMessageDAO().findById(mProfile.getMemberAcNo());
			if(mMessage != null) {
				mMessage.setMroleId(role.getRoleId());
				daoFactory.getMMessageDAO().merge(mMessage);
				
				if(ConversionUtil.isValidMobileNo(mMessage.getMobileNo())) {
					MobileM mobileM = daoFactory.getMobileMDAO().findById(mMessage.getMobileNo());
					if(mobileM != null) {
						mobileM.setMroleId(role.getRoleId());
						daoFactory.getMobileMDAO().merge(mobileM);
					}
				}
			}

			updateGMAccess(groupAcNo, access.getMemberAcNo(), role);
		}
		
		if(mProfile.getActiveStatusId() != activeStatusId) {
			
			mProfile.setActiveStatusId(activeStatusId);
			daoFactory.getMProfileDAO().merge(mProfile);
		}
		
		return access;
    }
    
    protected GM updateGMAccess(long groupAcNo, long memberAcNo, MRoleValue role) {
    	
		GM gm = daoFactory.getGMDAO().findById(groupAcNo, memberAcNo);
		gm.setUiAccessRights(role.getDefaultUiAccessRights());
		gm.setWsAccessRights(role.getDefaultWsAccessRights());
		daoFactory.getGMDAO().merge(gm);
		
		return gm;
    }
    
    public MemberAccessREST searchMemberAccessToGroup(MemberAccessREST access, long reqMemberAcNo) throws BadRequestException, AccessDeniedException {
    	
		if(!ConversionUtil.isValidMemberAcNo(access.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Group Account!");
		}
		GProfile parentProfile = daoFactory.getGProfileDAO().findById(ConversionUtil.getGroupAcFromMember(access.getMemberAcNo()));		
		if(parentProfile == null) {
			throw new BadRequestException("Invalid Group Account!");
		}
		if(EnumCache.getNameOfGroupType(parentProfile.getGroupTypeId()).equals(EnumConst.GroupType_SHG)) {
			throw new BadRequestException("Can't Search Member to Other Group for SHG Group Type!");
		}
		if(access.getMrole() == null || access.getMrole().isEmpty()) {
			throw new BadRequestException("Invalid Member Role!");
		}
		if(access.getGroupsMappings() == null || access.getGroupsMappings().isEmpty()) {
			throw new BadRequestException("Invalid Mapping Group!");
		}
		
		for(MapRole mapRole: access.getGroupsMappings()) {

			if(!ConversionUtil.isValidGroupAcNo(mapRole.getGroupAcNo())) {
				throw new BadRequestException("Invalid Member Group Account!");
			}
			GProfile gProfile = daoFactory.getGProfileDAO().findById(mapRole.getGroupAcNo());		
			if(gProfile == null) {
				throw new BadRequestException("Invalid Group Account!");
			}
			mapRole.setGroupType(EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()));
			mapRole.setGroupStatus(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()));
			
			GM gm = getGMForAccess(mapRole.getGroupAcNo(), access.getMemberAcNo());
			
			if(gm != null) {
				MRoleValue role = MemberAccessREST.getMRoleValue(access.getGroupType(), gm.getUiAccessRights(), gm.getWsAccessRights());
				if(role == null) {
					role = MemberAccessREST.getMRoleValueDefault(access.getGroupType());
				}
				mapRole.setMrole(role.getRole());
			} else {
				mapRole.setMrole(null);
			}
		}
		
    	return access;
    }
    
    public MemberAccessREST updateMemberAccessToGroup(MemberAccessREST access, long reqMemberAcNo) throws BadRequestException {
    	
		if(!ConversionUtil.isValidMemberAcNo(access.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Group Account!");
		}
		GProfile parentProfile = daoFactory.getGProfileDAO().findById(ConversionUtil.getGroupAcFromMember(access.getMemberAcNo()));		
		if(parentProfile == null) {
			throw new BadRequestException("Invalid Group Account!");
		}
		if(EnumCache.getNameOfGroupType(parentProfile.getGroupTypeId()).equals(EnumConst.GroupType_SHG)) {
			throw new BadRequestException("Can't Update Member to Other Group for SHG Group Type!");
		}
		if(access.getGroupsMappings() == null || access.getGroupsMappings().isEmpty()) {
			throw new BadRequestException("Invalid Mapping Group!");
		}
		if(!ConversionUtil.isValidMemberAcNo(reqMemberAcNo)) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		MProfile reqProfile = daoFactory.getMProfileDAO().findById(reqMemberAcNo);		
		if(reqProfile == null) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		
		for(MapRole mapRole: access.getGroupsMappings()) {
			
			GRules gRules = daoFactory.getGRulesDAO().findById(mapRole.getGroupAcNo());

			if(!MemberAccessREST.isPossibleRole(EnumCache.getNameOfMRole(reqProfile.getMroleId()), access.getGroupType(), mapRole.getMrole(), gRules)) {
				throw new BadRequestException("Invalid Request Member Role!");
			}
			if(!ConversionUtil.isValidGroupAcNo(mapRole.getGroupAcNo())) {
				throw new BadRequestException("Invalid Member Group Account!");
			}
			if(mapRole.getMrole() == null || mapRole.getMrole().isEmpty()) {
				throw new BadRequestException("Invalid Member Role!");
			}
			MRoleValue role = EnumCache.getMRoleValue(mapRole.getMrole());
			if(role == null) {
				throw new BadRequestException("Invalid Member Role: " + mapRole.getMrole() + "!");
			}

			GM gm = daoFactory.getGMDAO().findById(mapRole.getGroupAcNo(), access.getMemberAcNo());
			boolean newGM = false;
			
			if(gm == null) {
				gm = new GM();
				gm.setId(new GMId(mapRole.getGroupAcNo(), access.getMemberAcNo()));
				gm.setGroupAcNo(mapRole.getGroupAcNo());
				gm.setMemberAcNo(access.getMemberAcNo());
				newGM = true;
			}
			gm.setUiAccessRights(role.getDefaultUiAccessRights());
			gm.setWsAccessRights(role.getDefaultWsAccessRights());
			
			if(newGM) {
				daoFactory.getGMDAO().persist(gm);
			} else {
				daoFactory.getGMDAO().merge(gm);
			}
		}
		
    	return access;
    }
    
    public MemberAccessREST removeMemberAccessToGroup(MemberAccessREST access, long reqMemberAcNo) throws BadRequestException {
    	
		if(!ConversionUtil.isValidMemberAcNo(access.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Group Account!");
		}
		GProfile parentProfile = daoFactory.getGProfileDAO().findById(ConversionUtil.getGroupAcFromMember(access.getMemberAcNo()));		
		if(parentProfile == null) {
			throw new BadRequestException("Invalid Group Account!");
		}
		if(EnumCache.getNameOfGroupType(parentProfile.getGroupTypeId()).equals(EnumConst.GroupType_SHG)) {
			throw new BadRequestException("Can't Update Member to Other Group for SHG Group Type!");
		}
		if(access.getGroupsMappings() == null || access.getGroupsMappings().isEmpty()) {
			throw new BadRequestException("Invalid Mapping Group!");
		}
				
		for(MapRole mapRole: access.getGroupsMappings()) {
			
			if(!ConversionUtil.isValidGroupAcNo(mapRole.getGroupAcNo())) {
				throw new BadRequestException("Invalid Member Group Account!");
			}

			GM gm = daoFactory.getGMDAO().findById(mapRole.getGroupAcNo(), access.getMemberAcNo());
			if(gm != null) {
				daoFactory.getGMDAO().remove(gm);
			}
			mapRole.setMrole(null);
		}		
    	return access;
    }
    
	public List<AccessCodeValue> getUiAccessCodes(long reqMemberAcNo, long accessRights) throws BadRequestException {
		
		if(!ConversionUtil.isValidMemberAcNo(reqMemberAcNo)) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		MProfile reqProfile = daoFactory.getMProfileDAO().findById(reqMemberAcNo);		
		if(reqProfile == null) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		
		MRoleValue mRoleValue = EnumCache.getMRoleValue(reqProfile.getMroleId());
		
		switch(mRoleValue.getSystem()) {

		case EnumConst.MRole_System_SHG:
			return uiAccessCodeUtil.getAccessCodes(accessRights);

		case EnumConst.MRole_System_Micro_Retailer:
			return mrUiAccessCodeUtil.getAccessCodes(accessRights);
		}
		
		return new ArrayList<AccessCodeValue>(0);
	}
    
	public long generateUiAccessRight(long reqMemberAcNo, List<String> access) throws BadRequestException {
		
		if(!ConversionUtil.isValidMemberAcNo(reqMemberAcNo)) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		MProfile reqProfile = daoFactory.getMProfileDAO().findById(reqMemberAcNo);		
		if(reqProfile == null) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		
		MRoleValue mRoleValue = EnumCache.getMRoleValue(reqProfile.getMroleId());
		
		switch(mRoleValue.getSystem()) {

		case EnumConst.MRole_System_SHG:
			return uiAccessCodeUtil.generateAccessRight(access);

		case EnumConst.MRole_System_Micro_Retailer:
			return mrUiAccessCodeUtil.generateAccessRight(access);
		}
		
		return DataUtil.ZERO_LONG;
	}
   
	public List<AccessCodeValue> getWsAccessCodes(long reqMemberAcNo, long accessRights) throws BadRequestException {
		
		if(!ConversionUtil.isValidMemberAcNo(reqMemberAcNo)) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		MProfile reqProfile = daoFactory.getMProfileDAO().findById(reqMemberAcNo);		
		if(reqProfile == null) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		
		MRoleValue mRoleValue = EnumCache.getMRoleValue(reqProfile.getMroleId());
		
		switch(mRoleValue.getSystem()) {

		case EnumConst.MRole_System_SHG:
			return wsAccessCodeUtil.getAccessCodes(accessRights);

		case EnumConst.MRole_System_Micro_Retailer:
			return mrWsAccessCodeUtil.getAccessCodes(accessRights);
		}
		
		return new ArrayList<AccessCodeValue>(0);
	}
    
	public long generateWsAccessRight(long reqMemberAcNo, List<String> access) throws BadRequestException {
		
		if(!ConversionUtil.isValidMemberAcNo(reqMemberAcNo)) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		MProfile reqProfile = daoFactory.getMProfileDAO().findById(reqMemberAcNo);		
		if(reqProfile == null) {
			throw new BadRequestException("Invalid Request Member Account Number!");
		}
		
		MRoleValue mRoleValue = EnumCache.getMRoleValue(reqProfile.getMroleId());
		
		switch(mRoleValue.getSystem()) {

		case EnumConst.MRole_System_SHG:
			return wsAccessCodeUtil.generateAccessRight(access);

		case EnumConst.MRole_System_Micro_Retailer:
			return mrWsAccessCodeUtil.generateAccessRight(access);
		}
		
		return DataUtil.ZERO_LONG;
	}
	
}
