package com.beone.shg.net.webservice.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beone.shg.common.web.security.annotation.UnsecuredMethod;
import com.beone.shg.net.config.RESTConst;
import com.beone.shg.net.persistence.bo.GroupDataBO;
import com.beone.shg.net.webservice.rest.model.rest.GroupAllDataREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;

@Controller
@RequestMapping("/data")
public class GroupDataWS implements InitializingBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(GroupDataWS.class);

	@Autowired
	@Qualifier("groupDataBO")
	private GroupDataBO groupDataBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
    @Override
    // initialization
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("afterPropertiesSet()");
    }

    @RequestMapping(value = "/v1/group_data_import", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> importGroupData(@RequestBody GroupAllDataREST request) {
		// Log that request came to WS
		LOGGER.debug("importGroupData(request:" + request + ")");

		try {
			groupDataBO.importGroupData(request);
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
    
	@RequestMapping(value = "/v1/group_data_export/{groupAcNo}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> exportGroupData(@PathVariable("groupAcNo") long groupAcNo) {
		// Log that request came to WS
		LOGGER.debug("exportGroupData(groupAcNo:" + groupAcNo + ")");

		try {
			Object response = groupDataBO.exportGroupData(groupAcNo);    	
			return RestResponse.OK(response);

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
