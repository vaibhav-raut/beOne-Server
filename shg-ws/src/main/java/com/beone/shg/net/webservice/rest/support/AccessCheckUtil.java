package com.beone.shg.net.webservice.rest.support;

import com.beone.shg.common.web.security.ShgAuthToken;
import com.beone.shg.net.persistence.util.ConversionUtil;

public class AccessCheckUtil {

	public static void passForGroupAcNo(ShgAuthToken token, long groupAcNo) throws AccessDeniedException {
		if(token.getSelectedGroupAcNo() != groupAcNo) {
			throw new AccessDeniedException("Access Denied to the Requested Group");
		}
	}

	public static void passForGroupByMemberAcNo(ShgAuthToken token, long memberAcNo) throws AccessDeniedException {
		if(token.getSelectedGroupAcNo() != ConversionUtil.getGroupAcFromMember(memberAcNo)) {
			throw new AccessDeniedException("Access Denied to the Requested Group");
		}
	}

	public static void passForMemberAcNo(ShgAuthToken token, long memberAcNo) throws AccessDeniedException {
		if(token.getMemberAcNo() != memberAcNo) {
			throw new AccessDeniedException("Access Denied to the Requested Member Ac No");
		}
	}
}
