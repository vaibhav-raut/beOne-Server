package com.beone.shg.net.webservice.rest.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.beone.shg.net.webservice.rest.model.gen.ErrorREST;
import com.beone.shg.net.webservice.rest.model.gen.ErrorResponseREST;

public class RestResponse {

	public static <T> ResponseEntity<T> CREATED(T resource, String resourceUrl) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpRESTHeaders.CONTENT_LOСATION, resourceUrl);
		return new ResponseEntity<T>(resource, headers, HttpStatus.CREATED);
	}

	public static <T> ResponseEntity<T> MOVED_PERMANENTLY(T resource, String resourceUrl) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpRESTHeaders.LOСATION, resourceUrl);
		return new ResponseEntity<T>(resource, headers, HttpStatus.MOVED_PERMANENTLY);
	}

	public static <T> ResponseEntity<T> OK(T resource) {
		return new ResponseEntity<T>(resource, HttpStatus.OK);
	}

	private static ResponseEntity<ErrorResponseREST> createErrorResponse(HttpStatus status, String message, String reference) {
		ErrorREST error = new ErrorREST();
		error.setMessage(message);
		error.setReference(reference);
		ErrorResponseREST errorResponse = new ErrorResponseREST();
		errorResponse.setError(error);
		return new ResponseEntity<ErrorResponseREST>(errorResponse, status);
	}

	public static ResponseEntity<ErrorResponseREST> NOT_FOUND(String message) {
		return createErrorResponse(HttpStatus.NOT_FOUND, message, null);
	}

	public static ResponseEntity<ErrorResponseREST> METHOD_NOT_ALLOWED() {
		return createErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "HTTP method is not allowed for this resource", null);
	}

	public static ResponseEntity<ErrorResponseREST> INTERNAL_SERVER_ERROR(String message) {
		return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
	}

	public static ResponseEntity<ErrorResponseREST> BAD_REQUEST(String message) {
		return createErrorResponse(HttpStatus.BAD_REQUEST, message, null);
	}

	public static ResponseEntity<ErrorResponseREST> FORBIDDEN(String message) {
		return createErrorResponse(HttpStatus.FORBIDDEN, message, null);
	}

}
