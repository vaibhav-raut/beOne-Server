package com.beone.shg.net.webservice.rest.support;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = 5462665412475917110L;
	private static final String BadRequestMessage = "Bad Input Request Exception";

	public BadRequestException() {
		super(BadRequestMessage);
	}

	public BadRequestException(String message) {
		super(BadRequestMessage + ": " + message);
	}
}
