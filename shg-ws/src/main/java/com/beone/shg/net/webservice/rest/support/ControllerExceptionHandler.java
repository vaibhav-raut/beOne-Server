package com.beone.shg.net.webservice.rest.support;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.beone.shg.net.webservice.rest.model.gen.ErrorREST;

public class ControllerExceptionHandler implements HandlerExceptionResolver{
	private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class); 

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
		ErrorREST error = new ErrorREST();
		HttpStatus httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
		StackTraceElement[] stackTrace = ex.getStackTrace();

		if (isSpringHttpConverterError(stackTrace)) {
			error.setMessage("Unable to parse request ("+ex+")");
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		else {
			error.setMessage("Internal Server Error ("+ex+")");
		}
	    mv.addObject("error", error);
	    response.setStatus(httpStatus.value());
	    log.error("HttpStatus("+httpStatus+") "+error.getMessage(),ex); 
		return mv;
	}

	private boolean isSpringHttpConverterError(StackTraceElement[] stackTrace) {
		for (StackTraceElement stackTraceElement : stackTrace) {
			if ("org.springframework.http.converter.AbstractHttpMessageConverter".equals(stackTraceElement.getClassName())) {
				return true;
			}
		}
		return false;
	}

}
