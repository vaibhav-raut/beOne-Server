package com.beone.shg.net.webservice.rest.support;

import org.springframework.http.ResponseEntity;

import com.beone.shg.net.webservice.rest.model.gen.ErrorResponseREST;


//@Controller
public class DefaultController {
	// @RequestMapping(produces = "application/json")
	public ResponseEntity<ErrorResponseREST> forwardRequest() {
		return RestResponse.METHOD_NOT_ALLOWED();
	}
}
