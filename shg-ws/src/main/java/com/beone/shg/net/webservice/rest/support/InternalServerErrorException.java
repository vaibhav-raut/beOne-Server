package com.beone.shg.net.webservice.rest.support;

public class InternalServerErrorException extends Exception {

	private static final long serialVersionUID = 1120314055323295541L;
	private static final String InternalServerErrorMessage = "Internal Server Error Exception";

	public InternalServerErrorException() {
		super(InternalServerErrorMessage);
	}

	public InternalServerErrorException(String message) {
		super(InternalServerErrorMessage + ": " + message);
	}
}
