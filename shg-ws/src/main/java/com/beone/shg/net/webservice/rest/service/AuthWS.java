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
import com.beone.shg.net.persistence.bo.AuthBO;
import com.beone.shg.net.webservice.rest.model.rest.DistrictSearchInfo;
import com.beone.shg.net.webservice.rest.model.rest.GroupSearchInfo;
import com.beone.shg.net.webservice.rest.model.rest.LockRequestREST;
import com.beone.shg.net.webservice.rest.model.rest.LoginInfoREST;
import com.beone.shg.net.webservice.rest.model.rest.LoginRequestREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberAccessREST;
import com.beone.shg.net.webservice.rest.model.rest.PassChangeRequestREST;
import com.beone.shg.net.webservice.rest.support.AccessCheckUtil;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.InternalServerErrorException;
import com.beone.shg.net.webservice.rest.support.RestResponse;

@Controller
@RequestMapping("/auth")
public class AuthWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(AuthWS.class);
	private final static String PORTAL_KEY = "Direct_Access_for_Portal_Key";

	@Autowired
	@Qualifier("authBO")
	private AuthBO authBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");
	}

	// Member login API
	@RequestMapping(value = "/v1/login", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody LoginRequestREST request) {
		
		// Log that request came to WS
		LOGGER.debug("login(LoginRequestREST:" + request + ")");

		try {
			
			LoginInfoREST response = authBO.login(request);			
			return RestResponse.OK(response);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// Select Group for Member API
	@RequestMapping(value = "/v1/select_group", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> selectGroup(@RequestBody LoginRequestREST request) {
		
		// Log that request came to WS
		LOGGER.debug("selectGroup(LoginRequestREST:" + request + ")");

		try {
			LoginInfoREST response = authBO.selectGroup(request);			
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// Set Member Password
	@RequestMapping(value = "/v1/reset_member_password", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> resetMemberPassword(@RequestBody PassChangeRequestREST request) {
		
		// Log that request came to WS
		LOGGER.debug("resetMemberPassword(PassChangeRequestREST:" + request + ")");

		try {			
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, request.getMemberAcNo());

			PassChangeRequestREST responce = authBO.resetMemberPassword(request);			
			return RestResponse.OK(responce);

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

	// Set Member Password
	@RequestMapping(value = "/v1/change_member_password", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> changeMemberPassword(@RequestBody PassChangeRequestREST request) {
		
		// Log that request came to WS
		LOGGER.debug("changeMemberPassword(PassChangeRequestREST:" + request + ")");

		try {			
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForMemberAcNo(token, request.getMemberAcNo());

			authBO.changeMemberPassword(request);			
			return RestResponse.OK(RESTConst.message("Successfully Changed Password!"));

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

	// Search Group No in District API
	@RequestMapping(value = "/v1/search_group_no_in_district", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> searchGroupNoInDistrict(@RequestBody GroupSearchInfo request) {
		
		// Log that request came to WS
		LOGGER.debug("searchGroupNoInDistrict(GroupSearchInfo:" + request + ")");

		try {
			GroupSearchInfo response = authBO.searchGroupNoInDistrict(request);			
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

	// Search Group No in District API
	@RequestMapping(value = "/v1/search_groups_for_member", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> searchGroupsForMember(@RequestBody GroupSearchInfo request) {
		
		// Log that request came to WS
		LOGGER.debug("searchGroupsForMember(GroupSearchInfo:" + request + ")");

		try {
			GroupSearchInfo response = authBO.searchGroupsForMember(request);			
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

	// Search Group By Name in District API
	@RequestMapping(value = "/v1/search_group_by_name_in_district", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> searchGroupByNameInDistrict(@RequestBody GroupSearchInfo request) {
		
		// Log that request came to WS
		LOGGER.debug("searchGroupByNameInDistrict(GroupSearchInfo:" + request + ")");

		try {
			GroupSearchInfo response = authBO.searchGroupByNameInDistrict(request);			
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

	@RequestMapping(value = "/v1/access_to_district", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getAccessToDistrict(@RequestBody DistrictSearchInfo request) {
		
		// Log that request came to WS
		LOGGER.debug("getAccessToDistrict(DistrictSearchInfo:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForMemberAcNo(token, request.getMemberAcNo());
			
			DistrictSearchInfo response = authBO.getAccessToDistrict(request);			
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
	
	// Get UI Access Codes 
	@RequestMapping(value = "/v1/ui_access_codes/{accessRights}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getUiAccessCodes(@PathVariable("accessRights") long accessRights) {
		
		// Log that request came to WS
		LOGGER.debug("getAccessCodes(accessRights:" + accessRights + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());

			Object response = authBO.getUiAccessCodes(token.getMemberAcNo(), accessRights);
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
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
	
	// Get UI Access Codes for Portal
	@RequestMapping(value = "/v1/portal_access/ui_access_codes/{portalKey}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getUiAccessCodesForPortal(@PathVariable("portalKey") String portalKey) {
		
		// Log that request came to WS
		LOGGER.debug("getAccessCodes(portalKey:" + portalKey + ")");
		
		try {			
			if(!portalKey.equals(PORTAL_KEY)) {
				throw new AccessDeniedException("Invalid Portal Key");
			}
			
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			
			Object response = authBO.getUiAccessCodes(token.getMemberAcNo(), token.getUiAccess());

			return RestResponse.OK(response);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
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

	// Get UI Access Rights for given Access Codes 
	@RequestMapping(value = "/v1/generate_ui_access_right", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> generateUiAccessRight(@RequestBody List<String> request) {
		
		// Log that request came to WS
		LOGGER.debug("generateAccessRight(request: " + request + ")");

		try {
			
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			
			Object response = authBO.generateUiAccessRight(token.getMemberAcNo(), request);

			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
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
	
	// Get WS Access Codes 
	@RequestMapping(value = "/v1/ws_access_codes/{accessRights}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getWsAccessCodes(@PathVariable("accessRights") long accessRights) {
		
		// Log that request came to WS
		LOGGER.debug("getAccessCodes(accessRights:" + accessRights + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());

			Object response = authBO.getWsAccessCodes(token.getMemberAcNo(), accessRights);
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
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

	// Get WS Access Rights for given Access Codes 
	@RequestMapping(value = "/v1/generate_ws_access_right", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> generateWsAccessRight(@RequestBody List<String> request) {
		
		// Log that request came to WS
		LOGGER.debug("generateAccessRight(request: " + request + ")");
		
		try {
			
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			
			Object response = authBO.generateWsAccessRight(token.getMemberAcNo(), request);

			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
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

	// lockService API
	@RequestMapping(value = "/v1/lock_service", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> lockService(@RequestBody LockRequestREST request) {
		
		// Log that request came to WS
		LOGGER.debug("lockService(LockRequestREST:" + request + ")");

		try {
			
			LockRequestREST response = authBO.lockService(request);			
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
	
	@RequestMapping(value = "/v1/member_parent_access/{memberAcNo}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMemberParentAccess(@PathVariable("memberAcNo") long memberAcNo) {
		
		// Log that request came to WS
		LOGGER.debug("getMemberParentAccess(memberAcNo:" + memberAcNo + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, memberAcNo);
			
			Object response = authBO.getMemberParentAccess(memberAcNo, token.getMemberAcNo());
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
	
	@RequestMapping(value = "/v1/all_member_access_to_groups/{memberAcNo}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllMemberAccessToGroups(@PathVariable("memberAcNo") long memberAcNo) {
		
		// Log that request came to WS
		LOGGER.debug("getAllMemberAccessToGroups(memberAcNo:" + memberAcNo + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, memberAcNo);
			
			Object response = authBO.getAllMemberAccessToGroups(memberAcNo, token.getMemberAcNo());
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

	@RequestMapping(value = "/v1/update_member_parent_access", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMemberParentAccess(@RequestBody MemberAccessREST request) {
		
		// Log that request came to WS
		LOGGER.debug("updateMemberParentAccess(MemberAccessREST:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, request.getMemberAcNo());
			
			MemberAccessREST response = authBO.updateMemberParentAccess(request, token.getMemberAcNo());			
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

	@RequestMapping(value = "/v1/search_member_access_to_group", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> searchMemberAccessToGroup(@RequestBody MemberAccessREST request) {
		
		// Log that request came to WS
		LOGGER.debug("searchMemberAccessToGroup(MemberAccessREST:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, request.getMemberAcNo());
			
			MemberAccessREST response = authBO.searchMemberAccessToGroup(request, token.getMemberAcNo());			
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

	@RequestMapping(value = "/v1/update_member_access_to_group", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMemberAccessToGroup(@RequestBody MemberAccessREST request) {
		
		// Log that request came to WS
		LOGGER.debug("updateMemberAccessToGroup(MemberAccessREST:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, request.getMemberAcNo());
			
			MemberAccessREST response = authBO.updateMemberAccessToGroup(request, token.getMemberAcNo());			
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

	@RequestMapping(value = "/v1/remove_member_access_to_group", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> removeMemberAccessToGroup(@RequestBody MemberAccessREST request) {
		
		// Log that request came to WS
		LOGGER.debug("removeMemberAccessToGroup(MemberAccessREST:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, request.getMemberAcNo());
			
			MemberAccessREST response = authBO.removeMemberAccessToGroup(request, token.getMemberAcNo());			
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
