package com.beone.shg.net.webservice.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beone.shg.common.web.security.annotation.UnsecuredMethod;
import com.beone.shg.net.config.RESTConst;
import com.beone.shg.net.persistence.bo.DataGeneratorBO;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;

@Controller
@RequestMapping("/data_generator")
public class DataGeneratorWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(DataGeneratorWS.class);

	@Autowired
	@Qualifier("dataGeneratorBO")
	private DataGeneratorBO dataGeneratorBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");

	}

	// API to Create SHG-One Groups for each District
	@UnsecuredMethod(justification = "Generate Initial Data, when no User is Registered")
	@RequestMapping(value = "/v1/add_default_groups", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addDefaultGroups() {
		// Log that request came to WS
		LOGGER.debug("addDefaultGroups()");

		try {
			dataGeneratorBO.addDefaultGroups();      
			return RestResponse.OK(RESTConst.SUCCESS);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
//		} catch (InternalServerErrorException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}
	  
	@RequestMapping(value = "/v1/add_group/{district}/{groupType}/{mRole}", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addGroup(@PathVariable("district") String district,
			@PathVariable("groupType") String groupType, @PathVariable("mRole") String mRole) {
		// Log that request came to WS
		LOGGER.debug("addGroup(district:" + district + ", groupType:" + groupType + ", mRole:" + mRole + ")");

		try {

			dataGeneratorBO.addGroup(district, groupType, mRole);    	
			return RestResponse.OK(RESTConst.SUCCESS);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
//		} catch (InternalServerErrorException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}
}
