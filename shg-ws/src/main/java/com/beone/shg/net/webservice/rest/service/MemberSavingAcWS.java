package com.beone.shg.net.webservice.rest.service;

import java.util.List;

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
import com.beone.shg.net.config.RESTConst;
import com.beone.shg.net.persistence.bo.MemberSavingAcBO;
import com.beone.shg.net.webservice.rest.model.rest.MemberSavingAcREST;
import com.beone.shg.net.webservice.rest.support.AccessCheckUtil;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;

@Controller
@RequestMapping("/msaving")
public class MemberSavingAcWS implements InitializingBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(MemberSavingAcWS.class);

	@Autowired
	@Qualifier("memberSavingAcBO")
	private MemberSavingAcBO memberSavingAcBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
    @Override
    // initialization
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("afterPropertiesSet()");
    }

    @RequestMapping(value = "/v1/member_saving_ac", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> addMemberSavingAc(@RequestBody MemberSavingAcREST request) {
		// Log that request came to WS
		LOGGER.debug("addMemberSavingAc(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, request.getMemberAc());

			MemberSavingAcREST response = memberSavingAcBO.add(request);
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

    @RequestMapping(value = "/v1/update_member_saving_ac", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<?> updateMemberSavingAc(@RequestBody MemberSavingAcREST request) {
		// Log that request came to WS
		LOGGER.debug("updateMemberSavingAc(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, request.getMemberAc());

			memberSavingAcBO.updateMemberSavingAc(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

    @RequestMapping(value = "/v1/approve_member_saving_ac", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<?> approveReject(@RequestBody MemberSavingAcREST request) {
		// Log that request came to WS
		LOGGER.debug("approveReject(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, request.getMemberAc());

			Object response = memberSavingAcBO.approveReject(request);
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
    
	@RequestMapping(value = "/v1/member_saving_ac/{lang}/{memberSavingAcNo}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMemberSavingAc(@PathVariable("lang") String lang,
			@PathVariable("memberSavingAcNo") long memberSavingAcNo) {
		// Log that request came to WS
		LOGGER.debug("getMemberSavingAc(lang:" + lang + ", memberSavingAcNo:" + memberSavingAcNo + ")");

		try {
			MemberSavingAcREST response = memberSavingAcBO.getMemberSavingAc(lang, memberSavingAcNo);    	

			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, response.getMemberAc());

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
    
	@RequestMapping(value = "/v1/member_saving_ac_for_member/{lang}/{memberAcNo}/{status}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMemberSavingAcForMember(@PathVariable("lang") String lang,
			@PathVariable("memberAcNo") long memberAcNo,
			@PathVariable("status") String status) {
		
		// Log that request came to WS
		LOGGER.debug("getMemberSavingAcsForMember(lang:" + lang + ", memberAcNo:" + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, memberAcNo);

			Object response = memberSavingAcBO.getMemberSavingAcsForMember(lang, memberAcNo, status);    	
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
    
	@RequestMapping(value = "/v1/member_saving_ac_for_group/{lang}/{groupAcNo}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMemberSavingAcsForGroup(@PathVariable("lang") String lang,
			@PathVariable("groupAcNo") long groupAcNo,
			@PathVariable("accountType") String accountType) {
		
		// Log that request came to WS
		LOGGER.debug("getMemberSavingAcsForGroup(lang:" + lang + ", groupAcNo:" + groupAcNo + ", accountType:" + accountType + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			List<MemberSavingAcREST> response = memberSavingAcBO.getMemberSavingAcsForGroup(lang, groupAcNo);    	
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
