package com.beone.shg.net.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import com.beone.shg.common.web.security.ShgAuthToken;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DataUtil;

public class ShgWSAccessDecisionManager extends AbstractAccessDecisionManager {

	protected ShgWSAccessDecisionManager() {
	}

	public ShgWSAccessDecisionManager(@SuppressWarnings("rawtypes") List<AccessDecisionVoter> decisionVoters) {
		super(decisionVoters);
	}

	@SuppressWarnings("finally")
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		try {

			if(configAttributes != null && configAttributes.size() >= 1) {

				List<ConfigAttribute> list = new ArrayList<ConfigAttribute>(configAttributes);
				long accessKey = EnumCache.getWsAccessCodeUtil().getAccessKey(list.get(0).getAttribute());

				if((((ShgAuthToken) authentication).getWsAccess() & accessKey) != DataUtil.ZERO_LONG) {
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied!"));
		}
	}
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}
}
