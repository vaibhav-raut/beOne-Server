package com.beone.shg.common.web.security;

import org.springframework.security.core.AuthenticationException;

public class ShgAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 139487857447514114L;

	public ShgAuthenticationException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
    public ShgAuthenticationException(String msg, Throwable t) {
        super(msg, t);
		// TODO Auto-generated constructor stub
    }

}
