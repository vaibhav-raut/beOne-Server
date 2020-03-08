package com.beone.shg.net.auth;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeEnum;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeValue;

public abstract class AccessCodeUtil {

	protected Map<Integer, AccessCodeValue> accessCodeIdMap = new LinkedHashMap<Integer, AccessCodeValue>();
	protected Map<String, AccessCodeValue> accessCodeMap = new LinkedHashMap<String, AccessCodeValue>();

	public abstract void loadAccessCode(AccessCodeEnum accessCodeEnum);
	
	public long getAccessKey(String accessCode, String accessLevel) {		
		long key = DataUtil.ZERO_LONG;

		if(accessCodeMap.containsKey(accessCode)) {
			long accessCodeId = accessCodeMap.get(accessCode).getAccessCodeId();
			long accessLevelValue = AccessLevel.getAccessLevelValue(accessLevel);

			if(accessCodeId > DataUtil.ZERO_LONG && accessLevelValue > DataUtil.ZERO_LONG) {
				key = (accessLevelValue * (long)Math.pow(AccessLevel.ACCESS_TOP, (accessCodeId - 1)));
			}
		}
		return key;
	}

	public long getAccessKey(String accessWord) {
		long key = DataUtil.ZERO_LONG;

		if(accessWord != null && !accessWord.isEmpty()) {
			String[] accessArray = accessWord.split(":");
			if(accessArray != null && accessArray.length > 1) {
				key = getAccessKey(accessArray[0], accessArray[1]);
			}
		}

		return key;
	}
	
	public List<AccessCodeValue> getAccessCodes(long accessRight) {
		List<AccessCodeValue> accessCodes = new ArrayList<AccessCodeValue>();
		
		for(AccessCodeValue value: accessCodeIdMap.values()) {
			String[] accessLevels = value.getAccessLevel().split(":");
			String finalAccessLevel = DataUtil.EMPTY_STRING;
			
			for(String accessLevel: accessLevels) {
				if(!accessLevel.equals(AccessLevel.NO_ACCESS.toString())) {
					if((getAccessKey(value.getAccessCode(), accessLevel) & accessRight) > DataUtil.ZERO_LONG) {
						finalAccessLevel = accessLevel;
					} else {
						break;
					}
				}
			}
			
			if(!finalAccessLevel.equals(DataUtil.EMPTY_STRING)) {
				accessCodes.add(new AccessCodeValue(value, finalAccessLevel));
			}			
		}
		
		return accessCodes;
	}
	
	public long generateAccessRight(List<String> accessWords) {
		long accessRight = DataUtil.ZERO_LONG;
		
		for(String accessWord : accessWords) {
			accessRight = (accessRight | getAccessKey(accessWord));
		}
		
		return accessRight;
	}
	
	public boolean isAccess(long accessRight, String accessCode, String accessLevel) {
		List<AccessCodeValue> accessCodes = getAccessCodes(accessRight);
		
		for(AccessCodeValue code: accessCodes) {
			if(code.getAccessCode().equals(accessCode) && 
					((AccessLevel.getAccessLevelValue(code.getAccessLevel()) & AccessLevel.getAccessLevelValue(accessLevel)) > 0)) {
				return true;
			}
		}
		
		return false;
	}
}
