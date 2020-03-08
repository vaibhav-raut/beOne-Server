package com.beone.shg.net.webservice.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beone.shg.common.web.security.ShgAuthToken;
import com.beone.shg.common.web.security.annotation.UnsecuredMethod;
import com.beone.shg.net.persistence.bo.ProfileBO;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingData;
import com.beone.shg.net.webservice.rest.support.AccessCheckUtil;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;

@Controller
@RequestMapping("/profile")
public class ProfileWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProfileWS.class);

	@Autowired
	@Qualifier("profileBO")
	private ProfileBO profileBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");
	}
	
	@RequestMapping(value = "/v1/mprofiling_data/{memberAcNo}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMProfilingData(@PathVariable("memberAcNo") long memberAcNo) {
		
		// Log that request came to WS
		LOGGER.debug("getMProfilingData(memberAcNo:" + memberAcNo + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, memberAcNo);
			
			Object response = profileBO.getMProfilingData(memberAcNo);
			return RestResponse.OK(response);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
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

	@RequestMapping(value = "/v1/update_mprofiling_data", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMProfilingData(@RequestBody MProfilingData request) {
		
		// Log that request came to WS
		LOGGER.debug("updateMProfilingData(MProfilingData:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, request.getMemberAcNo());
			
			Object response = profileBO.updateMProfilingData(request);
			return RestResponse.OK(response);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
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
