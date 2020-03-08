package com.beone.shg.common.web.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class ShgAuthToken extends AbstractAuthenticationToken {
	
	private String token;
	private boolean processed;
	private long memberAcNo;
	private long selectedGroupAcNo;
	private long wsAccess;
	private long uiAccess;	

	public ShgAuthToken(String token) {
		this(token, null);
		processed = false;
	}
	
	public ShgAuthToken(String token, long memberAcNo, long selectedGroupAcNo) {
		this(token, null);
		processed = false;
		this.memberAcNo = memberAcNo;
		this.selectedGroupAcNo = selectedGroupAcNo;
	}
	
	public ShgAuthToken(String token, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.token = token;
	}

	public boolean getProcessed() {
		return processed;
	}
	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getMemberAcNo() {
		return memberAcNo;
	}

	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}

	public long getSelectedGroupAcNo() {
		return selectedGroupAcNo;
	}

	public void setSelectedGroupAcNo(long selectedGroupAcNo) {
		this.selectedGroupAcNo = selectedGroupAcNo;
	}

	public long getWsAccess() {
		return wsAccess;
	}

	public void setWsAccess(long wsAccess) {
		this.wsAccess = wsAccess;
	}

	public long getUiAccess() {
		return uiAccess;
	}

	public void setUiAccess(long uiAccess) {
		this.uiAccess = uiAccess;
	}
	
	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}
}
