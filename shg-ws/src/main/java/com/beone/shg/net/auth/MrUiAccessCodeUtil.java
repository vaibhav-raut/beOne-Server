package com.beone.shg.net.auth;

import org.springframework.stereotype.Component;

import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeEnum;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeValue;

@Component("mrUiAccessCodeUtil")
public class MrUiAccessCodeUtil extends AccessCodeUtil {

	@Override
	public void loadAccessCode(AccessCodeEnum accessCodeEnum) {
		accessCodeIdMap.clear();
		accessCodeMap.clear();

		for(AccessCodeValue value: accessCodeEnum.getEnumValues()) {
			accessCodeIdMap.put(value.getAccessCodeId(), value);
			accessCodeMap.put(value.getAccessCode(), value);
		}
		
		EnumCache.setMrUiAccessCodeUtil(this);
	}
}
