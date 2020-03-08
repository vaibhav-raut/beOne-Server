package com.beone.shg.net.webservice.rest.support;

public class AccessDeniedException extends Exception {

	private static final long serialVersionUID = -2875683020171812L;
	private static final String AccessDeniedMessage = "Access Denied Exception";

	public AccessDeniedException() {
		super(AccessDeniedMessage);
	}

	public AccessDeniedException(String message) {
		super(AccessDeniedMessage + ": " + message);
	}
}
